/*   1:    */ package org.apache.xalan.lib;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileNotFoundException;
/*   5:    */ import java.io.FileOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.OutputStream;
/*   8:    */ import java.net.MalformedURLException;
/*   9:    */ import java.util.Hashtable;
/*  10:    */ import javax.xml.transform.Result;
/*  11:    */ import javax.xml.transform.TransformerException;
/*  12:    */ import javax.xml.transform.stream.StreamResult;
/*  13:    */ import org.apache.xalan.extensions.XSLProcessorContext;
/*  14:    */ import org.apache.xalan.templates.ElemExtensionCall;
/*  15:    */ import org.apache.xalan.templates.OutputProperties;
/*  16:    */ import org.apache.xalan.transformer.MsgMgr;
/*  17:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  18:    */ import org.apache.xml.serializer.SerializationHandler;
/*  19:    */ import org.apache.xpath.XPath;
/*  20:    */ import org.apache.xpath.XPathContext;
/*  21:    */ import org.apache.xpath.objects.XObject;
/*  22:    */ import org.xml.sax.ContentHandler;
/*  23:    */ import org.xml.sax.SAXException;
/*  24:    */ 
/*  25:    */ public class Redirect
/*  26:    */ {
/*  27:129 */   protected Hashtable m_formatterListeners = new Hashtable();
/*  28:134 */   protected Hashtable m_outputStreams = new Hashtable();
/*  29:    */   public static final boolean DEFAULT_APPEND_OPEN = false;
/*  30:    */   public static final boolean DEFAULT_APPEND_WRITE = false;
/*  31:    */   
/*  32:    */   public void open(XSLProcessorContext context, ElemExtensionCall elem)
/*  33:    */     throws MalformedURLException, FileNotFoundException, IOException, TransformerException
/*  34:    */   {
/*  35:157 */     String fileName = getFilename(context, elem);
/*  36:158 */     Object flistener = this.m_formatterListeners.get(fileName);
/*  37:    */     Object ignored;
/*  38:159 */     if (null == flistener)
/*  39:    */     {
/*  40:161 */       String mkdirsExpr = elem.getAttribute("mkdirs", context.getContextNode(), context.getTransformer());
/*  41:    */       
/*  42:    */ 
/*  43:164 */       boolean mkdirs = (mkdirsExpr.equals("true")) || (mkdirsExpr.equals("yes"));
/*  44:    */       
/*  45:    */ 
/*  46:    */ 
/*  47:168 */       String appendExpr = elem.getAttribute("append", context.getContextNode(), context.getTransformer());
/*  48:169 */       boolean append = (appendExpr.equals("true")) || (appendExpr.equals("yes"));
/*  49:    */       
/*  50:    */ 
/*  51:172 */       ignored = makeFormatterListener(context, elem, fileName, true, mkdirs, append);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void write(XSLProcessorContext context, ElemExtensionCall elem)
/*  56:    */     throws MalformedURLException, FileNotFoundException, IOException, TransformerException
/*  57:    */   {
/*  58:186 */     String fileName = getFilename(context, elem);
/*  59:187 */     Object flObject = this.m_formatterListeners.get(fileName);
/*  60:    */     
/*  61:189 */     boolean inTable = false;
/*  62:    */     ContentHandler formatter;
/*  63:190 */     if (null == flObject)
/*  64:    */     {
/*  65:192 */       String mkdirsExpr = elem.getAttribute("mkdirs", context.getContextNode(), context.getTransformer());
/*  66:    */       
/*  67:    */ 
/*  68:    */ 
/*  69:196 */       boolean mkdirs = (mkdirsExpr.equals("true")) || (mkdirsExpr.equals("yes"));
/*  70:    */       
/*  71:    */ 
/*  72:    */ 
/*  73:200 */       String appendExpr = elem.getAttribute("append", context.getContextNode(), context.getTransformer());
/*  74:201 */       boolean append = (appendExpr.equals("true")) || (appendExpr.equals("yes"));
/*  75:    */       
/*  76:    */ 
/*  77:204 */       formatter = makeFormatterListener(context, elem, fileName, true, mkdirs, append);
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81:208 */       inTable = true;
/*  82:209 */       formatter = (ContentHandler)flObject;
/*  83:    */     }
/*  84:212 */     TransformerImpl transf = context.getTransformer();
/*  85:    */     
/*  86:214 */     startRedirection(transf, formatter);
/*  87:    */     
/*  88:216 */     transf.executeChildTemplates(elem, context.getContextNode(), context.getMode(), formatter);
/*  89:    */     
/*  90:    */ 
/*  91:    */ 
/*  92:220 */     endRedirection(transf);
/*  93:222 */     if (!inTable)
/*  94:    */     {
/*  95:224 */       OutputStream ostream = (OutputStream)this.m_outputStreams.get(fileName);
/*  96:225 */       if (null != ostream)
/*  97:    */       {
/*  98:    */         try
/*  99:    */         {
/* 100:229 */           formatter.endDocument();
/* 101:    */         }
/* 102:    */         catch (SAXException se)
/* 103:    */         {
/* 104:233 */           throw new TransformerException(se);
/* 105:    */         }
/* 106:235 */         ostream.close();
/* 107:236 */         this.m_outputStreams.remove(fileName);
/* 108:237 */         this.m_formatterListeners.remove(fileName);
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void close(XSLProcessorContext context, ElemExtensionCall elem)
/* 114:    */     throws MalformedURLException, FileNotFoundException, IOException, TransformerException
/* 115:    */   {
/* 116:252 */     String fileName = getFilename(context, elem);
/* 117:253 */     Object formatterObj = this.m_formatterListeners.get(fileName);
/* 118:254 */     if (null != formatterObj)
/* 119:    */     {
/* 120:256 */       ContentHandler fl = (ContentHandler)formatterObj;
/* 121:    */       try
/* 122:    */       {
/* 123:259 */         fl.endDocument();
/* 124:    */       }
/* 125:    */       catch (SAXException se)
/* 126:    */       {
/* 127:263 */         throw new TransformerException(se);
/* 128:    */       }
/* 129:265 */       OutputStream ostream = (OutputStream)this.m_outputStreams.get(fileName);
/* 130:266 */       if (null != ostream)
/* 131:    */       {
/* 132:268 */         ostream.close();
/* 133:269 */         this.m_outputStreams.remove(fileName);
/* 134:    */       }
/* 135:271 */       this.m_formatterListeners.remove(fileName);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   private String getFilename(XSLProcessorContext context, ElemExtensionCall elem)
/* 140:    */     throws MalformedURLException, FileNotFoundException, IOException, TransformerException
/* 141:    */   {
/* 142:285 */     String fileNameExpr = elem.getAttribute("select", context.getContextNode(), context.getTransformer());
/* 143:    */     String fileName;
/* 144:289 */     if (null != fileNameExpr)
/* 145:    */     {
/* 146:291 */       XPathContext xctxt = context.getTransformer().getXPathContext();
/* 147:    */       
/* 148:293 */       XPath myxpath = new XPath(fileNameExpr, elem, xctxt.getNamespaceContext(), 0);
/* 149:294 */       XObject xobj = myxpath.execute(xctxt, context.getContextNode(), elem);
/* 150:295 */       fileName = xobj.str();
/* 151:296 */       if ((null == fileName) || (fileName.length() == 0)) {
/* 152:298 */         fileName = elem.getAttribute("file", context.getContextNode(), context.getTransformer());
/* 153:    */       }
/* 154:    */     }
/* 155:    */     else
/* 156:    */     {
/* 157:305 */       fileName = elem.getAttribute("file", context.getContextNode(), context.getTransformer());
/* 158:    */     }
/* 159:308 */     if (null == fileName) {
/* 160:310 */       context.getTransformer().getMsgMgr().error(elem, elem, context.getContextNode(), "ER_REDIRECT_COULDNT_GET_FILENAME");
/* 161:    */     }
/* 162:315 */     return fileName;
/* 163:    */   }
/* 164:    */   
/* 165:    */   private String urlToFileName(String base)
/* 166:    */   {
/* 167:323 */     if (null != base) {
/* 168:325 */       if (base.startsWith("file:////")) {
/* 169:327 */         base = base.substring(7);
/* 170:329 */       } else if (base.startsWith("file:///")) {
/* 171:331 */         base = base.substring(6);
/* 172:333 */       } else if (base.startsWith("file://")) {
/* 173:335 */         base = base.substring(5);
/* 174:337 */       } else if (base.startsWith("file:/")) {
/* 175:339 */         base = base.substring(5);
/* 176:341 */       } else if (base.startsWith("file:")) {
/* 177:343 */         base = base.substring(4);
/* 178:    */       }
/* 179:    */     }
/* 180:346 */     return base;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private ContentHandler makeFormatterListener(XSLProcessorContext context, ElemExtensionCall elem, String fileName, boolean shouldPutInTable, boolean mkdirs, boolean append)
/* 184:    */     throws MalformedURLException, FileNotFoundException, IOException, TransformerException
/* 185:    */   {
/* 186:363 */     File file = new File(fileName);
/* 187:364 */     TransformerImpl transformer = context.getTransformer();
/* 188:367 */     if (!file.isAbsolute())
/* 189:    */     {
/* 190:376 */       Result outputTarget = transformer.getOutputTarget();
/* 191:    */       String base;
/* 192:377 */       if ((null != outputTarget) && ((base = outputTarget.getSystemId()) != null)) {
/* 193:378 */         base = urlToFileName(base);
/* 194:    */       } else {
/* 195:382 */         base = urlToFileName(transformer.getBaseURLOfSource());
/* 196:    */       }
/* 197:385 */       if (null != base)
/* 198:    */       {
/* 199:387 */         File baseFile = new File(base);
/* 200:388 */         file = new File(baseFile.getParent(), fileName);
/* 201:    */       }
/* 202:    */     }
/* 203:393 */     if (mkdirs)
/* 204:    */     {
/* 205:395 */       String dirStr = file.getParent();
/* 206:396 */       if ((null != dirStr) && (dirStr.length() > 0))
/* 207:    */       {
/* 208:398 */         File dir = new File(dirStr);
/* 209:399 */         dir.mkdirs();
/* 210:    */       }
/* 211:    */     }
/* 212:405 */     OutputProperties format = transformer.getOutputFormat();
/* 213:    */     
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:413 */     FileOutputStream ostream = new FileOutputStream(file.getPath(), append);
/* 221:    */     try
/* 222:    */     {
/* 223:417 */       SerializationHandler flistener = createSerializationHandler(transformer, ostream, file, format);
/* 224:    */       try
/* 225:    */       {
/* 226:422 */         flistener.startDocument();
/* 227:    */       }
/* 228:    */       catch (SAXException se)
/* 229:    */       {
/* 230:426 */         throw new TransformerException(se);
/* 231:    */       }
/* 232:428 */       if (shouldPutInTable)
/* 233:    */       {
/* 234:430 */         this.m_outputStreams.put(fileName, ostream);
/* 235:431 */         this.m_formatterListeners.put(fileName, flistener);
/* 236:    */       }
/* 237:433 */       return flistener;
/* 238:    */     }
/* 239:    */     catch (TransformerException te)
/* 240:    */     {
/* 241:437 */       throw new TransformerException(te);
/* 242:    */     }
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void startRedirection(TransformerImpl transf, ContentHandler formatter) {}
/* 246:    */   
/* 247:    */   public void endRedirection(TransformerImpl transf) {}
/* 248:    */   
/* 249:    */   public SerializationHandler createSerializationHandler(TransformerImpl transformer, FileOutputStream ostream, File file, OutputProperties format)
/* 250:    */     throws IOException, TransformerException
/* 251:    */   {
/* 252:484 */     SerializationHandler serializer = transformer.createSerializationHandler(new StreamResult(ostream), format);
/* 253:    */     
/* 254:    */ 
/* 255:    */ 
/* 256:488 */     return serializer;
/* 257:    */   }
/* 258:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.Redirect
 * JD-Core Version:    0.7.0.1
 */
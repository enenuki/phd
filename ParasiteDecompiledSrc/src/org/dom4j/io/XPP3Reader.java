/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.CharArrayReader;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileReader;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.io.InputStreamReader;
/*  10:    */ import java.io.Reader;
/*  11:    */ import java.net.URL;
/*  12:    */ import org.dom4j.Document;
/*  13:    */ import org.dom4j.DocumentException;
/*  14:    */ import org.dom4j.DocumentFactory;
/*  15:    */ import org.dom4j.Element;
/*  16:    */ import org.dom4j.ElementHandler;
/*  17:    */ import org.dom4j.QName;
/*  18:    */ import org.xmlpull.v1.XmlPullParser;
/*  19:    */ import org.xmlpull.v1.XmlPullParserException;
/*  20:    */ import org.xmlpull.v1.XmlPullParserFactory;
/*  21:    */ 
/*  22:    */ public class XPP3Reader
/*  23:    */ {
/*  24:    */   private DocumentFactory factory;
/*  25:    */   private XmlPullParser xppParser;
/*  26:    */   private XmlPullParserFactory xppFactory;
/*  27:    */   private DispatchHandler dispatchHandler;
/*  28:    */   
/*  29:    */   public XPP3Reader() {}
/*  30:    */   
/*  31:    */   public XPP3Reader(DocumentFactory factory)
/*  32:    */   {
/*  33: 59 */     this.factory = factory;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Document read(File file)
/*  37:    */     throws DocumentException, IOException, XmlPullParserException
/*  38:    */   {
/*  39: 81 */     String systemID = file.getAbsolutePath();
/*  40:    */     
/*  41: 83 */     return read(new BufferedReader(new FileReader(file)), systemID);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Document read(URL url)
/*  45:    */     throws DocumentException, IOException, XmlPullParserException
/*  46:    */   {
/*  47:105 */     String systemID = url.toExternalForm();
/*  48:    */     
/*  49:107 */     return read(createReader(url.openStream()), systemID);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Document read(String systemID)
/*  53:    */     throws DocumentException, IOException, XmlPullParserException
/*  54:    */   {
/*  55:137 */     if (systemID.indexOf(':') >= 0) {
/*  56:139 */       return read(new URL(systemID));
/*  57:    */     }
/*  58:142 */     return read(new File(systemID));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Document read(InputStream in)
/*  62:    */     throws DocumentException, IOException, XmlPullParserException
/*  63:    */   {
/*  64:165 */     return read(createReader(in));
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Document read(Reader reader)
/*  68:    */     throws DocumentException, IOException, XmlPullParserException
/*  69:    */   {
/*  70:187 */     getXPPParser().setInput(reader);
/*  71:    */     
/*  72:189 */     return parseDocument();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Document read(char[] text)
/*  76:    */     throws DocumentException, IOException, XmlPullParserException
/*  77:    */   {
/*  78:211 */     getXPPParser().setInput(new CharArrayReader(text));
/*  79:    */     
/*  80:213 */     return parseDocument();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Document read(InputStream in, String systemID)
/*  84:    */     throws DocumentException, IOException, XmlPullParserException
/*  85:    */   {
/*  86:237 */     return read(createReader(in), systemID);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Document read(Reader reader, String systemID)
/*  90:    */     throws DocumentException, IOException, XmlPullParserException
/*  91:    */   {
/*  92:261 */     Document document = read(reader);
/*  93:262 */     document.setName(systemID);
/*  94:    */     
/*  95:264 */     return document;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public XmlPullParser getXPPParser()
/*  99:    */     throws XmlPullParserException
/* 100:    */   {
/* 101:270 */     if (this.xppParser == null) {
/* 102:271 */       this.xppParser = getXPPFactory().newPullParser();
/* 103:    */     }
/* 104:274 */     return this.xppParser;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public XmlPullParserFactory getXPPFactory()
/* 108:    */     throws XmlPullParserException
/* 109:    */   {
/* 110:278 */     if (this.xppFactory == null) {
/* 111:279 */       this.xppFactory = XmlPullParserFactory.newInstance();
/* 112:    */     }
/* 113:282 */     this.xppFactory.setNamespaceAware(true);
/* 114:    */     
/* 115:284 */     return this.xppFactory;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setXPPFactory(XmlPullParserFactory xPPfactory)
/* 119:    */   {
/* 120:288 */     this.xppFactory = xPPfactory;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public DocumentFactory getDocumentFactory()
/* 124:    */   {
/* 125:298 */     if (this.factory == null) {
/* 126:299 */       this.factory = DocumentFactory.getInstance();
/* 127:    */     }
/* 128:302 */     return this.factory;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setDocumentFactory(DocumentFactory documentFactory)
/* 132:    */   {
/* 133:317 */     this.factory = documentFactory;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void addHandler(String path, ElementHandler handler)
/* 137:    */   {
/* 138:331 */     getDispatchHandler().addHandler(path, handler);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void removeHandler(String path)
/* 142:    */   {
/* 143:342 */     getDispatchHandler().removeHandler(path);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setDefaultHandler(ElementHandler handler)
/* 147:    */   {
/* 148:355 */     getDispatchHandler().setDefaultHandler(handler);
/* 149:    */   }
/* 150:    */   
/* 151:    */   protected Document parseDocument()
/* 152:    */     throws DocumentException, IOException, XmlPullParserException
/* 153:    */   {
/* 154:362 */     DocumentFactory df = getDocumentFactory();
/* 155:363 */     Document document = df.createDocument();
/* 156:364 */     Element parent = null;
/* 157:365 */     XmlPullParser pp = getXPPParser();
/* 158:366 */     pp.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
/* 159:    */     for (;;)
/* 160:    */     {
/* 161:369 */       int type = pp.nextToken();
/* 162:371 */       switch (type)
/* 163:    */       {
/* 164:    */       case 8: 
/* 165:373 */         String text = pp.getText();
/* 166:374 */         int loc = text.indexOf(" ");
/* 167:376 */         if (loc >= 0)
/* 168:    */         {
/* 169:377 */           String target = text.substring(0, loc);
/* 170:378 */           String txt = text.substring(loc + 1);
/* 171:379 */           document.addProcessingInstruction(target, txt);
/* 172:    */         }
/* 173:    */         else
/* 174:    */         {
/* 175:381 */           document.addProcessingInstruction(text, "");
/* 176:    */         }
/* 177:384 */         break;
/* 178:    */       case 9: 
/* 179:388 */         if (parent != null) {
/* 180:389 */           parent.addComment(pp.getText());
/* 181:    */         } else {
/* 182:391 */           document.addComment(pp.getText());
/* 183:    */         }
/* 184:394 */         break;
/* 185:    */       case 5: 
/* 186:398 */         if (parent != null)
/* 187:    */         {
/* 188:399 */           parent.addCDATA(pp.getText());
/* 189:    */         }
/* 190:    */         else
/* 191:    */         {
/* 192:401 */           String msg = "Cannot have text content outside of the root document";
/* 193:    */           
/* 194:403 */           throw new DocumentException(msg);
/* 195:    */         }
/* 196:    */         break;
/* 197:    */       case 6: 
/* 198:    */         break;
/* 199:    */       case 1: 
/* 200:413 */         return document;
/* 201:    */       case 2: 
/* 202:416 */         QName qname = pp.getPrefix() == null ? df.createQName(pp.getName(), pp.getNamespace()) : df.createQName(pp.getName(), pp.getPrefix(), pp.getNamespace());
/* 203:    */         
/* 204:    */ 
/* 205:419 */         Element newElement = df.createElement(qname);
/* 206:420 */         int nsStart = pp.getNamespaceCount(pp.getDepth() - 1);
/* 207:421 */         int nsEnd = pp.getNamespaceCount(pp.getDepth());
/* 208:423 */         for (int i = nsStart; i < nsEnd; i++) {
/* 209:424 */           if (pp.getNamespacePrefix(i) != null) {
/* 210:425 */             newElement.addNamespace(pp.getNamespacePrefix(i), pp.getNamespaceUri(i));
/* 211:    */           }
/* 212:    */         }
/* 213:430 */         for (int i = 0; i < pp.getAttributeCount(); i++)
/* 214:    */         {
/* 215:431 */           QName qa = pp.getAttributePrefix(i) == null ? df.createQName(pp.getAttributeName(i)) : df.createQName(pp.getAttributeName(i), pp.getAttributePrefix(i), pp.getAttributeNamespace(i));
/* 216:    */           
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:436 */           newElement.addAttribute(qa, pp.getAttributeValue(i));
/* 221:    */         }
/* 222:439 */         if (parent != null) {
/* 223:440 */           parent.add(newElement);
/* 224:    */         } else {
/* 225:442 */           document.add(newElement);
/* 226:    */         }
/* 227:445 */         parent = newElement;
/* 228:    */         
/* 229:447 */         break;
/* 230:    */       case 3: 
/* 231:451 */         if (parent != null) {
/* 232:452 */           parent = parent.getParent();
/* 233:    */         }
/* 234:    */         break;
/* 235:    */       case 4: 
/* 236:459 */         String text = pp.getText();
/* 237:461 */         if (parent != null)
/* 238:    */         {
/* 239:462 */           parent.addText(text);
/* 240:    */         }
/* 241:    */         else
/* 242:    */         {
/* 243:464 */           String msg = "Cannot have text content outside of the root document";
/* 244:    */           
/* 245:466 */           throw new DocumentException(msg);
/* 246:    */         }
/* 247:    */         break;
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   protected DispatchHandler getDispatchHandler()
/* 253:    */   {
/* 254:479 */     if (this.dispatchHandler == null) {
/* 255:480 */       this.dispatchHandler = new DispatchHandler();
/* 256:    */     }
/* 257:483 */     return this.dispatchHandler;
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected void setDispatchHandler(DispatchHandler dispatchHandler)
/* 261:    */   {
/* 262:487 */     this.dispatchHandler = dispatchHandler;
/* 263:    */   }
/* 264:    */   
/* 265:    */   protected Reader createReader(InputStream in)
/* 266:    */     throws IOException
/* 267:    */   {
/* 268:502 */     return new BufferedReader(new InputStreamReader(in));
/* 269:    */   }
/* 270:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.XPP3Reader
 * JD-Core Version:    0.7.0.1
 */
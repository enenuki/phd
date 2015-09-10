/*   1:    */ package org.dom4j.jaxb;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.InputStreamReader;
/*   8:    */ import java.io.Reader;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.nio.charset.Charset;
/*  11:    */ import org.dom4j.Document;
/*  12:    */ import org.dom4j.DocumentException;
/*  13:    */ import org.dom4j.ElementHandler;
/*  14:    */ import org.dom4j.ElementPath;
/*  15:    */ import org.dom4j.io.SAXReader;
/*  16:    */ import org.xml.sax.InputSource;
/*  17:    */ 
/*  18:    */ public class JAXBReader
/*  19:    */   extends JAXBSupport
/*  20:    */ {
/*  21:    */   private SAXReader reader;
/*  22:    */   private boolean pruneElements;
/*  23:    */   
/*  24:    */   public JAXBReader(String contextPath)
/*  25:    */   {
/*  26: 55 */     super(contextPath);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public JAXBReader(String contextPath, ClassLoader classloader)
/*  30:    */   {
/*  31: 72 */     super(contextPath, classloader);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Document read(File source)
/*  35:    */     throws DocumentException
/*  36:    */   {
/*  37: 87 */     return getReader().read(source);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Document read(File file, Charset charset)
/*  41:    */     throws DocumentException
/*  42:    */   {
/*  43:    */     try
/*  44:    */     {
/*  45:106 */       Reader xmlReader = new InputStreamReader(new FileInputStream(file), charset);
/*  46:    */       
/*  47:    */ 
/*  48:109 */       return getReader().read(xmlReader);
/*  49:    */     }
/*  50:    */     catch (JAXBRuntimeException ex)
/*  51:    */     {
/*  52:111 */       Throwable cause = ex.getCause();
/*  53:112 */       throw new DocumentException(cause.getMessage(), cause);
/*  54:    */     }
/*  55:    */     catch (FileNotFoundException ex)
/*  56:    */     {
/*  57:114 */       throw new DocumentException(ex.getMessage(), ex);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Document read(InputSource source)
/*  62:    */     throws DocumentException
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66:131 */       return getReader().read(source);
/*  67:    */     }
/*  68:    */     catch (JAXBRuntimeException ex)
/*  69:    */     {
/*  70:133 */       Throwable cause = ex.getCause();
/*  71:134 */       throw new DocumentException(cause.getMessage(), cause);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Document read(InputStream source)
/*  76:    */     throws DocumentException
/*  77:    */   {
/*  78:    */     try
/*  79:    */     {
/*  80:151 */       return getReader().read(source);
/*  81:    */     }
/*  82:    */     catch (JAXBRuntimeException ex)
/*  83:    */     {
/*  84:153 */       Throwable cause = ex.getCause();
/*  85:154 */       throw new DocumentException(cause.getMessage(), cause);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Document read(InputStream source, String systemId)
/*  90:    */     throws DocumentException
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:174 */       return getReader().read(source);
/*  95:    */     }
/*  96:    */     catch (JAXBRuntimeException ex)
/*  97:    */     {
/*  98:176 */       Throwable cause = ex.getCause();
/*  99:177 */       throw new DocumentException(cause.getMessage(), cause);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Document read(Reader source)
/* 104:    */     throws DocumentException
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:194 */       return getReader().read(source);
/* 109:    */     }
/* 110:    */     catch (JAXBRuntimeException ex)
/* 111:    */     {
/* 112:196 */       Throwable cause = ex.getCause();
/* 113:197 */       throw new DocumentException(cause.getMessage(), cause);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Document read(Reader source, String systemId)
/* 118:    */     throws DocumentException
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:217 */       return getReader().read(source);
/* 123:    */     }
/* 124:    */     catch (JAXBRuntimeException ex)
/* 125:    */     {
/* 126:219 */       Throwable cause = ex.getCause();
/* 127:220 */       throw new DocumentException(cause.getMessage(), cause);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Document read(String source)
/* 132:    */     throws DocumentException
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:237 */       return getReader().read(source);
/* 137:    */     }
/* 138:    */     catch (JAXBRuntimeException ex)
/* 139:    */     {
/* 140:239 */       Throwable cause = ex.getCause();
/* 141:240 */       throw new DocumentException(cause.getMessage(), cause);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Document read(URL source)
/* 146:    */     throws DocumentException
/* 147:    */   {
/* 148:    */     try
/* 149:    */     {
/* 150:257 */       return getReader().read(source);
/* 151:    */     }
/* 152:    */     catch (JAXBRuntimeException ex)
/* 153:    */     {
/* 154:259 */       Throwable cause = ex.getCause();
/* 155:260 */       throw new DocumentException(cause.getMessage(), cause);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void addObjectHandler(String path, JAXBObjectHandler handler)
/* 160:    */   {
/* 161:275 */     ElementHandler eHandler = new UnmarshalElementHandler(this, handler);
/* 162:276 */     getReader().addHandler(path, eHandler);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void removeObjectHandler(String path)
/* 166:    */   {
/* 167:287 */     getReader().removeHandler(path);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void addHandler(String path, ElementHandler handler)
/* 171:    */   {
/* 172:301 */     getReader().addHandler(path, handler);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void removeHandler(String path)
/* 176:    */   {
/* 177:312 */     getReader().removeHandler(path);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void resetHandlers()
/* 181:    */   {
/* 182:320 */     getReader().resetHandlers();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isPruneElements()
/* 186:    */   {
/* 187:329 */     return this.pruneElements;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setPruneElements(boolean pruneElements)
/* 191:    */   {
/* 192:339 */     this.pruneElements = pruneElements;
/* 193:341 */     if (pruneElements) {
/* 194:342 */       getReader().setDefaultHandler(new PruningElementHandler());
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   private SAXReader getReader()
/* 199:    */   {
/* 200:347 */     if (this.reader == null) {
/* 201:348 */       this.reader = new SAXReader();
/* 202:    */     }
/* 203:351 */     return this.reader;
/* 204:    */   }
/* 205:    */   
/* 206:    */   private class UnmarshalElementHandler
/* 207:    */     implements ElementHandler
/* 208:    */   {
/* 209:    */     private JAXBReader jaxbReader;
/* 210:    */     private JAXBObjectHandler handler;
/* 211:    */     
/* 212:    */     public UnmarshalElementHandler(JAXBReader documentReader, JAXBObjectHandler handler)
/* 213:    */     {
/* 214:361 */       this.jaxbReader = documentReader;
/* 215:362 */       this.handler = handler;
/* 216:    */     }
/* 217:    */     
/* 218:    */     public void onStart(ElementPath elementPath) {}
/* 219:    */     
/* 220:    */     public void onEnd(ElementPath elementPath)
/* 221:    */     {
/* 222:    */       try
/* 223:    */       {
/* 224:370 */         org.dom4j.Element elem = elementPath.getCurrent();
/* 225:    */         
/* 226:372 */         javax.xml.bind.Element jaxbObject = this.jaxbReader.unmarshal(elem);
/* 227:375 */         if (this.jaxbReader.isPruneElements()) {
/* 228:376 */           elem.detach();
/* 229:    */         }
/* 230:379 */         this.handler.handleObject(jaxbObject);
/* 231:    */       }
/* 232:    */       catch (Exception ex)
/* 233:    */       {
/* 234:381 */         throw new JAXBRuntimeException(ex);
/* 235:    */       }
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   private class PruningElementHandler
/* 240:    */     implements ElementHandler
/* 241:    */   {
/* 242:    */     public PruningElementHandler() {}
/* 243:    */     
/* 244:    */     public void onStart(ElementPath parm1) {}
/* 245:    */     
/* 246:    */     public void onEnd(ElementPath elementPath)
/* 247:    */     {
/* 248:394 */       org.dom4j.Element elem = elementPath.getCurrent();
/* 249:395 */       elem.detach();
/* 250:396 */       elem = null;
/* 251:    */     }
/* 252:    */   }
/* 253:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.jaxb.JAXBReader
 * JD-Core Version:    0.7.0.1
 */
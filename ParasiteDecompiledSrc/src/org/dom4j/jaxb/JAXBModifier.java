/*   1:    */ package org.dom4j.jaxb;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.io.InputStreamReader;
/*  10:    */ import java.io.OutputStream;
/*  11:    */ import java.io.Reader;
/*  12:    */ import java.io.Writer;
/*  13:    */ import java.net.URL;
/*  14:    */ import java.nio.charset.Charset;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.Map.Entry;
/*  18:    */ import java.util.Set;
/*  19:    */ import org.dom4j.Document;
/*  20:    */ import org.dom4j.DocumentException;
/*  21:    */ import org.dom4j.io.ElementModifier;
/*  22:    */ import org.dom4j.io.OutputFormat;
/*  23:    */ import org.dom4j.io.SAXModifier;
/*  24:    */ import org.dom4j.io.XMLWriter;
/*  25:    */ import org.xml.sax.InputSource;
/*  26:    */ 
/*  27:    */ public class JAXBModifier
/*  28:    */   extends JAXBSupport
/*  29:    */ {
/*  30:    */   private SAXModifier modifier;
/*  31:    */   private XMLWriter xmlWriter;
/*  32:    */   private boolean pruneElements;
/*  33:    */   private OutputFormat outputFormat;
/*  34: 54 */   private HashMap modifiers = new HashMap();
/*  35:    */   
/*  36:    */   public JAXBModifier(String contextPath)
/*  37:    */   {
/*  38: 67 */     super(contextPath);
/*  39: 68 */     this.outputFormat = new OutputFormat();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public JAXBModifier(String contextPath, ClassLoader classloader)
/*  43:    */   {
/*  44: 85 */     super(contextPath, classloader);
/*  45: 86 */     this.outputFormat = new OutputFormat();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public JAXBModifier(String contextPath, OutputFormat outputFormat)
/*  49:    */   {
/*  50:102 */     super(contextPath);
/*  51:103 */     this.outputFormat = outputFormat;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public JAXBModifier(String contextPath, ClassLoader classloader, OutputFormat outputFormat)
/*  55:    */   {
/*  56:122 */     super(contextPath, classloader);
/*  57:123 */     this.outputFormat = outputFormat;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Document modify(File source)
/*  61:    */     throws DocumentException, IOException
/*  62:    */   {
/*  63:141 */     return installModifier().modify(source);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Document modify(File source, Charset charset)
/*  67:    */     throws DocumentException, IOException
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:164 */       Reader reader = new InputStreamReader(new FileInputStream(source), charset);
/*  72:    */       
/*  73:    */ 
/*  74:167 */       return installModifier().modify(reader);
/*  75:    */     }
/*  76:    */     catch (JAXBRuntimeException ex)
/*  77:    */     {
/*  78:169 */       Throwable cause = ex.getCause();
/*  79:170 */       throw new DocumentException(cause.getMessage(), cause);
/*  80:    */     }
/*  81:    */     catch (FileNotFoundException ex)
/*  82:    */     {
/*  83:172 */       throw new DocumentException(ex.getMessage(), ex);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Document modify(InputSource source)
/*  88:    */     throws DocumentException, IOException
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:193 */       return installModifier().modify(source);
/*  93:    */     }
/*  94:    */     catch (JAXBRuntimeException ex)
/*  95:    */     {
/*  96:195 */       Throwable cause = ex.getCause();
/*  97:196 */       throw new DocumentException(cause.getMessage(), cause);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Document modify(InputStream source)
/* 102:    */     throws DocumentException, IOException
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:217 */       return installModifier().modify(source);
/* 107:    */     }
/* 108:    */     catch (JAXBRuntimeException ex)
/* 109:    */     {
/* 110:219 */       Throwable cause = ex.getCause();
/* 111:220 */       throw new DocumentException(cause.getMessage(), cause);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Document modify(InputStream source, String systemId)
/* 116:    */     throws DocumentException, IOException
/* 117:    */   {
/* 118:    */     try
/* 119:    */     {
/* 120:243 */       return installModifier().modify(source);
/* 121:    */     }
/* 122:    */     catch (JAXBRuntimeException ex)
/* 123:    */     {
/* 124:245 */       Throwable cause = ex.getCause();
/* 125:246 */       throw new DocumentException(cause.getMessage(), cause);
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Document modify(Reader r)
/* 130:    */     throws DocumentException, IOException
/* 131:    */   {
/* 132:    */     try
/* 133:    */     {
/* 134:266 */       return installModifier().modify(r);
/* 135:    */     }
/* 136:    */     catch (JAXBRuntimeException ex)
/* 137:    */     {
/* 138:268 */       Throwable cause = ex.getCause();
/* 139:269 */       throw new DocumentException(cause.getMessage(), cause);
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Document modify(Reader source, String systemId)
/* 144:    */     throws DocumentException, IOException
/* 145:    */   {
/* 146:    */     try
/* 147:    */     {
/* 148:292 */       return installModifier().modify(source);
/* 149:    */     }
/* 150:    */     catch (JAXBRuntimeException ex)
/* 151:    */     {
/* 152:294 */       Throwable cause = ex.getCause();
/* 153:295 */       throw new DocumentException(cause.getMessage(), cause);
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Document modify(String url)
/* 158:    */     throws DocumentException, IOException
/* 159:    */   {
/* 160:    */     try
/* 161:    */     {
/* 162:315 */       return installModifier().modify(url);
/* 163:    */     }
/* 164:    */     catch (JAXBRuntimeException ex)
/* 165:    */     {
/* 166:317 */       Throwable cause = ex.getCause();
/* 167:318 */       throw new DocumentException(cause.getMessage(), cause);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Document modify(URL source)
/* 172:    */     throws DocumentException, IOException
/* 173:    */   {
/* 174:    */     try
/* 175:    */     {
/* 176:338 */       return installModifier().modify(source);
/* 177:    */     }
/* 178:    */     catch (JAXBRuntimeException ex)
/* 179:    */     {
/* 180:340 */       Throwable cause = ex.getCause();
/* 181:341 */       throw new DocumentException(cause.getMessage(), cause);
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setOutput(File file)
/* 186:    */     throws IOException
/* 187:    */   {
/* 188:355 */     createXMLWriter().setOutputStream(new FileOutputStream(file));
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setOutput(OutputStream outputStream)
/* 192:    */     throws IOException
/* 193:    */   {
/* 194:368 */     createXMLWriter().setOutputStream(outputStream);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setOutput(Writer writer)
/* 198:    */     throws IOException
/* 199:    */   {
/* 200:381 */     createXMLWriter().setWriter(writer);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void addObjectModifier(String path, JAXBObjectModifier mod)
/* 204:    */   {
/* 205:394 */     this.modifiers.put(path, mod);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void removeObjectModifier(String path)
/* 209:    */   {
/* 210:405 */     this.modifiers.remove(path);
/* 211:406 */     getModifier().removeModifier(path);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void resetObjectModifiers()
/* 215:    */   {
/* 216:414 */     this.modifiers.clear();
/* 217:415 */     getModifier().resetModifiers();
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean isPruneElements()
/* 221:    */   {
/* 222:425 */     return this.pruneElements;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setPruneElements(boolean pruneElements)
/* 226:    */   {
/* 227:436 */     this.pruneElements = pruneElements;
/* 228:    */   }
/* 229:    */   
/* 230:    */   private SAXModifier installModifier()
/* 231:    */     throws IOException
/* 232:    */   {
/* 233:440 */     this.modifier = new SAXModifier(isPruneElements());
/* 234:    */     
/* 235:442 */     this.modifier.resetModifiers();
/* 236:    */     
/* 237:444 */     Iterator modifierIt = this.modifiers.entrySet().iterator();
/* 238:446 */     while (modifierIt.hasNext())
/* 239:    */     {
/* 240:447 */       Map.Entry entry = (Map.Entry)modifierIt.next();
/* 241:448 */       ElementModifier mod = new JAXBElementModifier(this, (JAXBObjectModifier)entry.getValue());
/* 242:    */       
/* 243:450 */       getModifier().addModifier((String)entry.getKey(), mod);
/* 244:    */     }
/* 245:453 */     this.modifier.setXMLWriter(getXMLWriter());
/* 246:    */     
/* 247:455 */     return this.modifier;
/* 248:    */   }
/* 249:    */   
/* 250:    */   private SAXModifier getModifier()
/* 251:    */   {
/* 252:459 */     if (this.modifier == null) {
/* 253:460 */       this.modifier = new SAXModifier(isPruneElements());
/* 254:    */     }
/* 255:463 */     return this.modifier;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private XMLWriter getXMLWriter()
/* 259:    */   {
/* 260:467 */     return this.xmlWriter;
/* 261:    */   }
/* 262:    */   
/* 263:    */   private XMLWriter createXMLWriter()
/* 264:    */     throws IOException
/* 265:    */   {
/* 266:471 */     if (this.xmlWriter == null) {
/* 267:472 */       this.xmlWriter = new XMLWriter(this.outputFormat);
/* 268:    */     }
/* 269:475 */     return this.xmlWriter;
/* 270:    */   }
/* 271:    */   
/* 272:    */   private class JAXBElementModifier
/* 273:    */     implements ElementModifier
/* 274:    */   {
/* 275:    */     private JAXBModifier jaxbModifier;
/* 276:    */     private JAXBObjectModifier objectModifier;
/* 277:    */     
/* 278:    */     public JAXBElementModifier(JAXBModifier jaxbModifier, JAXBObjectModifier objectModifier)
/* 279:    */     {
/* 280:485 */       this.jaxbModifier = jaxbModifier;
/* 281:486 */       this.objectModifier = objectModifier;
/* 282:    */     }
/* 283:    */     
/* 284:    */     public org.dom4j.Element modifyElement(org.dom4j.Element element)
/* 285:    */       throws Exception
/* 286:    */     {
/* 287:491 */       javax.xml.bind.Element originalObject = this.jaxbModifier.unmarshal(element);
/* 288:    */       
/* 289:493 */       javax.xml.bind.Element modifiedObject = this.objectModifier.modifyObject(originalObject);
/* 290:    */       
/* 291:    */ 
/* 292:496 */       return this.jaxbModifier.marshal(modifiedObject);
/* 293:    */     }
/* 294:    */   }
/* 295:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.jaxb.JAXBModifier
 * JD-Core Version:    0.7.0.1
 */
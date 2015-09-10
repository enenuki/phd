/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.Reader;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.dom4j.Document;
/*  12:    */ import org.dom4j.DocumentException;
/*  13:    */ import org.dom4j.DocumentFactory;
/*  14:    */ import org.xml.sax.InputSource;
/*  15:    */ import org.xml.sax.SAXException;
/*  16:    */ import org.xml.sax.XMLReader;
/*  17:    */ 
/*  18:    */ public class SAXModifier
/*  19:    */ {
/*  20:    */   private XMLWriter xmlWriter;
/*  21:    */   private XMLReader xmlReader;
/*  22:    */   private boolean pruneElements;
/*  23:    */   private SAXModifyReader modifyReader;
/*  24: 53 */   private HashMap modifiers = new HashMap();
/*  25:    */   
/*  26:    */   public SAXModifier() {}
/*  27:    */   
/*  28:    */   public SAXModifier(boolean pruneElements)
/*  29:    */   {
/*  30: 75 */     this.pruneElements = pruneElements;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public SAXModifier(XMLReader xmlReader)
/*  34:    */   {
/*  35: 86 */     this.xmlReader = xmlReader;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public SAXModifier(XMLReader xmlReader, boolean pruneElements)
/*  39:    */   {
/*  40:100 */     this.xmlReader = xmlReader;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Document modify(File source)
/*  44:    */     throws DocumentException
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48:119 */       return installModifyReader().read(source);
/*  49:    */     }
/*  50:    */     catch (SAXModifyException ex)
/*  51:    */     {
/*  52:121 */       Throwable cause = ex.getCause();
/*  53:122 */       throw new DocumentException(cause.getMessage(), cause);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Document modify(InputSource source)
/*  58:    */     throws DocumentException
/*  59:    */   {
/*  60:    */     try
/*  61:    */     {
/*  62:142 */       return installModifyReader().read(source);
/*  63:    */     }
/*  64:    */     catch (SAXModifyException ex)
/*  65:    */     {
/*  66:144 */       Throwable cause = ex.getCause();
/*  67:145 */       throw new DocumentException(cause.getMessage(), cause);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Document modify(InputStream source)
/*  72:    */     throws DocumentException
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:165 */       return installModifyReader().read(source);
/*  77:    */     }
/*  78:    */     catch (SAXModifyException ex)
/*  79:    */     {
/*  80:167 */       Throwable cause = ex.getCause();
/*  81:168 */       throw new DocumentException(cause.getMessage(), cause);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Document modify(InputStream source, String systemId)
/*  86:    */     throws DocumentException
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:191 */       return installModifyReader().read(source);
/*  91:    */     }
/*  92:    */     catch (SAXModifyException ex)
/*  93:    */     {
/*  94:193 */       Throwable cause = ex.getCause();
/*  95:194 */       throw new DocumentException(cause.getMessage(), cause);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Document modify(Reader source)
/* 100:    */     throws DocumentException
/* 101:    */   {
/* 102:    */     try
/* 103:    */     {
/* 104:214 */       return installModifyReader().read(source);
/* 105:    */     }
/* 106:    */     catch (SAXModifyException ex)
/* 107:    */     {
/* 108:216 */       Throwable cause = ex.getCause();
/* 109:217 */       throw new DocumentException(cause.getMessage(), cause);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Document modify(Reader source, String systemId)
/* 114:    */     throws DocumentException
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:240 */       return installModifyReader().read(source);
/* 119:    */     }
/* 120:    */     catch (SAXModifyException ex)
/* 121:    */     {
/* 122:242 */       Throwable cause = ex.getCause();
/* 123:243 */       throw new DocumentException(cause.getMessage(), cause);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Document modify(URL source)
/* 128:    */     throws DocumentException
/* 129:    */   {
/* 130:    */     try
/* 131:    */     {
/* 132:263 */       return installModifyReader().read(source);
/* 133:    */     }
/* 134:    */     catch (SAXModifyException ex)
/* 135:    */     {
/* 136:265 */       Throwable cause = ex.getCause();
/* 137:266 */       throw new DocumentException(cause.getMessage(), cause);
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Document modify(String source)
/* 142:    */     throws DocumentException
/* 143:    */   {
/* 144:    */     try
/* 145:    */     {
/* 146:286 */       return installModifyReader().read(source);
/* 147:    */     }
/* 148:    */     catch (SAXModifyException ex)
/* 149:    */     {
/* 150:288 */       Throwable cause = ex.getCause();
/* 151:289 */       throw new DocumentException(cause.getMessage(), cause);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void addModifier(String path, ElementModifier modifier)
/* 156:    */   {
/* 157:304 */     this.modifiers.put(path, modifier);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void resetModifiers()
/* 161:    */   {
/* 162:312 */     this.modifiers.clear();
/* 163:313 */     getSAXModifyReader().resetHandlers();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void removeModifier(String path)
/* 167:    */   {
/* 168:324 */     this.modifiers.remove(path);
/* 169:325 */     getSAXModifyReader().removeHandler(path);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public DocumentFactory getDocumentFactory()
/* 173:    */   {
/* 174:335 */     return getSAXModifyReader().getDocumentFactory();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setDocumentFactory(DocumentFactory factory)
/* 178:    */   {
/* 179:346 */     getSAXModifyReader().setDocumentFactory(factory);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public XMLWriter getXMLWriter()
/* 183:    */   {
/* 184:355 */     return this.xmlWriter;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setXMLWriter(XMLWriter writer)
/* 188:    */   {
/* 189:365 */     this.xmlWriter = writer;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean isPruneElements()
/* 193:    */   {
/* 194:375 */     return this.pruneElements;
/* 195:    */   }
/* 196:    */   
/* 197:    */   private SAXReader installModifyReader()
/* 198:    */     throws DocumentException
/* 199:    */   {
/* 200:    */     try
/* 201:    */     {
/* 202:380 */       SAXModifyReader reader = getSAXModifyReader();
/* 203:382 */       if (isPruneElements()) {
/* 204:383 */         this.modifyReader.setDispatchHandler(new PruningDispatchHandler());
/* 205:    */       }
/* 206:386 */       reader.resetHandlers();
/* 207:    */       
/* 208:388 */       Iterator modifierIt = this.modifiers.entrySet().iterator();
/* 209:390 */       while (modifierIt.hasNext())
/* 210:    */       {
/* 211:391 */         Map.Entry entry = (Map.Entry)modifierIt.next();
/* 212:    */         
/* 213:393 */         SAXModifyElementHandler handler = new SAXModifyElementHandler((ElementModifier)entry.getValue());
/* 214:    */         
/* 215:395 */         reader.addHandler((String)entry.getKey(), handler);
/* 216:    */       }
/* 217:398 */       reader.setXMLWriter(getXMLWriter());
/* 218:399 */       reader.setXMLReader(getXMLReader());
/* 219:    */       
/* 220:401 */       return reader;
/* 221:    */     }
/* 222:    */     catch (SAXException ex)
/* 223:    */     {
/* 224:403 */       throw new DocumentException(ex.getMessage(), ex);
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   private XMLReader getXMLReader()
/* 229:    */     throws SAXException
/* 230:    */   {
/* 231:408 */     if (this.xmlReader == null) {
/* 232:409 */       this.xmlReader = SAXHelper.createXMLReader(false);
/* 233:    */     }
/* 234:412 */     return this.xmlReader;
/* 235:    */   }
/* 236:    */   
/* 237:    */   private SAXModifyReader getSAXModifyReader()
/* 238:    */   {
/* 239:416 */     if (this.modifyReader == null) {
/* 240:417 */       this.modifyReader = new SAXModifyReader();
/* 241:    */     }
/* 242:420 */     return this.modifyReader;
/* 243:    */   }
/* 244:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXModifier
 * JD-Core Version:    0.7.0.1
 */
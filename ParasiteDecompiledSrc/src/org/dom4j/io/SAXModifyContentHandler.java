/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.dom4j.DocumentFactory;
/*   5:    */ import org.dom4j.Element;
/*   6:    */ import org.dom4j.ElementHandler;
/*   7:    */ import org.xml.sax.Attributes;
/*   8:    */ import org.xml.sax.Locator;
/*   9:    */ import org.xml.sax.SAXException;
/*  10:    */ 
/*  11:    */ class SAXModifyContentHandler
/*  12:    */   extends SAXContentHandler
/*  13:    */ {
/*  14:    */   private XMLWriter xmlWriter;
/*  15:    */   
/*  16:    */   public SAXModifyContentHandler() {}
/*  17:    */   
/*  18:    */   public SAXModifyContentHandler(DocumentFactory documentFactory)
/*  19:    */   {
/*  20: 36 */     super(documentFactory);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public SAXModifyContentHandler(DocumentFactory documentFactory, ElementHandler elementHandler)
/*  24:    */   {
/*  25: 41 */     super(documentFactory, elementHandler);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public SAXModifyContentHandler(DocumentFactory documentFactory, ElementHandler elementHandler, ElementStack elementStack)
/*  29:    */   {
/*  30: 46 */     super(documentFactory, elementHandler, elementStack);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setXMLWriter(XMLWriter writer)
/*  34:    */   {
/*  35: 50 */     this.xmlWriter = writer;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void startCDATA()
/*  39:    */     throws SAXException
/*  40:    */   {
/*  41: 54 */     super.startCDATA();
/*  42: 56 */     if ((!activeHandlers()) && (this.xmlWriter != null)) {
/*  43: 57 */       this.xmlWriter.startCDATA();
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void startDTD(String name, String publicId, String systemId)
/*  48:    */     throws SAXException
/*  49:    */   {
/*  50: 63 */     super.startDTD(name, publicId, systemId);
/*  51: 65 */     if (this.xmlWriter != null) {
/*  52: 66 */       this.xmlWriter.startDTD(name, publicId, systemId);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void endDTD()
/*  57:    */     throws SAXException
/*  58:    */   {
/*  59: 71 */     super.endDTD();
/*  60: 73 */     if (this.xmlWriter != null) {
/*  61: 74 */       this.xmlWriter.endDTD();
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void comment(char[] characters, int parm2, int parm3)
/*  66:    */     throws SAXException
/*  67:    */   {
/*  68: 80 */     super.comment(characters, parm2, parm3);
/*  69: 82 */     if ((!activeHandlers()) && (this.xmlWriter != null)) {
/*  70: 83 */       this.xmlWriter.comment(characters, parm2, parm3);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void startEntity(String name)
/*  75:    */     throws SAXException
/*  76:    */   {
/*  77: 88 */     super.startEntity(name);
/*  78: 90 */     if (this.xmlWriter != null) {
/*  79: 91 */       this.xmlWriter.startEntity(name);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void endCDATA()
/*  84:    */     throws SAXException
/*  85:    */   {
/*  86: 96 */     super.endCDATA();
/*  87: 98 */     if ((!activeHandlers()) && (this.xmlWriter != null)) {
/*  88: 99 */       this.xmlWriter.endCDATA();
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void endEntity(String name)
/*  93:    */     throws SAXException
/*  94:    */   {
/*  95:104 */     super.endEntity(name);
/*  96:106 */     if (this.xmlWriter != null) {
/*  97:107 */       this.xmlWriter.endEntity(name);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notation)
/* 102:    */     throws SAXException
/* 103:    */   {
/* 104:113 */     super.unparsedEntityDecl(name, publicId, systemId, notation);
/* 105:115 */     if ((!activeHandlers()) && (this.xmlWriter != null)) {
/* 106:116 */       this.xmlWriter.unparsedEntityDecl(name, publicId, systemId, notation);
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void notationDecl(String name, String publicId, String systemId)
/* 111:    */     throws SAXException
/* 112:    */   {
/* 113:122 */     super.notationDecl(name, publicId, systemId);
/* 114:124 */     if (this.xmlWriter != null) {
/* 115:125 */       this.xmlWriter.notationDecl(name, publicId, systemId);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void startElement(String uri, String localName, String qName, Attributes atts)
/* 120:    */     throws SAXException
/* 121:    */   {
/* 122:131 */     super.startElement(uri, localName, qName, atts);
/* 123:133 */     if ((!activeHandlers()) && (this.xmlWriter != null)) {
/* 124:134 */       this.xmlWriter.startElement(uri, localName, qName, atts);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void startDocument()
/* 129:    */     throws SAXException
/* 130:    */   {
/* 131:139 */     super.startDocument();
/* 132:141 */     if (this.xmlWriter != null) {
/* 133:142 */       this.xmlWriter.startDocument();
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void ignorableWhitespace(char[] parm1, int parm2, int parm3)
/* 138:    */     throws SAXException
/* 139:    */   {
/* 140:148 */     super.ignorableWhitespace(parm1, parm2, parm3);
/* 141:150 */     if ((!activeHandlers()) && (this.xmlWriter != null)) {
/* 142:151 */       this.xmlWriter.ignorableWhitespace(parm1, parm2, parm3);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void processingInstruction(String target, String data)
/* 147:    */     throws SAXException
/* 148:    */   {
/* 149:157 */     super.processingInstruction(target, data);
/* 150:159 */     if ((!activeHandlers()) && (this.xmlWriter != null)) {
/* 151:160 */       this.xmlWriter.processingInstruction(target, data);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setDocumentLocator(Locator locator)
/* 156:    */   {
/* 157:165 */     super.setDocumentLocator(locator);
/* 158:167 */     if (this.xmlWriter != null) {
/* 159:168 */       this.xmlWriter.setDocumentLocator(locator);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void skippedEntity(String name)
/* 164:    */     throws SAXException
/* 165:    */   {
/* 166:173 */     super.skippedEntity(name);
/* 167:175 */     if ((!activeHandlers()) && (this.xmlWriter != null)) {
/* 168:176 */       this.xmlWriter.skippedEntity(name);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void endDocument()
/* 173:    */     throws SAXException
/* 174:    */   {
/* 175:181 */     super.endDocument();
/* 176:183 */     if (this.xmlWriter != null) {
/* 177:184 */       this.xmlWriter.endDocument();
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void startPrefixMapping(String prefix, String uri)
/* 182:    */     throws SAXException
/* 183:    */   {
/* 184:190 */     super.startPrefixMapping(prefix, uri);
/* 185:192 */     if (this.xmlWriter != null) {
/* 186:193 */       this.xmlWriter.startPrefixMapping(prefix, uri);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void endElement(String uri, String localName, String qName)
/* 191:    */     throws SAXException
/* 192:    */   {
/* 193:199 */     ElementHandler currentHandler = getElementStack().getDispatchHandler().getHandler(getElementStack().getPath());
/* 194:    */     
/* 195:    */ 
/* 196:202 */     super.endElement(uri, localName, qName);
/* 197:204 */     if ((!activeHandlers()) && 
/* 198:205 */       (this.xmlWriter != null)) {
/* 199:206 */       if (currentHandler == null)
/* 200:    */       {
/* 201:207 */         this.xmlWriter.endElement(uri, localName, qName);
/* 202:    */       }
/* 203:208 */       else if ((currentHandler instanceof SAXModifyElementHandler))
/* 204:    */       {
/* 205:209 */         SAXModifyElementHandler modifyHandler = (SAXModifyElementHandler)currentHandler;
/* 206:    */         
/* 207:211 */         Element modifiedElement = modifyHandler.getModifiedElement();
/* 208:    */         try
/* 209:    */         {
/* 210:215 */           this.xmlWriter.write(modifiedElement);
/* 211:    */         }
/* 212:    */         catch (IOException ex)
/* 213:    */         {
/* 214:217 */           throw new SAXModifyException(ex);
/* 215:    */         }
/* 216:    */       }
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void endPrefixMapping(String prefix)
/* 221:    */     throws SAXException
/* 222:    */   {
/* 223:225 */     super.endPrefixMapping(prefix);
/* 224:227 */     if (this.xmlWriter != null) {
/* 225:228 */       this.xmlWriter.endPrefixMapping(prefix);
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void characters(char[] parm1, int parm2, int parm3)
/* 230:    */     throws SAXException
/* 231:    */   {
/* 232:234 */     super.characters(parm1, parm2, parm3);
/* 233:236 */     if ((!activeHandlers()) && (this.xmlWriter != null)) {
/* 234:237 */       this.xmlWriter.characters(parm1, parm2, parm3);
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   protected XMLWriter getXMLWriter()
/* 239:    */   {
/* 240:242 */     return this.xmlWriter;
/* 241:    */   }
/* 242:    */   
/* 243:    */   private boolean activeHandlers()
/* 244:    */   {
/* 245:246 */     DispatchHandler handler = getElementStack().getDispatchHandler();
/* 246:    */     
/* 247:248 */     return handler.getActiveHandlerCount() > 0;
/* 248:    */   }
/* 249:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXModifyContentHandler
 * JD-Core Version:    0.7.0.1
 */
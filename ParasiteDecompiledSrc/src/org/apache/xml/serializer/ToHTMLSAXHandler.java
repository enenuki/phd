/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.w3c.dom.Node;
/*   8:    */ import org.xml.sax.Attributes;
/*   9:    */ import org.xml.sax.ContentHandler;
/*  10:    */ import org.xml.sax.Locator;
/*  11:    */ import org.xml.sax.SAXException;
/*  12:    */ import org.xml.sax.ext.LexicalHandler;
/*  13:    */ 
/*  14:    */ /**
/*  15:    */  * @deprecated
/*  16:    */  */
/*  17:    */ public final class ToHTMLSAXHandler
/*  18:    */   extends ToSAXHandler
/*  19:    */ {
/*  20: 54 */   private boolean m_dtdHandled = false;
/*  21: 59 */   protected boolean m_escapeSetting = true;
/*  22:    */   
/*  23:    */   public Properties getOutputFormat()
/*  24:    */   {
/*  25: 68 */     return null;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public OutputStream getOutputStream()
/*  29:    */   {
/*  30: 78 */     return null;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Writer getWriter()
/*  34:    */   {
/*  35: 88 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void indent(int n)
/*  39:    */     throws SAXException
/*  40:    */   {}
/*  41:    */   
/*  42:    */   public void serialize(Node node)
/*  43:    */     throws IOException
/*  44:    */   {}
/*  45:    */   
/*  46:    */   public boolean setEscaping(boolean escape)
/*  47:    */     throws SAXException
/*  48:    */   {
/*  49:118 */     boolean oldEscapeSetting = this.m_escapeSetting;
/*  50:119 */     this.m_escapeSetting = escape;
/*  51:121 */     if (escape) {
/*  52:122 */       processingInstruction("javax.xml.transform.enable-output-escaping", "");
/*  53:    */     } else {
/*  54:124 */       processingInstruction("javax.xml.transform.disable-output-escaping", "");
/*  55:    */     }
/*  56:127 */     return oldEscapeSetting;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIndent(boolean indent) {}
/*  60:    */   
/*  61:    */   public void setOutputFormat(Properties format) {}
/*  62:    */   
/*  63:    */   public void setOutputStream(OutputStream output) {}
/*  64:    */   
/*  65:    */   public void setWriter(Writer writer) {}
/*  66:    */   
/*  67:    */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value)
/*  68:    */     throws SAXException
/*  69:    */   {}
/*  70:    */   
/*  71:    */   public void elementDecl(String name, String model)
/*  72:    */     throws SAXException
/*  73:    */   {}
/*  74:    */   
/*  75:    */   public void externalEntityDecl(String arg0, String arg1, String arg2)
/*  76:    */     throws SAXException
/*  77:    */   {}
/*  78:    */   
/*  79:    */   public void internalEntityDecl(String name, String value)
/*  80:    */     throws SAXException
/*  81:    */   {}
/*  82:    */   
/*  83:    */   public void endElement(String uri, String localName, String qName)
/*  84:    */     throws SAXException
/*  85:    */   {
/*  86:246 */     flushPending();
/*  87:247 */     this.m_saxHandler.endElement(uri, localName, qName);
/*  88:250 */     if (this.m_tracer != null) {
/*  89:251 */       super.fireEndElem(qName);
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void endPrefixMapping(String prefix)
/*  94:    */     throws SAXException
/*  95:    */   {}
/*  96:    */   
/*  97:    */   public void ignorableWhitespace(char[] ch, int start, int length)
/*  98:    */     throws SAXException
/*  99:    */   {}
/* 100:    */   
/* 101:    */   public void processingInstruction(String target, String data)
/* 102:    */     throws SAXException
/* 103:    */   {
/* 104:293 */     flushPending();
/* 105:294 */     this.m_saxHandler.processingInstruction(target, data);
/* 106:298 */     if (this.m_tracer != null) {
/* 107:299 */       super.fireEscapingEvent(target, data);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setDocumentLocator(Locator arg0) {}
/* 112:    */   
/* 113:    */   public void skippedEntity(String arg0)
/* 114:    */     throws SAXException
/* 115:    */   {}
/* 116:    */   
/* 117:    */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
/* 118:    */     throws SAXException
/* 119:    */   {
/* 120:350 */     flushPending();
/* 121:351 */     super.startElement(namespaceURI, localName, qName, atts);
/* 122:352 */     this.m_saxHandler.startElement(namespaceURI, localName, qName, atts);
/* 123:353 */     this.m_elemContext.m_startTagOpen = false;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void comment(char[] ch, int start, int length)
/* 127:    */     throws SAXException
/* 128:    */   {
/* 129:368 */     flushPending();
/* 130:369 */     if (this.m_lexHandler != null) {
/* 131:370 */       this.m_lexHandler.comment(ch, start, length);
/* 132:    */     }
/* 133:373 */     if (this.m_tracer != null) {
/* 134:374 */       super.fireCommentEvent(ch, start, length);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void endCDATA()
/* 139:    */     throws SAXException
/* 140:    */   {}
/* 141:    */   
/* 142:    */   public void endDTD()
/* 143:    */     throws SAXException
/* 144:    */   {}
/* 145:    */   
/* 146:    */   public void startCDATA()
/* 147:    */     throws SAXException
/* 148:    */   {}
/* 149:    */   
/* 150:    */   public void startEntity(String arg0)
/* 151:    */     throws SAXException
/* 152:    */   {}
/* 153:    */   
/* 154:    */   public void endDocument()
/* 155:    */     throws SAXException
/* 156:    */   {
/* 157:429 */     flushPending();
/* 158:    */     
/* 159:    */ 
/* 160:432 */     this.m_saxHandler.endDocument();
/* 161:434 */     if (this.m_tracer != null) {
/* 162:435 */       super.fireEndDoc();
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected void closeStartTag()
/* 167:    */     throws SAXException
/* 168:    */   {
/* 169:445 */     this.m_elemContext.m_startTagOpen = false;
/* 170:    */     
/* 171:    */ 
/* 172:448 */     this.m_saxHandler.startElement("", this.m_elemContext.m_elementName, this.m_elemContext.m_elementName, this.m_attributes);
/* 173:    */     
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:453 */     this.m_attributes.clear();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void close() {}
/* 181:    */   
/* 182:    */   public void characters(String chars)
/* 183:    */     throws SAXException
/* 184:    */   {
/* 185:477 */     int length = chars.length();
/* 186:478 */     if (length > this.m_charsBuff.length) {
/* 187:480 */       this.m_charsBuff = new char[length * 2 + 1];
/* 188:    */     }
/* 189:482 */     chars.getChars(0, length, this.m_charsBuff, 0);
/* 190:483 */     characters(this.m_charsBuff, 0, length);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public ToHTMLSAXHandler(ContentHandler handler, String encoding)
/* 194:    */   {
/* 195:494 */     super(handler, encoding);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public ToHTMLSAXHandler(ContentHandler handler, LexicalHandler lex, String encoding)
/* 199:    */   {
/* 200:507 */     super(handler, lex, encoding);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void startElement(String elementNamespaceURI, String elementLocalName, String elementName)
/* 204:    */     throws SAXException
/* 205:    */   {
/* 206:527 */     super.startElement(elementNamespaceURI, elementLocalName, elementName);
/* 207:    */     
/* 208:529 */     flushPending();
/* 209:532 */     if (!this.m_dtdHandled)
/* 210:    */     {
/* 211:534 */       String doctypeSystem = getDoctypeSystem();
/* 212:535 */       String doctypePublic = getDoctypePublic();
/* 213:536 */       if (((doctypeSystem != null) || (doctypePublic != null)) && 
/* 214:537 */         (this.m_lexHandler != null)) {
/* 215:538 */         this.m_lexHandler.startDTD(elementName, doctypePublic, doctypeSystem);
/* 216:    */       }
/* 217:543 */       this.m_dtdHandled = true;
/* 218:    */     }
/* 219:545 */     this.m_elemContext = this.m_elemContext.push(elementNamespaceURI, elementLocalName, elementName);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void startElement(String elementName)
/* 223:    */     throws SAXException
/* 224:    */   {
/* 225:556 */     startElement(null, null, elementName);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void endElement(String elementName)
/* 229:    */     throws SAXException
/* 230:    */   {
/* 231:569 */     flushPending();
/* 232:570 */     this.m_saxHandler.endElement("", elementName, elementName);
/* 233:573 */     if (this.m_tracer != null) {
/* 234:574 */       super.fireEndElem(elementName);
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void characters(char[] ch, int off, int len)
/* 239:    */     throws SAXException
/* 240:    */   {
/* 241:609 */     flushPending();
/* 242:610 */     this.m_saxHandler.characters(ch, off, len);
/* 243:613 */     if (this.m_tracer != null) {
/* 244:614 */       super.fireCharEvent(ch, off, len);
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void flushPending()
/* 249:    */     throws SAXException
/* 250:    */   {
/* 251:623 */     if (this.m_needToCallStartDocument)
/* 252:    */     {
/* 253:625 */       startDocumentInternal();
/* 254:626 */       this.m_needToCallStartDocument = false;
/* 255:    */     }
/* 256:629 */     if (this.m_elemContext.m_startTagOpen)
/* 257:    */     {
/* 258:631 */       closeStartTag();
/* 259:632 */       this.m_elemContext.m_startTagOpen = false;
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush)
/* 264:    */     throws SAXException
/* 265:    */   {
/* 266:661 */     if (shouldFlush) {
/* 267:662 */       flushPending();
/* 268:    */     }
/* 269:663 */     this.m_saxHandler.startPrefixMapping(prefix, uri);
/* 270:664 */     return false;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void startPrefixMapping(String prefix, String uri)
/* 274:    */     throws SAXException
/* 275:    */   {
/* 276:685 */     startPrefixMapping(prefix, uri, true);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void namespaceAfterStartElement(String prefix, String uri)
/* 280:    */     throws SAXException
/* 281:    */   {
/* 282:705 */     if (this.m_elemContext.m_elementURI == null)
/* 283:    */     {
/* 284:707 */       String prefix1 = SerializerBase.getPrefixPart(this.m_elemContext.m_elementName);
/* 285:708 */       if ((prefix1 == null) && ("".equals(prefix))) {
/* 286:714 */         this.m_elemContext.m_elementURI = uri;
/* 287:    */       }
/* 288:    */     }
/* 289:717 */     startPrefixMapping(prefix, uri, false);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public boolean reset()
/* 293:    */   {
/* 294:730 */     boolean wasReset = false;
/* 295:731 */     if (super.reset())
/* 296:    */     {
/* 297:733 */       resetToHTMLSAXHandler();
/* 298:734 */       wasReset = true;
/* 299:    */     }
/* 300:736 */     return wasReset;
/* 301:    */   }
/* 302:    */   
/* 303:    */   private void resetToHTMLSAXHandler()
/* 304:    */   {
/* 305:745 */     this.m_dtdHandled = false;
/* 306:746 */     this.m_escapeSetting = true;
/* 307:    */   }
/* 308:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ToHTMLSAXHandler
 * JD-Core Version:    0.7.0.1
 */
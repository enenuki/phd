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
/*  14:    */ public final class ToXMLSAXHandler
/*  15:    */   extends ToSAXHandler
/*  16:    */ {
/*  17: 51 */   protected boolean m_escapeSetting = true;
/*  18:    */   
/*  19:    */   public ToXMLSAXHandler()
/*  20:    */   {
/*  21: 56 */     this.m_prefixMap = new NamespaceMappings();
/*  22: 57 */     initCDATA();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Properties getOutputFormat()
/*  26:    */   {
/*  27: 65 */     return null;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public OutputStream getOutputStream()
/*  31:    */   {
/*  32: 73 */     return null;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Writer getWriter()
/*  36:    */   {
/*  37: 81 */     return null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void indent(int n)
/*  41:    */     throws SAXException
/*  42:    */   {}
/*  43:    */   
/*  44:    */   public void serialize(Node node)
/*  45:    */     throws IOException
/*  46:    */   {}
/*  47:    */   
/*  48:    */   public boolean setEscaping(boolean escape)
/*  49:    */     throws SAXException
/*  50:    */   {
/*  51:104 */     boolean oldEscapeSetting = this.m_escapeSetting;
/*  52:105 */     this.m_escapeSetting = escape;
/*  53:107 */     if (escape) {
/*  54:108 */       processingInstruction("javax.xml.transform.enable-output-escaping", "");
/*  55:    */     } else {
/*  56:110 */       processingInstruction("javax.xml.transform.disable-output-escaping", "");
/*  57:    */     }
/*  58:113 */     return oldEscapeSetting;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setOutputFormat(Properties format) {}
/*  62:    */   
/*  63:    */   public void setOutputStream(OutputStream output) {}
/*  64:    */   
/*  65:    */   public void setWriter(Writer writer) {}
/*  66:    */   
/*  67:    */   public void attributeDecl(String arg0, String arg1, String arg2, String arg3, String arg4)
/*  68:    */     throws SAXException
/*  69:    */   {}
/*  70:    */   
/*  71:    */   public void elementDecl(String arg0, String arg1)
/*  72:    */     throws SAXException
/*  73:    */   {}
/*  74:    */   
/*  75:    */   public void externalEntityDecl(String arg0, String arg1, String arg2)
/*  76:    */     throws SAXException
/*  77:    */   {}
/*  78:    */   
/*  79:    */   public void internalEntityDecl(String arg0, String arg1)
/*  80:    */     throws SAXException
/*  81:    */   {}
/*  82:    */   
/*  83:    */   public void endDocument()
/*  84:    */     throws SAXException
/*  85:    */   {
/*  86:180 */     flushPending();
/*  87:    */     
/*  88:    */ 
/*  89:183 */     this.m_saxHandler.endDocument();
/*  90:185 */     if (this.m_tracer != null) {
/*  91:186 */       super.fireEndDoc();
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void closeStartTag()
/*  96:    */     throws SAXException
/*  97:    */   {
/*  98:196 */     this.m_elemContext.m_startTagOpen = false;
/*  99:    */     
/* 100:198 */     String localName = SerializerBase.getLocalName(this.m_elemContext.m_elementName);
/* 101:199 */     String uri = getNamespaceURI(this.m_elemContext.m_elementName, true);
/* 102:202 */     if (this.m_needToCallStartDocument) {
/* 103:204 */       startDocumentInternal();
/* 104:    */     }
/* 105:206 */     this.m_saxHandler.startElement(uri, localName, this.m_elemContext.m_elementName, this.m_attributes);
/* 106:    */     
/* 107:    */ 
/* 108:209 */     this.m_attributes.clear();
/* 109:211 */     if (this.m_state != null) {
/* 110:212 */       this.m_state.setCurrentNode(null);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void closeCDATA()
/* 115:    */     throws SAXException
/* 116:    */   {
/* 117:226 */     if ((this.m_lexHandler != null) && (this.m_cdataTagOpen)) {
/* 118:227 */       this.m_lexHandler.endCDATA();
/* 119:    */     }
/* 120:235 */     this.m_cdataTagOpen = false;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void endElement(String namespaceURI, String localName, String qName)
/* 124:    */     throws SAXException
/* 125:    */   {
/* 126:245 */     flushPending();
/* 127:247 */     if (namespaceURI == null) {
/* 128:249 */       if (this.m_elemContext.m_elementURI != null) {
/* 129:250 */         namespaceURI = this.m_elemContext.m_elementURI;
/* 130:    */       } else {
/* 131:252 */         namespaceURI = getNamespaceURI(qName, true);
/* 132:    */       }
/* 133:    */     }
/* 134:255 */     if (localName == null) {
/* 135:257 */       if (this.m_elemContext.m_elementLocalName != null) {
/* 136:258 */         localName = this.m_elemContext.m_elementLocalName;
/* 137:    */       } else {
/* 138:260 */         localName = SerializerBase.getLocalName(qName);
/* 139:    */       }
/* 140:    */     }
/* 141:263 */     this.m_saxHandler.endElement(namespaceURI, localName, qName);
/* 142:265 */     if (this.m_tracer != null) {
/* 143:266 */       super.fireEndElem(qName);
/* 144:    */     }
/* 145:271 */     this.m_prefixMap.popNamespaces(this.m_elemContext.m_currentElemDepth, this.m_saxHandler);
/* 146:    */     
/* 147:273 */     this.m_elemContext = this.m_elemContext.m_prev;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void endPrefixMapping(String prefix)
/* 151:    */     throws SAXException
/* 152:    */   {}
/* 153:    */   
/* 154:    */   public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
/* 155:    */     throws SAXException
/* 156:    */   {
/* 157:293 */     this.m_saxHandler.ignorableWhitespace(arg0, arg1, arg2);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setDocumentLocator(Locator arg0)
/* 161:    */   {
/* 162:301 */     this.m_saxHandler.setDocumentLocator(arg0);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void skippedEntity(String arg0)
/* 166:    */     throws SAXException
/* 167:    */   {
/* 168:309 */     this.m_saxHandler.skippedEntity(arg0);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void startPrefixMapping(String prefix, String uri)
/* 172:    */     throws SAXException
/* 173:    */   {
/* 174:320 */     startPrefixMapping(prefix, uri, true);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush)
/* 178:    */     throws SAXException
/* 179:    */   {
/* 180:    */     int pushDepth;
/* 181:348 */     if (shouldFlush)
/* 182:    */     {
/* 183:350 */       flushPending();
/* 184:    */       
/* 185:352 */       pushDepth = this.m_elemContext.m_currentElemDepth + 1;
/* 186:    */     }
/* 187:    */     else
/* 188:    */     {
/* 189:357 */       pushDepth = this.m_elemContext.m_currentElemDepth;
/* 190:    */     }
/* 191:359 */     boolean pushed = this.m_prefixMap.pushNamespace(prefix, uri, pushDepth);
/* 192:361 */     if (pushed)
/* 193:    */     {
/* 194:363 */       this.m_saxHandler.startPrefixMapping(prefix, uri);
/* 195:365 */       if (getShouldOutputNSAttr())
/* 196:    */       {
/* 197:    */         String name;
/* 198:374 */         if ("".equals(prefix))
/* 199:    */         {
/* 200:376 */           name = "xmlns";
/* 201:377 */           addAttributeAlways("http://www.w3.org/2000/xmlns/", name, name, "CDATA", uri, false);
/* 202:    */         }
/* 203:381 */         else if (!"".equals(uri))
/* 204:    */         {
/* 205:383 */           name = "xmlns:" + prefix;
/* 206:    */           
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:389 */           addAttributeAlways("http://www.w3.org/2000/xmlns/", prefix, name, "CDATA", uri, false);
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:394 */     return pushed;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void comment(char[] arg0, int arg1, int arg2)
/* 219:    */     throws SAXException
/* 220:    */   {
/* 221:403 */     flushPending();
/* 222:404 */     if (this.m_lexHandler != null) {
/* 223:405 */       this.m_lexHandler.comment(arg0, arg1, arg2);
/* 224:    */     }
/* 225:407 */     if (this.m_tracer != null) {
/* 226:408 */       super.fireCommentEvent(arg0, arg1, arg2);
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void endCDATA()
/* 231:    */     throws SAXException
/* 232:    */   {}
/* 233:    */   
/* 234:    */   public void endDTD()
/* 235:    */     throws SAXException
/* 236:    */   {
/* 237:444 */     if (this.m_lexHandler != null) {
/* 238:445 */       this.m_lexHandler.endDTD();
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void startEntity(String arg0)
/* 243:    */     throws SAXException
/* 244:    */   {
/* 245:453 */     if (this.m_lexHandler != null) {
/* 246:454 */       this.m_lexHandler.startEntity(arg0);
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void characters(String chars)
/* 251:    */     throws SAXException
/* 252:    */   {
/* 253:462 */     int length = chars.length();
/* 254:463 */     if (length > this.m_charsBuff.length) {
/* 255:465 */       this.m_charsBuff = new char[length * 2 + 1];
/* 256:    */     }
/* 257:467 */     chars.getChars(0, length, this.m_charsBuff, 0);
/* 258:468 */     characters(this.m_charsBuff, 0, length);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public ToXMLSAXHandler(ContentHandler handler, String encoding)
/* 262:    */   {
/* 263:473 */     super(handler, encoding);
/* 264:    */     
/* 265:475 */     initCDATA();
/* 266:    */     
/* 267:477 */     this.m_prefixMap = new NamespaceMappings();
/* 268:    */   }
/* 269:    */   
/* 270:    */   public ToXMLSAXHandler(ContentHandler handler, LexicalHandler lex, String encoding)
/* 271:    */   {
/* 272:485 */     super(handler, lex, encoding);
/* 273:    */     
/* 274:487 */     initCDATA();
/* 275:    */     
/* 276:489 */     this.m_prefixMap = new NamespaceMappings();
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void startElement(String elementNamespaceURI, String elementLocalName, String elementName)
/* 280:    */     throws SAXException
/* 281:    */   {
/* 282:501 */     startElement(elementNamespaceURI, elementLocalName, elementName, null);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void startElement(String elementName)
/* 286:    */     throws SAXException
/* 287:    */   {
/* 288:508 */     startElement(null, null, elementName, null);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void characters(char[] ch, int off, int len)
/* 292:    */     throws SAXException
/* 293:    */   {
/* 294:516 */     if (this.m_needToCallStartDocument)
/* 295:    */     {
/* 296:518 */       startDocumentInternal();
/* 297:519 */       this.m_needToCallStartDocument = false;
/* 298:    */     }
/* 299:522 */     if (this.m_elemContext.m_startTagOpen)
/* 300:    */     {
/* 301:524 */       closeStartTag();
/* 302:525 */       this.m_elemContext.m_startTagOpen = false;
/* 303:    */     }
/* 304:528 */     if ((this.m_elemContext.m_isCdataSection) && (!this.m_cdataTagOpen) && (this.m_lexHandler != null))
/* 305:    */     {
/* 306:531 */       this.m_lexHandler.startCDATA();
/* 307:    */       
/* 308:    */ 
/* 309:    */ 
/* 310:535 */       this.m_cdataTagOpen = true;
/* 311:    */     }
/* 312:542 */     this.m_saxHandler.characters(ch, off, len);
/* 313:545 */     if (this.m_tracer != null) {
/* 314:546 */       fireCharEvent(ch, off, len);
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void endElement(String elemName)
/* 319:    */     throws SAXException
/* 320:    */   {
/* 321:555 */     endElement(null, null, elemName);
/* 322:    */   }
/* 323:    */   
/* 324:    */   public void namespaceAfterStartElement(String prefix, String uri)
/* 325:    */     throws SAXException
/* 326:    */   {
/* 327:569 */     startPrefixMapping(prefix, uri, false);
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void processingInstruction(String target, String data)
/* 331:    */     throws SAXException
/* 332:    */   {
/* 333:580 */     flushPending();
/* 334:    */     
/* 335:    */ 
/* 336:583 */     this.m_saxHandler.processingInstruction(target, data);
/* 337:587 */     if (this.m_tracer != null) {
/* 338:588 */       super.fireEscapingEvent(target, data);
/* 339:    */     }
/* 340:    */   }
/* 341:    */   
/* 342:    */   protected boolean popNamespace(String prefix)
/* 343:    */   {
/* 344:    */     try
/* 345:    */     {
/* 346:599 */       if (this.m_prefixMap.popNamespace(prefix))
/* 347:    */       {
/* 348:601 */         this.m_saxHandler.endPrefixMapping(prefix);
/* 349:602 */         return true;
/* 350:    */       }
/* 351:    */     }
/* 352:    */     catch (SAXException e) {}
/* 353:609 */     return false;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void startCDATA()
/* 357:    */     throws SAXException
/* 358:    */   {
/* 359:622 */     if (!this.m_cdataTagOpen)
/* 360:    */     {
/* 361:624 */       flushPending();
/* 362:625 */       if (this.m_lexHandler != null)
/* 363:    */       {
/* 364:626 */         this.m_lexHandler.startCDATA();
/* 365:    */         
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:631 */         this.m_cdataTagOpen = true;
/* 370:    */       }
/* 371:    */     }
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void startElement(String namespaceURI, String localName, String name, Attributes atts)
/* 375:    */     throws SAXException
/* 376:    */   {
/* 377:646 */     flushPending();
/* 378:647 */     super.startElement(namespaceURI, localName, name, atts);
/* 379:650 */     if (this.m_needToOutputDocTypeDecl)
/* 380:    */     {
/* 381:652 */       String doctypeSystem = getDoctypeSystem();
/* 382:653 */       if ((doctypeSystem != null) && (this.m_lexHandler != null))
/* 383:    */       {
/* 384:655 */         String doctypePublic = getDoctypePublic();
/* 385:656 */         if (doctypeSystem != null) {
/* 386:657 */           this.m_lexHandler.startDTD(name, doctypePublic, doctypeSystem);
/* 387:    */         }
/* 388:    */       }
/* 389:662 */       this.m_needToOutputDocTypeDecl = false;
/* 390:    */     }
/* 391:664 */     this.m_elemContext = this.m_elemContext.push(namespaceURI, localName, name);
/* 392:668 */     if (namespaceURI != null) {
/* 393:669 */       ensurePrefixIsDeclared(namespaceURI, name);
/* 394:    */     }
/* 395:672 */     if (atts != null) {
/* 396:673 */       addAttributes(atts);
/* 397:    */     }
/* 398:677 */     this.m_elemContext.m_isCdataSection = isCdataSection();
/* 399:    */   }
/* 400:    */   
/* 401:    */   private void ensurePrefixIsDeclared(String ns, String rawName)
/* 402:    */     throws SAXException
/* 403:    */   {
/* 404:685 */     if ((ns != null) && (ns.length() > 0))
/* 405:    */     {
/* 406:    */       int index;
/* 407:688 */       boolean no_prefix = (index = rawName.indexOf(":")) < 0;
/* 408:689 */       String prefix = no_prefix ? "" : rawName.substring(0, index);
/* 409:692 */       if (null != prefix)
/* 410:    */       {
/* 411:694 */         String foundURI = this.m_prefixMap.lookupNamespace(prefix);
/* 412:696 */         if ((null == foundURI) || (!foundURI.equals(ns)))
/* 413:    */         {
/* 414:698 */           startPrefixMapping(prefix, ns, false);
/* 415:700 */           if (getShouldOutputNSAttr()) {
/* 416:703 */             addAttributeAlways("http://www.w3.org/2000/xmlns/", no_prefix ? "xmlns" : prefix, "xmlns:" + prefix, "CDATA", ns, false);
/* 417:    */           }
/* 418:    */         }
/* 419:    */       }
/* 420:    */     }
/* 421:    */   }
/* 422:    */   
/* 423:    */   public void addAttribute(String uri, String localName, String rawName, String type, String value, boolean XSLAttribute)
/* 424:    */     throws SAXException
/* 425:    */   {
/* 426:738 */     if (this.m_elemContext.m_startTagOpen)
/* 427:    */     {
/* 428:740 */       ensurePrefixIsDeclared(uri, rawName);
/* 429:741 */       addAttributeAlways(uri, localName, rawName, type, value, false);
/* 430:    */     }
/* 431:    */   }
/* 432:    */   
/* 433:    */   public boolean reset()
/* 434:    */   {
/* 435:756 */     boolean wasReset = false;
/* 436:757 */     if (super.reset())
/* 437:    */     {
/* 438:759 */       resetToXMLSAXHandler();
/* 439:760 */       wasReset = true;
/* 440:    */     }
/* 441:762 */     return wasReset;
/* 442:    */   }
/* 443:    */   
/* 444:    */   private void resetToXMLSAXHandler()
/* 445:    */   {
/* 446:771 */     this.m_escapeSetting = true;
/* 447:    */   }
/* 448:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ToXMLSAXHandler
 * JD-Core Version:    0.7.0.1
 */
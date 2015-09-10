/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.dom4j.Branch;
/*   9:    */ import org.dom4j.Document;
/*  10:    */ import org.dom4j.DocumentFactory;
/*  11:    */ import org.dom4j.DocumentType;
/*  12:    */ import org.dom4j.Element;
/*  13:    */ import org.dom4j.ElementHandler;
/*  14:    */ import org.dom4j.Namespace;
/*  15:    */ import org.dom4j.QName;
/*  16:    */ import org.dom4j.dtd.AttributeDecl;
/*  17:    */ import org.dom4j.dtd.ElementDecl;
/*  18:    */ import org.dom4j.dtd.ExternalEntityDecl;
/*  19:    */ import org.dom4j.dtd.InternalEntityDecl;
/*  20:    */ import org.dom4j.tree.AbstractElement;
/*  21:    */ import org.dom4j.tree.NamespaceStack;
/*  22:    */ import org.xml.sax.Attributes;
/*  23:    */ import org.xml.sax.DTDHandler;
/*  24:    */ import org.xml.sax.EntityResolver;
/*  25:    */ import org.xml.sax.InputSource;
/*  26:    */ import org.xml.sax.Locator;
/*  27:    */ import org.xml.sax.SAXException;
/*  28:    */ import org.xml.sax.SAXParseException;
/*  29:    */ import org.xml.sax.ext.DeclHandler;
/*  30:    */ import org.xml.sax.ext.LexicalHandler;
/*  31:    */ import org.xml.sax.helpers.DefaultHandler;
/*  32:    */ 
/*  33:    */ public class SAXContentHandler
/*  34:    */   extends DefaultHandler
/*  35:    */   implements LexicalHandler, DeclHandler, DTDHandler
/*  36:    */ {
/*  37:    */   private DocumentFactory documentFactory;
/*  38:    */   private Document document;
/*  39:    */   private ElementStack elementStack;
/*  40:    */   private NamespaceStack namespaceStack;
/*  41:    */   private ElementHandler elementHandler;
/*  42:    */   private Locator locator;
/*  43:    */   private String entity;
/*  44:    */   private boolean insideDTDSection;
/*  45:    */   private boolean insideCDATASection;
/*  46:    */   private StringBuffer cdataText;
/*  47: 86 */   private Map availableNamespaceMap = new HashMap();
/*  48: 89 */   private List declaredNamespaceList = new ArrayList();
/*  49:    */   private List internalDTDDeclarations;
/*  50:    */   private List externalDTDDeclarations;
/*  51:    */   private int declaredNamespaceIndex;
/*  52:    */   private EntityResolver entityResolver;
/*  53:    */   private InputSource inputSource;
/*  54:    */   private Element currentElement;
/*  55:109 */   private boolean includeInternalDTDDeclarations = false;
/*  56:112 */   private boolean includeExternalDTDDeclarations = false;
/*  57:    */   private int entityLevel;
/*  58:118 */   private boolean internalDTDsubset = false;
/*  59:121 */   private boolean mergeAdjacentText = false;
/*  60:124 */   private boolean textInTextBuffer = false;
/*  61:127 */   private boolean ignoreComments = false;
/*  62:    */   private StringBuffer textBuffer;
/*  63:133 */   private boolean stripWhitespaceText = false;
/*  64:    */   
/*  65:    */   public SAXContentHandler()
/*  66:    */   {
/*  67:136 */     this(DocumentFactory.getInstance());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public SAXContentHandler(DocumentFactory documentFactory)
/*  71:    */   {
/*  72:140 */     this(documentFactory, null);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public SAXContentHandler(DocumentFactory documentFactory, ElementHandler elementHandler)
/*  76:    */   {
/*  77:145 */     this(documentFactory, elementHandler, null);
/*  78:146 */     this.elementStack = createElementStack();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public SAXContentHandler(DocumentFactory documentFactory, ElementHandler elementHandler, ElementStack elementStack)
/*  82:    */   {
/*  83:151 */     this.documentFactory = documentFactory;
/*  84:152 */     this.elementHandler = elementHandler;
/*  85:153 */     this.elementStack = elementStack;
/*  86:154 */     this.namespaceStack = new NamespaceStack(documentFactory);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Document getDocument()
/*  90:    */   {
/*  91:163 */     if (this.document == null) {
/*  92:164 */       this.document = createDocument();
/*  93:    */     }
/*  94:167 */     return this.document;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setDocumentLocator(Locator documentLocator)
/*  98:    */   {
/*  99:173 */     this.locator = documentLocator;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void processingInstruction(String target, String data)
/* 103:    */     throws SAXException
/* 104:    */   {
/* 105:178 */     if ((this.mergeAdjacentText) && (this.textInTextBuffer)) {
/* 106:179 */       completeCurrentTextNode();
/* 107:    */     }
/* 108:182 */     if (this.currentElement != null) {
/* 109:183 */       this.currentElement.addProcessingInstruction(target, data);
/* 110:    */     } else {
/* 111:185 */       getDocument().addProcessingInstruction(target, data);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void startPrefixMapping(String prefix, String uri)
/* 116:    */     throws SAXException
/* 117:    */   {
/* 118:191 */     this.namespaceStack.push(prefix, uri);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void endPrefixMapping(String prefix)
/* 122:    */     throws SAXException
/* 123:    */   {
/* 124:195 */     this.namespaceStack.pop(prefix);
/* 125:196 */     this.declaredNamespaceIndex = this.namespaceStack.size();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void startDocument()
/* 129:    */     throws SAXException
/* 130:    */   {
/* 131:201 */     this.document = null;
/* 132:202 */     this.currentElement = null;
/* 133:    */     
/* 134:204 */     this.elementStack.clear();
/* 135:206 */     if ((this.elementHandler != null) && ((this.elementHandler instanceof DispatchHandler))) {
/* 136:208 */       this.elementStack.setDispatchHandler((DispatchHandler)this.elementHandler);
/* 137:    */     }
/* 138:211 */     this.namespaceStack.clear();
/* 139:212 */     this.declaredNamespaceIndex = 0;
/* 140:214 */     if ((this.mergeAdjacentText) && (this.textBuffer == null)) {
/* 141:215 */       this.textBuffer = new StringBuffer();
/* 142:    */     }
/* 143:218 */     this.textInTextBuffer = false;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void endDocument()
/* 147:    */     throws SAXException
/* 148:    */   {
/* 149:222 */     this.namespaceStack.clear();
/* 150:223 */     this.elementStack.clear();
/* 151:224 */     this.currentElement = null;
/* 152:225 */     this.textBuffer = null;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attributes)
/* 156:    */     throws SAXException
/* 157:    */   {
/* 158:230 */     if ((this.mergeAdjacentText) && (this.textInTextBuffer)) {
/* 159:231 */       completeCurrentTextNode();
/* 160:    */     }
/* 161:234 */     QName qName = this.namespaceStack.getQName(namespaceURI, localName, qualifiedName);
/* 162:    */     
/* 163:    */ 
/* 164:237 */     Branch branch = this.currentElement;
/* 165:239 */     if (branch == null) {
/* 166:240 */       branch = getDocument();
/* 167:    */     }
/* 168:243 */     Element element = branch.addElement(qName);
/* 169:    */     
/* 170:    */ 
/* 171:246 */     addDeclaredNamespaces(element);
/* 172:    */     
/* 173:    */ 
/* 174:249 */     addAttributes(element, attributes);
/* 175:    */     
/* 176:251 */     this.elementStack.pushElement(element);
/* 177:252 */     this.currentElement = element;
/* 178:    */     
/* 179:254 */     this.entity = null;
/* 180:256 */     if (this.elementHandler != null) {
/* 181:257 */       this.elementHandler.onStart(this.elementStack);
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void endElement(String namespaceURI, String localName, String qName)
/* 186:    */     throws SAXException
/* 187:    */   {
/* 188:263 */     if ((this.mergeAdjacentText) && (this.textInTextBuffer)) {
/* 189:264 */       completeCurrentTextNode();
/* 190:    */     }
/* 191:267 */     if ((this.elementHandler != null) && (this.currentElement != null)) {
/* 192:268 */       this.elementHandler.onEnd(this.elementStack);
/* 193:    */     }
/* 194:271 */     this.elementStack.popElement();
/* 195:272 */     this.currentElement = this.elementStack.peekElement();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void characters(char[] ch, int start, int end)
/* 199:    */     throws SAXException
/* 200:    */   {
/* 201:276 */     if (end == 0) {
/* 202:277 */       return;
/* 203:    */     }
/* 204:280 */     if (this.currentElement != null) {
/* 205:281 */       if (this.entity != null)
/* 206:    */       {
/* 207:282 */         if ((this.mergeAdjacentText) && (this.textInTextBuffer)) {
/* 208:283 */           completeCurrentTextNode();
/* 209:    */         }
/* 210:286 */         this.currentElement.addEntity(this.entity, new String(ch, start, end));
/* 211:287 */         this.entity = null;
/* 212:    */       }
/* 213:288 */       else if (this.insideCDATASection)
/* 214:    */       {
/* 215:289 */         if ((this.mergeAdjacentText) && (this.textInTextBuffer)) {
/* 216:290 */           completeCurrentTextNode();
/* 217:    */         }
/* 218:293 */         this.cdataText.append(new String(ch, start, end));
/* 219:    */       }
/* 220:295 */       else if (this.mergeAdjacentText)
/* 221:    */       {
/* 222:296 */         this.textBuffer.append(ch, start, end);
/* 223:297 */         this.textInTextBuffer = true;
/* 224:    */       }
/* 225:    */       else
/* 226:    */       {
/* 227:299 */         this.currentElement.addText(new String(ch, start, end));
/* 228:    */       }
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void warning(SAXParseException exception)
/* 233:    */     throws SAXException
/* 234:    */   {}
/* 235:    */   
/* 236:    */   public void error(SAXParseException exception)
/* 237:    */     throws SAXException
/* 238:    */   {
/* 239:333 */     throw exception;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void fatalError(SAXParseException exception)
/* 243:    */     throws SAXException
/* 244:    */   {
/* 245:347 */     throw exception;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void startDTD(String name, String publicId, String systemId)
/* 249:    */     throws SAXException
/* 250:    */   {
/* 251:354 */     getDocument().addDocType(name, publicId, systemId);
/* 252:355 */     this.insideDTDSection = true;
/* 253:356 */     this.internalDTDsubset = true;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void endDTD()
/* 257:    */     throws SAXException
/* 258:    */   {
/* 259:360 */     this.insideDTDSection = false;
/* 260:    */     
/* 261:362 */     DocumentType docType = getDocument().getDocType();
/* 262:364 */     if (docType != null)
/* 263:    */     {
/* 264:365 */       if (this.internalDTDDeclarations != null) {
/* 265:366 */         docType.setInternalDeclarations(this.internalDTDDeclarations);
/* 266:    */       }
/* 267:369 */       if (this.externalDTDDeclarations != null) {
/* 268:370 */         docType.setExternalDeclarations(this.externalDTDDeclarations);
/* 269:    */       }
/* 270:    */     }
/* 271:374 */     this.internalDTDDeclarations = null;
/* 272:375 */     this.externalDTDDeclarations = null;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void startEntity(String name)
/* 276:    */     throws SAXException
/* 277:    */   {
/* 278:379 */     this.entityLevel += 1;
/* 279:    */     
/* 280:    */ 
/* 281:382 */     this.entity = null;
/* 282:384 */     if ((!this.insideDTDSection) && 
/* 283:385 */       (!isIgnorableEntity(name))) {
/* 284:386 */       this.entity = name;
/* 285:    */     }
/* 286:394 */     this.internalDTDsubset = false;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void endEntity(String name)
/* 290:    */     throws SAXException
/* 291:    */   {
/* 292:398 */     this.entityLevel -= 1;
/* 293:399 */     this.entity = null;
/* 294:401 */     if (this.entityLevel == 0) {
/* 295:402 */       this.internalDTDsubset = true;
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void startCDATA()
/* 300:    */     throws SAXException
/* 301:    */   {
/* 302:407 */     this.insideCDATASection = true;
/* 303:408 */     this.cdataText = new StringBuffer();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void endCDATA()
/* 307:    */     throws SAXException
/* 308:    */   {
/* 309:412 */     this.insideCDATASection = false;
/* 310:413 */     this.currentElement.addCDATA(this.cdataText.toString());
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void comment(char[] ch, int start, int end)
/* 314:    */     throws SAXException
/* 315:    */   {
/* 316:417 */     if (!this.ignoreComments)
/* 317:    */     {
/* 318:418 */       if ((this.mergeAdjacentText) && (this.textInTextBuffer)) {
/* 319:419 */         completeCurrentTextNode();
/* 320:    */       }
/* 321:422 */       String text = new String(ch, start, end);
/* 322:424 */       if ((!this.insideDTDSection) && (text.length() > 0)) {
/* 323:425 */         if (this.currentElement != null) {
/* 324:426 */           this.currentElement.addComment(text);
/* 325:    */         } else {
/* 326:428 */           getDocument().addComment(text);
/* 327:    */         }
/* 328:    */       }
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void elementDecl(String name, String model)
/* 333:    */     throws SAXException
/* 334:    */   {
/* 335:458 */     if (this.internalDTDsubset)
/* 336:    */     {
/* 337:459 */       if (this.includeInternalDTDDeclarations) {
/* 338:460 */         addDTDDeclaration(new ElementDecl(name, model));
/* 339:    */       }
/* 340:    */     }
/* 341:463 */     else if (this.includeExternalDTDDeclarations) {
/* 342:464 */       addExternalDTDDeclaration(new ElementDecl(name, model));
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String val)
/* 347:    */     throws SAXException
/* 348:    */   {
/* 349:504 */     if (this.internalDTDsubset)
/* 350:    */     {
/* 351:505 */       if (this.includeInternalDTDDeclarations) {
/* 352:506 */         addDTDDeclaration(new AttributeDecl(eName, aName, type, valueDefault, val));
/* 353:    */       }
/* 354:    */     }
/* 355:510 */     else if (this.includeExternalDTDDeclarations) {
/* 356:511 */       addExternalDTDDeclaration(new AttributeDecl(eName, aName, type, valueDefault, val));
/* 357:    */     }
/* 358:    */   }
/* 359:    */   
/* 360:    */   public void internalEntityDecl(String name, String value)
/* 361:    */     throws SAXException
/* 362:    */   {
/* 363:540 */     if (this.internalDTDsubset)
/* 364:    */     {
/* 365:541 */       if (this.includeInternalDTDDeclarations) {
/* 366:542 */         addDTDDeclaration(new InternalEntityDecl(name, value));
/* 367:    */       }
/* 368:    */     }
/* 369:545 */     else if (this.includeExternalDTDDeclarations) {
/* 370:546 */       addExternalDTDDeclaration(new InternalEntityDecl(name, value));
/* 371:    */     }
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void externalEntityDecl(String name, String publicId, String sysId)
/* 375:    */     throws SAXException
/* 376:    */   {
/* 377:575 */     ExternalEntityDecl declaration = new ExternalEntityDecl(name, publicId, sysId);
/* 378:578 */     if (this.internalDTDsubset)
/* 379:    */     {
/* 380:579 */       if (this.includeInternalDTDDeclarations) {
/* 381:580 */         addDTDDeclaration(declaration);
/* 382:    */       }
/* 383:    */     }
/* 384:583 */     else if (this.includeExternalDTDDeclarations) {
/* 385:584 */       addExternalDTDDeclaration(declaration);
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void notationDecl(String name, String publicId, String systemId)
/* 390:    */     throws SAXException
/* 391:    */   {}
/* 392:    */   
/* 393:    */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
/* 394:    */     throws SAXException
/* 395:    */   {}
/* 396:    */   
/* 397:    */   public ElementStack getElementStack()
/* 398:    */   {
/* 399:666 */     return this.elementStack;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void setElementStack(ElementStack elementStack)
/* 403:    */   {
/* 404:670 */     this.elementStack = elementStack;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public EntityResolver getEntityResolver()
/* 408:    */   {
/* 409:674 */     return this.entityResolver;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setEntityResolver(EntityResolver entityResolver)
/* 413:    */   {
/* 414:678 */     this.entityResolver = entityResolver;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public InputSource getInputSource()
/* 418:    */   {
/* 419:682 */     return this.inputSource;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void setInputSource(InputSource inputSource)
/* 423:    */   {
/* 424:686 */     this.inputSource = inputSource;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public boolean isIncludeInternalDTDDeclarations()
/* 428:    */   {
/* 429:696 */     return this.includeInternalDTDDeclarations;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setIncludeInternalDTDDeclarations(boolean include)
/* 433:    */   {
/* 434:708 */     this.includeInternalDTDDeclarations = include;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public boolean isIncludeExternalDTDDeclarations()
/* 438:    */   {
/* 439:718 */     return this.includeExternalDTDDeclarations;
/* 440:    */   }
/* 441:    */   
/* 442:    */   public void setIncludeExternalDTDDeclarations(boolean include)
/* 443:    */   {
/* 444:730 */     this.includeExternalDTDDeclarations = include;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public boolean isMergeAdjacentText()
/* 448:    */   {
/* 449:739 */     return this.mergeAdjacentText;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void setMergeAdjacentText(boolean mergeAdjacentText)
/* 453:    */   {
/* 454:750 */     this.mergeAdjacentText = mergeAdjacentText;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public boolean isStripWhitespaceText()
/* 458:    */   {
/* 459:760 */     return this.stripWhitespaceText;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setStripWhitespaceText(boolean stripWhitespaceText)
/* 463:    */   {
/* 464:771 */     this.stripWhitespaceText = stripWhitespaceText;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public boolean isIgnoreComments()
/* 468:    */   {
/* 469:780 */     return this.ignoreComments;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public void setIgnoreComments(boolean ignoreComments)
/* 473:    */   {
/* 474:790 */     this.ignoreComments = ignoreComments;
/* 475:    */   }
/* 476:    */   
/* 477:    */   protected void completeCurrentTextNode()
/* 478:    */   {
/* 479:801 */     if (this.stripWhitespaceText)
/* 480:    */     {
/* 481:802 */       boolean whitespace = true;
/* 482:    */       
/* 483:804 */       int i = 0;
/* 484:804 */       for (int size = this.textBuffer.length(); i < size; i++) {
/* 485:805 */         if (!Character.isWhitespace(this.textBuffer.charAt(i)))
/* 486:    */         {
/* 487:806 */           whitespace = false;
/* 488:    */           
/* 489:808 */           break;
/* 490:    */         }
/* 491:    */       }
/* 492:812 */       if (!whitespace) {
/* 493:813 */         this.currentElement.addText(this.textBuffer.toString());
/* 494:    */       }
/* 495:    */     }
/* 496:    */     else
/* 497:    */     {
/* 498:816 */       this.currentElement.addText(this.textBuffer.toString());
/* 499:    */     }
/* 500:819 */     this.textBuffer.setLength(0);
/* 501:820 */     this.textInTextBuffer = false;
/* 502:    */   }
/* 503:    */   
/* 504:    */   protected Document createDocument()
/* 505:    */   {
/* 506:829 */     String encoding = getEncoding();
/* 507:830 */     Document doc = this.documentFactory.createDocument(encoding);
/* 508:    */     
/* 509:    */ 
/* 510:833 */     doc.setEntityResolver(this.entityResolver);
/* 511:835 */     if (this.inputSource != null) {
/* 512:836 */       doc.setName(this.inputSource.getSystemId());
/* 513:    */     }
/* 514:839 */     return doc;
/* 515:    */   }
/* 516:    */   
/* 517:    */   private String getEncoding()
/* 518:    */   {
/* 519:843 */     if (this.locator == null) {
/* 520:844 */       return null;
/* 521:    */     }
/* 522:    */     try
/* 523:    */     {
/* 524:850 */       Method m = this.locator.getClass().getMethod("getEncoding", new Class[0]);
/* 525:853 */       if (m != null) {
/* 526:854 */         return (String)m.invoke(this.locator, null);
/* 527:    */       }
/* 528:    */     }
/* 529:    */     catch (Exception e) {}
/* 530:861 */     return null;
/* 531:    */   }
/* 532:    */   
/* 533:    */   protected boolean isIgnorableEntity(String name)
/* 534:    */   {
/* 535:873 */     return ("amp".equals(name)) || ("apos".equals(name)) || ("gt".equals(name)) || ("lt".equals(name)) || ("quot".equals(name));
/* 536:    */   }
/* 537:    */   
/* 538:    */   protected void addDeclaredNamespaces(Element element)
/* 539:    */   {
/* 540:886 */     Namespace elementNamespace = element.getNamespace();
/* 541:888 */     for (int size = this.namespaceStack.size(); this.declaredNamespaceIndex < size; this.declaredNamespaceIndex += 1)
/* 542:    */     {
/* 543:890 */       Namespace namespace = this.namespaceStack.getNamespace(this.declaredNamespaceIndex);
/* 544:    */       
/* 545:    */ 
/* 546:    */ 
/* 547:894 */       element.add(namespace);
/* 548:    */     }
/* 549:    */   }
/* 550:    */   
/* 551:    */   protected void addAttributes(Element element, Attributes attributes)
/* 552:    */   {
/* 553:911 */     boolean noNamespaceAttributes = false;
/* 554:913 */     if ((element instanceof AbstractElement))
/* 555:    */     {
/* 556:915 */       AbstractElement baseElement = (AbstractElement)element;
/* 557:916 */       baseElement.setAttributes(attributes, this.namespaceStack, noNamespaceAttributes);
/* 558:    */     }
/* 559:    */     else
/* 560:    */     {
/* 561:919 */       int size = attributes.getLength();
/* 562:921 */       for (int i = 0; i < size; i++)
/* 563:    */       {
/* 564:922 */         String attributeQName = attributes.getQName(i);
/* 565:924 */         if ((noNamespaceAttributes) || (!attributeQName.startsWith("xmlns")))
/* 566:    */         {
/* 567:926 */           String attributeURI = attributes.getURI(i);
/* 568:927 */           String attributeLocalName = attributes.getLocalName(i);
/* 569:928 */           String attributeValue = attributes.getValue(i);
/* 570:    */           
/* 571:930 */           QName qName = this.namespaceStack.getAttributeQName(attributeURI, attributeLocalName, attributeQName);
/* 572:    */           
/* 573:932 */           element.addAttribute(qName, attributeValue);
/* 574:    */         }
/* 575:    */       }
/* 576:    */     }
/* 577:    */   }
/* 578:    */   
/* 579:    */   protected void addDTDDeclaration(Object declaration)
/* 580:    */   {
/* 581:945 */     if (this.internalDTDDeclarations == null) {
/* 582:946 */       this.internalDTDDeclarations = new ArrayList();
/* 583:    */     }
/* 584:949 */     this.internalDTDDeclarations.add(declaration);
/* 585:    */   }
/* 586:    */   
/* 587:    */   protected void addExternalDTDDeclaration(Object declaration)
/* 588:    */   {
/* 589:959 */     if (this.externalDTDDeclarations == null) {
/* 590:960 */       this.externalDTDDeclarations = new ArrayList();
/* 591:    */     }
/* 592:963 */     this.externalDTDDeclarations.add(declaration);
/* 593:    */   }
/* 594:    */   
/* 595:    */   protected ElementStack createElementStack()
/* 596:    */   {
/* 597:967 */     return new ElementStack();
/* 598:    */   }
/* 599:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXContentHandler
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.dom4j.Attribute;
/*   9:    */ import org.dom4j.Branch;
/*  10:    */ import org.dom4j.CDATA;
/*  11:    */ import org.dom4j.CharacterData;
/*  12:    */ import org.dom4j.Comment;
/*  13:    */ import org.dom4j.Document;
/*  14:    */ import org.dom4j.DocumentType;
/*  15:    */ import org.dom4j.Element;
/*  16:    */ import org.dom4j.Entity;
/*  17:    */ import org.dom4j.Namespace;
/*  18:    */ import org.dom4j.Node;
/*  19:    */ import org.dom4j.ProcessingInstruction;
/*  20:    */ import org.dom4j.Text;
/*  21:    */ import org.dom4j.tree.NamespaceStack;
/*  22:    */ import org.xml.sax.Attributes;
/*  23:    */ import org.xml.sax.ContentHandler;
/*  24:    */ import org.xml.sax.DTDHandler;
/*  25:    */ import org.xml.sax.EntityResolver;
/*  26:    */ import org.xml.sax.ErrorHandler;
/*  27:    */ import org.xml.sax.InputSource;
/*  28:    */ import org.xml.sax.SAXException;
/*  29:    */ import org.xml.sax.SAXNotRecognizedException;
/*  30:    */ import org.xml.sax.SAXNotSupportedException;
/*  31:    */ import org.xml.sax.XMLReader;
/*  32:    */ import org.xml.sax.ext.LexicalHandler;
/*  33:    */ import org.xml.sax.helpers.AttributesImpl;
/*  34:    */ import org.xml.sax.helpers.LocatorImpl;
/*  35:    */ 
/*  36:    */ public class SAXWriter
/*  37:    */   implements XMLReader
/*  38:    */ {
/*  39: 54 */   protected static final String[] LEXICAL_HANDLER_NAMES = { "http://xml.org/sax/properties/lexical-handler", "http://xml.org/sax/handlers/LexicalHandler" };
/*  40:    */   protected static final String FEATURE_NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
/*  41:    */   protected static final String FEATURE_NAMESPACES = "http://xml.org/sax/features/namespaces";
/*  42:    */   private ContentHandler contentHandler;
/*  43:    */   private DTDHandler dtdHandler;
/*  44:    */   private EntityResolver entityResolver;
/*  45:    */   private ErrorHandler errorHandler;
/*  46:    */   private LexicalHandler lexicalHandler;
/*  47: 79 */   private AttributesImpl attributes = new AttributesImpl();
/*  48: 82 */   private Map features = new HashMap();
/*  49: 85 */   private Map properties = new HashMap();
/*  50:    */   private boolean declareNamespaceAttributes;
/*  51:    */   
/*  52:    */   public SAXWriter()
/*  53:    */   {
/*  54: 91 */     this.properties.put("http://xml.org/sax/features/namespace-prefixes", Boolean.FALSE);
/*  55: 92 */     this.properties.put("http://xml.org/sax/features/namespace-prefixes", Boolean.TRUE);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public SAXWriter(ContentHandler contentHandler)
/*  59:    */   {
/*  60: 96 */     this();
/*  61: 97 */     this.contentHandler = contentHandler;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public SAXWriter(ContentHandler contentHandler, LexicalHandler lexicalHandler)
/*  65:    */   {
/*  66:102 */     this();
/*  67:103 */     this.contentHandler = contentHandler;
/*  68:104 */     this.lexicalHandler = lexicalHandler;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public SAXWriter(ContentHandler contentHandler, LexicalHandler lexicalHandler, EntityResolver entityResolver)
/*  72:    */   {
/*  73:109 */     this();
/*  74:110 */     this.contentHandler = contentHandler;
/*  75:111 */     this.lexicalHandler = lexicalHandler;
/*  76:112 */     this.entityResolver = entityResolver;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void write(Node node)
/*  80:    */     throws SAXException
/*  81:    */   {
/*  82:125 */     int nodeType = node.getNodeType();
/*  83:127 */     switch (nodeType)
/*  84:    */     {
/*  85:    */     case 1: 
/*  86:129 */       write((Element)node);
/*  87:    */       
/*  88:131 */       break;
/*  89:    */     case 2: 
/*  90:134 */       write((Attribute)node);
/*  91:    */       
/*  92:136 */       break;
/*  93:    */     case 3: 
/*  94:139 */       write(node.getText());
/*  95:    */       
/*  96:141 */       break;
/*  97:    */     case 4: 
/*  98:144 */       write((CDATA)node);
/*  99:    */       
/* 100:146 */       break;
/* 101:    */     case 5: 
/* 102:149 */       write((Entity)node);
/* 103:    */       
/* 104:151 */       break;
/* 105:    */     case 7: 
/* 106:154 */       write((ProcessingInstruction)node);
/* 107:    */       
/* 108:156 */       break;
/* 109:    */     case 8: 
/* 110:159 */       write((Comment)node);
/* 111:    */       
/* 112:161 */       break;
/* 113:    */     case 9: 
/* 114:164 */       write((Document)node);
/* 115:    */       
/* 116:166 */       break;
/* 117:    */     case 10: 
/* 118:169 */       write((DocumentType)node);
/* 119:    */       
/* 120:171 */       break;
/* 121:    */     case 13: 
/* 122:    */       break;
/* 123:    */     case 6: 
/* 124:    */     case 11: 
/* 125:    */     case 12: 
/* 126:    */     default: 
/* 127:180 */       throw new SAXException("Invalid node type: " + node);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void write(Document document)
/* 132:    */     throws SAXException
/* 133:    */   {
/* 134:194 */     if (document != null)
/* 135:    */     {
/* 136:195 */       checkForNullHandlers();
/* 137:    */       
/* 138:197 */       documentLocator(document);
/* 139:198 */       startDocument();
/* 140:199 */       entityResolver(document);
/* 141:200 */       dtdHandler(document);
/* 142:    */       
/* 143:202 */       writeContent(document, new NamespaceStack());
/* 144:203 */       endDocument();
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void write(Element element)
/* 149:    */     throws SAXException
/* 150:    */   {
/* 151:217 */     write(element, new NamespaceStack());
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void writeOpen(Element element)
/* 155:    */     throws SAXException
/* 156:    */   {
/* 157:233 */     startElement(element, null);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void writeClose(Element element)
/* 161:    */     throws SAXException
/* 162:    */   {
/* 163:248 */     endElement(element);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void write(String text)
/* 167:    */     throws SAXException
/* 168:    */   {
/* 169:261 */     if (text != null)
/* 170:    */     {
/* 171:262 */       char[] chars = text.toCharArray();
/* 172:263 */       this.contentHandler.characters(chars, 0, chars.length);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void write(CDATA cdata)
/* 177:    */     throws SAXException
/* 178:    */   {
/* 179:277 */     String text = cdata.getText();
/* 180:279 */     if (this.lexicalHandler != null)
/* 181:    */     {
/* 182:280 */       this.lexicalHandler.startCDATA();
/* 183:281 */       write(text);
/* 184:282 */       this.lexicalHandler.endCDATA();
/* 185:    */     }
/* 186:    */     else
/* 187:    */     {
/* 188:284 */       write(text);
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void write(Comment comment)
/* 193:    */     throws SAXException
/* 194:    */   {
/* 195:298 */     if (this.lexicalHandler != null)
/* 196:    */     {
/* 197:299 */       String text = comment.getText();
/* 198:300 */       char[] chars = text.toCharArray();
/* 199:301 */       this.lexicalHandler.comment(chars, 0, chars.length);
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void write(Entity entity)
/* 204:    */     throws SAXException
/* 205:    */   {
/* 206:315 */     String text = entity.getText();
/* 207:317 */     if (this.lexicalHandler != null)
/* 208:    */     {
/* 209:318 */       String name = entity.getName();
/* 210:319 */       this.lexicalHandler.startEntity(name);
/* 211:320 */       write(text);
/* 212:321 */       this.lexicalHandler.endEntity(name);
/* 213:    */     }
/* 214:    */     else
/* 215:    */     {
/* 216:323 */       write(text);
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void write(ProcessingInstruction pi)
/* 221:    */     throws SAXException
/* 222:    */   {
/* 223:337 */     String target = pi.getTarget();
/* 224:338 */     String text = pi.getText();
/* 225:339 */     this.contentHandler.processingInstruction(target, text);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean isDeclareNamespaceAttributes()
/* 229:    */   {
/* 230:351 */     return this.declareNamespaceAttributes;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setDeclareNamespaceAttributes(boolean declareNamespaceAttrs)
/* 234:    */   {
/* 235:363 */     this.declareNamespaceAttributes = declareNamespaceAttrs;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public ContentHandler getContentHandler()
/* 239:    */   {
/* 240:376 */     return this.contentHandler;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setContentHandler(ContentHandler contentHandler)
/* 244:    */   {
/* 245:387 */     this.contentHandler = contentHandler;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public DTDHandler getDTDHandler()
/* 249:    */   {
/* 250:396 */     return this.dtdHandler;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setDTDHandler(DTDHandler handler)
/* 254:    */   {
/* 255:406 */     this.dtdHandler = handler;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public ErrorHandler getErrorHandler()
/* 259:    */   {
/* 260:415 */     return this.errorHandler;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setErrorHandler(ErrorHandler errorHandler)
/* 264:    */   {
/* 265:425 */     this.errorHandler = errorHandler;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public EntityResolver getEntityResolver()
/* 269:    */   {
/* 270:435 */     return this.entityResolver;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setEntityResolver(EntityResolver entityResolver)
/* 274:    */   {
/* 275:445 */     this.entityResolver = entityResolver;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public LexicalHandler getLexicalHandler()
/* 279:    */   {
/* 280:455 */     return this.lexicalHandler;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setLexicalHandler(LexicalHandler lexicalHandler)
/* 284:    */   {
/* 285:465 */     this.lexicalHandler = lexicalHandler;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setXMLReader(XMLReader xmlReader)
/* 289:    */   {
/* 290:475 */     setContentHandler(xmlReader.getContentHandler());
/* 291:476 */     setDTDHandler(xmlReader.getDTDHandler());
/* 292:477 */     setEntityResolver(xmlReader.getEntityResolver());
/* 293:478 */     setErrorHandler(xmlReader.getErrorHandler());
/* 294:    */   }
/* 295:    */   
/* 296:    */   public boolean getFeature(String name)
/* 297:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 298:    */   {
/* 299:496 */     Boolean answer = (Boolean)this.features.get(name);
/* 300:    */     
/* 301:498 */     return (answer != null) && (answer.booleanValue());
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setFeature(String name, boolean value)
/* 305:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 306:    */   {
/* 307:517 */     if ("http://xml.org/sax/features/namespace-prefixes".equals(name))
/* 308:    */     {
/* 309:518 */       setDeclareNamespaceAttributes(value);
/* 310:    */     }
/* 311:519 */     else if (("http://xml.org/sax/features/namespace-prefixes".equals(name)) && 
/* 312:520 */       (!value))
/* 313:    */     {
/* 314:521 */       String msg = "Namespace feature is always supported in dom4j";
/* 315:522 */       throw new SAXNotSupportedException(msg);
/* 316:    */     }
/* 317:526 */     this.features.put(name, value ? Boolean.TRUE : Boolean.FALSE);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void setProperty(String name, Object value)
/* 321:    */   {
/* 322:538 */     for (int i = 0; i < LEXICAL_HANDLER_NAMES.length; i++) {
/* 323:539 */       if (LEXICAL_HANDLER_NAMES[i].equals(name))
/* 324:    */       {
/* 325:540 */         setLexicalHandler((LexicalHandler)value);
/* 326:    */         
/* 327:542 */         return;
/* 328:    */       }
/* 329:    */     }
/* 330:546 */     this.properties.put(name, value);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public Object getProperty(String name)
/* 334:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 335:    */   {
/* 336:564 */     for (int i = 0; i < LEXICAL_HANDLER_NAMES.length; i++) {
/* 337:565 */       if (LEXICAL_HANDLER_NAMES[i].equals(name)) {
/* 338:566 */         return getLexicalHandler();
/* 339:    */       }
/* 340:    */     }
/* 341:570 */     return this.properties.get(name);
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void parse(String systemId)
/* 345:    */     throws SAXNotSupportedException
/* 346:    */   {
/* 347:583 */     throw new SAXNotSupportedException("This XMLReader can only accept <dom4j> InputSource objects");
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void parse(InputSource input)
/* 351:    */     throws SAXException
/* 352:    */   {
/* 353:600 */     if ((input instanceof DocumentInputSource))
/* 354:    */     {
/* 355:601 */       DocumentInputSource documentInput = (DocumentInputSource)input;
/* 356:602 */       Document document = documentInput.getDocument();
/* 357:603 */       write(document);
/* 358:    */     }
/* 359:    */     else
/* 360:    */     {
/* 361:605 */       throw new SAXNotSupportedException("This XMLReader can only accept <dom4j> InputSource objects");
/* 362:    */     }
/* 363:    */   }
/* 364:    */   
/* 365:    */   protected void writeContent(Branch branch, NamespaceStack namespaceStack)
/* 366:    */     throws SAXException
/* 367:    */   {
/* 368:615 */     for (Iterator iter = branch.nodeIterator(); iter.hasNext();)
/* 369:    */     {
/* 370:616 */       Object object = iter.next();
/* 371:618 */       if ((object instanceof Element)) {
/* 372:619 */         write((Element)object, namespaceStack);
/* 373:620 */       } else if ((object instanceof CharacterData))
/* 374:    */       {
/* 375:621 */         if ((object instanceof Text))
/* 376:    */         {
/* 377:622 */           Text text = (Text)object;
/* 378:623 */           write(text.getText());
/* 379:    */         }
/* 380:624 */         else if ((object instanceof CDATA))
/* 381:    */         {
/* 382:625 */           write((CDATA)object);
/* 383:    */         }
/* 384:626 */         else if ((object instanceof Comment))
/* 385:    */         {
/* 386:627 */           write((Comment)object);
/* 387:    */         }
/* 388:    */         else
/* 389:    */         {
/* 390:629 */           throw new SAXException("Invalid Node in DOM4J content: " + object + " of type: " + object.getClass());
/* 391:    */         }
/* 392:    */       }
/* 393:632 */       else if ((object instanceof String)) {
/* 394:633 */         write((String)object);
/* 395:634 */       } else if ((object instanceof Entity)) {
/* 396:635 */         write((Entity)object);
/* 397:636 */       } else if ((object instanceof ProcessingInstruction)) {
/* 398:637 */         write((ProcessingInstruction)object);
/* 399:638 */       } else if ((object instanceof Namespace)) {
/* 400:639 */         write((Namespace)object);
/* 401:    */       } else {
/* 402:641 */         throw new SAXException("Invalid Node in DOM4J content: " + object);
/* 403:    */       }
/* 404:    */     }
/* 405:    */   }
/* 406:    */   
/* 407:    */   protected void documentLocator(Document document)
/* 408:    */     throws SAXException
/* 409:    */   {
/* 410:661 */     LocatorImpl locator = new LocatorImpl();
/* 411:    */     
/* 412:663 */     String publicID = null;
/* 413:664 */     String systemID = null;
/* 414:665 */     DocumentType docType = document.getDocType();
/* 415:667 */     if (docType != null)
/* 416:    */     {
/* 417:668 */       publicID = docType.getPublicID();
/* 418:669 */       systemID = docType.getSystemID();
/* 419:    */     }
/* 420:672 */     if (publicID != null) {
/* 421:673 */       locator.setPublicId(publicID);
/* 422:    */     }
/* 423:676 */     if (systemID != null) {
/* 424:677 */       locator.setSystemId(systemID);
/* 425:    */     }
/* 426:680 */     locator.setLineNumber(-1);
/* 427:681 */     locator.setColumnNumber(-1);
/* 428:    */     
/* 429:683 */     this.contentHandler.setDocumentLocator(locator);
/* 430:    */   }
/* 431:    */   
/* 432:    */   protected void entityResolver(Document document)
/* 433:    */     throws SAXException
/* 434:    */   {
/* 435:687 */     if (this.entityResolver != null)
/* 436:    */     {
/* 437:688 */       DocumentType docType = document.getDocType();
/* 438:690 */       if (docType != null)
/* 439:    */       {
/* 440:691 */         String publicID = docType.getPublicID();
/* 441:692 */         String systemID = docType.getSystemID();
/* 442:694 */         if ((publicID != null) || (systemID != null)) {
/* 443:    */           try
/* 444:    */           {
/* 445:696 */             this.entityResolver.resolveEntity(publicID, systemID);
/* 446:    */           }
/* 447:    */           catch (IOException e)
/* 448:    */           {
/* 449:698 */             throw new SAXException("Could not resolve publicID: " + publicID + " systemID: " + systemID, e);
/* 450:    */           }
/* 451:    */         }
/* 452:    */       }
/* 453:    */     }
/* 454:    */   }
/* 455:    */   
/* 456:    */   protected void dtdHandler(Document document)
/* 457:    */     throws SAXException
/* 458:    */   {}
/* 459:    */   
/* 460:    */   protected void startDocument()
/* 461:    */     throws SAXException
/* 462:    */   {
/* 463:720 */     this.contentHandler.startDocument();
/* 464:    */   }
/* 465:    */   
/* 466:    */   protected void endDocument()
/* 467:    */     throws SAXException
/* 468:    */   {
/* 469:724 */     this.contentHandler.endDocument();
/* 470:    */   }
/* 471:    */   
/* 472:    */   protected void write(Element element, NamespaceStack namespaceStack)
/* 473:    */     throws SAXException
/* 474:    */   {
/* 475:729 */     int stackSize = namespaceStack.size();
/* 476:730 */     AttributesImpl namespaceAttributes = startPrefixMapping(element, namespaceStack);
/* 477:    */     
/* 478:732 */     startElement(element, namespaceAttributes);
/* 479:733 */     writeContent(element, namespaceStack);
/* 480:734 */     endElement(element);
/* 481:735 */     endPrefixMapping(namespaceStack, stackSize);
/* 482:    */   }
/* 483:    */   
/* 484:    */   protected AttributesImpl startPrefixMapping(Element element, NamespaceStack namespaceStack)
/* 485:    */     throws SAXException
/* 486:    */   {
/* 487:754 */     AttributesImpl namespaceAttributes = null;
/* 488:    */     
/* 489:    */ 
/* 490:757 */     Namespace elementNamespace = element.getNamespace();
/* 491:759 */     if ((elementNamespace != null) && (!isIgnoreableNamespace(elementNamespace, namespaceStack)))
/* 492:    */     {
/* 493:761 */       namespaceStack.push(elementNamespace);
/* 494:762 */       this.contentHandler.startPrefixMapping(elementNamespace.getPrefix(), elementNamespace.getURI());
/* 495:    */       
/* 496:764 */       namespaceAttributes = addNamespaceAttribute(namespaceAttributes, elementNamespace);
/* 497:    */     }
/* 498:768 */     List declaredNamespaces = element.declaredNamespaces();
/* 499:    */     
/* 500:770 */     int i = 0;
/* 501:770 */     for (int size = declaredNamespaces.size(); i < size; i++)
/* 502:    */     {
/* 503:771 */       Namespace namespace = (Namespace)declaredNamespaces.get(i);
/* 504:773 */       if (!isIgnoreableNamespace(namespace, namespaceStack))
/* 505:    */       {
/* 506:774 */         namespaceStack.push(namespace);
/* 507:775 */         this.contentHandler.startPrefixMapping(namespace.getPrefix(), namespace.getURI());
/* 508:    */         
/* 509:777 */         namespaceAttributes = addNamespaceAttribute(namespaceAttributes, namespace);
/* 510:    */       }
/* 511:    */     }
/* 512:782 */     return namespaceAttributes;
/* 513:    */   }
/* 514:    */   
/* 515:    */   protected void endPrefixMapping(NamespaceStack stack, int stackSize)
/* 516:    */     throws SAXException
/* 517:    */   {
/* 518:799 */     while (stack.size() > stackSize)
/* 519:    */     {
/* 520:800 */       Namespace namespace = stack.pop();
/* 521:802 */       if (namespace != null) {
/* 522:803 */         this.contentHandler.endPrefixMapping(namespace.getPrefix());
/* 523:    */       }
/* 524:    */     }
/* 525:    */   }
/* 526:    */   
/* 527:    */   protected void startElement(Element element, AttributesImpl namespaceAttributes)
/* 528:    */     throws SAXException
/* 529:    */   {
/* 530:810 */     this.contentHandler.startElement(element.getNamespaceURI(), element.getName(), element.getQualifiedName(), createAttributes(element, namespaceAttributes));
/* 531:    */   }
/* 532:    */   
/* 533:    */   protected void endElement(Element element)
/* 534:    */     throws SAXException
/* 535:    */   {
/* 536:816 */     this.contentHandler.endElement(element.getNamespaceURI(), element.getName(), element.getQualifiedName());
/* 537:    */   }
/* 538:    */   
/* 539:    */   protected Attributes createAttributes(Element element, Attributes namespaceAttributes)
/* 540:    */     throws SAXException
/* 541:    */   {
/* 542:822 */     this.attributes.clear();
/* 543:824 */     if (namespaceAttributes != null) {
/* 544:825 */       this.attributes.setAttributes(namespaceAttributes);
/* 545:    */     }
/* 546:828 */     for (Iterator iter = element.attributeIterator(); iter.hasNext();)
/* 547:    */     {
/* 548:829 */       Attribute attribute = (Attribute)iter.next();
/* 549:830 */       this.attributes.addAttribute(attribute.getNamespaceURI(), attribute.getName(), attribute.getQualifiedName(), "CDATA", attribute.getValue());
/* 550:    */     }
/* 551:835 */     return this.attributes;
/* 552:    */   }
/* 553:    */   
/* 554:    */   protected AttributesImpl addNamespaceAttribute(AttributesImpl attrs, Namespace namespace)
/* 555:    */   {
/* 556:852 */     if (this.declareNamespaceAttributes)
/* 557:    */     {
/* 558:853 */       if (attrs == null) {
/* 559:854 */         attrs = new AttributesImpl();
/* 560:    */       }
/* 561:857 */       String prefix = namespace.getPrefix();
/* 562:858 */       String qualifiedName = "xmlns";
/* 563:860 */       if ((prefix != null) && (prefix.length() > 0)) {
/* 564:861 */         qualifiedName = "xmlns:" + prefix;
/* 565:    */       }
/* 566:864 */       String uri = "";
/* 567:865 */       String localName = prefix;
/* 568:866 */       String type = "CDATA";
/* 569:867 */       String value = namespace.getURI();
/* 570:    */       
/* 571:869 */       attrs.addAttribute(uri, localName, qualifiedName, type, value);
/* 572:    */     }
/* 573:872 */     return attrs;
/* 574:    */   }
/* 575:    */   
/* 576:    */   protected boolean isIgnoreableNamespace(Namespace namespace, NamespaceStack namespaceStack)
/* 577:    */   {
/* 578:889 */     if ((namespace.equals(Namespace.NO_NAMESPACE)) || (namespace.equals(Namespace.XML_NAMESPACE))) {
/* 579:891 */       return true;
/* 580:    */     }
/* 581:894 */     String uri = namespace.getURI();
/* 582:896 */     if ((uri == null) || (uri.length() <= 0)) {
/* 583:897 */       return true;
/* 584:    */     }
/* 585:900 */     return namespaceStack.contains(namespace);
/* 586:    */   }
/* 587:    */   
/* 588:    */   protected void checkForNullHandlers() {}
/* 589:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXWriter
 * JD-Core Version:    0.7.0.1
 */
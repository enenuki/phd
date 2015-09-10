/*   1:    */ package org.hibernate.tuple;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.dom4j.Attribute;
/*  10:    */ import org.dom4j.Branch;
/*  11:    */ import org.dom4j.CDATA;
/*  12:    */ import org.dom4j.Comment;
/*  13:    */ import org.dom4j.Document;
/*  14:    */ import org.dom4j.Element;
/*  15:    */ import org.dom4j.Entity;
/*  16:    */ import org.dom4j.InvalidXPathException;
/*  17:    */ import org.dom4j.Namespace;
/*  18:    */ import org.dom4j.Node;
/*  19:    */ import org.dom4j.ProcessingInstruction;
/*  20:    */ import org.dom4j.QName;
/*  21:    */ import org.dom4j.Text;
/*  22:    */ import org.dom4j.Visitor;
/*  23:    */ import org.dom4j.XPath;
/*  24:    */ 
/*  25:    */ public class ElementWrapper
/*  26:    */   implements Element, Serializable
/*  27:    */ {
/*  28:    */   private Element element;
/*  29:    */   private Element parent;
/*  30:    */   
/*  31:    */   public Element getElement()
/*  32:    */   {
/*  33: 61 */     return this.element;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ElementWrapper(Element element)
/*  37:    */   {
/*  38: 65 */     this.element = element;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public QName getQName()
/*  42:    */   {
/*  43: 69 */     return this.element.getQName();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public QName getQName(String s)
/*  47:    */   {
/*  48: 73 */     return this.element.getQName(s);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setQName(QName qName)
/*  52:    */   {
/*  53: 77 */     this.element.setQName(qName);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Namespace getNamespace()
/*  57:    */   {
/*  58: 81 */     return this.element.getNamespace();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Namespace getNamespaceForPrefix(String s)
/*  62:    */   {
/*  63: 85 */     return this.element.getNamespaceForPrefix(s);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Namespace getNamespaceForURI(String s)
/*  67:    */   {
/*  68: 89 */     return this.element.getNamespaceForURI(s);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List getNamespacesForURI(String s)
/*  72:    */   {
/*  73: 93 */     return this.element.getNamespacesForURI(s);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getNamespacePrefix()
/*  77:    */   {
/*  78: 97 */     return this.element.getNamespacePrefix();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getNamespaceURI()
/*  82:    */   {
/*  83:101 */     return this.element.getNamespaceURI();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getQualifiedName()
/*  87:    */   {
/*  88:105 */     return this.element.getQualifiedName();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public List additionalNamespaces()
/*  92:    */   {
/*  93:109 */     return this.element.additionalNamespaces();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public List declaredNamespaces()
/*  97:    */   {
/*  98:113 */     return this.element.declaredNamespaces();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Element addAttribute(String attrName, String text)
/* 102:    */   {
/* 103:117 */     return this.element.addAttribute(attrName, text);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Element addAttribute(QName attrName, String text)
/* 107:    */   {
/* 108:121 */     return this.element.addAttribute(attrName, text);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Element addComment(String text)
/* 112:    */   {
/* 113:125 */     return this.element.addComment(text);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Element addCDATA(String text)
/* 117:    */   {
/* 118:129 */     return this.element.addCDATA(text);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Element addEntity(String name, String text)
/* 122:    */   {
/* 123:133 */     return this.element.addEntity(name, text);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Element addNamespace(String prefix, String uri)
/* 127:    */   {
/* 128:137 */     return this.element.addNamespace(prefix, uri);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Element addProcessingInstruction(String target, String text)
/* 132:    */   {
/* 133:141 */     return this.element.addProcessingInstruction(target, text);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Element addProcessingInstruction(String target, Map data)
/* 137:    */   {
/* 138:145 */     return this.element.addProcessingInstruction(target, data);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Element addText(String text)
/* 142:    */   {
/* 143:149 */     return this.element.addText(text);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void add(Attribute attribute)
/* 147:    */   {
/* 148:153 */     this.element.add(attribute);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void add(CDATA cdata)
/* 152:    */   {
/* 153:157 */     this.element.add(cdata);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void add(Entity entity)
/* 157:    */   {
/* 158:161 */     this.element.add(entity);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void add(Text text)
/* 162:    */   {
/* 163:165 */     this.element.add(text);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void add(Namespace namespace)
/* 167:    */   {
/* 168:169 */     this.element.add(namespace);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean remove(Attribute attribute)
/* 172:    */   {
/* 173:173 */     return this.element.remove(attribute);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean remove(CDATA cdata)
/* 177:    */   {
/* 178:177 */     return this.element.remove(cdata);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean remove(Entity entity)
/* 182:    */   {
/* 183:181 */     return this.element.remove(entity);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean remove(Namespace namespace)
/* 187:    */   {
/* 188:185 */     return this.element.remove(namespace);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean remove(Text text)
/* 192:    */   {
/* 193:189 */     return this.element.remove(text);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean supportsParent()
/* 197:    */   {
/* 198:193 */     return this.element.supportsParent();
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Element getParent()
/* 202:    */   {
/* 203:197 */     return this.parent == null ? this.element.getParent() : this.parent;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setParent(Element parent)
/* 207:    */   {
/* 208:201 */     this.element.setParent(parent);
/* 209:202 */     this.parent = parent;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Document getDocument()
/* 213:    */   {
/* 214:206 */     return this.element.getDocument();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setDocument(Document document)
/* 218:    */   {
/* 219:210 */     this.element.setDocument(document);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public boolean isReadOnly()
/* 223:    */   {
/* 224:214 */     return this.element.isReadOnly();
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean hasContent()
/* 228:    */   {
/* 229:218 */     return this.element.hasContent();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String getName()
/* 233:    */   {
/* 234:222 */     return this.element.getName();
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setName(String name)
/* 238:    */   {
/* 239:226 */     this.element.setName(name);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String getText()
/* 243:    */   {
/* 244:230 */     return this.element.getText();
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setText(String text)
/* 248:    */   {
/* 249:234 */     this.element.setText(text);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public String getTextTrim()
/* 253:    */   {
/* 254:238 */     return this.element.getTextTrim();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public String getStringValue()
/* 258:    */   {
/* 259:242 */     return this.element.getStringValue();
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String getPath()
/* 263:    */   {
/* 264:246 */     return this.element.getPath();
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getPath(Element element)
/* 268:    */   {
/* 269:250 */     return element.getPath(element);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String getUniquePath()
/* 273:    */   {
/* 274:254 */     return this.element.getUniquePath();
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String getUniquePath(Element element)
/* 278:    */   {
/* 279:258 */     return element.getUniquePath(element);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String asXML()
/* 283:    */   {
/* 284:262 */     return this.element.asXML();
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void write(Writer writer)
/* 288:    */     throws IOException
/* 289:    */   {
/* 290:266 */     this.element.write(writer);
/* 291:    */   }
/* 292:    */   
/* 293:    */   public short getNodeType()
/* 294:    */   {
/* 295:270 */     return this.element.getNodeType();
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String getNodeTypeName()
/* 299:    */   {
/* 300:274 */     return this.element.getNodeTypeName();
/* 301:    */   }
/* 302:    */   
/* 303:    */   public Node detach()
/* 304:    */   {
/* 305:278 */     if (this.parent != null)
/* 306:    */     {
/* 307:279 */       this.parent.remove(this);
/* 308:280 */       this.parent = null;
/* 309:    */     }
/* 310:282 */     return this.element.detach();
/* 311:    */   }
/* 312:    */   
/* 313:    */   public List selectNodes(String xpath)
/* 314:    */   {
/* 315:286 */     return this.element.selectNodes(xpath);
/* 316:    */   }
/* 317:    */   
/* 318:    */   public Object selectObject(String xpath)
/* 319:    */   {
/* 320:290 */     return this.element.selectObject(xpath);
/* 321:    */   }
/* 322:    */   
/* 323:    */   public List selectNodes(String xpath, String comparison)
/* 324:    */   {
/* 325:294 */     return this.element.selectNodes(xpath, comparison);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public List selectNodes(String xpath, String comparison, boolean removeDups)
/* 329:    */   {
/* 330:298 */     return this.element.selectNodes(xpath, comparison, removeDups);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public Node selectSingleNode(String xpath)
/* 334:    */   {
/* 335:302 */     return this.element.selectSingleNode(xpath);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String valueOf(String xpath)
/* 339:    */   {
/* 340:306 */     return this.element.valueOf(xpath);
/* 341:    */   }
/* 342:    */   
/* 343:    */   public Number numberValueOf(String xpath)
/* 344:    */   {
/* 345:310 */     return this.element.numberValueOf(xpath);
/* 346:    */   }
/* 347:    */   
/* 348:    */   public boolean matches(String xpath)
/* 349:    */   {
/* 350:314 */     return this.element.matches(xpath);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public XPath createXPath(String xpath)
/* 354:    */     throws InvalidXPathException
/* 355:    */   {
/* 356:318 */     return this.element.createXPath(xpath);
/* 357:    */   }
/* 358:    */   
/* 359:    */   public Node asXPathResult(Element element)
/* 360:    */   {
/* 361:322 */     return element.asXPathResult(element);
/* 362:    */   }
/* 363:    */   
/* 364:    */   public void accept(Visitor visitor)
/* 365:    */   {
/* 366:326 */     this.element.accept(visitor);
/* 367:    */   }
/* 368:    */   
/* 369:    */   public Object clone()
/* 370:    */   {
/* 371:330 */     return this.element.clone();
/* 372:    */   }
/* 373:    */   
/* 374:    */   public Object getData()
/* 375:    */   {
/* 376:334 */     return this.element.getData();
/* 377:    */   }
/* 378:    */   
/* 379:    */   public void setData(Object data)
/* 380:    */   {
/* 381:338 */     this.element.setData(data);
/* 382:    */   }
/* 383:    */   
/* 384:    */   public List attributes()
/* 385:    */   {
/* 386:342 */     return this.element.attributes();
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void setAttributes(List list)
/* 390:    */   {
/* 391:346 */     this.element.setAttributes(list);
/* 392:    */   }
/* 393:    */   
/* 394:    */   public int attributeCount()
/* 395:    */   {
/* 396:350 */     return this.element.attributeCount();
/* 397:    */   }
/* 398:    */   
/* 399:    */   public Iterator attributeIterator()
/* 400:    */   {
/* 401:354 */     return this.element.attributeIterator();
/* 402:    */   }
/* 403:    */   
/* 404:    */   public Attribute attribute(int i)
/* 405:    */   {
/* 406:358 */     return this.element.attribute(i);
/* 407:    */   }
/* 408:    */   
/* 409:    */   public Attribute attribute(String name)
/* 410:    */   {
/* 411:362 */     return this.element.attribute(name);
/* 412:    */   }
/* 413:    */   
/* 414:    */   public Attribute attribute(QName qName)
/* 415:    */   {
/* 416:366 */     return this.element.attribute(qName);
/* 417:    */   }
/* 418:    */   
/* 419:    */   public String attributeValue(String name)
/* 420:    */   {
/* 421:370 */     return this.element.attributeValue(name);
/* 422:    */   }
/* 423:    */   
/* 424:    */   public String attributeValue(String name, String defaultValue)
/* 425:    */   {
/* 426:374 */     return this.element.attributeValue(name, defaultValue);
/* 427:    */   }
/* 428:    */   
/* 429:    */   public String attributeValue(QName qName)
/* 430:    */   {
/* 431:378 */     return this.element.attributeValue(qName);
/* 432:    */   }
/* 433:    */   
/* 434:    */   public String attributeValue(QName qName, String defaultValue)
/* 435:    */   {
/* 436:382 */     return this.element.attributeValue(qName, defaultValue);
/* 437:    */   }
/* 438:    */   
/* 439:    */   /**
/* 440:    */    * @deprecated
/* 441:    */    */
/* 442:    */   public void setAttributeValue(String name, String value)
/* 443:    */   {
/* 444:389 */     this.element.setAttributeValue(name, value);
/* 445:    */   }
/* 446:    */   
/* 447:    */   /**
/* 448:    */    * @deprecated
/* 449:    */    */
/* 450:    */   public void setAttributeValue(QName qName, String value)
/* 451:    */   {
/* 452:396 */     this.element.setAttributeValue(qName, value);
/* 453:    */   }
/* 454:    */   
/* 455:    */   public Element element(String name)
/* 456:    */   {
/* 457:400 */     return this.element.element(name);
/* 458:    */   }
/* 459:    */   
/* 460:    */   public Element element(QName qName)
/* 461:    */   {
/* 462:404 */     return this.element.element(qName);
/* 463:    */   }
/* 464:    */   
/* 465:    */   public List elements()
/* 466:    */   {
/* 467:408 */     return this.element.elements();
/* 468:    */   }
/* 469:    */   
/* 470:    */   public List elements(String name)
/* 471:    */   {
/* 472:412 */     return this.element.elements(name);
/* 473:    */   }
/* 474:    */   
/* 475:    */   public List elements(QName qName)
/* 476:    */   {
/* 477:416 */     return this.element.elements(qName);
/* 478:    */   }
/* 479:    */   
/* 480:    */   public Iterator elementIterator()
/* 481:    */   {
/* 482:420 */     return this.element.elementIterator();
/* 483:    */   }
/* 484:    */   
/* 485:    */   public Iterator elementIterator(String name)
/* 486:    */   {
/* 487:424 */     return this.element.elementIterator(name);
/* 488:    */   }
/* 489:    */   
/* 490:    */   public Iterator elementIterator(QName qName)
/* 491:    */   {
/* 492:429 */     return this.element.elementIterator(qName);
/* 493:    */   }
/* 494:    */   
/* 495:    */   public boolean isRootElement()
/* 496:    */   {
/* 497:433 */     return this.element.isRootElement();
/* 498:    */   }
/* 499:    */   
/* 500:    */   public boolean hasMixedContent()
/* 501:    */   {
/* 502:437 */     return this.element.hasMixedContent();
/* 503:    */   }
/* 504:    */   
/* 505:    */   public boolean isTextOnly()
/* 506:    */   {
/* 507:441 */     return this.element.isTextOnly();
/* 508:    */   }
/* 509:    */   
/* 510:    */   public void appendAttributes(Element element)
/* 511:    */   {
/* 512:445 */     element.appendAttributes(element);
/* 513:    */   }
/* 514:    */   
/* 515:    */   public Element createCopy()
/* 516:    */   {
/* 517:449 */     return this.element.createCopy();
/* 518:    */   }
/* 519:    */   
/* 520:    */   public Element createCopy(String name)
/* 521:    */   {
/* 522:453 */     return this.element.createCopy(name);
/* 523:    */   }
/* 524:    */   
/* 525:    */   public Element createCopy(QName qName)
/* 526:    */   {
/* 527:457 */     return this.element.createCopy(qName);
/* 528:    */   }
/* 529:    */   
/* 530:    */   public String elementText(String name)
/* 531:    */   {
/* 532:461 */     return this.element.elementText(name);
/* 533:    */   }
/* 534:    */   
/* 535:    */   public String elementText(QName qName)
/* 536:    */   {
/* 537:465 */     return this.element.elementText(qName);
/* 538:    */   }
/* 539:    */   
/* 540:    */   public String elementTextTrim(String name)
/* 541:    */   {
/* 542:469 */     return this.element.elementTextTrim(name);
/* 543:    */   }
/* 544:    */   
/* 545:    */   public String elementTextTrim(QName qName)
/* 546:    */   {
/* 547:473 */     return this.element.elementTextTrim(qName);
/* 548:    */   }
/* 549:    */   
/* 550:    */   public Node getXPathResult(int i)
/* 551:    */   {
/* 552:477 */     return this.element.getXPathResult(i);
/* 553:    */   }
/* 554:    */   
/* 555:    */   public Node node(int i)
/* 556:    */   {
/* 557:481 */     return this.element.node(i);
/* 558:    */   }
/* 559:    */   
/* 560:    */   public int indexOf(Node node)
/* 561:    */   {
/* 562:485 */     return this.element.indexOf(node);
/* 563:    */   }
/* 564:    */   
/* 565:    */   public int nodeCount()
/* 566:    */   {
/* 567:489 */     return this.element.nodeCount();
/* 568:    */   }
/* 569:    */   
/* 570:    */   public Element elementByID(String id)
/* 571:    */   {
/* 572:493 */     return this.element.elementByID(id);
/* 573:    */   }
/* 574:    */   
/* 575:    */   public List content()
/* 576:    */   {
/* 577:497 */     return this.element.content();
/* 578:    */   }
/* 579:    */   
/* 580:    */   public Iterator nodeIterator()
/* 581:    */   {
/* 582:501 */     return this.element.nodeIterator();
/* 583:    */   }
/* 584:    */   
/* 585:    */   public void setContent(List list)
/* 586:    */   {
/* 587:505 */     this.element.setContent(list);
/* 588:    */   }
/* 589:    */   
/* 590:    */   public void appendContent(Branch branch)
/* 591:    */   {
/* 592:509 */     this.element.appendContent(branch);
/* 593:    */   }
/* 594:    */   
/* 595:    */   public void clearContent()
/* 596:    */   {
/* 597:513 */     this.element.clearContent();
/* 598:    */   }
/* 599:    */   
/* 600:    */   public List processingInstructions()
/* 601:    */   {
/* 602:517 */     return this.element.processingInstructions();
/* 603:    */   }
/* 604:    */   
/* 605:    */   public List processingInstructions(String name)
/* 606:    */   {
/* 607:521 */     return this.element.processingInstructions(name);
/* 608:    */   }
/* 609:    */   
/* 610:    */   public ProcessingInstruction processingInstruction(String name)
/* 611:    */   {
/* 612:525 */     return this.element.processingInstruction(name);
/* 613:    */   }
/* 614:    */   
/* 615:    */   public void setProcessingInstructions(List list)
/* 616:    */   {
/* 617:529 */     this.element.setProcessingInstructions(list);
/* 618:    */   }
/* 619:    */   
/* 620:    */   public Element addElement(String name)
/* 621:    */   {
/* 622:533 */     return this.element.addElement(name);
/* 623:    */   }
/* 624:    */   
/* 625:    */   public Element addElement(QName qName)
/* 626:    */   {
/* 627:537 */     return this.element.addElement(qName);
/* 628:    */   }
/* 629:    */   
/* 630:    */   public Element addElement(String name, String text)
/* 631:    */   {
/* 632:541 */     return this.element.addElement(name, text);
/* 633:    */   }
/* 634:    */   
/* 635:    */   public boolean removeProcessingInstruction(String name)
/* 636:    */   {
/* 637:546 */     return this.element.removeProcessingInstruction(name);
/* 638:    */   }
/* 639:    */   
/* 640:    */   public void add(Node node)
/* 641:    */   {
/* 642:550 */     this.element.add(node);
/* 643:    */   }
/* 644:    */   
/* 645:    */   public void add(Comment comment)
/* 646:    */   {
/* 647:554 */     this.element.add(comment);
/* 648:    */   }
/* 649:    */   
/* 650:    */   public void add(Element element)
/* 651:    */   {
/* 652:558 */     element.add(element);
/* 653:    */   }
/* 654:    */   
/* 655:    */   public void add(ProcessingInstruction processingInstruction)
/* 656:    */   {
/* 657:562 */     this.element.add(processingInstruction);
/* 658:    */   }
/* 659:    */   
/* 660:    */   public boolean remove(Node node)
/* 661:    */   {
/* 662:566 */     return this.element.remove(node);
/* 663:    */   }
/* 664:    */   
/* 665:    */   public boolean remove(Comment comment)
/* 666:    */   {
/* 667:570 */     return this.element.remove(comment);
/* 668:    */   }
/* 669:    */   
/* 670:    */   public boolean remove(Element element)
/* 671:    */   {
/* 672:574 */     return element.remove(element);
/* 673:    */   }
/* 674:    */   
/* 675:    */   public boolean remove(ProcessingInstruction processingInstruction)
/* 676:    */   {
/* 677:578 */     return this.element.remove(processingInstruction);
/* 678:    */   }
/* 679:    */   
/* 680:    */   public void normalize()
/* 681:    */   {
/* 682:582 */     this.element.normalize();
/* 683:    */   }
/* 684:    */   
/* 685:    */   public boolean equals(Object other)
/* 686:    */   {
/* 687:586 */     return this.element.equals(other);
/* 688:    */   }
/* 689:    */   
/* 690:    */   public int hashCode()
/* 691:    */   {
/* 692:590 */     return this.element.hashCode();
/* 693:    */   }
/* 694:    */   
/* 695:    */   public String toString()
/* 696:    */   {
/* 697:594 */     return this.element.toString();
/* 698:    */   }
/* 699:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.ElementWrapper
 * JD-Core Version:    0.7.0.1
 */
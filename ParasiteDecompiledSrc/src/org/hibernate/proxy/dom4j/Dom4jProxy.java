/*   1:    */ package org.hibernate.proxy.dom4j;
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
/*  24:    */ import org.hibernate.proxy.HibernateProxy;
/*  25:    */ import org.hibernate.proxy.LazyInitializer;
/*  26:    */ 
/*  27:    */ public class Dom4jProxy
/*  28:    */   implements HibernateProxy, Element, Serializable
/*  29:    */ {
/*  30:    */   private Dom4jLazyInitializer li;
/*  31:    */   
/*  32:    */   public Dom4jProxy(Dom4jLazyInitializer li)
/*  33:    */   {
/*  34: 62 */     this.li = li;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Object writeReplace()
/*  38:    */   {
/*  39: 66 */     return this;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public LazyInitializer getHibernateLazyInitializer()
/*  43:    */   {
/*  44: 70 */     return this.li;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public QName getQName()
/*  48:    */   {
/*  49: 74 */     return target().getQName();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public QName getQName(String s)
/*  53:    */   {
/*  54: 78 */     return target().getQName(s);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setQName(QName qName)
/*  58:    */   {
/*  59: 82 */     target().setQName(qName);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Namespace getNamespace()
/*  63:    */   {
/*  64: 86 */     return target().getNamespace();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Namespace getNamespaceForPrefix(String s)
/*  68:    */   {
/*  69: 90 */     return target().getNamespaceForPrefix(s);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Namespace getNamespaceForURI(String s)
/*  73:    */   {
/*  74: 94 */     return target().getNamespaceForURI(s);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List getNamespacesForURI(String s)
/*  78:    */   {
/*  79: 98 */     return target().getNamespacesForURI(s);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getNamespacePrefix()
/*  83:    */   {
/*  84:102 */     return target().getNamespacePrefix();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getNamespaceURI()
/*  88:    */   {
/*  89:106 */     return target().getNamespaceURI();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getQualifiedName()
/*  93:    */   {
/*  94:110 */     return target().getQualifiedName();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List additionalNamespaces()
/*  98:    */   {
/*  99:114 */     return target().additionalNamespaces();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List declaredNamespaces()
/* 103:    */   {
/* 104:118 */     return target().declaredNamespaces();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Element addAttribute(String attrName, String text)
/* 108:    */   {
/* 109:122 */     return target().addAttribute(attrName, text);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Element addAttribute(QName attrName, String text)
/* 113:    */   {
/* 114:126 */     return target().addAttribute(attrName, text);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Element addComment(String text)
/* 118:    */   {
/* 119:130 */     return target().addComment(text);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Element addCDATA(String text)
/* 123:    */   {
/* 124:134 */     return target().addCDATA(text);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Element addEntity(String name, String text)
/* 128:    */   {
/* 129:138 */     return target().addEntity(name, text);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Element addNamespace(String prefix, String uri)
/* 133:    */   {
/* 134:142 */     return target().addNamespace(prefix, uri);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Element addProcessingInstruction(String target, String text)
/* 138:    */   {
/* 139:146 */     return target().addProcessingInstruction(target, text);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Element addProcessingInstruction(String target, Map data)
/* 143:    */   {
/* 144:150 */     return target().addProcessingInstruction(target, data);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Element addText(String text)
/* 148:    */   {
/* 149:154 */     return target().addText(text);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void add(Attribute attribute)
/* 153:    */   {
/* 154:158 */     target().add(attribute);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void add(CDATA cdata)
/* 158:    */   {
/* 159:162 */     target().add(cdata);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void add(Entity entity)
/* 163:    */   {
/* 164:166 */     target().add(entity);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void add(Text text)
/* 168:    */   {
/* 169:170 */     target().add(text);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void add(Namespace namespace)
/* 173:    */   {
/* 174:174 */     target().add(namespace);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean remove(Attribute attribute)
/* 178:    */   {
/* 179:178 */     return target().remove(attribute);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean remove(CDATA cdata)
/* 183:    */   {
/* 184:182 */     return target().remove(cdata);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean remove(Entity entity)
/* 188:    */   {
/* 189:186 */     return target().remove(entity);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean remove(Namespace namespace)
/* 193:    */   {
/* 194:190 */     return target().remove(namespace);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean remove(Text text)
/* 198:    */   {
/* 199:194 */     return target().remove(text);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean supportsParent()
/* 203:    */   {
/* 204:198 */     return target().supportsParent();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Element getParent()
/* 208:    */   {
/* 209:202 */     return target().getParent();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setParent(Element element)
/* 213:    */   {
/* 214:206 */     target().setParent(element);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Document getDocument()
/* 218:    */   {
/* 219:210 */     return target().getDocument();
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setDocument(Document document)
/* 223:    */   {
/* 224:214 */     target().setDocument(document);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean isReadOnly()
/* 228:    */   {
/* 229:218 */     return target().isReadOnly();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean hasContent()
/* 233:    */   {
/* 234:222 */     return target().hasContent();
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getName()
/* 238:    */   {
/* 239:226 */     return target().getName();
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setName(String name)
/* 243:    */   {
/* 244:230 */     target().setName(name);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getText()
/* 248:    */   {
/* 249:234 */     return target().getText();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setText(String text)
/* 253:    */   {
/* 254:238 */     target().setText(text);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public String getTextTrim()
/* 258:    */   {
/* 259:242 */     return target().getTextTrim();
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String getStringValue()
/* 263:    */   {
/* 264:246 */     return target().getStringValue();
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getPath()
/* 268:    */   {
/* 269:250 */     return target().getPath();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String getPath(Element element)
/* 273:    */   {
/* 274:254 */     return target().getPath(element);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public String getUniquePath()
/* 278:    */   {
/* 279:258 */     return target().getUniquePath();
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String getUniquePath(Element element)
/* 283:    */   {
/* 284:262 */     return target().getUniquePath(element);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public String asXML()
/* 288:    */   {
/* 289:266 */     return target().asXML();
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void write(Writer writer)
/* 293:    */     throws IOException
/* 294:    */   {
/* 295:270 */     target().write(writer);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public short getNodeType()
/* 299:    */   {
/* 300:274 */     return target().getNodeType();
/* 301:    */   }
/* 302:    */   
/* 303:    */   public String getNodeTypeName()
/* 304:    */   {
/* 305:278 */     return target().getNodeTypeName();
/* 306:    */   }
/* 307:    */   
/* 308:    */   public Node detach()
/* 309:    */   {
/* 310:282 */     Element parent = target().getParent();
/* 311:283 */     if (parent != null) {
/* 312:283 */       parent.remove(this);
/* 313:    */     }
/* 314:284 */     return target().detach();
/* 315:    */   }
/* 316:    */   
/* 317:    */   public List selectNodes(String xpath)
/* 318:    */   {
/* 319:288 */     return target().selectNodes(xpath);
/* 320:    */   }
/* 321:    */   
/* 322:    */   public Object selectObject(String xpath)
/* 323:    */   {
/* 324:292 */     return target().selectObject(xpath);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public List selectNodes(String xpath, String comparison)
/* 328:    */   {
/* 329:296 */     return target().selectNodes(xpath, comparison);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public List selectNodes(String xpath, String comparison, boolean removeDups)
/* 333:    */   {
/* 334:300 */     return target().selectNodes(xpath, comparison, removeDups);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public Node selectSingleNode(String xpath)
/* 338:    */   {
/* 339:304 */     return target().selectSingleNode(xpath);
/* 340:    */   }
/* 341:    */   
/* 342:    */   public String valueOf(String xpath)
/* 343:    */   {
/* 344:308 */     return target().valueOf(xpath);
/* 345:    */   }
/* 346:    */   
/* 347:    */   public Number numberValueOf(String xpath)
/* 348:    */   {
/* 349:312 */     return target().numberValueOf(xpath);
/* 350:    */   }
/* 351:    */   
/* 352:    */   public boolean matches(String xpath)
/* 353:    */   {
/* 354:316 */     return target().matches(xpath);
/* 355:    */   }
/* 356:    */   
/* 357:    */   public XPath createXPath(String xpath)
/* 358:    */     throws InvalidXPathException
/* 359:    */   {
/* 360:320 */     return target().createXPath(xpath);
/* 361:    */   }
/* 362:    */   
/* 363:    */   public Node asXPathResult(Element element)
/* 364:    */   {
/* 365:324 */     return target().asXPathResult(element);
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void accept(Visitor visitor)
/* 369:    */   {
/* 370:328 */     target().accept(visitor);
/* 371:    */   }
/* 372:    */   
/* 373:    */   public Object clone()
/* 374:    */   {
/* 375:332 */     return target().clone();
/* 376:    */   }
/* 377:    */   
/* 378:    */   public Object getData()
/* 379:    */   {
/* 380:336 */     return target().getData();
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void setData(Object data)
/* 384:    */   {
/* 385:340 */     target().setData(data);
/* 386:    */   }
/* 387:    */   
/* 388:    */   public List attributes()
/* 389:    */   {
/* 390:344 */     return target().attributes();
/* 391:    */   }
/* 392:    */   
/* 393:    */   public void setAttributes(List list)
/* 394:    */   {
/* 395:348 */     target().setAttributes(list);
/* 396:    */   }
/* 397:    */   
/* 398:    */   public int attributeCount()
/* 399:    */   {
/* 400:352 */     return target().attributeCount();
/* 401:    */   }
/* 402:    */   
/* 403:    */   public Iterator attributeIterator()
/* 404:    */   {
/* 405:356 */     return target().attributeIterator();
/* 406:    */   }
/* 407:    */   
/* 408:    */   public Attribute attribute(int i)
/* 409:    */   {
/* 410:360 */     return target().attribute(i);
/* 411:    */   }
/* 412:    */   
/* 413:    */   public Attribute attribute(String name)
/* 414:    */   {
/* 415:364 */     return target().attribute(name);
/* 416:    */   }
/* 417:    */   
/* 418:    */   public Attribute attribute(QName qName)
/* 419:    */   {
/* 420:368 */     return target().attribute(qName);
/* 421:    */   }
/* 422:    */   
/* 423:    */   public String attributeValue(String name)
/* 424:    */   {
/* 425:372 */     return target().attributeValue(name);
/* 426:    */   }
/* 427:    */   
/* 428:    */   public String attributeValue(String name, String defaultValue)
/* 429:    */   {
/* 430:376 */     return target().attributeValue(name, defaultValue);
/* 431:    */   }
/* 432:    */   
/* 433:    */   public String attributeValue(QName qName)
/* 434:    */   {
/* 435:380 */     return target().attributeValue(qName);
/* 436:    */   }
/* 437:    */   
/* 438:    */   public String attributeValue(QName qName, String defaultValue)
/* 439:    */   {
/* 440:384 */     return target().attributeValue(qName, defaultValue);
/* 441:    */   }
/* 442:    */   
/* 443:    */   /**
/* 444:    */    * @deprecated
/* 445:    */    */
/* 446:    */   public void setAttributeValue(String name, String value)
/* 447:    */   {
/* 448:391 */     target().setAttributeValue(name, value);
/* 449:    */   }
/* 450:    */   
/* 451:    */   /**
/* 452:    */    * @deprecated
/* 453:    */    */
/* 454:    */   public void setAttributeValue(QName qName, String value)
/* 455:    */   {
/* 456:398 */     target().setAttributeValue(qName, value);
/* 457:    */   }
/* 458:    */   
/* 459:    */   public Element element(String name)
/* 460:    */   {
/* 461:402 */     return target().element(name);
/* 462:    */   }
/* 463:    */   
/* 464:    */   public Element element(QName qName)
/* 465:    */   {
/* 466:406 */     return target().element(qName);
/* 467:    */   }
/* 468:    */   
/* 469:    */   public List elements()
/* 470:    */   {
/* 471:410 */     return target().elements();
/* 472:    */   }
/* 473:    */   
/* 474:    */   public List elements(String name)
/* 475:    */   {
/* 476:414 */     return target().elements(name);
/* 477:    */   }
/* 478:    */   
/* 479:    */   public List elements(QName qName)
/* 480:    */   {
/* 481:418 */     return target().elements(qName);
/* 482:    */   }
/* 483:    */   
/* 484:    */   public Iterator elementIterator()
/* 485:    */   {
/* 486:422 */     return target().elementIterator();
/* 487:    */   }
/* 488:    */   
/* 489:    */   public Iterator elementIterator(String name)
/* 490:    */   {
/* 491:426 */     return target().elementIterator(name);
/* 492:    */   }
/* 493:    */   
/* 494:    */   public Iterator elementIterator(QName qName)
/* 495:    */   {
/* 496:431 */     return target().elementIterator(qName);
/* 497:    */   }
/* 498:    */   
/* 499:    */   public boolean isRootElement()
/* 500:    */   {
/* 501:435 */     return target().isRootElement();
/* 502:    */   }
/* 503:    */   
/* 504:    */   public boolean hasMixedContent()
/* 505:    */   {
/* 506:439 */     return target().hasMixedContent();
/* 507:    */   }
/* 508:    */   
/* 509:    */   public boolean isTextOnly()
/* 510:    */   {
/* 511:443 */     return target().isTextOnly();
/* 512:    */   }
/* 513:    */   
/* 514:    */   public void appendAttributes(Element element)
/* 515:    */   {
/* 516:447 */     target().appendAttributes(element);
/* 517:    */   }
/* 518:    */   
/* 519:    */   public Element createCopy()
/* 520:    */   {
/* 521:451 */     return target().createCopy();
/* 522:    */   }
/* 523:    */   
/* 524:    */   public Element createCopy(String name)
/* 525:    */   {
/* 526:455 */     return target().createCopy(name);
/* 527:    */   }
/* 528:    */   
/* 529:    */   public Element createCopy(QName qName)
/* 530:    */   {
/* 531:459 */     return target().createCopy(qName);
/* 532:    */   }
/* 533:    */   
/* 534:    */   public String elementText(String name)
/* 535:    */   {
/* 536:463 */     return target().elementText(name);
/* 537:    */   }
/* 538:    */   
/* 539:    */   public String elementText(QName qName)
/* 540:    */   {
/* 541:467 */     return target().elementText(qName);
/* 542:    */   }
/* 543:    */   
/* 544:    */   public String elementTextTrim(String name)
/* 545:    */   {
/* 546:471 */     return target().elementTextTrim(name);
/* 547:    */   }
/* 548:    */   
/* 549:    */   public String elementTextTrim(QName qName)
/* 550:    */   {
/* 551:475 */     return target().elementTextTrim(qName);
/* 552:    */   }
/* 553:    */   
/* 554:    */   public Node getXPathResult(int i)
/* 555:    */   {
/* 556:479 */     return target().getXPathResult(i);
/* 557:    */   }
/* 558:    */   
/* 559:    */   public Node node(int i)
/* 560:    */   {
/* 561:483 */     return target().node(i);
/* 562:    */   }
/* 563:    */   
/* 564:    */   public int indexOf(Node node)
/* 565:    */   {
/* 566:487 */     return target().indexOf(node);
/* 567:    */   }
/* 568:    */   
/* 569:    */   public int nodeCount()
/* 570:    */   {
/* 571:491 */     return target().nodeCount();
/* 572:    */   }
/* 573:    */   
/* 574:    */   public Element elementByID(String id)
/* 575:    */   {
/* 576:495 */     return target().elementByID(id);
/* 577:    */   }
/* 578:    */   
/* 579:    */   public List content()
/* 580:    */   {
/* 581:499 */     return target().content();
/* 582:    */   }
/* 583:    */   
/* 584:    */   public Iterator nodeIterator()
/* 585:    */   {
/* 586:503 */     return target().nodeIterator();
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void setContent(List list)
/* 590:    */   {
/* 591:507 */     target().setContent(list);
/* 592:    */   }
/* 593:    */   
/* 594:    */   public void appendContent(Branch branch)
/* 595:    */   {
/* 596:511 */     target().appendContent(branch);
/* 597:    */   }
/* 598:    */   
/* 599:    */   public void clearContent()
/* 600:    */   {
/* 601:515 */     target().clearContent();
/* 602:    */   }
/* 603:    */   
/* 604:    */   public List processingInstructions()
/* 605:    */   {
/* 606:519 */     return target().processingInstructions();
/* 607:    */   }
/* 608:    */   
/* 609:    */   public List processingInstructions(String name)
/* 610:    */   {
/* 611:523 */     return target().processingInstructions(name);
/* 612:    */   }
/* 613:    */   
/* 614:    */   public ProcessingInstruction processingInstruction(String name)
/* 615:    */   {
/* 616:527 */     return target().processingInstruction(name);
/* 617:    */   }
/* 618:    */   
/* 619:    */   public void setProcessingInstructions(List list)
/* 620:    */   {
/* 621:531 */     target().setProcessingInstructions(list);
/* 622:    */   }
/* 623:    */   
/* 624:    */   public Element addElement(String name)
/* 625:    */   {
/* 626:535 */     return target().addElement(name);
/* 627:    */   }
/* 628:    */   
/* 629:    */   public Element addElement(QName qName)
/* 630:    */   {
/* 631:539 */     return target().addElement(qName);
/* 632:    */   }
/* 633:    */   
/* 634:    */   public Element addElement(String name, String text)
/* 635:    */   {
/* 636:543 */     return target().addElement(name, text);
/* 637:    */   }
/* 638:    */   
/* 639:    */   public boolean removeProcessingInstruction(String name)
/* 640:    */   {
/* 641:548 */     return target().removeProcessingInstruction(name);
/* 642:    */   }
/* 643:    */   
/* 644:    */   public void add(Node node)
/* 645:    */   {
/* 646:552 */     target().add(node);
/* 647:    */   }
/* 648:    */   
/* 649:    */   public void add(Comment comment)
/* 650:    */   {
/* 651:556 */     target().add(comment);
/* 652:    */   }
/* 653:    */   
/* 654:    */   public void add(Element element)
/* 655:    */   {
/* 656:560 */     target().add(element);
/* 657:    */   }
/* 658:    */   
/* 659:    */   public void add(ProcessingInstruction processingInstruction)
/* 660:    */   {
/* 661:564 */     target().add(processingInstruction);
/* 662:    */   }
/* 663:    */   
/* 664:    */   public boolean remove(Node node)
/* 665:    */   {
/* 666:568 */     return target().remove(node);
/* 667:    */   }
/* 668:    */   
/* 669:    */   public boolean remove(Comment comment)
/* 670:    */   {
/* 671:572 */     return target().remove(comment);
/* 672:    */   }
/* 673:    */   
/* 674:    */   public boolean remove(Element element)
/* 675:    */   {
/* 676:576 */     return target().remove(element);
/* 677:    */   }
/* 678:    */   
/* 679:    */   public boolean remove(ProcessingInstruction processingInstruction)
/* 680:    */   {
/* 681:580 */     return target().remove(processingInstruction);
/* 682:    */   }
/* 683:    */   
/* 684:    */   public void normalize()
/* 685:    */   {
/* 686:584 */     target().normalize();
/* 687:    */   }
/* 688:    */   
/* 689:    */   private Element target()
/* 690:    */   {
/* 691:588 */     return this.li.getElement();
/* 692:    */   }
/* 693:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.dom4j.Dom4jProxy
 * JD-Core Version:    0.7.0.1
 */
/*    1:     */ package org.dom4j.tree;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.StringWriter;
/*    5:     */ import java.io.Writer;
/*    6:     */ import java.util.ArrayList;
/*    7:     */ import java.util.Collections;
/*    8:     */ import java.util.Iterator;
/*    9:     */ import java.util.List;
/*   10:     */ import java.util.Map;
/*   11:     */ import org.dom4j.Attribute;
/*   12:     */ import org.dom4j.CDATA;
/*   13:     */ import org.dom4j.CharacterData;
/*   14:     */ import org.dom4j.Comment;
/*   15:     */ import org.dom4j.Document;
/*   16:     */ import org.dom4j.DocumentFactory;
/*   17:     */ import org.dom4j.Element;
/*   18:     */ import org.dom4j.Entity;
/*   19:     */ import org.dom4j.IllegalAddException;
/*   20:     */ import org.dom4j.Namespace;
/*   21:     */ import org.dom4j.Node;
/*   22:     */ import org.dom4j.ProcessingInstruction;
/*   23:     */ import org.dom4j.QName;
/*   24:     */ import org.dom4j.Text;
/*   25:     */ import org.dom4j.Visitor;
/*   26:     */ import org.dom4j.io.OutputFormat;
/*   27:     */ import org.dom4j.io.XMLWriter;
/*   28:     */ import org.xml.sax.Attributes;
/*   29:     */ 
/*   30:     */ public abstract class AbstractElement
/*   31:     */   extends AbstractBranch
/*   32:     */   implements Element
/*   33:     */ {
/*   34:  51 */   private static final DocumentFactory DOCUMENT_FACTORY = ;
/*   35:  54 */   protected static final List EMPTY_LIST = Collections.EMPTY_LIST;
/*   36:  56 */   protected static final Iterator EMPTY_ITERATOR = EMPTY_LIST.iterator();
/*   37:     */   protected static final boolean VERBOSE_TOSTRING = false;
/*   38:     */   protected static final boolean USE_STRINGVALUE_SEPARATOR = false;
/*   39:     */   
/*   40:     */   public short getNodeType()
/*   41:     */   {
/*   42:  66 */     return 1;
/*   43:     */   }
/*   44:     */   
/*   45:     */   public boolean isRootElement()
/*   46:     */   {
/*   47:  70 */     Document document = getDocument();
/*   48:  72 */     if (document != null)
/*   49:     */     {
/*   50:  73 */       Element root = document.getRootElement();
/*   51:  75 */       if (root == this) {
/*   52:  76 */         return true;
/*   53:     */       }
/*   54:     */     }
/*   55:  80 */     return false;
/*   56:     */   }
/*   57:     */   
/*   58:     */   public void setName(String name)
/*   59:     */   {
/*   60:  84 */     setQName(getDocumentFactory().createQName(name));
/*   61:     */   }
/*   62:     */   
/*   63:     */   public void setNamespace(Namespace namespace)
/*   64:     */   {
/*   65:  88 */     setQName(getDocumentFactory().createQName(getName(), namespace));
/*   66:     */   }
/*   67:     */   
/*   68:     */   public String getXPathNameStep()
/*   69:     */   {
/*   70: 100 */     String uri = getNamespaceURI();
/*   71: 102 */     if ((uri == null) || (uri.length() == 0)) {
/*   72: 103 */       return getName();
/*   73:     */     }
/*   74: 106 */     String prefix = getNamespacePrefix();
/*   75: 108 */     if ((prefix == null) || (prefix.length() == 0)) {
/*   76: 109 */       return "*[name()='" + getName() + "']";
/*   77:     */     }
/*   78: 112 */     return getQualifiedName();
/*   79:     */   }
/*   80:     */   
/*   81:     */   public String getPath(Element context)
/*   82:     */   {
/*   83: 116 */     if (this == context) {
/*   84: 117 */       return ".";
/*   85:     */     }
/*   86: 120 */     Element parent = getParent();
/*   87: 122 */     if (parent == null) {
/*   88: 123 */       return "/" + getXPathNameStep();
/*   89:     */     }
/*   90: 124 */     if (parent == context) {
/*   91: 125 */       return getXPathNameStep();
/*   92:     */     }
/*   93: 128 */     return parent.getPath(context) + "/" + getXPathNameStep();
/*   94:     */   }
/*   95:     */   
/*   96:     */   public String getUniquePath(Element context)
/*   97:     */   {
/*   98: 132 */     Element parent = getParent();
/*   99: 134 */     if (parent == null) {
/*  100: 135 */       return "/" + getXPathNameStep();
/*  101:     */     }
/*  102: 138 */     StringBuffer buffer = new StringBuffer();
/*  103: 140 */     if (parent != context)
/*  104:     */     {
/*  105: 141 */       buffer.append(parent.getUniquePath(context));
/*  106:     */       
/*  107: 143 */       buffer.append("/");
/*  108:     */     }
/*  109: 146 */     buffer.append(getXPathNameStep());
/*  110:     */     
/*  111: 148 */     List mySiblings = parent.elements(getQName());
/*  112: 150 */     if (mySiblings.size() > 1)
/*  113:     */     {
/*  114: 151 */       int idx = mySiblings.indexOf(this);
/*  115: 153 */       if (idx >= 0)
/*  116:     */       {
/*  117: 154 */         buffer.append("[");
/*  118:     */         
/*  119: 156 */         buffer.append(Integer.toString(++idx));
/*  120:     */         
/*  121: 158 */         buffer.append("]");
/*  122:     */       }
/*  123:     */     }
/*  124: 162 */     return buffer.toString();
/*  125:     */   }
/*  126:     */   
/*  127:     */   public String asXML()
/*  128:     */   {
/*  129:     */     try
/*  130:     */     {
/*  131: 167 */       StringWriter out = new StringWriter();
/*  132: 168 */       XMLWriter writer = new XMLWriter(out, new OutputFormat());
/*  133:     */       
/*  134: 170 */       writer.write(this);
/*  135: 171 */       writer.flush();
/*  136:     */       
/*  137: 173 */       return out.toString();
/*  138:     */     }
/*  139:     */     catch (IOException e)
/*  140:     */     {
/*  141: 175 */       throw new RuntimeException("IOException while generating textual representation: " + e.getMessage());
/*  142:     */     }
/*  143:     */   }
/*  144:     */   
/*  145:     */   public void write(Writer out)
/*  146:     */     throws IOException
/*  147:     */   {
/*  148: 181 */     XMLWriter writer = new XMLWriter(out, new OutputFormat());
/*  149: 182 */     writer.write(this);
/*  150:     */   }
/*  151:     */   
/*  152:     */   public void accept(Visitor visitor)
/*  153:     */   {
/*  154: 195 */     visitor.visit(this);
/*  155:     */     
/*  156:     */ 
/*  157: 198 */     int i = 0;
/*  158: 198 */     for (int size = attributeCount(); i < size; i++)
/*  159:     */     {
/*  160: 199 */       Attribute attribute = attribute(i);
/*  161:     */       
/*  162: 201 */       visitor.visit(attribute);
/*  163:     */     }
/*  164: 205 */     int i = 0;
/*  165: 205 */     for (int size = nodeCount(); i < size; i++)
/*  166:     */     {
/*  167: 206 */       Node node = node(i);
/*  168:     */       
/*  169: 208 */       node.accept(visitor);
/*  170:     */     }
/*  171:     */   }
/*  172:     */   
/*  173:     */   public String toString()
/*  174:     */   {
/*  175: 213 */     String uri = getNamespaceURI();
/*  176: 215 */     if ((uri != null) && (uri.length() > 0)) {
/*  177: 221 */       return super.toString() + " [Element: <" + getQualifiedName() + " uri: " + uri + " attributes: " + attributeList() + "/>]";
/*  178:     */     }
/*  179: 231 */     return super.toString() + " [Element: <" + getQualifiedName() + " attributes: " + attributeList() + "/>]";
/*  180:     */   }
/*  181:     */   
/*  182:     */   public Namespace getNamespace()
/*  183:     */   {
/*  184: 240 */     return getQName().getNamespace();
/*  185:     */   }
/*  186:     */   
/*  187:     */   public String getName()
/*  188:     */   {
/*  189: 244 */     return getQName().getName();
/*  190:     */   }
/*  191:     */   
/*  192:     */   public String getNamespacePrefix()
/*  193:     */   {
/*  194: 248 */     return getQName().getNamespacePrefix();
/*  195:     */   }
/*  196:     */   
/*  197:     */   public String getNamespaceURI()
/*  198:     */   {
/*  199: 252 */     return getQName().getNamespaceURI();
/*  200:     */   }
/*  201:     */   
/*  202:     */   public String getQualifiedName()
/*  203:     */   {
/*  204: 256 */     return getQName().getQualifiedName();
/*  205:     */   }
/*  206:     */   
/*  207:     */   public Object getData()
/*  208:     */   {
/*  209: 260 */     return getText();
/*  210:     */   }
/*  211:     */   
/*  212:     */   public void setData(Object data) {}
/*  213:     */   
/*  214:     */   public Node node(int index)
/*  215:     */   {
/*  216: 270 */     if (index >= 0)
/*  217:     */     {
/*  218: 271 */       List list = contentList();
/*  219: 273 */       if (index >= list.size()) {
/*  220: 274 */         return null;
/*  221:     */       }
/*  222: 277 */       Object node = list.get(index);
/*  223: 279 */       if (node != null)
/*  224:     */       {
/*  225: 280 */         if ((node instanceof Node)) {
/*  226: 281 */           return (Node)node;
/*  227:     */         }
/*  228: 283 */         return getDocumentFactory().createText(node.toString());
/*  229:     */       }
/*  230:     */     }
/*  231: 288 */     return null;
/*  232:     */   }
/*  233:     */   
/*  234:     */   public int indexOf(Node node)
/*  235:     */   {
/*  236: 292 */     return contentList().indexOf(node);
/*  237:     */   }
/*  238:     */   
/*  239:     */   public int nodeCount()
/*  240:     */   {
/*  241: 296 */     return contentList().size();
/*  242:     */   }
/*  243:     */   
/*  244:     */   public Iterator nodeIterator()
/*  245:     */   {
/*  246: 300 */     return contentList().iterator();
/*  247:     */   }
/*  248:     */   
/*  249:     */   public Element element(String name)
/*  250:     */   {
/*  251: 306 */     List list = contentList();
/*  252:     */     
/*  253: 308 */     int size = list.size();
/*  254: 310 */     for (int i = 0; i < size; i++)
/*  255:     */     {
/*  256: 311 */       Object object = list.get(i);
/*  257: 313 */       if ((object instanceof Element))
/*  258:     */       {
/*  259: 314 */         Element element = (Element)object;
/*  260: 316 */         if (name.equals(element.getName())) {
/*  261: 317 */           return element;
/*  262:     */         }
/*  263:     */       }
/*  264:     */     }
/*  265: 322 */     return null;
/*  266:     */   }
/*  267:     */   
/*  268:     */   public Element element(QName qName)
/*  269:     */   {
/*  270: 326 */     List list = contentList();
/*  271:     */     
/*  272: 328 */     int size = list.size();
/*  273: 330 */     for (int i = 0; i < size; i++)
/*  274:     */     {
/*  275: 331 */       Object object = list.get(i);
/*  276: 333 */       if ((object instanceof Element))
/*  277:     */       {
/*  278: 334 */         Element element = (Element)object;
/*  279: 336 */         if (qName.equals(element.getQName())) {
/*  280: 337 */           return element;
/*  281:     */         }
/*  282:     */       }
/*  283:     */     }
/*  284: 342 */     return null;
/*  285:     */   }
/*  286:     */   
/*  287:     */   public Element element(String name, Namespace namespace)
/*  288:     */   {
/*  289: 346 */     return element(getDocumentFactory().createQName(name, namespace));
/*  290:     */   }
/*  291:     */   
/*  292:     */   public List elements()
/*  293:     */   {
/*  294: 350 */     List list = contentList();
/*  295:     */     
/*  296: 352 */     BackedList answer = createResultList();
/*  297:     */     
/*  298: 354 */     int size = list.size();
/*  299: 356 */     for (int i = 0; i < size; i++)
/*  300:     */     {
/*  301: 357 */       Object object = list.get(i);
/*  302: 359 */       if ((object instanceof Element)) {
/*  303: 360 */         answer.addLocal(object);
/*  304:     */       }
/*  305:     */     }
/*  306: 364 */     return answer;
/*  307:     */   }
/*  308:     */   
/*  309:     */   public List elements(String name)
/*  310:     */   {
/*  311: 368 */     List list = contentList();
/*  312:     */     
/*  313: 370 */     BackedList answer = createResultList();
/*  314:     */     
/*  315: 372 */     int size = list.size();
/*  316: 374 */     for (int i = 0; i < size; i++)
/*  317:     */     {
/*  318: 375 */       Object object = list.get(i);
/*  319: 377 */       if ((object instanceof Element))
/*  320:     */       {
/*  321: 378 */         Element element = (Element)object;
/*  322: 380 */         if (name.equals(element.getName())) {
/*  323: 381 */           answer.addLocal(element);
/*  324:     */         }
/*  325:     */       }
/*  326:     */     }
/*  327: 386 */     return answer;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public List elements(QName qName)
/*  331:     */   {
/*  332: 390 */     List list = contentList();
/*  333:     */     
/*  334: 392 */     BackedList answer = createResultList();
/*  335:     */     
/*  336: 394 */     int size = list.size();
/*  337: 396 */     for (int i = 0; i < size; i++)
/*  338:     */     {
/*  339: 397 */       Object object = list.get(i);
/*  340: 399 */       if ((object instanceof Element))
/*  341:     */       {
/*  342: 400 */         Element element = (Element)object;
/*  343: 402 */         if (qName.equals(element.getQName())) {
/*  344: 403 */           answer.addLocal(element);
/*  345:     */         }
/*  346:     */       }
/*  347:     */     }
/*  348: 408 */     return answer;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public List elements(String name, Namespace namespace)
/*  352:     */   {
/*  353: 412 */     return elements(getDocumentFactory().createQName(name, namespace));
/*  354:     */   }
/*  355:     */   
/*  356:     */   public Iterator elementIterator()
/*  357:     */   {
/*  358: 416 */     List list = elements();
/*  359:     */     
/*  360: 418 */     return list.iterator();
/*  361:     */   }
/*  362:     */   
/*  363:     */   public Iterator elementIterator(String name)
/*  364:     */   {
/*  365: 422 */     List list = elements(name);
/*  366:     */     
/*  367: 424 */     return list.iterator();
/*  368:     */   }
/*  369:     */   
/*  370:     */   public Iterator elementIterator(QName qName)
/*  371:     */   {
/*  372: 428 */     List list = elements(qName);
/*  373:     */     
/*  374: 430 */     return list.iterator();
/*  375:     */   }
/*  376:     */   
/*  377:     */   public Iterator elementIterator(String name, Namespace ns)
/*  378:     */   {
/*  379: 434 */     return elementIterator(getDocumentFactory().createQName(name, ns));
/*  380:     */   }
/*  381:     */   
/*  382:     */   public List attributes()
/*  383:     */   {
/*  384: 440 */     return new ContentListFacade(this, attributeList());
/*  385:     */   }
/*  386:     */   
/*  387:     */   public Iterator attributeIterator()
/*  388:     */   {
/*  389: 444 */     return attributeList().iterator();
/*  390:     */   }
/*  391:     */   
/*  392:     */   public Attribute attribute(int index)
/*  393:     */   {
/*  394: 448 */     return (Attribute)attributeList().get(index);
/*  395:     */   }
/*  396:     */   
/*  397:     */   public int attributeCount()
/*  398:     */   {
/*  399: 452 */     return attributeList().size();
/*  400:     */   }
/*  401:     */   
/*  402:     */   public Attribute attribute(String name)
/*  403:     */   {
/*  404: 456 */     List list = attributeList();
/*  405:     */     
/*  406: 458 */     int size = list.size();
/*  407: 460 */     for (int i = 0; i < size; i++)
/*  408:     */     {
/*  409: 461 */       Attribute attribute = (Attribute)list.get(i);
/*  410: 463 */       if (name.equals(attribute.getName())) {
/*  411: 464 */         return attribute;
/*  412:     */       }
/*  413:     */     }
/*  414: 468 */     return null;
/*  415:     */   }
/*  416:     */   
/*  417:     */   public Attribute attribute(QName qName)
/*  418:     */   {
/*  419: 472 */     List list = attributeList();
/*  420:     */     
/*  421: 474 */     int size = list.size();
/*  422: 476 */     for (int i = 0; i < size; i++)
/*  423:     */     {
/*  424: 477 */       Attribute attribute = (Attribute)list.get(i);
/*  425: 479 */       if (qName.equals(attribute.getQName())) {
/*  426: 480 */         return attribute;
/*  427:     */       }
/*  428:     */     }
/*  429: 484 */     return null;
/*  430:     */   }
/*  431:     */   
/*  432:     */   public Attribute attribute(String name, Namespace namespace)
/*  433:     */   {
/*  434: 488 */     return attribute(getDocumentFactory().createQName(name, namespace));
/*  435:     */   }
/*  436:     */   
/*  437:     */   public void setAttributes(Attributes attributes, NamespaceStack namespaceStack, boolean noNamespaceAttributes)
/*  438:     */   {
/*  439: 505 */     int size = attributes.getLength();
/*  440: 507 */     if (size > 0)
/*  441:     */     {
/*  442: 508 */       DocumentFactory factory = getDocumentFactory();
/*  443: 510 */       if (size == 1)
/*  444:     */       {
/*  445: 512 */         String name = attributes.getQName(0);
/*  446: 514 */         if ((noNamespaceAttributes) || (!name.startsWith("xmlns")))
/*  447:     */         {
/*  448: 515 */           String attributeURI = attributes.getURI(0);
/*  449:     */           
/*  450: 517 */           String attributeLocalName = attributes.getLocalName(0);
/*  451:     */           
/*  452: 519 */           String attributeValue = attributes.getValue(0);
/*  453:     */           
/*  454: 521 */           QName attributeQName = namespaceStack.getAttributeQName(attributeURI, attributeLocalName, name);
/*  455:     */           
/*  456:     */ 
/*  457: 524 */           add(factory.createAttribute(this, attributeQName, attributeValue));
/*  458:     */         }
/*  459:     */       }
/*  460:     */       else
/*  461:     */       {
/*  462: 528 */         List list = attributeList(size);
/*  463:     */         
/*  464: 530 */         list.clear();
/*  465: 532 */         for (int i = 0; i < size; i++)
/*  466:     */         {
/*  467: 535 */           String attributeName = attributes.getQName(i);
/*  468: 537 */           if ((noNamespaceAttributes) || (!attributeName.startsWith("xmlns")))
/*  469:     */           {
/*  470: 539 */             String attributeURI = attributes.getURI(i);
/*  471:     */             
/*  472: 541 */             String attributeLocalName = attributes.getLocalName(i);
/*  473:     */             
/*  474: 543 */             String attributeValue = attributes.getValue(i);
/*  475:     */             
/*  476: 545 */             QName attributeQName = namespaceStack.getAttributeQName(attributeURI, attributeLocalName, attributeName);
/*  477:     */             
/*  478:     */ 
/*  479:     */ 
/*  480: 549 */             Attribute attribute = factory.createAttribute(this, attributeQName, attributeValue);
/*  481:     */             
/*  482:     */ 
/*  483: 552 */             list.add(attribute);
/*  484:     */             
/*  485: 554 */             childAdded(attribute);
/*  486:     */           }
/*  487:     */         }
/*  488:     */       }
/*  489:     */     }
/*  490:     */   }
/*  491:     */   
/*  492:     */   public String attributeValue(String name)
/*  493:     */   {
/*  494: 562 */     Attribute attrib = attribute(name);
/*  495: 564 */     if (attrib == null) {
/*  496: 565 */       return null;
/*  497:     */     }
/*  498: 567 */     return attrib.getValue();
/*  499:     */   }
/*  500:     */   
/*  501:     */   public String attributeValue(QName qName)
/*  502:     */   {
/*  503: 572 */     Attribute attrib = attribute(qName);
/*  504: 574 */     if (attrib == null) {
/*  505: 575 */       return null;
/*  506:     */     }
/*  507: 577 */     return attrib.getValue();
/*  508:     */   }
/*  509:     */   
/*  510:     */   public String attributeValue(String name, String defaultValue)
/*  511:     */   {
/*  512: 582 */     String answer = attributeValue(name);
/*  513:     */     
/*  514: 584 */     return answer != null ? answer : defaultValue;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public String attributeValue(QName qName, String defaultValue)
/*  518:     */   {
/*  519: 588 */     String answer = attributeValue(qName);
/*  520:     */     
/*  521: 590 */     return answer != null ? answer : defaultValue;
/*  522:     */   }
/*  523:     */   
/*  524:     */   /**
/*  525:     */    * @deprecated
/*  526:     */    */
/*  527:     */   public void setAttributeValue(String name, String value)
/*  528:     */   {
/*  529: 606 */     addAttribute(name, value);
/*  530:     */   }
/*  531:     */   
/*  532:     */   /**
/*  533:     */    * @deprecated
/*  534:     */    */
/*  535:     */   public void setAttributeValue(QName qName, String value)
/*  536:     */   {
/*  537: 622 */     addAttribute(qName, value);
/*  538:     */   }
/*  539:     */   
/*  540:     */   public void add(Attribute attribute)
/*  541:     */   {
/*  542: 626 */     if (attribute.getParent() != null)
/*  543:     */     {
/*  544: 627 */       String message = "The Attribute already has an existing parent \"" + attribute.getParent().getQualifiedName() + "\"";
/*  545:     */       
/*  546:     */ 
/*  547: 630 */       throw new IllegalAddException(this, attribute, message);
/*  548:     */     }
/*  549: 633 */     if (attribute.getValue() == null)
/*  550:     */     {
/*  551: 637 */       Attribute oldAttribute = attribute(attribute.getQName());
/*  552: 639 */       if (oldAttribute != null) {
/*  553: 640 */         remove(oldAttribute);
/*  554:     */       }
/*  555:     */     }
/*  556:     */     else
/*  557:     */     {
/*  558: 643 */       attributeList().add(attribute);
/*  559:     */       
/*  560: 645 */       childAdded(attribute);
/*  561:     */     }
/*  562:     */   }
/*  563:     */   
/*  564:     */   public boolean remove(Attribute attribute)
/*  565:     */   {
/*  566: 650 */     List list = attributeList();
/*  567:     */     
/*  568: 652 */     boolean answer = list.remove(attribute);
/*  569: 654 */     if (answer)
/*  570:     */     {
/*  571: 655 */       childRemoved(attribute);
/*  572:     */     }
/*  573:     */     else
/*  574:     */     {
/*  575: 658 */       Attribute copy = attribute(attribute.getQName());
/*  576: 660 */       if (copy != null)
/*  577:     */       {
/*  578: 661 */         list.remove(copy);
/*  579:     */         
/*  580: 663 */         answer = true;
/*  581:     */       }
/*  582:     */     }
/*  583: 667 */     return answer;
/*  584:     */   }
/*  585:     */   
/*  586:     */   public List processingInstructions()
/*  587:     */   {
/*  588: 673 */     List list = contentList();
/*  589:     */     
/*  590: 675 */     BackedList answer = createResultList();
/*  591:     */     
/*  592: 677 */     int size = list.size();
/*  593: 679 */     for (int i = 0; i < size; i++)
/*  594:     */     {
/*  595: 680 */       Object object = list.get(i);
/*  596: 682 */       if ((object instanceof ProcessingInstruction)) {
/*  597: 683 */         answer.addLocal(object);
/*  598:     */       }
/*  599:     */     }
/*  600: 687 */     return answer;
/*  601:     */   }
/*  602:     */   
/*  603:     */   public List processingInstructions(String target)
/*  604:     */   {
/*  605: 691 */     List list = contentList();
/*  606:     */     
/*  607: 693 */     BackedList answer = createResultList();
/*  608:     */     
/*  609: 695 */     int size = list.size();
/*  610: 697 */     for (int i = 0; i < size; i++)
/*  611:     */     {
/*  612: 698 */       Object object = list.get(i);
/*  613: 700 */       if ((object instanceof ProcessingInstruction))
/*  614:     */       {
/*  615: 701 */         ProcessingInstruction pi = (ProcessingInstruction)object;
/*  616: 703 */         if (target.equals(pi.getName())) {
/*  617: 704 */           answer.addLocal(pi);
/*  618:     */         }
/*  619:     */       }
/*  620:     */     }
/*  621: 709 */     return answer;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public ProcessingInstruction processingInstruction(String target)
/*  625:     */   {
/*  626: 713 */     List list = contentList();
/*  627:     */     
/*  628: 715 */     int size = list.size();
/*  629: 717 */     for (int i = 0; i < size; i++)
/*  630:     */     {
/*  631: 718 */       Object object = list.get(i);
/*  632: 720 */       if ((object instanceof ProcessingInstruction))
/*  633:     */       {
/*  634: 721 */         ProcessingInstruction pi = (ProcessingInstruction)object;
/*  635: 723 */         if (target.equals(pi.getName())) {
/*  636: 724 */           return pi;
/*  637:     */         }
/*  638:     */       }
/*  639:     */     }
/*  640: 729 */     return null;
/*  641:     */   }
/*  642:     */   
/*  643:     */   public boolean removeProcessingInstruction(String target)
/*  644:     */   {
/*  645: 733 */     List list = contentList();
/*  646: 735 */     for (Iterator iter = list.iterator(); iter.hasNext();)
/*  647:     */     {
/*  648: 736 */       Object object = iter.next();
/*  649: 738 */       if ((object instanceof ProcessingInstruction))
/*  650:     */       {
/*  651: 739 */         ProcessingInstruction pi = (ProcessingInstruction)object;
/*  652: 741 */         if (target.equals(pi.getName()))
/*  653:     */         {
/*  654: 742 */           iter.remove();
/*  655:     */           
/*  656: 744 */           return true;
/*  657:     */         }
/*  658:     */       }
/*  659:     */     }
/*  660: 749 */     return false;
/*  661:     */   }
/*  662:     */   
/*  663:     */   public Node getXPathResult(int index)
/*  664:     */   {
/*  665: 755 */     Node answer = node(index);
/*  666: 757 */     if ((answer != null) && (!answer.supportsParent())) {
/*  667: 758 */       return answer.asXPathResult(this);
/*  668:     */     }
/*  669: 761 */     return answer;
/*  670:     */   }
/*  671:     */   
/*  672:     */   public Element addAttribute(String name, String value)
/*  673:     */   {
/*  674: 766 */     Attribute attribute = attribute(name);
/*  675: 768 */     if (value != null)
/*  676:     */     {
/*  677: 769 */       if (attribute == null)
/*  678:     */       {
/*  679: 770 */         add(getDocumentFactory().createAttribute(this, name, value));
/*  680:     */       }
/*  681: 771 */       else if (attribute.isReadOnly())
/*  682:     */       {
/*  683: 772 */         remove(attribute);
/*  684:     */         
/*  685: 774 */         add(getDocumentFactory().createAttribute(this, name, value));
/*  686:     */       }
/*  687:     */       else
/*  688:     */       {
/*  689: 776 */         attribute.setValue(value);
/*  690:     */       }
/*  691:     */     }
/*  692: 778 */     else if (attribute != null) {
/*  693: 779 */       remove(attribute);
/*  694:     */     }
/*  695: 782 */     return this;
/*  696:     */   }
/*  697:     */   
/*  698:     */   public Element addAttribute(QName qName, String value)
/*  699:     */   {
/*  700: 787 */     Attribute attribute = attribute(qName);
/*  701: 789 */     if (value != null)
/*  702:     */     {
/*  703: 790 */       if (attribute == null)
/*  704:     */       {
/*  705: 791 */         add(getDocumentFactory().createAttribute(this, qName, value));
/*  706:     */       }
/*  707: 792 */       else if (attribute.isReadOnly())
/*  708:     */       {
/*  709: 793 */         remove(attribute);
/*  710:     */         
/*  711: 795 */         add(getDocumentFactory().createAttribute(this, qName, value));
/*  712:     */       }
/*  713:     */       else
/*  714:     */       {
/*  715: 797 */         attribute.setValue(value);
/*  716:     */       }
/*  717:     */     }
/*  718: 799 */     else if (attribute != null) {
/*  719: 800 */       remove(attribute);
/*  720:     */     }
/*  721: 803 */     return this;
/*  722:     */   }
/*  723:     */   
/*  724:     */   public Element addCDATA(String cdata)
/*  725:     */   {
/*  726: 807 */     CDATA node = getDocumentFactory().createCDATA(cdata);
/*  727:     */     
/*  728: 809 */     addNewNode(node);
/*  729:     */     
/*  730: 811 */     return this;
/*  731:     */   }
/*  732:     */   
/*  733:     */   public Element addComment(String comment)
/*  734:     */   {
/*  735: 815 */     Comment node = getDocumentFactory().createComment(comment);
/*  736:     */     
/*  737: 817 */     addNewNode(node);
/*  738:     */     
/*  739: 819 */     return this;
/*  740:     */   }
/*  741:     */   
/*  742:     */   public Element addElement(String name)
/*  743:     */   {
/*  744: 823 */     DocumentFactory factory = getDocumentFactory();
/*  745:     */     
/*  746: 825 */     int index = name.indexOf(":");
/*  747:     */     
/*  748: 827 */     String prefix = "";
/*  749:     */     
/*  750: 829 */     String localName = name;
/*  751:     */     
/*  752: 831 */     Namespace namespace = null;
/*  753: 833 */     if (index > 0)
/*  754:     */     {
/*  755: 834 */       prefix = name.substring(0, index);
/*  756:     */       
/*  757: 836 */       localName = name.substring(index + 1);
/*  758:     */       
/*  759: 838 */       namespace = getNamespaceForPrefix(prefix);
/*  760: 840 */       if (namespace == null) {
/*  761: 841 */         throw new IllegalAddException("No such namespace prefix: " + prefix + " is in scope on: " + this + " so cannot add element: " + name);
/*  762:     */       }
/*  763:     */     }
/*  764:     */     else
/*  765:     */     {
/*  766: 846 */       namespace = getNamespaceForPrefix("");
/*  767:     */     }
/*  768:     */     Element node;
/*  769:     */     Element node;
/*  770: 851 */     if (namespace != null)
/*  771:     */     {
/*  772: 852 */       QName qname = factory.createQName(localName, namespace);
/*  773:     */       
/*  774: 854 */       node = factory.createElement(qname);
/*  775:     */     }
/*  776:     */     else
/*  777:     */     {
/*  778: 856 */       node = factory.createElement(name);
/*  779:     */     }
/*  780: 859 */     addNewNode(node);
/*  781:     */     
/*  782: 861 */     return node;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public Element addEntity(String name, String text)
/*  786:     */   {
/*  787: 865 */     Entity node = getDocumentFactory().createEntity(name, text);
/*  788:     */     
/*  789: 867 */     addNewNode(node);
/*  790:     */     
/*  791: 869 */     return this;
/*  792:     */   }
/*  793:     */   
/*  794:     */   public Element addNamespace(String prefix, String uri)
/*  795:     */   {
/*  796: 873 */     Namespace node = getDocumentFactory().createNamespace(prefix, uri);
/*  797:     */     
/*  798: 875 */     addNewNode(node);
/*  799:     */     
/*  800: 877 */     return this;
/*  801:     */   }
/*  802:     */   
/*  803:     */   public Element addProcessingInstruction(String target, String data)
/*  804:     */   {
/*  805: 881 */     ProcessingInstruction node = getDocumentFactory().createProcessingInstruction(target, data);
/*  806:     */     
/*  807:     */ 
/*  808: 884 */     addNewNode(node);
/*  809:     */     
/*  810: 886 */     return this;
/*  811:     */   }
/*  812:     */   
/*  813:     */   public Element addProcessingInstruction(String target, Map data)
/*  814:     */   {
/*  815: 890 */     ProcessingInstruction node = getDocumentFactory().createProcessingInstruction(target, data);
/*  816:     */     
/*  817:     */ 
/*  818: 893 */     addNewNode(node);
/*  819:     */     
/*  820: 895 */     return this;
/*  821:     */   }
/*  822:     */   
/*  823:     */   public Element addText(String text)
/*  824:     */   {
/*  825: 899 */     Text node = getDocumentFactory().createText(text);
/*  826:     */     
/*  827: 901 */     addNewNode(node);
/*  828:     */     
/*  829: 903 */     return this;
/*  830:     */   }
/*  831:     */   
/*  832:     */   public void add(Node node)
/*  833:     */   {
/*  834: 908 */     switch (node.getNodeType())
/*  835:     */     {
/*  836:     */     case 1: 
/*  837: 910 */       add((Element)node);
/*  838:     */       
/*  839: 912 */       break;
/*  840:     */     case 2: 
/*  841: 915 */       add((Attribute)node);
/*  842:     */       
/*  843: 917 */       break;
/*  844:     */     case 3: 
/*  845: 920 */       add((Text)node);
/*  846:     */       
/*  847: 922 */       break;
/*  848:     */     case 4: 
/*  849: 925 */       add((CDATA)node);
/*  850:     */       
/*  851: 927 */       break;
/*  852:     */     case 5: 
/*  853: 930 */       add((Entity)node);
/*  854:     */       
/*  855: 932 */       break;
/*  856:     */     case 7: 
/*  857: 935 */       add((ProcessingInstruction)node);
/*  858:     */       
/*  859: 937 */       break;
/*  860:     */     case 8: 
/*  861: 940 */       add((Comment)node);
/*  862:     */       
/*  863: 942 */       break;
/*  864:     */     case 13: 
/*  865: 949 */       add((Namespace)node);
/*  866:     */       
/*  867: 951 */       break;
/*  868:     */     case 6: 
/*  869:     */     case 9: 
/*  870:     */     case 10: 
/*  871:     */     case 11: 
/*  872:     */     case 12: 
/*  873:     */     default: 
/*  874: 954 */       invalidNodeTypeAddException(node);
/*  875:     */     }
/*  876:     */   }
/*  877:     */   
/*  878:     */   public boolean remove(Node node)
/*  879:     */   {
/*  880: 959 */     switch (node.getNodeType())
/*  881:     */     {
/*  882:     */     case 1: 
/*  883: 961 */       return remove((Element)node);
/*  884:     */     case 2: 
/*  885: 964 */       return remove((Attribute)node);
/*  886:     */     case 3: 
/*  887: 967 */       return remove((Text)node);
/*  888:     */     case 4: 
/*  889: 970 */       return remove((CDATA)node);
/*  890:     */     case 5: 
/*  891: 973 */       return remove((Entity)node);
/*  892:     */     case 7: 
/*  893: 976 */       return remove((ProcessingInstruction)node);
/*  894:     */     case 8: 
/*  895: 979 */       return remove((Comment)node);
/*  896:     */     case 13: 
/*  897: 985 */       return remove((Namespace)node);
/*  898:     */     }
/*  899: 988 */     return false;
/*  900:     */   }
/*  901:     */   
/*  902:     */   public void add(CDATA cdata)
/*  903:     */   {
/*  904: 994 */     addNode(cdata);
/*  905:     */   }
/*  906:     */   
/*  907:     */   public void add(Comment comment)
/*  908:     */   {
/*  909: 998 */     addNode(comment);
/*  910:     */   }
/*  911:     */   
/*  912:     */   public void add(Element element)
/*  913:     */   {
/*  914:1002 */     addNode(element);
/*  915:     */   }
/*  916:     */   
/*  917:     */   public void add(Entity entity)
/*  918:     */   {
/*  919:1006 */     addNode(entity);
/*  920:     */   }
/*  921:     */   
/*  922:     */   public void add(Namespace namespace)
/*  923:     */   {
/*  924:1010 */     addNode(namespace);
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void add(ProcessingInstruction pi)
/*  928:     */   {
/*  929:1014 */     addNode(pi);
/*  930:     */   }
/*  931:     */   
/*  932:     */   public void add(Text text)
/*  933:     */   {
/*  934:1018 */     addNode(text);
/*  935:     */   }
/*  936:     */   
/*  937:     */   public boolean remove(CDATA cdata)
/*  938:     */   {
/*  939:1022 */     return removeNode(cdata);
/*  940:     */   }
/*  941:     */   
/*  942:     */   public boolean remove(Comment comment)
/*  943:     */   {
/*  944:1026 */     return removeNode(comment);
/*  945:     */   }
/*  946:     */   
/*  947:     */   public boolean remove(Element element)
/*  948:     */   {
/*  949:1030 */     return removeNode(element);
/*  950:     */   }
/*  951:     */   
/*  952:     */   public boolean remove(Entity entity)
/*  953:     */   {
/*  954:1034 */     return removeNode(entity);
/*  955:     */   }
/*  956:     */   
/*  957:     */   public boolean remove(Namespace namespace)
/*  958:     */   {
/*  959:1038 */     return removeNode(namespace);
/*  960:     */   }
/*  961:     */   
/*  962:     */   public boolean remove(ProcessingInstruction pi)
/*  963:     */   {
/*  964:1042 */     return removeNode(pi);
/*  965:     */   }
/*  966:     */   
/*  967:     */   public boolean remove(Text text)
/*  968:     */   {
/*  969:1046 */     return removeNode(text);
/*  970:     */   }
/*  971:     */   
/*  972:     */   public boolean hasMixedContent()
/*  973:     */   {
/*  974:1052 */     List content = contentList();
/*  975:1054 */     if ((content == null) || (content.isEmpty()) || (content.size() < 2)) {
/*  976:1055 */       return false;
/*  977:     */     }
/*  978:1058 */     Class prevClass = null;
/*  979:1060 */     for (Iterator iter = content.iterator(); iter.hasNext();)
/*  980:     */     {
/*  981:1061 */       Object object = iter.next();
/*  982:     */       
/*  983:1063 */       Class newClass = object.getClass();
/*  984:1065 */       if (newClass != prevClass)
/*  985:     */       {
/*  986:1066 */         if (prevClass != null) {
/*  987:1067 */           return true;
/*  988:     */         }
/*  989:1070 */         prevClass = newClass;
/*  990:     */       }
/*  991:     */     }
/*  992:1074 */     return false;
/*  993:     */   }
/*  994:     */   
/*  995:     */   public boolean isTextOnly()
/*  996:     */   {
/*  997:1078 */     List content = contentList();
/*  998:1080 */     if ((content == null) || (content.isEmpty())) {
/*  999:1081 */       return true;
/* 1000:     */     }
/* 1001:1084 */     for (Iterator iter = content.iterator(); iter.hasNext();)
/* 1002:     */     {
/* 1003:1085 */       Object object = iter.next();
/* 1004:1087 */       if ((!(object instanceof CharacterData)) && (!(object instanceof String))) {
/* 1005:1089 */         return false;
/* 1006:     */       }
/* 1007:     */     }
/* 1008:1093 */     return true;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public void setText(String text)
/* 1012:     */   {
/* 1013:1098 */     List allContent = contentList();
/* 1014:1100 */     if (allContent != null)
/* 1015:     */     {
/* 1016:1101 */       Iterator it = allContent.iterator();
/* 1017:1103 */       while (it.hasNext())
/* 1018:     */       {
/* 1019:1104 */         Node node = (Node)it.next();
/* 1020:1106 */         switch (node.getNodeType())
/* 1021:     */         {
/* 1022:     */         case 3: 
/* 1023:     */         case 4: 
/* 1024:     */         case 5: 
/* 1025:1112 */           it.remove();
/* 1026:     */         }
/* 1027:     */       }
/* 1028:     */     }
/* 1029:1120 */     addText(text);
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   public String getStringValue()
/* 1033:     */   {
/* 1034:1124 */     List list = contentList();
/* 1035:     */     
/* 1036:1126 */     int size = list.size();
/* 1037:1128 */     if (size > 0)
/* 1038:     */     {
/* 1039:1129 */       if (size == 1) {
/* 1040:1131 */         return getContentAsStringValue(list.get(0));
/* 1041:     */       }
/* 1042:1133 */       StringBuffer buffer = new StringBuffer();
/* 1043:1135 */       for (int i = 0; i < size; i++)
/* 1044:     */       {
/* 1045:1136 */         Object node = list.get(i);
/* 1046:     */         
/* 1047:1138 */         String string = getContentAsStringValue(node);
/* 1048:1140 */         if (string.length() > 0) {
/* 1049:1147 */           buffer.append(string);
/* 1050:     */         }
/* 1051:     */       }
/* 1052:1151 */       return buffer.toString();
/* 1053:     */     }
/* 1054:1155 */     return "";
/* 1055:     */   }
/* 1056:     */   
/* 1057:     */   public void normalize()
/* 1058:     */   {
/* 1059:1176 */     List content = contentList();
/* 1060:     */     
/* 1061:1178 */     Text previousText = null;
/* 1062:     */     
/* 1063:1180 */     int i = 0;
/* 1064:1182 */     while (i < content.size())
/* 1065:     */     {
/* 1066:1183 */       Node node = (Node)content.get(i);
/* 1067:1185 */       if ((node instanceof Text))
/* 1068:     */       {
/* 1069:1186 */         Text text = (Text)node;
/* 1070:1188 */         if (previousText != null)
/* 1071:     */         {
/* 1072:1189 */           previousText.appendText(text.getText());
/* 1073:     */           
/* 1074:1191 */           remove(text);
/* 1075:     */         }
/* 1076:     */         else
/* 1077:     */         {
/* 1078:1193 */           String value = text.getText();
/* 1079:1197 */           if ((value == null) || (value.length() <= 0))
/* 1080:     */           {
/* 1081:1198 */             remove(text);
/* 1082:     */           }
/* 1083:     */           else
/* 1084:     */           {
/* 1085:1200 */             previousText = text;
/* 1086:     */             
/* 1087:1202 */             i++;
/* 1088:     */           }
/* 1089:     */         }
/* 1090:     */       }
/* 1091:     */       else
/* 1092:     */       {
/* 1093:1206 */         if ((node instanceof Element))
/* 1094:     */         {
/* 1095:1207 */           Element element = (Element)node;
/* 1096:     */           
/* 1097:1209 */           element.normalize();
/* 1098:     */         }
/* 1099:1212 */         previousText = null;
/* 1100:     */         
/* 1101:1214 */         i++;
/* 1102:     */       }
/* 1103:     */     }
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public String elementText(String name)
/* 1107:     */   {
/* 1108:1220 */     Element element = element(name);
/* 1109:     */     
/* 1110:1222 */     return element != null ? element.getText() : null;
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public String elementText(QName qName)
/* 1114:     */   {
/* 1115:1226 */     Element element = element(qName);
/* 1116:     */     
/* 1117:1228 */     return element != null ? element.getText() : null;
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public String elementTextTrim(String name)
/* 1121:     */   {
/* 1122:1232 */     Element element = element(name);
/* 1123:     */     
/* 1124:1234 */     return element != null ? element.getTextTrim() : null;
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   public String elementTextTrim(QName qName)
/* 1128:     */   {
/* 1129:1238 */     Element element = element(qName);
/* 1130:     */     
/* 1131:1240 */     return element != null ? element.getTextTrim() : null;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public void appendAttributes(Element element)
/* 1135:     */   {
/* 1136:1246 */     int i = 0;
/* 1137:1246 */     for (int size = element.attributeCount(); i < size; i++)
/* 1138:     */     {
/* 1139:1247 */       Attribute attribute = element.attribute(i);
/* 1140:1249 */       if (attribute.supportsParent()) {
/* 1141:1250 */         addAttribute(attribute.getQName(), attribute.getValue());
/* 1142:     */       } else {
/* 1143:1252 */         add(attribute);
/* 1144:     */       }
/* 1145:     */     }
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public Element createCopy()
/* 1149:     */   {
/* 1150:1271 */     Element clone = createElement(getQName());
/* 1151:     */     
/* 1152:1273 */     clone.appendAttributes(this);
/* 1153:     */     
/* 1154:1275 */     clone.appendContent(this);
/* 1155:     */     
/* 1156:1277 */     return clone;
/* 1157:     */   }
/* 1158:     */   
/* 1159:     */   public Element createCopy(String name)
/* 1160:     */   {
/* 1161:1281 */     Element clone = createElement(name);
/* 1162:     */     
/* 1163:1283 */     clone.appendAttributes(this);
/* 1164:     */     
/* 1165:1285 */     clone.appendContent(this);
/* 1166:     */     
/* 1167:1287 */     return clone;
/* 1168:     */   }
/* 1169:     */   
/* 1170:     */   public Element createCopy(QName qName)
/* 1171:     */   {
/* 1172:1291 */     Element clone = createElement(qName);
/* 1173:     */     
/* 1174:1293 */     clone.appendAttributes(this);
/* 1175:     */     
/* 1176:1295 */     clone.appendContent(this);
/* 1177:     */     
/* 1178:1297 */     return clone;
/* 1179:     */   }
/* 1180:     */   
/* 1181:     */   public QName getQName(String qualifiedName)
/* 1182:     */   {
/* 1183:1301 */     String prefix = "";
/* 1184:     */     
/* 1185:1303 */     String localName = qualifiedName;
/* 1186:     */     
/* 1187:1305 */     int index = qualifiedName.indexOf(":");
/* 1188:1307 */     if (index > 0)
/* 1189:     */     {
/* 1190:1308 */       prefix = qualifiedName.substring(0, index);
/* 1191:     */       
/* 1192:1310 */       localName = qualifiedName.substring(index + 1);
/* 1193:     */     }
/* 1194:1313 */     Namespace namespace = getNamespaceForPrefix(prefix);
/* 1195:1315 */     if (namespace != null) {
/* 1196:1316 */       return getDocumentFactory().createQName(localName, namespace);
/* 1197:     */     }
/* 1198:1318 */     return getDocumentFactory().createQName(localName);
/* 1199:     */   }
/* 1200:     */   
/* 1201:     */   public Namespace getNamespaceForPrefix(String prefix)
/* 1202:     */   {
/* 1203:1323 */     if (prefix == null) {
/* 1204:1324 */       prefix = "";
/* 1205:     */     }
/* 1206:1327 */     if (prefix.equals(getNamespacePrefix())) {
/* 1207:1328 */       return getNamespace();
/* 1208:     */     }
/* 1209:1329 */     if (prefix.equals("xml")) {
/* 1210:1330 */       return Namespace.XML_NAMESPACE;
/* 1211:     */     }
/* 1212:1332 */     List list = contentList();
/* 1213:     */     
/* 1214:1334 */     int size = list.size();
/* 1215:1336 */     for (int i = 0; i < size; i++)
/* 1216:     */     {
/* 1217:1337 */       Object object = list.get(i);
/* 1218:1339 */       if ((object instanceof Namespace))
/* 1219:     */       {
/* 1220:1340 */         Namespace namespace = (Namespace)object;
/* 1221:1342 */         if (prefix.equals(namespace.getPrefix())) {
/* 1222:1343 */           return namespace;
/* 1223:     */         }
/* 1224:     */       }
/* 1225:     */     }
/* 1226:1349 */     Element parent = getParent();
/* 1227:1351 */     if (parent != null)
/* 1228:     */     {
/* 1229:1352 */       Namespace answer = parent.getNamespaceForPrefix(prefix);
/* 1230:1354 */       if (answer != null) {
/* 1231:1355 */         return answer;
/* 1232:     */       }
/* 1233:     */     }
/* 1234:1359 */     if ((prefix == null) || (prefix.length() <= 0)) {
/* 1235:1360 */       return Namespace.NO_NAMESPACE;
/* 1236:     */     }
/* 1237:1363 */     return null;
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   public Namespace getNamespaceForURI(String uri)
/* 1241:     */   {
/* 1242:1367 */     if ((uri == null) || (uri.length() <= 0)) {
/* 1243:1368 */       return Namespace.NO_NAMESPACE;
/* 1244:     */     }
/* 1245:1369 */     if (uri.equals(getNamespaceURI())) {
/* 1246:1370 */       return getNamespace();
/* 1247:     */     }
/* 1248:1372 */     List list = contentList();
/* 1249:     */     
/* 1250:1374 */     int size = list.size();
/* 1251:1376 */     for (int i = 0; i < size; i++)
/* 1252:     */     {
/* 1253:1377 */       Object object = list.get(i);
/* 1254:1379 */       if ((object instanceof Namespace))
/* 1255:     */       {
/* 1256:1380 */         Namespace namespace = (Namespace)object;
/* 1257:1382 */         if (uri.equals(namespace.getURI())) {
/* 1258:1383 */           return namespace;
/* 1259:     */         }
/* 1260:     */       }
/* 1261:     */     }
/* 1262:1388 */     return null;
/* 1263:     */   }
/* 1264:     */   
/* 1265:     */   public List getNamespacesForURI(String uri)
/* 1266:     */   {
/* 1267:1393 */     BackedList answer = createResultList();
/* 1268:     */     
/* 1269:     */ 
/* 1270:     */ 
/* 1271:     */ 
/* 1272:     */ 
/* 1273:     */ 
/* 1274:1400 */     List list = contentList();
/* 1275:     */     
/* 1276:1402 */     int size = list.size();
/* 1277:1404 */     for (int i = 0; i < size; i++)
/* 1278:     */     {
/* 1279:1405 */       Object object = list.get(i);
/* 1280:1407 */       if (((object instanceof Namespace)) && (((Namespace)object).getURI().equals(uri))) {
/* 1281:1409 */         answer.addLocal(object);
/* 1282:     */       }
/* 1283:     */     }
/* 1284:1413 */     return answer;
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   public List declaredNamespaces()
/* 1288:     */   {
/* 1289:1417 */     BackedList answer = createResultList();
/* 1290:     */     
/* 1291:     */ 
/* 1292:     */ 
/* 1293:     */ 
/* 1294:     */ 
/* 1295:     */ 
/* 1296:     */ 
/* 1297:1425 */     List list = contentList();
/* 1298:     */     
/* 1299:1427 */     int size = list.size();
/* 1300:1429 */     for (int i = 0; i < size; i++)
/* 1301:     */     {
/* 1302:1430 */       Object object = list.get(i);
/* 1303:1432 */       if ((object instanceof Namespace)) {
/* 1304:1433 */         answer.addLocal(object);
/* 1305:     */       }
/* 1306:     */     }
/* 1307:1437 */     return answer;
/* 1308:     */   }
/* 1309:     */   
/* 1310:     */   public List additionalNamespaces()
/* 1311:     */   {
/* 1312:1441 */     List list = contentList();
/* 1313:     */     
/* 1314:1443 */     int size = list.size();
/* 1315:     */     
/* 1316:1445 */     BackedList answer = createResultList();
/* 1317:1447 */     for (int i = 0; i < size; i++)
/* 1318:     */     {
/* 1319:1448 */       Object object = list.get(i);
/* 1320:1450 */       if ((object instanceof Namespace))
/* 1321:     */       {
/* 1322:1451 */         Namespace namespace = (Namespace)object;
/* 1323:1453 */         if (!namespace.equals(getNamespace())) {
/* 1324:1454 */           answer.addLocal(namespace);
/* 1325:     */         }
/* 1326:     */       }
/* 1327:     */     }
/* 1328:1459 */     return answer;
/* 1329:     */   }
/* 1330:     */   
/* 1331:     */   public List additionalNamespaces(String defaultNamespaceURI)
/* 1332:     */   {
/* 1333:1463 */     List list = contentList();
/* 1334:     */     
/* 1335:1465 */     BackedList answer = createResultList();
/* 1336:     */     
/* 1337:1467 */     int size = list.size();
/* 1338:1469 */     for (int i = 0; i < size; i++)
/* 1339:     */     {
/* 1340:1470 */       Object object = list.get(i);
/* 1341:1472 */       if ((object instanceof Namespace))
/* 1342:     */       {
/* 1343:1473 */         Namespace namespace = (Namespace)object;
/* 1344:1475 */         if (!defaultNamespaceURI.equals(namespace.getURI())) {
/* 1345:1476 */           answer.addLocal(namespace);
/* 1346:     */         }
/* 1347:     */       }
/* 1348:     */     }
/* 1349:1481 */     return answer;
/* 1350:     */   }
/* 1351:     */   
/* 1352:     */   public void ensureAttributesCapacity(int minCapacity)
/* 1353:     */   {
/* 1354:1494 */     if (minCapacity > 1)
/* 1355:     */     {
/* 1356:1495 */       List list = attributeList();
/* 1357:1497 */       if ((list instanceof ArrayList))
/* 1358:     */       {
/* 1359:1498 */         ArrayList arrayList = (ArrayList)list;
/* 1360:     */         
/* 1361:1500 */         arrayList.ensureCapacity(minCapacity);
/* 1362:     */       }
/* 1363:     */     }
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   protected Element createElement(String name)
/* 1367:     */   {
/* 1368:1508 */     return getDocumentFactory().createElement(name);
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   protected Element createElement(QName qName)
/* 1372:     */   {
/* 1373:1512 */     return getDocumentFactory().createElement(qName);
/* 1374:     */   }
/* 1375:     */   
/* 1376:     */   protected void addNode(Node node)
/* 1377:     */   {
/* 1378:1516 */     if (node.getParent() != null)
/* 1379:     */     {
/* 1380:1518 */       String message = "The Node already has an existing parent of \"" + node.getParent().getQualifiedName() + "\"";
/* 1381:     */       
/* 1382:     */ 
/* 1383:1521 */       throw new IllegalAddException(this, node, message);
/* 1384:     */     }
/* 1385:1524 */     addNewNode(node);
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   protected void addNode(int index, Node node)
/* 1389:     */   {
/* 1390:1528 */     if (node.getParent() != null)
/* 1391:     */     {
/* 1392:1530 */       String message = "The Node already has an existing parent of \"" + node.getParent().getQualifiedName() + "\"";
/* 1393:     */       
/* 1394:     */ 
/* 1395:1533 */       throw new IllegalAddException(this, node, message);
/* 1396:     */     }
/* 1397:1536 */     addNewNode(index, node);
/* 1398:     */   }
/* 1399:     */   
/* 1400:     */   protected void addNewNode(Node node)
/* 1401:     */   {
/* 1402:1546 */     contentList().add(node);
/* 1403:     */     
/* 1404:1548 */     childAdded(node);
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   protected void addNewNode(int index, Node node)
/* 1408:     */   {
/* 1409:1552 */     contentList().add(index, node);
/* 1410:     */     
/* 1411:1554 */     childAdded(node);
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   protected boolean removeNode(Node node)
/* 1415:     */   {
/* 1416:1558 */     boolean answer = contentList().remove(node);
/* 1417:1560 */     if (answer) {
/* 1418:1561 */       childRemoved(node);
/* 1419:     */     }
/* 1420:1564 */     return answer;
/* 1421:     */   }
/* 1422:     */   
/* 1423:     */   protected void childAdded(Node node)
/* 1424:     */   {
/* 1425:1574 */     if (node != null) {
/* 1426:1575 */       node.setParent(this);
/* 1427:     */     }
/* 1428:     */   }
/* 1429:     */   
/* 1430:     */   protected void childRemoved(Node node)
/* 1431:     */   {
/* 1432:1580 */     if (node != null)
/* 1433:     */     {
/* 1434:1581 */       node.setParent(null);
/* 1435:     */       
/* 1436:1583 */       node.setDocument(null);
/* 1437:     */     }
/* 1438:     */   }
/* 1439:     */   
/* 1440:     */   protected abstract List attributeList();
/* 1441:     */   
/* 1442:     */   protected abstract List attributeList(int paramInt);
/* 1443:     */   
/* 1444:     */   protected DocumentFactory getDocumentFactory()
/* 1445:     */   {
/* 1446:1607 */     QName qName = getQName();
/* 1447:1610 */     if (qName != null)
/* 1448:     */     {
/* 1449:1611 */       DocumentFactory factory = qName.getDocumentFactory();
/* 1450:1613 */       if (factory != null) {
/* 1451:1614 */         return factory;
/* 1452:     */       }
/* 1453:     */     }
/* 1454:1618 */     return DOCUMENT_FACTORY;
/* 1455:     */   }
/* 1456:     */   
/* 1457:     */   protected List createAttributeList()
/* 1458:     */   {
/* 1459:1628 */     return createAttributeList(5);
/* 1460:     */   }
/* 1461:     */   
/* 1462:     */   protected List createAttributeList(int size)
/* 1463:     */   {
/* 1464:1641 */     return new ArrayList(size);
/* 1465:     */   }
/* 1466:     */   
/* 1467:     */   protected Iterator createSingleIterator(Object result)
/* 1468:     */   {
/* 1469:1645 */     return new SingleIterator(result);
/* 1470:     */   }
/* 1471:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractElement
 * JD-Core Version:    0.7.0.1
 */
/*    1:     */ package org.dom4j.tree;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Iterator;
/*    5:     */ import java.util.List;
/*    6:     */ import org.dom4j.Attribute;
/*    7:     */ import org.dom4j.Branch;
/*    8:     */ import org.dom4j.Document;
/*    9:     */ import org.dom4j.DocumentFactory;
/*   10:     */ import org.dom4j.Element;
/*   11:     */ import org.dom4j.IllegalAddException;
/*   12:     */ import org.dom4j.Namespace;
/*   13:     */ import org.dom4j.Node;
/*   14:     */ import org.dom4j.ProcessingInstruction;
/*   15:     */ import org.dom4j.QName;
/*   16:     */ 
/*   17:     */ public class DefaultElement
/*   18:     */   extends AbstractElement
/*   19:     */ {
/*   20:  36 */   private static final transient DocumentFactory DOCUMENT_FACTORY = ;
/*   21:     */   private QName qname;
/*   22:     */   private Branch parentBranch;
/*   23:     */   private Object content;
/*   24:     */   private Object attributes;
/*   25:     */   
/*   26:     */   public DefaultElement(String name)
/*   27:     */   {
/*   28:  61 */     this.qname = DOCUMENT_FACTORY.createQName(name);
/*   29:     */   }
/*   30:     */   
/*   31:     */   public DefaultElement(QName qname)
/*   32:     */   {
/*   33:  65 */     this.qname = qname;
/*   34:     */   }
/*   35:     */   
/*   36:     */   public DefaultElement(QName qname, int attributeCount)
/*   37:     */   {
/*   38:  69 */     this.qname = qname;
/*   39:  71 */     if (attributeCount > 1) {
/*   40:  72 */       this.attributes = new ArrayList(attributeCount);
/*   41:     */     }
/*   42:     */   }
/*   43:     */   
/*   44:     */   public DefaultElement(String name, Namespace namespace)
/*   45:     */   {
/*   46:  77 */     this.qname = DOCUMENT_FACTORY.createQName(name, namespace);
/*   47:     */   }
/*   48:     */   
/*   49:     */   public Element getParent()
/*   50:     */   {
/*   51:  81 */     Element result = null;
/*   52:  83 */     if ((this.parentBranch instanceof Element)) {
/*   53:  84 */       result = (Element)this.parentBranch;
/*   54:     */     }
/*   55:  87 */     return result;
/*   56:     */   }
/*   57:     */   
/*   58:     */   public void setParent(Element parent)
/*   59:     */   {
/*   60:  91 */     if (((this.parentBranch instanceof Element)) || (parent != null)) {
/*   61:  92 */       this.parentBranch = parent;
/*   62:     */     }
/*   63:     */   }
/*   64:     */   
/*   65:     */   public Document getDocument()
/*   66:     */   {
/*   67:  97 */     if ((this.parentBranch instanceof Document)) {
/*   68:  98 */       return (Document)this.parentBranch;
/*   69:     */     }
/*   70:  99 */     if ((this.parentBranch instanceof Element))
/*   71:     */     {
/*   72: 100 */       Element parent = (Element)this.parentBranch;
/*   73:     */       
/*   74: 102 */       return parent.getDocument();
/*   75:     */     }
/*   76: 105 */     return null;
/*   77:     */   }
/*   78:     */   
/*   79:     */   public void setDocument(Document document)
/*   80:     */   {
/*   81: 109 */     if (((this.parentBranch instanceof Document)) || (document != null)) {
/*   82: 110 */       this.parentBranch = document;
/*   83:     */     }
/*   84:     */   }
/*   85:     */   
/*   86:     */   public boolean supportsParent()
/*   87:     */   {
/*   88: 115 */     return true;
/*   89:     */   }
/*   90:     */   
/*   91:     */   public QName getQName()
/*   92:     */   {
/*   93: 119 */     return this.qname;
/*   94:     */   }
/*   95:     */   
/*   96:     */   public void setQName(QName name)
/*   97:     */   {
/*   98: 123 */     this.qname = name;
/*   99:     */   }
/*  100:     */   
/*  101:     */   public String getText()
/*  102:     */   {
/*  103: 127 */     Object contentShadow = this.content;
/*  104: 129 */     if ((contentShadow instanceof List)) {
/*  105: 130 */       return super.getText();
/*  106:     */     }
/*  107: 132 */     if (contentShadow != null) {
/*  108: 133 */       return getContentAsText(contentShadow);
/*  109:     */     }
/*  110: 135 */     return "";
/*  111:     */   }
/*  112:     */   
/*  113:     */   public String getStringValue()
/*  114:     */   {
/*  115: 141 */     Object contentShadow = this.content;
/*  116: 143 */     if ((contentShadow instanceof List))
/*  117:     */     {
/*  118: 144 */       List list = (List)contentShadow;
/*  119:     */       
/*  120: 146 */       int size = list.size();
/*  121: 148 */       if (size > 0)
/*  122:     */       {
/*  123: 149 */         if (size == 1) {
/*  124: 151 */           return getContentAsStringValue(list.get(0));
/*  125:     */         }
/*  126: 153 */         StringBuffer buffer = new StringBuffer();
/*  127: 155 */         for (int i = 0; i < size; i++)
/*  128:     */         {
/*  129: 156 */           Object node = list.get(i);
/*  130:     */           
/*  131: 158 */           String string = getContentAsStringValue(node);
/*  132: 160 */           if (string.length() > 0) {
/*  133: 167 */             buffer.append(string);
/*  134:     */           }
/*  135:     */         }
/*  136: 171 */         return buffer.toString();
/*  137:     */       }
/*  138:     */     }
/*  139: 175 */     else if (contentShadow != null)
/*  140:     */     {
/*  141: 176 */       return getContentAsStringValue(contentShadow);
/*  142:     */     }
/*  143: 180 */     return "";
/*  144:     */   }
/*  145:     */   
/*  146:     */   public Object clone()
/*  147:     */   {
/*  148: 184 */     DefaultElement answer = (DefaultElement)super.clone();
/*  149: 186 */     if (answer != this)
/*  150:     */     {
/*  151: 187 */       answer.content = null;
/*  152:     */       
/*  153: 189 */       answer.attributes = null;
/*  154:     */       
/*  155: 191 */       answer.appendAttributes(this);
/*  156:     */       
/*  157: 193 */       answer.appendContent(this);
/*  158:     */     }
/*  159: 196 */     return answer;
/*  160:     */   }
/*  161:     */   
/*  162:     */   public Namespace getNamespaceForPrefix(String prefix)
/*  163:     */   {
/*  164: 200 */     if (prefix == null) {
/*  165: 201 */       prefix = "";
/*  166:     */     }
/*  167: 204 */     if (prefix.equals(getNamespacePrefix())) {
/*  168: 205 */       return getNamespace();
/*  169:     */     }
/*  170: 206 */     if (prefix.equals("xml")) {
/*  171: 207 */       return Namespace.XML_NAMESPACE;
/*  172:     */     }
/*  173: 209 */     Object contentShadow = this.content;
/*  174: 211 */     if ((contentShadow instanceof List))
/*  175:     */     {
/*  176: 212 */       List list = (List)contentShadow;
/*  177:     */       
/*  178: 214 */       int size = list.size();
/*  179: 216 */       for (int i = 0; i < size; i++)
/*  180:     */       {
/*  181: 217 */         Object object = list.get(i);
/*  182: 219 */         if ((object instanceof Namespace))
/*  183:     */         {
/*  184: 220 */           Namespace namespace = (Namespace)object;
/*  185: 222 */           if (prefix.equals(namespace.getPrefix())) {
/*  186: 223 */             return namespace;
/*  187:     */           }
/*  188:     */         }
/*  189:     */       }
/*  190:     */     }
/*  191: 227 */     else if ((contentShadow instanceof Namespace))
/*  192:     */     {
/*  193: 228 */       Namespace namespace = (Namespace)contentShadow;
/*  194: 230 */       if (prefix.equals(namespace.getPrefix())) {
/*  195: 231 */         return namespace;
/*  196:     */       }
/*  197:     */     }
/*  198: 236 */     Element parent = getParent();
/*  199: 238 */     if (parent != null)
/*  200:     */     {
/*  201: 239 */       Namespace answer = parent.getNamespaceForPrefix(prefix);
/*  202: 241 */       if (answer != null) {
/*  203: 242 */         return answer;
/*  204:     */       }
/*  205:     */     }
/*  206: 246 */     if ((prefix == null) || (prefix.length() <= 0)) {
/*  207: 247 */       return Namespace.NO_NAMESPACE;
/*  208:     */     }
/*  209: 250 */     return null;
/*  210:     */   }
/*  211:     */   
/*  212:     */   public Namespace getNamespaceForURI(String uri)
/*  213:     */   {
/*  214: 254 */     if ((uri == null) || (uri.length() <= 0)) {
/*  215: 255 */       return Namespace.NO_NAMESPACE;
/*  216:     */     }
/*  217: 256 */     if (uri.equals(getNamespaceURI())) {
/*  218: 257 */       return getNamespace();
/*  219:     */     }
/*  220: 259 */     Object contentShadow = this.content;
/*  221: 261 */     if ((contentShadow instanceof List))
/*  222:     */     {
/*  223: 262 */       List list = (List)contentShadow;
/*  224:     */       
/*  225: 264 */       int size = list.size();
/*  226: 266 */       for (int i = 0; i < size; i++)
/*  227:     */       {
/*  228: 267 */         Object object = list.get(i);
/*  229: 269 */         if ((object instanceof Namespace))
/*  230:     */         {
/*  231: 270 */           Namespace namespace = (Namespace)object;
/*  232: 272 */           if (uri.equals(namespace.getURI())) {
/*  233: 273 */             return namespace;
/*  234:     */           }
/*  235:     */         }
/*  236:     */       }
/*  237:     */     }
/*  238: 277 */     else if ((contentShadow instanceof Namespace))
/*  239:     */     {
/*  240: 278 */       Namespace namespace = (Namespace)contentShadow;
/*  241: 280 */       if (uri.equals(namespace.getURI())) {
/*  242: 281 */         return namespace;
/*  243:     */       }
/*  244:     */     }
/*  245: 285 */     Element parent = getParent();
/*  246: 287 */     if (parent != null) {
/*  247: 288 */       return parent.getNamespaceForURI(uri);
/*  248:     */     }
/*  249: 291 */     return null;
/*  250:     */   }
/*  251:     */   
/*  252:     */   public List declaredNamespaces()
/*  253:     */   {
/*  254: 296 */     BackedList answer = createResultList();
/*  255:     */     
/*  256:     */ 
/*  257:     */ 
/*  258:     */ 
/*  259:     */ 
/*  260:     */ 
/*  261: 303 */     Object contentShadow = this.content;
/*  262: 305 */     if ((contentShadow instanceof List))
/*  263:     */     {
/*  264: 306 */       List list = (List)contentShadow;
/*  265:     */       
/*  266: 308 */       int size = list.size();
/*  267: 310 */       for (int i = 0; i < size; i++)
/*  268:     */       {
/*  269: 311 */         Object object = list.get(i);
/*  270: 313 */         if ((object instanceof Namespace)) {
/*  271: 314 */           answer.addLocal(object);
/*  272:     */         }
/*  273:     */       }
/*  274:     */     }
/*  275: 318 */     else if ((contentShadow instanceof Namespace))
/*  276:     */     {
/*  277: 319 */       answer.addLocal(contentShadow);
/*  278:     */     }
/*  279: 323 */     return answer;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public List additionalNamespaces()
/*  283:     */   {
/*  284: 327 */     Object contentShadow = this.content;
/*  285: 329 */     if ((contentShadow instanceof List))
/*  286:     */     {
/*  287: 330 */       List list = (List)contentShadow;
/*  288:     */       
/*  289: 332 */       int size = list.size();
/*  290:     */       
/*  291: 334 */       BackedList answer = createResultList();
/*  292: 336 */       for (int i = 0; i < size; i++)
/*  293:     */       {
/*  294: 337 */         Object object = list.get(i);
/*  295: 339 */         if ((object instanceof Namespace))
/*  296:     */         {
/*  297: 340 */           Namespace namespace = (Namespace)object;
/*  298: 342 */           if (!namespace.equals(getNamespace())) {
/*  299: 343 */             answer.addLocal(namespace);
/*  300:     */           }
/*  301:     */         }
/*  302:     */       }
/*  303: 348 */       return answer;
/*  304:     */     }
/*  305: 350 */     if ((contentShadow instanceof Namespace))
/*  306:     */     {
/*  307: 351 */       Namespace namespace = (Namespace)contentShadow;
/*  308: 353 */       if (namespace.equals(getNamespace())) {
/*  309: 354 */         return createEmptyList();
/*  310:     */       }
/*  311: 357 */       return createSingleResultList(namespace);
/*  312:     */     }
/*  313: 359 */     return createEmptyList();
/*  314:     */   }
/*  315:     */   
/*  316:     */   public List additionalNamespaces(String defaultNamespaceURI)
/*  317:     */   {
/*  318: 365 */     Object contentShadow = this.content;
/*  319: 367 */     if ((contentShadow instanceof List))
/*  320:     */     {
/*  321: 368 */       List list = (List)contentShadow;
/*  322:     */       
/*  323: 370 */       BackedList answer = createResultList();
/*  324:     */       
/*  325: 372 */       int size = list.size();
/*  326: 374 */       for (int i = 0; i < size; i++)
/*  327:     */       {
/*  328: 375 */         Object object = list.get(i);
/*  329: 377 */         if ((object instanceof Namespace))
/*  330:     */         {
/*  331: 378 */           Namespace namespace = (Namespace)object;
/*  332: 380 */           if (!defaultNamespaceURI.equals(namespace.getURI())) {
/*  333: 381 */             answer.addLocal(namespace);
/*  334:     */           }
/*  335:     */         }
/*  336:     */       }
/*  337: 386 */       return answer;
/*  338:     */     }
/*  339: 388 */     if ((contentShadow instanceof Namespace))
/*  340:     */     {
/*  341: 389 */       Namespace namespace = (Namespace)contentShadow;
/*  342: 391 */       if (!defaultNamespaceURI.equals(namespace.getURI())) {
/*  343: 392 */         return createSingleResultList(namespace);
/*  344:     */       }
/*  345:     */     }
/*  346: 397 */     return createEmptyList();
/*  347:     */   }
/*  348:     */   
/*  349:     */   public List processingInstructions()
/*  350:     */   {
/*  351: 402 */     Object contentShadow = this.content;
/*  352: 404 */     if ((contentShadow instanceof List))
/*  353:     */     {
/*  354: 405 */       List list = (List)contentShadow;
/*  355:     */       
/*  356: 407 */       BackedList answer = createResultList();
/*  357:     */       
/*  358: 409 */       int size = list.size();
/*  359: 411 */       for (int i = 0; i < size; i++)
/*  360:     */       {
/*  361: 412 */         Object object = list.get(i);
/*  362: 414 */         if ((object instanceof ProcessingInstruction)) {
/*  363: 415 */           answer.addLocal(object);
/*  364:     */         }
/*  365:     */       }
/*  366: 419 */       return answer;
/*  367:     */     }
/*  368: 421 */     if ((contentShadow instanceof ProcessingInstruction)) {
/*  369: 422 */       return createSingleResultList(contentShadow);
/*  370:     */     }
/*  371: 425 */     return createEmptyList();
/*  372:     */   }
/*  373:     */   
/*  374:     */   public List processingInstructions(String target)
/*  375:     */   {
/*  376: 430 */     Object shadow = this.content;
/*  377: 432 */     if ((shadow instanceof List))
/*  378:     */     {
/*  379: 433 */       List list = (List)shadow;
/*  380:     */       
/*  381: 435 */       BackedList answer = createResultList();
/*  382:     */       
/*  383: 437 */       int size = list.size();
/*  384: 439 */       for (int i = 0; i < size; i++)
/*  385:     */       {
/*  386: 440 */         Object object = list.get(i);
/*  387: 442 */         if ((object instanceof ProcessingInstruction))
/*  388:     */         {
/*  389: 443 */           ProcessingInstruction pi = (ProcessingInstruction)object;
/*  390: 445 */           if (target.equals(pi.getName())) {
/*  391: 446 */             answer.addLocal(pi);
/*  392:     */           }
/*  393:     */         }
/*  394:     */       }
/*  395: 451 */       return answer;
/*  396:     */     }
/*  397: 453 */     if ((shadow instanceof ProcessingInstruction))
/*  398:     */     {
/*  399: 454 */       ProcessingInstruction pi = (ProcessingInstruction)shadow;
/*  400: 456 */       if (target.equals(pi.getName())) {
/*  401: 457 */         return createSingleResultList(pi);
/*  402:     */       }
/*  403:     */     }
/*  404: 461 */     return createEmptyList();
/*  405:     */   }
/*  406:     */   
/*  407:     */   public ProcessingInstruction processingInstruction(String target)
/*  408:     */   {
/*  409: 466 */     Object shadow = this.content;
/*  410: 468 */     if ((shadow instanceof List))
/*  411:     */     {
/*  412: 469 */       List list = (List)shadow;
/*  413:     */       
/*  414: 471 */       int size = list.size();
/*  415: 473 */       for (int i = 0; i < size; i++)
/*  416:     */       {
/*  417: 474 */         Object object = list.get(i);
/*  418: 476 */         if ((object instanceof ProcessingInstruction))
/*  419:     */         {
/*  420: 477 */           ProcessingInstruction pi = (ProcessingInstruction)object;
/*  421: 479 */           if (target.equals(pi.getName())) {
/*  422: 480 */             return pi;
/*  423:     */           }
/*  424:     */         }
/*  425:     */       }
/*  426:     */     }
/*  427: 485 */     else if ((shadow instanceof ProcessingInstruction))
/*  428:     */     {
/*  429: 486 */       ProcessingInstruction pi = (ProcessingInstruction)shadow;
/*  430: 488 */       if (target.equals(pi.getName())) {
/*  431: 489 */         return pi;
/*  432:     */       }
/*  433:     */     }
/*  434: 494 */     return null;
/*  435:     */   }
/*  436:     */   
/*  437:     */   public boolean removeProcessingInstruction(String target)
/*  438:     */   {
/*  439: 498 */     Object shadow = this.content;
/*  440:     */     Iterator iter;
/*  441: 500 */     if ((shadow instanceof List))
/*  442:     */     {
/*  443: 501 */       List list = (List)shadow;
/*  444: 503 */       for (iter = list.iterator(); iter.hasNext();)
/*  445:     */       {
/*  446: 504 */         Object object = iter.next();
/*  447: 506 */         if ((object instanceof ProcessingInstruction))
/*  448:     */         {
/*  449: 507 */           ProcessingInstruction pi = (ProcessingInstruction)object;
/*  450: 509 */           if (target.equals(pi.getName()))
/*  451:     */           {
/*  452: 510 */             iter.remove();
/*  453:     */             
/*  454: 512 */             return true;
/*  455:     */           }
/*  456:     */         }
/*  457:     */       }
/*  458:     */     }
/*  459: 517 */     else if ((shadow instanceof ProcessingInstruction))
/*  460:     */     {
/*  461: 518 */       ProcessingInstruction pi = (ProcessingInstruction)shadow;
/*  462: 520 */       if (target.equals(pi.getName()))
/*  463:     */       {
/*  464: 521 */         this.content = null;
/*  465:     */         
/*  466: 523 */         return true;
/*  467:     */       }
/*  468:     */     }
/*  469: 528 */     return false;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public Element element(String name)
/*  473:     */   {
/*  474: 532 */     Object contentShadow = this.content;
/*  475: 534 */     if ((contentShadow instanceof List))
/*  476:     */     {
/*  477: 535 */       List list = (List)contentShadow;
/*  478:     */       
/*  479: 537 */       int size = list.size();
/*  480: 539 */       for (int i = 0; i < size; i++)
/*  481:     */       {
/*  482: 540 */         Object object = list.get(i);
/*  483: 542 */         if ((object instanceof Element))
/*  484:     */         {
/*  485: 543 */           Element element = (Element)object;
/*  486: 545 */           if (name.equals(element.getName())) {
/*  487: 546 */             return element;
/*  488:     */           }
/*  489:     */         }
/*  490:     */       }
/*  491:     */     }
/*  492: 551 */     else if ((contentShadow instanceof Element))
/*  493:     */     {
/*  494: 552 */       Element element = (Element)contentShadow;
/*  495: 554 */       if (name.equals(element.getName())) {
/*  496: 555 */         return element;
/*  497:     */       }
/*  498:     */     }
/*  499: 560 */     return null;
/*  500:     */   }
/*  501:     */   
/*  502:     */   public Element element(QName qName)
/*  503:     */   {
/*  504: 564 */     Object contentShadow = this.content;
/*  505: 566 */     if ((contentShadow instanceof List))
/*  506:     */     {
/*  507: 567 */       List list = (List)contentShadow;
/*  508:     */       
/*  509: 569 */       int size = list.size();
/*  510: 571 */       for (int i = 0; i < size; i++)
/*  511:     */       {
/*  512: 572 */         Object object = list.get(i);
/*  513: 574 */         if ((object instanceof Element))
/*  514:     */         {
/*  515: 575 */           Element element = (Element)object;
/*  516: 577 */           if (qName.equals(element.getQName())) {
/*  517: 578 */             return element;
/*  518:     */           }
/*  519:     */         }
/*  520:     */       }
/*  521:     */     }
/*  522: 583 */     else if ((contentShadow instanceof Element))
/*  523:     */     {
/*  524: 584 */       Element element = (Element)contentShadow;
/*  525: 586 */       if (qName.equals(element.getQName())) {
/*  526: 587 */         return element;
/*  527:     */       }
/*  528:     */     }
/*  529: 592 */     return null;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public Element element(String name, Namespace namespace)
/*  533:     */   {
/*  534: 596 */     return element(getDocumentFactory().createQName(name, namespace));
/*  535:     */   }
/*  536:     */   
/*  537:     */   public void setContent(List content)
/*  538:     */   {
/*  539: 600 */     contentRemoved();
/*  540: 602 */     if ((content instanceof ContentListFacade)) {
/*  541: 603 */       content = ((ContentListFacade)content).getBackingList();
/*  542:     */     }
/*  543: 606 */     if (content == null)
/*  544:     */     {
/*  545: 607 */       this.content = null;
/*  546:     */     }
/*  547:     */     else
/*  548:     */     {
/*  549: 609 */       int size = content.size();
/*  550:     */       
/*  551: 611 */       List newContent = createContentList(size);
/*  552: 613 */       for (int i = 0; i < size; i++)
/*  553:     */       {
/*  554: 614 */         Object object = content.get(i);
/*  555: 616 */         if ((object instanceof Node))
/*  556:     */         {
/*  557: 617 */           Node node = (Node)object;
/*  558: 618 */           Element parent = node.getParent();
/*  559: 620 */           if ((parent != null) && (parent != this)) {
/*  560: 621 */             node = (Node)node.clone();
/*  561:     */           }
/*  562: 624 */           newContent.add(node);
/*  563: 625 */           childAdded(node);
/*  564:     */         }
/*  565: 626 */         else if (object != null)
/*  566:     */         {
/*  567: 627 */           String text = object.toString();
/*  568: 628 */           Node node = getDocumentFactory().createText(text);
/*  569: 629 */           newContent.add(node);
/*  570: 630 */           childAdded(node);
/*  571:     */         }
/*  572:     */       }
/*  573: 634 */       this.content = newContent;
/*  574:     */     }
/*  575:     */   }
/*  576:     */   
/*  577:     */   public void clearContent()
/*  578:     */   {
/*  579: 639 */     if (this.content != null)
/*  580:     */     {
/*  581: 640 */       contentRemoved();
/*  582:     */       
/*  583: 642 */       this.content = null;
/*  584:     */     }
/*  585:     */   }
/*  586:     */   
/*  587:     */   public Node node(int index)
/*  588:     */   {
/*  589: 647 */     if (index >= 0)
/*  590:     */     {
/*  591: 648 */       Object contentShadow = this.content;
/*  592:     */       Object node;
/*  593:     */       Object node;
/*  594: 651 */       if ((contentShadow instanceof List))
/*  595:     */       {
/*  596: 652 */         List list = (List)contentShadow;
/*  597: 654 */         if (index >= list.size()) {
/*  598: 655 */           return null;
/*  599:     */         }
/*  600: 658 */         node = list.get(index);
/*  601:     */       }
/*  602:     */       else
/*  603:     */       {
/*  604: 660 */         node = index == 0 ? contentShadow : null;
/*  605:     */       }
/*  606: 663 */       if (node != null)
/*  607:     */       {
/*  608: 664 */         if ((node instanceof Node)) {
/*  609: 665 */           return (Node)node;
/*  610:     */         }
/*  611: 667 */         return new DefaultText(node.toString());
/*  612:     */       }
/*  613:     */     }
/*  614: 672 */     return null;
/*  615:     */   }
/*  616:     */   
/*  617:     */   public int indexOf(Node node)
/*  618:     */   {
/*  619: 676 */     Object contentShadow = this.content;
/*  620: 678 */     if ((contentShadow instanceof List))
/*  621:     */     {
/*  622: 679 */       List list = (List)contentShadow;
/*  623:     */       
/*  624: 681 */       return list.indexOf(node);
/*  625:     */     }
/*  626: 683 */     if ((contentShadow != null) && (contentShadow.equals(node))) {
/*  627: 684 */       return 0;
/*  628:     */     }
/*  629: 686 */     return -1;
/*  630:     */   }
/*  631:     */   
/*  632:     */   public int nodeCount()
/*  633:     */   {
/*  634: 692 */     Object contentShadow = this.content;
/*  635: 694 */     if ((contentShadow instanceof List))
/*  636:     */     {
/*  637: 695 */       List list = (List)contentShadow;
/*  638:     */       
/*  639: 697 */       return list.size();
/*  640:     */     }
/*  641: 699 */     return contentShadow != null ? 1 : 0;
/*  642:     */   }
/*  643:     */   
/*  644:     */   public Iterator nodeIterator()
/*  645:     */   {
/*  646: 704 */     Object contentShadow = this.content;
/*  647: 706 */     if ((contentShadow instanceof List))
/*  648:     */     {
/*  649: 707 */       List list = (List)contentShadow;
/*  650:     */       
/*  651: 709 */       return list.iterator();
/*  652:     */     }
/*  653: 711 */     if (contentShadow != null) {
/*  654: 712 */       return createSingleIterator(contentShadow);
/*  655:     */     }
/*  656: 714 */     return EMPTY_ITERATOR;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public List attributes()
/*  660:     */   {
/*  661: 720 */     return new ContentListFacade(this, attributeList());
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void setAttributes(List attributes)
/*  665:     */   {
/*  666: 724 */     if ((attributes instanceof ContentListFacade)) {
/*  667: 725 */       attributes = ((ContentListFacade)attributes).getBackingList();
/*  668:     */     }
/*  669: 728 */     this.attributes = attributes;
/*  670:     */   }
/*  671:     */   
/*  672:     */   public Iterator attributeIterator()
/*  673:     */   {
/*  674: 732 */     Object attributesShadow = this.attributes;
/*  675: 734 */     if ((attributesShadow instanceof List))
/*  676:     */     {
/*  677: 735 */       List list = (List)attributesShadow;
/*  678:     */       
/*  679: 737 */       return list.iterator();
/*  680:     */     }
/*  681: 738 */     if (attributesShadow != null) {
/*  682: 739 */       return createSingleIterator(attributesShadow);
/*  683:     */     }
/*  684: 741 */     return EMPTY_ITERATOR;
/*  685:     */   }
/*  686:     */   
/*  687:     */   public Attribute attribute(int index)
/*  688:     */   {
/*  689: 746 */     Object attributesShadow = this.attributes;
/*  690: 748 */     if ((attributesShadow instanceof List))
/*  691:     */     {
/*  692: 749 */       List list = (List)attributesShadow;
/*  693:     */       
/*  694: 751 */       return (Attribute)list.get(index);
/*  695:     */     }
/*  696: 752 */     if ((attributesShadow != null) && (index == 0)) {
/*  697: 753 */       return (Attribute)attributesShadow;
/*  698:     */     }
/*  699: 755 */     return null;
/*  700:     */   }
/*  701:     */   
/*  702:     */   public int attributeCount()
/*  703:     */   {
/*  704: 760 */     Object attributesShadow = this.attributes;
/*  705: 762 */     if ((attributesShadow instanceof List))
/*  706:     */     {
/*  707: 763 */       List list = (List)attributesShadow;
/*  708:     */       
/*  709: 765 */       return list.size();
/*  710:     */     }
/*  711: 767 */     return attributesShadow != null ? 1 : 0;
/*  712:     */   }
/*  713:     */   
/*  714:     */   public Attribute attribute(String name)
/*  715:     */   {
/*  716: 772 */     Object attributesShadow = this.attributes;
/*  717: 774 */     if ((attributesShadow instanceof List))
/*  718:     */     {
/*  719: 775 */       List list = (List)attributesShadow;
/*  720:     */       
/*  721: 777 */       int size = list.size();
/*  722: 779 */       for (int i = 0; i < size; i++)
/*  723:     */       {
/*  724: 780 */         Attribute attribute = (Attribute)list.get(i);
/*  725: 782 */         if (name.equals(attribute.getName())) {
/*  726: 783 */           return attribute;
/*  727:     */         }
/*  728:     */       }
/*  729:     */     }
/*  730: 786 */     else if (attributesShadow != null)
/*  731:     */     {
/*  732: 787 */       Attribute attribute = (Attribute)attributesShadow;
/*  733: 789 */       if (name.equals(attribute.getName())) {
/*  734: 790 */         return attribute;
/*  735:     */       }
/*  736:     */     }
/*  737: 794 */     return null;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public Attribute attribute(QName qName)
/*  741:     */   {
/*  742: 798 */     Object attributesShadow = this.attributes;
/*  743: 800 */     if ((attributesShadow instanceof List))
/*  744:     */     {
/*  745: 801 */       List list = (List)attributesShadow;
/*  746:     */       
/*  747: 803 */       int size = list.size();
/*  748: 805 */       for (int i = 0; i < size; i++)
/*  749:     */       {
/*  750: 806 */         Attribute attribute = (Attribute)list.get(i);
/*  751: 808 */         if (qName.equals(attribute.getQName())) {
/*  752: 809 */           return attribute;
/*  753:     */         }
/*  754:     */       }
/*  755:     */     }
/*  756: 812 */     else if (attributesShadow != null)
/*  757:     */     {
/*  758: 813 */       Attribute attribute = (Attribute)attributesShadow;
/*  759: 815 */       if (qName.equals(attribute.getQName())) {
/*  760: 816 */         return attribute;
/*  761:     */       }
/*  762:     */     }
/*  763: 820 */     return null;
/*  764:     */   }
/*  765:     */   
/*  766:     */   public Attribute attribute(String name, Namespace namespace)
/*  767:     */   {
/*  768: 824 */     return attribute(getDocumentFactory().createQName(name, namespace));
/*  769:     */   }
/*  770:     */   
/*  771:     */   public void add(Attribute attribute)
/*  772:     */   {
/*  773: 828 */     if (attribute.getParent() != null)
/*  774:     */     {
/*  775: 829 */       String message = "The Attribute already has an existing parent \"" + attribute.getParent().getQualifiedName() + "\"";
/*  776:     */       
/*  777:     */ 
/*  778: 832 */       throw new IllegalAddException(this, attribute, message);
/*  779:     */     }
/*  780: 835 */     if (attribute.getValue() == null)
/*  781:     */     {
/*  782: 839 */       Attribute oldAttribute = attribute(attribute.getQName());
/*  783: 841 */       if (oldAttribute != null) {
/*  784: 842 */         remove(oldAttribute);
/*  785:     */       }
/*  786:     */     }
/*  787:     */     else
/*  788:     */     {
/*  789: 845 */       if (this.attributes == null) {
/*  790: 846 */         this.attributes = attribute;
/*  791:     */       } else {
/*  792: 848 */         attributeList().add(attribute);
/*  793:     */       }
/*  794: 851 */       childAdded(attribute);
/*  795:     */     }
/*  796:     */   }
/*  797:     */   
/*  798:     */   public boolean remove(Attribute attribute)
/*  799:     */   {
/*  800: 856 */     boolean answer = false;
/*  801: 857 */     Object attributesShadow = this.attributes;
/*  802: 859 */     if ((attributesShadow instanceof List))
/*  803:     */     {
/*  804: 860 */       List list = (List)attributesShadow;
/*  805:     */       
/*  806: 862 */       answer = list.remove(attribute);
/*  807: 864 */       if (!answer)
/*  808:     */       {
/*  809: 866 */         Attribute copy = attribute(attribute.getQName());
/*  810: 868 */         if (copy != null)
/*  811:     */         {
/*  812: 869 */           list.remove(copy);
/*  813:     */           
/*  814: 871 */           answer = true;
/*  815:     */         }
/*  816:     */       }
/*  817:     */     }
/*  818: 874 */     else if (attributesShadow != null)
/*  819:     */     {
/*  820: 875 */       if (attribute.equals(attributesShadow))
/*  821:     */       {
/*  822: 876 */         this.attributes = null;
/*  823:     */         
/*  824: 878 */         answer = true;
/*  825:     */       }
/*  826:     */       else
/*  827:     */       {
/*  828: 881 */         Attribute other = (Attribute)attributesShadow;
/*  829: 883 */         if (attribute.getQName().equals(other.getQName()))
/*  830:     */         {
/*  831: 884 */           this.attributes = null;
/*  832:     */           
/*  833: 886 */           answer = true;
/*  834:     */         }
/*  835:     */       }
/*  836:     */     }
/*  837: 891 */     if (answer) {
/*  838: 892 */       childRemoved(attribute);
/*  839:     */     }
/*  840: 895 */     return answer;
/*  841:     */   }
/*  842:     */   
/*  843:     */   protected void addNewNode(Node node)
/*  844:     */   {
/*  845: 901 */     Object contentShadow = this.content;
/*  846: 903 */     if (contentShadow == null)
/*  847:     */     {
/*  848: 904 */       this.content = node;
/*  849:     */     }
/*  850: 906 */     else if ((contentShadow instanceof List))
/*  851:     */     {
/*  852: 907 */       List list = (List)contentShadow;
/*  853:     */       
/*  854: 909 */       list.add(node);
/*  855:     */     }
/*  856:     */     else
/*  857:     */     {
/*  858: 911 */       List list = createContentList();
/*  859:     */       
/*  860: 913 */       list.add(contentShadow);
/*  861:     */       
/*  862: 915 */       list.add(node);
/*  863:     */       
/*  864: 917 */       this.content = list;
/*  865:     */     }
/*  866: 921 */     childAdded(node);
/*  867:     */   }
/*  868:     */   
/*  869:     */   protected boolean removeNode(Node node)
/*  870:     */   {
/*  871: 925 */     boolean answer = false;
/*  872: 926 */     Object contentShadow = this.content;
/*  873: 928 */     if (contentShadow != null) {
/*  874: 929 */       if (contentShadow == node)
/*  875:     */       {
/*  876: 930 */         this.content = null;
/*  877:     */         
/*  878: 932 */         answer = true;
/*  879:     */       }
/*  880: 933 */       else if ((contentShadow instanceof List))
/*  881:     */       {
/*  882: 934 */         List list = (List)contentShadow;
/*  883:     */         
/*  884: 936 */         answer = list.remove(node);
/*  885:     */       }
/*  886:     */     }
/*  887: 940 */     if (answer) {
/*  888: 941 */       childRemoved(node);
/*  889:     */     }
/*  890: 944 */     return answer;
/*  891:     */   }
/*  892:     */   
/*  893:     */   protected List contentList()
/*  894:     */   {
/*  895: 948 */     Object contentShadow = this.content;
/*  896: 950 */     if ((contentShadow instanceof List)) {
/*  897: 951 */       return (List)contentShadow;
/*  898:     */     }
/*  899: 953 */     List list = createContentList();
/*  900: 955 */     if (contentShadow != null) {
/*  901: 956 */       list.add(contentShadow);
/*  902:     */     }
/*  903: 959 */     this.content = list;
/*  904:     */     
/*  905: 961 */     return list;
/*  906:     */   }
/*  907:     */   
/*  908:     */   protected List attributeList()
/*  909:     */   {
/*  910: 966 */     Object attributesShadow = this.attributes;
/*  911: 968 */     if ((attributesShadow instanceof List)) {
/*  912: 969 */       return (List)attributesShadow;
/*  913:     */     }
/*  914: 970 */     if (attributesShadow != null)
/*  915:     */     {
/*  916: 971 */       List list = createAttributeList();
/*  917:     */       
/*  918: 973 */       list.add(attributesShadow);
/*  919:     */       
/*  920: 975 */       this.attributes = list;
/*  921:     */       
/*  922: 977 */       return list;
/*  923:     */     }
/*  924: 979 */     List list = createAttributeList();
/*  925:     */     
/*  926: 981 */     this.attributes = list;
/*  927:     */     
/*  928: 983 */     return list;
/*  929:     */   }
/*  930:     */   
/*  931:     */   protected List attributeList(int size)
/*  932:     */   {
/*  933: 988 */     Object attributesShadow = this.attributes;
/*  934: 990 */     if ((attributesShadow instanceof List)) {
/*  935: 991 */       return (List)attributesShadow;
/*  936:     */     }
/*  937: 992 */     if (attributesShadow != null)
/*  938:     */     {
/*  939: 993 */       List list = createAttributeList(size);
/*  940:     */       
/*  941: 995 */       list.add(attributesShadow);
/*  942:     */       
/*  943: 997 */       this.attributes = list;
/*  944:     */       
/*  945: 999 */       return list;
/*  946:     */     }
/*  947:1001 */     List list = createAttributeList(size);
/*  948:     */     
/*  949:1003 */     this.attributes = list;
/*  950:     */     
/*  951:1005 */     return list;
/*  952:     */   }
/*  953:     */   
/*  954:     */   protected void setAttributeList(List attributeList)
/*  955:     */   {
/*  956:1010 */     this.attributes = attributeList;
/*  957:     */   }
/*  958:     */   
/*  959:     */   protected DocumentFactory getDocumentFactory()
/*  960:     */   {
/*  961:1014 */     DocumentFactory factory = this.qname.getDocumentFactory();
/*  962:     */     
/*  963:1016 */     return factory != null ? factory : DOCUMENT_FACTORY;
/*  964:     */   }
/*  965:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.DefaultElement
 * JD-Core Version:    0.7.0.1
 */
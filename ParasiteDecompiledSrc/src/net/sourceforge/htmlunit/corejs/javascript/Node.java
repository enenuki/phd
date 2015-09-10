/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.util.Iterator;
/*    4:     */ import java.util.NoSuchElementException;
/*    5:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Jump;
/*    6:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Name;
/*    7:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.NumberLiteral;
/*    8:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Scope;
/*    9:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*   10:     */ 
/*   11:     */ public class Node
/*   12:     */   implements Iterable<Node>
/*   13:     */ {
/*   14:     */   public static final int FUNCTION_PROP = 1;
/*   15:     */   public static final int LOCAL_PROP = 2;
/*   16:     */   public static final int LOCAL_BLOCK_PROP = 3;
/*   17:     */   public static final int REGEXP_PROP = 4;
/*   18:     */   public static final int CASEARRAY_PROP = 5;
/*   19:     */   public static final int TARGETBLOCK_PROP = 6;
/*   20:     */   public static final int VARIABLE_PROP = 7;
/*   21:     */   public static final int ISNUMBER_PROP = 8;
/*   22:     */   public static final int DIRECTCALL_PROP = 9;
/*   23:     */   public static final int SPECIALCALL_PROP = 10;
/*   24:     */   public static final int SKIP_INDEXES_PROP = 11;
/*   25:     */   public static final int OBJECT_IDS_PROP = 12;
/*   26:     */   public static final int INCRDECR_PROP = 13;
/*   27:     */   public static final int CATCH_SCOPE_PROP = 14;
/*   28:     */   public static final int LABEL_ID_PROP = 15;
/*   29:     */   public static final int MEMBER_TYPE_PROP = 16;
/*   30:     */   public static final int NAME_PROP = 17;
/*   31:     */   public static final int CONTROL_BLOCK_PROP = 18;
/*   32:     */   public static final int PARENTHESIZED_PROP = 19;
/*   33:     */   public static final int GENERATOR_END_PROP = 20;
/*   34:     */   public static final int DESTRUCTURING_ARRAY_LENGTH = 21;
/*   35:     */   public static final int DESTRUCTURING_NAMES = 22;
/*   36:     */   public static final int DESTRUCTURING_PARAMS = 23;
/*   37:     */   public static final int JSDOC_PROP = 24;
/*   38:     */   public static final int EXPRESSION_CLOSURE_PROP = 25;
/*   39:     */   public static final int DESTRUCTURING_SHORTHAND = 26;
/*   40:     */   public static final int LAST_PROP = 26;
/*   41:     */   public static final int BOTH = 0;
/*   42:     */   public static final int LEFT = 1;
/*   43:     */   public static final int RIGHT = 2;
/*   44:     */   public static final int NON_SPECIALCALL = 0;
/*   45:     */   public static final int SPECIALCALL_EVAL = 1;
/*   46:     */   public static final int SPECIALCALL_WITH = 2;
/*   47:     */   public static final int DECR_FLAG = 1;
/*   48:     */   public static final int POST_FLAG = 2;
/*   49:     */   public static final int PROPERTY_FLAG = 1;
/*   50:     */   public static final int ATTRIBUTE_FLAG = 2;
/*   51:     */   public static final int DESCENDANTS_FLAG = 4;
/*   52:     */   
/*   53:     */   public Node(int nodeType)
/*   54:     */   {
/*   55: 132 */     this.type = nodeType;
/*   56:     */   }
/*   57:     */   
/*   58:     */   public Node(int nodeType, Node child)
/*   59:     */   {
/*   60: 136 */     this.type = nodeType;
/*   61: 137 */     this.first = (this.last = child);
/*   62: 138 */     child.next = null;
/*   63:     */   }
/*   64:     */   
/*   65:     */   public Node(int nodeType, Node left, Node right)
/*   66:     */   {
/*   67: 142 */     this.type = nodeType;
/*   68: 143 */     this.first = left;
/*   69: 144 */     this.last = right;
/*   70: 145 */     left.next = right;
/*   71: 146 */     right.next = null;
/*   72:     */   }
/*   73:     */   
/*   74:     */   public Node(int nodeType, Node left, Node mid, Node right)
/*   75:     */   {
/*   76: 150 */     this.type = nodeType;
/*   77: 151 */     this.first = left;
/*   78: 152 */     this.last = right;
/*   79: 153 */     left.next = mid;
/*   80: 154 */     mid.next = right;
/*   81: 155 */     right.next = null;
/*   82:     */   }
/*   83:     */   
/*   84:     */   public Node(int nodeType, int line)
/*   85:     */   {
/*   86: 159 */     this.type = nodeType;
/*   87: 160 */     this.lineno = line;
/*   88:     */   }
/*   89:     */   
/*   90:     */   public Node(int nodeType, Node child, int line)
/*   91:     */   {
/*   92: 164 */     this(nodeType, child);
/*   93: 165 */     this.lineno = line;
/*   94:     */   }
/*   95:     */   
/*   96:     */   public Node(int nodeType, Node left, Node right, int line)
/*   97:     */   {
/*   98: 169 */     this(nodeType, left, right);
/*   99: 170 */     this.lineno = line;
/*  100:     */   }
/*  101:     */   
/*  102:     */   public Node(int nodeType, Node left, Node mid, Node right, int line)
/*  103:     */   {
/*  104: 174 */     this(nodeType, left, mid, right);
/*  105: 175 */     this.lineno = line;
/*  106:     */   }
/*  107:     */   
/*  108:     */   public static Node newNumber(double number)
/*  109:     */   {
/*  110: 179 */     NumberLiteral n = new NumberLiteral();
/*  111: 180 */     n.setNumber(number);
/*  112: 181 */     return n;
/*  113:     */   }
/*  114:     */   
/*  115:     */   public static Node newString(String str)
/*  116:     */   {
/*  117: 185 */     return newString(41, str);
/*  118:     */   }
/*  119:     */   
/*  120:     */   public static Node newString(int type, String str)
/*  121:     */   {
/*  122: 189 */     Name name = new Name();
/*  123: 190 */     name.setIdentifier(str);
/*  124: 191 */     name.setType(type);
/*  125: 192 */     return name;
/*  126:     */   }
/*  127:     */   
/*  128:     */   public int getType()
/*  129:     */   {
/*  130: 196 */     return this.type;
/*  131:     */   }
/*  132:     */   
/*  133:     */   public Node setType(int type)
/*  134:     */   {
/*  135: 203 */     this.type = type;
/*  136: 204 */     return this;
/*  137:     */   }
/*  138:     */   
/*  139:     */   public String getJsDoc()
/*  140:     */   {
/*  141: 213 */     return (String)getProp(24);
/*  142:     */   }
/*  143:     */   
/*  144:     */   public void setJsDoc(String jsdoc)
/*  145:     */   {
/*  146: 220 */     putProp(24, jsdoc);
/*  147:     */   }
/*  148:     */   
/*  149:     */   public boolean hasChildren()
/*  150:     */   {
/*  151: 224 */     return this.first != null;
/*  152:     */   }
/*  153:     */   
/*  154:     */   public Node getFirstChild()
/*  155:     */   {
/*  156: 228 */     return this.first;
/*  157:     */   }
/*  158:     */   
/*  159:     */   public Node getLastChild()
/*  160:     */   {
/*  161: 232 */     return this.last;
/*  162:     */   }
/*  163:     */   
/*  164:     */   public Node getNext()
/*  165:     */   {
/*  166: 236 */     return this.next;
/*  167:     */   }
/*  168:     */   
/*  169:     */   public Node getChildBefore(Node child)
/*  170:     */   {
/*  171: 240 */     if (child == this.first) {
/*  172: 241 */       return null;
/*  173:     */     }
/*  174: 242 */     Node n = this.first;
/*  175: 243 */     while (n.next != child)
/*  176:     */     {
/*  177: 244 */       n = n.next;
/*  178: 245 */       if (n == null) {
/*  179: 246 */         throw new RuntimeException("node is not a child");
/*  180:     */       }
/*  181:     */     }
/*  182: 248 */     return n;
/*  183:     */   }
/*  184:     */   
/*  185:     */   public Node getLastSibling()
/*  186:     */   {
/*  187: 252 */     Node n = this;
/*  188: 253 */     while (n.next != null) {
/*  189: 254 */       n = n.next;
/*  190:     */     }
/*  191: 256 */     return n;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public void addChildToFront(Node child)
/*  195:     */   {
/*  196: 260 */     child.next = this.first;
/*  197: 261 */     this.first = child;
/*  198: 262 */     if (this.last == null) {
/*  199: 263 */       this.last = child;
/*  200:     */     }
/*  201:     */   }
/*  202:     */   
/*  203:     */   public void addChildToBack(Node child)
/*  204:     */   {
/*  205: 268 */     child.next = null;
/*  206: 269 */     if (this.last == null)
/*  207:     */     {
/*  208: 270 */       this.first = (this.last = child);
/*  209: 271 */       return;
/*  210:     */     }
/*  211: 273 */     this.last.next = child;
/*  212: 274 */     this.last = child;
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void addChildrenToFront(Node children)
/*  216:     */   {
/*  217: 278 */     Node lastSib = children.getLastSibling();
/*  218: 279 */     lastSib.next = this.first;
/*  219: 280 */     this.first = children;
/*  220: 281 */     if (this.last == null) {
/*  221: 282 */       this.last = lastSib;
/*  222:     */     }
/*  223:     */   }
/*  224:     */   
/*  225:     */   public void addChildrenToBack(Node children)
/*  226:     */   {
/*  227: 287 */     if (this.last != null) {
/*  228: 288 */       this.last.next = children;
/*  229:     */     }
/*  230: 290 */     this.last = children.getLastSibling();
/*  231: 291 */     if (this.first == null) {
/*  232: 292 */       this.first = children;
/*  233:     */     }
/*  234:     */   }
/*  235:     */   
/*  236:     */   public void addChildBefore(Node newChild, Node node)
/*  237:     */   {
/*  238: 300 */     if (newChild.next != null) {
/*  239: 301 */       throw new RuntimeException("newChild had siblings in addChildBefore");
/*  240:     */     }
/*  241: 303 */     if (this.first == node)
/*  242:     */     {
/*  243: 304 */       newChild.next = this.first;
/*  244: 305 */       this.first = newChild;
/*  245: 306 */       return;
/*  246:     */     }
/*  247: 308 */     Node prev = getChildBefore(node);
/*  248: 309 */     addChildAfter(newChild, prev);
/*  249:     */   }
/*  250:     */   
/*  251:     */   public void addChildAfter(Node newChild, Node node)
/*  252:     */   {
/*  253: 316 */     if (newChild.next != null) {
/*  254: 317 */       throw new RuntimeException("newChild had siblings in addChildAfter");
/*  255:     */     }
/*  256: 319 */     newChild.next = node.next;
/*  257: 320 */     node.next = newChild;
/*  258: 321 */     if (this.last == node) {
/*  259: 322 */       this.last = newChild;
/*  260:     */     }
/*  261:     */   }
/*  262:     */   
/*  263:     */   public void removeChild(Node child)
/*  264:     */   {
/*  265: 326 */     Node prev = getChildBefore(child);
/*  266: 327 */     if (prev == null) {
/*  267: 328 */       this.first = this.first.next;
/*  268:     */     } else {
/*  269: 330 */       prev.next = child.next;
/*  270:     */     }
/*  271: 331 */     if (child == this.last) {
/*  272: 331 */       this.last = prev;
/*  273:     */     }
/*  274: 332 */     child.next = null;
/*  275:     */   }
/*  276:     */   
/*  277:     */   public void replaceChild(Node child, Node newChild)
/*  278:     */   {
/*  279: 336 */     newChild.next = child.next;
/*  280: 337 */     if (child == this.first)
/*  281:     */     {
/*  282: 338 */       this.first = newChild;
/*  283:     */     }
/*  284:     */     else
/*  285:     */     {
/*  286: 340 */       Node prev = getChildBefore(child);
/*  287: 341 */       prev.next = newChild;
/*  288:     */     }
/*  289: 343 */     if (child == this.last) {
/*  290: 344 */       this.last = newChild;
/*  291:     */     }
/*  292: 345 */     child.next = null;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public void replaceChildAfter(Node prevChild, Node newChild)
/*  296:     */   {
/*  297: 349 */     Node child = prevChild.next;
/*  298: 350 */     newChild.next = child.next;
/*  299: 351 */     prevChild.next = newChild;
/*  300: 352 */     if (child == this.last) {
/*  301: 353 */       this.last = newChild;
/*  302:     */     }
/*  303: 354 */     child.next = null;
/*  304:     */   }
/*  305:     */   
/*  306:     */   public void removeChildren()
/*  307:     */   {
/*  308: 358 */     this.first = (this.last = null);
/*  309:     */   }
/*  310:     */   
/*  311: 361 */   private static final Node NOT_SET = new Node(-1);
/*  312:     */   public static final int END_UNREACHED = 0;
/*  313:     */   public static final int END_DROPS_OFF = 1;
/*  314:     */   public static final int END_RETURNS = 2;
/*  315:     */   public static final int END_RETURNS_VALUE = 4;
/*  316:     */   public static final int END_YIELDS = 8;
/*  317:     */   
/*  318:     */   public class NodeIterator
/*  319:     */     implements Iterator<Node>
/*  320:     */   {
/*  321:     */     private Node cursor;
/*  322: 370 */     private Node prev = Node.NOT_SET;
/*  323:     */     private Node prev2;
/*  324: 372 */     private boolean removed = false;
/*  325:     */     
/*  326:     */     public NodeIterator()
/*  327:     */     {
/*  328: 375 */       this.cursor = Node.this.first;
/*  329:     */     }
/*  330:     */     
/*  331:     */     public boolean hasNext()
/*  332:     */     {
/*  333: 379 */       return this.cursor != null;
/*  334:     */     }
/*  335:     */     
/*  336:     */     public Node next()
/*  337:     */     {
/*  338: 383 */       if (this.cursor == null) {
/*  339: 384 */         throw new NoSuchElementException();
/*  340:     */       }
/*  341: 386 */       this.removed = false;
/*  342: 387 */       this.prev2 = this.prev;
/*  343: 388 */       this.prev = this.cursor;
/*  344: 389 */       this.cursor = this.cursor.next;
/*  345: 390 */       return this.prev;
/*  346:     */     }
/*  347:     */     
/*  348:     */     public void remove()
/*  349:     */     {
/*  350: 394 */       if (this.prev == Node.NOT_SET) {
/*  351: 395 */         throw new IllegalStateException("next() has not been called");
/*  352:     */       }
/*  353: 397 */       if (this.removed) {
/*  354: 398 */         throw new IllegalStateException("remove() already called for current element");
/*  355:     */       }
/*  356: 401 */       if (this.prev == Node.this.first)
/*  357:     */       {
/*  358: 402 */         Node.this.first = this.prev.next;
/*  359:     */       }
/*  360: 403 */       else if (this.prev == Node.this.last)
/*  361:     */       {
/*  362: 404 */         this.prev2.next = null;
/*  363: 405 */         Node.this.last = this.prev2;
/*  364:     */       }
/*  365:     */       else
/*  366:     */       {
/*  367: 407 */         this.prev2.next = this.cursor;
/*  368:     */       }
/*  369:     */     }
/*  370:     */   }
/*  371:     */   
/*  372:     */   public Iterator<Node> iterator()
/*  373:     */   {
/*  374: 416 */     return new NodeIterator();
/*  375:     */   }
/*  376:     */   
/*  377:     */   private static final String propToString(int propType)
/*  378:     */   {
/*  379: 455 */     return null;
/*  380:     */   }
/*  381:     */   
/*  382:     */   private PropListItem lookupProperty(int propType)
/*  383:     */   {
/*  384: 460 */     PropListItem x = this.propListHead;
/*  385: 461 */     while ((x != null) && (propType != x.type)) {
/*  386: 462 */       x = x.next;
/*  387:     */     }
/*  388: 464 */     return x;
/*  389:     */   }
/*  390:     */   
/*  391:     */   private PropListItem ensureProperty(int propType)
/*  392:     */   {
/*  393: 469 */     PropListItem item = lookupProperty(propType);
/*  394: 470 */     if (item == null)
/*  395:     */     {
/*  396: 471 */       item = new PropListItem(null);
/*  397: 472 */       item.type = propType;
/*  398: 473 */       item.next = this.propListHead;
/*  399: 474 */       this.propListHead = item;
/*  400:     */     }
/*  401: 476 */     return item;
/*  402:     */   }
/*  403:     */   
/*  404:     */   public void removeProp(int propType)
/*  405:     */   {
/*  406: 481 */     PropListItem x = this.propListHead;
/*  407: 482 */     if (x != null)
/*  408:     */     {
/*  409: 483 */       PropListItem prev = null;
/*  410: 484 */       while (x.type != propType)
/*  411:     */       {
/*  412: 485 */         prev = x;
/*  413: 486 */         x = x.next;
/*  414: 487 */         if (x == null) {
/*  415: 487 */           return;
/*  416:     */         }
/*  417:     */       }
/*  418: 489 */       if (prev == null) {
/*  419: 490 */         this.propListHead = x.next;
/*  420:     */       } else {
/*  421: 492 */         prev.next = x.next;
/*  422:     */       }
/*  423:     */     }
/*  424:     */   }
/*  425:     */   
/*  426:     */   public Object getProp(int propType)
/*  427:     */   {
/*  428: 499 */     PropListItem item = lookupProperty(propType);
/*  429: 500 */     if (item == null) {
/*  430: 500 */       return null;
/*  431:     */     }
/*  432: 501 */     return item.objectValue;
/*  433:     */   }
/*  434:     */   
/*  435:     */   public int getIntProp(int propType, int defaultValue)
/*  436:     */   {
/*  437: 506 */     PropListItem item = lookupProperty(propType);
/*  438: 507 */     if (item == null) {
/*  439: 507 */       return defaultValue;
/*  440:     */     }
/*  441: 508 */     return item.intValue;
/*  442:     */   }
/*  443:     */   
/*  444:     */   public int getExistingIntProp(int propType)
/*  445:     */   {
/*  446: 513 */     PropListItem item = lookupProperty(propType);
/*  447: 514 */     if (item == null) {
/*  448: 514 */       Kit.codeBug();
/*  449:     */     }
/*  450: 515 */     return item.intValue;
/*  451:     */   }
/*  452:     */   
/*  453:     */   public void putProp(int propType, Object prop)
/*  454:     */   {
/*  455: 520 */     if (prop == null)
/*  456:     */     {
/*  457: 521 */       removeProp(propType);
/*  458:     */     }
/*  459:     */     else
/*  460:     */     {
/*  461: 523 */       PropListItem item = ensureProperty(propType);
/*  462: 524 */       item.objectValue = prop;
/*  463:     */     }
/*  464:     */   }
/*  465:     */   
/*  466:     */   public void putIntProp(int propType, int prop)
/*  467:     */   {
/*  468: 530 */     PropListItem item = ensureProperty(propType);
/*  469: 531 */     item.intValue = prop;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public int getLineno()
/*  473:     */   {
/*  474: 539 */     return this.lineno;
/*  475:     */   }
/*  476:     */   
/*  477:     */   public void setLineno(int lineno)
/*  478:     */   {
/*  479: 543 */     this.lineno = lineno;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public final double getDouble()
/*  483:     */   {
/*  484: 548 */     return ((NumberLiteral)this).getNumber();
/*  485:     */   }
/*  486:     */   
/*  487:     */   public final void setDouble(double number)
/*  488:     */   {
/*  489: 552 */     ((NumberLiteral)this).setNumber(number);
/*  490:     */   }
/*  491:     */   
/*  492:     */   public final String getString()
/*  493:     */   {
/*  494: 557 */     return ((Name)this).getIdentifier();
/*  495:     */   }
/*  496:     */   
/*  497:     */   public final void setString(String s)
/*  498:     */   {
/*  499: 562 */     if (s == null) {
/*  500: 562 */       Kit.codeBug();
/*  501:     */     }
/*  502: 563 */     ((Name)this).setIdentifier(s);
/*  503:     */   }
/*  504:     */   
/*  505:     */   public Scope getScope()
/*  506:     */   {
/*  507: 568 */     return ((Name)this).getScope();
/*  508:     */   }
/*  509:     */   
/*  510:     */   public void setScope(Scope s)
/*  511:     */   {
/*  512: 573 */     if (s == null) {
/*  513: 573 */       Kit.codeBug();
/*  514:     */     }
/*  515: 574 */     if (!(this instanceof Name)) {
/*  516: 575 */       throw Kit.codeBug();
/*  517:     */     }
/*  518: 577 */     ((Name)this).setScope(s);
/*  519:     */   }
/*  520:     */   
/*  521:     */   public static Node newTarget()
/*  522:     */   {
/*  523: 582 */     return new Node(131);
/*  524:     */   }
/*  525:     */   
/*  526:     */   public final int labelId()
/*  527:     */   {
/*  528: 587 */     if ((this.type != 131) && (this.type != 72)) {
/*  529: 587 */       Kit.codeBug();
/*  530:     */     }
/*  531: 588 */     return getIntProp(15, -1);
/*  532:     */   }
/*  533:     */   
/*  534:     */   public void labelId(int labelId)
/*  535:     */   {
/*  536: 593 */     if ((this.type != 131) && (this.type != 72)) {
/*  537: 593 */       Kit.codeBug();
/*  538:     */     }
/*  539: 594 */     putIntProp(15, labelId);
/*  540:     */   }
/*  541:     */   
/*  542:     */   public boolean hasConsistentReturnUsage()
/*  543:     */   {
/*  544: 664 */     int n = endCheck();
/*  545: 665 */     return ((n & 0x4) == 0) || ((n & 0xB) == 0);
/*  546:     */   }
/*  547:     */   
/*  548:     */   private int endCheckIf()
/*  549:     */   {
/*  550: 677 */     int rv = 0;
/*  551:     */     
/*  552: 679 */     Node th = this.next;
/*  553: 680 */     Node el = ((Jump)this).target;
/*  554:     */     
/*  555: 682 */     rv = th.endCheck();
/*  556: 684 */     if (el != null) {
/*  557: 685 */       rv |= el.endCheck();
/*  558:     */     } else {
/*  559: 687 */       rv |= 0x1;
/*  560:     */     }
/*  561: 689 */     return rv;
/*  562:     */   }
/*  563:     */   
/*  564:     */   private int endCheckSwitch()
/*  565:     */   {
/*  566: 701 */     int rv = 0;
/*  567:     */     
/*  568:     */ 
/*  569:     */ 
/*  570:     */ 
/*  571:     */ 
/*  572:     */ 
/*  573:     */ 
/*  574:     */ 
/*  575:     */ 
/*  576:     */ 
/*  577:     */ 
/*  578:     */ 
/*  579:     */ 
/*  580:     */ 
/*  581:     */ 
/*  582:     */ 
/*  583:     */ 
/*  584:     */ 
/*  585:     */ 
/*  586:     */ 
/*  587:     */ 
/*  588:     */ 
/*  589:     */ 
/*  590: 725 */     return rv;
/*  591:     */   }
/*  592:     */   
/*  593:     */   private int endCheckTry()
/*  594:     */   {
/*  595: 738 */     int rv = 0;
/*  596:     */     
/*  597:     */ 
/*  598:     */ 
/*  599:     */ 
/*  600:     */ 
/*  601:     */ 
/*  602:     */ 
/*  603:     */ 
/*  604:     */ 
/*  605:     */ 
/*  606:     */ 
/*  607:     */ 
/*  608:     */ 
/*  609:     */ 
/*  610:     */ 
/*  611:     */ 
/*  612:     */ 
/*  613:     */ 
/*  614:     */ 
/*  615:     */ 
/*  616:     */ 
/*  617:     */ 
/*  618:     */ 
/*  619:     */ 
/*  620:     */ 
/*  621:     */ 
/*  622:     */ 
/*  623:     */ 
/*  624:     */ 
/*  625:     */ 
/*  626:     */ 
/*  627:     */ 
/*  628: 771 */     return rv;
/*  629:     */   }
/*  630:     */   
/*  631:     */   private int endCheckLoop()
/*  632:     */   {
/*  633: 791 */     int rv = 0;
/*  634: 798 */     for (Node n = this.first; n.next != this.last; n = n.next) {}
/*  635: 801 */     if (n.type != 6) {
/*  636: 802 */       return 1;
/*  637:     */     }
/*  638: 805 */     rv = ((Jump)n).target.next.endCheck();
/*  639: 808 */     if (n.first.type == 45) {
/*  640: 809 */       rv &= 0xFFFFFFFE;
/*  641:     */     }
/*  642: 812 */     rv |= getIntProp(18, 0);
/*  643:     */     
/*  644: 814 */     return rv;
/*  645:     */   }
/*  646:     */   
/*  647:     */   private int endCheckBlock()
/*  648:     */   {
/*  649: 826 */     int rv = 1;
/*  650: 830 */     for (Node n = this.first; ((rv & 0x1) != 0) && (n != null); n = n.next)
/*  651:     */     {
/*  652: 832 */       rv &= 0xFFFFFFFE;
/*  653: 833 */       rv |= n.endCheck();
/*  654:     */     }
/*  655: 835 */     return rv;
/*  656:     */   }
/*  657:     */   
/*  658:     */   private int endCheckLabel()
/*  659:     */   {
/*  660: 847 */     int rv = 0;
/*  661:     */     
/*  662: 849 */     rv = this.next.endCheck();
/*  663: 850 */     rv |= getIntProp(18, 0);
/*  664:     */     
/*  665: 852 */     return rv;
/*  666:     */   }
/*  667:     */   
/*  668:     */   private int endCheckBreak()
/*  669:     */   {
/*  670: 862 */     Node n = ((Jump)this).getJumpStatement();
/*  671: 863 */     n.putIntProp(18, 1);
/*  672: 864 */     return 0;
/*  673:     */   }
/*  674:     */   
/*  675:     */   private int endCheck()
/*  676:     */   {
/*  677: 878 */     switch (this.type)
/*  678:     */     {
/*  679:     */     case 120: 
/*  680: 881 */       return endCheckBreak();
/*  681:     */     case 133: 
/*  682: 884 */       if (this.first != null) {
/*  683: 885 */         return this.first.endCheck();
/*  684:     */       }
/*  685: 886 */       return 1;
/*  686:     */     case 72: 
/*  687: 889 */       return 8;
/*  688:     */     case 50: 
/*  689:     */     case 121: 
/*  690: 893 */       return 0;
/*  691:     */     case 4: 
/*  692: 896 */       if (this.first != null) {
/*  693: 897 */         return 4;
/*  694:     */       }
/*  695: 899 */       return 2;
/*  696:     */     case 131: 
/*  697: 902 */       if (this.next != null) {
/*  698: 903 */         return this.next.endCheck();
/*  699:     */       }
/*  700: 905 */       return 1;
/*  701:     */     case 132: 
/*  702: 908 */       return endCheckLoop();
/*  703:     */     case 129: 
/*  704:     */     case 141: 
/*  705: 913 */       if (this.first == null) {
/*  706: 914 */         return 1;
/*  707:     */       }
/*  708: 916 */       switch (this.first.type)
/*  709:     */       {
/*  710:     */       case 130: 
/*  711: 918 */         return this.first.endCheckLabel();
/*  712:     */       case 7: 
/*  713: 921 */         return this.first.endCheckIf();
/*  714:     */       case 114: 
/*  715: 924 */         return this.first.endCheckSwitch();
/*  716:     */       case 81: 
/*  717: 927 */         return this.first.endCheckTry();
/*  718:     */       }
/*  719: 930 */       return endCheckBlock();
/*  720:     */     }
/*  721: 934 */     return 1;
/*  722:     */   }
/*  723:     */   
/*  724:     */   public boolean hasSideEffects()
/*  725:     */   {
/*  726: 940 */     switch (this.type)
/*  727:     */     {
/*  728:     */     case 89: 
/*  729:     */     case 133: 
/*  730: 943 */       if (this.last != null) {
/*  731: 944 */         return this.last.hasSideEffects();
/*  732:     */       }
/*  733: 946 */       return true;
/*  734:     */     case 102: 
/*  735: 949 */       if ((this.first == null) || (this.first.next == null) || (this.first.next.next == null)) {
/*  736: 952 */         Kit.codeBug();
/*  737:     */       }
/*  738: 953 */       return (this.first.next.hasSideEffects()) && (this.first.next.next.hasSideEffects());
/*  739:     */     case 104: 
/*  740:     */     case 105: 
/*  741: 958 */       if ((this.first == null) || (this.last == null)) {
/*  742: 959 */         Kit.codeBug();
/*  743:     */       }
/*  744: 960 */       return (this.first.hasSideEffects()) || (this.last.hasSideEffects());
/*  745:     */     case -1: 
/*  746:     */     case 2: 
/*  747:     */     case 3: 
/*  748:     */     case 4: 
/*  749:     */     case 5: 
/*  750:     */     case 6: 
/*  751:     */     case 7: 
/*  752:     */     case 8: 
/*  753:     */     case 30: 
/*  754:     */     case 31: 
/*  755:     */     case 35: 
/*  756:     */     case 37: 
/*  757:     */     case 38: 
/*  758:     */     case 50: 
/*  759:     */     case 51: 
/*  760:     */     case 56: 
/*  761:     */     case 57: 
/*  762:     */     case 64: 
/*  763:     */     case 68: 
/*  764:     */     case 69: 
/*  765:     */     case 70: 
/*  766:     */     case 72: 
/*  767:     */     case 81: 
/*  768:     */     case 82: 
/*  769:     */     case 90: 
/*  770:     */     case 91: 
/*  771:     */     case 92: 
/*  772:     */     case 93: 
/*  773:     */     case 94: 
/*  774:     */     case 95: 
/*  775:     */     case 96: 
/*  776:     */     case 97: 
/*  777:     */     case 98: 
/*  778:     */     case 99: 
/*  779:     */     case 100: 
/*  780:     */     case 101: 
/*  781:     */     case 106: 
/*  782:     */     case 107: 
/*  783:     */     case 112: 
/*  784:     */     case 113: 
/*  785:     */     case 114: 
/*  786:     */     case 117: 
/*  787:     */     case 118: 
/*  788:     */     case 119: 
/*  789:     */     case 120: 
/*  790:     */     case 121: 
/*  791:     */     case 122: 
/*  792:     */     case 123: 
/*  793:     */     case 124: 
/*  794:     */     case 125: 
/*  795:     */     case 129: 
/*  796:     */     case 130: 
/*  797:     */     case 131: 
/*  798:     */     case 132: 
/*  799:     */     case 134: 
/*  800:     */     case 135: 
/*  801:     */     case 139: 
/*  802:     */     case 140: 
/*  803:     */     case 141: 
/*  804:     */     case 142: 
/*  805:     */     case 153: 
/*  806:     */     case 154: 
/*  807:     */     case 158: 
/*  808:     */     case 159: 
/*  809:1026 */       return true;
/*  810:     */     }
/*  811:1029 */     return false;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public String toString()
/*  815:     */   {
/*  816:1041 */     return String.valueOf(this.type);
/*  817:     */   }
/*  818:     */   
/*  819:     */   private void toString(ObjToIntMap printIds, StringBuffer sb) {}
/*  820:     */   
/*  821:     */   public String toStringTree(ScriptNode treeTop)
/*  822:     */   {
/*  823:1208 */     return null;
/*  824:     */   }
/*  825:     */   
/*  826:1267 */   protected int type = -1;
/*  827:     */   protected Node next;
/*  828:     */   protected Node first;
/*  829:     */   protected Node last;
/*  830:1271 */   protected int lineno = -1;
/*  831:     */   protected PropListItem propListHead;
/*  832:     */   
/*  833:     */   private static void toStringTreeHelper(ScriptNode treeTop, Node n, ObjToIntMap printIds, int level, StringBuffer sb) {}
/*  834:     */   
/*  835:     */   private static void generatePrintIds(Node n, ObjToIntMap map) {}
/*  836:     */   
/*  837:     */   private static void appendPrintId(Node n, ObjToIntMap printIds, StringBuffer sb) {}
/*  838:     */   
/*  839:     */   private static class PropListItem
/*  840:     */   {
/*  841:     */     PropListItem next;
/*  842:     */     int type;
/*  843:     */     int intValue;
/*  844:     */     Object objectValue;
/*  845:     */   }
/*  846:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Node
 * JD-Core Version:    0.7.0.1
 */
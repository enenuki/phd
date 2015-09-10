/*    1:     */ package org.apache.xpath;
/*    2:     */ 
/*    3:     */ import org.apache.xml.dtm.DTM;
/*    4:     */ import org.apache.xml.dtm.DTMFilter;
/*    5:     */ import org.apache.xml.dtm.DTMIterator;
/*    6:     */ import org.apache.xml.dtm.DTMManager;
/*    7:     */ import org.apache.xml.utils.NodeVector;
/*    8:     */ import org.apache.xpath.res.XPATHMessages;
/*    9:     */ import org.w3c.dom.Node;
/*   10:     */ import org.w3c.dom.NodeList;
/*   11:     */ import org.w3c.dom.traversal.NodeIterator;
/*   12:     */ 
/*   13:     */ public class NodeSetDTM
/*   14:     */   extends NodeVector
/*   15:     */   implements DTMIterator, Cloneable
/*   16:     */ {
/*   17:     */   static final long serialVersionUID = 7686480133331317070L;
/*   18:     */   DTMManager m_manager;
/*   19:     */   
/*   20:     */   public NodeSetDTM(DTMManager dtmManager)
/*   21:     */   {
/*   22:  72 */     this.m_manager = dtmManager;
/*   23:     */   }
/*   24:     */   
/*   25:     */   public NodeSetDTM(int blocksize, int dummy, DTMManager dtmManager)
/*   26:     */   {
/*   27:  83 */     super(blocksize);
/*   28:  84 */     this.m_manager = dtmManager;
/*   29:     */   }
/*   30:     */   
/*   31:     */   public NodeSetDTM(NodeSetDTM nodelist)
/*   32:     */   {
/*   33: 112 */     this.m_manager = nodelist.getDTMManager();
/*   34: 113 */     this.m_root = nodelist.getRoot();
/*   35:     */     
/*   36: 115 */     addNodes(nodelist);
/*   37:     */   }
/*   38:     */   
/*   39:     */   public NodeSetDTM(DTMIterator ni)
/*   40:     */   {
/*   41: 129 */     this.m_manager = ni.getDTMManager();
/*   42: 130 */     this.m_root = ni.getRoot();
/*   43: 131 */     addNodes(ni);
/*   44:     */   }
/*   45:     */   
/*   46:     */   public NodeSetDTM(NodeIterator iterator, XPathContext xctxt)
/*   47:     */   {
/*   48: 146 */     this.m_manager = xctxt.getDTMManager();
/*   49:     */     Node node;
/*   50: 148 */     while (null != (node = iterator.nextNode()))
/*   51:     */     {
/*   52:     */       Node localNode1;
/*   53: 150 */       int handle = xctxt.getDTMHandleFromNode(localNode1);
/*   54: 151 */       addNodeInDocOrder(handle, xctxt);
/*   55:     */     }
/*   56:     */   }
/*   57:     */   
/*   58:     */   public NodeSetDTM(NodeList nodeList, XPathContext xctxt)
/*   59:     */   {
/*   60: 165 */     this.m_manager = xctxt.getDTMManager();
/*   61:     */     
/*   62: 167 */     int n = nodeList.getLength();
/*   63: 168 */     for (int i = 0; i < n; i++)
/*   64:     */     {
/*   65: 170 */       Node node = nodeList.item(i);
/*   66: 171 */       int handle = xctxt.getDTMHandleFromNode(node);
/*   67:     */       
/*   68: 173 */       addNode(handle);
/*   69:     */     }
/*   70:     */   }
/*   71:     */   
/*   72:     */   public NodeSetDTM(int node, DTMManager dtmManager)
/*   73:     */   {
/*   74: 187 */     this.m_manager = dtmManager;
/*   75:     */     
/*   76: 189 */     addNode(node);
/*   77:     */   }
/*   78:     */   
/*   79:     */   public void setEnvironment(Object environment) {}
/*   80:     */   
/*   81:     */   public int getRoot()
/*   82:     */   {
/*   83: 217 */     if (-1 == this.m_root)
/*   84:     */     {
/*   85: 219 */       if (size() > 0) {
/*   86: 220 */         return item(0);
/*   87:     */       }
/*   88: 222 */       return -1;
/*   89:     */     }
/*   90: 225 */     return this.m_root;
/*   91:     */   }
/*   92:     */   
/*   93:     */   public void setRoot(int context, Object environment) {}
/*   94:     */   
/*   95:     */   public Object clone()
/*   96:     */     throws CloneNotSupportedException
/*   97:     */   {
/*   98: 255 */     NodeSetDTM clone = (NodeSetDTM)super.clone();
/*   99:     */     
/*  100: 257 */     return clone;
/*  101:     */   }
/*  102:     */   
/*  103:     */   public DTMIterator cloneWithReset()
/*  104:     */     throws CloneNotSupportedException
/*  105:     */   {
/*  106: 273 */     NodeSetDTM clone = (NodeSetDTM)clone();
/*  107:     */     
/*  108: 275 */     clone.reset();
/*  109:     */     
/*  110: 277 */     return clone;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public void reset()
/*  114:     */   {
/*  115: 285 */     this.m_next = 0;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public int getWhatToShow()
/*  119:     */   {
/*  120: 302 */     return -17;
/*  121:     */   }
/*  122:     */   
/*  123:     */   public DTMFilter getFilter()
/*  124:     */   {
/*  125: 320 */     return null;
/*  126:     */   }
/*  127:     */   
/*  128:     */   public boolean getExpandEntityReferences()
/*  129:     */   {
/*  130: 341 */     return true;
/*  131:     */   }
/*  132:     */   
/*  133:     */   public DTM getDTM(int nodeHandle)
/*  134:     */   {
/*  135: 356 */     return this.m_manager.getDTM(nodeHandle);
/*  136:     */   }
/*  137:     */   
/*  138:     */   public DTMManager getDTMManager()
/*  139:     */   {
/*  140: 372 */     return this.m_manager;
/*  141:     */   }
/*  142:     */   
/*  143:     */   public int nextNode()
/*  144:     */   {
/*  145: 388 */     if (this.m_next < size())
/*  146:     */     {
/*  147: 390 */       int next = elementAt(this.m_next);
/*  148:     */       
/*  149: 392 */       this.m_next += 1;
/*  150:     */       
/*  151: 394 */       return next;
/*  152:     */     }
/*  153: 397 */     return -1;
/*  154:     */   }
/*  155:     */   
/*  156:     */   public int previousNode()
/*  157:     */   {
/*  158: 414 */     if (!this.m_cacheNodes) {
/*  159: 415 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_CANNOT_ITERATE", null));
/*  160:     */     }
/*  161: 418 */     if (this.m_next - 1 > 0)
/*  162:     */     {
/*  163: 420 */       this.m_next -= 1;
/*  164:     */       
/*  165: 422 */       return elementAt(this.m_next);
/*  166:     */     }
/*  167: 425 */     return -1;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public void detach() {}
/*  171:     */   
/*  172:     */   public void allowDetachToRelease(boolean allowRelease) {}
/*  173:     */   
/*  174:     */   public boolean isFresh()
/*  175:     */   {
/*  176: 463 */     return this.m_next == 0;
/*  177:     */   }
/*  178:     */   
/*  179:     */   public void runTo(int index)
/*  180:     */   {
/*  181: 481 */     if (!this.m_cacheNodes) {
/*  182: 482 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_CANNOT_INDEX", null));
/*  183:     */     }
/*  184: 485 */     if ((index >= 0) && (this.m_next < this.m_firstFree)) {
/*  185: 486 */       this.m_next = index;
/*  186:     */     } else {
/*  187: 488 */       this.m_next = (this.m_firstFree - 1);
/*  188:     */     }
/*  189:     */   }
/*  190:     */   
/*  191:     */   public int item(int index)
/*  192:     */   {
/*  193: 506 */     runTo(index);
/*  194:     */     
/*  195: 508 */     return elementAt(index);
/*  196:     */   }
/*  197:     */   
/*  198:     */   public int getLength()
/*  199:     */   {
/*  200: 522 */     runTo(-1);
/*  201:     */     
/*  202: 524 */     return size();
/*  203:     */   }
/*  204:     */   
/*  205:     */   public void addNode(int n)
/*  206:     */   {
/*  207: 538 */     if (!this.m_mutable) {
/*  208: 539 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  209:     */     }
/*  210: 541 */     addElement(n);
/*  211:     */   }
/*  212:     */   
/*  213:     */   public void insertNode(int n, int pos)
/*  214:     */   {
/*  215: 556 */     if (!this.m_mutable) {
/*  216: 557 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  217:     */     }
/*  218: 559 */     insertElementAt(n, pos);
/*  219:     */   }
/*  220:     */   
/*  221:     */   public void removeNode(int n)
/*  222:     */   {
/*  223: 572 */     if (!this.m_mutable) {
/*  224: 573 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  225:     */     }
/*  226: 575 */     removeElement(n);
/*  227:     */   }
/*  228:     */   
/*  229:     */   public void addNodes(DTMIterator iterator)
/*  230:     */   {
/*  231: 649 */     if (!this.m_mutable) {
/*  232: 650 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  233:     */     }
/*  234: 652 */     if (null != iterator)
/*  235:     */     {
/*  236:     */       int obj;
/*  237: 656 */       while (-1 != (obj = iterator.nextNode()))
/*  238:     */       {
/*  239:     */         int i;
/*  240: 658 */         addElement(i);
/*  241:     */       }
/*  242:     */     }
/*  243:     */   }
/*  244:     */   
/*  245:     */   public void addNodesInDocOrder(DTMIterator iterator, XPathContext support)
/*  246:     */   {
/*  247: 706 */     if (!this.m_mutable) {
/*  248: 707 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  249:     */     }
/*  250:     */     int node;
/*  251: 711 */     while (-1 != (node = iterator.nextNode()))
/*  252:     */     {
/*  253:     */       int i;
/*  254: 713 */       addNodeInDocOrder(i, support);
/*  255:     */     }
/*  256:     */   }
/*  257:     */   
/*  258:     */   public int addNodeInDocOrder(int node, boolean test, XPathContext support)
/*  259:     */   {
/*  260: 795 */     if (!this.m_mutable) {
/*  261: 796 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  262:     */     }
/*  263: 798 */     int insertIndex = -1;
/*  264: 800 */     if (test)
/*  265:     */     {
/*  266: 806 */       int size = size();
/*  267: 808 */       for (int i = size - 1; i >= 0; i--)
/*  268:     */       {
/*  269: 810 */         int child = elementAt(i);
/*  270: 812 */         if (child == node)
/*  271:     */         {
/*  272: 814 */           i = -2;
/*  273:     */         }
/*  274:     */         else
/*  275:     */         {
/*  276: 819 */           DTM dtm = support.getDTM(node);
/*  277: 820 */           if (!dtm.isNodeAfter(node, child)) {
/*  278:     */             break;
/*  279:     */           }
/*  280:     */         }
/*  281:     */       }
/*  282: 826 */       if (i != -2)
/*  283:     */       {
/*  284: 828 */         insertIndex = i + 1;
/*  285:     */         
/*  286: 830 */         insertElementAt(node, insertIndex);
/*  287:     */       }
/*  288:     */     }
/*  289:     */     else
/*  290:     */     {
/*  291: 835 */       insertIndex = size();
/*  292:     */       
/*  293: 837 */       boolean foundit = false;
/*  294: 839 */       for (int i = 0; i < insertIndex; i++) {
/*  295: 841 */         if (i == node)
/*  296:     */         {
/*  297: 843 */           foundit = true;
/*  298:     */           
/*  299: 845 */           break;
/*  300:     */         }
/*  301:     */       }
/*  302: 849 */       if (!foundit) {
/*  303: 850 */         addElement(node);
/*  304:     */       }
/*  305:     */     }
/*  306: 854 */     return insertIndex;
/*  307:     */   }
/*  308:     */   
/*  309:     */   public int addNodeInDocOrder(int node, XPathContext support)
/*  310:     */   {
/*  311: 870 */     if (!this.m_mutable) {
/*  312: 871 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  313:     */     }
/*  314: 873 */     return addNodeInDocOrder(node, true, support);
/*  315:     */   }
/*  316:     */   
/*  317:     */   public int size()
/*  318:     */   {
/*  319: 883 */     return super.size();
/*  320:     */   }
/*  321:     */   
/*  322:     */   public void addElement(int value)
/*  323:     */   {
/*  324: 896 */     if (!this.m_mutable) {
/*  325: 897 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  326:     */     }
/*  327: 899 */     super.addElement(value);
/*  328:     */   }
/*  329:     */   
/*  330:     */   public void insertElementAt(int value, int at)
/*  331:     */   {
/*  332: 916 */     if (!this.m_mutable) {
/*  333: 917 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  334:     */     }
/*  335: 919 */     super.insertElementAt(value, at);
/*  336:     */   }
/*  337:     */   
/*  338:     */   public void appendNodes(NodeVector nodes)
/*  339:     */   {
/*  340: 932 */     if (!this.m_mutable) {
/*  341: 933 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  342:     */     }
/*  343: 935 */     super.appendNodes(nodes);
/*  344:     */   }
/*  345:     */   
/*  346:     */   public void removeAllElements()
/*  347:     */   {
/*  348: 949 */     if (!this.m_mutable) {
/*  349: 950 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  350:     */     }
/*  351: 952 */     super.removeAllElements();
/*  352:     */   }
/*  353:     */   
/*  354:     */   public boolean removeElement(int s)
/*  355:     */   {
/*  356: 971 */     if (!this.m_mutable) {
/*  357: 972 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  358:     */     }
/*  359: 974 */     return super.removeElement(s);
/*  360:     */   }
/*  361:     */   
/*  362:     */   public void removeElementAt(int i)
/*  363:     */   {
/*  364: 990 */     if (!this.m_mutable) {
/*  365: 991 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  366:     */     }
/*  367: 993 */     super.removeElementAt(i);
/*  368:     */   }
/*  369:     */   
/*  370:     */   public void setElementAt(int node, int index)
/*  371:     */   {
/*  372:1011 */     if (!this.m_mutable) {
/*  373:1012 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  374:     */     }
/*  375:1014 */     super.setElementAt(node, index);
/*  376:     */   }
/*  377:     */   
/*  378:     */   public void setItem(int node, int index)
/*  379:     */   {
/*  380:1028 */     if (!this.m_mutable) {
/*  381:1029 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
/*  382:     */     }
/*  383:1031 */     super.setElementAt(node, index);
/*  384:     */   }
/*  385:     */   
/*  386:     */   public int elementAt(int i)
/*  387:     */   {
/*  388:1044 */     runTo(i);
/*  389:     */     
/*  390:1046 */     return super.elementAt(i);
/*  391:     */   }
/*  392:     */   
/*  393:     */   public boolean contains(int s)
/*  394:     */   {
/*  395:1059 */     runTo(-1);
/*  396:     */     
/*  397:1061 */     return super.contains(s);
/*  398:     */   }
/*  399:     */   
/*  400:     */   public int indexOf(int elem, int index)
/*  401:     */   {
/*  402:1078 */     runTo(-1);
/*  403:     */     
/*  404:1080 */     return super.indexOf(elem, index);
/*  405:     */   }
/*  406:     */   
/*  407:     */   public int indexOf(int elem)
/*  408:     */   {
/*  409:1096 */     runTo(-1);
/*  410:     */     
/*  411:1098 */     return super.indexOf(elem);
/*  412:     */   }
/*  413:     */   
/*  414:1103 */   protected transient int m_next = 0;
/*  415:     */   
/*  416:     */   public int getCurrentPos()
/*  417:     */   {
/*  418:1115 */     return this.m_next;
/*  419:     */   }
/*  420:     */   
/*  421:     */   public void setCurrentPos(int i)
/*  422:     */   {
/*  423:1127 */     if (!this.m_cacheNodes) {
/*  424:1128 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NODESETDTM_CANNOT_INDEX", null));
/*  425:     */     }
/*  426:1131 */     this.m_next = i;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public int getCurrentNode()
/*  430:     */   {
/*  431:1144 */     if (!this.m_cacheNodes) {
/*  432:1145 */       throw new RuntimeException("This NodeSetDTM can not do indexing or counting functions!");
/*  433:     */     }
/*  434:1148 */     int saved = this.m_next;
/*  435:     */     
/*  436:     */ 
/*  437:     */ 
/*  438:1152 */     int current = this.m_next > 0 ? this.m_next - 1 : this.m_next;
/*  439:1153 */     int n = current < this.m_firstFree ? elementAt(current) : -1;
/*  440:1154 */     this.m_next = saved;
/*  441:1155 */     return n;
/*  442:     */   }
/*  443:     */   
/*  444:1159 */   protected transient boolean m_mutable = true;
/*  445:1163 */   protected transient boolean m_cacheNodes = true;
/*  446:1166 */   protected int m_root = -1;
/*  447:     */   
/*  448:     */   public boolean getShouldCacheNodes()
/*  449:     */   {
/*  450:1176 */     return this.m_cacheNodes;
/*  451:     */   }
/*  452:     */   
/*  453:     */   public void setShouldCacheNodes(boolean b)
/*  454:     */   {
/*  455:1193 */     if (!isFresh()) {
/*  456:1194 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_CANNOT_CALL_SETSHOULDCACHENODE", null));
/*  457:     */     }
/*  458:1197 */     this.m_cacheNodes = b;
/*  459:1198 */     this.m_mutable = true;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public boolean isMutable()
/*  463:     */   {
/*  464:1209 */     return this.m_mutable;
/*  465:     */   }
/*  466:     */   
/*  467:1212 */   private transient int m_last = 0;
/*  468:     */   
/*  469:     */   public int getLast()
/*  470:     */   {
/*  471:1216 */     return this.m_last;
/*  472:     */   }
/*  473:     */   
/*  474:     */   public void setLast(int last)
/*  475:     */   {
/*  476:1221 */     this.m_last = last;
/*  477:     */   }
/*  478:     */   
/*  479:     */   public boolean isDocOrdered()
/*  480:     */   {
/*  481:1232 */     return true;
/*  482:     */   }
/*  483:     */   
/*  484:     */   public int getAxis()
/*  485:     */   {
/*  486:1243 */     return -1;
/*  487:     */   }
/*  488:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.NodeSetDTM
 * JD-Core Version:    0.7.0.1
 */
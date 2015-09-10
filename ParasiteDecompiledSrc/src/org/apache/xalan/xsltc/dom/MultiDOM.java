/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.DOM;
/*   4:    */ import org.apache.xalan.xsltc.StripFilter;
/*   5:    */ import org.apache.xalan.xsltc.TransletException;
/*   6:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   7:    */ import org.apache.xalan.xsltc.runtime.Hashtable;
/*   8:    */ import org.apache.xml.dtm.Axis;
/*   9:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*  10:    */ import org.apache.xml.dtm.DTMManager;
/*  11:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*  12:    */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*  13:    */ import org.apache.xml.serializer.SerializationHandler;
/*  14:    */ import org.apache.xml.utils.SuballocatedIntVector;
/*  15:    */ import org.w3c.dom.Node;
/*  16:    */ import org.w3c.dom.NodeList;
/*  17:    */ 
/*  18:    */ public final class MultiDOM
/*  19:    */   implements DOM
/*  20:    */ {
/*  21:    */   private static final int NO_TYPE = -2;
/*  22:    */   private static final int INITIAL_SIZE = 4;
/*  23:    */   private DOM[] _adapters;
/*  24:    */   private DOMAdapter _main;
/*  25:    */   private DTMManager _dtmManager;
/*  26:    */   private int _free;
/*  27:    */   private int _size;
/*  28: 57 */   private Hashtable _documents = new Hashtable();
/*  29:    */   
/*  30:    */   private final class AxisIterator
/*  31:    */     extends DTMAxisIteratorBase
/*  32:    */   {
/*  33:    */     private final int _axis;
/*  34:    */     private final int _type;
/*  35:    */     private DTMAxisIterator _source;
/*  36: 65 */     private int _dtmId = -1;
/*  37:    */     
/*  38:    */     public AxisIterator(int axis, int type)
/*  39:    */     {
/*  40: 68 */       this._axis = axis;
/*  41: 69 */       this._type = type;
/*  42:    */     }
/*  43:    */     
/*  44:    */     public int next()
/*  45:    */     {
/*  46: 73 */       if (this._source == null) {
/*  47: 74 */         return -1;
/*  48:    */       }
/*  49: 76 */       return this._source.next();
/*  50:    */     }
/*  51:    */     
/*  52:    */     public void setRestartable(boolean flag)
/*  53:    */     {
/*  54: 81 */       if (this._source != null) {
/*  55: 82 */         this._source.setRestartable(flag);
/*  56:    */       }
/*  57:    */     }
/*  58:    */     
/*  59:    */     public DTMAxisIterator setStartNode(int node)
/*  60:    */     {
/*  61: 87 */       if (node == -1) {
/*  62: 88 */         return this;
/*  63:    */       }
/*  64: 91 */       int dom = node >>> 16;
/*  65: 94 */       if ((this._source == null) || (this._dtmId != dom)) {
/*  66: 95 */         if (this._type == -2) {
/*  67: 96 */           this._source = MultiDOM.this._adapters[dom].getAxisIterator(this._axis);
/*  68: 97 */         } else if (this._axis == 3) {
/*  69: 98 */           this._source = MultiDOM.this._adapters[dom].getTypedChildren(this._type);
/*  70:    */         } else {
/*  71:100 */           this._source = MultiDOM.this._adapters[dom].getTypedAxisIterator(this._axis, this._type);
/*  72:    */         }
/*  73:    */       }
/*  74:104 */       this._dtmId = dom;
/*  75:105 */       this._source.setStartNode(node);
/*  76:106 */       return this;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public DTMAxisIterator reset()
/*  80:    */     {
/*  81:110 */       if (this._source != null) {
/*  82:111 */         this._source.reset();
/*  83:    */       }
/*  84:113 */       return this;
/*  85:    */     }
/*  86:    */     
/*  87:    */     public int getLast()
/*  88:    */     {
/*  89:117 */       if (this._source != null) {
/*  90:118 */         return this._source.getLast();
/*  91:    */       }
/*  92:121 */       return -1;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public int getPosition()
/*  96:    */     {
/*  97:126 */       if (this._source != null) {
/*  98:127 */         return this._source.getPosition();
/*  99:    */       }
/* 100:130 */       return -1;
/* 101:    */     }
/* 102:    */     
/* 103:    */     public boolean isReverse()
/* 104:    */     {
/* 105:135 */       return Axis.isReverse(this._axis);
/* 106:    */     }
/* 107:    */     
/* 108:    */     public void setMark()
/* 109:    */     {
/* 110:139 */       if (this._source != null) {
/* 111:140 */         this._source.setMark();
/* 112:    */       }
/* 113:    */     }
/* 114:    */     
/* 115:    */     public void gotoMark()
/* 116:    */     {
/* 117:145 */       if (this._source != null) {
/* 118:146 */         this._source.gotoMark();
/* 119:    */       }
/* 120:    */     }
/* 121:    */     
/* 122:    */     public DTMAxisIterator cloneIterator()
/* 123:    */     {
/* 124:151 */       AxisIterator clone = new AxisIterator(MultiDOM.this, this._axis, this._type);
/* 125:152 */       if (this._source != null) {
/* 126:153 */         clone._source = this._source.cloneIterator();
/* 127:    */       }
/* 128:155 */       clone._dtmId = this._dtmId;
/* 129:156 */       return clone;
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   private final class NodeValueIterator
/* 134:    */     extends DTMAxisIteratorBase
/* 135:    */   {
/* 136:    */     private DTMAxisIterator _source;
/* 137:    */     private String _value;
/* 138:    */     private boolean _op;
/* 139:    */     private final boolean _isReverse;
/* 140:171 */     private int _returnType = 1;
/* 141:    */     
/* 142:    */     public NodeValueIterator(DTMAxisIterator source, int returnType, String value, boolean op)
/* 143:    */     {
/* 144:175 */       this._source = source;
/* 145:176 */       this._returnType = returnType;
/* 146:177 */       this._value = value;
/* 147:178 */       this._op = op;
/* 148:179 */       this._isReverse = source.isReverse();
/* 149:    */     }
/* 150:    */     
/* 151:    */     public boolean isReverse()
/* 152:    */     {
/* 153:183 */       return this._isReverse;
/* 154:    */     }
/* 155:    */     
/* 156:    */     public DTMAxisIterator cloneIterator()
/* 157:    */     {
/* 158:    */       try
/* 159:    */       {
/* 160:188 */         NodeValueIterator clone = (NodeValueIterator)super.clone();
/* 161:189 */         clone._source = this._source.cloneIterator();
/* 162:190 */         clone.setRestartable(false);
/* 163:191 */         return clone.reset();
/* 164:    */       }
/* 165:    */       catch (CloneNotSupportedException e)
/* 166:    */       {
/* 167:194 */         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
/* 168:    */       }
/* 169:196 */       return null;
/* 170:    */     }
/* 171:    */     
/* 172:    */     public void setRestartable(boolean isRestartable)
/* 173:    */     {
/* 174:202 */       this._isRestartable = isRestartable;
/* 175:203 */       this._source.setRestartable(isRestartable);
/* 176:    */     }
/* 177:    */     
/* 178:    */     public DTMAxisIterator reset()
/* 179:    */     {
/* 180:207 */       this._source.reset();
/* 181:208 */       return resetPosition();
/* 182:    */     }
/* 183:    */     
/* 184:    */     public int next()
/* 185:    */     {
/* 186:    */       int node;
/* 187:214 */       while ((node = this._source.next()) != -1)
/* 188:    */       {
/* 189:    */         int i;
/* 190:215 */         String val = MultiDOM.this.getStringValueX(i);
/* 191:216 */         if (this._value.equals(val) == this._op)
/* 192:    */         {
/* 193:217 */           if (this._returnType == 0) {
/* 194:218 */             return returnNode(i);
/* 195:    */           }
/* 196:220 */           return returnNode(MultiDOM.this.getParent(i));
/* 197:    */         }
/* 198:    */       }
/* 199:223 */       return -1;
/* 200:    */     }
/* 201:    */     
/* 202:    */     public DTMAxisIterator setStartNode(int node)
/* 203:    */     {
/* 204:227 */       if (this._isRestartable)
/* 205:    */       {
/* 206:228 */         this._source.setStartNode(this._startNode = node);
/* 207:229 */         return resetPosition();
/* 208:    */       }
/* 209:231 */       return this;
/* 210:    */     }
/* 211:    */     
/* 212:    */     public void setMark()
/* 213:    */     {
/* 214:235 */       this._source.setMark();
/* 215:    */     }
/* 216:    */     
/* 217:    */     public void gotoMark()
/* 218:    */     {
/* 219:239 */       this._source.gotoMark();
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public MultiDOM(DOM main)
/* 224:    */   {
/* 225:244 */     this._size = 4;
/* 226:245 */     this._free = 1;
/* 227:246 */     this._adapters = new DOM[4];
/* 228:247 */     DOMAdapter adapter = (DOMAdapter)main;
/* 229:248 */     this._adapters[0] = adapter;
/* 230:249 */     this._main = adapter;
/* 231:250 */     DOM dom = adapter.getDOMImpl();
/* 232:251 */     if ((dom instanceof DTMDefaultBase)) {
/* 233:252 */       this._dtmManager = ((DTMDefaultBase)dom).getManager();
/* 234:    */     }
/* 235:269 */     addDOMAdapter(adapter, false);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public int nextMask()
/* 239:    */   {
/* 240:273 */     return this._free;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setupMapping(String[] names, String[] uris, int[] types, String[] namespaces) {}
/* 244:    */   
/* 245:    */   public int addDOMAdapter(DOMAdapter adapter)
/* 246:    */   {
/* 247:281 */     return addDOMAdapter(adapter, true);
/* 248:    */   }
/* 249:    */   
/* 250:    */   private int addDOMAdapter(DOMAdapter adapter, boolean indexByURI)
/* 251:    */   {
/* 252:286 */     DOM dom = adapter.getDOMImpl();
/* 253:    */     
/* 254:288 */     int domNo = 1;
/* 255:289 */     int dtmSize = 1;
/* 256:290 */     SuballocatedIntVector dtmIds = null;
/* 257:291 */     if ((dom instanceof DTMDefaultBase))
/* 258:    */     {
/* 259:292 */       DTMDefaultBase dtmdb = (DTMDefaultBase)dom;
/* 260:293 */       dtmIds = dtmdb.getDTMIDs();
/* 261:294 */       dtmSize = dtmIds.size();
/* 262:295 */       domNo = dtmIds.elementAt(dtmSize - 1) >>> 16;
/* 263:    */     }
/* 264:297 */     else if ((dom instanceof SimpleResultTreeImpl))
/* 265:    */     {
/* 266:298 */       SimpleResultTreeImpl simpleRTF = (SimpleResultTreeImpl)dom;
/* 267:299 */       domNo = simpleRTF.getDocument() >>> 16;
/* 268:    */     }
/* 269:302 */     if (domNo >= this._size)
/* 270:    */     {
/* 271:303 */       int oldSize = this._size;
/* 272:    */       do
/* 273:    */       {
/* 274:305 */         this._size *= 2;
/* 275:306 */       } while (this._size <= domNo);
/* 276:308 */       DOMAdapter[] newArray = new DOMAdapter[this._size];
/* 277:309 */       System.arraycopy(this._adapters, 0, newArray, 0, oldSize);
/* 278:310 */       this._adapters = newArray;
/* 279:    */     }
/* 280:313 */     this._free = (domNo + 1);
/* 281:315 */     if (dtmSize == 1)
/* 282:    */     {
/* 283:316 */       this._adapters[domNo] = adapter;
/* 284:    */     }
/* 285:318 */     else if (dtmIds != null)
/* 286:    */     {
/* 287:319 */       int domPos = 0;
/* 288:320 */       for (int i = dtmSize - 1; i >= 0; i--)
/* 289:    */       {
/* 290:321 */         domPos = dtmIds.elementAt(i) >>> 16;
/* 291:322 */         this._adapters[domPos] = adapter;
/* 292:    */       }
/* 293:324 */       domNo = domPos;
/* 294:    */     }
/* 295:328 */     if (indexByURI)
/* 296:    */     {
/* 297:329 */       String uri = adapter.getDocumentURI(0);
/* 298:330 */       this._documents.put(uri, new Integer(domNo));
/* 299:    */     }
/* 300:336 */     if ((dom instanceof AdaptiveResultTreeImpl))
/* 301:    */     {
/* 302:337 */       AdaptiveResultTreeImpl adaptiveRTF = (AdaptiveResultTreeImpl)dom;
/* 303:338 */       DOM nestedDom = adaptiveRTF.getNestedDOM();
/* 304:339 */       if (nestedDom != null)
/* 305:    */       {
/* 306:340 */         DOMAdapter newAdapter = new DOMAdapter(nestedDom, adapter.getNamesArray(), adapter.getUrisArray(), adapter.getTypesArray(), adapter.getNamespaceArray());
/* 307:    */         
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:345 */         addDOMAdapter(newAdapter);
/* 312:    */       }
/* 313:    */     }
/* 314:349 */     return domNo;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public int getDocumentMask(String uri)
/* 318:    */   {
/* 319:353 */     Integer domIdx = (Integer)this._documents.get(uri);
/* 320:354 */     if (domIdx == null) {
/* 321:355 */       return -1;
/* 322:    */     }
/* 323:357 */     return domIdx.intValue();
/* 324:    */   }
/* 325:    */   
/* 326:    */   public DOM getDOMAdapter(String uri)
/* 327:    */   {
/* 328:362 */     Integer domIdx = (Integer)this._documents.get(uri);
/* 329:363 */     if (domIdx == null) {
/* 330:364 */       return null;
/* 331:    */     }
/* 332:366 */     return this._adapters[domIdx.intValue()];
/* 333:    */   }
/* 334:    */   
/* 335:    */   public int getDocument()
/* 336:    */   {
/* 337:372 */     return this._main.getDocument();
/* 338:    */   }
/* 339:    */   
/* 340:    */   public DTMManager getDTMManager()
/* 341:    */   {
/* 342:376 */     return this._dtmManager;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public DTMAxisIterator getIterator()
/* 346:    */   {
/* 347:384 */     return this._main.getIterator();
/* 348:    */   }
/* 349:    */   
/* 350:    */   public String getStringValue()
/* 351:    */   {
/* 352:388 */     return this._main.getStringValue();
/* 353:    */   }
/* 354:    */   
/* 355:    */   public DTMAxisIterator getChildren(int node)
/* 356:    */   {
/* 357:392 */     return this._adapters[getDTMId(node)].getChildren(node);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public DTMAxisIterator getTypedChildren(int type)
/* 361:    */   {
/* 362:396 */     return new AxisIterator(3, type);
/* 363:    */   }
/* 364:    */   
/* 365:    */   public DTMAxisIterator getAxisIterator(int axis)
/* 366:    */   {
/* 367:400 */     return new AxisIterator(axis, -2);
/* 368:    */   }
/* 369:    */   
/* 370:    */   public DTMAxisIterator getTypedAxisIterator(int axis, int type)
/* 371:    */   {
/* 372:405 */     return new AxisIterator(axis, type);
/* 373:    */   }
/* 374:    */   
/* 375:    */   public DTMAxisIterator getNthDescendant(int node, int n, boolean includeself)
/* 376:    */   {
/* 377:411 */     return this._adapters[getDTMId(node)].getNthDescendant(node, n, includeself);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iterator, int type, String value, boolean op)
/* 381:    */   {
/* 382:418 */     return new NodeValueIterator(iterator, type, value, op);
/* 383:    */   }
/* 384:    */   
/* 385:    */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns)
/* 386:    */   {
/* 387:424 */     DTMAxisIterator iterator = this._main.getNamespaceAxisIterator(axis, ns);
/* 388:425 */     return iterator;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node)
/* 392:    */   {
/* 393:429 */     return this._adapters[getDTMId(node)].orderNodes(source, node);
/* 394:    */   }
/* 395:    */   
/* 396:    */   public int getExpandedTypeID(int node)
/* 397:    */   {
/* 398:433 */     if (node != -1) {
/* 399:434 */       return this._adapters[(node >>> 16)].getExpandedTypeID(node);
/* 400:    */     }
/* 401:437 */     return -1;
/* 402:    */   }
/* 403:    */   
/* 404:    */   public int getNamespaceType(int node)
/* 405:    */   {
/* 406:442 */     return this._adapters[getDTMId(node)].getNamespaceType(node);
/* 407:    */   }
/* 408:    */   
/* 409:    */   public int getNSType(int node)
/* 410:    */   {
/* 411:447 */     return this._adapters[getDTMId(node)].getNSType(node);
/* 412:    */   }
/* 413:    */   
/* 414:    */   public int getParent(int node)
/* 415:    */   {
/* 416:451 */     if (node == -1) {
/* 417:452 */       return -1;
/* 418:    */     }
/* 419:454 */     return this._adapters[(node >>> 16)].getParent(node);
/* 420:    */   }
/* 421:    */   
/* 422:    */   public int getAttributeNode(int type, int el)
/* 423:    */   {
/* 424:458 */     if (el == -1) {
/* 425:459 */       return -1;
/* 426:    */     }
/* 427:461 */     return this._adapters[(el >>> 16)].getAttributeNode(type, el);
/* 428:    */   }
/* 429:    */   
/* 430:    */   public String getNodeName(int node)
/* 431:    */   {
/* 432:465 */     if (node == -1) {
/* 433:466 */       return "";
/* 434:    */     }
/* 435:468 */     return this._adapters[(node >>> 16)].getNodeName(node);
/* 436:    */   }
/* 437:    */   
/* 438:    */   public String getNodeNameX(int node)
/* 439:    */   {
/* 440:472 */     if (node == -1) {
/* 441:473 */       return "";
/* 442:    */     }
/* 443:475 */     return this._adapters[(node >>> 16)].getNodeNameX(node);
/* 444:    */   }
/* 445:    */   
/* 446:    */   public String getNamespaceName(int node)
/* 447:    */   {
/* 448:479 */     if (node == -1) {
/* 449:480 */       return "";
/* 450:    */     }
/* 451:482 */     return this._adapters[(node >>> 16)].getNamespaceName(node);
/* 452:    */   }
/* 453:    */   
/* 454:    */   public String getStringValueX(int node)
/* 455:    */   {
/* 456:486 */     if (node == -1) {
/* 457:487 */       return "";
/* 458:    */     }
/* 459:489 */     return this._adapters[(node >>> 16)].getStringValueX(node);
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void copy(int node, SerializationHandler handler)
/* 463:    */     throws TransletException
/* 464:    */   {
/* 465:495 */     if (node != -1) {
/* 466:496 */       this._adapters[(node >>> 16)].copy(node, handler);
/* 467:    */     }
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void copy(DTMAxisIterator nodes, SerializationHandler handler)
/* 471:    */     throws TransletException
/* 472:    */   {
/* 473:    */     int node;
/* 474:504 */     while ((node = nodes.next()) != -1)
/* 475:    */     {
/* 476:    */       int i;
/* 477:505 */       this._adapters[(i >>> 16)].copy(i, handler);
/* 478:    */     }
/* 479:    */   }
/* 480:    */   
/* 481:    */   public String shallowCopy(int node, SerializationHandler handler)
/* 482:    */     throws TransletException
/* 483:    */   {
/* 484:513 */     if (node == -1) {
/* 485:514 */       return "";
/* 486:    */     }
/* 487:516 */     return this._adapters[(node >>> 16)].shallowCopy(node, handler);
/* 488:    */   }
/* 489:    */   
/* 490:    */   public boolean lessThan(int node1, int node2)
/* 491:    */   {
/* 492:520 */     if (node1 == -1) {
/* 493:521 */       return true;
/* 494:    */     }
/* 495:523 */     if (node2 == -1) {
/* 496:524 */       return false;
/* 497:    */     }
/* 498:526 */     int dom1 = getDTMId(node1);
/* 499:527 */     int dom2 = getDTMId(node2);
/* 500:528 */     return dom1 < dom2 ? true : dom1 == dom2 ? this._adapters[dom1].lessThan(node1, node2) : false;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public void characters(int textNode, SerializationHandler handler)
/* 504:    */     throws TransletException
/* 505:    */   {
/* 506:535 */     if (textNode != -1) {
/* 507:536 */       this._adapters[(textNode >>> 16)].characters(textNode, handler);
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void setFilter(StripFilter filter)
/* 512:    */   {
/* 513:541 */     for (int dom = 0; dom < this._free; dom++) {
/* 514:542 */       if (this._adapters[dom] != null) {
/* 515:543 */         this._adapters[dom].setFilter(filter);
/* 516:    */       }
/* 517:    */     }
/* 518:    */   }
/* 519:    */   
/* 520:    */   public Node makeNode(int index)
/* 521:    */   {
/* 522:549 */     if (index == -1) {
/* 523:550 */       return null;
/* 524:    */     }
/* 525:552 */     return this._adapters[getDTMId(index)].makeNode(index);
/* 526:    */   }
/* 527:    */   
/* 528:    */   public Node makeNode(DTMAxisIterator iter)
/* 529:    */   {
/* 530:557 */     return this._main.makeNode(iter);
/* 531:    */   }
/* 532:    */   
/* 533:    */   public NodeList makeNodeList(int index)
/* 534:    */   {
/* 535:561 */     if (index == -1) {
/* 536:562 */       return null;
/* 537:    */     }
/* 538:564 */     return this._adapters[getDTMId(index)].makeNodeList(index);
/* 539:    */   }
/* 540:    */   
/* 541:    */   public NodeList makeNodeList(DTMAxisIterator iter)
/* 542:    */   {
/* 543:569 */     return this._main.makeNodeList(iter);
/* 544:    */   }
/* 545:    */   
/* 546:    */   public String getLanguage(int node)
/* 547:    */   {
/* 548:573 */     return this._adapters[getDTMId(node)].getLanguage(node);
/* 549:    */   }
/* 550:    */   
/* 551:    */   public int getSize()
/* 552:    */   {
/* 553:577 */     int size = 0;
/* 554:578 */     for (int i = 0; i < this._size; i++) {
/* 555:579 */       size += this._adapters[i].getSize();
/* 556:    */     }
/* 557:581 */     return size;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public String getDocumentURI(int node)
/* 561:    */   {
/* 562:585 */     if (node == -1) {
/* 563:586 */       node = 0;
/* 564:    */     }
/* 565:588 */     return this._adapters[(node >>> 16)].getDocumentURI(0);
/* 566:    */   }
/* 567:    */   
/* 568:    */   public boolean isElement(int node)
/* 569:    */   {
/* 570:592 */     if (node == -1) {
/* 571:593 */       return false;
/* 572:    */     }
/* 573:595 */     return this._adapters[(node >>> 16)].isElement(node);
/* 574:    */   }
/* 575:    */   
/* 576:    */   public boolean isAttribute(int node)
/* 577:    */   {
/* 578:599 */     if (node == -1) {
/* 579:600 */       return false;
/* 580:    */     }
/* 581:602 */     return this._adapters[(node >>> 16)].isAttribute(node);
/* 582:    */   }
/* 583:    */   
/* 584:    */   public int getDTMId(int nodeHandle)
/* 585:    */   {
/* 586:607 */     if (nodeHandle == -1) {
/* 587:608 */       return 0;
/* 588:    */     }
/* 589:610 */     int id = nodeHandle >>> 16;
/* 590:611 */     while ((id >= 2) && (this._adapters[id] == this._adapters[(id - 1)])) {
/* 591:612 */       id--;
/* 592:    */     }
/* 593:614 */     return id;
/* 594:    */   }
/* 595:    */   
/* 596:    */   public int getNodeIdent(int nodeHandle)
/* 597:    */   {
/* 598:619 */     return this._adapters[(nodeHandle >>> 16)].getNodeIdent(nodeHandle);
/* 599:    */   }
/* 600:    */   
/* 601:    */   public int getNodeHandle(int nodeId)
/* 602:    */   {
/* 603:624 */     return this._main.getNodeHandle(nodeId);
/* 604:    */   }
/* 605:    */   
/* 606:    */   public DOM getResultTreeFrag(int initSize, int rtfType)
/* 607:    */   {
/* 608:629 */     return this._main.getResultTreeFrag(initSize, rtfType);
/* 609:    */   }
/* 610:    */   
/* 611:    */   public DOM getResultTreeFrag(int initSize, int rtfType, boolean addToManager)
/* 612:    */   {
/* 613:634 */     return this._main.getResultTreeFrag(initSize, rtfType, addToManager);
/* 614:    */   }
/* 615:    */   
/* 616:    */   public DOM getMain()
/* 617:    */   {
/* 618:639 */     return this._main;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public SerializationHandler getOutputDomBuilder()
/* 622:    */   {
/* 623:647 */     return this._main.getOutputDomBuilder();
/* 624:    */   }
/* 625:    */   
/* 626:    */   public String lookupNamespace(int node, String prefix)
/* 627:    */     throws TransletException
/* 628:    */   {
/* 629:653 */     return this._main.lookupNamespace(node, prefix);
/* 630:    */   }
/* 631:    */   
/* 632:    */   public String getUnparsedEntityURI(String entity)
/* 633:    */   {
/* 634:658 */     return this._main.getUnparsedEntityURI(entity);
/* 635:    */   }
/* 636:    */   
/* 637:    */   public Hashtable getElementsWithIDs()
/* 638:    */   {
/* 639:663 */     return this._main.getElementsWithIDs();
/* 640:    */   }
/* 641:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.MultiDOM
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import org.apache.xalan.xsltc.DOM;
/*   5:    */ import org.apache.xalan.xsltc.DOMEnhancedForDTM;
/*   6:    */ import org.apache.xalan.xsltc.runtime.BasisLibrary;
/*   7:    */ import org.apache.xalan.xsltc.runtime.Hashtable;
/*   8:    */ import org.apache.xalan.xsltc.util.IntegerArray;
/*   9:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*  10:    */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*  11:    */ 
/*  12:    */ public class KeyIndex
/*  13:    */   extends DTMAxisIteratorBase
/*  14:    */ {
/*  15:    */   private Hashtable _index;
/*  16: 56 */   private int _currentDocumentNode = -1;
/*  17: 61 */   private Hashtable _rootToIndexMap = new Hashtable();
/*  18: 67 */   private IntegerArray _nodes = null;
/*  19:    */   private DOM _dom;
/*  20:    */   private DOMEnhancedForDTM _enhancedDOM;
/*  21: 80 */   private int _markedPosition = 0;
/*  22:    */   
/*  23:    */   public KeyIndex(int dummy) {}
/*  24:    */   
/*  25:    */   public void setRestartable(boolean flag) {}
/*  26:    */   
/*  27:    */   public void add(Object value, int node, int rootNode)
/*  28:    */   {
/*  29: 93 */     if (this._currentDocumentNode != rootNode)
/*  30:    */     {
/*  31: 94 */       this._currentDocumentNode = rootNode;
/*  32: 95 */       this._index = new Hashtable();
/*  33: 96 */       this._rootToIndexMap.put(new Integer(rootNode), this._index);
/*  34:    */     }
/*  35: 99 */     IntegerArray nodes = (IntegerArray)this._index.get(value);
/*  36:101 */     if (nodes == null)
/*  37:    */     {
/*  38:102 */       nodes = new IntegerArray();
/*  39:103 */       this._index.put(value, nodes);
/*  40:104 */       nodes.add(node);
/*  41:    */     }
/*  42:108 */     else if (node != nodes.at(nodes.cardinality() - 1))
/*  43:    */     {
/*  44:109 */       nodes.add(node);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   /**
/*  49:    */    * @deprecated
/*  50:    */    */
/*  51:    */   public void merge(KeyIndex other)
/*  52:    */   {
/*  53:118 */     if (other == null) {
/*  54:118 */       return;
/*  55:    */     }
/*  56:120 */     if (other._nodes != null) {
/*  57:121 */       if (this._nodes == null) {
/*  58:122 */         this._nodes = ((IntegerArray)other._nodes.clone());
/*  59:    */       } else {
/*  60:125 */         this._nodes.merge(other._nodes);
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   /**
/*  66:    */    * @deprecated
/*  67:    */    */
/*  68:    */   public void lookupId(Object value)
/*  69:    */   {
/*  70:140 */     this._nodes = null;
/*  71:    */     
/*  72:142 */     StringTokenizer values = new StringTokenizer((String)value, " \n\t");
/*  73:144 */     while (values.hasMoreElements())
/*  74:    */     {
/*  75:145 */       String token = (String)values.nextElement();
/*  76:146 */       IntegerArray nodes = (IntegerArray)this._index.get(token);
/*  77:148 */       if ((nodes == null) && (this._enhancedDOM != null) && (this._enhancedDOM.hasDOMSource())) {
/*  78:150 */         nodes = getDOMNodeById(token);
/*  79:    */       }
/*  80:153 */       if (nodes != null) {
/*  81:155 */         if (this._nodes == null)
/*  82:    */         {
/*  83:156 */           nodes = (IntegerArray)nodes.clone();
/*  84:157 */           this._nodes = nodes;
/*  85:    */         }
/*  86:    */         else
/*  87:    */         {
/*  88:160 */           this._nodes.merge(nodes);
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public IntegerArray getDOMNodeById(String id)
/*  95:    */   {
/*  96:172 */     IntegerArray nodes = null;
/*  97:174 */     if (this._enhancedDOM != null)
/*  98:    */     {
/*  99:175 */       int ident = this._enhancedDOM.getElementById(id);
/* 100:177 */       if (ident != -1)
/* 101:    */       {
/* 102:178 */         Integer root = new Integer(this._enhancedDOM.getDocument());
/* 103:179 */         Hashtable index = (Hashtable)this._rootToIndexMap.get(root);
/* 104:181 */         if (index == null)
/* 105:    */         {
/* 106:182 */           index = new Hashtable();
/* 107:183 */           this._rootToIndexMap.put(root, index);
/* 108:    */         }
/* 109:    */         else
/* 110:    */         {
/* 111:185 */           nodes = (IntegerArray)index.get(id);
/* 112:    */         }
/* 113:188 */         if (nodes == null)
/* 114:    */         {
/* 115:189 */           nodes = new IntegerArray();
/* 116:190 */           index.put(id, nodes);
/* 117:    */         }
/* 118:193 */         nodes.add(this._enhancedDOM.getNodeHandle(ident));
/* 119:    */       }
/* 120:    */     }
/* 121:197 */     return nodes;
/* 122:    */   }
/* 123:    */   
/* 124:    */   /**
/* 125:    */    * @deprecated
/* 126:    */    */
/* 127:    */   public void lookupKey(Object value)
/* 128:    */   {
/* 129:208 */     IntegerArray nodes = (IntegerArray)this._index.get(value);
/* 130:209 */     this._nodes = (nodes != null ? (IntegerArray)nodes.clone() : null);
/* 131:210 */     this._position = 0;
/* 132:    */   }
/* 133:    */   
/* 134:    */   /**
/* 135:    */    * @deprecated
/* 136:    */    */
/* 137:    */   public int next()
/* 138:    */   {
/* 139:220 */     if (this._nodes == null) {
/* 140:220 */       return -1;
/* 141:    */     }
/* 142:222 */     return this._position < this._nodes.cardinality() ? this._dom.getNodeHandle(this._nodes.at(this._position++)) : -1;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int containsID(int node, Object value)
/* 146:    */   {
/* 147:239 */     String string = (String)value;
/* 148:240 */     int rootHandle = this._dom.getAxisIterator(19).setStartNode(node).next();
/* 149:    */     
/* 150:    */ 
/* 151:    */ 
/* 152:244 */     Hashtable index = (Hashtable)this._rootToIndexMap.get(new Integer(rootHandle));
/* 153:    */     
/* 154:    */ 
/* 155:    */ 
/* 156:248 */     StringTokenizer values = new StringTokenizer(string, " \n\t");
/* 157:250 */     while (values.hasMoreElements())
/* 158:    */     {
/* 159:251 */       String token = (String)values.nextElement();
/* 160:252 */       IntegerArray nodes = null;
/* 161:254 */       if (index != null) {
/* 162:255 */         nodes = (IntegerArray)index.get(token);
/* 163:    */       }
/* 164:260 */       if ((nodes == null) && (this._enhancedDOM != null) && (this._enhancedDOM.hasDOMSource())) {
/* 165:262 */         nodes = getDOMNodeById(token);
/* 166:    */       }
/* 167:266 */       if ((nodes != null) && (nodes.indexOf(node) >= 0)) {
/* 168:267 */         return 1;
/* 169:    */       }
/* 170:    */     }
/* 171:272 */     return 0;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public int containsKey(int node, Object value)
/* 175:    */   {
/* 176:291 */     int rootHandle = this._dom.getAxisIterator(19).setStartNode(node).next();
/* 177:    */     
/* 178:    */ 
/* 179:    */ 
/* 180:295 */     Hashtable index = (Hashtable)this._rootToIndexMap.get(new Integer(rootHandle));
/* 181:300 */     if (index != null)
/* 182:    */     {
/* 183:301 */       IntegerArray nodes = (IntegerArray)index.get(value);
/* 184:302 */       return (nodes != null) && (nodes.indexOf(node) >= 0) ? 1 : 0;
/* 185:    */     }
/* 186:306 */     return 0;
/* 187:    */   }
/* 188:    */   
/* 189:    */   /**
/* 190:    */    * @deprecated
/* 191:    */    */
/* 192:    */   public DTMAxisIterator reset()
/* 193:    */   {
/* 194:316 */     this._position = 0;
/* 195:317 */     return this;
/* 196:    */   }
/* 197:    */   
/* 198:    */   /**
/* 199:    */    * @deprecated
/* 200:    */    */
/* 201:    */   public int getLast()
/* 202:    */   {
/* 203:327 */     return this._nodes == null ? 0 : this._nodes.cardinality();
/* 204:    */   }
/* 205:    */   
/* 206:    */   /**
/* 207:    */    * @deprecated
/* 208:    */    */
/* 209:    */   public int getPosition()
/* 210:    */   {
/* 211:337 */     return this._position;
/* 212:    */   }
/* 213:    */   
/* 214:    */   /**
/* 215:    */    * @deprecated
/* 216:    */    */
/* 217:    */   public void setMark()
/* 218:    */   {
/* 219:347 */     this._markedPosition = this._position;
/* 220:    */   }
/* 221:    */   
/* 222:    */   /**
/* 223:    */    * @deprecated
/* 224:    */    */
/* 225:    */   public void gotoMark()
/* 226:    */   {
/* 227:357 */     this._position = this._markedPosition;
/* 228:    */   }
/* 229:    */   
/* 230:    */   /**
/* 231:    */    * @deprecated
/* 232:    */    */
/* 233:    */   public DTMAxisIterator setStartNode(int start)
/* 234:    */   {
/* 235:368 */     if (start == -1) {
/* 236:369 */       this._nodes = null;
/* 237:371 */     } else if (this._nodes != null) {
/* 238:372 */       this._position = 0;
/* 239:    */     }
/* 240:374 */     return this;
/* 241:    */   }
/* 242:    */   
/* 243:    */   /**
/* 244:    */    * @deprecated
/* 245:    */    */
/* 246:    */   public int getStartNode()
/* 247:    */   {
/* 248:385 */     return 0;
/* 249:    */   }
/* 250:    */   
/* 251:    */   /**
/* 252:    */    * @deprecated
/* 253:    */    */
/* 254:    */   public boolean isReverse()
/* 255:    */   {
/* 256:395 */     return false;
/* 257:    */   }
/* 258:    */   
/* 259:    */   /**
/* 260:    */    * @deprecated
/* 261:    */    */
/* 262:    */   public DTMAxisIterator cloneIterator()
/* 263:    */   {
/* 264:405 */     KeyIndex other = new KeyIndex(0);
/* 265:406 */     other._index = this._index;
/* 266:407 */     other._rootToIndexMap = this._rootToIndexMap;
/* 267:408 */     other._nodes = this._nodes;
/* 268:409 */     other._position = this._position;
/* 269:410 */     return other;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setDom(DOM dom)
/* 273:    */   {
/* 274:414 */     this._dom = dom;
/* 275:415 */     if ((dom instanceof DOMEnhancedForDTM))
/* 276:    */     {
/* 277:416 */       this._enhancedDOM = ((DOMEnhancedForDTM)dom);
/* 278:    */     }
/* 279:418 */     else if ((dom instanceof DOMAdapter))
/* 280:    */     {
/* 281:419 */       DOM idom = ((DOMAdapter)dom).getDOMImpl();
/* 282:420 */       if ((idom instanceof DOMEnhancedForDTM)) {
/* 283:421 */         this._enhancedDOM = ((DOMEnhancedForDTM)idom);
/* 284:    */       }
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public KeyIndexIterator getKeyIndexIterator(Object keyValue, boolean isKeyCall)
/* 289:    */   {
/* 290:439 */     if ((keyValue instanceof DTMAxisIterator)) {
/* 291:440 */       return getKeyIndexIterator((DTMAxisIterator)keyValue, isKeyCall);
/* 292:    */     }
/* 293:442 */     return getKeyIndexIterator(BasisLibrary.stringF(keyValue, this._dom), isKeyCall);
/* 294:    */   }
/* 295:    */   
/* 296:    */   public KeyIndexIterator getKeyIndexIterator(String keyValue, boolean isKeyCall)
/* 297:    */   {
/* 298:460 */     return new KeyIndexIterator(keyValue, isKeyCall);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public KeyIndexIterator getKeyIndexIterator(DTMAxisIterator keyValue, boolean isKeyCall)
/* 302:    */   {
/* 303:476 */     return new KeyIndexIterator(keyValue, isKeyCall);
/* 304:    */   }
/* 305:    */   
/* 306:482 */   private static final IntegerArray EMPTY_NODES = new IntegerArray(0);
/* 307:    */   
/* 308:    */   public class KeyIndexIterator
/* 309:    */     extends MultiValuedNodeHeapIterator
/* 310:    */   {
/* 311:    */     private IntegerArray _nodes;
/* 312:    */     private DTMAxisIterator _keyValueIterator;
/* 313:    */     private String _keyValue;
/* 314:    */     private boolean _isKeyIterator;
/* 315:    */     
/* 316:    */     protected class KeyIndexHeapNode
/* 317:    */       extends MultiValuedNodeHeapIterator.HeapNode
/* 318:    */     {
/* 319:    */       private IntegerArray _nodes;
/* 320:544 */       private int _position = 0;
/* 321:550 */       private int _markPosition = -1;
/* 322:    */       
/* 323:    */       KeyIndexHeapNode(IntegerArray nodes)
/* 324:    */       {
/* 325:557 */         super();
/* 326:558 */         this._nodes = nodes;
/* 327:    */       }
/* 328:    */       
/* 329:    */       public int step()
/* 330:    */       {
/* 331:567 */         if (this._position < this._nodes.cardinality())
/* 332:    */         {
/* 333:568 */           this._node = this._nodes.at(this._position);
/* 334:569 */           this._position += 1;
/* 335:    */         }
/* 336:    */         else
/* 337:    */         {
/* 338:571 */           this._node = -1;
/* 339:    */         }
/* 340:574 */         return this._node;
/* 341:    */       }
/* 342:    */       
/* 343:    */       public MultiValuedNodeHeapIterator.HeapNode cloneHeapNode()
/* 344:    */       {
/* 345:584 */         KeyIndexHeapNode clone = (KeyIndexHeapNode)super.cloneHeapNode();
/* 346:    */         
/* 347:    */ 
/* 348:587 */         clone._nodes = this._nodes;
/* 349:588 */         clone._position = this._position;
/* 350:589 */         clone._markPosition = this._markPosition;
/* 351:    */         
/* 352:591 */         return clone;
/* 353:    */       }
/* 354:    */       
/* 355:    */       public void setMark()
/* 356:    */       {
/* 357:599 */         this._markPosition = this._position;
/* 358:    */       }
/* 359:    */       
/* 360:    */       public void gotoMark()
/* 361:    */       {
/* 362:606 */         this._position = this._markPosition;
/* 363:    */       }
/* 364:    */       
/* 365:    */       public boolean isLessThan(MultiValuedNodeHeapIterator.HeapNode heapNode)
/* 366:    */       {
/* 367:618 */         return this._node < heapNode._node;
/* 368:    */       }
/* 369:    */       
/* 370:    */       public MultiValuedNodeHeapIterator.HeapNode setStartNode(int node)
/* 371:    */       {
/* 372:630 */         return this;
/* 373:    */       }
/* 374:    */       
/* 375:    */       public MultiValuedNodeHeapIterator.HeapNode reset()
/* 376:    */       {
/* 377:637 */         this._position = 0;
/* 378:638 */         return this;
/* 379:    */       }
/* 380:    */     }
/* 381:    */     
/* 382:    */     KeyIndexIterator(String keyValue, boolean isKeyIterator)
/* 383:    */     {
/* 384:653 */       this._isKeyIterator = isKeyIterator;
/* 385:654 */       this._keyValue = keyValue;
/* 386:    */     }
/* 387:    */     
/* 388:    */     KeyIndexIterator(DTMAxisIterator keyValues, boolean isKeyIterator)
/* 389:    */     {
/* 390:667 */       this._keyValueIterator = keyValues;
/* 391:668 */       this._isKeyIterator = isKeyIterator;
/* 392:    */     }
/* 393:    */     
/* 394:    */     protected IntegerArray lookupNodes(int root, String keyValue)
/* 395:    */     {
/* 396:680 */       IntegerArray result = null;
/* 397:    */       
/* 398:    */ 
/* 399:683 */       Hashtable index = (Hashtable)KeyIndex.this._rootToIndexMap.get(new Integer(root));
/* 400:685 */       if (!this._isKeyIterator)
/* 401:    */       {
/* 402:688 */         StringTokenizer values = new StringTokenizer(keyValue, " \n\t");
/* 403:691 */         while (values.hasMoreElements())
/* 404:    */         {
/* 405:692 */           String token = (String)values.nextElement();
/* 406:693 */           IntegerArray nodes = null;
/* 407:696 */           if (index != null) {
/* 408:697 */             nodes = (IntegerArray)index.get(token);
/* 409:    */           }
/* 410:702 */           if ((nodes == null) && (KeyIndex.this._enhancedDOM != null) && (KeyIndex.this._enhancedDOM.hasDOMSource())) {
/* 411:704 */             nodes = KeyIndex.this.getDOMNodeById(token);
/* 412:    */           }
/* 413:709 */           if (nodes != null) {
/* 414:710 */             if (result == null) {
/* 415:711 */               result = (IntegerArray)nodes.clone();
/* 416:    */             } else {
/* 417:713 */               result.merge(nodes);
/* 418:    */             }
/* 419:    */           }
/* 420:    */         }
/* 421:    */       }
/* 422:717 */       else if (index != null)
/* 423:    */       {
/* 424:719 */         result = (IntegerArray)index.get(keyValue);
/* 425:    */       }
/* 426:722 */       return result;
/* 427:    */     }
/* 428:    */     
/* 429:    */     public DTMAxisIterator setStartNode(int node)
/* 430:    */     {
/* 431:734 */       this._startNode = node;
/* 432:738 */       if (this._keyValueIterator != null) {
/* 433:739 */         this._keyValueIterator = this._keyValueIterator.setStartNode(node);
/* 434:    */       }
/* 435:742 */       init();
/* 436:    */       
/* 437:744 */       return super.setStartNode(node);
/* 438:    */     }
/* 439:    */     
/* 440:    */     public int next()
/* 441:    */     {
/* 442:    */       int nodeHandle;
/* 443:760 */       if (this._nodes != null)
/* 444:    */       {
/* 445:761 */         if (this._position < this._nodes.cardinality()) {
/* 446:762 */           nodeHandle = returnNode(this._nodes.at(this._position));
/* 447:    */         } else {
/* 448:764 */           nodeHandle = -1;
/* 449:    */         }
/* 450:    */       }
/* 451:    */       else {
/* 452:767 */         nodeHandle = super.next();
/* 453:    */       }
/* 454:770 */       return nodeHandle;
/* 455:    */     }
/* 456:    */     
/* 457:    */     public DTMAxisIterator reset()
/* 458:    */     {
/* 459:780 */       if (this._nodes == null) {
/* 460:781 */         init();
/* 461:    */       } else {
/* 462:783 */         super.reset();
/* 463:    */       }
/* 464:786 */       return resetPosition();
/* 465:    */     }
/* 466:    */     
/* 467:    */     protected void init()
/* 468:    */     {
/* 469:796 */       super.init();
/* 470:797 */       this._position = 0;
/* 471:    */       
/* 472:    */ 
/* 473:800 */       int rootHandle = KeyIndex.this._dom.getAxisIterator(19).setStartNode(this._startNode).next();
/* 474:804 */       if (this._keyValueIterator == null)
/* 475:    */       {
/* 476:806 */         this._nodes = lookupNodes(rootHandle, this._keyValue);
/* 477:808 */         if (this._nodes == null) {
/* 478:809 */           this._nodes = KeyIndex.EMPTY_NODES;
/* 479:    */         }
/* 480:    */       }
/* 481:    */       else
/* 482:    */       {
/* 483:812 */         DTMAxisIterator keyValues = this._keyValueIterator.reset();
/* 484:813 */         int retrievedKeyValueIdx = 0;
/* 485:814 */         boolean foundNodes = false;
/* 486:    */         
/* 487:816 */         this._nodes = null;
/* 488:823 */         for (int keyValueNode = keyValues.next(); keyValueNode != -1; keyValueNode = keyValues.next())
/* 489:    */         {
/* 490:827 */           String keyValue = BasisLibrary.stringF(keyValueNode, KeyIndex.this._dom);
/* 491:    */           
/* 492:829 */           IntegerArray nodes = lookupNodes(rootHandle, keyValue);
/* 493:831 */           if (nodes != null) {
/* 494:832 */             if (!foundNodes)
/* 495:    */             {
/* 496:833 */               this._nodes = nodes;
/* 497:834 */               foundNodes = true;
/* 498:    */             }
/* 499:    */             else
/* 500:    */             {
/* 501:836 */               if (this._nodes != null)
/* 502:    */               {
/* 503:837 */                 addHeapNode(new KeyIndexHeapNode(this._nodes));
/* 504:838 */                 this._nodes = null;
/* 505:    */               }
/* 506:840 */               addHeapNode(new KeyIndexHeapNode(nodes));
/* 507:    */             }
/* 508:    */           }
/* 509:    */         }
/* 510:845 */         if (!foundNodes) {
/* 511:846 */           this._nodes = KeyIndex.EMPTY_NODES;
/* 512:    */         }
/* 513:    */       }
/* 514:    */     }
/* 515:    */     
/* 516:    */     public int getLast()
/* 517:    */     {
/* 518:860 */       return this._nodes != null ? this._nodes.cardinality() : super.getLast();
/* 519:    */     }
/* 520:    */     
/* 521:    */     public int getNodeByPosition(int position)
/* 522:    */     {
/* 523:870 */       int node = -1;
/* 524:876 */       if (this._nodes != null)
/* 525:    */       {
/* 526:877 */         if (position > 0) {
/* 527:878 */           if (position <= this._nodes.cardinality())
/* 528:    */           {
/* 529:879 */             this._position = position;
/* 530:880 */             node = this._nodes.at(position - 1);
/* 531:    */           }
/* 532:    */           else
/* 533:    */           {
/* 534:882 */             this._position = this._nodes.cardinality();
/* 535:    */           }
/* 536:    */         }
/* 537:    */       }
/* 538:    */       else {
/* 539:886 */         node = super.getNodeByPosition(position);
/* 540:    */       }
/* 541:889 */       return node;
/* 542:    */     }
/* 543:    */   }
/* 544:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.KeyIndex
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.AbstractList;
/*   8:    */ import java.util.AbstractSet;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collection;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.ListIterator;
/*  15:    */ import java.util.Map;
/*  16:    */ import java.util.Map.Entry;
/*  17:    */ import java.util.NoSuchElementException;
/*  18:    */ import java.util.Set;
/*  19:    */ import org.apache.commons.collections.MapIterator;
/*  20:    */ import org.apache.commons.collections.OrderedMap;
/*  21:    */ import org.apache.commons.collections.OrderedMapIterator;
/*  22:    */ import org.apache.commons.collections.ResettableIterator;
/*  23:    */ import org.apache.commons.collections.iterators.AbstractIteratorDecorator;
/*  24:    */ import org.apache.commons.collections.keyvalue.AbstractMapEntry;
/*  25:    */ import org.apache.commons.collections.list.UnmodifiableList;
/*  26:    */ 
/*  27:    */ public class ListOrderedMap
/*  28:    */   extends AbstractMapDecorator
/*  29:    */   implements OrderedMap, Serializable
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 2728177751851003750L;
/*  32: 79 */   protected final List insertOrder = new ArrayList();
/*  33:    */   
/*  34:    */   public static OrderedMap decorate(Map map)
/*  35:    */   {
/*  36: 90 */     return new ListOrderedMap(map);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ListOrderedMap()
/*  40:    */   {
/*  41:101 */     this(new HashMap());
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected ListOrderedMap(Map map)
/*  45:    */   {
/*  46:111 */     super(map);
/*  47:112 */     this.insertOrder.addAll(getMap().keySet());
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void writeObject(ObjectOutputStream out)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53:124 */     out.defaultWriteObject();
/*  54:125 */     out.writeObject(this.map);
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void readObject(ObjectInputStream in)
/*  58:    */     throws IOException, ClassNotFoundException
/*  59:    */   {
/*  60:137 */     in.defaultReadObject();
/*  61:138 */     this.map = ((Map)in.readObject());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public MapIterator mapIterator()
/*  65:    */   {
/*  66:144 */     return orderedMapIterator();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public OrderedMapIterator orderedMapIterator()
/*  70:    */   {
/*  71:148 */     return new ListOrderedMapIterator(this);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object firstKey()
/*  75:    */   {
/*  76:158 */     if (size() == 0) {
/*  77:159 */       throw new NoSuchElementException("Map is empty");
/*  78:    */     }
/*  79:161 */     return this.insertOrder.get(0);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object lastKey()
/*  83:    */   {
/*  84:171 */     if (size() == 0) {
/*  85:172 */       throw new NoSuchElementException("Map is empty");
/*  86:    */     }
/*  87:174 */     return this.insertOrder.get(size() - 1);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Object nextKey(Object key)
/*  91:    */   {
/*  92:185 */     int index = this.insertOrder.indexOf(key);
/*  93:186 */     if ((index >= 0) && (index < size() - 1)) {
/*  94:187 */       return this.insertOrder.get(index + 1);
/*  95:    */     }
/*  96:189 */     return null;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Object previousKey(Object key)
/* 100:    */   {
/* 101:200 */     int index = this.insertOrder.indexOf(key);
/* 102:201 */     if (index > 0) {
/* 103:202 */       return this.insertOrder.get(index - 1);
/* 104:    */     }
/* 105:204 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Object put(Object key, Object value)
/* 109:    */   {
/* 110:209 */     if (getMap().containsKey(key)) {
/* 111:211 */       return getMap().put(key, value);
/* 112:    */     }
/* 113:214 */     Object result = getMap().put(key, value);
/* 114:215 */     this.insertOrder.add(key);
/* 115:216 */     return result;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void putAll(Map map)
/* 119:    */   {
/* 120:221 */     for (Iterator it = map.entrySet().iterator(); it.hasNext();)
/* 121:    */     {
/* 122:222 */       Map.Entry entry = (Map.Entry)it.next();
/* 123:223 */       put(entry.getKey(), entry.getValue());
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Object remove(Object key)
/* 128:    */   {
/* 129:228 */     Object result = getMap().remove(key);
/* 130:229 */     this.insertOrder.remove(key);
/* 131:230 */     return result;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void clear()
/* 135:    */   {
/* 136:234 */     getMap().clear();
/* 137:235 */     this.insertOrder.clear();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Set keySet()
/* 141:    */   {
/* 142:248 */     return new KeySetView(this);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public List keyList()
/* 146:    */   {
/* 147:262 */     return UnmodifiableList.decorate(this.insertOrder);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Collection values()
/* 151:    */   {
/* 152:277 */     return new ValuesView(this);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List valueList()
/* 156:    */   {
/* 157:291 */     return new ValuesView(this);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Set entrySet()
/* 161:    */   {
/* 162:302 */     return new EntrySetView(this, this.insertOrder);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String toString()
/* 166:    */   {
/* 167:312 */     if (isEmpty()) {
/* 168:313 */       return "{}";
/* 169:    */     }
/* 170:315 */     StringBuffer buf = new StringBuffer();
/* 171:316 */     buf.append('{');
/* 172:317 */     boolean first = true;
/* 173:318 */     Iterator it = entrySet().iterator();
/* 174:319 */     while (it.hasNext())
/* 175:    */     {
/* 176:320 */       Map.Entry entry = (Map.Entry)it.next();
/* 177:321 */       Object key = entry.getKey();
/* 178:322 */       Object value = entry.getValue();
/* 179:323 */       if (first) {
/* 180:324 */         first = false;
/* 181:    */       } else {
/* 182:326 */         buf.append(", ");
/* 183:    */       }
/* 184:328 */       buf.append(key == this ? "(this Map)" : key);
/* 185:329 */       buf.append('=');
/* 186:330 */       buf.append(value == this ? "(this Map)" : value);
/* 187:    */     }
/* 188:332 */     buf.append('}');
/* 189:333 */     return buf.toString();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Object get(int index)
/* 193:    */   {
/* 194:345 */     return this.insertOrder.get(index);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Object getValue(int index)
/* 198:    */   {
/* 199:356 */     return get(this.insertOrder.get(index));
/* 200:    */   }
/* 201:    */   
/* 202:    */   public int indexOf(Object key)
/* 203:    */   {
/* 204:366 */     return this.insertOrder.indexOf(key);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Object setValue(int index, Object value)
/* 208:    */   {
/* 209:378 */     Object key = this.insertOrder.get(index);
/* 210:379 */     return put(key, value);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Object put(int index, Object key, Object value)
/* 214:    */   {
/* 215:402 */     Map m = getMap();
/* 216:403 */     if (m.containsKey(key))
/* 217:    */     {
/* 218:404 */       Object result = m.remove(key);
/* 219:405 */       int pos = this.insertOrder.indexOf(key);
/* 220:406 */       this.insertOrder.remove(pos);
/* 221:407 */       if (pos < index) {
/* 222:408 */         index--;
/* 223:    */       }
/* 224:410 */       this.insertOrder.add(index, key);
/* 225:411 */       m.put(key, value);
/* 226:412 */       return result;
/* 227:    */     }
/* 228:414 */     this.insertOrder.add(index, key);
/* 229:415 */     m.put(key, value);
/* 230:416 */     return null;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Object remove(int index)
/* 234:    */   {
/* 235:428 */     return remove(get(index));
/* 236:    */   }
/* 237:    */   
/* 238:    */   public List asList()
/* 239:    */   {
/* 240:449 */     return keyList();
/* 241:    */   }
/* 242:    */   
/* 243:    */   static class ValuesView
/* 244:    */     extends AbstractList
/* 245:    */   {
/* 246:    */     private final ListOrderedMap parent;
/* 247:    */     
/* 248:    */     ValuesView(ListOrderedMap parent)
/* 249:    */     {
/* 250:458 */       this.parent = parent;
/* 251:    */     }
/* 252:    */     
/* 253:    */     public int size()
/* 254:    */     {
/* 255:462 */       return this.parent.size();
/* 256:    */     }
/* 257:    */     
/* 258:    */     public boolean contains(Object value)
/* 259:    */     {
/* 260:466 */       return this.parent.containsValue(value);
/* 261:    */     }
/* 262:    */     
/* 263:    */     public void clear()
/* 264:    */     {
/* 265:470 */       this.parent.clear();
/* 266:    */     }
/* 267:    */     
/* 268:    */     public Iterator iterator()
/* 269:    */     {
/* 270:474 */       new AbstractIteratorDecorator(this.parent.entrySet().iterator())
/* 271:    */       {
/* 272:    */         public Object next()
/* 273:    */         {
/* 274:476 */           return ((Map.Entry)this.iterator.next()).getValue();
/* 275:    */         }
/* 276:    */       };
/* 277:    */     }
/* 278:    */     
/* 279:    */     public Object get(int index)
/* 280:    */     {
/* 281:482 */       return this.parent.getValue(index);
/* 282:    */     }
/* 283:    */     
/* 284:    */     public Object set(int index, Object value)
/* 285:    */     {
/* 286:486 */       return this.parent.setValue(index, value);
/* 287:    */     }
/* 288:    */     
/* 289:    */     public Object remove(int index)
/* 290:    */     {
/* 291:490 */       return this.parent.remove(index);
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   static class KeySetView
/* 296:    */     extends AbstractSet
/* 297:    */   {
/* 298:    */     private final ListOrderedMap parent;
/* 299:    */     
/* 300:    */     KeySetView(ListOrderedMap parent)
/* 301:    */     {
/* 302:500 */       this.parent = parent;
/* 303:    */     }
/* 304:    */     
/* 305:    */     public int size()
/* 306:    */     {
/* 307:504 */       return this.parent.size();
/* 308:    */     }
/* 309:    */     
/* 310:    */     public boolean contains(Object value)
/* 311:    */     {
/* 312:508 */       return this.parent.containsKey(value);
/* 313:    */     }
/* 314:    */     
/* 315:    */     public void clear()
/* 316:    */     {
/* 317:512 */       this.parent.clear();
/* 318:    */     }
/* 319:    */     
/* 320:    */     public Iterator iterator()
/* 321:    */     {
/* 322:516 */       new AbstractIteratorDecorator(this.parent.entrySet().iterator())
/* 323:    */       {
/* 324:    */         public Object next()
/* 325:    */         {
/* 326:518 */           return ((Map.Entry)super.next()).getKey();
/* 327:    */         }
/* 328:    */       };
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   static class EntrySetView
/* 333:    */     extends AbstractSet
/* 334:    */   {
/* 335:    */     private final ListOrderedMap parent;
/* 336:    */     private final List insertOrder;
/* 337:    */     private Set entrySet;
/* 338:    */     
/* 339:    */     public EntrySetView(ListOrderedMap parent, List insertOrder)
/* 340:    */     {
/* 341:532 */       this.parent = parent;
/* 342:533 */       this.insertOrder = insertOrder;
/* 343:    */     }
/* 344:    */     
/* 345:    */     private Set getEntrySet()
/* 346:    */     {
/* 347:537 */       if (this.entrySet == null) {
/* 348:538 */         this.entrySet = this.parent.getMap().entrySet();
/* 349:    */       }
/* 350:540 */       return this.entrySet;
/* 351:    */     }
/* 352:    */     
/* 353:    */     public int size()
/* 354:    */     {
/* 355:544 */       return this.parent.size();
/* 356:    */     }
/* 357:    */     
/* 358:    */     public boolean isEmpty()
/* 359:    */     {
/* 360:547 */       return this.parent.isEmpty();
/* 361:    */     }
/* 362:    */     
/* 363:    */     public boolean contains(Object obj)
/* 364:    */     {
/* 365:551 */       return getEntrySet().contains(obj);
/* 366:    */     }
/* 367:    */     
/* 368:    */     public boolean containsAll(Collection coll)
/* 369:    */     {
/* 370:555 */       return getEntrySet().containsAll(coll);
/* 371:    */     }
/* 372:    */     
/* 373:    */     public boolean remove(Object obj)
/* 374:    */     {
/* 375:559 */       if (!(obj instanceof Map.Entry)) {
/* 376:560 */         return false;
/* 377:    */       }
/* 378:562 */       if (getEntrySet().contains(obj))
/* 379:    */       {
/* 380:563 */         Object key = ((Map.Entry)obj).getKey();
/* 381:564 */         this.parent.remove(key);
/* 382:565 */         return true;
/* 383:    */       }
/* 384:567 */       return false;
/* 385:    */     }
/* 386:    */     
/* 387:    */     public void clear()
/* 388:    */     {
/* 389:571 */       this.parent.clear();
/* 390:    */     }
/* 391:    */     
/* 392:    */     public boolean equals(Object obj)
/* 393:    */     {
/* 394:575 */       if (obj == this) {
/* 395:576 */         return true;
/* 396:    */       }
/* 397:578 */       return getEntrySet().equals(obj);
/* 398:    */     }
/* 399:    */     
/* 400:    */     public int hashCode()
/* 401:    */     {
/* 402:582 */       return getEntrySet().hashCode();
/* 403:    */     }
/* 404:    */     
/* 405:    */     public String toString()
/* 406:    */     {
/* 407:586 */       return getEntrySet().toString();
/* 408:    */     }
/* 409:    */     
/* 410:    */     public Iterator iterator()
/* 411:    */     {
/* 412:590 */       return new ListOrderedMap.ListOrderedIterator(this.parent, this.insertOrder);
/* 413:    */     }
/* 414:    */   }
/* 415:    */   
/* 416:    */   static class ListOrderedIterator
/* 417:    */     extends AbstractIteratorDecorator
/* 418:    */   {
/* 419:    */     private final ListOrderedMap parent;
/* 420:597 */     private Object last = null;
/* 421:    */     
/* 422:    */     ListOrderedIterator(ListOrderedMap parent, List insertOrder)
/* 423:    */     {
/* 424:600 */       super();
/* 425:601 */       this.parent = parent;
/* 426:    */     }
/* 427:    */     
/* 428:    */     public Object next()
/* 429:    */     {
/* 430:605 */       this.last = super.next();
/* 431:606 */       return new ListOrderedMap.ListOrderedMapEntry(this.parent, this.last);
/* 432:    */     }
/* 433:    */     
/* 434:    */     public void remove()
/* 435:    */     {
/* 436:610 */       super.remove();
/* 437:611 */       this.parent.getMap().remove(this.last);
/* 438:    */     }
/* 439:    */   }
/* 440:    */   
/* 441:    */   static class ListOrderedMapEntry
/* 442:    */     extends AbstractMapEntry
/* 443:    */   {
/* 444:    */     private final ListOrderedMap parent;
/* 445:    */     
/* 446:    */     ListOrderedMapEntry(ListOrderedMap parent, Object key)
/* 447:    */     {
/* 448:620 */       super(null);
/* 449:621 */       this.parent = parent;
/* 450:    */     }
/* 451:    */     
/* 452:    */     public Object getValue()
/* 453:    */     {
/* 454:625 */       return this.parent.get(this.key);
/* 455:    */     }
/* 456:    */     
/* 457:    */     public Object setValue(Object value)
/* 458:    */     {
/* 459:629 */       return this.parent.getMap().put(this.key, value);
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   static class ListOrderedMapIterator
/* 464:    */     implements OrderedMapIterator, ResettableIterator
/* 465:    */   {
/* 466:    */     private final ListOrderedMap parent;
/* 467:    */     private ListIterator iterator;
/* 468:637 */     private Object last = null;
/* 469:638 */     private boolean readable = false;
/* 470:    */     
/* 471:    */     ListOrderedMapIterator(ListOrderedMap parent)
/* 472:    */     {
/* 473:642 */       this.parent = parent;
/* 474:643 */       this.iterator = parent.insertOrder.listIterator();
/* 475:    */     }
/* 476:    */     
/* 477:    */     public boolean hasNext()
/* 478:    */     {
/* 479:647 */       return this.iterator.hasNext();
/* 480:    */     }
/* 481:    */     
/* 482:    */     public Object next()
/* 483:    */     {
/* 484:651 */       this.last = this.iterator.next();
/* 485:652 */       this.readable = true;
/* 486:653 */       return this.last;
/* 487:    */     }
/* 488:    */     
/* 489:    */     public boolean hasPrevious()
/* 490:    */     {
/* 491:657 */       return this.iterator.hasPrevious();
/* 492:    */     }
/* 493:    */     
/* 494:    */     public Object previous()
/* 495:    */     {
/* 496:661 */       this.last = this.iterator.previous();
/* 497:662 */       this.readable = true;
/* 498:663 */       return this.last;
/* 499:    */     }
/* 500:    */     
/* 501:    */     public void remove()
/* 502:    */     {
/* 503:667 */       if (!this.readable) {
/* 504:668 */         throw new IllegalStateException("remove() can only be called once after next()");
/* 505:    */       }
/* 506:670 */       this.iterator.remove();
/* 507:671 */       this.parent.map.remove(this.last);
/* 508:672 */       this.readable = false;
/* 509:    */     }
/* 510:    */     
/* 511:    */     public Object getKey()
/* 512:    */     {
/* 513:676 */       if (!this.readable) {
/* 514:677 */         throw new IllegalStateException("getKey() can only be called after next() and before remove()");
/* 515:    */       }
/* 516:679 */       return this.last;
/* 517:    */     }
/* 518:    */     
/* 519:    */     public Object getValue()
/* 520:    */     {
/* 521:683 */       if (!this.readable) {
/* 522:684 */         throw new IllegalStateException("getValue() can only be called after next() and before remove()");
/* 523:    */       }
/* 524:686 */       return this.parent.get(this.last);
/* 525:    */     }
/* 526:    */     
/* 527:    */     public Object setValue(Object value)
/* 528:    */     {
/* 529:690 */       if (!this.readable) {
/* 530:691 */         throw new IllegalStateException("setValue() can only be called after next() and before remove()");
/* 531:    */       }
/* 532:693 */       return this.parent.map.put(this.last, value);
/* 533:    */     }
/* 534:    */     
/* 535:    */     public void reset()
/* 536:    */     {
/* 537:697 */       this.iterator = this.parent.insertOrder.listIterator();
/* 538:698 */       this.last = null;
/* 539:699 */       this.readable = false;
/* 540:    */     }
/* 541:    */     
/* 542:    */     public String toString()
/* 543:    */     {
/* 544:703 */       if (this.readable == true) {
/* 545:704 */         return "Iterator[" + getKey() + "=" + getValue() + "]";
/* 546:    */       }
/* 547:706 */       return "Iterator[]";
/* 548:    */     }
/* 549:    */   }
/* 550:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.ListOrderedMap
 * JD-Core Version:    0.7.0.1
 */
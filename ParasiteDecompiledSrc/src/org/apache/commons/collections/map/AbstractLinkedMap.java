/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.util.ConcurrentModificationException;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.NoSuchElementException;
/*   7:    */ import org.apache.commons.collections.MapIterator;
/*   8:    */ import org.apache.commons.collections.OrderedIterator;
/*   9:    */ import org.apache.commons.collections.OrderedMap;
/*  10:    */ import org.apache.commons.collections.OrderedMapIterator;
/*  11:    */ import org.apache.commons.collections.ResettableIterator;
/*  12:    */ import org.apache.commons.collections.iterators.EmptyOrderedIterator;
/*  13:    */ import org.apache.commons.collections.iterators.EmptyOrderedMapIterator;
/*  14:    */ 
/*  15:    */ public class AbstractLinkedMap
/*  16:    */   extends AbstractHashedMap
/*  17:    */   implements OrderedMap
/*  18:    */ {
/*  19:    */   protected transient LinkEntry header;
/*  20:    */   
/*  21:    */   protected AbstractLinkedMap() {}
/*  22:    */   
/*  23:    */   protected AbstractLinkedMap(int initialCapacity, float loadFactor, int threshold)
/*  24:    */   {
/*  25: 86 */     super(initialCapacity, loadFactor, threshold);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected AbstractLinkedMap(int initialCapacity)
/*  29:    */   {
/*  30: 96 */     super(initialCapacity);
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected AbstractLinkedMap(int initialCapacity, float loadFactor)
/*  34:    */   {
/*  35:109 */     super(initialCapacity, loadFactor);
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected AbstractLinkedMap(Map map)
/*  39:    */   {
/*  40:119 */     super(map);
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected void init()
/*  44:    */   {
/*  45:130 */     this.header = ((LinkEntry)createEntry(null, -1, null, null));
/*  46:131 */     this.header.before = (this.header.after = this.header);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean containsValue(Object value)
/*  50:    */   {
/*  51:143 */     if (value == null) {
/*  52:144 */       for (LinkEntry entry = this.header.after; entry != this.header; entry = entry.after) {
/*  53:145 */         if (entry.getValue() == null) {
/*  54:146 */           return true;
/*  55:    */         }
/*  56:    */       }
/*  57:    */     } else {
/*  58:150 */       for (LinkEntry entry = this.header.after; entry != this.header; entry = entry.after) {
/*  59:151 */         if (isEqualValue(value, entry.getValue())) {
/*  60:152 */           return true;
/*  61:    */         }
/*  62:    */       }
/*  63:    */     }
/*  64:156 */     return false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void clear()
/*  68:    */   {
/*  69:165 */     super.clear();
/*  70:166 */     this.header.before = (this.header.after = this.header);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Object firstKey()
/*  74:    */   {
/*  75:176 */     if (this.size == 0) {
/*  76:177 */       throw new NoSuchElementException("Map is empty");
/*  77:    */     }
/*  78:179 */     return this.header.after.getKey();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Object lastKey()
/*  82:    */   {
/*  83:188 */     if (this.size == 0) {
/*  84:189 */       throw new NoSuchElementException("Map is empty");
/*  85:    */     }
/*  86:191 */     return this.header.before.getKey();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Object nextKey(Object key)
/*  90:    */   {
/*  91:201 */     LinkEntry entry = (LinkEntry)getEntry(key);
/*  92:202 */     return (entry == null) || (entry.after == this.header) ? null : entry.after.getKey();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Object previousKey(Object key)
/*  96:    */   {
/*  97:212 */     LinkEntry entry = (LinkEntry)getEntry(key);
/*  98:213 */     return (entry == null) || (entry.before == this.header) ? null : entry.before.getKey();
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected LinkEntry getEntry(int index)
/* 102:    */   {
/* 103:225 */     if (index < 0) {
/* 104:226 */       throw new IndexOutOfBoundsException("Index " + index + " is less than zero");
/* 105:    */     }
/* 106:228 */     if (index >= this.size) {
/* 107:229 */       throw new IndexOutOfBoundsException("Index " + index + " is invalid for size " + this.size);
/* 108:    */     }
/* 109:    */     LinkEntry entry;
/* 110:232 */     if (index < this.size / 2)
/* 111:    */     {
/* 112:234 */       LinkEntry entry = this.header.after;
/* 113:235 */       for (int currentIndex = 0; currentIndex < index; currentIndex++) {
/* 114:236 */         entry = entry.after;
/* 115:    */       }
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:240 */       entry = this.header;
/* 120:241 */       for (int currentIndex = this.size; currentIndex > index; currentIndex--) {
/* 121:242 */         entry = entry.before;
/* 122:    */       }
/* 123:    */     }
/* 124:245 */     return entry;
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void addEntry(AbstractHashedMap.HashEntry entry, int hashIndex)
/* 128:    */   {
/* 129:258 */     LinkEntry link = (LinkEntry)entry;
/* 130:259 */     link.after = this.header;
/* 131:260 */     link.before = this.header.before;
/* 132:261 */     this.header.before.after = link;
/* 133:262 */     this.header.before = link;
/* 134:263 */     this.data[hashIndex] = entry;
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected AbstractHashedMap.HashEntry createEntry(AbstractHashedMap.HashEntry next, int hashCode, Object key, Object value)
/* 138:    */   {
/* 139:278 */     return new LinkEntry(next, hashCode, key, value);
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void removeEntry(AbstractHashedMap.HashEntry entry, int hashIndex, AbstractHashedMap.HashEntry previous)
/* 143:    */   {
/* 144:292 */     LinkEntry link = (LinkEntry)entry;
/* 145:293 */     link.before.after = link.after;
/* 146:294 */     link.after.before = link.before;
/* 147:295 */     link.after = null;
/* 148:296 */     link.before = null;
/* 149:297 */     super.removeEntry(entry, hashIndex, previous);
/* 150:    */   }
/* 151:    */   
/* 152:    */   protected LinkEntry entryBefore(LinkEntry entry)
/* 153:    */   {
/* 154:311 */     return entry.before;
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected LinkEntry entryAfter(LinkEntry entry)
/* 158:    */   {
/* 159:324 */     return entry.after;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public MapIterator mapIterator()
/* 163:    */   {
/* 164:339 */     if (this.size == 0) {
/* 165:340 */       return EmptyOrderedMapIterator.INSTANCE;
/* 166:    */     }
/* 167:342 */     return new LinkMapIterator(this);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public OrderedMapIterator orderedMapIterator()
/* 171:    */   {
/* 172:356 */     if (this.size == 0) {
/* 173:357 */       return EmptyOrderedMapIterator.INSTANCE;
/* 174:    */     }
/* 175:359 */     return new LinkMapIterator(this);
/* 176:    */   }
/* 177:    */   
/* 178:    */   protected static class LinkMapIterator
/* 179:    */     extends AbstractLinkedMap.LinkIterator
/* 180:    */     implements OrderedMapIterator
/* 181:    */   {
/* 182:    */     protected LinkMapIterator(AbstractLinkedMap parent)
/* 183:    */     {
/* 184:368 */       super();
/* 185:    */     }
/* 186:    */     
/* 187:    */     public Object next()
/* 188:    */     {
/* 189:372 */       return super.nextEntry().getKey();
/* 190:    */     }
/* 191:    */     
/* 192:    */     public Object previous()
/* 193:    */     {
/* 194:376 */       return super.previousEntry().getKey();
/* 195:    */     }
/* 196:    */     
/* 197:    */     public Object getKey()
/* 198:    */     {
/* 199:380 */       AbstractHashedMap.HashEntry current = currentEntry();
/* 200:381 */       if (current == null) {
/* 201:382 */         throw new IllegalStateException("getKey() can only be called after next() and before remove()");
/* 202:    */       }
/* 203:384 */       return current.getKey();
/* 204:    */     }
/* 205:    */     
/* 206:    */     public Object getValue()
/* 207:    */     {
/* 208:388 */       AbstractHashedMap.HashEntry current = currentEntry();
/* 209:389 */       if (current == null) {
/* 210:390 */         throw new IllegalStateException("getValue() can only be called after next() and before remove()");
/* 211:    */       }
/* 212:392 */       return current.getValue();
/* 213:    */     }
/* 214:    */     
/* 215:    */     public Object setValue(Object value)
/* 216:    */     {
/* 217:396 */       AbstractHashedMap.HashEntry current = currentEntry();
/* 218:397 */       if (current == null) {
/* 219:398 */         throw new IllegalStateException("setValue() can only be called after next() and before remove()");
/* 220:    */       }
/* 221:400 */       return current.setValue(value);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   protected Iterator createEntrySetIterator()
/* 226:    */   {
/* 227:412 */     if (size() == 0) {
/* 228:413 */       return EmptyOrderedIterator.INSTANCE;
/* 229:    */     }
/* 230:415 */     return new EntrySetIterator(this);
/* 231:    */   }
/* 232:    */   
/* 233:    */   protected static class EntrySetIterator
/* 234:    */     extends AbstractLinkedMap.LinkIterator
/* 235:    */   {
/* 236:    */     protected EntrySetIterator(AbstractLinkedMap parent)
/* 237:    */     {
/* 238:424 */       super();
/* 239:    */     }
/* 240:    */     
/* 241:    */     public Object next()
/* 242:    */     {
/* 243:428 */       return super.nextEntry();
/* 244:    */     }
/* 245:    */     
/* 246:    */     public Object previous()
/* 247:    */     {
/* 248:432 */       return super.previousEntry();
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   protected Iterator createKeySetIterator()
/* 253:    */   {
/* 254:444 */     if (size() == 0) {
/* 255:445 */       return EmptyOrderedIterator.INSTANCE;
/* 256:    */     }
/* 257:447 */     return new KeySetIterator(this);
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected static class KeySetIterator
/* 261:    */     extends AbstractLinkedMap.EntrySetIterator
/* 262:    */   {
/* 263:    */     protected KeySetIterator(AbstractLinkedMap parent)
/* 264:    */     {
/* 265:456 */       super();
/* 266:    */     }
/* 267:    */     
/* 268:    */     public Object next()
/* 269:    */     {
/* 270:460 */       return super.nextEntry().getKey();
/* 271:    */     }
/* 272:    */     
/* 273:    */     public Object previous()
/* 274:    */     {
/* 275:464 */       return super.previousEntry().getKey();
/* 276:    */     }
/* 277:    */   }
/* 278:    */   
/* 279:    */   protected Iterator createValuesIterator()
/* 280:    */   {
/* 281:476 */     if (size() == 0) {
/* 282:477 */       return EmptyOrderedIterator.INSTANCE;
/* 283:    */     }
/* 284:479 */     return new ValuesIterator(this);
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected static class ValuesIterator
/* 288:    */     extends AbstractLinkedMap.LinkIterator
/* 289:    */   {
/* 290:    */     protected ValuesIterator(AbstractLinkedMap parent)
/* 291:    */     {
/* 292:488 */       super();
/* 293:    */     }
/* 294:    */     
/* 295:    */     public Object next()
/* 296:    */     {
/* 297:492 */       return super.nextEntry().getValue();
/* 298:    */     }
/* 299:    */     
/* 300:    */     public Object previous()
/* 301:    */     {
/* 302:496 */       return super.previousEntry().getValue();
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   protected static class LinkEntry
/* 307:    */     extends AbstractHashedMap.HashEntry
/* 308:    */   {
/* 309:    */     protected LinkEntry before;
/* 310:    */     protected LinkEntry after;
/* 311:    */     
/* 312:    */     protected LinkEntry(AbstractHashedMap.HashEntry next, int hashCode, Object key, Object value)
/* 313:    */     {
/* 314:524 */       super(hashCode, key, value);
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   protected static abstract class LinkIterator
/* 319:    */     implements OrderedIterator, ResettableIterator
/* 320:    */   {
/* 321:    */     protected final AbstractLinkedMap parent;
/* 322:    */     protected AbstractLinkedMap.LinkEntry last;
/* 323:    */     protected AbstractLinkedMap.LinkEntry next;
/* 324:    */     protected int expectedModCount;
/* 325:    */     
/* 326:    */     protected LinkIterator(AbstractLinkedMap parent)
/* 327:    */     {
/* 328:545 */       this.parent = parent;
/* 329:546 */       this.next = parent.header.after;
/* 330:547 */       this.expectedModCount = parent.modCount;
/* 331:    */     }
/* 332:    */     
/* 333:    */     public boolean hasNext()
/* 334:    */     {
/* 335:551 */       return this.next != this.parent.header;
/* 336:    */     }
/* 337:    */     
/* 338:    */     public boolean hasPrevious()
/* 339:    */     {
/* 340:555 */       return this.next.before != this.parent.header;
/* 341:    */     }
/* 342:    */     
/* 343:    */     protected AbstractLinkedMap.LinkEntry nextEntry()
/* 344:    */     {
/* 345:559 */       if (this.parent.modCount != this.expectedModCount) {
/* 346:560 */         throw new ConcurrentModificationException();
/* 347:    */       }
/* 348:562 */       if (this.next == this.parent.header) {
/* 349:563 */         throw new NoSuchElementException("No next() entry in the iteration");
/* 350:    */       }
/* 351:565 */       this.last = this.next;
/* 352:566 */       this.next = this.next.after;
/* 353:567 */       return this.last;
/* 354:    */     }
/* 355:    */     
/* 356:    */     protected AbstractLinkedMap.LinkEntry previousEntry()
/* 357:    */     {
/* 358:571 */       if (this.parent.modCount != this.expectedModCount) {
/* 359:572 */         throw new ConcurrentModificationException();
/* 360:    */       }
/* 361:574 */       AbstractLinkedMap.LinkEntry previous = this.next.before;
/* 362:575 */       if (previous == this.parent.header) {
/* 363:576 */         throw new NoSuchElementException("No previous() entry in the iteration");
/* 364:    */       }
/* 365:578 */       this.next = previous;
/* 366:579 */       this.last = previous;
/* 367:580 */       return this.last;
/* 368:    */     }
/* 369:    */     
/* 370:    */     protected AbstractLinkedMap.LinkEntry currentEntry()
/* 371:    */     {
/* 372:584 */       return this.last;
/* 373:    */     }
/* 374:    */     
/* 375:    */     public void remove()
/* 376:    */     {
/* 377:588 */       if (this.last == null) {
/* 378:589 */         throw new IllegalStateException("remove() can only be called once after next()");
/* 379:    */       }
/* 380:591 */       if (this.parent.modCount != this.expectedModCount) {
/* 381:592 */         throw new ConcurrentModificationException();
/* 382:    */       }
/* 383:594 */       this.parent.remove(this.last.getKey());
/* 384:595 */       this.last = null;
/* 385:596 */       this.expectedModCount = this.parent.modCount;
/* 386:    */     }
/* 387:    */     
/* 388:    */     public void reset()
/* 389:    */     {
/* 390:600 */       this.last = null;
/* 391:601 */       this.next = this.parent.header.after;
/* 392:    */     }
/* 393:    */     
/* 394:    */     public String toString()
/* 395:    */     {
/* 396:605 */       if (this.last != null) {
/* 397:606 */         return "Iterator[" + this.last.getKey() + "=" + this.last.getValue() + "]";
/* 398:    */       }
/* 399:608 */       return "Iterator[]";
/* 400:    */     }
/* 401:    */   }
/* 402:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.AbstractLinkedMap
 * JD-Core Version:    0.7.0.1
 */
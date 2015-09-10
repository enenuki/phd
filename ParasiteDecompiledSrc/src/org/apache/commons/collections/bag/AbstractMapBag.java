/*   1:    */ package org.apache.commons.collections.bag;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.lang.reflect.Array;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.ConcurrentModificationException;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.apache.commons.collections.Bag;
/*  14:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  15:    */ 
/*  16:    */ public abstract class AbstractMapBag
/*  17:    */   implements Bag
/*  18:    */ {
/*  19:    */   private transient Map map;
/*  20:    */   private int size;
/*  21:    */   private transient int modCount;
/*  22:    */   private transient Set uniqueSet;
/*  23:    */   
/*  24:    */   protected AbstractMapBag() {}
/*  25:    */   
/*  26:    */   protected AbstractMapBag(Map map)
/*  27:    */   {
/*  28: 76 */     this.map = map;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected Map getMap()
/*  32:    */   {
/*  33: 86 */     return this.map;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int size()
/*  37:    */   {
/*  38: 96 */     return this.size;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean isEmpty()
/*  42:    */   {
/*  43:105 */     return this.map.isEmpty();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getCount(Object object)
/*  47:    */   {
/*  48:116 */     MutableInteger count = (MutableInteger)this.map.get(object);
/*  49:117 */     if (count != null) {
/*  50:118 */       return count.value;
/*  51:    */     }
/*  52:120 */     return 0;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean contains(Object object)
/*  56:    */   {
/*  57:132 */     return this.map.containsKey(object);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean containsAll(Collection coll)
/*  61:    */   {
/*  62:142 */     if ((coll instanceof Bag)) {
/*  63:143 */       return containsAll((Bag)coll);
/*  64:    */     }
/*  65:145 */     return containsAll(new HashBag(coll));
/*  66:    */   }
/*  67:    */   
/*  68:    */   boolean containsAll(Bag other)
/*  69:    */   {
/*  70:156 */     boolean result = true;
/*  71:157 */     Iterator it = other.uniqueSet().iterator();
/*  72:158 */     while (it.hasNext())
/*  73:    */     {
/*  74:159 */       Object current = it.next();
/*  75:160 */       boolean contains = getCount(current) >= other.getCount(current);
/*  76:161 */       result = (result) && (contains);
/*  77:    */     }
/*  78:163 */     return result;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Iterator iterator()
/*  82:    */   {
/*  83:174 */     return new BagIterator(this);
/*  84:    */   }
/*  85:    */   
/*  86:    */   static class BagIterator
/*  87:    */     implements Iterator
/*  88:    */   {
/*  89:    */     private AbstractMapBag parent;
/*  90:    */     private Iterator entryIterator;
/*  91:    */     private Map.Entry current;
/*  92:    */     private int itemCount;
/*  93:    */     private final int mods;
/*  94:    */     private boolean canRemove;
/*  95:    */     
/*  96:    */     public BagIterator(AbstractMapBag parent)
/*  97:    */     {
/*  98:194 */       this.parent = parent;
/*  99:195 */       this.entryIterator = parent.map.entrySet().iterator();
/* 100:196 */       this.current = null;
/* 101:197 */       this.mods = parent.modCount;
/* 102:198 */       this.canRemove = false;
/* 103:    */     }
/* 104:    */     
/* 105:    */     public boolean hasNext()
/* 106:    */     {
/* 107:202 */       return (this.itemCount > 0) || (this.entryIterator.hasNext());
/* 108:    */     }
/* 109:    */     
/* 110:    */     public Object next()
/* 111:    */     {
/* 112:206 */       if (this.parent.modCount != this.mods) {
/* 113:207 */         throw new ConcurrentModificationException();
/* 114:    */       }
/* 115:209 */       if (this.itemCount == 0)
/* 116:    */       {
/* 117:210 */         this.current = ((Map.Entry)this.entryIterator.next());
/* 118:211 */         this.itemCount = ((AbstractMapBag.MutableInteger)this.current.getValue()).value;
/* 119:    */       }
/* 120:213 */       this.canRemove = true;
/* 121:214 */       this.itemCount -= 1;
/* 122:215 */       return this.current.getKey();
/* 123:    */     }
/* 124:    */     
/* 125:    */     public void remove()
/* 126:    */     {
/* 127:219 */       if (this.parent.modCount != this.mods) {
/* 128:220 */         throw new ConcurrentModificationException();
/* 129:    */       }
/* 130:222 */       if (!this.canRemove) {
/* 131:223 */         throw new IllegalStateException();
/* 132:    */       }
/* 133:225 */       AbstractMapBag.MutableInteger mut = (AbstractMapBag.MutableInteger)this.current.getValue();
/* 134:226 */       if (mut.value > 1) {
/* 135:227 */         mut.value -= 1;
/* 136:    */       } else {
/* 137:229 */         this.entryIterator.remove();
/* 138:    */       }
/* 139:231 */       AbstractMapBag.access$210(this.parent);
/* 140:232 */       this.canRemove = false;
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean add(Object object)
/* 145:    */   {
/* 146:244 */     return add(object, 1);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean add(Object object, int nCopies)
/* 150:    */   {
/* 151:255 */     this.modCount += 1;
/* 152:256 */     if (nCopies > 0)
/* 153:    */     {
/* 154:257 */       MutableInteger mut = (MutableInteger)this.map.get(object);
/* 155:258 */       this.size += nCopies;
/* 156:259 */       if (mut == null)
/* 157:    */       {
/* 158:260 */         this.map.put(object, new MutableInteger(nCopies));
/* 159:261 */         return true;
/* 160:    */       }
/* 161:263 */       mut.value += nCopies;
/* 162:264 */       return false;
/* 163:    */     }
/* 164:267 */     return false;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean addAll(Collection coll)
/* 168:    */   {
/* 169:278 */     boolean changed = false;
/* 170:279 */     Iterator i = coll.iterator();
/* 171:280 */     while (i.hasNext())
/* 172:    */     {
/* 173:281 */       boolean added = add(i.next());
/* 174:282 */       changed = (changed) || (added);
/* 175:    */     }
/* 176:284 */     return changed;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void clear()
/* 180:    */   {
/* 181:292 */     this.modCount += 1;
/* 182:293 */     this.map.clear();
/* 183:294 */     this.size = 0;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean remove(Object object)
/* 187:    */   {
/* 188:304 */     MutableInteger mut = (MutableInteger)this.map.get(object);
/* 189:305 */     if (mut == null) {
/* 190:306 */       return false;
/* 191:    */     }
/* 192:308 */     this.modCount += 1;
/* 193:309 */     this.map.remove(object);
/* 194:310 */     this.size -= mut.value;
/* 195:311 */     return true;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean remove(Object object, int nCopies)
/* 199:    */   {
/* 200:322 */     MutableInteger mut = (MutableInteger)this.map.get(object);
/* 201:323 */     if (mut == null) {
/* 202:324 */       return false;
/* 203:    */     }
/* 204:326 */     if (nCopies <= 0) {
/* 205:327 */       return false;
/* 206:    */     }
/* 207:329 */     this.modCount += 1;
/* 208:330 */     if (nCopies < mut.value)
/* 209:    */     {
/* 210:331 */       mut.value -= nCopies;
/* 211:332 */       this.size -= nCopies;
/* 212:    */     }
/* 213:    */     else
/* 214:    */     {
/* 215:334 */       this.map.remove(object);
/* 216:335 */       this.size -= mut.value;
/* 217:    */     }
/* 218:337 */     return true;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean removeAll(Collection coll)
/* 222:    */   {
/* 223:347 */     boolean result = false;
/* 224:348 */     if (coll != null)
/* 225:    */     {
/* 226:349 */       Iterator i = coll.iterator();
/* 227:350 */       while (i.hasNext())
/* 228:    */       {
/* 229:351 */         boolean changed = remove(i.next(), 1);
/* 230:352 */         result = (result) || (changed);
/* 231:    */       }
/* 232:    */     }
/* 233:355 */     return result;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean retainAll(Collection coll)
/* 237:    */   {
/* 238:366 */     if ((coll instanceof Bag)) {
/* 239:367 */       return retainAll((Bag)coll);
/* 240:    */     }
/* 241:369 */     return retainAll(new HashBag(coll));
/* 242:    */   }
/* 243:    */   
/* 244:    */   boolean retainAll(Bag other)
/* 245:    */   {
/* 246:381 */     boolean result = false;
/* 247:382 */     Bag excess = new HashBag();
/* 248:383 */     Iterator i = uniqueSet().iterator();
/* 249:384 */     while (i.hasNext())
/* 250:    */     {
/* 251:385 */       Object current = i.next();
/* 252:386 */       int myCount = getCount(current);
/* 253:387 */       int otherCount = other.getCount(current);
/* 254:388 */       if ((1 <= otherCount) && (otherCount <= myCount)) {
/* 255:389 */         excess.add(current, myCount - otherCount);
/* 256:    */       } else {
/* 257:391 */         excess.add(current, myCount);
/* 258:    */       }
/* 259:    */     }
/* 260:394 */     if (!excess.isEmpty()) {
/* 261:395 */       result = removeAll(excess);
/* 262:    */     }
/* 263:397 */     return result;
/* 264:    */   }
/* 265:    */   
/* 266:    */   protected static class MutableInteger
/* 267:    */   {
/* 268:    */     protected int value;
/* 269:    */     
/* 270:    */     MutableInteger(int value)
/* 271:    */     {
/* 272:413 */       this.value = value;
/* 273:    */     }
/* 274:    */     
/* 275:    */     public boolean equals(Object obj)
/* 276:    */     {
/* 277:417 */       if (!(obj instanceof MutableInteger)) {
/* 278:418 */         return false;
/* 279:    */       }
/* 280:420 */       return ((MutableInteger)obj).value == this.value;
/* 281:    */     }
/* 282:    */     
/* 283:    */     public int hashCode()
/* 284:    */     {
/* 285:424 */       return this.value;
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public Object[] toArray()
/* 290:    */   {
/* 291:435 */     Object[] result = new Object[size()];
/* 292:436 */     int i = 0;
/* 293:437 */     Iterator it = this.map.keySet().iterator();
/* 294:438 */     while (it.hasNext())
/* 295:    */     {
/* 296:439 */       Object current = it.next();
/* 297:440 */       for (int index = getCount(current); index > 0; index--) {
/* 298:441 */         result[(i++)] = current;
/* 299:    */       }
/* 300:    */     }
/* 301:444 */     return result;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public Object[] toArray(Object[] array)
/* 305:    */   {
/* 306:454 */     int size = size();
/* 307:455 */     if (array.length < size) {
/* 308:456 */       array = (Object[])Array.newInstance(array.getClass().getComponentType(), size);
/* 309:    */     }
/* 310:459 */     int i = 0;
/* 311:460 */     Iterator it = this.map.keySet().iterator();
/* 312:461 */     while (it.hasNext())
/* 313:    */     {
/* 314:462 */       Object current = it.next();
/* 315:463 */       for (int index = getCount(current); index > 0; index--) {
/* 316:464 */         array[(i++)] = current;
/* 317:    */       }
/* 318:    */     }
/* 319:467 */     if (array.length > size) {
/* 320:468 */       array[size] = null;
/* 321:    */     }
/* 322:470 */     return array;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public Set uniqueSet()
/* 326:    */   {
/* 327:479 */     if (this.uniqueSet == null) {
/* 328:480 */       this.uniqueSet = UnmodifiableSet.decorate(this.map.keySet());
/* 329:    */     }
/* 330:482 */     return this.uniqueSet;
/* 331:    */   }
/* 332:    */   
/* 333:    */   protected void doWriteObject(ObjectOutputStream out)
/* 334:    */     throws IOException
/* 335:    */   {
/* 336:492 */     out.writeInt(this.map.size());
/* 337:493 */     for (Iterator it = this.map.entrySet().iterator(); it.hasNext();)
/* 338:    */     {
/* 339:494 */       Map.Entry entry = (Map.Entry)it.next();
/* 340:495 */       out.writeObject(entry.getKey());
/* 341:496 */       out.writeInt(((MutableInteger)entry.getValue()).value);
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   protected void doReadObject(Map map, ObjectInputStream in)
/* 346:    */     throws IOException, ClassNotFoundException
/* 347:    */   {
/* 348:508 */     this.map = map;
/* 349:509 */     int entrySize = in.readInt();
/* 350:510 */     for (int i = 0; i < entrySize; i++)
/* 351:    */     {
/* 352:511 */       Object obj = in.readObject();
/* 353:512 */       int count = in.readInt();
/* 354:513 */       map.put(obj, new MutableInteger(count));
/* 355:514 */       this.size += count;
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   public boolean equals(Object object)
/* 360:    */   {
/* 361:528 */     if (object == this) {
/* 362:529 */       return true;
/* 363:    */     }
/* 364:531 */     if (!(object instanceof Bag)) {
/* 365:532 */       return false;
/* 366:    */     }
/* 367:534 */     Bag other = (Bag)object;
/* 368:535 */     if (other.size() != size()) {
/* 369:536 */       return false;
/* 370:    */     }
/* 371:538 */     for (Iterator it = this.map.keySet().iterator(); it.hasNext();)
/* 372:    */     {
/* 373:539 */       Object element = it.next();
/* 374:540 */       if (other.getCount(element) != getCount(element)) {
/* 375:541 */         return false;
/* 376:    */       }
/* 377:    */     }
/* 378:544 */     return true;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public int hashCode()
/* 382:    */   {
/* 383:557 */     int total = 0;
/* 384:558 */     for (Iterator it = this.map.entrySet().iterator(); it.hasNext();)
/* 385:    */     {
/* 386:559 */       Map.Entry entry = (Map.Entry)it.next();
/* 387:560 */       Object element = entry.getKey();
/* 388:561 */       MutableInteger count = (MutableInteger)entry.getValue();
/* 389:562 */       total += ((element == null ? 0 : element.hashCode()) ^ count.value);
/* 390:    */     }
/* 391:564 */     return total;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public String toString()
/* 395:    */   {
/* 396:573 */     if (size() == 0) {
/* 397:574 */       return "[]";
/* 398:    */     }
/* 399:576 */     StringBuffer buf = new StringBuffer();
/* 400:577 */     buf.append('[');
/* 401:578 */     Iterator it = uniqueSet().iterator();
/* 402:579 */     while (it.hasNext())
/* 403:    */     {
/* 404:580 */       Object current = it.next();
/* 405:581 */       int count = getCount(current);
/* 406:582 */       buf.append(count);
/* 407:583 */       buf.append(':');
/* 408:584 */       buf.append(current);
/* 409:585 */       if (it.hasNext()) {
/* 410:586 */         buf.append(',');
/* 411:    */       }
/* 412:    */     }
/* 413:589 */     buf.append(']');
/* 414:590 */     return buf.toString();
/* 415:    */   }
/* 416:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.AbstractMapBag
 * JD-Core Version:    0.7.0.1
 */
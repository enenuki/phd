/*   1:    */ package org.hibernate.collection.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Map.Entry;
/*  13:    */ import java.util.Set;
/*  14:    */ import org.hibernate.HibernateException;
/*  15:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  16:    */ import org.hibernate.loader.CollectionAliases;
/*  17:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  18:    */ import org.hibernate.type.CollectionType;
/*  19:    */ import org.hibernate.type.Type;
/*  20:    */ 
/*  21:    */ public class PersistentMap
/*  22:    */   extends AbstractPersistentCollection
/*  23:    */   implements Map
/*  24:    */ {
/*  25:    */   protected Map map;
/*  26:    */   
/*  27:    */   public PersistentMap() {}
/*  28:    */   
/*  29:    */   public PersistentMap(SessionImplementor session)
/*  30:    */   {
/*  31: 71 */     super(session);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public PersistentMap(SessionImplementor session, Map map)
/*  35:    */   {
/*  36: 82 */     super(session);
/*  37: 83 */     this.map = map;
/*  38: 84 */     setInitialized();
/*  39: 85 */     setDirectlyAccessible(true);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Serializable getSnapshot(CollectionPersister persister)
/*  43:    */     throws HibernateException
/*  44:    */   {
/*  45: 90 */     HashMap clonedMap = new HashMap(this.map.size());
/*  46: 91 */     for (Object o : this.map.entrySet())
/*  47:    */     {
/*  48: 92 */       Map.Entry e = (Map.Entry)o;
/*  49: 93 */       Object copy = persister.getElementType().deepCopy(e.getValue(), persister.getFactory());
/*  50: 94 */       clonedMap.put(e.getKey(), copy);
/*  51:    */     }
/*  52: 96 */     return clonedMap;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Collection getOrphans(Serializable snapshot, String entityName)
/*  56:    */     throws HibernateException
/*  57:    */   {
/*  58:100 */     Map sn = (Map)snapshot;
/*  59:101 */     return getOrphans(sn.values(), this.map.values(), entityName, getSession());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean equalsSnapshot(CollectionPersister persister)
/*  63:    */     throws HibernateException
/*  64:    */   {
/*  65:105 */     Type elementType = persister.getElementType();
/*  66:106 */     Map xmap = (Map)getSnapshot();
/*  67:107 */     if (xmap.size() != this.map.size()) {
/*  68:107 */       return false;
/*  69:    */     }
/*  70:108 */     Iterator iter = this.map.entrySet().iterator();
/*  71:109 */     while (iter.hasNext())
/*  72:    */     {
/*  73:110 */       Map.Entry entry = (Map.Entry)iter.next();
/*  74:111 */       if (elementType.isDirty(entry.getValue(), xmap.get(entry.getKey()), getSession())) {
/*  75:111 */         return false;
/*  76:    */       }
/*  77:    */     }
/*  78:113 */     return true;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isSnapshotEmpty(Serializable snapshot)
/*  82:    */   {
/*  83:117 */     return ((Map)snapshot).isEmpty();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean isWrapper(Object collection)
/*  87:    */   {
/*  88:121 */     return this.map == collection;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void beforeInitialize(CollectionPersister persister, int anticipatedSize)
/*  92:    */   {
/*  93:125 */     this.map = ((Map)persister.getCollectionType().instantiate(anticipatedSize));
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int size()
/*  97:    */   {
/*  98:133 */     return readSize() ? getCachedSize() : this.map.size();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isEmpty()
/* 102:    */   {
/* 103:140 */     return readSize() ? false : getCachedSize() == 0 ? true : this.map.isEmpty();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean containsKey(Object key)
/* 107:    */   {
/* 108:147 */     Boolean exists = readIndexExistence(key);
/* 109:148 */     return exists == null ? this.map.containsKey(key) : exists.booleanValue();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean containsValue(Object value)
/* 113:    */   {
/* 114:155 */     Boolean exists = readElementExistence(value);
/* 115:156 */     return exists == null ? this.map.containsValue(value) : exists.booleanValue();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Object get(Object key)
/* 119:    */   {
/* 120:165 */     Object result = readElementByIndex(key);
/* 121:166 */     return result == UNKNOWN ? this.map.get(key) : result;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Object put(Object key, Object value)
/* 125:    */   {
/* 126:173 */     if (isPutQueueEnabled())
/* 127:    */     {
/* 128:174 */       Object old = readElementByIndex(key);
/* 129:175 */       if (old != UNKNOWN)
/* 130:    */       {
/* 131:176 */         queueOperation(new Put(key, value, old));
/* 132:177 */         return old;
/* 133:    */       }
/* 134:    */     }
/* 135:180 */     initialize(true);
/* 136:181 */     Object old = this.map.put(key, value);
/* 137:186 */     if (value != old) {
/* 138:187 */       dirty();
/* 139:    */     }
/* 140:189 */     return old;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Object remove(Object key)
/* 144:    */   {
/* 145:196 */     if (isPutQueueEnabled())
/* 146:    */     {
/* 147:197 */       Object old = readElementByIndex(key);
/* 148:198 */       if (old != UNKNOWN)
/* 149:    */       {
/* 150:199 */         queueOperation(new Remove(key, old));
/* 151:200 */         return old;
/* 152:    */       }
/* 153:    */     }
/* 154:204 */     initialize(true);
/* 155:205 */     if (this.map.containsKey(key)) {
/* 156:206 */       dirty();
/* 157:    */     }
/* 158:208 */     return this.map.remove(key);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void putAll(Map puts)
/* 162:    */   {
/* 163:215 */     if (puts.size() > 0)
/* 164:    */     {
/* 165:216 */       initialize(true);
/* 166:217 */       Iterator itr = puts.entrySet().iterator();
/* 167:218 */       while (itr.hasNext())
/* 168:    */       {
/* 169:219 */         Map.Entry entry = (Map.Entry)itr.next();
/* 170:220 */         put(entry.getKey(), entry.getValue());
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void clear()
/* 176:    */   {
/* 177:229 */     if (isClearQueueEnabled())
/* 178:    */     {
/* 179:230 */       queueOperation(new Clear());
/* 180:    */     }
/* 181:    */     else
/* 182:    */     {
/* 183:233 */       initialize(true);
/* 184:234 */       if (!this.map.isEmpty())
/* 185:    */       {
/* 186:235 */         dirty();
/* 187:236 */         this.map.clear();
/* 188:    */       }
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Set keySet()
/* 193:    */   {
/* 194:245 */     read();
/* 195:246 */     return new AbstractPersistentCollection.SetProxy(this, this.map.keySet());
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Collection values()
/* 199:    */   {
/* 200:253 */     read();
/* 201:254 */     return new AbstractPersistentCollection.SetProxy(this, this.map.values());
/* 202:    */   }
/* 203:    */   
/* 204:    */   public Set entrySet()
/* 205:    */   {
/* 206:261 */     read();
/* 207:262 */     return new EntrySetProxy(this.map.entrySet());
/* 208:    */   }
/* 209:    */   
/* 210:    */   public boolean empty()
/* 211:    */   {
/* 212:266 */     return this.map.isEmpty();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public String toString()
/* 216:    */   {
/* 217:270 */     read();
/* 218:271 */     return this.map.toString();
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Object readFrom(ResultSet rs, CollectionPersister persister, CollectionAliases descriptor, Object owner)
/* 222:    */     throws HibernateException, SQLException
/* 223:    */   {
/* 224:276 */     Object element = persister.readElement(rs, owner, descriptor.getSuffixedElementAliases(), getSession());
/* 225:277 */     Object index = persister.readIndex(rs, descriptor.getSuffixedIndexAliases(), getSession());
/* 226:278 */     if (element != null) {
/* 227:278 */       this.map.put(index, element);
/* 228:    */     }
/* 229:279 */     return element;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public Iterator entries(CollectionPersister persister)
/* 233:    */   {
/* 234:283 */     return this.map.entrySet().iterator();
/* 235:    */   }
/* 236:    */   
/* 237:    */   class EntrySetProxy
/* 238:    */     implements Set
/* 239:    */   {
/* 240:    */     private final Set set;
/* 241:    */     
/* 242:    */     EntrySetProxy(Set set)
/* 243:    */     {
/* 244:290 */       this.set = set;
/* 245:    */     }
/* 246:    */     
/* 247:    */     public boolean add(Object entry)
/* 248:    */     {
/* 249:294 */       return this.set.add(entry);
/* 250:    */     }
/* 251:    */     
/* 252:    */     public boolean addAll(Collection entries)
/* 253:    */     {
/* 254:298 */       return this.set.addAll(entries);
/* 255:    */     }
/* 256:    */     
/* 257:    */     public void clear()
/* 258:    */     {
/* 259:301 */       PersistentMap.this.write();
/* 260:302 */       this.set.clear();
/* 261:    */     }
/* 262:    */     
/* 263:    */     public boolean contains(Object entry)
/* 264:    */     {
/* 265:305 */       return this.set.contains(entry);
/* 266:    */     }
/* 267:    */     
/* 268:    */     public boolean containsAll(Collection entries)
/* 269:    */     {
/* 270:308 */       return this.set.containsAll(entries);
/* 271:    */     }
/* 272:    */     
/* 273:    */     public boolean isEmpty()
/* 274:    */     {
/* 275:311 */       return this.set.isEmpty();
/* 276:    */     }
/* 277:    */     
/* 278:    */     public Iterator iterator()
/* 279:    */     {
/* 280:314 */       return new PersistentMap.EntryIteratorProxy(PersistentMap.this, this.set.iterator());
/* 281:    */     }
/* 282:    */     
/* 283:    */     public boolean remove(Object entry)
/* 284:    */     {
/* 285:317 */       PersistentMap.this.write();
/* 286:318 */       return this.set.remove(entry);
/* 287:    */     }
/* 288:    */     
/* 289:    */     public boolean removeAll(Collection entries)
/* 290:    */     {
/* 291:321 */       PersistentMap.this.write();
/* 292:322 */       return this.set.removeAll(entries);
/* 293:    */     }
/* 294:    */     
/* 295:    */     public boolean retainAll(Collection entries)
/* 296:    */     {
/* 297:325 */       PersistentMap.this.write();
/* 298:326 */       return this.set.retainAll(entries);
/* 299:    */     }
/* 300:    */     
/* 301:    */     public int size()
/* 302:    */     {
/* 303:329 */       return this.set.size();
/* 304:    */     }
/* 305:    */     
/* 306:    */     public Object[] toArray()
/* 307:    */     {
/* 308:334 */       return this.set.toArray();
/* 309:    */     }
/* 310:    */     
/* 311:    */     public Object[] toArray(Object[] array)
/* 312:    */     {
/* 313:337 */       return this.set.toArray(array);
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   final class EntryIteratorProxy
/* 318:    */     implements Iterator
/* 319:    */   {
/* 320:    */     private final Iterator iter;
/* 321:    */     
/* 322:    */     EntryIteratorProxy(Iterator iter)
/* 323:    */     {
/* 324:343 */       this.iter = iter;
/* 325:    */     }
/* 326:    */     
/* 327:    */     public boolean hasNext()
/* 328:    */     {
/* 329:346 */       return this.iter.hasNext();
/* 330:    */     }
/* 331:    */     
/* 332:    */     public Object next()
/* 333:    */     {
/* 334:349 */       return new PersistentMap.MapEntryProxy(PersistentMap.this, (Map.Entry)this.iter.next());
/* 335:    */     }
/* 336:    */     
/* 337:    */     public void remove()
/* 338:    */     {
/* 339:352 */       PersistentMap.this.write();
/* 340:353 */       this.iter.remove();
/* 341:    */     }
/* 342:    */   }
/* 343:    */   
/* 344:    */   final class MapEntryProxy
/* 345:    */     implements Map.Entry
/* 346:    */   {
/* 347:    */     private final Map.Entry me;
/* 348:    */     
/* 349:    */     MapEntryProxy(Map.Entry me)
/* 350:    */     {
/* 351:360 */       this.me = me;
/* 352:    */     }
/* 353:    */     
/* 354:    */     public Object getKey()
/* 355:    */     {
/* 356:362 */       return this.me.getKey();
/* 357:    */     }
/* 358:    */     
/* 359:    */     public Object getValue()
/* 360:    */     {
/* 361:363 */       return this.me.getValue();
/* 362:    */     }
/* 363:    */     
/* 364:    */     public boolean equals(Object o)
/* 365:    */     {
/* 366:364 */       return this.me.equals(o);
/* 367:    */     }
/* 368:    */     
/* 369:    */     public int hashCode()
/* 370:    */     {
/* 371:365 */       return this.me.hashCode();
/* 372:    */     }
/* 373:    */     
/* 374:    */     public Object setValue(Object value)
/* 375:    */     {
/* 376:368 */       PersistentMap.this.write();
/* 377:369 */       return this.me.setValue(value);
/* 378:    */     }
/* 379:    */   }
/* 380:    */   
/* 381:    */   public void initializeFromCache(CollectionPersister persister, Serializable disassembled, Object owner)
/* 382:    */     throws HibernateException
/* 383:    */   {
/* 384:375 */     Serializable[] array = (Serializable[])disassembled;
/* 385:376 */     int size = array.length;
/* 386:377 */     beforeInitialize(persister, size);
/* 387:378 */     for (int i = 0; i < size; i += 2) {
/* 388:379 */       this.map.put(persister.getIndexType().assemble(array[i], getSession(), owner), persister.getElementType().assemble(array[(i + 1)], getSession(), owner));
/* 389:    */     }
/* 390:    */   }
/* 391:    */   
/* 392:    */   public Serializable disassemble(CollectionPersister persister)
/* 393:    */     throws HibernateException
/* 394:    */   {
/* 395:388 */     Serializable[] result = new Serializable[this.map.size() * 2];
/* 396:389 */     Iterator iter = this.map.entrySet().iterator();
/* 397:390 */     int i = 0;
/* 398:391 */     while (iter.hasNext())
/* 399:    */     {
/* 400:392 */       Map.Entry e = (Map.Entry)iter.next();
/* 401:393 */       result[(i++)] = persister.getIndexType().disassemble(e.getKey(), getSession(), null);
/* 402:394 */       result[(i++)] = persister.getElementType().disassemble(e.getValue(), getSession(), null);
/* 403:    */     }
/* 404:396 */     return result;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public Iterator getDeletes(CollectionPersister persister, boolean indexIsFormula)
/* 408:    */     throws HibernateException
/* 409:    */   {
/* 410:402 */     List deletes = new ArrayList();
/* 411:403 */     Iterator iter = ((Map)getSnapshot()).entrySet().iterator();
/* 412:404 */     while (iter.hasNext())
/* 413:    */     {
/* 414:405 */       Map.Entry e = (Map.Entry)iter.next();
/* 415:406 */       Object key = e.getKey();
/* 416:407 */       if ((e.getValue() != null) && (this.map.get(key) == null)) {
/* 417:408 */         deletes.add(indexIsFormula ? e.getValue() : key);
/* 418:    */       }
/* 419:    */     }
/* 420:411 */     return deletes.iterator();
/* 421:    */   }
/* 422:    */   
/* 423:    */   public boolean needsInserting(Object entry, int i, Type elemType)
/* 424:    */     throws HibernateException
/* 425:    */   {
/* 426:416 */     Map sn = (Map)getSnapshot();
/* 427:417 */     Map.Entry e = (Map.Entry)entry;
/* 428:418 */     return (e.getValue() != null) && (sn.get(e.getKey()) == null);
/* 429:    */   }
/* 430:    */   
/* 431:    */   public boolean needsUpdating(Object entry, int i, Type elemType)
/* 432:    */     throws HibernateException
/* 433:    */   {
/* 434:423 */     Map sn = (Map)getSnapshot();
/* 435:424 */     Map.Entry e = (Map.Entry)entry;
/* 436:425 */     Object snValue = sn.get(e.getKey());
/* 437:426 */     return (e.getValue() != null) && (snValue != null) && (elemType.isDirty(snValue, e.getValue(), getSession()));
/* 438:    */   }
/* 439:    */   
/* 440:    */   public Object getIndex(Object entry, int i, CollectionPersister persister)
/* 441:    */   {
/* 442:433 */     return ((Map.Entry)entry).getKey();
/* 443:    */   }
/* 444:    */   
/* 445:    */   public Object getElement(Object entry)
/* 446:    */   {
/* 447:437 */     return ((Map.Entry)entry).getValue();
/* 448:    */   }
/* 449:    */   
/* 450:    */   public Object getSnapshotElement(Object entry, int i)
/* 451:    */   {
/* 452:441 */     Map sn = (Map)getSnapshot();
/* 453:442 */     return sn.get(((Map.Entry)entry).getKey());
/* 454:    */   }
/* 455:    */   
/* 456:    */   public boolean equals(Object other)
/* 457:    */   {
/* 458:446 */     read();
/* 459:447 */     return this.map.equals(other);
/* 460:    */   }
/* 461:    */   
/* 462:    */   public int hashCode()
/* 463:    */   {
/* 464:451 */     read();
/* 465:452 */     return this.map.hashCode();
/* 466:    */   }
/* 467:    */   
/* 468:    */   public boolean entryExists(Object entry, int i)
/* 469:    */   {
/* 470:456 */     return ((Map.Entry)entry).getValue() != null;
/* 471:    */   }
/* 472:    */   
/* 473:    */   final class Clear
/* 474:    */     implements AbstractPersistentCollection.DelayedOperation
/* 475:    */   {
/* 476:    */     Clear() {}
/* 477:    */     
/* 478:    */     public void operate()
/* 479:    */     {
/* 480:461 */       PersistentMap.this.map.clear();
/* 481:    */     }
/* 482:    */     
/* 483:    */     public Object getAddedInstance()
/* 484:    */     {
/* 485:464 */       return null;
/* 486:    */     }
/* 487:    */     
/* 488:    */     public Object getOrphan()
/* 489:    */     {
/* 490:467 */       throw new UnsupportedOperationException("queued clear cannot be used with orphan delete");
/* 491:    */     }
/* 492:    */   }
/* 493:    */   
/* 494:    */   final class Put
/* 495:    */     implements AbstractPersistentCollection.DelayedOperation
/* 496:    */   {
/* 497:    */     private Object index;
/* 498:    */     private Object value;
/* 499:    */     private Object old;
/* 500:    */     
/* 501:    */     public Put(Object index, Object value, Object old)
/* 502:    */     {
/* 503:477 */       this.index = index;
/* 504:478 */       this.value = value;
/* 505:479 */       this.old = old;
/* 506:    */     }
/* 507:    */     
/* 508:    */     public void operate()
/* 509:    */     {
/* 510:482 */       PersistentMap.this.map.put(this.index, this.value);
/* 511:    */     }
/* 512:    */     
/* 513:    */     public Object getAddedInstance()
/* 514:    */     {
/* 515:485 */       return this.value;
/* 516:    */     }
/* 517:    */     
/* 518:    */     public Object getOrphan()
/* 519:    */     {
/* 520:488 */       return this.old;
/* 521:    */     }
/* 522:    */   }
/* 523:    */   
/* 524:    */   final class Remove
/* 525:    */     implements AbstractPersistentCollection.DelayedOperation
/* 526:    */   {
/* 527:    */     private Object index;
/* 528:    */     private Object old;
/* 529:    */     
/* 530:    */     public Remove(Object index, Object old)
/* 531:    */     {
/* 532:497 */       this.index = index;
/* 533:498 */       this.old = old;
/* 534:    */     }
/* 535:    */     
/* 536:    */     public void operate()
/* 537:    */     {
/* 538:501 */       PersistentMap.this.map.remove(this.index);
/* 539:    */     }
/* 540:    */     
/* 541:    */     public Object getAddedInstance()
/* 542:    */     {
/* 543:504 */       return null;
/* 544:    */     }
/* 545:    */     
/* 546:    */     public Object getOrphan()
/* 547:    */     {
/* 548:507 */       return this.old;
/* 549:    */     }
/* 550:    */   }
/* 551:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentMap
 * JD-Core Version:    0.7.0.1
 */
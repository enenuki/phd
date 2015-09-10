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
/*  12:    */ import java.util.Set;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  15:    */ import org.hibernate.loader.CollectionAliases;
/*  16:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  17:    */ import org.hibernate.type.CollectionType;
/*  18:    */ import org.hibernate.type.Type;
/*  19:    */ 
/*  20:    */ public class PersistentSet
/*  21:    */   extends AbstractPersistentCollection
/*  22:    */   implements Set
/*  23:    */ {
/*  24:    */   protected Set set;
/*  25:    */   protected transient List tempList;
/*  26:    */   
/*  27:    */   public PersistentSet() {}
/*  28:    */   
/*  29:    */   public PersistentSet(SessionImplementor session)
/*  30:    */   {
/*  31: 72 */     super(session);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public PersistentSet(SessionImplementor session, Set set)
/*  35:    */   {
/*  36: 83 */     super(session);
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41: 88 */     this.set = set;
/*  42: 89 */     setInitialized();
/*  43: 90 */     setDirectlyAccessible(true);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Serializable getSnapshot(CollectionPersister persister)
/*  47:    */     throws HibernateException
/*  48:    */   {
/*  49: 96 */     HashMap clonedSet = new HashMap(this.set.size());
/*  50: 97 */     for (Object aSet : this.set)
/*  51:    */     {
/*  52: 98 */       Object copied = persister.getElementType().deepCopy(aSet, persister.getFactory());
/*  53:    */       
/*  54:100 */       clonedSet.put(copied, copied);
/*  55:    */     }
/*  56:102 */     return clonedSet;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Collection getOrphans(Serializable snapshot, String entityName)
/*  60:    */     throws HibernateException
/*  61:    */   {
/*  62:106 */     Map sn = (Map)snapshot;
/*  63:107 */     return getOrphans(sn.keySet(), this.set, entityName, getSession());
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean equalsSnapshot(CollectionPersister persister)
/*  67:    */     throws HibernateException
/*  68:    */   {
/*  69:111 */     Type elementType = persister.getElementType();
/*  70:112 */     Map sn = (Map)getSnapshot();
/*  71:113 */     if (sn.size() != this.set.size()) {
/*  72:114 */       return false;
/*  73:    */     }
/*  74:117 */     Iterator iter = this.set.iterator();
/*  75:118 */     for (; iter.hasNext(); return false)
/*  76:    */     {
/*  77:119 */       Object test = iter.next();
/*  78:120 */       Object oldValue = sn.get(test);
/*  79:121 */       if ((oldValue != null) && (!elementType.isDirty(oldValue, test, getSession()))) {}
/*  80:    */     }
/*  81:123 */     return true;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isSnapshotEmpty(Serializable snapshot)
/*  85:    */   {
/*  86:128 */     return ((Map)snapshot).isEmpty();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void beforeInitialize(CollectionPersister persister, int anticipatedSize)
/*  90:    */   {
/*  91:132 */     this.set = ((Set)persister.getCollectionType().instantiate(anticipatedSize));
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void initializeFromCache(CollectionPersister persister, Serializable disassembled, Object owner)
/*  95:    */     throws HibernateException
/*  96:    */   {
/*  97:137 */     Serializable[] array = (Serializable[])disassembled;
/*  98:138 */     int size = array.length;
/*  99:139 */     beforeInitialize(persister, size);
/* 100:140 */     for (int i = 0; i < size; i++)
/* 101:    */     {
/* 102:141 */       Object element = persister.getElementType().assemble(array[i], getSession(), owner);
/* 103:142 */       if (element != null) {
/* 104:143 */         this.set.add(element);
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean empty()
/* 110:    */   {
/* 111:149 */     return this.set.isEmpty();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int size()
/* 115:    */   {
/* 116:156 */     return readSize() ? getCachedSize() : this.set.size();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isEmpty()
/* 120:    */   {
/* 121:163 */     return readSize() ? false : getCachedSize() == 0 ? true : this.set.isEmpty();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean contains(Object object)
/* 125:    */   {
/* 126:170 */     Boolean exists = readElementExistence(object);
/* 127:171 */     return exists == null ? this.set.contains(object) : exists.booleanValue();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Iterator iterator()
/* 131:    */   {
/* 132:180 */     read();
/* 133:181 */     return new AbstractPersistentCollection.IteratorProxy(this, this.set.iterator());
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Object[] toArray()
/* 137:    */   {
/* 138:188 */     read();
/* 139:189 */     return this.set.toArray();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Object[] toArray(Object[] array)
/* 143:    */   {
/* 144:196 */     read();
/* 145:197 */     return this.set.toArray(array);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean add(Object value)
/* 149:    */   {
/* 150:204 */     Boolean exists = isOperationQueueEnabled() ? readElementExistence(value) : null;
/* 151:205 */     if (exists == null)
/* 152:    */     {
/* 153:206 */       initialize(true);
/* 154:207 */       if (this.set.add(value))
/* 155:    */       {
/* 156:208 */         dirty();
/* 157:209 */         return true;
/* 158:    */       }
/* 159:212 */       return false;
/* 160:    */     }
/* 161:215 */     if (exists.booleanValue()) {
/* 162:216 */       return false;
/* 163:    */     }
/* 164:219 */     queueOperation(new SimpleAdd(value));
/* 165:220 */     return true;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean remove(Object value)
/* 169:    */   {
/* 170:228 */     Boolean exists = isPutQueueEnabled() ? readElementExistence(value) : null;
/* 171:229 */     if (exists == null)
/* 172:    */     {
/* 173:230 */       initialize(true);
/* 174:231 */       if (this.set.remove(value))
/* 175:    */       {
/* 176:232 */         dirty();
/* 177:233 */         return true;
/* 178:    */       }
/* 179:236 */       return false;
/* 180:    */     }
/* 181:239 */     if (exists.booleanValue())
/* 182:    */     {
/* 183:240 */       queueOperation(new SimpleRemove(value));
/* 184:241 */       return true;
/* 185:    */     }
/* 186:244 */     return false;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean containsAll(Collection coll)
/* 190:    */   {
/* 191:252 */     read();
/* 192:253 */     return this.set.containsAll(coll);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean addAll(Collection coll)
/* 196:    */   {
/* 197:260 */     if (coll.size() > 0)
/* 198:    */     {
/* 199:261 */       initialize(true);
/* 200:262 */       if (this.set.addAll(coll))
/* 201:    */       {
/* 202:263 */         dirty();
/* 203:264 */         return true;
/* 204:    */       }
/* 205:267 */       return false;
/* 206:    */     }
/* 207:271 */     return false;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public boolean retainAll(Collection coll)
/* 211:    */   {
/* 212:279 */     initialize(true);
/* 213:280 */     if (this.set.retainAll(coll))
/* 214:    */     {
/* 215:281 */       dirty();
/* 216:282 */       return true;
/* 217:    */     }
/* 218:285 */     return false;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean removeAll(Collection coll)
/* 222:    */   {
/* 223:293 */     if (coll.size() > 0)
/* 224:    */     {
/* 225:294 */       initialize(true);
/* 226:295 */       if (this.set.removeAll(coll))
/* 227:    */       {
/* 228:296 */         dirty();
/* 229:297 */         return true;
/* 230:    */       }
/* 231:300 */       return false;
/* 232:    */     }
/* 233:304 */     return false;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void clear()
/* 237:    */   {
/* 238:312 */     if (isClearQueueEnabled())
/* 239:    */     {
/* 240:313 */       queueOperation(new Clear());
/* 241:    */     }
/* 242:    */     else
/* 243:    */     {
/* 244:316 */       initialize(true);
/* 245:317 */       if (!this.set.isEmpty())
/* 246:    */       {
/* 247:318 */         this.set.clear();
/* 248:319 */         dirty();
/* 249:    */       }
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   public String toString()
/* 254:    */   {
/* 255:326 */     read();
/* 256:327 */     return this.set.toString();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public Object readFrom(ResultSet rs, CollectionPersister persister, CollectionAliases descriptor, Object owner)
/* 260:    */     throws HibernateException, SQLException
/* 261:    */   {
/* 262:335 */     Object element = persister.readElement(rs, owner, descriptor.getSuffixedElementAliases(), getSession());
/* 263:336 */     if (element != null) {
/* 264:336 */       this.tempList.add(element);
/* 265:    */     }
/* 266:337 */     return element;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void beginRead()
/* 270:    */   {
/* 271:341 */     super.beginRead();
/* 272:342 */     this.tempList = new ArrayList();
/* 273:    */   }
/* 274:    */   
/* 275:    */   public boolean endRead()
/* 276:    */   {
/* 277:346 */     this.set.addAll(this.tempList);
/* 278:347 */     this.tempList = null;
/* 279:348 */     setInitialized();
/* 280:349 */     return true;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public Iterator entries(CollectionPersister persister)
/* 284:    */   {
/* 285:353 */     return this.set.iterator();
/* 286:    */   }
/* 287:    */   
/* 288:    */   public Serializable disassemble(CollectionPersister persister)
/* 289:    */     throws HibernateException
/* 290:    */   {
/* 291:359 */     Serializable[] result = new Serializable[this.set.size()];
/* 292:360 */     Iterator iter = this.set.iterator();
/* 293:361 */     int i = 0;
/* 294:362 */     while (iter.hasNext()) {
/* 295:363 */       result[(i++)] = persister.getElementType().disassemble(iter.next(), getSession(), null);
/* 296:    */     }
/* 297:365 */     return result;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public Iterator getDeletes(CollectionPersister persister, boolean indexIsFormula)
/* 301:    */     throws HibernateException
/* 302:    */   {
/* 303:370 */     Type elementType = persister.getElementType();
/* 304:371 */     Map sn = (Map)getSnapshot();
/* 305:372 */     ArrayList deletes = new ArrayList(sn.size());
/* 306:373 */     Iterator iter = sn.keySet().iterator();
/* 307:374 */     while (iter.hasNext())
/* 308:    */     {
/* 309:375 */       Object test = iter.next();
/* 310:376 */       if (!this.set.contains(test)) {
/* 311:378 */         deletes.add(test);
/* 312:    */       }
/* 313:    */     }
/* 314:381 */     iter = this.set.iterator();
/* 315:382 */     while (iter.hasNext())
/* 316:    */     {
/* 317:383 */       Object test = iter.next();
/* 318:384 */       Object oldValue = sn.get(test);
/* 319:385 */       if ((oldValue != null) && (elementType.isDirty(test, oldValue, getSession()))) {
/* 320:387 */         deletes.add(oldValue);
/* 321:    */       }
/* 322:    */     }
/* 323:390 */     return deletes.iterator();
/* 324:    */   }
/* 325:    */   
/* 326:    */   public boolean needsInserting(Object entry, int i, Type elemType)
/* 327:    */     throws HibernateException
/* 328:    */   {
/* 329:394 */     Map sn = (Map)getSnapshot();
/* 330:395 */     Object oldValue = sn.get(entry);
/* 331:    */     
/* 332:    */ 
/* 333:    */ 
/* 334:399 */     return (oldValue == null) || (elemType.isDirty(oldValue, entry, getSession()));
/* 335:    */   }
/* 336:    */   
/* 337:    */   public boolean needsUpdating(Object entry, int i, Type elemType)
/* 338:    */   {
/* 339:403 */     return false;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public boolean isRowUpdatePossible()
/* 343:    */   {
/* 344:407 */     return false;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public Object getIndex(Object entry, int i, CollectionPersister persister)
/* 348:    */   {
/* 349:411 */     throw new UnsupportedOperationException("Sets don't have indexes");
/* 350:    */   }
/* 351:    */   
/* 352:    */   public Object getElement(Object entry)
/* 353:    */   {
/* 354:415 */     return entry;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public Object getSnapshotElement(Object entry, int i)
/* 358:    */   {
/* 359:419 */     throw new UnsupportedOperationException("Sets don't support updating by element");
/* 360:    */   }
/* 361:    */   
/* 362:    */   public boolean equals(Object other)
/* 363:    */   {
/* 364:423 */     read();
/* 365:424 */     return this.set.equals(other);
/* 366:    */   }
/* 367:    */   
/* 368:    */   public int hashCode()
/* 369:    */   {
/* 370:428 */     read();
/* 371:429 */     return this.set.hashCode();
/* 372:    */   }
/* 373:    */   
/* 374:    */   public boolean entryExists(Object key, int i)
/* 375:    */   {
/* 376:433 */     return true;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public boolean isWrapper(Object collection)
/* 380:    */   {
/* 381:437 */     return this.set == collection;
/* 382:    */   }
/* 383:    */   
/* 384:    */   final class Clear
/* 385:    */     implements AbstractPersistentCollection.DelayedOperation
/* 386:    */   {
/* 387:    */     Clear() {}
/* 388:    */     
/* 389:    */     public void operate()
/* 390:    */     {
/* 391:442 */       PersistentSet.this.set.clear();
/* 392:    */     }
/* 393:    */     
/* 394:    */     public Object getAddedInstance()
/* 395:    */     {
/* 396:445 */       return null;
/* 397:    */     }
/* 398:    */     
/* 399:    */     public Object getOrphan()
/* 400:    */     {
/* 401:448 */       throw new UnsupportedOperationException("queued clear cannot be used with orphan delete");
/* 402:    */     }
/* 403:    */   }
/* 404:    */   
/* 405:    */   final class SimpleAdd
/* 406:    */     implements AbstractPersistentCollection.DelayedOperation
/* 407:    */   {
/* 408:    */     private Object value;
/* 409:    */     
/* 410:    */     public SimpleAdd(Object value)
/* 411:    */     {
/* 412:456 */       this.value = value;
/* 413:    */     }
/* 414:    */     
/* 415:    */     public void operate()
/* 416:    */     {
/* 417:459 */       PersistentSet.this.set.add(this.value);
/* 418:    */     }
/* 419:    */     
/* 420:    */     public Object getAddedInstance()
/* 421:    */     {
/* 422:462 */       return this.value;
/* 423:    */     }
/* 424:    */     
/* 425:    */     public Object getOrphan()
/* 426:    */     {
/* 427:465 */       return null;
/* 428:    */     }
/* 429:    */   }
/* 430:    */   
/* 431:    */   final class SimpleRemove
/* 432:    */     implements AbstractPersistentCollection.DelayedOperation
/* 433:    */   {
/* 434:    */     private Object value;
/* 435:    */     
/* 436:    */     public SimpleRemove(Object value)
/* 437:    */     {
/* 438:473 */       this.value = value;
/* 439:    */     }
/* 440:    */     
/* 441:    */     public void operate()
/* 442:    */     {
/* 443:476 */       PersistentSet.this.set.remove(this.value);
/* 444:    */     }
/* 445:    */     
/* 446:    */     public Object getAddedInstance()
/* 447:    */     {
/* 448:479 */       return null;
/* 449:    */     }
/* 450:    */     
/* 451:    */     public Object getOrphan()
/* 452:    */     {
/* 453:482 */       return this.value;
/* 454:    */     }
/* 455:    */   }
/* 456:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentSet
 * JD-Core Version:    0.7.0.1
 */
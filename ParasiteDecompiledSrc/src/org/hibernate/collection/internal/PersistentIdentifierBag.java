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
/*  11:    */ import java.util.ListIterator;
/*  12:    */ import java.util.Map;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  15:    */ import org.hibernate.id.IdentifierGenerator;
/*  16:    */ import org.hibernate.loader.CollectionAliases;
/*  17:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  18:    */ import org.hibernate.type.Type;
/*  19:    */ 
/*  20:    */ public class PersistentIdentifierBag
/*  21:    */   extends AbstractPersistentCollection
/*  22:    */   implements List
/*  23:    */ {
/*  24:    */   protected List values;
/*  25:    */   protected Map identifiers;
/*  26:    */   
/*  27:    */   public PersistentIdentifierBag(SessionImplementor session)
/*  28:    */   {
/*  29: 61 */     super(session);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public PersistentIdentifierBag() {}
/*  33:    */   
/*  34:    */   public PersistentIdentifierBag(SessionImplementor session, Collection coll)
/*  35:    */   {
/*  36: 67 */     super(session);
/*  37: 68 */     if ((coll instanceof List))
/*  38:    */     {
/*  39: 69 */       this.values = ((List)coll);
/*  40:    */     }
/*  41:    */     else
/*  42:    */     {
/*  43: 72 */       this.values = new ArrayList();
/*  44: 73 */       Iterator iter = coll.iterator();
/*  45: 74 */       while (iter.hasNext()) {
/*  46: 75 */         this.values.add(iter.next());
/*  47:    */       }
/*  48:    */     }
/*  49: 78 */     setInitialized();
/*  50: 79 */     setDirectlyAccessible(true);
/*  51: 80 */     this.identifiers = new HashMap();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void initializeFromCache(CollectionPersister persister, Serializable disassembled, Object owner)
/*  55:    */     throws HibernateException
/*  56:    */   {
/*  57: 85 */     Serializable[] array = (Serializable[])disassembled;
/*  58: 86 */     int size = array.length;
/*  59: 87 */     beforeInitialize(persister, size);
/*  60: 88 */     for (int i = 0; i < size; i += 2)
/*  61:    */     {
/*  62: 89 */       this.identifiers.put(Integer.valueOf(i / 2), persister.getIdentifierType().assemble(array[i], getSession(), owner));
/*  63:    */       
/*  64:    */ 
/*  65:    */ 
/*  66: 93 */       this.values.add(persister.getElementType().assemble(array[(i + 1)], getSession(), owner));
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Object getIdentifier(Object entry, int i)
/*  71:    */   {
/*  72: 98 */     return this.identifiers.get(Integer.valueOf(i));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isWrapper(Object collection)
/*  76:    */   {
/*  77:102 */     return this.values == collection;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean add(Object o)
/*  81:    */   {
/*  82:106 */     write();
/*  83:107 */     this.values.add(o);
/*  84:108 */     return true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void clear()
/*  88:    */   {
/*  89:112 */     initialize(true);
/*  90:113 */     if ((!this.values.isEmpty()) || (!this.identifiers.isEmpty()))
/*  91:    */     {
/*  92:114 */       this.values.clear();
/*  93:115 */       this.identifiers.clear();
/*  94:116 */       dirty();
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean contains(Object o)
/*  99:    */   {
/* 100:121 */     read();
/* 101:122 */     return this.values.contains(o);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean containsAll(Collection c)
/* 105:    */   {
/* 106:126 */     read();
/* 107:127 */     return this.values.containsAll(c);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isEmpty()
/* 111:    */   {
/* 112:131 */     return readSize() ? false : getCachedSize() == 0 ? true : this.values.isEmpty();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Iterator iterator()
/* 116:    */   {
/* 117:135 */     read();
/* 118:136 */     return new AbstractPersistentCollection.IteratorProxy(this, this.values.iterator());
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean remove(Object o)
/* 122:    */   {
/* 123:140 */     initialize(true);
/* 124:141 */     int index = this.values.indexOf(o);
/* 125:142 */     if (index >= 0)
/* 126:    */     {
/* 127:143 */       beforeRemove(index);
/* 128:144 */       this.values.remove(index);
/* 129:145 */       dirty();
/* 130:146 */       return true;
/* 131:    */     }
/* 132:149 */     return false;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean removeAll(Collection c)
/* 136:    */   {
/* 137:154 */     if (c.size() > 0)
/* 138:    */     {
/* 139:155 */       boolean result = false;
/* 140:156 */       Iterator iter = c.iterator();
/* 141:157 */       while (iter.hasNext()) {
/* 142:158 */         if (remove(iter.next())) {
/* 143:158 */           result = true;
/* 144:    */         }
/* 145:    */       }
/* 146:160 */       return result;
/* 147:    */     }
/* 148:163 */     return false;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean retainAll(Collection c)
/* 152:    */   {
/* 153:168 */     initialize(true);
/* 154:169 */     if (this.values.retainAll(c))
/* 155:    */     {
/* 156:170 */       dirty();
/* 157:171 */       return true;
/* 158:    */     }
/* 159:174 */     return false;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int size()
/* 163:    */   {
/* 164:179 */     return readSize() ? getCachedSize() : this.values.size();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Object[] toArray()
/* 168:    */   {
/* 169:183 */     read();
/* 170:184 */     return this.values.toArray();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Object[] toArray(Object[] a)
/* 174:    */   {
/* 175:188 */     read();
/* 176:189 */     return this.values.toArray(a);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void beforeInitialize(CollectionPersister persister, int anticipatedSize)
/* 180:    */   {
/* 181:193 */     this.identifiers = (anticipatedSize <= 0 ? new HashMap() : new HashMap(anticipatedSize + 1 + (int)(anticipatedSize * 0.75F), 0.75F));
/* 182:194 */     this.values = (anticipatedSize <= 0 ? new ArrayList() : new ArrayList(anticipatedSize));
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Serializable disassemble(CollectionPersister persister)
/* 186:    */     throws HibernateException
/* 187:    */   {
/* 188:199 */     Serializable[] result = new Serializable[this.values.size() * 2];
/* 189:200 */     int i = 0;
/* 190:201 */     for (int j = 0; j < this.values.size(); j++)
/* 191:    */     {
/* 192:202 */       Object value = this.values.get(j);
/* 193:203 */       result[(i++)] = persister.getIdentifierType().disassemble(this.identifiers.get(Integer.valueOf(j)), getSession(), null);
/* 194:204 */       result[(i++)] = persister.getElementType().disassemble(value, getSession(), null);
/* 195:    */     }
/* 196:206 */     return result;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean empty()
/* 200:    */   {
/* 201:210 */     return this.values.isEmpty();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public Iterator entries(CollectionPersister persister)
/* 205:    */   {
/* 206:214 */     return this.values.iterator();
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean entryExists(Object entry, int i)
/* 210:    */   {
/* 211:218 */     return entry != null;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean equalsSnapshot(CollectionPersister persister)
/* 215:    */     throws HibernateException
/* 216:    */   {
/* 217:222 */     Type elementType = persister.getElementType();
/* 218:223 */     Map snap = (Map)getSnapshot();
/* 219:224 */     if (snap.size() != this.values.size()) {
/* 220:224 */       return false;
/* 221:    */     }
/* 222:225 */     for (int i = 0; i < this.values.size(); i++)
/* 223:    */     {
/* 224:226 */       Object value = this.values.get(i);
/* 225:227 */       Object id = this.identifiers.get(Integer.valueOf(i));
/* 226:228 */       if (id == null) {
/* 227:228 */         return false;
/* 228:    */       }
/* 229:229 */       Object old = snap.get(id);
/* 230:230 */       if (elementType.isDirty(old, value, getSession())) {
/* 231:230 */         return false;
/* 232:    */       }
/* 233:    */     }
/* 234:232 */     return true;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public boolean isSnapshotEmpty(Serializable snapshot)
/* 238:    */   {
/* 239:236 */     return ((Map)snapshot).isEmpty();
/* 240:    */   }
/* 241:    */   
/* 242:    */   public Iterator getDeletes(CollectionPersister persister, boolean indexIsFormula)
/* 243:    */     throws HibernateException
/* 244:    */   {
/* 245:240 */     Map snap = (Map)getSnapshot();
/* 246:241 */     List deletes = new ArrayList(snap.keySet());
/* 247:242 */     for (int i = 0; i < this.values.size(); i++) {
/* 248:243 */       if (this.values.get(i) != null) {
/* 249:243 */         deletes.remove(this.identifiers.get(Integer.valueOf(i)));
/* 250:    */       }
/* 251:    */     }
/* 252:245 */     return deletes.iterator();
/* 253:    */   }
/* 254:    */   
/* 255:    */   public Object getIndex(Object entry, int i, CollectionPersister persister)
/* 256:    */   {
/* 257:249 */     throw new UnsupportedOperationException("Bags don't have indexes");
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Object getElement(Object entry)
/* 261:    */   {
/* 262:253 */     return entry;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Object getSnapshotElement(Object entry, int i)
/* 266:    */   {
/* 267:257 */     Map snap = (Map)getSnapshot();
/* 268:258 */     Object id = this.identifiers.get(Integer.valueOf(i));
/* 269:259 */     return snap.get(id);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public boolean needsInserting(Object entry, int i, Type elemType)
/* 273:    */     throws HibernateException
/* 274:    */   {
/* 275:265 */     Map snap = (Map)getSnapshot();
/* 276:266 */     Object id = this.identifiers.get(Integer.valueOf(i));
/* 277:267 */     return (entry != null) && ((id == null) || (snap.get(id) == null));
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean needsUpdating(Object entry, int i, Type elemType)
/* 281:    */     throws HibernateException
/* 282:    */   {
/* 283:272 */     if (entry == null) {
/* 284:272 */       return false;
/* 285:    */     }
/* 286:273 */     Map snap = (Map)getSnapshot();
/* 287:274 */     Object id = this.identifiers.get(Integer.valueOf(i));
/* 288:275 */     if (id == null) {
/* 289:275 */       return false;
/* 290:    */     }
/* 291:276 */     Object old = snap.get(id);
/* 292:277 */     return (old != null) && (elemType.isDirty(old, entry, getSession()));
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Object readFrom(ResultSet rs, CollectionPersister persister, CollectionAliases descriptor, Object owner)
/* 296:    */     throws HibernateException, SQLException
/* 297:    */   {
/* 298:288 */     Object element = persister.readElement(rs, owner, descriptor.getSuffixedElementAliases(), getSession());
/* 299:289 */     Object old = this.identifiers.put(Integer.valueOf(this.values.size()), persister.readIdentifier(rs, descriptor.getSuffixedIdentifierAlias(), getSession()));
/* 300:293 */     if (old == null) {
/* 301:293 */       this.values.add(element);
/* 302:    */     }
/* 303:294 */     return element;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Serializable getSnapshot(CollectionPersister persister)
/* 307:    */     throws HibernateException
/* 308:    */   {
/* 309:298 */     HashMap map = new HashMap(this.values.size());
/* 310:299 */     Iterator iter = this.values.iterator();
/* 311:300 */     int i = 0;
/* 312:301 */     while (iter.hasNext())
/* 313:    */     {
/* 314:302 */       Object value = iter.next();
/* 315:303 */       map.put(this.identifiers.get(Integer.valueOf(i++)), persister.getElementType().deepCopy(value, persister.getFactory()));
/* 316:    */     }
/* 317:308 */     return map;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public Collection getOrphans(Serializable snapshot, String entityName)
/* 321:    */     throws HibernateException
/* 322:    */   {
/* 323:312 */     Map sn = (Map)snapshot;
/* 324:313 */     return getOrphans(sn.values(), this.values, entityName, getSession());
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void preInsert(CollectionPersister persister)
/* 328:    */     throws HibernateException
/* 329:    */   {
/* 330:317 */     Iterator iter = this.values.iterator();
/* 331:318 */     int i = 0;
/* 332:319 */     while (iter.hasNext())
/* 333:    */     {
/* 334:320 */       Object entry = iter.next();
/* 335:321 */       Integer loc = Integer.valueOf(i++);
/* 336:322 */       if (!this.identifiers.containsKey(loc))
/* 337:    */       {
/* 338:323 */         Serializable id = persister.getIdentifierGenerator().generate(getSession(), entry);
/* 339:324 */         this.identifiers.put(loc, id);
/* 340:    */       }
/* 341:    */     }
/* 342:    */   }
/* 343:    */   
/* 344:    */   public void add(int index, Object element)
/* 345:    */   {
/* 346:330 */     write();
/* 347:331 */     beforeAdd(index);
/* 348:332 */     this.values.add(index, element);
/* 349:    */   }
/* 350:    */   
/* 351:    */   public boolean addAll(int index, Collection c)
/* 352:    */   {
/* 353:336 */     if (c.size() > 0)
/* 354:    */     {
/* 355:337 */       Iterator iter = c.iterator();
/* 356:338 */       while (iter.hasNext()) {
/* 357:339 */         add(index++, iter.next());
/* 358:    */       }
/* 359:341 */       return true;
/* 360:    */     }
/* 361:344 */     return false;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public Object get(int index)
/* 365:    */   {
/* 366:349 */     read();
/* 367:350 */     return this.values.get(index);
/* 368:    */   }
/* 369:    */   
/* 370:    */   public int indexOf(Object o)
/* 371:    */   {
/* 372:354 */     read();
/* 373:355 */     return this.values.indexOf(o);
/* 374:    */   }
/* 375:    */   
/* 376:    */   public int lastIndexOf(Object o)
/* 377:    */   {
/* 378:359 */     read();
/* 379:360 */     return this.values.lastIndexOf(o);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public ListIterator listIterator()
/* 383:    */   {
/* 384:364 */     read();
/* 385:365 */     return new AbstractPersistentCollection.ListIteratorProxy(this, this.values.listIterator());
/* 386:    */   }
/* 387:    */   
/* 388:    */   public ListIterator listIterator(int index)
/* 389:    */   {
/* 390:369 */     read();
/* 391:370 */     return new AbstractPersistentCollection.ListIteratorProxy(this, this.values.listIterator(index));
/* 392:    */   }
/* 393:    */   
/* 394:    */   private void beforeRemove(int index)
/* 395:    */   {
/* 396:374 */     Object removedId = this.identifiers.get(Integer.valueOf(index));
/* 397:375 */     int last = this.values.size() - 1;
/* 398:376 */     for (int i = index; i < last; i++)
/* 399:    */     {
/* 400:377 */       Object id = this.identifiers.get(Integer.valueOf(i + 1));
/* 401:378 */       if (id == null) {
/* 402:379 */         this.identifiers.remove(Integer.valueOf(i));
/* 403:    */       } else {
/* 404:382 */         this.identifiers.put(Integer.valueOf(i), id);
/* 405:    */       }
/* 406:    */     }
/* 407:385 */     this.identifiers.put(Integer.valueOf(last), removedId);
/* 408:    */   }
/* 409:    */   
/* 410:    */   private void beforeAdd(int index)
/* 411:    */   {
/* 412:389 */     for (int i = index; i < this.values.size(); i++) {
/* 413:390 */       this.identifiers.put(Integer.valueOf(i + 1), this.identifiers.get(Integer.valueOf(i)));
/* 414:    */     }
/* 415:392 */     this.identifiers.remove(Integer.valueOf(index));
/* 416:    */   }
/* 417:    */   
/* 418:    */   public Object remove(int index)
/* 419:    */   {
/* 420:396 */     write();
/* 421:397 */     beforeRemove(index);
/* 422:398 */     return this.values.remove(index);
/* 423:    */   }
/* 424:    */   
/* 425:    */   public Object set(int index, Object element)
/* 426:    */   {
/* 427:402 */     write();
/* 428:403 */     return this.values.set(index, element);
/* 429:    */   }
/* 430:    */   
/* 431:    */   public List subList(int fromIndex, int toIndex)
/* 432:    */   {
/* 433:407 */     read();
/* 434:408 */     return new AbstractPersistentCollection.ListProxy(this, this.values.subList(fromIndex, toIndex));
/* 435:    */   }
/* 436:    */   
/* 437:    */   public boolean addAll(Collection c)
/* 438:    */   {
/* 439:412 */     if (c.size() > 0)
/* 440:    */     {
/* 441:413 */       write();
/* 442:414 */       return this.values.addAll(c);
/* 443:    */     }
/* 444:417 */     return false;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public void afterRowInsert(CollectionPersister persister, Object entry, int i)
/* 448:    */     throws HibernateException
/* 449:    */   {}
/* 450:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentIdentifierBag
 * JD-Core Version:    0.7.0.1
 */
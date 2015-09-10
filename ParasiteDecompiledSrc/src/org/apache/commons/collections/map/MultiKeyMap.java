/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.commons.collections.IterableMap;
/*   9:    */ import org.apache.commons.collections.MapIterator;
/*  10:    */ import org.apache.commons.collections.keyvalue.MultiKey;
/*  11:    */ 
/*  12:    */ public class MultiKeyMap
/*  13:    */   implements IterableMap, Serializable
/*  14:    */ {
/*  15:    */   private static final long serialVersionUID = -1788199231038721040L;
/*  16:    */   protected final AbstractHashedMap map;
/*  17:    */   
/*  18:    */   public static MultiKeyMap decorate(AbstractHashedMap map)
/*  19:    */   {
/*  20: 98 */     if (map == null) {
/*  21: 99 */       throw new IllegalArgumentException("Map must not be null");
/*  22:    */     }
/*  23:101 */     if (map.size() > 0) {
/*  24:102 */       throw new IllegalArgumentException("Map must be empty");
/*  25:    */     }
/*  26:104 */     return new MultiKeyMap(map);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public MultiKeyMap()
/*  30:    */   {
/*  31:113 */     this.map = new HashedMap();
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected MultiKeyMap(AbstractHashedMap map)
/*  35:    */   {
/*  36:126 */     this.map = map;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Object get(Object key1, Object key2)
/*  40:    */   {
/*  41:138 */     int hashCode = hash(key1, key2);
/*  42:139 */     AbstractHashedMap.HashEntry entry = this.map.data[this.map.hashIndex(hashCode, this.map.data.length)];
/*  43:140 */     while (entry != null)
/*  44:    */     {
/*  45:141 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2))) {
/*  46:142 */         return entry.getValue();
/*  47:    */       }
/*  48:144 */       entry = entry.next;
/*  49:    */     }
/*  50:146 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean containsKey(Object key1, Object key2)
/*  54:    */   {
/*  55:157 */     int hashCode = hash(key1, key2);
/*  56:158 */     AbstractHashedMap.HashEntry entry = this.map.data[this.map.hashIndex(hashCode, this.map.data.length)];
/*  57:159 */     while (entry != null)
/*  58:    */     {
/*  59:160 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2))) {
/*  60:161 */         return true;
/*  61:    */       }
/*  62:163 */       entry = entry.next;
/*  63:    */     }
/*  64:165 */     return false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Object put(Object key1, Object key2, Object value)
/*  68:    */   {
/*  69:177 */     int hashCode = hash(key1, key2);
/*  70:178 */     int index = this.map.hashIndex(hashCode, this.map.data.length);
/*  71:179 */     AbstractHashedMap.HashEntry entry = this.map.data[index];
/*  72:180 */     while (entry != null)
/*  73:    */     {
/*  74:181 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2)))
/*  75:    */       {
/*  76:182 */         Object oldValue = entry.getValue();
/*  77:183 */         this.map.updateEntry(entry, value);
/*  78:184 */         return oldValue;
/*  79:    */       }
/*  80:186 */       entry = entry.next;
/*  81:    */     }
/*  82:189 */     this.map.addMapping(index, hashCode, new MultiKey(key1, key2), value);
/*  83:190 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Object remove(Object key1, Object key2)
/*  87:    */   {
/*  88:201 */     int hashCode = hash(key1, key2);
/*  89:202 */     int index = this.map.hashIndex(hashCode, this.map.data.length);
/*  90:203 */     AbstractHashedMap.HashEntry entry = this.map.data[index];
/*  91:204 */     AbstractHashedMap.HashEntry previous = null;
/*  92:205 */     while (entry != null)
/*  93:    */     {
/*  94:206 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2)))
/*  95:    */       {
/*  96:207 */         Object oldValue = entry.getValue();
/*  97:208 */         this.map.removeMapping(entry, index, previous);
/*  98:209 */         return oldValue;
/*  99:    */       }
/* 100:211 */       previous = entry;
/* 101:212 */       entry = entry.next;
/* 102:    */     }
/* 103:214 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected int hash(Object key1, Object key2)
/* 107:    */   {
/* 108:225 */     int h = 0;
/* 109:226 */     if (key1 != null) {
/* 110:227 */       h ^= key1.hashCode();
/* 111:    */     }
/* 112:229 */     if (key2 != null) {
/* 113:230 */       h ^= key2.hashCode();
/* 114:    */     }
/* 115:232 */     h += (h << 9 ^ 0xFFFFFFFF);
/* 116:233 */     h ^= h >>> 14;
/* 117:234 */     h += (h << 4);
/* 118:235 */     h ^= h >>> 10;
/* 119:236 */     return h;
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected boolean isEqualKey(AbstractHashedMap.HashEntry entry, Object key1, Object key2)
/* 123:    */   {
/* 124:248 */     MultiKey multi = (MultiKey)entry.getKey();
/* 125:249 */     return (multi.size() == 2) && (key1 == null ? multi.getKey(0) == null : key1.equals(multi.getKey(0))) && (key2 == null ? multi.getKey(1) == null : key2.equals(multi.getKey(1)));
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Object get(Object key1, Object key2, Object key3)
/* 129:    */   {
/* 130:265 */     int hashCode = hash(key1, key2, key3);
/* 131:266 */     AbstractHashedMap.HashEntry entry = this.map.data[this.map.hashIndex(hashCode, this.map.data.length)];
/* 132:267 */     while (entry != null)
/* 133:    */     {
/* 134:268 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3))) {
/* 135:269 */         return entry.getValue();
/* 136:    */       }
/* 137:271 */       entry = entry.next;
/* 138:    */     }
/* 139:273 */     return null;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean containsKey(Object key1, Object key2, Object key3)
/* 143:    */   {
/* 144:285 */     int hashCode = hash(key1, key2, key3);
/* 145:286 */     AbstractHashedMap.HashEntry entry = this.map.data[this.map.hashIndex(hashCode, this.map.data.length)];
/* 146:287 */     while (entry != null)
/* 147:    */     {
/* 148:288 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3))) {
/* 149:289 */         return true;
/* 150:    */       }
/* 151:291 */       entry = entry.next;
/* 152:    */     }
/* 153:293 */     return false;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Object put(Object key1, Object key2, Object key3, Object value)
/* 157:    */   {
/* 158:306 */     int hashCode = hash(key1, key2, key3);
/* 159:307 */     int index = this.map.hashIndex(hashCode, this.map.data.length);
/* 160:308 */     AbstractHashedMap.HashEntry entry = this.map.data[index];
/* 161:309 */     while (entry != null)
/* 162:    */     {
/* 163:310 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3)))
/* 164:    */       {
/* 165:311 */         Object oldValue = entry.getValue();
/* 166:312 */         this.map.updateEntry(entry, value);
/* 167:313 */         return oldValue;
/* 168:    */       }
/* 169:315 */       entry = entry.next;
/* 170:    */     }
/* 171:318 */     this.map.addMapping(index, hashCode, new MultiKey(key1, key2, key3), value);
/* 172:319 */     return null;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Object remove(Object key1, Object key2, Object key3)
/* 176:    */   {
/* 177:331 */     int hashCode = hash(key1, key2, key3);
/* 178:332 */     int index = this.map.hashIndex(hashCode, this.map.data.length);
/* 179:333 */     AbstractHashedMap.HashEntry entry = this.map.data[index];
/* 180:334 */     AbstractHashedMap.HashEntry previous = null;
/* 181:335 */     while (entry != null)
/* 182:    */     {
/* 183:336 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3)))
/* 184:    */       {
/* 185:337 */         Object oldValue = entry.getValue();
/* 186:338 */         this.map.removeMapping(entry, index, previous);
/* 187:339 */         return oldValue;
/* 188:    */       }
/* 189:341 */       previous = entry;
/* 190:342 */       entry = entry.next;
/* 191:    */     }
/* 192:344 */     return null;
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected int hash(Object key1, Object key2, Object key3)
/* 196:    */   {
/* 197:356 */     int h = 0;
/* 198:357 */     if (key1 != null) {
/* 199:358 */       h ^= key1.hashCode();
/* 200:    */     }
/* 201:360 */     if (key2 != null) {
/* 202:361 */       h ^= key2.hashCode();
/* 203:    */     }
/* 204:363 */     if (key3 != null) {
/* 205:364 */       h ^= key3.hashCode();
/* 206:    */     }
/* 207:366 */     h += (h << 9 ^ 0xFFFFFFFF);
/* 208:367 */     h ^= h >>> 14;
/* 209:368 */     h += (h << 4);
/* 210:369 */     h ^= h >>> 10;
/* 211:370 */     return h;
/* 212:    */   }
/* 213:    */   
/* 214:    */   protected boolean isEqualKey(AbstractHashedMap.HashEntry entry, Object key1, Object key2, Object key3)
/* 215:    */   {
/* 216:383 */     MultiKey multi = (MultiKey)entry.getKey();
/* 217:384 */     return (multi.size() == 3) && (key1 == null ? multi.getKey(0) == null : key1.equals(multi.getKey(0))) && (key2 == null ? multi.getKey(1) == null : key2.equals(multi.getKey(1))) && (key3 == null ? multi.getKey(2) == null : key3.equals(multi.getKey(2)));
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Object get(Object key1, Object key2, Object key3, Object key4)
/* 221:    */   {
/* 222:402 */     int hashCode = hash(key1, key2, key3, key4);
/* 223:403 */     AbstractHashedMap.HashEntry entry = this.map.data[this.map.hashIndex(hashCode, this.map.data.length)];
/* 224:404 */     while (entry != null)
/* 225:    */     {
/* 226:405 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3, key4))) {
/* 227:406 */         return entry.getValue();
/* 228:    */       }
/* 229:408 */       entry = entry.next;
/* 230:    */     }
/* 231:410 */     return null;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public boolean containsKey(Object key1, Object key2, Object key3, Object key4)
/* 235:    */   {
/* 236:423 */     int hashCode = hash(key1, key2, key3, key4);
/* 237:424 */     AbstractHashedMap.HashEntry entry = this.map.data[this.map.hashIndex(hashCode, this.map.data.length)];
/* 238:425 */     while (entry != null)
/* 239:    */     {
/* 240:426 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3, key4))) {
/* 241:427 */         return true;
/* 242:    */       }
/* 243:429 */       entry = entry.next;
/* 244:    */     }
/* 245:431 */     return false;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public Object put(Object key1, Object key2, Object key3, Object key4, Object value)
/* 249:    */   {
/* 250:445 */     int hashCode = hash(key1, key2, key3, key4);
/* 251:446 */     int index = this.map.hashIndex(hashCode, this.map.data.length);
/* 252:447 */     AbstractHashedMap.HashEntry entry = this.map.data[index];
/* 253:448 */     while (entry != null)
/* 254:    */     {
/* 255:449 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3, key4)))
/* 256:    */       {
/* 257:450 */         Object oldValue = entry.getValue();
/* 258:451 */         this.map.updateEntry(entry, value);
/* 259:452 */         return oldValue;
/* 260:    */       }
/* 261:454 */       entry = entry.next;
/* 262:    */     }
/* 263:457 */     this.map.addMapping(index, hashCode, new MultiKey(key1, key2, key3, key4), value);
/* 264:458 */     return null;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Object remove(Object key1, Object key2, Object key3, Object key4)
/* 268:    */   {
/* 269:471 */     int hashCode = hash(key1, key2, key3, key4);
/* 270:472 */     int index = this.map.hashIndex(hashCode, this.map.data.length);
/* 271:473 */     AbstractHashedMap.HashEntry entry = this.map.data[index];
/* 272:474 */     AbstractHashedMap.HashEntry previous = null;
/* 273:475 */     while (entry != null)
/* 274:    */     {
/* 275:476 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3, key4)))
/* 276:    */       {
/* 277:477 */         Object oldValue = entry.getValue();
/* 278:478 */         this.map.removeMapping(entry, index, previous);
/* 279:479 */         return oldValue;
/* 280:    */       }
/* 281:481 */       previous = entry;
/* 282:482 */       entry = entry.next;
/* 283:    */     }
/* 284:484 */     return null;
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected int hash(Object key1, Object key2, Object key3, Object key4)
/* 288:    */   {
/* 289:497 */     int h = 0;
/* 290:498 */     if (key1 != null) {
/* 291:499 */       h ^= key1.hashCode();
/* 292:    */     }
/* 293:501 */     if (key2 != null) {
/* 294:502 */       h ^= key2.hashCode();
/* 295:    */     }
/* 296:504 */     if (key3 != null) {
/* 297:505 */       h ^= key3.hashCode();
/* 298:    */     }
/* 299:507 */     if (key4 != null) {
/* 300:508 */       h ^= key4.hashCode();
/* 301:    */     }
/* 302:510 */     h += (h << 9 ^ 0xFFFFFFFF);
/* 303:511 */     h ^= h >>> 14;
/* 304:512 */     h += (h << 4);
/* 305:513 */     h ^= h >>> 10;
/* 306:514 */     return h;
/* 307:    */   }
/* 308:    */   
/* 309:    */   protected boolean isEqualKey(AbstractHashedMap.HashEntry entry, Object key1, Object key2, Object key3, Object key4)
/* 310:    */   {
/* 311:528 */     MultiKey multi = (MultiKey)entry.getKey();
/* 312:529 */     return (multi.size() == 4) && (key1 == null ? multi.getKey(0) == null : key1.equals(multi.getKey(0))) && (key2 == null ? multi.getKey(1) == null : key2.equals(multi.getKey(1))) && (key3 == null ? multi.getKey(2) == null : key3.equals(multi.getKey(2))) && (key4 == null ? multi.getKey(3) == null : key4.equals(multi.getKey(3)));
/* 313:    */   }
/* 314:    */   
/* 315:    */   public Object get(Object key1, Object key2, Object key3, Object key4, Object key5)
/* 316:    */   {
/* 317:549 */     int hashCode = hash(key1, key2, key3, key4, key5);
/* 318:550 */     AbstractHashedMap.HashEntry entry = this.map.data[this.map.hashIndex(hashCode, this.map.data.length)];
/* 319:551 */     while (entry != null)
/* 320:    */     {
/* 321:552 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3, key4, key5))) {
/* 322:553 */         return entry.getValue();
/* 323:    */       }
/* 324:555 */       entry = entry.next;
/* 325:    */     }
/* 326:557 */     return null;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public boolean containsKey(Object key1, Object key2, Object key3, Object key4, Object key5)
/* 330:    */   {
/* 331:571 */     int hashCode = hash(key1, key2, key3, key4, key5);
/* 332:572 */     AbstractHashedMap.HashEntry entry = this.map.data[this.map.hashIndex(hashCode, this.map.data.length)];
/* 333:573 */     while (entry != null)
/* 334:    */     {
/* 335:574 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3, key4, key5))) {
/* 336:575 */         return true;
/* 337:    */       }
/* 338:577 */       entry = entry.next;
/* 339:    */     }
/* 340:579 */     return false;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public Object put(Object key1, Object key2, Object key3, Object key4, Object key5, Object value)
/* 344:    */   {
/* 345:594 */     int hashCode = hash(key1, key2, key3, key4, key5);
/* 346:595 */     int index = this.map.hashIndex(hashCode, this.map.data.length);
/* 347:596 */     AbstractHashedMap.HashEntry entry = this.map.data[index];
/* 348:597 */     while (entry != null)
/* 349:    */     {
/* 350:598 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3, key4, key5)))
/* 351:    */       {
/* 352:599 */         Object oldValue = entry.getValue();
/* 353:600 */         this.map.updateEntry(entry, value);
/* 354:601 */         return oldValue;
/* 355:    */       }
/* 356:603 */       entry = entry.next;
/* 357:    */     }
/* 358:606 */     this.map.addMapping(index, hashCode, new MultiKey(key1, key2, key3, key4, key5), value);
/* 359:607 */     return null;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public Object remove(Object key1, Object key2, Object key3, Object key4, Object key5)
/* 363:    */   {
/* 364:621 */     int hashCode = hash(key1, key2, key3, key4, key5);
/* 365:622 */     int index = this.map.hashIndex(hashCode, this.map.data.length);
/* 366:623 */     AbstractHashedMap.HashEntry entry = this.map.data[index];
/* 367:624 */     AbstractHashedMap.HashEntry previous = null;
/* 368:625 */     while (entry != null)
/* 369:    */     {
/* 370:626 */       if ((entry.hashCode == hashCode) && (isEqualKey(entry, key1, key2, key3, key4, key5)))
/* 371:    */       {
/* 372:627 */         Object oldValue = entry.getValue();
/* 373:628 */         this.map.removeMapping(entry, index, previous);
/* 374:629 */         return oldValue;
/* 375:    */       }
/* 376:631 */       previous = entry;
/* 377:632 */       entry = entry.next;
/* 378:    */     }
/* 379:634 */     return null;
/* 380:    */   }
/* 381:    */   
/* 382:    */   protected int hash(Object key1, Object key2, Object key3, Object key4, Object key5)
/* 383:    */   {
/* 384:648 */     int h = 0;
/* 385:649 */     if (key1 != null) {
/* 386:650 */       h ^= key1.hashCode();
/* 387:    */     }
/* 388:652 */     if (key2 != null) {
/* 389:653 */       h ^= key2.hashCode();
/* 390:    */     }
/* 391:655 */     if (key3 != null) {
/* 392:656 */       h ^= key3.hashCode();
/* 393:    */     }
/* 394:658 */     if (key4 != null) {
/* 395:659 */       h ^= key4.hashCode();
/* 396:    */     }
/* 397:661 */     if (key5 != null) {
/* 398:662 */       h ^= key5.hashCode();
/* 399:    */     }
/* 400:664 */     h += (h << 9 ^ 0xFFFFFFFF);
/* 401:665 */     h ^= h >>> 14;
/* 402:666 */     h += (h << 4);
/* 403:667 */     h ^= h >>> 10;
/* 404:668 */     return h;
/* 405:    */   }
/* 406:    */   
/* 407:    */   protected boolean isEqualKey(AbstractHashedMap.HashEntry entry, Object key1, Object key2, Object key3, Object key4, Object key5)
/* 408:    */   {
/* 409:683 */     MultiKey multi = (MultiKey)entry.getKey();
/* 410:684 */     return (multi.size() == 5) && (key1 == null ? multi.getKey(0) == null : key1.equals(multi.getKey(0))) && (key2 == null ? multi.getKey(1) == null : key2.equals(multi.getKey(1))) && (key3 == null ? multi.getKey(2) == null : key3.equals(multi.getKey(2))) && (key4 == null ? multi.getKey(3) == null : key4.equals(multi.getKey(3))) && (key5 == null ? multi.getKey(4) == null : key5.equals(multi.getKey(4)));
/* 411:    */   }
/* 412:    */   
/* 413:    */   public boolean removeAll(Object key1)
/* 414:    */   {
/* 415:704 */     boolean modified = false;
/* 416:705 */     MapIterator it = mapIterator();
/* 417:706 */     while (it.hasNext())
/* 418:    */     {
/* 419:707 */       MultiKey multi = (MultiKey)it.next();
/* 420:708 */       if ((multi.size() >= 1) && (key1 == null ? multi.getKey(0) == null : key1.equals(multi.getKey(0))))
/* 421:    */       {
/* 422:710 */         it.remove();
/* 423:711 */         modified = true;
/* 424:    */       }
/* 425:    */     }
/* 426:714 */     return modified;
/* 427:    */   }
/* 428:    */   
/* 429:    */   public boolean removeAll(Object key1, Object key2)
/* 430:    */   {
/* 431:728 */     boolean modified = false;
/* 432:729 */     MapIterator it = mapIterator();
/* 433:730 */     while (it.hasNext())
/* 434:    */     {
/* 435:731 */       MultiKey multi = (MultiKey)it.next();
/* 436:732 */       if ((multi.size() >= 2) && (key1 == null ? multi.getKey(0) == null : key1.equals(multi.getKey(0))) && (key2 == null ? multi.getKey(1) == null : key2.equals(multi.getKey(1))))
/* 437:    */       {
/* 438:735 */         it.remove();
/* 439:736 */         modified = true;
/* 440:    */       }
/* 441:    */     }
/* 442:739 */     return modified;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public boolean removeAll(Object key1, Object key2, Object key3)
/* 446:    */   {
/* 447:754 */     boolean modified = false;
/* 448:755 */     MapIterator it = mapIterator();
/* 449:756 */     while (it.hasNext())
/* 450:    */     {
/* 451:757 */       MultiKey multi = (MultiKey)it.next();
/* 452:758 */       if ((multi.size() >= 3) && (key1 == null ? multi.getKey(0) == null : key1.equals(multi.getKey(0))) && (key2 == null ? multi.getKey(1) == null : key2.equals(multi.getKey(1))) && (key3 == null ? multi.getKey(2) == null : key3.equals(multi.getKey(2))))
/* 453:    */       {
/* 454:762 */         it.remove();
/* 455:763 */         modified = true;
/* 456:    */       }
/* 457:    */     }
/* 458:766 */     return modified;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public boolean removeAll(Object key1, Object key2, Object key3, Object key4)
/* 462:    */   {
/* 463:782 */     boolean modified = false;
/* 464:783 */     MapIterator it = mapIterator();
/* 465:784 */     while (it.hasNext())
/* 466:    */     {
/* 467:785 */       MultiKey multi = (MultiKey)it.next();
/* 468:786 */       if ((multi.size() >= 4) && (key1 == null ? multi.getKey(0) == null : key1.equals(multi.getKey(0))) && (key2 == null ? multi.getKey(1) == null : key2.equals(multi.getKey(1))) && (key3 == null ? multi.getKey(2) == null : key3.equals(multi.getKey(2))) && (key4 == null ? multi.getKey(3) == null : key4.equals(multi.getKey(3))))
/* 469:    */       {
/* 470:791 */         it.remove();
/* 471:792 */         modified = true;
/* 472:    */       }
/* 473:    */     }
/* 474:795 */     return modified;
/* 475:    */   }
/* 476:    */   
/* 477:    */   protected void checkKey(Object key)
/* 478:    */   {
/* 479:805 */     if (key == null) {
/* 480:806 */       throw new NullPointerException("Key must not be null");
/* 481:    */     }
/* 482:808 */     if (!(key instanceof MultiKey)) {
/* 483:809 */       throw new ClassCastException("Key must be a MultiKey");
/* 484:    */     }
/* 485:    */   }
/* 486:    */   
/* 487:    */   public Object clone()
/* 488:    */   {
/* 489:819 */     return new MultiKeyMap((AbstractHashedMap)this.map.clone());
/* 490:    */   }
/* 491:    */   
/* 492:    */   public Object put(Object key, Object value)
/* 493:    */   {
/* 494:833 */     checkKey(key);
/* 495:834 */     return this.map.put(key, value);
/* 496:    */   }
/* 497:    */   
/* 498:    */   public void putAll(Map mapToCopy)
/* 499:    */   {
/* 500:846 */     for (Iterator it = mapToCopy.keySet().iterator(); it.hasNext();)
/* 501:    */     {
/* 502:847 */       Object key = it.next();
/* 503:848 */       checkKey(key);
/* 504:    */     }
/* 505:850 */     this.map.putAll(mapToCopy);
/* 506:    */   }
/* 507:    */   
/* 508:    */   public MapIterator mapIterator()
/* 509:    */   {
/* 510:855 */     return this.map.mapIterator();
/* 511:    */   }
/* 512:    */   
/* 513:    */   public int size()
/* 514:    */   {
/* 515:859 */     return this.map.size();
/* 516:    */   }
/* 517:    */   
/* 518:    */   public boolean isEmpty()
/* 519:    */   {
/* 520:863 */     return this.map.isEmpty();
/* 521:    */   }
/* 522:    */   
/* 523:    */   public boolean containsKey(Object key)
/* 524:    */   {
/* 525:867 */     return this.map.containsKey(key);
/* 526:    */   }
/* 527:    */   
/* 528:    */   public boolean containsValue(Object value)
/* 529:    */   {
/* 530:871 */     return this.map.containsValue(value);
/* 531:    */   }
/* 532:    */   
/* 533:    */   public Object get(Object key)
/* 534:    */   {
/* 535:875 */     return this.map.get(key);
/* 536:    */   }
/* 537:    */   
/* 538:    */   public Object remove(Object key)
/* 539:    */   {
/* 540:879 */     return this.map.remove(key);
/* 541:    */   }
/* 542:    */   
/* 543:    */   public void clear()
/* 544:    */   {
/* 545:883 */     this.map.clear();
/* 546:    */   }
/* 547:    */   
/* 548:    */   public Set keySet()
/* 549:    */   {
/* 550:887 */     return this.map.keySet();
/* 551:    */   }
/* 552:    */   
/* 553:    */   public Collection values()
/* 554:    */   {
/* 555:891 */     return this.map.values();
/* 556:    */   }
/* 557:    */   
/* 558:    */   public Set entrySet()
/* 559:    */   {
/* 560:895 */     return this.map.entrySet();
/* 561:    */   }
/* 562:    */   
/* 563:    */   public boolean equals(Object obj)
/* 564:    */   {
/* 565:899 */     if (obj == this) {
/* 566:900 */       return true;
/* 567:    */     }
/* 568:902 */     return this.map.equals(obj);
/* 569:    */   }
/* 570:    */   
/* 571:    */   public int hashCode()
/* 572:    */   {
/* 573:906 */     return this.map.hashCode();
/* 574:    */   }
/* 575:    */   
/* 576:    */   public String toString()
/* 577:    */   {
/* 578:910 */     return this.map.toString();
/* 579:    */   }
/* 580:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.MultiKeyMap
 * JD-Core Version:    0.7.0.1
 */
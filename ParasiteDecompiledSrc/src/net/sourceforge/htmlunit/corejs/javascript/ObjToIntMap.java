/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ 
/*   8:    */ public class ObjToIntMap
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = -1542220580748809402L;
/*  12:    */   private static final int A = -1640531527;
/*  13:    */   
/*  14:    */   public static class Iterator
/*  15:    */   {
/*  16:    */     ObjToIntMap master;
/*  17:    */     private int cursor;
/*  18:    */     private int remaining;
/*  19:    */     private Object[] keys;
/*  20:    */     private int[] values;
/*  21:    */     
/*  22:    */     Iterator(ObjToIntMap master)
/*  23:    */     {
/*  24: 68 */       this.master = master;
/*  25:    */     }
/*  26:    */     
/*  27:    */     final void init(Object[] keys, int[] values, int keyCount)
/*  28:    */     {
/*  29: 72 */       this.keys = keys;
/*  30: 73 */       this.values = values;
/*  31: 74 */       this.cursor = -1;
/*  32: 75 */       this.remaining = keyCount;
/*  33:    */     }
/*  34:    */     
/*  35:    */     public void start()
/*  36:    */     {
/*  37: 79 */       this.master.initIterator(this);
/*  38: 80 */       next();
/*  39:    */     }
/*  40:    */     
/*  41:    */     public boolean done()
/*  42:    */     {
/*  43: 84 */       return this.remaining < 0;
/*  44:    */     }
/*  45:    */     
/*  46:    */     public void next()
/*  47:    */     {
/*  48: 88 */       if (this.remaining == -1) {
/*  49: 88 */         Kit.codeBug();
/*  50:    */       }
/*  51: 89 */       if (this.remaining == 0)
/*  52:    */       {
/*  53: 90 */         this.remaining = -1;
/*  54: 91 */         this.cursor = -1;
/*  55:    */       }
/*  56:    */       else
/*  57:    */       {
/*  58: 93 */         for (this.cursor += 1;; this.cursor += 1)
/*  59:    */         {
/*  60: 94 */           Object key = this.keys[this.cursor];
/*  61: 95 */           if ((key != null) && (key != ObjToIntMap.DELETED))
/*  62:    */           {
/*  63: 96 */             this.remaining -= 1;
/*  64: 97 */             break;
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */     
/*  70:    */     public Object getKey()
/*  71:    */     {
/*  72:104 */       Object key = this.keys[this.cursor];
/*  73:105 */       if (key == UniqueTag.NULL_VALUE) {
/*  74:105 */         key = null;
/*  75:    */       }
/*  76:106 */       return key;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public int getValue()
/*  80:    */     {
/*  81:110 */       return this.values[this.cursor];
/*  82:    */     }
/*  83:    */     
/*  84:    */     public void setValue(int value)
/*  85:    */     {
/*  86:114 */       this.values[this.cursor] = value;
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public ObjToIntMap()
/*  91:    */   {
/*  92:125 */     this(4);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public ObjToIntMap(int keyCountHint)
/*  96:    */   {
/*  97:129 */     if (keyCountHint < 0) {
/*  98:129 */       Kit.codeBug();
/*  99:    */     }
/* 100:131 */     int minimalCapacity = keyCountHint * 4 / 3;
/* 101:133 */     for (int i = 2; 1 << i < minimalCapacity; i++) {}
/* 102:134 */     this.power = i;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean isEmpty()
/* 106:    */   {
/* 107:139 */     return this.keyCount == 0;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int size()
/* 111:    */   {
/* 112:143 */     return this.keyCount;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean has(Object key)
/* 116:    */   {
/* 117:147 */     if (key == null) {
/* 118:147 */       key = UniqueTag.NULL_VALUE;
/* 119:    */     }
/* 120:148 */     return 0 <= findIndex(key);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int get(Object key, int defaultValue)
/* 124:    */   {
/* 125:156 */     if (key == null) {
/* 126:156 */       key = UniqueTag.NULL_VALUE;
/* 127:    */     }
/* 128:157 */     int index = findIndex(key);
/* 129:158 */     if (0 <= index) {
/* 130:159 */       return this.values[index];
/* 131:    */     }
/* 132:161 */     return defaultValue;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getExisting(Object key)
/* 136:    */   {
/* 137:170 */     if (key == null) {
/* 138:170 */       key = UniqueTag.NULL_VALUE;
/* 139:    */     }
/* 140:171 */     int index = findIndex(key);
/* 141:172 */     if (0 <= index) {
/* 142:173 */       return this.values[index];
/* 143:    */     }
/* 144:176 */     Kit.codeBug();
/* 145:177 */     return 0;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void put(Object key, int value)
/* 149:    */   {
/* 150:181 */     if (key == null) {
/* 151:181 */       key = UniqueTag.NULL_VALUE;
/* 152:    */     }
/* 153:182 */     int index = ensureIndex(key);
/* 154:183 */     this.values[index] = value;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Object intern(Object keyArg)
/* 158:    */   {
/* 159:192 */     boolean nullKey = false;
/* 160:193 */     if (keyArg == null)
/* 161:    */     {
/* 162:194 */       nullKey = true;
/* 163:195 */       keyArg = UniqueTag.NULL_VALUE;
/* 164:    */     }
/* 165:197 */     int index = ensureIndex(keyArg);
/* 166:198 */     this.values[index] = 0;
/* 167:199 */     return nullKey ? null : this.keys[index];
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void remove(Object key)
/* 171:    */   {
/* 172:203 */     if (key == null) {
/* 173:203 */       key = UniqueTag.NULL_VALUE;
/* 174:    */     }
/* 175:204 */     int index = findIndex(key);
/* 176:205 */     if (0 <= index)
/* 177:    */     {
/* 178:206 */       this.keys[index] = DELETED;
/* 179:207 */       this.keyCount -= 1;
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void clear()
/* 184:    */   {
/* 185:212 */     int i = this.keys.length;
/* 186:213 */     while (i != 0) {
/* 187:214 */       this.keys[(--i)] = null;
/* 188:    */     }
/* 189:216 */     this.keyCount = 0;
/* 190:217 */     this.occupiedCount = 0;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Iterator newIterator()
/* 194:    */   {
/* 195:221 */     return new Iterator(this);
/* 196:    */   }
/* 197:    */   
/* 198:    */   final void initIterator(Iterator i)
/* 199:    */   {
/* 200:228 */     i.init(this.keys, this.values, this.keyCount);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Object[] getKeys()
/* 204:    */   {
/* 205:233 */     Object[] array = new Object[this.keyCount];
/* 206:234 */     getKeys(array, 0);
/* 207:235 */     return array;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void getKeys(Object[] array, int offset)
/* 211:    */   {
/* 212:239 */     int count = this.keyCount;
/* 213:240 */     for (int i = 0; count != 0; i++)
/* 214:    */     {
/* 215:241 */       Object key = this.keys[i];
/* 216:242 */       if ((key != null) && (key != DELETED))
/* 217:    */       {
/* 218:243 */         if (key == UniqueTag.NULL_VALUE) {
/* 219:243 */           key = null;
/* 220:    */         }
/* 221:244 */         array[offset] = key;
/* 222:245 */         offset++;
/* 223:246 */         count--;
/* 224:    */       }
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   private static int tableLookupStep(int fraction, int mask, int power)
/* 229:    */   {
/* 230:252 */     int shift = 32 - 2 * power;
/* 231:253 */     if (shift >= 0) {
/* 232:254 */       return fraction >>> shift & mask | 0x1;
/* 233:    */     }
/* 234:257 */     return fraction & mask >>> -shift | 0x1;
/* 235:    */   }
/* 236:    */   
/* 237:    */   private int findIndex(Object key)
/* 238:    */   {
/* 239:262 */     if (this.keys != null)
/* 240:    */     {
/* 241:263 */       int hash = key.hashCode();
/* 242:264 */       int fraction = hash * -1640531527;
/* 243:265 */       int index = fraction >>> 32 - this.power;
/* 244:266 */       Object test = this.keys[index];
/* 245:267 */       if (test != null)
/* 246:    */       {
/* 247:268 */         int N = 1 << this.power;
/* 248:269 */         if ((test == key) || ((this.values[(N + index)] == hash) && (test.equals(key)))) {
/* 249:272 */           return index;
/* 250:    */         }
/* 251:275 */         int mask = N - 1;
/* 252:276 */         int step = tableLookupStep(fraction, mask, this.power);
/* 253:277 */         int n = 0;
/* 254:    */         do
/* 255:    */         {
/* 256:283 */           index = index + step & mask;
/* 257:284 */           test = this.keys[index];
/* 258:285 */           if (test == null) {
/* 259:    */             break;
/* 260:    */           }
/* 261:288 */         } while ((test != key) && ((this.values[(N + index)] != hash) || (!test.equals(key))));
/* 262:291 */         return index;
/* 263:    */       }
/* 264:    */     }
/* 265:296 */     return -1;
/* 266:    */   }
/* 267:    */   
/* 268:    */   private int insertNewKey(Object key, int hash)
/* 269:    */   {
/* 270:304 */     int fraction = hash * -1640531527;
/* 271:305 */     int index = fraction >>> 32 - this.power;
/* 272:306 */     int N = 1 << this.power;
/* 273:307 */     if (this.keys[index] != null)
/* 274:    */     {
/* 275:308 */       int mask = N - 1;
/* 276:309 */       int step = tableLookupStep(fraction, mask, this.power);
/* 277:310 */       int firstIndex = index;
/* 278:    */       do
/* 279:    */       {
/* 280:313 */         index = index + step & mask;
/* 281:315 */       } while (this.keys[index] != null);
/* 282:    */     }
/* 283:317 */     this.keys[index] = key;
/* 284:318 */     this.values[(N + index)] = hash;
/* 285:319 */     this.occupiedCount += 1;
/* 286:320 */     this.keyCount += 1;
/* 287:    */     
/* 288:322 */     return index;
/* 289:    */   }
/* 290:    */   
/* 291:    */   private void rehashTable()
/* 292:    */   {
/* 293:326 */     if (this.keys == null)
/* 294:    */     {
/* 295:329 */       int N = 1 << this.power;
/* 296:330 */       this.keys = new Object[N];
/* 297:331 */       this.values = new int[2 * N];
/* 298:    */     }
/* 299:    */     else
/* 300:    */     {
/* 301:335 */       if (this.keyCount * 2 >= this.occupiedCount) {
/* 302:337 */         this.power += 1;
/* 303:    */       }
/* 304:339 */       int N = 1 << this.power;
/* 305:340 */       Object[] oldKeys = this.keys;
/* 306:341 */       int[] oldValues = this.values;
/* 307:342 */       int oldN = oldKeys.length;
/* 308:343 */       this.keys = new Object[N];
/* 309:344 */       this.values = new int[2 * N];
/* 310:    */       
/* 311:346 */       int remaining = this.keyCount;
/* 312:347 */       this.occupiedCount = (this.keyCount = 0);
/* 313:348 */       for (int i = 0; remaining != 0; i++)
/* 314:    */       {
/* 315:349 */         Object key = oldKeys[i];
/* 316:350 */         if ((key != null) && (key != DELETED))
/* 317:    */         {
/* 318:351 */           int keyHash = oldValues[(oldN + i)];
/* 319:352 */           int index = insertNewKey(key, keyHash);
/* 320:353 */           this.values[index] = oldValues[i];
/* 321:354 */           remaining--;
/* 322:    */         }
/* 323:    */       }
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   private int ensureIndex(Object key)
/* 328:    */   {
/* 329:362 */     int hash = key.hashCode();
/* 330:363 */     int index = -1;
/* 331:364 */     int firstDeleted = -1;
/* 332:365 */     if (this.keys != null)
/* 333:    */     {
/* 334:366 */       int fraction = hash * -1640531527;
/* 335:367 */       index = fraction >>> 32 - this.power;
/* 336:368 */       Object test = this.keys[index];
/* 337:369 */       if (test != null)
/* 338:    */       {
/* 339:370 */         int N = 1 << this.power;
/* 340:371 */         if ((test == key) || ((this.values[(N + index)] == hash) && (test.equals(key)))) {
/* 341:374 */           return index;
/* 342:    */         }
/* 343:376 */         if (test == DELETED) {
/* 344:377 */           firstDeleted = index;
/* 345:    */         }
/* 346:381 */         int mask = N - 1;
/* 347:382 */         int step = tableLookupStep(fraction, mask, this.power);
/* 348:383 */         int n = 0;
/* 349:    */         for (;;)
/* 350:    */         {
/* 351:389 */           index = index + step & mask;
/* 352:390 */           test = this.keys[index];
/* 353:391 */           if (test == null) {
/* 354:    */             break;
/* 355:    */           }
/* 356:394 */           if ((test == key) || ((this.values[(N + index)] == hash) && (test.equals(key)))) {
/* 357:397 */             return index;
/* 358:    */           }
/* 359:399 */           if ((test == DELETED) && (firstDeleted < 0)) {
/* 360:400 */             firstDeleted = index;
/* 361:    */           }
/* 362:    */         }
/* 363:    */       }
/* 364:    */     }
/* 365:408 */     if (firstDeleted >= 0)
/* 366:    */     {
/* 367:409 */       index = firstDeleted;
/* 368:    */     }
/* 369:    */     else
/* 370:    */     {
/* 371:413 */       if ((this.keys == null) || (this.occupiedCount * 4 >= (1 << this.power) * 3))
/* 372:    */       {
/* 373:415 */         rehashTable();
/* 374:416 */         return insertNewKey(key, hash);
/* 375:    */       }
/* 376:418 */       this.occupiedCount += 1;
/* 377:    */     }
/* 378:420 */     this.keys[index] = key;
/* 379:421 */     this.values[((1 << this.power) + index)] = hash;
/* 380:422 */     this.keyCount += 1;
/* 381:423 */     return index;
/* 382:    */   }
/* 383:    */   
/* 384:    */   private void writeObject(ObjectOutputStream out)
/* 385:    */     throws IOException
/* 386:    */   {
/* 387:429 */     out.defaultWriteObject();
/* 388:    */     
/* 389:431 */     int count = this.keyCount;
/* 390:432 */     for (int i = 0; count != 0; i++)
/* 391:    */     {
/* 392:433 */       Object key = this.keys[i];
/* 393:434 */       if ((key != null) && (key != DELETED))
/* 394:    */       {
/* 395:435 */         count--;
/* 396:436 */         out.writeObject(key);
/* 397:437 */         out.writeInt(this.values[i]);
/* 398:    */       }
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   private void readObject(ObjectInputStream in)
/* 403:    */     throws IOException, ClassNotFoundException
/* 404:    */   {
/* 405:445 */     in.defaultReadObject();
/* 406:    */     
/* 407:447 */     int writtenKeyCount = this.keyCount;
/* 408:448 */     if (writtenKeyCount != 0)
/* 409:    */     {
/* 410:449 */       this.keyCount = 0;
/* 411:450 */       int N = 1 << this.power;
/* 412:451 */       this.keys = new Object[N];
/* 413:452 */       this.values = new int[2 * N];
/* 414:453 */       for (int i = 0; i != writtenKeyCount; i++)
/* 415:    */       {
/* 416:454 */         Object key = in.readObject();
/* 417:455 */         int hash = key.hashCode();
/* 418:456 */         int index = insertNewKey(key, hash);
/* 419:457 */         this.values[index] = in.readInt();
/* 420:    */       }
/* 421:    */     }
/* 422:    */   }
/* 423:    */   
/* 424:466 */   private static final Object DELETED = new Object();
/* 425:    */   private transient Object[] keys;
/* 426:    */   private transient int[] values;
/* 427:    */   private int power;
/* 428:    */   private int keyCount;
/* 429:    */   private transient int occupiedCount;
/* 430:    */   private static final boolean check = false;
/* 431:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ObjToIntMap
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ 
/*   8:    */ public class UintMap
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = 4242698212885848444L;
/*  12:    */   private static final int A = -1640531527;
/*  13:    */   private static final int EMPTY = -1;
/*  14:    */   private static final int DELETED = -2;
/*  15:    */   private transient int[] keys;
/*  16:    */   private transient Object[] values;
/*  17:    */   private int power;
/*  18:    */   private int keyCount;
/*  19:    */   private transient int occupiedCount;
/*  20:    */   private transient int ivaluesShift;
/*  21:    */   private static final boolean check = false;
/*  22:    */   
/*  23:    */   public UintMap()
/*  24:    */   {
/*  25: 64 */     this(4);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public UintMap(int initialCapacity)
/*  29:    */   {
/*  30: 68 */     if (initialCapacity < 0) {
/*  31: 68 */       Kit.codeBug();
/*  32:    */     }
/*  33: 70 */     int minimalCapacity = initialCapacity * 4 / 3;
/*  34: 72 */     for (int i = 2; 1 << i < minimalCapacity; i++) {}
/*  35: 73 */     this.power = i;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isEmpty()
/*  39:    */   {
/*  40: 78 */     return this.keyCount == 0;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int size()
/*  44:    */   {
/*  45: 82 */     return this.keyCount;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean has(int key)
/*  49:    */   {
/*  50: 86 */     if (key < 0) {
/*  51: 86 */       Kit.codeBug();
/*  52:    */     }
/*  53: 87 */     return 0 <= findIndex(key);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object getObject(int key)
/*  57:    */   {
/*  58: 95 */     if (key < 0) {
/*  59: 95 */       Kit.codeBug();
/*  60:    */     }
/*  61: 96 */     if (this.values != null)
/*  62:    */     {
/*  63: 97 */       int index = findIndex(key);
/*  64: 98 */       if (0 <= index) {
/*  65: 99 */         return this.values[index];
/*  66:    */       }
/*  67:    */     }
/*  68:102 */     return null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getInt(int key, int defaultValue)
/*  72:    */   {
/*  73:110 */     if (key < 0) {
/*  74:110 */       Kit.codeBug();
/*  75:    */     }
/*  76:111 */     int index = findIndex(key);
/*  77:112 */     if (0 <= index)
/*  78:    */     {
/*  79:113 */       if (this.ivaluesShift != 0) {
/*  80:114 */         return this.keys[(this.ivaluesShift + index)];
/*  81:    */       }
/*  82:116 */       return 0;
/*  83:    */     }
/*  84:118 */     return defaultValue;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getExistingInt(int key)
/*  88:    */   {
/*  89:128 */     if (key < 0) {
/*  90:128 */       Kit.codeBug();
/*  91:    */     }
/*  92:129 */     int index = findIndex(key);
/*  93:130 */     if (0 <= index)
/*  94:    */     {
/*  95:131 */       if (this.ivaluesShift != 0) {
/*  96:132 */         return this.keys[(this.ivaluesShift + index)];
/*  97:    */       }
/*  98:134 */       return 0;
/*  99:    */     }
/* 100:137 */     Kit.codeBug();
/* 101:138 */     return 0;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void put(int key, Object value)
/* 105:    */   {
/* 106:146 */     if (key < 0) {
/* 107:146 */       Kit.codeBug();
/* 108:    */     }
/* 109:147 */     int index = ensureIndex(key, false);
/* 110:148 */     if (this.values == null) {
/* 111:149 */       this.values = new Object[1 << this.power];
/* 112:    */     }
/* 113:151 */     this.values[index] = value;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void put(int key, int value)
/* 117:    */   {
/* 118:159 */     if (key < 0) {
/* 119:159 */       Kit.codeBug();
/* 120:    */     }
/* 121:160 */     int index = ensureIndex(key, true);
/* 122:161 */     if (this.ivaluesShift == 0)
/* 123:    */     {
/* 124:162 */       int N = 1 << this.power;
/* 125:164 */       if (this.keys.length != N * 2)
/* 126:    */       {
/* 127:165 */         int[] tmp = new int[N * 2];
/* 128:166 */         System.arraycopy(this.keys, 0, tmp, 0, N);
/* 129:167 */         this.keys = tmp;
/* 130:    */       }
/* 131:169 */       this.ivaluesShift = N;
/* 132:    */     }
/* 133:171 */     this.keys[(this.ivaluesShift + index)] = value;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void remove(int key)
/* 137:    */   {
/* 138:175 */     if (key < 0) {
/* 139:175 */       Kit.codeBug();
/* 140:    */     }
/* 141:176 */     int index = findIndex(key);
/* 142:177 */     if (0 <= index)
/* 143:    */     {
/* 144:178 */       this.keys[index] = -2;
/* 145:179 */       this.keyCount -= 1;
/* 146:182 */       if (this.values != null) {
/* 147:182 */         this.values[index] = null;
/* 148:    */       }
/* 149:183 */       if (this.ivaluesShift != 0) {
/* 150:183 */         this.keys[(this.ivaluesShift + index)] = 0;
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void clear()
/* 156:    */   {
/* 157:188 */     int N = 1 << this.power;
/* 158:189 */     if (this.keys != null)
/* 159:    */     {
/* 160:190 */       for (int i = 0; i != N; i++) {
/* 161:191 */         this.keys[i] = -1;
/* 162:    */       }
/* 163:193 */       if (this.values != null) {
/* 164:194 */         for (int i = 0; i != N; i++) {
/* 165:195 */           this.values[i] = null;
/* 166:    */         }
/* 167:    */       }
/* 168:    */     }
/* 169:199 */     this.ivaluesShift = 0;
/* 170:200 */     this.keyCount = 0;
/* 171:201 */     this.occupiedCount = 0;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public int[] getKeys()
/* 175:    */   {
/* 176:206 */     int[] keys = this.keys;
/* 177:207 */     int n = this.keyCount;
/* 178:208 */     int[] result = new int[n];
/* 179:209 */     for (int i = 0; n != 0; i++)
/* 180:    */     {
/* 181:210 */       int entry = keys[i];
/* 182:211 */       if ((entry != -1) && (entry != -2)) {
/* 183:212 */         result[(--n)] = entry;
/* 184:    */       }
/* 185:    */     }
/* 186:215 */     return result;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private static int tableLookupStep(int fraction, int mask, int power)
/* 190:    */   {
/* 191:219 */     int shift = 32 - 2 * power;
/* 192:220 */     if (shift >= 0) {
/* 193:221 */       return fraction >>> shift & mask | 0x1;
/* 194:    */     }
/* 195:224 */     return fraction & mask >>> -shift | 0x1;
/* 196:    */   }
/* 197:    */   
/* 198:    */   private int findIndex(int key)
/* 199:    */   {
/* 200:229 */     int[] keys = this.keys;
/* 201:230 */     if (keys != null)
/* 202:    */     {
/* 203:231 */       int fraction = key * -1640531527;
/* 204:232 */       int index = fraction >>> 32 - this.power;
/* 205:233 */       int entry = keys[index];
/* 206:234 */       if (entry == key) {
/* 207:234 */         return index;
/* 208:    */       }
/* 209:235 */       if (entry != -1)
/* 210:    */       {
/* 211:237 */         int mask = (1 << this.power) - 1;
/* 212:238 */         int step = tableLookupStep(fraction, mask, this.power);
/* 213:239 */         int n = 0;
/* 214:    */         do
/* 215:    */         {
/* 216:245 */           index = index + step & mask;
/* 217:246 */           entry = keys[index];
/* 218:247 */           if (entry == key) {
/* 219:247 */             return index;
/* 220:    */           }
/* 221:248 */         } while (entry != -1);
/* 222:    */       }
/* 223:    */     }
/* 224:251 */     return -1;
/* 225:    */   }
/* 226:    */   
/* 227:    */   private int insertNewKey(int key)
/* 228:    */   {
/* 229:259 */     int[] keys = this.keys;
/* 230:260 */     int fraction = key * -1640531527;
/* 231:261 */     int index = fraction >>> 32 - this.power;
/* 232:262 */     if (keys[index] != -1)
/* 233:    */     {
/* 234:263 */       int mask = (1 << this.power) - 1;
/* 235:264 */       int step = tableLookupStep(fraction, mask, this.power);
/* 236:265 */       int firstIndex = index;
/* 237:    */       do
/* 238:    */       {
/* 239:268 */         index = index + step & mask;
/* 240:270 */       } while (keys[index] != -1);
/* 241:    */     }
/* 242:272 */     keys[index] = key;
/* 243:273 */     this.occupiedCount += 1;
/* 244:274 */     this.keyCount += 1;
/* 245:275 */     return index;
/* 246:    */   }
/* 247:    */   
/* 248:    */   private void rehashTable(boolean ensureIntSpace)
/* 249:    */   {
/* 250:279 */     if (this.keys != null) {
/* 251:281 */       if (this.keyCount * 2 >= this.occupiedCount) {
/* 252:283 */         this.power += 1;
/* 253:    */       }
/* 254:    */     }
/* 255:286 */     int N = 1 << this.power;
/* 256:287 */     int[] old = this.keys;
/* 257:288 */     int oldShift = this.ivaluesShift;
/* 258:289 */     if ((oldShift == 0) && (!ensureIntSpace))
/* 259:    */     {
/* 260:290 */       this.keys = new int[N];
/* 261:    */     }
/* 262:    */     else
/* 263:    */     {
/* 264:293 */       this.ivaluesShift = N;this.keys = new int[N * 2];
/* 265:    */     }
/* 266:295 */     for (int i = 0; i != N; i++) {
/* 267:295 */       this.keys[i] = -1;
/* 268:    */     }
/* 269:297 */     Object[] oldValues = this.values;
/* 270:298 */     if (oldValues != null) {
/* 271:298 */       this.values = new Object[N];
/* 272:    */     }
/* 273:300 */     int oldCount = this.keyCount;
/* 274:301 */     this.occupiedCount = 0;
/* 275:302 */     if (oldCount != 0)
/* 276:    */     {
/* 277:303 */       this.keyCount = 0;
/* 278:304 */       int i = 0;
/* 279:304 */       for (int remaining = oldCount; remaining != 0; i++)
/* 280:    */       {
/* 281:305 */         int key = old[i];
/* 282:306 */         if ((key != -1) && (key != -2))
/* 283:    */         {
/* 284:307 */           int index = insertNewKey(key);
/* 285:308 */           if (oldValues != null) {
/* 286:309 */             this.values[index] = oldValues[i];
/* 287:    */           }
/* 288:311 */           if (oldShift != 0) {
/* 289:312 */             this.keys[(this.ivaluesShift + index)] = old[(oldShift + i)];
/* 290:    */           }
/* 291:314 */           remaining--;
/* 292:    */         }
/* 293:    */       }
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   private int ensureIndex(int key, boolean intType)
/* 298:    */   {
/* 299:322 */     int index = -1;
/* 300:323 */     int firstDeleted = -1;
/* 301:324 */     int[] keys = this.keys;
/* 302:325 */     if (keys != null)
/* 303:    */     {
/* 304:326 */       int fraction = key * -1640531527;
/* 305:327 */       index = fraction >>> 32 - this.power;
/* 306:328 */       int entry = keys[index];
/* 307:329 */       if (entry == key) {
/* 308:329 */         return index;
/* 309:    */       }
/* 310:330 */       if (entry != -1)
/* 311:    */       {
/* 312:331 */         if (entry == -2) {
/* 313:331 */           firstDeleted = index;
/* 314:    */         }
/* 315:333 */         int mask = (1 << this.power) - 1;
/* 316:334 */         int step = tableLookupStep(fraction, mask, this.power);
/* 317:335 */         int n = 0;
/* 318:    */         do
/* 319:    */         {
/* 320:341 */           index = index + step & mask;
/* 321:342 */           entry = keys[index];
/* 322:343 */           if (entry == key) {
/* 323:343 */             return index;
/* 324:    */           }
/* 325:344 */           if ((entry == -2) && (firstDeleted < 0)) {
/* 326:345 */             firstDeleted = index;
/* 327:    */           }
/* 328:347 */         } while (entry != -1);
/* 329:    */       }
/* 330:    */     }
/* 331:353 */     if (firstDeleted >= 0)
/* 332:    */     {
/* 333:354 */       index = firstDeleted;
/* 334:    */     }
/* 335:    */     else
/* 336:    */     {
/* 337:358 */       if ((keys == null) || (this.occupiedCount * 4 >= (1 << this.power) * 3))
/* 338:    */       {
/* 339:360 */         rehashTable(intType);
/* 340:361 */         return insertNewKey(key);
/* 341:    */       }
/* 342:363 */       this.occupiedCount += 1;
/* 343:    */     }
/* 344:365 */     keys[index] = key;
/* 345:366 */     this.keyCount += 1;
/* 346:367 */     return index;
/* 347:    */   }
/* 348:    */   
/* 349:    */   private void writeObject(ObjectOutputStream out)
/* 350:    */     throws IOException
/* 351:    */   {
/* 352:373 */     out.defaultWriteObject();
/* 353:    */     
/* 354:375 */     int count = this.keyCount;
/* 355:376 */     if (count != 0)
/* 356:    */     {
/* 357:377 */       boolean hasIntValues = this.ivaluesShift != 0;
/* 358:378 */       boolean hasObjectValues = this.values != null;
/* 359:379 */       out.writeBoolean(hasIntValues);
/* 360:380 */       out.writeBoolean(hasObjectValues);
/* 361:382 */       for (int i = 0; count != 0; i++)
/* 362:    */       {
/* 363:383 */         int key = this.keys[i];
/* 364:384 */         if ((key != -1) && (key != -2))
/* 365:    */         {
/* 366:385 */           count--;
/* 367:386 */           out.writeInt(key);
/* 368:387 */           if (hasIntValues) {
/* 369:388 */             out.writeInt(this.keys[(this.ivaluesShift + i)]);
/* 370:    */           }
/* 371:390 */           if (hasObjectValues) {
/* 372:391 */             out.writeObject(this.values[i]);
/* 373:    */           }
/* 374:    */         }
/* 375:    */       }
/* 376:    */     }
/* 377:    */   }
/* 378:    */   
/* 379:    */   private void readObject(ObjectInputStream in)
/* 380:    */     throws IOException, ClassNotFoundException
/* 381:    */   {
/* 382:401 */     in.defaultReadObject();
/* 383:    */     
/* 384:403 */     int writtenKeyCount = this.keyCount;
/* 385:404 */     if (writtenKeyCount != 0)
/* 386:    */     {
/* 387:405 */       this.keyCount = 0;
/* 388:406 */       boolean hasIntValues = in.readBoolean();
/* 389:407 */       boolean hasObjectValues = in.readBoolean();
/* 390:    */       
/* 391:409 */       int N = 1 << this.power;
/* 392:410 */       if (hasIntValues)
/* 393:    */       {
/* 394:411 */         this.keys = new int[2 * N];
/* 395:412 */         this.ivaluesShift = N;
/* 396:    */       }
/* 397:    */       else
/* 398:    */       {
/* 399:414 */         this.keys = new int[N];
/* 400:    */       }
/* 401:416 */       for (int i = 0; i != N; i++) {
/* 402:417 */         this.keys[i] = -1;
/* 403:    */       }
/* 404:419 */       if (hasObjectValues) {
/* 405:420 */         this.values = new Object[N];
/* 406:    */       }
/* 407:422 */       for (int i = 0; i != writtenKeyCount; i++)
/* 408:    */       {
/* 409:423 */         int key = in.readInt();
/* 410:424 */         int index = insertNewKey(key);
/* 411:425 */         if (hasIntValues)
/* 412:    */         {
/* 413:426 */           int ivalue = in.readInt();
/* 414:427 */           this.keys[(this.ivaluesShift + index)] = ivalue;
/* 415:    */         }
/* 416:429 */         if (hasObjectValues) {
/* 417:430 */           this.values[index] = in.readObject();
/* 418:    */         }
/* 419:    */       }
/* 420:    */     }
/* 421:    */   }
/* 422:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.UintMap
 * JD-Core Version:    0.7.0.1
 */
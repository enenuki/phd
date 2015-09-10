/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ 
/*   8:    */ public class ObjArray
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = 4174889037736658296L;
/*  12:    */   private int size;
/*  13:    */   private boolean sealed;
/*  14:    */   private static final int FIELDS_STORE_SIZE = 5;
/*  15:    */   private transient Object f0;
/*  16:    */   private transient Object f1;
/*  17:    */   private transient Object f2;
/*  18:    */   private transient Object f3;
/*  19:    */   private transient Object f4;
/*  20:    */   private transient Object[] data;
/*  21:    */   
/*  22:    */   public final boolean isSealed()
/*  23:    */   {
/*  24: 58 */     return this.sealed;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public final void seal()
/*  28:    */   {
/*  29: 63 */     this.sealed = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public final boolean isEmpty()
/*  33:    */   {
/*  34: 68 */     return this.size == 0;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public final int size()
/*  38:    */   {
/*  39: 73 */     return this.size;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final void setSize(int newSize)
/*  43:    */   {
/*  44: 78 */     if (newSize < 0) {
/*  45: 78 */       throw new IllegalArgumentException();
/*  46:    */     }
/*  47: 79 */     if (this.sealed) {
/*  48: 79 */       throw onSeledMutation();
/*  49:    */     }
/*  50: 80 */     int N = this.size;
/*  51: 81 */     if (newSize < N) {
/*  52: 82 */       for (int i = newSize; i != N; i++) {
/*  53: 83 */         setImpl(i, null);
/*  54:    */       }
/*  55: 85 */     } else if ((newSize > N) && 
/*  56: 86 */       (newSize > 5)) {
/*  57: 87 */       ensureCapacity(newSize);
/*  58:    */     }
/*  59: 90 */     this.size = newSize;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final Object get(int index)
/*  63:    */   {
/*  64: 95 */     if ((0 > index) || (index >= this.size)) {
/*  65: 95 */       throw onInvalidIndex(index, this.size);
/*  66:    */     }
/*  67: 96 */     return getImpl(index);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final void set(int index, Object value)
/*  71:    */   {
/*  72:101 */     if ((0 > index) || (index >= this.size)) {
/*  73:101 */       throw onInvalidIndex(index, this.size);
/*  74:    */     }
/*  75:102 */     if (this.sealed) {
/*  76:102 */       throw onSeledMutation();
/*  77:    */     }
/*  78:103 */     setImpl(index, value);
/*  79:    */   }
/*  80:    */   
/*  81:    */   private Object getImpl(int index)
/*  82:    */   {
/*  83:108 */     switch (index)
/*  84:    */     {
/*  85:    */     case 0: 
/*  86:109 */       return this.f0;
/*  87:    */     case 1: 
/*  88:110 */       return this.f1;
/*  89:    */     case 2: 
/*  90:111 */       return this.f2;
/*  91:    */     case 3: 
/*  92:112 */       return this.f3;
/*  93:    */     case 4: 
/*  94:113 */       return this.f4;
/*  95:    */     }
/*  96:115 */     return this.data[(index - 5)];
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void setImpl(int index, Object value)
/* 100:    */   {
/* 101:120 */     switch (index)
/* 102:    */     {
/* 103:    */     case 0: 
/* 104:121 */       this.f0 = value; break;
/* 105:    */     case 1: 
/* 106:122 */       this.f1 = value; break;
/* 107:    */     case 2: 
/* 108:123 */       this.f2 = value; break;
/* 109:    */     case 3: 
/* 110:124 */       this.f3 = value; break;
/* 111:    */     case 4: 
/* 112:125 */       this.f4 = value; break;
/* 113:    */     default: 
/* 114:126 */       this.data[(index - 5)] = value;
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int indexOf(Object obj)
/* 119:    */   {
/* 120:133 */     int N = this.size;
/* 121:134 */     for (int i = 0; i != N; i++)
/* 122:    */     {
/* 123:135 */       Object current = getImpl(i);
/* 124:136 */       if ((current == obj) || ((current != null) && (current.equals(obj)))) {
/* 125:137 */         return i;
/* 126:    */       }
/* 127:    */     }
/* 128:140 */     return -1;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int lastIndexOf(Object obj)
/* 132:    */   {
/* 133:145 */     for (int i = this.size; i != 0;)
/* 134:    */     {
/* 135:146 */       i--;
/* 136:147 */       Object current = getImpl(i);
/* 137:148 */       if ((current == obj) || ((current != null) && (current.equals(obj)))) {
/* 138:149 */         return i;
/* 139:    */       }
/* 140:    */     }
/* 141:152 */     return -1;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public final Object peek()
/* 145:    */   {
/* 146:157 */     int N = this.size;
/* 147:158 */     if (N == 0) {
/* 148:158 */       throw onEmptyStackTopRead();
/* 149:    */     }
/* 150:159 */     return getImpl(N - 1);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final Object pop()
/* 154:    */   {
/* 155:164 */     if (this.sealed) {
/* 156:164 */       throw onSeledMutation();
/* 157:    */     }
/* 158:165 */     int N = this.size;
/* 159:166 */     N--;
/* 160:    */     Object top;
/* 161:168 */     switch (N)
/* 162:    */     {
/* 163:    */     case -1: 
/* 164:169 */       throw onEmptyStackTopRead();
/* 165:    */     case 0: 
/* 166:170 */       top = this.f0;this.f0 = null; break;
/* 167:    */     case 1: 
/* 168:171 */       top = this.f1;this.f1 = null; break;
/* 169:    */     case 2: 
/* 170:172 */       top = this.f2;this.f2 = null; break;
/* 171:    */     case 3: 
/* 172:173 */       top = this.f3;this.f3 = null; break;
/* 173:    */     case 4: 
/* 174:174 */       top = this.f4;this.f4 = null; break;
/* 175:    */     default: 
/* 176:176 */       top = this.data[(N - 5)];
/* 177:177 */       this.data[(N - 5)] = null;
/* 178:    */     }
/* 179:179 */     this.size = N;
/* 180:180 */     return top;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public final void push(Object value)
/* 184:    */   {
/* 185:185 */     add(value);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public final void add(Object value)
/* 189:    */   {
/* 190:190 */     if (this.sealed) {
/* 191:190 */       throw onSeledMutation();
/* 192:    */     }
/* 193:191 */     int N = this.size;
/* 194:192 */     if (N >= 5) {
/* 195:193 */       ensureCapacity(N + 1);
/* 196:    */     }
/* 197:195 */     this.size = (N + 1);
/* 198:196 */     setImpl(N, value);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public final void add(int index, Object value)
/* 202:    */   {
/* 203:201 */     int N = this.size;
/* 204:202 */     if ((0 > index) || (index > N)) {
/* 205:202 */       throw onInvalidIndex(index, N + 1);
/* 206:    */     }
/* 207:203 */     if (this.sealed) {
/* 208:203 */       throw onSeledMutation();
/* 209:    */     }
/* 210:    */     Object tmp;
/* 211:205 */     switch (index)
/* 212:    */     {
/* 213:    */     case 0: 
/* 214:207 */       if (N == 0)
/* 215:    */       {
/* 216:207 */         this.f0 = value;
/* 217:    */       }
/* 218:    */       else
/* 219:    */       {
/* 220:208 */         tmp = this.f0;this.f0 = value;value = tmp;
/* 221:    */       }
/* 222:    */       break;
/* 223:    */     case 1: 
/* 224:210 */       if (N == 1)
/* 225:    */       {
/* 226:210 */         this.f1 = value;
/* 227:    */       }
/* 228:    */       else
/* 229:    */       {
/* 230:211 */         tmp = this.f1;this.f1 = value;value = tmp;
/* 231:    */       }
/* 232:    */       break;
/* 233:    */     case 2: 
/* 234:213 */       if (N == 2)
/* 235:    */       {
/* 236:213 */         this.f2 = value;
/* 237:    */       }
/* 238:    */       else
/* 239:    */       {
/* 240:214 */         tmp = this.f2;this.f2 = value;value = tmp;
/* 241:    */       }
/* 242:    */       break;
/* 243:    */     case 3: 
/* 244:216 */       if (N == 3)
/* 245:    */       {
/* 246:216 */         this.f3 = value;
/* 247:    */       }
/* 248:    */       else
/* 249:    */       {
/* 250:217 */         tmp = this.f3;this.f3 = value;value = tmp;
/* 251:    */       }
/* 252:    */       break;
/* 253:    */     case 4: 
/* 254:219 */       if (N == 4)
/* 255:    */       {
/* 256:219 */         this.f4 = value;
/* 257:    */       }
/* 258:    */       else
/* 259:    */       {
/* 260:220 */         tmp = this.f4;this.f4 = value;value = tmp;
/* 261:    */         
/* 262:222 */         index = 5;
/* 263:    */       }
/* 264:    */       break;
/* 265:    */     default: 
/* 266:224 */       ensureCapacity(N + 1);
/* 267:225 */       if (index != N) {
/* 268:226 */         System.arraycopy(this.data, index - 5, this.data, index - 5 + 1, N - index);
/* 269:    */       }
/* 270:230 */       this.data[(index - 5)] = value;
/* 271:    */     }
/* 272:232 */     this.size = (N + 1);
/* 273:    */   }
/* 274:    */   
/* 275:    */   public final void remove(int index)
/* 276:    */   {
/* 277:237 */     int N = this.size;
/* 278:238 */     if ((0 > index) || (index >= N)) {
/* 279:238 */       throw onInvalidIndex(index, N);
/* 280:    */     }
/* 281:239 */     if (this.sealed) {
/* 282:239 */       throw onSeledMutation();
/* 283:    */     }
/* 284:240 */     N--;
/* 285:241 */     switch (index)
/* 286:    */     {
/* 287:    */     case 0: 
/* 288:243 */       if (N == 0) {
/* 289:243 */         this.f0 = null;
/* 290:    */       } else {
/* 291:244 */         this.f0 = this.f1;
/* 292:    */       }
/* 293:    */       break;
/* 294:    */     case 1: 
/* 295:246 */       if (N == 1) {
/* 296:246 */         this.f1 = null;
/* 297:    */       } else {
/* 298:247 */         this.f1 = this.f2;
/* 299:    */       }
/* 300:    */       break;
/* 301:    */     case 2: 
/* 302:249 */       if (N == 2) {
/* 303:249 */         this.f2 = null;
/* 304:    */       } else {
/* 305:250 */         this.f2 = this.f3;
/* 306:    */       }
/* 307:    */       break;
/* 308:    */     case 3: 
/* 309:252 */       if (N == 3) {
/* 310:252 */         this.f3 = null;
/* 311:    */       } else {
/* 312:253 */         this.f3 = this.f4;
/* 313:    */       }
/* 314:    */       break;
/* 315:    */     case 4: 
/* 316:255 */       if (N == 4)
/* 317:    */       {
/* 318:255 */         this.f4 = null;
/* 319:    */       }
/* 320:    */       else
/* 321:    */       {
/* 322:256 */         this.f4 = this.data[0];
/* 323:    */         
/* 324:258 */         index = 5;
/* 325:    */       }
/* 326:    */       break;
/* 327:    */     default: 
/* 328:260 */       if (index != N) {
/* 329:261 */         System.arraycopy(this.data, index - 5 + 1, this.data, index - 5, N - index);
/* 330:    */       }
/* 331:265 */       this.data[(N - 5)] = null;
/* 332:    */     }
/* 333:267 */     this.size = N;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public final void clear()
/* 337:    */   {
/* 338:272 */     if (this.sealed) {
/* 339:272 */       throw onSeledMutation();
/* 340:    */     }
/* 341:273 */     int N = this.size;
/* 342:274 */     for (int i = 0; i != N; i++) {
/* 343:275 */       setImpl(i, null);
/* 344:    */     }
/* 345:277 */     this.size = 0;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public final Object[] toArray()
/* 349:    */   {
/* 350:282 */     Object[] array = new Object[this.size];
/* 351:283 */     toArray(array, 0);
/* 352:284 */     return array;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public final void toArray(Object[] array)
/* 356:    */   {
/* 357:289 */     toArray(array, 0);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public final void toArray(Object[] array, int offset)
/* 361:    */   {
/* 362:294 */     int N = this.size;
/* 363:295 */     switch (N)
/* 364:    */     {
/* 365:    */     default: 
/* 366:297 */       System.arraycopy(this.data, 0, array, offset + 5, N - 5);
/* 367:    */     case 5: 
/* 368:299 */       array[(offset + 4)] = this.f4;
/* 369:    */     case 4: 
/* 370:300 */       array[(offset + 3)] = this.f3;
/* 371:    */     case 3: 
/* 372:301 */       array[(offset + 2)] = this.f2;
/* 373:    */     case 2: 
/* 374:302 */       array[(offset + 1)] = this.f1;
/* 375:    */     case 1: 
/* 376:303 */       array[(offset + 0)] = this.f0;
/* 377:    */     }
/* 378:    */   }
/* 379:    */   
/* 380:    */   private void ensureCapacity(int minimalCapacity)
/* 381:    */   {
/* 382:310 */     int required = minimalCapacity - 5;
/* 383:311 */     if (required <= 0) {
/* 384:311 */       throw new IllegalArgumentException();
/* 385:    */     }
/* 386:312 */     if (this.data == null)
/* 387:    */     {
/* 388:313 */       int alloc = 10;
/* 389:314 */       if (alloc < required) {
/* 390:315 */         alloc = required;
/* 391:    */       }
/* 392:317 */       this.data = new Object[alloc];
/* 393:    */     }
/* 394:    */     else
/* 395:    */     {
/* 396:319 */       int alloc = this.data.length;
/* 397:320 */       if (alloc < required)
/* 398:    */       {
/* 399:321 */         if (alloc <= 5) {
/* 400:322 */           alloc = 10;
/* 401:    */         } else {
/* 402:324 */           alloc *= 2;
/* 403:    */         }
/* 404:326 */         if (alloc < required) {
/* 405:327 */           alloc = required;
/* 406:    */         }
/* 407:329 */         Object[] tmp = new Object[alloc];
/* 408:330 */         if (this.size > 5) {
/* 409:331 */           System.arraycopy(this.data, 0, tmp, 0, this.size - 5);
/* 410:    */         }
/* 411:334 */         this.data = tmp;
/* 412:    */       }
/* 413:    */     }
/* 414:    */   }
/* 415:    */   
/* 416:    */   private static RuntimeException onInvalidIndex(int index, int upperBound)
/* 417:    */   {
/* 418:342 */     String msg = index + " ∉ [0, " + upperBound + ')';
/* 419:343 */     throw new IndexOutOfBoundsException(msg);
/* 420:    */   }
/* 421:    */   
/* 422:    */   private static RuntimeException onEmptyStackTopRead()
/* 423:    */   {
/* 424:348 */     throw new RuntimeException("Empty stack");
/* 425:    */   }
/* 426:    */   
/* 427:    */   private static RuntimeException onSeledMutation()
/* 428:    */   {
/* 429:353 */     throw new IllegalStateException("Attempt to modify sealed array");
/* 430:    */   }
/* 431:    */   
/* 432:    */   private void writeObject(ObjectOutputStream os)
/* 433:    */     throws IOException
/* 434:    */   {
/* 435:358 */     os.defaultWriteObject();
/* 436:359 */     int N = this.size;
/* 437:360 */     for (int i = 0; i != N; i++)
/* 438:    */     {
/* 439:361 */       Object obj = getImpl(i);
/* 440:362 */       os.writeObject(obj);
/* 441:    */     }
/* 442:    */   }
/* 443:    */   
/* 444:    */   private void readObject(ObjectInputStream is)
/* 445:    */     throws IOException, ClassNotFoundException
/* 446:    */   {
/* 447:369 */     is.defaultReadObject();
/* 448:370 */     int N = this.size;
/* 449:371 */     if (N > 5) {
/* 450:372 */       this.data = new Object[N - 5];
/* 451:    */     }
/* 452:374 */     for (int i = 0; i != N; i++)
/* 453:    */     {
/* 454:375 */       Object obj = is.readObject();
/* 455:376 */       setImpl(i, obj);
/* 456:    */     }
/* 457:    */   }
/* 458:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ObjArray
 * JD-Core Version:    0.7.0.1
 */
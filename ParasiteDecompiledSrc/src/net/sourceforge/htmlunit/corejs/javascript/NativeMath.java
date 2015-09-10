/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ final class NativeMath
/*   4:    */   extends IdScriptableObject
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -8838847185801131569L;
/*   7: 52 */   private static final Object MATH_TAG = "Math";
/*   8:    */   private static final int Id_toSource = 1;
/*   9:    */   private static final int Id_abs = 2;
/*  10:    */   private static final int Id_acos = 3;
/*  11:    */   private static final int Id_asin = 4;
/*  12:    */   private static final int Id_atan = 5;
/*  13:    */   private static final int Id_atan2 = 6;
/*  14:    */   private static final int Id_ceil = 7;
/*  15:    */   private static final int Id_cos = 8;
/*  16:    */   private static final int Id_exp = 9;
/*  17:    */   private static final int Id_floor = 10;
/*  18:    */   private static final int Id_log = 11;
/*  19:    */   private static final int Id_max = 12;
/*  20:    */   private static final int Id_min = 13;
/*  21:    */   private static final int Id_pow = 14;
/*  22:    */   private static final int Id_random = 15;
/*  23:    */   private static final int Id_round = 16;
/*  24:    */   private static final int Id_sin = 17;
/*  25:    */   private static final int Id_sqrt = 18;
/*  26:    */   private static final int Id_tan = 19;
/*  27:    */   private static final int LAST_METHOD_ID = 19;
/*  28:    */   private static final int Id_E = 20;
/*  29:    */   private static final int Id_PI = 21;
/*  30:    */   private static final int Id_LN10 = 22;
/*  31:    */   private static final int Id_LN2 = 23;
/*  32:    */   private static final int Id_LOG2E = 24;
/*  33:    */   private static final int Id_LOG10E = 25;
/*  34:    */   private static final int Id_SQRT1_2 = 26;
/*  35:    */   private static final int Id_SQRT2 = 27;
/*  36:    */   private static final int MAX_ID = 27;
/*  37:    */   
/*  38:    */   static void init(Scriptable scope, boolean sealed)
/*  39:    */   {
/*  40: 56 */     NativeMath obj = new NativeMath();
/*  41: 57 */     obj.activatePrototypeMap(27);
/*  42: 58 */     obj.setPrototype(getObjectPrototype(scope));
/*  43: 59 */     obj.setParentScope(scope);
/*  44: 60 */     if (sealed) {
/*  45: 60 */       obj.sealObject();
/*  46:    */     }
/*  47: 61 */     ScriptableObject.defineProperty(scope, "Math", obj, 2);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getClassName()
/*  51:    */   {
/*  52: 70 */     return "Math";
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void initPrototypeId(int id)
/*  56:    */   {
/*  57: 75 */     if (id <= 19)
/*  58:    */     {
/*  59:    */       int arity;
/*  60:    */       String name;
/*  61: 78 */       switch (id)
/*  62:    */       {
/*  63:    */       case 1: 
/*  64: 79 */         arity = 0;name = "toSource"; break;
/*  65:    */       case 2: 
/*  66: 80 */         arity = 1;name = "abs"; break;
/*  67:    */       case 3: 
/*  68: 81 */         arity = 1;name = "acos"; break;
/*  69:    */       case 4: 
/*  70: 82 */         arity = 1;name = "asin"; break;
/*  71:    */       case 5: 
/*  72: 83 */         arity = 1;name = "atan"; break;
/*  73:    */       case 6: 
/*  74: 84 */         arity = 2;name = "atan2"; break;
/*  75:    */       case 7: 
/*  76: 85 */         arity = 1;name = "ceil"; break;
/*  77:    */       case 8: 
/*  78: 86 */         arity = 1;name = "cos"; break;
/*  79:    */       case 9: 
/*  80: 87 */         arity = 1;name = "exp"; break;
/*  81:    */       case 10: 
/*  82: 88 */         arity = 1;name = "floor"; break;
/*  83:    */       case 11: 
/*  84: 89 */         arity = 1;name = "log"; break;
/*  85:    */       case 12: 
/*  86: 90 */         arity = 2;name = "max"; break;
/*  87:    */       case 13: 
/*  88: 91 */         arity = 2;name = "min"; break;
/*  89:    */       case 14: 
/*  90: 92 */         arity = 2;name = "pow"; break;
/*  91:    */       case 15: 
/*  92: 93 */         arity = 0;name = "random"; break;
/*  93:    */       case 16: 
/*  94: 94 */         arity = 1;name = "round"; break;
/*  95:    */       case 17: 
/*  96: 95 */         arity = 1;name = "sin"; break;
/*  97:    */       case 18: 
/*  98: 96 */         arity = 1;name = "sqrt"; break;
/*  99:    */       case 19: 
/* 100: 97 */         arity = 1;name = "tan"; break;
/* 101:    */       default: 
/* 102: 98 */         throw new IllegalStateException(String.valueOf(id));
/* 103:    */       }
/* 104:100 */       initPrototypeMethod(MATH_TAG, id, name, arity);
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:    */       double x;
/* 109:    */       String name;
/* 110:104 */       switch (id)
/* 111:    */       {
/* 112:    */       case 20: 
/* 113:105 */         x = 2.718281828459045D;name = "E"; break;
/* 114:    */       case 21: 
/* 115:106 */         x = 3.141592653589793D;name = "PI"; break;
/* 116:    */       case 22: 
/* 117:107 */         x = 2.302585092994046D;name = "LN10"; break;
/* 118:    */       case 23: 
/* 119:108 */         x = 0.6931471805599453D;name = "LN2"; break;
/* 120:    */       case 24: 
/* 121:109 */         x = 1.442695040888963D;name = "LOG2E"; break;
/* 122:    */       case 25: 
/* 123:110 */         x = 0.4342944819032518D;name = "LOG10E"; break;
/* 124:    */       case 26: 
/* 125:111 */         x = 0.7071067811865476D;name = "SQRT1_2"; break;
/* 126:    */       case 27: 
/* 127:112 */         x = 1.414213562373095D;name = "SQRT2"; break;
/* 128:    */       default: 
/* 129:113 */         throw new IllegalStateException(String.valueOf(id));
/* 130:    */       }
/* 131:115 */       initPrototypeValue(id, name, ScriptRuntime.wrapNumber(x), 7);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 136:    */   {
/* 137:124 */     if (!f.hasTag(MATH_TAG)) {
/* 138:125 */       return super.execIdCall(f, cx, scope, thisObj, args);
/* 139:    */     }
/* 140:128 */     int methodId = f.methodId();
/* 141:    */     double x;
/* 142:129 */     switch (methodId)
/* 143:    */     {
/* 144:    */     case 1: 
/* 145:131 */       return "Math";
/* 146:    */     case 2: 
/* 147:134 */       x = ScriptRuntime.toNumber(args, 0);
/* 148:    */       
/* 149:136 */       x = x < 0.0D ? -x : x == 0.0D ? 0.0D : x;
/* 150:137 */       break;
/* 151:    */     case 3: 
/* 152:    */     case 4: 
/* 153:141 */       x = ScriptRuntime.toNumber(args, 0);
/* 154:142 */       if ((x == x) && (-1.0D <= x) && (x <= 1.0D)) {
/* 155:143 */         x = methodId == 3 ? Math.acos(x) : Math.asin(x);
/* 156:    */       } else {
/* 157:145 */         x = (0.0D / 0.0D);
/* 158:    */       }
/* 159:147 */       break;
/* 160:    */     case 5: 
/* 161:150 */       x = ScriptRuntime.toNumber(args, 0);
/* 162:151 */       x = Math.atan(x);
/* 163:152 */       break;
/* 164:    */     case 6: 
/* 165:155 */       x = ScriptRuntime.toNumber(args, 0);
/* 166:156 */       x = Math.atan2(x, ScriptRuntime.toNumber(args, 1));
/* 167:157 */       break;
/* 168:    */     case 7: 
/* 169:160 */       x = ScriptRuntime.toNumber(args, 0);
/* 170:161 */       x = Math.ceil(x);
/* 171:162 */       break;
/* 172:    */     case 8: 
/* 173:165 */       x = ScriptRuntime.toNumber(args, 0);
/* 174:166 */       x = (x == (1.0D / 0.0D)) || (x == (-1.0D / 0.0D)) ? (0.0D / 0.0D) : Math.cos(x);
/* 175:    */       
/* 176:    */ 
/* 177:169 */       break;
/* 178:    */     case 9: 
/* 179:172 */       x = ScriptRuntime.toNumber(args, 0);
/* 180:173 */       x = x == (-1.0D / 0.0D) ? 0.0D : x == (1.0D / 0.0D) ? x : Math.exp(x);
/* 181:    */       
/* 182:    */ 
/* 183:176 */       break;
/* 184:    */     case 10: 
/* 185:179 */       x = ScriptRuntime.toNumber(args, 0);
/* 186:180 */       x = Math.floor(x);
/* 187:181 */       break;
/* 188:    */     case 11: 
/* 189:184 */       x = ScriptRuntime.toNumber(args, 0);
/* 190:    */       
/* 191:186 */       x = x < 0.0D ? (0.0D / 0.0D) : Math.log(x);
/* 192:187 */       break;
/* 193:    */     case 12: 
/* 194:    */     case 13: 
/* 195:191 */       x = methodId == 12 ? (-1.0D / 0.0D) : (1.0D / 0.0D);
/* 196:193 */       for (int i = 0; i != args.length; i++)
/* 197:    */       {
/* 198:194 */         double d = ScriptRuntime.toNumber(args[i]);
/* 199:195 */         if (d != d)
/* 200:    */         {
/* 201:196 */           x = d;
/* 202:197 */           break;
/* 203:    */         }
/* 204:199 */         if (methodId == 12) {
/* 205:201 */           x = Math.max(x, d);
/* 206:    */         } else {
/* 207:203 */           x = Math.min(x, d);
/* 208:    */         }
/* 209:    */       }
/* 210:206 */       break;
/* 211:    */     case 14: 
/* 212:209 */       x = ScriptRuntime.toNumber(args, 0);
/* 213:210 */       x = js_pow(x, ScriptRuntime.toNumber(args, 1));
/* 214:211 */       break;
/* 215:    */     case 15: 
/* 216:214 */       x = Math.random();
/* 217:215 */       break;
/* 218:    */     case 16: 
/* 219:218 */       x = ScriptRuntime.toNumber(args, 0);
/* 220:219 */       if ((x == x) && (x != (1.0D / 0.0D)) && (x != (-1.0D / 0.0D)))
/* 221:    */       {
/* 222:223 */         long l = Math.round(x);
/* 223:224 */         if (l != 0L) {
/* 224:225 */           x = l;
/* 225:228 */         } else if (x < 0.0D) {
/* 226:229 */           x = ScriptRuntime.negativeZero;
/* 227:230 */         } else if (x != 0.0D) {
/* 228:231 */           x = 0.0D;
/* 229:    */         }
/* 230:    */       }
/* 231:234 */       break;
/* 232:    */     case 17: 
/* 233:238 */       x = ScriptRuntime.toNumber(args, 0);
/* 234:239 */       x = (x == (1.0D / 0.0D)) || (x == (-1.0D / 0.0D)) ? (0.0D / 0.0D) : Math.sin(x);
/* 235:    */       
/* 236:    */ 
/* 237:242 */       break;
/* 238:    */     case 18: 
/* 239:245 */       x = ScriptRuntime.toNumber(args, 0);
/* 240:246 */       x = Math.sqrt(x);
/* 241:247 */       break;
/* 242:    */     case 19: 
/* 243:250 */       x = ScriptRuntime.toNumber(args, 0);
/* 244:251 */       x = Math.tan(x);
/* 245:252 */       break;
/* 246:    */     default: 
/* 247:254 */       throw new IllegalStateException(String.valueOf(methodId));
/* 248:    */     }
/* 249:256 */     return ScriptRuntime.wrapNumber(x);
/* 250:    */   }
/* 251:    */   
/* 252:    */   private double js_pow(double x, double y)
/* 253:    */   {
/* 254:    */     double result;
/* 255:    */     double result;
/* 256:262 */     if (y != y)
/* 257:    */     {
/* 258:264 */       result = y;
/* 259:    */     }
/* 260:    */     else
/* 261:    */     {
/* 262:    */       double result;
/* 263:265 */       if (y == 0.0D)
/* 264:    */       {
/* 265:267 */         result = 1.0D;
/* 266:    */       }
/* 267:    */       else
/* 268:    */       {
/* 269:    */         double result;
/* 270:268 */         if (x == 0.0D)
/* 271:    */         {
/* 272:    */           double result;
/* 273:270 */           if (1.0D / x > 0.0D)
/* 274:    */           {
/* 275:271 */             result = y > 0.0D ? 0.0D : (1.0D / 0.0D);
/* 276:    */           }
/* 277:    */           else
/* 278:    */           {
/* 279:274 */             long y_long = y;
/* 280:    */             double result;
/* 281:275 */             if ((y_long == y) && ((y_long & 1L) != 0L)) {
/* 282:276 */               result = y > 0.0D ? -0.0D : (-1.0D / 0.0D);
/* 283:    */             } else {
/* 284:278 */               result = y > 0.0D ? 0.0D : (1.0D / 0.0D);
/* 285:    */             }
/* 286:    */           }
/* 287:    */         }
/* 288:    */         else
/* 289:    */         {
/* 290:282 */           result = Math.pow(x, y);
/* 291:283 */           if (result != result) {
/* 292:286 */             if (y == (1.0D / 0.0D))
/* 293:    */             {
/* 294:287 */               if ((x < -1.0D) || (1.0D < x)) {
/* 295:288 */                 result = (1.0D / 0.0D);
/* 296:289 */               } else if ((-1.0D < x) && (x < 1.0D)) {
/* 297:290 */                 result = 0.0D;
/* 298:    */               }
/* 299:    */             }
/* 300:292 */             else if (y == (-1.0D / 0.0D))
/* 301:    */             {
/* 302:293 */               if ((x < -1.0D) || (1.0D < x)) {
/* 303:294 */                 result = 0.0D;
/* 304:295 */               } else if ((-1.0D < x) && (x < 1.0D)) {
/* 305:296 */                 result = (1.0D / 0.0D);
/* 306:    */               }
/* 307:    */             }
/* 308:298 */             else if (x == (1.0D / 0.0D))
/* 309:    */             {
/* 310:299 */               result = y > 0.0D ? (1.0D / 0.0D) : 0.0D;
/* 311:    */             }
/* 312:300 */             else if (x == (-1.0D / 0.0D))
/* 313:    */             {
/* 314:301 */               long y_long = y;
/* 315:302 */               if ((y_long == y) && ((y_long & 1L) != 0L)) {
/* 316:304 */                 result = y > 0.0D ? (-1.0D / 0.0D) : -0.0D;
/* 317:    */               } else {
/* 318:306 */                 result = y > 0.0D ? (1.0D / 0.0D) : 0.0D;
/* 319:    */               }
/* 320:    */             }
/* 321:    */           }
/* 322:    */         }
/* 323:    */       }
/* 324:    */     }
/* 325:311 */     return result;
/* 326:    */   }
/* 327:    */   
/* 328:    */   protected int findPrototypeId(String s)
/* 329:    */   {
/* 330:321 */     int id = 0;String X = null;
/* 331:    */     int c;
/* 332:322 */     switch (s.length())
/* 333:    */     {
/* 334:    */     case 1: 
/* 335:323 */       if (s.charAt(0) == 'E') {
/* 336:323 */         id = 20;
/* 337:    */       }
/* 338:    */       break;
/* 339:    */     case 2: 
/* 340:324 */       if ((s.charAt(0) == 'P') && (s.charAt(1) == 'I')) {
/* 341:324 */         id = 21;
/* 342:    */       }
/* 343:    */       break;
/* 344:    */     case 3: 
/* 345:325 */       switch (s.charAt(0))
/* 346:    */       {
/* 347:    */       case 'L': 
/* 348:326 */         if ((s.charAt(2) == '2') && (s.charAt(1) == 'N')) {
/* 349:326 */           id = 23;
/* 350:    */         }
/* 351:    */         break;
/* 352:    */       case 'a': 
/* 353:327 */         if ((s.charAt(2) == 's') && (s.charAt(1) == 'b')) {
/* 354:327 */           id = 2;
/* 355:    */         }
/* 356:    */         break;
/* 357:    */       case 'c': 
/* 358:328 */         if ((s.charAt(2) == 's') && (s.charAt(1) == 'o')) {
/* 359:328 */           id = 8;
/* 360:    */         }
/* 361:    */         break;
/* 362:    */       case 'e': 
/* 363:329 */         if ((s.charAt(2) == 'p') && (s.charAt(1) == 'x')) {
/* 364:329 */           id = 9;
/* 365:    */         }
/* 366:    */         break;
/* 367:    */       case 'l': 
/* 368:330 */         if ((s.charAt(2) == 'g') && (s.charAt(1) == 'o')) {
/* 369:330 */           id = 11;
/* 370:    */         }
/* 371:    */         break;
/* 372:    */       case 'm': 
/* 373:331 */         c = s.charAt(2);
/* 374:332 */         if (c == 110)
/* 375:    */         {
/* 376:332 */           if (s.charAt(1) == 'i')
/* 377:    */           {
/* 378:332 */             id = 13; return id;
/* 379:    */           }
/* 380:    */         }
/* 381:333 */         else if ((c == 120) && (s.charAt(1) == 'a')) {
/* 382:333 */           id = 12;
/* 383:    */         }
/* 384:    */         break;
/* 385:    */       case 'p': 
/* 386:335 */         if ((s.charAt(2) == 'w') && (s.charAt(1) == 'o')) {
/* 387:335 */           id = 14;
/* 388:    */         }
/* 389:    */         break;
/* 390:    */       case 's': 
/* 391:336 */         if ((s.charAt(2) == 'n') && (s.charAt(1) == 'i')) {
/* 392:336 */           id = 17;
/* 393:    */         }
/* 394:    */         break;
/* 395:    */       case 't': 
/* 396:337 */         if ((s.charAt(2) == 'n') && (s.charAt(1) == 'a'))
/* 397:    */         {
/* 398:337 */           id = 19; return id;
/* 399:    */         }
/* 400:    */         break;
/* 401:    */       }
/* 402:338 */       break;
/* 403:    */     case 4: 
/* 404:339 */       switch (s.charAt(1))
/* 405:    */       {
/* 406:    */       case 'N': 
/* 407:340 */         X = "LN10";id = 22; break;
/* 408:    */       case 'c': 
/* 409:341 */         X = "acos";id = 3; break;
/* 410:    */       case 'e': 
/* 411:342 */         X = "ceil";id = 7; break;
/* 412:    */       case 'q': 
/* 413:343 */         X = "sqrt";id = 18; break;
/* 414:    */       case 's': 
/* 415:344 */         X = "asin";id = 4; break;
/* 416:    */       case 't': 
/* 417:345 */         X = "atan";id = 5;
/* 418:    */       }
/* 419:346 */       break;
/* 420:    */     case 5: 
/* 421:347 */       switch (s.charAt(0))
/* 422:    */       {
/* 423:    */       case 'L': 
/* 424:348 */         X = "LOG2E";id = 24; break;
/* 425:    */       case 'S': 
/* 426:349 */         X = "SQRT2";id = 27; break;
/* 427:    */       case 'a': 
/* 428:350 */         X = "atan2";id = 6; break;
/* 429:    */       case 'f': 
/* 430:351 */         X = "floor";id = 10; break;
/* 431:    */       case 'r': 
/* 432:352 */         X = "round";id = 16;
/* 433:    */       }
/* 434:353 */       break;
/* 435:    */     case 6: 
/* 436:354 */       c = s.charAt(0);
/* 437:355 */       if (c == 76)
/* 438:    */       {
/* 439:355 */         X = "LOG10E";id = 25;
/* 440:    */       }
/* 441:356 */       else if (c == 114)
/* 442:    */       {
/* 443:356 */         X = "random";id = 15;
/* 444:    */       }
/* 445:    */       break;
/* 446:    */     case 7: 
/* 447:358 */       X = "SQRT1_2";id = 26; break;
/* 448:    */     case 8: 
/* 449:359 */       X = "toSource";id = 1; break;
/* 450:    */     }
/* 451:361 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 452:361 */       id = 0;
/* 453:    */     }
/* 454:364 */     return id;
/* 455:    */   }
/* 456:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeMath
 * JD-Core Version:    0.7.0.1
 */
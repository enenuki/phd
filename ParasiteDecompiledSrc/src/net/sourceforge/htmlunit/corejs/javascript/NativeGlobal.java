/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.xml.XMLLib;
/*   5:    */ 
/*   6:    */ public class NativeGlobal
/*   7:    */   implements Serializable, IdFunctionCall
/*   8:    */ {
/*   9:    */   static final long serialVersionUID = 6080442165748707530L;
/*  10:    */   private static final String URI_DECODE_RESERVED = ";/?:@&=+$,#";
/*  11:    */   
/*  12:    */   public static void init(Context cx, Scriptable scope, boolean sealed)
/*  13:    */   {
/*  14: 64 */     NativeGlobal obj = new NativeGlobal();
/*  15: 66 */     for (int id = 1; id <= 13; id++)
/*  16:    */     {
/*  17: 68 */       int arity = 1;
/*  18:    */       String name;
/*  19: 69 */       switch (id)
/*  20:    */       {
/*  21:    */       case 1: 
/*  22: 71 */         name = "decodeURI";
/*  23: 72 */         break;
/*  24:    */       case 2: 
/*  25: 74 */         name = "decodeURIComponent";
/*  26: 75 */         break;
/*  27:    */       case 3: 
/*  28: 77 */         name = "encodeURI";
/*  29: 78 */         break;
/*  30:    */       case 4: 
/*  31: 80 */         name = "encodeURIComponent";
/*  32: 81 */         break;
/*  33:    */       case 5: 
/*  34: 83 */         name = "escape";
/*  35: 84 */         break;
/*  36:    */       case 6: 
/*  37: 86 */         name = "eval";
/*  38: 87 */         break;
/*  39:    */       case 7: 
/*  40: 89 */         name = "isFinite";
/*  41: 90 */         break;
/*  42:    */       case 8: 
/*  43: 92 */         name = "isNaN";
/*  44: 93 */         break;
/*  45:    */       case 9: 
/*  46: 95 */         name = "isXMLName";
/*  47: 96 */         break;
/*  48:    */       case 10: 
/*  49: 98 */         name = "parseFloat";
/*  50: 99 */         break;
/*  51:    */       case 11: 
/*  52:101 */         name = "parseInt";
/*  53:102 */         arity = 2;
/*  54:103 */         break;
/*  55:    */       case 12: 
/*  56:105 */         name = "unescape";
/*  57:106 */         break;
/*  58:    */       case 13: 
/*  59:108 */         name = "uneval";
/*  60:109 */         break;
/*  61:    */       default: 
/*  62:111 */         throw Kit.codeBug();
/*  63:    */       }
/*  64:113 */       IdFunctionObject f = new IdFunctionObject(obj, FTAG, id, name, arity, scope);
/*  65:115 */       if (sealed) {
/*  66:116 */         f.sealObject();
/*  67:    */       }
/*  68:118 */       f.exportAsScopeProperty();
/*  69:    */     }
/*  70:121 */     ScriptableObject.defineProperty(scope, "NaN", ScriptRuntime.NaNobj, 7);
/*  71:    */     
/*  72:    */ 
/*  73:124 */     ScriptableObject.defineProperty(scope, "Infinity", ScriptRuntime.wrapNumber((1.0D / 0.0D)), 7);
/*  74:    */     
/*  75:    */ 
/*  76:    */ 
/*  77:128 */     ScriptableObject.defineProperty(scope, "undefined", Undefined.instance, 7);
/*  78:    */     
/*  79:    */ 
/*  80:    */ 
/*  81:132 */     String[] errorMethods = { "ConversionError", "EvalError", "RangeError", "ReferenceError", "SyntaxError", "TypeError", "URIError", "InternalError", "JavaException" };
/*  82:148 */     for (int i = 0; i < errorMethods.length; i++)
/*  83:    */     {
/*  84:149 */       String name = errorMethods[i];
/*  85:150 */       ScriptableObject errorProto = (ScriptableObject)ScriptRuntime.newObject(cx, scope, "Error", ScriptRuntime.emptyArgs);
/*  86:    */       
/*  87:    */ 
/*  88:153 */       errorProto.put("name", errorProto, name);
/*  89:154 */       errorProto.put("message", errorProto, "");
/*  90:155 */       IdFunctionObject ctor = new IdFunctionObject(obj, FTAG, 14, name, 1, scope);
/*  91:    */       
/*  92:    */ 
/*  93:158 */       ctor.markAsConstructor(errorProto);
/*  94:159 */       errorProto.put("constructor", errorProto, ctor);
/*  95:160 */       errorProto.setAttributes("constructor", 2);
/*  96:161 */       if (sealed)
/*  97:    */       {
/*  98:162 */         errorProto.sealObject();
/*  99:163 */         ctor.sealObject();
/* 100:    */       }
/* 101:165 */       ctor.exportAsScopeProperty();
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 106:    */   {
/* 107:172 */     if (f.hasTag(FTAG))
/* 108:    */     {
/* 109:173 */       int methodId = f.methodId();
/* 110:174 */       switch (methodId)
/* 111:    */       {
/* 112:    */       case 1: 
/* 113:    */       case 2: 
/* 114:177 */         String str = ScriptRuntime.toString(args, 0);
/* 115:178 */         return decode(str, methodId == 1);
/* 116:    */       case 3: 
/* 117:    */       case 4: 
/* 118:183 */         String str = ScriptRuntime.toString(args, 0);
/* 119:184 */         return encode(str, methodId == 3);
/* 120:    */       case 5: 
/* 121:188 */         return js_escape(args);
/* 122:    */       case 6: 
/* 123:191 */         return js_eval(cx, scope, args);
/* 124:    */       case 7: 
/* 125:    */         boolean result;
/* 126:    */         boolean result;
/* 127:195 */         if (args.length < 1)
/* 128:    */         {
/* 129:196 */           result = false;
/* 130:    */         }
/* 131:    */         else
/* 132:    */         {
/* 133:198 */           double d = ScriptRuntime.toNumber(args[0]);
/* 134:199 */           result = (d == d) && (d != (1.0D / 0.0D)) && (d != (-1.0D / 0.0D));
/* 135:    */         }
/* 136:203 */         return ScriptRuntime.wrapBoolean(result);
/* 137:    */       case 8: 
/* 138:    */         boolean result;
/* 139:    */         boolean result;
/* 140:209 */         if (args.length < 1)
/* 141:    */         {
/* 142:210 */           result = true;
/* 143:    */         }
/* 144:    */         else
/* 145:    */         {
/* 146:212 */           double d = ScriptRuntime.toNumber(args[0]);
/* 147:213 */           result = d != d;
/* 148:    */         }
/* 149:215 */         return ScriptRuntime.wrapBoolean(result);
/* 150:    */       case 9: 
/* 151:219 */         Object name = args.length == 0 ? Undefined.instance : args[0];
/* 152:    */         
/* 153:221 */         XMLLib xmlLib = XMLLib.extractFromScope(scope);
/* 154:222 */         return ScriptRuntime.wrapBoolean(xmlLib.isXMLName(cx, name));
/* 155:    */       case 10: 
/* 156:227 */         return js_parseFloat(args);
/* 157:    */       case 11: 
/* 158:230 */         return js_parseInt(args);
/* 159:    */       case 12: 
/* 160:233 */         return js_unescape(args);
/* 161:    */       case 13: 
/* 162:236 */         Object value = args.length != 0 ? args[0] : Undefined.instance;
/* 163:    */         
/* 164:238 */         return ScriptRuntime.uneval(cx, scope, value);
/* 165:    */       case 14: 
/* 166:244 */         return NativeError.make(cx, scope, f, args);
/* 167:    */       }
/* 168:    */     }
/* 169:247 */     throw f.unknown();
/* 170:    */   }
/* 171:    */   
/* 172:    */   private Object js_parseInt(Object[] args)
/* 173:    */   {
/* 174:254 */     String s = ScriptRuntime.toString(args, 0);
/* 175:255 */     int radix = ScriptRuntime.toInt32(args, 1);
/* 176:    */     
/* 177:257 */     int len = s.length();
/* 178:258 */     if (len == 0) {
/* 179:259 */       return ScriptRuntime.NaNobj;
/* 180:    */     }
/* 181:261 */     boolean negative = false;
/* 182:262 */     int start = 0;
/* 183:    */     char c;
/* 184:    */     do
/* 185:    */     {
/* 186:265 */       c = s.charAt(start);
/* 187:266 */       if (!ScriptRuntime.isStrWhiteSpaceChar(c)) {
/* 188:    */         break;
/* 189:    */       }
/* 190:268 */       start++;
/* 191:269 */     } while (start < len);
/* 192:271 */     if (c != '+')
/* 193:    */     {
/* 194:271 */       if ((negative = c == '-' ? 1 : 0) == 0) {}
/* 195:    */     }
/* 196:    */     else {
/* 197:272 */       start++;
/* 198:    */     }
/* 199:274 */     int NO_RADIX = -1;
/* 200:275 */     if (radix == 0)
/* 201:    */     {
/* 202:276 */       radix = -1;
/* 203:    */     }
/* 204:    */     else
/* 205:    */     {
/* 206:277 */       if ((radix < 2) || (radix > 36)) {
/* 207:278 */         return ScriptRuntime.NaNobj;
/* 208:    */       }
/* 209:279 */       if ((radix == 16) && (len - start > 1) && (s.charAt(start) == '0'))
/* 210:    */       {
/* 211:280 */         c = s.charAt(start + 1);
/* 212:281 */         if ((c == 'x') || (c == 'X')) {
/* 213:282 */           start += 2;
/* 214:    */         }
/* 215:    */       }
/* 216:    */     }
/* 217:285 */     if (radix == -1)
/* 218:    */     {
/* 219:286 */       radix = 10;
/* 220:287 */       if ((len - start > 1) && (s.charAt(start) == '0'))
/* 221:    */       {
/* 222:288 */         c = s.charAt(start + 1);
/* 223:289 */         if ((c == 'x') || (c == 'X'))
/* 224:    */         {
/* 225:290 */           radix = 16;
/* 226:291 */           start += 2;
/* 227:    */         }
/* 228:292 */         else if (('0' <= c) && (c <= '9'))
/* 229:    */         {
/* 230:293 */           radix = 8;
/* 231:294 */           start++;
/* 232:    */         }
/* 233:    */       }
/* 234:    */     }
/* 235:299 */     double d = ScriptRuntime.stringToNumber(s, start, radix);
/* 236:300 */     return ScriptRuntime.wrapNumber(negative ? -d : d);
/* 237:    */   }
/* 238:    */   
/* 239:    */   private Object js_parseFloat(Object[] args)
/* 240:    */   {
/* 241:310 */     if (args.length < 1) {
/* 242:311 */       return ScriptRuntime.NaNobj;
/* 243:    */     }
/* 244:313 */     String s = ScriptRuntime.toString(args[0]);
/* 245:314 */     int len = s.length();
/* 246:315 */     int start = 0;
/* 247:    */     char c;
/* 248:    */     for (;;)
/* 249:    */     {
/* 250:319 */       if (start == len) {
/* 251:320 */         return ScriptRuntime.NaNobj;
/* 252:    */       }
/* 253:322 */       c = s.charAt(start);
/* 254:323 */       if (!ScriptRuntime.isStrWhiteSpaceChar(c)) {
/* 255:    */         break;
/* 256:    */       }
/* 257:326 */       start++;
/* 258:    */     }
/* 259:329 */     int i = start;
/* 260:330 */     if ((c == '+') || (c == '-'))
/* 261:    */     {
/* 262:331 */       i++;
/* 263:332 */       if (i == len) {
/* 264:333 */         return ScriptRuntime.NaNobj;
/* 265:    */       }
/* 266:335 */       c = s.charAt(i);
/* 267:    */     }
/* 268:338 */     if (c == 'I')
/* 269:    */     {
/* 270:340 */       if ((i + 8 <= len) && (s.regionMatches(i, "Infinity", 0, 8)))
/* 271:    */       {
/* 272:    */         double d;
/* 273:    */         double d;
/* 274:342 */         if (s.charAt(start) == '-') {
/* 275:343 */           d = (-1.0D / 0.0D);
/* 276:    */         } else {
/* 277:345 */           d = (1.0D / 0.0D);
/* 278:    */         }
/* 279:347 */         return ScriptRuntime.wrapNumber(d);
/* 280:    */       }
/* 281:349 */       return ScriptRuntime.NaNobj;
/* 282:    */     }
/* 283:353 */     int decimal = -1;
/* 284:354 */     int exponent = -1;
/* 285:355 */     boolean exponentValid = false;
/* 286:356 */     for (; i < len; i++) {
/* 287:357 */       switch (s.charAt(i))
/* 288:    */       {
/* 289:    */       case '.': 
/* 290:359 */         if (decimal != -1) {
/* 291:    */           break label526;
/* 292:    */         }
/* 293:361 */         decimal = i;
/* 294:362 */         break;
/* 295:    */       case 'E': 
/* 296:    */       case 'e': 
/* 297:366 */         if (exponent != -1) {
/* 298:    */           break label526;
/* 299:    */         }
/* 300:368 */         if (i == len - 1) {
/* 301:    */           break label526;
/* 302:    */         }
/* 303:371 */         exponent = i;
/* 304:372 */         break;
/* 305:    */       case '+': 
/* 306:    */       case '-': 
/* 307:377 */         if (exponent != i - 1) {
/* 308:    */           break label526;
/* 309:    */         }
/* 310:379 */         if (i == len - 1) {
/* 311:380 */           i--;
/* 312:    */         }
/* 313:381 */         break;
/* 314:    */       case '0': 
/* 315:    */       case '1': 
/* 316:    */       case '2': 
/* 317:    */       case '3': 
/* 318:    */       case '4': 
/* 319:    */       case '5': 
/* 320:    */       case '6': 
/* 321:    */       case '7': 
/* 322:    */       case '8': 
/* 323:    */       case '9': 
/* 324:387 */         if (exponent != -1) {
/* 325:388 */           exponentValid = true;
/* 326:    */         }
/* 327:    */         break;
/* 328:    */       case ',': 
/* 329:    */       case '/': 
/* 330:    */       case ':': 
/* 331:    */       case ';': 
/* 332:    */       case '<': 
/* 333:    */       case '=': 
/* 334:    */       case '>': 
/* 335:    */       case '?': 
/* 336:    */       case '@': 
/* 337:    */       case 'A': 
/* 338:    */       case 'B': 
/* 339:    */       case 'C': 
/* 340:    */       case 'D': 
/* 341:    */       case 'F': 
/* 342:    */       case 'G': 
/* 343:    */       case 'H': 
/* 344:    */       case 'I': 
/* 345:    */       case 'J': 
/* 346:    */       case 'K': 
/* 347:    */       case 'L': 
/* 348:    */       case 'M': 
/* 349:    */       case 'N': 
/* 350:    */       case 'O': 
/* 351:    */       case 'P': 
/* 352:    */       case 'Q': 
/* 353:    */       case 'R': 
/* 354:    */       case 'S': 
/* 355:    */       case 'T': 
/* 356:    */       case 'U': 
/* 357:    */       case 'V': 
/* 358:    */       case 'W': 
/* 359:    */       case 'X': 
/* 360:    */       case 'Y': 
/* 361:    */       case 'Z': 
/* 362:    */       case '[': 
/* 363:    */       case '\\': 
/* 364:    */       case ']': 
/* 365:    */       case '^': 
/* 366:    */       case '_': 
/* 367:    */       case '`': 
/* 368:    */       case 'a': 
/* 369:    */       case 'b': 
/* 370:    */       case 'c': 
/* 371:    */       case 'd': 
/* 372:    */       default: 
/* 373:    */         break label526;
/* 374:    */       }
/* 375:    */     }
/* 376:    */     label526:
/* 377:397 */     if ((exponent != -1) && (!exponentValid)) {
/* 378:398 */       i = exponent;
/* 379:    */     }
/* 380:400 */     s = s.substring(start, i);
/* 381:    */     try
/* 382:    */     {
/* 383:402 */       return Double.valueOf(s);
/* 384:    */     }
/* 385:    */     catch (NumberFormatException ex) {}
/* 386:405 */     return ScriptRuntime.NaNobj;
/* 387:    */   }
/* 388:    */   
/* 389:    */   private Object js_escape(Object[] args)
/* 390:    */   {
/* 391:419 */     int URL_XALPHAS = 1;
/* 392:420 */     int URL_XPALPHAS = 2;
/* 393:421 */     int URL_PATH = 4;
/* 394:    */     
/* 395:423 */     String s = ScriptRuntime.toString(args, 0);
/* 396:    */     
/* 397:425 */     int mask = 7;
/* 398:426 */     if (args.length > 1)
/* 399:    */     {
/* 400:427 */       double d = ScriptRuntime.toNumber(args[1]);
/* 401:428 */       if ((d != d) || ((mask = (int)d) != d) || (0 != (mask & 0xFFFFFFF8))) {
/* 402:431 */         throw Context.reportRuntimeError0("msg.bad.esc.mask");
/* 403:    */       }
/* 404:    */     }
/* 405:435 */     StringBuffer sb = null;
/* 406:436 */     int k = 0;
/* 407:436 */     for (int L = s.length(); k != L; k++)
/* 408:    */     {
/* 409:437 */       int c = s.charAt(k);
/* 410:438 */       if ((mask != 0) && (((c >= 48) && (c <= 57)) || ((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122)) || (c == 64) || (c == 42) || (c == 95) || (c == 45) || (c == 46) || ((0 != (mask & 0x4)) && ((c == 47) || (c == 43)))))
/* 411:    */       {
/* 412:444 */         if (sb != null) {
/* 413:445 */           sb.append((char)c);
/* 414:    */         }
/* 415:    */       }
/* 416:    */       else
/* 417:    */       {
/* 418:448 */         if (sb == null)
/* 419:    */         {
/* 420:449 */           sb = new StringBuffer(L + 3);
/* 421:450 */           sb.append(s);
/* 422:451 */           sb.setLength(k);
/* 423:    */         }
/* 424:    */         int hexSize;
/* 425:    */         int hexSize;
/* 426:455 */         if (c < 256)
/* 427:    */         {
/* 428:456 */           if ((c == 32) && (mask == 2))
/* 429:    */           {
/* 430:457 */             sb.append('+');
/* 431:458 */             continue;
/* 432:    */           }
/* 433:460 */           sb.append('%');
/* 434:461 */           hexSize = 2;
/* 435:    */         }
/* 436:    */         else
/* 437:    */         {
/* 438:463 */           sb.append('%');
/* 439:464 */           sb.append('u');
/* 440:465 */           hexSize = 4;
/* 441:    */         }
/* 442:469 */         for (int shift = (hexSize - 1) * 4; shift >= 0; shift -= 4)
/* 443:    */         {
/* 444:470 */           int digit = 0xF & c >> shift;
/* 445:471 */           int hc = digit < 10 ? 48 + digit : 55 + digit;
/* 446:472 */           sb.append((char)hc);
/* 447:    */         }
/* 448:    */       }
/* 449:    */     }
/* 450:477 */     return sb == null ? s : sb.toString();
/* 451:    */   }
/* 452:    */   
/* 453:    */   private Object js_unescape(Object[] args)
/* 454:    */   {
/* 455:486 */     String s = ScriptRuntime.toString(args, 0);
/* 456:487 */     int firstEscapePos = s.indexOf('%');
/* 457:488 */     if (firstEscapePos >= 0)
/* 458:    */     {
/* 459:489 */       int L = s.length();
/* 460:490 */       char[] buf = s.toCharArray();
/* 461:491 */       int destination = firstEscapePos;
/* 462:492 */       for (int k = firstEscapePos; k != L;)
/* 463:    */       {
/* 464:493 */         char c = buf[k];
/* 465:494 */         k++;
/* 466:495 */         if ((c == '%') && (k != L))
/* 467:    */         {
/* 468:    */           int end;
/* 469:    */           int start;
/* 470:    */           int end;
/* 471:497 */           if (buf[k] == 'u')
/* 472:    */           {
/* 473:498 */             int start = k + 1;
/* 474:499 */             end = k + 5;
/* 475:    */           }
/* 476:    */           else
/* 477:    */           {
/* 478:501 */             start = k;
/* 479:502 */             end = k + 2;
/* 480:    */           }
/* 481:504 */           if (end <= L)
/* 482:    */           {
/* 483:505 */             int x = 0;
/* 484:506 */             for (int i = start; i != end; i++) {
/* 485:507 */               x = Kit.xDigitToInt(buf[i], x);
/* 486:    */             }
/* 487:509 */             if (x >= 0)
/* 488:    */             {
/* 489:510 */               c = (char)x;
/* 490:511 */               k = end;
/* 491:    */             }
/* 492:    */           }
/* 493:    */         }
/* 494:515 */         buf[destination] = c;
/* 495:516 */         destination++;
/* 496:    */       }
/* 497:518 */       s = new String(buf, 0, destination);
/* 498:    */     }
/* 499:520 */     return s;
/* 500:    */   }
/* 501:    */   
/* 502:    */   private Object js_eval(Context cx, Scriptable scope, Object[] args)
/* 503:    */   {
/* 504:529 */     Scriptable global = ScriptableObject.getTopLevelScope(scope);
/* 505:530 */     return ScriptRuntime.evalSpecial(cx, global, global, args, "eval code", 1);
/* 506:    */   }
/* 507:    */   
/* 508:    */   static boolean isEvalFunction(Object functionObj)
/* 509:    */   {
/* 510:535 */     if ((functionObj instanceof IdFunctionObject))
/* 511:    */     {
/* 512:536 */       IdFunctionObject function = (IdFunctionObject)functionObj;
/* 513:537 */       if ((function.hasTag(FTAG)) && (function.methodId() == 6)) {
/* 514:538 */         return true;
/* 515:    */       }
/* 516:    */     }
/* 517:541 */     return false;
/* 518:    */   }
/* 519:    */   
/* 520:    */   /**
/* 521:    */    * @deprecated
/* 522:    */    */
/* 523:    */   public static EcmaError constructError(Context cx, String error, String message, Scriptable scope)
/* 524:    */   {
/* 525:553 */     return ScriptRuntime.constructError(error, message);
/* 526:    */   }
/* 527:    */   
/* 528:    */   /**
/* 529:    */    * @deprecated
/* 530:    */    */
/* 531:    */   public static EcmaError constructError(Context cx, String error, String message, Scriptable scope, String sourceName, int lineNumber, int columnNumber, String lineSource)
/* 532:    */   {
/* 533:570 */     return ScriptRuntime.constructError(error, message, sourceName, lineNumber, lineSource, columnNumber);
/* 534:    */   }
/* 535:    */   
/* 536:    */   private static String encode(String str, boolean fullUri)
/* 537:    */   {
/* 538:583 */     byte[] utf8buf = null;
/* 539:584 */     StringBuffer sb = null;
/* 540:    */     
/* 541:586 */     int k = 0;
/* 542:586 */     for (int length = str.length(); k != length; k++)
/* 543:    */     {
/* 544:587 */       char C = str.charAt(k);
/* 545:588 */       if (encodeUnescaped(C, fullUri))
/* 546:    */       {
/* 547:589 */         if (sb != null) {
/* 548:590 */           sb.append(C);
/* 549:    */         }
/* 550:    */       }
/* 551:    */       else
/* 552:    */       {
/* 553:593 */         if (sb == null)
/* 554:    */         {
/* 555:594 */           sb = new StringBuffer(length + 3);
/* 556:595 */           sb.append(str);
/* 557:596 */           sb.setLength(k);
/* 558:597 */           utf8buf = new byte[6];
/* 559:    */         }
/* 560:599 */         if ((56320 <= C) && (C <= 57343)) {
/* 561:600 */           throw Context.reportRuntimeError0("msg.bad.uri");
/* 562:    */         }
/* 563:    */         int V;
/* 564:    */         int V;
/* 565:603 */         if ((C < 55296) || (56319 < C))
/* 566:    */         {
/* 567:604 */           V = C;
/* 568:    */         }
/* 569:    */         else
/* 570:    */         {
/* 571:606 */           k++;
/* 572:607 */           if (k == length) {
/* 573:608 */             throw Context.reportRuntimeError0("msg.bad.uri");
/* 574:    */           }
/* 575:610 */           char C2 = str.charAt(k);
/* 576:611 */           if ((56320 > C2) || (C2 > 57343)) {
/* 577:612 */             throw Context.reportRuntimeError0("msg.bad.uri");
/* 578:    */           }
/* 579:614 */           V = (C - 55296 << 10) + (C2 - 56320) + 65536;
/* 580:    */         }
/* 581:616 */         int L = oneUcs4ToUtf8Char(utf8buf, V);
/* 582:617 */         for (int j = 0; j < L; j++)
/* 583:    */         {
/* 584:618 */           int d = 0xFF & utf8buf[j];
/* 585:619 */           sb.append('%');
/* 586:620 */           sb.append(toHexChar(d >>> 4));
/* 587:621 */           sb.append(toHexChar(d & 0xF));
/* 588:    */         }
/* 589:    */       }
/* 590:    */     }
/* 591:625 */     return sb == null ? str : sb.toString();
/* 592:    */   }
/* 593:    */   
/* 594:    */   private static char toHexChar(int i)
/* 595:    */   {
/* 596:629 */     if (i >> 4 != 0) {
/* 597:629 */       Kit.codeBug();
/* 598:    */     }
/* 599:630 */     return (char)(i < 10 ? i + 48 : i - 10 + 65);
/* 600:    */   }
/* 601:    */   
/* 602:    */   private static int unHex(char c)
/* 603:    */   {
/* 604:634 */     if (('A' <= c) && (c <= 'F')) {
/* 605:635 */       return c - 'A' + 10;
/* 606:    */     }
/* 607:636 */     if (('a' <= c) && (c <= 'f')) {
/* 608:637 */       return c - 'a' + 10;
/* 609:    */     }
/* 610:638 */     if (('0' <= c) && (c <= '9')) {
/* 611:639 */       return c - '0';
/* 612:    */     }
/* 613:641 */     return -1;
/* 614:    */   }
/* 615:    */   
/* 616:    */   private static int unHex(char c1, char c2)
/* 617:    */   {
/* 618:646 */     int i1 = unHex(c1);
/* 619:647 */     int i2 = unHex(c2);
/* 620:648 */     if ((i1 >= 0) && (i2 >= 0)) {
/* 621:649 */       return i1 << 4 | i2;
/* 622:    */     }
/* 623:651 */     return -1;
/* 624:    */   }
/* 625:    */   
/* 626:    */   private static String decode(String str, boolean fullUri)
/* 627:    */   {
/* 628:655 */     char[] buf = null;
/* 629:656 */     int bufTop = 0;
/* 630:    */     
/* 631:658 */     int k = 0;
/* 632:658 */     for (int length = str.length(); k != length;)
/* 633:    */     {
/* 634:659 */       char C = str.charAt(k);
/* 635:660 */       if (C != '%')
/* 636:    */       {
/* 637:661 */         if (buf != null) {
/* 638:662 */           buf[(bufTop++)] = C;
/* 639:    */         }
/* 640:664 */         k++;
/* 641:    */       }
/* 642:    */       else
/* 643:    */       {
/* 644:666 */         if (buf == null)
/* 645:    */         {
/* 646:669 */           buf = new char[length];
/* 647:670 */           str.getChars(0, k, buf, 0);
/* 648:671 */           bufTop = k;
/* 649:    */         }
/* 650:673 */         int start = k;
/* 651:674 */         if (k + 3 > length) {
/* 652:675 */           throw Context.reportRuntimeError0("msg.bad.uri");
/* 653:    */         }
/* 654:676 */         int B = unHex(str.charAt(k + 1), str.charAt(k + 2));
/* 655:677 */         if (B < 0) {
/* 656:677 */           throw Context.reportRuntimeError0("msg.bad.uri");
/* 657:    */         }
/* 658:678 */         k += 3;
/* 659:679 */         if ((B & 0x80) == 0)
/* 660:    */         {
/* 661:680 */           C = (char)B;
/* 662:    */         }
/* 663:    */         else
/* 664:    */         {
/* 665:685 */           if ((B & 0xC0) == 128) {
/* 666:687 */             throw Context.reportRuntimeError0("msg.bad.uri");
/* 667:    */           }
/* 668:    */           int minUcs4Char;
/* 669:688 */           if ((B & 0x20) == 0)
/* 670:    */           {
/* 671:689 */             int utf8Tail = 1;int ucs4Char = B & 0x1F;
/* 672:690 */             minUcs4Char = 128;
/* 673:    */           }
/* 674:    */           else
/* 675:    */           {
/* 676:    */             int minUcs4Char;
/* 677:691 */             if ((B & 0x10) == 0)
/* 678:    */             {
/* 679:692 */               int utf8Tail = 2;int ucs4Char = B & 0xF;
/* 680:693 */               minUcs4Char = 2048;
/* 681:    */             }
/* 682:    */             else
/* 683:    */             {
/* 684:    */               int minUcs4Char;
/* 685:694 */               if ((B & 0x8) == 0)
/* 686:    */               {
/* 687:695 */                 int utf8Tail = 3;int ucs4Char = B & 0x7;
/* 688:696 */                 minUcs4Char = 65536;
/* 689:    */               }
/* 690:    */               else
/* 691:    */               {
/* 692:    */                 int minUcs4Char;
/* 693:697 */                 if ((B & 0x4) == 0)
/* 694:    */                 {
/* 695:698 */                   int utf8Tail = 4;int ucs4Char = B & 0x3;
/* 696:699 */                   minUcs4Char = 2097152;
/* 697:    */                 }
/* 698:    */                 else
/* 699:    */                 {
/* 700:    */                   int minUcs4Char;
/* 701:700 */                   if ((B & 0x2) == 0)
/* 702:    */                   {
/* 703:701 */                     int utf8Tail = 5;int ucs4Char = B & 0x1;
/* 704:702 */                     minUcs4Char = 67108864;
/* 705:    */                   }
/* 706:    */                   else
/* 707:    */                   {
/* 708:705 */                     throw Context.reportRuntimeError0("msg.bad.uri");
/* 709:    */                   }
/* 710:    */                 }
/* 711:    */               }
/* 712:    */             }
/* 713:    */           }
/* 714:    */           int minUcs4Char;
/* 715:    */           int ucs4Char;
/* 716:    */           int utf8Tail;
/* 717:707 */           if (k + 3 * utf8Tail > length) {
/* 718:708 */             throw Context.reportRuntimeError0("msg.bad.uri");
/* 719:    */           }
/* 720:709 */           for (int j = 0; j != utf8Tail; j++)
/* 721:    */           {
/* 722:710 */             if (str.charAt(k) != '%') {
/* 723:711 */               throw Context.reportRuntimeError0("msg.bad.uri");
/* 724:    */             }
/* 725:712 */             B = unHex(str.charAt(k + 1), str.charAt(k + 2));
/* 726:713 */             if ((B < 0) || ((B & 0xC0) != 128)) {
/* 727:714 */               throw Context.reportRuntimeError0("msg.bad.uri");
/* 728:    */             }
/* 729:715 */             ucs4Char = ucs4Char << 6 | B & 0x3F;
/* 730:716 */             k += 3;
/* 731:    */           }
/* 732:719 */           if ((ucs4Char < minUcs4Char) || (ucs4Char == 65534) || (ucs4Char == 65535)) {
/* 733:722 */             ucs4Char = 65533;
/* 734:    */           }
/* 735:724 */           if (ucs4Char >= 65536)
/* 736:    */           {
/* 737:725 */             ucs4Char -= 65536;
/* 738:726 */             if (ucs4Char > 1048575) {
/* 739:727 */               throw Context.reportRuntimeError0("msg.bad.uri");
/* 740:    */             }
/* 741:728 */             char H = (char)((ucs4Char >>> 10) + 55296);
/* 742:729 */             C = (char)((ucs4Char & 0x3FF) + 56320);
/* 743:730 */             buf[(bufTop++)] = H;
/* 744:    */           }
/* 745:    */           else
/* 746:    */           {
/* 747:732 */             C = (char)ucs4Char;
/* 748:    */           }
/* 749:    */         }
/* 750:735 */         if ((fullUri) && (";/?:@&=+$,#".indexOf(C) >= 0)) {
/* 751:736 */           for (int x = start; x != k; x++) {
/* 752:737 */             buf[(bufTop++)] = str.charAt(x);
/* 753:    */           }
/* 754:    */         } else {
/* 755:740 */           buf[(bufTop++)] = C;
/* 756:    */         }
/* 757:    */       }
/* 758:    */     }
/* 759:744 */     return buf == null ? str : new String(buf, 0, bufTop);
/* 760:    */   }
/* 761:    */   
/* 762:    */   private static boolean encodeUnescaped(char c, boolean fullUri)
/* 763:    */   {
/* 764:748 */     if ((('A' <= c) && (c <= 'Z')) || (('a' <= c) && (c <= 'z')) || (('0' <= c) && (c <= '9'))) {
/* 765:751 */       return true;
/* 766:    */     }
/* 767:753 */     if ("-_.!~*'()".indexOf(c) >= 0) {
/* 768:754 */       return true;
/* 769:    */     }
/* 770:755 */     if (fullUri) {
/* 771:756 */       return ";/?:@&=+$,#".indexOf(c) >= 0;
/* 772:    */     }
/* 773:758 */     return false;
/* 774:    */   }
/* 775:    */   
/* 776:    */   private static int oneUcs4ToUtf8Char(byte[] utf8Buffer, int ucs4Char)
/* 777:    */   {
/* 778:767 */     int utf8Length = 1;
/* 779:770 */     if ((ucs4Char & 0xFFFFFF80) == 0)
/* 780:    */     {
/* 781:771 */       utf8Buffer[0] = ((byte)ucs4Char);
/* 782:    */     }
/* 783:    */     else
/* 784:    */     {
/* 785:774 */       int a = ucs4Char >>> 11;
/* 786:775 */       utf8Length = 2;
/* 787:776 */       while (a != 0)
/* 788:    */       {
/* 789:777 */         a >>>= 5;
/* 790:778 */         utf8Length++;
/* 791:    */       }
/* 792:780 */       int i = utf8Length;
/* 793:    */       for (;;)
/* 794:    */       {
/* 795:781 */         i--;
/* 796:781 */         if (i <= 0) {
/* 797:    */           break;
/* 798:    */         }
/* 799:782 */         utf8Buffer[i] = ((byte)(ucs4Char & 0x3F | 0x80));
/* 800:783 */         ucs4Char >>>= 6;
/* 801:    */       }
/* 802:785 */       utf8Buffer[0] = ((byte)(256 - (1 << 8 - utf8Length) + ucs4Char));
/* 803:    */     }
/* 804:787 */     return utf8Length;
/* 805:    */   }
/* 806:    */   
/* 807:790 */   private static final Object FTAG = "Global";
/* 808:    */   private static final int Id_decodeURI = 1;
/* 809:    */   private static final int Id_decodeURIComponent = 2;
/* 810:    */   private static final int Id_encodeURI = 3;
/* 811:    */   private static final int Id_encodeURIComponent = 4;
/* 812:    */   private static final int Id_escape = 5;
/* 813:    */   private static final int Id_eval = 6;
/* 814:    */   private static final int Id_isFinite = 7;
/* 815:    */   private static final int Id_isNaN = 8;
/* 816:    */   private static final int Id_isXMLName = 9;
/* 817:    */   private static final int Id_parseFloat = 10;
/* 818:    */   private static final int Id_parseInt = 11;
/* 819:    */   private static final int Id_unescape = 12;
/* 820:    */   private static final int Id_uneval = 13;
/* 821:    */   private static final int LAST_SCOPE_FUNCTION_ID = 13;
/* 822:    */   private static final int Id_new_CommonError = 14;
/* 823:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeGlobal
 * JD-Core Version:    0.7.0.1
 */
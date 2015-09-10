/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.text.Collator;
/*   4:    */ 
/*   5:    */ final class NativeString
/*   6:    */   extends IdScriptableObject
/*   7:    */ {
/*   8:    */   static final long serialVersionUID = 920268368584188687L;
/*   9: 62 */   private static final Object STRING_TAG = "String";
/*  10:    */   private static final int Id_length = 1;
/*  11:    */   private static final int MAX_INSTANCE_ID = 1;
/*  12:    */   private static final int ConstructorId_fromCharCode = -1;
/*  13:    */   private static final int Id_constructor = 1;
/*  14:    */   private static final int Id_toString = 2;
/*  15:    */   private static final int Id_toSource = 3;
/*  16:    */   private static final int Id_valueOf = 4;
/*  17:    */   private static final int Id_charAt = 5;
/*  18:    */   private static final int Id_charCodeAt = 6;
/*  19:    */   private static final int Id_indexOf = 7;
/*  20:    */   private static final int Id_lastIndexOf = 8;
/*  21:    */   private static final int Id_split = 9;
/*  22:    */   private static final int Id_substring = 10;
/*  23:    */   private static final int Id_toLowerCase = 11;
/*  24:    */   private static final int Id_toUpperCase = 12;
/*  25:    */   private static final int Id_substr = 13;
/*  26:    */   private static final int Id_concat = 14;
/*  27:    */   private static final int Id_slice = 15;
/*  28:    */   private static final int Id_bold = 16;
/*  29:    */   private static final int Id_italics = 17;
/*  30:    */   private static final int Id_fixed = 18;
/*  31:    */   private static final int Id_strike = 19;
/*  32:    */   private static final int Id_small = 20;
/*  33:    */   private static final int Id_big = 21;
/*  34:    */   private static final int Id_blink = 22;
/*  35:    */   private static final int Id_sup = 23;
/*  36:    */   private static final int Id_sub = 24;
/*  37:    */   private static final int Id_fontsize = 25;
/*  38:    */   private static final int Id_fontcolor = 26;
/*  39:    */   private static final int Id_link = 27;
/*  40:    */   private static final int Id_anchor = 28;
/*  41:    */   private static final int Id_equals = 29;
/*  42:    */   private static final int Id_equalsIgnoreCase = 30;
/*  43:    */   private static final int Id_match = 31;
/*  44:    */   private static final int Id_search = 32;
/*  45:    */   private static final int Id_replace = 33;
/*  46:    */   private static final int Id_localeCompare = 34;
/*  47:    */   private static final int Id_toLocaleLowerCase = 35;
/*  48:    */   private static final int Id_toLocaleUpperCase = 36;
/*  49:    */   private static final int Id_trim = 37;
/*  50:    */   private static final int MAX_PROTOTYPE_ID = 37;
/*  51:    */   private static final int ConstructorId_charAt = -5;
/*  52:    */   private static final int ConstructorId_charCodeAt = -6;
/*  53:    */   private static final int ConstructorId_indexOf = -7;
/*  54:    */   private static final int ConstructorId_lastIndexOf = -8;
/*  55:    */   private static final int ConstructorId_split = -9;
/*  56:    */   private static final int ConstructorId_substring = -10;
/*  57:    */   private static final int ConstructorId_toLowerCase = -11;
/*  58:    */   private static final int ConstructorId_toUpperCase = -12;
/*  59:    */   private static final int ConstructorId_substr = -13;
/*  60:    */   private static final int ConstructorId_concat = -14;
/*  61:    */   private static final int ConstructorId_slice = -15;
/*  62:    */   private static final int ConstructorId_equalsIgnoreCase = -30;
/*  63:    */   private static final int ConstructorId_match = -31;
/*  64:    */   private static final int ConstructorId_search = -32;
/*  65:    */   private static final int ConstructorId_replace = -33;
/*  66:    */   private static final int ConstructorId_localeCompare = -34;
/*  67:    */   private static final int ConstructorId_toLocaleLowerCase = -35;
/*  68:    */   private String string;
/*  69:    */   
/*  70:    */   static void init(Scriptable scope, boolean sealed)
/*  71:    */   {
/*  72: 66 */     NativeString obj = new NativeString("");
/*  73: 67 */     obj.exportAsJSClass(37, scope, sealed);
/*  74:    */   }
/*  75:    */   
/*  76:    */   NativeString(String s)
/*  77:    */   {
/*  78: 71 */     this.string = s;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getClassName()
/*  82:    */   {
/*  83: 76 */     return "String";
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected int getMaxInstanceId()
/*  87:    */   {
/*  88: 86 */     return 1;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected int findInstanceIdInfo(String s)
/*  92:    */   {
/*  93: 92 */     if (s.equals("length")) {
/*  94: 93 */       return instanceIdInfo(7, 1);
/*  95:    */     }
/*  96: 95 */     return super.findInstanceIdInfo(s);
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected String getInstanceIdName(int id)
/* 100:    */   {
/* 101:101 */     if (id == 1) {
/* 102:101 */       return "length";
/* 103:    */     }
/* 104:102 */     return super.getInstanceIdName(id);
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected Object getInstanceIdValue(int id)
/* 108:    */   {
/* 109:108 */     if (id == 1) {
/* 110:109 */       return ScriptRuntime.wrapInt(this.string.length());
/* 111:    */     }
/* 112:111 */     return super.getInstanceIdValue(id);
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected void fillConstructorProperties(IdFunctionObject ctor)
/* 116:    */   {
/* 117:117 */     addIdFunctionProperty(ctor, STRING_TAG, -1, "fromCharCode", 1);
/* 118:    */     
/* 119:119 */     addIdFunctionProperty(ctor, STRING_TAG, -5, "charAt", 2);
/* 120:    */     
/* 121:121 */     addIdFunctionProperty(ctor, STRING_TAG, -6, "charCodeAt", 2);
/* 122:    */     
/* 123:123 */     addIdFunctionProperty(ctor, STRING_TAG, -7, "indexOf", 2);
/* 124:    */     
/* 125:125 */     addIdFunctionProperty(ctor, STRING_TAG, -8, "lastIndexOf", 2);
/* 126:    */     
/* 127:127 */     addIdFunctionProperty(ctor, STRING_TAG, -9, "split", 3);
/* 128:    */     
/* 129:129 */     addIdFunctionProperty(ctor, STRING_TAG, -10, "substring", 3);
/* 130:    */     
/* 131:131 */     addIdFunctionProperty(ctor, STRING_TAG, -11, "toLowerCase", 1);
/* 132:    */     
/* 133:133 */     addIdFunctionProperty(ctor, STRING_TAG, -12, "toUpperCase", 1);
/* 134:    */     
/* 135:135 */     addIdFunctionProperty(ctor, STRING_TAG, -13, "substr", 3);
/* 136:    */     
/* 137:137 */     addIdFunctionProperty(ctor, STRING_TAG, -14, "concat", 2);
/* 138:    */     
/* 139:139 */     addIdFunctionProperty(ctor, STRING_TAG, -15, "slice", 3);
/* 140:    */     
/* 141:141 */     addIdFunctionProperty(ctor, STRING_TAG, -30, "equalsIgnoreCase", 2);
/* 142:    */     
/* 143:143 */     addIdFunctionProperty(ctor, STRING_TAG, -31, "match", 2);
/* 144:    */     
/* 145:145 */     addIdFunctionProperty(ctor, STRING_TAG, -32, "search", 2);
/* 146:    */     
/* 147:147 */     addIdFunctionProperty(ctor, STRING_TAG, -33, "replace", 2);
/* 148:    */     
/* 149:149 */     addIdFunctionProperty(ctor, STRING_TAG, -34, "localeCompare", 2);
/* 150:    */     
/* 151:151 */     addIdFunctionProperty(ctor, STRING_TAG, -35, "toLocaleLowerCase", 1);
/* 152:    */     
/* 153:153 */     super.fillConstructorProperties(ctor);
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected void initPrototypeId(int id)
/* 157:    */   {
/* 158:    */     int arity;
/* 159:    */     String s;
/* 160:161 */     switch (id)
/* 161:    */     {
/* 162:    */     case 1: 
/* 163:162 */       arity = 1;s = "constructor"; break;
/* 164:    */     case 2: 
/* 165:163 */       arity = 0;s = "toString"; break;
/* 166:    */     case 3: 
/* 167:164 */       arity = 0;s = "toSource"; break;
/* 168:    */     case 4: 
/* 169:165 */       arity = 0;s = "valueOf"; break;
/* 170:    */     case 5: 
/* 171:166 */       arity = 1;s = "charAt"; break;
/* 172:    */     case 6: 
/* 173:167 */       arity = 1;s = "charCodeAt"; break;
/* 174:    */     case 7: 
/* 175:168 */       arity = 1;s = "indexOf"; break;
/* 176:    */     case 8: 
/* 177:169 */       arity = 1;s = "lastIndexOf"; break;
/* 178:    */     case 9: 
/* 179:170 */       arity = 2;s = "split"; break;
/* 180:    */     case 10: 
/* 181:171 */       arity = 2;s = "substring"; break;
/* 182:    */     case 11: 
/* 183:172 */       arity = 0;s = "toLowerCase"; break;
/* 184:    */     case 12: 
/* 185:173 */       arity = 0;s = "toUpperCase"; break;
/* 186:    */     case 13: 
/* 187:174 */       arity = 2;s = "substr"; break;
/* 188:    */     case 14: 
/* 189:175 */       arity = 1;s = "concat"; break;
/* 190:    */     case 15: 
/* 191:176 */       arity = 2;s = "slice"; break;
/* 192:    */     case 16: 
/* 193:177 */       arity = 0;s = "bold"; break;
/* 194:    */     case 17: 
/* 195:178 */       arity = 0;s = "italics"; break;
/* 196:    */     case 18: 
/* 197:179 */       arity = 0;s = "fixed"; break;
/* 198:    */     case 19: 
/* 199:180 */       arity = 0;s = "strike"; break;
/* 200:    */     case 20: 
/* 201:181 */       arity = 0;s = "small"; break;
/* 202:    */     case 21: 
/* 203:182 */       arity = 0;s = "big"; break;
/* 204:    */     case 22: 
/* 205:183 */       arity = 0;s = "blink"; break;
/* 206:    */     case 23: 
/* 207:184 */       arity = 0;s = "sup"; break;
/* 208:    */     case 24: 
/* 209:185 */       arity = 0;s = "sub"; break;
/* 210:    */     case 25: 
/* 211:186 */       arity = 0;s = "fontsize"; break;
/* 212:    */     case 26: 
/* 213:187 */       arity = 0;s = "fontcolor"; break;
/* 214:    */     case 27: 
/* 215:188 */       arity = 0;s = "link"; break;
/* 216:    */     case 28: 
/* 217:189 */       arity = 0;s = "anchor"; break;
/* 218:    */     case 29: 
/* 219:190 */       arity = 1;s = "equals"; break;
/* 220:    */     case 30: 
/* 221:191 */       arity = 1;s = "equalsIgnoreCase"; break;
/* 222:    */     case 31: 
/* 223:192 */       arity = 1;s = "match"; break;
/* 224:    */     case 32: 
/* 225:193 */       arity = 1;s = "search"; break;
/* 226:    */     case 33: 
/* 227:194 */       arity = 1;s = "replace"; break;
/* 228:    */     case 34: 
/* 229:195 */       arity = 1;s = "localeCompare"; break;
/* 230:    */     case 35: 
/* 231:196 */       arity = 0;s = "toLocaleLowerCase"; break;
/* 232:    */     case 36: 
/* 233:197 */       arity = 0;s = "toLocaleUpperCase"; break;
/* 234:    */     case 37: 
/* 235:198 */       arity = 0;s = "trim"; break;
/* 236:    */     default: 
/* 237:199 */       throw new IllegalArgumentException(String.valueOf(id));
/* 238:    */     }
/* 239:201 */     initPrototypeMethod(STRING_TAG, id, s, arity);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 243:    */   {
/* 244:208 */     if (!f.hasTag(STRING_TAG)) {
/* 245:209 */       return super.execIdCall(f, cx, scope, thisObj, args);
/* 246:    */     }
/* 247:211 */     int id = f.methodId();
/* 248:    */     for (;;)
/* 249:    */     {
/* 250:214 */       switch (id)
/* 251:    */       {
/* 252:    */       case -35: 
/* 253:    */       case -34: 
/* 254:    */       case -33: 
/* 255:    */       case -32: 
/* 256:    */       case -31: 
/* 257:    */       case -30: 
/* 258:    */       case -15: 
/* 259:    */       case -14: 
/* 260:    */       case -13: 
/* 261:    */       case -12: 
/* 262:    */       case -11: 
/* 263:    */       case -10: 
/* 264:    */       case -9: 
/* 265:    */       case -8: 
/* 266:    */       case -7: 
/* 267:    */       case -6: 
/* 268:    */       case -5: 
/* 269:232 */         if (args.length > 0)
/* 270:    */         {
/* 271:233 */           thisObj = ScriptRuntime.toObject(scope, ScriptRuntime.toString(args[0]));
/* 272:    */           
/* 273:235 */           Object[] newArgs = new Object[args.length - 1];
/* 274:236 */           for (int i = 0; i < newArgs.length; i++) {
/* 275:237 */             newArgs[i] = args[(i + 1)];
/* 276:    */           }
/* 277:238 */           args = newArgs;
/* 278:    */         }
/* 279:    */         else
/* 280:    */         {
/* 281:240 */           thisObj = ScriptRuntime.toObject(scope, ScriptRuntime.toString(thisObj));
/* 282:    */         }
/* 283:243 */         id = -id;
/* 284:    */       }
/* 285:    */     }
/* 286:248 */     int N = args.length;
/* 287:249 */     if (N < 1) {
/* 288:250 */       return "";
/* 289:    */     }
/* 290:251 */     StringBuffer sb = new StringBuffer(N);
/* 291:252 */     for (int i = 0; i != N; i++) {
/* 292:253 */       sb.append(ScriptRuntime.toUint16(args[i]));
/* 293:    */     }
/* 294:255 */     return sb.toString();
/* 295:    */     
/* 296:    */ 
/* 297:    */ 
/* 298:259 */     String s = args.length >= 1 ? ScriptRuntime.toString(args[0]) : "";
/* 299:261 */     if (thisObj == null) {
/* 300:263 */       return new NativeString(s);
/* 301:    */     }
/* 302:266 */     return s;
/* 303:    */     
/* 304:    */ 
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:272 */     return realThis(thisObj, f).string;
/* 309:    */     
/* 310:    */ 
/* 311:275 */     String s = realThis(thisObj, f).string;
/* 312:276 */     return "(new String(\"" + ScriptRuntime.escapeString(s) + "\"))";
/* 313:    */     
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:282 */     String target = ScriptRuntime.toString(thisObj);
/* 319:283 */     double pos = ScriptRuntime.toInteger(args, 0);
/* 320:284 */     if ((pos < 0.0D) || (pos >= target.length()))
/* 321:    */     {
/* 322:285 */       if (id == 5) {
/* 323:285 */         return "";
/* 324:    */       }
/* 325:286 */       return ScriptRuntime.NaNobj;
/* 326:    */     }
/* 327:288 */     char c = target.charAt((int)pos);
/* 328:289 */     if (id == 5) {
/* 329:289 */       return String.valueOf(c);
/* 330:    */     }
/* 331:290 */     return ScriptRuntime.wrapInt(c);
/* 332:    */     
/* 333:    */ 
/* 334:    */ 
/* 335:294 */     return ScriptRuntime.wrapInt(js_indexOf(ScriptRuntime.toString(thisObj), args));
/* 336:    */     
/* 337:    */ 
/* 338:    */ 
/* 339:298 */     return ScriptRuntime.wrapInt(js_lastIndexOf(ScriptRuntime.toString(thisObj), args));
/* 340:    */     
/* 341:    */ 
/* 342:    */ 
/* 343:302 */     return ScriptRuntime.checkRegExpProxy(cx).js_split(cx, scope, ScriptRuntime.toString(thisObj), args);
/* 344:    */     
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:307 */     return js_substring(cx, ScriptRuntime.toString(thisObj), args);
/* 349:    */     
/* 350:    */ 
/* 351:    */ 
/* 352:311 */     return ScriptRuntime.toString(thisObj).toLowerCase(ScriptRuntime.ROOT_LOCALE);
/* 353:    */     
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:316 */     return ScriptRuntime.toString(thisObj).toUpperCase(ScriptRuntime.ROOT_LOCALE);
/* 358:    */     
/* 359:    */ 
/* 360:    */ 
/* 361:320 */     return js_substr(ScriptRuntime.toString(thisObj), args);
/* 362:    */     
/* 363:    */ 
/* 364:323 */     return js_concat(ScriptRuntime.toString(thisObj), args);
/* 365:    */     
/* 366:    */ 
/* 367:326 */     return js_slice(ScriptRuntime.toString(thisObj), args);
/* 368:    */     
/* 369:    */ 
/* 370:329 */     return tagify(thisObj, "b", null, null);
/* 371:    */     
/* 372:    */ 
/* 373:332 */     return tagify(thisObj, "i", null, null);
/* 374:    */     
/* 375:    */ 
/* 376:335 */     return tagify(thisObj, "tt", null, null);
/* 377:    */     
/* 378:    */ 
/* 379:338 */     return tagify(thisObj, "strike", null, null);
/* 380:    */     
/* 381:    */ 
/* 382:341 */     return tagify(thisObj, "small", null, null);
/* 383:    */     
/* 384:    */ 
/* 385:344 */     return tagify(thisObj, "big", null, null);
/* 386:    */     
/* 387:    */ 
/* 388:347 */     return tagify(thisObj, "blink", null, null);
/* 389:    */     
/* 390:    */ 
/* 391:350 */     return tagify(thisObj, "sup", null, null);
/* 392:    */     
/* 393:    */ 
/* 394:353 */     return tagify(thisObj, "sub", null, null);
/* 395:    */     
/* 396:    */ 
/* 397:356 */     return tagify(thisObj, "font", "size", args);
/* 398:    */     
/* 399:    */ 
/* 400:359 */     return tagify(thisObj, "font", "color", args);
/* 401:    */     
/* 402:    */ 
/* 403:362 */     return tagify(thisObj, "a", "href", args);
/* 404:    */     
/* 405:    */ 
/* 406:365 */     return tagify(thisObj, "a", "name", args);
/* 407:    */     
/* 408:    */ 
/* 409:    */ 
/* 410:369 */     String s1 = ScriptRuntime.toString(thisObj);
/* 411:370 */     String s2 = ScriptRuntime.toString(args, 0);
/* 412:371 */     return ScriptRuntime.wrapBoolean(id == 29 ? s1.equals(s2) : s1.equalsIgnoreCase(s2));
/* 413:    */     int actionType;
/* 414:    */     int actionType;
/* 415:381 */     if (id == 31)
/* 416:    */     {
/* 417:382 */       actionType = 1;
/* 418:    */     }
/* 419:    */     else
/* 420:    */     {
/* 421:    */       int actionType;
/* 422:383 */       if (id == 32) {
/* 423:384 */         actionType = 3;
/* 424:    */       } else {
/* 425:386 */         actionType = 2;
/* 426:    */       }
/* 427:    */     }
/* 428:388 */     return ScriptRuntime.checkRegExpProxy(cx).action(cx, scope, thisObj, args, actionType);
/* 429:    */     
/* 430:    */ 
/* 431:    */ 
/* 432:    */ 
/* 433:    */ 
/* 434:    */ 
/* 435:    */ 
/* 436:    */ 
/* 437:    */ 
/* 438:398 */     Collator collator = Collator.getInstance(cx.getLocale());
/* 439:399 */     collator.setStrength(3);
/* 440:400 */     collator.setDecomposition(1);
/* 441:401 */     return ScriptRuntime.wrapNumber(collator.compare(ScriptRuntime.toString(thisObj), ScriptRuntime.toString(args, 0)));
/* 442:    */     
/* 443:    */ 
/* 444:    */ 
/* 445:    */ 
/* 446:    */ 
/* 447:407 */     return ScriptRuntime.toString(thisObj).toLowerCase(cx.getLocale());
/* 448:    */     
/* 449:    */ 
/* 450:    */ 
/* 451:    */ 
/* 452:412 */     return ScriptRuntime.toString(thisObj).toUpperCase(cx.getLocale());
/* 453:    */     
/* 454:    */ 
/* 455:    */ 
/* 456:    */ 
/* 457:417 */     String str = ScriptRuntime.toString(thisObj);
/* 458:418 */     char[] chars = str.toCharArray();
/* 459:    */     
/* 460:420 */     int start = 0;
/* 461:421 */     while ((start < chars.length) && (ScriptRuntime.isJSWhitespaceOrLineTerminator(chars[start]))) {
/* 462:422 */       start++;
/* 463:    */     }
/* 464:424 */     int end = chars.length;
/* 465:425 */     while ((end > start) && (ScriptRuntime.isJSWhitespaceOrLineTerminator(chars[(end - 1)]))) {
/* 466:426 */       end--;
/* 467:    */     }
/* 468:429 */     return str.substring(start, end);
/* 469:    */     
/* 470:    */ 
/* 471:432 */     throw new IllegalArgumentException(String.valueOf(id));
/* 472:    */   }
/* 473:    */   
/* 474:    */   private static NativeString realThis(Scriptable thisObj, IdFunctionObject f)
/* 475:    */   {
/* 476:438 */     if (!(thisObj instanceof NativeString)) {
/* 477:439 */       throw incompatibleCallError(f);
/* 478:    */     }
/* 479:440 */     return (NativeString)thisObj;
/* 480:    */   }
/* 481:    */   
/* 482:    */   private static String tagify(Object thisObj, String tag, String attribute, Object[] args)
/* 483:    */   {
/* 484:449 */     String str = ScriptRuntime.toString(thisObj);
/* 485:450 */     StringBuffer result = new StringBuffer();
/* 486:451 */     result.append('<');
/* 487:452 */     result.append(tag);
/* 488:453 */     if (attribute != null)
/* 489:    */     {
/* 490:454 */       result.append(' ');
/* 491:455 */       result.append(attribute);
/* 492:456 */       result.append("=\"");
/* 493:457 */       result.append(ScriptRuntime.toString(args, 0));
/* 494:458 */       result.append('"');
/* 495:    */     }
/* 496:460 */     result.append('>');
/* 497:461 */     result.append(str);
/* 498:462 */     result.append("</");
/* 499:463 */     result.append(tag);
/* 500:464 */     result.append('>');
/* 501:465 */     return result.toString();
/* 502:    */   }
/* 503:    */   
/* 504:    */   public String toString()
/* 505:    */   {
/* 506:470 */     return this.string;
/* 507:    */   }
/* 508:    */   
/* 509:    */   public Object get(int index, Scriptable start)
/* 510:    */   {
/* 511:478 */     if ((0 <= index) && (index < this.string.length())) {
/* 512:479 */       return this.string.substring(index, index + 1);
/* 513:    */     }
/* 514:481 */     return super.get(index, start);
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void put(int index, Scriptable start, Object value)
/* 518:    */   {
/* 519:486 */     if ((0 <= index) && (index < this.string.length())) {
/* 520:487 */       return;
/* 521:    */     }
/* 522:489 */     super.put(index, start, value);
/* 523:    */   }
/* 524:    */   
/* 525:    */   private static int js_indexOf(String target, Object[] args)
/* 526:    */   {
/* 527:498 */     String search = ScriptRuntime.toString(args, 0);
/* 528:499 */     double begin = ScriptRuntime.toInteger(args, 1);
/* 529:501 */     if (begin > target.length()) {
/* 530:502 */       return -1;
/* 531:    */     }
/* 532:504 */     if (begin < 0.0D) {
/* 533:505 */       begin = 0.0D;
/* 534:    */     }
/* 535:506 */     return target.indexOf(search, (int)begin);
/* 536:    */   }
/* 537:    */   
/* 538:    */   private static int js_lastIndexOf(String target, Object[] args)
/* 539:    */   {
/* 540:516 */     String search = ScriptRuntime.toString(args, 0);
/* 541:517 */     double end = ScriptRuntime.toNumber(args, 1);
/* 542:519 */     if ((end != end) || (end > target.length())) {
/* 543:520 */       end = target.length();
/* 544:521 */     } else if (end < 0.0D) {
/* 545:522 */       end = 0.0D;
/* 546:    */     }
/* 547:524 */     return target.lastIndexOf(search, (int)end);
/* 548:    */   }
/* 549:    */   
/* 550:    */   private static String js_substring(Context cx, String target, Object[] args)
/* 551:    */   {
/* 552:534 */     int length = target.length();
/* 553:535 */     double start = ScriptRuntime.toInteger(args, 0);
/* 554:538 */     if (start < 0.0D) {
/* 555:539 */       start = 0.0D;
/* 556:540 */     } else if (start > length) {
/* 557:541 */       start = length;
/* 558:    */     }
/* 559:    */     double end;
/* 560:    */     double end;
/* 561:543 */     if ((args.length <= 1) || (args[1] == Undefined.instance))
/* 562:    */     {
/* 563:544 */       end = length;
/* 564:    */     }
/* 565:    */     else
/* 566:    */     {
/* 567:546 */       end = ScriptRuntime.toInteger(args[1]);
/* 568:547 */       if (end < 0.0D) {
/* 569:548 */         end = 0.0D;
/* 570:549 */       } else if (end > length) {
/* 571:550 */         end = length;
/* 572:    */       }
/* 573:553 */       if (end < start) {
/* 574:554 */         if (cx.getLanguageVersion() != 120)
/* 575:    */         {
/* 576:555 */           double temp = start;
/* 577:556 */           start = end;
/* 578:557 */           end = temp;
/* 579:    */         }
/* 580:    */         else
/* 581:    */         {
/* 582:560 */           end = start;
/* 583:    */         }
/* 584:    */       }
/* 585:    */     }
/* 586:564 */     return target.substring((int)start, (int)end);
/* 587:    */   }
/* 588:    */   
/* 589:    */   int getLength()
/* 590:    */   {
/* 591:568 */     return this.string.length();
/* 592:    */   }
/* 593:    */   
/* 594:    */   private static String js_substr(String target, Object[] args)
/* 595:    */   {
/* 596:575 */     if (args.length < 1) {
/* 597:576 */       return target;
/* 598:    */     }
/* 599:578 */     double begin = ScriptRuntime.toInteger(args[0]);
/* 600:    */     
/* 601:580 */     int length = target.length();
/* 602:582 */     if (begin < 0.0D)
/* 603:    */     {
/* 604:583 */       begin += length;
/* 605:584 */       if (begin < 0.0D) {
/* 606:585 */         begin = 0.0D;
/* 607:    */       }
/* 608:    */     }
/* 609:586 */     else if (begin > length)
/* 610:    */     {
/* 611:587 */       begin = length;
/* 612:    */     }
/* 613:    */     double end;
/* 614:    */     double end;
/* 615:590 */     if (args.length == 1)
/* 616:    */     {
/* 617:591 */       end = length;
/* 618:    */     }
/* 619:    */     else
/* 620:    */     {
/* 621:593 */       end = ScriptRuntime.toInteger(args[1]);
/* 622:594 */       if (end < 0.0D) {
/* 623:595 */         end = 0.0D;
/* 624:    */       }
/* 625:596 */       end += begin;
/* 626:597 */       if (end > length) {
/* 627:598 */         end = length;
/* 628:    */       }
/* 629:    */     }
/* 630:601 */     return target.substring((int)begin, (int)end);
/* 631:    */   }
/* 632:    */   
/* 633:    */   private static String js_concat(String target, Object[] args)
/* 634:    */   {
/* 635:608 */     int N = args.length;
/* 636:609 */     if (N == 0) {
/* 637:609 */       return target;
/* 638:    */     }
/* 639:610 */     if (N == 1)
/* 640:    */     {
/* 641:611 */       String arg = ScriptRuntime.toString(args[0]);
/* 642:612 */       return target.concat(arg);
/* 643:    */     }
/* 644:617 */     int size = target.length();
/* 645:618 */     String[] argsAsStrings = new String[N];
/* 646:619 */     for (int i = 0; i != N; i++)
/* 647:    */     {
/* 648:620 */       String s = ScriptRuntime.toString(args[i]);
/* 649:621 */       argsAsStrings[i] = s;
/* 650:622 */       size += s.length();
/* 651:    */     }
/* 652:625 */     StringBuffer result = new StringBuffer(size);
/* 653:626 */     result.append(target);
/* 654:627 */     for (int i = 0; i != N; i++) {
/* 655:628 */       result.append(argsAsStrings[i]);
/* 656:    */     }
/* 657:630 */     return result.toString();
/* 658:    */   }
/* 659:    */   
/* 660:    */   private static String js_slice(String target, Object[] args)
/* 661:    */   {
/* 662:634 */     if (args.length != 0)
/* 663:    */     {
/* 664:635 */       double begin = ScriptRuntime.toInteger(args[0]);
/* 665:    */       
/* 666:637 */       int length = target.length();
/* 667:638 */       if (begin < 0.0D)
/* 668:    */       {
/* 669:639 */         begin += length;
/* 670:640 */         if (begin < 0.0D) {
/* 671:641 */           begin = 0.0D;
/* 672:    */         }
/* 673:    */       }
/* 674:642 */       else if (begin > length)
/* 675:    */       {
/* 676:643 */         begin = length;
/* 677:    */       }
/* 678:    */       double end;
/* 679:    */       double end;
/* 680:646 */       if (args.length == 1)
/* 681:    */       {
/* 682:647 */         end = length;
/* 683:    */       }
/* 684:    */       else
/* 685:    */       {
/* 686:649 */         end = ScriptRuntime.toInteger(args[1]);
/* 687:650 */         if (end < 0.0D)
/* 688:    */         {
/* 689:651 */           end += length;
/* 690:652 */           if (end < 0.0D) {
/* 691:653 */             end = 0.0D;
/* 692:    */           }
/* 693:    */         }
/* 694:654 */         else if (end > length)
/* 695:    */         {
/* 696:655 */           end = length;
/* 697:    */         }
/* 698:657 */         if (end < begin) {
/* 699:658 */           end = begin;
/* 700:    */         }
/* 701:    */       }
/* 702:660 */       return target.substring((int)begin, (int)end);
/* 703:    */     }
/* 704:662 */     return target;
/* 705:    */   }
/* 706:    */   
/* 707:    */   protected int findPrototypeId(String s)
/* 708:    */   {
/* 709:672 */     int id = 0;String X = null;
/* 710:    */     int c;
/* 711:673 */     switch (s.length())
/* 712:    */     {
/* 713:    */     case 3: 
/* 714:674 */       c = s.charAt(2);
/* 715:675 */       if (c == 98)
/* 716:    */       {
/* 717:675 */         if ((s.charAt(0) == 's') && (s.charAt(1) == 'u'))
/* 718:    */         {
/* 719:675 */           id = 24; return id;
/* 720:    */         }
/* 721:    */       }
/* 722:676 */       else if (c == 103)
/* 723:    */       {
/* 724:676 */         if ((s.charAt(0) == 'b') && (s.charAt(1) == 'i'))
/* 725:    */         {
/* 726:676 */           id = 21; return id;
/* 727:    */         }
/* 728:    */       }
/* 729:677 */       else if ((c == 112) && (s.charAt(0) == 's') && (s.charAt(1) == 'u')) {
/* 730:677 */         id = 23;
/* 731:    */       }
/* 732:    */       break;
/* 733:    */     case 4: 
/* 734:679 */       c = s.charAt(0);
/* 735:680 */       if (c == 98)
/* 736:    */       {
/* 737:680 */         X = "bold";id = 16;
/* 738:    */       }
/* 739:681 */       else if (c == 108)
/* 740:    */       {
/* 741:681 */         X = "link";id = 27;
/* 742:    */       }
/* 743:682 */       else if (c == 116)
/* 744:    */       {
/* 745:682 */         X = "trim";id = 37;
/* 746:    */       }
/* 747:    */       break;
/* 748:    */     case 5: 
/* 749:684 */       switch (s.charAt(4))
/* 750:    */       {
/* 751:    */       case 'd': 
/* 752:685 */         X = "fixed";id = 18; break;
/* 753:    */       case 'e': 
/* 754:686 */         X = "slice";id = 15; break;
/* 755:    */       case 'h': 
/* 756:687 */         X = "match";id = 31; break;
/* 757:    */       case 'k': 
/* 758:688 */         X = "blink";id = 22; break;
/* 759:    */       case 'l': 
/* 760:689 */         X = "small";id = 20; break;
/* 761:    */       case 't': 
/* 762:690 */         X = "split";id = 9;
/* 763:    */       }
/* 764:691 */       break;
/* 765:    */     case 6: 
/* 766:692 */       switch (s.charAt(1))
/* 767:    */       {
/* 768:    */       case 'e': 
/* 769:693 */         X = "search";id = 32; break;
/* 770:    */       case 'h': 
/* 771:694 */         X = "charAt";id = 5; break;
/* 772:    */       case 'n': 
/* 773:695 */         X = "anchor";id = 28; break;
/* 774:    */       case 'o': 
/* 775:696 */         X = "concat";id = 14; break;
/* 776:    */       case 'q': 
/* 777:697 */         X = "equals";id = 29; break;
/* 778:    */       case 't': 
/* 779:698 */         X = "strike";id = 19; break;
/* 780:    */       case 'u': 
/* 781:699 */         X = "substr";id = 13;
/* 782:    */       }
/* 783:700 */       break;
/* 784:    */     case 7: 
/* 785:701 */       switch (s.charAt(1))
/* 786:    */       {
/* 787:    */       case 'a': 
/* 788:702 */         X = "valueOf";id = 4; break;
/* 789:    */       case 'e': 
/* 790:703 */         X = "replace";id = 33; break;
/* 791:    */       case 'n': 
/* 792:704 */         X = "indexOf";id = 7; break;
/* 793:    */       case 't': 
/* 794:705 */         X = "italics";id = 17;
/* 795:    */       }
/* 796:706 */       break;
/* 797:    */     case 8: 
/* 798:707 */       c = s.charAt(4);
/* 799:708 */       if (c == 114)
/* 800:    */       {
/* 801:708 */         X = "toString";id = 2;
/* 802:    */       }
/* 803:709 */       else if (c == 115)
/* 804:    */       {
/* 805:709 */         X = "fontsize";id = 25;
/* 806:    */       }
/* 807:710 */       else if (c == 117)
/* 808:    */       {
/* 809:710 */         X = "toSource";id = 3;
/* 810:    */       }
/* 811:    */       break;
/* 812:    */     case 9: 
/* 813:712 */       c = s.charAt(0);
/* 814:713 */       if (c == 102)
/* 815:    */       {
/* 816:713 */         X = "fontcolor";id = 26;
/* 817:    */       }
/* 818:714 */       else if (c == 115)
/* 819:    */       {
/* 820:714 */         X = "substring";id = 10;
/* 821:    */       }
/* 822:    */       break;
/* 823:    */     case 10: 
/* 824:716 */       X = "charCodeAt";id = 6; break;
/* 825:    */     case 11: 
/* 826:717 */       switch (s.charAt(2))
/* 827:    */       {
/* 828:    */       case 'L': 
/* 829:718 */         X = "toLowerCase";id = 11; break;
/* 830:    */       case 'U': 
/* 831:719 */         X = "toUpperCase";id = 12; break;
/* 832:    */       case 'n': 
/* 833:720 */         X = "constructor";id = 1; break;
/* 834:    */       case 's': 
/* 835:721 */         X = "lastIndexOf";id = 8;
/* 836:    */       }
/* 837:722 */       break;
/* 838:    */     case 13: 
/* 839:723 */       X = "localeCompare";id = 34; break;
/* 840:    */     case 16: 
/* 841:724 */       X = "equalsIgnoreCase";id = 30; break;
/* 842:    */     case 17: 
/* 843:725 */       c = s.charAt(8);
/* 844:726 */       if (c == 76)
/* 845:    */       {
/* 846:726 */         X = "toLocaleLowerCase";id = 35;
/* 847:    */       }
/* 848:727 */       else if (c == 85)
/* 849:    */       {
/* 850:727 */         X = "toLocaleUpperCase";id = 36;
/* 851:    */       }
/* 852:    */       break;
/* 853:    */     }
/* 854:730 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 855:730 */       id = 0;
/* 856:    */     }
/* 857:734 */     return id;
/* 858:    */   }
/* 859:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeString
 * JD-Core Version:    0.7.0.1
 */
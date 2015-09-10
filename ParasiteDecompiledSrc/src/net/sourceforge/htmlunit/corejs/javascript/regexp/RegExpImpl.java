/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.RegExpProxy;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  11:    */ 
/*  12:    */ public class RegExpImpl
/*  13:    */   implements RegExpProxy
/*  14:    */ {
/*  15:    */   protected String input;
/*  16:    */   protected boolean multiline;
/*  17:    */   protected SubString[] parens;
/*  18:    */   protected SubString lastMatch;
/*  19:    */   protected SubString lastParen;
/*  20:    */   protected SubString leftContext;
/*  21:    */   protected SubString rightContext;
/*  22:    */   
/*  23:    */   public boolean isRegExp(Scriptable obj)
/*  24:    */   {
/*  25: 48 */     return obj instanceof NativeRegExp;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object compileRegExp(Context cx, String source, String flags)
/*  29:    */   {
/*  30: 53 */     return NativeRegExp.compileRE(cx, source, flags, false);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Scriptable wrapRegExp(Context cx, Scriptable scope, Object compiled)
/*  34:    */   {
/*  35: 59 */     return new NativeRegExp(scope, compiled);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object action(Context cx, Scriptable scope, Scriptable thisObj, Object[] args, int actionType)
/*  39:    */   {
/*  40: 66 */     GlobData data = new GlobData();
/*  41: 67 */     data.mode = actionType;
/*  42: 69 */     switch (actionType)
/*  43:    */     {
/*  44:    */     case 1: 
/*  45: 73 */       data.optarg = 1;
/*  46: 74 */       Object rval = matchOrReplace(cx, scope, thisObj, args, this, data, false);
/*  47:    */       
/*  48: 76 */       return data.arrayobj == null ? rval : data.arrayobj;
/*  49:    */     case 3: 
/*  50: 80 */       data.optarg = 1;
/*  51: 81 */       return matchOrReplace(cx, scope, thisObj, args, this, data, false);
/*  52:    */     case 2: 
/*  53: 86 */       Object arg1 = args.length < 2 ? Undefined.instance : args[1];
/*  54: 87 */       String repstr = null;
/*  55: 88 */       Function lambda = null;
/*  56: 89 */       if ((arg1 instanceof Function)) {
/*  57: 90 */         lambda = (Function)arg1;
/*  58:    */       } else {
/*  59: 92 */         repstr = ScriptRuntime.toString(arg1);
/*  60:    */       }
/*  61: 95 */       data.optarg = 2;
/*  62: 96 */       data.lambda = lambda;
/*  63: 97 */       data.repstr = repstr;
/*  64: 98 */       data.dollar = (repstr == null ? -1 : repstr.indexOf('$'));
/*  65: 99 */       data.charBuf = null;
/*  66:100 */       data.leftIndex = 0;
/*  67:101 */       Object val = matchOrReplace(cx, scope, thisObj, args, this, data, true);
/*  68:104 */       if (data.charBuf == null)
/*  69:    */       {
/*  70:105 */         if ((data.global) || (val == null) || (!val.equals(Boolean.TRUE))) {
/*  71:109 */           return data.str;
/*  72:    */         }
/*  73:111 */         SubString lc = this.leftContext;
/*  74:112 */         replace_glob(data, cx, scope, this, lc.index, lc.length);
/*  75:    */       }
/*  76:114 */       SubString rc = this.rightContext;
/*  77:115 */       data.charBuf.append(rc.str, rc.index, rc.index + rc.length);
/*  78:116 */       return data.charBuf.toString();
/*  79:    */     }
/*  80:120 */     throw Kit.codeBug();
/*  81:    */   }
/*  82:    */   
/*  83:    */   private static Object matchOrReplace(Context cx, Scriptable scope, Scriptable thisObj, Object[] args, RegExpImpl reImpl, GlobData data, boolean forceFlat)
/*  84:    */   {
/*  85:134 */     String str = ScriptRuntime.toString(thisObj);
/*  86:135 */     data.str = str;
/*  87:136 */     Scriptable topScope = ScriptableObject.getTopLevelScope(scope);
/*  88:    */     NativeRegExp re;
/*  89:    */     NativeRegExp re;
/*  90:138 */     if (args.length == 0)
/*  91:    */     {
/*  92:139 */       Object compiled = NativeRegExp.compileRE(cx, "", "", false);
/*  93:140 */       re = new NativeRegExp(topScope, compiled);
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:    */       NativeRegExp re;
/*  98:141 */       if ((args[0] instanceof NativeRegExp))
/*  99:    */       {
/* 100:142 */         re = (NativeRegExp)args[0];
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:144 */         String src = ScriptRuntime.toString(args[0]);
/* 105:    */         String opt;
/* 106:    */         String opt;
/* 107:146 */         if (data.optarg < args.length)
/* 108:    */         {
/* 109:147 */           args[0] = src;
/* 110:148 */           opt = ScriptRuntime.toString(args[data.optarg]);
/* 111:    */         }
/* 112:    */         else
/* 113:    */         {
/* 114:150 */           opt = null;
/* 115:    */         }
/* 116:152 */         Object compiled = NativeRegExp.compileRE(cx, src, opt, forceFlat);
/* 117:153 */         re = new NativeRegExp(topScope, compiled);
/* 118:    */       }
/* 119:    */     }
/* 120:156 */     data.global = ((re.getFlags() & 0x1) != 0);
/* 121:157 */     int[] indexp = { 0 };
/* 122:158 */     Object result = null;
/* 123:159 */     if (data.mode == 3)
/* 124:    */     {
/* 125:160 */       result = re.executeRegExp(cx, scope, reImpl, str, indexp, 0);
/* 126:162 */       if ((result != null) && (result.equals(Boolean.TRUE))) {
/* 127:163 */         result = Integer.valueOf(reImpl.leftContext.length);
/* 128:    */       } else {
/* 129:165 */         result = Integer.valueOf(-1);
/* 130:    */       }
/* 131:    */     }
/* 132:166 */     else if (data.global)
/* 133:    */     {
/* 134:167 */       re.lastIndex = 0.0D;
/* 135:168 */       for (int count = 0; indexp[0] <= str.length(); count++)
/* 136:    */       {
/* 137:169 */         result = re.executeRegExp(cx, scope, reImpl, str, indexp, 0);
/* 138:171 */         if ((result == null) || (!result.equals(Boolean.TRUE))) {
/* 139:    */           break;
/* 140:    */         }
/* 141:173 */         if (data.mode == 1)
/* 142:    */         {
/* 143:174 */           match_glob(data, cx, scope, count, reImpl);
/* 144:    */         }
/* 145:    */         else
/* 146:    */         {
/* 147:176 */           if (data.mode != 2) {
/* 148:176 */             Kit.codeBug();
/* 149:    */           }
/* 150:177 */           SubString lastMatch = reImpl.lastMatch;
/* 151:178 */           int leftIndex = data.leftIndex;
/* 152:179 */           int leftlen = lastMatch.index - leftIndex;
/* 153:180 */           data.leftIndex = (lastMatch.index + lastMatch.length);
/* 154:181 */           replace_glob(data, cx, scope, reImpl, leftIndex, leftlen);
/* 155:    */         }
/* 156:183 */         if (reImpl.lastMatch.length == 0)
/* 157:    */         {
/* 158:184 */           if (indexp[0] == str.length()) {
/* 159:    */             break;
/* 160:    */           }
/* 161:186 */           indexp[0] += 1;
/* 162:    */         }
/* 163:    */       }
/* 164:    */     }
/* 165:    */     else
/* 166:    */     {
/* 167:190 */       result = re.executeRegExp(cx, scope, reImpl, str, indexp, data.mode == 2 ? 0 : 1);
/* 168:    */     }
/* 169:196 */     return result;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int find_split(Context cx, Scriptable scope, String target, String separator, Scriptable reObj, int[] ip, int[] matchlen, boolean[] matched, String[][] parensp)
/* 173:    */   {
/* 174:206 */     int i = ip[0];
/* 175:207 */     int length = target.length();
/* 176:    */     
/* 177:    */ 
/* 178:210 */     int version = cx.getLanguageVersion();
/* 179:211 */     NativeRegExp re = (NativeRegExp)reObj;
/* 180:    */     for (;;)
/* 181:    */     {
/* 182:215 */       int ipsave = ip[0];
/* 183:216 */       ip[0] = i;
/* 184:217 */       Object ret = re.executeRegExp(cx, scope, this, target, ip, 0);
/* 185:219 */       if (ret != Boolean.TRUE)
/* 186:    */       {
/* 187:221 */         ip[0] = ipsave;
/* 188:222 */         matchlen[0] = 1;
/* 189:223 */         matched[0] = false;
/* 190:224 */         return length;
/* 191:    */       }
/* 192:226 */       i = ip[0];
/* 193:227 */       ip[0] = ipsave;
/* 194:228 */       matched[0] = true;
/* 195:    */       
/* 196:230 */       SubString sep = this.lastMatch;
/* 197:231 */       matchlen[0] = sep.length;
/* 198:232 */       if (matchlen[0] != 0) {
/* 199:    */         break;
/* 200:    */       }
/* 201:239 */       if (i != ip[0]) {
/* 202:    */         break;
/* 203:    */       }
/* 204:246 */       if (i == length)
/* 205:    */       {
/* 206:247 */         if (version == 120)
/* 207:    */         {
/* 208:248 */           matchlen[0] = 1;
/* 209:249 */           int result = i;
/* 210:    */           break label176;
/* 211:    */         }
/* 212:252 */         int result = -1;
/* 213:    */         break label176;
/* 214:    */       }
/* 215:255 */       i++;
/* 216:    */     }
/* 217:260 */     int result = i - matchlen[0];
/* 218:    */     label176:
/* 219:263 */     int size = this.parens == null ? 0 : this.parens.length;
/* 220:264 */     parensp[0] = new String[size];
/* 221:265 */     for (int num = 0; num < size; num++)
/* 222:    */     {
/* 223:266 */       SubString parsub = getParenSubString(num);
/* 224:267 */       parensp[0][num] = parsub.toString();
/* 225:    */     }
/* 226:269 */     return result;
/* 227:    */   }
/* 228:    */   
/* 229:    */   SubString getParenSubString(int i)
/* 230:    */   {
/* 231:278 */     if ((this.parens != null) && (i < this.parens.length))
/* 232:    */     {
/* 233:279 */       SubString parsub = this.parens[i];
/* 234:280 */       if (parsub != null) {
/* 235:281 */         return parsub;
/* 236:    */       }
/* 237:    */     }
/* 238:284 */     return SubString.emptySubString;
/* 239:    */   }
/* 240:    */   
/* 241:    */   private static void match_glob(GlobData mdata, Context cx, Scriptable scope, int count, RegExpImpl reImpl)
/* 242:    */   {
/* 243:294 */     if (mdata.arrayobj == null) {
/* 244:295 */       mdata.arrayobj = cx.newArray(scope, 0);
/* 245:    */     }
/* 246:297 */     SubString matchsub = reImpl.lastMatch;
/* 247:298 */     String matchstr = matchsub.toString();
/* 248:299 */     mdata.arrayobj.put(count, mdata.arrayobj, matchstr);
/* 249:    */   }
/* 250:    */   
/* 251:    */   private static void replace_glob(GlobData rdata, Context cx, Scriptable scope, RegExpImpl reImpl, int leftIndex, int leftlen)
/* 252:    */   {
/* 253:    */     int replen;
/* 254:    */     String lambdaStr;
/* 255:    */     int replen;
/* 256:311 */     if (rdata.lambda != null)
/* 257:    */     {
/* 258:314 */       SubString[] parens = reImpl.parens;
/* 259:315 */       int parenCount = parens == null ? 0 : parens.length;
/* 260:316 */       Object[] args = new Object[parenCount + 3];
/* 261:317 */       args[0] = reImpl.lastMatch.toString();
/* 262:318 */       for (int i = 0; i < parenCount; i++)
/* 263:    */       {
/* 264:319 */         SubString sub = parens[i];
/* 265:320 */         if (sub != null) {
/* 266:321 */           args[(i + 1)] = sub.toString();
/* 267:    */         } else {
/* 268:323 */           args[(i + 1)] = Undefined.instance;
/* 269:    */         }
/* 270:    */       }
/* 271:326 */       args[(parenCount + 1)] = Integer.valueOf(reImpl.leftContext.length);
/* 272:327 */       args[(parenCount + 2)] = rdata.str;
/* 273:332 */       if (reImpl != ScriptRuntime.getRegExpProxy(cx)) {
/* 274:332 */         Kit.codeBug();
/* 275:    */       }
/* 276:333 */       RegExpImpl re2 = new RegExpImpl();
/* 277:334 */       re2.multiline = reImpl.multiline;
/* 278:335 */       re2.input = reImpl.input;
/* 279:336 */       ScriptRuntime.setRegExpProxy(cx, re2);
/* 280:    */       String lambdaStr;
/* 281:    */       try
/* 282:    */       {
/* 283:338 */         Scriptable parent = ScriptableObject.getTopLevelScope(scope);
/* 284:339 */         Object result = rdata.lambda.call(cx, parent, parent, args);
/* 285:340 */         lambdaStr = ScriptRuntime.toString(result);
/* 286:    */       }
/* 287:    */       finally
/* 288:    */       {
/* 289:342 */         ScriptRuntime.setRegExpProxy(cx, reImpl);
/* 290:    */       }
/* 291:344 */       replen = lambdaStr.length();
/* 292:    */     }
/* 293:    */     else
/* 294:    */     {
/* 295:346 */       lambdaStr = null;
/* 296:347 */       replen = rdata.repstr.length();
/* 297:348 */       if (rdata.dollar >= 0)
/* 298:    */       {
/* 299:349 */         int[] skip = new int[1];
/* 300:350 */         int dp = rdata.dollar;
/* 301:    */         do
/* 302:    */         {
/* 303:352 */           SubString sub = interpretDollar(cx, reImpl, rdata.repstr, dp, skip);
/* 304:354 */           if (sub != null)
/* 305:    */           {
/* 306:355 */             replen += sub.length - skip[0];
/* 307:356 */             dp += skip[0];
/* 308:    */           }
/* 309:    */           else
/* 310:    */           {
/* 311:358 */             dp++;
/* 312:    */           }
/* 313:360 */           dp = rdata.repstr.indexOf('$', dp);
/* 314:361 */         } while (dp >= 0);
/* 315:    */       }
/* 316:    */     }
/* 317:365 */     int growth = leftlen + replen + reImpl.rightContext.length;
/* 318:366 */     StringBuilder charBuf = rdata.charBuf;
/* 319:367 */     if (charBuf == null)
/* 320:    */     {
/* 321:368 */       charBuf = new StringBuilder(growth);
/* 322:369 */       rdata.charBuf = charBuf;
/* 323:    */     }
/* 324:    */     else
/* 325:    */     {
/* 326:371 */       charBuf.ensureCapacity(rdata.charBuf.length() + growth);
/* 327:    */     }
/* 328:374 */     charBuf.append(reImpl.leftContext.str, leftIndex, leftIndex + leftlen);
/* 329:375 */     if (rdata.lambda != null) {
/* 330:376 */       charBuf.append(lambdaStr);
/* 331:    */     } else {
/* 332:378 */       do_replace(rdata, cx, reImpl);
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   private static SubString interpretDollar(Context cx, RegExpImpl res, String da, int dp, int[] skip)
/* 337:    */   {
/* 338:388 */     if (da.charAt(dp) != '$') {
/* 339:388 */       Kit.codeBug();
/* 340:    */     }
/* 341:391 */     int version = cx.getLanguageVersion();
/* 342:392 */     if ((version != 0) && (version <= 140)) {
/* 343:395 */       if ((dp > 0) && (da.charAt(dp - 1) == '\\')) {
/* 344:396 */         return null;
/* 345:    */       }
/* 346:    */     }
/* 347:398 */     int daL = da.length();
/* 348:399 */     if (dp + 1 >= daL) {
/* 349:400 */       return null;
/* 350:    */     }
/* 351:402 */     char dc = da.charAt(dp + 1);
/* 352:403 */     if (NativeRegExp.isDigit(dc))
/* 353:    */     {
/* 354:405 */       if ((version != 0) && (version <= 140))
/* 355:    */       {
/* 356:408 */         if (dc == '0') {
/* 357:409 */           return null;
/* 358:    */         }
/* 359:411 */         int num = 0;
/* 360:412 */         int cp = dp;
/* 361:    */         for (;;)
/* 362:    */         {
/* 363:413 */           cp++;
/* 364:413 */           if ((cp >= daL) || (!NativeRegExp.isDigit(dc = da.charAt(cp)))) {
/* 365:    */             break;
/* 366:    */           }
/* 367:415 */           int tmp = 10 * num + (dc - '0');
/* 368:416 */           if (tmp < num) {
/* 369:    */             break;
/* 370:    */           }
/* 371:418 */           num = tmp;
/* 372:    */         }
/* 373:    */       }
/* 374:422 */       int parenCount = res.parens == null ? 0 : res.parens.length;
/* 375:423 */       int num = dc - '0';
/* 376:424 */       if (num > parenCount) {
/* 377:425 */         return null;
/* 378:    */       }
/* 379:426 */       int cp = dp + 2;
/* 380:427 */       if (dp + 2 < daL)
/* 381:    */       {
/* 382:428 */         dc = da.charAt(dp + 2);
/* 383:429 */         if (NativeRegExp.isDigit(dc))
/* 384:    */         {
/* 385:430 */           int tmp = 10 * num + (dc - '0');
/* 386:431 */           if (tmp <= parenCount)
/* 387:    */           {
/* 388:432 */             cp++;
/* 389:433 */             num = tmp;
/* 390:    */           }
/* 391:    */         }
/* 392:    */       }
/* 393:437 */       if (num == 0) {
/* 394:437 */         return null;
/* 395:    */       }
/* 396:440 */       num--;
/* 397:441 */       skip[0] = (cp - dp);
/* 398:442 */       return res.getParenSubString(num);
/* 399:    */     }
/* 400:445 */     skip[0] = 2;
/* 401:446 */     switch (dc)
/* 402:    */     {
/* 403:    */     case '$': 
/* 404:448 */       return new SubString("$");
/* 405:    */     case '&': 
/* 406:450 */       return res.lastMatch;
/* 407:    */     case '+': 
/* 408:452 */       return res.lastParen;
/* 409:    */     case '`': 
/* 410:454 */       if (version == 120)
/* 411:    */       {
/* 412:462 */         res.leftContext.index = 0;
/* 413:463 */         res.leftContext.length = res.lastMatch.index;
/* 414:    */       }
/* 415:465 */       return res.leftContext;
/* 416:    */     case '\'': 
/* 417:467 */       return res.rightContext;
/* 418:    */     }
/* 419:469 */     return null;
/* 420:    */   }
/* 421:    */   
/* 422:    */   private static void do_replace(GlobData rdata, Context cx, RegExpImpl regExpImpl)
/* 423:    */   {
/* 424:478 */     StringBuilder charBuf = rdata.charBuf;
/* 425:479 */     int cp = 0;
/* 426:480 */     String da = rdata.repstr;
/* 427:481 */     int dp = rdata.dollar;
/* 428:482 */     if (dp != -1)
/* 429:    */     {
/* 430:483 */       int[] skip = new int[1];
/* 431:    */       do
/* 432:    */       {
/* 433:485 */         int len = dp - cp;
/* 434:486 */         charBuf.append(da.substring(cp, dp));
/* 435:487 */         cp = dp;
/* 436:488 */         SubString sub = interpretDollar(cx, regExpImpl, da, dp, skip);
/* 437:490 */         if (sub != null)
/* 438:    */         {
/* 439:491 */           len = sub.length;
/* 440:492 */           if (len > 0) {
/* 441:493 */             charBuf.append(sub.str, sub.index, sub.index + len);
/* 442:    */           }
/* 443:495 */           cp += skip[0];
/* 444:496 */           dp += skip[0];
/* 445:    */         }
/* 446:    */         else
/* 447:    */         {
/* 448:498 */           dp++;
/* 449:    */         }
/* 450:500 */         dp = da.indexOf('$', dp);
/* 451:501 */       } while (dp >= 0);
/* 452:    */     }
/* 453:503 */     int daL = da.length();
/* 454:504 */     if (daL > cp) {
/* 455:505 */       charBuf.append(da.substring(cp, daL));
/* 456:    */     }
/* 457:    */   }
/* 458:    */   
/* 459:    */   public Object js_split(Context cx, Scriptable scope, String target, Object[] args)
/* 460:    */   {
/* 461:518 */     Scriptable result = cx.newArray(scope, 0);
/* 462:523 */     if (args.length < 1)
/* 463:    */     {
/* 464:524 */       result.put(0, result, target);
/* 465:525 */       return result;
/* 466:    */     }
/* 467:529 */     boolean limited = (args.length > 1) && (args[1] != Undefined.instance);
/* 468:530 */     long limit = 0L;
/* 469:531 */     if (limited)
/* 470:    */     {
/* 471:533 */       limit = ScriptRuntime.toUint32(args[1]);
/* 472:534 */       if (limit > target.length()) {
/* 473:535 */         limit = 1 + target.length();
/* 474:    */       }
/* 475:    */     }
/* 476:538 */     String separator = null;
/* 477:539 */     int[] matchlen = new int[1];
/* 478:540 */     Scriptable re = null;
/* 479:541 */     RegExpProxy reProxy = null;
/* 480:542 */     if ((args[0] instanceof Scriptable))
/* 481:    */     {
/* 482:543 */       reProxy = ScriptRuntime.getRegExpProxy(cx);
/* 483:544 */       if (reProxy != null)
/* 484:    */       {
/* 485:545 */         Scriptable test = (Scriptable)args[0];
/* 486:546 */         if (reProxy.isRegExp(test)) {
/* 487:547 */           re = test;
/* 488:    */         }
/* 489:    */       }
/* 490:    */     }
/* 491:551 */     if (re == null)
/* 492:    */     {
/* 493:552 */       separator = ScriptRuntime.toString(args[0]);
/* 494:553 */       matchlen[0] = separator.length();
/* 495:    */     }
/* 496:557 */     int[] ip = { 0 };
/* 497:    */     
/* 498:559 */     int len = 0;
/* 499:560 */     boolean[] matched = { false };
/* 500:561 */     String[][] parens = { null };
/* 501:562 */     int version = cx.getLanguageVersion();
/* 502:    */     int match;
/* 503:565 */     while ((match = find_split(cx, scope, target, separator, version, reProxy, re, ip, matchlen, matched, parens)) >= 0)
/* 504:    */     {
/* 505:567 */       if (((limited) && (len >= limit)) || (match > target.length())) {
/* 506:    */         break;
/* 507:    */       }
/* 508:    */       String substr;
/* 509:    */       String substr;
/* 510:571 */       if (target.length() == 0) {
/* 511:572 */         substr = target;
/* 512:    */       } else {
/* 513:574 */         substr = target.substring(ip[0], match);
/* 514:    */       }
/* 515:576 */       result.put(len, result, substr);
/* 516:577 */       len++;
/* 517:583 */       if ((re != null) && (matched[0] == 1))
/* 518:    */       {
/* 519:584 */         int size = parens[0].length;
/* 520:585 */         for (int num = 0; num < size; num++)
/* 521:    */         {
/* 522:586 */           if ((limited) && (len >= limit)) {
/* 523:    */             break;
/* 524:    */           }
/* 525:588 */           result.put(len, result, parens[0][num]);
/* 526:589 */           len++;
/* 527:    */         }
/* 528:591 */         matched[0] = false;
/* 529:    */       }
/* 530:593 */       ip[0] = (match + matchlen[0]);
/* 531:595 */       if ((version < 130) && (version != 0) && 
/* 532:    */       
/* 533:    */ 
/* 534:    */ 
/* 535:    */ 
/* 536:    */ 
/* 537:    */ 
/* 538:602 */         (!limited) && (ip[0] == target.length())) {
/* 539:    */         break;
/* 540:    */       }
/* 541:    */     }
/* 542:606 */     return result;
/* 543:    */   }
/* 544:    */   
/* 545:    */   private static int find_split(Context cx, Scriptable scope, String target, String separator, int version, RegExpProxy reProxy, Scriptable re, int[] ip, int[] matchlen, boolean[] matched, String[][] parensp)
/* 546:    */   {
/* 547:627 */     int i = ip[0];
/* 548:628 */     int length = target.length();
/* 549:635 */     if ((version == 120) && (re == null) && (separator.length() == 1) && (separator.charAt(0) == ' '))
/* 550:    */     {
/* 551:639 */       if (i == 0)
/* 552:    */       {
/* 553:640 */         while ((i < length) && (Character.isWhitespace(target.charAt(i)))) {
/* 554:641 */           i++;
/* 555:    */         }
/* 556:642 */         ip[0] = i;
/* 557:    */       }
/* 558:646 */       if (i == length) {
/* 559:647 */         return -1;
/* 560:    */       }
/* 561:651 */       while ((i < length) && (!Character.isWhitespace(target.charAt(i)))) {
/* 562:652 */         i++;
/* 563:    */       }
/* 564:655 */       int j = i;
/* 565:656 */       while ((j < length) && (Character.isWhitespace(target.charAt(j)))) {
/* 566:657 */         j++;
/* 567:    */       }
/* 568:660 */       matchlen[0] = (j - i);
/* 569:661 */       return i;
/* 570:    */     }
/* 571:674 */     if (i > length) {
/* 572:675 */       return -1;
/* 573:    */     }
/* 574:682 */     if (re != null) {
/* 575:683 */       return reProxy.find_split(cx, scope, target, separator, re, ip, matchlen, matched, parensp);
/* 576:    */     }
/* 577:692 */     if ((version != 0) && (version < 130) && (length == 0)) {
/* 578:694 */       return -1;
/* 579:    */     }
/* 580:706 */     if (separator.length() == 0)
/* 581:    */     {
/* 582:707 */       if (version == 120)
/* 583:    */       {
/* 584:708 */         if (i == length)
/* 585:    */         {
/* 586:709 */           matchlen[0] = 1;
/* 587:710 */           return i;
/* 588:    */         }
/* 589:712 */         return i + 1;
/* 590:    */       }
/* 591:714 */       return i == length ? -1 : i + 1;
/* 592:    */     }
/* 593:720 */     if (ip[0] >= length) {
/* 594:721 */       return length;
/* 595:    */     }
/* 596:723 */     i = target.indexOf(separator, ip[0]);
/* 597:    */     
/* 598:725 */     return i != -1 ? i : length;
/* 599:    */   }
/* 600:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.RegExpImpl
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class Token
/*   4:    */ {
/*   5:    */   public static final boolean printTrees = false;
/*   6:    */   static final boolean printICode = false;
/*   7:    */   static final boolean printNames = false;
/*   8:    */   public static final int ERROR = -1;
/*   9:    */   public static final int EOF = 0;
/*  10:    */   public static final int EOL = 1;
/*  11:    */   public static final int FIRST_BYTECODE_TOKEN = 2;
/*  12:    */   public static final int ENTERWITH = 2;
/*  13:    */   public static final int LEAVEWITH = 3;
/*  14:    */   public static final int RETURN = 4;
/*  15:    */   public static final int GOTO = 5;
/*  16:    */   public static final int IFEQ = 6;
/*  17:    */   public static final int IFNE = 7;
/*  18:    */   public static final int SETNAME = 8;
/*  19:    */   public static final int BITOR = 9;
/*  20:    */   public static final int BITXOR = 10;
/*  21:    */   public static final int BITAND = 11;
/*  22:    */   public static final int EQ = 12;
/*  23:    */   public static final int NE = 13;
/*  24:    */   public static final int LT = 14;
/*  25:    */   public static final int LE = 15;
/*  26:    */   public static final int GT = 16;
/*  27:    */   public static final int GE = 17;
/*  28:    */   public static final int LSH = 18;
/*  29:    */   public static final int RSH = 19;
/*  30:    */   public static final int URSH = 20;
/*  31:    */   public static final int ADD = 21;
/*  32:    */   public static final int SUB = 22;
/*  33:    */   public static final int MUL = 23;
/*  34:    */   public static final int DIV = 24;
/*  35:    */   public static final int MOD = 25;
/*  36:    */   public static final int NOT = 26;
/*  37:    */   public static final int BITNOT = 27;
/*  38:    */   public static final int POS = 28;
/*  39:    */   public static final int NEG = 29;
/*  40:    */   public static final int NEW = 30;
/*  41:    */   public static final int DELPROP = 31;
/*  42:    */   public static final int TYPEOF = 32;
/*  43:    */   public static final int GETPROP = 33;
/*  44:    */   public static final int GETPROPNOWARN = 34;
/*  45:    */   public static final int SETPROP = 35;
/*  46:    */   public static final int GETELEM = 36;
/*  47:    */   public static final int SETELEM = 37;
/*  48:    */   public static final int CALL = 38;
/*  49:    */   public static final int NAME = 39;
/*  50:    */   public static final int NUMBER = 40;
/*  51:    */   public static final int STRING = 41;
/*  52:    */   public static final int NULL = 42;
/*  53:    */   public static final int THIS = 43;
/*  54:    */   public static final int FALSE = 44;
/*  55:    */   public static final int TRUE = 45;
/*  56:    */   public static final int SHEQ = 46;
/*  57:    */   public static final int SHNE = 47;
/*  58:    */   public static final int REGEXP = 48;
/*  59:    */   public static final int BINDNAME = 49;
/*  60:    */   public static final int THROW = 50;
/*  61:    */   public static final int RETHROW = 51;
/*  62:    */   public static final int IN = 52;
/*  63:    */   public static final int INSTANCEOF = 53;
/*  64:    */   public static final int LOCAL_LOAD = 54;
/*  65:    */   public static final int GETVAR = 55;
/*  66:    */   public static final int SETVAR = 56;
/*  67:    */   public static final int CATCH_SCOPE = 57;
/*  68:    */   public static final int ENUM_INIT_KEYS = 58;
/*  69:    */   public static final int ENUM_INIT_VALUES = 59;
/*  70:    */   public static final int ENUM_INIT_ARRAY = 60;
/*  71:    */   public static final int ENUM_NEXT = 61;
/*  72:    */   public static final int ENUM_ID = 62;
/*  73:    */   public static final int THISFN = 63;
/*  74:    */   public static final int RETURN_RESULT = 64;
/*  75:    */   public static final int ARRAYLIT = 65;
/*  76:    */   public static final int OBJECTLIT = 66;
/*  77:    */   public static final int GET_REF = 67;
/*  78:    */   public static final int SET_REF = 68;
/*  79:    */   public static final int DEL_REF = 69;
/*  80:    */   public static final int REF_CALL = 70;
/*  81:    */   public static final int REF_SPECIAL = 71;
/*  82:    */   public static final int YIELD = 72;
/*  83:    */   public static final int STRICT_SETNAME = 73;
/*  84:    */   public static final int DEFAULTNAMESPACE = 74;
/*  85:    */   public static final int ESCXMLATTR = 75;
/*  86:    */   public static final int ESCXMLTEXT = 76;
/*  87:    */   public static final int REF_MEMBER = 77;
/*  88:    */   public static final int REF_NS_MEMBER = 78;
/*  89:    */   public static final int REF_NAME = 79;
/*  90:    */   public static final int REF_NS_NAME = 80;
/*  91:    */   public static final int LAST_BYTECODE_TOKEN = 80;
/*  92:    */   public static final int TRY = 81;
/*  93:    */   public static final int SEMI = 82;
/*  94:    */   public static final int LB = 83;
/*  95:    */   public static final int RB = 84;
/*  96:    */   public static final int LC = 85;
/*  97:    */   public static final int RC = 86;
/*  98:    */   public static final int LP = 87;
/*  99:    */   public static final int RP = 88;
/* 100:    */   public static final int COMMA = 89;
/* 101:    */   public static final int ASSIGN = 90;
/* 102:    */   public static final int ASSIGN_BITOR = 91;
/* 103:    */   public static final int ASSIGN_BITXOR = 92;
/* 104:    */   public static final int ASSIGN_BITAND = 93;
/* 105:    */   public static final int ASSIGN_LSH = 94;
/* 106:    */   public static final int ASSIGN_RSH = 95;
/* 107:    */   public static final int ASSIGN_URSH = 96;
/* 108:    */   public static final int ASSIGN_ADD = 97;
/* 109:    */   public static final int ASSIGN_SUB = 98;
/* 110:    */   public static final int ASSIGN_MUL = 99;
/* 111:    */   public static final int ASSIGN_DIV = 100;
/* 112:    */   public static final int ASSIGN_MOD = 101;
/* 113:    */   public static final int FIRST_ASSIGN = 90;
/* 114:    */   public static final int LAST_ASSIGN = 101;
/* 115:    */   public static final int HOOK = 102;
/* 116:    */   public static final int COLON = 103;
/* 117:    */   public static final int OR = 104;
/* 118:    */   public static final int AND = 105;
/* 119:    */   public static final int INC = 106;
/* 120:    */   public static final int DEC = 107;
/* 121:    */   public static final int DOT = 108;
/* 122:    */   public static final int FUNCTION = 109;
/* 123:    */   public static final int EXPORT = 110;
/* 124:    */   public static final int IMPORT = 111;
/* 125:    */   public static final int IF = 112;
/* 126:    */   public static final int ELSE = 113;
/* 127:    */   public static final int SWITCH = 114;
/* 128:    */   public static final int CASE = 115;
/* 129:    */   public static final int DEFAULT = 116;
/* 130:    */   public static final int WHILE = 117;
/* 131:    */   public static final int DO = 118;
/* 132:    */   public static final int FOR = 119;
/* 133:    */   public static final int BREAK = 120;
/* 134:    */   public static final int CONTINUE = 121;
/* 135:    */   public static final int VAR = 122;
/* 136:    */   public static final int WITH = 123;
/* 137:    */   public static final int CATCH = 124;
/* 138:    */   public static final int FINALLY = 125;
/* 139:    */   public static final int VOID = 126;
/* 140:    */   public static final int RESERVED = 127;
/* 141:    */   public static final int EMPTY = 128;
/* 142:    */   public static final int BLOCK = 129;
/* 143:    */   public static final int LABEL = 130;
/* 144:    */   public static final int TARGET = 131;
/* 145:    */   public static final int LOOP = 132;
/* 146:    */   public static final int EXPR_VOID = 133;
/* 147:    */   public static final int EXPR_RESULT = 134;
/* 148:    */   public static final int JSR = 135;
/* 149:    */   public static final int SCRIPT = 136;
/* 150:    */   public static final int TYPEOFNAME = 137;
/* 151:    */   public static final int USE_STACK = 138;
/* 152:    */   public static final int SETPROP_OP = 139;
/* 153:    */   public static final int SETELEM_OP = 140;
/* 154:    */   public static final int LOCAL_BLOCK = 141;
/* 155:    */   public static final int SET_REF_OP = 142;
/* 156:    */   public static final int DOTDOT = 143;
/* 157:    */   public static final int COLONCOLON = 144;
/* 158:    */   public static final int XML = 145;
/* 159:    */   public static final int DOTQUERY = 146;
/* 160:    */   public static final int XMLATTR = 147;
/* 161:    */   public static final int XMLEND = 148;
/* 162:    */   public static final int TO_OBJECT = 149;
/* 163:    */   public static final int TO_DOUBLE = 150;
/* 164:    */   public static final int GET = 151;
/* 165:    */   public static final int SET = 152;
/* 166:    */   public static final int LET = 153;
/* 167:    */   public static final int CONST = 154;
/* 168:    */   public static final int SETCONST = 155;
/* 169:    */   public static final int SETCONSTVAR = 156;
/* 170:    */   public static final int ARRAYCOMP = 157;
/* 171:    */   public static final int LETEXPR = 158;
/* 172:    */   public static final int WITHEXPR = 159;
/* 173:    */   public static final int DEBUGGER = 160;
/* 174:    */   public static final int COMMENT = 161;
/* 175:    */   public static final int LAST_TOKEN = 162;
/* 176:    */   
/* 177:    */   public static enum CommentType
/* 178:    */   {
/* 179: 60 */     LINE,  BLOCK_COMMENT,  JSDOC,  HTML;
/* 180:    */     
/* 181:    */     private CommentType() {}
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static String name(int token)
/* 185:    */   {
/* 186:276 */     return String.valueOf(token);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static String typeToName(int token)
/* 190:    */   {
/* 191:288 */     switch (token)
/* 192:    */     {
/* 193:    */     case -1: 
/* 194:289 */       return "ERROR";
/* 195:    */     case 0: 
/* 196:290 */       return "EOF";
/* 197:    */     case 1: 
/* 198:291 */       return "EOL";
/* 199:    */     case 2: 
/* 200:292 */       return "ENTERWITH";
/* 201:    */     case 3: 
/* 202:293 */       return "LEAVEWITH";
/* 203:    */     case 4: 
/* 204:294 */       return "RETURN";
/* 205:    */     case 5: 
/* 206:295 */       return "GOTO";
/* 207:    */     case 6: 
/* 208:296 */       return "IFEQ";
/* 209:    */     case 7: 
/* 210:297 */       return "IFNE";
/* 211:    */     case 8: 
/* 212:298 */       return "SETNAME";
/* 213:    */     case 9: 
/* 214:299 */       return "BITOR";
/* 215:    */     case 10: 
/* 216:300 */       return "BITXOR";
/* 217:    */     case 11: 
/* 218:301 */       return "BITAND";
/* 219:    */     case 12: 
/* 220:302 */       return "EQ";
/* 221:    */     case 13: 
/* 222:303 */       return "NE";
/* 223:    */     case 14: 
/* 224:304 */       return "LT";
/* 225:    */     case 15: 
/* 226:305 */       return "LE";
/* 227:    */     case 16: 
/* 228:306 */       return "GT";
/* 229:    */     case 17: 
/* 230:307 */       return "GE";
/* 231:    */     case 18: 
/* 232:308 */       return "LSH";
/* 233:    */     case 19: 
/* 234:309 */       return "RSH";
/* 235:    */     case 20: 
/* 236:310 */       return "URSH";
/* 237:    */     case 21: 
/* 238:311 */       return "ADD";
/* 239:    */     case 22: 
/* 240:312 */       return "SUB";
/* 241:    */     case 23: 
/* 242:313 */       return "MUL";
/* 243:    */     case 24: 
/* 244:314 */       return "DIV";
/* 245:    */     case 25: 
/* 246:315 */       return "MOD";
/* 247:    */     case 26: 
/* 248:316 */       return "NOT";
/* 249:    */     case 27: 
/* 250:317 */       return "BITNOT";
/* 251:    */     case 28: 
/* 252:318 */       return "POS";
/* 253:    */     case 29: 
/* 254:319 */       return "NEG";
/* 255:    */     case 30: 
/* 256:320 */       return "NEW";
/* 257:    */     case 31: 
/* 258:321 */       return "DELPROP";
/* 259:    */     case 32: 
/* 260:322 */       return "TYPEOF";
/* 261:    */     case 33: 
/* 262:323 */       return "GETPROP";
/* 263:    */     case 34: 
/* 264:324 */       return "GETPROPNOWARN";
/* 265:    */     case 35: 
/* 266:325 */       return "SETPROP";
/* 267:    */     case 36: 
/* 268:326 */       return "GETELEM";
/* 269:    */     case 37: 
/* 270:327 */       return "SETELEM";
/* 271:    */     case 38: 
/* 272:328 */       return "CALL";
/* 273:    */     case 39: 
/* 274:329 */       return "NAME";
/* 275:    */     case 40: 
/* 276:330 */       return "NUMBER";
/* 277:    */     case 41: 
/* 278:331 */       return "STRING";
/* 279:    */     case 42: 
/* 280:332 */       return "NULL";
/* 281:    */     case 43: 
/* 282:333 */       return "THIS";
/* 283:    */     case 44: 
/* 284:334 */       return "FALSE";
/* 285:    */     case 45: 
/* 286:335 */       return "TRUE";
/* 287:    */     case 46: 
/* 288:336 */       return "SHEQ";
/* 289:    */     case 47: 
/* 290:337 */       return "SHNE";
/* 291:    */     case 48: 
/* 292:338 */       return "REGEXP";
/* 293:    */     case 49: 
/* 294:339 */       return "BINDNAME";
/* 295:    */     case 50: 
/* 296:340 */       return "THROW";
/* 297:    */     case 51: 
/* 298:341 */       return "RETHROW";
/* 299:    */     case 52: 
/* 300:342 */       return "IN";
/* 301:    */     case 53: 
/* 302:343 */       return "INSTANCEOF";
/* 303:    */     case 54: 
/* 304:344 */       return "LOCAL_LOAD";
/* 305:    */     case 55: 
/* 306:345 */       return "GETVAR";
/* 307:    */     case 56: 
/* 308:346 */       return "SETVAR";
/* 309:    */     case 57: 
/* 310:347 */       return "CATCH_SCOPE";
/* 311:    */     case 58: 
/* 312:348 */       return "ENUM_INIT_KEYS";
/* 313:    */     case 59: 
/* 314:349 */       return "ENUM_INIT_VALUES";
/* 315:    */     case 60: 
/* 316:350 */       return "ENUM_INIT_ARRAY";
/* 317:    */     case 61: 
/* 318:351 */       return "ENUM_NEXT";
/* 319:    */     case 62: 
/* 320:352 */       return "ENUM_ID";
/* 321:    */     case 63: 
/* 322:353 */       return "THISFN";
/* 323:    */     case 64: 
/* 324:354 */       return "RETURN_RESULT";
/* 325:    */     case 65: 
/* 326:355 */       return "ARRAYLIT";
/* 327:    */     case 66: 
/* 328:356 */       return "OBJECTLIT";
/* 329:    */     case 67: 
/* 330:357 */       return "GET_REF";
/* 331:    */     case 68: 
/* 332:358 */       return "SET_REF";
/* 333:    */     case 69: 
/* 334:359 */       return "DEL_REF";
/* 335:    */     case 70: 
/* 336:360 */       return "REF_CALL";
/* 337:    */     case 71: 
/* 338:361 */       return "REF_SPECIAL";
/* 339:    */     case 74: 
/* 340:362 */       return "DEFAULTNAMESPACE";
/* 341:    */     case 76: 
/* 342:363 */       return "ESCXMLTEXT";
/* 343:    */     case 75: 
/* 344:364 */       return "ESCXMLATTR";
/* 345:    */     case 77: 
/* 346:365 */       return "REF_MEMBER";
/* 347:    */     case 78: 
/* 348:366 */       return "REF_NS_MEMBER";
/* 349:    */     case 79: 
/* 350:367 */       return "REF_NAME";
/* 351:    */     case 80: 
/* 352:368 */       return "REF_NS_NAME";
/* 353:    */     case 81: 
/* 354:369 */       return "TRY";
/* 355:    */     case 82: 
/* 356:370 */       return "SEMI";
/* 357:    */     case 83: 
/* 358:371 */       return "LB";
/* 359:    */     case 84: 
/* 360:372 */       return "RB";
/* 361:    */     case 85: 
/* 362:373 */       return "LC";
/* 363:    */     case 86: 
/* 364:374 */       return "RC";
/* 365:    */     case 87: 
/* 366:375 */       return "LP";
/* 367:    */     case 88: 
/* 368:376 */       return "RP";
/* 369:    */     case 89: 
/* 370:377 */       return "COMMA";
/* 371:    */     case 90: 
/* 372:378 */       return "ASSIGN";
/* 373:    */     case 91: 
/* 374:379 */       return "ASSIGN_BITOR";
/* 375:    */     case 92: 
/* 376:380 */       return "ASSIGN_BITXOR";
/* 377:    */     case 93: 
/* 378:381 */       return "ASSIGN_BITAND";
/* 379:    */     case 94: 
/* 380:382 */       return "ASSIGN_LSH";
/* 381:    */     case 95: 
/* 382:383 */       return "ASSIGN_RSH";
/* 383:    */     case 96: 
/* 384:384 */       return "ASSIGN_URSH";
/* 385:    */     case 97: 
/* 386:385 */       return "ASSIGN_ADD";
/* 387:    */     case 98: 
/* 388:386 */       return "ASSIGN_SUB";
/* 389:    */     case 99: 
/* 390:387 */       return "ASSIGN_MUL";
/* 391:    */     case 100: 
/* 392:388 */       return "ASSIGN_DIV";
/* 393:    */     case 101: 
/* 394:389 */       return "ASSIGN_MOD";
/* 395:    */     case 102: 
/* 396:390 */       return "HOOK";
/* 397:    */     case 103: 
/* 398:391 */       return "COLON";
/* 399:    */     case 104: 
/* 400:392 */       return "OR";
/* 401:    */     case 105: 
/* 402:393 */       return "AND";
/* 403:    */     case 106: 
/* 404:394 */       return "INC";
/* 405:    */     case 107: 
/* 406:395 */       return "DEC";
/* 407:    */     case 108: 
/* 408:396 */       return "DOT";
/* 409:    */     case 109: 
/* 410:397 */       return "FUNCTION";
/* 411:    */     case 110: 
/* 412:398 */       return "EXPORT";
/* 413:    */     case 111: 
/* 414:399 */       return "IMPORT";
/* 415:    */     case 112: 
/* 416:400 */       return "IF";
/* 417:    */     case 113: 
/* 418:401 */       return "ELSE";
/* 419:    */     case 114: 
/* 420:402 */       return "SWITCH";
/* 421:    */     case 115: 
/* 422:403 */       return "CASE";
/* 423:    */     case 116: 
/* 424:404 */       return "DEFAULT";
/* 425:    */     case 117: 
/* 426:405 */       return "WHILE";
/* 427:    */     case 118: 
/* 428:406 */       return "DO";
/* 429:    */     case 119: 
/* 430:407 */       return "FOR";
/* 431:    */     case 120: 
/* 432:408 */       return "BREAK";
/* 433:    */     case 121: 
/* 434:409 */       return "CONTINUE";
/* 435:    */     case 122: 
/* 436:410 */       return "VAR";
/* 437:    */     case 123: 
/* 438:411 */       return "WITH";
/* 439:    */     case 124: 
/* 440:412 */       return "CATCH";
/* 441:    */     case 125: 
/* 442:413 */       return "FINALLY";
/* 443:    */     case 126: 
/* 444:414 */       return "VOID";
/* 445:    */     case 127: 
/* 446:415 */       return "RESERVED";
/* 447:    */     case 128: 
/* 448:416 */       return "EMPTY";
/* 449:    */     case 129: 
/* 450:417 */       return "BLOCK";
/* 451:    */     case 130: 
/* 452:418 */       return "LABEL";
/* 453:    */     case 131: 
/* 454:419 */       return "TARGET";
/* 455:    */     case 132: 
/* 456:420 */       return "LOOP";
/* 457:    */     case 133: 
/* 458:421 */       return "EXPR_VOID";
/* 459:    */     case 134: 
/* 460:422 */       return "EXPR_RESULT";
/* 461:    */     case 135: 
/* 462:423 */       return "JSR";
/* 463:    */     case 136: 
/* 464:424 */       return "SCRIPT";
/* 465:    */     case 137: 
/* 466:425 */       return "TYPEOFNAME";
/* 467:    */     case 138: 
/* 468:426 */       return "USE_STACK";
/* 469:    */     case 139: 
/* 470:427 */       return "SETPROP_OP";
/* 471:    */     case 140: 
/* 472:428 */       return "SETELEM_OP";
/* 473:    */     case 141: 
/* 474:429 */       return "LOCAL_BLOCK";
/* 475:    */     case 142: 
/* 476:430 */       return "SET_REF_OP";
/* 477:    */     case 143: 
/* 478:431 */       return "DOTDOT";
/* 479:    */     case 144: 
/* 480:432 */       return "COLONCOLON";
/* 481:    */     case 145: 
/* 482:433 */       return "XML";
/* 483:    */     case 146: 
/* 484:434 */       return "DOTQUERY";
/* 485:    */     case 147: 
/* 486:435 */       return "XMLATTR";
/* 487:    */     case 148: 
/* 488:436 */       return "XMLEND";
/* 489:    */     case 149: 
/* 490:437 */       return "TO_OBJECT";
/* 491:    */     case 150: 
/* 492:438 */       return "TO_DOUBLE";
/* 493:    */     case 151: 
/* 494:439 */       return "GET";
/* 495:    */     case 152: 
/* 496:440 */       return "SET";
/* 497:    */     case 153: 
/* 498:441 */       return "LET";
/* 499:    */     case 72: 
/* 500:442 */       return "YIELD";
/* 501:    */     case 154: 
/* 502:443 */       return "CONST";
/* 503:    */     case 155: 
/* 504:444 */       return "SETCONST";
/* 505:    */     case 157: 
/* 506:445 */       return "ARRAYCOMP";
/* 507:    */     case 159: 
/* 508:446 */       return "WITHEXPR";
/* 509:    */     case 158: 
/* 510:447 */       return "LETEXPR";
/* 511:    */     case 160: 
/* 512:448 */       return "DEBUGGER";
/* 513:    */     case 161: 
/* 514:449 */       return "COMMENT";
/* 515:    */     }
/* 516:453 */     throw new IllegalStateException(String.valueOf(token));
/* 517:    */   }
/* 518:    */   
/* 519:    */   public static String keywordToName(int token)
/* 520:    */   {
/* 521:463 */     switch (token)
/* 522:    */     {
/* 523:    */     case 120: 
/* 524:464 */       return "break";
/* 525:    */     case 115: 
/* 526:465 */       return "case";
/* 527:    */     case 121: 
/* 528:466 */       return "continue";
/* 529:    */     case 116: 
/* 530:467 */       return "default";
/* 531:    */     case 31: 
/* 532:468 */       return "delete";
/* 533:    */     case 118: 
/* 534:469 */       return "do";
/* 535:    */     case 113: 
/* 536:470 */       return "else";
/* 537:    */     case 44: 
/* 538:471 */       return "false";
/* 539:    */     case 119: 
/* 540:472 */       return "for";
/* 541:    */     case 109: 
/* 542:473 */       return "function";
/* 543:    */     case 112: 
/* 544:474 */       return "if";
/* 545:    */     case 52: 
/* 546:475 */       return "in";
/* 547:    */     case 153: 
/* 548:476 */       return "let";
/* 549:    */     case 30: 
/* 550:477 */       return "new";
/* 551:    */     case 42: 
/* 552:478 */       return "null";
/* 553:    */     case 4: 
/* 554:479 */       return "return";
/* 555:    */     case 114: 
/* 556:480 */       return "switch";
/* 557:    */     case 43: 
/* 558:481 */       return "this";
/* 559:    */     case 45: 
/* 560:482 */       return "true";
/* 561:    */     case 32: 
/* 562:483 */       return "typeof";
/* 563:    */     case 122: 
/* 564:484 */       return "var";
/* 565:    */     case 126: 
/* 566:485 */       return "void";
/* 567:    */     case 117: 
/* 568:486 */       return "while";
/* 569:    */     case 123: 
/* 570:487 */       return "with";
/* 571:    */     case 72: 
/* 572:488 */       return "yield";
/* 573:    */     case 124: 
/* 574:489 */       return "catch";
/* 575:    */     case 154: 
/* 576:490 */       return "const";
/* 577:    */     case 160: 
/* 578:491 */       return "debugger";
/* 579:    */     case 125: 
/* 580:492 */       return "finally";
/* 581:    */     case 53: 
/* 582:493 */       return "instanceof";
/* 583:    */     case 50: 
/* 584:494 */       return "throw";
/* 585:    */     case 81: 
/* 586:495 */       return "try";
/* 587:    */     }
/* 588:496 */     return null;
/* 589:    */   }
/* 590:    */   
/* 591:    */   public static boolean isValidToken(int code)
/* 592:    */   {
/* 593:506 */     return (code >= -1) && (code <= 162);
/* 594:    */   }
/* 595:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Token
 * JD-Core Version:    0.7.0.1
 */
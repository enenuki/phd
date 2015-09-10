/*   1:    */ package com.steadystate.css.parser;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.w3c.css.sac.LexicalUnit;
/*   5:    */ import org.w3c.css.sac.Locator;
/*   6:    */ 
/*   7:    */ public class LexicalUnitImpl
/*   8:    */   implements LexicalUnit, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = -7260032046960116891L;
/*  11:    */   private short lexicalUnitType;
/*  12:    */   private LexicalUnit nextLexicalUnit;
/*  13:    */   private LexicalUnit previousLexicalUnit;
/*  14:    */   private float floatValue;
/*  15:    */   private String dimension;
/*  16:    */   private String functionName;
/*  17:    */   private LexicalUnit parameters;
/*  18:    */   private String stringValue;
/*  19:    */   private Locator locator;
/*  20:    */   
/*  21:    */   public Locator getLocator()
/*  22:    */   {
/*  23:102 */     return this.locator;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setLocator(Locator locator)
/*  27:    */   {
/*  28:107 */     this.locator = locator;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setLexicalUnitType(short type)
/*  32:    */   {
/*  33:112 */     this.lexicalUnitType = type;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setNextLexicalUnit(LexicalUnit next)
/*  37:    */   {
/*  38:117 */     this.nextLexicalUnit = next;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setPreviousLexicalUnit(LexicalUnit prev)
/*  42:    */   {
/*  43:122 */     this.previousLexicalUnit = prev;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setFloatValue(float floatVal)
/*  47:    */   {
/*  48:127 */     this.floatValue = floatVal;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getDimension()
/*  52:    */   {
/*  53:132 */     return this.dimension;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setDimension(String dimension)
/*  57:    */   {
/*  58:137 */     this.dimension = dimension;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setFunctionName(String function)
/*  62:    */   {
/*  63:142 */     this.functionName = function;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setParameters(LexicalUnit params)
/*  67:    */   {
/*  68:147 */     this.parameters = params;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setStringValue(String stringVal)
/*  72:    */   {
/*  73:152 */     this.stringValue = stringVal;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected LexicalUnitImpl(LexicalUnit previous, short type)
/*  77:    */   {
/*  78:157 */     this.lexicalUnitType = type;
/*  79:158 */     this.previousLexicalUnit = previous;
/*  80:159 */     if (this.previousLexicalUnit != null) {
/*  81:160 */       ((LexicalUnitImpl)this.previousLexicalUnit).nextLexicalUnit = this;
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected LexicalUnitImpl(LexicalUnit previous, int value)
/*  86:    */   {
/*  87:168 */     this(previous, (short)13);
/*  88:    */     
/*  89:170 */     this.floatValue = value;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected LexicalUnitImpl(LexicalUnit previous, short type, float value)
/*  93:    */   {
/*  94:177 */     this(previous, type);
/*  95:178 */     this.floatValue = value;
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected LexicalUnitImpl(LexicalUnit previous, short type, String dimension, float value)
/*  99:    */   {
/* 100:189 */     this(previous, type);
/* 101:190 */     this.dimension = dimension;
/* 102:191 */     this.floatValue = value;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected LexicalUnitImpl(LexicalUnit previous, short type, String value)
/* 106:    */   {
/* 107:198 */     this(previous, type);
/* 108:199 */     this.stringValue = value;
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected LexicalUnitImpl(LexicalUnit previous, short type, String name, LexicalUnit params)
/* 112:    */   {
/* 113:210 */     this(previous, type);
/* 114:211 */     this.functionName = name;
/* 115:212 */     this.parameters = params;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public LexicalUnitImpl() {}
/* 119:    */   
/* 120:    */   public short getLexicalUnitType()
/* 121:    */   {
/* 122:221 */     return this.lexicalUnitType;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public LexicalUnit getNextLexicalUnit()
/* 126:    */   {
/* 127:225 */     return this.nextLexicalUnit;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public LexicalUnit getPreviousLexicalUnit()
/* 131:    */   {
/* 132:229 */     return this.previousLexicalUnit;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getIntegerValue()
/* 136:    */   {
/* 137:234 */     return (int)this.floatValue;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public float getFloatValue()
/* 141:    */   {
/* 142:238 */     return this.floatValue;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getDimensionUnitText()
/* 146:    */   {
/* 147:242 */     switch (this.lexicalUnitType)
/* 148:    */     {
/* 149:    */     case 15: 
/* 150:244 */       return "em";
/* 151:    */     case 16: 
/* 152:246 */       return "ex";
/* 153:    */     case 17: 
/* 154:248 */       return "px";
/* 155:    */     case 18: 
/* 156:250 */       return "in";
/* 157:    */     case 19: 
/* 158:252 */       return "cm";
/* 159:    */     case 20: 
/* 160:254 */       return "mm";
/* 161:    */     case 21: 
/* 162:256 */       return "pt";
/* 163:    */     case 22: 
/* 164:258 */       return "pc";
/* 165:    */     case 23: 
/* 166:260 */       return "%";
/* 167:    */     case 28: 
/* 168:262 */       return "deg";
/* 169:    */     case 29: 
/* 170:264 */       return "grad";
/* 171:    */     case 30: 
/* 172:266 */       return "rad";
/* 173:    */     case 31: 
/* 174:268 */       return "ms";
/* 175:    */     case 32: 
/* 176:270 */       return "s";
/* 177:    */     case 33: 
/* 178:272 */       return "Hz";
/* 179:    */     case 34: 
/* 180:274 */       return "kHz";
/* 181:    */     case 42: 
/* 182:276 */       return this.dimension;
/* 183:    */     }
/* 184:278 */     return "";
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String getFunctionName()
/* 188:    */   {
/* 189:282 */     return this.functionName;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public LexicalUnit getParameters()
/* 193:    */   {
/* 194:286 */     return this.parameters;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getStringValue()
/* 198:    */   {
/* 199:290 */     return this.stringValue;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public LexicalUnit getSubValues()
/* 203:    */   {
/* 204:294 */     return this.parameters;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String toString()
/* 208:    */   {
/* 209:298 */     StringBuilder sb = new StringBuilder();
/* 210:299 */     switch (this.lexicalUnitType)
/* 211:    */     {
/* 212:    */     case 0: 
/* 213:301 */       sb.append(",");
/* 214:302 */       break;
/* 215:    */     case 1: 
/* 216:304 */       sb.append("+");
/* 217:305 */       break;
/* 218:    */     case 2: 
/* 219:307 */       sb.append("-");
/* 220:308 */       break;
/* 221:    */     case 3: 
/* 222:310 */       sb.append("*");
/* 223:311 */       break;
/* 224:    */     case 4: 
/* 225:313 */       sb.append("/");
/* 226:314 */       break;
/* 227:    */     case 5: 
/* 228:316 */       sb.append("%");
/* 229:317 */       break;
/* 230:    */     case 6: 
/* 231:319 */       sb.append("^");
/* 232:320 */       break;
/* 233:    */     case 7: 
/* 234:322 */       sb.append("<");
/* 235:323 */       break;
/* 236:    */     case 8: 
/* 237:325 */       sb.append(">");
/* 238:326 */       break;
/* 239:    */     case 9: 
/* 240:328 */       sb.append("<=");
/* 241:329 */       break;
/* 242:    */     case 10: 
/* 243:331 */       sb.append(">=");
/* 244:332 */       break;
/* 245:    */     case 11: 
/* 246:334 */       sb.append("~");
/* 247:335 */       break;
/* 248:    */     case 12: 
/* 249:337 */       sb.append("inherit");
/* 250:338 */       break;
/* 251:    */     case 13: 
/* 252:340 */       sb.append(String.valueOf(getIntegerValue()));
/* 253:341 */       break;
/* 254:    */     case 14: 
/* 255:343 */       sb.append(trimFloat(getFloatValue()));
/* 256:344 */       break;
/* 257:    */     case 15: 
/* 258:    */     case 16: 
/* 259:    */     case 17: 
/* 260:    */     case 18: 
/* 261:    */     case 19: 
/* 262:    */     case 20: 
/* 263:    */     case 21: 
/* 264:    */     case 22: 
/* 265:    */     case 23: 
/* 266:    */     case 28: 
/* 267:    */     case 29: 
/* 268:    */     case 30: 
/* 269:    */     case 31: 
/* 270:    */     case 32: 
/* 271:    */     case 33: 
/* 272:    */     case 34: 
/* 273:    */     case 42: 
/* 274:362 */       sb.append(trimFloat(getFloatValue())).append(getDimensionUnitText());
/* 275:    */       
/* 276:364 */       break;
/* 277:    */     case 24: 
/* 278:366 */       sb.append("url(").append(getStringValue()).append(")");
/* 279:367 */       break;
/* 280:    */     case 25: 
/* 281:369 */       sb.append("counter(");
/* 282:370 */       appendParams(sb, this.parameters);
/* 283:371 */       sb.append(")");
/* 284:372 */       break;
/* 285:    */     case 26: 
/* 286:374 */       sb.append("counters(");
/* 287:375 */       appendParams(sb, this.parameters);
/* 288:376 */       sb.append(")");
/* 289:377 */       break;
/* 290:    */     case 27: 
/* 291:379 */       sb.append("rgb(");
/* 292:380 */       appendParams(sb, this.parameters);
/* 293:381 */       sb.append(")");
/* 294:382 */       break;
/* 295:    */     case 35: 
/* 296:384 */       sb.append(getStringValue());
/* 297:385 */       break;
/* 298:    */     case 36: 
/* 299:387 */       sb.append("\"").append(getStringValue()).append("\"");
/* 300:388 */       break;
/* 301:    */     case 37: 
/* 302:390 */       sb.append("attr(");
/* 303:391 */       appendParams(sb, this.parameters);
/* 304:392 */       sb.append(")");
/* 305:393 */       break;
/* 306:    */     case 38: 
/* 307:395 */       sb.append("rect(");
/* 308:396 */       appendParams(sb, this.parameters);
/* 309:397 */       sb.append(")");
/* 310:398 */       break;
/* 311:    */     case 39: 
/* 312:400 */       sb.append(getStringValue());
/* 313:401 */       break;
/* 314:    */     case 40: 
/* 315:403 */       sb.append(getStringValue());
/* 316:404 */       break;
/* 317:    */     case 41: 
/* 318:406 */       sb.append(getFunctionName()).append('(');
/* 319:407 */       appendParams(sb, this.parameters);
/* 320:408 */       sb.append(")");
/* 321:    */     }
/* 322:411 */     return sb.toString();
/* 323:    */   }
/* 324:    */   
/* 325:    */   public String toDebugString()
/* 326:    */   {
/* 327:415 */     StringBuilder sb = new StringBuilder();
/* 328:416 */     switch (this.lexicalUnitType)
/* 329:    */     {
/* 330:    */     case 0: 
/* 331:418 */       sb.append("SAC_OPERATOR_COMMA");
/* 332:419 */       break;
/* 333:    */     case 1: 
/* 334:421 */       sb.append("SAC_OPERATOR_PLUS");
/* 335:422 */       break;
/* 336:    */     case 2: 
/* 337:424 */       sb.append("SAC_OPERATOR_MINUS");
/* 338:425 */       break;
/* 339:    */     case 3: 
/* 340:427 */       sb.append("SAC_OPERATOR_MULTIPLY");
/* 341:428 */       break;
/* 342:    */     case 4: 
/* 343:430 */       sb.append("SAC_OPERATOR_SLASH");
/* 344:431 */       break;
/* 345:    */     case 5: 
/* 346:433 */       sb.append("SAC_OPERATOR_MOD");
/* 347:434 */       break;
/* 348:    */     case 6: 
/* 349:436 */       sb.append("SAC_OPERATOR_EXP");
/* 350:437 */       break;
/* 351:    */     case 7: 
/* 352:439 */       sb.append("SAC_OPERATOR_LT");
/* 353:440 */       break;
/* 354:    */     case 8: 
/* 355:442 */       sb.append("SAC_OPERATOR_GT");
/* 356:443 */       break;
/* 357:    */     case 9: 
/* 358:445 */       sb.append("SAC_OPERATOR_LE");
/* 359:446 */       break;
/* 360:    */     case 10: 
/* 361:448 */       sb.append("SAC_OPERATOR_GE");
/* 362:449 */       break;
/* 363:    */     case 11: 
/* 364:451 */       sb.append("SAC_OPERATOR_TILDE");
/* 365:452 */       break;
/* 366:    */     case 12: 
/* 367:454 */       sb.append("SAC_INHERIT");
/* 368:455 */       break;
/* 369:    */     case 13: 
/* 370:457 */       sb.append("SAC_INTEGER(").append(String.valueOf(getIntegerValue())).append(")");
/* 371:    */       
/* 372:    */ 
/* 373:460 */       break;
/* 374:    */     case 14: 
/* 375:462 */       sb.append("SAC_REAL(").append(trimFloat(getFloatValue())).append(")");
/* 376:    */       
/* 377:    */ 
/* 378:465 */       break;
/* 379:    */     case 15: 
/* 380:467 */       sb.append("SAC_EM(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 381:    */       
/* 382:    */ 
/* 383:    */ 
/* 384:471 */       break;
/* 385:    */     case 16: 
/* 386:473 */       sb.append("SAC_EX(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 387:    */       
/* 388:    */ 
/* 389:    */ 
/* 390:477 */       break;
/* 391:    */     case 17: 
/* 392:479 */       sb.append("SAC_PIXEL(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 393:    */       
/* 394:    */ 
/* 395:    */ 
/* 396:483 */       break;
/* 397:    */     case 18: 
/* 398:485 */       sb.append("SAC_INCH(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 399:    */       
/* 400:    */ 
/* 401:    */ 
/* 402:489 */       break;
/* 403:    */     case 19: 
/* 404:491 */       sb.append("SAC_CENTIMETER(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 405:    */       
/* 406:    */ 
/* 407:    */ 
/* 408:495 */       break;
/* 409:    */     case 20: 
/* 410:497 */       sb.append("SAC_MILLIMETER(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 411:    */       
/* 412:    */ 
/* 413:    */ 
/* 414:501 */       break;
/* 415:    */     case 21: 
/* 416:503 */       sb.append("SAC_POINT(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 417:    */       
/* 418:    */ 
/* 419:    */ 
/* 420:507 */       break;
/* 421:    */     case 22: 
/* 422:509 */       sb.append("SAC_PICA(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 423:    */       
/* 424:    */ 
/* 425:    */ 
/* 426:513 */       break;
/* 427:    */     case 23: 
/* 428:515 */       sb.append("SAC_PERCENTAGE(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 429:    */       
/* 430:    */ 
/* 431:    */ 
/* 432:519 */       break;
/* 433:    */     case 28: 
/* 434:521 */       sb.append("SAC_DEGREE(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 435:    */       
/* 436:    */ 
/* 437:    */ 
/* 438:525 */       break;
/* 439:    */     case 29: 
/* 440:527 */       sb.append("SAC_GRADIAN(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 441:    */       
/* 442:    */ 
/* 443:    */ 
/* 444:531 */       break;
/* 445:    */     case 30: 
/* 446:533 */       sb.append("SAC_RADIAN(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 447:    */       
/* 448:    */ 
/* 449:    */ 
/* 450:537 */       break;
/* 451:    */     case 31: 
/* 452:539 */       sb.append("SAC_MILLISECOND(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 453:    */       
/* 454:    */ 
/* 455:    */ 
/* 456:543 */       break;
/* 457:    */     case 32: 
/* 458:545 */       sb.append("SAC_SECOND(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 459:    */       
/* 460:    */ 
/* 461:    */ 
/* 462:549 */       break;
/* 463:    */     case 33: 
/* 464:551 */       sb.append("SAC_HERTZ(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 465:    */       
/* 466:    */ 
/* 467:    */ 
/* 468:555 */       break;
/* 469:    */     case 34: 
/* 470:557 */       sb.append("SAC_KILOHERTZ(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 471:    */       
/* 472:    */ 
/* 473:    */ 
/* 474:561 */       break;
/* 475:    */     case 42: 
/* 476:563 */       sb.append("SAC_DIMENSION(").append(trimFloat(getFloatValue())).append(getDimensionUnitText()).append(")");
/* 477:    */       
/* 478:    */ 
/* 479:    */ 
/* 480:567 */       break;
/* 481:    */     case 24: 
/* 482:569 */       sb.append("SAC_URI(url(").append(getStringValue()).append("))");
/* 483:    */       
/* 484:    */ 
/* 485:572 */       break;
/* 486:    */     case 25: 
/* 487:574 */       sb.append("SAC_COUNTER_FUNCTION(counter(");
/* 488:575 */       appendParams(sb, this.parameters);
/* 489:576 */       sb.append("))");
/* 490:577 */       break;
/* 491:    */     case 26: 
/* 492:579 */       sb.append("SAC_COUNTERS_FUNCTION(counters(");
/* 493:580 */       appendParams(sb, this.parameters);
/* 494:581 */       sb.append("))");
/* 495:582 */       break;
/* 496:    */     case 27: 
/* 497:584 */       sb.append("SAC_RGBCOLOR(rgb(");
/* 498:585 */       appendParams(sb, this.parameters);
/* 499:586 */       sb.append("))");
/* 500:587 */       break;
/* 501:    */     case 35: 
/* 502:589 */       sb.append("SAC_IDENT(").append(getStringValue()).append(")");
/* 503:    */       
/* 504:    */ 
/* 505:592 */       break;
/* 506:    */     case 36: 
/* 507:594 */       sb.append("SAC_STRING_VALUE(\"").append(getStringValue()).append("\")");
/* 508:    */       
/* 509:    */ 
/* 510:597 */       break;
/* 511:    */     case 37: 
/* 512:599 */       sb.append("SAC_ATTR(attr(");
/* 513:600 */       appendParams(sb, this.parameters);
/* 514:601 */       sb.append("))");
/* 515:602 */       break;
/* 516:    */     case 38: 
/* 517:604 */       sb.append("SAC_RECT_FUNCTION(rect(");
/* 518:605 */       appendParams(sb, this.parameters);
/* 519:606 */       sb.append("))");
/* 520:607 */       break;
/* 521:    */     case 39: 
/* 522:609 */       sb.append("SAC_UNICODERANGE(").append(getStringValue()).append(")");
/* 523:    */       
/* 524:    */ 
/* 525:612 */       break;
/* 526:    */     case 40: 
/* 527:614 */       sb.append("SAC_SUB_EXPRESSION(").append(getStringValue()).append(")");
/* 528:    */       
/* 529:    */ 
/* 530:617 */       break;
/* 531:    */     case 41: 
/* 532:619 */       sb.append("SAC_FUNCTION(").append(getFunctionName()).append("(");
/* 533:    */       
/* 534:    */ 
/* 535:622 */       appendParams(sb, this.parameters);
/* 536:623 */       sb.append("))");
/* 537:    */     }
/* 538:626 */     return sb.toString();
/* 539:    */   }
/* 540:    */   
/* 541:    */   private void appendParams(StringBuilder sb, LexicalUnit first)
/* 542:    */   {
/* 543:630 */     LexicalUnit l = first;
/* 544:631 */     while (l != null)
/* 545:    */     {
/* 546:632 */       sb.append(l.toString());
/* 547:633 */       l = l.getNextLexicalUnit();
/* 548:    */     }
/* 549:    */   }
/* 550:    */   
/* 551:    */   private String trimFloat(float f)
/* 552:    */   {
/* 553:638 */     String s = String.valueOf(getFloatValue());
/* 554:639 */     return f - (int)f != 0.0F ? s : s.substring(0, s.length() - 2);
/* 555:    */   }
/* 556:    */   
/* 557:    */   public static LexicalUnit createNumber(LexicalUnit prev, int i)
/* 558:    */   {
/* 559:650 */     return new LexicalUnitImpl(prev, i);
/* 560:    */   }
/* 561:    */   
/* 562:    */   public static LexicalUnit createNumber(LexicalUnit prev, float f)
/* 563:    */   {
/* 564:654 */     return new LexicalUnitImpl(prev, (short)14, f);
/* 565:    */   }
/* 566:    */   
/* 567:    */   public static LexicalUnit createPercentage(LexicalUnit prev, float f)
/* 568:    */   {
/* 569:658 */     return new LexicalUnitImpl(prev, (short)23, f);
/* 570:    */   }
/* 571:    */   
/* 572:    */   public static LexicalUnit createPixel(LexicalUnit prev, float f)
/* 573:    */   {
/* 574:662 */     return new LexicalUnitImpl(prev, (short)17, f);
/* 575:    */   }
/* 576:    */   
/* 577:    */   public static LexicalUnit createCentimeter(LexicalUnit prev, float f)
/* 578:    */   {
/* 579:666 */     return new LexicalUnitImpl(prev, (short)19, f);
/* 580:    */   }
/* 581:    */   
/* 582:    */   public static LexicalUnit createMillimeter(LexicalUnit prev, float f)
/* 583:    */   {
/* 584:670 */     return new LexicalUnitImpl(prev, (short)20, f);
/* 585:    */   }
/* 586:    */   
/* 587:    */   public static LexicalUnit createInch(LexicalUnit prev, float f)
/* 588:    */   {
/* 589:674 */     return new LexicalUnitImpl(prev, (short)18, f);
/* 590:    */   }
/* 591:    */   
/* 592:    */   public static LexicalUnit createPoint(LexicalUnit prev, float f)
/* 593:    */   {
/* 594:678 */     return new LexicalUnitImpl(prev, (short)21, f);
/* 595:    */   }
/* 596:    */   
/* 597:    */   public static LexicalUnit createPica(LexicalUnit prev, float f)
/* 598:    */   {
/* 599:682 */     return new LexicalUnitImpl(prev, (short)22, f);
/* 600:    */   }
/* 601:    */   
/* 602:    */   public static LexicalUnit createEm(LexicalUnit prev, float f)
/* 603:    */   {
/* 604:686 */     return new LexicalUnitImpl(prev, (short)15, f);
/* 605:    */   }
/* 606:    */   
/* 607:    */   public static LexicalUnit createEx(LexicalUnit prev, float f)
/* 608:    */   {
/* 609:690 */     return new LexicalUnitImpl(prev, (short)16, f);
/* 610:    */   }
/* 611:    */   
/* 612:    */   public static LexicalUnit createDegree(LexicalUnit prev, float f)
/* 613:    */   {
/* 614:694 */     return new LexicalUnitImpl(prev, (short)28, f);
/* 615:    */   }
/* 616:    */   
/* 617:    */   public static LexicalUnit createRadian(LexicalUnit prev, float f)
/* 618:    */   {
/* 619:698 */     return new LexicalUnitImpl(prev, (short)30, f);
/* 620:    */   }
/* 621:    */   
/* 622:    */   public static LexicalUnit createGradian(LexicalUnit prev, float f)
/* 623:    */   {
/* 624:702 */     return new LexicalUnitImpl(prev, (short)29, f);
/* 625:    */   }
/* 626:    */   
/* 627:    */   public static LexicalUnit createMillisecond(LexicalUnit prev, float f)
/* 628:    */   {
/* 629:706 */     return new LexicalUnitImpl(prev, (short)31, f);
/* 630:    */   }
/* 631:    */   
/* 632:    */   public static LexicalUnit createSecond(LexicalUnit prev, float f)
/* 633:    */   {
/* 634:710 */     return new LexicalUnitImpl(prev, (short)32, f);
/* 635:    */   }
/* 636:    */   
/* 637:    */   public static LexicalUnit createHertz(LexicalUnit prev, float f)
/* 638:    */   {
/* 639:714 */     return new LexicalUnitImpl(prev, (short)33, f);
/* 640:    */   }
/* 641:    */   
/* 642:    */   public static LexicalUnit createDimension(LexicalUnit prev, float f, String dim)
/* 643:    */   {
/* 644:718 */     return new LexicalUnitImpl(prev, (short)42, dim, f);
/* 645:    */   }
/* 646:    */   
/* 647:    */   public static LexicalUnit createKiloHertz(LexicalUnit prev, float f)
/* 648:    */   {
/* 649:722 */     return new LexicalUnitImpl(prev, (short)34, f);
/* 650:    */   }
/* 651:    */   
/* 652:    */   public static LexicalUnit createCounter(LexicalUnit prev, LexicalUnit params)
/* 653:    */   {
/* 654:726 */     return new LexicalUnitImpl(prev, (short)25, "counter", params);
/* 655:    */   }
/* 656:    */   
/* 657:    */   public static LexicalUnit createCounters(LexicalUnit prev, LexicalUnit params)
/* 658:    */   {
/* 659:730 */     return new LexicalUnitImpl(prev, (short)26, "counters", params);
/* 660:    */   }
/* 661:    */   
/* 662:    */   public static LexicalUnit createAttr(LexicalUnit prev, LexicalUnit params)
/* 663:    */   {
/* 664:734 */     return new LexicalUnitImpl(prev, (short)37, "attr", params);
/* 665:    */   }
/* 666:    */   
/* 667:    */   public static LexicalUnit createRect(LexicalUnit prev, LexicalUnit params)
/* 668:    */   {
/* 669:738 */     return new LexicalUnitImpl(prev, (short)38, "rect", params);
/* 670:    */   }
/* 671:    */   
/* 672:    */   public static LexicalUnit createRgbColor(LexicalUnit prev, LexicalUnit params)
/* 673:    */   {
/* 674:742 */     return new LexicalUnitImpl(prev, (short)27, "rgb", params);
/* 675:    */   }
/* 676:    */   
/* 677:    */   public static LexicalUnit createFunction(LexicalUnit prev, String name, LexicalUnit params)
/* 678:    */   {
/* 679:746 */     return new LexicalUnitImpl(prev, (short)41, name, params);
/* 680:    */   }
/* 681:    */   
/* 682:    */   public static LexicalUnit createString(LexicalUnit prev, String value)
/* 683:    */   {
/* 684:750 */     return new LexicalUnitImpl(prev, (short)36, value);
/* 685:    */   }
/* 686:    */   
/* 687:    */   public static LexicalUnit createIdent(LexicalUnit prev, String value)
/* 688:    */   {
/* 689:754 */     return new LexicalUnitImpl(prev, (short)35, value);
/* 690:    */   }
/* 691:    */   
/* 692:    */   public static LexicalUnit createURI(LexicalUnit prev, String value)
/* 693:    */   {
/* 694:758 */     return new LexicalUnitImpl(prev, (short)24, value);
/* 695:    */   }
/* 696:    */   
/* 697:    */   public static LexicalUnit createComma(LexicalUnit prev)
/* 698:    */   {
/* 699:762 */     return new LexicalUnitImpl(prev, (short)0);
/* 700:    */   }
/* 701:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.LexicalUnitImpl
 * JD-Core Version:    0.7.0.1
 */
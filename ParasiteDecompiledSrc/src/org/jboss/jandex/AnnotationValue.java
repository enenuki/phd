/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ public abstract class AnnotationValue
/*   4:    */ {
/*   5: 69 */   static final AnnotationValue[] EMPTY_VALUE_ARRAY = new AnnotationValue[0];
/*   6:    */   private final String name;
/*   7:    */   
/*   8:    */   AnnotationValue(String name)
/*   9:    */   {
/*  10: 74 */     this.name = name;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public static final AnnotationValue createByteValue(String name, byte b)
/*  14:    */   {
/*  15: 78 */     return new ByteValue(name, b);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static final AnnotationValue createShortValue(String name, short s)
/*  19:    */   {
/*  20: 82 */     return new ShortValue(name, s);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static final AnnotationValue createIntegerValue(String name, int i)
/*  24:    */   {
/*  25: 86 */     return new IntegerValue(name, i);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static final AnnotationValue createCharacterValue(String name, char c)
/*  29:    */   {
/*  30: 90 */     return new CharacterValue(name, c);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static final AnnotationValue createFloatValue(String name, float f)
/*  34:    */   {
/*  35: 94 */     return new FloatValue(name, f);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static final AnnotationValue createDouleValue(String name, double d)
/*  39:    */   {
/*  40: 98 */     return new DoubleValue(name, d);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static final AnnotationValue createLongalue(String name, long l)
/*  44:    */   {
/*  45:102 */     return new LongValue(name, l);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static final AnnotationValue createBooleanValue(String name, boolean bool)
/*  49:    */   {
/*  50:106 */     return new BooleanValue(name, bool);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static final AnnotationValue createStringValue(String name, String string)
/*  54:    */   {
/*  55:110 */     return new StringValue(name, string);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static final AnnotationValue createClassValue(String name, Type type)
/*  59:    */   {
/*  60:114 */     return new ClassValue(name, type);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static final AnnotationValue createEnumValue(String name, DotName typeName, String value)
/*  64:    */   {
/*  65:118 */     return new EnumValue(name, typeName, value);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static final AnnotationValue createArrayValue(String name, AnnotationValue[] values)
/*  69:    */   {
/*  70:122 */     return new ArrayValue(name, values);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static final AnnotationValue createNestedAnnotationValue(String name, AnnotationInstance instance)
/*  74:    */   {
/*  75:127 */     return new NestedAnnotation(name, instance);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final String name()
/*  79:    */   {
/*  80:138 */     return this.name;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public abstract Object value();
/*  84:    */   
/*  85:    */   public int asInt()
/*  86:    */   {
/*  87:157 */     throw new IllegalArgumentException("Not a number");
/*  88:    */   }
/*  89:    */   
/*  90:    */   public long asLong()
/*  91:    */   {
/*  92:169 */     throw new IllegalArgumentException("Not a number");
/*  93:    */   }
/*  94:    */   
/*  95:    */   public short asShort()
/*  96:    */   {
/*  97:180 */     throw new IllegalArgumentException("not a number");
/*  98:    */   }
/*  99:    */   
/* 100:    */   public byte asByte()
/* 101:    */   {
/* 102:191 */     throw new IllegalArgumentException("not a number");
/* 103:    */   }
/* 104:    */   
/* 105:    */   public float asFloat()
/* 106:    */   {
/* 107:202 */     throw new IllegalArgumentException("not a number");
/* 108:    */   }
/* 109:    */   
/* 110:    */   public double asDouble()
/* 111:    */   {
/* 112:213 */     throw new IllegalArgumentException("not a number");
/* 113:    */   }
/* 114:    */   
/* 115:    */   public char asChar()
/* 116:    */   {
/* 117:223 */     throw new IllegalArgumentException("not a character");
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean asBoolean()
/* 121:    */   {
/* 122:233 */     throw new IllegalArgumentException("not a boolean");
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String asString()
/* 126:    */   {
/* 127:245 */     return value().toString();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String asEnum()
/* 131:    */   {
/* 132:257 */     throw new IllegalArgumentException("not an enum");
/* 133:    */   }
/* 134:    */   
/* 135:    */   public DotName asEnumType()
/* 136:    */   {
/* 137:269 */     throw new IllegalArgumentException("not an enum");
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Type asClass()
/* 141:    */   {
/* 142:283 */     throw new IllegalArgumentException("not a class");
/* 143:    */   }
/* 144:    */   
/* 145:    */   public AnnotationInstance asNested()
/* 146:    */   {
/* 147:294 */     throw new IllegalArgumentException("not a nested annotation");
/* 148:    */   }
/* 149:    */   
/* 150:    */   AnnotationValue[] asArray()
/* 151:    */   {
/* 152:298 */     throw new IllegalArgumentException("Not an array");
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int[] asIntArray()
/* 156:    */   {
/* 157:309 */     throw new IllegalArgumentException("Not a numerical array");
/* 158:    */   }
/* 159:    */   
/* 160:    */   public long[] asLongArray()
/* 161:    */   {
/* 162:320 */     throw new IllegalArgumentException("Not a numerical array");
/* 163:    */   }
/* 164:    */   
/* 165:    */   public short[] asShortArray()
/* 166:    */   {
/* 167:330 */     throw new IllegalArgumentException("not a numerical array");
/* 168:    */   }
/* 169:    */   
/* 170:    */   public byte[] asByteArray()
/* 171:    */   {
/* 172:340 */     throw new IllegalArgumentException("not a numerical array");
/* 173:    */   }
/* 174:    */   
/* 175:    */   public float[] asFloatArray()
/* 176:    */   {
/* 177:350 */     throw new IllegalArgumentException("not a numerical array");
/* 178:    */   }
/* 179:    */   
/* 180:    */   public double[] asDoubleArray()
/* 181:    */   {
/* 182:361 */     throw new IllegalArgumentException("not a numerical array");
/* 183:    */   }
/* 184:    */   
/* 185:    */   public char[] asCharArray()
/* 186:    */   {
/* 187:371 */     throw new IllegalArgumentException("not a character array");
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean[] asBooleanArray()
/* 191:    */   {
/* 192:381 */     throw new IllegalArgumentException("not a boolean array");
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String[] asStringArray()
/* 196:    */   {
/* 197:393 */     throw new IllegalArgumentException("not a string array");
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String[] asEnumArray()
/* 201:    */   {
/* 202:405 */     throw new IllegalArgumentException("not an enum array");
/* 203:    */   }
/* 204:    */   
/* 205:    */   public DotName[] asEnumTypeArray()
/* 206:    */   {
/* 207:419 */     throw new IllegalArgumentException("not an enum array");
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Type[] asClassArray()
/* 211:    */   {
/* 212:430 */     throw new IllegalArgumentException("not a class array");
/* 213:    */   }
/* 214:    */   
/* 215:    */   public AnnotationInstance[] asNestedArray()
/* 216:    */   {
/* 217:441 */     throw new IllegalArgumentException("not a nested annotation array");
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String toString()
/* 221:    */   {
/* 222:445 */     StringBuilder builder = new StringBuilder();
/* 223:446 */     if (this.name.length() > 0) {
/* 224:447 */       builder.append(this.name).append(" = ");
/* 225:    */     }
/* 226:448 */     return value();
/* 227:    */   }
/* 228:    */   
/* 229:    */   static final class StringValue
/* 230:    */     extends AnnotationValue
/* 231:    */   {
/* 232:    */     private final String value;
/* 233:    */     
/* 234:    */     StringValue(String name, String value)
/* 235:    */     {
/* 236:455 */       super();
/* 237:456 */       this.value = value;
/* 238:    */     }
/* 239:    */     
/* 240:    */     public String value()
/* 241:    */     {
/* 242:460 */       return this.value;
/* 243:    */     }
/* 244:    */     
/* 245:    */     public String toString()
/* 246:    */     {
/* 247:464 */       StringBuilder builder = new StringBuilder();
/* 248:465 */       if (this.name.length() > 0) {
/* 249:466 */         builder.append(this.name).append(" = ");
/* 250:    */       }
/* 251:468 */       return '"' + this.value + '"';
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   static final class ByteValue
/* 256:    */     extends AnnotationValue
/* 257:    */   {
/* 258:    */     private final byte value;
/* 259:    */     
/* 260:    */     ByteValue(String name, byte value)
/* 261:    */     {
/* 262:476 */       super();
/* 263:477 */       this.value = value;
/* 264:    */     }
/* 265:    */     
/* 266:    */     public Byte value()
/* 267:    */     {
/* 268:481 */       return Byte.valueOf(this.value);
/* 269:    */     }
/* 270:    */     
/* 271:    */     public int asInt()
/* 272:    */     {
/* 273:485 */       return this.value;
/* 274:    */     }
/* 275:    */     
/* 276:    */     public long asLong()
/* 277:    */     {
/* 278:489 */       return this.value;
/* 279:    */     }
/* 280:    */     
/* 281:    */     public short asShort()
/* 282:    */     {
/* 283:493 */       return (short)this.value;
/* 284:    */     }
/* 285:    */     
/* 286:    */     public byte asByte()
/* 287:    */     {
/* 288:497 */       return this.value;
/* 289:    */     }
/* 290:    */     
/* 291:    */     public float asFloat()
/* 292:    */     {
/* 293:501 */       return this.value;
/* 294:    */     }
/* 295:    */     
/* 296:    */     public double asDouble()
/* 297:    */     {
/* 298:505 */       return this.value;
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   static final class CharacterValue
/* 303:    */     extends AnnotationValue
/* 304:    */   {
/* 305:    */     private final char value;
/* 306:    */     
/* 307:    */     CharacterValue(String name, char value)
/* 308:    */     {
/* 309:513 */       super();
/* 310:514 */       this.value = value;
/* 311:    */     }
/* 312:    */     
/* 313:    */     public Character value()
/* 314:    */     {
/* 315:518 */       return Character.valueOf(this.value);
/* 316:    */     }
/* 317:    */     
/* 318:    */     public char asChar()
/* 319:    */     {
/* 320:522 */       return this.value;
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   static final class DoubleValue
/* 325:    */     extends AnnotationValue
/* 326:    */   {
/* 327:    */     private final double value;
/* 328:    */     
/* 329:    */     public DoubleValue(String name, double value)
/* 330:    */     {
/* 331:530 */       super();
/* 332:531 */       this.value = value;
/* 333:    */     }
/* 334:    */     
/* 335:    */     public Double value()
/* 336:    */     {
/* 337:535 */       return Double.valueOf(this.value);
/* 338:    */     }
/* 339:    */     
/* 340:    */     public int asInt()
/* 341:    */     {
/* 342:539 */       return (int)this.value;
/* 343:    */     }
/* 344:    */     
/* 345:    */     public long asLong()
/* 346:    */     {
/* 347:543 */       return this.value;
/* 348:    */     }
/* 349:    */     
/* 350:    */     public short asShort()
/* 351:    */     {
/* 352:547 */       return (short)(int)this.value;
/* 353:    */     }
/* 354:    */     
/* 355:    */     public byte asByte()
/* 356:    */     {
/* 357:551 */       return (byte)(int)this.value;
/* 358:    */     }
/* 359:    */     
/* 360:    */     public float asFloat()
/* 361:    */     {
/* 362:555 */       return (float)this.value;
/* 363:    */     }
/* 364:    */     
/* 365:    */     public double asDouble()
/* 366:    */     {
/* 367:559 */       return this.value;
/* 368:    */     }
/* 369:    */   }
/* 370:    */   
/* 371:    */   static final class FloatValue
/* 372:    */     extends AnnotationValue
/* 373:    */   {
/* 374:    */     private final float value;
/* 375:    */     
/* 376:    */     FloatValue(String name, float value)
/* 377:    */     {
/* 378:567 */       super();
/* 379:568 */       this.value = value;
/* 380:    */     }
/* 381:    */     
/* 382:    */     public Float value()
/* 383:    */     {
/* 384:572 */       return Float.valueOf(this.value);
/* 385:    */     }
/* 386:    */     
/* 387:    */     public int asInt()
/* 388:    */     {
/* 389:576 */       return (int)this.value;
/* 390:    */     }
/* 391:    */     
/* 392:    */     public long asLong()
/* 393:    */     {
/* 394:580 */       return this.value;
/* 395:    */     }
/* 396:    */     
/* 397:    */     public short asShort()
/* 398:    */     {
/* 399:584 */       return (short)(int)this.value;
/* 400:    */     }
/* 401:    */     
/* 402:    */     public byte asByte()
/* 403:    */     {
/* 404:588 */       return (byte)(int)this.value;
/* 405:    */     }
/* 406:    */     
/* 407:    */     public float asFloat()
/* 408:    */     {
/* 409:592 */       return this.value;
/* 410:    */     }
/* 411:    */     
/* 412:    */     public double asDouble()
/* 413:    */     {
/* 414:596 */       return this.value;
/* 415:    */     }
/* 416:    */   }
/* 417:    */   
/* 418:    */   static final class ShortValue
/* 419:    */     extends AnnotationValue
/* 420:    */   {
/* 421:    */     private final short value;
/* 422:    */     
/* 423:    */     ShortValue(String name, short value)
/* 424:    */     {
/* 425:605 */       super();
/* 426:606 */       this.value = value;
/* 427:    */     }
/* 428:    */     
/* 429:    */     public Short value()
/* 430:    */     {
/* 431:610 */       return Short.valueOf(this.value);
/* 432:    */     }
/* 433:    */     
/* 434:    */     public int asInt()
/* 435:    */     {
/* 436:614 */       return this.value;
/* 437:    */     }
/* 438:    */     
/* 439:    */     public long asLong()
/* 440:    */     {
/* 441:618 */       return this.value;
/* 442:    */     }
/* 443:    */     
/* 444:    */     public short asShort()
/* 445:    */     {
/* 446:622 */       return this.value;
/* 447:    */     }
/* 448:    */     
/* 449:    */     public byte asByte()
/* 450:    */     {
/* 451:626 */       return (byte)this.value;
/* 452:    */     }
/* 453:    */     
/* 454:    */     public float asFloat()
/* 455:    */     {
/* 456:630 */       return this.value;
/* 457:    */     }
/* 458:    */     
/* 459:    */     public double asDouble()
/* 460:    */     {
/* 461:634 */       return this.value;
/* 462:    */     }
/* 463:    */   }
/* 464:    */   
/* 465:    */   static final class IntegerValue
/* 466:    */     extends AnnotationValue
/* 467:    */   {
/* 468:    */     private final int value;
/* 469:    */     
/* 470:    */     IntegerValue(String name, int value)
/* 471:    */     {
/* 472:643 */       super();
/* 473:644 */       this.value = value;
/* 474:    */     }
/* 475:    */     
/* 476:    */     public Integer value()
/* 477:    */     {
/* 478:648 */       return Integer.valueOf(this.value);
/* 479:    */     }
/* 480:    */     
/* 481:    */     public int asInt()
/* 482:    */     {
/* 483:652 */       return this.value;
/* 484:    */     }
/* 485:    */     
/* 486:    */     public long asLong()
/* 487:    */     {
/* 488:656 */       return this.value;
/* 489:    */     }
/* 490:    */     
/* 491:    */     public short asShort()
/* 492:    */     {
/* 493:660 */       return (short)this.value;
/* 494:    */     }
/* 495:    */     
/* 496:    */     public byte asByte()
/* 497:    */     {
/* 498:664 */       return (byte)this.value;
/* 499:    */     }
/* 500:    */     
/* 501:    */     public float asFloat()
/* 502:    */     {
/* 503:668 */       return this.value;
/* 504:    */     }
/* 505:    */     
/* 506:    */     public double asDouble()
/* 507:    */     {
/* 508:672 */       return this.value;
/* 509:    */     }
/* 510:    */   }
/* 511:    */   
/* 512:    */   static final class LongValue
/* 513:    */     extends AnnotationValue
/* 514:    */   {
/* 515:    */     private final long value;
/* 516:    */     
/* 517:    */     LongValue(String name, long value)
/* 518:    */     {
/* 519:681 */       super();
/* 520:682 */       this.value = value;
/* 521:    */     }
/* 522:    */     
/* 523:    */     public Long value()
/* 524:    */     {
/* 525:686 */       return Long.valueOf(this.value);
/* 526:    */     }
/* 527:    */     
/* 528:    */     public int asInt()
/* 529:    */     {
/* 530:690 */       return (int)this.value;
/* 531:    */     }
/* 532:    */     
/* 533:    */     public long asLong()
/* 534:    */     {
/* 535:694 */       return this.value;
/* 536:    */     }
/* 537:    */     
/* 538:    */     public short asShort()
/* 539:    */     {
/* 540:698 */       return (short)(int)this.value;
/* 541:    */     }
/* 542:    */     
/* 543:    */     public byte asByte()
/* 544:    */     {
/* 545:702 */       return (byte)(int)this.value;
/* 546:    */     }
/* 547:    */     
/* 548:    */     public float asFloat()
/* 549:    */     {
/* 550:706 */       return (float)this.value;
/* 551:    */     }
/* 552:    */     
/* 553:    */     public double asDouble()
/* 554:    */     {
/* 555:710 */       return this.value;
/* 556:    */     }
/* 557:    */   }
/* 558:    */   
/* 559:    */   static final class BooleanValue
/* 560:    */     extends AnnotationValue
/* 561:    */   {
/* 562:    */     private final boolean value;
/* 563:    */     
/* 564:    */     BooleanValue(String name, boolean value)
/* 565:    */     {
/* 566:719 */       super();
/* 567:720 */       this.value = value;
/* 568:    */     }
/* 569:    */     
/* 570:    */     public Boolean value()
/* 571:    */     {
/* 572:724 */       return Boolean.valueOf(this.value);
/* 573:    */     }
/* 574:    */     
/* 575:    */     public boolean asBoolean()
/* 576:    */     {
/* 577:728 */       return this.value;
/* 578:    */     }
/* 579:    */   }
/* 580:    */   
/* 581:    */   static final class EnumValue
/* 582:    */     extends AnnotationValue
/* 583:    */   {
/* 584:    */     private final String value;
/* 585:    */     private final DotName typeName;
/* 586:    */     
/* 587:    */     EnumValue(String name, DotName typeName, String value)
/* 588:    */     {
/* 589:738 */       super();
/* 590:739 */       this.typeName = typeName;
/* 591:740 */       this.value = value;
/* 592:    */     }
/* 593:    */     
/* 594:    */     public String value()
/* 595:    */     {
/* 596:744 */       return this.value;
/* 597:    */     }
/* 598:    */     
/* 599:    */     public String asEnum()
/* 600:    */     {
/* 601:748 */       return this.value;
/* 602:    */     }
/* 603:    */     
/* 604:    */     public DotName asEnumType()
/* 605:    */     {
/* 606:752 */       return this.typeName;
/* 607:    */     }
/* 608:    */   }
/* 609:    */   
/* 610:    */   static final class ClassValue
/* 611:    */     extends AnnotationValue
/* 612:    */   {
/* 613:    */     private final Type type;
/* 614:    */     
/* 615:    */     ClassValue(String name, Type type)
/* 616:    */     {
/* 617:760 */       super();
/* 618:761 */       this.type = type;
/* 619:    */     }
/* 620:    */     
/* 621:    */     public Type value()
/* 622:    */     {
/* 623:765 */       return this.type;
/* 624:    */     }
/* 625:    */     
/* 626:    */     public Type asClass()
/* 627:    */     {
/* 628:769 */       return this.type;
/* 629:    */     }
/* 630:    */   }
/* 631:    */   
/* 632:    */   static final class NestedAnnotation
/* 633:    */     extends AnnotationValue
/* 634:    */   {
/* 635:    */     private final AnnotationInstance value;
/* 636:    */     
/* 637:    */     NestedAnnotation(String name, AnnotationInstance value)
/* 638:    */     {
/* 639:777 */       super();
/* 640:778 */       this.value = value;
/* 641:    */     }
/* 642:    */     
/* 643:    */     public AnnotationInstance value()
/* 644:    */     {
/* 645:782 */       return this.value;
/* 646:    */     }
/* 647:    */     
/* 648:    */     public AnnotationInstance asNested()
/* 649:    */     {
/* 650:786 */       return this.value;
/* 651:    */     }
/* 652:    */   }
/* 653:    */   
/* 654:    */   static final class ArrayValue
/* 655:    */     extends AnnotationValue
/* 656:    */   {
/* 657:    */     private final AnnotationValue[] value;
/* 658:    */     
/* 659:    */     ArrayValue(String name, AnnotationValue[] value)
/* 660:    */     {
/* 661:794 */       super();
/* 662:795 */       this.value = (value.length > 0 ? value : EMPTY_VALUE_ARRAY);
/* 663:    */     }
/* 664:    */     
/* 665:    */     public AnnotationValue[] value()
/* 666:    */     {
/* 667:799 */       return this.value;
/* 668:    */     }
/* 669:    */     
/* 670:    */     AnnotationValue[] asArray()
/* 671:    */     {
/* 672:803 */       return this.value;
/* 673:    */     }
/* 674:    */     
/* 675:    */     public String toString()
/* 676:    */     {
/* 677:807 */       StringBuilder builder = new StringBuilder();
/* 678:808 */       if (this.name.length() > 0) {
/* 679:809 */         builder.append(this.name).append(" = ");
/* 680:    */       }
/* 681:810 */       builder.append('[');
/* 682:811 */       for (int i = 0; i < this.value.length; i++)
/* 683:    */       {
/* 684:812 */         builder.append(this.value[i]);
/* 685:813 */         if (i < this.value.length - 1) {
/* 686:814 */           builder.append(',');
/* 687:    */         }
/* 688:    */       }
/* 689:816 */       return ']';
/* 690:    */     }
/* 691:    */     
/* 692:    */     public int[] asIntArray()
/* 693:    */     {
/* 694:820 */       int length = this.value.length;
/* 695:821 */       int[] array = new int[length];
/* 696:823 */       for (int i = 0; i < length; i++) {
/* 697:824 */         array[i] = this.value[i].asInt();
/* 698:    */       }
/* 699:827 */       return array;
/* 700:    */     }
/* 701:    */     
/* 702:    */     public long[] asLongArray()
/* 703:    */     {
/* 704:831 */       int length = this.value.length;
/* 705:832 */       long[] array = new long[length];
/* 706:834 */       for (int i = 0; i < length; i++) {
/* 707:835 */         array[i] = this.value[i].asLong();
/* 708:    */       }
/* 709:838 */       return array;
/* 710:    */     }
/* 711:    */     
/* 712:    */     public short[] asShortArray()
/* 713:    */     {
/* 714:842 */       int length = this.value.length;
/* 715:843 */       short[] array = new short[length];
/* 716:845 */       for (int i = 0; i < length; i++) {
/* 717:846 */         array[i] = this.value[i].asShort();
/* 718:    */       }
/* 719:849 */       return array;
/* 720:    */     }
/* 721:    */     
/* 722:    */     public byte[] asByteArray()
/* 723:    */     {
/* 724:853 */       int length = this.value.length;
/* 725:854 */       byte[] array = new byte[length];
/* 726:856 */       for (int i = 0; i < length; i++) {
/* 727:857 */         array[i] = this.value[i].asByte();
/* 728:    */       }
/* 729:860 */       return array;
/* 730:    */     }
/* 731:    */     
/* 732:    */     public float[] asFloatArray()
/* 733:    */     {
/* 734:864 */       int length = this.value.length;
/* 735:865 */       float[] array = new float[length];
/* 736:867 */       for (int i = 0; i < length; i++) {
/* 737:868 */         array[i] = this.value[i].asFloat();
/* 738:    */       }
/* 739:871 */       return array;
/* 740:    */     }
/* 741:    */     
/* 742:    */     public double[] asDoubleArray()
/* 743:    */     {
/* 744:875 */       int length = this.value.length;
/* 745:876 */       double[] array = new double[length];
/* 746:878 */       for (int i = 0; i < length; i++) {
/* 747:879 */         array[i] = this.value[i].asDouble();
/* 748:    */       }
/* 749:881 */       return array;
/* 750:    */     }
/* 751:    */     
/* 752:    */     public char[] asCharArray()
/* 753:    */     {
/* 754:885 */       int length = this.value.length;
/* 755:886 */       char[] array = new char[length];
/* 756:888 */       for (int i = 0; i < length; i++) {
/* 757:889 */         array[i] = this.value[i].asChar();
/* 758:    */       }
/* 759:892 */       return array;
/* 760:    */     }
/* 761:    */     
/* 762:    */     public boolean[] asBooleanArray()
/* 763:    */     {
/* 764:896 */       int length = this.value.length;
/* 765:897 */       boolean[] array = new boolean[length];
/* 766:899 */       for (int i = 0; i < length; i++) {
/* 767:900 */         array[i] = this.value[i].asBoolean();
/* 768:    */       }
/* 769:903 */       return array;
/* 770:    */     }
/* 771:    */     
/* 772:    */     public String[] asStringArray()
/* 773:    */     {
/* 774:907 */       int length = this.value.length;
/* 775:908 */       String[] array = new String[length];
/* 776:910 */       for (int i = 0; i < length; i++) {
/* 777:911 */         array[i] = this.value[i].asString();
/* 778:    */       }
/* 779:914 */       return array;
/* 780:    */     }
/* 781:    */     
/* 782:    */     public String[] asEnumArray()
/* 783:    */     {
/* 784:918 */       int length = this.value.length;
/* 785:919 */       String[] array = new String[length];
/* 786:921 */       for (int i = 0; i < length; i++) {
/* 787:922 */         array[i] = this.value[i].asEnum();
/* 788:    */       }
/* 789:925 */       return array;
/* 790:    */     }
/* 791:    */     
/* 792:    */     public Type[] asClassArray()
/* 793:    */     {
/* 794:929 */       int length = this.value.length;
/* 795:930 */       Type[] array = new Type[length];
/* 796:932 */       for (int i = 0; i < length; i++) {
/* 797:933 */         array[i] = this.value[i].asClass();
/* 798:    */       }
/* 799:936 */       return array;
/* 800:    */     }
/* 801:    */     
/* 802:    */     public AnnotationInstance[] asNestedArray()
/* 803:    */     {
/* 804:940 */       int length = this.value.length;
/* 805:941 */       AnnotationInstance[] array = new AnnotationInstance[length];
/* 806:943 */       for (int i = 0; i < length; i++) {
/* 807:944 */         array[i] = this.value[i].asNested();
/* 808:    */       }
/* 809:947 */       return array;
/* 810:    */     }
/* 811:    */     
/* 812:    */     public DotName[] asEnumTypeArray()
/* 813:    */     {
/* 814:951 */       int length = this.value.length;
/* 815:952 */       DotName[] array = new DotName[length];
/* 816:954 */       for (int i = 0; i < length; i++) {
/* 817:955 */         array[i] = this.value[i].asEnumType();
/* 818:    */       }
/* 819:958 */       return array;
/* 820:    */     }
/* 821:    */   }
/* 822:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.AnnotationValue
 * JD-Core Version:    0.7.0.1
 */
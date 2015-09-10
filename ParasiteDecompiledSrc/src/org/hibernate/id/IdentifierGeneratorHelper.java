/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.math.BigInteger;
/*   6:    */ import java.sql.PreparedStatement;
/*   7:    */ import java.sql.ResultSet;
/*   8:    */ import java.sql.SQLException;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.type.CustomType;
/*  12:    */ import org.hibernate.type.Type;
/*  13:    */ import org.jboss.logging.Logger;
/*  14:    */ 
/*  15:    */ public final class IdentifierGeneratorHelper
/*  16:    */ {
/*  17: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, IdentifierGeneratorHelper.class.getName());
/*  18: 57 */   public static final Serializable SHORT_CIRCUIT_INDICATOR = new Serializable()
/*  19:    */   {
/*  20:    */     public String toString()
/*  21:    */     {
/*  22: 60 */       return "SHORT_CIRCUIT_INDICATOR";
/*  23:    */     }
/*  24:    */   };
/*  25: 68 */   public static final Serializable POST_INSERT_INDICATOR = new Serializable()
/*  26:    */   {
/*  27:    */     public String toString()
/*  28:    */     {
/*  29: 71 */       return "POST_INSERT_INDICATOR";
/*  30:    */     }
/*  31:    */   };
/*  32:    */   
/*  33:    */   public static Serializable getGeneratedIdentity(ResultSet rs, Type type)
/*  34:    */     throws SQLException, HibernateException
/*  35:    */   {
/*  36: 86 */     if (!rs.next()) {
/*  37: 87 */       throw new HibernateException("The database returned no natively generated identity value");
/*  38:    */     }
/*  39: 89 */     Serializable id = get(rs, type);
/*  40: 90 */     LOG.debugf("Natively generated identity: %s", id);
/*  41: 91 */     return id;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static Serializable get(ResultSet rs, Type type)
/*  45:    */     throws SQLException, IdentifierGenerationException
/*  46:    */   {
/*  47:105 */     if (ResultSetIdentifierConsumer.class.isInstance(type)) {
/*  48:106 */       return ((ResultSetIdentifierConsumer)type).consumeIdentifier(rs);
/*  49:    */     }
/*  50:108 */     if (CustomType.class.isInstance(type))
/*  51:    */     {
/*  52:109 */       CustomType customType = (CustomType)type;
/*  53:110 */       if (ResultSetIdentifierConsumer.class.isInstance(customType.getUserType())) {
/*  54:111 */         return ((ResultSetIdentifierConsumer)customType.getUserType()).consumeIdentifier(rs);
/*  55:    */       }
/*  56:    */     }
/*  57:115 */     Class clazz = type.getReturnedClass();
/*  58:116 */     if (clazz == Long.class) {
/*  59:117 */       return Long.valueOf(rs.getLong(1));
/*  60:    */     }
/*  61:119 */     if (clazz == Integer.class) {
/*  62:120 */       return Integer.valueOf(rs.getInt(1));
/*  63:    */     }
/*  64:122 */     if (clazz == Short.class) {
/*  65:123 */       return Short.valueOf(rs.getShort(1));
/*  66:    */     }
/*  67:125 */     if (clazz == String.class) {
/*  68:126 */       return rs.getString(1);
/*  69:    */     }
/*  70:128 */     if (clazz == BigInteger.class) {
/*  71:129 */       return rs.getBigDecimal(1).setScale(0, 7).toBigInteger();
/*  72:    */     }
/*  73:131 */     if (clazz == BigDecimal.class) {
/*  74:132 */       return rs.getBigDecimal(1).setScale(0, 7);
/*  75:    */     }
/*  76:135 */     throw new IdentifierGenerationException("unrecognized id type : " + type.getName() + " -> " + clazz.getName());
/*  77:    */   }
/*  78:    */   
/*  79:    */   @Deprecated
/*  80:    */   public static Number createNumber(long value, Class clazz)
/*  81:    */     throws IdentifierGenerationException
/*  82:    */   {
/*  83:155 */     if (clazz == Long.class) {
/*  84:156 */       return Long.valueOf(value);
/*  85:    */     }
/*  86:158 */     if (clazz == Integer.class) {
/*  87:159 */       return Integer.valueOf((int)value);
/*  88:    */     }
/*  89:161 */     if (clazz == Short.class) {
/*  90:162 */       return Short.valueOf((short)(int)value);
/*  91:    */     }
/*  92:165 */     throw new IdentifierGenerationException("unrecognized id type : " + clazz.getName());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static IntegralDataTypeHolder getIntegralDataTypeHolder(Class integralType)
/*  96:    */   {
/*  97:170 */     if ((integralType == Long.class) || (integralType == Integer.class) || (integralType == Short.class)) {
/*  98:173 */       return new BasicHolder(integralType);
/*  99:    */     }
/* 100:175 */     if (integralType == BigInteger.class) {
/* 101:176 */       return new BigIntegerHolder();
/* 102:    */     }
/* 103:178 */     if (integralType == BigDecimal.class) {
/* 104:179 */       return new BigDecimalHolder();
/* 105:    */     }
/* 106:182 */     throw new IdentifierGenerationException("Unknown integral data type for ids : " + integralType.getName());
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static long extractLong(IntegralDataTypeHolder holder)
/* 110:    */   {
/* 111:189 */     if (holder.getClass() == BasicHolder.class)
/* 112:    */     {
/* 113:190 */       ((BasicHolder)holder).checkInitialized();
/* 114:191 */       return ((BasicHolder)holder).value;
/* 115:    */     }
/* 116:193 */     if (holder.getClass() == BigIntegerHolder.class)
/* 117:    */     {
/* 118:194 */       ((BigIntegerHolder)holder).checkInitialized();
/* 119:195 */       return ((BigIntegerHolder)holder).value.longValue();
/* 120:    */     }
/* 121:197 */     if (holder.getClass() == BigDecimalHolder.class)
/* 122:    */     {
/* 123:198 */       ((BigDecimalHolder)holder).checkInitialized();
/* 124:199 */       return ((BigDecimalHolder)holder).value.longValue();
/* 125:    */     }
/* 126:201 */     throw new IdentifierGenerationException("Unknown IntegralDataTypeHolder impl [" + holder + "]");
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static BigInteger extractBigInteger(IntegralDataTypeHolder holder)
/* 130:    */   {
/* 131:205 */     if (holder.getClass() == BasicHolder.class)
/* 132:    */     {
/* 133:206 */       ((BasicHolder)holder).checkInitialized();
/* 134:207 */       return BigInteger.valueOf(((BasicHolder)holder).value);
/* 135:    */     }
/* 136:209 */     if (holder.getClass() == BigIntegerHolder.class)
/* 137:    */     {
/* 138:210 */       ((BigIntegerHolder)holder).checkInitialized();
/* 139:211 */       return ((BigIntegerHolder)holder).value;
/* 140:    */     }
/* 141:213 */     if (holder.getClass() == BigDecimalHolder.class)
/* 142:    */     {
/* 143:214 */       ((BigDecimalHolder)holder).checkInitialized();
/* 144:    */       
/* 145:216 */       return ((BigDecimalHolder)holder).value.toBigInteger();
/* 146:    */     }
/* 147:218 */     throw new IdentifierGenerationException("Unknown IntegralDataTypeHolder impl [" + holder + "]");
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static BigDecimal extractBigDecimal(IntegralDataTypeHolder holder)
/* 151:    */   {
/* 152:222 */     if (holder.getClass() == BasicHolder.class)
/* 153:    */     {
/* 154:223 */       ((BasicHolder)holder).checkInitialized();
/* 155:224 */       return BigDecimal.valueOf(((BasicHolder)holder).value);
/* 156:    */     }
/* 157:226 */     if (holder.getClass() == BigIntegerHolder.class)
/* 158:    */     {
/* 159:227 */       ((BigIntegerHolder)holder).checkInitialized();
/* 160:228 */       return new BigDecimal(((BigIntegerHolder)holder).value);
/* 161:    */     }
/* 162:230 */     if (holder.getClass() == BigDecimalHolder.class)
/* 163:    */     {
/* 164:231 */       ((BigDecimalHolder)holder).checkInitialized();
/* 165:    */       
/* 166:233 */       return ((BigDecimalHolder)holder).value;
/* 167:    */     }
/* 168:235 */     throw new IdentifierGenerationException("Unknown IntegralDataTypeHolder impl [" + holder + "]");
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static class BasicHolder
/* 172:    */     implements IntegralDataTypeHolder
/* 173:    */   {
/* 174:    */     private final Class exactType;
/* 175:240 */     private long value = -9223372036854775808L;
/* 176:    */     
/* 177:    */     public BasicHolder(Class exactType)
/* 178:    */     {
/* 179:243 */       this.exactType = exactType;
/* 180:244 */       if ((exactType != Long.class) && (exactType != Integer.class) && (exactType != Short.class)) {
/* 181:245 */         throw new IdentifierGenerationException("Invalid type for basic integral holder : " + exactType);
/* 182:    */       }
/* 183:    */     }
/* 184:    */     
/* 185:    */     public long getActualLongValue()
/* 186:    */     {
/* 187:250 */       return this.value;
/* 188:    */     }
/* 189:    */     
/* 190:    */     public IntegralDataTypeHolder initialize(long value)
/* 191:    */     {
/* 192:254 */       this.value = value;
/* 193:255 */       return this;
/* 194:    */     }
/* 195:    */     
/* 196:    */     public IntegralDataTypeHolder initialize(ResultSet resultSet, long defaultValue)
/* 197:    */       throws SQLException
/* 198:    */     {
/* 199:259 */       long value = resultSet.getLong(1);
/* 200:260 */       if (resultSet.wasNull()) {
/* 201:261 */         value = defaultValue;
/* 202:    */       }
/* 203:263 */       return initialize(value);
/* 204:    */     }
/* 205:    */     
/* 206:    */     public void bind(PreparedStatement preparedStatement, int position)
/* 207:    */       throws SQLException
/* 208:    */     {
/* 209:268 */       preparedStatement.setLong(position, this.value);
/* 210:    */     }
/* 211:    */     
/* 212:    */     public IntegralDataTypeHolder increment()
/* 213:    */     {
/* 214:272 */       checkInitialized();
/* 215:273 */       this.value += 1L;
/* 216:274 */       return this;
/* 217:    */     }
/* 218:    */     
/* 219:    */     private void checkInitialized()
/* 220:    */     {
/* 221:278 */       if (this.value == -9223372036854775808L) {
/* 222:279 */         throw new IdentifierGenerationException("integral holder was not initialized");
/* 223:    */       }
/* 224:    */     }
/* 225:    */     
/* 226:    */     public IntegralDataTypeHolder add(long addend)
/* 227:    */     {
/* 228:284 */       checkInitialized();
/* 229:285 */       this.value += addend;
/* 230:286 */       return this;
/* 231:    */     }
/* 232:    */     
/* 233:    */     public IntegralDataTypeHolder decrement()
/* 234:    */     {
/* 235:290 */       checkInitialized();
/* 236:291 */       this.value -= 1L;
/* 237:292 */       return this;
/* 238:    */     }
/* 239:    */     
/* 240:    */     public IntegralDataTypeHolder subtract(long subtrahend)
/* 241:    */     {
/* 242:296 */       checkInitialized();
/* 243:297 */       this.value -= subtrahend;
/* 244:298 */       return this;
/* 245:    */     }
/* 246:    */     
/* 247:    */     public IntegralDataTypeHolder multiplyBy(IntegralDataTypeHolder factor)
/* 248:    */     {
/* 249:302 */       return multiplyBy(IdentifierGeneratorHelper.extractLong(factor));
/* 250:    */     }
/* 251:    */     
/* 252:    */     public IntegralDataTypeHolder multiplyBy(long factor)
/* 253:    */     {
/* 254:306 */       checkInitialized();
/* 255:307 */       this.value *= factor;
/* 256:308 */       return this;
/* 257:    */     }
/* 258:    */     
/* 259:    */     public boolean eq(IntegralDataTypeHolder other)
/* 260:    */     {
/* 261:312 */       return eq(IdentifierGeneratorHelper.extractLong(other));
/* 262:    */     }
/* 263:    */     
/* 264:    */     public boolean eq(long value)
/* 265:    */     {
/* 266:316 */       checkInitialized();
/* 267:317 */       return this.value == value;
/* 268:    */     }
/* 269:    */     
/* 270:    */     public boolean lt(IntegralDataTypeHolder other)
/* 271:    */     {
/* 272:321 */       return lt(IdentifierGeneratorHelper.extractLong(other));
/* 273:    */     }
/* 274:    */     
/* 275:    */     public boolean lt(long value)
/* 276:    */     {
/* 277:325 */       checkInitialized();
/* 278:326 */       return this.value < value;
/* 279:    */     }
/* 280:    */     
/* 281:    */     public boolean gt(IntegralDataTypeHolder other)
/* 282:    */     {
/* 283:330 */       return gt(IdentifierGeneratorHelper.extractLong(other));
/* 284:    */     }
/* 285:    */     
/* 286:    */     public boolean gt(long value)
/* 287:    */     {
/* 288:334 */       checkInitialized();
/* 289:335 */       return this.value > value;
/* 290:    */     }
/* 291:    */     
/* 292:    */     public IntegralDataTypeHolder copy()
/* 293:    */     {
/* 294:339 */       BasicHolder copy = new BasicHolder(this.exactType);
/* 295:340 */       copy.value = this.value;
/* 296:341 */       return copy;
/* 297:    */     }
/* 298:    */     
/* 299:    */     public Number makeValue()
/* 300:    */     {
/* 301:346 */       checkInitialized();
/* 302:347 */       if (this.exactType == Long.class) {
/* 303:348 */         return Long.valueOf(this.value);
/* 304:    */       }
/* 305:350 */       if (this.exactType == Integer.class) {
/* 306:351 */         return Integer.valueOf((int)this.value);
/* 307:    */       }
/* 308:354 */       return Short.valueOf((short)(int)this.value);
/* 309:    */     }
/* 310:    */     
/* 311:    */     public Number makeValueThenIncrement()
/* 312:    */     {
/* 313:359 */       Number result = makeValue();
/* 314:360 */       this.value += 1L;
/* 315:361 */       return result;
/* 316:    */     }
/* 317:    */     
/* 318:    */     public Number makeValueThenAdd(long addend)
/* 319:    */     {
/* 320:365 */       Number result = makeValue();
/* 321:366 */       this.value += addend;
/* 322:367 */       return result;
/* 323:    */     }
/* 324:    */     
/* 325:    */     public String toString()
/* 326:    */     {
/* 327:372 */       return "BasicHolder[" + this.exactType.getName() + "[" + this.value + "]]";
/* 328:    */     }
/* 329:    */     
/* 330:    */     public boolean equals(Object o)
/* 331:    */     {
/* 332:377 */       if (this == o) {
/* 333:378 */         return true;
/* 334:    */       }
/* 335:380 */       if ((o == null) || (getClass() != o.getClass())) {
/* 336:381 */         return false;
/* 337:    */       }
/* 338:384 */       BasicHolder that = (BasicHolder)o;
/* 339:    */       
/* 340:386 */       return this.value == that.value;
/* 341:    */     }
/* 342:    */     
/* 343:    */     public int hashCode()
/* 344:    */     {
/* 345:391 */       return (int)(this.value ^ this.value >>> 32);
/* 346:    */     }
/* 347:    */   }
/* 348:    */   
/* 349:    */   public static class BigIntegerHolder
/* 350:    */     implements IntegralDataTypeHolder
/* 351:    */   {
/* 352:    */     private BigInteger value;
/* 353:    */     
/* 354:    */     public IntegralDataTypeHolder initialize(long value)
/* 355:    */     {
/* 356:399 */       this.value = BigInteger.valueOf(value);
/* 357:400 */       return this;
/* 358:    */     }
/* 359:    */     
/* 360:    */     public IntegralDataTypeHolder initialize(ResultSet resultSet, long defaultValue)
/* 361:    */       throws SQLException
/* 362:    */     {
/* 363:404 */       BigDecimal rsValue = resultSet.getBigDecimal(1);
/* 364:405 */       if (resultSet.wasNull()) {
/* 365:406 */         return initialize(defaultValue);
/* 366:    */       }
/* 367:408 */       this.value = rsValue.setScale(0, 7).toBigInteger();
/* 368:409 */       return this;
/* 369:    */     }
/* 370:    */     
/* 371:    */     public void bind(PreparedStatement preparedStatement, int position)
/* 372:    */       throws SQLException
/* 373:    */     {
/* 374:413 */       preparedStatement.setBigDecimal(position, new BigDecimal(this.value));
/* 375:    */     }
/* 376:    */     
/* 377:    */     public IntegralDataTypeHolder increment()
/* 378:    */     {
/* 379:417 */       checkInitialized();
/* 380:418 */       this.value = this.value.add(BigInteger.ONE);
/* 381:419 */       return this;
/* 382:    */     }
/* 383:    */     
/* 384:    */     private void checkInitialized()
/* 385:    */     {
/* 386:423 */       if (this.value == null) {
/* 387:424 */         throw new IdentifierGenerationException("integral holder was not initialized");
/* 388:    */       }
/* 389:    */     }
/* 390:    */     
/* 391:    */     public IntegralDataTypeHolder add(long increment)
/* 392:    */     {
/* 393:429 */       checkInitialized();
/* 394:430 */       this.value = this.value.add(BigInteger.valueOf(increment));
/* 395:431 */       return this;
/* 396:    */     }
/* 397:    */     
/* 398:    */     public IntegralDataTypeHolder decrement()
/* 399:    */     {
/* 400:435 */       checkInitialized();
/* 401:436 */       this.value = this.value.subtract(BigInteger.ONE);
/* 402:437 */       return this;
/* 403:    */     }
/* 404:    */     
/* 405:    */     public IntegralDataTypeHolder subtract(long subtrahend)
/* 406:    */     {
/* 407:441 */       checkInitialized();
/* 408:442 */       this.value = this.value.subtract(BigInteger.valueOf(subtrahend));
/* 409:443 */       return this;
/* 410:    */     }
/* 411:    */     
/* 412:    */     public IntegralDataTypeHolder multiplyBy(IntegralDataTypeHolder factor)
/* 413:    */     {
/* 414:447 */       checkInitialized();
/* 415:448 */       this.value = this.value.multiply(IdentifierGeneratorHelper.extractBigInteger(factor));
/* 416:449 */       return this;
/* 417:    */     }
/* 418:    */     
/* 419:    */     public IntegralDataTypeHolder multiplyBy(long factor)
/* 420:    */     {
/* 421:453 */       checkInitialized();
/* 422:454 */       this.value = this.value.multiply(BigInteger.valueOf(factor));
/* 423:455 */       return this;
/* 424:    */     }
/* 425:    */     
/* 426:    */     public boolean eq(IntegralDataTypeHolder other)
/* 427:    */     {
/* 428:459 */       checkInitialized();
/* 429:460 */       return this.value.compareTo(IdentifierGeneratorHelper.extractBigInteger(other)) == 0;
/* 430:    */     }
/* 431:    */     
/* 432:    */     public boolean eq(long value)
/* 433:    */     {
/* 434:464 */       checkInitialized();
/* 435:465 */       return this.value.compareTo(BigInteger.valueOf(value)) == 0;
/* 436:    */     }
/* 437:    */     
/* 438:    */     public boolean lt(IntegralDataTypeHolder other)
/* 439:    */     {
/* 440:469 */       checkInitialized();
/* 441:470 */       return this.value.compareTo(IdentifierGeneratorHelper.extractBigInteger(other)) < 0;
/* 442:    */     }
/* 443:    */     
/* 444:    */     public boolean lt(long value)
/* 445:    */     {
/* 446:474 */       checkInitialized();
/* 447:475 */       return this.value.compareTo(BigInteger.valueOf(value)) < 0;
/* 448:    */     }
/* 449:    */     
/* 450:    */     public boolean gt(IntegralDataTypeHolder other)
/* 451:    */     {
/* 452:479 */       checkInitialized();
/* 453:480 */       return this.value.compareTo(IdentifierGeneratorHelper.extractBigInteger(other)) > 0;
/* 454:    */     }
/* 455:    */     
/* 456:    */     public boolean gt(long value)
/* 457:    */     {
/* 458:484 */       checkInitialized();
/* 459:485 */       return this.value.compareTo(BigInteger.valueOf(value)) > 0;
/* 460:    */     }
/* 461:    */     
/* 462:    */     public IntegralDataTypeHolder copy()
/* 463:    */     {
/* 464:489 */       BigIntegerHolder copy = new BigIntegerHolder();
/* 465:490 */       copy.value = this.value;
/* 466:491 */       return copy;
/* 467:    */     }
/* 468:    */     
/* 469:    */     public Number makeValue()
/* 470:    */     {
/* 471:495 */       checkInitialized();
/* 472:496 */       return this.value;
/* 473:    */     }
/* 474:    */     
/* 475:    */     public Number makeValueThenIncrement()
/* 476:    */     {
/* 477:500 */       Number result = makeValue();
/* 478:501 */       this.value = this.value.add(BigInteger.ONE);
/* 479:502 */       return result;
/* 480:    */     }
/* 481:    */     
/* 482:    */     public Number makeValueThenAdd(long addend)
/* 483:    */     {
/* 484:506 */       Number result = makeValue();
/* 485:507 */       this.value = this.value.add(BigInteger.valueOf(addend));
/* 486:508 */       return result;
/* 487:    */     }
/* 488:    */     
/* 489:    */     public String toString()
/* 490:    */     {
/* 491:513 */       return "BigIntegerHolder[" + this.value + "]";
/* 492:    */     }
/* 493:    */     
/* 494:    */     public boolean equals(Object o)
/* 495:    */     {
/* 496:518 */       if (this == o) {
/* 497:519 */         return true;
/* 498:    */       }
/* 499:521 */       if ((o == null) || (getClass() != o.getClass())) {
/* 500:522 */         return false;
/* 501:    */       }
/* 502:525 */       BigIntegerHolder that = (BigIntegerHolder)o;
/* 503:    */       
/* 504:527 */       return this.value == null ? false : that.value == null ? true : this.value.equals(that.value);
/* 505:    */     }
/* 506:    */     
/* 507:    */     public int hashCode()
/* 508:    */     {
/* 509:534 */       return this.value != null ? this.value.hashCode() : 0;
/* 510:    */     }
/* 511:    */   }
/* 512:    */   
/* 513:    */   public static class BigDecimalHolder
/* 514:    */     implements IntegralDataTypeHolder
/* 515:    */   {
/* 516:    */     private BigDecimal value;
/* 517:    */     
/* 518:    */     public IntegralDataTypeHolder initialize(long value)
/* 519:    */     {
/* 520:542 */       this.value = BigDecimal.valueOf(value);
/* 521:543 */       return this;
/* 522:    */     }
/* 523:    */     
/* 524:    */     public IntegralDataTypeHolder initialize(ResultSet resultSet, long defaultValue)
/* 525:    */       throws SQLException
/* 526:    */     {
/* 527:547 */       BigDecimal rsValue = resultSet.getBigDecimal(1);
/* 528:548 */       if (resultSet.wasNull()) {
/* 529:549 */         return initialize(defaultValue);
/* 530:    */       }
/* 531:551 */       this.value = rsValue.setScale(0, 7);
/* 532:552 */       return this;
/* 533:    */     }
/* 534:    */     
/* 535:    */     public void bind(PreparedStatement preparedStatement, int position)
/* 536:    */       throws SQLException
/* 537:    */     {
/* 538:556 */       preparedStatement.setBigDecimal(position, this.value);
/* 539:    */     }
/* 540:    */     
/* 541:    */     public IntegralDataTypeHolder increment()
/* 542:    */     {
/* 543:560 */       checkInitialized();
/* 544:561 */       this.value = this.value.add(BigDecimal.ONE);
/* 545:562 */       return this;
/* 546:    */     }
/* 547:    */     
/* 548:    */     private void checkInitialized()
/* 549:    */     {
/* 550:566 */       if (this.value == null) {
/* 551:567 */         throw new IdentifierGenerationException("integral holder was not initialized");
/* 552:    */       }
/* 553:    */     }
/* 554:    */     
/* 555:    */     public IntegralDataTypeHolder add(long increment)
/* 556:    */     {
/* 557:572 */       checkInitialized();
/* 558:573 */       this.value = this.value.add(BigDecimal.valueOf(increment));
/* 559:574 */       return this;
/* 560:    */     }
/* 561:    */     
/* 562:    */     public IntegralDataTypeHolder decrement()
/* 563:    */     {
/* 564:578 */       checkInitialized();
/* 565:579 */       this.value = this.value.subtract(BigDecimal.ONE);
/* 566:580 */       return this;
/* 567:    */     }
/* 568:    */     
/* 569:    */     public IntegralDataTypeHolder subtract(long subtrahend)
/* 570:    */     {
/* 571:584 */       checkInitialized();
/* 572:585 */       this.value = this.value.subtract(BigDecimal.valueOf(subtrahend));
/* 573:586 */       return this;
/* 574:    */     }
/* 575:    */     
/* 576:    */     public IntegralDataTypeHolder multiplyBy(IntegralDataTypeHolder factor)
/* 577:    */     {
/* 578:590 */       checkInitialized();
/* 579:591 */       this.value = this.value.multiply(IdentifierGeneratorHelper.extractBigDecimal(factor));
/* 580:592 */       return this;
/* 581:    */     }
/* 582:    */     
/* 583:    */     public IntegralDataTypeHolder multiplyBy(long factor)
/* 584:    */     {
/* 585:596 */       checkInitialized();
/* 586:597 */       this.value = this.value.multiply(BigDecimal.valueOf(factor));
/* 587:598 */       return this;
/* 588:    */     }
/* 589:    */     
/* 590:    */     public boolean eq(IntegralDataTypeHolder other)
/* 591:    */     {
/* 592:602 */       checkInitialized();
/* 593:603 */       return this.value.compareTo(IdentifierGeneratorHelper.extractBigDecimal(other)) == 0;
/* 594:    */     }
/* 595:    */     
/* 596:    */     public boolean eq(long value)
/* 597:    */     {
/* 598:607 */       checkInitialized();
/* 599:608 */       return this.value.compareTo(BigDecimal.valueOf(value)) == 0;
/* 600:    */     }
/* 601:    */     
/* 602:    */     public boolean lt(IntegralDataTypeHolder other)
/* 603:    */     {
/* 604:612 */       checkInitialized();
/* 605:613 */       return this.value.compareTo(IdentifierGeneratorHelper.extractBigDecimal(other)) < 0;
/* 606:    */     }
/* 607:    */     
/* 608:    */     public boolean lt(long value)
/* 609:    */     {
/* 610:617 */       checkInitialized();
/* 611:618 */       return this.value.compareTo(BigDecimal.valueOf(value)) < 0;
/* 612:    */     }
/* 613:    */     
/* 614:    */     public boolean gt(IntegralDataTypeHolder other)
/* 615:    */     {
/* 616:622 */       checkInitialized();
/* 617:623 */       return this.value.compareTo(IdentifierGeneratorHelper.extractBigDecimal(other)) > 0;
/* 618:    */     }
/* 619:    */     
/* 620:    */     public boolean gt(long value)
/* 621:    */     {
/* 622:627 */       checkInitialized();
/* 623:628 */       return this.value.compareTo(BigDecimal.valueOf(value)) > 0;
/* 624:    */     }
/* 625:    */     
/* 626:    */     public IntegralDataTypeHolder copy()
/* 627:    */     {
/* 628:632 */       BigDecimalHolder copy = new BigDecimalHolder();
/* 629:633 */       copy.value = this.value;
/* 630:634 */       return copy;
/* 631:    */     }
/* 632:    */     
/* 633:    */     public Number makeValue()
/* 634:    */     {
/* 635:638 */       checkInitialized();
/* 636:639 */       return this.value;
/* 637:    */     }
/* 638:    */     
/* 639:    */     public Number makeValueThenIncrement()
/* 640:    */     {
/* 641:643 */       Number result = makeValue();
/* 642:644 */       this.value = this.value.add(BigDecimal.ONE);
/* 643:645 */       return result;
/* 644:    */     }
/* 645:    */     
/* 646:    */     public Number makeValueThenAdd(long addend)
/* 647:    */     {
/* 648:649 */       Number result = makeValue();
/* 649:650 */       this.value = this.value.add(BigDecimal.valueOf(addend));
/* 650:651 */       return result;
/* 651:    */     }
/* 652:    */     
/* 653:    */     public String toString()
/* 654:    */     {
/* 655:656 */       return "BigDecimalHolder[" + this.value + "]";
/* 656:    */     }
/* 657:    */     
/* 658:    */     public boolean equals(Object o)
/* 659:    */     {
/* 660:661 */       if (this == o) {
/* 661:662 */         return true;
/* 662:    */       }
/* 663:664 */       if ((o == null) || (getClass() != o.getClass())) {
/* 664:665 */         return false;
/* 665:    */       }
/* 666:668 */       BigDecimalHolder that = (BigDecimalHolder)o;
/* 667:    */       
/* 668:670 */       return this.value == null ? false : that.value == null ? true : this.value.equals(that.value);
/* 669:    */     }
/* 670:    */     
/* 671:    */     public int hashCode()
/* 672:    */     {
/* 673:677 */       return this.value != null ? this.value.hashCode() : 0;
/* 674:    */     }
/* 675:    */   }
/* 676:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.IdentifierGeneratorHelper
 * JD-Core Version:    0.7.0.1
 */
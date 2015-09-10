/*   1:    */ package org.apache.commons.lang.builder;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.AccessibleObject;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ import java.util.Collection;
/*   7:    */ import org.apache.commons.lang.ArrayUtils;
/*   8:    */ 
/*   9:    */ public class EqualsBuilder
/*  10:    */ {
/*  11: 91 */   private boolean isEquals = true;
/*  12:    */   
/*  13:    */   public static boolean reflectionEquals(Object lhs, Object rhs)
/*  14:    */   {
/*  15:124 */     return reflectionEquals(lhs, rhs, false, null, null);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static boolean reflectionEquals(Object lhs, Object rhs, Collection excludeFields)
/*  19:    */   {
/*  20:147 */     return reflectionEquals(lhs, rhs, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static boolean reflectionEquals(Object lhs, Object rhs, String[] excludeFields)
/*  24:    */   {
/*  25:170 */     return reflectionEquals(lhs, rhs, false, null, excludeFields);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients)
/*  29:    */   {
/*  30:194 */     return reflectionEquals(lhs, rhs, testTransients, null, null);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients, Class reflectUpToClass)
/*  34:    */   {
/*  35:223 */     return reflectionEquals(lhs, rhs, testTransients, reflectUpToClass, null);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients, Class reflectUpToClass, String[] excludeFields)
/*  39:    */   {
/*  40:254 */     if (lhs == rhs) {
/*  41:255 */       return true;
/*  42:    */     }
/*  43:257 */     if ((lhs == null) || (rhs == null)) {
/*  44:258 */       return false;
/*  45:    */     }
/*  46:264 */     Class lhsClass = lhs.getClass();
/*  47:265 */     Class rhsClass = rhs.getClass();
/*  48:267 */     if (lhsClass.isInstance(rhs))
/*  49:    */     {
/*  50:268 */       Class testClass = lhsClass;
/*  51:269 */       if (!rhsClass.isInstance(lhs)) {
/*  52:271 */         testClass = rhsClass;
/*  53:    */       }
/*  54:    */     }
/*  55:273 */     else if (rhsClass.isInstance(lhs))
/*  56:    */     {
/*  57:274 */       Class testClass = rhsClass;
/*  58:275 */       if (!lhsClass.isInstance(rhs)) {
/*  59:277 */         testClass = lhsClass;
/*  60:    */       }
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64:281 */       return false;
/*  65:    */     }
/*  66:    */     Class testClass;
/*  67:283 */     EqualsBuilder equalsBuilder = new EqualsBuilder();
/*  68:    */     try
/*  69:    */     {
/*  70:285 */       reflectionAppend(lhs, rhs, testClass, equalsBuilder, testTransients, excludeFields);
/*  71:286 */       while ((testClass.getSuperclass() != null) && (testClass != reflectUpToClass))
/*  72:    */       {
/*  73:287 */         testClass = testClass.getSuperclass();
/*  74:288 */         reflectionAppend(lhs, rhs, testClass, equalsBuilder, testTransients, excludeFields);
/*  75:    */       }
/*  76:    */     }
/*  77:    */     catch (IllegalArgumentException e)
/*  78:    */     {
/*  79:296 */       return false;
/*  80:    */     }
/*  81:298 */     return equalsBuilder.isEquals();
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static void reflectionAppend(Object lhs, Object rhs, Class clazz, EqualsBuilder builder, boolean useTransients, String[] excludeFields)
/*  85:    */   {
/*  86:319 */     Field[] fields = clazz.getDeclaredFields();
/*  87:320 */     AccessibleObject.setAccessible(fields, true);
/*  88:321 */     for (int i = 0; (i < fields.length) && (builder.isEquals); i++)
/*  89:    */     {
/*  90:322 */       Field f = fields[i];
/*  91:323 */       if ((!ArrayUtils.contains(excludeFields, f.getName())) && (f.getName().indexOf('$') == -1) && ((useTransients) || (!Modifier.isTransient(f.getModifiers()))) && (!Modifier.isStatic(f.getModifiers()))) {
/*  92:    */         try
/*  93:    */         {
/*  94:328 */           builder.append(f.get(lhs), f.get(rhs));
/*  95:    */         }
/*  96:    */         catch (IllegalAccessException e)
/*  97:    */         {
/*  98:332 */           throw new InternalError("Unexpected IllegalAccessException");
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public EqualsBuilder appendSuper(boolean superEquals)
/* 105:    */   {
/* 106:348 */     if (!this.isEquals) {
/* 107:349 */       return this;
/* 108:    */     }
/* 109:351 */     this.isEquals = superEquals;
/* 110:352 */     return this;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public EqualsBuilder append(Object lhs, Object rhs)
/* 114:    */   {
/* 115:366 */     if (!this.isEquals) {
/* 116:367 */       return this;
/* 117:    */     }
/* 118:369 */     if (lhs == rhs) {
/* 119:370 */       return this;
/* 120:    */     }
/* 121:372 */     if ((lhs == null) || (rhs == null))
/* 122:    */     {
/* 123:373 */       setEquals(false);
/* 124:374 */       return this;
/* 125:    */     }
/* 126:376 */     Class lhsClass = lhs.getClass();
/* 127:377 */     if (!lhsClass.isArray()) {
/* 128:379 */       this.isEquals = lhs.equals(rhs);
/* 129:380 */     } else if (lhs.getClass() != rhs.getClass()) {
/* 130:382 */       setEquals(false);
/* 131:386 */     } else if ((lhs instanceof long[])) {
/* 132:387 */       append((long[])lhs, (long[])rhs);
/* 133:388 */     } else if ((lhs instanceof int[])) {
/* 134:389 */       append((int[])lhs, (int[])rhs);
/* 135:390 */     } else if ((lhs instanceof short[])) {
/* 136:391 */       append((short[])lhs, (short[])rhs);
/* 137:392 */     } else if ((lhs instanceof char[])) {
/* 138:393 */       append((char[])lhs, (char[])rhs);
/* 139:394 */     } else if ((lhs instanceof byte[])) {
/* 140:395 */       append((byte[])lhs, (byte[])rhs);
/* 141:396 */     } else if ((lhs instanceof double[])) {
/* 142:397 */       append((double[])lhs, (double[])rhs);
/* 143:398 */     } else if ((lhs instanceof float[])) {
/* 144:399 */       append((float[])lhs, (float[])rhs);
/* 145:400 */     } else if ((lhs instanceof boolean[])) {
/* 146:401 */       append((boolean[])lhs, (boolean[])rhs);
/* 147:    */     } else {
/* 148:404 */       append((Object[])lhs, (Object[])rhs);
/* 149:    */     }
/* 150:406 */     return this;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public EqualsBuilder append(long lhs, long rhs)
/* 154:    */   {
/* 155:421 */     if (!this.isEquals) {
/* 156:422 */       return this;
/* 157:    */     }
/* 158:424 */     this.isEquals = (lhs == rhs);
/* 159:425 */     return this;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public EqualsBuilder append(int lhs, int rhs)
/* 163:    */   {
/* 164:436 */     if (!this.isEquals) {
/* 165:437 */       return this;
/* 166:    */     }
/* 167:439 */     this.isEquals = (lhs == rhs);
/* 168:440 */     return this;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public EqualsBuilder append(short lhs, short rhs)
/* 172:    */   {
/* 173:451 */     if (!this.isEquals) {
/* 174:452 */       return this;
/* 175:    */     }
/* 176:454 */     this.isEquals = (lhs == rhs);
/* 177:455 */     return this;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public EqualsBuilder append(char lhs, char rhs)
/* 181:    */   {
/* 182:466 */     if (!this.isEquals) {
/* 183:467 */       return this;
/* 184:    */     }
/* 185:469 */     this.isEquals = (lhs == rhs);
/* 186:470 */     return this;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public EqualsBuilder append(byte lhs, byte rhs)
/* 190:    */   {
/* 191:481 */     if (!this.isEquals) {
/* 192:482 */       return this;
/* 193:    */     }
/* 194:484 */     this.isEquals = (lhs == rhs);
/* 195:485 */     return this;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public EqualsBuilder append(double lhs, double rhs)
/* 199:    */   {
/* 200:502 */     if (!this.isEquals) {
/* 201:503 */       return this;
/* 202:    */     }
/* 203:505 */     return append(Double.doubleToLongBits(lhs), Double.doubleToLongBits(rhs));
/* 204:    */   }
/* 205:    */   
/* 206:    */   public EqualsBuilder append(float lhs, float rhs)
/* 207:    */   {
/* 208:522 */     if (!this.isEquals) {
/* 209:523 */       return this;
/* 210:    */     }
/* 211:525 */     return append(Float.floatToIntBits(lhs), Float.floatToIntBits(rhs));
/* 212:    */   }
/* 213:    */   
/* 214:    */   public EqualsBuilder append(boolean lhs, boolean rhs)
/* 215:    */   {
/* 216:536 */     if (!this.isEquals) {
/* 217:537 */       return this;
/* 218:    */     }
/* 219:539 */     this.isEquals = (lhs == rhs);
/* 220:540 */     return this;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public EqualsBuilder append(Object[] lhs, Object[] rhs)
/* 224:    */   {
/* 225:554 */     if (!this.isEquals) {
/* 226:555 */       return this;
/* 227:    */     }
/* 228:557 */     if (lhs == rhs) {
/* 229:558 */       return this;
/* 230:    */     }
/* 231:560 */     if ((lhs == null) || (rhs == null))
/* 232:    */     {
/* 233:561 */       setEquals(false);
/* 234:562 */       return this;
/* 235:    */     }
/* 236:564 */     if (lhs.length != rhs.length)
/* 237:    */     {
/* 238:565 */       setEquals(false);
/* 239:566 */       return this;
/* 240:    */     }
/* 241:568 */     for (int i = 0; (i < lhs.length) && (this.isEquals); i++) {
/* 242:569 */       append(lhs[i], rhs[i]);
/* 243:    */     }
/* 244:571 */     return this;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public EqualsBuilder append(long[] lhs, long[] rhs)
/* 248:    */   {
/* 249:585 */     if (!this.isEquals) {
/* 250:586 */       return this;
/* 251:    */     }
/* 252:588 */     if (lhs == rhs) {
/* 253:589 */       return this;
/* 254:    */     }
/* 255:591 */     if ((lhs == null) || (rhs == null))
/* 256:    */     {
/* 257:592 */       setEquals(false);
/* 258:593 */       return this;
/* 259:    */     }
/* 260:595 */     if (lhs.length != rhs.length)
/* 261:    */     {
/* 262:596 */       setEquals(false);
/* 263:597 */       return this;
/* 264:    */     }
/* 265:599 */     for (int i = 0; (i < lhs.length) && (this.isEquals); i++) {
/* 266:600 */       append(lhs[i], rhs[i]);
/* 267:    */     }
/* 268:602 */     return this;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public EqualsBuilder append(int[] lhs, int[] rhs)
/* 272:    */   {
/* 273:616 */     if (!this.isEquals) {
/* 274:617 */       return this;
/* 275:    */     }
/* 276:619 */     if (lhs == rhs) {
/* 277:620 */       return this;
/* 278:    */     }
/* 279:622 */     if ((lhs == null) || (rhs == null))
/* 280:    */     {
/* 281:623 */       setEquals(false);
/* 282:624 */       return this;
/* 283:    */     }
/* 284:626 */     if (lhs.length != rhs.length)
/* 285:    */     {
/* 286:627 */       setEquals(false);
/* 287:628 */       return this;
/* 288:    */     }
/* 289:630 */     for (int i = 0; (i < lhs.length) && (this.isEquals); i++) {
/* 290:631 */       append(lhs[i], rhs[i]);
/* 291:    */     }
/* 292:633 */     return this;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public EqualsBuilder append(short[] lhs, short[] rhs)
/* 296:    */   {
/* 297:647 */     if (!this.isEquals) {
/* 298:648 */       return this;
/* 299:    */     }
/* 300:650 */     if (lhs == rhs) {
/* 301:651 */       return this;
/* 302:    */     }
/* 303:653 */     if ((lhs == null) || (rhs == null))
/* 304:    */     {
/* 305:654 */       setEquals(false);
/* 306:655 */       return this;
/* 307:    */     }
/* 308:657 */     if (lhs.length != rhs.length)
/* 309:    */     {
/* 310:658 */       setEquals(false);
/* 311:659 */       return this;
/* 312:    */     }
/* 313:661 */     for (int i = 0; (i < lhs.length) && (this.isEquals); i++) {
/* 314:662 */       append(lhs[i], rhs[i]);
/* 315:    */     }
/* 316:664 */     return this;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public EqualsBuilder append(char[] lhs, char[] rhs)
/* 320:    */   {
/* 321:678 */     if (!this.isEquals) {
/* 322:679 */       return this;
/* 323:    */     }
/* 324:681 */     if (lhs == rhs) {
/* 325:682 */       return this;
/* 326:    */     }
/* 327:684 */     if ((lhs == null) || (rhs == null))
/* 328:    */     {
/* 329:685 */       setEquals(false);
/* 330:686 */       return this;
/* 331:    */     }
/* 332:688 */     if (lhs.length != rhs.length)
/* 333:    */     {
/* 334:689 */       setEquals(false);
/* 335:690 */       return this;
/* 336:    */     }
/* 337:692 */     for (int i = 0; (i < lhs.length) && (this.isEquals); i++) {
/* 338:693 */       append(lhs[i], rhs[i]);
/* 339:    */     }
/* 340:695 */     return this;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public EqualsBuilder append(byte[] lhs, byte[] rhs)
/* 344:    */   {
/* 345:709 */     if (!this.isEquals) {
/* 346:710 */       return this;
/* 347:    */     }
/* 348:712 */     if (lhs == rhs) {
/* 349:713 */       return this;
/* 350:    */     }
/* 351:715 */     if ((lhs == null) || (rhs == null))
/* 352:    */     {
/* 353:716 */       setEquals(false);
/* 354:717 */       return this;
/* 355:    */     }
/* 356:719 */     if (lhs.length != rhs.length)
/* 357:    */     {
/* 358:720 */       setEquals(false);
/* 359:721 */       return this;
/* 360:    */     }
/* 361:723 */     for (int i = 0; (i < lhs.length) && (this.isEquals); i++) {
/* 362:724 */       append(lhs[i], rhs[i]);
/* 363:    */     }
/* 364:726 */     return this;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public EqualsBuilder append(double[] lhs, double[] rhs)
/* 368:    */   {
/* 369:740 */     if (!this.isEquals) {
/* 370:741 */       return this;
/* 371:    */     }
/* 372:743 */     if (lhs == rhs) {
/* 373:744 */       return this;
/* 374:    */     }
/* 375:746 */     if ((lhs == null) || (rhs == null))
/* 376:    */     {
/* 377:747 */       setEquals(false);
/* 378:748 */       return this;
/* 379:    */     }
/* 380:750 */     if (lhs.length != rhs.length)
/* 381:    */     {
/* 382:751 */       setEquals(false);
/* 383:752 */       return this;
/* 384:    */     }
/* 385:754 */     for (int i = 0; (i < lhs.length) && (this.isEquals); i++) {
/* 386:755 */       append(lhs[i], rhs[i]);
/* 387:    */     }
/* 388:757 */     return this;
/* 389:    */   }
/* 390:    */   
/* 391:    */   public EqualsBuilder append(float[] lhs, float[] rhs)
/* 392:    */   {
/* 393:771 */     if (!this.isEquals) {
/* 394:772 */       return this;
/* 395:    */     }
/* 396:774 */     if (lhs == rhs) {
/* 397:775 */       return this;
/* 398:    */     }
/* 399:777 */     if ((lhs == null) || (rhs == null))
/* 400:    */     {
/* 401:778 */       setEquals(false);
/* 402:779 */       return this;
/* 403:    */     }
/* 404:781 */     if (lhs.length != rhs.length)
/* 405:    */     {
/* 406:782 */       setEquals(false);
/* 407:783 */       return this;
/* 408:    */     }
/* 409:785 */     for (int i = 0; (i < lhs.length) && (this.isEquals); i++) {
/* 410:786 */       append(lhs[i], rhs[i]);
/* 411:    */     }
/* 412:788 */     return this;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public EqualsBuilder append(boolean[] lhs, boolean[] rhs)
/* 416:    */   {
/* 417:802 */     if (!this.isEquals) {
/* 418:803 */       return this;
/* 419:    */     }
/* 420:805 */     if (lhs == rhs) {
/* 421:806 */       return this;
/* 422:    */     }
/* 423:808 */     if ((lhs == null) || (rhs == null))
/* 424:    */     {
/* 425:809 */       setEquals(false);
/* 426:810 */       return this;
/* 427:    */     }
/* 428:812 */     if (lhs.length != rhs.length)
/* 429:    */     {
/* 430:813 */       setEquals(false);
/* 431:814 */       return this;
/* 432:    */     }
/* 433:816 */     for (int i = 0; (i < lhs.length) && (this.isEquals); i++) {
/* 434:817 */       append(lhs[i], rhs[i]);
/* 435:    */     }
/* 436:819 */     return this;
/* 437:    */   }
/* 438:    */   
/* 439:    */   public boolean isEquals()
/* 440:    */   {
/* 441:829 */     return this.isEquals;
/* 442:    */   }
/* 443:    */   
/* 444:    */   protected void setEquals(boolean isEquals)
/* 445:    */   {
/* 446:839 */     this.isEquals = isEquals;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public void reset()
/* 450:    */   {
/* 451:847 */     this.isEquals = true;
/* 452:    */   }
/* 453:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.builder.EqualsBuilder
 * JD-Core Version:    0.7.0.1
 */
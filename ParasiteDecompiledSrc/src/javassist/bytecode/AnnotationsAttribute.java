/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Map;
/*   7:    */ import javassist.bytecode.annotation.Annotation;
/*   8:    */ import javassist.bytecode.annotation.AnnotationMemberValue;
/*   9:    */ import javassist.bytecode.annotation.AnnotationsWriter;
/*  10:    */ import javassist.bytecode.annotation.ArrayMemberValue;
/*  11:    */ import javassist.bytecode.annotation.BooleanMemberValue;
/*  12:    */ import javassist.bytecode.annotation.ByteMemberValue;
/*  13:    */ import javassist.bytecode.annotation.CharMemberValue;
/*  14:    */ import javassist.bytecode.annotation.ClassMemberValue;
/*  15:    */ import javassist.bytecode.annotation.DoubleMemberValue;
/*  16:    */ import javassist.bytecode.annotation.EnumMemberValue;
/*  17:    */ import javassist.bytecode.annotation.FloatMemberValue;
/*  18:    */ import javassist.bytecode.annotation.IntegerMemberValue;
/*  19:    */ import javassist.bytecode.annotation.LongMemberValue;
/*  20:    */ import javassist.bytecode.annotation.MemberValue;
/*  21:    */ import javassist.bytecode.annotation.ShortMemberValue;
/*  22:    */ import javassist.bytecode.annotation.StringMemberValue;
/*  23:    */ 
/*  24:    */ public class AnnotationsAttribute
/*  25:    */   extends AttributeInfo
/*  26:    */ {
/*  27:    */   public static final String visibleTag = "RuntimeVisibleAnnotations";
/*  28:    */   public static final String invisibleTag = "RuntimeInvisibleAnnotations";
/*  29:    */   
/*  30:    */   public AnnotationsAttribute(ConstPool cp, String attrname, byte[] info)
/*  31:    */   {
/*  32:124 */     super(cp, attrname, info);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public AnnotationsAttribute(ConstPool cp, String attrname)
/*  36:    */   {
/*  37:139 */     this(cp, attrname, new byte[] { 0, 0 });
/*  38:    */   }
/*  39:    */   
/*  40:    */   AnnotationsAttribute(ConstPool cp, int n, DataInputStream in)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:148 */     super(cp, n, in);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int numAnnotations()
/*  47:    */   {
/*  48:155 */     return ByteArray.readU16bit(this.info, 0);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  52:    */   {
/*  53:162 */     Copier copier = new Copier(this.info, this.constPool, newCp, classnames);
/*  54:    */     try
/*  55:    */     {
/*  56:164 */       copier.annotationArray();
/*  57:165 */       return new AnnotationsAttribute(newCp, getName(), copier.close());
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61:168 */       throw new RuntimeException(e.toString());
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Annotation getAnnotation(String type)
/*  66:    */   {
/*  67:182 */     Annotation[] annotations = getAnnotations();
/*  68:183 */     for (int i = 0; i < annotations.length; i++) {
/*  69:184 */       if (annotations[i].getTypeName().equals(type)) {
/*  70:185 */         return annotations[i];
/*  71:    */       }
/*  72:    */     }
/*  73:188 */     return null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void addAnnotation(Annotation annotation)
/*  77:    */   {
/*  78:198 */     String type = annotation.getTypeName();
/*  79:199 */     Annotation[] annotations = getAnnotations();
/*  80:200 */     for (int i = 0; i < annotations.length; i++) {
/*  81:201 */       if (annotations[i].getTypeName().equals(type))
/*  82:    */       {
/*  83:202 */         annotations[i] = annotation;
/*  84:203 */         setAnnotations(annotations);
/*  85:204 */         return;
/*  86:    */       }
/*  87:    */     }
/*  88:208 */     Annotation[] newlist = new Annotation[annotations.length + 1];
/*  89:209 */     System.arraycopy(annotations, 0, newlist, 0, annotations.length);
/*  90:210 */     newlist[annotations.length] = annotation;
/*  91:211 */     setAnnotations(newlist);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Annotation[] getAnnotations()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:225 */       return new Parser(this.info, this.constPool).parseAnnotations();
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:228 */       throw new RuntimeException(e.toString());
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setAnnotations(Annotation[] annotations)
/* 107:    */   {
/* 108:240 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/* 109:241 */     AnnotationsWriter writer = new AnnotationsWriter(output, this.constPool);
/* 110:    */     try
/* 111:    */     {
/* 112:243 */       int n = annotations.length;
/* 113:244 */       writer.numAnnotations(n);
/* 114:245 */       for (int i = 0; i < n; i++) {
/* 115:246 */         annotations[i].write(writer);
/* 116:    */       }
/* 117:248 */       writer.close();
/* 118:    */     }
/* 119:    */     catch (IOException e)
/* 120:    */     {
/* 121:251 */       throw new RuntimeException(e);
/* 122:    */     }
/* 123:254 */     set(output.toByteArray());
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setAnnotation(Annotation annotation)
/* 127:    */   {
/* 128:265 */     setAnnotations(new Annotation[] { annotation });
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String toString()
/* 132:    */   {
/* 133:272 */     Annotation[] a = getAnnotations();
/* 134:273 */     StringBuffer sbuf = new StringBuffer();
/* 135:274 */     int i = 0;
/* 136:275 */     while (i < a.length)
/* 137:    */     {
/* 138:276 */       sbuf.append(a[(i++)].toString());
/* 139:277 */       if (i != a.length) {
/* 140:278 */         sbuf.append(", ");
/* 141:    */       }
/* 142:    */     }
/* 143:281 */     return sbuf.toString();
/* 144:    */   }
/* 145:    */   
/* 146:    */   static class Walker
/* 147:    */   {
/* 148:    */     byte[] info;
/* 149:    */     
/* 150:    */     Walker(byte[] attrInfo)
/* 151:    */     {
/* 152:288 */       this.info = attrInfo;
/* 153:    */     }
/* 154:    */     
/* 155:    */     final void parameters()
/* 156:    */       throws Exception
/* 157:    */     {
/* 158:292 */       int numParam = this.info[0] & 0xFF;
/* 159:293 */       parameters(numParam, 1);
/* 160:    */     }
/* 161:    */     
/* 162:    */     void parameters(int numParam, int pos)
/* 163:    */       throws Exception
/* 164:    */     {
/* 165:297 */       for (int i = 0; i < numParam; i++) {
/* 166:298 */         pos = annotationArray(pos);
/* 167:    */       }
/* 168:    */     }
/* 169:    */     
/* 170:    */     final void annotationArray()
/* 171:    */       throws Exception
/* 172:    */     {
/* 173:302 */       annotationArray(0);
/* 174:    */     }
/* 175:    */     
/* 176:    */     final int annotationArray(int pos)
/* 177:    */       throws Exception
/* 178:    */     {
/* 179:306 */       int num = ByteArray.readU16bit(this.info, pos);
/* 180:307 */       return annotationArray(pos + 2, num);
/* 181:    */     }
/* 182:    */     
/* 183:    */     int annotationArray(int pos, int num)
/* 184:    */       throws Exception
/* 185:    */     {
/* 186:311 */       for (int i = 0; i < num; i++) {
/* 187:312 */         pos = annotation(pos);
/* 188:    */       }
/* 189:314 */       return pos;
/* 190:    */     }
/* 191:    */     
/* 192:    */     final int annotation(int pos)
/* 193:    */       throws Exception
/* 194:    */     {
/* 195:318 */       int type = ByteArray.readU16bit(this.info, pos);
/* 196:319 */       int numPairs = ByteArray.readU16bit(this.info, pos + 2);
/* 197:320 */       return annotation(pos + 4, type, numPairs);
/* 198:    */     }
/* 199:    */     
/* 200:    */     int annotation(int pos, int type, int numPairs)
/* 201:    */       throws Exception
/* 202:    */     {
/* 203:324 */       for (int j = 0; j < numPairs; j++) {
/* 204:325 */         pos = memberValuePair(pos);
/* 205:    */       }
/* 206:327 */       return pos;
/* 207:    */     }
/* 208:    */     
/* 209:    */     final int memberValuePair(int pos)
/* 210:    */       throws Exception
/* 211:    */     {
/* 212:331 */       int nameIndex = ByteArray.readU16bit(this.info, pos);
/* 213:332 */       return memberValuePair(pos + 2, nameIndex);
/* 214:    */     }
/* 215:    */     
/* 216:    */     int memberValuePair(int pos, int nameIndex)
/* 217:    */       throws Exception
/* 218:    */     {
/* 219:336 */       return memberValue(pos);
/* 220:    */     }
/* 221:    */     
/* 222:    */     final int memberValue(int pos)
/* 223:    */       throws Exception
/* 224:    */     {
/* 225:340 */       int tag = this.info[pos] & 0xFF;
/* 226:341 */       if (tag == 101)
/* 227:    */       {
/* 228:342 */         int typeNameIndex = ByteArray.readU16bit(this.info, pos + 1);
/* 229:343 */         int constNameIndex = ByteArray.readU16bit(this.info, pos + 3);
/* 230:344 */         enumMemberValue(typeNameIndex, constNameIndex);
/* 231:345 */         return pos + 5;
/* 232:    */       }
/* 233:347 */       if (tag == 99)
/* 234:    */       {
/* 235:348 */         int index = ByteArray.readU16bit(this.info, pos + 1);
/* 236:349 */         classMemberValue(index);
/* 237:350 */         return pos + 3;
/* 238:    */       }
/* 239:352 */       if (tag == 64) {
/* 240:353 */         return annotationMemberValue(pos + 1);
/* 241:    */       }
/* 242:354 */       if (tag == 91)
/* 243:    */       {
/* 244:355 */         int num = ByteArray.readU16bit(this.info, pos + 1);
/* 245:356 */         return arrayMemberValue(pos + 3, num);
/* 246:    */       }
/* 247:359 */       int index = ByteArray.readU16bit(this.info, pos + 1);
/* 248:360 */       constValueMember(tag, index);
/* 249:361 */       return pos + 3;
/* 250:    */     }
/* 251:    */     
/* 252:    */     void constValueMember(int tag, int index)
/* 253:    */       throws Exception
/* 254:    */     {}
/* 255:    */     
/* 256:    */     void enumMemberValue(int typeNameIndex, int constNameIndex)
/* 257:    */       throws Exception
/* 258:    */     {}
/* 259:    */     
/* 260:    */     void classMemberValue(int index)
/* 261:    */       throws Exception
/* 262:    */     {}
/* 263:    */     
/* 264:    */     int annotationMemberValue(int pos)
/* 265:    */       throws Exception
/* 266:    */     {
/* 267:374 */       return annotation(pos);
/* 268:    */     }
/* 269:    */     
/* 270:    */     int arrayMemberValue(int pos, int num)
/* 271:    */       throws Exception
/* 272:    */     {
/* 273:378 */       for (int i = 0; i < num; i++) {
/* 274:379 */         pos = memberValue(pos);
/* 275:    */       }
/* 276:382 */       return pos;
/* 277:    */     }
/* 278:    */   }
/* 279:    */   
/* 280:    */   static class Copier
/* 281:    */     extends AnnotationsAttribute.Walker
/* 282:    */   {
/* 283:    */     ByteArrayOutputStream output;
/* 284:    */     AnnotationsWriter writer;
/* 285:    */     ConstPool srcPool;
/* 286:    */     ConstPool destPool;
/* 287:    */     Map classnames;
/* 288:    */     
/* 289:    */     Copier(byte[] info, ConstPool src, ConstPool dest, Map map)
/* 290:    */     {
/* 291:404 */       super();
/* 292:405 */       this.output = new ByteArrayOutputStream();
/* 293:406 */       this.writer = new AnnotationsWriter(this.output, dest);
/* 294:407 */       this.srcPool = src;
/* 295:408 */       this.destPool = dest;
/* 296:409 */       this.classnames = map;
/* 297:    */     }
/* 298:    */     
/* 299:    */     byte[] close()
/* 300:    */       throws IOException
/* 301:    */     {
/* 302:413 */       this.writer.close();
/* 303:414 */       return this.output.toByteArray();
/* 304:    */     }
/* 305:    */     
/* 306:    */     void parameters(int numParam, int pos)
/* 307:    */       throws Exception
/* 308:    */     {
/* 309:418 */       this.writer.numParameters(numParam);
/* 310:419 */       super.parameters(numParam, pos);
/* 311:    */     }
/* 312:    */     
/* 313:    */     int annotationArray(int pos, int num)
/* 314:    */       throws Exception
/* 315:    */     {
/* 316:423 */       this.writer.numAnnotations(num);
/* 317:424 */       return super.annotationArray(pos, num);
/* 318:    */     }
/* 319:    */     
/* 320:    */     int annotation(int pos, int type, int numPairs)
/* 321:    */       throws Exception
/* 322:    */     {
/* 323:428 */       this.writer.annotation(copy(type), numPairs);
/* 324:429 */       return super.annotation(pos, type, numPairs);
/* 325:    */     }
/* 326:    */     
/* 327:    */     int memberValuePair(int pos, int nameIndex)
/* 328:    */       throws Exception
/* 329:    */     {
/* 330:433 */       this.writer.memberValuePair(copy(nameIndex));
/* 331:434 */       return super.memberValuePair(pos, nameIndex);
/* 332:    */     }
/* 333:    */     
/* 334:    */     void constValueMember(int tag, int index)
/* 335:    */       throws Exception
/* 336:    */     {
/* 337:438 */       this.writer.constValueIndex(tag, copy(index));
/* 338:439 */       super.constValueMember(tag, index);
/* 339:    */     }
/* 340:    */     
/* 341:    */     void enumMemberValue(int typeNameIndex, int constNameIndex)
/* 342:    */       throws Exception
/* 343:    */     {
/* 344:445 */       this.writer.enumConstValue(copy(typeNameIndex), copy(constNameIndex));
/* 345:446 */       super.enumMemberValue(typeNameIndex, constNameIndex);
/* 346:    */     }
/* 347:    */     
/* 348:    */     void classMemberValue(int index)
/* 349:    */       throws Exception
/* 350:    */     {
/* 351:450 */       this.writer.classInfoIndex(copy(index));
/* 352:451 */       super.classMemberValue(index);
/* 353:    */     }
/* 354:    */     
/* 355:    */     int annotationMemberValue(int pos)
/* 356:    */       throws Exception
/* 357:    */     {
/* 358:455 */       this.writer.annotationValue();
/* 359:456 */       return super.annotationMemberValue(pos);
/* 360:    */     }
/* 361:    */     
/* 362:    */     int arrayMemberValue(int pos, int num)
/* 363:    */       throws Exception
/* 364:    */     {
/* 365:460 */       this.writer.arrayValue(num);
/* 366:461 */       return super.arrayMemberValue(pos, num);
/* 367:    */     }
/* 368:    */     
/* 369:    */     int copy(int srcIndex)
/* 370:    */     {
/* 371:474 */       return this.srcPool.copy(srcIndex, this.destPool, this.classnames);
/* 372:    */     }
/* 373:    */   }
/* 374:    */   
/* 375:    */   static class Parser
/* 376:    */     extends AnnotationsAttribute.Walker
/* 377:    */   {
/* 378:    */     ConstPool pool;
/* 379:    */     Annotation[][] allParams;
/* 380:    */     Annotation[] allAnno;
/* 381:    */     Annotation currentAnno;
/* 382:    */     MemberValue currentMember;
/* 383:    */     
/* 384:    */     Parser(byte[] info, ConstPool cp)
/* 385:    */     {
/* 386:493 */       super();
/* 387:494 */       this.pool = cp;
/* 388:    */     }
/* 389:    */     
/* 390:    */     Annotation[][] parseParameters()
/* 391:    */       throws Exception
/* 392:    */     {
/* 393:498 */       parameters();
/* 394:499 */       return this.allParams;
/* 395:    */     }
/* 396:    */     
/* 397:    */     Annotation[] parseAnnotations()
/* 398:    */       throws Exception
/* 399:    */     {
/* 400:503 */       annotationArray();
/* 401:504 */       return this.allAnno;
/* 402:    */     }
/* 403:    */     
/* 404:    */     MemberValue parseMemberValue()
/* 405:    */       throws Exception
/* 406:    */     {
/* 407:508 */       memberValue(0);
/* 408:509 */       return this.currentMember;
/* 409:    */     }
/* 410:    */     
/* 411:    */     void parameters(int numParam, int pos)
/* 412:    */       throws Exception
/* 413:    */     {
/* 414:513 */       Annotation[][] params = new Annotation[numParam][];
/* 415:514 */       for (int i = 0; i < numParam; i++)
/* 416:    */       {
/* 417:515 */         pos = annotationArray(pos);
/* 418:516 */         params[i] = this.allAnno;
/* 419:    */       }
/* 420:519 */       this.allParams = params;
/* 421:    */     }
/* 422:    */     
/* 423:    */     int annotationArray(int pos, int num)
/* 424:    */       throws Exception
/* 425:    */     {
/* 426:523 */       Annotation[] array = new Annotation[num];
/* 427:524 */       for (int i = 0; i < num; i++)
/* 428:    */       {
/* 429:525 */         pos = annotation(pos);
/* 430:526 */         array[i] = this.currentAnno;
/* 431:    */       }
/* 432:529 */       this.allAnno = array;
/* 433:530 */       return pos;
/* 434:    */     }
/* 435:    */     
/* 436:    */     int annotation(int pos, int type, int numPairs)
/* 437:    */       throws Exception
/* 438:    */     {
/* 439:534 */       this.currentAnno = new Annotation(type, this.pool);
/* 440:535 */       return super.annotation(pos, type, numPairs);
/* 441:    */     }
/* 442:    */     
/* 443:    */     int memberValuePair(int pos, int nameIndex)
/* 444:    */       throws Exception
/* 445:    */     {
/* 446:539 */       pos = super.memberValuePair(pos, nameIndex);
/* 447:540 */       this.currentAnno.addMemberValue(nameIndex, this.currentMember);
/* 448:541 */       return pos;
/* 449:    */     }
/* 450:    */     
/* 451:    */     void constValueMember(int tag, int index)
/* 452:    */       throws Exception
/* 453:    */     {
/* 454:546 */       ConstPool cp = this.pool;
/* 455:    */       MemberValue m;
/* 456:547 */       switch (tag)
/* 457:    */       {
/* 458:    */       case 66: 
/* 459:549 */         m = new ByteMemberValue(index, cp);
/* 460:550 */         break;
/* 461:    */       case 67: 
/* 462:552 */         m = new CharMemberValue(index, cp);
/* 463:553 */         break;
/* 464:    */       case 68: 
/* 465:555 */         m = new DoubleMemberValue(index, cp);
/* 466:556 */         break;
/* 467:    */       case 70: 
/* 468:558 */         m = new FloatMemberValue(index, cp);
/* 469:559 */         break;
/* 470:    */       case 73: 
/* 471:561 */         m = new IntegerMemberValue(index, cp);
/* 472:562 */         break;
/* 473:    */       case 74: 
/* 474:564 */         m = new LongMemberValue(index, cp);
/* 475:565 */         break;
/* 476:    */       case 83: 
/* 477:567 */         m = new ShortMemberValue(index, cp);
/* 478:568 */         break;
/* 479:    */       case 90: 
/* 480:570 */         m = new BooleanMemberValue(index, cp);
/* 481:571 */         break;
/* 482:    */       case 115: 
/* 483:573 */         m = new StringMemberValue(index, cp);
/* 484:574 */         break;
/* 485:    */       default: 
/* 486:576 */         throw new RuntimeException("unknown tag:" + tag);
/* 487:    */       }
/* 488:579 */       this.currentMember = m;
/* 489:580 */       super.constValueMember(tag, index);
/* 490:    */     }
/* 491:    */     
/* 492:    */     void enumMemberValue(int typeNameIndex, int constNameIndex)
/* 493:    */       throws Exception
/* 494:    */     {
/* 495:586 */       this.currentMember = new EnumMemberValue(typeNameIndex, constNameIndex, this.pool);
/* 496:    */       
/* 497:588 */       super.enumMemberValue(typeNameIndex, constNameIndex);
/* 498:    */     }
/* 499:    */     
/* 500:    */     void classMemberValue(int index)
/* 501:    */       throws Exception
/* 502:    */     {
/* 503:592 */       this.currentMember = new ClassMemberValue(index, this.pool);
/* 504:593 */       super.classMemberValue(index);
/* 505:    */     }
/* 506:    */     
/* 507:    */     int annotationMemberValue(int pos)
/* 508:    */       throws Exception
/* 509:    */     {
/* 510:597 */       Annotation anno = this.currentAnno;
/* 511:598 */       pos = super.annotationMemberValue(pos);
/* 512:599 */       this.currentMember = new AnnotationMemberValue(this.currentAnno, this.pool);
/* 513:600 */       this.currentAnno = anno;
/* 514:601 */       return pos;
/* 515:    */     }
/* 516:    */     
/* 517:    */     int arrayMemberValue(int pos, int num)
/* 518:    */       throws Exception
/* 519:    */     {
/* 520:605 */       ArrayMemberValue amv = new ArrayMemberValue(this.pool);
/* 521:606 */       MemberValue[] elements = new MemberValue[num];
/* 522:607 */       for (int i = 0; i < num; i++)
/* 523:    */       {
/* 524:608 */         pos = memberValue(pos);
/* 525:609 */         elements[i] = this.currentMember;
/* 526:    */       }
/* 527:612 */       amv.setValue(elements);
/* 528:613 */       this.currentMember = amv;
/* 529:614 */       return pos;
/* 530:    */     }
/* 531:    */   }
/* 532:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.AnnotationsAttribute
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import org.apache.bcel.classfile.Constant;
/*   5:    */ import org.apache.bcel.classfile.ConstantCP;
/*   6:    */ import org.apache.bcel.classfile.ConstantClass;
/*   7:    */ import org.apache.bcel.classfile.ConstantDouble;
/*   8:    */ import org.apache.bcel.classfile.ConstantFieldref;
/*   9:    */ import org.apache.bcel.classfile.ConstantFloat;
/*  10:    */ import org.apache.bcel.classfile.ConstantInteger;
/*  11:    */ import org.apache.bcel.classfile.ConstantInterfaceMethodref;
/*  12:    */ import org.apache.bcel.classfile.ConstantLong;
/*  13:    */ import org.apache.bcel.classfile.ConstantMethodref;
/*  14:    */ import org.apache.bcel.classfile.ConstantNameAndType;
/*  15:    */ import org.apache.bcel.classfile.ConstantPool;
/*  16:    */ import org.apache.bcel.classfile.ConstantString;
/*  17:    */ import org.apache.bcel.classfile.ConstantUtf8;
/*  18:    */ 
/*  19:    */ public class ConstantPoolGen
/*  20:    */ {
/*  21: 76 */   protected int size = 1024;
/*  22: 77 */   protected Constant[] constants = new Constant[this.size];
/*  23: 78 */   protected int index = 1;
/*  24:    */   private static final String METHODREF_DELIM = ":";
/*  25:    */   private static final String IMETHODREF_DELIM = "#";
/*  26:    */   private static final String FIELDREF_DELIM = "&";
/*  27:    */   private static final String NAT_DELIM = "%";
/*  28:    */   
/*  29:    */   private static class Index
/*  30:    */   {
/*  31:    */     int index;
/*  32:    */     
/*  33:    */     Index(int i)
/*  34:    */     {
/*  35: 87 */       this.index = i;
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ConstantPoolGen(Constant[] cs)
/*  40:    */   {
/*  41: 96 */     if (cs.length > this.size)
/*  42:    */     {
/*  43: 97 */       this.size = cs.length;
/*  44: 98 */       this.constants = new Constant[this.size];
/*  45:    */     }
/*  46:101 */     System.arraycopy(cs, 0, this.constants, 0, cs.length);
/*  47:103 */     if (cs.length > 0) {
/*  48:104 */       this.index = cs.length;
/*  49:    */     }
/*  50:106 */     for (int i = 1; i < this.index; i++)
/*  51:    */     {
/*  52:107 */       Constant c = this.constants[i];
/*  53:109 */       if ((c instanceof ConstantString))
/*  54:    */       {
/*  55:110 */         ConstantString s = (ConstantString)c;
/*  56:111 */         ConstantUtf8 u8 = (ConstantUtf8)this.constants[s.getStringIndex()];
/*  57:    */         
/*  58:113 */         this.string_table.put(u8.getBytes(), new Index(i));
/*  59:    */       }
/*  60:114 */       else if ((c instanceof ConstantClass))
/*  61:    */       {
/*  62:115 */         ConstantClass s = (ConstantClass)c;
/*  63:116 */         ConstantUtf8 u8 = (ConstantUtf8)this.constants[s.getNameIndex()];
/*  64:    */         
/*  65:118 */         this.class_table.put(u8.getBytes(), new Index(i));
/*  66:    */       }
/*  67:119 */       else if ((c instanceof ConstantNameAndType))
/*  68:    */       {
/*  69:120 */         ConstantNameAndType n = (ConstantNameAndType)c;
/*  70:121 */         ConstantUtf8 u8 = (ConstantUtf8)this.constants[n.getNameIndex()];
/*  71:122 */         ConstantUtf8 u8_2 = (ConstantUtf8)this.constants[n.getSignatureIndex()];
/*  72:    */         
/*  73:124 */         this.n_a_t_table.put(u8.getBytes() + "%" + u8_2.getBytes(), new Index(i));
/*  74:    */       }
/*  75:125 */       else if ((c instanceof ConstantUtf8))
/*  76:    */       {
/*  77:126 */         ConstantUtf8 u = (ConstantUtf8)c;
/*  78:    */         
/*  79:128 */         this.utf8_table.put(u.getBytes(), new Index(i));
/*  80:    */       }
/*  81:129 */       else if ((c instanceof ConstantCP))
/*  82:    */       {
/*  83:130 */         ConstantCP m = (ConstantCP)c;
/*  84:131 */         ConstantClass clazz = (ConstantClass)this.constants[m.getClassIndex()];
/*  85:132 */         ConstantNameAndType n = (ConstantNameAndType)this.constants[m.getNameAndTypeIndex()];
/*  86:    */         
/*  87:134 */         ConstantUtf8 u8 = (ConstantUtf8)this.constants[clazz.getNameIndex()];
/*  88:135 */         String class_name = u8.getBytes().replace('/', '.');
/*  89:    */         
/*  90:137 */         u8 = (ConstantUtf8)this.constants[n.getNameIndex()];
/*  91:138 */         String method_name = u8.getBytes();
/*  92:    */         
/*  93:140 */         u8 = (ConstantUtf8)this.constants[n.getSignatureIndex()];
/*  94:141 */         String signature = u8.getBytes();
/*  95:    */         
/*  96:143 */         String delim = ":";
/*  97:145 */         if ((c instanceof ConstantInterfaceMethodref)) {
/*  98:146 */           delim = "#";
/*  99:147 */         } else if ((c instanceof ConstantFieldref)) {
/* 100:148 */           delim = "&";
/* 101:    */         }
/* 102:150 */         this.cp_table.put(class_name + delim + method_name + delim + signature, new Index(i));
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public ConstantPoolGen(ConstantPool cp)
/* 108:    */   {
/* 109:159 */     this(cp.getConstantPool());
/* 110:    */   }
/* 111:    */   
/* 112:    */   public ConstantPoolGen() {}
/* 113:    */   
/* 114:    */   protected void adjustSize()
/* 115:    */   {
/* 116:170 */     if (this.index + 3 >= this.size)
/* 117:    */     {
/* 118:171 */       Constant[] cs = this.constants;
/* 119:    */       
/* 120:173 */       this.size *= 2;
/* 121:174 */       this.constants = new Constant[this.size];
/* 122:175 */       System.arraycopy(cs, 0, this.constants, 0, this.index);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:179 */   private HashMap string_table = new HashMap();
/* 127:    */   
/* 128:    */   public int lookupString(String str)
/* 129:    */   {
/* 130:188 */     Index index = (Index)this.string_table.get(str);
/* 131:189 */     return index != null ? index.index : -1;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int addString(String str)
/* 135:    */   {
/* 136:201 */     if ((ret = lookupString(str)) != -1) {
/* 137:202 */       return ret;
/* 138:    */     }
/* 139:204 */     adjustSize();
/* 140:    */     
/* 141:206 */     ConstantUtf8 u8 = new ConstantUtf8(str);
/* 142:207 */     ConstantString s = new ConstantString(this.index);
/* 143:    */     
/* 144:209 */     this.constants[(this.index++)] = u8;
/* 145:210 */     int ret = this.index;
/* 146:211 */     this.constants[(this.index++)] = s;
/* 147:    */     
/* 148:213 */     this.string_table.put(str, new Index(ret));
/* 149:    */     
/* 150:215 */     return ret;
/* 151:    */   }
/* 152:    */   
/* 153:218 */   private HashMap class_table = new HashMap();
/* 154:    */   
/* 155:    */   public int lookupClass(String str)
/* 156:    */   {
/* 157:227 */     Index index = (Index)this.class_table.get(str.replace('.', '/'));
/* 158:228 */     return index != null ? index.index : -1;
/* 159:    */   }
/* 160:    */   
/* 161:    */   private int addClass_(String clazz)
/* 162:    */   {
/* 163:234 */     if ((ret = lookupClass(clazz)) != -1) {
/* 164:235 */       return ret;
/* 165:    */     }
/* 166:237 */     adjustSize();
/* 167:    */     
/* 168:239 */     ConstantClass c = new ConstantClass(addUtf8(clazz));
/* 169:    */     
/* 170:241 */     int ret = this.index;
/* 171:242 */     this.constants[(this.index++)] = c;
/* 172:    */     
/* 173:244 */     this.class_table.put(clazz, new Index(ret));
/* 174:    */     
/* 175:246 */     return ret;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int addClass(String str)
/* 179:    */   {
/* 180:256 */     return addClass_(str.replace('.', '/'));
/* 181:    */   }
/* 182:    */   
/* 183:    */   public int addClass(ObjectType type)
/* 184:    */   {
/* 185:266 */     return addClass(type.getClassName());
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int addArrayClass(ArrayType type)
/* 189:    */   {
/* 190:277 */     return addClass_(type.getSignature());
/* 191:    */   }
/* 192:    */   
/* 193:    */   public int lookupInteger(int n)
/* 194:    */   {
/* 195:287 */     for (int i = 1; i < this.index; i++) {
/* 196:288 */       if ((this.constants[i] instanceof ConstantInteger))
/* 197:    */       {
/* 198:289 */         ConstantInteger c = (ConstantInteger)this.constants[i];
/* 199:291 */         if (c.getBytes() == n) {
/* 200:292 */           return i;
/* 201:    */         }
/* 202:    */       }
/* 203:    */     }
/* 204:296 */     return -1;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int addInteger(int n)
/* 208:    */   {
/* 209:308 */     if ((ret = lookupInteger(n)) != -1) {
/* 210:309 */       return ret;
/* 211:    */     }
/* 212:311 */     adjustSize();
/* 213:    */     
/* 214:313 */     int ret = this.index;
/* 215:314 */     this.constants[(this.index++)] = new ConstantInteger(n);
/* 216:    */     
/* 217:316 */     return ret;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public int lookupFloat(float n)
/* 221:    */   {
/* 222:326 */     for (int i = 1; i < this.index; i++) {
/* 223:327 */       if ((this.constants[i] instanceof ConstantFloat))
/* 224:    */       {
/* 225:328 */         ConstantFloat c = (ConstantFloat)this.constants[i];
/* 226:330 */         if (c.getBytes() == n) {
/* 227:331 */           return i;
/* 228:    */         }
/* 229:    */       }
/* 230:    */     }
/* 231:335 */     return -1;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int addFloat(float n)
/* 235:    */   {
/* 236:347 */     if ((ret = lookupFloat(n)) != -1) {
/* 237:348 */       return ret;
/* 238:    */     }
/* 239:350 */     adjustSize();
/* 240:    */     
/* 241:352 */     int ret = this.index;
/* 242:353 */     this.constants[(this.index++)] = new ConstantFloat(n);
/* 243:    */     
/* 244:355 */     return ret;
/* 245:    */   }
/* 246:    */   
/* 247:358 */   private HashMap utf8_table = new HashMap();
/* 248:    */   
/* 249:    */   public int lookupUtf8(String n)
/* 250:    */   {
/* 251:367 */     Index index = (Index)this.utf8_table.get(n);
/* 252:    */     
/* 253:369 */     return index != null ? index.index : -1;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public int addUtf8(String n)
/* 257:    */   {
/* 258:381 */     if ((ret = lookupUtf8(n)) != -1) {
/* 259:382 */       return ret;
/* 260:    */     }
/* 261:384 */     adjustSize();
/* 262:    */     
/* 263:386 */     int ret = this.index;
/* 264:387 */     this.constants[(this.index++)] = new ConstantUtf8(n);
/* 265:    */     
/* 266:389 */     this.utf8_table.put(n, new Index(ret));
/* 267:    */     
/* 268:391 */     return ret;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public int lookupLong(long n)
/* 272:    */   {
/* 273:401 */     for (int i = 1; i < this.index; i++) {
/* 274:402 */       if ((this.constants[i] instanceof ConstantLong))
/* 275:    */       {
/* 276:403 */         ConstantLong c = (ConstantLong)this.constants[i];
/* 277:405 */         if (c.getBytes() == n) {
/* 278:406 */           return i;
/* 279:    */         }
/* 280:    */       }
/* 281:    */     }
/* 282:410 */     return -1;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public int addLong(long n)
/* 286:    */   {
/* 287:422 */     if ((ret = lookupLong(n)) != -1) {
/* 288:423 */       return ret;
/* 289:    */     }
/* 290:425 */     adjustSize();
/* 291:    */     
/* 292:427 */     int ret = this.index;
/* 293:428 */     this.constants[this.index] = new ConstantLong(n);
/* 294:429 */     this.index += 2;
/* 295:    */     
/* 296:431 */     return ret;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public int lookupDouble(double n)
/* 300:    */   {
/* 301:441 */     for (int i = 1; i < this.index; i++) {
/* 302:442 */       if ((this.constants[i] instanceof ConstantDouble))
/* 303:    */       {
/* 304:443 */         ConstantDouble c = (ConstantDouble)this.constants[i];
/* 305:445 */         if (c.getBytes() == n) {
/* 306:446 */           return i;
/* 307:    */         }
/* 308:    */       }
/* 309:    */     }
/* 310:450 */     return -1;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public int addDouble(double n)
/* 314:    */   {
/* 315:462 */     if ((ret = lookupDouble(n)) != -1) {
/* 316:463 */       return ret;
/* 317:    */     }
/* 318:465 */     adjustSize();
/* 319:    */     
/* 320:467 */     int ret = this.index;
/* 321:468 */     this.constants[this.index] = new ConstantDouble(n);
/* 322:469 */     this.index += 2;
/* 323:    */     
/* 324:471 */     return ret;
/* 325:    */   }
/* 326:    */   
/* 327:474 */   private HashMap n_a_t_table = new HashMap();
/* 328:    */   
/* 329:    */   public int lookupNameAndType(String name, String signature)
/* 330:    */   {
/* 331:484 */     Index index = (Index)this.n_a_t_table.get(name + "%" + signature);
/* 332:485 */     return index != null ? index.index : -1;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public int addNameAndType(String name, String signature)
/* 336:    */   {
/* 337:499 */     if ((ret = lookupNameAndType(name, signature)) != -1) {
/* 338:500 */       return ret;
/* 339:    */     }
/* 340:502 */     adjustSize();
/* 341:    */     
/* 342:504 */     int name_index = addUtf8(name);
/* 343:505 */     int signature_index = addUtf8(signature);
/* 344:506 */     int ret = this.index;
/* 345:507 */     this.constants[(this.index++)] = new ConstantNameAndType(name_index, signature_index);
/* 346:    */     
/* 347:509 */     this.n_a_t_table.put(name + "%" + signature, new Index(ret));
/* 348:510 */     return ret;
/* 349:    */   }
/* 350:    */   
/* 351:513 */   private HashMap cp_table = new HashMap();
/* 352:    */   
/* 353:    */   public int lookupMethodref(String class_name, String method_name, String signature)
/* 354:    */   {
/* 355:524 */     Index index = (Index)this.cp_table.get(class_name + ":" + method_name + ":" + signature);
/* 356:    */     
/* 357:526 */     return index != null ? index.index : -1;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public int lookupMethodref(MethodGen method)
/* 361:    */   {
/* 362:530 */     return lookupMethodref(method.getClassName(), method.getName(), method.getSignature());
/* 363:    */   }
/* 364:    */   
/* 365:    */   public int addMethodref(String class_name, String method_name, String signature)
/* 366:    */   {
/* 367:544 */     if ((ret = lookupMethodref(class_name, method_name, signature)) != -1) {
/* 368:545 */       return ret;
/* 369:    */     }
/* 370:547 */     adjustSize();
/* 371:    */     
/* 372:549 */     int name_and_type_index = addNameAndType(method_name, signature);
/* 373:550 */     int class_index = addClass(class_name);
/* 374:551 */     int ret = this.index;
/* 375:552 */     this.constants[(this.index++)] = new ConstantMethodref(class_index, name_and_type_index);
/* 376:    */     
/* 377:554 */     this.cp_table.put(class_name + ":" + method_name + ":" + signature, new Index(ret));
/* 378:    */     
/* 379:    */ 
/* 380:557 */     return ret;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public int addMethodref(MethodGen method)
/* 384:    */   {
/* 385:561 */     return addMethodref(method.getClassName(), method.getName(), method.getSignature());
/* 386:    */   }
/* 387:    */   
/* 388:    */   public int lookupInterfaceMethodref(String class_name, String method_name, String signature)
/* 389:    */   {
/* 390:574 */     Index index = (Index)this.cp_table.get(class_name + "#" + method_name + "#" + signature);
/* 391:    */     
/* 392:576 */     return index != null ? index.index : -1;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public int lookupInterfaceMethodref(MethodGen method)
/* 396:    */   {
/* 397:580 */     return lookupInterfaceMethodref(method.getClassName(), method.getName(), method.getSignature());
/* 398:    */   }
/* 399:    */   
/* 400:    */   public int addInterfaceMethodref(String class_name, String method_name, String signature)
/* 401:    */   {
/* 402:594 */     if ((ret = lookupInterfaceMethodref(class_name, method_name, signature)) != -1) {
/* 403:595 */       return ret;
/* 404:    */     }
/* 405:597 */     adjustSize();
/* 406:    */     
/* 407:599 */     int class_index = addClass(class_name);
/* 408:600 */     int name_and_type_index = addNameAndType(method_name, signature);
/* 409:601 */     int ret = this.index;
/* 410:602 */     this.constants[(this.index++)] = new ConstantInterfaceMethodref(class_index, name_and_type_index);
/* 411:    */     
/* 412:604 */     this.cp_table.put(class_name + "#" + method_name + "#" + signature, new Index(ret));
/* 413:    */     
/* 414:    */ 
/* 415:607 */     return ret;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public int addInterfaceMethodref(MethodGen method)
/* 419:    */   {
/* 420:611 */     return addInterfaceMethodref(method.getClassName(), method.getName(), method.getSignature());
/* 421:    */   }
/* 422:    */   
/* 423:    */   public int lookupFieldref(String class_name, String field_name, String signature)
/* 424:    */   {
/* 425:624 */     Index index = (Index)this.cp_table.get(class_name + "&" + field_name + "&" + signature);
/* 426:    */     
/* 427:626 */     return index != null ? index.index : -1;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public int addFieldref(String class_name, String field_name, String signature)
/* 431:    */   {
/* 432:640 */     if ((ret = lookupFieldref(class_name, field_name, signature)) != -1) {
/* 433:641 */       return ret;
/* 434:    */     }
/* 435:643 */     adjustSize();
/* 436:    */     
/* 437:645 */     int class_index = addClass(class_name);
/* 438:646 */     int name_and_type_index = addNameAndType(field_name, signature);
/* 439:647 */     int ret = this.index;
/* 440:648 */     this.constants[(this.index++)] = new ConstantFieldref(class_index, name_and_type_index);
/* 441:    */     
/* 442:650 */     this.cp_table.put(class_name + "&" + field_name + "&" + signature, new Index(ret));
/* 443:    */     
/* 444:652 */     return ret;
/* 445:    */   }
/* 446:    */   
/* 447:    */   public Constant getConstant(int i)
/* 448:    */   {
/* 449:659 */     return this.constants[i];
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void setConstant(int i, Constant c)
/* 453:    */   {
/* 454:667 */     this.constants[i] = c;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public ConstantPool getConstantPool()
/* 458:    */   {
/* 459:673 */     return new ConstantPool(this.constants);
/* 460:    */   }
/* 461:    */   
/* 462:    */   public int getSize()
/* 463:    */   {
/* 464:680 */     return this.index;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public ConstantPool getFinalConstantPool()
/* 468:    */   {
/* 469:687 */     Constant[] cs = new Constant[this.index];
/* 470:    */     
/* 471:689 */     System.arraycopy(this.constants, 0, cs, 0, this.index);
/* 472:    */     
/* 473:691 */     return new ConstantPool(cs);
/* 474:    */   }
/* 475:    */   
/* 476:    */   public String toString()
/* 477:    */   {
/* 478:698 */     StringBuffer buf = new StringBuffer();
/* 479:700 */     for (int i = 1; i < this.index; i++) {
/* 480:701 */       buf.append(i + ")" + this.constants[i] + "\n");
/* 481:    */     }
/* 482:703 */     return buf.toString();
/* 483:    */   }
/* 484:    */   
/* 485:    */   public int addConstant(Constant c, ConstantPoolGen cp)
/* 486:    */   {
/* 487:709 */     Constant[] constants = cp.getConstantPool().getConstantPool();
/* 488:711 */     switch (c.getTag())
/* 489:    */     {
/* 490:    */     case 8: 
/* 491:713 */       ConstantString s = (ConstantString)c;
/* 492:714 */       ConstantUtf8 u8 = (ConstantUtf8)constants[s.getStringIndex()];
/* 493:    */       
/* 494:716 */       return addString(u8.getBytes());
/* 495:    */     case 7: 
/* 496:720 */       ConstantClass s = (ConstantClass)c;
/* 497:721 */       ConstantUtf8 u8 = (ConstantUtf8)constants[s.getNameIndex()];
/* 498:    */       
/* 499:723 */       return addClass(u8.getBytes());
/* 500:    */     case 12: 
/* 501:727 */       ConstantNameAndType n = (ConstantNameAndType)c;
/* 502:728 */       ConstantUtf8 u8 = (ConstantUtf8)constants[n.getNameIndex()];
/* 503:729 */       ConstantUtf8 u8_2 = (ConstantUtf8)constants[n.getSignatureIndex()];
/* 504:    */       
/* 505:731 */       return addNameAndType(u8.getBytes(), u8_2.getBytes());
/* 506:    */     case 1: 
/* 507:735 */       return addUtf8(((ConstantUtf8)c).getBytes());
/* 508:    */     case 6: 
/* 509:738 */       return addDouble(((ConstantDouble)c).getBytes());
/* 510:    */     case 4: 
/* 511:741 */       return addFloat(((ConstantFloat)c).getBytes());
/* 512:    */     case 5: 
/* 513:744 */       return addLong(((ConstantLong)c).getBytes());
/* 514:    */     case 3: 
/* 515:747 */       return addInteger(((ConstantInteger)c).getBytes());
/* 516:    */     case 9: 
/* 517:    */     case 10: 
/* 518:    */     case 11: 
/* 519:751 */       ConstantCP m = (ConstantCP)c;
/* 520:752 */       ConstantClass clazz = (ConstantClass)constants[m.getClassIndex()];
/* 521:753 */       ConstantNameAndType n = (ConstantNameAndType)constants[m.getNameAndTypeIndex()];
/* 522:754 */       ConstantUtf8 u8 = (ConstantUtf8)constants[clazz.getNameIndex()];
/* 523:755 */       String class_name = u8.getBytes().replace('/', '.');
/* 524:    */       
/* 525:757 */       u8 = (ConstantUtf8)constants[n.getNameIndex()];
/* 526:758 */       String name = u8.getBytes();
/* 527:    */       
/* 528:760 */       u8 = (ConstantUtf8)constants[n.getSignatureIndex()];
/* 529:761 */       String signature = u8.getBytes();
/* 530:763 */       switch (c.getTag())
/* 531:    */       {
/* 532:    */       case 11: 
/* 533:765 */         return addInterfaceMethodref(class_name, name, signature);
/* 534:    */       case 10: 
/* 535:768 */         return addMethodref(class_name, name, signature);
/* 536:    */       case 9: 
/* 537:771 */         return addFieldref(class_name, name, signature);
/* 538:    */       }
/* 539:774 */       throw new RuntimeException("Unknown constant type " + c);
/* 540:    */     }
/* 541:779 */     throw new RuntimeException("Unknown constant type " + c);
/* 542:    */   }
/* 543:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ConstantPoolGen
 * JD-Core Version:    0.7.0.1
 */
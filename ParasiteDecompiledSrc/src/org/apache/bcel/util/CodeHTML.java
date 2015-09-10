/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.FilterInputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.PrintWriter;
/*   9:    */ import java.util.BitSet;
/*  10:    */ import org.apache.bcel.Constants;
/*  11:    */ import org.apache.bcel.classfile.AccessFlags;
/*  12:    */ import org.apache.bcel.classfile.Attribute;
/*  13:    */ import org.apache.bcel.classfile.Code;
/*  14:    */ import org.apache.bcel.classfile.CodeException;
/*  15:    */ import org.apache.bcel.classfile.Constant;
/*  16:    */ import org.apache.bcel.classfile.ConstantCP;
/*  17:    */ import org.apache.bcel.classfile.ConstantFieldref;
/*  18:    */ import org.apache.bcel.classfile.ConstantInterfaceMethodref;
/*  19:    */ import org.apache.bcel.classfile.ConstantMethodref;
/*  20:    */ import org.apache.bcel.classfile.ConstantNameAndType;
/*  21:    */ import org.apache.bcel.classfile.ConstantPool;
/*  22:    */ import org.apache.bcel.classfile.FieldOrMethod;
/*  23:    */ import org.apache.bcel.classfile.LocalVariable;
/*  24:    */ import org.apache.bcel.classfile.LocalVariableTable;
/*  25:    */ import org.apache.bcel.classfile.Method;
/*  26:    */ import org.apache.bcel.classfile.Utility;
/*  27:    */ 
/*  28:    */ final class CodeHTML
/*  29:    */   implements Constants
/*  30:    */ {
/*  31:    */   private String class_name;
/*  32:    */   private Method[] methods;
/*  33:    */   private PrintWriter file;
/*  34:    */   private BitSet goto_set;
/*  35:    */   private ConstantPool constant_pool;
/*  36:    */   private ConstantHTML constant_html;
/*  37: 75 */   private static boolean wide = false;
/*  38:    */   
/*  39:    */   CodeHTML(String dir, String class_name, Method[] methods, ConstantPool constant_pool, ConstantHTML constant_html)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 81 */     this.class_name = class_name;
/*  43: 82 */     this.methods = methods;
/*  44: 83 */     this.constant_pool = constant_pool;
/*  45: 84 */     this.constant_html = constant_html;
/*  46:    */     
/*  47: 86 */     this.file = new PrintWriter(new FileOutputStream(dir + class_name + "_code.html"));
/*  48: 87 */     this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\">");
/*  49: 89 */     for (int i = 0; i < methods.length; i++) {
/*  50: 90 */       writeMethod(methods[i], i);
/*  51:    */     }
/*  52: 92 */     this.file.println("</BODY></HTML>");
/*  53: 93 */     this.file.close();
/*  54:    */   }
/*  55:    */   
/*  56:    */   private final String codeToHTML(ByteSequence bytes, int method_number)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:106 */     short opcode = (short)bytes.readUnsignedByte();
/*  60:    */     
/*  61:    */ 
/*  62:109 */     int default_offset = 0;
/*  63:    */     
/*  64:    */ 
/*  65:112 */     int no_pad_bytes = 0;
/*  66:    */     
/*  67:114 */     StringBuffer buf = new StringBuffer("<TT>" + Constants.OPCODE_NAMES[opcode] + "</TT></TD><TD>");
/*  68:119 */     if ((opcode == 170) || (opcode == 171))
/*  69:    */     {
/*  70:120 */       int remainder = bytes.getIndex() % 4;
/*  71:121 */       no_pad_bytes = remainder == 0 ? 0 : 4 - remainder;
/*  72:123 */       for (int i = 0; i < no_pad_bytes; i++) {
/*  73:124 */         bytes.readByte();
/*  74:    */       }
/*  75:127 */       default_offset = bytes.readInt();
/*  76:    */     }
/*  77:    */     int offset;
/*  78:    */     int[] jump_table;
/*  79:    */     int index;
/*  80:    */     int vindex;
/*  81:    */     int class_index;
/*  82:    */     String name;
/*  83:130 */     switch (opcode)
/*  84:    */     {
/*  85:    */     case 170: 
/*  86:132 */       int low = bytes.readInt();
/*  87:133 */       int high = bytes.readInt();
/*  88:    */       
/*  89:135 */       offset = bytes.getIndex() - 12 - no_pad_bytes - 1;
/*  90:136 */       default_offset += offset;
/*  91:    */       
/*  92:138 */       buf.append("<TABLE BORDER=1><TR>");
/*  93:    */       
/*  94:    */ 
/*  95:141 */       jump_table = new int[high - low + 1];
/*  96:142 */       for (int i = 0; i < jump_table.length; i++)
/*  97:    */       {
/*  98:143 */         jump_table[i] = (offset + bytes.readInt());
/*  99:    */         
/* 100:145 */         buf.append("<TH>" + (low + i) + "</TH>");
/* 101:    */       }
/* 102:147 */       buf.append("<TH>default</TH></TR>\n<TR>");
/* 103:150 */       for (int i = 0; i < jump_table.length; i++) {
/* 104:151 */         buf.append("<TD><A HREF=\"#code" + method_number + "@" + jump_table[i] + "\">" + jump_table[i] + "</A></TD>");
/* 105:    */       }
/* 106:153 */       buf.append("<TD><A HREF=\"#code" + method_number + "@" + default_offset + "\">" + default_offset + "</A></TD></TR>\n</TABLE>\n");
/* 107:    */       
/* 108:    */ 
/* 109:156 */       break;
/* 110:    */     case 171: 
/* 111:161 */       int npairs = bytes.readInt();
/* 112:162 */       offset = bytes.getIndex() - 8 - no_pad_bytes - 1;
/* 113:163 */       jump_table = new int[npairs];
/* 114:164 */       default_offset += offset;
/* 115:    */       
/* 116:166 */       buf.append("<TABLE BORDER=1><TR>");
/* 117:169 */       for (int i = 0; i < npairs; i++)
/* 118:    */       {
/* 119:170 */         int match = bytes.readInt();
/* 120:    */         
/* 121:172 */         jump_table[i] = (offset + bytes.readInt());
/* 122:173 */         buf.append("<TH>" + match + "</TH>");
/* 123:    */       }
/* 124:175 */       buf.append("<TH>default</TH></TR>\n<TR>");
/* 125:178 */       for (int i = 0; i < npairs; i++) {
/* 126:179 */         buf.append("<TD><A HREF=\"#code" + method_number + "@" + jump_table[i] + "\">" + jump_table[i] + "</A></TD>");
/* 127:    */       }
/* 128:181 */       buf.append("<TD><A HREF=\"#code" + method_number + "@" + default_offset + "\">" + default_offset + "</A></TD></TR>\n</TABLE>\n");
/* 129:    */       
/* 130:183 */       break;
/* 131:    */     case 153: 
/* 132:    */     case 154: 
/* 133:    */     case 155: 
/* 134:    */     case 156: 
/* 135:    */     case 157: 
/* 136:    */     case 158: 
/* 137:    */     case 159: 
/* 138:    */     case 160: 
/* 139:    */     case 161: 
/* 140:    */     case 162: 
/* 141:    */     case 163: 
/* 142:    */     case 164: 
/* 143:    */     case 165: 
/* 144:    */     case 166: 
/* 145:    */     case 167: 
/* 146:    */     case 168: 
/* 147:    */     case 198: 
/* 148:    */     case 199: 
/* 149:194 */       index = bytes.getIndex() + bytes.readShort() - 1;
/* 150:    */       
/* 151:196 */       buf.append("<A HREF=\"#code" + method_number + "@" + index + "\">" + index + "</A>");
/* 152:197 */       break;
/* 153:    */     case 200: 
/* 154:    */     case 201: 
/* 155:202 */       int windex = bytes.getIndex() + bytes.readInt() - 1;
/* 156:203 */       buf.append("<A HREF=\"#code" + method_number + "@" + windex + "\">" + windex + "</A>");
/* 157:    */       
/* 158:205 */       break;
/* 159:    */     case 21: 
/* 160:    */     case 22: 
/* 161:    */     case 23: 
/* 162:    */     case 24: 
/* 163:    */     case 25: 
/* 164:    */     case 54: 
/* 165:    */     case 55: 
/* 166:    */     case 56: 
/* 167:    */     case 57: 
/* 168:    */     case 58: 
/* 169:    */     case 169: 
/* 170:212 */       if (wide)
/* 171:    */       {
/* 172:213 */         vindex = bytes.readShort();
/* 173:214 */         wide = false;
/* 174:    */       }
/* 175:    */       else
/* 176:    */       {
/* 177:217 */         vindex = bytes.readUnsignedByte();
/* 178:    */       }
/* 179:219 */       buf.append("%" + vindex);
/* 180:220 */       break;
/* 181:    */     case 196: 
/* 182:228 */       wide = true;
/* 183:229 */       buf.append("(wide)");
/* 184:230 */       break;
/* 185:    */     case 188: 
/* 186:235 */       buf.append("<FONT COLOR=\"#00FF00\">" + Constants.TYPE_NAMES[bytes.readByte()] + "</FONT>");
/* 187:236 */       break;
/* 188:    */     case 178: 
/* 189:    */     case 179: 
/* 190:    */     case 180: 
/* 191:    */     case 181: 
/* 192:241 */       index = bytes.readShort();
/* 193:242 */       ConstantFieldref c1 = (ConstantFieldref)this.constant_pool.getConstant(index, (byte)9);
/* 194:    */       
/* 195:244 */       class_index = c1.getClassIndex();
/* 196:245 */       name = this.constant_pool.getConstantString(class_index, (byte)7);
/* 197:246 */       name = Utility.compactClassName(name, false);
/* 198:    */       
/* 199:248 */       index = c1.getNameAndTypeIndex();
/* 200:249 */       String field_name = this.constant_pool.constantToString(index, (byte)12);
/* 201:251 */       if (name.equals(this.class_name)) {
/* 202:252 */         buf.append("<A HREF=\"" + this.class_name + "_methods.html#field" + field_name + "\" TARGET=Methods>" + field_name + "</A>\n");
/* 203:    */       } else {
/* 204:256 */         buf.append(this.constant_html.referenceConstant(class_index) + "." + field_name);
/* 205:    */       }
/* 206:258 */       break;
/* 207:    */     case 187: 
/* 208:    */     case 192: 
/* 209:    */     case 193: 
/* 210:263 */       index = bytes.readShort();
/* 211:264 */       buf.append(this.constant_html.referenceConstant(index));
/* 212:265 */       break;
/* 213:    */     case 182: 
/* 214:    */     case 183: 
/* 215:    */     case 184: 
/* 216:    */     case 185: 
/* 217:270 */       int m_index = bytes.readShort();
/* 218:273 */       if (opcode == 185)
/* 219:    */       {
/* 220:274 */         int nargs = bytes.readUnsignedByte();
/* 221:275 */         int reserved = bytes.readUnsignedByte();
/* 222:    */         
/* 223:277 */         ConstantInterfaceMethodref c = (ConstantInterfaceMethodref)this.constant_pool.getConstant(m_index, (byte)11);
/* 224:    */         
/* 225:279 */         class_index = c.getClassIndex();
/* 226:280 */         str = this.constant_pool.constantToString(c);
/* 227:281 */         index = c.getNameAndTypeIndex();
/* 228:    */       }
/* 229:    */       else
/* 230:    */       {
/* 231:284 */         ConstantMethodref c = (ConstantMethodref)this.constant_pool.getConstant(m_index, (byte)10);
/* 232:285 */         class_index = c.getClassIndex();
/* 233:    */         
/* 234:287 */         str = this.constant_pool.constantToString(c);
/* 235:288 */         index = c.getNameAndTypeIndex();
/* 236:    */       }
/* 237:291 */       name = Class2HTML.referenceClass(class_index);
/* 238:292 */       String str = Class2HTML.toHTML(this.constant_pool.constantToString(this.constant_pool.getConstant(index, (byte)12)));
/* 239:    */       
/* 240:    */ 
/* 241:295 */       ConstantNameAndType c2 = (ConstantNameAndType)this.constant_pool.getConstant(index, (byte)12);
/* 242:    */       
/* 243:297 */       String signature = this.constant_pool.constantToString(c2.getSignatureIndex(), (byte)1);
/* 244:    */       
/* 245:299 */       String[] args = Utility.methodSignatureArgumentTypes(signature, false);
/* 246:300 */       String type = Utility.methodSignatureReturnType(signature, false);
/* 247:    */       
/* 248:302 */       buf.append(name + ".<A HREF=\"" + this.class_name + "_cp.html#cp" + m_index + "\" TARGET=ConstantPool>" + str + "</A>" + "(");
/* 249:306 */       for (int i = 0; i < args.length; i++)
/* 250:    */       {
/* 251:307 */         buf.append(Class2HTML.referenceType(args[i]));
/* 252:309 */         if (i < args.length - 1) {
/* 253:310 */           buf.append(", ");
/* 254:    */         }
/* 255:    */       }
/* 256:313 */       buf.append("):" + Class2HTML.referenceType(type));
/* 257:    */       
/* 258:315 */       break;
/* 259:    */     case 19: 
/* 260:    */     case 20: 
/* 261:320 */       index = bytes.readShort();
/* 262:    */       
/* 263:322 */       buf.append("<A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">" + Class2HTML.toHTML(this.constant_pool.constantToString(index, this.constant_pool.getConstant(index).getTag())) + "</a>");
/* 264:    */       
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:328 */       break;
/* 270:    */     case 18: 
/* 271:331 */       index = bytes.readUnsignedByte();
/* 272:332 */       buf.append("<A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">" + Class2HTML.toHTML(this.constant_pool.constantToString(index, this.constant_pool.getConstant(index).getTag())) + "</a>");
/* 273:    */       
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:338 */       break;
/* 279:    */     case 189: 
/* 280:343 */       index = bytes.readShort();
/* 281:    */       
/* 282:345 */       buf.append(this.constant_html.referenceConstant(index));
/* 283:346 */       break;
/* 284:    */     case 197: 
/* 285:351 */       index = bytes.readShort();
/* 286:352 */       int dimensions = bytes.readByte();
/* 287:353 */       buf.append(this.constant_html.referenceConstant(index) + ":" + dimensions + "-dimensional");
/* 288:354 */       break;
/* 289:    */     case 132: 
/* 290:    */       int constant;
/* 291:359 */       if (wide)
/* 292:    */       {
/* 293:360 */         vindex = bytes.readShort();
/* 294:361 */         constant = bytes.readShort();
/* 295:362 */         wide = false;
/* 296:    */       }
/* 297:    */       else
/* 298:    */       {
/* 299:365 */         vindex = bytes.readUnsignedByte();
/* 300:366 */         constant = bytes.readByte();
/* 301:    */       }
/* 302:368 */       buf.append("%" + vindex + " " + constant);
/* 303:369 */       break;
/* 304:    */     case 26: 
/* 305:    */     case 27: 
/* 306:    */     case 28: 
/* 307:    */     case 29: 
/* 308:    */     case 30: 
/* 309:    */     case 31: 
/* 310:    */     case 32: 
/* 311:    */     case 33: 
/* 312:    */     case 34: 
/* 313:    */     case 35: 
/* 314:    */     case 36: 
/* 315:    */     case 37: 
/* 316:    */     case 38: 
/* 317:    */     case 39: 
/* 318:    */     case 40: 
/* 319:    */     case 41: 
/* 320:    */     case 42: 
/* 321:    */     case 43: 
/* 322:    */     case 44: 
/* 323:    */     case 45: 
/* 324:    */     case 46: 
/* 325:    */     case 47: 
/* 326:    */     case 48: 
/* 327:    */     case 49: 
/* 328:    */     case 50: 
/* 329:    */     case 51: 
/* 330:    */     case 52: 
/* 331:    */     case 53: 
/* 332:    */     case 59: 
/* 333:    */     case 60: 
/* 334:    */     case 61: 
/* 335:    */     case 62: 
/* 336:    */     case 63: 
/* 337:    */     case 64: 
/* 338:    */     case 65: 
/* 339:    */     case 66: 
/* 340:    */     case 67: 
/* 341:    */     case 68: 
/* 342:    */     case 69: 
/* 343:    */     case 70: 
/* 344:    */     case 71: 
/* 345:    */     case 72: 
/* 346:    */     case 73: 
/* 347:    */     case 74: 
/* 348:    */     case 75: 
/* 349:    */     case 76: 
/* 350:    */     case 77: 
/* 351:    */     case 78: 
/* 352:    */     case 79: 
/* 353:    */     case 80: 
/* 354:    */     case 81: 
/* 355:    */     case 82: 
/* 356:    */     case 83: 
/* 357:    */     case 84: 
/* 358:    */     case 85: 
/* 359:    */     case 86: 
/* 360:    */     case 87: 
/* 361:    */     case 88: 
/* 362:    */     case 89: 
/* 363:    */     case 90: 
/* 364:    */     case 91: 
/* 365:    */     case 92: 
/* 366:    */     case 93: 
/* 367:    */     case 94: 
/* 368:    */     case 95: 
/* 369:    */     case 96: 
/* 370:    */     case 97: 
/* 371:    */     case 98: 
/* 372:    */     case 99: 
/* 373:    */     case 100: 
/* 374:    */     case 101: 
/* 375:    */     case 102: 
/* 376:    */     case 103: 
/* 377:    */     case 104: 
/* 378:    */     case 105: 
/* 379:    */     case 106: 
/* 380:    */     case 107: 
/* 381:    */     case 108: 
/* 382:    */     case 109: 
/* 383:    */     case 110: 
/* 384:    */     case 111: 
/* 385:    */     case 112: 
/* 386:    */     case 113: 
/* 387:    */     case 114: 
/* 388:    */     case 115: 
/* 389:    */     case 116: 
/* 390:    */     case 117: 
/* 391:    */     case 118: 
/* 392:    */     case 119: 
/* 393:    */     case 120: 
/* 394:    */     case 121: 
/* 395:    */     case 122: 
/* 396:    */     case 123: 
/* 397:    */     case 124: 
/* 398:    */     case 125: 
/* 399:    */     case 126: 
/* 400:    */     case 127: 
/* 401:    */     case 128: 
/* 402:    */     case 129: 
/* 403:    */     case 130: 
/* 404:    */     case 131: 
/* 405:    */     case 133: 
/* 406:    */     case 134: 
/* 407:    */     case 135: 
/* 408:    */     case 136: 
/* 409:    */     case 137: 
/* 410:    */     case 138: 
/* 411:    */     case 139: 
/* 412:    */     case 140: 
/* 413:    */     case 141: 
/* 414:    */     case 142: 
/* 415:    */     case 143: 
/* 416:    */     case 144: 
/* 417:    */     case 145: 
/* 418:    */     case 146: 
/* 419:    */     case 147: 
/* 420:    */     case 148: 
/* 421:    */     case 149: 
/* 422:    */     case 150: 
/* 423:    */     case 151: 
/* 424:    */     case 152: 
/* 425:    */     case 172: 
/* 426:    */     case 173: 
/* 427:    */     case 174: 
/* 428:    */     case 175: 
/* 429:    */     case 176: 
/* 430:    */     case 177: 
/* 431:    */     case 186: 
/* 432:    */     case 190: 
/* 433:    */     case 191: 
/* 434:    */     case 194: 
/* 435:    */     case 195: 
/* 436:    */     default: 
/* 437:372 */       if (Constants.NO_OF_OPERANDS[opcode] > 0) {
/* 438:373 */         for (int i = 0; i < Constants.TYPE_OF_OPERANDS[opcode].length; i++)
/* 439:    */         {
/* 440:374 */           switch (Constants.TYPE_OF_OPERANDS[opcode][i])
/* 441:    */           {
/* 442:    */           case 8: 
/* 443:376 */             buf.append(bytes.readUnsignedByte());
/* 444:377 */             break;
/* 445:    */           case 9: 
/* 446:380 */             buf.append(bytes.readShort());
/* 447:381 */             break;
/* 448:    */           case 10: 
/* 449:384 */             buf.append(bytes.readInt());
/* 450:385 */             break;
/* 451:    */           default: 
/* 452:388 */             System.err.println("Unreachable default case reached!");
/* 453:389 */             System.exit(-1);
/* 454:    */           }
/* 455:391 */           buf.append("&nbsp;");
/* 456:    */         }
/* 457:    */       }
/* 458:    */       break;
/* 459:    */     }
/* 460:396 */     buf.append("</TD>");
/* 461:397 */     return buf.toString();
/* 462:    */   }
/* 463:    */   
/* 464:    */   private final void findGotos(ByteSequence bytes, Method method, Code code)
/* 465:    */     throws IOException
/* 466:    */   {
/* 467:408 */     this.goto_set = new BitSet(bytes.available());
/* 468:415 */     if (code != null)
/* 469:    */     {
/* 470:416 */       CodeException[] ce = code.getExceptionTable();
/* 471:417 */       int len = ce.length;
/* 472:419 */       for (int i = 0; i < len; i++)
/* 473:    */       {
/* 474:420 */         this.goto_set.set(ce[i].getStartPC());
/* 475:421 */         this.goto_set.set(ce[i].getEndPC());
/* 476:422 */         this.goto_set.set(ce[i].getHandlerPC());
/* 477:    */       }
/* 478:426 */       Attribute[] attributes = code.getAttributes();
/* 479:427 */       for (int i = 0; i < attributes.length; i++) {
/* 480:428 */         if (attributes[i].getTag() == 5)
/* 481:    */         {
/* 482:429 */           LocalVariable[] vars = ((LocalVariableTable)attributes[i]).getLocalVariableTable();
/* 483:431 */           for (int j = 0; j < vars.length; j++)
/* 484:    */           {
/* 485:432 */             int start = vars[j].getStartPC();
/* 486:433 */             int end = start + vars[j].getLength();
/* 487:434 */             this.goto_set.set(start);
/* 488:435 */             this.goto_set.set(end);
/* 489:    */           }
/* 490:437 */           break;
/* 491:    */         }
/* 492:    */       }
/* 493:    */     }
/* 494:443 */     for (int i = 0; bytes.available() > 0; i++)
/* 495:    */     {
/* 496:444 */       int opcode = bytes.readUnsignedByte();
/* 497:    */       int index;
/* 498:446 */       switch (opcode)
/* 499:    */       {
/* 500:    */       case 170: 
/* 501:    */       case 171: 
/* 502:450 */         int remainder = bytes.getIndex() % 4;
/* 503:451 */         int no_pad_bytes = remainder == 0 ? 0 : 4 - remainder;
/* 504:454 */         for (int j = 0; j < no_pad_bytes; j++) {
/* 505:455 */           bytes.readByte();
/* 506:    */         }
/* 507:458 */         int default_offset = bytes.readInt();
/* 508:    */         int offset;
/* 509:460 */         if (opcode == 170)
/* 510:    */         {
/* 511:461 */           int low = bytes.readInt();
/* 512:462 */           int high = bytes.readInt();
/* 513:    */           
/* 514:464 */           offset = bytes.getIndex() - 12 - no_pad_bytes - 1;
/* 515:465 */           default_offset += offset;
/* 516:466 */           this.goto_set.set(default_offset);
/* 517:468 */           for (int j = 0; j < high - low + 1; j++)
/* 518:    */           {
/* 519:469 */             index = offset + bytes.readInt();
/* 520:470 */             this.goto_set.set(index);
/* 521:    */           }
/* 522:    */         }
/* 523:    */         else
/* 524:    */         {
/* 525:474 */           int npairs = bytes.readInt();
/* 526:    */           
/* 527:476 */           offset = bytes.getIndex() - 8 - no_pad_bytes - 1;
/* 528:477 */           default_offset += offset;
/* 529:478 */           this.goto_set.set(default_offset);
/* 530:480 */           for (int j = 0; j < npairs; j++)
/* 531:    */           {
/* 532:481 */             int match = bytes.readInt();
/* 533:    */             
/* 534:483 */             index = offset + bytes.readInt();
/* 535:484 */             this.goto_set.set(index);
/* 536:    */           }
/* 537:    */         }
/* 538:487 */         break;
/* 539:    */       case 153: 
/* 540:    */       case 154: 
/* 541:    */       case 155: 
/* 542:    */       case 156: 
/* 543:    */       case 157: 
/* 544:    */       case 158: 
/* 545:    */       case 159: 
/* 546:    */       case 160: 
/* 547:    */       case 161: 
/* 548:    */       case 162: 
/* 549:    */       case 163: 
/* 550:    */       case 164: 
/* 551:    */       case 165: 
/* 552:    */       case 166: 
/* 553:    */       case 167: 
/* 554:    */       case 168: 
/* 555:    */       case 198: 
/* 556:    */       case 199: 
/* 557:495 */         index = bytes.getIndex() + bytes.readShort() - 1;
/* 558:    */         
/* 559:497 */         this.goto_set.set(index);
/* 560:498 */         break;
/* 561:    */       case 200: 
/* 562:    */       case 201: 
/* 563:502 */         index = bytes.getIndex() + bytes.readInt() - 1;
/* 564:503 */         this.goto_set.set(index);
/* 565:504 */         break;
/* 566:    */       case 169: 
/* 567:    */       case 172: 
/* 568:    */       case 173: 
/* 569:    */       case 174: 
/* 570:    */       case 175: 
/* 571:    */       case 176: 
/* 572:    */       case 177: 
/* 573:    */       case 178: 
/* 574:    */       case 179: 
/* 575:    */       case 180: 
/* 576:    */       case 181: 
/* 577:    */       case 182: 
/* 578:    */       case 183: 
/* 579:    */       case 184: 
/* 580:    */       case 185: 
/* 581:    */       case 186: 
/* 582:    */       case 187: 
/* 583:    */       case 188: 
/* 584:    */       case 189: 
/* 585:    */       case 190: 
/* 586:    */       case 191: 
/* 587:    */       case 192: 
/* 588:    */       case 193: 
/* 589:    */       case 194: 
/* 590:    */       case 195: 
/* 591:    */       case 196: 
/* 592:    */       case 197: 
/* 593:    */       default: 
/* 594:507 */         bytes.unreadByte();
/* 595:508 */         codeToHTML(bytes, 0);
/* 596:    */       }
/* 597:    */     }
/* 598:    */   }
/* 599:    */   
/* 600:    */   private void writeMethod(Method method, int method_number)
/* 601:    */     throws IOException
/* 602:    */   {
/* 603:520 */     String signature = method.getSignature();
/* 604:    */     
/* 605:522 */     String[] args = Utility.methodSignatureArgumentTypes(signature, false);
/* 606:    */     
/* 607:524 */     String type = Utility.methodSignatureReturnType(signature, false);
/* 608:    */     
/* 609:526 */     String name = method.getName();
/* 610:527 */     String html_name = Class2HTML.toHTML(name);
/* 611:    */     
/* 612:529 */     String access = Utility.accessToString(method.getAccessFlags());
/* 613:530 */     access = Utility.replace(access, " ", "&nbsp;");
/* 614:    */     
/* 615:532 */     Attribute[] attributes = method.getAttributes();
/* 616:    */     
/* 617:534 */     this.file.print("<P><B><FONT COLOR=\"#FF0000\">" + access + "</FONT>&nbsp;" + "<A NAME=method" + method_number + ">" + Class2HTML.referenceType(type) + "</A>&nbsp<A HREF=\"" + this.class_name + "_methods.html#method" + method_number + "\" TARGET=Methods>" + html_name + "</A>(");
/* 618:539 */     for (int i = 0; i < args.length; i++)
/* 619:    */     {
/* 620:540 */       this.file.print(Class2HTML.referenceType(args[i]));
/* 621:541 */       if (i < args.length - 1) {
/* 622:542 */         this.file.print(",&nbsp;");
/* 623:    */       }
/* 624:    */     }
/* 625:545 */     this.file.println(")</B></P>");
/* 626:    */     
/* 627:547 */     Code c = null;
/* 628:548 */     byte[] code = null;
/* 629:550 */     if (attributes.length > 0)
/* 630:    */     {
/* 631:551 */       this.file.print("<H4>Attributes</H4><UL>\n");
/* 632:552 */       for (int i = 0; i < attributes.length; i++)
/* 633:    */       {
/* 634:553 */         byte tag = attributes[i].getTag();
/* 635:555 */         if (tag != -1) {
/* 636:556 */           this.file.print("<LI><A HREF=\"" + this.class_name + "_attributes.html#method" + method_number + "@" + i + "\" TARGET=Attributes>" + Constants.ATTRIBUTE_NAMES[tag] + "</A></LI>\n");
/* 637:    */         } else {
/* 638:559 */           this.file.print("<LI>" + attributes[i] + "</LI>");
/* 639:    */         }
/* 640:561 */         if (tag == 2)
/* 641:    */         {
/* 642:562 */           c = (Code)attributes[i];
/* 643:563 */           Attribute[] attributes2 = c.getAttributes();
/* 644:564 */           code = c.getCode();
/* 645:    */           
/* 646:566 */           this.file.print("<UL>");
/* 647:567 */           for (int j = 0; j < attributes2.length; j++)
/* 648:    */           {
/* 649:568 */             tag = attributes2[j].getTag();
/* 650:569 */             this.file.print("<LI><A HREF=\"" + this.class_name + "_attributes.html#" + "method" + method_number + "@" + i + "@" + j + "\" TARGET=Attributes>" + Constants.ATTRIBUTE_NAMES[tag] + "</A></LI>\n");
/* 651:    */           }
/* 652:574 */           this.file.print("</UL>");
/* 653:    */         }
/* 654:    */       }
/* 655:577 */       this.file.println("</UL>");
/* 656:    */     }
/* 657:580 */     if (code != null)
/* 658:    */     {
/* 659:584 */       ByteSequence stream = new ByteSequence(code);
/* 660:585 */       stream.mark(stream.available());
/* 661:586 */       findGotos(stream, method, c);
/* 662:587 */       stream.reset();
/* 663:    */       
/* 664:589 */       this.file.println("<TABLE BORDER=0><TR><TH ALIGN=LEFT>Byte<BR>offset</TH><TH ALIGN=LEFT>Instruction</TH><TH ALIGN=LEFT>Argument</TH>");
/* 665:592 */       for (int i = 0; stream.available() > 0; i++)
/* 666:    */       {
/* 667:593 */         int offset = stream.getIndex();
/* 668:594 */         String str = codeToHTML(stream, method_number);
/* 669:595 */         String anchor = "";
/* 670:600 */         if (this.goto_set.get(offset)) {
/* 671:601 */           anchor = "<A NAME=code" + method_number + "@" + offset + "></A>";
/* 672:    */         }
/* 673:    */         String anchor2;
/* 674:604 */         if (stream.getIndex() == code.length) {
/* 675:605 */           anchor2 = "<A NAME=code" + method_number + "@" + code.length + ">" + offset + "</A>";
/* 676:    */         } else {
/* 677:607 */           anchor2 = "" + offset;
/* 678:    */         }
/* 679:609 */         this.file.println("<TR VALIGN=TOP><TD>" + anchor2 + "</TD><TD>" + anchor + str + "</TR>");
/* 680:    */       }
/* 681:613 */       this.file.println("<TR><TD> </A></TD></TR>");
/* 682:614 */       this.file.println("</TABLE>");
/* 683:    */     }
/* 684:    */   }
/* 685:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.CodeHTML
 * JD-Core Version:    0.7.0.1
 */
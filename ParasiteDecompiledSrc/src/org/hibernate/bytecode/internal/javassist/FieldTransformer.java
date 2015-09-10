/*   1:    */ package org.hibernate.bytecode.internal.javassist;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import javassist.CannotCompileException;
/*  11:    */ import javassist.bytecode.BadBytecode;
/*  12:    */ import javassist.bytecode.Bytecode;
/*  13:    */ import javassist.bytecode.ClassFile;
/*  14:    */ import javassist.bytecode.CodeAttribute;
/*  15:    */ import javassist.bytecode.CodeIterator;
/*  16:    */ import javassist.bytecode.ConstPool;
/*  17:    */ import javassist.bytecode.Descriptor;
/*  18:    */ import javassist.bytecode.FieldInfo;
/*  19:    */ import javassist.bytecode.MethodInfo;
/*  20:    */ 
/*  21:    */ public class FieldTransformer
/*  22:    */ {
/*  23:    */   private static final String EACH_READ_METHOD_PREFIX = "$javassist_read_";
/*  24:    */   private static final String EACH_WRITE_METHOD_PREFIX = "$javassist_write_";
/*  25: 60 */   private static final String FIELD_HANDLED_TYPE_NAME = FieldHandled.class.getName();
/*  26:    */   private static final String HANDLER_FIELD_NAME = "$JAVASSIST_READ_WRITE_HANDLER";
/*  27: 65 */   private static final String FIELD_HANDLER_TYPE_NAME = FieldHandler.class.getName();
/*  28: 68 */   private static final String HANDLER_FIELD_DESCRIPTOR = 'L' + FIELD_HANDLER_TYPE_NAME.replace('.', '/') + ';';
/*  29:    */   private static final String GETFIELDHANDLER_METHOD_NAME = "getFieldHandler";
/*  30:    */   private static final String SETFIELDHANDLER_METHOD_NAME = "setFieldHandler";
/*  31: 75 */   private static final String GETFIELDHANDLER_METHOD_DESCRIPTOR = "()" + HANDLER_FIELD_DESCRIPTOR;
/*  32: 78 */   private static final String SETFIELDHANDLER_METHOD_DESCRIPTOR = "(" + HANDLER_FIELD_DESCRIPTOR + ")V";
/*  33:    */   private FieldFilter filter;
/*  34:    */   
/*  35:    */   public FieldTransformer()
/*  36:    */   {
/*  37: 84 */     this(null);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public FieldTransformer(FieldFilter f)
/*  41:    */   {
/*  42: 88 */     this.filter = f;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setFieldFilter(FieldFilter f)
/*  46:    */   {
/*  47: 92 */     this.filter = f;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void transform(File file)
/*  51:    */     throws Exception
/*  52:    */   {
/*  53: 96 */     DataInputStream in = new DataInputStream(new FileInputStream(file));
/*  54: 97 */     ClassFile classfile = new ClassFile(in);
/*  55: 98 */     transform(classfile);
/*  56: 99 */     DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
/*  57:    */     try
/*  58:    */     {
/*  59:101 */       classfile.write(out);
/*  60:    */     }
/*  61:    */     finally
/*  62:    */     {
/*  63:103 */       out.close();
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void transform(ClassFile classfile)
/*  68:    */     throws Exception
/*  69:    */   {
/*  70:108 */     if (classfile.isInterface()) {
/*  71:109 */       return;
/*  72:    */     }
/*  73:    */     try
/*  74:    */     {
/*  75:112 */       addFieldHandlerField(classfile);
/*  76:113 */       addGetFieldHandlerMethod(classfile);
/*  77:114 */       addSetFieldHandlerMethod(classfile);
/*  78:115 */       addFieldHandledInterface(classfile);
/*  79:116 */       addReadWriteMethods(classfile);
/*  80:117 */       transformInvokevirtualsIntoPutAndGetfields(classfile);
/*  81:    */     }
/*  82:    */     catch (CannotCompileException e)
/*  83:    */     {
/*  84:119 */       throw new RuntimeException(e.getMessage(), e);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void addFieldHandlerField(ClassFile classfile)
/*  89:    */     throws CannotCompileException
/*  90:    */   {
/*  91:125 */     ConstPool cp = classfile.getConstPool();
/*  92:126 */     FieldInfo finfo = new FieldInfo(cp, "$JAVASSIST_READ_WRITE_HANDLER", HANDLER_FIELD_DESCRIPTOR);
/*  93:    */     
/*  94:128 */     finfo.setAccessFlags(130);
/*  95:129 */     classfile.addField(finfo);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void addGetFieldHandlerMethod(ClassFile classfile)
/*  99:    */     throws CannotCompileException
/* 100:    */   {
/* 101:134 */     ConstPool cp = classfile.getConstPool();
/* 102:135 */     int this_class_index = cp.getThisClassInfo();
/* 103:136 */     MethodInfo minfo = new MethodInfo(cp, "getFieldHandler", GETFIELDHANDLER_METHOD_DESCRIPTOR);
/* 104:    */     
/* 105:    */ 
/* 106:139 */     Bytecode code = new Bytecode(cp, 2, 1);
/* 107:    */     
/* 108:141 */     code.addAload(0);
/* 109:    */     
/* 110:143 */     code.addOpcode(180);
/* 111:144 */     int field_index = cp.addFieldrefInfo(this_class_index, "$JAVASSIST_READ_WRITE_HANDLER", HANDLER_FIELD_DESCRIPTOR);
/* 112:    */     
/* 113:146 */     code.addIndex(field_index);
/* 114:    */     
/* 115:148 */     code.addOpcode(176);
/* 116:149 */     minfo.setCodeAttribute(code.toCodeAttribute());
/* 117:150 */     minfo.setAccessFlags(1);
/* 118:151 */     classfile.addMethod(minfo);
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void addSetFieldHandlerMethod(ClassFile classfile)
/* 122:    */     throws CannotCompileException
/* 123:    */   {
/* 124:156 */     ConstPool cp = classfile.getConstPool();
/* 125:157 */     int this_class_index = cp.getThisClassInfo();
/* 126:158 */     MethodInfo minfo = new MethodInfo(cp, "setFieldHandler", SETFIELDHANDLER_METHOD_DESCRIPTOR);
/* 127:    */     
/* 128:    */ 
/* 129:161 */     Bytecode code = new Bytecode(cp, 3, 3);
/* 130:    */     
/* 131:163 */     code.addAload(0);
/* 132:    */     
/* 133:165 */     code.addAload(1);
/* 134:    */     
/* 135:167 */     code.addOpcode(181);
/* 136:168 */     int field_index = cp.addFieldrefInfo(this_class_index, "$JAVASSIST_READ_WRITE_HANDLER", HANDLER_FIELD_DESCRIPTOR);
/* 137:    */     
/* 138:170 */     code.addIndex(field_index);
/* 139:    */     
/* 140:172 */     code.addOpcode(177);
/* 141:173 */     minfo.setCodeAttribute(code.toCodeAttribute());
/* 142:174 */     minfo.setAccessFlags(1);
/* 143:175 */     classfile.addMethod(minfo);
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void addFieldHandledInterface(ClassFile classfile)
/* 147:    */   {
/* 148:179 */     String[] interfaceNames = classfile.getInterfaces();
/* 149:180 */     String[] newInterfaceNames = new String[interfaceNames.length + 1];
/* 150:181 */     System.arraycopy(interfaceNames, 0, newInterfaceNames, 0, interfaceNames.length);
/* 151:    */     
/* 152:183 */     newInterfaceNames[(newInterfaceNames.length - 1)] = FIELD_HANDLED_TYPE_NAME;
/* 153:184 */     classfile.setInterfaces(newInterfaceNames);
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void addReadWriteMethods(ClassFile classfile)
/* 157:    */     throws CannotCompileException
/* 158:    */   {
/* 159:189 */     List fields = classfile.getFields();
/* 160:190 */     for (Iterator field_iter = fields.iterator(); field_iter.hasNext();)
/* 161:    */     {
/* 162:191 */       FieldInfo finfo = (FieldInfo)field_iter.next();
/* 163:192 */       if (((finfo.getAccessFlags() & 0x8) == 0) && (!finfo.getName().equals("$JAVASSIST_READ_WRITE_HANDLER")))
/* 164:    */       {
/* 165:195 */         if (this.filter.handleRead(finfo.getDescriptor(), finfo.getName())) {
/* 166:197 */           addReadMethod(classfile, finfo);
/* 167:    */         }
/* 168:199 */         if (this.filter.handleWrite(finfo.getDescriptor(), finfo.getName())) {
/* 169:201 */           addWriteMethod(classfile, finfo);
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   private void addReadMethod(ClassFile classfile, FieldInfo finfo)
/* 176:    */     throws CannotCompileException
/* 177:    */   {
/* 178:209 */     ConstPool cp = classfile.getConstPool();
/* 179:210 */     int this_class_index = cp.getThisClassInfo();
/* 180:211 */     String desc = "()" + finfo.getDescriptor();
/* 181:212 */     MethodInfo minfo = new MethodInfo(cp, "$javassist_read_" + finfo.getName(), desc);
/* 182:    */     
/* 183:    */ 
/* 184:215 */     Bytecode code = new Bytecode(cp, 5, 3);
/* 185:    */     
/* 186:217 */     code.addAload(0);
/* 187:    */     
/* 188:219 */     code.addOpcode(180);
/* 189:220 */     int base_field_index = cp.addFieldrefInfo(this_class_index, finfo.getName(), finfo.getDescriptor());
/* 190:    */     
/* 191:222 */     code.addIndex(base_field_index);
/* 192:    */     
/* 193:224 */     code.addAload(0);
/* 194:    */     
/* 195:226 */     int enabled_class_index = cp.addClassInfo(FIELD_HANDLED_TYPE_NAME);
/* 196:227 */     code.addInvokeinterface(enabled_class_index, "getFieldHandler", GETFIELDHANDLER_METHOD_DESCRIPTOR, 1);
/* 197:    */     
/* 198:    */ 
/* 199:    */ 
/* 200:231 */     code.addOpcode(199);
/* 201:232 */     code.addIndex(4);
/* 202:    */     
/* 203:234 */     addTypeDependDataReturn(code, finfo.getDescriptor());
/* 204:    */     
/* 205:236 */     addTypeDependDataStore(code, finfo.getDescriptor(), 1);
/* 206:    */     
/* 207:238 */     code.addAload(0);
/* 208:    */     
/* 209:240 */     code.addInvokeinterface(enabled_class_index, "getFieldHandler", GETFIELDHANDLER_METHOD_DESCRIPTOR, 1);
/* 210:    */     
/* 211:    */ 
/* 212:    */ 
/* 213:244 */     code.addAload(0);
/* 214:    */     
/* 215:246 */     code.addLdc(finfo.getName());
/* 216:    */     
/* 217:248 */     addTypeDependDataLoad(code, finfo.getDescriptor(), 1);
/* 218:    */     
/* 219:250 */     addInvokeFieldHandlerMethod(classfile, code, finfo.getDescriptor(), true);
/* 220:    */     
/* 221:    */ 
/* 222:253 */     addTypeDependDataReturn(code, finfo.getDescriptor());
/* 223:    */     
/* 224:255 */     minfo.setCodeAttribute(code.toCodeAttribute());
/* 225:256 */     minfo.setAccessFlags(1);
/* 226:257 */     classfile.addMethod(minfo);
/* 227:    */   }
/* 228:    */   
/* 229:    */   private void addWriteMethod(ClassFile classfile, FieldInfo finfo)
/* 230:    */     throws CannotCompileException
/* 231:    */   {
/* 232:262 */     ConstPool cp = classfile.getConstPool();
/* 233:263 */     int this_class_index = cp.getThisClassInfo();
/* 234:264 */     String desc = "(" + finfo.getDescriptor() + ")V";
/* 235:265 */     MethodInfo minfo = new MethodInfo(cp, "$javassist_write_" + finfo.getName(), desc);
/* 236:    */     
/* 237:    */ 
/* 238:268 */     Bytecode code = new Bytecode(cp, 6, 3);
/* 239:    */     
/* 240:270 */     code.addAload(0);
/* 241:    */     
/* 242:272 */     int enabled_class_index = cp.addClassInfo(FIELD_HANDLED_TYPE_NAME);
/* 243:273 */     code.addInvokeinterface(enabled_class_index, "getFieldHandler", GETFIELDHANDLER_METHOD_DESCRIPTOR, 1);
/* 244:    */     
/* 245:    */ 
/* 246:    */ 
/* 247:277 */     code.addOpcode(199);
/* 248:278 */     code.addIndex(9);
/* 249:    */     
/* 250:280 */     code.addAload(0);
/* 251:    */     
/* 252:282 */     addTypeDependDataLoad(code, finfo.getDescriptor(), 1);
/* 253:    */     
/* 254:284 */     code.addOpcode(181);
/* 255:285 */     int base_field_index = cp.addFieldrefInfo(this_class_index, finfo.getName(), finfo.getDescriptor());
/* 256:    */     
/* 257:287 */     code.addIndex(base_field_index);
/* 258:288 */     code.growStack(-Descriptor.dataSize(finfo.getDescriptor()));
/* 259:    */     
/* 260:290 */     code.addOpcode(177);
/* 261:    */     
/* 262:292 */     code.addAload(0);
/* 263:    */     
/* 264:294 */     code.addOpcode(89);
/* 265:    */     
/* 266:296 */     code.addInvokeinterface(enabled_class_index, "getFieldHandler", GETFIELDHANDLER_METHOD_DESCRIPTOR, 1);
/* 267:    */     
/* 268:    */ 
/* 269:    */ 
/* 270:300 */     code.addAload(0);
/* 271:    */     
/* 272:302 */     code.addLdc(finfo.getName());
/* 273:    */     
/* 274:304 */     code.addAload(0);
/* 275:    */     
/* 276:306 */     code.addOpcode(180);
/* 277:307 */     code.addIndex(base_field_index);
/* 278:308 */     code.growStack(Descriptor.dataSize(finfo.getDescriptor()) - 1);
/* 279:    */     
/* 280:310 */     addTypeDependDataLoad(code, finfo.getDescriptor(), 1);
/* 281:    */     
/* 282:312 */     addInvokeFieldHandlerMethod(classfile, code, finfo.getDescriptor(), false);
/* 283:    */     
/* 284:    */ 
/* 285:315 */     code.addOpcode(181);
/* 286:316 */     code.addIndex(base_field_index);
/* 287:317 */     code.growStack(-Descriptor.dataSize(finfo.getDescriptor()));
/* 288:    */     
/* 289:319 */     code.addOpcode(177);
/* 290:    */     
/* 291:321 */     minfo.setCodeAttribute(code.toCodeAttribute());
/* 292:322 */     minfo.setAccessFlags(1);
/* 293:323 */     classfile.addMethod(minfo);
/* 294:    */   }
/* 295:    */   
/* 296:    */   private void transformInvokevirtualsIntoPutAndGetfields(ClassFile classfile)
/* 297:    */     throws CannotCompileException
/* 298:    */   {
/* 299:328 */     List methods = classfile.getMethods();
/* 300:329 */     for (Iterator method_iter = methods.iterator(); method_iter.hasNext();)
/* 301:    */     {
/* 302:330 */       MethodInfo minfo = (MethodInfo)method_iter.next();
/* 303:331 */       String methodName = minfo.getName();
/* 304:332 */       if ((!methodName.startsWith("$javassist_read_")) && (!methodName.startsWith("$javassist_write_")) && (!methodName.equals("getFieldHandler")) && (!methodName.equals("setFieldHandler")))
/* 305:    */       {
/* 306:338 */         CodeAttribute codeAttr = minfo.getCodeAttribute();
/* 307:339 */         if (codeAttr != null)
/* 308:    */         {
/* 309:342 */           CodeIterator iter = codeAttr.iterator();
/* 310:343 */           while (iter.hasNext()) {
/* 311:    */             try
/* 312:    */             {
/* 313:345 */               int pos = iter.next();
/* 314:346 */               pos = transformInvokevirtualsIntoGetfields(classfile, iter, pos);
/* 315:347 */               pos = transformInvokevirtualsIntoPutfields(classfile, iter, pos);
/* 316:    */             }
/* 317:    */             catch (BadBytecode e)
/* 318:    */             {
/* 319:350 */               throw new CannotCompileException(e);
/* 320:    */             }
/* 321:    */           }
/* 322:    */         }
/* 323:    */       }
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   private int transformInvokevirtualsIntoGetfields(ClassFile classfile, CodeIterator iter, int pos)
/* 328:    */   {
/* 329:357 */     ConstPool cp = classfile.getConstPool();
/* 330:358 */     int c = iter.byteAt(pos);
/* 331:359 */     if (c != 180) {
/* 332:360 */       return pos;
/* 333:    */     }
/* 334:362 */     int index = iter.u16bitAt(pos + 1);
/* 335:363 */     String fieldName = cp.getFieldrefName(index);
/* 336:364 */     String className = cp.getFieldrefClassName(index);
/* 337:365 */     if (!this.filter.handleReadAccess(className, fieldName)) {
/* 338:366 */       return pos;
/* 339:    */     }
/* 340:368 */     String desc = "()" + cp.getFieldrefType(index);
/* 341:369 */     int read_method_index = cp.addMethodrefInfo(cp.getThisClassInfo(), "$javassist_read_" + fieldName, desc);
/* 342:    */     
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:374 */     iter.writeByte(182, pos);
/* 347:375 */     iter.write16bit(read_method_index, pos + 1);
/* 348:376 */     return pos;
/* 349:    */   }
/* 350:    */   
/* 351:    */   private int transformInvokevirtualsIntoPutfields(ClassFile classfile, CodeIterator iter, int pos)
/* 352:    */   {
/* 353:382 */     ConstPool cp = classfile.getConstPool();
/* 354:383 */     int c = iter.byteAt(pos);
/* 355:384 */     if (c != 181) {
/* 356:385 */       return pos;
/* 357:    */     }
/* 358:387 */     int index = iter.u16bitAt(pos + 1);
/* 359:388 */     String fieldName = cp.getFieldrefName(index);
/* 360:389 */     String className = cp.getFieldrefClassName(index);
/* 361:390 */     if (!this.filter.handleWriteAccess(className, fieldName)) {
/* 362:391 */       return pos;
/* 363:    */     }
/* 364:393 */     String desc = "(" + cp.getFieldrefType(index) + ")V";
/* 365:394 */     int write_method_index = cp.addMethodrefInfo(cp.getThisClassInfo(), "$javassist_write_" + fieldName, desc);
/* 366:    */     
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:399 */     iter.writeByte(182, pos);
/* 371:400 */     iter.write16bit(write_method_index, pos + 1);
/* 372:401 */     return pos;
/* 373:    */   }
/* 374:    */   
/* 375:    */   private static void addInvokeFieldHandlerMethod(ClassFile classfile, Bytecode code, String typeName, boolean isReadMethod)
/* 376:    */   {
/* 377:406 */     ConstPool cp = classfile.getConstPool();
/* 378:    */     
/* 379:408 */     int callback_type_index = cp.addClassInfo(FIELD_HANDLER_TYPE_NAME);
/* 380:409 */     if (((typeName.charAt(0) == 'L') && (typeName.charAt(typeName.length() - 1) == ';')) || (typeName.charAt(0) == '['))
/* 381:    */     {
/* 382:413 */       int indexOfL = typeName.indexOf('L');
/* 383:    */       String type;
/* 384:415 */       if (indexOfL == 0)
/* 385:    */       {
/* 386:417 */         String type = typeName.substring(1, typeName.length() - 1);
/* 387:418 */         type = type.replace('/', '.');
/* 388:    */       }
/* 389:    */       else
/* 390:    */       {
/* 391:    */         String type;
/* 392:419 */         if (indexOfL == -1) {
/* 393:422 */           type = typeName;
/* 394:    */         } else {
/* 395:425 */           type = typeName.replace('/', '.');
/* 396:    */         }
/* 397:    */       }
/* 398:427 */       if (isReadMethod)
/* 399:    */       {
/* 400:428 */         code.addInvokeinterface(callback_type_index, "readObject", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", 4);
/* 401:    */         
/* 402:    */ 
/* 403:    */ 
/* 404:    */ 
/* 405:    */ 
/* 406:    */ 
/* 407:435 */         code.addCheckcast(type);
/* 408:    */       }
/* 409:    */       else
/* 410:    */       {
/* 411:437 */         code.addInvokeinterface(callback_type_index, "writeObject", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", 5);
/* 412:    */         
/* 413:    */ 
/* 414:    */ 
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:444 */         code.addCheckcast(type);
/* 419:    */       }
/* 420:    */     }
/* 421:446 */     else if (typeName.equals("Z"))
/* 422:    */     {
/* 423:448 */       if (isReadMethod) {
/* 424:449 */         code.addInvokeinterface(callback_type_index, "readBoolean", "(Ljava/lang/Object;Ljava/lang/String;Z)Z", 4);
/* 425:    */       } else {
/* 426:452 */         code.addInvokeinterface(callback_type_index, "writeBoolean", "(Ljava/lang/Object;Ljava/lang/String;ZZ)Z", 5);
/* 427:    */       }
/* 428:    */     }
/* 429:455 */     else if (typeName.equals("B"))
/* 430:    */     {
/* 431:457 */       if (isReadMethod) {
/* 432:458 */         code.addInvokeinterface(callback_type_index, "readByte", "(Ljava/lang/Object;Ljava/lang/String;B)B", 4);
/* 433:    */       } else {
/* 434:461 */         code.addInvokeinterface(callback_type_index, "writeByte", "(Ljava/lang/Object;Ljava/lang/String;BB)B", 5);
/* 435:    */       }
/* 436:    */     }
/* 437:464 */     else if (typeName.equals("C"))
/* 438:    */     {
/* 439:466 */       if (isReadMethod) {
/* 440:467 */         code.addInvokeinterface(callback_type_index, "readChar", "(Ljava/lang/Object;Ljava/lang/String;C)C", 4);
/* 441:    */       } else {
/* 442:470 */         code.addInvokeinterface(callback_type_index, "writeChar", "(Ljava/lang/Object;Ljava/lang/String;CC)C", 5);
/* 443:    */       }
/* 444:    */     }
/* 445:473 */     else if (typeName.equals("I"))
/* 446:    */     {
/* 447:475 */       if (isReadMethod) {
/* 448:476 */         code.addInvokeinterface(callback_type_index, "readInt", "(Ljava/lang/Object;Ljava/lang/String;I)I", 4);
/* 449:    */       } else {
/* 450:479 */         code.addInvokeinterface(callback_type_index, "writeInt", "(Ljava/lang/Object;Ljava/lang/String;II)I", 5);
/* 451:    */       }
/* 452:    */     }
/* 453:482 */     else if (typeName.equals("S"))
/* 454:    */     {
/* 455:484 */       if (isReadMethod) {
/* 456:485 */         code.addInvokeinterface(callback_type_index, "readShort", "(Ljava/lang/Object;Ljava/lang/String;S)S", 4);
/* 457:    */       } else {
/* 458:488 */         code.addInvokeinterface(callback_type_index, "writeShort", "(Ljava/lang/Object;Ljava/lang/String;SS)S", 5);
/* 459:    */       }
/* 460:    */     }
/* 461:491 */     else if (typeName.equals("D"))
/* 462:    */     {
/* 463:493 */       if (isReadMethod) {
/* 464:494 */         code.addInvokeinterface(callback_type_index, "readDouble", "(Ljava/lang/Object;Ljava/lang/String;D)D", 5);
/* 465:    */       } else {
/* 466:497 */         code.addInvokeinterface(callback_type_index, "writeDouble", "(Ljava/lang/Object;Ljava/lang/String;DD)D", 7);
/* 467:    */       }
/* 468:    */     }
/* 469:500 */     else if (typeName.equals("F"))
/* 470:    */     {
/* 471:502 */       if (isReadMethod) {
/* 472:503 */         code.addInvokeinterface(callback_type_index, "readFloat", "(Ljava/lang/Object;Ljava/lang/String;F)F", 4);
/* 473:    */       } else {
/* 474:506 */         code.addInvokeinterface(callback_type_index, "writeFloat", "(Ljava/lang/Object;Ljava/lang/String;FF)F", 5);
/* 475:    */       }
/* 476:    */     }
/* 477:509 */     else if (typeName.equals("J"))
/* 478:    */     {
/* 479:511 */       if (isReadMethod) {
/* 480:512 */         code.addInvokeinterface(callback_type_index, "readLong", "(Ljava/lang/Object;Ljava/lang/String;J)J", 5);
/* 481:    */       } else {
/* 482:515 */         code.addInvokeinterface(callback_type_index, "writeLong", "(Ljava/lang/Object;Ljava/lang/String;JJ)J", 7);
/* 483:    */       }
/* 484:    */     }
/* 485:    */     else
/* 486:    */     {
/* 487:520 */       throw new RuntimeException("bad type: " + typeName);
/* 488:    */     }
/* 489:    */   }
/* 490:    */   
/* 491:    */   private static void addTypeDependDataLoad(Bytecode code, String typeName, int i)
/* 492:    */   {
/* 493:526 */     if (((typeName.charAt(0) == 'L') && (typeName.charAt(typeName.length() - 1) == ';')) || (typeName.charAt(0) == '[')) {
/* 494:530 */       code.addAload(i);
/* 495:531 */     } else if ((typeName.equals("Z")) || (typeName.equals("B")) || (typeName.equals("C")) || (typeName.equals("I")) || (typeName.equals("S"))) {
/* 496:535 */       code.addIload(i);
/* 497:536 */     } else if (typeName.equals("D")) {
/* 498:538 */       code.addDload(i);
/* 499:539 */     } else if (typeName.equals("F")) {
/* 500:541 */       code.addFload(i);
/* 501:542 */     } else if (typeName.equals("J")) {
/* 502:544 */       code.addLload(i);
/* 503:    */     } else {
/* 504:547 */       throw new RuntimeException("bad type: " + typeName);
/* 505:    */     }
/* 506:    */   }
/* 507:    */   
/* 508:    */   private static void addTypeDependDataStore(Bytecode code, String typeName, int i)
/* 509:    */   {
/* 510:553 */     if (((typeName.charAt(0) == 'L') && (typeName.charAt(typeName.length() - 1) == ';')) || (typeName.charAt(0) == '[')) {
/* 511:557 */       code.addAstore(i);
/* 512:558 */     } else if ((typeName.equals("Z")) || (typeName.equals("B")) || (typeName.equals("C")) || (typeName.equals("I")) || (typeName.equals("S"))) {
/* 513:562 */       code.addIstore(i);
/* 514:563 */     } else if (typeName.equals("D")) {
/* 515:565 */       code.addDstore(i);
/* 516:566 */     } else if (typeName.equals("F")) {
/* 517:568 */       code.addFstore(i);
/* 518:569 */     } else if (typeName.equals("J")) {
/* 519:571 */       code.addLstore(i);
/* 520:    */     } else {
/* 521:574 */       throw new RuntimeException("bad type: " + typeName);
/* 522:    */     }
/* 523:    */   }
/* 524:    */   
/* 525:    */   private static void addTypeDependDataReturn(Bytecode code, String typeName)
/* 526:    */   {
/* 527:579 */     if (((typeName.charAt(0) == 'L') && (typeName.charAt(typeName.length() - 1) == ';')) || (typeName.charAt(0) == '[')) {
/* 528:583 */       code.addOpcode(176);
/* 529:584 */     } else if ((typeName.equals("Z")) || (typeName.equals("B")) || (typeName.equals("C")) || (typeName.equals("I")) || (typeName.equals("S"))) {
/* 530:588 */       code.addOpcode(172);
/* 531:589 */     } else if (typeName.equals("D")) {
/* 532:591 */       code.addOpcode(175);
/* 533:592 */     } else if (typeName.equals("F")) {
/* 534:594 */       code.addOpcode(174);
/* 535:595 */     } else if (typeName.equals("J")) {
/* 536:597 */       code.addOpcode(173);
/* 537:    */     } else {
/* 538:600 */       throw new RuntimeException("bad type: " + typeName);
/* 539:    */     }
/* 540:    */   }
/* 541:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.FieldTransformer
 * JD-Core Version:    0.7.0.1
 */
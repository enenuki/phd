/*    1:     */ package net.sourceforge.htmlunit.corejs.classfile;
/*    2:     */ 
/*    3:     */ import net.sourceforge.htmlunit.corejs.javascript.ObjToIntMap;
/*    4:     */ import net.sourceforge.htmlunit.corejs.javascript.UintMap;
/*    5:     */ 
/*    6:     */ final class ConstantPool
/*    7:     */ {
/*    8:     */   private static final int ConstantPoolSize = 256;
/*    9:     */   static final byte CONSTANT_Class = 7;
/*   10:     */   static final byte CONSTANT_Fieldref = 9;
/*   11:     */   static final byte CONSTANT_Methodref = 10;
/*   12:     */   static final byte CONSTANT_InterfaceMethodref = 11;
/*   13:     */   static final byte CONSTANT_String = 8;
/*   14:     */   static final byte CONSTANT_Integer = 3;
/*   15:     */   static final byte CONSTANT_Float = 4;
/*   16:     */   static final byte CONSTANT_Long = 5;
/*   17:     */   static final byte CONSTANT_Double = 6;
/*   18:     */   static final byte CONSTANT_NameAndType = 12;
/*   19:     */   static final byte CONSTANT_Utf8 = 1;
/*   20:     */   private ClassFileWriter cfw;
/*   21:     */   private static final int MAX_UTF_ENCODING_SIZE = 65535;
/*   22:     */   
/*   23:     */   ConstantPool(ClassFileWriter cfw)
/*   24:     */   {
/*   25:4406 */     this.cfw = cfw;
/*   26:4407 */     this.itsTopIndex = 1;
/*   27:4408 */     this.itsPool = new byte[256];
/*   28:4409 */     this.itsTop = 0;
/*   29:     */   }
/*   30:     */   
/*   31:     */   int write(byte[] data, int offset)
/*   32:     */   {
/*   33:4428 */     offset = ClassFileWriter.putInt16((short)this.itsTopIndex, data, offset);
/*   34:4429 */     System.arraycopy(this.itsPool, 0, data, offset, this.itsTop);
/*   35:4430 */     offset += this.itsTop;
/*   36:4431 */     return offset;
/*   37:     */   }
/*   38:     */   
/*   39:     */   int getWriteSize()
/*   40:     */   {
/*   41:4436 */     return 2 + this.itsTop;
/*   42:     */   }
/*   43:     */   
/*   44:     */   int addConstant(int k)
/*   45:     */   {
/*   46:4441 */     ensure(5);
/*   47:4442 */     this.itsPool[(this.itsTop++)] = 3;
/*   48:4443 */     this.itsTop = ClassFileWriter.putInt32(k, this.itsPool, this.itsTop);
/*   49:4444 */     this.itsPoolTypes.put(this.itsTopIndex, 3);
/*   50:4445 */     return (short)this.itsTopIndex++;
/*   51:     */   }
/*   52:     */   
/*   53:     */   int addConstant(long k)
/*   54:     */   {
/*   55:4450 */     ensure(9);
/*   56:4451 */     this.itsPool[(this.itsTop++)] = 5;
/*   57:4452 */     this.itsTop = ClassFileWriter.putInt64(k, this.itsPool, this.itsTop);
/*   58:4453 */     int index = this.itsTopIndex;
/*   59:4454 */     this.itsTopIndex += 2;
/*   60:4455 */     this.itsPoolTypes.put(index, 5);
/*   61:4456 */     return index;
/*   62:     */   }
/*   63:     */   
/*   64:     */   int addConstant(float k)
/*   65:     */   {
/*   66:4461 */     ensure(5);
/*   67:4462 */     this.itsPool[(this.itsTop++)] = 4;
/*   68:4463 */     int bits = Float.floatToIntBits(k);
/*   69:4464 */     this.itsTop = ClassFileWriter.putInt32(bits, this.itsPool, this.itsTop);
/*   70:4465 */     this.itsPoolTypes.put(this.itsTopIndex, 4);
/*   71:4466 */     return this.itsTopIndex++;
/*   72:     */   }
/*   73:     */   
/*   74:     */   int addConstant(double k)
/*   75:     */   {
/*   76:4471 */     ensure(9);
/*   77:4472 */     this.itsPool[(this.itsTop++)] = 6;
/*   78:4473 */     long bits = Double.doubleToLongBits(k);
/*   79:4474 */     this.itsTop = ClassFileWriter.putInt64(bits, this.itsPool, this.itsTop);
/*   80:4475 */     int index = this.itsTopIndex;
/*   81:4476 */     this.itsTopIndex += 2;
/*   82:4477 */     this.itsPoolTypes.put(index, 6);
/*   83:4478 */     return index;
/*   84:     */   }
/*   85:     */   
/*   86:     */   int addConstant(String k)
/*   87:     */   {
/*   88:4483 */     int utf8Index = 0xFFFF & addUtf8(k);
/*   89:4484 */     int theIndex = this.itsStringConstHash.getInt(utf8Index, -1);
/*   90:4485 */     if (theIndex == -1)
/*   91:     */     {
/*   92:4486 */       theIndex = this.itsTopIndex++;
/*   93:4487 */       ensure(3);
/*   94:4488 */       this.itsPool[(this.itsTop++)] = 8;
/*   95:4489 */       this.itsTop = ClassFileWriter.putInt16(utf8Index, this.itsPool, this.itsTop);
/*   96:4490 */       this.itsStringConstHash.put(utf8Index, theIndex);
/*   97:     */     }
/*   98:4492 */     this.itsPoolTypes.put(theIndex, 8);
/*   99:4493 */     return theIndex;
/*  100:     */   }
/*  101:     */   
/*  102:     */   boolean isUnderUtfEncodingLimit(String s)
/*  103:     */   {
/*  104:4498 */     int strLen = s.length();
/*  105:4499 */     if (strLen * 3 <= 65535) {
/*  106:4500 */       return true;
/*  107:     */     }
/*  108:4501 */     if (strLen > 65535) {
/*  109:4502 */       return false;
/*  110:     */     }
/*  111:4504 */     return strLen == getUtfEncodingLimit(s, 0, strLen);
/*  112:     */   }
/*  113:     */   
/*  114:     */   int getUtfEncodingLimit(String s, int start, int end)
/*  115:     */   {
/*  116:4513 */     if ((end - start) * 3 <= 65535) {
/*  117:4514 */       return end;
/*  118:     */     }
/*  119:4516 */     int limit = 65535;
/*  120:4517 */     for (int i = start; i != end; i++)
/*  121:     */     {
/*  122:4518 */       int c = s.charAt(i);
/*  123:4519 */       if ((0 != c) && (c <= 127)) {
/*  124:4520 */         limit--;
/*  125:4521 */       } else if (c < 2047) {
/*  126:4522 */         limit -= 2;
/*  127:     */       } else {
/*  128:4524 */         limit -= 3;
/*  129:     */       }
/*  130:4526 */       if (limit < 0) {
/*  131:4527 */         return i;
/*  132:     */       }
/*  133:     */     }
/*  134:4530 */     return end;
/*  135:     */   }
/*  136:     */   
/*  137:     */   short addUtf8(String k)
/*  138:     */   {
/*  139:4535 */     int theIndex = this.itsUtf8Hash.get(k, -1);
/*  140:4536 */     if (theIndex == -1)
/*  141:     */     {
/*  142:4537 */       int strLen = k.length();
/*  143:     */       boolean tooBigString;
/*  144:     */       boolean tooBigString;
/*  145:4539 */       if (strLen > 65535)
/*  146:     */       {
/*  147:4540 */         tooBigString = true;
/*  148:     */       }
/*  149:     */       else
/*  150:     */       {
/*  151:4542 */         tooBigString = false;
/*  152:     */         
/*  153:     */ 
/*  154:4545 */         ensure(3 + strLen * 3);
/*  155:4546 */         int top = this.itsTop;
/*  156:     */         
/*  157:4548 */         this.itsPool[(top++)] = 1;
/*  158:4549 */         top += 2;
/*  159:     */         
/*  160:4551 */         char[] chars = this.cfw.getCharBuffer(strLen);
/*  161:4552 */         k.getChars(0, strLen, chars, 0);
/*  162:4554 */         for (int i = 0; i != strLen; i++)
/*  163:     */         {
/*  164:4555 */           int c = chars[i];
/*  165:4556 */           if ((c != 0) && (c <= 127))
/*  166:     */           {
/*  167:4557 */             this.itsPool[(top++)] = ((byte)c);
/*  168:     */           }
/*  169:4558 */           else if (c > 2047)
/*  170:     */           {
/*  171:4559 */             this.itsPool[(top++)] = ((byte)(0xE0 | c >> 12));
/*  172:4560 */             this.itsPool[(top++)] = ((byte)(0x80 | c >> 6 & 0x3F));
/*  173:4561 */             this.itsPool[(top++)] = ((byte)(0x80 | c & 0x3F));
/*  174:     */           }
/*  175:     */           else
/*  176:     */           {
/*  177:4563 */             this.itsPool[(top++)] = ((byte)(0xC0 | c >> 6));
/*  178:4564 */             this.itsPool[(top++)] = ((byte)(0x80 | c & 0x3F));
/*  179:     */           }
/*  180:     */         }
/*  181:4568 */         int utfLen = top - (this.itsTop + 1 + 2);
/*  182:4569 */         if (utfLen > 65535)
/*  183:     */         {
/*  184:4570 */           tooBigString = true;
/*  185:     */         }
/*  186:     */         else
/*  187:     */         {
/*  188:4573 */           this.itsPool[(this.itsTop + 1)] = ((byte)(utfLen >>> 8));
/*  189:4574 */           this.itsPool[(this.itsTop + 2)] = ((byte)utfLen);
/*  190:     */           
/*  191:4576 */           this.itsTop = top;
/*  192:4577 */           theIndex = this.itsTopIndex++;
/*  193:4578 */           this.itsUtf8Hash.put(k, theIndex);
/*  194:     */         }
/*  195:     */       }
/*  196:4581 */       if (tooBigString) {
/*  197:4582 */         throw new IllegalArgumentException("Too big string");
/*  198:     */       }
/*  199:     */     }
/*  200:4585 */     setConstantData(theIndex, k);
/*  201:4586 */     this.itsPoolTypes.put(theIndex, 1);
/*  202:4587 */     return (short)theIndex;
/*  203:     */   }
/*  204:     */   
/*  205:     */   private short addNameAndType(String name, String type)
/*  206:     */   {
/*  207:4592 */     short nameIndex = addUtf8(name);
/*  208:4593 */     short typeIndex = addUtf8(type);
/*  209:4594 */     ensure(5);
/*  210:4595 */     this.itsPool[(this.itsTop++)] = 12;
/*  211:4596 */     this.itsTop = ClassFileWriter.putInt16(nameIndex, this.itsPool, this.itsTop);
/*  212:4597 */     this.itsTop = ClassFileWriter.putInt16(typeIndex, this.itsPool, this.itsTop);
/*  213:4598 */     this.itsPoolTypes.put(this.itsTopIndex, 12);
/*  214:4599 */     return (short)this.itsTopIndex++;
/*  215:     */   }
/*  216:     */   
/*  217:     */   short addClass(String className)
/*  218:     */   {
/*  219:4604 */     int theIndex = this.itsClassHash.get(className, -1);
/*  220:4605 */     if (theIndex == -1)
/*  221:     */     {
/*  222:4606 */       String slashed = className;
/*  223:4607 */       if (className.indexOf('.') > 0)
/*  224:     */       {
/*  225:4608 */         slashed = ClassFileWriter.getSlashedForm(className);
/*  226:4609 */         theIndex = this.itsClassHash.get(slashed, -1);
/*  227:4610 */         if (theIndex != -1) {
/*  228:4611 */           this.itsClassHash.put(className, theIndex);
/*  229:     */         }
/*  230:     */       }
/*  231:4614 */       if (theIndex == -1)
/*  232:     */       {
/*  233:4615 */         int utf8Index = addUtf8(slashed);
/*  234:4616 */         ensure(3);
/*  235:4617 */         this.itsPool[(this.itsTop++)] = 7;
/*  236:4618 */         this.itsTop = ClassFileWriter.putInt16(utf8Index, this.itsPool, this.itsTop);
/*  237:4619 */         theIndex = this.itsTopIndex++;
/*  238:4620 */         this.itsClassHash.put(slashed, theIndex);
/*  239:4621 */         if (className != slashed) {
/*  240:4622 */           this.itsClassHash.put(className, theIndex);
/*  241:     */         }
/*  242:     */       }
/*  243:     */     }
/*  244:4626 */     setConstantData(theIndex, className);
/*  245:4627 */     this.itsPoolTypes.put(theIndex, 7);
/*  246:4628 */     return (short)theIndex;
/*  247:     */   }
/*  248:     */   
/*  249:     */   short addFieldRef(String className, String fieldName, String fieldType)
/*  250:     */   {
/*  251:4633 */     FieldOrMethodRef ref = new FieldOrMethodRef(className, fieldName, fieldType);
/*  252:     */     
/*  253:     */ 
/*  254:4636 */     int theIndex = this.itsFieldRefHash.get(ref, -1);
/*  255:4637 */     if (theIndex == -1)
/*  256:     */     {
/*  257:4638 */       short ntIndex = addNameAndType(fieldName, fieldType);
/*  258:4639 */       short classIndex = addClass(className);
/*  259:4640 */       ensure(5);
/*  260:4641 */       this.itsPool[(this.itsTop++)] = 9;
/*  261:4642 */       this.itsTop = ClassFileWriter.putInt16(classIndex, this.itsPool, this.itsTop);
/*  262:4643 */       this.itsTop = ClassFileWriter.putInt16(ntIndex, this.itsPool, this.itsTop);
/*  263:4644 */       theIndex = this.itsTopIndex++;
/*  264:4645 */       this.itsFieldRefHash.put(ref, theIndex);
/*  265:     */     }
/*  266:4647 */     setConstantData(theIndex, ref);
/*  267:4648 */     this.itsPoolTypes.put(theIndex, 9);
/*  268:4649 */     return (short)theIndex;
/*  269:     */   }
/*  270:     */   
/*  271:     */   short addMethodRef(String className, String methodName, String methodType)
/*  272:     */   {
/*  273:4655 */     FieldOrMethodRef ref = new FieldOrMethodRef(className, methodName, methodType);
/*  274:     */     
/*  275:     */ 
/*  276:4658 */     int theIndex = this.itsMethodRefHash.get(ref, -1);
/*  277:4659 */     if (theIndex == -1)
/*  278:     */     {
/*  279:4660 */       short ntIndex = addNameAndType(methodName, methodType);
/*  280:4661 */       short classIndex = addClass(className);
/*  281:4662 */       ensure(5);
/*  282:4663 */       this.itsPool[(this.itsTop++)] = 10;
/*  283:4664 */       this.itsTop = ClassFileWriter.putInt16(classIndex, this.itsPool, this.itsTop);
/*  284:4665 */       this.itsTop = ClassFileWriter.putInt16(ntIndex, this.itsPool, this.itsTop);
/*  285:4666 */       theIndex = this.itsTopIndex++;
/*  286:4667 */       this.itsMethodRefHash.put(ref, theIndex);
/*  287:     */     }
/*  288:4669 */     setConstantData(theIndex, ref);
/*  289:4670 */     this.itsPoolTypes.put(theIndex, 10);
/*  290:4671 */     return (short)theIndex;
/*  291:     */   }
/*  292:     */   
/*  293:     */   short addInterfaceMethodRef(String className, String methodName, String methodType)
/*  294:     */   {
/*  295:4677 */     short ntIndex = addNameAndType(methodName, methodType);
/*  296:4678 */     short classIndex = addClass(className);
/*  297:4679 */     ensure(5);
/*  298:4680 */     this.itsPool[(this.itsTop++)] = 11;
/*  299:4681 */     this.itsTop = ClassFileWriter.putInt16(classIndex, this.itsPool, this.itsTop);
/*  300:4682 */     this.itsTop = ClassFileWriter.putInt16(ntIndex, this.itsPool, this.itsTop);
/*  301:4683 */     FieldOrMethodRef r = new FieldOrMethodRef(className, methodName, methodType);
/*  302:     */     
/*  303:4685 */     setConstantData(this.itsTopIndex, r);
/*  304:4686 */     this.itsPoolTypes.put(this.itsTopIndex, 11);
/*  305:4687 */     return (short)this.itsTopIndex++;
/*  306:     */   }
/*  307:     */   
/*  308:     */   Object getConstantData(int index)
/*  309:     */   {
/*  310:4692 */     return this.itsConstantData.getObject(index);
/*  311:     */   }
/*  312:     */   
/*  313:     */   void setConstantData(int index, Object data)
/*  314:     */   {
/*  315:4697 */     this.itsConstantData.put(index, data);
/*  316:     */   }
/*  317:     */   
/*  318:     */   byte getConstantType(int index)
/*  319:     */   {
/*  320:4702 */     return (byte)this.itsPoolTypes.getInt(index, 0);
/*  321:     */   }
/*  322:     */   
/*  323:     */   void ensure(int howMuch)
/*  324:     */   {
/*  325:4707 */     if (this.itsTop + howMuch > this.itsPool.length)
/*  326:     */     {
/*  327:4708 */       int newCapacity = this.itsPool.length * 2;
/*  328:4709 */       if (this.itsTop + howMuch > newCapacity) {
/*  329:4710 */         newCapacity = this.itsTop + howMuch;
/*  330:     */       }
/*  331:4712 */       byte[] tmp = new byte[newCapacity];
/*  332:4713 */       System.arraycopy(this.itsPool, 0, tmp, 0, this.itsTop);
/*  333:4714 */       this.itsPool = tmp;
/*  334:     */     }
/*  335:     */   }
/*  336:     */   
/*  337:4722 */   private UintMap itsStringConstHash = new UintMap();
/*  338:4723 */   private ObjToIntMap itsUtf8Hash = new ObjToIntMap();
/*  339:4724 */   private ObjToIntMap itsFieldRefHash = new ObjToIntMap();
/*  340:4725 */   private ObjToIntMap itsMethodRefHash = new ObjToIntMap();
/*  341:4726 */   private ObjToIntMap itsClassHash = new ObjToIntMap();
/*  342:     */   private int itsTop;
/*  343:     */   private int itsTopIndex;
/*  344:4730 */   private UintMap itsConstantData = new UintMap();
/*  345:4731 */   private UintMap itsPoolTypes = new UintMap();
/*  346:     */   private byte[] itsPool;
/*  347:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.classfile.ConstantPool
 * JD-Core Version:    0.7.0.1
 */
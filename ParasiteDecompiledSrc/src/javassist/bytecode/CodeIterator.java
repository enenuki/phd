/*    1:     */ package javassist.bytecode;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ 
/*    5:     */ public class CodeIterator
/*    6:     */   implements Opcode
/*    7:     */ {
/*    8:     */   protected CodeAttribute codeAttr;
/*    9:     */   protected byte[] bytecode;
/*   10:     */   protected int endPos;
/*   11:     */   protected int currentPos;
/*   12:     */   protected int mark;
/*   13:     */   
/*   14:     */   protected CodeIterator(CodeAttribute ca)
/*   15:     */   {
/*   16:  42 */     this.codeAttr = ca;
/*   17:  43 */     this.bytecode = ca.getCode();
/*   18:  44 */     begin();
/*   19:     */   }
/*   20:     */   
/*   21:     */   public void begin()
/*   22:     */   {
/*   23:  51 */     this.currentPos = (this.mark = 0);
/*   24:  52 */     this.endPos = getCodeLength();
/*   25:     */   }
/*   26:     */   
/*   27:     */   public void move(int index)
/*   28:     */   {
/*   29:  68 */     this.currentPos = index;
/*   30:     */   }
/*   31:     */   
/*   32:     */   public void setMark(int index)
/*   33:     */   {
/*   34:  82 */     this.mark = index;
/*   35:     */   }
/*   36:     */   
/*   37:     */   public int getMark()
/*   38:     */   {
/*   39:  93 */     return this.mark;
/*   40:     */   }
/*   41:     */   
/*   42:     */   public CodeAttribute get()
/*   43:     */   {
/*   44:  99 */     return this.codeAttr;
/*   45:     */   }
/*   46:     */   
/*   47:     */   public int getCodeLength()
/*   48:     */   {
/*   49: 106 */     return this.bytecode.length;
/*   50:     */   }
/*   51:     */   
/*   52:     */   public int byteAt(int index)
/*   53:     */   {
/*   54: 112 */     return this.bytecode[index] & 0xFF;
/*   55:     */   }
/*   56:     */   
/*   57:     */   public void writeByte(int value, int index)
/*   58:     */   {
/*   59: 118 */     this.bytecode[index] = ((byte)value);
/*   60:     */   }
/*   61:     */   
/*   62:     */   public int u16bitAt(int index)
/*   63:     */   {
/*   64: 125 */     return ByteArray.readU16bit(this.bytecode, index);
/*   65:     */   }
/*   66:     */   
/*   67:     */   public int s16bitAt(int index)
/*   68:     */   {
/*   69: 132 */     return ByteArray.readS16bit(this.bytecode, index);
/*   70:     */   }
/*   71:     */   
/*   72:     */   public void write16bit(int value, int index)
/*   73:     */   {
/*   74: 139 */     ByteArray.write16bit(value, this.bytecode, index);
/*   75:     */   }
/*   76:     */   
/*   77:     */   public int s32bitAt(int index)
/*   78:     */   {
/*   79: 146 */     return ByteArray.read32bit(this.bytecode, index);
/*   80:     */   }
/*   81:     */   
/*   82:     */   public void write32bit(int value, int index)
/*   83:     */   {
/*   84: 153 */     ByteArray.write32bit(value, this.bytecode, index);
/*   85:     */   }
/*   86:     */   
/*   87:     */   public void write(byte[] code, int index)
/*   88:     */   {
/*   89: 162 */     int len = code.length;
/*   90: 163 */     for (int j = 0; j < len; j++) {
/*   91: 164 */       this.bytecode[(index++)] = code[j];
/*   92:     */     }
/*   93:     */   }
/*   94:     */   
/*   95:     */   public boolean hasNext()
/*   96:     */   {
/*   97: 170 */     return this.currentPos < this.endPos;
/*   98:     */   }
/*   99:     */   
/*  100:     */   public int next()
/*  101:     */     throws BadBytecode
/*  102:     */   {
/*  103: 183 */     int pos = this.currentPos;
/*  104: 184 */     this.currentPos = nextOpcode(this.bytecode, pos);
/*  105: 185 */     return pos;
/*  106:     */   }
/*  107:     */   
/*  108:     */   public int lookAhead()
/*  109:     */   {
/*  110: 197 */     return this.currentPos;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public int skipConstructor()
/*  114:     */     throws BadBytecode
/*  115:     */   {
/*  116: 219 */     return skipSuperConstructor0(-1);
/*  117:     */   }
/*  118:     */   
/*  119:     */   public int skipSuperConstructor()
/*  120:     */     throws BadBytecode
/*  121:     */   {
/*  122: 241 */     return skipSuperConstructor0(0);
/*  123:     */   }
/*  124:     */   
/*  125:     */   public int skipThisConstructor()
/*  126:     */     throws BadBytecode
/*  127:     */   {
/*  128: 263 */     return skipSuperConstructor0(1);
/*  129:     */   }
/*  130:     */   
/*  131:     */   private int skipSuperConstructor0(int skipThis)
/*  132:     */     throws BadBytecode
/*  133:     */   {
/*  134: 269 */     begin();
/*  135: 270 */     ConstPool cp = this.codeAttr.getConstPool();
/*  136: 271 */     String thisClassName = this.codeAttr.getDeclaringClass();
/*  137: 272 */     int nested = 0;
/*  138: 273 */     while (hasNext())
/*  139:     */     {
/*  140: 274 */       int index = next();
/*  141: 275 */       int c = byteAt(index);
/*  142: 276 */       if (c == 187)
/*  143:     */       {
/*  144: 277 */         nested++;
/*  145:     */       }
/*  146: 278 */       else if (c == 183)
/*  147:     */       {
/*  148: 279 */         int mref = ByteArray.readU16bit(this.bytecode, index + 1);
/*  149: 280 */         if (cp.getMethodrefName(mref).equals("<init>"))
/*  150:     */         {
/*  151: 281 */           nested--;
/*  152: 281 */           if (nested < 0)
/*  153:     */           {
/*  154: 282 */             if (skipThis < 0) {
/*  155: 283 */               return index;
/*  156:     */             }
/*  157: 285 */             String cname = cp.getMethodrefClassName(mref);
/*  158: 286 */             if (cname.equals(thisClassName) != skipThis > 0) {
/*  159:     */               break;
/*  160:     */             }
/*  161: 287 */             return index;
/*  162:     */           }
/*  163:     */         }
/*  164:     */       }
/*  165:     */     }
/*  166: 294 */     begin();
/*  167: 295 */     return -1;
/*  168:     */   }
/*  169:     */   
/*  170:     */   public int insert(byte[] code)
/*  171:     */     throws BadBytecode
/*  172:     */   {
/*  173: 319 */     return insert0(this.currentPos, code, false);
/*  174:     */   }
/*  175:     */   
/*  176:     */   public void insert(int pos, byte[] code)
/*  177:     */     throws BadBytecode
/*  178:     */   {
/*  179: 344 */     insert0(pos, code, false);
/*  180:     */   }
/*  181:     */   
/*  182:     */   public int insertAt(int pos, byte[] code)
/*  183:     */     throws BadBytecode
/*  184:     */   {
/*  185: 368 */     return insert0(pos, code, false);
/*  186:     */   }
/*  187:     */   
/*  188:     */   public int insertEx(byte[] code)
/*  189:     */     throws BadBytecode
/*  190:     */   {
/*  191: 392 */     return insert0(this.currentPos, code, true);
/*  192:     */   }
/*  193:     */   
/*  194:     */   public void insertEx(int pos, byte[] code)
/*  195:     */     throws BadBytecode
/*  196:     */   {
/*  197: 417 */     insert0(pos, code, true);
/*  198:     */   }
/*  199:     */   
/*  200:     */   public int insertExAt(int pos, byte[] code)
/*  201:     */     throws BadBytecode
/*  202:     */   {
/*  203: 441 */     return insert0(pos, code, true);
/*  204:     */   }
/*  205:     */   
/*  206:     */   private int insert0(int pos, byte[] code, boolean exclusive)
/*  207:     */     throws BadBytecode
/*  208:     */   {
/*  209: 451 */     int len = code.length;
/*  210: 452 */     if (len <= 0) {
/*  211: 453 */       return pos;
/*  212:     */     }
/*  213: 456 */     pos = insertGapAt(pos, len, exclusive).position;
/*  214:     */     
/*  215: 458 */     int p = pos;
/*  216: 459 */     for (int j = 0; j < len; j++) {
/*  217: 460 */       this.bytecode[(p++)] = code[j];
/*  218:     */     }
/*  219: 462 */     return pos;
/*  220:     */   }
/*  221:     */   
/*  222:     */   public int insertGap(int length)
/*  223:     */     throws BadBytecode
/*  224:     */   {
/*  225: 481 */     return insertGapAt(this.currentPos, length, false).position;
/*  226:     */   }
/*  227:     */   
/*  228:     */   public int insertGap(int pos, int length)
/*  229:     */     throws BadBytecode
/*  230:     */   {
/*  231: 501 */     return insertGapAt(pos, length, false).length;
/*  232:     */   }
/*  233:     */   
/*  234:     */   public int insertExGap(int length)
/*  235:     */     throws BadBytecode
/*  236:     */   {
/*  237: 520 */     return insertGapAt(this.currentPos, length, true).position;
/*  238:     */   }
/*  239:     */   
/*  240:     */   public int insertExGap(int pos, int length)
/*  241:     */     throws BadBytecode
/*  242:     */   {
/*  243: 540 */     return insertGapAt(pos, length, true).length;
/*  244:     */   }
/*  245:     */   
/*  246:     */   public Gap insertGapAt(int pos, int length, boolean exclusive)
/*  247:     */     throws BadBytecode
/*  248:     */   {
/*  249: 599 */     Gap gap = new Gap();
/*  250: 600 */     if (length <= 0)
/*  251:     */     {
/*  252: 601 */       gap.position = pos;
/*  253: 602 */       gap.length = 0;
/*  254: 603 */       return gap;
/*  255:     */     }
/*  256:     */     int length2;
/*  257:     */     byte[] c;
/*  258:     */     int length2;
/*  259: 608 */     if (this.bytecode.length + length > 32767)
/*  260:     */     {
/*  261: 610 */       byte[] c = insertGapCore0w(this.bytecode, pos, length, exclusive, get().getExceptionTable(), this.codeAttr, gap);
/*  262:     */       
/*  263: 612 */       pos = gap.position;
/*  264: 613 */       length2 = length;
/*  265:     */     }
/*  266:     */     else
/*  267:     */     {
/*  268: 616 */       int cur = this.currentPos;
/*  269: 617 */       c = insertGapCore0(this.bytecode, pos, length, exclusive, get().getExceptionTable(), this.codeAttr);
/*  270:     */       
/*  271:     */ 
/*  272: 620 */       length2 = c.length - this.bytecode.length;
/*  273: 621 */       gap.position = pos;
/*  274: 622 */       gap.length = length2;
/*  275: 623 */       if (cur >= pos) {
/*  276: 624 */         this.currentPos = (cur + length2);
/*  277:     */       }
/*  278: 626 */       if ((this.mark > pos) || ((this.mark == pos) && (exclusive))) {
/*  279: 627 */         this.mark += length2;
/*  280:     */       }
/*  281:     */     }
/*  282: 630 */     this.codeAttr.setCode(c);
/*  283: 631 */     this.bytecode = c;
/*  284: 632 */     this.endPos = getCodeLength();
/*  285: 633 */     updateCursors(pos, length2);
/*  286: 634 */     return gap;
/*  287:     */   }
/*  288:     */   
/*  289:     */   protected void updateCursors(int pos, int length) {}
/*  290:     */   
/*  291:     */   public void insert(ExceptionTable et, int offset)
/*  292:     */   {
/*  293: 657 */     this.codeAttr.getExceptionTable().add(0, et, offset);
/*  294:     */   }
/*  295:     */   
/*  296:     */   public int append(byte[] code)
/*  297:     */   {
/*  298: 667 */     int size = getCodeLength();
/*  299: 668 */     int len = code.length;
/*  300: 669 */     if (len <= 0) {
/*  301: 670 */       return size;
/*  302:     */     }
/*  303: 672 */     appendGap(len);
/*  304: 673 */     byte[] dest = this.bytecode;
/*  305: 674 */     for (int i = 0; i < len; i++) {
/*  306: 675 */       dest[(i + size)] = code[i];
/*  307:     */     }
/*  308: 677 */     return size;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public void appendGap(int gapLength)
/*  312:     */   {
/*  313: 686 */     byte[] code = this.bytecode;
/*  314: 687 */     int codeLength = code.length;
/*  315: 688 */     byte[] newcode = new byte[codeLength + gapLength];
/*  316: 691 */     for (int i = 0; i < codeLength; i++) {
/*  317: 692 */       newcode[i] = code[i];
/*  318:     */     }
/*  319: 694 */     for (i = codeLength; i < codeLength + gapLength; i++) {
/*  320: 695 */       newcode[i] = 0;
/*  321:     */     }
/*  322: 697 */     this.codeAttr.setCode(newcode);
/*  323: 698 */     this.bytecode = newcode;
/*  324: 699 */     this.endPos = getCodeLength();
/*  325:     */   }
/*  326:     */   
/*  327:     */   public void append(ExceptionTable et, int offset)
/*  328:     */   {
/*  329: 711 */     ExceptionTable table = this.codeAttr.getExceptionTable();
/*  330: 712 */     table.add(table.size(), et, offset);
/*  331:     */   }
/*  332:     */   
/*  333: 717 */   private static final int[] opcodeLength = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 2, 3, 3, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 0, 0, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 5, 0, 3, 2, 3, 1, 1, 3, 3, 1, 1, 0, 4, 3, 3, 5, 5 };
/*  334:     */   
/*  335:     */   static int nextOpcode(byte[] code, int index)
/*  336:     */     throws BadBytecode
/*  337:     */   {
/*  338:     */     int opcode;
/*  339:     */     try
/*  340:     */     {
/*  341: 740 */       opcode = code[index] & 0xFF;
/*  342:     */     }
/*  343:     */     catch (IndexOutOfBoundsException e)
/*  344:     */     {
/*  345: 743 */       throw new BadBytecode("invalid opcode address");
/*  346:     */     }
/*  347:     */     try
/*  348:     */     {
/*  349: 747 */       int len = opcodeLength[opcode];
/*  350: 748 */       if (len > 0) {
/*  351: 749 */         return index + len;
/*  352:     */       }
/*  353: 750 */       if (opcode == 196)
/*  354:     */       {
/*  355: 751 */         if (code[(index + 1)] == -124) {
/*  356: 752 */           return index + 6;
/*  357:     */         }
/*  358: 754 */         return index + 4;
/*  359:     */       }
/*  360: 756 */       int index2 = (index & 0xFFFFFFFC) + 8;
/*  361: 757 */       if (opcode == 171)
/*  362:     */       {
/*  363: 758 */         int npairs = ByteArray.read32bit(code, index2);
/*  364: 759 */         return index2 + npairs * 8 + 4;
/*  365:     */       }
/*  366: 761 */       if (opcode == 170)
/*  367:     */       {
/*  368: 762 */         int low = ByteArray.read32bit(code, index2);
/*  369: 763 */         int high = ByteArray.read32bit(code, index2 + 4);
/*  370: 764 */         return index2 + (high - low + 1) * 4 + 8;
/*  371:     */       }
/*  372:     */     }
/*  373:     */     catch (IndexOutOfBoundsException e) {}
/*  374: 774 */     throw new BadBytecode(opcode);
/*  375:     */   }
/*  376:     */   
/*  377:     */   static byte[] insertGapCore0(byte[] code, int where, int gapLength, boolean exclusive, ExceptionTable etable, CodeAttribute ca)
/*  378:     */     throws BadBytecode
/*  379:     */   {
/*  380: 800 */     if (gapLength <= 0) {
/*  381: 801 */       return code;
/*  382:     */     }
/*  383:     */     try
/*  384:     */     {
/*  385: 804 */       return insertGapCore1(code, where, gapLength, exclusive, etable, ca);
/*  386:     */     }
/*  387:     */     catch (AlignmentException e)
/*  388:     */     {
/*  389:     */       try
/*  390:     */       {
/*  391: 808 */         return insertGapCore1(code, where, gapLength + 3 & 0xFFFFFFFC, exclusive, etable, ca);
/*  392:     */       }
/*  393:     */       catch (AlignmentException e2)
/*  394:     */       {
/*  395: 812 */         throw new RuntimeException("fatal error?");
/*  396:     */       }
/*  397:     */     }
/*  398:     */   }
/*  399:     */   
/*  400:     */   private static byte[] insertGapCore1(byte[] code, int where, int gapLength, boolean exclusive, ExceptionTable etable, CodeAttribute ca)
/*  401:     */     throws BadBytecode, CodeIterator.AlignmentException
/*  402:     */   {
/*  403: 822 */     int codeLength = code.length;
/*  404: 823 */     byte[] newcode = new byte[codeLength + gapLength];
/*  405: 824 */     insertGap2(code, where, gapLength, codeLength, newcode, exclusive);
/*  406: 825 */     etable.shiftPc(where, gapLength, exclusive);
/*  407: 826 */     LineNumberAttribute na = (LineNumberAttribute)ca.getAttribute("LineNumberTable");
/*  408: 828 */     if (na != null) {
/*  409: 829 */       na.shiftPc(where, gapLength, exclusive);
/*  410:     */     }
/*  411: 831 */     LocalVariableAttribute va = (LocalVariableAttribute)ca.getAttribute("LocalVariableTable");
/*  412: 833 */     if (va != null) {
/*  413: 834 */       va.shiftPc(where, gapLength, exclusive);
/*  414:     */     }
/*  415: 836 */     LocalVariableAttribute vta = (LocalVariableAttribute)ca.getAttribute("LocalVariableTypeTable");
/*  416: 839 */     if (vta != null) {
/*  417: 840 */       vta.shiftPc(where, gapLength, exclusive);
/*  418:     */     }
/*  419: 842 */     StackMapTable smt = (StackMapTable)ca.getAttribute("StackMapTable");
/*  420: 843 */     if (smt != null) {
/*  421: 844 */       smt.shiftPc(where, gapLength, exclusive);
/*  422:     */     }
/*  423: 846 */     StackMap sm = (StackMap)ca.getAttribute("StackMap");
/*  424: 847 */     if (sm != null) {
/*  425: 848 */       sm.shiftPc(where, gapLength, exclusive);
/*  426:     */     }
/*  427: 850 */     return newcode;
/*  428:     */   }
/*  429:     */   
/*  430:     */   private static void insertGap2(byte[] code, int where, int gapLength, int endPos, byte[] newcode, boolean exclusive)
/*  431:     */     throws BadBytecode, CodeIterator.AlignmentException
/*  432:     */   {
/*  433: 858 */     int i = 0;
/*  434: 859 */     int j = 0;
/*  435:     */     int nextPos;
/*  436: 860 */     for (; i < endPos; i = nextPos)
/*  437:     */     {
/*  438: 861 */       if (i == where)
/*  439:     */       {
/*  440: 862 */         int j2 = j + gapLength;
/*  441: 863 */         while (j < j2) {
/*  442: 864 */           newcode[(j++)] = 0;
/*  443:     */         }
/*  444:     */       }
/*  445: 867 */       nextPos = nextOpcode(code, i);
/*  446: 868 */       int inst = code[i] & 0xFF;
/*  447: 870 */       if (((153 <= inst) && (inst <= 168)) || (inst == 198) || (inst == 199))
/*  448:     */       {
/*  449: 873 */         int offset = code[(i + 1)] << 8 | code[(i + 2)] & 0xFF;
/*  450: 874 */         offset = newOffset(i, offset, where, gapLength, exclusive);
/*  451: 875 */         newcode[j] = code[i];
/*  452: 876 */         ByteArray.write16bit(offset, newcode, j + 1);
/*  453: 877 */         j += 3;
/*  454:     */       }
/*  455: 879 */       else if ((inst == 200) || (inst == 201))
/*  456:     */       {
/*  457: 881 */         int offset = ByteArray.read32bit(code, i + 1);
/*  458: 882 */         offset = newOffset(i, offset, where, gapLength, exclusive);
/*  459: 883 */         newcode[(j++)] = code[i];
/*  460: 884 */         ByteArray.write32bit(offset, newcode, j);
/*  461: 885 */         j += 4;
/*  462:     */       }
/*  463: 887 */       else if (inst == 170)
/*  464:     */       {
/*  465: 888 */         if ((i != j) && ((gapLength & 0x3) != 0)) {
/*  466: 889 */           throw new AlignmentException();
/*  467:     */         }
/*  468: 891 */         int i2 = (i & 0xFFFFFFFC) + 4;
/*  469:     */         
/*  470:     */ 
/*  471:     */ 
/*  472:     */ 
/*  473:     */ 
/*  474:     */ 
/*  475: 898 */         j = copyGapBytes(newcode, j, code, i, i2);
/*  476:     */         
/*  477: 900 */         int defaultbyte = newOffset(i, ByteArray.read32bit(code, i2), where, gapLength, exclusive);
/*  478:     */         
/*  479: 902 */         ByteArray.write32bit(defaultbyte, newcode, j);
/*  480: 903 */         int lowbyte = ByteArray.read32bit(code, i2 + 4);
/*  481: 904 */         ByteArray.write32bit(lowbyte, newcode, j + 4);
/*  482: 905 */         int highbyte = ByteArray.read32bit(code, i2 + 8);
/*  483: 906 */         ByteArray.write32bit(highbyte, newcode, j + 8);
/*  484: 907 */         j += 12;
/*  485: 908 */         int i0 = i2 + 12;
/*  486: 909 */         i2 = i0 + (highbyte - lowbyte + 1) * 4;
/*  487: 910 */         while (i0 < i2)
/*  488:     */         {
/*  489: 911 */           int offset = newOffset(i, ByteArray.read32bit(code, i0), where, gapLength, exclusive);
/*  490:     */           
/*  491: 913 */           ByteArray.write32bit(offset, newcode, j);
/*  492: 914 */           j += 4;
/*  493: 915 */           i0 += 4;
/*  494:     */         }
/*  495:     */       }
/*  496: 918 */       else if (inst == 171)
/*  497:     */       {
/*  498: 919 */         if ((i != j) && ((gapLength & 0x3) != 0)) {
/*  499: 920 */           throw new AlignmentException();
/*  500:     */         }
/*  501: 922 */         int i2 = (i & 0xFFFFFFFC) + 4;
/*  502:     */         
/*  503:     */ 
/*  504:     */ 
/*  505:     */ 
/*  506:     */ 
/*  507:     */ 
/*  508:     */ 
/*  509: 930 */         j = copyGapBytes(newcode, j, code, i, i2);
/*  510:     */         
/*  511: 932 */         int defaultbyte = newOffset(i, ByteArray.read32bit(code, i2), where, gapLength, exclusive);
/*  512:     */         
/*  513: 934 */         ByteArray.write32bit(defaultbyte, newcode, j);
/*  514: 935 */         int npairs = ByteArray.read32bit(code, i2 + 4);
/*  515: 936 */         ByteArray.write32bit(npairs, newcode, j + 4);
/*  516: 937 */         j += 8;
/*  517: 938 */         int i0 = i2 + 8;
/*  518: 939 */         i2 = i0 + npairs * 8;
/*  519: 940 */         while (i0 < i2)
/*  520:     */         {
/*  521: 941 */           ByteArray.copy32bit(code, i0, newcode, j);
/*  522: 942 */           int offset = newOffset(i, ByteArray.read32bit(code, i0 + 4), where, gapLength, exclusive);
/*  523:     */           
/*  524:     */ 
/*  525: 945 */           ByteArray.write32bit(offset, newcode, j + 4);
/*  526: 946 */           j += 8;
/*  527: 947 */           i0 += 8;
/*  528:     */         }
/*  529:     */       }
/*  530:     */       else
/*  531:     */       {
/*  532: 951 */         while (i < nextPos) {
/*  533: 952 */           newcode[(j++)] = code[(i++)];
/*  534:     */         }
/*  535:     */       }
/*  536:     */     }
/*  537:     */   }
/*  538:     */   
/*  539:     */   private static int copyGapBytes(byte[] newcode, int j, byte[] code, int i, int iEnd)
/*  540:     */   {
/*  541: 958 */     switch (iEnd - i)
/*  542:     */     {
/*  543:     */     case 4: 
/*  544: 960 */       newcode[(j++)] = code[(i++)];
/*  545:     */     case 3: 
/*  546: 962 */       newcode[(j++)] = code[(i++)];
/*  547:     */     case 2: 
/*  548: 964 */       newcode[(j++)] = code[(i++)];
/*  549:     */     case 1: 
/*  550: 966 */       newcode[(j++)] = code[(i++)];
/*  551:     */     }
/*  552: 970 */     return j;
/*  553:     */   }
/*  554:     */   
/*  555:     */   private static int newOffset(int i, int offset, int where, int gapLength, boolean exclusive)
/*  556:     */   {
/*  557: 975 */     int target = i + offset;
/*  558: 976 */     if (i < where)
/*  559:     */     {
/*  560: 977 */       if ((where < target) || ((exclusive) && (where == target))) {
/*  561: 978 */         offset += gapLength;
/*  562:     */       }
/*  563:     */     }
/*  564: 980 */     else if (i == where)
/*  565:     */     {
/*  566: 981 */       if ((target < where) && (exclusive)) {
/*  567: 982 */         offset -= gapLength;
/*  568: 983 */       } else if ((where < target) && (!exclusive)) {
/*  569: 984 */         offset += gapLength;
/*  570:     */       }
/*  571:     */     }
/*  572: 987 */     else if ((target < where) || ((!exclusive) && (where == target))) {
/*  573: 988 */       offset -= gapLength;
/*  574:     */     }
/*  575: 990 */     return offset;
/*  576:     */   }
/*  577:     */   
/*  578:     */   public static class Gap
/*  579:     */   {
/*  580:     */     public int position;
/*  581:     */     public int length;
/*  582:     */   }
/*  583:     */   
/*  584:     */   static class AlignmentException
/*  585:     */     extends Exception
/*  586:     */   {}
/*  587:     */   
/*  588:     */   static class Pointers
/*  589:     */   {
/*  590:     */     int cursor;
/*  591:     */     int mark0;
/*  592:     */     int mark;
/*  593:     */     ExceptionTable etable;
/*  594:     */     LineNumberAttribute line;
/*  595:     */     LocalVariableAttribute vars;
/*  596:     */     LocalVariableAttribute types;
/*  597:     */     StackMapTable stack;
/*  598:     */     StackMap stack2;
/*  599:     */     
/*  600:     */     Pointers(int cur, int m, int m0, ExceptionTable et, CodeAttribute ca)
/*  601:     */     {
/*  602:1003 */       this.cursor = cur;
/*  603:1004 */       this.mark = m;
/*  604:1005 */       this.mark0 = m0;
/*  605:1006 */       this.etable = et;
/*  606:1007 */       this.line = ((LineNumberAttribute)ca.getAttribute("LineNumberTable"));
/*  607:1008 */       this.vars = ((LocalVariableAttribute)ca.getAttribute("LocalVariableTable"));
/*  608:1009 */       this.types = ((LocalVariableAttribute)ca.getAttribute("LocalVariableTypeTable"));
/*  609:1010 */       this.stack = ((StackMapTable)ca.getAttribute("StackMapTable"));
/*  610:1011 */       this.stack2 = ((StackMap)ca.getAttribute("StackMap"));
/*  611:     */     }
/*  612:     */     
/*  613:     */     void shiftPc(int where, int gapLength, boolean exclusive)
/*  614:     */       throws BadBytecode
/*  615:     */     {
/*  616:1015 */       if ((where < this.cursor) || ((where == this.cursor) && (exclusive))) {
/*  617:1016 */         this.cursor += gapLength;
/*  618:     */       }
/*  619:1018 */       if ((where < this.mark) || ((where == this.mark) && (exclusive))) {
/*  620:1019 */         this.mark += gapLength;
/*  621:     */       }
/*  622:1021 */       if ((where < this.mark0) || ((where == this.mark0) && (exclusive))) {
/*  623:1022 */         this.mark0 += gapLength;
/*  624:     */       }
/*  625:1024 */       this.etable.shiftPc(where, gapLength, exclusive);
/*  626:1025 */       if (this.line != null) {
/*  627:1026 */         this.line.shiftPc(where, gapLength, exclusive);
/*  628:     */       }
/*  629:1028 */       if (this.vars != null) {
/*  630:1029 */         this.vars.shiftPc(where, gapLength, exclusive);
/*  631:     */       }
/*  632:1031 */       if (this.types != null) {
/*  633:1032 */         this.types.shiftPc(where, gapLength, exclusive);
/*  634:     */       }
/*  635:1034 */       if (this.stack != null) {
/*  636:1035 */         this.stack.shiftPc(where, gapLength, exclusive);
/*  637:     */       }
/*  638:1037 */       if (this.stack2 != null) {
/*  639:1038 */         this.stack2.shiftPc(where, gapLength, exclusive);
/*  640:     */       }
/*  641:     */     }
/*  642:     */   }
/*  643:     */   
/*  644:     */   static byte[] changeLdcToLdcW(byte[] code, ExceptionTable etable, CodeAttribute ca, CodeAttribute.LdcEntry ldcs)
/*  645:     */     throws BadBytecode
/*  646:     */   {
/*  647:1049 */     ArrayList jumps = makeJumpList(code, code.length);
/*  648:1050 */     while (ldcs != null)
/*  649:     */     {
/*  650:1051 */       addLdcW(ldcs, jumps);
/*  651:1052 */       ldcs = ldcs.next;
/*  652:     */     }
/*  653:1055 */     Pointers pointers = new Pointers(0, 0, 0, etable, ca);
/*  654:1056 */     byte[] r = insertGap2w(code, 0, 0, false, jumps, pointers);
/*  655:1057 */     return r;
/*  656:     */   }
/*  657:     */   
/*  658:     */   private static void addLdcW(CodeAttribute.LdcEntry ldcs, ArrayList jumps)
/*  659:     */   {
/*  660:1061 */     int where = ldcs.where;
/*  661:1062 */     LdcW ldcw = new LdcW(where, ldcs.index);
/*  662:1063 */     int s = jumps.size();
/*  663:1064 */     for (int i = 0; i < s; i++) {
/*  664:1065 */       if (where < ((Branch)jumps.get(i)).orgPos)
/*  665:     */       {
/*  666:1066 */         jumps.add(i, ldcw);
/*  667:1067 */         return;
/*  668:     */       }
/*  669:     */     }
/*  670:1070 */     jumps.add(ldcw);
/*  671:     */   }
/*  672:     */   
/*  673:     */   private byte[] insertGapCore0w(byte[] code, int where, int gapLength, boolean exclusive, ExceptionTable etable, CodeAttribute ca, Gap newWhere)
/*  674:     */     throws BadBytecode
/*  675:     */   {
/*  676:1090 */     if (gapLength <= 0) {
/*  677:1091 */       return code;
/*  678:     */     }
/*  679:1093 */     ArrayList jumps = makeJumpList(code, code.length);
/*  680:1094 */     Pointers pointers = new Pointers(this.currentPos, this.mark, where, etable, ca);
/*  681:1095 */     byte[] r = insertGap2w(code, where, gapLength, exclusive, jumps, pointers);
/*  682:1096 */     this.currentPos = pointers.cursor;
/*  683:1097 */     this.mark = pointers.mark;
/*  684:1098 */     int where2 = pointers.mark0;
/*  685:1099 */     if ((where2 == this.currentPos) && (!exclusive)) {
/*  686:1100 */       this.currentPos += gapLength;
/*  687:     */     }
/*  688:1102 */     if (exclusive) {
/*  689:1103 */       where2 -= gapLength;
/*  690:     */     }
/*  691:1105 */     newWhere.position = where2;
/*  692:1106 */     newWhere.length = gapLength;
/*  693:1107 */     return r;
/*  694:     */   }
/*  695:     */   
/*  696:     */   private static byte[] insertGap2w(byte[] code, int where, int gapLength, boolean exclusive, ArrayList jumps, Pointers ptrs)
/*  697:     */     throws BadBytecode
/*  698:     */   {
/*  699:1114 */     int n = jumps.size();
/*  700:1115 */     if (gapLength > 0)
/*  701:     */     {
/*  702:1116 */       ptrs.shiftPc(where, gapLength, exclusive);
/*  703:1117 */       for (int i = 0; i < n; i++) {
/*  704:1118 */         ((Branch)jumps.get(i)).shift(where, gapLength, exclusive);
/*  705:     */       }
/*  706:     */     }
/*  707:1121 */     boolean unstable = true;
/*  708:     */     do
/*  709:     */     {
/*  710:1123 */       while (unstable)
/*  711:     */       {
/*  712:1124 */         unstable = false;
/*  713:1125 */         for (int i = 0; i < n; i++)
/*  714:     */         {
/*  715:1126 */           Branch b = (Branch)jumps.get(i);
/*  716:1127 */           if (b.expanded())
/*  717:     */           {
/*  718:1128 */             unstable = true;
/*  719:1129 */             int p = b.pos;
/*  720:1130 */             int delta = b.deltaSize();
/*  721:1131 */             ptrs.shiftPc(p, delta, false);
/*  722:1132 */             for (int j = 0; j < n; j++) {
/*  723:1133 */               ((Branch)jumps.get(j)).shift(p, delta, false);
/*  724:     */             }
/*  725:     */           }
/*  726:     */         }
/*  727:     */       }
/*  728:1138 */       for (int i = 0; i < n; i++)
/*  729:     */       {
/*  730:1139 */         Branch b = (Branch)jumps.get(i);
/*  731:1140 */         int diff = b.gapChanged();
/*  732:1141 */         if (diff > 0)
/*  733:     */         {
/*  734:1142 */           unstable = true;
/*  735:1143 */           int p = b.pos;
/*  736:1144 */           ptrs.shiftPc(p, diff, false);
/*  737:1145 */           for (int j = 0; j < n; j++) {
/*  738:1146 */             ((Branch)jumps.get(j)).shift(p, diff, false);
/*  739:     */           }
/*  740:     */         }
/*  741:     */       }
/*  742:1149 */     } while (unstable);
/*  743:1151 */     return makeExapndedCode(code, jumps, where, gapLength);
/*  744:     */   }
/*  745:     */   
/*  746:     */   private static ArrayList makeJumpList(byte[] code, int endPos)
/*  747:     */     throws BadBytecode
/*  748:     */   {
/*  749:1157 */     ArrayList jumps = new ArrayList();
/*  750:     */     int nextPos;
/*  751:1159 */     for (int i = 0; i < endPos; i = nextPos)
/*  752:     */     {
/*  753:1160 */       nextPos = nextOpcode(code, i);
/*  754:1161 */       int inst = code[i] & 0xFF;
/*  755:1163 */       if (((153 <= inst) && (inst <= 168)) || (inst == 198) || (inst == 199))
/*  756:     */       {
/*  757:1166 */         int offset = code[(i + 1)] << 8 | code[(i + 2)] & 0xFF;
/*  758:     */         Branch b;
/*  759:     */         Branch b;
/*  760:1168 */         if ((inst == 167) || (inst == 168)) {
/*  761:1169 */           b = new Jump16(i, offset);
/*  762:     */         } else {
/*  763:1171 */           b = new If16(i, offset);
/*  764:     */         }
/*  765:1173 */         jumps.add(b);
/*  766:     */       }
/*  767:1175 */       else if ((inst == 200) || (inst == 201))
/*  768:     */       {
/*  769:1177 */         int offset = ByteArray.read32bit(code, i + 1);
/*  770:1178 */         jumps.add(new Jump32(i, offset));
/*  771:     */       }
/*  772:1180 */       else if (inst == 170)
/*  773:     */       {
/*  774:1181 */         int i2 = (i & 0xFFFFFFFC) + 4;
/*  775:1182 */         int defaultbyte = ByteArray.read32bit(code, i2);
/*  776:1183 */         int lowbyte = ByteArray.read32bit(code, i2 + 4);
/*  777:1184 */         int highbyte = ByteArray.read32bit(code, i2 + 8);
/*  778:1185 */         int i0 = i2 + 12;
/*  779:1186 */         int size = highbyte - lowbyte + 1;
/*  780:1187 */         int[] offsets = new int[size];
/*  781:1188 */         for (int j = 0; j < size; j++)
/*  782:     */         {
/*  783:1189 */           offsets[j] = ByteArray.read32bit(code, i0);
/*  784:1190 */           i0 += 4;
/*  785:     */         }
/*  786:1193 */         jumps.add(new Table(i, defaultbyte, lowbyte, highbyte, offsets));
/*  787:     */       }
/*  788:1195 */       else if (inst == 171)
/*  789:     */       {
/*  790:1196 */         int i2 = (i & 0xFFFFFFFC) + 4;
/*  791:1197 */         int defaultbyte = ByteArray.read32bit(code, i2);
/*  792:1198 */         int npairs = ByteArray.read32bit(code, i2 + 4);
/*  793:1199 */         int i0 = i2 + 8;
/*  794:1200 */         int[] matches = new int[npairs];
/*  795:1201 */         int[] offsets = new int[npairs];
/*  796:1202 */         for (int j = 0; j < npairs; j++)
/*  797:     */         {
/*  798:1203 */           matches[j] = ByteArray.read32bit(code, i0);
/*  799:1204 */           offsets[j] = ByteArray.read32bit(code, i0 + 4);
/*  800:1205 */           i0 += 8;
/*  801:     */         }
/*  802:1208 */         jumps.add(new Lookup(i, defaultbyte, matches, offsets));
/*  803:     */       }
/*  804:     */     }
/*  805:1212 */     return jumps;
/*  806:     */   }
/*  807:     */   
/*  808:     */   private static byte[] makeExapndedCode(byte[] code, ArrayList jumps, int where, int gapLength)
/*  809:     */     throws BadBytecode
/*  810:     */   {
/*  811:1219 */     int n = jumps.size();
/*  812:1220 */     int size = code.length + gapLength;
/*  813:1221 */     for (int i = 0; i < n; i++)
/*  814:     */     {
/*  815:1222 */       Branch b = (Branch)jumps.get(i);
/*  816:1223 */       size += b.deltaSize();
/*  817:     */     }
/*  818:1226 */     byte[] newcode = new byte[size];
/*  819:1227 */     int src = 0;int dest = 0;int bindex = 0;
/*  820:1228 */     int len = code.length;
/*  821:     */     int bpos;
/*  822:     */     Branch b;
/*  823:     */     int bpos;
/*  824:1231 */     if (0 < n)
/*  825:     */     {
/*  826:1232 */       Branch b = (Branch)jumps.get(0);
/*  827:1233 */       bpos = b.orgPos;
/*  828:     */     }
/*  829:     */     else
/*  830:     */     {
/*  831:1236 */       b = null;
/*  832:1237 */       bpos = len;
/*  833:     */     }
/*  834:1240 */     while (src < len)
/*  835:     */     {
/*  836:1241 */       if (src == where)
/*  837:     */       {
/*  838:1242 */         int pos2 = dest + gapLength;
/*  839:1243 */         while (dest < pos2) {
/*  840:1244 */           newcode[(dest++)] = 0;
/*  841:     */         }
/*  842:     */       }
/*  843:1247 */       if (src != bpos)
/*  844:     */       {
/*  845:1248 */         newcode[(dest++)] = code[(src++)];
/*  846:     */       }
/*  847:     */       else
/*  848:     */       {
/*  849:1250 */         int s = b.write(src, code, dest, newcode);
/*  850:1251 */         src += s;
/*  851:1252 */         dest += s + b.deltaSize();
/*  852:1253 */         bindex++;
/*  853:1253 */         if (bindex < n)
/*  854:     */         {
/*  855:1254 */           b = (Branch)jumps.get(bindex);
/*  856:1255 */           bpos = b.orgPos;
/*  857:     */         }
/*  858:     */         else
/*  859:     */         {
/*  860:1258 */           b = null;
/*  861:1259 */           bpos = len;
/*  862:     */         }
/*  863:     */       }
/*  864:     */     }
/*  865:1264 */     return newcode;
/*  866:     */   }
/*  867:     */   
/*  868:     */   static abstract class Branch
/*  869:     */   {
/*  870:     */     int pos;
/*  871:     */     int orgPos;
/*  872:     */     
/*  873:     */     Branch(int p)
/*  874:     */     {
/*  875:1269 */       this.pos = (this.orgPos = p);
/*  876:     */     }
/*  877:     */     
/*  878:     */     void shift(int where, int gapLength, boolean exclusive)
/*  879:     */     {
/*  880:1271 */       if ((where < this.pos) || ((where == this.pos) && (exclusive))) {
/*  881:1272 */         this.pos += gapLength;
/*  882:     */       }
/*  883:     */     }
/*  884:     */     
/*  885:     */     boolean expanded()
/*  886:     */     {
/*  887:1275 */       return false;
/*  888:     */     }
/*  889:     */     
/*  890:     */     int gapChanged()
/*  891:     */     {
/*  892:1276 */       return 0;
/*  893:     */     }
/*  894:     */     
/*  895:     */     int deltaSize()
/*  896:     */     {
/*  897:1277 */       return 0;
/*  898:     */     }
/*  899:     */     
/*  900:     */     abstract int write(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2);
/*  901:     */   }
/*  902:     */   
/*  903:     */   static class LdcW
/*  904:     */     extends CodeIterator.Branch
/*  905:     */   {
/*  906:     */     int index;
/*  907:     */     boolean state;
/*  908:     */     
/*  909:     */     LdcW(int p, int i)
/*  910:     */     {
/*  911:1289 */       super();
/*  912:1290 */       this.index = i;
/*  913:1291 */       this.state = true;
/*  914:     */     }
/*  915:     */     
/*  916:     */     boolean expanded()
/*  917:     */     {
/*  918:1295 */       if (this.state)
/*  919:     */       {
/*  920:1296 */         this.state = false;
/*  921:1297 */         return true;
/*  922:     */       }
/*  923:1300 */       return false;
/*  924:     */     }
/*  925:     */     
/*  926:     */     int deltaSize()
/*  927:     */     {
/*  928:1303 */       return 1;
/*  929:     */     }
/*  930:     */     
/*  931:     */     int write(int srcPos, byte[] code, int destPos, byte[] newcode)
/*  932:     */     {
/*  933:1306 */       newcode[destPos] = 19;
/*  934:1307 */       ByteArray.write16bit(this.index, newcode, destPos + 1);
/*  935:1308 */       return 2;
/*  936:     */     }
/*  937:     */   }
/*  938:     */   
/*  939:     */   static abstract class Branch16
/*  940:     */     extends CodeIterator.Branch
/*  941:     */   {
/*  942:     */     int offset;
/*  943:     */     int state;
/*  944:     */     static final int BIT16 = 0;
/*  945:     */     static final int EXPAND = 1;
/*  946:     */     static final int BIT32 = 2;
/*  947:     */     
/*  948:     */     Branch16(int p, int off)
/*  949:     */     {
/*  950:1320 */       super();
/*  951:1321 */       this.offset = off;
/*  952:1322 */       this.state = 0;
/*  953:     */     }
/*  954:     */     
/*  955:     */     void shift(int where, int gapLength, boolean exclusive)
/*  956:     */     {
/*  957:1326 */       this.offset = CodeIterator.newOffset(this.pos, this.offset, where, gapLength, exclusive);
/*  958:1327 */       super.shift(where, gapLength, exclusive);
/*  959:1328 */       if ((this.state == 0) && (
/*  960:1329 */         (this.offset < -32768) || (32767 < this.offset))) {
/*  961:1330 */         this.state = 1;
/*  962:     */       }
/*  963:     */     }
/*  964:     */     
/*  965:     */     boolean expanded()
/*  966:     */     {
/*  967:1334 */       if (this.state == 1)
/*  968:     */       {
/*  969:1335 */         this.state = 2;
/*  970:1336 */         return true;
/*  971:     */       }
/*  972:1339 */       return false;
/*  973:     */     }
/*  974:     */     
/*  975:     */     abstract int deltaSize();
/*  976:     */     
/*  977:     */     abstract void write32(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2);
/*  978:     */     
/*  979:     */     int write(int src, byte[] code, int dest, byte[] newcode)
/*  980:     */     {
/*  981:1346 */       if (this.state == 2)
/*  982:     */       {
/*  983:1347 */         write32(src, code, dest, newcode);
/*  984:     */       }
/*  985:     */       else
/*  986:     */       {
/*  987:1349 */         newcode[dest] = code[src];
/*  988:1350 */         ByteArray.write16bit(this.offset, newcode, dest + 1);
/*  989:     */       }
/*  990:1353 */       return 3;
/*  991:     */     }
/*  992:     */   }
/*  993:     */   
/*  994:     */   static class Jump16
/*  995:     */     extends CodeIterator.Branch16
/*  996:     */   {
/*  997:     */     Jump16(int p, int off)
/*  998:     */     {
/*  999:1360 */       super(off);
/* 1000:     */     }
/* 1001:     */     
/* 1002:     */     int deltaSize()
/* 1003:     */     {
/* 1004:1364 */       return this.state == 2 ? 2 : 0;
/* 1005:     */     }
/* 1006:     */     
/* 1007:     */     void write32(int src, byte[] code, int dest, byte[] newcode)
/* 1008:     */     {
/* 1009:1368 */       newcode[dest] = ((byte)((code[src] & 0xFF) == 167 ? 'È' : 'É'));
/* 1010:1369 */       ByteArray.write32bit(this.offset, newcode, dest + 1);
/* 1011:     */     }
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   static class If16
/* 1015:     */     extends CodeIterator.Branch16
/* 1016:     */   {
/* 1017:     */     If16(int p, int off)
/* 1018:     */     {
/* 1019:1376 */       super(off);
/* 1020:     */     }
/* 1021:     */     
/* 1022:     */     int deltaSize()
/* 1023:     */     {
/* 1024:1380 */       return this.state == 2 ? 5 : 0;
/* 1025:     */     }
/* 1026:     */     
/* 1027:     */     void write32(int src, byte[] code, int dest, byte[] newcode)
/* 1028:     */     {
/* 1029:1384 */       newcode[dest] = ((byte)opcode(code[src] & 0xFF));
/* 1030:1385 */       newcode[(dest + 1)] = 0;
/* 1031:1386 */       newcode[(dest + 2)] = 8;
/* 1032:1387 */       newcode[(dest + 3)] = -56;
/* 1033:1388 */       ByteArray.write32bit(this.offset - 3, newcode, dest + 4);
/* 1034:     */     }
/* 1035:     */     
/* 1036:     */     int opcode(int op)
/* 1037:     */     {
/* 1038:1392 */       if (op == 198) {
/* 1039:1393 */         return 199;
/* 1040:     */       }
/* 1041:1394 */       if (op == 199) {
/* 1042:1395 */         return 198;
/* 1043:     */       }
/* 1044:1397 */       if ((op - 153 & 0x1) == 0) {
/* 1045:1398 */         return op + 1;
/* 1046:     */       }
/* 1047:1400 */       return op - 1;
/* 1048:     */     }
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   static class Jump32
/* 1052:     */     extends CodeIterator.Branch
/* 1053:     */   {
/* 1054:     */     int offset;
/* 1055:     */     
/* 1056:     */     Jump32(int p, int off)
/* 1057:     */     {
/* 1058:1409 */       super();
/* 1059:1410 */       this.offset = off;
/* 1060:     */     }
/* 1061:     */     
/* 1062:     */     void shift(int where, int gapLength, boolean exclusive)
/* 1063:     */     {
/* 1064:1414 */       this.offset = CodeIterator.newOffset(this.pos, this.offset, where, gapLength, exclusive);
/* 1065:1415 */       super.shift(where, gapLength, exclusive);
/* 1066:     */     }
/* 1067:     */     
/* 1068:     */     int write(int src, byte[] code, int dest, byte[] newcode)
/* 1069:     */     {
/* 1070:1419 */       newcode[dest] = code[src];
/* 1071:1420 */       ByteArray.write32bit(this.offset, newcode, dest + 1);
/* 1072:1421 */       return 5;
/* 1073:     */     }
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   static abstract class Switcher
/* 1077:     */     extends CodeIterator.Branch
/* 1078:     */   {
/* 1079:     */     int gap;
/* 1080:     */     int defaultByte;
/* 1081:     */     int[] offsets;
/* 1082:     */     
/* 1083:     */     Switcher(int pos, int defaultByte, int[] offsets)
/* 1084:     */     {
/* 1085:1430 */       super();
/* 1086:1431 */       this.gap = (3 - (pos & 0x3));
/* 1087:1432 */       this.defaultByte = defaultByte;
/* 1088:1433 */       this.offsets = offsets;
/* 1089:     */     }
/* 1090:     */     
/* 1091:     */     void shift(int where, int gapLength, boolean exclusive)
/* 1092:     */     {
/* 1093:1437 */       int p = this.pos;
/* 1094:1438 */       this.defaultByte = CodeIterator.newOffset(p, this.defaultByte, where, gapLength, exclusive);
/* 1095:1439 */       int num = this.offsets.length;
/* 1096:1440 */       for (int i = 0; i < num; i++) {
/* 1097:1441 */         this.offsets[i] = CodeIterator.newOffset(p, this.offsets[i], where, gapLength, exclusive);
/* 1098:     */       }
/* 1099:1443 */       super.shift(where, gapLength, exclusive);
/* 1100:     */     }
/* 1101:     */     
/* 1102:     */     int gapChanged()
/* 1103:     */     {
/* 1104:1447 */       int newGap = 3 - (this.pos & 0x3);
/* 1105:1448 */       if (newGap > this.gap)
/* 1106:     */       {
/* 1107:1449 */         int diff = newGap - this.gap;
/* 1108:1450 */         this.gap = newGap;
/* 1109:1451 */         return diff;
/* 1110:     */       }
/* 1111:1454 */       return 0;
/* 1112:     */     }
/* 1113:     */     
/* 1114:     */     int deltaSize()
/* 1115:     */     {
/* 1116:1458 */       return this.gap - (3 - (this.orgPos & 0x3));
/* 1117:     */     }
/* 1118:     */     
/* 1119:     */     int write(int src, byte[] code, int dest, byte[] newcode)
/* 1120:     */     {
/* 1121:1462 */       int padding = 3 - (this.pos & 0x3);
/* 1122:1463 */       int nops = this.gap - padding;
/* 1123:1464 */       int bytecodeSize = 5 + (3 - (this.orgPos & 0x3)) + tableSize();
/* 1124:1465 */       adjustOffsets(bytecodeSize, nops);
/* 1125:1466 */       newcode[(dest++)] = code[src];
/* 1126:1467 */       while (padding-- > 0) {
/* 1127:1468 */         newcode[(dest++)] = 0;
/* 1128:     */       }
/* 1129:1470 */       ByteArray.write32bit(this.defaultByte, newcode, dest);
/* 1130:1471 */       int size = write2(dest + 4, newcode);
/* 1131:1472 */       dest += size + 4;
/* 1132:1473 */       while (nops-- > 0) {
/* 1133:1474 */         newcode[(dest++)] = 0;
/* 1134:     */       }
/* 1135:1476 */       return 5 + (3 - (this.orgPos & 0x3)) + size;
/* 1136:     */     }
/* 1137:     */     
/* 1138:     */     abstract int write2(int paramInt, byte[] paramArrayOfByte);
/* 1139:     */     
/* 1140:     */     abstract int tableSize();
/* 1141:     */     
/* 1142:     */     void adjustOffsets(int size, int nops)
/* 1143:     */     {
/* 1144:1492 */       if (this.defaultByte == size) {
/* 1145:1493 */         this.defaultByte -= nops;
/* 1146:     */       }
/* 1147:1495 */       for (int i = 0; i < this.offsets.length; i++) {
/* 1148:1496 */         if (this.offsets[i] == size) {
/* 1149:1497 */           this.offsets[i] -= nops;
/* 1150:     */         }
/* 1151:     */       }
/* 1152:     */     }
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   static class Table
/* 1156:     */     extends CodeIterator.Switcher
/* 1157:     */   {
/* 1158:     */     int low;
/* 1159:     */     int high;
/* 1160:     */     
/* 1161:     */     Table(int pos, int defaultByte, int low, int high, int[] offsets)
/* 1162:     */     {
/* 1163:1505 */       super(defaultByte, offsets);
/* 1164:1506 */       this.low = low;
/* 1165:1507 */       this.high = high;
/* 1166:     */     }
/* 1167:     */     
/* 1168:     */     int write2(int dest, byte[] newcode)
/* 1169:     */     {
/* 1170:1511 */       ByteArray.write32bit(this.low, newcode, dest);
/* 1171:1512 */       ByteArray.write32bit(this.high, newcode, dest + 4);
/* 1172:1513 */       int n = this.offsets.length;
/* 1173:1514 */       dest += 8;
/* 1174:1515 */       for (int i = 0; i < n; i++)
/* 1175:     */       {
/* 1176:1516 */         ByteArray.write32bit(this.offsets[i], newcode, dest);
/* 1177:1517 */         dest += 4;
/* 1178:     */       }
/* 1179:1520 */       return 8 + 4 * n;
/* 1180:     */     }
/* 1181:     */     
/* 1182:     */     int tableSize()
/* 1183:     */     {
/* 1184:1523 */       return 8 + 4 * this.offsets.length;
/* 1185:     */     }
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   static class Lookup
/* 1189:     */     extends CodeIterator.Switcher
/* 1190:     */   {
/* 1191:     */     int[] matches;
/* 1192:     */     
/* 1193:     */     Lookup(int pos, int defaultByte, int[] matches, int[] offsets)
/* 1194:     */     {
/* 1195:1530 */       super(defaultByte, offsets);
/* 1196:1531 */       this.matches = matches;
/* 1197:     */     }
/* 1198:     */     
/* 1199:     */     int write2(int dest, byte[] newcode)
/* 1200:     */     {
/* 1201:1535 */       int n = this.matches.length;
/* 1202:1536 */       ByteArray.write32bit(n, newcode, dest);
/* 1203:1537 */       dest += 4;
/* 1204:1538 */       for (int i = 0; i < n; i++)
/* 1205:     */       {
/* 1206:1539 */         ByteArray.write32bit(this.matches[i], newcode, dest);
/* 1207:1540 */         ByteArray.write32bit(this.offsets[i], newcode, dest + 4);
/* 1208:1541 */         dest += 8;
/* 1209:     */       }
/* 1210:1544 */       return 4 + 8 * n;
/* 1211:     */     }
/* 1212:     */     
/* 1213:     */     int tableSize()
/* 1214:     */     {
/* 1215:1547 */       return 4 + 8 * this.matches.length;
/* 1216:     */     }
/* 1217:     */   }
/* 1218:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.CodeIterator
 * JD-Core Version:    0.7.0.1
 */
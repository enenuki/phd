/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ class CodeAnalyzer
/*   4:    */   implements Opcode
/*   5:    */ {
/*   6:    */   private ConstPool constPool;
/*   7:    */   private CodeAttribute codeAttr;
/*   8:    */   
/*   9:    */   public CodeAnalyzer(CodeAttribute ca)
/*  10:    */   {
/*  11: 26 */     this.codeAttr = ca;
/*  12: 27 */     this.constPool = ca.getConstPool();
/*  13:    */   }
/*  14:    */   
/*  15:    */   public int computeMaxStack()
/*  16:    */     throws BadBytecode
/*  17:    */   {
/*  18: 38 */     CodeIterator ci = this.codeAttr.iterator();
/*  19: 39 */     int length = ci.getCodeLength();
/*  20: 40 */     int[] stack = new int[length];
/*  21: 41 */     this.constPool = this.codeAttr.getConstPool();
/*  22: 42 */     initStack(stack, this.codeAttr);
/*  23:    */     boolean repeat;
/*  24:    */     do
/*  25:    */     {
/*  26: 45 */       repeat = false;
/*  27: 46 */       for (int i = 0; i < length; i++) {
/*  28: 47 */         if (stack[i] < 0)
/*  29:    */         {
/*  30: 48 */           repeat = true;
/*  31: 49 */           visitBytecode(ci, stack, i);
/*  32:    */         }
/*  33:    */       }
/*  34: 51 */     } while (repeat);
/*  35: 53 */     int maxStack = 1;
/*  36: 54 */     for (int i = 0; i < length; i++) {
/*  37: 55 */       if (stack[i] > maxStack) {
/*  38: 56 */         maxStack = stack[i];
/*  39:    */       }
/*  40:    */     }
/*  41: 58 */     return maxStack - 1;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void initStack(int[] stack, CodeAttribute ca)
/*  45:    */   {
/*  46: 62 */     stack[0] = -1;
/*  47: 63 */     ExceptionTable et = ca.getExceptionTable();
/*  48: 64 */     if (et != null)
/*  49:    */     {
/*  50: 65 */       int size = et.size();
/*  51: 66 */       for (int i = 0; i < size; i++) {
/*  52: 67 */         stack[et.handlerPc(i)] = -2;
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void visitBytecode(CodeIterator ci, int[] stack, int index)
/*  58:    */     throws BadBytecode
/*  59:    */   {
/*  60: 74 */     int codeLength = stack.length;
/*  61: 75 */     ci.move(index);
/*  62: 76 */     int stackDepth = -stack[index];
/*  63: 77 */     while (ci.hasNext())
/*  64:    */     {
/*  65: 78 */       index = ci.next();
/*  66: 79 */       stack[index] = stackDepth;
/*  67: 80 */       int op = ci.byteAt(index);
/*  68: 81 */       stackDepth = visitInst(op, ci, index, stackDepth);
/*  69: 82 */       if (stackDepth < 1) {
/*  70: 83 */         throw new BadBytecode("stack underflow at " + index);
/*  71:    */       }
/*  72: 85 */       if (processBranch(op, ci, index, codeLength, stack, stackDepth)) {
/*  73:    */         break;
/*  74:    */       }
/*  75: 88 */       if (isEnd(op)) {
/*  76:    */         break;
/*  77:    */       }
/*  78: 91 */       if ((op == 168) || (op == 201)) {
/*  79: 92 */         stackDepth--;
/*  80:    */       }
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   private boolean processBranch(int opcode, CodeIterator ci, int index, int codeLength, int[] stack, int stackDepth)
/*  85:    */     throws BadBytecode
/*  86:    */   {
/*  87:100 */     if (((153 <= opcode) && (opcode <= 166)) || (opcode == 198) || (opcode == 199))
/*  88:    */     {
/*  89:102 */       int target = index + ci.s16bitAt(index + 1);
/*  90:103 */       checkTarget(index, target, codeLength, stack, stackDepth);
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:    */       int target;
/*  95:    */       int target;
/*  96:107 */       switch (opcode)
/*  97:    */       {
/*  98:    */       case 167: 
/*  99:109 */         target = index + ci.s16bitAt(index + 1);
/* 100:110 */         checkTarget(index, target, codeLength, stack, stackDepth);
/* 101:111 */         return true;
/* 102:    */       case 200: 
/* 103:113 */         target = index + ci.s32bitAt(index + 1);
/* 104:114 */         checkTarget(index, target, codeLength, stack, stackDepth);
/* 105:115 */         return true;
/* 106:    */       case 168: 
/* 107:    */       case 201: 
/* 108:118 */         if (opcode == 168) {
/* 109:119 */           target = index + ci.s16bitAt(index + 1);
/* 110:    */         } else {
/* 111:121 */           target = index + ci.s32bitAt(index + 1);
/* 112:    */         }
/* 113:123 */         checkTarget(index, target, codeLength, stack, stackDepth);
/* 114:124 */         if (stackDepth == 2) {
/* 115:125 */           return false;
/* 116:    */         }
/* 117:127 */         throw new BadBytecode("sorry, cannot compute this data flow due to JSR");
/* 118:    */       case 169: 
/* 119:130 */         if (stackDepth == 1) {
/* 120:131 */           return true;
/* 121:    */         }
/* 122:133 */         throw new BadBytecode("sorry, cannot compute this data flow due to RET");
/* 123:    */       case 170: 
/* 124:    */       case 171: 
/* 125:137 */         int index2 = (index & 0xFFFFFFFC) + 4;
/* 126:138 */         target = index + ci.s32bitAt(index2);
/* 127:139 */         checkTarget(index, target, codeLength, stack, stackDepth);
/* 128:140 */         if (opcode == 171)
/* 129:    */         {
/* 130:141 */           int npairs = ci.s32bitAt(index2 + 4);
/* 131:142 */           index2 += 12;
/* 132:143 */           for (int i = 0; i < npairs; i++)
/* 133:    */           {
/* 134:144 */             target = index + ci.s32bitAt(index2);
/* 135:145 */             checkTarget(index, target, codeLength, stack, stackDepth);
/* 136:    */             
/* 137:147 */             index2 += 8;
/* 138:    */           }
/* 139:    */         }
/* 140:    */         else
/* 141:    */         {
/* 142:151 */           int low = ci.s32bitAt(index2 + 4);
/* 143:152 */           int high = ci.s32bitAt(index2 + 8);
/* 144:153 */           int n = high - low + 1;
/* 145:154 */           index2 += 12;
/* 146:155 */           for (int i = 0; i < n; i++)
/* 147:    */           {
/* 148:156 */             target = index + ci.s32bitAt(index2);
/* 149:157 */             checkTarget(index, target, codeLength, stack, stackDepth);
/* 150:    */             
/* 151:159 */             index2 += 4;
/* 152:    */           }
/* 153:    */         }
/* 154:163 */         return true;
/* 155:    */       }
/* 156:    */     }
/* 157:167 */     return false;
/* 158:    */   }
/* 159:    */   
/* 160:    */   private void checkTarget(int opIndex, int target, int codeLength, int[] stack, int stackDepth)
/* 161:    */     throws BadBytecode
/* 162:    */   {
/* 163:174 */     if ((target < 0) || (codeLength <= target)) {
/* 164:175 */       throw new BadBytecode("bad branch offset at " + opIndex);
/* 165:    */     }
/* 166:177 */     int d = stack[target];
/* 167:178 */     if (d == 0) {
/* 168:179 */       stack[target] = (-stackDepth);
/* 169:180 */     } else if ((d != stackDepth) && (d != -stackDepth)) {
/* 170:181 */       throw new BadBytecode("verification error (" + stackDepth + "," + d + ") at " + opIndex);
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   private static boolean isEnd(int opcode)
/* 175:    */   {
/* 176:186 */     return ((172 <= opcode) && (opcode <= 177)) || (opcode == 191);
/* 177:    */   }
/* 178:    */   
/* 179:    */   private int visitInst(int op, CodeIterator ci, int index, int stack)
/* 180:    */     throws BadBytecode
/* 181:    */   {
/* 182:    */     String desc;
/* 183:196 */     switch (op)
/* 184:    */     {
/* 185:    */     case 180: 
/* 186:198 */       stack += getFieldSize(ci, index) - 1;
/* 187:199 */       break;
/* 188:    */     case 181: 
/* 189:201 */       stack -= getFieldSize(ci, index) + 1;
/* 190:202 */       break;
/* 191:    */     case 178: 
/* 192:204 */       stack += getFieldSize(ci, index);
/* 193:205 */       break;
/* 194:    */     case 179: 
/* 195:207 */       stack -= getFieldSize(ci, index);
/* 196:208 */       break;
/* 197:    */     case 182: 
/* 198:    */     case 183: 
/* 199:211 */       desc = this.constPool.getMethodrefType(ci.u16bitAt(index + 1));
/* 200:212 */       stack += Descriptor.dataSize(desc) - 1;
/* 201:213 */       break;
/* 202:    */     case 184: 
/* 203:215 */       desc = this.constPool.getMethodrefType(ci.u16bitAt(index + 1));
/* 204:216 */       stack += Descriptor.dataSize(desc);
/* 205:217 */       break;
/* 206:    */     case 185: 
/* 207:219 */       desc = this.constPool.getInterfaceMethodrefType(ci.u16bitAt(index + 1));
/* 208:    */       
/* 209:221 */       stack += Descriptor.dataSize(desc) - 1;
/* 210:222 */       break;
/* 211:    */     case 191: 
/* 212:224 */       stack = 1;
/* 213:225 */       break;
/* 214:    */     case 197: 
/* 215:227 */       stack += 1 - ci.byteAt(index + 3);
/* 216:228 */       break;
/* 217:    */     case 196: 
/* 218:230 */       op = ci.byteAt(index + 1);
/* 219:    */     case 186: 
/* 220:    */     case 187: 
/* 221:    */     case 188: 
/* 222:    */     case 189: 
/* 223:    */     case 190: 
/* 224:    */     case 192: 
/* 225:    */     case 193: 
/* 226:    */     case 194: 
/* 227:    */     case 195: 
/* 228:    */     default: 
/* 229:233 */       stack += STACK_GROW[op];
/* 230:    */     }
/* 231:236 */     return stack;
/* 232:    */   }
/* 233:    */   
/* 234:    */   private int getFieldSize(CodeIterator ci, int index)
/* 235:    */   {
/* 236:240 */     String desc = this.constPool.getFieldrefType(ci.u16bitAt(index + 1));
/* 237:241 */     return Descriptor.dataSize(desc);
/* 238:    */   }
/* 239:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.CodeAnalyzer
 * JD-Core Version:    0.7.0.1
 */
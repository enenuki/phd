/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import javassist.CtMethod;
/*   5:    */ 
/*   6:    */ public class InstructionPrinter
/*   7:    */   implements Opcode
/*   8:    */ {
/*   9: 28 */   private static final String[] opcodes = Mnemonic.OPCODE;
/*  10:    */   private final PrintStream stream;
/*  11:    */   
/*  12:    */   public InstructionPrinter(PrintStream stream)
/*  13:    */   {
/*  14: 32 */     this.stream = stream;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static void print(CtMethod method, PrintStream stream)
/*  18:    */   {
/*  19: 36 */     new InstructionPrinter(stream).print(method);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void print(CtMethod method)
/*  23:    */   {
/*  24: 40 */     MethodInfo info = method.getMethodInfo2();
/*  25: 41 */     ConstPool pool = info.getConstPool();
/*  26: 42 */     CodeAttribute code = info.getCodeAttribute();
/*  27: 43 */     if (code == null) {
/*  28: 44 */       return;
/*  29:    */     }
/*  30: 46 */     CodeIterator iterator = code.iterator();
/*  31: 47 */     while (iterator.hasNext())
/*  32:    */     {
/*  33:    */       int pos;
/*  34:    */       try
/*  35:    */       {
/*  36: 50 */         pos = iterator.next();
/*  37:    */       }
/*  38:    */       catch (BadBytecode e)
/*  39:    */       {
/*  40: 52 */         throw new RuntimeException(e);
/*  41:    */       }
/*  42: 55 */       this.stream.println(pos + ": " + instructionString(iterator, pos, pool));
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static String instructionString(CodeIterator iter, int pos, ConstPool pool)
/*  47:    */   {
/*  48: 60 */     int opcode = iter.byteAt(pos);
/*  49: 62 */     if ((opcode > opcodes.length) || (opcode < 0)) {
/*  50: 63 */       throw new IllegalArgumentException("Invalid opcode, opcode: " + opcode + " pos: " + pos);
/*  51:    */     }
/*  52: 65 */     String opstring = opcodes[opcode];
/*  53: 66 */     switch (opcode)
/*  54:    */     {
/*  55:    */     case 16: 
/*  56: 68 */       return opstring + " " + iter.byteAt(pos + 1);
/*  57:    */     case 17: 
/*  58: 70 */       return opstring + " " + iter.s16bitAt(pos + 1);
/*  59:    */     case 18: 
/*  60: 72 */       return opstring + " " + ldc(pool, iter.byteAt(pos + 1));
/*  61:    */     case 19: 
/*  62:    */     case 20: 
/*  63: 75 */       return opstring + " " + ldc(pool, iter.u16bitAt(pos + 1));
/*  64:    */     case 21: 
/*  65:    */     case 22: 
/*  66:    */     case 23: 
/*  67:    */     case 24: 
/*  68:    */     case 25: 
/*  69:    */     case 54: 
/*  70:    */     case 55: 
/*  71:    */     case 56: 
/*  72:    */     case 57: 
/*  73:    */     case 58: 
/*  74: 86 */       return opstring + " " + iter.byteAt(pos + 1);
/*  75:    */     case 153: 
/*  76:    */     case 154: 
/*  77:    */     case 155: 
/*  78:    */     case 156: 
/*  79:    */     case 157: 
/*  80:    */     case 158: 
/*  81:    */     case 159: 
/*  82:    */     case 160: 
/*  83:    */     case 161: 
/*  84:    */     case 162: 
/*  85:    */     case 163: 
/*  86:    */     case 164: 
/*  87:    */     case 165: 
/*  88:    */     case 166: 
/*  89:    */     case 198: 
/*  90:    */     case 199: 
/*  91:103 */       return opstring + " " + (iter.s16bitAt(pos + 1) + pos);
/*  92:    */     case 132: 
/*  93:105 */       return opstring + " " + iter.byteAt(pos + 1);
/*  94:    */     case 167: 
/*  95:    */     case 168: 
/*  96:108 */       return opstring + " " + (iter.s16bitAt(pos + 1) + pos);
/*  97:    */     case 169: 
/*  98:110 */       return opstring + " " + iter.byteAt(pos + 1);
/*  99:    */     case 170: 
/* 100:112 */       return tableSwitch(iter, pos);
/* 101:    */     case 171: 
/* 102:114 */       return lookupSwitch(iter, pos);
/* 103:    */     case 178: 
/* 104:    */     case 179: 
/* 105:    */     case 180: 
/* 106:    */     case 181: 
/* 107:119 */       return opstring + " " + fieldInfo(pool, iter.u16bitAt(pos + 1));
/* 108:    */     case 182: 
/* 109:    */     case 183: 
/* 110:    */     case 184: 
/* 111:123 */       return opstring + " " + methodInfo(pool, iter.u16bitAt(pos + 1));
/* 112:    */     case 185: 
/* 113:125 */       return opstring + " " + interfaceMethodInfo(pool, iter.u16bitAt(pos + 1));
/* 114:    */     case 186: 
/* 115:127 */       throw new RuntimeException("Bad opcode 186");
/* 116:    */     case 187: 
/* 117:129 */       return opstring + " " + classInfo(pool, iter.u16bitAt(pos + 1));
/* 118:    */     case 188: 
/* 119:131 */       return opstring + " " + arrayInfo(iter.byteAt(pos + 1));
/* 120:    */     case 189: 
/* 121:    */     case 192: 
/* 122:134 */       return opstring + " " + classInfo(pool, iter.u16bitAt(pos + 1));
/* 123:    */     case 196: 
/* 124:136 */       return wide(iter, pos);
/* 125:    */     case 197: 
/* 126:138 */       return opstring + " " + classInfo(pool, iter.u16bitAt(pos + 1));
/* 127:    */     case 200: 
/* 128:    */     case 201: 
/* 129:141 */       return opstring + " " + (iter.s32bitAt(pos + 1) + pos);
/* 130:    */     }
/* 131:143 */     return opstring;
/* 132:    */   }
/* 133:    */   
/* 134:    */   private static String wide(CodeIterator iter, int pos)
/* 135:    */   {
/* 136:149 */     int opcode = iter.byteAt(pos + 1);
/* 137:150 */     int index = iter.u16bitAt(pos + 2);
/* 138:151 */     switch (opcode)
/* 139:    */     {
/* 140:    */     case 21: 
/* 141:    */     case 22: 
/* 142:    */     case 23: 
/* 143:    */     case 24: 
/* 144:    */     case 25: 
/* 145:    */     case 54: 
/* 146:    */     case 55: 
/* 147:    */     case 56: 
/* 148:    */     case 57: 
/* 149:    */     case 58: 
/* 150:    */     case 132: 
/* 151:    */     case 169: 
/* 152:164 */       return opcodes[opcode] + " " + index;
/* 153:    */     }
/* 154:166 */     throw new RuntimeException("Invalid WIDE operand");
/* 155:    */   }
/* 156:    */   
/* 157:    */   private static String arrayInfo(int type)
/* 158:    */   {
/* 159:172 */     switch (type)
/* 160:    */     {
/* 161:    */     case 4: 
/* 162:174 */       return "boolean";
/* 163:    */     case 5: 
/* 164:176 */       return "char";
/* 165:    */     case 8: 
/* 166:178 */       return "byte";
/* 167:    */     case 9: 
/* 168:180 */       return "short";
/* 169:    */     case 10: 
/* 170:182 */       return "int";
/* 171:    */     case 11: 
/* 172:184 */       return "long";
/* 173:    */     case 6: 
/* 174:186 */       return "float";
/* 175:    */     case 7: 
/* 176:188 */       return "double";
/* 177:    */     }
/* 178:190 */     throw new RuntimeException("Invalid array type");
/* 179:    */   }
/* 180:    */   
/* 181:    */   private static String classInfo(ConstPool pool, int index)
/* 182:    */   {
/* 183:196 */     return "#" + index + " = Class " + pool.getClassInfo(index);
/* 184:    */   }
/* 185:    */   
/* 186:    */   private static String interfaceMethodInfo(ConstPool pool, int index)
/* 187:    */   {
/* 188:201 */     return "#" + index + " = Method " + pool.getInterfaceMethodrefClassName(index) + "." + pool.getInterfaceMethodrefName(index) + "(" + pool.getInterfaceMethodrefType(index) + ")";
/* 189:    */   }
/* 190:    */   
/* 191:    */   private static String methodInfo(ConstPool pool, int index)
/* 192:    */   {
/* 193:208 */     return "#" + index + " = Method " + pool.getMethodrefClassName(index) + "." + pool.getMethodrefName(index) + "(" + pool.getMethodrefType(index) + ")";
/* 194:    */   }
/* 195:    */   
/* 196:    */   private static String fieldInfo(ConstPool pool, int index)
/* 197:    */   {
/* 198:216 */     return "#" + index + " = Field " + pool.getFieldrefClassName(index) + "." + pool.getFieldrefName(index) + "(" + pool.getFieldrefType(index) + ")";
/* 199:    */   }
/* 200:    */   
/* 201:    */   private static String lookupSwitch(CodeIterator iter, int pos)
/* 202:    */   {
/* 203:224 */     StringBuffer buffer = new StringBuffer("lookupswitch {\n");
/* 204:225 */     int index = (pos & 0xFFFFFFFC) + 4;
/* 205:    */     
/* 206:227 */     buffer.append("\t\tdefault: ").append(pos + iter.s32bitAt(index)).append("\n");
/* 207:228 */     index += 4;int npairs = iter.s32bitAt(index);
/* 208:229 */     index += 4;int end = npairs * 8 + index;
/* 209:231 */     for (; index < end; index += 8)
/* 210:    */     {
/* 211:232 */       int match = iter.s32bitAt(index);
/* 212:233 */       int target = iter.s32bitAt(index + 4) + pos;
/* 213:234 */       buffer.append("\t\t").append(match).append(": ").append(target).append("\n");
/* 214:    */     }
/* 215:237 */     buffer.setCharAt(buffer.length() - 1, '}');
/* 216:238 */     return buffer.toString();
/* 217:    */   }
/* 218:    */   
/* 219:    */   private static String tableSwitch(CodeIterator iter, int pos)
/* 220:    */   {
/* 221:243 */     StringBuffer buffer = new StringBuffer("tableswitch {\n");
/* 222:244 */     int index = (pos & 0xFFFFFFFC) + 4;
/* 223:    */     
/* 224:246 */     buffer.append("\t\tdefault: ").append(pos + iter.s32bitAt(index)).append("\n");
/* 225:247 */     index += 4;int low = iter.s32bitAt(index);
/* 226:248 */     index += 4;int high = iter.s32bitAt(index);
/* 227:249 */     index += 4;int end = (high - low + 1) * 4 + index;
/* 228:252 */     for (int key = low; index < end; key++)
/* 229:    */     {
/* 230:253 */       int target = iter.s32bitAt(index) + pos;
/* 231:254 */       buffer.append("\t\t").append(key).append(": ").append(target).append("\n");index += 4;
/* 232:    */     }
/* 233:257 */     buffer.setCharAt(buffer.length() - 1, '}');
/* 234:258 */     return buffer.toString();
/* 235:    */   }
/* 236:    */   
/* 237:    */   private static String ldc(ConstPool pool, int index)
/* 238:    */   {
/* 239:263 */     int tag = pool.getTag(index);
/* 240:264 */     switch (tag)
/* 241:    */     {
/* 242:    */     case 8: 
/* 243:266 */       return "#" + index + " = \"" + pool.getStringInfo(index) + "\"";
/* 244:    */     case 3: 
/* 245:268 */       return "#" + index + " = int " + pool.getIntegerInfo(index);
/* 246:    */     case 4: 
/* 247:270 */       return "#" + index + " = float " + pool.getFloatInfo(index);
/* 248:    */     case 5: 
/* 249:272 */       return "#" + index + " = long " + pool.getLongInfo(index);
/* 250:    */     case 6: 
/* 251:274 */       return "#" + index + " = int " + pool.getDoubleInfo(index);
/* 252:    */     case 7: 
/* 253:276 */       return classInfo(pool, index);
/* 254:    */     }
/* 255:278 */     throw new RuntimeException("bad LDC: " + tag);
/* 256:    */   }
/* 257:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.InstructionPrinter
 * JD-Core Version:    0.7.0.1
 */
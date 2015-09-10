/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.BranchHandle;
/*   4:    */ import org.apache.bcel.generic.BranchInstruction;
/*   5:    */ import org.apache.bcel.generic.CHECKCAST;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.GOTO;
/*   9:    */ import org.apache.bcel.generic.IFEQ;
/*  10:    */ import org.apache.bcel.generic.IFGE;
/*  11:    */ import org.apache.bcel.generic.IFGT;
/*  12:    */ import org.apache.bcel.generic.IFLE;
/*  13:    */ import org.apache.bcel.generic.IFLT;
/*  14:    */ import org.apache.bcel.generic.IF_ICMPGE;
/*  15:    */ import org.apache.bcel.generic.IF_ICMPGT;
/*  16:    */ import org.apache.bcel.generic.IF_ICMPLE;
/*  17:    */ import org.apache.bcel.generic.IF_ICMPLT;
/*  18:    */ import org.apache.bcel.generic.ILOAD;
/*  19:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  20:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  21:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  22:    */ import org.apache.bcel.generic.ISTORE;
/*  23:    */ import org.apache.bcel.generic.Instruction;
/*  24:    */ import org.apache.bcel.generic.InstructionConstants;
/*  25:    */ import org.apache.bcel.generic.InstructionList;
/*  26:    */ import org.apache.bcel.generic.MethodGen;
/*  27:    */ import org.apache.bcel.generic.NEW;
/*  28:    */ import org.apache.xalan.xsltc.compiler.FlowList;
/*  29:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  30:    */ 
/*  31:    */ public final class IntType
/*  32:    */   extends NumberType
/*  33:    */ {
/*  34:    */   public String toString()
/*  35:    */   {
/*  36: 58 */     return "int";
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean identicalTo(Type other)
/*  40:    */   {
/*  41: 62 */     return this == other;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String toSignature()
/*  45:    */   {
/*  46: 66 */     return "I";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public org.apache.bcel.generic.Type toJCType()
/*  50:    */   {
/*  51: 70 */     return org.apache.bcel.generic.Type.INT;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int distanceTo(Type type)
/*  55:    */   {
/*  56: 77 */     if (type == this) {
/*  57: 78 */       return 0;
/*  58:    */     }
/*  59: 80 */     if (type == Type.Real) {
/*  60: 81 */       return 1;
/*  61:    */     }
/*  62: 84 */     return 2147483647;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  66:    */   {
/*  67: 94 */     if (type == Type.Real)
/*  68:    */     {
/*  69: 95 */       translateTo(classGen, methodGen, (RealType)type);
/*  70:    */     }
/*  71: 97 */     else if (type == Type.String)
/*  72:    */     {
/*  73: 98 */       translateTo(classGen, methodGen, (StringType)type);
/*  74:    */     }
/*  75:100 */     else if (type == Type.Boolean)
/*  76:    */     {
/*  77:101 */       translateTo(classGen, methodGen, (BooleanType)type);
/*  78:    */     }
/*  79:103 */     else if (type == Type.Reference)
/*  80:    */     {
/*  81:104 */       translateTo(classGen, methodGen, (ReferenceType)type);
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:107 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/*  86:    */       
/*  87:109 */       classGen.getParser().reportError(2, err);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, RealType type)
/*  92:    */   {
/*  93:120 */     methodGen.getInstructionList().append(InstructionConstants.I2D);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type)
/*  97:    */   {
/*  98:131 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  99:132 */     InstructionList il = methodGen.getInstructionList();
/* 100:133 */     il.append(new INVOKESTATIC(cpg.addMethodref("java.lang.Integer", "toString", "(I)Ljava/lang/String;")));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 104:    */   {
/* 105:146 */     InstructionList il = methodGen.getInstructionList();
/* 106:147 */     BranchHandle falsec = il.append(new IFEQ(null));
/* 107:148 */     il.append(InstructionConstants.ICONST_1);
/* 108:149 */     BranchHandle truec = il.append(new GOTO(null));
/* 109:150 */     falsec.setTarget(il.append(InstructionConstants.ICONST_0));
/* 110:151 */     truec.setTarget(il.append(InstructionConstants.NOP));
/* 111:    */   }
/* 112:    */   
/* 113:    */   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 114:    */   {
/* 115:164 */     InstructionList il = methodGen.getInstructionList();
/* 116:165 */     return new FlowList(il.append(new IFEQ(null)));
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ReferenceType type)
/* 120:    */   {
/* 121:177 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 122:178 */     InstructionList il = methodGen.getInstructionList();
/* 123:179 */     il.append(new NEW(cpg.addClass("java.lang.Integer")));
/* 124:180 */     il.append(InstructionConstants.DUP_X1);
/* 125:181 */     il.append(InstructionConstants.SWAP);
/* 126:182 */     il.append(new INVOKESPECIAL(cpg.addMethodref("java.lang.Integer", "<init>", "(I)V")));
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 130:    */   {
/* 131:193 */     InstructionList il = methodGen.getInstructionList();
/* 132:194 */     if (clazz == Character.TYPE)
/* 133:    */     {
/* 134:195 */       il.append(InstructionConstants.I2C);
/* 135:    */     }
/* 136:197 */     else if (clazz == Byte.TYPE)
/* 137:    */     {
/* 138:198 */       il.append(InstructionConstants.I2B);
/* 139:    */     }
/* 140:200 */     else if (clazz == Short.TYPE)
/* 141:    */     {
/* 142:201 */       il.append(InstructionConstants.I2S);
/* 143:    */     }
/* 144:203 */     else if (clazz == Integer.TYPE)
/* 145:    */     {
/* 146:204 */       il.append(InstructionConstants.NOP);
/* 147:    */     }
/* 148:206 */     else if (clazz == Long.TYPE)
/* 149:    */     {
/* 150:207 */       il.append(InstructionConstants.I2L);
/* 151:    */     }
/* 152:209 */     else if (clazz == Float.TYPE)
/* 153:    */     {
/* 154:210 */       il.append(InstructionConstants.I2F);
/* 155:    */     }
/* 156:212 */     else if (clazz == Double.TYPE)
/* 157:    */     {
/* 158:213 */       il.append(InstructionConstants.I2D);
/* 159:    */     }
/* 160:216 */     else if (clazz.isAssignableFrom(Double.class))
/* 161:    */     {
/* 162:217 */       il.append(InstructionConstants.I2D);
/* 163:218 */       Type.Real.translateTo(classGen, methodGen, Type.Reference);
/* 164:    */     }
/* 165:    */     else
/* 166:    */     {
/* 167:221 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 168:    */       
/* 169:223 */       classGen.getParser().reportError(2, err);
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 174:    */   {
/* 175:232 */     translateTo(classGen, methodGen, Type.Reference);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 179:    */   {
/* 180:240 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 181:241 */     InstructionList il = methodGen.getInstructionList();
/* 182:242 */     il.append(new CHECKCAST(cpg.addClass("java.lang.Integer")));
/* 183:243 */     int index = cpg.addMethodref("java.lang.Integer", "intValue", "()I");
/* 184:    */     
/* 185:    */ 
/* 186:246 */     il.append(new INVOKEVIRTUAL(index));
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Instruction ADD()
/* 190:    */   {
/* 191:250 */     return InstructionConstants.IADD;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Instruction SUB()
/* 195:    */   {
/* 196:254 */     return InstructionConstants.ISUB;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Instruction MUL()
/* 200:    */   {
/* 201:258 */     return InstructionConstants.IMUL;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public Instruction DIV()
/* 205:    */   {
/* 206:262 */     return InstructionConstants.IDIV;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Instruction REM()
/* 210:    */   {
/* 211:266 */     return InstructionConstants.IREM;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public Instruction NEG()
/* 215:    */   {
/* 216:270 */     return InstructionConstants.INEG;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public Instruction LOAD(int slot)
/* 220:    */   {
/* 221:274 */     return new ILOAD(slot);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public Instruction STORE(int slot)
/* 225:    */   {
/* 226:278 */     return new ISTORE(slot);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public BranchInstruction GT(boolean tozero)
/* 230:    */   {
/* 231:282 */     return tozero ? new IFGT(null) : new IF_ICMPGT(null);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public BranchInstruction GE(boolean tozero)
/* 235:    */   {
/* 236:287 */     return tozero ? new IFGE(null) : new IF_ICMPGE(null);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public BranchInstruction LT(boolean tozero)
/* 240:    */   {
/* 241:292 */     return tozero ? new IFLT(null) : new IF_ICMPLT(null);
/* 242:    */   }
/* 243:    */   
/* 244:    */   public BranchInstruction LE(boolean tozero)
/* 245:    */   {
/* 246:297 */     return tozero ? new IFLE(null) : new IF_ICMPLE(null);
/* 247:    */   }
/* 248:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.IntType
 * JD-Core Version:    0.7.0.1
 */
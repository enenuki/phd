/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.BranchHandle;
/*   4:    */ import org.apache.bcel.generic.CHECKCAST;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.DLOAD;
/*   8:    */ import org.apache.bcel.generic.DSTORE;
/*   9:    */ import org.apache.bcel.generic.GOTO;
/*  10:    */ import org.apache.bcel.generic.IFEQ;
/*  11:    */ import org.apache.bcel.generic.IFNE;
/*  12:    */ import org.apache.bcel.generic.INVOKESPECIAL;
/*  13:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  14:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  15:    */ import org.apache.bcel.generic.Instruction;
/*  16:    */ import org.apache.bcel.generic.InstructionConstants;
/*  17:    */ import org.apache.bcel.generic.InstructionList;
/*  18:    */ import org.apache.bcel.generic.LocalVariableGen;
/*  19:    */ import org.apache.bcel.generic.MethodGen;
/*  20:    */ import org.apache.bcel.generic.NEW;
/*  21:    */ import org.apache.xalan.xsltc.compiler.FlowList;
/*  22:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*  23:    */ 
/*  24:    */ public final class RealType
/*  25:    */   extends NumberType
/*  26:    */ {
/*  27:    */   public String toString()
/*  28:    */   {
/*  29: 51 */     return "real";
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean identicalTo(Type other)
/*  33:    */   {
/*  34: 55 */     return this == other;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String toSignature()
/*  38:    */   {
/*  39: 59 */     return "D";
/*  40:    */   }
/*  41:    */   
/*  42:    */   public org.apache.bcel.generic.Type toJCType()
/*  43:    */   {
/*  44: 63 */     return org.apache.bcel.generic.Type.DOUBLE;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int distanceTo(Type type)
/*  48:    */   {
/*  49: 70 */     if (type == this) {
/*  50: 71 */       return 0;
/*  51:    */     }
/*  52: 73 */     if (type == Type.Int) {
/*  53: 74 */       return 1;
/*  54:    */     }
/*  55: 77 */     return 2147483647;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  59:    */   {
/*  60: 89 */     if (type == Type.String)
/*  61:    */     {
/*  62: 90 */       translateTo(classGen, methodGen, (StringType)type);
/*  63:    */     }
/*  64: 92 */     else if (type == Type.Boolean)
/*  65:    */     {
/*  66: 93 */       translateTo(classGen, methodGen, (BooleanType)type);
/*  67:    */     }
/*  68: 95 */     else if (type == Type.Reference)
/*  69:    */     {
/*  70: 96 */       translateTo(classGen, methodGen, (ReferenceType)type);
/*  71:    */     }
/*  72: 98 */     else if (type == Type.Int)
/*  73:    */     {
/*  74: 99 */       translateTo(classGen, methodGen, (IntType)type);
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78:102 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/*  79:    */       
/*  80:104 */       classGen.getParser().reportError(2, err);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type)
/*  85:    */   {
/*  86:116 */     ConstantPoolGen cpg = classGen.getConstantPool();
/*  87:117 */     InstructionList il = methodGen.getInstructionList();
/*  88:118 */     il.append(new INVOKESTATIC(cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "realToString", "(D)Ljava/lang/String;")));
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/*  92:    */   {
/*  93:131 */     InstructionList il = methodGen.getInstructionList();
/*  94:132 */     FlowList falsel = translateToDesynthesized(classGen, methodGen, type);
/*  95:133 */     il.append(InstructionConstants.ICONST_1);
/*  96:134 */     BranchHandle truec = il.append(new GOTO(null));
/*  97:135 */     falsel.backPatch(il.append(InstructionConstants.ICONST_0));
/*  98:136 */     truec.setTarget(il.append(InstructionConstants.NOP));
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, IntType type)
/* 102:    */   {
/* 103:146 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 104:147 */     InstructionList il = methodGen.getInstructionList();
/* 105:148 */     il.append(new INVOKESTATIC(cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "realToInt", "(D)I")));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/* 109:    */   {
/* 110:163 */     FlowList flowlist = new FlowList();
/* 111:164 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 112:165 */     InstructionList il = methodGen.getInstructionList();
/* 113:    */     
/* 114:    */ 
/* 115:168 */     il.append(InstructionConstants.DUP2);
/* 116:169 */     LocalVariableGen local = methodGen.addLocalVariable("real_to_boolean_tmp", org.apache.bcel.generic.Type.DOUBLE, null, null);
/* 117:    */     
/* 118:    */ 
/* 119:172 */     local.setStart(il.append(new DSTORE(local.getIndex())));
/* 120:    */     
/* 121:    */ 
/* 122:175 */     il.append(InstructionConstants.DCONST_0);
/* 123:176 */     il.append(InstructionConstants.DCMPG);
/* 124:177 */     flowlist.add(il.append(new IFEQ(null)));
/* 125:    */     
/* 126:    */ 
/* 127:    */ 
/* 128:181 */     il.append(new DLOAD(local.getIndex()));
/* 129:182 */     local.setEnd(il.append(new DLOAD(local.getIndex())));
/* 130:183 */     il.append(InstructionConstants.DCMPG);
/* 131:184 */     flowlist.add(il.append(new IFNE(null)));
/* 132:185 */     return flowlist;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ReferenceType type)
/* 136:    */   {
/* 137:196 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 138:197 */     InstructionList il = methodGen.getInstructionList();
/* 139:198 */     il.append(new NEW(cpg.addClass("java.lang.Double")));
/* 140:199 */     il.append(InstructionConstants.DUP_X2);
/* 141:200 */     il.append(InstructionConstants.DUP_X2);
/* 142:201 */     il.append(InstructionConstants.POP);
/* 143:202 */     il.append(new INVOKESPECIAL(cpg.addMethodref("java.lang.Double", "<init>", "(D)V")));
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 147:    */   {
/* 148:213 */     InstructionList il = methodGen.getInstructionList();
/* 149:214 */     if (clazz == Character.TYPE)
/* 150:    */     {
/* 151:215 */       il.append(InstructionConstants.D2I);
/* 152:216 */       il.append(InstructionConstants.I2C);
/* 153:    */     }
/* 154:218 */     else if (clazz == Byte.TYPE)
/* 155:    */     {
/* 156:219 */       il.append(InstructionConstants.D2I);
/* 157:220 */       il.append(InstructionConstants.I2B);
/* 158:    */     }
/* 159:222 */     else if (clazz == Short.TYPE)
/* 160:    */     {
/* 161:223 */       il.append(InstructionConstants.D2I);
/* 162:224 */       il.append(InstructionConstants.I2S);
/* 163:    */     }
/* 164:226 */     else if (clazz == Integer.TYPE)
/* 165:    */     {
/* 166:227 */       il.append(InstructionConstants.D2I);
/* 167:    */     }
/* 168:229 */     else if (clazz == Long.TYPE)
/* 169:    */     {
/* 170:230 */       il.append(InstructionConstants.D2L);
/* 171:    */     }
/* 172:232 */     else if (clazz == Float.TYPE)
/* 173:    */     {
/* 174:233 */       il.append(InstructionConstants.D2F);
/* 175:    */     }
/* 176:235 */     else if (clazz == Double.TYPE)
/* 177:    */     {
/* 178:236 */       il.append(InstructionConstants.NOP);
/* 179:    */     }
/* 180:239 */     else if (clazz.isAssignableFrom(Double.class))
/* 181:    */     {
/* 182:240 */       translateTo(classGen, methodGen, Type.Reference);
/* 183:    */     }
/* 184:    */     else
/* 185:    */     {
/* 186:243 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 187:    */       
/* 188:245 */       classGen.getParser().reportError(2, err);
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 193:    */   {
/* 194:255 */     InstructionList il = methodGen.getInstructionList();
/* 195:257 */     if ((clazz == Character.TYPE) || (clazz == Byte.TYPE) || (clazz == Short.TYPE) || (clazz == Integer.TYPE))
/* 196:    */     {
/* 197:259 */       il.append(InstructionConstants.I2D);
/* 198:    */     }
/* 199:261 */     else if (clazz == Long.TYPE)
/* 200:    */     {
/* 201:262 */       il.append(InstructionConstants.L2D);
/* 202:    */     }
/* 203:264 */     else if (clazz == Float.TYPE)
/* 204:    */     {
/* 205:265 */       il.append(InstructionConstants.F2D);
/* 206:    */     }
/* 207:267 */     else if (clazz == Double.TYPE)
/* 208:    */     {
/* 209:268 */       il.append(InstructionConstants.NOP);
/* 210:    */     }
/* 211:    */     else
/* 212:    */     {
/* 213:271 */       ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getName());
/* 214:    */       
/* 215:273 */       classGen.getParser().reportError(2, err);
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 220:    */   {
/* 221:282 */     translateTo(classGen, methodGen, Type.Reference);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 225:    */   {
/* 226:290 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 227:291 */     InstructionList il = methodGen.getInstructionList();
/* 228:292 */     il.append(new CHECKCAST(cpg.addClass("java.lang.Double")));
/* 229:293 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.Double", "doubleValue", "()D")));
/* 230:    */   }
/* 231:    */   
/* 232:    */   public Instruction ADD()
/* 233:    */   {
/* 234:299 */     return InstructionConstants.DADD;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Instruction SUB()
/* 238:    */   {
/* 239:303 */     return InstructionConstants.DSUB;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public Instruction MUL()
/* 243:    */   {
/* 244:307 */     return InstructionConstants.DMUL;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Instruction DIV()
/* 248:    */   {
/* 249:311 */     return InstructionConstants.DDIV;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public Instruction REM()
/* 253:    */   {
/* 254:315 */     return InstructionConstants.DREM;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Instruction NEG()
/* 258:    */   {
/* 259:319 */     return InstructionConstants.DNEG;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public Instruction LOAD(int slot)
/* 263:    */   {
/* 264:323 */     return new DLOAD(slot);
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Instruction STORE(int slot)
/* 268:    */   {
/* 269:327 */     return new DSTORE(slot);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Instruction POP()
/* 273:    */   {
/* 274:331 */     return InstructionConstants.POP2;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Instruction CMP(boolean less)
/* 278:    */   {
/* 279:335 */     return less ? InstructionConstants.DCMPG : InstructionConstants.DCMPL;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public Instruction DUP()
/* 283:    */   {
/* 284:339 */     return InstructionConstants.DUP2;
/* 285:    */   }
/* 286:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.RealType
 * JD-Core Version:    0.7.0.1
 */
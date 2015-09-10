/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.BranchInstruction;
/*   4:    */ import org.apache.bcel.generic.Instruction;
/*   5:    */ import org.apache.bcel.generic.InstructionConstants;
/*   6:    */ import org.apache.xalan.xsltc.compiler.Constants;
/*   7:    */ import org.apache.xalan.xsltc.compiler.FlowList;
/*   8:    */ import org.apache.xalan.xsltc.compiler.Parser;
/*   9:    */ 
/*  10:    */ public abstract class Type
/*  11:    */   implements Constants
/*  12:    */ {
/*  13: 36 */   public static final Type Int = new IntType();
/*  14: 37 */   public static final Type Real = new RealType();
/*  15: 38 */   public static final Type Boolean = new BooleanType();
/*  16: 39 */   public static final Type NodeSet = new NodeSetType();
/*  17: 40 */   public static final Type String = new StringType();
/*  18: 41 */   public static final Type ResultTree = new ResultTreeType();
/*  19: 42 */   public static final Type Reference = new ReferenceType();
/*  20: 43 */   public static final Type Void = new VoidType();
/*  21: 44 */   public static final Type Object = new ObjectType(Object.class);
/*  22: 46 */   public static final Type Node = new NodeType(-1);
/*  23: 47 */   public static final Type Root = new NodeType(9);
/*  24: 48 */   public static final Type Element = new NodeType(1);
/*  25: 49 */   public static final Type Attribute = new NodeType(2);
/*  26: 50 */   public static final Type Text = new NodeType(3);
/*  27: 51 */   public static final Type Comment = new NodeType(8);
/*  28: 52 */   public static final Type Processing_Instruction = new NodeType(7);
/*  29:    */   
/*  30:    */   public static Type newObjectType(String javaClassName)
/*  31:    */   {
/*  32: 59 */     if (javaClassName == "java.lang.Object") {
/*  33: 60 */       return Object;
/*  34:    */     }
/*  35: 62 */     if (javaClassName == "java.lang.String") {
/*  36: 63 */       return String;
/*  37:    */     }
/*  38: 66 */     return new ObjectType(javaClassName);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Type newObjectType(Class clazz)
/*  42:    */   {
/*  43: 75 */     if (clazz == Object.class) {
/*  44: 76 */       return Object;
/*  45:    */     }
/*  46: 78 */     if (clazz == String.class) {
/*  47: 79 */       return String;
/*  48:    */     }
/*  49: 82 */     return new ObjectType(clazz);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public abstract String toString();
/*  53:    */   
/*  54:    */   public abstract boolean identicalTo(Type paramType);
/*  55:    */   
/*  56:    */   public boolean isNumber()
/*  57:    */   {
/*  58:100 */     return false;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean implementedAsMethod()
/*  62:    */   {
/*  63:108 */     return false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isSimple()
/*  67:    */   {
/*  68:116 */     return false;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public abstract org.apache.bcel.generic.Type toJCType();
/*  72:    */   
/*  73:    */   public int distanceTo(Type type)
/*  74:    */   {
/*  75:127 */     return type == this ? 0 : 2147483647;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public abstract String toSignature();
/*  79:    */   
/*  80:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  81:    */   {
/*  82:142 */     ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/*  83:    */     
/*  84:144 */     classGen.getParser().reportError(2, err);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, Type type)
/*  88:    */   {
/*  89:156 */     FlowList fl = null;
/*  90:157 */     if (type == Boolean) {
/*  91:158 */       fl = translateToDesynthesized(classGen, methodGen, (BooleanType)type);
/*  92:    */     } else {
/*  93:162 */       translateTo(classGen, methodGen, type);
/*  94:    */     }
/*  95:164 */     return fl;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type)
/*  99:    */   {
/* 100:175 */     ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), type.toString());
/* 101:    */     
/* 102:177 */     classGen.getParser().reportError(2, err);
/* 103:178 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 107:    */   {
/* 108:188 */     ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), clazz.getClass().toString());
/* 109:    */     
/* 110:190 */     classGen.getParser().reportError(2, err);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz)
/* 114:    */   {
/* 115:200 */     ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", clazz.getClass().toString(), toString());
/* 116:    */     
/* 117:202 */     classGen.getParser().reportError(2, err);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 121:    */   {
/* 122:210 */     ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", toString(), "[" + toString() + "]");
/* 123:    */     
/* 124:212 */     classGen.getParser().reportError(2, err);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen)
/* 128:    */   {
/* 129:220 */     ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", "[" + toString() + "]", toString());
/* 130:    */     
/* 131:222 */     classGen.getParser().reportError(2, err);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getClassName()
/* 135:    */   {
/* 136:229 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Instruction ADD()
/* 140:    */   {
/* 141:233 */     return null;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Instruction SUB()
/* 145:    */   {
/* 146:237 */     return null;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Instruction MUL()
/* 150:    */   {
/* 151:241 */     return null;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Instruction DIV()
/* 155:    */   {
/* 156:245 */     return null;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Instruction REM()
/* 160:    */   {
/* 161:249 */     return null;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Instruction NEG()
/* 165:    */   {
/* 166:253 */     return null;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Instruction LOAD(int slot)
/* 170:    */   {
/* 171:257 */     return null;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Instruction STORE(int slot)
/* 175:    */   {
/* 176:261 */     return null;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Instruction POP()
/* 180:    */   {
/* 181:265 */     return InstructionConstants.POP;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public BranchInstruction GT(boolean tozero)
/* 185:    */   {
/* 186:269 */     return null;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public BranchInstruction GE(boolean tozero)
/* 190:    */   {
/* 191:273 */     return null;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public BranchInstruction LT(boolean tozero)
/* 195:    */   {
/* 196:277 */     return null;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public BranchInstruction LE(boolean tozero)
/* 200:    */   {
/* 201:281 */     return null;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public Instruction CMP(boolean less)
/* 205:    */   {
/* 206:285 */     return null;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Instruction DUP()
/* 210:    */   {
/* 211:289 */     return InstructionConstants.DUP;
/* 212:    */   }
/* 213:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.Type
 * JD-Core Version:    0.7.0.1
 */
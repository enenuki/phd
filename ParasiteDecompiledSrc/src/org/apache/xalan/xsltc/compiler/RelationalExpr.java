/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.BranchInstruction;
/*   5:    */ import org.apache.bcel.generic.ClassGen;
/*   6:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   7:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*   8:    */ import org.apache.bcel.generic.InstructionList;
/*   9:    */ import org.apache.bcel.generic.MethodGen;
/*  10:    */ import org.apache.bcel.generic.PUSH;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.BooleanType;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.IntType;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.MethodType;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.NodeType;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.RealType;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.ResultTreeType;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  23:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  24:    */ import org.apache.xalan.xsltc.runtime.Operators;
/*  25:    */ 
/*  26:    */ final class RelationalExpr
/*  27:    */   extends Expression
/*  28:    */ {
/*  29:    */   private int _op;
/*  30:    */   private Expression _left;
/*  31:    */   private Expression _right;
/*  32:    */   
/*  33:    */   public RelationalExpr(int op, Expression left, Expression right)
/*  34:    */   {
/*  35: 54 */     this._op = op;
/*  36: 55 */     (this._left = left).setParent(this);
/*  37: 56 */     (this._right = right).setParent(this);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setParser(Parser parser)
/*  41:    */   {
/*  42: 60 */     super.setParser(parser);
/*  43: 61 */     this._left.setParser(parser);
/*  44: 62 */     this._right.setParser(parser);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean hasPositionCall()
/*  48:    */   {
/*  49: 70 */     if (this._left.hasPositionCall()) {
/*  50: 70 */       return true;
/*  51:    */     }
/*  52: 71 */     if (this._right.hasPositionCall()) {
/*  53: 71 */       return true;
/*  54:    */     }
/*  55: 72 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean hasLastCall()
/*  59:    */   {
/*  60: 79 */     return (this._left.hasLastCall()) || (this._right.hasLastCall());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean hasReferenceArgs()
/*  64:    */   {
/*  65: 83 */     return ((this._left.getType() instanceof ReferenceType)) || ((this._right.getType() instanceof ReferenceType));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean hasNodeArgs()
/*  69:    */   {
/*  70: 88 */     return ((this._left.getType() instanceof NodeType)) || ((this._right.getType() instanceof NodeType));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean hasNodeSetArgs()
/*  74:    */   {
/*  75: 93 */     return ((this._left.getType() instanceof NodeSetType)) || ((this._right.getType() instanceof NodeSetType));
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Type typeCheck(SymbolTable stable)
/*  79:    */     throws TypeCheckError
/*  80:    */   {
/*  81: 98 */     Type tleft = this._left.typeCheck(stable);
/*  82: 99 */     Type tright = this._right.typeCheck(stable);
/*  83:102 */     if (((tleft instanceof ResultTreeType)) && ((tright instanceof ResultTreeType)))
/*  84:    */     {
/*  85:105 */       this._right = new CastExpr(this._right, Type.Real);
/*  86:106 */       this._left = new CastExpr(this._left, Type.Real);
/*  87:107 */       return this._type = Type.Boolean;
/*  88:    */     }
/*  89:111 */     if (hasReferenceArgs())
/*  90:    */     {
/*  91:112 */       Type type = null;
/*  92:113 */       Type typeL = null;
/*  93:114 */       Type typeR = null;
/*  94:115 */       if (((tleft instanceof ReferenceType)) && 
/*  95:116 */         ((this._left instanceof VariableRefBase)))
/*  96:    */       {
/*  97:117 */         VariableRefBase ref = (VariableRefBase)this._left;
/*  98:118 */         VariableBase var = ref.getVariable();
/*  99:119 */         typeL = var.getType();
/* 100:    */       }
/* 101:122 */       if (((tright instanceof ReferenceType)) && 
/* 102:123 */         ((this._right instanceof VariableRefBase)))
/* 103:    */       {
/* 104:124 */         VariableRefBase ref = (VariableRefBase)this._right;
/* 105:125 */         VariableBase var = ref.getVariable();
/* 106:126 */         typeR = var.getType();
/* 107:    */       }
/* 108:130 */       if (typeL == null) {
/* 109:131 */         type = typeR;
/* 110:132 */       } else if (typeR == null) {
/* 111:133 */         type = typeL;
/* 112:    */       } else {
/* 113:135 */         type = Type.Real;
/* 114:    */       }
/* 115:137 */       if (type == null) {
/* 116:137 */         type = Type.Real;
/* 117:    */       }
/* 118:139 */       this._right = new CastExpr(this._right, type);
/* 119:140 */       this._left = new CastExpr(this._left, type);
/* 120:141 */       return this._type = Type.Boolean;
/* 121:    */     }
/* 122:144 */     if (hasNodeSetArgs())
/* 123:    */     {
/* 124:146 */       if ((tright instanceof NodeSetType))
/* 125:    */       {
/* 126:147 */         Expression temp = this._right;this._right = this._left;this._left = temp;
/* 127:148 */         this._op = (this._op == 4 ? 5 : this._op == 3 ? 2 : this._op == 2 ? 3 : 4);
/* 128:    */         
/* 129:    */ 
/* 130:151 */         tright = this._right.getType();
/* 131:    */       }
/* 132:155 */       if ((tright instanceof NodeType)) {
/* 133:156 */         this._right = new CastExpr(this._right, Type.NodeSet);
/* 134:    */       }
/* 135:159 */       if ((tright instanceof IntType)) {
/* 136:160 */         this._right = new CastExpr(this._right, Type.Real);
/* 137:    */       }
/* 138:163 */       if ((tright instanceof ResultTreeType)) {
/* 139:164 */         this._right = new CastExpr(this._right, Type.String);
/* 140:    */       }
/* 141:166 */       return this._type = Type.Boolean;
/* 142:    */     }
/* 143:170 */     if (hasNodeArgs())
/* 144:    */     {
/* 145:171 */       if ((tleft instanceof BooleanType))
/* 146:    */       {
/* 147:172 */         this._right = new CastExpr(this._right, Type.Boolean);
/* 148:173 */         tright = Type.Boolean;
/* 149:    */       }
/* 150:175 */       if ((tright instanceof BooleanType))
/* 151:    */       {
/* 152:176 */         this._left = new CastExpr(this._left, Type.Boolean);
/* 153:177 */         tleft = Type.Boolean;
/* 154:    */       }
/* 155:    */     }
/* 156:182 */     MethodType ptype = lookupPrimop(stable, Operators.getOpNames(this._op), new MethodType(Type.Void, tleft, tright));
/* 157:185 */     if (ptype != null)
/* 158:    */     {
/* 159:186 */       Type arg1 = (Type)ptype.argsType().elementAt(0);
/* 160:187 */       if (!arg1.identicalTo(tleft)) {
/* 161:188 */         this._left = new CastExpr(this._left, arg1);
/* 162:    */       }
/* 163:190 */       Type arg2 = (Type)ptype.argsType().elementAt(1);
/* 164:191 */       if (!arg2.identicalTo(tright)) {
/* 165:192 */         this._right = new CastExpr(this._right, arg1);
/* 166:    */       }
/* 167:194 */       return this._type = ptype.resultType();
/* 168:    */     }
/* 169:196 */     throw new TypeCheckError(this);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 173:    */   {
/* 174:200 */     if ((hasNodeSetArgs()) || (hasReferenceArgs()))
/* 175:    */     {
/* 176:201 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 177:202 */       InstructionList il = methodGen.getInstructionList();
/* 178:    */       
/* 179:    */ 
/* 180:205 */       this._left.translate(classGen, methodGen);
/* 181:206 */       this._left.startIterator(classGen, methodGen);
/* 182:207 */       this._right.translate(classGen, methodGen);
/* 183:208 */       this._right.startIterator(classGen, methodGen);
/* 184:    */       
/* 185:210 */       il.append(new PUSH(cpg, this._op));
/* 186:211 */       il.append(methodGen.loadDOM());
/* 187:    */       
/* 188:213 */       int index = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "compare", "(" + this._left.getType().toSignature() + this._right.getType().toSignature() + "I" + "Lorg/apache/xalan/xsltc/DOM;" + ")Z");
/* 189:    */       
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:220 */       il.append(new INVOKESTATIC(index));
/* 196:    */     }
/* 197:    */     else
/* 198:    */     {
/* 199:223 */       translateDesynthesized(classGen, methodGen);
/* 200:224 */       synthesize(classGen, methodGen);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen)
/* 205:    */   {
/* 206:230 */     if ((hasNodeSetArgs()) || (hasReferenceArgs()))
/* 207:    */     {
/* 208:231 */       translate(classGen, methodGen);
/* 209:232 */       desynthesize(classGen, methodGen);
/* 210:    */     }
/* 211:    */     else
/* 212:    */     {
/* 213:235 */       BranchInstruction bi = null;
/* 214:236 */       InstructionList il = methodGen.getInstructionList();
/* 215:    */       
/* 216:238 */       this._left.translate(classGen, methodGen);
/* 217:239 */       this._right.translate(classGen, methodGen);
/* 218:    */       
/* 219:    */ 
/* 220:    */ 
/* 221:243 */       boolean tozero = false;
/* 222:244 */       Type tleft = this._left.getType();
/* 223:246 */       if ((tleft instanceof RealType))
/* 224:    */       {
/* 225:247 */         il.append(tleft.CMP((this._op == 3) || (this._op == 5)));
/* 226:248 */         tleft = Type.Int;
/* 227:249 */         tozero = true;
/* 228:    */       }
/* 229:252 */       switch (this._op)
/* 230:    */       {
/* 231:    */       case 3: 
/* 232:254 */         bi = tleft.GE(tozero);
/* 233:255 */         break;
/* 234:    */       case 2: 
/* 235:258 */         bi = tleft.LE(tozero);
/* 236:259 */         break;
/* 237:    */       case 5: 
/* 238:262 */         bi = tleft.GT(tozero);
/* 239:263 */         break;
/* 240:    */       case 4: 
/* 241:266 */         bi = tleft.LT(tozero);
/* 242:267 */         break;
/* 243:    */       default: 
/* 244:270 */         ErrorMsg msg = new ErrorMsg("ILLEGAL_RELAT_OP_ERR", this);
/* 245:271 */         getParser().reportError(2, msg);
/* 246:    */       }
/* 247:274 */       this._falseList.add(il.append(bi));
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String toString()
/* 252:    */   {
/* 253:279 */     return Operators.getOpNames(this._op) + '(' + this._left + ", " + this._right + ')';
/* 254:    */   }
/* 255:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.RelationalExpr
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.BranchHandle;
/*   4:    */ import org.apache.bcel.generic.ClassGen;
/*   5:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   6:    */ import org.apache.bcel.generic.GOTO;
/*   7:    */ import org.apache.bcel.generic.IFEQ;
/*   8:    */ import org.apache.bcel.generic.IFNE;
/*   9:    */ import org.apache.bcel.generic.IF_ICMPEQ;
/*  10:    */ import org.apache.bcel.generic.IF_ICMPNE;
/*  11:    */ import org.apache.bcel.generic.INVOKESTATIC;
/*  12:    */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  13:    */ import org.apache.bcel.generic.InstructionConstants;
/*  14:    */ import org.apache.bcel.generic.InstructionList;
/*  15:    */ import org.apache.bcel.generic.MethodGen;
/*  16:    */ import org.apache.bcel.generic.PUSH;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.BooleanType;
/*  18:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  19:    */ import org.apache.xalan.xsltc.compiler.util.IntType;
/*  20:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  21:    */ import org.apache.xalan.xsltc.compiler.util.NodeSetType;
/*  22:    */ import org.apache.xalan.xsltc.compiler.util.NodeType;
/*  23:    */ import org.apache.xalan.xsltc.compiler.util.NumberType;
/*  24:    */ import org.apache.xalan.xsltc.compiler.util.RealType;
/*  25:    */ import org.apache.xalan.xsltc.compiler.util.ReferenceType;
/*  26:    */ import org.apache.xalan.xsltc.compiler.util.ResultTreeType;
/*  27:    */ import org.apache.xalan.xsltc.compiler.util.StringType;
/*  28:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  29:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  30:    */ import org.apache.xalan.xsltc.runtime.Operators;
/*  31:    */ 
/*  32:    */ final class EqualityExpr
/*  33:    */   extends Expression
/*  34:    */ {
/*  35:    */   private final int _op;
/*  36:    */   private Expression _left;
/*  37:    */   private Expression _right;
/*  38:    */   
/*  39:    */   public EqualityExpr(int op, Expression left, Expression right)
/*  40:    */   {
/*  41: 64 */     this._op = op;
/*  42: 65 */     (this._left = left).setParent(this);
/*  43: 66 */     (this._right = right).setParent(this);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setParser(Parser parser)
/*  47:    */   {
/*  48: 70 */     super.setParser(parser);
/*  49: 71 */     this._left.setParser(parser);
/*  50: 72 */     this._right.setParser(parser);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String toString()
/*  54:    */   {
/*  55: 76 */     return Operators.getOpNames(this._op) + '(' + this._left + ", " + this._right + ')';
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Expression getLeft()
/*  59:    */   {
/*  60: 80 */     return this._left;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Expression getRight()
/*  64:    */   {
/*  65: 84 */     return this._right;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean getOp()
/*  69:    */   {
/*  70: 88 */     return this._op != 1;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean hasPositionCall()
/*  74:    */   {
/*  75: 96 */     if (this._left.hasPositionCall()) {
/*  76: 96 */       return true;
/*  77:    */     }
/*  78: 97 */     if (this._right.hasPositionCall()) {
/*  79: 97 */       return true;
/*  80:    */     }
/*  81: 98 */     return false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean hasLastCall()
/*  85:    */   {
/*  86:102 */     if (this._left.hasLastCall()) {
/*  87:102 */       return true;
/*  88:    */     }
/*  89:103 */     if (this._right.hasLastCall()) {
/*  90:103 */       return true;
/*  91:    */     }
/*  92:104 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   private void swapArguments()
/*  96:    */   {
/*  97:108 */     Expression temp = this._left;
/*  98:109 */     this._left = this._right;
/*  99:110 */     this._right = temp;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Type typeCheck(SymbolTable stable)
/* 103:    */     throws TypeCheckError
/* 104:    */   {
/* 105:117 */     Type tleft = this._left.typeCheck(stable);
/* 106:118 */     Type tright = this._right.typeCheck(stable);
/* 107:120 */     if ((tleft.isSimple()) && (tright.isSimple()))
/* 108:    */     {
/* 109:121 */       if (tleft != tright) {
/* 110:122 */         if ((tleft instanceof BooleanType))
/* 111:    */         {
/* 112:123 */           this._right = new CastExpr(this._right, Type.Boolean);
/* 113:    */         }
/* 114:125 */         else if ((tright instanceof BooleanType))
/* 115:    */         {
/* 116:126 */           this._left = new CastExpr(this._left, Type.Boolean);
/* 117:    */         }
/* 118:128 */         else if (((tleft instanceof NumberType)) || ((tright instanceof NumberType)))
/* 119:    */         {
/* 120:130 */           this._left = new CastExpr(this._left, Type.Real);
/* 121:131 */           this._right = new CastExpr(this._right, Type.Real);
/* 122:    */         }
/* 123:    */         else
/* 124:    */         {
/* 125:134 */           this._left = new CastExpr(this._left, Type.String);
/* 126:135 */           this._right = new CastExpr(this._right, Type.String);
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:139 */     else if ((tleft instanceof ReferenceType))
/* 131:    */     {
/* 132:140 */       this._right = new CastExpr(this._right, Type.Reference);
/* 133:    */     }
/* 134:142 */     else if ((tright instanceof ReferenceType))
/* 135:    */     {
/* 136:143 */       this._left = new CastExpr(this._left, Type.Reference);
/* 137:    */     }
/* 138:146 */     else if (((tleft instanceof NodeType)) && (tright == Type.String))
/* 139:    */     {
/* 140:147 */       this._left = new CastExpr(this._left, Type.String);
/* 141:    */     }
/* 142:149 */     else if ((tleft == Type.String) && ((tright instanceof NodeType)))
/* 143:    */     {
/* 144:150 */       this._right = new CastExpr(this._right, Type.String);
/* 145:    */     }
/* 146:153 */     else if (((tleft instanceof NodeType)) && ((tright instanceof NodeType)))
/* 147:    */     {
/* 148:154 */       this._left = new CastExpr(this._left, Type.String);
/* 149:155 */       this._right = new CastExpr(this._right, Type.String);
/* 150:    */     }
/* 151:157 */     else if ((!(tleft instanceof NodeType)) || (!(tright instanceof NodeSetType)))
/* 152:    */     {
/* 153:160 */       if (((tleft instanceof NodeSetType)) && ((tright instanceof NodeType)))
/* 154:    */       {
/* 155:161 */         swapArguments();
/* 156:    */       }
/* 157:    */       else
/* 158:    */       {
/* 159:167 */         if ((tleft instanceof NodeType)) {
/* 160:168 */           this._left = new CastExpr(this._left, Type.NodeSet);
/* 161:    */         }
/* 162:170 */         if ((tright instanceof NodeType)) {
/* 163:171 */           this._right = new CastExpr(this._right, Type.NodeSet);
/* 164:    */         }
/* 165:175 */         if ((tleft.isSimple()) || (((tleft instanceof ResultTreeType)) && ((tright instanceof NodeSetType)))) {
/* 166:178 */           swapArguments();
/* 167:    */         }
/* 168:182 */         if ((this._right.getType() instanceof IntType)) {
/* 169:183 */           this._right = new CastExpr(this._right, Type.Real);
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:186 */     return this._type = Type.Boolean;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen)
/* 177:    */   {
/* 178:191 */     Type tleft = this._left.getType();
/* 179:192 */     InstructionList il = methodGen.getInstructionList();
/* 180:194 */     if ((tleft instanceof BooleanType))
/* 181:    */     {
/* 182:195 */       this._left.translate(classGen, methodGen);
/* 183:196 */       this._right.translate(classGen, methodGen);
/* 184:197 */       this._falseList.add(il.append(this._op == 0 ? new IF_ICMPNE(null) : new IF_ICMPEQ(null)));
/* 185:    */     }
/* 186:201 */     else if ((tleft instanceof NumberType))
/* 187:    */     {
/* 188:202 */       this._left.translate(classGen, methodGen);
/* 189:203 */       this._right.translate(classGen, methodGen);
/* 190:205 */       if ((tleft instanceof RealType))
/* 191:    */       {
/* 192:206 */         il.append(InstructionConstants.DCMPG);
/* 193:207 */         this._falseList.add(il.append(this._op == 0 ? new IFNE(null) : new IFEQ(null)));
/* 194:    */       }
/* 195:    */       else
/* 196:    */       {
/* 197:212 */         this._falseList.add(il.append(this._op == 0 ? new IF_ICMPNE(null) : new IF_ICMPEQ(null)));
/* 198:    */       }
/* 199:    */     }
/* 200:    */     else
/* 201:    */     {
/* 202:218 */       translate(classGen, methodGen);
/* 203:219 */       desynthesize(classGen, methodGen);
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 208:    */   {
/* 209:224 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 210:225 */     InstructionList il = methodGen.getInstructionList();
/* 211:    */     
/* 212:227 */     Type tleft = this._left.getType();
/* 213:228 */     Type tright = this._right.getType();
/* 214:230 */     if (((tleft instanceof BooleanType)) || ((tleft instanceof NumberType)))
/* 215:    */     {
/* 216:231 */       translateDesynthesized(classGen, methodGen);
/* 217:232 */       synthesize(classGen, methodGen);
/* 218:233 */       return;
/* 219:    */     }
/* 220:236 */     if ((tleft instanceof StringType))
/* 221:    */     {
/* 222:237 */       int equals = cpg.addMethodref("java.lang.String", "equals", "(Ljava/lang/Object;)Z");
/* 223:    */       
/* 224:    */ 
/* 225:240 */       this._left.translate(classGen, methodGen);
/* 226:241 */       this._right.translate(classGen, methodGen);
/* 227:242 */       il.append(new INVOKEVIRTUAL(equals));
/* 228:244 */       if (this._op == 1)
/* 229:    */       {
/* 230:245 */         il.append(InstructionConstants.ICONST_1);
/* 231:246 */         il.append(InstructionConstants.IXOR);
/* 232:    */       }
/* 233:248 */       return;
/* 234:    */     }
/* 235:253 */     if ((tleft instanceof ResultTreeType))
/* 236:    */     {
/* 237:254 */       if ((tright instanceof BooleanType))
/* 238:    */       {
/* 239:255 */         this._right.translate(classGen, methodGen);
/* 240:256 */         if (this._op == 1)
/* 241:    */         {
/* 242:257 */           il.append(InstructionConstants.ICONST_1);
/* 243:258 */           il.append(InstructionConstants.IXOR);
/* 244:    */         }
/* 245:260 */         return;
/* 246:    */       }
/* 247:263 */       if ((tright instanceof RealType))
/* 248:    */       {
/* 249:264 */         this._left.translate(classGen, methodGen);
/* 250:265 */         tleft.translateTo(classGen, methodGen, Type.Real);
/* 251:266 */         this._right.translate(classGen, methodGen);
/* 252:    */         
/* 253:268 */         il.append(InstructionConstants.DCMPG);
/* 254:269 */         BranchHandle falsec = il.append(this._op == 0 ? new IFNE(null) : new IFEQ(null));
/* 255:    */         
/* 256:    */ 
/* 257:272 */         il.append(InstructionConstants.ICONST_1);
/* 258:273 */         BranchHandle truec = il.append(new GOTO(null));
/* 259:274 */         falsec.setTarget(il.append(InstructionConstants.ICONST_0));
/* 260:275 */         truec.setTarget(il.append(InstructionConstants.NOP));
/* 261:276 */         return;
/* 262:    */       }
/* 263:281 */       this._left.translate(classGen, methodGen);
/* 264:282 */       tleft.translateTo(classGen, methodGen, Type.String);
/* 265:283 */       this._right.translate(classGen, methodGen);
/* 266:285 */       if ((tright instanceof ResultTreeType)) {
/* 267:286 */         tright.translateTo(classGen, methodGen, Type.String);
/* 268:    */       }
/* 269:289 */       int equals = cpg.addMethodref("java.lang.String", "equals", "(Ljava/lang/Object;)Z");
/* 270:    */       
/* 271:    */ 
/* 272:292 */       il.append(new INVOKEVIRTUAL(equals));
/* 273:294 */       if (this._op == 1)
/* 274:    */       {
/* 275:295 */         il.append(InstructionConstants.ICONST_1);
/* 276:296 */         il.append(InstructionConstants.IXOR);
/* 277:    */       }
/* 278:298 */       return;
/* 279:    */     }
/* 280:301 */     if (((tleft instanceof NodeSetType)) && ((tright instanceof BooleanType)))
/* 281:    */     {
/* 282:302 */       this._left.translate(classGen, methodGen);
/* 283:303 */       this._left.startIterator(classGen, methodGen);
/* 284:304 */       Type.NodeSet.translateTo(classGen, methodGen, Type.Boolean);
/* 285:305 */       this._right.translate(classGen, methodGen);
/* 286:    */       
/* 287:307 */       il.append(InstructionConstants.IXOR);
/* 288:308 */       if (this._op == 0)
/* 289:    */       {
/* 290:309 */         il.append(InstructionConstants.ICONST_1);
/* 291:310 */         il.append(InstructionConstants.IXOR);
/* 292:    */       }
/* 293:312 */       return;
/* 294:    */     }
/* 295:315 */     if (((tleft instanceof NodeSetType)) && ((tright instanceof StringType)))
/* 296:    */     {
/* 297:316 */       this._left.translate(classGen, methodGen);
/* 298:317 */       this._left.startIterator(classGen, methodGen);
/* 299:318 */       this._right.translate(classGen, methodGen);
/* 300:319 */       il.append(new PUSH(cpg, this._op));
/* 301:320 */       il.append(methodGen.loadDOM());
/* 302:321 */       int cmp = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "compare", "(" + tleft.toSignature() + tright.toSignature() + "I" + "Lorg/apache/xalan/xsltc/DOM;" + ")Z");
/* 303:    */       
/* 304:    */ 
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:329 */       il.append(new INVOKESTATIC(cmp));
/* 311:330 */       return;
/* 312:    */     }
/* 313:334 */     this._left.translate(classGen, methodGen);
/* 314:335 */     this._left.startIterator(classGen, methodGen);
/* 315:336 */     this._right.translate(classGen, methodGen);
/* 316:337 */     this._right.startIterator(classGen, methodGen);
/* 317:340 */     if ((tright instanceof ResultTreeType))
/* 318:    */     {
/* 319:341 */       tright.translateTo(classGen, methodGen, Type.String);
/* 320:342 */       tright = Type.String;
/* 321:    */     }
/* 322:346 */     il.append(new PUSH(cpg, this._op));
/* 323:347 */     il.append(methodGen.loadDOM());
/* 324:    */     
/* 325:349 */     int compare = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "compare", "(" + tleft.toSignature() + tright.toSignature() + "I" + "Lorg/apache/xalan/xsltc/DOM;" + ")Z");
/* 326:    */     
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:357 */     il.append(new INVOKESTATIC(compare));
/* 334:    */   }
/* 335:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.EqualityExpr
 * JD-Core Version:    0.7.0.1
 */
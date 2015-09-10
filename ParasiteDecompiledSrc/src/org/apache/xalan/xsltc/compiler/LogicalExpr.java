/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.GOTO;
/*   5:    */ import org.apache.bcel.generic.InstructionConstants;
/*   6:    */ import org.apache.bcel.generic.InstructionHandle;
/*   7:    */ import org.apache.bcel.generic.InstructionList;
/*   8:    */ import org.apache.bcel.generic.MethodGen;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.MethodType;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  14:    */ 
/*  15:    */ final class LogicalExpr
/*  16:    */   extends Expression
/*  17:    */ {
/*  18:    */   public static final int OR = 0;
/*  19:    */   public static final int AND = 1;
/*  20:    */   private final int _op;
/*  21:    */   private Expression _left;
/*  22:    */   private Expression _right;
/*  23: 47 */   private static final String[] Ops = { "or", "and" };
/*  24:    */   
/*  25:    */   public LogicalExpr(int op, Expression left, Expression right)
/*  26:    */   {
/*  27: 56 */     this._op = op;
/*  28: 57 */     (this._left = left).setParent(this);
/*  29: 58 */     (this._right = right).setParent(this);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean hasPositionCall()
/*  33:    */   {
/*  34: 66 */     return (this._left.hasPositionCall()) || (this._right.hasPositionCall());
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean hasLastCall()
/*  38:    */   {
/*  39: 73 */     return (this._left.hasLastCall()) || (this._right.hasLastCall());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object evaluateAtCompileTime()
/*  43:    */   {
/*  44: 82 */     Object leftb = this._left.evaluateAtCompileTime();
/*  45: 83 */     Object rightb = this._right.evaluateAtCompileTime();
/*  46: 86 */     if ((leftb == null) || (rightb == null)) {
/*  47: 87 */       return null;
/*  48:    */     }
/*  49: 90 */     if (this._op == 1) {
/*  50: 91 */       return (leftb == Boolean.TRUE) && (rightb == Boolean.TRUE) ? Boolean.TRUE : Boolean.FALSE;
/*  51:    */     }
/*  52: 95 */     return (leftb == Boolean.TRUE) || (rightb == Boolean.TRUE) ? Boolean.TRUE : Boolean.FALSE;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getOp()
/*  56:    */   {
/*  57:105 */     return this._op;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setParser(Parser parser)
/*  61:    */   {
/*  62:113 */     super.setParser(parser);
/*  63:114 */     this._left.setParser(parser);
/*  64:115 */     this._right.setParser(parser);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String toString()
/*  68:    */   {
/*  69:122 */     return Ops[this._op] + '(' + this._left + ", " + this._right + ')';
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Type typeCheck(SymbolTable stable)
/*  73:    */     throws TypeCheckError
/*  74:    */   {
/*  75:130 */     Type tleft = this._left.typeCheck(stable);
/*  76:131 */     Type tright = this._right.typeCheck(stable);
/*  77:    */     
/*  78:    */ 
/*  79:134 */     MethodType wantType = new MethodType(Type.Void, tleft, tright);
/*  80:135 */     MethodType haveType = lookupPrimop(stable, Ops[this._op], wantType);
/*  81:138 */     if (haveType != null)
/*  82:    */     {
/*  83:140 */       Type arg1 = (Type)haveType.argsType().elementAt(0);
/*  84:141 */       if (!arg1.identicalTo(tleft)) {
/*  85:142 */         this._left = new CastExpr(this._left, arg1);
/*  86:    */       }
/*  87:144 */       Type arg2 = (Type)haveType.argsType().elementAt(1);
/*  88:145 */       if (!arg2.identicalTo(tright)) {
/*  89:146 */         this._right = new CastExpr(this._right, arg1);
/*  90:    */       }
/*  91:148 */       return this._type = haveType.resultType();
/*  92:    */     }
/*  93:150 */     throw new TypeCheckError(this);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  97:    */   {
/*  98:157 */     translateDesynthesized(classGen, methodGen);
/*  99:158 */     synthesize(classGen, methodGen);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen)
/* 103:    */   {
/* 104:167 */     InstructionList il = methodGen.getInstructionList();
/* 105:168 */     SyntaxTreeNode parent = getParent();
/* 106:171 */     if (this._op == 1)
/* 107:    */     {
/* 108:174 */       this._left.translateDesynthesized(classGen, methodGen);
/* 109:    */       
/* 110:    */ 
/* 111:177 */       InstructionHandle middle = il.append(InstructionConstants.NOP);
/* 112:    */       
/* 113:    */ 
/* 114:180 */       this._right.translateDesynthesized(classGen, methodGen);
/* 115:    */       
/* 116:    */ 
/* 117:183 */       InstructionHandle after = il.append(InstructionConstants.NOP);
/* 118:    */       
/* 119:    */ 
/* 120:186 */       this._falseList.append(this._right._falseList.append(this._left._falseList));
/* 121:190 */       if (((this._left instanceof LogicalExpr)) && (((LogicalExpr)this._left).getOp() == 0)) {
/* 122:192 */         this._left.backPatchTrueList(middle);
/* 123:194 */       } else if ((this._left instanceof NotCall)) {
/* 124:195 */         this._left.backPatchTrueList(middle);
/* 125:    */       } else {
/* 126:198 */         this._trueList.append(this._left._trueList);
/* 127:    */       }
/* 128:203 */       if (((this._right instanceof LogicalExpr)) && (((LogicalExpr)this._right).getOp() == 0)) {
/* 129:205 */         this._right.backPatchTrueList(after);
/* 130:207 */       } else if ((this._right instanceof NotCall)) {
/* 131:208 */         this._right.backPatchTrueList(after);
/* 132:    */       } else {
/* 133:211 */         this._trueList.append(this._right._trueList);
/* 134:    */       }
/* 135:    */     }
/* 136:    */     else
/* 137:    */     {
/* 138:217 */       this._left.translateDesynthesized(classGen, methodGen);
/* 139:    */       
/* 140:    */ 
/* 141:    */ 
/* 142:221 */       InstructionHandle ih = il.append(new GOTO(null));
/* 143:    */       
/* 144:    */ 
/* 145:224 */       this._right.translateDesynthesized(classGen, methodGen);
/* 146:    */       
/* 147:226 */       this._left._trueList.backPatch(ih);
/* 148:227 */       this._left._falseList.backPatch(ih.getNext());
/* 149:    */       
/* 150:229 */       this._falseList.append(this._right._falseList);
/* 151:230 */       this._trueList.add(ih).append(this._right._trueList);
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.LogicalExpr
 * JD-Core Version:    0.7.0.1
 */
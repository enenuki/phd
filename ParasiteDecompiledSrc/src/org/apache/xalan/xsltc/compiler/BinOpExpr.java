/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.bcel.generic.InstructionList;
/*   5:    */ import org.apache.bcel.generic.MethodGen;
/*   6:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*   7:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   8:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.MethodType;
/*  10:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  11:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  12:    */ 
/*  13:    */ final class BinOpExpr
/*  14:    */   extends Expression
/*  15:    */ {
/*  16:    */   public static final int PLUS = 0;
/*  17:    */   public static final int MINUS = 1;
/*  18:    */   public static final int TIMES = 2;
/*  19:    */   public static final int DIV = 3;
/*  20:    */   public static final int MOD = 4;
/*  21: 43 */   private static final String[] Ops = { "+", "-", "*", "/", "%" };
/*  22:    */   private int _op;
/*  23:    */   private Expression _left;
/*  24:    */   private Expression _right;
/*  25:    */   
/*  26:    */   public BinOpExpr(int op, Expression left, Expression right)
/*  27:    */   {
/*  28: 51 */     this._op = op;
/*  29: 52 */     (this._left = left).setParent(this);
/*  30: 53 */     (this._right = right).setParent(this);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean hasPositionCall()
/*  34:    */   {
/*  35: 61 */     if (this._left.hasPositionCall()) {
/*  36: 61 */       return true;
/*  37:    */     }
/*  38: 62 */     if (this._right.hasPositionCall()) {
/*  39: 62 */       return true;
/*  40:    */     }
/*  41: 63 */     return false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean hasLastCall()
/*  45:    */   {
/*  46: 70 */     return (this._left.hasLastCall()) || (this._right.hasLastCall());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setParser(Parser parser)
/*  50:    */   {
/*  51: 74 */     super.setParser(parser);
/*  52: 75 */     this._left.setParser(parser);
/*  53: 76 */     this._right.setParser(parser);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Type typeCheck(SymbolTable stable)
/*  57:    */     throws TypeCheckError
/*  58:    */   {
/*  59: 80 */     Type tleft = this._left.typeCheck(stable);
/*  60: 81 */     Type tright = this._right.typeCheck(stable);
/*  61: 82 */     MethodType ptype = lookupPrimop(stable, Ops[this._op], new MethodType(Type.Void, tleft, tright));
/*  62: 85 */     if (ptype != null)
/*  63:    */     {
/*  64: 86 */       Type arg1 = (Type)ptype.argsType().elementAt(0);
/*  65: 87 */       if (!arg1.identicalTo(tleft)) {
/*  66: 88 */         this._left = new CastExpr(this._left, arg1);
/*  67:    */       }
/*  68: 90 */       Type arg2 = (Type)ptype.argsType().elementAt(1);
/*  69: 91 */       if (!arg2.identicalTo(tright)) {
/*  70: 92 */         this._right = new CastExpr(this._right, arg1);
/*  71:    */       }
/*  72: 94 */       return this._type = ptype.resultType();
/*  73:    */     }
/*  74: 96 */     throw new TypeCheckError(this);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/*  78:    */   {
/*  79:100 */     InstructionList il = methodGen.getInstructionList();
/*  80:    */     
/*  81:102 */     this._left.translate(classGen, methodGen);
/*  82:103 */     this._right.translate(classGen, methodGen);
/*  83:105 */     switch (this._op)
/*  84:    */     {
/*  85:    */     case 0: 
/*  86:107 */       il.append(this._type.ADD());
/*  87:108 */       break;
/*  88:    */     case 1: 
/*  89:110 */       il.append(this._type.SUB());
/*  90:111 */       break;
/*  91:    */     case 2: 
/*  92:113 */       il.append(this._type.MUL());
/*  93:114 */       break;
/*  94:    */     case 3: 
/*  95:116 */       il.append(this._type.DIV());
/*  96:117 */       break;
/*  97:    */     case 4: 
/*  98:119 */       il.append(this._type.REM());
/*  99:120 */       break;
/* 100:    */     default: 
/* 101:122 */       ErrorMsg msg = new ErrorMsg("ILLEGAL_BINARY_OP_ERR", this);
/* 102:123 */       getParser().reportError(3, msg);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String toString()
/* 107:    */   {
/* 108:128 */     return Ops[this._op] + '(' + this._left + ", " + this._right + ')';
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.BinOpExpr
 * JD-Core Version:    0.7.0.1
 */
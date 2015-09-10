/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ClassGen;
/*  4:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  5:   */ import org.apache.bcel.generic.InstructionList;
/*  6:   */ import org.apache.bcel.generic.MethodGen;
/*  7:   */ import org.apache.bcel.generic.PUSH;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 12:   */ 
/* 13:   */ final class IntExpr
/* 14:   */   extends Expression
/* 15:   */ {
/* 16:   */   private final int _value;
/* 17:   */   
/* 18:   */   public IntExpr(int value)
/* 19:   */   {
/* 20:40 */     this._value = value;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Type typeCheck(SymbolTable stable)
/* 24:   */     throws TypeCheckError
/* 25:   */   {
/* 26:44 */     return this._type = Type.Int;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String toString()
/* 30:   */   {
/* 31:48 */     return "int-expr(" + this._value + ')';
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 35:   */   {
/* 36:52 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 37:53 */     InstructionList il = methodGen.getInstructionList();
/* 38:54 */     il.append(new PUSH(cpg, this._value));
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.IntExpr
 * JD-Core Version:    0.7.0.1
 */
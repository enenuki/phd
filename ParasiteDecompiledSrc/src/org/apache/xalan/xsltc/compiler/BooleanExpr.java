/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ClassGen;
/*  4:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  5:   */ import org.apache.bcel.generic.GOTO;
/*  6:   */ import org.apache.bcel.generic.InstructionConstants;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.MethodGen;
/*  9:   */ import org.apache.bcel.generic.PUSH;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 12:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 13:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 14:   */ 
/* 15:   */ final class BooleanExpr
/* 16:   */   extends Expression
/* 17:   */ {
/* 18:   */   private boolean _value;
/* 19:   */   
/* 20:   */   public BooleanExpr(boolean value)
/* 21:   */   {
/* 22:43 */     this._value = value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Type typeCheck(SymbolTable stable)
/* 26:   */     throws TypeCheckError
/* 27:   */   {
/* 28:47 */     this._type = Type.Boolean;
/* 29:48 */     return this._type;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String toString()
/* 33:   */   {
/* 34:52 */     return this._value ? "true()" : "false()";
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean getValue()
/* 38:   */   {
/* 39:56 */     return this._value;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean contextDependent()
/* 43:   */   {
/* 44:60 */     return false;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 48:   */   {
/* 49:64 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 50:65 */     InstructionList il = methodGen.getInstructionList();
/* 51:66 */     il.append(new PUSH(cpg, this._value));
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen)
/* 55:   */   {
/* 56:71 */     InstructionList il = methodGen.getInstructionList();
/* 57:72 */     if (this._value) {
/* 58:73 */       il.append(InstructionConstants.NOP);
/* 59:   */     } else {
/* 60:76 */       this._falseList.add(il.append(new GOTO(null)));
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.BooleanExpr
 * JD-Core Version:    0.7.0.1
 */
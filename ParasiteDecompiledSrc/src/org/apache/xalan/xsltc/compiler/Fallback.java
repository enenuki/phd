/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ClassGen;
/*  4:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  5:   */ import org.apache.bcel.generic.InstructionList;
/*  6:   */ import org.apache.bcel.generic.MethodGen;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 11:   */ 
/* 12:   */ final class Fallback
/* 13:   */   extends Instruction
/* 14:   */ {
/* 15:36 */   private boolean _active = false;
/* 16:   */   
/* 17:   */   public Type typeCheck(SymbolTable stable)
/* 18:   */     throws TypeCheckError
/* 19:   */   {
/* 20:42 */     if (this._active) {
/* 21:43 */       return typeCheckContents(stable);
/* 22:   */     }
/* 23:46 */     return Type.Void;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void activate()
/* 27:   */   {
/* 28:54 */     this._active = true;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String toString()
/* 32:   */   {
/* 33:58 */     return "fallback";
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void parseContents(Parser parser)
/* 37:   */   {
/* 38:66 */     if (this._active) {
/* 39:66 */       parseChildren(parser);
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 44:   */   {
/* 45:74 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 46:75 */     InstructionList il = methodGen.getInstructionList();
/* 47:77 */     if (this._active) {
/* 48:77 */       translateContents(classGen, methodGen);
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Fallback
 * JD-Core Version:    0.7.0.1
 */
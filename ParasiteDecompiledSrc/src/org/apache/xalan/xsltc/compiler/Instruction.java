/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  4:   */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  5:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  8:   */ 
/*  9:   */ abstract class Instruction
/* 10:   */   extends SyntaxTreeNode
/* 11:   */ {
/* 12:   */   public Type typeCheck(SymbolTable stable)
/* 13:   */     throws TypeCheckError
/* 14:   */   {
/* 15:39 */     return typeCheckContents(stable);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 19:   */   {
/* 20:46 */     ErrorMsg msg = new ErrorMsg("NOT_IMPLEMENTED_ERR", getClass(), this);
/* 21:   */     
/* 22:48 */     getParser().reportError(2, msg);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Instruction
 * JD-Core Version:    0.7.0.1
 */
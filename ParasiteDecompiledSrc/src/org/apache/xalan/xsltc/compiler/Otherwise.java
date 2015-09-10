/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  4:   */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  5:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  9:   */ 
/* 10:   */ final class Otherwise
/* 11:   */   extends Instruction
/* 12:   */ {
/* 13:   */   public void display(int indent)
/* 14:   */   {
/* 15:37 */     indent(indent);
/* 16:38 */     Util.println("Otherwise");
/* 17:39 */     indent(indent + 4);
/* 18:40 */     displayContents(indent + 4);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Type typeCheck(SymbolTable stable)
/* 22:   */     throws TypeCheckError
/* 23:   */   {
/* 24:44 */     typeCheckContents(stable);
/* 25:45 */     return Type.Void;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 29:   */   {
/* 30:49 */     Parser parser = getParser();
/* 31:50 */     ErrorMsg err = new ErrorMsg("STRAY_OTHERWISE_ERR", this);
/* 32:51 */     parser.reportError(3, err);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.Otherwise
 * JD-Core Version:    0.7.0.1
 */
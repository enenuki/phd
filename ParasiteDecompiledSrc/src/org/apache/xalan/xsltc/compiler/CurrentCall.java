/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.InstructionList;
/*  4:   */ import org.apache.bcel.generic.MethodGen;
/*  5:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  7:   */ 
/*  8:   */ final class CurrentCall
/*  9:   */   extends FunctionCall
/* 10:   */ {
/* 11:   */   public CurrentCall(QName fname)
/* 12:   */   {
/* 13:33 */     super(fname);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 17:   */   {
/* 18:37 */     methodGen.getInstructionList().append(methodGen.loadCurrentNode());
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.CurrentCall
 * JD-Core Version:    0.7.0.1
 */
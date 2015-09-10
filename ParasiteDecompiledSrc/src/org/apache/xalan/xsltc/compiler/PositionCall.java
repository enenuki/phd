/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import org.apache.bcel.generic.ClassGen;
/*  4:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  5:   */ import org.apache.bcel.generic.ILOAD;
/*  6:   */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.MethodGen;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.CompareGenerator;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 12:   */ import org.apache.xalan.xsltc.compiler.util.TestGenerator;
/* 13:   */ 
/* 14:   */ final class PositionCall
/* 15:   */   extends FunctionCall
/* 16:   */ {
/* 17:   */   public PositionCall(QName fname)
/* 18:   */   {
/* 19:41 */     super(fname);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean hasPositionCall()
/* 23:   */   {
/* 24:45 */     return true;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 28:   */   {
/* 29:49 */     InstructionList il = methodGen.getInstructionList();
/* 30:51 */     if ((methodGen instanceof CompareGenerator))
/* 31:   */     {
/* 32:52 */       il.append(((CompareGenerator)methodGen).loadCurrentNode());
/* 33:   */     }
/* 34:54 */     else if ((methodGen instanceof TestGenerator))
/* 35:   */     {
/* 36:55 */       il.append(new ILOAD(2));
/* 37:   */     }
/* 38:   */     else
/* 39:   */     {
/* 40:58 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 41:59 */       int index = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "getPosition", "()I");
/* 42:   */       
/* 43:   */ 
/* 44:   */ 
/* 45:63 */       il.append(methodGen.loadIterator());
/* 46:64 */       il.append(new INVOKEINTERFACE(index, 1));
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.PositionCall
 * JD-Core Version:    0.7.0.1
 */
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
/* 14:   */ final class LastCall
/* 15:   */   extends FunctionCall
/* 16:   */ {
/* 17:   */   public LastCall(QName fname)
/* 18:   */   {
/* 19:40 */     super(fname);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean hasPositionCall()
/* 23:   */   {
/* 24:44 */     return true;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean hasLastCall()
/* 28:   */   {
/* 29:48 */     return true;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 33:   */   {
/* 34:52 */     InstructionList il = methodGen.getInstructionList();
/* 35:54 */     if ((methodGen instanceof CompareGenerator))
/* 36:   */     {
/* 37:55 */       il.append(((CompareGenerator)methodGen).loadLastNode());
/* 38:   */     }
/* 39:57 */     else if ((methodGen instanceof TestGenerator))
/* 40:   */     {
/* 41:58 */       il.append(new ILOAD(3));
/* 42:   */     }
/* 43:   */     else
/* 44:   */     {
/* 45:61 */       ConstantPoolGen cpg = classGen.getConstantPool();
/* 46:62 */       int getLast = cpg.addInterfaceMethodref("org.apache.xml.dtm.DTMAxisIterator", "getLast", "()I");
/* 47:   */       
/* 48:   */ 
/* 49:65 */       il.append(methodGen.loadIterator());
/* 50:66 */       il.append(new INVOKEINTERFACE(getLast, 1));
/* 51:   */     }
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.LastCall
 * JD-Core Version:    0.7.0.1
 */
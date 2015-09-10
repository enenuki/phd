/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.INVOKEVIRTUAL;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.MethodGen;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 12:   */ 
/* 13:   */ final class StringLengthCall
/* 14:   */   extends FunctionCall
/* 15:   */ {
/* 16:   */   public StringLengthCall(QName fname, Vector arguments)
/* 17:   */   {
/* 18:39 */     super(fname, arguments);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 22:   */   {
/* 23:43 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 24:44 */     InstructionList il = methodGen.getInstructionList();
/* 25:45 */     if (argumentCount() > 0)
/* 26:   */     {
/* 27:46 */       argument().translate(classGen, methodGen);
/* 28:   */     }
/* 29:   */     else
/* 30:   */     {
/* 31:49 */       il.append(methodGen.loadContextNode());
/* 32:50 */       Type.Node.translateTo(classGen, methodGen, Type.String);
/* 33:   */     }
/* 34:52 */     il.append(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.String", "length", "()I")));
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.StringLengthCall
 * JD-Core Version:    0.7.0.1
 */
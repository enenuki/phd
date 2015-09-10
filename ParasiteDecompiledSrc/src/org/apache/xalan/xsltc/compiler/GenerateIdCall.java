/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.INVOKESTATIC;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.MethodGen;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 11:   */ 
/* 12:   */ final class GenerateIdCall
/* 13:   */   extends FunctionCall
/* 14:   */ {
/* 15:   */   public GenerateIdCall(QName fname, Vector arguments)
/* 16:   */   {
/* 17:38 */     super(fname, arguments);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 21:   */   {
/* 22:42 */     InstructionList il = methodGen.getInstructionList();
/* 23:43 */     if (argumentCount() == 0) {
/* 24:44 */       il.append(methodGen.loadContextNode());
/* 25:   */     } else {
/* 26:47 */       argument().translate(classGen, methodGen);
/* 27:   */     }
/* 28:49 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 29:50 */     il.append(new INVOKESTATIC(cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "generate_idF", "(I)Ljava/lang/String;")));
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.GenerateIdCall
 * JD-Core Version:    0.7.0.1
 */
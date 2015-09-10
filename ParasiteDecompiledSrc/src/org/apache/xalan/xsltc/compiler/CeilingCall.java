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
/* 12:   */ final class CeilingCall
/* 13:   */   extends FunctionCall
/* 14:   */ {
/* 15:   */   public CeilingCall(QName fname, Vector arguments)
/* 16:   */   {
/* 17:38 */     super(fname, arguments);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 21:   */   {
/* 22:42 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 23:43 */     InstructionList il = methodGen.getInstructionList();
/* 24:44 */     argument(0).translate(classGen, methodGen);
/* 25:45 */     il.append(new INVOKESTATIC(cpg.addMethodref("java.lang.Math", "ceil", "(D)D")));
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.CeilingCall
 * JD-Core Version:    0.7.0.1
 */
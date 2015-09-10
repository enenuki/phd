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
/* 12:   */ final class FloorCall
/* 13:   */   extends FunctionCall
/* 14:   */ {
/* 15:   */   public FloorCall(QName fname, Vector arguments)
/* 16:   */   {
/* 17:36 */     super(fname, arguments);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 21:   */   {
/* 22:40 */     argument().translate(classGen, methodGen);
/* 23:41 */     methodGen.getInstructionList().append(new INVOKESTATIC(classGen.getConstantPool().addMethodref("java.lang.Math", "floor", "(D)D")));
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.FloorCall
 * JD-Core Version:    0.7.0.1
 */
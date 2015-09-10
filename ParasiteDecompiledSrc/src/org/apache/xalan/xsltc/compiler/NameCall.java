/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.MethodGen;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 11:   */ 
/* 12:   */ final class NameCall
/* 13:   */   extends NameBase
/* 14:   */ {
/* 15:   */   public NameCall(QName fname)
/* 16:   */   {
/* 17:43 */     super(fname);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public NameCall(QName fname, Vector arguments)
/* 21:   */   {
/* 22:50 */     super(fname, arguments);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 26:   */   {
/* 27:57 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 28:58 */     InstructionList il = methodGen.getInstructionList();
/* 29:   */     
/* 30:60 */     int getName = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNodeNameX", "(I)Ljava/lang/String;");
/* 31:   */     
/* 32:   */ 
/* 33:63 */     super.translate(classGen, methodGen);
/* 34:64 */     il.append(new INVOKEINTERFACE(getName, 2));
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.NameCall
 * JD-Core Version:    0.7.0.1
 */
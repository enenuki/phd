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
/* 12:   */ final class NamespaceUriCall
/* 13:   */   extends NameBase
/* 14:   */ {
/* 15:   */   public NamespaceUriCall(QName fname)
/* 16:   */   {
/* 17:41 */     super(fname);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public NamespaceUriCall(QName fname, Vector arguments)
/* 21:   */   {
/* 22:48 */     super(fname, arguments);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 26:   */   {
/* 27:57 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 28:58 */     InstructionList il = methodGen.getInstructionList();
/* 29:   */     
/* 30:   */ 
/* 31:61 */     int getNamespace = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNamespaceName", "(I)Ljava/lang/String;");
/* 32:   */     
/* 33:   */ 
/* 34:64 */     super.translate(classGen, methodGen);
/* 35:65 */     il.append(new INVOKEINTERFACE(getNamespace, 2));
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.NamespaceUriCall
 * JD-Core Version:    0.7.0.1
 */
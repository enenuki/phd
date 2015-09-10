/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.ClassGen;
/*  5:   */ import org.apache.bcel.generic.ConstantPoolGen;
/*  6:   */ import org.apache.bcel.generic.INVOKEINTERFACE;
/*  7:   */ import org.apache.bcel.generic.INVOKESTATIC;
/*  8:   */ import org.apache.bcel.generic.InstructionList;
/*  9:   */ import org.apache.bcel.generic.MethodGen;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 11:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 12:   */ 
/* 13:   */ final class LocalNameCall
/* 14:   */   extends NameBase
/* 15:   */ {
/* 16:   */   public LocalNameCall(QName fname)
/* 17:   */   {
/* 18:42 */     super(fname);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public LocalNameCall(QName fname, Vector arguments)
/* 22:   */   {
/* 23:49 */     super(fname, arguments);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 27:   */   {
/* 28:58 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 29:59 */     InstructionList il = methodGen.getInstructionList();
/* 30:   */     
/* 31:   */ 
/* 32:62 */     int getNodeName = cpg.addInterfaceMethodref("org.apache.xalan.xsltc.DOM", "getNodeName", "(I)Ljava/lang/String;");
/* 33:   */     
/* 34:   */ 
/* 35:   */ 
/* 36:66 */     int getLocalName = cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "getLocalName", "(Ljava/lang/String;)Ljava/lang/String;");
/* 37:   */     
/* 38:   */ 
/* 39:   */ 
/* 40:70 */     super.translate(classGen, methodGen);
/* 41:71 */     il.append(new INVOKEINTERFACE(getNodeName, 2));
/* 42:72 */     il.append(new INVOKESTATIC(getLocalName));
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.LocalNameCall
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.BranchHandle;
/*  5:   */ import org.apache.bcel.generic.GOTO;
/*  6:   */ import org.apache.bcel.generic.InstructionConstants;
/*  7:   */ import org.apache.bcel.generic.InstructionList;
/*  8:   */ import org.apache.bcel.generic.MethodGen;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/* 11:   */ 
/* 12:   */ final class NotCall
/* 13:   */   extends FunctionCall
/* 14:   */ {
/* 15:   */   public NotCall(QName fname, Vector arguments)
/* 16:   */   {
/* 17:38 */     super(fname, arguments);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 21:   */   {
/* 22:42 */     InstructionList il = methodGen.getInstructionList();
/* 23:43 */     argument().translate(classGen, methodGen);
/* 24:44 */     il.append(InstructionConstants.ICONST_1);
/* 25:45 */     il.append(InstructionConstants.IXOR);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen)
/* 29:   */   {
/* 30:50 */     InstructionList il = methodGen.getInstructionList();
/* 31:51 */     Expression exp = argument();
/* 32:52 */     exp.translateDesynthesized(classGen, methodGen);
/* 33:53 */     BranchHandle gotoh = il.append(new GOTO(null));
/* 34:54 */     this._trueList = exp._falseList;
/* 35:55 */     this._falseList = exp._trueList;
/* 36:56 */     this._falseList.add(gotoh);
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.NotCall
 * JD-Core Version:    0.7.0.1
 */
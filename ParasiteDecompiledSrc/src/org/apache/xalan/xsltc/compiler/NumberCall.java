/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.InstructionList;
/*  5:   */ import org.apache.bcel.generic.MethodGen;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 10:   */ 
/* 11:   */ final class NumberCall
/* 12:   */   extends FunctionCall
/* 13:   */ {
/* 14:   */   public NumberCall(QName fname, Vector arguments)
/* 15:   */   {
/* 16:39 */     super(fname, arguments);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Type typeCheck(SymbolTable stable)
/* 20:   */     throws TypeCheckError
/* 21:   */   {
/* 22:43 */     if (argumentCount() > 0) {
/* 23:44 */       argument().typeCheck(stable);
/* 24:   */     }
/* 25:46 */     return this._type = Type.Real;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 29:   */   {
/* 30:50 */     InstructionList il = methodGen.getInstructionList();
/* 31:   */     Type targ;
/* 32:53 */     if (argumentCount() == 0)
/* 33:   */     {
/* 34:54 */       il.append(methodGen.loadContextNode());
/* 35:55 */       targ = Type.Node;
/* 36:   */     }
/* 37:   */     else
/* 38:   */     {
/* 39:58 */       Expression arg = argument();
/* 40:59 */       arg.translate(classGen, methodGen);
/* 41:60 */       arg.startIterator(classGen, methodGen);
/* 42:61 */       targ = arg.getType();
/* 43:   */     }
/* 44:64 */     if (!targ.identicalTo(Type.Real)) {
/* 45:65 */       targ.translateTo(classGen, methodGen, Type.Real);
/* 46:   */     }
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.NumberCall
 * JD-Core Version:    0.7.0.1
 */
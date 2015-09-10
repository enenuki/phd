/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.bcel.generic.InstructionList;
/*  5:   */ import org.apache.bcel.generic.MethodGen;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  8:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  9:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/* 10:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/* 11:   */ 
/* 12:   */ final class StringCall
/* 13:   */   extends FunctionCall
/* 14:   */ {
/* 15:   */   public StringCall(QName fname, Vector arguments)
/* 16:   */   {
/* 17:39 */     super(fname, arguments);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Type typeCheck(SymbolTable stable)
/* 21:   */     throws TypeCheckError
/* 22:   */   {
/* 23:43 */     int argc = argumentCount();
/* 24:44 */     if (argc > 1)
/* 25:   */     {
/* 26:45 */       ErrorMsg err = new ErrorMsg("ILLEGAL_ARG_ERR", this);
/* 27:46 */       throw new TypeCheckError(err);
/* 28:   */     }
/* 29:49 */     if (argc > 0) {
/* 30:50 */       argument().typeCheck(stable);
/* 31:   */     }
/* 32:52 */     return this._type = Type.String;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 36:   */   {
/* 37:56 */     InstructionList il = methodGen.getInstructionList();
/* 38:   */     Type targ;
/* 39:59 */     if (argumentCount() == 0)
/* 40:   */     {
/* 41:60 */       il.append(methodGen.loadContextNode());
/* 42:61 */       targ = Type.Node;
/* 43:   */     }
/* 44:   */     else
/* 45:   */     {
/* 46:64 */       Expression arg = argument();
/* 47:65 */       arg.translate(classGen, methodGen);
/* 48:66 */       arg.startIterator(classGen, methodGen);
/* 49:67 */       targ = arg.getType();
/* 50:   */     }
/* 51:70 */     if (!targ.identicalTo(Type.String)) {
/* 52:71 */       targ.translateTo(classGen, methodGen, Type.String);
/* 53:   */     }
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.StringCall
 * JD-Core Version:    0.7.0.1
 */
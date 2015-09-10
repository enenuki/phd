/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  5:   */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  6:   */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  7:   */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  8:   */ 
/*  9:   */ final class BooleanCall
/* 10:   */   extends FunctionCall
/* 11:   */ {
/* 12:37 */   private Expression _arg = null;
/* 13:   */   
/* 14:   */   public BooleanCall(QName fname, Vector arguments)
/* 15:   */   {
/* 16:40 */     super(fname, arguments);
/* 17:41 */     this._arg = argument(0);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Type typeCheck(SymbolTable stable)
/* 21:   */     throws TypeCheckError
/* 22:   */   {
/* 23:45 */     this._arg.typeCheck(stable);
/* 24:46 */     return this._type = Type.Boolean;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 28:   */   {
/* 29:50 */     this._arg.translate(classGen, methodGen);
/* 30:51 */     Type targ = this._arg.getType();
/* 31:52 */     if (!targ.identicalTo(Type.Boolean))
/* 32:   */     {
/* 33:53 */       this._arg.startIterator(classGen, methodGen);
/* 34:54 */       targ.translateTo(classGen, methodGen, Type.Boolean);
/* 35:   */     }
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.BooleanCall
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ final class ArgumentList
/*  4:   */ {
/*  5:   */   private final Expression _arg;
/*  6:   */   private final ArgumentList _rest;
/*  7:   */   
/*  8:   */   public ArgumentList(Expression arg, ArgumentList rest)
/*  9:   */   {
/* 10:33 */     this._arg = arg;
/* 11:34 */     this._rest = rest;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String toString()
/* 15:   */   {
/* 16:38 */     return this._arg.toString() + ", " + this._rest.toString();
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.ArgumentList
 * JD-Core Version:    0.7.0.1
 */
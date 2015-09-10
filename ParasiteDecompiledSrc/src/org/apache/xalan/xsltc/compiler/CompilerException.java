/*  1:   */ package org.apache.xalan.xsltc.compiler;
/*  2:   */ 
/*  3:   */ public final class CompilerException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   static final long serialVersionUID = 1732939618562742663L;
/*  7:   */   private String _msg;
/*  8:   */   
/*  9:   */   public CompilerException() {}
/* 10:   */   
/* 11:   */   public CompilerException(Exception e)
/* 12:   */   {
/* 13:37 */     super(e.toString());
/* 14:38 */     this._msg = e.toString();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public CompilerException(String message)
/* 18:   */   {
/* 19:42 */     super(message);
/* 20:43 */     this._msg = message;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getMessage()
/* 24:   */   {
/* 25:47 */     int col = this._msg.indexOf(':');
/* 26:49 */     if (col > -1) {
/* 27:50 */       return this._msg.substring(col);
/* 28:   */     }
/* 29:52 */     return this._msg;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.CompilerException
 * JD-Core Version:    0.7.0.1
 */
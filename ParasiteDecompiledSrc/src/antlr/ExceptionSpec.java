/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.impl.Vector;
/*  4:   */ 
/*  5:   */ class ExceptionSpec
/*  6:   */ {
/*  7:   */   protected Token label;
/*  8:   */   protected Vector handlers;
/*  9:   */   
/* 10:   */   public ExceptionSpec(Token paramToken)
/* 11:   */   {
/* 12:22 */     this.label = paramToken;
/* 13:23 */     this.handlers = new Vector();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void addHandler(ExceptionHandler paramExceptionHandler)
/* 17:   */   {
/* 18:27 */     this.handlers.appendElement(paramExceptionHandler);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ExceptionSpec
 * JD-Core Version:    0.7.0.1
 */
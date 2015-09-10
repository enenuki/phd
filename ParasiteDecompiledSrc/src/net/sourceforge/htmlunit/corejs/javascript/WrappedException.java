/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ public class WrappedException
/*  4:   */   extends EvaluatorException
/*  5:   */ {
/*  6:   */   static final long serialVersionUID = -1551979216966520648L;
/*  7:   */   private Throwable exception;
/*  8:   */   
/*  9:   */   public WrappedException(Throwable exception)
/* 10:   */   {
/* 11:58 */     super("Wrapped " + exception.toString());
/* 12:59 */     this.exception = exception;
/* 13:60 */     Kit.initCause(this, exception);
/* 14:   */     
/* 15:62 */     int[] linep = { 0 };
/* 16:63 */     String sourceName = Context.getSourcePositionFromStack(linep);
/* 17:64 */     int lineNumber = linep[0];
/* 18:65 */     if (sourceName != null) {
/* 19:66 */       initSourceName(sourceName);
/* 20:   */     }
/* 21:68 */     if (lineNumber != 0) {
/* 22:69 */       initLineNumber(lineNumber);
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Throwable getWrappedException()
/* 27:   */   {
/* 28:81 */     return this.exception;
/* 29:   */   }
/* 30:   */   
/* 31:   */   /**
/* 32:   */    * @deprecated
/* 33:   */    */
/* 34:   */   public Object unwrap()
/* 35:   */   {
/* 36:89 */     return getWrappedException();
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.WrappedException
 * JD-Core Version:    0.7.0.1
 */
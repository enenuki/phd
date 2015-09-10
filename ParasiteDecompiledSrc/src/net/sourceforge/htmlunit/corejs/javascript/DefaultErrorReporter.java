/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ class DefaultErrorReporter
/*   4:    */   implements ErrorReporter
/*   5:    */ {
/*   6: 48 */   static final DefaultErrorReporter instance = new DefaultErrorReporter();
/*   7:    */   private boolean forEval;
/*   8:    */   private ErrorReporter chainedReporter;
/*   9:    */   
/*  10:    */   static ErrorReporter forEval(ErrorReporter reporter)
/*  11:    */   {
/*  12: 57 */     DefaultErrorReporter r = new DefaultErrorReporter();
/*  13: 58 */     r.forEval = true;
/*  14: 59 */     r.chainedReporter = reporter;
/*  15: 60 */     return r;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void warning(String message, String sourceURI, int line, String lineText, int lineOffset)
/*  19:    */   {
/*  20: 66 */     if (this.chainedReporter != null) {
/*  21: 67 */       this.chainedReporter.warning(message, sourceURI, line, lineText, lineOffset);
/*  22:    */     }
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void error(String message, String sourceURI, int line, String lineText, int lineOffset)
/*  26:    */   {
/*  27: 77 */     if (this.forEval)
/*  28:    */     {
/*  29: 81 */       String error = "SyntaxError";
/*  30: 82 */       String TYPE_ERROR_NAME = "TypeError";
/*  31: 83 */       String DELIMETER = ": ";
/*  32: 84 */       String prefix = "TypeError: ";
/*  33: 85 */       if (message.startsWith("TypeError: "))
/*  34:    */       {
/*  35: 86 */         error = "TypeError";
/*  36: 87 */         message = message.substring("TypeError: ".length());
/*  37:    */       }
/*  38: 89 */       throw ScriptRuntime.constructError(error, message, sourceURI, line, lineText, lineOffset);
/*  39:    */     }
/*  40: 92 */     if (this.chainedReporter != null) {
/*  41: 93 */       this.chainedReporter.error(message, sourceURI, line, lineText, lineOffset);
/*  42:    */     } else {
/*  43: 96 */       throw runtimeError(message, sourceURI, line, lineText, lineOffset);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public EvaluatorException runtimeError(String message, String sourceURI, int line, String lineText, int lineOffset)
/*  48:    */   {
/*  49:105 */     if (this.chainedReporter != null) {
/*  50:106 */       return this.chainedReporter.runtimeError(message, sourceURI, line, lineText, lineOffset);
/*  51:    */     }
/*  52:109 */     return new EvaluatorException(message, sourceURI, line, lineText, lineOffset);
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.DefaultErrorReporter
 * JD-Core Version:    0.7.0.1
 */
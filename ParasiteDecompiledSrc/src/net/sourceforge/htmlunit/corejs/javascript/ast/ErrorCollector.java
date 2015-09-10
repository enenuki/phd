/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.EvaluatorException;
/*   6:    */ 
/*   7:    */ public class ErrorCollector
/*   8:    */   implements IdeErrorReporter
/*   9:    */ {
/*  10: 56 */   private List<ParseProblem> errors = new ArrayList();
/*  11:    */   
/*  12:    */   public void warning(String message, String sourceName, int line, String lineSource, int lineOffset)
/*  13:    */   {
/*  14: 65 */     throw new UnsupportedOperationException();
/*  15:    */   }
/*  16:    */   
/*  17:    */   public void warning(String message, String sourceName, int offset, int length)
/*  18:    */   {
/*  19: 73 */     this.errors.add(new ParseProblem(ParseProblem.Type.Warning, message, sourceName, offset, length));
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void error(String message, String sourceName, int line, String lineSource, int lineOffset)
/*  23:    */   {
/*  24: 86 */     throw new UnsupportedOperationException();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void error(String message, String sourceName, int fileOffset, int length)
/*  28:    */   {
/*  29: 95 */     this.errors.add(new ParseProblem(ParseProblem.Type.Error, message, sourceName, fileOffset, length));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset)
/*  33:    */   {
/*  34:107 */     throw new UnsupportedOperationException();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public List<ParseProblem> getErrors()
/*  38:    */   {
/*  39:114 */     return this.errors;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String toString()
/*  43:    */   {
/*  44:119 */     StringBuilder sb = new StringBuilder(this.errors.size() * 100);
/*  45:120 */     for (ParseProblem pp : this.errors) {
/*  46:121 */       sb.append(pp.toString()).append("\n");
/*  47:    */     }
/*  48:123 */     return sb.toString();
/*  49:    */   }
/*  50:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ErrorCollector
 * JD-Core Version:    0.7.0.1
 */
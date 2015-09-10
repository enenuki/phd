/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class EvaluatorException
/*   4:    */   extends RhinoException
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -8743165779676009808L;
/*   7:    */   
/*   8:    */   public EvaluatorException(String detail)
/*   9:    */   {
/*  10: 51 */     super(detail);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public EvaluatorException(String detail, String sourceName, int lineNumber)
/*  14:    */   {
/*  15: 67 */     this(detail, sourceName, lineNumber, null, 0);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public EvaluatorException(String detail, String sourceName, int lineNumber, String lineSource, int columnNumber)
/*  19:    */   {
/*  20: 87 */     super(detail);
/*  21: 88 */     recordErrorOrigin(sourceName, lineNumber, lineSource, columnNumber);
/*  22:    */   }
/*  23:    */   
/*  24:    */   /**
/*  25:    */    * @deprecated
/*  26:    */    */
/*  27:    */   public String getSourceName()
/*  28:    */   {
/*  29: 96 */     return sourceName();
/*  30:    */   }
/*  31:    */   
/*  32:    */   /**
/*  33:    */    * @deprecated
/*  34:    */    */
/*  35:    */   public int getLineNumber()
/*  36:    */   {
/*  37:104 */     return lineNumber();
/*  38:    */   }
/*  39:    */   
/*  40:    */   /**
/*  41:    */    * @deprecated
/*  42:    */    */
/*  43:    */   public int getColumnNumber()
/*  44:    */   {
/*  45:112 */     return columnNumber();
/*  46:    */   }
/*  47:    */   
/*  48:    */   /**
/*  49:    */    * @deprecated
/*  50:    */    */
/*  51:    */   public String getLineSource()
/*  52:    */   {
/*  53:120 */     return lineSource();
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.EvaluatorException
 * JD-Core Version:    0.7.0.1
 */
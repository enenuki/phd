/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class EcmaError
/*   4:    */   extends RhinoException
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -6261226256957286699L;
/*   7:    */   private String errorName;
/*   8:    */   private String errorMessage;
/*   9:    */   
/*  10:    */   EcmaError(String errorName, String errorMessage, String sourceName, int lineNumber, String lineSource, int columnNumber)
/*  11:    */   {
/*  12: 71 */     recordErrorOrigin(sourceName, lineNumber, lineSource, columnNumber);
/*  13: 72 */     this.errorName = errorName;
/*  14: 73 */     this.errorMessage = errorMessage;
/*  15:    */   }
/*  16:    */   
/*  17:    */   /**
/*  18:    */    * @deprecated
/*  19:    */    */
/*  20:    */   public EcmaError(Scriptable nativeError, String sourceName, int lineNumber, int columnNumber, String lineSource)
/*  21:    */   {
/*  22: 83 */     this("InternalError", ScriptRuntime.toString(nativeError), sourceName, lineNumber, lineSource, columnNumber);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String details()
/*  26:    */   {
/*  27: 90 */     return this.errorName + ": " + this.errorMessage;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getName()
/*  31:    */   {
/*  32:107 */     return this.errorName;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getErrorMessage()
/*  36:    */   {
/*  37:119 */     return this.errorMessage;
/*  38:    */   }
/*  39:    */   
/*  40:    */   /**
/*  41:    */    * @deprecated
/*  42:    */    */
/*  43:    */   public String getSourceName()
/*  44:    */   {
/*  45:127 */     return sourceName();
/*  46:    */   }
/*  47:    */   
/*  48:    */   /**
/*  49:    */    * @deprecated
/*  50:    */    */
/*  51:    */   public int getLineNumber()
/*  52:    */   {
/*  53:135 */     return lineNumber();
/*  54:    */   }
/*  55:    */   
/*  56:    */   /**
/*  57:    */    * @deprecated
/*  58:    */    */
/*  59:    */   public int getColumnNumber()
/*  60:    */   {
/*  61:143 */     return columnNumber();
/*  62:    */   }
/*  63:    */   
/*  64:    */   /**
/*  65:    */    * @deprecated
/*  66:    */    */
/*  67:    */   public String getLineSource()
/*  68:    */   {
/*  69:150 */     return lineSource();
/*  70:    */   }
/*  71:    */   
/*  72:    */   /**
/*  73:    */    * @deprecated
/*  74:    */    */
/*  75:    */   public Scriptable getErrorObject()
/*  76:    */   {
/*  77:159 */     return null;
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.EcmaError
 * JD-Core Version:    0.7.0.1
 */
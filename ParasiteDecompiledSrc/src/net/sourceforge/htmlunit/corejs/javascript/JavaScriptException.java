/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class JavaScriptException
/*   4:    */   extends RhinoException
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -7666130513694669293L;
/*   7:    */   private Object value;
/*   8:    */   
/*   9:    */   /**
/*  10:    */    * @deprecated
/*  11:    */    */
/*  12:    */   public JavaScriptException(Object value)
/*  13:    */   {
/*  14: 62 */     this(value, "", 0);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public JavaScriptException(Object value, String sourceName, int lineNumber)
/*  18:    */   {
/*  19: 72 */     recordErrorOrigin(sourceName, lineNumber, null, 0);
/*  20: 73 */     this.value = value;
/*  21: 76 */     if (((value instanceof NativeError)) && (Context.getContext().hasFeature(10)))
/*  22:    */     {
/*  23: 78 */       NativeError error = (NativeError)value;
/*  24: 79 */       if (!error.has("fileName", error)) {
/*  25: 80 */         error.put("fileName", error, sourceName);
/*  26:    */       }
/*  27: 82 */       if (!error.has("lineNumber", error)) {
/*  28: 83 */         error.put("lineNumber", error, Integer.valueOf(lineNumber));
/*  29:    */       }
/*  30: 86 */       error.setStackProvider(this);
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String details()
/*  35:    */   {
/*  36: 93 */     if (this.value == null) {
/*  37: 94 */       return "null";
/*  38:    */     }
/*  39: 95 */     if ((this.value instanceof NativeError)) {
/*  40: 96 */       return this.value.toString();
/*  41:    */     }
/*  42:    */     try
/*  43:    */     {
/*  44: 99 */       return ScriptRuntime.toString(this.value);
/*  45:    */     }
/*  46:    */     catch (RuntimeException rte)
/*  47:    */     {
/*  48:102 */       if ((this.value instanceof Scriptable)) {
/*  49:103 */         return ScriptRuntime.defaultObjectToString((Scriptable)this.value);
/*  50:    */       }
/*  51:    */     }
/*  52:105 */     return this.value.toString();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object getValue()
/*  56:    */   {
/*  57:115 */     return this.value;
/*  58:    */   }
/*  59:    */   
/*  60:    */   /**
/*  61:    */    * @deprecated
/*  62:    */    */
/*  63:    */   public String getSourceName()
/*  64:    */   {
/*  65:123 */     return sourceName();
/*  66:    */   }
/*  67:    */   
/*  68:    */   /**
/*  69:    */    * @deprecated
/*  70:    */    */
/*  71:    */   public int getLineNumber()
/*  72:    */   {
/*  73:131 */     return lineNumber();
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.JavaScriptException
 * JD-Core Version:    0.7.0.1
 */
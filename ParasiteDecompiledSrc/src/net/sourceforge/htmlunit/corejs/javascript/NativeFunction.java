/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript;
/*   4:    */ 
/*   5:    */ public abstract class NativeFunction
/*   6:    */   extends BaseFunction
/*   7:    */ {
/*   8:    */   public final void initScriptFunction(Context cx, Scriptable scope)
/*   9:    */   {
/*  10: 57 */     ScriptRuntime.setFunctionProtoAndParent(this, scope);
/*  11:    */   }
/*  12:    */   
/*  13:    */   final String decompile(int indent, int flags)
/*  14:    */   {
/*  15: 68 */     String encodedSource = getEncodedSource();
/*  16: 69 */     if (encodedSource == null) {
/*  17: 70 */       return super.decompile(indent, flags);
/*  18:    */     }
/*  19: 72 */     UintMap properties = new UintMap(1);
/*  20: 73 */     properties.put(1, indent);
/*  21: 74 */     return Decompiler.decompile(encodedSource, flags, properties);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int getLength()
/*  25:    */   {
/*  26: 81 */     int paramCount = getParamCount();
/*  27: 82 */     if (getLanguageVersion() != 120) {
/*  28: 83 */       return paramCount;
/*  29:    */     }
/*  30: 85 */     Context cx = Context.getContext();
/*  31: 86 */     NativeCall activation = ScriptRuntime.findFunctionActivation(cx, this);
/*  32: 87 */     if (activation == null) {
/*  33: 88 */       return paramCount;
/*  34:    */     }
/*  35: 90 */     return activation.originalArgs.length;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getArity()
/*  39:    */   {
/*  40: 96 */     return getParamCount();
/*  41:    */   }
/*  42:    */   
/*  43:    */   /**
/*  44:    */    * @deprecated
/*  45:    */    */
/*  46:    */   public String jsGet_name()
/*  47:    */   {
/*  48:106 */     return getFunctionName();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getEncodedSource()
/*  52:    */   {
/*  53:114 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public DebuggableScript getDebuggableView()
/*  57:    */   {
/*  58:119 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Object resumeGenerator(Context cx, Scriptable scope, int operation, Object state, Object value)
/*  62:    */   {
/*  63:134 */     throw new EvaluatorException("resumeGenerator() not implemented");
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected abstract int getLanguageVersion();
/*  67:    */   
/*  68:    */   protected abstract int getParamCount();
/*  69:    */   
/*  70:    */   protected abstract int getParamAndVarCount();
/*  71:    */   
/*  72:    */   protected abstract String getParamOrVarName(int paramInt);
/*  73:    */   
/*  74:    */   protected boolean getParamOrVarConst(int index)
/*  75:    */   {
/*  76:169 */     return false;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeFunction
 * JD-Core Version:    0.7.0.1
 */
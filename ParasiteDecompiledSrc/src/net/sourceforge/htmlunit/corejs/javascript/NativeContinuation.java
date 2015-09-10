/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public final class NativeContinuation
/*   4:    */   extends IdScriptableObject
/*   5:    */   implements Function
/*   6:    */ {
/*   7:    */   static final long serialVersionUID = 1794167133757605367L;
/*   8: 46 */   private static final Object FTAG = "Continuation";
/*   9:    */   private Object implementation;
/*  10:    */   private static final int Id_constructor = 1;
/*  11:    */   private static final int MAX_PROTOTYPE_ID = 1;
/*  12:    */   
/*  13:    */   public static void init(Context cx, Scriptable scope, boolean sealed)
/*  14:    */   {
/*  15: 52 */     NativeContinuation obj = new NativeContinuation();
/*  16: 53 */     obj.exportAsJSClass(1, scope, sealed);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Object getImplementation()
/*  20:    */   {
/*  21: 58 */     return this.implementation;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void initImplementation(Object implementation)
/*  25:    */   {
/*  26: 63 */     this.implementation = implementation;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getClassName()
/*  30:    */   {
/*  31: 69 */     return "Continuation";
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/*  35:    */   {
/*  36: 74 */     throw Context.reportRuntimeError("Direct call is not supported");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  40:    */   {
/*  41: 80 */     return Interpreter.restartContinuation(this, cx, scope, args);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static boolean isContinuationConstructor(IdFunctionObject f)
/*  45:    */   {
/*  46: 85 */     if ((f.hasTag(FTAG)) && (f.methodId() == 1)) {
/*  47: 86 */       return true;
/*  48:    */     }
/*  49: 88 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected void initPrototypeId(int id)
/*  53:    */   {
/*  54:    */     int arity;
/*  55:    */     String s;
/*  56: 96 */     switch (id)
/*  57:    */     {
/*  58:    */     case 1: 
/*  59: 97 */       arity = 0;s = "constructor"; break;
/*  60:    */     default: 
/*  61: 98 */       throw new IllegalArgumentException(String.valueOf(id));
/*  62:    */     }
/*  63:100 */     initPrototypeMethod(FTAG, id, s, arity);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  67:    */   {
/*  68:107 */     if (!f.hasTag(FTAG)) {
/*  69:108 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  70:    */     }
/*  71:110 */     int id = f.methodId();
/*  72:111 */     switch (id)
/*  73:    */     {
/*  74:    */     case 1: 
/*  75:113 */       throw Context.reportRuntimeError("Direct call is not supported");
/*  76:    */     }
/*  77:115 */     throw new IllegalArgumentException(String.valueOf(id));
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected int findPrototypeId(String s)
/*  81:    */   {
/*  82:125 */     int id = 0;String X = null;
/*  83:126 */     if (s.length() == 11)
/*  84:    */     {
/*  85:126 */       X = "constructor";id = 1;
/*  86:    */     }
/*  87:127 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/*  88:127 */       id = 0;
/*  89:    */     }
/*  90:131 */     return id;
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeContinuation
 * JD-Core Version:    0.7.0.1
 */
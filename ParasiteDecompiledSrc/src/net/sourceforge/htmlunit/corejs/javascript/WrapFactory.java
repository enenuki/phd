/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class WrapFactory
/*   4:    */ {
/*   5:    */   public Object wrap(Context cx, Scriptable scope, Object obj, Class<?> staticType)
/*   6:    */   {
/*   7: 80 */     if ((obj == null) || (obj == Undefined.instance) || ((obj instanceof Scriptable))) {
/*   8: 83 */       return obj;
/*   9:    */     }
/*  10: 85 */     if ((staticType != null) && (staticType.isPrimitive()))
/*  11:    */     {
/*  12: 86 */       if (staticType == Void.TYPE) {
/*  13: 87 */         return Undefined.instance;
/*  14:    */       }
/*  15: 88 */       if (staticType == Character.TYPE) {
/*  16: 89 */         return Integer.valueOf(((Character)obj).charValue());
/*  17:    */       }
/*  18: 90 */       return obj;
/*  19:    */     }
/*  20: 92 */     if (!isJavaPrimitiveWrap())
/*  21:    */     {
/*  22: 93 */       if (((obj instanceof String)) || ((obj instanceof Number)) || ((obj instanceof Boolean))) {
/*  23: 96 */         return obj;
/*  24:    */       }
/*  25: 97 */       if ((obj instanceof Character)) {
/*  26: 98 */         return String.valueOf(((Character)obj).charValue());
/*  27:    */       }
/*  28:    */     }
/*  29:101 */     Class<?> cls = obj.getClass();
/*  30:102 */     if (cls.isArray()) {
/*  31:103 */       return NativeJavaArray.wrap(scope, obj);
/*  32:    */     }
/*  33:105 */     return wrapAsJavaObject(cx, scope, obj, staticType);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Scriptable wrapNewObject(Context cx, Scriptable scope, Object obj)
/*  37:    */   {
/*  38:117 */     if ((obj instanceof Scriptable)) {
/*  39:118 */       return (Scriptable)obj;
/*  40:    */     }
/*  41:120 */     Class<?> cls = obj.getClass();
/*  42:121 */     if (cls.isArray()) {
/*  43:122 */       return NativeJavaArray.wrap(scope, obj);
/*  44:    */     }
/*  45:124 */     return wrapAsJavaObject(cx, scope, obj, null);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Scriptable wrapAsJavaObject(Context cx, Scriptable scope, Object javaObject, Class<?> staticType)
/*  49:    */   {
/*  50:148 */     return new NativeJavaObject(scope, javaObject, staticType);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Scriptable wrapJavaClass(Context cx, Scriptable scope, Class javaClass)
/*  54:    */   {
/*  55:167 */     return new NativeJavaClass(scope, javaClass);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final boolean isJavaPrimitiveWrap()
/*  59:    */   {
/*  60:183 */     return this.javaPrimitiveWrap;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public final void setJavaPrimitiveWrap(boolean value)
/*  64:    */   {
/*  65:191 */     Context cx = Context.getCurrentContext();
/*  66:192 */     if ((cx != null) && (cx.isSealed())) {
/*  67:193 */       Context.onSealedMutation();
/*  68:    */     }
/*  69:195 */     this.javaPrimitiveWrap = value;
/*  70:    */   }
/*  71:    */   
/*  72:198 */   private boolean javaPrimitiveWrap = true;
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.WrapFactory
 * JD-Core Version:    0.7.0.1
 */
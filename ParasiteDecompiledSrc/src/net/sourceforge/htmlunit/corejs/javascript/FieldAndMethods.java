/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ 
/*   5:    */ class FieldAndMethods
/*   6:    */   extends NativeJavaMethod
/*   7:    */ {
/*   8:    */   static final long serialVersionUID = -9222428244284796755L;
/*   9:    */   Field field;
/*  10:    */   Object javaObject;
/*  11:    */   
/*  12:    */   FieldAndMethods(Scriptable scope, MemberBox[] methods, Field field)
/*  13:    */   {
/*  14:919 */     super(methods);
/*  15:920 */     this.field = field;
/*  16:921 */     setParentScope(scope);
/*  17:922 */     setPrototype(ScriptableObject.getFunctionPrototype(scope));
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Object getDefaultValue(Class<?> hint)
/*  21:    */   {
/*  22:928 */     if (hint == ScriptRuntime.FunctionClass) {
/*  23:929 */       return this;
/*  24:    */     }
/*  25:    */     Class<?> type;
/*  26:    */     try
/*  27:    */     {
/*  28:933 */       rval = this.field.get(this.javaObject);
/*  29:934 */       type = this.field.getType();
/*  30:    */     }
/*  31:    */     catch (IllegalAccessException accEx)
/*  32:    */     {
/*  33:936 */       throw Context.reportRuntimeError1("msg.java.internal.private", this.field.getName());
/*  34:    */     }
/*  35:939 */     Context cx = Context.getContext();
/*  36:940 */     Object rval = cx.getWrapFactory().wrap(cx, this, rval, type);
/*  37:941 */     if ((rval instanceof Scriptable)) {
/*  38:942 */       rval = ((Scriptable)rval).getDefaultValue(hint);
/*  39:    */     }
/*  40:944 */     return rval;
/*  41:    */   }
/*  42:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.FieldAndMethods
 * JD-Core Version:    0.7.0.1
 */
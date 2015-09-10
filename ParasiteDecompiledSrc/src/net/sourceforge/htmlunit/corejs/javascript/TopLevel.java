/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.util.EnumMap;
/*   4:    */ 
/*   5:    */ public class TopLevel
/*   6:    */   extends IdScriptableObject
/*   7:    */ {
/*   8:    */   static final long serialVersionUID = -4648046356662472260L;
/*   9:    */   private EnumMap<Builtins, BaseFunction> ctors;
/*  10:    */   
/*  11:    */   public static enum Builtins
/*  12:    */   {
/*  13: 79 */     Object,  Array,  Function,  String,  Number,  Boolean,  RegExp,  Error;
/*  14:    */     
/*  15:    */     private Builtins() {}
/*  16:    */   }
/*  17:    */   
/*  18:    */   public String getClassName()
/*  19:    */   {
/*  20:100 */     return "global";
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void cacheBuiltins()
/*  24:    */   {
/*  25:112 */     this.ctors = new EnumMap(Builtins.class);
/*  26:113 */     for (Builtins builtin : Builtins.values())
/*  27:    */     {
/*  28:114 */       Object value = ScriptableObject.getProperty(this, builtin.name());
/*  29:115 */       if ((value instanceof BaseFunction)) {
/*  30:116 */         this.ctors.put(builtin, (BaseFunction)value);
/*  31:    */       }
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static Function getBuiltinCtor(Context cx, Scriptable scope, Builtins type)
/*  36:    */   {
/*  37:136 */     assert (scope.getParentScope() == null);
/*  38:137 */     if ((scope instanceof TopLevel))
/*  39:    */     {
/*  40:138 */       Function result = ((TopLevel)scope).getBuiltinCtor(type);
/*  41:139 */       if (result != null) {
/*  42:140 */         return result;
/*  43:    */       }
/*  44:    */     }
/*  45:144 */     return ScriptRuntime.getExistingCtor(cx, scope, type.name());
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static Scriptable getBuiltinPrototype(Scriptable scope, Builtins type)
/*  49:    */   {
/*  50:160 */     assert (scope.getParentScope() == null);
/*  51:161 */     if ((scope instanceof TopLevel))
/*  52:    */     {
/*  53:162 */       Scriptable result = ((TopLevel)scope).getBuiltinPrototype(type);
/*  54:164 */       if (result != null) {
/*  55:165 */         return result;
/*  56:    */       }
/*  57:    */     }
/*  58:169 */     return ScriptableObject.getClassPrototype(scope, type.name());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public BaseFunction getBuiltinCtor(Builtins type)
/*  62:    */   {
/*  63:180 */     return this.ctors != null ? (BaseFunction)this.ctors.get(type) : null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Scriptable getBuiltinPrototype(Builtins type)
/*  67:    */   {
/*  68:191 */     BaseFunction func = getBuiltinCtor(type);
/*  69:192 */     Object proto = func != null ? func.getPrototypeProperty() : null;
/*  70:193 */     return (proto instanceof Scriptable) ? (Scriptable)proto : null;
/*  71:    */   }
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.TopLevel
 * JD-Core Version:    0.7.0.1
 */
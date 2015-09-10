/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class BoundFunction
/*   4:    */   extends BaseFunction
/*   5:    */ {
/*   6:    */   private final Callable targetFunction;
/*   7:    */   private final Scriptable boundThis;
/*   8:    */   private final Object[] boundArgs;
/*   9:    */   private final int length;
/*  10:    */   
/*  11:    */   public BoundFunction(Context cx, Scriptable scope, Callable targetFunction, Scriptable boundThis, Object[] boundArgs)
/*  12:    */   {
/*  13: 56 */     this.targetFunction = targetFunction;
/*  14: 57 */     this.boundThis = boundThis;
/*  15: 58 */     this.boundArgs = boundArgs;
/*  16: 59 */     if ((targetFunction instanceof BaseFunction)) {
/*  17: 60 */       this.length = Math.max(0, ((BaseFunction)targetFunction).getLength() - boundArgs.length);
/*  18:    */     } else {
/*  19: 62 */       this.length = 0;
/*  20:    */     }
/*  21: 65 */     ScriptRuntime.setFunctionProtoAndParent(this, scope);
/*  22:    */     
/*  23: 67 */     Function thrower = ScriptRuntime.typeErrorThrower();
/*  24: 68 */     NativeObject throwing = new NativeObject();
/*  25: 69 */     throwing.put("get", throwing, thrower);
/*  26: 70 */     throwing.put("set", throwing, thrower);
/*  27: 71 */     throwing.put("enumerable", throwing, Boolean.valueOf(false));
/*  28: 72 */     throwing.put("configurable", throwing, Boolean.valueOf(false));
/*  29: 73 */     throwing.preventExtensions();
/*  30:    */     
/*  31: 75 */     defineOwnProperty(cx, "caller", throwing);
/*  32: 76 */     defineOwnProperty(cx, "arguments", throwing);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] extraArgs)
/*  36:    */   {
/*  37: 82 */     Scriptable callThis = this.boundThis != null ? this.boundThis : ScriptRuntime.getTopCallScope(cx);
/*  38: 83 */     return this.targetFunction.call(cx, scope, callThis, concat(this.boundArgs, extraArgs));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] extraArgs)
/*  42:    */   {
/*  43: 88 */     if ((this.targetFunction instanceof Function)) {
/*  44: 89 */       return ((Function)this.targetFunction).construct(cx, scope, concat(this.boundArgs, extraArgs));
/*  45:    */     }
/*  46: 91 */     throw ScriptRuntime.typeError0("msg.not.ctor");
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hasInstance(Scriptable instance)
/*  50:    */   {
/*  51: 96 */     if ((this.targetFunction instanceof Function)) {
/*  52: 97 */       return ((Function)this.targetFunction).hasInstance(instance);
/*  53:    */     }
/*  54: 99 */     throw ScriptRuntime.typeError0("msg.not.ctor");
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getLength()
/*  58:    */   {
/*  59:104 */     return this.length;
/*  60:    */   }
/*  61:    */   
/*  62:    */   private Object[] concat(Object[] first, Object[] second)
/*  63:    */   {
/*  64:108 */     Object[] args = new Object[first.length + second.length];
/*  65:109 */     System.arraycopy(first, 0, args, 0, first.length);
/*  66:110 */     System.arraycopy(second, 0, args, first.length, second.length);
/*  67:111 */     return args;
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.BoundFunction
 * JD-Core Version:    0.7.0.1
 */
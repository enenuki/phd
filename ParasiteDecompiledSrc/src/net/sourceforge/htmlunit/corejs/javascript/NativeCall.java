/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public final class NativeCall
/*   4:    */   extends IdScriptableObject
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -7471457301304454454L;
/*   7: 54 */   private static final Object CALL_TAG = "Call";
/*   8:    */   private static final int Id_constructor = 1;
/*   9:    */   private static final int MAX_PROTOTYPE_ID = 1;
/*  10:    */   NativeFunction function;
/*  11:    */   Object[] originalArgs;
/*  12:    */   transient NativeCall parentActivationCall;
/*  13:    */   
/*  14:    */   static void init(Scriptable scope, boolean sealed)
/*  15:    */   {
/*  16: 58 */     NativeCall obj = new NativeCall();
/*  17: 59 */     obj.exportAsJSClass(1, scope, sealed);
/*  18:    */   }
/*  19:    */   
/*  20:    */   NativeCall() {}
/*  21:    */   
/*  22:    */   NativeCall(NativeFunction function, Scriptable scope, Object[] args)
/*  23:    */   {
/*  24: 66 */     this.function = function;
/*  25:    */     
/*  26: 68 */     setParentScope(scope);
/*  27:    */     
/*  28:    */ 
/*  29: 71 */     this.originalArgs = (args == null ? ScriptRuntime.emptyArgs : args);
/*  30:    */     
/*  31:    */ 
/*  32: 74 */     int paramAndVarCount = function.getParamAndVarCount();
/*  33: 75 */     int paramCount = function.getParamCount();
/*  34: 76 */     if (paramAndVarCount != 0) {
/*  35: 77 */       for (int i = 0; i < paramCount; i++)
/*  36:    */       {
/*  37: 78 */         String name = function.getParamOrVarName(i);
/*  38: 79 */         Object val = i < args.length ? args[i] : Undefined.instance;
/*  39:    */         
/*  40: 81 */         defineProperty(name, val, 4);
/*  41:    */       }
/*  42:    */     }
/*  43: 87 */     if (!super.has("arguments", this)) {
/*  44: 88 */       defineProperty("arguments", new Arguments(this), 4);
/*  45:    */     }
/*  46: 91 */     if (paramAndVarCount != 0) {
/*  47: 92 */       for (int i = paramCount; i < paramAndVarCount; i++)
/*  48:    */       {
/*  49: 93 */         String name = function.getParamOrVarName(i);
/*  50: 94 */         if (!super.has(name, this)) {
/*  51: 95 */           if (function.getParamOrVarConst(i)) {
/*  52: 96 */             defineProperty(name, Undefined.instance, 13);
/*  53:    */           } else {
/*  54: 98 */             defineProperty(name, Undefined.instance, 4);
/*  55:    */           }
/*  56:    */         }
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getClassName()
/*  62:    */   {
/*  63:107 */     return "Call";
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected int findPrototypeId(String s)
/*  67:    */   {
/*  68:113 */     return s.equals("constructor") ? 1 : 0;
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void initPrototypeId(int id)
/*  72:    */   {
/*  73:    */     String s;
/*  74:121 */     if (id == 1)
/*  75:    */     {
/*  76:122 */       int arity = 1;s = "constructor";
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80:124 */       throw new IllegalArgumentException(String.valueOf(id));
/*  81:    */     }
/*  82:    */     int arity;
/*  83:    */     String s;
/*  84:126 */     initPrototypeMethod(CALL_TAG, id, s, arity);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  88:    */   {
/*  89:133 */     if (!f.hasTag(CALL_TAG)) {
/*  90:134 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  91:    */     }
/*  92:136 */     int id = f.methodId();
/*  93:137 */     if (id == 1)
/*  94:    */     {
/*  95:138 */       if (thisObj != null) {
/*  96:139 */         throw Context.reportRuntimeError1("msg.only.from.new", "Call");
/*  97:    */       }
/*  98:141 */       ScriptRuntime.checkDeprecated(cx, "Call");
/*  99:142 */       NativeCall result = new NativeCall();
/* 100:143 */       result.setPrototype(getObjectPrototype(scope));
/* 101:144 */       return result;
/* 102:    */     }
/* 103:146 */     throw new IllegalArgumentException(String.valueOf(id));
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeCall
 * JD-Core Version:    0.7.0.1
 */
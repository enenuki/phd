/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ final class NativeBoolean
/*   4:    */   extends IdScriptableObject
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -3716996899943880933L;
/*   7: 52 */   private static final Object BOOLEAN_TAG = "Boolean";
/*   8:    */   private static final int Id_constructor = 1;
/*   9:    */   private static final int Id_toString = 2;
/*  10:    */   private static final int Id_toSource = 3;
/*  11:    */   private static final int Id_valueOf = 4;
/*  12:    */   private static final int MAX_PROTOTYPE_ID = 4;
/*  13:    */   private boolean booleanValue;
/*  14:    */   
/*  15:    */   static void init(Scriptable scope, boolean sealed)
/*  16:    */   {
/*  17: 56 */     NativeBoolean obj = new NativeBoolean(false);
/*  18: 57 */     obj.exportAsJSClass(4, scope, sealed);
/*  19:    */   }
/*  20:    */   
/*  21:    */   NativeBoolean(boolean b)
/*  22:    */   {
/*  23: 62 */     this.booleanValue = b;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getClassName()
/*  27:    */   {
/*  28: 68 */     return "Boolean";
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Object getDefaultValue(Class<?> typeHint)
/*  32:    */   {
/*  33: 75 */     if (typeHint == ScriptRuntime.BooleanClass) {
/*  34: 76 */       return ScriptRuntime.wrapBoolean(this.booleanValue);
/*  35:    */     }
/*  36: 77 */     return super.getDefaultValue(typeHint);
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected void initPrototypeId(int id)
/*  40:    */   {
/*  41:    */     int arity;
/*  42:    */     String s;
/*  43: 85 */     switch (id)
/*  44:    */     {
/*  45:    */     case 1: 
/*  46: 86 */       arity = 1;s = "constructor"; break;
/*  47:    */     case 2: 
/*  48: 87 */       arity = 0;s = "toString"; break;
/*  49:    */     case 3: 
/*  50: 88 */       arity = 0;s = "toSource"; break;
/*  51:    */     case 4: 
/*  52: 89 */       arity = 0;s = "valueOf"; break;
/*  53:    */     default: 
/*  54: 90 */       throw new IllegalArgumentException(String.valueOf(id));
/*  55:    */     }
/*  56: 92 */     initPrototypeMethod(BOOLEAN_TAG, id, s, arity);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  60:    */   {
/*  61: 99 */     if (!f.hasTag(BOOLEAN_TAG)) {
/*  62:100 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  63:    */     }
/*  64:102 */     int id = f.methodId();
/*  65:104 */     if (id == 1)
/*  66:    */     {
/*  67:    */       boolean b;
/*  68:    */       boolean b;
/*  69:106 */       if (args.length == 0) {
/*  70:107 */         b = false;
/*  71:    */       } else {
/*  72:109 */         b = ((args[0] instanceof ScriptableObject)) && (((ScriptableObject)args[0]).avoidObjectDetection()) ? true : ScriptRuntime.toBoolean(args[0]);
/*  73:    */       }
/*  74:114 */       if (thisObj == null) {
/*  75:116 */         return new NativeBoolean(b);
/*  76:    */       }
/*  77:119 */       return ScriptRuntime.wrapBoolean(b);
/*  78:    */     }
/*  79:124 */     if (!(thisObj instanceof NativeBoolean)) {
/*  80:125 */       throw incompatibleCallError(f);
/*  81:    */     }
/*  82:126 */     boolean value = ((NativeBoolean)thisObj).booleanValue;
/*  83:128 */     switch (id)
/*  84:    */     {
/*  85:    */     case 2: 
/*  86:131 */       return value ? "true" : "false";
/*  87:    */     case 3: 
/*  88:134 */       return value ? "(new Boolean(true))" : "(new Boolean(false))";
/*  89:    */     case 4: 
/*  90:137 */       return ScriptRuntime.wrapBoolean(value);
/*  91:    */     }
/*  92:139 */     throw new IllegalArgumentException(String.valueOf(id));
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected int findPrototypeId(String s)
/*  96:    */   {
/*  97:149 */     int id = 0;String X = null;
/*  98:150 */     int s_length = s.length();
/*  99:151 */     if (s_length == 7)
/* 100:    */     {
/* 101:151 */       X = "valueOf";id = 4;
/* 102:    */     }
/* 103:152 */     else if (s_length == 8)
/* 104:    */     {
/* 105:153 */       int c = s.charAt(3);
/* 106:154 */       if (c == 111)
/* 107:    */       {
/* 108:154 */         X = "toSource";id = 3;
/* 109:    */       }
/* 110:155 */       else if (c == 116)
/* 111:    */       {
/* 112:155 */         X = "toString";id = 2;
/* 113:    */       }
/* 114:    */     }
/* 115:157 */     else if (s_length == 11)
/* 116:    */     {
/* 117:157 */       X = "constructor";id = 1;
/* 118:    */     }
/* 119:158 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 120:158 */       id = 0;
/* 121:    */     }
/* 122:162 */     return id;
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeBoolean
 * JD-Core Version:    0.7.0.1
 */
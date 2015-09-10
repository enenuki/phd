/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ class NativeScript
/*   4:    */   extends BaseFunction
/*   5:    */ {
/*   6:    */   static final long serialVersionUID = -6795101161980121700L;
/*   7: 62 */   private static final Object SCRIPT_TAG = "Script";
/*   8:    */   private static final int Id_constructor = 1;
/*   9:    */   private static final int Id_toString = 2;
/*  10:    */   private static final int Id_compile = 3;
/*  11:    */   private static final int Id_exec = 4;
/*  12:    */   private static final int MAX_PROTOTYPE_ID = 4;
/*  13:    */   private Script script;
/*  14:    */   
/*  15:    */   static void init(Scriptable scope, boolean sealed)
/*  16:    */   {
/*  17: 66 */     NativeScript obj = new NativeScript(null);
/*  18: 67 */     obj.exportAsJSClass(4, scope, sealed);
/*  19:    */   }
/*  20:    */   
/*  21:    */   private NativeScript(Script script)
/*  22:    */   {
/*  23: 72 */     this.script = script;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getClassName()
/*  27:    */   {
/*  28: 81 */     return "Script";
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  32:    */   {
/*  33: 88 */     if (this.script != null) {
/*  34: 89 */       return this.script.exec(cx, scope);
/*  35:    */     }
/*  36: 91 */     return Undefined.instance;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/*  40:    */   {
/*  41: 97 */     throw Context.reportRuntimeError0("msg.script.is.not.constructor");
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getLength()
/*  45:    */   {
/*  46:103 */     return 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getArity()
/*  50:    */   {
/*  51:109 */     return 0;
/*  52:    */   }
/*  53:    */   
/*  54:    */   String decompile(int indent, int flags)
/*  55:    */   {
/*  56:115 */     if ((this.script instanceof NativeFunction)) {
/*  57:116 */       return ((NativeFunction)this.script).decompile(indent, flags);
/*  58:    */     }
/*  59:118 */     return super.decompile(indent, flags);
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected void initPrototypeId(int id)
/*  63:    */   {
/*  64:    */     int arity;
/*  65:    */     String s;
/*  66:126 */     switch (id)
/*  67:    */     {
/*  68:    */     case 1: 
/*  69:127 */       arity = 1;s = "constructor"; break;
/*  70:    */     case 2: 
/*  71:128 */       arity = 0;s = "toString"; break;
/*  72:    */     case 4: 
/*  73:129 */       arity = 0;s = "exec"; break;
/*  74:    */     case 3: 
/*  75:130 */       arity = 1;s = "compile"; break;
/*  76:    */     default: 
/*  77:131 */       throw new IllegalArgumentException(String.valueOf(id));
/*  78:    */     }
/*  79:133 */     initPrototypeMethod(SCRIPT_TAG, id, s, arity);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  83:    */   {
/*  84:140 */     if (!f.hasTag(SCRIPT_TAG)) {
/*  85:141 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  86:    */     }
/*  87:143 */     int id = f.methodId();
/*  88:144 */     switch (id)
/*  89:    */     {
/*  90:    */     case 1: 
/*  91:146 */       String source = args.length == 0 ? "" : ScriptRuntime.toString(args[0]);
/*  92:    */       
/*  93:    */ 
/*  94:149 */       Script script = compile(cx, source);
/*  95:150 */       NativeScript nscript = new NativeScript(script);
/*  96:151 */       ScriptRuntime.setObjectProtoAndParent(nscript, scope);
/*  97:152 */       return nscript;
/*  98:    */     case 2: 
/*  99:156 */       NativeScript real = realThis(thisObj, f);
/* 100:157 */       Script realScript = real.script;
/* 101:158 */       if (realScript == null) {
/* 102:158 */         return "";
/* 103:    */       }
/* 104:159 */       return cx.decompileScript(realScript, 0);
/* 105:    */     case 4: 
/* 106:163 */       throw Context.reportRuntimeError1("msg.cant.call.indirect", "exec");
/* 107:    */     case 3: 
/* 108:168 */       NativeScript real = realThis(thisObj, f);
/* 109:169 */       String source = ScriptRuntime.toString(args, 0);
/* 110:170 */       real.script = compile(cx, source);
/* 111:171 */       return real;
/* 112:    */     }
/* 113:174 */     throw new IllegalArgumentException(String.valueOf(id));
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static NativeScript realThis(Scriptable thisObj, IdFunctionObject f)
/* 117:    */   {
/* 118:179 */     if (!(thisObj instanceof NativeScript)) {
/* 119:180 */       throw incompatibleCallError(f);
/* 120:    */     }
/* 121:181 */     return (NativeScript)thisObj;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private static Script compile(Context cx, String source)
/* 125:    */   {
/* 126:186 */     int[] linep = { 0 };
/* 127:187 */     String filename = Context.getSourcePositionFromStack(linep);
/* 128:188 */     if (filename == null)
/* 129:    */     {
/* 130:189 */       filename = "<Script object>";
/* 131:190 */       linep[0] = 1;
/* 132:    */     }
/* 133:193 */     ErrorReporter reporter = DefaultErrorReporter.forEval(cx.getErrorReporter());
/* 134:194 */     return cx.compileString(source, null, reporter, filename, linep[0], null);
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected int findPrototypeId(String s)
/* 138:    */   {
/* 139:205 */     int id = 0;String X = null;
/* 140:206 */     switch (s.length())
/* 141:    */     {
/* 142:    */     case 4: 
/* 143:207 */       X = "exec";id = 4; break;
/* 144:    */     case 7: 
/* 145:208 */       X = "compile";id = 3; break;
/* 146:    */     case 8: 
/* 147:209 */       X = "toString";id = 2; break;
/* 148:    */     case 11: 
/* 149:210 */       X = "constructor";id = 1; break;
/* 150:    */     }
/* 151:212 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 152:212 */       id = 0;
/* 153:    */     }
/* 154:216 */     return id;
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeScript
 * JD-Core Version:    0.7.0.1
 */
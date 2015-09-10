/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript;
/*   4:    */ 
/*   5:    */ final class InterpretedFunction
/*   6:    */   extends NativeFunction
/*   7:    */   implements Script
/*   8:    */ {
/*   9:    */   static final long serialVersionUID = 541475680333911468L;
/*  10:    */   InterpreterData idata;
/*  11:    */   SecurityController securityController;
/*  12:    */   Object securityDomain;
/*  13:    */   Scriptable[] functionRegExps;
/*  14:    */   
/*  15:    */   private InterpretedFunction(InterpreterData idata, Object staticSecurityDomain)
/*  16:    */   {
/*  17: 57 */     this.idata = idata;
/*  18:    */     
/*  19:    */ 
/*  20:    */ 
/*  21:    */ 
/*  22: 62 */     Context cx = Context.getContext();
/*  23: 63 */     SecurityController sc = cx.getSecurityController();
/*  24:    */     Object dynamicDomain;
/*  25:    */     Object dynamicDomain;
/*  26: 65 */     if (sc != null)
/*  27:    */     {
/*  28: 66 */       dynamicDomain = sc.getDynamicSecurityDomain(staticSecurityDomain);
/*  29:    */     }
/*  30:    */     else
/*  31:    */     {
/*  32: 68 */       if (staticSecurityDomain != null) {
/*  33: 69 */         throw new IllegalArgumentException();
/*  34:    */       }
/*  35: 71 */       dynamicDomain = null;
/*  36:    */     }
/*  37: 74 */     this.securityController = sc;
/*  38: 75 */     this.securityDomain = dynamicDomain;
/*  39:    */   }
/*  40:    */   
/*  41:    */   private InterpretedFunction(InterpretedFunction parent, int index)
/*  42:    */   {
/*  43: 80 */     this.idata = parent.idata.itsNestedFunctions[index];
/*  44: 81 */     this.securityController = parent.securityController;
/*  45: 82 */     this.securityDomain = parent.securityDomain;
/*  46:    */   }
/*  47:    */   
/*  48:    */   static InterpretedFunction createScript(InterpreterData idata, Object staticSecurityDomain)
/*  49:    */   {
/*  50: 92 */     InterpretedFunction f = new InterpretedFunction(idata, staticSecurityDomain);
/*  51: 93 */     return f;
/*  52:    */   }
/*  53:    */   
/*  54:    */   static InterpretedFunction createFunction(Context cx, Scriptable scope, InterpreterData idata, Object staticSecurityDomain)
/*  55:    */   {
/*  56:104 */     InterpretedFunction f = new InterpretedFunction(idata, staticSecurityDomain);
/*  57:105 */     f.initInterpretedFunction(cx, scope);
/*  58:106 */     return f;
/*  59:    */   }
/*  60:    */   
/*  61:    */   static InterpretedFunction createFunction(Context cx, Scriptable scope, InterpretedFunction parent, int index)
/*  62:    */   {
/*  63:116 */     InterpretedFunction f = new InterpretedFunction(parent, index);
/*  64:117 */     f.initInterpretedFunction(cx, scope);
/*  65:118 */     return f;
/*  66:    */   }
/*  67:    */   
/*  68:    */   Scriptable[] createRegExpWraps(Context cx, Scriptable scope)
/*  69:    */   {
/*  70:123 */     if (this.idata.itsRegExpLiterals == null) {
/*  71:123 */       Kit.codeBug();
/*  72:    */     }
/*  73:125 */     RegExpProxy rep = ScriptRuntime.checkRegExpProxy(cx);
/*  74:126 */     int N = this.idata.itsRegExpLiterals.length;
/*  75:127 */     Scriptable[] array = new Scriptable[N];
/*  76:128 */     for (int i = 0; i != N; i++) {
/*  77:129 */       array[i] = rep.wrapRegExp(cx, scope, this.idata.itsRegExpLiterals[i]);
/*  78:    */     }
/*  79:131 */     return array;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void initInterpretedFunction(Context cx, Scriptable scope)
/*  83:    */   {
/*  84:136 */     initScriptFunction(cx, scope);
/*  85:137 */     if (this.idata.itsRegExpLiterals != null) {
/*  86:138 */       this.functionRegExps = createRegExpWraps(cx, scope);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getFunctionName()
/*  91:    */   {
/*  92:145 */     return this.idata.itsName == null ? "" : this.idata.itsName;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  96:    */   {
/*  97:161 */     if (!ScriptRuntime.hasTopCall(cx)) {
/*  98:162 */       return ScriptRuntime.doTopCall(this, cx, scope, thisObj, args);
/*  99:    */     }
/* 100:164 */     return Interpreter.interpret(this, cx, scope, thisObj, args);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Object exec(Context cx, Scriptable scope)
/* 104:    */   {
/* 105:169 */     if (!isScript()) {
/* 106:171 */       throw new IllegalStateException();
/* 107:    */     }
/* 108:173 */     if (!ScriptRuntime.hasTopCall(cx)) {
/* 109:175 */       return ScriptRuntime.doTopCall(this, cx, scope, scope, ScriptRuntime.emptyArgs);
/* 110:    */     }
/* 111:178 */     return Interpreter.interpret(this, cx, scope, scope, ScriptRuntime.emptyArgs);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isScript()
/* 115:    */   {
/* 116:183 */     return this.idata.itsFunctionType == 0;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getEncodedSource()
/* 120:    */   {
/* 121:189 */     return Interpreter.getEncodedSource(this.idata);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public DebuggableScript getDebuggableView()
/* 125:    */   {
/* 126:195 */     return this.idata;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Object resumeGenerator(Context cx, Scriptable scope, int operation, Object state, Object value)
/* 130:    */   {
/* 131:202 */     return Interpreter.resumeGenerator(cx, scope, operation, state, value);
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected int getLanguageVersion()
/* 135:    */   {
/* 136:208 */     return this.idata.languageVersion;
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected int getParamCount()
/* 140:    */   {
/* 141:214 */     return this.idata.argCount;
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected int getParamAndVarCount()
/* 145:    */   {
/* 146:220 */     return this.idata.argNames.length;
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected String getParamOrVarName(int index)
/* 150:    */   {
/* 151:226 */     return this.idata.argNames[index];
/* 152:    */   }
/* 153:    */   
/* 154:    */   protected boolean getParamOrVarConst(int index)
/* 155:    */   {
/* 156:232 */     return this.idata.argIsConst[index];
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String toString()
/* 160:    */   {
/* 161:241 */     return decompile(2, 0);
/* 162:    */   }
/* 163:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.InterpretedFunction
 * JD-Core Version:    0.7.0.1
 */
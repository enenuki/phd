/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public final class NativeGenerator
/*   4:    */   extends IdScriptableObject
/*   5:    */ {
/*   6:    */   private static final long serialVersionUID = 1645892441041347273L;
/*   7: 42 */   private static final Object GENERATOR_TAG = "Generator";
/*   8:    */   public static final int GENERATOR_SEND = 0;
/*   9:    */   public static final int GENERATOR_THROW = 1;
/*  10:    */   public static final int GENERATOR_CLOSE = 2;
/*  11:    */   private static final int Id_close = 1;
/*  12:    */   private static final int Id_next = 2;
/*  13:    */   private static final int Id_send = 3;
/*  14:    */   private static final int Id_throw = 4;
/*  15:    */   private static final int Id___iterator__ = 5;
/*  16:    */   private static final int MAX_PROTOTYPE_ID = 5;
/*  17:    */   private NativeFunction function;
/*  18:    */   private Object savedState;
/*  19:    */   private String lineSource;
/*  20:    */   private int lineNumber;
/*  21:    */   
/*  22:    */   static NativeGenerator init(ScriptableObject scope, boolean sealed)
/*  23:    */   {
/*  24: 49 */     NativeGenerator prototype = new NativeGenerator();
/*  25: 50 */     if (scope != null)
/*  26:    */     {
/*  27: 51 */       prototype.setParentScope(scope);
/*  28: 52 */       prototype.setPrototype(getObjectPrototype(scope));
/*  29:    */     }
/*  30: 54 */     prototype.activatePrototypeMap(5);
/*  31: 55 */     if (sealed) {
/*  32: 56 */       prototype.sealObject();
/*  33:    */     }
/*  34: 63 */     if (scope != null) {
/*  35: 64 */       scope.associateValue(GENERATOR_TAG, prototype);
/*  36:    */     }
/*  37: 67 */     return prototype;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private NativeGenerator() {}
/*  41:    */   
/*  42:    */   public NativeGenerator(Scriptable scope, NativeFunction function, Object savedState)
/*  43:    */   {
/*  44: 78 */     this.function = function;
/*  45: 79 */     this.savedState = savedState;
/*  46:    */     
/*  47:    */ 
/*  48:    */ 
/*  49: 83 */     Scriptable top = ScriptableObject.getTopLevelScope(scope);
/*  50: 84 */     setParentScope(top);
/*  51: 85 */     NativeGenerator prototype = (NativeGenerator)ScriptableObject.getTopScopeValue(top, GENERATOR_TAG);
/*  52:    */     
/*  53: 87 */     setPrototype(prototype);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getClassName()
/*  57:    */   {
/*  58: 96 */     return "Generator";
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected void finalize()
/*  62:    */     throws Throwable
/*  63:    */   {
/*  64:104 */     if (this.savedState != null)
/*  65:    */     {
/*  66:109 */       Context cx = Context.getCurrentContext();
/*  67:110 */       ContextFactory factory = cx != null ? cx.getFactory() : ContextFactory.getGlobal();
/*  68:    */       
/*  69:112 */       factory.call(new CloseGeneratorAction(this));
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static class GeneratorClosedException
/*  74:    */     extends RuntimeException
/*  75:    */   {
/*  76:    */     private static final long serialVersionUID = 2561315658662379681L;
/*  77:    */   }
/*  78:    */   
/*  79:    */   private static class CloseGeneratorAction
/*  80:    */     implements ContextAction
/*  81:    */   {
/*  82:    */     private NativeGenerator generator;
/*  83:    */     
/*  84:    */     CloseGeneratorAction(NativeGenerator generator)
/*  85:    */     {
/*  86:120 */       this.generator = generator;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public Object run(Context cx)
/*  90:    */     {
/*  91:124 */       Scriptable scope = ScriptableObject.getTopLevelScope(this.generator);
/*  92:125 */       Callable closeGenerator = new Callable()
/*  93:    */       {
/*  94:    */         public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  95:    */         {
/*  96:128 */           return ((NativeGenerator)thisObj).resume(cx, scope, 2, new NativeGenerator.GeneratorClosedException());
/*  97:    */         }
/*  98:131 */       };
/*  99:132 */       return ScriptRuntime.doTopCall(closeGenerator, cx, scope, this.generator, null);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected void initPrototypeId(int id)
/* 104:    */   {
/* 105:    */     int arity;
/* 106:    */     String s;
/* 107:141 */     switch (id)
/* 108:    */     {
/* 109:    */     case 1: 
/* 110:142 */       arity = 1;s = "close"; break;
/* 111:    */     case 2: 
/* 112:143 */       arity = 1;s = "next"; break;
/* 113:    */     case 3: 
/* 114:144 */       arity = 0;s = "send"; break;
/* 115:    */     case 4: 
/* 116:145 */       arity = 0;s = "throw"; break;
/* 117:    */     case 5: 
/* 118:146 */       arity = 1;s = "__iterator__"; break;
/* 119:    */     default: 
/* 120:147 */       throw new IllegalArgumentException(String.valueOf(id));
/* 121:    */     }
/* 122:149 */     initPrototypeMethod(GENERATOR_TAG, id, s, arity);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 126:    */   {
/* 127:156 */     if (!f.hasTag(GENERATOR_TAG)) {
/* 128:157 */       return super.execIdCall(f, cx, scope, thisObj, args);
/* 129:    */     }
/* 130:159 */     int id = f.methodId();
/* 131:161 */     if (!(thisObj instanceof NativeGenerator)) {
/* 132:162 */       throw incompatibleCallError(f);
/* 133:    */     }
/* 134:164 */     NativeGenerator generator = (NativeGenerator)thisObj;
/* 135:166 */     switch (id)
/* 136:    */     {
/* 137:    */     case 1: 
/* 138:170 */       return generator.resume(cx, scope, 2, new GeneratorClosedException());
/* 139:    */     case 2: 
/* 140:175 */       generator.firstTime = false;
/* 141:176 */       return generator.resume(cx, scope, 0, Undefined.instance);
/* 142:    */     case 3: 
/* 143:180 */       Object arg = args.length > 0 ? args[0] : Undefined.instance;
/* 144:181 */       if ((generator.firstTime) && (!arg.equals(Undefined.instance))) {
/* 145:182 */         throw ScriptRuntime.typeError0("msg.send.newborn");
/* 146:    */       }
/* 147:184 */       return generator.resume(cx, scope, 0, arg);
/* 148:    */     case 4: 
/* 149:188 */       return generator.resume(cx, scope, 1, args.length > 0 ? args[0] : Undefined.instance);
/* 150:    */     case 5: 
/* 151:192 */       return thisObj;
/* 152:    */     }
/* 153:195 */     throw new IllegalArgumentException(String.valueOf(id));
/* 154:    */   }
/* 155:    */   
/* 156:    */   private Object resume(Context cx, Scriptable scope, int operation, Object value)
/* 157:    */   {
/* 158:202 */     if (this.savedState == null)
/* 159:    */     {
/* 160:203 */       if (operation == 2) {
/* 161:204 */         return Undefined.instance;
/* 162:    */       }
/* 163:    */       Object thrown;
/* 164:    */       Object thrown;
/* 165:206 */       if (operation == 1) {
/* 166:207 */         thrown = value;
/* 167:    */       } else {
/* 168:209 */         thrown = NativeIterator.getStopIterationObject(scope);
/* 169:    */       }
/* 170:211 */       throw new JavaScriptException(thrown, this.lineSource, this.lineNumber);
/* 171:    */     }
/* 172:    */     try
/* 173:    */     {
/* 174:214 */       synchronized (this)
/* 175:    */       {
/* 176:218 */         if (this.locked) {
/* 177:219 */           throw ScriptRuntime.typeError0("msg.already.exec.gen");
/* 178:    */         }
/* 179:220 */         this.locked = true;
/* 180:    */       }
/* 181:222 */       return this.function.resumeGenerator(cx, scope, operation, this.savedState, value);
/* 182:    */     }
/* 183:    */     catch (GeneratorClosedException e)
/* 184:    */     {
/* 185:228 */       return Undefined.instance;
/* 186:    */     }
/* 187:    */     catch (RhinoException e)
/* 188:    */     {
/* 189:230 */       this.lineNumber = e.lineNumber();
/* 190:231 */       this.lineSource = e.lineSource();
/* 191:232 */       this.savedState = null;
/* 192:233 */       throw e;
/* 193:    */     }
/* 194:    */     finally
/* 195:    */     {
/* 196:235 */       synchronized (this)
/* 197:    */       {
/* 198:236 */         this.locked = false;
/* 199:    */       }
/* 200:238 */       if (operation == 2) {
/* 201:239 */         this.savedState = null;
/* 202:    */       }
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected int findPrototypeId(String s)
/* 207:    */   {
/* 208:249 */     int id = 0;String X = null;
/* 209:250 */     int s_length = s.length();
/* 210:251 */     if (s_length == 4)
/* 211:    */     {
/* 212:252 */       int c = s.charAt(0);
/* 213:253 */       if (c == 110)
/* 214:    */       {
/* 215:253 */         X = "next";id = 2;
/* 216:    */       }
/* 217:254 */       else if (c == 115)
/* 218:    */       {
/* 219:254 */         X = "send";id = 3;
/* 220:    */       }
/* 221:    */     }
/* 222:256 */     else if (s_length == 5)
/* 223:    */     {
/* 224:257 */       int c = s.charAt(0);
/* 225:258 */       if (c == 99)
/* 226:    */       {
/* 227:258 */         X = "close";id = 1;
/* 228:    */       }
/* 229:259 */       else if (c == 116)
/* 230:    */       {
/* 231:259 */         X = "throw";id = 4;
/* 232:    */       }
/* 233:    */     }
/* 234:261 */     else if (s_length == 12)
/* 235:    */     {
/* 236:261 */       X = "__iterator__";id = 5;
/* 237:    */     }
/* 238:262 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 239:262 */       id = 0;
/* 240:    */     }
/* 241:266 */     return id;
/* 242:    */   }
/* 243:    */   
/* 244:282 */   private boolean firstTime = true;
/* 245:    */   private boolean locked;
/* 246:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeGenerator
 * JD-Core Version:    0.7.0.1
 */
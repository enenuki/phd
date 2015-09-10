/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.net.URI;
/*   5:    */ import java.net.URISyntaxException;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.concurrent.ConcurrentHashMap;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.BaseFunction;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  15:    */ 
/*  16:    */ public class Require
/*  17:    */   extends BaseFunction
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   private final ModuleScriptProvider moduleScriptProvider;
/*  21:    */   private final Scriptable nativeScope;
/*  22:    */   private final Scriptable paths;
/*  23:    */   private final boolean sandboxed;
/*  24:    */   private final Script preExec;
/*  25:    */   private final Script postExec;
/*  26: 47 */   private String mainModuleId = null;
/*  27:    */   private Scriptable mainExports;
/*  28: 51 */   private final Map<String, Scriptable> exportedModuleInterfaces = new ConcurrentHashMap();
/*  29: 53 */   private final Object loadLock = new Object();
/*  30: 57 */   private static final ThreadLocal<Map<String, Scriptable>> loadingModuleInterfaces = new ThreadLocal();
/*  31:    */   
/*  32:    */   public Require(Context cx, Scriptable nativeScope, ModuleScriptProvider moduleScriptProvider, Script preExec, Script postExec, boolean sandboxed)
/*  33:    */   {
/*  34: 80 */     this.moduleScriptProvider = moduleScriptProvider;
/*  35: 81 */     this.nativeScope = nativeScope;
/*  36: 82 */     this.sandboxed = sandboxed;
/*  37: 83 */     this.preExec = preExec;
/*  38: 84 */     this.postExec = postExec;
/*  39: 85 */     setPrototype(ScriptableObject.getFunctionPrototype(nativeScope));
/*  40: 86 */     if (!sandboxed)
/*  41:    */     {
/*  42: 87 */       this.paths = cx.newArray(nativeScope, 0);
/*  43: 88 */       defineReadOnlyProperty(this, "paths", this.paths);
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47: 91 */       this.paths = null;
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Scriptable requireMain(Context cx, String mainModuleId)
/*  52:    */   {
/*  53:113 */     if (this.mainModuleId != null)
/*  54:    */     {
/*  55:114 */       if (!this.mainModuleId.equals(mainModuleId)) {
/*  56:115 */         throw new IllegalStateException("Main module already set to " + this.mainModuleId);
/*  57:    */       }
/*  58:118 */       return this.mainExports;
/*  59:    */     }
/*  60:    */     ModuleScript moduleScript;
/*  61:    */     try
/*  62:    */     {
/*  63:124 */       moduleScript = this.moduleScriptProvider.getModuleScript(cx, mainModuleId, null, this.paths);
/*  64:    */     }
/*  65:    */     catch (RuntimeException x)
/*  66:    */     {
/*  67:127 */       throw x;
/*  68:    */     }
/*  69:    */     catch (Exception x)
/*  70:    */     {
/*  71:129 */       throw new RuntimeException(x);
/*  72:    */     }
/*  73:132 */     if (moduleScript != null)
/*  74:    */     {
/*  75:133 */       this.mainExports = getExportedModuleInterface(cx, mainModuleId, null, true);
/*  76:    */     }
/*  77:135 */     else if (!this.sandboxed)
/*  78:    */     {
/*  79:137 */       URI mainUri = null;
/*  80:    */       try
/*  81:    */       {
/*  82:141 */         mainUri = new URI(mainModuleId);
/*  83:    */       }
/*  84:    */       catch (URISyntaxException usx) {}
/*  85:147 */       if ((mainUri == null) || (!mainUri.isAbsolute()))
/*  86:    */       {
/*  87:148 */         File file = new File(mainModuleId);
/*  88:149 */         if (!file.isFile()) {
/*  89:150 */           throw ScriptRuntime.throwError(cx, this.nativeScope, "Module \"" + mainModuleId + "\" not found.");
/*  90:    */         }
/*  91:153 */         mainUri = file.toURI();
/*  92:    */       }
/*  93:155 */       this.mainExports = getExportedModuleInterface(cx, mainUri.toString(), mainUri, true);
/*  94:    */     }
/*  95:159 */     this.mainModuleId = mainModuleId;
/*  96:160 */     return this.mainExports;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void install(Scriptable scope)
/* 100:    */   {
/* 101:169 */     ScriptableObject.putProperty(scope, "require", this);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 105:    */   {
/* 106:175 */     if ((args == null) || (args.length < 1)) {
/* 107:176 */       throw ScriptRuntime.throwError(cx, scope, "require() needs one argument");
/* 108:    */     }
/* 109:180 */     String id = (String)Context.jsToJava(args[0], String.class);
/* 110:181 */     URI uri = null;
/* 111:182 */     if ((id.startsWith("./")) || (id.startsWith("../")))
/* 112:    */     {
/* 113:183 */       if (!(thisObj instanceof ModuleScope)) {
/* 114:184 */         throw ScriptRuntime.throwError(cx, scope, "Can't resolve relative module ID \"" + id + "\" when require() is used outside of a module");
/* 115:    */       }
/* 116:189 */       ModuleScope moduleScope = (ModuleScope)thisObj;
/* 117:190 */       URI base = moduleScope.getBase();
/* 118:191 */       URI current = moduleScope.getUri();
/* 119:193 */       if (base == null)
/* 120:    */       {
/* 121:196 */         uri = current.resolve(id);
/* 122:197 */         id = uri.toString();
/* 123:    */       }
/* 124:    */       else
/* 125:    */       {
/* 126:200 */         id = base.relativize(current).resolve(id).toString();
/* 127:201 */         if (id.charAt(0) == '.')
/* 128:    */         {
/* 129:204 */           if (this.sandboxed) {
/* 130:205 */             throw ScriptRuntime.throwError(cx, scope, "Module \"" + id + "\" is not contained in sandbox.");
/* 131:    */           }
/* 132:208 */           uri = current.resolve(id);
/* 133:209 */           id = uri.toString();
/* 134:    */         }
/* 135:    */       }
/* 136:    */     }
/* 137:214 */     return getExportedModuleInterface(cx, id, uri, false);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/* 141:    */   {
/* 142:218 */     throw ScriptRuntime.throwError(cx, scope, "require() can not be invoked as a constructor");
/* 143:    */   }
/* 144:    */   
/* 145:    */   private Scriptable getExportedModuleInterface(Context cx, String id, URI uri, boolean isMain)
/* 146:    */   {
/* 147:226 */     Scriptable exports = (Scriptable)this.exportedModuleInterfaces.get(id);
/* 148:227 */     if (exports != null)
/* 149:    */     {
/* 150:228 */       if (isMain) {
/* 151:229 */         throw new IllegalStateException("Attempt to set main module after it was loaded");
/* 152:    */       }
/* 153:232 */       return exports;
/* 154:    */     }
/* 155:236 */     Map<String, Scriptable> threadLoadingModules = (Map)loadingModuleInterfaces.get();
/* 156:238 */     if (threadLoadingModules != null)
/* 157:    */     {
/* 158:239 */       exports = (Scriptable)threadLoadingModules.get(id);
/* 159:240 */       if (exports != null) {
/* 160:241 */         return exports;
/* 161:    */       }
/* 162:    */     }
/* 163:252 */     synchronized (this.loadLock)
/* 164:    */     {
/* 165:255 */       exports = (Scriptable)this.exportedModuleInterfaces.get(id);
/* 166:256 */       if (exports != null) {
/* 167:257 */         return exports;
/* 168:    */       }
/* 169:260 */       ModuleScript moduleScript = getModule(cx, id, uri);
/* 170:261 */       if ((this.sandboxed) && (!moduleScript.isSandboxed())) {
/* 171:262 */         throw ScriptRuntime.throwError(cx, this.nativeScope, "Module \"" + id + "\" is not contained in sandbox.");
/* 172:    */       }
/* 173:265 */       exports = cx.newObject(this.nativeScope);
/* 174:    */       
/* 175:267 */       boolean outermostLocked = threadLoadingModules == null;
/* 176:268 */       if (outermostLocked)
/* 177:    */       {
/* 178:269 */         threadLoadingModules = new HashMap();
/* 179:270 */         loadingModuleInterfaces.set(threadLoadingModules);
/* 180:    */       }
/* 181:280 */       threadLoadingModules.put(id, exports);
/* 182:    */       try
/* 183:    */       {
/* 184:284 */         Scriptable newExports = executeModuleScript(cx, id, exports, moduleScript, isMain);
/* 185:286 */         if (exports != newExports)
/* 186:    */         {
/* 187:287 */           threadLoadingModules.put(id, newExports);
/* 188:288 */           exports = newExports;
/* 189:    */         }
/* 190:    */       }
/* 191:    */       catch (RuntimeException e)
/* 192:    */       {
/* 193:293 */         threadLoadingModules.remove(id);
/* 194:294 */         throw e;
/* 195:    */       }
/* 196:    */       finally
/* 197:    */       {
/* 198:297 */         if (outermostLocked)
/* 199:    */         {
/* 200:304 */           this.exportedModuleInterfaces.putAll(threadLoadingModules);
/* 201:305 */           loadingModuleInterfaces.set(null);
/* 202:    */         }
/* 203:    */       }
/* 204:    */     }
/* 205:309 */     return exports;
/* 206:    */   }
/* 207:    */   
/* 208:    */   private Scriptable executeModuleScript(Context cx, String id, Scriptable exports, ModuleScript moduleScript, boolean isMain)
/* 209:    */   {
/* 210:315 */     ScriptableObject moduleObject = (ScriptableObject)cx.newObject(this.nativeScope);
/* 211:    */     
/* 212:317 */     URI uri = moduleScript.getUri();
/* 213:318 */     URI base = moduleScript.getBase();
/* 214:319 */     defineReadOnlyProperty(moduleObject, "id", id);
/* 215:320 */     if (!this.sandboxed) {
/* 216:321 */       defineReadOnlyProperty(moduleObject, "uri", uri.toString());
/* 217:    */     }
/* 218:323 */     Scriptable executionScope = new ModuleScope(this.nativeScope, uri, base);
/* 219:    */     
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:328 */     executionScope.put("exports", executionScope, exports);
/* 224:329 */     executionScope.put("module", executionScope, moduleObject);
/* 225:330 */     moduleObject.put("exports", moduleObject, exports);
/* 226:331 */     install(executionScope);
/* 227:332 */     if (isMain) {
/* 228:333 */       defineReadOnlyProperty(this, "main", moduleObject);
/* 229:    */     }
/* 230:335 */     executeOptionalScript(this.preExec, cx, executionScope);
/* 231:336 */     moduleScript.getScript().exec(cx, executionScope);
/* 232:337 */     executeOptionalScript(this.postExec, cx, executionScope);
/* 233:338 */     return ScriptRuntime.toObject(this.nativeScope, ScriptableObject.getProperty(moduleObject, "exports"));
/* 234:    */   }
/* 235:    */   
/* 236:    */   private static void executeOptionalScript(Script script, Context cx, Scriptable executionScope)
/* 237:    */   {
/* 238:345 */     if (script != null) {
/* 239:346 */       script.exec(cx, executionScope);
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   private static void defineReadOnlyProperty(ScriptableObject obj, String name, Object value)
/* 244:    */   {
/* 245:352 */     ScriptableObject.putProperty(obj, name, value);
/* 246:353 */     obj.setAttributes(name, 5);
/* 247:    */   }
/* 248:    */   
/* 249:    */   private ModuleScript getModule(Context cx, String id, URI uri)
/* 250:    */   {
/* 251:    */     try
/* 252:    */     {
/* 253:359 */       ModuleScript moduleScript = this.moduleScriptProvider.getModuleScript(cx, id, uri, this.paths);
/* 254:361 */       if (moduleScript == null) {
/* 255:362 */         throw ScriptRuntime.throwError(cx, this.nativeScope, "Module \"" + id + "\" not found.");
/* 256:    */       }
/* 257:365 */       return moduleScript;
/* 258:    */     }
/* 259:    */     catch (RuntimeException e)
/* 260:    */     {
/* 261:368 */       throw e;
/* 262:    */     }
/* 263:    */     catch (Exception e)
/* 264:    */     {
/* 265:371 */       throw Context.throwAsScriptRuntimeEx(e);
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getFunctionName()
/* 270:    */   {
/* 271:377 */     return "require";
/* 272:    */   }
/* 273:    */   
/* 274:    */   public int getArity()
/* 275:    */   {
/* 276:382 */     return 1;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public int getLength()
/* 280:    */   {
/* 281:387 */     return 1;
/* 282:    */   }
/* 283:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.Require
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.lang.ref.ReferenceQueue;
/*   7:    */ import java.lang.ref.SoftReference;
/*   8:    */ import java.net.URI;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.concurrent.ConcurrentHashMap;
/*  13:    */ import java.util.concurrent.ConcurrentMap;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*  16:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  17:    */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.ModuleScript;
/*  18:    */ 
/*  19:    */ public class SoftCachingModuleScriptProvider
/*  20:    */   extends CachingModuleScriptProviderBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23: 32 */   private transient ReferenceQueue<Script> scriptRefQueue = new ReferenceQueue();
/*  24: 35 */   private transient ConcurrentMap<String, ScriptReference> scripts = new ConcurrentHashMap(16, 0.75F, getConcurrencyLevel());
/*  25:    */   
/*  26:    */   public SoftCachingModuleScriptProvider(ModuleSourceProvider moduleSourceProvider)
/*  27:    */   {
/*  28: 46 */     super(moduleSourceProvider);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public ModuleScript getModuleScript(Context cx, String moduleId, URI uri, Scriptable paths)
/*  32:    */     throws Exception
/*  33:    */   {
/*  34:    */     for (;;)
/*  35:    */     {
/*  36: 57 */       ScriptReference ref = (ScriptReference)this.scriptRefQueue.poll();
/*  37: 58 */       if (ref == null) {
/*  38:    */         break;
/*  39:    */       }
/*  40: 61 */       this.scripts.remove(ref.getModuleId(), ref);
/*  41:    */     }
/*  42: 63 */     return super.getModuleScript(cx, moduleId, uri, paths);
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected CachingModuleScriptProviderBase.CachedModuleScript getLoadedModule(String moduleId)
/*  46:    */   {
/*  47: 68 */     ScriptReference scriptRef = (ScriptReference)this.scripts.get(moduleId);
/*  48: 69 */     return scriptRef != null ? scriptRef.getCachedModuleScript() : null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void putLoadedModule(String moduleId, ModuleScript moduleScript, Object validator)
/*  52:    */   {
/*  53: 76 */     this.scripts.put(moduleId, new ScriptReference(moduleScript.getScript(), moduleId, moduleScript.getUri(), moduleScript.getBase(), validator, this.scriptRefQueue));
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static class ScriptReference
/*  57:    */     extends SoftReference<Script>
/*  58:    */   {
/*  59:    */     private final String moduleId;
/*  60:    */     private final URI uri;
/*  61:    */     private final URI base;
/*  62:    */     private final Object validator;
/*  63:    */     
/*  64:    */     ScriptReference(Script script, String moduleId, URI uri, URI base, Object validator, ReferenceQueue<Script> refQueue)
/*  65:    */     {
/*  66: 89 */       super(refQueue);
/*  67: 90 */       this.moduleId = moduleId;
/*  68: 91 */       this.uri = uri;
/*  69: 92 */       this.base = base;
/*  70: 93 */       this.validator = validator;
/*  71:    */     }
/*  72:    */     
/*  73:    */     CachingModuleScriptProviderBase.CachedModuleScript getCachedModuleScript()
/*  74:    */     {
/*  75: 97 */       Script script = (Script)get();
/*  76: 98 */       if (script == null) {
/*  77: 99 */         return null;
/*  78:    */       }
/*  79:101 */       return new CachingModuleScriptProviderBase.CachedModuleScript(new ModuleScript(script, this.uri, this.base), this.validator);
/*  80:    */     }
/*  81:    */     
/*  82:    */     String getModuleId()
/*  83:    */     {
/*  84:106 */       return this.moduleId;
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private void readObject(ObjectInputStream in)
/*  89:    */     throws IOException, ClassNotFoundException
/*  90:    */   {
/*  91:113 */     this.scriptRefQueue = new ReferenceQueue();
/*  92:114 */     this.scripts = new ConcurrentHashMap();
/*  93:115 */     Map<String, CachingModuleScriptProviderBase.CachedModuleScript> serScripts = (Map)in.readObject();
/*  94:116 */     for (Map.Entry<String, CachingModuleScriptProviderBase.CachedModuleScript> entry : serScripts.entrySet())
/*  95:    */     {
/*  96:117 */       CachingModuleScriptProviderBase.CachedModuleScript cachedModuleScript = (CachingModuleScriptProviderBase.CachedModuleScript)entry.getValue();
/*  97:118 */       putLoadedModule((String)entry.getKey(), cachedModuleScript.getModule(), cachedModuleScript.getValidator());
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   private void writeObject(ObjectOutputStream out)
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:124 */     Map<String, CachingModuleScriptProviderBase.CachedModuleScript> serScripts = new HashMap();
/* 105:126 */     for (Map.Entry<String, ScriptReference> entry : this.scripts.entrySet())
/* 106:    */     {
/* 107:127 */       CachingModuleScriptProviderBase.CachedModuleScript cachedModuleScript = ((ScriptReference)entry.getValue()).getCachedModuleScript();
/* 108:129 */       if (cachedModuleScript != null) {
/* 109:130 */         serScripts.put(entry.getKey(), cachedModuleScript);
/* 110:    */       }
/* 111:    */     }
/* 112:133 */     out.writeObject(serScripts);
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.SoftCachingModuleScriptProvider
 * JD-Core Version:    0.7.0.1
 */
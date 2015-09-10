/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import java.util.concurrent.ConcurrentHashMap;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.ModuleScript;
/*  6:   */ 
/*  7:   */ public class StrongCachingModuleScriptProvider
/*  8:   */   extends CachingModuleScriptProviderBase
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 1L;
/* 11:20 */   private final Map<String, CachingModuleScriptProviderBase.CachedModuleScript> modules = new ConcurrentHashMap(16, 0.75F, getConcurrencyLevel());
/* 12:   */   
/* 13:   */   public StrongCachingModuleScriptProvider(ModuleSourceProvider moduleSourceProvider)
/* 14:   */   {
/* 15:30 */     super(moduleSourceProvider);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected CachingModuleScriptProviderBase.CachedModuleScript getLoadedModule(String moduleId)
/* 19:   */   {
/* 20:35 */     return (CachingModuleScriptProviderBase.CachedModuleScript)this.modules.get(moduleId);
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected void putLoadedModule(String moduleId, ModuleScript moduleScript, Object validator)
/* 24:   */   {
/* 25:41 */     this.modules.put(moduleId, new CachingModuleScriptProviderBase.CachedModuleScript(moduleScript, validator));
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.StrongCachingModuleScriptProvider
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import java.util.LinkedList;
/*  5:   */ import java.util.List;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  7:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  8:   */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.ModuleScript;
/*  9:   */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.ModuleScriptProvider;
/* 10:   */ 
/* 11:   */ public class MultiModuleScriptProvider
/* 12:   */   implements ModuleScriptProvider
/* 13:   */ {
/* 14:   */   private final ModuleScriptProvider[] providers;
/* 15:   */   
/* 16:   */   public MultiModuleScriptProvider(Iterable<? extends ModuleScriptProvider> providers)
/* 17:   */   {
/* 18:27 */     List<ModuleScriptProvider> l = new LinkedList();
/* 19:28 */     for (ModuleScriptProvider provider : providers) {
/* 20:29 */       l.add(provider);
/* 21:   */     }
/* 22:31 */     this.providers = ((ModuleScriptProvider[])l.toArray(new ModuleScriptProvider[l.size()]));
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ModuleScript getModuleScript(Context cx, String moduleId, URI uri, Scriptable paths)
/* 26:   */     throws Exception
/* 27:   */   {
/* 28:36 */     for (ModuleScriptProvider provider : this.providers)
/* 29:   */     {
/* 30:37 */       ModuleScript script = provider.getModuleScript(cx, moduleId, uri, paths);
/* 31:39 */       if (script != null) {
/* 32:40 */         return script;
/* 33:   */       }
/* 34:   */     }
/* 35:43 */     return null;
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.MultiModuleScriptProvider
 * JD-Core Version:    0.7.0.1
 */
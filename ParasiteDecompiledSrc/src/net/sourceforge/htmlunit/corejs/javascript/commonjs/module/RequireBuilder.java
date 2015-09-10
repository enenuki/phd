/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  7:   */ 
/*  8:   */ public class RequireBuilder
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 1L;
/* 12:23 */   private boolean sandboxed = true;
/* 13:   */   private ModuleScriptProvider moduleScriptProvider;
/* 14:   */   private Script preExec;
/* 15:   */   private Script postExec;
/* 16:   */   
/* 17:   */   public RequireBuilder setModuleScriptProvider(ModuleScriptProvider moduleScriptProvider)
/* 18:   */   {
/* 19:38 */     this.moduleScriptProvider = moduleScriptProvider;
/* 20:39 */     return this;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public RequireBuilder setPostExec(Script postExec)
/* 24:   */   {
/* 25:49 */     this.postExec = postExec;
/* 26:50 */     return this;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public RequireBuilder setPreExec(Script preExec)
/* 30:   */   {
/* 31:60 */     this.preExec = preExec;
/* 32:61 */     return this;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public RequireBuilder setSandboxed(boolean sandboxed)
/* 36:   */   {
/* 37:73 */     this.sandboxed = sandboxed;
/* 38:74 */     return this;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Require createRequire(Context cx, Scriptable globalScope)
/* 42:   */   {
/* 43:87 */     return new Require(cx, globalScope, this.moduleScriptProvider, this.preExec, this.postExec, this.sandboxed);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.RequireBuilder
 * JD-Core Version:    0.7.0.1
 */
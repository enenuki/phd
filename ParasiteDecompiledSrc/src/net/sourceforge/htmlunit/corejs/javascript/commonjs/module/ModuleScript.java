/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.net.URI;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*  6:   */ 
/*  7:   */ public class ModuleScript
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 1L;
/* 11:   */   private final Script script;
/* 12:   */   private final URI uri;
/* 13:   */   private final URI base;
/* 14:   */   
/* 15:   */   public ModuleScript(Script script, URI uri, URI base)
/* 16:   */   {
/* 17:30 */     this.script = script;
/* 18:31 */     this.uri = uri;
/* 19:32 */     this.base = base;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Script getScript()
/* 23:   */   {
/* 24:40 */     return this.script;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public URI getUri()
/* 28:   */   {
/* 29:48 */     return this.uri;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public URI getBase()
/* 33:   */   {
/* 34:57 */     return this.base;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean isSandboxed()
/* 38:   */   {
/* 39:66 */     return (this.base != null) && (this.uri != null) && (!this.base.relativize(this.uri).isAbsolute());
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.ModuleScript
 * JD-Core Version:    0.7.0.1
 */
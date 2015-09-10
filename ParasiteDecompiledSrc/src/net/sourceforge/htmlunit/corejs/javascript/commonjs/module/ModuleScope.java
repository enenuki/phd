/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module;
/*  2:   */ 
/*  3:   */ import java.net.URI;
/*  4:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.TopLevel;
/*  6:   */ 
/*  7:   */ public class ModuleScope
/*  8:   */   extends TopLevel
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 1L;
/* 11:   */   private final URI uri;
/* 12:   */   private final URI base;
/* 13:   */   
/* 14:   */   public ModuleScope(Scriptable prototype, URI uri, URI base)
/* 15:   */   {
/* 16:21 */     this.uri = uri;
/* 17:22 */     this.base = base;
/* 18:23 */     setPrototype(prototype);
/* 19:24 */     cacheBuiltins();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public URI getUri()
/* 23:   */   {
/* 24:28 */     return this.uri;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public URI getBase()
/* 28:   */   {
/* 29:32 */     return this.base;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.ModuleScope
 * JD-Core Version:    0.7.0.1
 */
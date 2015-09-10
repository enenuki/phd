/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.URI;
/*  5:   */ import java.net.URISyntaxException;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  7:   */ 
/*  8:   */ public abstract interface ModuleSourceProvider
/*  9:   */ {
/* 10:22 */   public static final ModuleSource NOT_MODIFIED = new ModuleSource(null, null, null, null, null);
/* 11:   */   
/* 12:   */   public abstract ModuleSource loadSource(String paramString, Scriptable paramScriptable, Object paramObject)
/* 13:   */     throws IOException, URISyntaxException;
/* 14:   */   
/* 15:   */   public abstract ModuleSource loadSource(URI paramURI, Object paramObject)
/* 16:   */     throws IOException, URISyntaxException;
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.ModuleSourceProvider
 * JD-Core Version:    0.7.0.1
 */
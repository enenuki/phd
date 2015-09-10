/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.net.MalformedURLException;
/*   7:    */ import java.net.URI;
/*   8:    */ import java.net.URISyntaxException;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  12:    */ 
/*  13:    */ public abstract class ModuleSourceProviderBase
/*  14:    */   implements ModuleSourceProvider, Serializable
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = 1L;
/*  17:    */   
/*  18:    */   public ModuleSource loadSource(String moduleId, Scriptable paths, Object validator)
/*  19:    */     throws IOException, URISyntaxException
/*  20:    */   {
/*  21: 33 */     if (!entityNeedsRevalidation(validator)) {
/*  22: 34 */       return NOT_MODIFIED;
/*  23:    */     }
/*  24: 37 */     ModuleSource moduleSource = loadFromPrivilegedLocations(moduleId, validator);
/*  25: 39 */     if (moduleSource != null) {
/*  26: 40 */       return moduleSource;
/*  27:    */     }
/*  28: 42 */     if (paths != null)
/*  29:    */     {
/*  30: 43 */       moduleSource = loadFromPathArray(moduleId, paths, validator);
/*  31: 45 */       if (moduleSource != null) {
/*  32: 46 */         return moduleSource;
/*  33:    */       }
/*  34:    */     }
/*  35: 49 */     return loadFromFallbackLocations(moduleId, validator);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ModuleSource loadSource(URI uri, Object validator)
/*  39:    */     throws IOException, URISyntaxException
/*  40:    */   {
/*  41: 54 */     return loadFromUri(uri, null, validator);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private ModuleSource loadFromPathArray(String moduleId, Scriptable paths, Object validator)
/*  45:    */     throws IOException
/*  46:    */   {
/*  47: 60 */     long llength = ScriptRuntime.toUint32(ScriptableObject.getProperty(paths, "length"));
/*  48:    */     
/*  49:    */ 
/*  50: 63 */     int ilength = llength > 2147483647L ? 2147483647 : (int)llength;
/*  51: 66 */     for (int i = 0; i < ilength; i++)
/*  52:    */     {
/*  53: 67 */       String path = ensureTrailingSlash((String)ScriptableObject.getTypedProperty(paths, i, String.class));
/*  54:    */       try
/*  55:    */       {
/*  56: 70 */         URI uri = new URI(path);
/*  57: 71 */         if (!uri.isAbsolute()) {
/*  58: 72 */           uri = new File(path).toURI().resolve("");
/*  59:    */         }
/*  60: 74 */         ModuleSource moduleSource = loadFromUri(uri.resolve(moduleId), uri, validator);
/*  61: 76 */         if (moduleSource != null) {
/*  62: 77 */           return moduleSource;
/*  63:    */         }
/*  64:    */       }
/*  65:    */       catch (URISyntaxException e)
/*  66:    */       {
/*  67: 81 */         throw new MalformedURLException(e.getMessage());
/*  68:    */       }
/*  69:    */     }
/*  70: 84 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static String ensureTrailingSlash(String path)
/*  74:    */   {
/*  75: 88 */     return path.endsWith("/") ? path : path.concat("/");
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected boolean entityNeedsRevalidation(Object validator)
/*  79:    */   {
/*  80:104 */     return true;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected abstract ModuleSource loadFromUri(URI paramURI1, URI paramURI2, Object paramObject)
/*  84:    */     throws IOException, URISyntaxException;
/*  85:    */   
/*  86:    */   protected ModuleSource loadFromPrivilegedLocations(String moduleId, Object validator)
/*  87:    */     throws IOException, URISyntaxException
/*  88:    */   {
/*  89:145 */     return null;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected ModuleSource loadFromFallbackLocations(String moduleId, Object validator)
/*  93:    */     throws IOException, URISyntaxException
/*  94:    */   {
/*  95:166 */     return null;
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.ModuleSourceProviderBase
 * JD-Core Version:    0.7.0.1
 */
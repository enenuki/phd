/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider;
/*   2:    */ 
/*   3:    */ import java.io.Reader;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.net.URI;
/*   6:    */ 
/*   7:    */ public class ModuleSource
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 1L;
/*  11:    */   private final Reader reader;
/*  12:    */   private final Object securityDomain;
/*  13:    */   private final URI uri;
/*  14:    */   private final URI base;
/*  15:    */   private final Object validator;
/*  16:    */   
/*  17:    */   public ModuleSource(Reader reader, Object securityDomain, URI uri, URI base, Object validator)
/*  18:    */   {
/*  19: 49 */     this.reader = reader;
/*  20: 50 */     this.securityDomain = securityDomain;
/*  21: 51 */     this.uri = uri;
/*  22: 52 */     this.base = base;
/*  23: 53 */     this.validator = validator;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Reader getReader()
/*  27:    */   {
/*  28: 63 */     return this.reader;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Object getSecurityDomain()
/*  32:    */   {
/*  33: 73 */     return this.securityDomain;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public URI getUri()
/*  37:    */   {
/*  38: 81 */     return this.uri;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public URI getBase()
/*  42:    */   {
/*  43: 90 */     return this.base;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Object getValidator()
/*  47:    */   {
/*  48:100 */     return this.validator;
/*  49:    */   }
/*  50:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.commonjs.module.provider.ModuleSource
 * JD-Core Version:    0.7.0.1
 */
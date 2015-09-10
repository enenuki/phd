/*   1:    */ package org.apache.http.auth;
/*   2:    */ 
/*   3:    */ import org.apache.http.annotation.NotThreadSafe;
/*   4:    */ 
/*   5:    */ @NotThreadSafe
/*   6:    */ public class AuthState
/*   7:    */ {
/*   8:    */   private AuthScheme authScheme;
/*   9:    */   private AuthScope authScope;
/*  10:    */   private Credentials credentials;
/*  11:    */   
/*  12:    */   public void invalidate()
/*  13:    */   {
/*  14: 63 */     this.authScheme = null;
/*  15: 64 */     this.authScope = null;
/*  16: 65 */     this.credentials = null;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean isValid()
/*  20:    */   {
/*  21: 69 */     return this.authScheme != null;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setAuthScheme(AuthScheme authScheme)
/*  25:    */   {
/*  26: 78 */     if (authScheme == null)
/*  27:    */     {
/*  28: 79 */       invalidate();
/*  29: 80 */       return;
/*  30:    */     }
/*  31: 82 */     this.authScheme = authScheme;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public AuthScheme getAuthScheme()
/*  35:    */   {
/*  36: 91 */     return this.authScheme;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Credentials getCredentials()
/*  40:    */   {
/*  41:101 */     return this.credentials;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setCredentials(Credentials credentials)
/*  45:    */   {
/*  46:111 */     this.credentials = credentials;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public AuthScope getAuthScope()
/*  50:    */   {
/*  51:121 */     return this.authScope;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setAuthScope(AuthScope authScope)
/*  55:    */   {
/*  56:130 */     this.authScope = authScope;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String toString()
/*  60:    */   {
/*  61:136 */     StringBuilder buffer = new StringBuilder();
/*  62:137 */     buffer.append("auth scope [");
/*  63:138 */     buffer.append(this.authScope);
/*  64:139 */     buffer.append("]; credentials set [");
/*  65:140 */     buffer.append(this.credentials != null ? "true" : "false");
/*  66:141 */     buffer.append("]");
/*  67:142 */     return buffer.toString();
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.AuthState
 * JD-Core Version:    0.7.0.1
 */
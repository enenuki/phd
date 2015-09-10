/*   1:    */ package org.apache.http.auth;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.security.Principal;
/*   5:    */ import org.apache.http.annotation.Immutable;
/*   6:    */ import org.apache.http.util.LangUtils;
/*   7:    */ 
/*   8:    */ @Immutable
/*   9:    */ public class UsernamePasswordCredentials
/*  10:    */   implements Credentials, Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 243343858802739403L;
/*  13:    */   private final BasicUserPrincipal principal;
/*  14:    */   private final String password;
/*  15:    */   
/*  16:    */   public UsernamePasswordCredentials(String usernamePassword)
/*  17:    */   {
/*  18: 58 */     if (usernamePassword == null) {
/*  19: 59 */       throw new IllegalArgumentException("Username:password string may not be null");
/*  20:    */     }
/*  21: 61 */     int atColon = usernamePassword.indexOf(':');
/*  22: 62 */     if (atColon >= 0)
/*  23:    */     {
/*  24: 63 */       this.principal = new BasicUserPrincipal(usernamePassword.substring(0, atColon));
/*  25: 64 */       this.password = usernamePassword.substring(atColon + 1);
/*  26:    */     }
/*  27:    */     else
/*  28:    */     {
/*  29: 66 */       this.principal = new BasicUserPrincipal(usernamePassword);
/*  30: 67 */       this.password = null;
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public UsernamePasswordCredentials(String userName, String password)
/*  35:    */   {
/*  36: 80 */     if (userName == null) {
/*  37: 81 */       throw new IllegalArgumentException("Username may not be null");
/*  38:    */     }
/*  39: 83 */     this.principal = new BasicUserPrincipal(userName);
/*  40: 84 */     this.password = password;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Principal getUserPrincipal()
/*  44:    */   {
/*  45: 88 */     return this.principal;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getUserName()
/*  49:    */   {
/*  50: 92 */     return this.principal.getName();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getPassword()
/*  54:    */   {
/*  55: 96 */     return this.password;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int hashCode()
/*  59:    */   {
/*  60:101 */     return this.principal.hashCode();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean equals(Object o)
/*  64:    */   {
/*  65:106 */     if (this == o) {
/*  66:106 */       return true;
/*  67:    */     }
/*  68:107 */     if ((o instanceof UsernamePasswordCredentials))
/*  69:    */     {
/*  70:108 */       UsernamePasswordCredentials that = (UsernamePasswordCredentials)o;
/*  71:109 */       if (LangUtils.equals(this.principal, that.principal)) {
/*  72:110 */         return true;
/*  73:    */       }
/*  74:    */     }
/*  75:113 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String toString()
/*  79:    */   {
/*  80:118 */     return this.principal.toString();
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.UsernamePasswordCredentials
 * JD-Core Version:    0.7.0.1
 */
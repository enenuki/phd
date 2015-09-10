/*   1:    */ package org.apache.http.auth;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.security.Principal;
/*   5:    */ import java.util.Locale;
/*   6:    */ import org.apache.http.annotation.Immutable;
/*   7:    */ import org.apache.http.util.LangUtils;
/*   8:    */ 
/*   9:    */ @Immutable
/*  10:    */ public class NTCredentials
/*  11:    */   implements Credentials, Serializable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -7385699315228907265L;
/*  14:    */   private final NTUserPrincipal principal;
/*  15:    */   private final String password;
/*  16:    */   private final String workstation;
/*  17:    */   
/*  18:    */   public NTCredentials(String usernamePassword)
/*  19:    */   {
/*  20: 65 */     if (usernamePassword == null) {
/*  21: 66 */       throw new IllegalArgumentException("Username:password string may not be null");
/*  22:    */     }
/*  23: 69 */     int atColon = usernamePassword.indexOf(':');
/*  24:    */     String username;
/*  25: 70 */     if (atColon >= 0)
/*  26:    */     {
/*  27: 71 */       String username = usernamePassword.substring(0, atColon);
/*  28: 72 */       this.password = usernamePassword.substring(atColon + 1);
/*  29:    */     }
/*  30:    */     else
/*  31:    */     {
/*  32: 74 */       username = usernamePassword;
/*  33: 75 */       this.password = null;
/*  34:    */     }
/*  35: 77 */     int atSlash = username.indexOf('/');
/*  36: 78 */     if (atSlash >= 0) {
/*  37: 79 */       this.principal = new NTUserPrincipal(username.substring(0, atSlash).toUpperCase(Locale.ENGLISH), username.substring(atSlash + 1));
/*  38:    */     } else {
/*  39: 83 */       this.principal = new NTUserPrincipal(null, username.substring(atSlash + 1));
/*  40:    */     }
/*  41: 87 */     this.workstation = null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public NTCredentials(String userName, String password, String workstation, String domain)
/*  45:    */   {
/*  46:105 */     if (userName == null) {
/*  47:106 */       throw new IllegalArgumentException("User name may not be null");
/*  48:    */     }
/*  49:108 */     this.principal = new NTUserPrincipal(domain, userName);
/*  50:109 */     this.password = password;
/*  51:110 */     if (workstation != null) {
/*  52:111 */       this.workstation = workstation.toUpperCase(Locale.ENGLISH);
/*  53:    */     } else {
/*  54:113 */       this.workstation = null;
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Principal getUserPrincipal()
/*  59:    */   {
/*  60:118 */     return this.principal;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getUserName()
/*  64:    */   {
/*  65:122 */     return this.principal.getUsername();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getPassword()
/*  69:    */   {
/*  70:126 */     return this.password;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getDomain()
/*  74:    */   {
/*  75:135 */     return this.principal.getDomain();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getWorkstation()
/*  79:    */   {
/*  80:144 */     return this.workstation;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int hashCode()
/*  84:    */   {
/*  85:149 */     int hash = 17;
/*  86:150 */     hash = LangUtils.hashCode(hash, this.principal);
/*  87:151 */     hash = LangUtils.hashCode(hash, this.workstation);
/*  88:152 */     return hash;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean equals(Object o)
/*  92:    */   {
/*  93:157 */     if (this == o) {
/*  94:157 */       return true;
/*  95:    */     }
/*  96:158 */     if ((o instanceof NTCredentials))
/*  97:    */     {
/*  98:159 */       NTCredentials that = (NTCredentials)o;
/*  99:160 */       if ((LangUtils.equals(this.principal, that.principal)) && (LangUtils.equals(this.workstation, that.workstation))) {
/* 100:162 */         return true;
/* 101:    */       }
/* 102:    */     }
/* 103:165 */     return false;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String toString()
/* 107:    */   {
/* 108:170 */     StringBuilder buffer = new StringBuilder();
/* 109:171 */     buffer.append("[principal: ");
/* 110:172 */     buffer.append(this.principal);
/* 111:173 */     buffer.append("][workstation: ");
/* 112:174 */     buffer.append(this.workstation);
/* 113:175 */     buffer.append("]");
/* 114:176 */     return buffer.toString();
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.NTCredentials
 * JD-Core Version:    0.7.0.1
 */
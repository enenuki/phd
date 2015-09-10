/*   1:    */ package org.apache.http.auth;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.security.Principal;
/*   5:    */ import java.util.Locale;
/*   6:    */ import org.apache.http.annotation.Immutable;
/*   7:    */ import org.apache.http.util.LangUtils;
/*   8:    */ 
/*   9:    */ @Immutable
/*  10:    */ public class NTUserPrincipal
/*  11:    */   implements Principal, Serializable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -6870169797924406894L;
/*  14:    */   private final String username;
/*  15:    */   private final String domain;
/*  16:    */   private final String ntname;
/*  17:    */   
/*  18:    */   public NTUserPrincipal(String domain, String username)
/*  19:    */   {
/*  20: 55 */     if (username == null) {
/*  21: 56 */       throw new IllegalArgumentException("User name may not be null");
/*  22:    */     }
/*  23: 58 */     this.username = username;
/*  24: 59 */     if (domain != null) {
/*  25: 60 */       this.domain = domain.toUpperCase(Locale.ENGLISH);
/*  26:    */     } else {
/*  27: 62 */       this.domain = null;
/*  28:    */     }
/*  29: 64 */     if ((this.domain != null) && (this.domain.length() > 0))
/*  30:    */     {
/*  31: 65 */       StringBuilder buffer = new StringBuilder();
/*  32: 66 */       buffer.append(this.domain);
/*  33: 67 */       buffer.append('/');
/*  34: 68 */       buffer.append(this.username);
/*  35: 69 */       this.ntname = buffer.toString();
/*  36:    */     }
/*  37:    */     else
/*  38:    */     {
/*  39: 71 */       this.ntname = this.username;
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getName()
/*  44:    */   {
/*  45: 76 */     return this.ntname;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getDomain()
/*  49:    */   {
/*  50: 80 */     return this.domain;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getUsername()
/*  54:    */   {
/*  55: 84 */     return this.username;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int hashCode()
/*  59:    */   {
/*  60: 89 */     int hash = 17;
/*  61: 90 */     hash = LangUtils.hashCode(hash, this.username);
/*  62: 91 */     hash = LangUtils.hashCode(hash, this.domain);
/*  63: 92 */     return hash;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean equals(Object o)
/*  67:    */   {
/*  68: 97 */     if (this == o) {
/*  69: 97 */       return true;
/*  70:    */     }
/*  71: 98 */     if ((o instanceof NTUserPrincipal))
/*  72:    */     {
/*  73: 99 */       NTUserPrincipal that = (NTUserPrincipal)o;
/*  74:100 */       if ((LangUtils.equals(this.username, that.username)) && (LangUtils.equals(this.domain, that.domain))) {
/*  75:102 */         return true;
/*  76:    */       }
/*  77:    */     }
/*  78:105 */     return false;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String toString()
/*  82:    */   {
/*  83:110 */     return this.ntname;
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.NTUserPrincipal
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.apache.http.auth;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.security.Principal;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ import org.apache.http.util.LangUtils;
/*  7:   */ 
/*  8:   */ @Immutable
/*  9:   */ public final class BasicUserPrincipal
/* 10:   */   implements Principal, Serializable
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = -2266305184969850467L;
/* 13:   */   private final String username;
/* 14:   */   
/* 15:   */   public BasicUserPrincipal(String username)
/* 16:   */   {
/* 17:50 */     if (username == null) {
/* 18:51 */       throw new IllegalArgumentException("User name may not be null");
/* 19:   */     }
/* 20:53 */     this.username = username;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:57 */     return this.username;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int hashCode()
/* 29:   */   {
/* 30:62 */     int hash = 17;
/* 31:63 */     hash = LangUtils.hashCode(hash, this.username);
/* 32:64 */     return hash;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean equals(Object o)
/* 36:   */   {
/* 37:69 */     if (this == o) {
/* 38:69 */       return true;
/* 39:   */     }
/* 40:70 */     if ((o instanceof BasicUserPrincipal))
/* 41:   */     {
/* 42:71 */       BasicUserPrincipal that = (BasicUserPrincipal)o;
/* 43:72 */       if (LangUtils.equals(this.username, that.username)) {
/* 44:73 */         return true;
/* 45:   */       }
/* 46:   */     }
/* 47:76 */     return false;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String toString()
/* 51:   */   {
/* 52:81 */     StringBuilder buffer = new StringBuilder();
/* 53:82 */     buffer.append("[principal: ");
/* 54:83 */     buffer.append(this.username);
/* 55:84 */     buffer.append("]");
/* 56:85 */     return buffer.toString();
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.auth.BasicUserPrincipal
 * JD-Core Version:    0.7.0.1
 */
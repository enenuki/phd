/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ public abstract class NtlmAuthenticator
/*  4:   */ {
/*  5:   */   private static NtlmAuthenticator auth;
/*  6:   */   private String url;
/*  7:   */   private SmbAuthException sae;
/*  8:   */   
/*  9:   */   private void reset()
/* 10:   */   {
/* 11:33 */     this.url = null;
/* 12:34 */     this.sae = null;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static synchronized void setDefault(NtlmAuthenticator a)
/* 16:   */   {
/* 17:42 */     if (auth != null) {
/* 18:43 */       return;
/* 19:   */     }
/* 20:45 */     auth = a;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected final String getRequestingURL()
/* 24:   */   {
/* 25:49 */     return this.url;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected final SmbAuthException getRequestingException()
/* 29:   */   {
/* 30:52 */     return this.sae;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static NtlmPasswordAuthentication requestNtlmPasswordAuthentication(String url, SmbAuthException sae)
/* 34:   */   {
/* 35:61 */     if (auth == null) {
/* 36:62 */       return null;
/* 37:   */     }
/* 38:64 */     synchronized (auth)
/* 39:   */     {
/* 40:65 */       auth.url = url;
/* 41:66 */       auth.sae = sae;
/* 42:67 */       return auth.getNtlmPasswordAuthentication();
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected NtlmPasswordAuthentication getNtlmPasswordAuthentication()
/* 47:   */   {
/* 48:75 */     return null;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NtlmAuthenticator
 * JD-Core Version:    0.7.0.1
 */
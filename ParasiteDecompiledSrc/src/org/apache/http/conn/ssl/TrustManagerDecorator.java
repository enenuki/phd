/*  1:   */ package org.apache.http.conn.ssl;
/*  2:   */ 
/*  3:   */ import java.security.cert.CertificateException;
/*  4:   */ import java.security.cert.X509Certificate;
/*  5:   */ import javax.net.ssl.X509TrustManager;
/*  6:   */ 
/*  7:   */ class TrustManagerDecorator
/*  8:   */   implements X509TrustManager
/*  9:   */ {
/* 10:   */   private final X509TrustManager trustManager;
/* 11:   */   private final TrustStrategy trustStrategy;
/* 12:   */   
/* 13:   */   TrustManagerDecorator(X509TrustManager trustManager, TrustStrategy trustStrategy)
/* 14:   */   {
/* 15:44 */     this.trustManager = trustManager;
/* 16:45 */     this.trustStrategy = trustStrategy;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void checkClientTrusted(X509Certificate[] chain, String authType)
/* 20:   */     throws CertificateException
/* 21:   */   {
/* 22:50 */     this.trustManager.checkClientTrusted(chain, authType);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void checkServerTrusted(X509Certificate[] chain, String authType)
/* 26:   */     throws CertificateException
/* 27:   */   {
/* 28:55 */     if (!this.trustStrategy.isTrusted(chain, authType)) {
/* 29:56 */       this.trustManager.checkServerTrusted(chain, authType);
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public X509Certificate[] getAcceptedIssuers()
/* 34:   */   {
/* 35:61 */     return this.trustManager.getAcceptedIssuers();
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.ssl.TrustManagerDecorator
 * JD-Core Version:    0.7.0.1
 */
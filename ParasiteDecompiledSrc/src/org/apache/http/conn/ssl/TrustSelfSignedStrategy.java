/*  1:   */ package org.apache.http.conn.ssl;
/*  2:   */ 
/*  3:   */ import java.security.cert.CertificateException;
/*  4:   */ import java.security.cert.X509Certificate;
/*  5:   */ 
/*  6:   */ public class TrustSelfSignedStrategy
/*  7:   */   implements TrustStrategy
/*  8:   */ {
/*  9:   */   public boolean isTrusted(X509Certificate[] chain, String authType)
/* 10:   */     throws CertificateException
/* 11:   */   {
/* 12:42 */     return chain.length == 1;
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.ssl.TrustSelfSignedStrategy
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.security.cert.CertificateException;
/*  4:   */ import java.security.cert.X509Certificate;
/*  5:   */ import javax.net.ssl.X509TrustManager;
/*  6:   */ 
/*  7:   */ class InsecureTrustManager
/*  8:   */   implements X509TrustManager
/*  9:   */ {
/* 10:   */   public void checkClientTrusted(X509Certificate[] chain, String authType)
/* 11:   */     throws CertificateException
/* 12:   */   {}
/* 13:   */   
/* 14:   */   public void checkServerTrusted(X509Certificate[] chain, String authType)
/* 15:   */     throws CertificateException
/* 16:   */   {}
/* 17:   */   
/* 18:   */   public X509Certificate[] getAcceptedIssuers()
/* 19:   */   {
/* 20:88 */     return new X509Certificate[0];
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.InsecureTrustManager
 * JD-Core Version:    0.7.0.1
 */
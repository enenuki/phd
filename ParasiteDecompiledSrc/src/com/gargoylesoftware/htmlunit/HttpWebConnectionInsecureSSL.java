/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.security.GeneralSecurityException;
/*  4:   */ import javax.net.ssl.SSLContext;
/*  5:   */ import javax.net.ssl.TrustManager;
/*  6:   */ import org.apache.http.conn.ClientConnectionManager;
/*  7:   */ import org.apache.http.conn.scheme.Scheme;
/*  8:   */ import org.apache.http.conn.scheme.SchemeRegistry;
/*  9:   */ import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
/* 10:   */ import org.apache.http.conn.ssl.SSLSocketFactory;
/* 11:   */ import org.apache.http.impl.client.AbstractHttpClient;
/* 12:   */ 
/* 13:   */ final class HttpWebConnectionInsecureSSL
/* 14:   */ {
/* 15:   */   static void setUseInsecureSSL(AbstractHttpClient httpClient, boolean useInsecureSSL)
/* 16:   */     throws GeneralSecurityException
/* 17:   */   {
/* 18:45 */     if (useInsecureSSL)
/* 19:   */     {
/* 20:46 */       SSLContext sslContext = SSLContext.getInstance("SSL");
/* 21:47 */       sslContext.init(null, new TrustManager[] { new InsecureTrustManager() }, null);
/* 22:48 */       SSLSocketFactory factory = new SSLSocketFactory(sslContext, new AllowAllHostnameVerifier());
/* 23:49 */       Scheme https = new Scheme("https", 443, factory);
/* 24:   */       
/* 25:51 */       SchemeRegistry schemeRegistry = httpClient.getConnectionManager().getSchemeRegistry();
/* 26:52 */       schemeRegistry.register(https);
/* 27:   */     }
/* 28:   */     else
/* 29:   */     {
/* 30:55 */       SchemeRegistry schemeRegistry = httpClient.getConnectionManager().getSchemeRegistry();
/* 31:56 */       schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.HttpWebConnectionInsecureSSL
 * JD-Core Version:    0.7.0.1
 */
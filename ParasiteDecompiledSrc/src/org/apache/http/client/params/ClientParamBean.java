/*  1:   */ package org.apache.http.client.params;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import org.apache.http.Header;
/*  5:   */ import org.apache.http.HttpHost;
/*  6:   */ import org.apache.http.annotation.NotThreadSafe;
/*  7:   */ import org.apache.http.conn.ClientConnectionManagerFactory;
/*  8:   */ import org.apache.http.params.HttpAbstractParamBean;
/*  9:   */ import org.apache.http.params.HttpParams;
/* 10:   */ 
/* 11:   */ @NotThreadSafe
/* 12:   */ public class ClientParamBean
/* 13:   */   extends HttpAbstractParamBean
/* 14:   */ {
/* 15:   */   public ClientParamBean(HttpParams params)
/* 16:   */   {
/* 17:51 */     super(params);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setConnectionManagerFactoryClassName(String factory)
/* 21:   */   {
/* 22:55 */     this.params.setParameter("http.connection-manager.factory-class-name", factory);
/* 23:   */   }
/* 24:   */   
/* 25:   */   @Deprecated
/* 26:   */   public void setConnectionManagerFactory(ClientConnectionManagerFactory factory)
/* 27:   */   {
/* 28:60 */     this.params.setParameter("http.connection-manager.factory-object", factory);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setHandleRedirects(boolean handle)
/* 32:   */   {
/* 33:64 */     this.params.setBooleanParameter("http.protocol.handle-redirects", handle);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setRejectRelativeRedirect(boolean reject)
/* 37:   */   {
/* 38:68 */     this.params.setBooleanParameter("http.protocol.reject-relative-redirect", reject);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setMaxRedirects(int maxRedirects)
/* 42:   */   {
/* 43:72 */     this.params.setIntParameter("http.protocol.max-redirects", maxRedirects);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setAllowCircularRedirects(boolean allow)
/* 47:   */   {
/* 48:76 */     this.params.setBooleanParameter("http.protocol.allow-circular-redirects", allow);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void setHandleAuthentication(boolean handle)
/* 52:   */   {
/* 53:80 */     this.params.setBooleanParameter("http.protocol.handle-authentication", handle);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setCookiePolicy(String policy)
/* 57:   */   {
/* 58:84 */     this.params.setParameter("http.protocol.cookie-policy", policy);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void setVirtualHost(HttpHost host)
/* 62:   */   {
/* 63:88 */     this.params.setParameter("http.virtual-host", host);
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void setDefaultHeaders(Collection<Header> headers)
/* 67:   */   {
/* 68:92 */     this.params.setParameter("http.default-headers", headers);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void setDefaultHost(HttpHost host)
/* 72:   */   {
/* 73:96 */     this.params.setParameter("http.default-host", host);
/* 74:   */   }
/* 75:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.params.ClientParamBean
 * JD-Core Version:    0.7.0.1
 */
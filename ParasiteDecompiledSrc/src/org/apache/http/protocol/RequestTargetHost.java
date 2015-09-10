/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.net.InetAddress;
/*  5:   */ import org.apache.http.HttpConnection;
/*  6:   */ import org.apache.http.HttpException;
/*  7:   */ import org.apache.http.HttpHost;
/*  8:   */ import org.apache.http.HttpInetConnection;
/*  9:   */ import org.apache.http.HttpRequest;
/* 10:   */ import org.apache.http.HttpRequestInterceptor;
/* 11:   */ import org.apache.http.HttpVersion;
/* 12:   */ import org.apache.http.ProtocolException;
/* 13:   */ import org.apache.http.ProtocolVersion;
/* 14:   */ import org.apache.http.RequestLine;
/* 15:   */ 
/* 16:   */ public class RequestTargetHost
/* 17:   */   implements HttpRequestInterceptor
/* 18:   */ {
/* 19:   */   public void process(HttpRequest request, HttpContext context)
/* 20:   */     throws HttpException, IOException
/* 21:   */   {
/* 22:57 */     if (request == null) {
/* 23:58 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 24:   */     }
/* 25:60 */     if (context == null) {
/* 26:61 */       throw new IllegalArgumentException("HTTP context may not be null");
/* 27:   */     }
/* 28:64 */     ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
/* 29:65 */     String method = request.getRequestLine().getMethod();
/* 30:66 */     if ((method.equalsIgnoreCase("CONNECT")) && (ver.lessEquals(HttpVersion.HTTP_1_0))) {
/* 31:67 */       return;
/* 32:   */     }
/* 33:70 */     if (!request.containsHeader("Host"))
/* 34:   */     {
/* 35:71 */       HttpHost targethost = (HttpHost)context.getAttribute("http.target_host");
/* 36:73 */       if (targethost == null)
/* 37:   */       {
/* 38:74 */         HttpConnection conn = (HttpConnection)context.getAttribute("http.connection");
/* 39:76 */         if ((conn instanceof HttpInetConnection))
/* 40:   */         {
/* 41:79 */           InetAddress address = ((HttpInetConnection)conn).getRemoteAddress();
/* 42:80 */           int port = ((HttpInetConnection)conn).getRemotePort();
/* 43:81 */           if (address != null) {
/* 44:82 */             targethost = new HttpHost(address.getHostName(), port);
/* 45:   */           }
/* 46:   */         }
/* 47:85 */         if (targethost == null)
/* 48:   */         {
/* 49:86 */           if (ver.lessEquals(HttpVersion.HTTP_1_0)) {
/* 50:87 */             return;
/* 51:   */           }
/* 52:89 */           throw new ProtocolException("Target host missing");
/* 53:   */         }
/* 54:   */       }
/* 55:93 */       request.addHeader("Host", targethost.toHostString());
/* 56:   */     }
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.RequestTargetHost
 * JD-Core Version:    0.7.0.1
 */
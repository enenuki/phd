/*  1:   */ package org.apache.http.client.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.commons.logging.Log;
/*  5:   */ import org.apache.commons.logging.LogFactory;
/*  6:   */ import org.apache.http.HttpException;
/*  7:   */ import org.apache.http.HttpRequest;
/*  8:   */ import org.apache.http.HttpRequestInterceptor;
/*  9:   */ import org.apache.http.RequestLine;
/* 10:   */ import org.apache.http.annotation.Immutable;
/* 11:   */ import org.apache.http.conn.HttpRoutedConnection;
/* 12:   */ import org.apache.http.conn.routing.HttpRoute;
/* 13:   */ import org.apache.http.protocol.HttpContext;
/* 14:   */ 
/* 15:   */ @Immutable
/* 16:   */ public class RequestClientConnControl
/* 17:   */   implements HttpRequestInterceptor
/* 18:   */ {
/* 19:55 */   private final Log log = LogFactory.getLog(getClass());
/* 20:   */   private static final String PROXY_CONN_DIRECTIVE = "Proxy-Connection";
/* 21:   */   
/* 22:   */   public void process(HttpRequest request, HttpContext context)
/* 23:   */     throws HttpException, IOException
/* 24:   */   {
/* 25:65 */     if (request == null) {
/* 26:66 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 27:   */     }
/* 28:69 */     String method = request.getRequestLine().getMethod();
/* 29:70 */     if (method.equalsIgnoreCase("CONNECT"))
/* 30:   */     {
/* 31:71 */       request.setHeader("Proxy-Connection", "Keep-Alive");
/* 32:72 */       return;
/* 33:   */     }
/* 34:76 */     HttpRoutedConnection conn = (HttpRoutedConnection)context.getAttribute("http.connection");
/* 35:78 */     if (conn == null)
/* 36:   */     {
/* 37:79 */       this.log.debug("HTTP connection not set in the context");
/* 38:80 */       return;
/* 39:   */     }
/* 40:83 */     HttpRoute route = conn.getRoute();
/* 41:85 */     if (((route.getHopCount() == 1) || (route.isTunnelled())) && 
/* 42:86 */       (!request.containsHeader("Connection"))) {
/* 43:87 */       request.addHeader("Connection", "Keep-Alive");
/* 44:   */     }
/* 45:90 */     if ((route.getHopCount() == 2) && (!route.isTunnelled()) && 
/* 46:91 */       (!request.containsHeader("Proxy-Connection"))) {
/* 47:92 */       request.addHeader("Proxy-Connection", "Keep-Alive");
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.RequestClientConnControl
 * JD-Core Version:    0.7.0.1
 */
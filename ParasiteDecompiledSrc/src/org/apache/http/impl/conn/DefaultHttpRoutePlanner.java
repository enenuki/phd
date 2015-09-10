/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import org.apache.http.HttpException;
/*   5:    */ import org.apache.http.HttpHost;
/*   6:    */ import org.apache.http.HttpRequest;
/*   7:    */ import org.apache.http.annotation.ThreadSafe;
/*   8:    */ import org.apache.http.conn.params.ConnRouteParams;
/*   9:    */ import org.apache.http.conn.routing.HttpRoute;
/*  10:    */ import org.apache.http.conn.routing.HttpRoutePlanner;
/*  11:    */ import org.apache.http.conn.scheme.Scheme;
/*  12:    */ import org.apache.http.conn.scheme.SchemeRegistry;
/*  13:    */ import org.apache.http.protocol.HttpContext;
/*  14:    */ 
/*  15:    */ @ThreadSafe
/*  16:    */ public class DefaultHttpRoutePlanner
/*  17:    */   implements HttpRoutePlanner
/*  18:    */ {
/*  19:    */   protected final SchemeRegistry schemeRegistry;
/*  20:    */   
/*  21:    */   public DefaultHttpRoutePlanner(SchemeRegistry schreg)
/*  22:    */   {
/*  23: 75 */     if (schreg == null) {
/*  24: 76 */       throw new IllegalArgumentException("SchemeRegistry must not be null.");
/*  25:    */     }
/*  26: 79 */     this.schemeRegistry = schreg;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context)
/*  30:    */     throws HttpException
/*  31:    */   {
/*  32: 87 */     if (request == null) {
/*  33: 88 */       throw new IllegalStateException("Request must not be null.");
/*  34:    */     }
/*  35: 93 */     HttpRoute route = ConnRouteParams.getForcedRoute(request.getParams());
/*  36: 95 */     if (route != null) {
/*  37: 96 */       return route;
/*  38:    */     }
/*  39:101 */     if (target == null) {
/*  40:102 */       throw new IllegalStateException("Target host must not be null.");
/*  41:    */     }
/*  42:106 */     InetAddress local = ConnRouteParams.getLocalAddress(request.getParams());
/*  43:    */     
/*  44:108 */     HttpHost proxy = ConnRouteParams.getDefaultProxy(request.getParams());
/*  45:    */     Scheme schm;
/*  46:    */     try
/*  47:    */     {
/*  48:113 */       schm = this.schemeRegistry.getScheme(target.getSchemeName());
/*  49:    */     }
/*  50:    */     catch (IllegalStateException ex)
/*  51:    */     {
/*  52:115 */       throw new HttpException(ex.getMessage());
/*  53:    */     }
/*  54:119 */     boolean secure = schm.isLayered();
/*  55:121 */     if (proxy == null) {
/*  56:122 */       route = new HttpRoute(target, local, secure);
/*  57:    */     } else {
/*  58:124 */       route = new HttpRoute(target, local, proxy, secure);
/*  59:    */     }
/*  60:126 */     return route;
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.DefaultHttpRoutePlanner
 * JD-Core Version:    0.7.0.1
 */
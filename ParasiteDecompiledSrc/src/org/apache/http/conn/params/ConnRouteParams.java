/*   1:    */ package org.apache.http.conn.params;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import org.apache.http.HttpHost;
/*   5:    */ import org.apache.http.annotation.Immutable;
/*   6:    */ import org.apache.http.conn.routing.HttpRoute;
/*   7:    */ import org.apache.http.params.HttpParams;
/*   8:    */ 
/*   9:    */ @Immutable
/*  10:    */ public class ConnRouteParams
/*  11:    */   implements ConnRoutePNames
/*  12:    */ {
/*  13: 51 */   public static final HttpHost NO_HOST = new HttpHost("127.0.0.255", 0, "no-host");
/*  14: 58 */   public static final HttpRoute NO_ROUTE = new HttpRoute(NO_HOST);
/*  15:    */   
/*  16:    */   public static HttpHost getDefaultProxy(HttpParams params)
/*  17:    */   {
/*  18: 77 */     if (params == null) {
/*  19: 78 */       throw new IllegalArgumentException("Parameters must not be null.");
/*  20:    */     }
/*  21: 80 */     HttpHost proxy = (HttpHost)params.getParameter("http.route.default-proxy");
/*  22: 82 */     if ((proxy != null) && (NO_HOST.equals(proxy))) {
/*  23: 84 */       proxy = null;
/*  24:    */     }
/*  25: 86 */     return proxy;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static void setDefaultProxy(HttpParams params, HttpHost proxy)
/*  29:    */   {
/*  30:101 */     if (params == null) {
/*  31:102 */       throw new IllegalArgumentException("Parameters must not be null.");
/*  32:    */     }
/*  33:104 */     params.setParameter("http.route.default-proxy", proxy);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static HttpRoute getForcedRoute(HttpParams params)
/*  37:    */   {
/*  38:119 */     if (params == null) {
/*  39:120 */       throw new IllegalArgumentException("Parameters must not be null.");
/*  40:    */     }
/*  41:122 */     HttpRoute route = (HttpRoute)params.getParameter("http.route.forced-route");
/*  42:124 */     if ((route != null) && (NO_ROUTE.equals(route))) {
/*  43:126 */       route = null;
/*  44:    */     }
/*  45:128 */     return route;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void setForcedRoute(HttpParams params, HttpRoute route)
/*  49:    */   {
/*  50:143 */     if (params == null) {
/*  51:144 */       throw new IllegalArgumentException("Parameters must not be null.");
/*  52:    */     }
/*  53:146 */     params.setParameter("http.route.forced-route", route);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static InetAddress getLocalAddress(HttpParams params)
/*  57:    */   {
/*  58:162 */     if (params == null) {
/*  59:163 */       throw new IllegalArgumentException("Parameters must not be null.");
/*  60:    */     }
/*  61:165 */     InetAddress local = (InetAddress)params.getParameter("http.route.local-address");
/*  62:    */     
/*  63:    */ 
/*  64:168 */     return local;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void setLocalAddress(HttpParams params, InetAddress local)
/*  68:    */   {
/*  69:180 */     if (params == null) {
/*  70:181 */       throw new IllegalArgumentException("Parameters must not be null.");
/*  71:    */     }
/*  72:183 */     params.setParameter("http.route.local-address", local);
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.params.ConnRouteParams
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.apache.http.conn.params;
/*  2:   */ 
/*  3:   */ import java.net.InetAddress;
/*  4:   */ import org.apache.http.HttpHost;
/*  5:   */ import org.apache.http.annotation.NotThreadSafe;
/*  6:   */ import org.apache.http.conn.routing.HttpRoute;
/*  7:   */ import org.apache.http.params.HttpAbstractParamBean;
/*  8:   */ import org.apache.http.params.HttpParams;
/*  9:   */ 
/* 10:   */ @NotThreadSafe
/* 11:   */ public class ConnRouteParamBean
/* 12:   */   extends HttpAbstractParamBean
/* 13:   */ {
/* 14:   */   public ConnRouteParamBean(HttpParams params)
/* 15:   */   {
/* 16:50 */     super(params);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setDefaultProxy(HttpHost defaultProxy)
/* 20:   */   {
/* 21:55 */     this.params.setParameter("http.route.default-proxy", defaultProxy);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setLocalAddress(InetAddress address)
/* 25:   */   {
/* 26:60 */     this.params.setParameter("http.route.local-address", address);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setForcedRoute(HttpRoute route)
/* 30:   */   {
/* 31:65 */     this.params.setParameter("http.route.forced-route", route);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.params.ConnRouteParamBean
 * JD-Core Version:    0.7.0.1
 */
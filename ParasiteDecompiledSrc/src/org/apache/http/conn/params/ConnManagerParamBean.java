/*  1:   */ package org.apache.http.conn.params;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.NotThreadSafe;
/*  4:   */ import org.apache.http.params.HttpAbstractParamBean;
/*  5:   */ import org.apache.http.params.HttpParams;
/*  6:   */ 
/*  7:   */ @Deprecated
/*  8:   */ @NotThreadSafe
/*  9:   */ public class ConnManagerParamBean
/* 10:   */   extends HttpAbstractParamBean
/* 11:   */ {
/* 12:   */   public ConnManagerParamBean(HttpParams params)
/* 13:   */   {
/* 14:47 */     super(params);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setTimeout(long timeout)
/* 18:   */   {
/* 19:51 */     this.params.setLongParameter("http.conn-manager.timeout", timeout);
/* 20:   */   }
/* 21:   */   
/* 22:   */   @Deprecated
/* 23:   */   public void setMaxTotalConnections(int maxConnections)
/* 24:   */   {
/* 25:57 */     this.params.setIntParameter("http.conn-manager.max-total", maxConnections);
/* 26:   */   }
/* 27:   */   
/* 28:   */   @Deprecated
/* 29:   */   public void setConnectionsPerRoute(ConnPerRouteBean connPerRoute)
/* 30:   */   {
/* 31:63 */     this.params.setParameter("http.conn-manager.max-per-route", connPerRoute);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.params.ConnManagerParamBean
 * JD-Core Version:    0.7.0.1
 */
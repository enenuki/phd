/*   1:    */ package org.apache.http.conn.params;
/*   2:    */ 
/*   3:    */ import org.apache.http.annotation.Immutable;
/*   4:    */ import org.apache.http.conn.routing.HttpRoute;
/*   5:    */ import org.apache.http.params.HttpParams;
/*   6:    */ 
/*   7:    */ @Deprecated
/*   8:    */ @Immutable
/*   9:    */ public final class ConnManagerParams
/*  10:    */   implements ConnManagerPNames
/*  11:    */ {
/*  12:    */   public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;
/*  13:    */   
/*  14:    */   @Deprecated
/*  15:    */   public static long getTimeout(HttpParams params)
/*  16:    */   {
/*  17: 65 */     if (params == null) {
/*  18: 66 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  19:    */     }
/*  20: 68 */     Long param = (Long)params.getParameter("http.conn-manager.timeout");
/*  21: 69 */     if (param != null) {
/*  22: 70 */       return param.longValue();
/*  23:    */     }
/*  24: 72 */     return params.getIntParameter("http.connection.timeout", 0);
/*  25:    */   }
/*  26:    */   
/*  27:    */   @Deprecated
/*  28:    */   public static void setTimeout(HttpParams params, long timeout)
/*  29:    */   {
/*  30: 86 */     if (params == null) {
/*  31: 87 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  32:    */     }
/*  33: 89 */     params.setLongParameter("http.conn-manager.timeout", timeout);
/*  34:    */   }
/*  35:    */   
/*  36: 93 */   private static final ConnPerRoute DEFAULT_CONN_PER_ROUTE = new ConnPerRoute()
/*  37:    */   {
/*  38:    */     public int getMaxForRoute(HttpRoute route)
/*  39:    */     {
/*  40: 96 */       return 2;
/*  41:    */     }
/*  42:    */   };
/*  43:    */   
/*  44:    */   @Deprecated
/*  45:    */   public static void setMaxConnectionsPerRoute(HttpParams params, ConnPerRoute connPerRoute)
/*  46:    */   {
/*  47:113 */     if (params == null) {
/*  48:114 */       throw new IllegalArgumentException("HTTP parameters must not be null.");
/*  49:    */     }
/*  50:117 */     params.setParameter("http.conn-manager.max-per-route", connPerRoute);
/*  51:    */   }
/*  52:    */   
/*  53:    */   @Deprecated
/*  54:    */   public static ConnPerRoute getMaxConnectionsPerRoute(HttpParams params)
/*  55:    */   {
/*  56:131 */     if (params == null) {
/*  57:132 */       throw new IllegalArgumentException("HTTP parameters must not be null.");
/*  58:    */     }
/*  59:135 */     ConnPerRoute connPerRoute = (ConnPerRoute)params.getParameter("http.conn-manager.max-per-route");
/*  60:136 */     if (connPerRoute == null) {
/*  61:137 */       connPerRoute = DEFAULT_CONN_PER_ROUTE;
/*  62:    */     }
/*  63:139 */     return connPerRoute;
/*  64:    */   }
/*  65:    */   
/*  66:    */   @Deprecated
/*  67:    */   public static void setMaxTotalConnections(HttpParams params, int maxTotalConnections)
/*  68:    */   {
/*  69:154 */     if (params == null) {
/*  70:155 */       throw new IllegalArgumentException("HTTP parameters must not be null.");
/*  71:    */     }
/*  72:158 */     params.setIntParameter("http.conn-manager.max-total", maxTotalConnections);
/*  73:    */   }
/*  74:    */   
/*  75:    */   @Deprecated
/*  76:    */   public static int getMaxTotalConnections(HttpParams params)
/*  77:    */   {
/*  78:173 */     if (params == null) {
/*  79:174 */       throw new IllegalArgumentException("HTTP parameters must not be null.");
/*  80:    */     }
/*  81:177 */     return params.getIntParameter("http.conn-manager.max-total", 20);
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.params.ConnManagerParams
 * JD-Core Version:    0.7.0.1
 */
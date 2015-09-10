/*   1:    */ package org.apache.http.conn.params;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import java.util.concurrent.ConcurrentHashMap;
/*   5:    */ import org.apache.http.annotation.ThreadSafe;
/*   6:    */ import org.apache.http.conn.routing.HttpRoute;
/*   7:    */ 
/*   8:    */ @ThreadSafe
/*   9:    */ public final class ConnPerRouteBean
/*  10:    */   implements ConnPerRoute
/*  11:    */ {
/*  12:    */   public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
/*  13:    */   private final ConcurrentHashMap<HttpRoute, Integer> maxPerHostMap;
/*  14:    */   private volatile int defaultMax;
/*  15:    */   
/*  16:    */   public ConnPerRouteBean(int defaultMax)
/*  17:    */   {
/*  18: 56 */     this.maxPerHostMap = new ConcurrentHashMap();
/*  19: 57 */     setDefaultMaxPerRoute(defaultMax);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ConnPerRouteBean()
/*  23:    */   {
/*  24: 61 */     this(2);
/*  25:    */   }
/*  26:    */   
/*  27:    */   @Deprecated
/*  28:    */   public int getDefaultMax()
/*  29:    */   {
/*  30: 69 */     return this.defaultMax;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getDefaultMaxPerRoute()
/*  34:    */   {
/*  35: 76 */     return this.defaultMax;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setDefaultMaxPerRoute(int max)
/*  39:    */   {
/*  40: 80 */     if (max < 1) {
/*  41: 81 */       throw new IllegalArgumentException("The maximum must be greater than 0.");
/*  42:    */     }
/*  43: 84 */     this.defaultMax = max;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setMaxForRoute(HttpRoute route, int max)
/*  47:    */   {
/*  48: 88 */     if (route == null) {
/*  49: 89 */       throw new IllegalArgumentException("HTTP route may not be null.");
/*  50:    */     }
/*  51: 92 */     if (max < 1) {
/*  52: 93 */       throw new IllegalArgumentException("The maximum must be greater than 0.");
/*  53:    */     }
/*  54: 96 */     this.maxPerHostMap.put(route, Integer.valueOf(max));
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getMaxForRoute(HttpRoute route)
/*  58:    */   {
/*  59:100 */     if (route == null) {
/*  60:101 */       throw new IllegalArgumentException("HTTP route may not be null.");
/*  61:    */     }
/*  62:104 */     Integer max = (Integer)this.maxPerHostMap.get(route);
/*  63:105 */     if (max != null) {
/*  64:106 */       return max.intValue();
/*  65:    */     }
/*  66:108 */     return this.defaultMax;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setMaxForRoutes(Map<HttpRoute, Integer> map)
/*  70:    */   {
/*  71:113 */     if (map == null) {
/*  72:114 */       return;
/*  73:    */     }
/*  74:116 */     this.maxPerHostMap.clear();
/*  75:117 */     this.maxPerHostMap.putAll(map);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String toString()
/*  79:    */   {
/*  80:122 */     return this.maxPerHostMap.toString();
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.params.ConnPerRouteBean
 * JD-Core Version:    0.7.0.1
 */
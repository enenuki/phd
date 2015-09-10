/*  1:   */ package org.apache.http.conn.routing;
/*  2:   */ 
/*  3:   */ import java.net.InetAddress;
/*  4:   */ import org.apache.http.HttpHost;
/*  5:   */ 
/*  6:   */ public abstract interface RouteInfo
/*  7:   */ {
/*  8:   */   public abstract HttpHost getTargetHost();
/*  9:   */   
/* 10:   */   public abstract InetAddress getLocalAddress();
/* 11:   */   
/* 12:   */   public abstract int getHopCount();
/* 13:   */   
/* 14:   */   public abstract HttpHost getHopTarget(int paramInt);
/* 15:   */   
/* 16:   */   public abstract HttpHost getProxyHost();
/* 17:   */   
/* 18:   */   public abstract TunnelType getTunnelType();
/* 19:   */   
/* 20:   */   public abstract boolean isTunnelled();
/* 21:   */   
/* 22:   */   public abstract LayerType getLayerType();
/* 23:   */   
/* 24:   */   public abstract boolean isLayered();
/* 25:   */   
/* 26:   */   public abstract boolean isSecure();
/* 27:   */   
/* 28:   */   public static enum TunnelType
/* 29:   */   {
/* 30:49 */     PLAIN,  TUNNELLED;
/* 31:   */     
/* 32:   */     private TunnelType() {}
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static enum LayerType
/* 36:   */   {
/* 37:64 */     PLAIN,  LAYERED;
/* 38:   */     
/* 39:   */     private LayerType() {}
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.routing.RouteInfo
 * JD-Core Version:    0.7.0.1
 */
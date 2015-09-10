/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.HttpHost;
/*   5:    */ import org.apache.http.annotation.NotThreadSafe;
/*   6:    */ import org.apache.http.conn.ClientConnectionOperator;
/*   7:    */ import org.apache.http.conn.OperatedClientConnection;
/*   8:    */ import org.apache.http.conn.routing.HttpRoute;
/*   9:    */ import org.apache.http.conn.routing.RouteTracker;
/*  10:    */ import org.apache.http.params.HttpParams;
/*  11:    */ import org.apache.http.protocol.HttpContext;
/*  12:    */ 
/*  13:    */ @NotThreadSafe
/*  14:    */ public abstract class AbstractPoolEntry
/*  15:    */ {
/*  16:    */   protected final ClientConnectionOperator connOperator;
/*  17:    */   protected final OperatedClientConnection connection;
/*  18:    */   protected volatile HttpRoute route;
/*  19:    */   protected volatile Object state;
/*  20:    */   protected volatile RouteTracker tracker;
/*  21:    */   
/*  22:    */   protected AbstractPoolEntry(ClientConnectionOperator connOperator, HttpRoute route)
/*  23:    */   {
/*  24: 88 */     if (connOperator == null) {
/*  25: 89 */       throw new IllegalArgumentException("Connection operator may not be null");
/*  26:    */     }
/*  27: 91 */     this.connOperator = connOperator;
/*  28: 92 */     this.connection = connOperator.createConnection();
/*  29: 93 */     this.route = route;
/*  30: 94 */     this.tracker = null;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object getState()
/*  34:    */   {
/*  35:103 */     return this.state;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setState(Object state)
/*  39:    */   {
/*  40:112 */     this.state = state;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void open(HttpRoute route, HttpContext context, HttpParams params)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46:128 */     if (route == null) {
/*  47:129 */       throw new IllegalArgumentException("Route must not be null.");
/*  48:    */     }
/*  49:132 */     if (params == null) {
/*  50:133 */       throw new IllegalArgumentException("Parameters must not be null.");
/*  51:    */     }
/*  52:136 */     if ((this.tracker != null) && (this.tracker.isConnected())) {
/*  53:137 */       throw new IllegalStateException("Connection already open.");
/*  54:    */     }
/*  55:146 */     this.tracker = new RouteTracker(route);
/*  56:147 */     HttpHost proxy = route.getProxyHost();
/*  57:    */     
/*  58:149 */     this.connOperator.openConnection(this.connection, proxy != null ? proxy : route.getTargetHost(), route.getLocalAddress(), context, params);
/*  59:    */     
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:155 */     RouteTracker localTracker = this.tracker;
/*  65:159 */     if (localTracker == null) {
/*  66:160 */       throw new IOException("Request aborted");
/*  67:    */     }
/*  68:163 */     if (proxy == null) {
/*  69:164 */       localTracker.connectTarget(this.connection.isSecure());
/*  70:    */     } else {
/*  71:166 */       localTracker.connectProxy(proxy, this.connection.isSecure());
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void tunnelTarget(boolean secure, HttpParams params)
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:185 */     if (params == null) {
/*  79:186 */       throw new IllegalArgumentException("Parameters must not be null.");
/*  80:    */     }
/*  81:190 */     if ((this.tracker == null) || (!this.tracker.isConnected())) {
/*  82:191 */       throw new IllegalStateException("Connection not open.");
/*  83:    */     }
/*  84:193 */     if (this.tracker.isTunnelled()) {
/*  85:194 */       throw new IllegalStateException("Connection is already tunnelled.");
/*  86:    */     }
/*  87:198 */     this.connection.update(null, this.tracker.getTargetHost(), secure, params);
/*  88:    */     
/*  89:200 */     this.tracker.tunnelTarget(secure);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void tunnelProxy(HttpHost next, boolean secure, HttpParams params)
/*  93:    */     throws IOException
/*  94:    */   {
/*  95:221 */     if (next == null) {
/*  96:222 */       throw new IllegalArgumentException("Next proxy must not be null.");
/*  97:    */     }
/*  98:225 */     if (params == null) {
/*  99:226 */       throw new IllegalArgumentException("Parameters must not be null.");
/* 100:    */     }
/* 101:231 */     if ((this.tracker == null) || (!this.tracker.isConnected())) {
/* 102:232 */       throw new IllegalStateException("Connection not open.");
/* 103:    */     }
/* 104:235 */     this.connection.update(null, next, secure, params);
/* 105:236 */     this.tracker.tunnelProxy(next, secure);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void layerProtocol(HttpContext context, HttpParams params)
/* 109:    */     throws IOException
/* 110:    */   {
/* 111:251 */     if (params == null) {
/* 112:252 */       throw new IllegalArgumentException("Parameters must not be null.");
/* 113:    */     }
/* 114:256 */     if ((this.tracker == null) || (!this.tracker.isConnected())) {
/* 115:257 */       throw new IllegalStateException("Connection not open.");
/* 116:    */     }
/* 117:259 */     if (!this.tracker.isTunnelled()) {
/* 118:261 */       throw new IllegalStateException("Protocol layering without a tunnel not supported.");
/* 119:    */     }
/* 120:264 */     if (this.tracker.isLayered()) {
/* 121:265 */       throw new IllegalStateException("Multiple protocol layering not supported.");
/* 122:    */     }
/* 123:275 */     HttpHost target = this.tracker.getTargetHost();
/* 124:    */     
/* 125:277 */     this.connOperator.updateSecureConnection(this.connection, target, context, params);
/* 126:    */     
/* 127:    */ 
/* 128:280 */     this.tracker.layerProtocol(this.connection.isSecure());
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void shutdownEntry()
/* 132:    */   {
/* 133:291 */     this.tracker = null;
/* 134:292 */     this.state = null;
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.AbstractPoolEntry
 * JD-Core Version:    0.7.0.1
 */
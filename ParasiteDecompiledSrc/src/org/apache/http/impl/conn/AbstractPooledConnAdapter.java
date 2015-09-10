/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.HttpHost;
/*   5:    */ import org.apache.http.conn.ClientConnectionManager;
/*   6:    */ import org.apache.http.conn.OperatedClientConnection;
/*   7:    */ import org.apache.http.conn.routing.HttpRoute;
/*   8:    */ import org.apache.http.conn.routing.RouteTracker;
/*   9:    */ import org.apache.http.params.HttpParams;
/*  10:    */ import org.apache.http.protocol.HttpContext;
/*  11:    */ 
/*  12:    */ public abstract class AbstractPooledConnAdapter
/*  13:    */   extends AbstractClientConnAdapter
/*  14:    */ {
/*  15:    */   protected volatile AbstractPoolEntry poolEntry;
/*  16:    */   
/*  17:    */   protected AbstractPooledConnAdapter(ClientConnectionManager manager, AbstractPoolEntry entry)
/*  18:    */   {
/*  19: 63 */     super(manager, entry.connection);
/*  20: 64 */     this.poolEntry = entry;
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected AbstractPoolEntry getPoolEntry()
/*  24:    */   {
/*  25: 73 */     return this.poolEntry;
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected void assertValid(AbstractPoolEntry entry)
/*  29:    */   {
/*  30: 85 */     if ((isReleased()) || (entry == null)) {
/*  31: 86 */       throw new ConnectionShutdownException();
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   @Deprecated
/*  36:    */   protected final void assertAttached()
/*  37:    */   {
/*  38: 95 */     if (this.poolEntry == null) {
/*  39: 96 */       throw new ConnectionShutdownException();
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected synchronized void detach()
/*  44:    */   {
/*  45:106 */     this.poolEntry = null;
/*  46:107 */     super.detach();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public HttpRoute getRoute()
/*  50:    */   {
/*  51:111 */     AbstractPoolEntry entry = getPoolEntry();
/*  52:112 */     assertValid(entry);
/*  53:113 */     return entry.tracker == null ? null : entry.tracker.toRoute();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void open(HttpRoute route, HttpContext context, HttpParams params)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:119 */     AbstractPoolEntry entry = getPoolEntry();
/*  60:120 */     assertValid(entry);
/*  61:121 */     entry.open(route, context, params);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void tunnelTarget(boolean secure, HttpParams params)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67:126 */     AbstractPoolEntry entry = getPoolEntry();
/*  68:127 */     assertValid(entry);
/*  69:128 */     entry.tunnelTarget(secure, params);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void tunnelProxy(HttpHost next, boolean secure, HttpParams params)
/*  73:    */     throws IOException
/*  74:    */   {
/*  75:133 */     AbstractPoolEntry entry = getPoolEntry();
/*  76:134 */     assertValid(entry);
/*  77:135 */     entry.tunnelProxy(next, secure, params);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void layerProtocol(HttpContext context, HttpParams params)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:140 */     AbstractPoolEntry entry = getPoolEntry();
/*  84:141 */     assertValid(entry);
/*  85:142 */     entry.layerProtocol(context, params);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void close()
/*  89:    */     throws IOException
/*  90:    */   {
/*  91:146 */     AbstractPoolEntry entry = getPoolEntry();
/*  92:147 */     if (entry != null) {
/*  93:148 */       entry.shutdownEntry();
/*  94:    */     }
/*  95:150 */     OperatedClientConnection conn = getWrappedConnection();
/*  96:151 */     if (conn != null) {
/*  97:152 */       conn.close();
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void shutdown()
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:157 */     AbstractPoolEntry entry = getPoolEntry();
/* 105:158 */     if (entry != null) {
/* 106:159 */       entry.shutdownEntry();
/* 107:    */     }
/* 108:161 */     OperatedClientConnection conn = getWrappedConnection();
/* 109:162 */     if (conn != null) {
/* 110:163 */       conn.shutdown();
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Object getState()
/* 115:    */   {
/* 116:168 */     AbstractPoolEntry entry = getPoolEntry();
/* 117:169 */     assertValid(entry);
/* 118:170 */     return entry.getState();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setState(Object state)
/* 122:    */   {
/* 123:174 */     AbstractPoolEntry entry = getPoolEntry();
/* 124:175 */     assertValid(entry);
/* 125:176 */     entry.setState(state);
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.AbstractPooledConnAdapter
 * JD-Core Version:    0.7.0.1
 */
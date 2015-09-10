/*   1:    */ package org.apache.http.impl.conn.tsccm;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.concurrent.TimeUnit;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.http.annotation.ThreadSafe;
/*   8:    */ import org.apache.http.conn.ClientConnectionManager;
/*   9:    */ import org.apache.http.conn.ClientConnectionOperator;
/*  10:    */ import org.apache.http.conn.ClientConnectionRequest;
/*  11:    */ import org.apache.http.conn.ConnectionPoolTimeoutException;
/*  12:    */ import org.apache.http.conn.ManagedClientConnection;
/*  13:    */ import org.apache.http.conn.params.ConnPerRouteBean;
/*  14:    */ import org.apache.http.conn.routing.HttpRoute;
/*  15:    */ import org.apache.http.conn.scheme.SchemeRegistry;
/*  16:    */ import org.apache.http.impl.conn.DefaultClientConnectionOperator;
/*  17:    */ import org.apache.http.impl.conn.SchemeRegistryFactory;
/*  18:    */ import org.apache.http.params.HttpParams;
/*  19:    */ 
/*  20:    */ @ThreadSafe
/*  21:    */ public class ThreadSafeClientConnManager
/*  22:    */   implements ClientConnectionManager
/*  23:    */ {
/*  24:    */   private final Log log;
/*  25:    */   protected final SchemeRegistry schemeRegistry;
/*  26:    */   @Deprecated
/*  27:    */   protected final AbstractConnPool connectionPool;
/*  28:    */   protected final ConnPoolByRoute pool;
/*  29:    */   protected final ClientConnectionOperator connOperator;
/*  30:    */   protected final ConnPerRouteBean connPerRoute;
/*  31:    */   
/*  32:    */   public ThreadSafeClientConnManager(SchemeRegistry schreg)
/*  33:    */   {
/*  34: 91 */     this(schreg, -1L, TimeUnit.MILLISECONDS);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public ThreadSafeClientConnManager()
/*  38:    */   {
/*  39: 98 */     this(SchemeRegistryFactory.createDefault());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public ThreadSafeClientConnManager(SchemeRegistry schreg, long connTTL, TimeUnit connTTLTimeUnit)
/*  43:    */   {
/*  44:113 */     if (schreg == null) {
/*  45:114 */       throw new IllegalArgumentException("Scheme registry may not be null");
/*  46:    */     }
/*  47:116 */     this.log = LogFactory.getLog(getClass());
/*  48:117 */     this.schemeRegistry = schreg;
/*  49:118 */     this.connPerRoute = new ConnPerRouteBean();
/*  50:119 */     this.connOperator = createConnectionOperator(schreg);
/*  51:120 */     this.pool = createConnectionPool(connTTL, connTTLTimeUnit);
/*  52:121 */     this.connectionPool = this.pool;
/*  53:    */   }
/*  54:    */   
/*  55:    */   @Deprecated
/*  56:    */   public ThreadSafeClientConnManager(HttpParams params, SchemeRegistry schreg)
/*  57:    */   {
/*  58:135 */     if (schreg == null) {
/*  59:136 */       throw new IllegalArgumentException("Scheme registry may not be null");
/*  60:    */     }
/*  61:138 */     this.log = LogFactory.getLog(getClass());
/*  62:139 */     this.schemeRegistry = schreg;
/*  63:140 */     this.connPerRoute = new ConnPerRouteBean();
/*  64:141 */     this.connOperator = createConnectionOperator(schreg);
/*  65:142 */     this.pool = ((ConnPoolByRoute)createConnectionPool(params));
/*  66:143 */     this.connectionPool = this.pool;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void finalize()
/*  70:    */     throws Throwable
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:149 */       shutdown();
/*  75:    */     }
/*  76:    */     finally
/*  77:    */     {
/*  78:151 */       super.finalize();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   @Deprecated
/*  83:    */   protected AbstractConnPool createConnectionPool(HttpParams params)
/*  84:    */   {
/*  85:164 */     return new ConnPoolByRoute(this.connOperator, params);
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected ConnPoolByRoute createConnectionPool(long connTTL, TimeUnit connTTLTimeUnit)
/*  89:    */   {
/*  90:175 */     return new ConnPoolByRoute(this.connOperator, this.connPerRoute, 20, connTTL, connTTLTimeUnit);
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schreg)
/*  94:    */   {
/*  95:193 */     return new DefaultClientConnectionOperator(schreg);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public SchemeRegistry getSchemeRegistry()
/*  99:    */   {
/* 100:197 */     return this.schemeRegistry;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public ClientConnectionRequest requestConnection(final HttpRoute route, Object state)
/* 104:    */   {
/* 105:204 */     final PoolEntryRequest poolRequest = this.pool.requestPoolEntry(route, state);
/* 106:    */     
/* 107:    */ 
/* 108:207 */     new ClientConnectionRequest()
/* 109:    */     {
/* 110:    */       public void abortRequest()
/* 111:    */       {
/* 112:210 */         poolRequest.abortRequest();
/* 113:    */       }
/* 114:    */       
/* 115:    */       public ManagedClientConnection getConnection(long timeout, TimeUnit tunit)
/* 116:    */         throws InterruptedException, ConnectionPoolTimeoutException
/* 117:    */       {
/* 118:216 */         if (route == null) {
/* 119:217 */           throw new IllegalArgumentException("Route may not be null.");
/* 120:    */         }
/* 121:220 */         if (ThreadSafeClientConnManager.this.log.isDebugEnabled()) {
/* 122:221 */           ThreadSafeClientConnManager.this.log.debug("Get connection: " + route + ", timeout = " + timeout);
/* 123:    */         }
/* 124:224 */         BasicPoolEntry entry = poolRequest.getPoolEntry(timeout, tunit);
/* 125:225 */         return new BasicPooledConnAdapter(ThreadSafeClientConnManager.this, entry);
/* 126:    */       }
/* 127:    */     };
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void releaseConnection(ManagedClientConnection conn, long validDuration, TimeUnit timeUnit)
/* 131:    */   {
/* 132:234 */     if (!(conn instanceof BasicPooledConnAdapter)) {
/* 133:235 */       throw new IllegalArgumentException("Connection class mismatch, connection not obtained from this manager.");
/* 134:    */     }
/* 135:239 */     BasicPooledConnAdapter hca = (BasicPooledConnAdapter)conn;
/* 136:240 */     if ((hca.getPoolEntry() != null) && (hca.getManager() != this)) {
/* 137:241 */       throw new IllegalArgumentException("Connection not obtained from this manager.");
/* 138:    */     }
/* 139:244 */     synchronized (hca)
/* 140:    */     {
/* 141:245 */       BasicPoolEntry entry = (BasicPoolEntry)hca.getPoolEntry();
/* 142:246 */       if (entry == null) {
/* 143:247 */         return;
/* 144:    */       }
/* 145:    */       try
/* 146:    */       {
/* 147:251 */         if ((hca.isOpen()) && (!hca.isMarkedReusable())) {
/* 148:260 */           hca.shutdown();
/* 149:    */         }
/* 150:    */       }
/* 151:    */       catch (IOException iox)
/* 152:    */       {
/* 153:    */         boolean reusable;
/* 154:263 */         if (this.log.isDebugEnabled()) {
/* 155:264 */           this.log.debug("Exception shutting down released connection.", iox);
/* 156:    */         }
/* 157:    */       }
/* 158:    */       finally
/* 159:    */       {
/* 160:    */         boolean reusable;
/* 161:267 */         boolean reusable = hca.isMarkedReusable();
/* 162:268 */         if (this.log.isDebugEnabled()) {
/* 163:269 */           if (reusable) {
/* 164:270 */             this.log.debug("Released connection is reusable.");
/* 165:    */           } else {
/* 166:272 */             this.log.debug("Released connection is not reusable.");
/* 167:    */           }
/* 168:    */         }
/* 169:275 */         hca.detach();
/* 170:276 */         this.pool.freeEntry(entry, reusable, validDuration, timeUnit);
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void shutdown()
/* 176:    */   {
/* 177:282 */     this.log.debug("Shutting down");
/* 178:283 */     this.pool.shutdown();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int getConnectionsInPool(HttpRoute route)
/* 182:    */   {
/* 183:297 */     return this.pool.getConnectionsInPool(route);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public int getConnectionsInPool()
/* 187:    */   {
/* 188:309 */     return this.pool.getConnectionsInPool();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void closeIdleConnections(long idleTimeout, TimeUnit tunit)
/* 192:    */   {
/* 193:313 */     if (this.log.isDebugEnabled()) {
/* 194:314 */       this.log.debug("Closing connections idle longer than " + idleTimeout + " " + tunit);
/* 195:    */     }
/* 196:316 */     this.pool.closeIdleConnections(idleTimeout, tunit);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void closeExpiredConnections()
/* 200:    */   {
/* 201:320 */     this.log.debug("Closing expired connections");
/* 202:321 */     this.pool.closeExpiredConnections();
/* 203:    */   }
/* 204:    */   
/* 205:    */   public int getMaxTotal()
/* 206:    */   {
/* 207:328 */     return this.pool.getMaxTotalConnections();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setMaxTotal(int max)
/* 211:    */   {
/* 212:335 */     this.pool.setMaxTotalConnections(max);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public int getDefaultMaxPerRoute()
/* 216:    */   {
/* 217:342 */     return this.connPerRoute.getDefaultMaxPerRoute();
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setDefaultMaxPerRoute(int max)
/* 221:    */   {
/* 222:349 */     this.connPerRoute.setDefaultMaxPerRoute(max);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public int getMaxForRoute(HttpRoute route)
/* 226:    */   {
/* 227:356 */     return this.connPerRoute.getMaxForRoute(route);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setMaxForRoute(HttpRoute route, int max)
/* 231:    */   {
/* 232:363 */     this.connPerRoute.setMaxForRoute(route, max);
/* 233:    */   }
/* 234:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
 * JD-Core Version:    0.7.0.1
 */
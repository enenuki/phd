/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.concurrent.TimeUnit;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ import org.apache.http.annotation.GuardedBy;
/*   8:    */ import org.apache.http.annotation.ThreadSafe;
/*   9:    */ import org.apache.http.conn.ClientConnectionManager;
/*  10:    */ import org.apache.http.conn.ClientConnectionOperator;
/*  11:    */ import org.apache.http.conn.ClientConnectionRequest;
/*  12:    */ import org.apache.http.conn.ManagedClientConnection;
/*  13:    */ import org.apache.http.conn.OperatedClientConnection;
/*  14:    */ import org.apache.http.conn.routing.HttpRoute;
/*  15:    */ import org.apache.http.conn.routing.RouteTracker;
/*  16:    */ import org.apache.http.conn.scheme.SchemeRegistry;
/*  17:    */ import org.apache.http.params.HttpParams;
/*  18:    */ 
/*  19:    */ @ThreadSafe
/*  20:    */ public class SingleClientConnManager
/*  21:    */   implements ClientConnectionManager
/*  22:    */ {
/*  23: 64 */   private final Log log = LogFactory.getLog(getClass());
/*  24:    */   public static final String MISUSE_MESSAGE = "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
/*  25:    */   protected final SchemeRegistry schemeRegistry;
/*  26:    */   protected final ClientConnectionOperator connOperator;
/*  27:    */   protected final boolean alwaysShutDown;
/*  28:    */   @GuardedBy("this")
/*  29:    */   protected PoolEntry uniquePoolEntry;
/*  30:    */   @GuardedBy("this")
/*  31:    */   protected ConnAdapter managedConn;
/*  32:    */   @GuardedBy("this")
/*  33:    */   protected long lastReleaseTime;
/*  34:    */   @GuardedBy("this")
/*  35:    */   protected long connectionExpiresTime;
/*  36:    */   protected volatile boolean isShutDown;
/*  37:    */   
/*  38:    */   @Deprecated
/*  39:    */   public SingleClientConnManager(HttpParams params, SchemeRegistry schreg)
/*  40:    */   {
/*  41:110 */     this(schreg);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public SingleClientConnManager(SchemeRegistry schreg)
/*  45:    */   {
/*  46:118 */     if (schreg == null) {
/*  47:119 */       throw new IllegalArgumentException("Scheme registry must not be null.");
/*  48:    */     }
/*  49:122 */     this.schemeRegistry = schreg;
/*  50:123 */     this.connOperator = createConnectionOperator(schreg);
/*  51:124 */     this.uniquePoolEntry = new PoolEntry();
/*  52:125 */     this.managedConn = null;
/*  53:126 */     this.lastReleaseTime = -1L;
/*  54:127 */     this.alwaysShutDown = false;
/*  55:128 */     this.isShutDown = false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public SingleClientConnManager()
/*  59:    */   {
/*  60:135 */     this(SchemeRegistryFactory.createDefault());
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void finalize()
/*  64:    */     throws Throwable
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:141 */       shutdown();
/*  69:    */     }
/*  70:    */     finally
/*  71:    */     {
/*  72:143 */       super.finalize();
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public SchemeRegistry getSchemeRegistry()
/*  77:    */   {
/*  78:148 */     return this.schemeRegistry;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schreg)
/*  82:    */   {
/*  83:165 */     return new DefaultClientConnectionOperator(schreg);
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected final void assertStillUp()
/*  87:    */     throws IllegalStateException
/*  88:    */   {
/*  89:174 */     if (this.isShutDown) {
/*  90:175 */       throw new IllegalStateException("Manager is shut down.");
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public final ClientConnectionRequest requestConnection(final HttpRoute route, final Object state)
/*  95:    */   {
/*  96:182 */     new ClientConnectionRequest()
/*  97:    */     {
/*  98:    */       public void abortRequest() {}
/*  99:    */       
/* 100:    */       public ManagedClientConnection getConnection(long timeout, TimeUnit tunit)
/* 101:    */       {
/* 102:190 */         return SingleClientConnManager.this.getConnection(route, state);
/* 103:    */       }
/* 104:    */     };
/* 105:    */   }
/* 106:    */   
/* 107:    */   public synchronized ManagedClientConnection getConnection(HttpRoute route, Object state)
/* 108:    */   {
/* 109:206 */     if (route == null) {
/* 110:207 */       throw new IllegalArgumentException("Route may not be null.");
/* 111:    */     }
/* 112:209 */     assertStillUp();
/* 113:211 */     if (this.log.isDebugEnabled()) {
/* 114:212 */       this.log.debug("Get connection for route " + route);
/* 115:    */     }
/* 116:215 */     if (this.managedConn != null) {
/* 117:216 */       throw new IllegalStateException("Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.");
/* 118:    */     }
/* 119:219 */     boolean recreate = false;
/* 120:220 */     boolean shutdown = false;
/* 121:    */     
/* 122:    */ 
/* 123:223 */     closeExpiredConnections();
/* 124:225 */     if (this.uniquePoolEntry.connection.isOpen())
/* 125:    */     {
/* 126:226 */       RouteTracker tracker = this.uniquePoolEntry.tracker;
/* 127:227 */       shutdown = (tracker == null) || (!tracker.toRoute().equals(route));
/* 128:    */     }
/* 129:    */     else
/* 130:    */     {
/* 131:235 */       recreate = true;
/* 132:    */     }
/* 133:238 */     if (shutdown)
/* 134:    */     {
/* 135:239 */       recreate = true;
/* 136:    */       try
/* 137:    */       {
/* 138:241 */         this.uniquePoolEntry.shutdown();
/* 139:    */       }
/* 140:    */       catch (IOException iox)
/* 141:    */       {
/* 142:243 */         this.log.debug("Problem shutting down connection.", iox);
/* 143:    */       }
/* 144:    */     }
/* 145:247 */     if (recreate) {
/* 146:248 */       this.uniquePoolEntry = new PoolEntry();
/* 147:    */     }
/* 148:250 */     this.managedConn = new ConnAdapter(this.uniquePoolEntry, route);
/* 149:    */     
/* 150:252 */     return this.managedConn;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public synchronized void releaseConnection(ManagedClientConnection conn, long validDuration, TimeUnit timeUnit)
/* 154:    */   {
/* 155:258 */     assertStillUp();
/* 156:260 */     if (!(conn instanceof ConnAdapter)) {
/* 157:261 */       throw new IllegalArgumentException("Connection class mismatch, connection not obtained from this manager.");
/* 158:    */     }
/* 159:266 */     if (this.log.isDebugEnabled()) {
/* 160:267 */       this.log.debug("Releasing connection " + conn);
/* 161:    */     }
/* 162:270 */     ConnAdapter sca = (ConnAdapter)conn;
/* 163:271 */     if (sca.poolEntry == null) {
/* 164:272 */       return;
/* 165:    */     }
/* 166:273 */     ClientConnectionManager manager = sca.getManager();
/* 167:274 */     if ((manager != null) && (manager != this)) {
/* 168:275 */       throw new IllegalArgumentException("Connection not obtained from this manager.");
/* 169:    */     }
/* 170:    */     try
/* 171:    */     {
/* 172:281 */       if ((sca.isOpen()) && ((this.alwaysShutDown) || (!sca.isMarkedReusable())))
/* 173:    */       {
/* 174:284 */         if (this.log.isDebugEnabled()) {
/* 175:285 */           this.log.debug("Released connection open but not reusable.");
/* 176:    */         }
/* 177:292 */         sca.shutdown();
/* 178:    */       }
/* 179:    */     }
/* 180:    */     catch (IOException iox)
/* 181:    */     {
/* 182:295 */       if (this.log.isDebugEnabled()) {
/* 183:296 */         this.log.debug("Exception shutting down released connection.", iox);
/* 184:    */       }
/* 185:    */     }
/* 186:    */     finally
/* 187:    */     {
/* 188:299 */       sca.detach();
/* 189:300 */       this.managedConn = null;
/* 190:301 */       this.lastReleaseTime = System.currentTimeMillis();
/* 191:302 */       if (validDuration > 0L) {
/* 192:303 */         this.connectionExpiresTime = (timeUnit.toMillis(validDuration) + this.lastReleaseTime);
/* 193:    */       } else {
/* 194:305 */         this.connectionExpiresTime = 9223372036854775807L;
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public synchronized void closeExpiredConnections()
/* 200:    */   {
/* 201:310 */     if (System.currentTimeMillis() >= this.connectionExpiresTime) {
/* 202:311 */       closeIdleConnections(0L, TimeUnit.MILLISECONDS);
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public synchronized void closeIdleConnections(long idletime, TimeUnit tunit)
/* 207:    */   {
/* 208:316 */     assertStillUp();
/* 209:319 */     if (tunit == null) {
/* 210:320 */       throw new IllegalArgumentException("Time unit must not be null.");
/* 211:    */     }
/* 212:323 */     if ((this.managedConn == null) && (this.uniquePoolEntry.connection.isOpen()))
/* 213:    */     {
/* 214:324 */       long cutoff = System.currentTimeMillis() - tunit.toMillis(idletime);
/* 215:326 */       if (this.lastReleaseTime <= cutoff) {
/* 216:    */         try
/* 217:    */         {
/* 218:328 */           this.uniquePoolEntry.close();
/* 219:    */         }
/* 220:    */         catch (IOException iox)
/* 221:    */         {
/* 222:331 */           this.log.debug("Problem closing idle connection.", iox);
/* 223:    */         }
/* 224:    */       }
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public synchronized void shutdown()
/* 229:    */   {
/* 230:339 */     this.isShutDown = true;
/* 231:341 */     if (this.managedConn != null) {
/* 232:342 */       this.managedConn.detach();
/* 233:    */     }
/* 234:    */     try
/* 235:    */     {
/* 236:345 */       if (this.uniquePoolEntry != null) {
/* 237:346 */         this.uniquePoolEntry.shutdown();
/* 238:    */       }
/* 239:    */     }
/* 240:    */     catch (IOException iox)
/* 241:    */     {
/* 242:349 */       this.log.debug("Problem while shutting down manager.", iox);
/* 243:    */     }
/* 244:    */     finally
/* 245:    */     {
/* 246:351 */       this.uniquePoolEntry = null;
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   @Deprecated
/* 251:    */   protected synchronized void revokeConnection()
/* 252:    */   {
/* 253:360 */     if (this.managedConn == null) {
/* 254:361 */       return;
/* 255:    */     }
/* 256:362 */     this.managedConn.detach();
/* 257:    */     try
/* 258:    */     {
/* 259:364 */       this.uniquePoolEntry.shutdown();
/* 260:    */     }
/* 261:    */     catch (IOException iox)
/* 262:    */     {
/* 263:367 */       this.log.debug("Problem while shutting down connection.", iox);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   protected class PoolEntry
/* 268:    */     extends AbstractPoolEntry
/* 269:    */   {
/* 270:    */     protected PoolEntry()
/* 271:    */     {
/* 272:381 */       super(null);
/* 273:    */     }
/* 274:    */     
/* 275:    */     protected void close()
/* 276:    */       throws IOException
/* 277:    */     {
/* 278:388 */       shutdownEntry();
/* 279:389 */       if (this.connection.isOpen()) {
/* 280:390 */         this.connection.close();
/* 281:    */       }
/* 282:    */     }
/* 283:    */     
/* 284:    */     protected void shutdown()
/* 285:    */       throws IOException
/* 286:    */     {
/* 287:397 */       shutdownEntry();
/* 288:398 */       if (this.connection.isOpen()) {
/* 289:399 */         this.connection.shutdown();
/* 290:    */       }
/* 291:    */     }
/* 292:    */   }
/* 293:    */   
/* 294:    */   protected class ConnAdapter
/* 295:    */     extends AbstractPooledConnAdapter
/* 296:    */   {
/* 297:    */     protected ConnAdapter(SingleClientConnManager.PoolEntry entry, HttpRoute route)
/* 298:    */     {
/* 299:416 */       super(entry);
/* 300:417 */       markReusable();
/* 301:418 */       entry.route = route;
/* 302:    */     }
/* 303:    */   }
/* 304:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.SingleClientConnManager
 * JD-Core Version:    0.7.0.1
 */
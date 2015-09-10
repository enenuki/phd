/*   1:    */ package org.hibernate.engine.jdbc.internal;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.sql.Connection;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import org.hibernate.ConnectionReleaseMode;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.JDBCException;
/*  14:    */ import org.hibernate.engine.jdbc.internal.proxy.ProxyBuilder;
/*  15:    */ import org.hibernate.engine.jdbc.spi.ConnectionObserver;
/*  16:    */ import org.hibernate.engine.jdbc.spi.JdbcConnectionAccess;
/*  17:    */ import org.hibernate.engine.jdbc.spi.JdbcResourceRegistry;
/*  18:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  19:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  20:    */ import org.hibernate.engine.jdbc.spi.NonDurableConnectionObserver;
/*  21:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  22:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  23:    */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*  24:    */ import org.hibernate.internal.CoreMessageLogger;
/*  25:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  26:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  27:    */ import org.jboss.logging.Logger;
/*  28:    */ 
/*  29:    */ public class LogicalConnectionImpl
/*  30:    */   implements LogicalConnectionImplementor
/*  31:    */ {
/*  32: 60 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, LogicalConnectionImpl.class.getName());
/*  33:    */   private transient Connection physicalConnection;
/*  34:    */   private transient Connection shareableConnectionProxy;
/*  35:    */   private final transient ConnectionReleaseMode connectionReleaseMode;
/*  36:    */   private final transient JdbcServices jdbcServices;
/*  37:    */   private final transient JdbcConnectionAccess jdbcConnectionAccess;
/*  38:    */   private final transient JdbcResourceRegistry jdbcResourceRegistry;
/*  39:    */   private final transient List<ConnectionObserver> observers;
/*  40: 71 */   private boolean releasesEnabled = true;
/*  41:    */   private final boolean isUserSuppliedConnection;
/*  42:    */   private boolean isClosed;
/*  43:    */   
/*  44:    */   public LogicalConnectionImpl(Connection userSuppliedConnection, ConnectionReleaseMode connectionReleaseMode, JdbcServices jdbcServices, JdbcConnectionAccess jdbcConnectionAccess)
/*  45:    */   {
/*  46: 82 */     this(connectionReleaseMode, jdbcServices, jdbcConnectionAccess, userSuppliedConnection != null, false, new ArrayList());
/*  47:    */     
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54: 90 */     this.physicalConnection = userSuppliedConnection;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private LogicalConnectionImpl(ConnectionReleaseMode connectionReleaseMode, JdbcServices jdbcServices, JdbcConnectionAccess jdbcConnectionAccess, boolean isUserSuppliedConnection, boolean isClosed, List<ConnectionObserver> observers)
/*  58:    */   {
/*  59:100 */     this.connectionReleaseMode = determineConnectionReleaseMode(jdbcServices, isUserSuppliedConnection, connectionReleaseMode);
/*  60:    */     
/*  61:    */ 
/*  62:103 */     this.jdbcServices = jdbcServices;
/*  63:104 */     this.jdbcConnectionAccess = jdbcConnectionAccess;
/*  64:105 */     this.jdbcResourceRegistry = new JdbcResourceRegistryImpl(getJdbcServices().getSqlExceptionHelper());
/*  65:106 */     this.observers = observers;
/*  66:    */     
/*  67:108 */     this.isUserSuppliedConnection = isUserSuppliedConnection;
/*  68:109 */     this.isClosed = isClosed;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private static ConnectionReleaseMode determineConnectionReleaseMode(JdbcServices jdbcServices, boolean isUserSuppliedConnection, ConnectionReleaseMode connectionReleaseMode)
/*  72:    */   {
/*  73:116 */     if (isUserSuppliedConnection) {
/*  74:117 */       return ConnectionReleaseMode.ON_CLOSE;
/*  75:    */     }
/*  76:119 */     if ((connectionReleaseMode == ConnectionReleaseMode.AFTER_STATEMENT) && (!jdbcServices.getConnectionProvider().supportsAggressiveRelease()))
/*  77:    */     {
/*  78:121 */       LOG.debug("Connection provider reports to not support aggressive release; overriding");
/*  79:122 */       return ConnectionReleaseMode.AFTER_TRANSACTION;
/*  80:    */     }
/*  81:125 */     return connectionReleaseMode;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public JdbcServices getJdbcServices()
/*  85:    */   {
/*  86:131 */     return this.jdbcServices;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public JdbcResourceRegistry getResourceRegistry()
/*  90:    */   {
/*  91:136 */     return this.jdbcResourceRegistry;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void addObserver(ConnectionObserver observer)
/*  95:    */   {
/*  96:141 */     this.observers.add(observer);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void removeObserver(ConnectionObserver connectionObserver)
/* 100:    */   {
/* 101:146 */     this.observers.remove(connectionObserver);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isOpen()
/* 105:    */   {
/* 106:151 */     return !this.isClosed;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isPhysicallyConnected()
/* 110:    */   {
/* 111:156 */     return this.physicalConnection != null;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Connection getConnection()
/* 115:    */     throws HibernateException
/* 116:    */   {
/* 117:161 */     if (this.isClosed) {
/* 118:162 */       throw new HibernateException("Logical connection is closed");
/* 119:    */     }
/* 120:164 */     if (this.physicalConnection == null)
/* 121:    */     {
/* 122:165 */       if (this.isUserSuppliedConnection) {
/* 123:167 */         throw new HibernateException("User-supplied connection was null");
/* 124:    */       }
/* 125:169 */       obtainConnection();
/* 126:    */     }
/* 127:171 */     return this.physicalConnection;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Connection getShareableConnectionProxy()
/* 131:    */   {
/* 132:176 */     if (this.shareableConnectionProxy == null) {
/* 133:177 */       this.shareableConnectionProxy = buildConnectionProxy();
/* 134:    */     }
/* 135:179 */     return this.shareableConnectionProxy;
/* 136:    */   }
/* 137:    */   
/* 138:    */   private Connection buildConnectionProxy()
/* 139:    */   {
/* 140:183 */     return ProxyBuilder.buildConnection(this);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Connection getDistinctConnectionProxy()
/* 144:    */   {
/* 145:188 */     return buildConnectionProxy();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Connection close()
/* 149:    */   {
/* 150:193 */     LOG.trace("Closing logical connection");
/* 151:194 */     Connection c = this.isUserSuppliedConnection ? this.physicalConnection : null;
/* 152:    */     try
/* 153:    */     {
/* 154:196 */       releaseProxies();
/* 155:197 */       this.jdbcResourceRegistry.close();
/* 156:198 */       if ((!this.isUserSuppliedConnection) && (this.physicalConnection != null)) {
/* 157:199 */         releaseConnection();
/* 158:    */       }
/* 159:    */       Iterator i$;
/* 160:    */       ConnectionObserver observer;
/* 161:201 */       return c;
/* 162:    */     }
/* 163:    */     finally
/* 164:    */     {
/* 165:205 */       this.physicalConnection = null;
/* 166:206 */       this.isClosed = true;
/* 167:207 */       LOG.trace("Logical connection closed");
/* 168:208 */       for (ConnectionObserver observer : this.observers) {
/* 169:209 */         observer.logicalConnectionClosed();
/* 170:    */       }
/* 171:211 */       this.observers.clear();
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   private void releaseProxies()
/* 176:    */   {
/* 177:216 */     if (this.shareableConnectionProxy != null) {
/* 178:    */       try
/* 179:    */       {
/* 180:218 */         this.shareableConnectionProxy.close();
/* 181:    */       }
/* 182:    */       catch (SQLException e)
/* 183:    */       {
/* 184:221 */         LOG.debug("Error releasing shared connection proxy", e);
/* 185:    */       }
/* 186:    */     }
/* 187:224 */     this.shareableConnectionProxy = null;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public ConnectionReleaseMode getConnectionReleaseMode()
/* 191:    */   {
/* 192:229 */     return this.connectionReleaseMode;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void afterStatementExecution()
/* 196:    */   {
/* 197:234 */     LOG.tracev("Starting after statement execution processing [{0}]", this.connectionReleaseMode);
/* 198:235 */     if (this.connectionReleaseMode == ConnectionReleaseMode.AFTER_STATEMENT)
/* 199:    */     {
/* 200:236 */       if (!this.releasesEnabled)
/* 201:    */       {
/* 202:237 */         LOG.debug("Skipping aggressive release due to manual disabling");
/* 203:238 */         return;
/* 204:    */       }
/* 205:240 */       if (this.jdbcResourceRegistry.hasRegisteredResources())
/* 206:    */       {
/* 207:241 */         LOG.debug("Skipping aggressive release due to registered resources");
/* 208:242 */         return;
/* 209:    */       }
/* 210:244 */       releaseConnection();
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void afterTransaction()
/* 215:    */   {
/* 216:250 */     if ((this.connectionReleaseMode == ConnectionReleaseMode.AFTER_STATEMENT) || (this.connectionReleaseMode == ConnectionReleaseMode.AFTER_TRANSACTION))
/* 217:    */     {
/* 218:252 */       if (this.jdbcResourceRegistry.hasRegisteredResources())
/* 219:    */       {
/* 220:253 */         LOG.forcingContainerResourceCleanup();
/* 221:254 */         this.jdbcResourceRegistry.releaseResources();
/* 222:    */       }
/* 223:256 */       aggressiveRelease();
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void disableReleases()
/* 228:    */   {
/* 229:262 */     LOG.trace("Disabling releases");
/* 230:263 */     this.releasesEnabled = false;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void enableReleases()
/* 234:    */   {
/* 235:268 */     LOG.trace("(Re)enabling releases");
/* 236:269 */     this.releasesEnabled = true;
/* 237:270 */     afterStatementExecution();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void aggressiveRelease()
/* 241:    */   {
/* 242:277 */     if (this.isUserSuppliedConnection)
/* 243:    */     {
/* 244:278 */       LOG.debug("Cannot aggressively release user-supplied connection; skipping");
/* 245:    */     }
/* 246:    */     else
/* 247:    */     {
/* 248:281 */       LOG.debug("Aggressively releasing JDBC connection");
/* 249:282 */       if (this.physicalConnection != null) {
/* 250:283 */         releaseConnection();
/* 251:    */       }
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   private void obtainConnection()
/* 256:    */     throws JDBCException
/* 257:    */   {
/* 258:295 */     LOG.debug("Obtaining JDBC connection");
/* 259:    */     try
/* 260:    */     {
/* 261:297 */       this.physicalConnection = this.jdbcConnectionAccess.obtainConnection();
/* 262:298 */       for (ConnectionObserver observer : this.observers) {
/* 263:299 */         observer.physicalConnectionObtained(this.physicalConnection);
/* 264:    */       }
/* 265:301 */       LOG.debug("Obtained JDBC connection");
/* 266:    */     }
/* 267:    */     catch (SQLException sqle)
/* 268:    */     {
/* 269:304 */       throw getJdbcServices().getSqlExceptionHelper().convert(sqle, "Could not open connection");
/* 270:    */     }
/* 271:    */   }
/* 272:    */   
/* 273:    */   private void releaseConnection()
/* 274:    */     throws JDBCException
/* 275:    */   {
/* 276:314 */     LOG.debug("Releasing JDBC connection");
/* 277:315 */     if (this.physicalConnection == null) {
/* 278:316 */       return;
/* 279:    */     }
/* 280:    */     try
/* 281:    */     {
/* 282:319 */       if (!this.physicalConnection.isClosed()) {
/* 283:320 */         getJdbcServices().getSqlExceptionHelper().logAndClearWarnings(this.physicalConnection);
/* 284:    */       }
/* 285:322 */       if (!this.isUserSuppliedConnection) {
/* 286:323 */         this.jdbcConnectionAccess.releaseConnection(this.physicalConnection);
/* 287:    */       }
/* 288:    */     }
/* 289:    */     catch (SQLException e)
/* 290:    */     {
/* 291:327 */       throw getJdbcServices().getSqlExceptionHelper().convert(e, "Could not close connection");
/* 292:    */     }
/* 293:    */     finally
/* 294:    */     {
/* 295:330 */       this.physicalConnection = null;
/* 296:    */     }
/* 297:332 */     LOG.debug("Released JDBC connection");
/* 298:333 */     for (ConnectionObserver observer : this.observers) {
/* 299:334 */       observer.physicalConnectionReleased();
/* 300:    */     }
/* 301:336 */     releaseNonDurableObservers();
/* 302:    */   }
/* 303:    */   
/* 304:    */   private void releaseNonDurableObservers()
/* 305:    */   {
/* 306:340 */     Iterator observers = this.observers.iterator();
/* 307:341 */     while (observers.hasNext()) {
/* 308:342 */       if (NonDurableConnectionObserver.class.isInstance(observers.next())) {
/* 309:343 */         observers.remove();
/* 310:    */       }
/* 311:    */     }
/* 312:    */   }
/* 313:    */   
/* 314:    */   public Connection manualDisconnect()
/* 315:    */   {
/* 316:350 */     if (this.isClosed) {
/* 317:351 */       throw new IllegalStateException("cannot manually disconnect because logical connection is already closed");
/* 318:    */     }
/* 319:353 */     releaseProxies();
/* 320:354 */     Connection c = this.physicalConnection;
/* 321:355 */     this.jdbcResourceRegistry.releaseResources();
/* 322:356 */     releaseConnection();
/* 323:357 */     return c;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void manualReconnect(Connection suppliedConnection)
/* 327:    */   {
/* 328:362 */     if (this.isClosed) {
/* 329:363 */       throw new IllegalStateException("cannot manually reconnect because logical connection is already closed");
/* 330:    */     }
/* 331:365 */     if (!this.isUserSuppliedConnection) {
/* 332:366 */       throw new IllegalStateException("cannot manually reconnect unless Connection was originally supplied");
/* 333:    */     }
/* 334:369 */     if (suppliedConnection == null) {
/* 335:370 */       throw new IllegalArgumentException("cannot reconnect a null user-supplied connection");
/* 336:    */     }
/* 337:372 */     if (suppliedConnection == this.physicalConnection) {
/* 338:373 */       LOG.debug("reconnecting the same connection that is already connected; should this connection have been disconnected?");
/* 339:375 */     } else if (this.physicalConnection != null) {
/* 340:376 */       throw new IllegalArgumentException("cannot reconnect to a new user-supplied connection because currently connected; must disconnect before reconnecting.");
/* 341:    */     }
/* 342:380 */     this.physicalConnection = suppliedConnection;
/* 343:381 */     LOG.debug("Reconnected JDBC connection");
/* 344:    */   }
/* 345:    */   
/* 346:    */   public boolean isAutoCommit()
/* 347:    */   {
/* 348:387 */     if ((!isOpen()) || (!isPhysicallyConnected())) {
/* 349:388 */       return true;
/* 350:    */     }
/* 351:    */     try
/* 352:    */     {
/* 353:392 */       return getConnection().getAutoCommit();
/* 354:    */     }
/* 355:    */     catch (SQLException e)
/* 356:    */     {
/* 357:395 */       throw this.jdbcServices.getSqlExceptionHelper().convert(e, "could not inspect JDBC autocommit mode");
/* 358:    */     }
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void notifyObserversStatementPrepared()
/* 362:    */   {
/* 363:401 */     for (ConnectionObserver observer : this.observers) {
/* 364:402 */       observer.statementPrepared();
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   public boolean isReadyForSerialization()
/* 369:    */   {
/* 370:408 */     return !isPhysicallyConnected();
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void serialize(ObjectOutputStream oos)
/* 374:    */     throws IOException
/* 375:    */   {
/* 376:414 */     oos.writeBoolean(this.isUserSuppliedConnection);
/* 377:415 */     oos.writeBoolean(this.isClosed);
/* 378:416 */     List<ConnectionObserver> durableConnectionObservers = new ArrayList();
/* 379:417 */     for (ConnectionObserver observer : this.observers) {
/* 380:418 */       if (!NonDurableConnectionObserver.class.isInstance(observer)) {
/* 381:419 */         durableConnectionObservers.add(observer);
/* 382:    */       }
/* 383:    */     }
/* 384:422 */     oos.writeInt(durableConnectionObservers.size());
/* 385:423 */     for (ConnectionObserver observer : durableConnectionObservers) {
/* 386:424 */       oos.writeObject(observer);
/* 387:    */     }
/* 388:    */   }
/* 389:    */   
/* 390:    */   public static LogicalConnectionImpl deserialize(ObjectInputStream ois, TransactionContext transactionContext)
/* 391:    */     throws IOException, ClassNotFoundException
/* 392:    */   {
/* 393:431 */     boolean isUserSuppliedConnection = ois.readBoolean();
/* 394:432 */     boolean isClosed = ois.readBoolean();
/* 395:433 */     int observerCount = ois.readInt();
/* 396:434 */     List<ConnectionObserver> observers = CollectionHelper.arrayList(observerCount);
/* 397:435 */     for (int i = 0; i < observerCount; i++) {
/* 398:436 */       observers.add((ConnectionObserver)ois.readObject());
/* 399:    */     }
/* 400:438 */     return new LogicalConnectionImpl(transactionContext.getConnectionReleaseMode(), transactionContext.getTransactionEnvironment().getJdbcServices(), transactionContext.getJdbcConnectionAccess(), isUserSuppliedConnection, isClosed, observers);
/* 401:    */   }
/* 402:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.LogicalConnectionImpl
 * JD-Core Version:    0.7.0.1
 */
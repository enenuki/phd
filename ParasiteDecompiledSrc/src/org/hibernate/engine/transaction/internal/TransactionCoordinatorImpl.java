/*   1:    */ package org.hibernate.engine.transaction.internal;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.sql.Connection;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import org.hibernate.ConnectionReleaseMode;
/*  10:    */ import org.hibernate.ResourceClosedException;
/*  11:    */ import org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl;
/*  12:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  13:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  14:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  15:    */ import org.hibernate.engine.transaction.internal.jta.JtaStatusHelper;
/*  16:    */ import org.hibernate.engine.transaction.spi.JoinStatus;
/*  17:    */ import org.hibernate.engine.transaction.spi.SynchronizationRegistry;
/*  18:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  19:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  20:    */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*  21:    */ import org.hibernate.engine.transaction.spi.TransactionFactory;
/*  22:    */ import org.hibernate.engine.transaction.spi.TransactionImplementor;
/*  23:    */ import org.hibernate.engine.transaction.spi.TransactionObserver;
/*  24:    */ import org.hibernate.engine.transaction.synchronization.internal.RegisteredSynchronization;
/*  25:    */ import org.hibernate.engine.transaction.synchronization.internal.SynchronizationCallbackCoordinatorImpl;
/*  26:    */ import org.hibernate.engine.transaction.synchronization.spi.SynchronizationCallbackCoordinator;
/*  27:    */ import org.hibernate.internal.CoreMessageLogger;
/*  28:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  29:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  30:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  31:    */ import org.jboss.logging.Logger;
/*  32:    */ 
/*  33:    */ public class TransactionCoordinatorImpl
/*  34:    */   implements TransactionCoordinator
/*  35:    */ {
/*  36: 65 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TransactionCoordinatorImpl.class.getName());
/*  37:    */   private final transient TransactionContext transactionContext;
/*  38:    */   private final transient JdbcCoordinatorImpl jdbcCoordinator;
/*  39:    */   private final transient TransactionFactory transactionFactory;
/*  40:    */   private final transient TransactionEnvironment transactionEnvironment;
/*  41:    */   private final transient List<TransactionObserver> observers;
/*  42:    */   private final transient SynchronizationRegistryImpl synchronizationRegistry;
/*  43:    */   private transient TransactionImplementor currentHibernateTransaction;
/*  44:    */   private transient SynchronizationCallbackCoordinatorImpl callbackCoordinator;
/*  45: 79 */   private transient boolean open = true;
/*  46:    */   private transient boolean synchronizationRegistered;
/*  47:    */   private transient boolean ownershipTaken;
/*  48:    */   
/*  49:    */   public TransactionCoordinatorImpl(Connection userSuppliedConnection, TransactionContext transactionContext)
/*  50:    */   {
/*  51: 86 */     this.transactionContext = transactionContext;
/*  52: 87 */     this.jdbcCoordinator = new JdbcCoordinatorImpl(userSuppliedConnection, this);
/*  53: 88 */     this.transactionEnvironment = transactionContext.getTransactionEnvironment();
/*  54: 89 */     this.transactionFactory = this.transactionEnvironment.getTransactionFactory();
/*  55: 90 */     this.observers = new ArrayList();
/*  56: 91 */     this.synchronizationRegistry = new SynchronizationRegistryImpl();
/*  57: 92 */     reset();
/*  58:    */     
/*  59: 94 */     boolean registerSynchronization = (transactionContext.isAutoCloseSessionEnabled()) || (transactionContext.isFlushBeforeCompletionEnabled()) || (transactionContext.getConnectionReleaseMode() == ConnectionReleaseMode.AFTER_TRANSACTION);
/*  60: 97 */     if (registerSynchronization) {
/*  61: 98 */       pulse();
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public TransactionCoordinatorImpl(TransactionContext transactionContext, JdbcCoordinatorImpl jdbcCoordinator, List<TransactionObserver> observers)
/*  66:    */   {
/*  67:106 */     this.transactionContext = transactionContext;
/*  68:107 */     this.jdbcCoordinator = jdbcCoordinator;
/*  69:108 */     this.transactionEnvironment = transactionContext.getTransactionEnvironment();
/*  70:109 */     this.transactionFactory = this.transactionEnvironment.getTransactionFactory();
/*  71:110 */     this.observers = observers;
/*  72:111 */     this.synchronizationRegistry = new SynchronizationRegistryImpl();
/*  73:112 */     reset();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void reset()
/*  77:    */   {
/*  78:119 */     this.synchronizationRegistered = false;
/*  79:120 */     this.ownershipTaken = false;
/*  80:122 */     if (this.currentHibernateTransaction != null) {
/*  81:123 */       this.currentHibernateTransaction.invalidate();
/*  82:    */     }
/*  83:125 */     this.currentHibernateTransaction = transactionFactory().createTransaction(this);
/*  84:126 */     if (this.transactionContext.shouldAutoJoinTransaction())
/*  85:    */     {
/*  86:127 */       this.currentHibernateTransaction.markForJoin();
/*  87:128 */       this.currentHibernateTransaction.join();
/*  88:    */     }
/*  89:132 */     this.synchronizationRegistry.clearSynchronizations();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void afterTransaction(TransactionImplementor hibernateTransaction, int status)
/*  93:    */   {
/*  94:136 */     LOG.trace("after transaction completion");
/*  95:    */     
/*  96:138 */     boolean success = JtaStatusHelper.isCommitted(status);
/*  97:    */     
/*  98:140 */     this.transactionEnvironment.getStatisticsImplementor().endTransaction(success);
/*  99:    */     
/* 100:142 */     getJdbcCoordinator().afterTransaction();
/* 101:    */     
/* 102:144 */     getTransactionContext().afterTransactionCompletion(hibernateTransaction, success);
/* 103:145 */     sendAfterTransactionCompletionNotifications(hibernateTransaction, status);
/* 104:146 */     reset();
/* 105:    */   }
/* 106:    */   
/* 107:    */   private SessionFactoryImplementor sessionFactory()
/* 108:    */   {
/* 109:150 */     return this.transactionEnvironment.getSessionFactory();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isSynchronizationRegistered()
/* 113:    */   {
/* 114:154 */     return this.synchronizationRegistered;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isTransactionInProgress()
/* 118:    */   {
/* 119:160 */     return (getTransaction().isActive()) && (getTransaction().getJoinStatus() == JoinStatus.JOINED);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public TransactionContext getTransactionContext()
/* 123:    */   {
/* 124:165 */     return this.transactionContext;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public JdbcCoordinator getJdbcCoordinator()
/* 128:    */   {
/* 129:170 */     return this.jdbcCoordinator;
/* 130:    */   }
/* 131:    */   
/* 132:    */   private TransactionFactory transactionFactory()
/* 133:    */   {
/* 134:174 */     return this.transactionFactory;
/* 135:    */   }
/* 136:    */   
/* 137:    */   private TransactionEnvironment getTransactionEnvironment()
/* 138:    */   {
/* 139:178 */     return this.transactionEnvironment;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public TransactionImplementor getTransaction()
/* 143:    */   {
/* 144:183 */     if (!this.open) {
/* 145:184 */       throw new ResourceClosedException("This TransactionCoordinator has been closed");
/* 146:    */     }
/* 147:186 */     pulse();
/* 148:187 */     return this.currentHibernateTransaction;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void afterNonTransactionalQuery(boolean success)
/* 152:    */   {
/* 153:193 */     boolean isAutocommit = getJdbcCoordinator().getLogicalConnection().isAutoCommit();
/* 154:194 */     getJdbcCoordinator().getLogicalConnection().afterTransaction();
/* 155:196 */     if (isAutocommit) {
/* 156:197 */       for (TransactionObserver observer : this.observers) {
/* 157:198 */         observer.afterCompletion(success, getTransaction());
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void resetJoinStatus()
/* 163:    */   {
/* 164:205 */     getTransaction().resetJoinStatus();
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void attemptToRegisterJtaSync()
/* 168:    */   {
/* 169:210 */     if (this.synchronizationRegistered) {
/* 170:211 */       return;
/* 171:    */     }
/* 172:215 */     if (this.currentHibernateTransaction.isInitiator()) {
/* 173:216 */       return;
/* 174:    */     }
/* 175:219 */     if ((!this.transactionContext.shouldAutoJoinTransaction()) && 
/* 176:220 */       (this.currentHibernateTransaction.getJoinStatus() != JoinStatus.MARKED_FOR_JOINED))
/* 177:    */     {
/* 178:221 */       LOG.debug("Skipping JTA sync registration due to auto join checking");
/* 179:222 */       return;
/* 180:    */     }
/* 181:231 */     JtaPlatform jtaPlatform = getTransactionEnvironment().getJtaPlatform();
/* 182:232 */     if (jtaPlatform == null) {
/* 183:234 */       return;
/* 184:    */     }
/* 185:238 */     if (!jtaPlatform.canRegisterSynchronization())
/* 186:    */     {
/* 187:239 */       LOG.trace("registered JTA platform says we cannot currently resister synchronization; skipping");
/* 188:240 */       return;
/* 189:    */     }
/* 190:244 */     if (!transactionFactory().isJoinableJtaTransaction(this, this.currentHibernateTransaction))
/* 191:    */     {
/* 192:245 */       LOG.trace("TransactionFactory reported no JTA transaction to join; skipping Synchronization registration");
/* 193:246 */       return;
/* 194:    */     }
/* 195:249 */     jtaPlatform.registerSynchronization(new RegisteredSynchronization(getSynchronizationCallbackCoordinator()));
/* 196:250 */     this.synchronizationRegistered = true;
/* 197:251 */     LOG.debug("successfully registered Synchronization");
/* 198:    */   }
/* 199:    */   
/* 200:    */   public SynchronizationCallbackCoordinator getSynchronizationCallbackCoordinator()
/* 201:    */   {
/* 202:256 */     if (this.callbackCoordinator == null) {
/* 203:257 */       this.callbackCoordinator = new SynchronizationCallbackCoordinatorImpl(this);
/* 204:    */     }
/* 205:259 */     return this.callbackCoordinator;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void pulse()
/* 209:    */   {
/* 210:263 */     if (transactionFactory().compatibleWithJtaSynchronization()) {
/* 211:266 */       attemptToRegisterJtaSync();
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Connection close()
/* 216:    */   {
/* 217:271 */     this.open = false;
/* 218:272 */     reset();
/* 219:273 */     this.observers.clear();
/* 220:274 */     return this.jdbcCoordinator.close();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public SynchronizationRegistry getSynchronizationRegistry()
/* 224:    */   {
/* 225:278 */     return this.synchronizationRegistry;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void addObserver(TransactionObserver observer)
/* 229:    */   {
/* 230:282 */     this.observers.add(observer);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean isTransactionJoinable()
/* 234:    */   {
/* 235:288 */     return transactionFactory().isJoinableJtaTransaction(this, this.currentHibernateTransaction);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean isTransactionJoined()
/* 239:    */   {
/* 240:294 */     return (this.currentHibernateTransaction != null) && (this.currentHibernateTransaction.getJoinStatus() == JoinStatus.JOINED);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setRollbackOnly()
/* 244:    */   {
/* 245:298 */     getTransaction().markRollbackOnly();
/* 246:    */   }
/* 247:    */   
/* 248:    */   public boolean takeOwnership()
/* 249:    */   {
/* 250:303 */     if (this.ownershipTaken) {
/* 251:304 */       return false;
/* 252:    */     }
/* 253:307 */     this.ownershipTaken = true;
/* 254:308 */     return true;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void sendAfterTransactionBeginNotifications(TransactionImplementor hibernateTransaction)
/* 258:    */   {
/* 259:314 */     for (TransactionObserver observer : this.observers) {
/* 260:315 */       observer.afterBegin(this.currentHibernateTransaction);
/* 261:    */     }
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void sendBeforeTransactionCompletionNotifications(TransactionImplementor hibernateTransaction)
/* 265:    */   {
/* 266:321 */     this.synchronizationRegistry.notifySynchronizationsBeforeTransactionCompletion();
/* 267:322 */     for (TransactionObserver observer : this.observers) {
/* 268:323 */       observer.beforeCompletion(hibernateTransaction);
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void sendAfterTransactionCompletionNotifications(TransactionImplementor hibernateTransaction, int status)
/* 273:    */   {
/* 274:329 */     boolean successful = JtaStatusHelper.isCommitted(status);
/* 275:330 */     for (TransactionObserver observer : this.observers) {
/* 276:331 */       observer.afterCompletion(successful, hibernateTransaction);
/* 277:    */     }
/* 278:333 */     this.synchronizationRegistry.notifySynchronizationsAfterTransactionCompletion(status);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void serialize(ObjectOutputStream oos)
/* 282:    */     throws IOException
/* 283:    */   {
/* 284:340 */     this.jdbcCoordinator.serialize(oos);
/* 285:341 */     oos.writeInt(this.observers.size());
/* 286:342 */     for (TransactionObserver observer : this.observers) {
/* 287:343 */       oos.writeObject(observer);
/* 288:    */     }
/* 289:    */   }
/* 290:    */   
/* 291:    */   public static TransactionCoordinatorImpl deserialize(ObjectInputStream ois, TransactionContext transactionContext)
/* 292:    */     throws ClassNotFoundException, IOException
/* 293:    */   {
/* 294:350 */     JdbcCoordinatorImpl jdbcCoordinator = JdbcCoordinatorImpl.deserialize(ois, transactionContext);
/* 295:351 */     int observerCount = ois.readInt();
/* 296:352 */     List<TransactionObserver> observers = CollectionHelper.arrayList(observerCount);
/* 297:353 */     for (int i = 0; i < observerCount; i++) {
/* 298:354 */       observers.add((TransactionObserver)ois.readObject());
/* 299:    */     }
/* 300:356 */     TransactionCoordinatorImpl transactionCoordinator = new TransactionCoordinatorImpl(transactionContext, jdbcCoordinator, observers);
/* 301:357 */     jdbcCoordinator.afterDeserialize(transactionCoordinator);
/* 302:358 */     return transactionCoordinator;
/* 303:    */   }
/* 304:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.TransactionCoordinatorImpl
 * JD-Core Version:    0.7.0.1
 */
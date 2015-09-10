/*   1:    */ package org.hibernate.engine.transaction.internal.jta;
/*   2:    */ 
/*   3:    */ import javax.transaction.SystemException;
/*   4:    */ import javax.transaction.TransactionManager;
/*   5:    */ import javax.transaction.UserTransaction;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.TransactionException;
/*   8:    */ import org.hibernate.engine.transaction.spi.AbstractTransactionImpl;
/*   9:    */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*  10:    */ import org.hibernate.engine.transaction.spi.JoinStatus;
/*  11:    */ import org.hibernate.engine.transaction.spi.LocalStatus;
/*  12:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  13:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  14:    */ import org.hibernate.internal.CoreMessageLogger;
/*  15:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class JtaTransaction
/*  19:    */   extends AbstractTransactionImpl
/*  20:    */ {
/*  21: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JtaTransaction.class.getName());
/*  22:    */   private UserTransaction userTransaction;
/*  23:    */   private boolean isInitiator;
/*  24:    */   private boolean isDriver;
/*  25:    */   
/*  26:    */   protected JtaTransaction(TransactionCoordinator transactionCoordinator)
/*  27:    */   {
/*  28: 59 */     super(transactionCoordinator);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public UserTransaction getUserTransaction()
/*  32:    */   {
/*  33: 64 */     return this.userTransaction;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void doBegin()
/*  37:    */   {
/*  38: 69 */     LOG.debug("begin");
/*  39:    */     
/*  40: 71 */     this.userTransaction = jtaPlatform().retrieveUserTransaction();
/*  41: 72 */     if (this.userTransaction == null) {
/*  42: 73 */       throw new TransactionException("Unable to locate JTA UserTransaction");
/*  43:    */     }
/*  44:    */     try
/*  45:    */     {
/*  46: 77 */       if (this.userTransaction.getStatus() == 6)
/*  47:    */       {
/*  48: 78 */         this.userTransaction.begin();
/*  49: 79 */         this.isInitiator = true;
/*  50: 80 */         LOG.debug("Began a new JTA transaction");
/*  51:    */       }
/*  52:    */     }
/*  53:    */     catch (Exception e)
/*  54:    */     {
/*  55: 84 */       throw new TransactionException("JTA transaction begin failed", e);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected void afterTransactionBegin()
/*  60:    */   {
/*  61: 91 */     transactionCoordinator().pulse();
/*  62: 93 */     if (!transactionCoordinator().isSynchronizationRegistered()) {
/*  63: 94 */       this.isDriver = transactionCoordinator().takeOwnership();
/*  64:    */     }
/*  65: 97 */     applyTimeout();
/*  66: 98 */     transactionCoordinator().sendAfterTransactionBeginNotifications(this);
/*  67: 99 */     transactionCoordinator().getTransactionContext().afterTransactionBegin(this);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void applyTimeout()
/*  71:    */   {
/*  72:103 */     if (getTimeout() > 0) {
/*  73:104 */       if (this.userTransaction != null) {
/*  74:    */         try
/*  75:    */         {
/*  76:106 */           this.userTransaction.setTransactionTimeout(getTimeout());
/*  77:    */         }
/*  78:    */         catch (SystemException e)
/*  79:    */         {
/*  80:109 */           throw new TransactionException("Unable to apply requested transaction timeout", e);
/*  81:    */         }
/*  82:    */       } else {
/*  83:113 */         LOG.debug("Unable to apply requested transaction timeout; no UserTransaction.  Will try later");
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void beforeTransactionCommit()
/*  89:    */   {
/*  90:120 */     transactionCoordinator().sendBeforeTransactionCompletionNotifications(this);
/*  91:    */     
/*  92:122 */     boolean flush = (!transactionCoordinator().getTransactionContext().isFlushModeNever()) && ((this.isDriver) || (!transactionCoordinator().getTransactionContext().isFlushBeforeCompletionEnabled()));
/*  93:125 */     if (flush) {
/*  94:127 */       transactionCoordinator().getTransactionContext().managedFlush();
/*  95:    */     }
/*  96:130 */     if ((this.isDriver) && (this.isInitiator)) {
/*  97:131 */       transactionCoordinator().getTransactionContext().beforeTransactionCompletion(this);
/*  98:    */     }
/*  99:134 */     closeIfRequired();
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void closeIfRequired()
/* 103:    */     throws HibernateException
/* 104:    */   {
/* 105:138 */     boolean close = (this.isDriver) && (transactionCoordinator().getTransactionContext().shouldAutoClose()) && (!transactionCoordinator().getTransactionContext().isClosed());
/* 106:141 */     if (close) {
/* 107:142 */       transactionCoordinator().getTransactionContext().managedClose();
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void doCommit()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:149 */       if (this.isInitiator)
/* 116:    */       {
/* 117:150 */         this.userTransaction.commit();
/* 118:151 */         LOG.debug("Committed JTA UserTransaction");
/* 119:    */       }
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:155 */       throw new TransactionException("JTA commit failed: ", e);
/* 124:    */     }
/* 125:    */     finally
/* 126:    */     {
/* 127:158 */       this.isInitiator = false;
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void afterTransactionCompletion(int status) {}
/* 132:    */   
/* 133:    */   protected void afterAfterCompletion()
/* 134:    */   {
/* 135:170 */     if (this.isDriver)
/* 136:    */     {
/* 137:171 */       if (!this.isInitiator) {
/* 138:172 */         LOG.setManagerLookupClass();
/* 139:    */       }
/* 140:    */       try
/* 141:    */       {
/* 142:175 */         transactionCoordinator().afterTransaction(this, this.userTransaction.getStatus());
/* 143:    */       }
/* 144:    */       catch (SystemException e)
/* 145:    */       {
/* 146:178 */         throw new TransactionException("Unable to determine UserTransaction status", e);
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   protected void beforeTransactionRollBack() {}
/* 152:    */   
/* 153:    */   protected void doRollback()
/* 154:    */   {
/* 155:    */     try
/* 156:    */     {
/* 157:191 */       if (this.isInitiator)
/* 158:    */       {
/* 159:193 */         if (getLocalStatus() != LocalStatus.FAILED_COMMIT)
/* 160:    */         {
/* 161:194 */           this.userTransaction.rollback();
/* 162:195 */           LOG.debug("Rolled back JTA UserTransaction");
/* 163:    */         }
/* 164:    */       }
/* 165:    */       else {
/* 166:199 */         markRollbackOnly();
/* 167:    */       }
/* 168:    */     }
/* 169:    */     catch (Exception e)
/* 170:    */     {
/* 171:203 */       throw new TransactionException("JTA rollback failed", e);
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void markRollbackOnly()
/* 176:    */   {
/* 177:209 */     LOG.trace("Marking transaction for rollback only");
/* 178:    */     try
/* 179:    */     {
/* 180:211 */       this.userTransaction.setRollbackOnly();
/* 181:212 */       LOG.debug("set JTA UserTransaction to rollback only");
/* 182:    */     }
/* 183:    */     catch (SystemException e)
/* 184:    */     {
/* 185:215 */       LOG.debug("Unable to mark transaction for rollback only", e);
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public IsolationDelegate createIsolationDelegate()
/* 190:    */   {
/* 191:221 */     return new JtaIsolationDelegate(transactionCoordinator());
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean isInitiator()
/* 195:    */   {
/* 196:226 */     return this.isInitiator;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean isActive()
/* 200:    */     throws HibernateException
/* 201:    */   {
/* 202:231 */     if (getLocalStatus() != LocalStatus.ACTIVE) {
/* 203:232 */       return false;
/* 204:    */     }
/* 205:    */     int status;
/* 206:    */     try
/* 207:    */     {
/* 208:237 */       status = this.userTransaction.getStatus();
/* 209:    */     }
/* 210:    */     catch (SystemException se)
/* 211:    */     {
/* 212:240 */       throw new TransactionException("Could not determine transaction status: ", se);
/* 213:    */     }
/* 214:242 */     return JtaStatusHelper.isActive(status);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setTimeout(int seconds)
/* 218:    */   {
/* 219:247 */     super.setTimeout(seconds);
/* 220:248 */     applyTimeout();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void join() {}
/* 224:    */   
/* 225:    */   public void resetJoinStatus() {}
/* 226:    */   
/* 227:    */   public JoinStatus getJoinStatus()
/* 228:    */   {
/* 229:262 */     if (this.userTransaction != null) {
/* 230:263 */       return JtaStatusHelper.isActive(this.userTransaction) ? JoinStatus.JOINED : JoinStatus.NOT_JOINED;
/* 231:    */     }
/* 232:267 */     TransactionManager transactionManager = jtaPlatform().retrieveTransactionManager();
/* 233:268 */     if (transactionManager != null) {
/* 234:269 */       return JtaStatusHelper.isActive(transactionManager) ? JoinStatus.JOINED : JoinStatus.NOT_JOINED;
/* 235:    */     }
/* 236:273 */     UserTransaction userTransaction = jtaPlatform().retrieveUserTransaction();
/* 237:274 */     return (userTransaction != null) && (JtaStatusHelper.isActive(userTransaction)) ? JoinStatus.JOINED : JoinStatus.NOT_JOINED;
/* 238:    */   }
/* 239:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.jta.JtaTransaction
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.hibernate.engine.transaction.spi;
/*   2:    */ 
/*   3:    */ import javax.transaction.Synchronization;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.TransactionException;
/*   6:    */ import org.hibernate.internal.CoreMessageLogger;
/*   7:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*   8:    */ import org.jboss.logging.Logger;
/*   9:    */ 
/*  10:    */ public abstract class AbstractTransactionImpl
/*  11:    */   implements TransactionImplementor
/*  12:    */ {
/*  13: 43 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractTransactionImpl.class.getName());
/*  14:    */   private final TransactionCoordinator transactionCoordinator;
/*  15: 48 */   private boolean valid = true;
/*  16: 50 */   private LocalStatus localStatus = LocalStatus.NOT_ACTIVE;
/*  17: 51 */   private int timeout = -1;
/*  18:    */   
/*  19:    */   protected AbstractTransactionImpl(TransactionCoordinator transactionCoordinator)
/*  20:    */   {
/*  21: 54 */     this.transactionCoordinator = transactionCoordinator;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void invalidate()
/*  25:    */   {
/*  26: 59 */     this.valid = false;
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected abstract void doBegin();
/*  30:    */   
/*  31:    */   protected abstract void doCommit();
/*  32:    */   
/*  33:    */   protected abstract void doRollback();
/*  34:    */   
/*  35:    */   protected abstract void afterTransactionBegin();
/*  36:    */   
/*  37:    */   protected abstract void beforeTransactionCommit();
/*  38:    */   
/*  39:    */   protected abstract void beforeTransactionRollBack();
/*  40:    */   
/*  41:    */   protected abstract void afterTransactionCompletion(int paramInt);
/*  42:    */   
/*  43:    */   protected abstract void afterAfterCompletion();
/*  44:    */   
/*  45:    */   protected TransactionCoordinator transactionCoordinator()
/*  46:    */   {
/*  47: 95 */     return this.transactionCoordinator;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected JtaPlatform jtaPlatform()
/*  51:    */   {
/*  52:104 */     return transactionCoordinator().getTransactionContext().getTransactionEnvironment().getJtaPlatform();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void registerSynchronization(Synchronization synchronization)
/*  56:    */   {
/*  57:109 */     transactionCoordinator().getSynchronizationRegistry().registerSynchronization(synchronization);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public LocalStatus getLocalStatus()
/*  61:    */   {
/*  62:114 */     return this.localStatus;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isActive()
/*  66:    */   {
/*  67:119 */     return (this.localStatus == LocalStatus.ACTIVE) && (doExtendedActiveCheck());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean isParticipating()
/*  71:    */   {
/*  72:124 */     return (getJoinStatus() == JoinStatus.JOINED) && (isActive());
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean wasCommitted()
/*  76:    */   {
/*  77:129 */     return this.localStatus == LocalStatus.COMMITTED;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean wasRolledBack()
/*  81:    */     throws HibernateException
/*  82:    */   {
/*  83:134 */     return this.localStatus == LocalStatus.ROLLED_BACK;
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected boolean doExtendedActiveCheck()
/*  87:    */   {
/*  88:143 */     return true;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void begin()
/*  92:    */     throws HibernateException
/*  93:    */   {
/*  94:148 */     if (!this.valid) {
/*  95:149 */       throw new TransactionException("Transaction instance is no longer valid");
/*  96:    */     }
/*  97:151 */     if (this.localStatus == LocalStatus.ACTIVE) {
/*  98:152 */       throw new TransactionException("nested transactions not supported");
/*  99:    */     }
/* 100:154 */     if (this.localStatus != LocalStatus.NOT_ACTIVE) {
/* 101:155 */       throw new TransactionException("reuse of Transaction instances not supported");
/* 102:    */     }
/* 103:158 */     LOG.debug("begin");
/* 104:    */     
/* 105:160 */     doBegin();
/* 106:    */     
/* 107:162 */     this.localStatus = LocalStatus.ACTIVE;
/* 108:    */     
/* 109:164 */     afterTransactionBegin();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void commit()
/* 113:    */     throws HibernateException
/* 114:    */   {
/* 115:169 */     if (this.localStatus != LocalStatus.ACTIVE) {
/* 116:170 */       throw new TransactionException("Transaction not successfully started");
/* 117:    */     }
/* 118:173 */     LOG.debug("committing");
/* 119:    */     
/* 120:175 */     beforeTransactionCommit();
/* 121:    */     try
/* 122:    */     {
/* 123:178 */       doCommit();
/* 124:179 */       this.localStatus = LocalStatus.COMMITTED;
/* 125:180 */       afterTransactionCompletion(3);
/* 126:    */     }
/* 127:    */     catch (Exception e)
/* 128:    */     {
/* 129:183 */       this.localStatus = LocalStatus.FAILED_COMMIT;
/* 130:184 */       afterTransactionCompletion(5);
/* 131:185 */       throw new TransactionException("commit failed", e);
/* 132:    */     }
/* 133:    */     finally
/* 134:    */     {
/* 135:188 */       invalidate();
/* 136:189 */       afterAfterCompletion();
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected boolean allowFailedCommitToPhysicallyRollback()
/* 141:    */   {
/* 142:194 */     return false;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void rollback()
/* 146:    */     throws HibernateException
/* 147:    */   {
/* 148:199 */     if ((this.localStatus != LocalStatus.ACTIVE) && (this.localStatus != LocalStatus.FAILED_COMMIT)) {
/* 149:200 */       throw new TransactionException("Transaction not successfully started");
/* 150:    */     }
/* 151:203 */     LOG.debug("rolling back");
/* 152:    */     
/* 153:205 */     beforeTransactionRollBack();
/* 154:207 */     if ((this.localStatus != LocalStatus.FAILED_COMMIT) || (allowFailedCommitToPhysicallyRollback())) {
/* 155:    */       try
/* 156:    */       {
/* 157:209 */         doRollback();
/* 158:210 */         this.localStatus = LocalStatus.ROLLED_BACK;
/* 159:211 */         afterTransactionCompletion(4);
/* 160:    */       }
/* 161:    */       catch (Exception e)
/* 162:    */       {
/* 163:214 */         afterTransactionCompletion(5);
/* 164:215 */         throw new TransactionException("rollback failed", e);
/* 165:    */       }
/* 166:    */       finally
/* 167:    */       {
/* 168:218 */         invalidate();
/* 169:219 */         afterAfterCompletion();
/* 170:    */       }
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setTimeout(int seconds)
/* 175:    */   {
/* 176:227 */     this.timeout = seconds;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int getTimeout()
/* 180:    */   {
/* 181:232 */     return this.timeout;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void markForJoin() {}
/* 185:    */   
/* 186:    */   public void join() {}
/* 187:    */   
/* 188:    */   public void resetJoinStatus() {}
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.spi.AbstractTransactionImpl
 * JD-Core Version:    0.7.0.1
 */
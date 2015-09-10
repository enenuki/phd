/*   1:    */ package org.hibernate.context.internal;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Map;
/*   5:    */ import javax.transaction.Synchronization;
/*   6:    */ import javax.transaction.Transaction;
/*   7:    */ import javax.transaction.TransactionManager;
/*   8:    */ import org.hibernate.ConnectionReleaseMode;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.Session;
/*  11:    */ import org.hibernate.SessionBuilder;
/*  12:    */ import org.hibernate.context.spi.CurrentSessionContext;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.engine.transaction.internal.jta.JtaStatusHelper;
/*  15:    */ import org.hibernate.internal.CoreMessageLogger;
/*  16:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  17:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ public class JTASessionContext
/*  21:    */   implements CurrentSessionContext
/*  22:    */ {
/*  23: 67 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JTASessionContext.class.getName());
/*  24:    */   protected final SessionFactoryImplementor factory;
/*  25: 70 */   private transient Map currentSessionMap = new Hashtable();
/*  26:    */   
/*  27:    */   public JTASessionContext(SessionFactoryImplementor factory)
/*  28:    */   {
/*  29: 73 */     this.factory = factory;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Session currentSession()
/*  33:    */     throws HibernateException
/*  34:    */   {
/*  35: 78 */     JtaPlatform jtaPlatform = (JtaPlatform)this.factory.getServiceRegistry().getService(JtaPlatform.class);
/*  36: 79 */     TransactionManager transactionManager = jtaPlatform.retrieveTransactionManager();
/*  37: 80 */     if (transactionManager == null) {
/*  38: 81 */       throw new HibernateException("No TransactionManagerLookup specified");
/*  39:    */     }
/*  40:    */     Transaction txn;
/*  41:    */     try
/*  42:    */     {
/*  43: 86 */       txn = transactionManager.getTransaction();
/*  44: 87 */       if (txn == null) {
/*  45: 88 */         throw new HibernateException("Unable to locate current JTA transaction");
/*  46:    */       }
/*  47: 90 */       if (!JtaStatusHelper.isActive(txn.getStatus())) {
/*  48: 94 */         throw new HibernateException("Current transaction is not in progress");
/*  49:    */       }
/*  50:    */     }
/*  51:    */     catch (HibernateException e)
/*  52:    */     {
/*  53: 98 */       throw e;
/*  54:    */     }
/*  55:    */     catch (Throwable t)
/*  56:    */     {
/*  57:101 */       throw new HibernateException("Problem locating/validating JTA transaction", t);
/*  58:    */     }
/*  59:104 */     Object txnIdentifier = jtaPlatform.getTransactionIdentifier(txn);
/*  60:    */     
/*  61:106 */     Session currentSession = (Session)this.currentSessionMap.get(txnIdentifier);
/*  62:108 */     if (currentSession == null)
/*  63:    */     {
/*  64:109 */       currentSession = buildOrObtainSession();
/*  65:    */       try
/*  66:    */       {
/*  67:112 */         txn.registerSynchronization(buildCleanupSynch(txnIdentifier));
/*  68:    */       }
/*  69:    */       catch (Throwable t)
/*  70:    */       {
/*  71:    */         try
/*  72:    */         {
/*  73:116 */           currentSession.close();
/*  74:    */         }
/*  75:    */         catch (Throwable ignore)
/*  76:    */         {
/*  77:119 */           LOG.debug("Unable to release generated current-session on failed synch registration", ignore);
/*  78:    */         }
/*  79:121 */         throw new HibernateException("Unable to register cleanup Synchronization with TransactionManager");
/*  80:    */       }
/*  81:124 */       this.currentSessionMap.put(txnIdentifier, currentSession);
/*  82:    */     }
/*  83:127 */     return currentSession;
/*  84:    */   }
/*  85:    */   
/*  86:    */   private CleanupSynch buildCleanupSynch(Object transactionIdentifier)
/*  87:    */   {
/*  88:138 */     return new CleanupSynch(transactionIdentifier, this);
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected Session buildOrObtainSession()
/*  92:    */   {
/*  93:150 */     return this.factory.withOptions().autoClose(isAutoCloseEnabled()).connectionReleaseMode(getConnectionReleaseMode()).flushBeforeCompletion(isAutoFlushEnabled()).openSession();
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected boolean isAutoCloseEnabled()
/*  97:    */   {
/*  98:163 */     return true;
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected boolean isAutoFlushEnabled()
/* 102:    */   {
/* 103:172 */     return true;
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected ConnectionReleaseMode getConnectionReleaseMode()
/* 107:    */   {
/* 108:181 */     return ConnectionReleaseMode.AFTER_STATEMENT;
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected static class CleanupSynch
/* 112:    */     implements Synchronization
/* 113:    */   {
/* 114:    */     private Object transactionIdentifier;
/* 115:    */     private JTASessionContext context;
/* 116:    */     
/* 117:    */     public CleanupSynch(Object transactionIdentifier, JTASessionContext context)
/* 118:    */     {
/* 119:192 */       this.transactionIdentifier = transactionIdentifier;
/* 120:193 */       this.context = context;
/* 121:    */     }
/* 122:    */     
/* 123:    */     public void beforeCompletion() {}
/* 124:    */     
/* 125:    */     public void afterCompletion(int i)
/* 126:    */     {
/* 127:206 */       this.context.currentSessionMap.remove(this.transactionIdentifier);
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.context.internal.JTASessionContext
 * JD-Core Version:    0.7.0.1
 */
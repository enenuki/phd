/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import java.util.Properties;
/*  4:   */ import javax.transaction.Transaction;
/*  5:   */ import javax.transaction.TransactionManager;
/*  6:   */ import javax.transaction.UserTransaction;
/*  7:   */ import org.hibernate.service.ServiceRegistry;
/*  8:   */ import org.hibernate.service.jndi.spi.JndiService;
/*  9:   */ import org.hibernate.transaction.TransactionManagerLookup;
/* 10:   */ 
/* 11:   */ public class TransactionManagerLookupBridge
/* 12:   */   extends AbstractJtaPlatform
/* 13:   */ {
/* 14:   */   private final TransactionManagerLookup lookup;
/* 15:   */   private final Properties jndiProperties;
/* 16:   */   
/* 17:   */   public TransactionManagerLookupBridge(TransactionManagerLookup lookup, Properties jndiProperties)
/* 18:   */   {
/* 19:42 */     this.lookup = lookup;
/* 20:43 */     this.jndiProperties = jndiProperties;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected TransactionManager locateTransactionManager()
/* 24:   */   {
/* 25:48 */     return this.lookup.getTransactionManager(this.jndiProperties);
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected UserTransaction locateUserTransaction()
/* 29:   */   {
/* 30:53 */     return (UserTransaction)((JndiService)serviceRegistry().getService(JndiService.class)).locate(this.lookup.getUserTransactionName());
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Object getTransactionIdentifier(Transaction transaction)
/* 34:   */   {
/* 35:58 */     return this.lookup.getTransactionIdentifier(transaction);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.TransactionManagerLookupBridge
 * JD-Core Version:    0.7.0.1
 */
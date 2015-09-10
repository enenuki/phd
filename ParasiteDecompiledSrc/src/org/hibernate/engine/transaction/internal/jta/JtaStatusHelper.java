/*   1:    */ package org.hibernate.engine.transaction.internal.jta;
/*   2:    */ 
/*   3:    */ import javax.transaction.SystemException;
/*   4:    */ import javax.transaction.TransactionManager;
/*   5:    */ import javax.transaction.UserTransaction;
/*   6:    */ import org.hibernate.TransactionException;
/*   7:    */ 
/*   8:    */ public class JtaStatusHelper
/*   9:    */ {
/*  10:    */   public static int getStatus(UserTransaction userTransaction)
/*  11:    */   {
/*  12:    */     try
/*  13:    */     {
/*  14: 50 */       int status = userTransaction.getStatus();
/*  15: 51 */       if (status == 5) {
/*  16: 52 */         throw new TransactionException("UserTransaction reported transaction status as unknown");
/*  17:    */       }
/*  18: 54 */       return status;
/*  19:    */     }
/*  20:    */     catch (SystemException se)
/*  21:    */     {
/*  22: 57 */       throw new TransactionException("Could not determine transaction status", se);
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static int getStatus(TransactionManager transactionManager)
/*  27:    */   {
/*  28:    */     try
/*  29:    */     {
/*  30: 73 */       int status = transactionManager.getStatus();
/*  31: 74 */       if (status == 5) {
/*  32: 75 */         throw new TransactionException("TransactionManager reported transaction status as unknwon");
/*  33:    */       }
/*  34: 77 */       return status;
/*  35:    */     }
/*  36:    */     catch (SystemException se)
/*  37:    */     {
/*  38: 80 */       throw new TransactionException("Could not determine transaction status", se);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static boolean isActive(int status)
/*  43:    */   {
/*  44: 92 */     return status == 0;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static boolean isActive(UserTransaction userTransaction)
/*  48:    */   {
/*  49:103 */     int status = getStatus(userTransaction);
/*  50:104 */     return isActive(status);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static boolean isActive(TransactionManager transactionManager)
/*  54:    */   {
/*  55:115 */     return isActive(getStatus(transactionManager));
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static boolean isRollback(int status)
/*  59:    */   {
/*  60:126 */     return (status == 1) || (status == 9) || (status == 4);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static boolean isRollback(UserTransaction userTransaction)
/*  64:    */   {
/*  65:139 */     return isRollback(getStatus(userTransaction));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static boolean isRollback(TransactionManager transactionManager)
/*  69:    */   {
/*  70:150 */     return isRollback(getStatus(transactionManager));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static boolean isCommitted(int status)
/*  74:    */   {
/*  75:161 */     return status == 3;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static boolean isCommitted(UserTransaction userTransaction)
/*  79:    */   {
/*  80:172 */     return isCommitted(getStatus(userTransaction));
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static boolean isCommitted(TransactionManager transactionManager)
/*  84:    */   {
/*  85:183 */     return isCommitted(getStatus(transactionManager));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static boolean isMarkedForRollback(int status)
/*  89:    */   {
/*  90:195 */     return status == 1;
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.jta.JtaStatusHelper
 * JD-Core Version:    0.7.0.1
 */
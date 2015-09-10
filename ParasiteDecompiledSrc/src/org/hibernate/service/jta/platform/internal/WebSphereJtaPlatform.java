/*  1:   */ package org.hibernate.service.jta.platform.internal;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import javax.transaction.TransactionManager;
/*  5:   */ import javax.transaction.UserTransaction;
/*  6:   */ import org.hibernate.internal.CoreMessageLogger;
/*  7:   */ import org.hibernate.service.jndi.spi.JndiService;
/*  8:   */ import org.hibernate.service.jta.platform.spi.JtaPlatformException;
/*  9:   */ import org.jboss.logging.Logger;
/* 10:   */ 
/* 11:   */ public class WebSphereJtaPlatform
/* 12:   */   extends AbstractJtaPlatform
/* 13:   */ {
/* 14:42 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, WebSphereJtaPlatform.class.getName());
/* 15:   */   public static final String VERSION_5_UT_NAME = "java:comp/UserTransaction";
/* 16:   */   public static final String VERSION_4_UT_NAME = "jta/usertransaction";
/* 17:   */   private final Class transactionManagerAccessClass;
/* 18:   */   private final int webSphereVersion;
/* 19:   */   
/* 20:   */   public WebSphereJtaPlatform()
/* 21:   */   {
/* 22:   */     try
/* 23:   */     {
/* 24:   */       Class clazz;
/* 25:   */       int version;
/* 26:   */       try
/* 27:   */       {
/* 28:55 */         clazz = Class.forName("com.ibm.ws.Transaction.TransactionManagerFactory");
/* 29:56 */         version = 5;
/* 30:57 */         LOG.debug("WebSphere 5.1");
/* 31:   */       }
/* 32:   */       catch (Exception e)
/* 33:   */       {
/* 34:   */         try
/* 35:   */         {
/* 36:61 */           clazz = Class.forName("com.ibm.ejs.jts.jta.TransactionManagerFactory");
/* 37:62 */           version = 5;
/* 38:63 */           LOG.debug("WebSphere 5.0");
/* 39:   */         }
/* 40:   */         catch (Exception e2)
/* 41:   */         {
/* 42:66 */           clazz = Class.forName("com.ibm.ejs.jts.jta.JTSXA");
/* 43:67 */           version = 4;
/* 44:68 */           LOG.debug("WebSphere 4");
/* 45:   */         }
/* 46:   */       }
/* 47:72 */       this.transactionManagerAccessClass = clazz;
/* 48:73 */       this.webSphereVersion = version;
/* 49:   */     }
/* 50:   */     catch (Exception e)
/* 51:   */     {
/* 52:76 */       throw new JtaPlatformException("Could not locate WebSphere TransactionManager access class", e);
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected TransactionManager locateTransactionManager()
/* 57:   */   {
/* 58:   */     try
/* 59:   */     {
/* 60:83 */       Method method = this.transactionManagerAccessClass.getMethod("getTransactionManager", new Class[0]);
/* 61:84 */       return (TransactionManager)method.invoke(null, new Object[0]);
/* 62:   */     }
/* 63:   */     catch (Exception e)
/* 64:   */     {
/* 65:87 */       throw new JtaPlatformException("Could not obtain WebSphere TransactionManager", e);
/* 66:   */     }
/* 67:   */   }
/* 68:   */   
/* 69:   */   protected UserTransaction locateUserTransaction()
/* 70:   */   {
/* 71:94 */     String utName = this.webSphereVersion == 5 ? "java:comp/UserTransaction" : "jta/usertransaction";
/* 72:95 */     return (UserTransaction)jndiService().locate(utName);
/* 73:   */   }
/* 74:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.WebSphereJtaPlatform
 * JD-Core Version:    0.7.0.1
 */
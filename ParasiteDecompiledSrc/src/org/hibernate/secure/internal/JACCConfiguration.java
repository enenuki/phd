/*  1:   */ package org.hibernate.secure.internal;
/*  2:   */ 
/*  3:   */ import java.util.StringTokenizer;
/*  4:   */ import javax.security.jacc.EJBMethodPermission;
/*  5:   */ import javax.security.jacc.PolicyConfiguration;
/*  6:   */ import javax.security.jacc.PolicyConfigurationFactory;
/*  7:   */ import javax.security.jacc.PolicyContextException;
/*  8:   */ import org.hibernate.HibernateException;
/*  9:   */ import org.hibernate.internal.CoreMessageLogger;
/* 10:   */ import org.jboss.logging.Logger;
/* 11:   */ 
/* 12:   */ public class JACCConfiguration
/* 13:   */ {
/* 14:44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JACCConfiguration.class.getName());
/* 15:   */   private final PolicyConfiguration policyConfiguration;
/* 16:   */   
/* 17:   */   public JACCConfiguration(String contextId)
/* 18:   */     throws HibernateException
/* 19:   */   {
/* 20:   */     try
/* 21:   */     {
/* 22:50 */       this.policyConfiguration = PolicyConfigurationFactory.getPolicyConfigurationFactory().getPolicyConfiguration(contextId, false);
/* 23:   */     }
/* 24:   */     catch (ClassNotFoundException cnfe)
/* 25:   */     {
/* 26:55 */       throw new HibernateException("JACC provider class not found", cnfe);
/* 27:   */     }
/* 28:   */     catch (PolicyContextException pce)
/* 29:   */     {
/* 30:58 */       throw new HibernateException("policy context exception occurred", pce);
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void addPermission(String role, String entityName, String action)
/* 35:   */   {
/* 36:64 */     if (action.equals("*")) {
/* 37:65 */       action = "insert,read,update,delete";
/* 38:   */     }
/* 39:68 */     StringTokenizer tok = new StringTokenizer(action, ",");
/* 40:70 */     while (tok.hasMoreTokens())
/* 41:   */     {
/* 42:71 */       String methodName = tok.nextToken().trim();
/* 43:72 */       EJBMethodPermission permission = new EJBMethodPermission(entityName, methodName, null, null);
/* 44:   */       
/* 45:   */ 
/* 46:   */ 
/* 47:   */ 
/* 48:   */ 
/* 49:   */ 
/* 50:79 */       LOG.debugf("Adding permission to role %s: %s", role, permission);
/* 51:   */       try
/* 52:   */       {
/* 53:81 */         this.policyConfiguration.addToRole(role, permission);
/* 54:   */       }
/* 55:   */       catch (PolicyContextException pce)
/* 56:   */       {
/* 57:84 */         throw new HibernateException("policy context exception occurred", pce);
/* 58:   */       }
/* 59:   */     }
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.secure.internal.JACCConfiguration
 * JD-Core Version:    0.7.0.1
 */
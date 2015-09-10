/*   1:    */ package org.hibernate.engine.jdbc.internal;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.DatabaseMetaData;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.hibernate.engine.jdbc.ContextualLobCreator;
/*   9:    */ import org.hibernate.engine.jdbc.LobCreationContext;
/*  10:    */ import org.hibernate.engine.jdbc.LobCreator;
/*  11:    */ import org.hibernate.engine.jdbc.NonContextualLobCreator;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  14:    */ import org.jboss.logging.Logger;
/*  15:    */ 
/*  16:    */ public class LobCreatorBuilder
/*  17:    */ {
/*  18: 49 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, LobCreatorBuilder.class.getName());
/*  19:    */   private boolean useContextualLobCreation;
/*  20:    */   
/*  21:    */   public LobCreatorBuilder(Map configValues, Connection jdbcConnection)
/*  22:    */   {
/*  23: 61 */     this.useContextualLobCreation = useContextualLobCreation(configValues, jdbcConnection);
/*  24:    */   }
/*  25:    */   
/*  26: 64 */   private static final Class[] NO_ARG_SIG = new Class[0];
/*  27: 65 */   private static final Object[] NO_ARGS = new Object[0];
/*  28:    */   
/*  29:    */   private static boolean useContextualLobCreation(Map configValues, Connection jdbcConnection)
/*  30:    */   {
/*  31: 78 */     boolean isNonContextualLobCreationRequired = ConfigurationHelper.getBoolean("hibernate.jdbc.lob.non_contextual_creation", configValues);
/*  32: 80 */     if (isNonContextualLobCreationRequired)
/*  33:    */     {
/*  34: 81 */       LOG.disablingContextualLOBCreation("hibernate.jdbc.lob.non_contextual_creation");
/*  35: 82 */       return false;
/*  36:    */     }
/*  37: 84 */     if (jdbcConnection == null)
/*  38:    */     {
/*  39: 85 */       LOG.disablingContextualLOBCreationSinceConnectionNull();
/*  40: 86 */       return false;
/*  41:    */     }
/*  42:    */     try
/*  43:    */     {
/*  44:    */       try
/*  45:    */       {
/*  46: 91 */         DatabaseMetaData meta = jdbcConnection.getMetaData();
/*  47: 93 */         if (meta.getJDBCMajorVersion() < 4)
/*  48:    */         {
/*  49: 94 */           LOG.disablingContextualLOBCreationSinceOldJdbcVersion(meta.getJDBCMajorVersion());
/*  50: 95 */           return false;
/*  51:    */         }
/*  52:    */       }
/*  53:    */       catch (SQLException ignore) {}
/*  54:102 */       Class connectionClass = Connection.class;
/*  55:103 */       Method createClobMethod = connectionClass.getMethod("createClob", NO_ARG_SIG);
/*  56:104 */       if (createClobMethod.getDeclaringClass().equals(Connection.class)) {
/*  57:    */         try
/*  58:    */         {
/*  59:109 */           Object clob = createClobMethod.invoke(jdbcConnection, NO_ARGS);
/*  60:    */           try
/*  61:    */           {
/*  62:111 */             Method freeMethod = clob.getClass().getMethod("free", NO_ARG_SIG);
/*  63:112 */             freeMethod.invoke(clob, NO_ARGS);
/*  64:    */           }
/*  65:    */           catch (Throwable ignore)
/*  66:    */           {
/*  67:115 */             LOG.tracef("Unable to free CLOB created to test createClob() implementation : %s", ignore);
/*  68:    */           }
/*  69:117 */           return true;
/*  70:    */         }
/*  71:    */         catch (Throwable t)
/*  72:    */         {
/*  73:120 */           LOG.disablingContextualLOBCreationSinceCreateClobFailed(t);
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:    */     catch (NoSuchMethodException ignore) {}
/*  78:127 */     return false;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public LobCreator buildLobCreator(LobCreationContext lobCreationContext)
/*  82:    */   {
/*  83:131 */     return this.useContextualLobCreation ? new ContextualLobCreator(lobCreationContext) : NonContextualLobCreator.INSTANCE;
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.LobCreatorBuilder
 * JD-Core Version:    0.7.0.1
 */
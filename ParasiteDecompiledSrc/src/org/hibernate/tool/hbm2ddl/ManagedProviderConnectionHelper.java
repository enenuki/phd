/*   1:    */ package org.hibernate.tool.hbm2ddl;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.Properties;
/*   6:    */ import org.hibernate.cfg.Environment;
/*   7:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   8:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*   9:    */ import org.hibernate.service.ServiceRegistryBuilder;
/*  10:    */ import org.hibernate.service.internal.StandardServiceRegistryImpl;
/*  11:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  12:    */ 
/*  13:    */ class ManagedProviderConnectionHelper
/*  14:    */   implements ConnectionHelper
/*  15:    */ {
/*  16:    */   private Properties cfgProperties;
/*  17:    */   private StandardServiceRegistryImpl serviceRegistry;
/*  18:    */   private Connection connection;
/*  19:    */   
/*  20:    */   public ManagedProviderConnectionHelper(Properties cfgProperties)
/*  21:    */   {
/*  22: 50 */     this.cfgProperties = cfgProperties;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void prepare(boolean needsAutoCommit)
/*  26:    */     throws SQLException
/*  27:    */   {
/*  28: 54 */     this.serviceRegistry = createServiceRegistry(this.cfgProperties);
/*  29: 55 */     this.connection = ((ConnectionProvider)this.serviceRegistry.getService(ConnectionProvider.class)).getConnection();
/*  30: 56 */     if ((needsAutoCommit) && (!this.connection.getAutoCommit()))
/*  31:    */     {
/*  32: 57 */       this.connection.commit();
/*  33: 58 */       this.connection.setAutoCommit(true);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   private static StandardServiceRegistryImpl createServiceRegistry(Properties properties)
/*  38:    */   {
/*  39: 63 */     Environment.verifyProperties(properties);
/*  40: 64 */     ConfigurationHelper.resolvePlaceHolders(properties);
/*  41: 65 */     return (StandardServiceRegistryImpl)new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Connection getConnection()
/*  45:    */     throws SQLException
/*  46:    */   {
/*  47: 69 */     return this.connection;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void release()
/*  51:    */     throws SQLException
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55: 74 */       releaseConnection();
/*  56:    */     }
/*  57:    */     finally
/*  58:    */     {
/*  59: 77 */       releaseServiceRegistry();
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void releaseConnection()
/*  64:    */     throws SQLException
/*  65:    */   {
/*  66: 82 */     if (this.connection != null) {
/*  67:    */       try
/*  68:    */       {
/*  69: 84 */         new SqlExceptionHelper().logAndClearWarnings(this.connection);
/*  70:    */       }
/*  71:    */       finally
/*  72:    */       {
/*  73:    */         try
/*  74:    */         {
/*  75: 88 */           ((ConnectionProvider)this.serviceRegistry.getService(ConnectionProvider.class)).closeConnection(this.connection);
/*  76:    */         }
/*  77:    */         finally
/*  78:    */         {
/*  79: 91 */           this.connection = null;
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void releaseServiceRegistry()
/*  86:    */   {
/*  87: 98 */     if (this.serviceRegistry != null) {
/*  88:    */       try
/*  89:    */       {
/*  90:100 */         this.serviceRegistry.destroy();
/*  91:    */       }
/*  92:    */       finally
/*  93:    */       {
/*  94:103 */         this.serviceRegistry = null;
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.ManagedProviderConnectionHelper
 * JD-Core Version:    0.7.0.1
 */
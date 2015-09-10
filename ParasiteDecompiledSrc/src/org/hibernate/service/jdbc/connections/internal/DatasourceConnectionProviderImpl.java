/*   1:    */ package org.hibernate.service.jdbc.connections.internal;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.Map;
/*   6:    */ import javax.sql.DataSource;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.service.UnknownUnwrapTypeException;
/*   9:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  10:    */ import org.hibernate.service.jndi.spi.JndiService;
/*  11:    */ import org.hibernate.service.spi.Configurable;
/*  12:    */ import org.hibernate.service.spi.InjectService;
/*  13:    */ import org.hibernate.service.spi.Stoppable;
/*  14:    */ 
/*  15:    */ public class DatasourceConnectionProviderImpl
/*  16:    */   implements ConnectionProvider, Configurable, Stoppable
/*  17:    */ {
/*  18:    */   private DataSource dataSource;
/*  19:    */   private String user;
/*  20:    */   private String pass;
/*  21:    */   private boolean useCredentials;
/*  22:    */   private JndiService jndiService;
/*  23:    */   private boolean available;
/*  24:    */   
/*  25:    */   public DataSource getDataSource()
/*  26:    */   {
/*  27: 62 */     return this.dataSource;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setDataSource(DataSource dataSource)
/*  31:    */   {
/*  32: 66 */     this.dataSource = dataSource;
/*  33:    */   }
/*  34:    */   
/*  35:    */   @InjectService(required=false)
/*  36:    */   public void setJndiService(JndiService jndiService)
/*  37:    */   {
/*  38: 71 */     this.jndiService = jndiService;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean isUnwrappableAs(Class unwrapType)
/*  42:    */   {
/*  43: 76 */     return (ConnectionProvider.class.equals(unwrapType)) || (DatasourceConnectionProviderImpl.class.isAssignableFrom(unwrapType)) || (DataSource.class.isAssignableFrom(unwrapType));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public <T> T unwrap(Class<T> unwrapType)
/*  47:    */   {
/*  48: 84 */     if ((ConnectionProvider.class.equals(unwrapType)) || (DatasourceConnectionProviderImpl.class.isAssignableFrom(unwrapType))) {
/*  49: 86 */       return this;
/*  50:    */     }
/*  51: 88 */     if (DataSource.class.isAssignableFrom(unwrapType)) {
/*  52: 89 */       return getDataSource();
/*  53:    */     }
/*  54: 92 */     throw new UnknownUnwrapTypeException(unwrapType);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void configure(Map configValues)
/*  58:    */   {
/*  59:100 */     if (this.dataSource == null)
/*  60:    */     {
/*  61:101 */       Object dataSource = configValues.get("hibernate.connection.datasource");
/*  62:102 */       if (DataSource.class.isInstance(dataSource))
/*  63:    */       {
/*  64:103 */         this.dataSource = ((DataSource)dataSource);
/*  65:    */       }
/*  66:    */       else
/*  67:    */       {
/*  68:106 */         String dataSourceJndiName = (String)dataSource;
/*  69:107 */         if (dataSourceJndiName == null) {
/*  70:108 */           throw new HibernateException("DataSource to use was not injected nor specified by [hibernate.connection.datasource] configuration property");
/*  71:    */         }
/*  72:113 */         if (this.jndiService == null) {
/*  73:114 */           throw new HibernateException("Unable to locate JndiService to lookup Datasource");
/*  74:    */         }
/*  75:116 */         this.dataSource = ((DataSource)this.jndiService.locate(dataSourceJndiName));
/*  76:    */       }
/*  77:    */     }
/*  78:119 */     if (this.dataSource == null) {
/*  79:120 */       throw new HibernateException("Unable to determine appropriate DataSource to use");
/*  80:    */     }
/*  81:123 */     this.user = ((String)configValues.get("hibernate.connection.username"));
/*  82:124 */     this.pass = ((String)configValues.get("hibernate.connection.password"));
/*  83:125 */     this.useCredentials = ((this.user != null) || (this.pass != null));
/*  84:126 */     this.available = true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void stop()
/*  88:    */   {
/*  89:130 */     this.available = false;
/*  90:131 */     this.dataSource = null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Connection getConnection()
/*  94:    */     throws SQLException
/*  95:    */   {
/*  96:138 */     if (!this.available) {
/*  97:139 */       throw new HibernateException("Provider is closed!");
/*  98:    */     }
/*  99:141 */     return this.useCredentials ? this.dataSource.getConnection(this.user, this.pass) : this.dataSource.getConnection();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void closeConnection(Connection connection)
/* 103:    */     throws SQLException
/* 104:    */   {
/* 105:148 */     connection.close();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean supportsAggressiveRelease()
/* 109:    */   {
/* 110:155 */     return true;
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.connections.internal.DatasourceConnectionProviderImpl
 * JD-Core Version:    0.7.0.1
 */
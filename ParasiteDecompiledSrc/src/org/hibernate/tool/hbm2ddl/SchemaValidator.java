/*   1:    */ package org.hibernate.tool.hbm2ddl;
/*   2:    */ 
/*   3:    */ import java.io.FileInputStream;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.cfg.Configuration;
/*   9:    */ import org.hibernate.cfg.Environment;
/*  10:    */ import org.hibernate.cfg.NamingStrategy;
/*  11:    */ import org.hibernate.dialect.Dialect;
/*  12:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.internal.util.ReflectHelper;
/*  15:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  16:    */ import org.hibernate.service.ServiceRegistry;
/*  17:    */ import org.hibernate.service.ServiceRegistryBuilder;
/*  18:    */ import org.hibernate.service.internal.StandardServiceRegistryImpl;
/*  19:    */ import org.jboss.logging.Logger;
/*  20:    */ 
/*  21:    */ public class SchemaValidator
/*  22:    */ {
/*  23: 53 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SchemaValidator.class.getName());
/*  24:    */   private ConnectionHelper connectionHelper;
/*  25:    */   private Configuration configuration;
/*  26:    */   private Dialect dialect;
/*  27:    */   
/*  28:    */   public SchemaValidator(Configuration cfg)
/*  29:    */     throws HibernateException
/*  30:    */   {
/*  31: 60 */     this(cfg, cfg.getProperties());
/*  32:    */   }
/*  33:    */   
/*  34:    */   public SchemaValidator(Configuration cfg, Properties connectionProperties)
/*  35:    */     throws HibernateException
/*  36:    */   {
/*  37: 64 */     this.configuration = cfg;
/*  38: 65 */     this.dialect = Dialect.getDialect(connectionProperties);
/*  39: 66 */     Properties props = new Properties();
/*  40: 67 */     props.putAll(this.dialect.getDefaultProperties());
/*  41: 68 */     props.putAll(connectionProperties);
/*  42: 69 */     this.connectionHelper = new ManagedProviderConnectionHelper(props);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public SchemaValidator(ServiceRegistry serviceRegistry, Configuration cfg)
/*  46:    */     throws HibernateException
/*  47:    */   {
/*  48: 73 */     this.configuration = cfg;
/*  49: 74 */     JdbcServices jdbcServices = (JdbcServices)serviceRegistry.getService(JdbcServices.class);
/*  50: 75 */     this.dialect = jdbcServices.getDialect();
/*  51: 76 */     this.connectionHelper = new SuppliedConnectionProviderConnectionHelper(jdbcServices.getConnectionProvider());
/*  52:    */   }
/*  53:    */   
/*  54:    */   private static StandardServiceRegistryImpl createServiceRegistry(Properties properties)
/*  55:    */   {
/*  56: 80 */     Environment.verifyProperties(properties);
/*  57: 81 */     ConfigurationHelper.resolvePlaceHolders(properties);
/*  58: 82 */     return (StandardServiceRegistryImpl)new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static void main(String[] args)
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65: 87 */       Configuration cfg = new Configuration();
/*  66:    */       
/*  67: 89 */       String propFile = null;
/*  68: 91 */       for (int i = 0; i < args.length; i++) {
/*  69: 92 */         if (args[i].startsWith("--"))
/*  70:    */         {
/*  71: 93 */           if (args[i].startsWith("--properties=")) {
/*  72: 94 */             propFile = args[i].substring(13);
/*  73: 96 */           } else if (args[i].startsWith("--config=")) {
/*  74: 97 */             cfg.configure(args[i].substring(9));
/*  75: 99 */           } else if (args[i].startsWith("--naming=")) {
/*  76:100 */             cfg.setNamingStrategy((NamingStrategy)ReflectHelper.classForName(args[i].substring(9)).newInstance());
/*  77:    */           }
/*  78:    */         }
/*  79:    */         else {
/*  80:106 */           cfg.addFile(args[i]);
/*  81:    */         }
/*  82:    */       }
/*  83:111 */       if (propFile != null)
/*  84:    */       {
/*  85:112 */         Properties props = new Properties();
/*  86:113 */         props.putAll(cfg.getProperties());
/*  87:114 */         props.load(new FileInputStream(propFile));
/*  88:115 */         cfg.setProperties(props);
/*  89:    */       }
/*  90:118 */       StandardServiceRegistryImpl serviceRegistry = createServiceRegistry(cfg.getProperties());
/*  91:    */       try
/*  92:    */       {
/*  93:120 */         new SchemaValidator(serviceRegistry, cfg).validate();
/*  94:    */       }
/*  95:    */       finally
/*  96:    */       {
/*  97:123 */         serviceRegistry.destroy();
/*  98:    */       }
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:127 */       LOG.unableToRunSchemaUpdate(e);
/* 103:128 */       e.printStackTrace();
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void validate()
/* 108:    */   {
/* 109:137 */     LOG.runningSchemaValidator();
/* 110:    */     
/* 111:139 */     Connection connection = null;
/* 112:    */     try
/* 113:    */     {
/* 114:    */       DatabaseMetadata meta;
/* 115:    */       try
/* 116:    */       {
/* 117:145 */         LOG.fetchingDatabaseMetadata();
/* 118:146 */         this.connectionHelper.prepare(false);
/* 119:147 */         connection = this.connectionHelper.getConnection();
/* 120:148 */         meta = new DatabaseMetadata(connection, this.dialect, false);
/* 121:    */       }
/* 122:    */       catch (SQLException sqle)
/* 123:    */       {
/* 124:151 */         LOG.unableToGetDatabaseMetadata(sqle);
/* 125:152 */         throw sqle;
/* 126:    */       }
/* 127:155 */       this.configuration.validateSchema(this.dialect, meta); return;
/* 128:    */     }
/* 129:    */     catch (SQLException e)
/* 130:    */     {
/* 131:159 */       LOG.unableToCompleteSchemaValidation(e);
/* 132:    */     }
/* 133:    */     finally
/* 134:    */     {
/* 135:    */       try
/* 136:    */       {
/* 137:164 */         this.connectionHelper.release();
/* 138:    */       }
/* 139:    */       catch (Exception e)
/* 140:    */       {
/* 141:167 */         LOG.unableToCloseConnection(e);
/* 142:    */       }
/* 143:    */     }
/* 144:    */   }
/* 145:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.SchemaValidator
 * JD-Core Version:    0.7.0.1
 */
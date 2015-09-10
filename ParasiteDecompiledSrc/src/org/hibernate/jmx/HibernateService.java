/*   1:    */ package org.hibernate.jmx;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import java.util.Properties;
/*   5:    */ import javax.naming.InitialContext;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.SessionFactory;
/*   8:    */ import org.hibernate.cfg.Configuration;
/*   9:    */ import org.hibernate.cfg.ExternalSessionFactoryConfig;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.internal.util.jndi.JndiHelper;
/*  12:    */ import org.hibernate.service.ServiceRegistryBuilder;
/*  13:    */ import org.hibernate.tool.hbm2ddl.SchemaExport;
/*  14:    */ import org.jboss.logging.Logger;
/*  15:    */ 
/*  16:    */ @Deprecated
/*  17:    */ public class HibernateService
/*  18:    */   extends ExternalSessionFactoryConfig
/*  19:    */   implements HibernateServiceMBean
/*  20:    */ {
/*  21: 34 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, HibernateService.class.getName());
/*  22:    */   private String boundName;
/*  23: 37 */   private Properties properties = new Properties();
/*  24:    */   
/*  25:    */   public void start()
/*  26:    */     throws HibernateException
/*  27:    */   {
/*  28: 41 */     this.boundName = getJndiName();
/*  29:    */     try
/*  30:    */     {
/*  31: 43 */       buildSessionFactory();
/*  32:    */     }
/*  33:    */     catch (HibernateException he)
/*  34:    */     {
/*  35: 46 */       LOG.unableToBuildSessionFactoryUsingMBeanClasspath(he.getMessage());
/*  36: 47 */       LOG.debug("Error was", he);
/*  37: 48 */       new SessionFactoryStub(this);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void stop()
/*  42:    */   {
/*  43: 54 */     LOG.stoppingService();
/*  44:    */     try
/*  45:    */     {
/*  46: 56 */       InitialContext context = JndiHelper.getInitialContext(buildProperties());
/*  47: 57 */       ((SessionFactory)context.lookup(this.boundName)).close();
/*  48:    */     }
/*  49:    */     catch (Exception e)
/*  50:    */     {
/*  51: 61 */       LOG.unableToStopHibernateService(e);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   SessionFactory buildSessionFactory()
/*  56:    */     throws HibernateException
/*  57:    */   {
/*  58: 66 */     LOG.startingServiceAtJndiName(this.boundName);
/*  59: 67 */     LOG.serviceProperties(this.properties);
/*  60: 68 */     return buildConfiguration().buildSessionFactory(new ServiceRegistryBuilder().applySettings(this.properties).buildServiceRegistry());
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected Map getExtraProperties()
/*  64:    */   {
/*  65: 75 */     return this.properties;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getTransactionStrategy()
/*  69:    */   {
/*  70: 80 */     return getProperty("hibernate.transaction.factory_class");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setTransactionStrategy(String txnStrategy)
/*  74:    */   {
/*  75: 85 */     setProperty("hibernate.transaction.factory_class", txnStrategy);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getUserTransactionName()
/*  79:    */   {
/*  80: 90 */     return getProperty("jta.UserTransaction");
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setUserTransactionName(String utName)
/*  84:    */   {
/*  85: 95 */     setProperty("jta.UserTransaction", utName);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getJtaPlatformName()
/*  89:    */   {
/*  90:100 */     return getProperty("hibernate.transaction.jta.platform");
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setJtaPlatformName(String name)
/*  94:    */   {
/*  95:105 */     setProperty("hibernate.transaction.jta.platform", name);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getPropertyList()
/*  99:    */   {
/* 100:110 */     return buildProperties().toString();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getProperty(String property)
/* 104:    */   {
/* 105:115 */     return this.properties.getProperty(property);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setProperty(String property, String value)
/* 109:    */   {
/* 110:120 */     this.properties.setProperty(property, value);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void dropSchema()
/* 114:    */   {
/* 115:125 */     new SchemaExport(buildConfiguration()).drop(false, true);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void createSchema()
/* 119:    */   {
/* 120:130 */     new SchemaExport(buildConfiguration()).create(false, true);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getName()
/* 124:    */   {
/* 125:134 */     return getProperty("hibernate.session_factory_name");
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getDatasource()
/* 129:    */   {
/* 130:139 */     return getProperty("hibernate.connection.datasource");
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setDatasource(String datasource)
/* 134:    */   {
/* 135:144 */     setProperty("hibernate.connection.datasource", datasource);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getJndiName()
/* 139:    */   {
/* 140:149 */     return getProperty("hibernate.session_factory_name");
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setJndiName(String jndiName)
/* 144:    */   {
/* 145:154 */     setProperty("hibernate.session_factory_name", jndiName);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getUserName()
/* 149:    */   {
/* 150:159 */     return getProperty("hibernate.connection.username");
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setUserName(String userName)
/* 154:    */   {
/* 155:164 */     setProperty("hibernate.connection.username", userName);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getPassword()
/* 159:    */   {
/* 160:169 */     return getProperty("hibernate.connection.password");
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setPassword(String password)
/* 164:    */   {
/* 165:174 */     setProperty("hibernate.connection.password", password);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setFlushBeforeCompletionEnabled(String enabled)
/* 169:    */   {
/* 170:179 */     setProperty("hibernate.transaction.flush_before_completion", enabled);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String getFlushBeforeCompletionEnabled()
/* 174:    */   {
/* 175:184 */     return getProperty("hibernate.transaction.flush_before_completion");
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setAutoCloseSessionEnabled(String enabled)
/* 179:    */   {
/* 180:189 */     setProperty("hibernate.transaction.auto_close_session", enabled);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String getAutoCloseSessionEnabled()
/* 184:    */   {
/* 185:194 */     return getProperty("hibernate.transaction.auto_close_session");
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Properties getProperties()
/* 189:    */   {
/* 190:198 */     return buildProperties();
/* 191:    */   }
/* 192:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jmx.HibernateService
 * JD-Core Version:    0.7.0.1
 */
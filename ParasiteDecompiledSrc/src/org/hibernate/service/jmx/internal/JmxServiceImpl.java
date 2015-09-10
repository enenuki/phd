/*   1:    */ package org.hibernate.service.jmx.internal;
/*   2:    */ 
/*   3:    */ import java.lang.management.ManagementFactory;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Map;
/*   6:    */ import javax.management.MBeanServer;
/*   7:    */ import javax.management.MBeanServerFactory;
/*   8:    */ import javax.management.MalformedObjectNameException;
/*   9:    */ import javax.management.ObjectName;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.internal.CoreMessageLogger;
/*  12:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  13:    */ import org.hibernate.service.Service;
/*  14:    */ import org.hibernate.service.jmx.spi.JmxService;
/*  15:    */ import org.hibernate.service.spi.Manageable;
/*  16:    */ import org.hibernate.service.spi.Stoppable;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ public class JmxServiceImpl
/*  20:    */   implements JmxService, Stoppable
/*  21:    */ {
/*  22: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JmxServiceImpl.class.getName());
/*  23:    */   public static final String OBJ_NAME_TEMPLATE = "%s:sessionFactory=%s,serviceRole=%s,serviceType=%s";
/*  24:    */   private final boolean usePlatformServer;
/*  25:    */   private final String agentId;
/*  26:    */   private final String defaultDomain;
/*  27:    */   private final String sessionFactoryName;
/*  28:    */   private boolean startedServer;
/*  29:    */   private ArrayList<ObjectName> registeredMBeans;
/*  30:    */   
/*  31:    */   public JmxServiceImpl(Map configValues)
/*  32:    */   {
/*  33: 61 */     this.usePlatformServer = ConfigurationHelper.getBoolean("hibernate.jmx.usePlatformServer", configValues);
/*  34: 62 */     this.agentId = ((String)configValues.get("hibernate.jmx.agentId"));
/*  35: 63 */     this.defaultDomain = ((String)configValues.get("hibernate.jmx.defaultDomain"));
/*  36: 64 */     this.sessionFactoryName = ConfigurationHelper.getString("hibernate.jmx.sessionFactoryName", configValues, ConfigurationHelper.getString("hibernate.session_factory_name", configValues));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void stop()
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 79 */       if ((this.startedServer) || (this.registeredMBeans != null))
/*  44:    */       {
/*  45: 80 */         MBeanServer mBeanServer = findServer();
/*  46: 81 */         if (mBeanServer == null)
/*  47:    */         {
/*  48: 82 */           LOG.unableToLocateMBeanServer(); return;
/*  49:    */         }
/*  50: 87 */         if (this.registeredMBeans != null) {
/*  51: 88 */           for (ObjectName objectName : this.registeredMBeans) {
/*  52:    */             try
/*  53:    */             {
/*  54: 90 */               LOG.tracev("Unregistering registered MBean [ON={0}]", objectName);
/*  55: 91 */               mBeanServer.unregisterMBean(objectName);
/*  56:    */             }
/*  57:    */             catch (Exception e)
/*  58:    */             {
/*  59: 94 */               LOG.debugf("Unable to unregsiter registered MBean [ON=%s] : %s", objectName, e.toString());
/*  60:    */             }
/*  61:    */           }
/*  62:    */         }
/*  63:100 */         if (this.startedServer)
/*  64:    */         {
/*  65:101 */           LOG.trace("Attempting to release created MBeanServer");
/*  66:    */           try
/*  67:    */           {
/*  68:103 */             MBeanServerFactory.releaseMBeanServer(mBeanServer);
/*  69:    */           }
/*  70:    */           catch (Exception e)
/*  71:    */           {
/*  72:106 */             LOG.unableToReleaseCreatedMBeanServer(e.toString());
/*  73:    */           }
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:    */     finally
/*  78:    */     {
/*  79:112 */       this.startedServer = false;
/*  80:113 */       if (this.registeredMBeans != null)
/*  81:    */       {
/*  82:114 */         this.registeredMBeans.clear();
/*  83:115 */         this.registeredMBeans = null;
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void registerService(Manageable service, Class<? extends Service> serviceRole)
/*  89:    */   {
/*  90:126 */     String domain = service.getManagementDomain() == null ? "org.hibernate.core" : service.getManagementDomain();
/*  91:    */     
/*  92:    */ 
/*  93:129 */     String serviceType = service.getManagementServiceType() == null ? service.getClass().getName() : service.getManagementServiceType();
/*  94:    */     try
/*  95:    */     {
/*  96:133 */       ObjectName objectName = new ObjectName(String.format("%s:sessionFactory=%s,serviceRole=%s,serviceType=%s", new Object[] { domain, this.sessionFactoryName, serviceRole.getName(), serviceType }));
/*  97:    */       
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:142 */       registerMBean(objectName, service.getManagementBean());
/* 106:    */     }
/* 107:    */     catch (MalformedObjectNameException e)
/* 108:    */     {
/* 109:145 */       throw new HibernateException("Unable to generate service IbjectName", e);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void registerMBean(ObjectName objectName, Object mBean)
/* 114:    */   {
/* 115:151 */     MBeanServer mBeanServer = findServer();
/* 116:152 */     if (mBeanServer == null)
/* 117:    */     {
/* 118:153 */       if (this.startedServer) {
/* 119:154 */         throw new HibernateException("Could not locate previously started MBeanServer");
/* 120:    */       }
/* 121:156 */       mBeanServer = startMBeanServer();
/* 122:157 */       this.startedServer = true;
/* 123:    */     }
/* 124:    */     try
/* 125:    */     {
/* 126:161 */       mBeanServer.registerMBean(mBean, objectName);
/* 127:162 */       if (this.registeredMBeans == null) {
/* 128:163 */         this.registeredMBeans = new ArrayList();
/* 129:    */       }
/* 130:165 */       this.registeredMBeans.add(objectName);
/* 131:    */     }
/* 132:    */     catch (Exception e)
/* 133:    */     {
/* 134:168 */       throw new HibernateException("Unable to register MBean [ON=" + objectName + "]", e);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   private MBeanServer findServer()
/* 139:    */   {
/* 140:178 */     if (this.usePlatformServer) {
/* 141:180 */       return ManagementFactory.getPlatformMBeanServer();
/* 142:    */     }
/* 143:185 */     ArrayList<MBeanServer> mbeanServers = MBeanServerFactory.findMBeanServer(this.agentId);
/* 144:187 */     if (this.defaultDomain == null) {
/* 145:189 */       return (MBeanServer)mbeanServers.get(0);
/* 146:    */     }
/* 147:192 */     for (MBeanServer mbeanServer : mbeanServers) {
/* 148:195 */       if (this.defaultDomain.equals(mbeanServer.getDefaultDomain())) {
/* 149:196 */         return mbeanServer;
/* 150:    */       }
/* 151:    */     }
/* 152:200 */     return null;
/* 153:    */   }
/* 154:    */   
/* 155:    */   private MBeanServer startMBeanServer()
/* 156:    */   {
/* 157:    */     try
/* 158:    */     {
/* 159:205 */       return MBeanServerFactory.createMBeanServer(this.defaultDomain);
/* 160:    */     }
/* 161:    */     catch (Exception e)
/* 162:    */     {
/* 163:208 */       throw new HibernateException("Unable to start MBeanServer", e);
/* 164:    */     }
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jmx.internal.JmxServiceImpl
 * JD-Core Version:    0.7.0.1
 */
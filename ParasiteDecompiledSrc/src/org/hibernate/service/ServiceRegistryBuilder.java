/*   1:    */ package org.hibernate.service;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Properties;
/*  10:    */ import org.hibernate.cfg.Environment;
/*  11:    */ import org.hibernate.integrator.spi.Integrator;
/*  12:    */ import org.hibernate.integrator.spi.IntegratorService;
/*  13:    */ import org.hibernate.integrator.spi.ServiceContributingIntegrator;
/*  14:    */ import org.hibernate.internal.jaxb.Origin;
/*  15:    */ import org.hibernate.internal.jaxb.SourceType;
/*  16:    */ import org.hibernate.internal.jaxb.cfg.JaxbHibernateConfiguration;
/*  17:    */ import org.hibernate.internal.jaxb.cfg.JaxbHibernateConfiguration.JaxbSessionFactory;
/*  18:    */ import org.hibernate.internal.jaxb.cfg.JaxbHibernateConfiguration.JaxbSessionFactory.JaxbProperty;
/*  19:    */ import org.hibernate.internal.util.Value;
/*  20:    */ import org.hibernate.internal.util.Value.DeferredInitializer;
/*  21:    */ import org.hibernate.internal.util.config.ConfigurationException;
/*  22:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  23:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  24:    */ import org.hibernate.service.internal.BootstrapServiceRegistryImpl;
/*  25:    */ import org.hibernate.service.internal.JaxbProcessor;
/*  26:    */ import org.hibernate.service.internal.ProvidedService;
/*  27:    */ import org.hibernate.service.internal.StandardServiceRegistryImpl;
/*  28:    */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  29:    */ import org.jboss.logging.Logger;
/*  30:    */ 
/*  31:    */ public class ServiceRegistryBuilder
/*  32:    */ {
/*  33: 62 */   private static final Logger log = Logger.getLogger(ServiceRegistryBuilder.class);
/*  34:    */   public static final String DEFAULT_CFG_RESOURCE_NAME = "hibernate.cfg.xml";
/*  35:    */   private final Map settings;
/*  36: 67 */   private final List<BasicServiceInitiator> initiators = standardInitiatorList();
/*  37: 68 */   private final List<ProvidedService> providedServices = new ArrayList();
/*  38:    */   private final BootstrapServiceRegistry bootstrapServiceRegistry;
/*  39:    */   
/*  40:    */   public ServiceRegistryBuilder()
/*  41:    */   {
/*  42: 76 */     this(new BootstrapServiceRegistryImpl());
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ServiceRegistryBuilder(BootstrapServiceRegistry bootstrapServiceRegistry)
/*  46:    */   {
/*  47: 85 */     this.settings = Environment.getProperties();
/*  48: 86 */     this.bootstrapServiceRegistry = bootstrapServiceRegistry;
/*  49:    */   }
/*  50:    */   
/*  51:    */   private static List<BasicServiceInitiator> standardInitiatorList()
/*  52:    */   {
/*  53: 95 */     List<BasicServiceInitiator> initiators = new ArrayList();
/*  54: 96 */     initiators.addAll(StandardServiceInitiators.LIST);
/*  55: 97 */     return initiators;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ServiceRegistryBuilder loadProperties(String resourceName)
/*  59:    */   {
/*  60:113 */     InputStream stream = ((ClassLoaderService)this.bootstrapServiceRegistry.getService(ClassLoaderService.class)).locateResourceStream(resourceName);
/*  61:    */     try
/*  62:    */     {
/*  63:115 */       Properties properties = new Properties();
/*  64:116 */       properties.load(stream);
/*  65:117 */       this.settings.putAll(properties);
/*  66:    */       
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:134 */       return this;
/*  83:    */     }
/*  84:    */     catch (IOException e)
/*  85:    */     {
/*  86:120 */       throw new ConfigurationException("Unable to apply settings from properties file [" + resourceName + "]", e);
/*  87:    */     }
/*  88:    */     finally
/*  89:    */     {
/*  90:    */       try
/*  91:    */       {
/*  92:124 */         stream.close();
/*  93:    */       }
/*  94:    */       catch (IOException e)
/*  95:    */       {
/*  96:127 */         log.debug(String.format("Unable to close properties file [%s] stream", new Object[] { resourceName }), e);
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public ServiceRegistryBuilder configure()
/* 102:    */   {
/* 103:147 */     return configure("hibernate.cfg.xml");
/* 104:    */   }
/* 105:    */   
/* 106:    */   public ServiceRegistryBuilder configure(String resourceName)
/* 107:    */   {
/* 108:161 */     InputStream stream = ((ClassLoaderService)this.bootstrapServiceRegistry.getService(ClassLoaderService.class)).locateResourceStream(resourceName);
/* 109:162 */     JaxbHibernateConfiguration configurationElement = ((JaxbProcessor)this.jaxbProcessorHolder.getValue()).unmarshal(stream, new Origin(SourceType.RESOURCE, resourceName));
/* 110:166 */     for (JaxbHibernateConfiguration.JaxbSessionFactory.JaxbProperty xmlProperty : configurationElement.getSessionFactory().getProperty()) {
/* 111:167 */       this.settings.put(xmlProperty.getName(), xmlProperty.getValue());
/* 112:    */     }
/* 113:170 */     return this;
/* 114:    */   }
/* 115:    */   
/* 116:173 */   private Value<JaxbProcessor> jaxbProcessorHolder = new Value(new Value.DeferredInitializer()
/* 117:    */   {
/* 118:    */     public JaxbProcessor initialize()
/* 119:    */     {
/* 120:177 */       return new JaxbProcessor((ClassLoaderService)ServiceRegistryBuilder.this.bootstrapServiceRegistry.getService(ClassLoaderService.class));
/* 121:    */     }
/* 122:173 */   });
/* 123:    */   
/* 124:    */   public ServiceRegistryBuilder applySetting(String settingName, Object value)
/* 125:    */   {
/* 126:192 */     this.settings.put(settingName, value);
/* 127:193 */     return this;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public ServiceRegistryBuilder applySettings(Map settings)
/* 131:    */   {
/* 132:205 */     this.settings.putAll(settings);
/* 133:206 */     return this;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public ServiceRegistryBuilder addInitiator(BasicServiceInitiator initiator)
/* 137:    */   {
/* 138:218 */     this.initiators.add(initiator);
/* 139:219 */     return this;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public ServiceRegistryBuilder addService(Class serviceRole, Service service)
/* 143:    */   {
/* 144:232 */     this.providedServices.add(new ProvidedService(serviceRole, service));
/* 145:233 */     return this;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public ServiceRegistry buildServiceRegistry()
/* 149:    */   {
/* 150:242 */     Map<?, ?> settingsCopy = new HashMap();
/* 151:243 */     settingsCopy.putAll(this.settings);
/* 152:244 */     Environment.verifyProperties(settingsCopy);
/* 153:245 */     ConfigurationHelper.resolvePlaceHolders(settingsCopy);
/* 154:247 */     for (Integrator integrator : ((IntegratorService)this.bootstrapServiceRegistry.getService(IntegratorService.class)).getIntegrators()) {
/* 155:248 */       if (ServiceContributingIntegrator.class.isInstance(integrator)) {
/* 156:249 */         ((ServiceContributingIntegrator)ServiceContributingIntegrator.class.cast(integrator)).prepareServices(this);
/* 157:    */       }
/* 158:    */     }
/* 159:253 */     return new StandardServiceRegistryImpl(this.bootstrapServiceRegistry, this.initiators, this.providedServices, settingsCopy);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static void destroy(ServiceRegistry serviceRegistry)
/* 163:    */   {
/* 164:262 */     ((StandardServiceRegistryImpl)serviceRegistry).destroy();
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.ServiceRegistryBuilder
 * JD-Core Version:    0.7.0.1
 */
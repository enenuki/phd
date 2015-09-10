/*   1:    */ package org.hibernate.service.jta.platform.internal;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.internal.CoreMessageLogger;
/*   6:    */ import org.hibernate.internal.util.jndi.JndiHelper;
/*   7:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*   8:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*   9:    */ import org.hibernate.service.jta.platform.spi.JtaPlatformException;
/*  10:    */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  11:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  12:    */ import org.hibernate.transaction.TransactionManagerLookup;
/*  13:    */ import org.jboss.logging.Logger;
/*  14:    */ 
/*  15:    */ public class JtaPlatformInitiator
/*  16:    */   implements BasicServiceInitiator<JtaPlatform>
/*  17:    */ {
/*  18: 48 */   public static final JtaPlatformInitiator INSTANCE = new JtaPlatformInitiator();
/*  19: 50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JtaPlatformInitiator.class.getName());
/*  20:    */   
/*  21:    */   public Class<JtaPlatform> getServiceInitiated()
/*  22:    */   {
/*  23: 54 */     return JtaPlatform.class;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public JtaPlatform initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/*  27:    */   {
/*  28: 60 */     Object platform = getConfiguredPlatform(configurationValues, registry);
/*  29: 61 */     if (platform == null) {
/*  30: 62 */       return new NoJtaPlatform();
/*  31:    */     }
/*  32: 65 */     if (JtaPlatform.class.isInstance(platform)) {
/*  33: 66 */       return (JtaPlatform)platform;
/*  34:    */     }
/*  35:    */     Class<JtaPlatform> jtaPlatformImplClass;
/*  36:    */     Class<JtaPlatform> jtaPlatformImplClass;
/*  37: 71 */     if (Class.class.isInstance(platform))
/*  38:    */     {
/*  39: 72 */       jtaPlatformImplClass = (Class)platform;
/*  40:    */     }
/*  41:    */     else
/*  42:    */     {
/*  43: 75 */       String platformImplName = platform.toString();
/*  44: 76 */       ClassLoaderService classLoaderService = (ClassLoaderService)registry.getService(ClassLoaderService.class);
/*  45:    */       try
/*  46:    */       {
/*  47: 78 */         jtaPlatformImplClass = classLoaderService.classForName(platformImplName);
/*  48:    */       }
/*  49:    */       catch (Exception e)
/*  50:    */       {
/*  51: 81 */         throw new HibernateException("Unable to locate specified JtaPlatform class [" + platformImplName + "]", e);
/*  52:    */       }
/*  53:    */     }
/*  54:    */     try
/*  55:    */     {
/*  56: 86 */       return (JtaPlatform)jtaPlatformImplClass.newInstance();
/*  57:    */     }
/*  58:    */     catch (Exception e)
/*  59:    */     {
/*  60: 89 */       throw new HibernateException("Unable to create specified JtaPlatform class [" + jtaPlatformImplClass.getName() + "]", e);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   private Object getConfiguredPlatform(Map configVales, ServiceRegistryImplementor registry)
/*  65:    */   {
/*  66: 94 */     Object platform = configVales.get("hibernate.transaction.jta.platform");
/*  67: 95 */     if (platform == null)
/*  68:    */     {
/*  69: 96 */       String transactionManagerLookupImplName = (String)configVales.get("hibernate.transaction.manager_lookup_class");
/*  70: 97 */       if (transactionManagerLookupImplName != null)
/*  71:    */       {
/*  72: 98 */         LOG.deprecatedTransactionManagerStrategy(TransactionManagerLookup.class.getName(), "hibernate.transaction.manager_lookup_class", JtaPlatform.class.getName(), "hibernate.transaction.jta.platform");
/*  73:    */         
/*  74:    */ 
/*  75:    */ 
/*  76:102 */         platform = mapLegacyClasses(transactionManagerLookupImplName, configVales, registry);
/*  77:103 */         LOG.debugf("Mapped %s -> %s", transactionManagerLookupImplName, platform);
/*  78:    */       }
/*  79:    */     }
/*  80:106 */     return platform;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private JtaPlatform mapLegacyClasses(String tmlImplName, Map configVales, ServiceRegistryImplementor registry)
/*  84:    */   {
/*  85:110 */     if (tmlImplName == null) {
/*  86:111 */       return null;
/*  87:    */     }
/*  88:114 */     LOG.legacyTransactionManagerStrategy(JtaPlatform.class.getName(), "hibernate.transaction.jta.platform");
/*  89:116 */     if ("org.hibernate.transaction.BESTransactionManagerLookup".equals(tmlImplName)) {
/*  90:117 */       return new BorlandEnterpriseServerJtaPlatform();
/*  91:    */     }
/*  92:120 */     if ("org.hibernate.transaction.BTMTransactionManagerLookup".equals(tmlImplName)) {
/*  93:121 */       return new BitronixJtaPlatform();
/*  94:    */     }
/*  95:124 */     if ("org.hibernate.transaction.JBossTransactionManagerLookup".equals(tmlImplName)) {
/*  96:125 */       return new JBossAppServerJtaPlatform();
/*  97:    */     }
/*  98:128 */     if ("org.hibernate.transaction.JBossTSStandaloneTransactionManagerLookup".equals(tmlImplName)) {
/*  99:129 */       return new JBossStandAloneJtaPlatform();
/* 100:    */     }
/* 101:132 */     if ("org.hibernate.transaction.JOnASTransactionManagerLookup".equals(tmlImplName)) {
/* 102:133 */       return new JOnASJtaPlatform();
/* 103:    */     }
/* 104:136 */     if ("org.hibernate.transaction.JOTMTransactionManagerLookup".equals(tmlImplName)) {
/* 105:137 */       return new JOTMJtaPlatform();
/* 106:    */     }
/* 107:140 */     if ("org.hibernate.transaction.JRun4TransactionManagerLookup".equals(tmlImplName)) {
/* 108:141 */       return new JRun4JtaPlatform();
/* 109:    */     }
/* 110:144 */     if ("org.hibernate.transaction.OC4JTransactionManagerLookup".equals(tmlImplName)) {
/* 111:145 */       return new OC4JJtaPlatform();
/* 112:    */     }
/* 113:148 */     if ("org.hibernate.transaction.OrionTransactionManagerLookup".equals(tmlImplName)) {
/* 114:149 */       return new OrionJtaPlatform();
/* 115:    */     }
/* 116:152 */     if ("org.hibernate.transaction.ResinTransactionManagerLookup".equals(tmlImplName)) {
/* 117:153 */       return new ResinJtaPlatform();
/* 118:    */     }
/* 119:156 */     if ("org.hibernate.transaction.SunONETransactionManagerLookup".equals(tmlImplName)) {
/* 120:157 */       return new SunOneJtaPlatform();
/* 121:    */     }
/* 122:160 */     if ("org.hibernate.transaction.WeblogicTransactionManagerLookup".equals(tmlImplName)) {
/* 123:161 */       return new WeblogicJtaPlatform();
/* 124:    */     }
/* 125:164 */     if ("org.hibernate.transaction.WebSphereTransactionManagerLookup".equals(tmlImplName)) {
/* 126:165 */       return new WebSphereJtaPlatform();
/* 127:    */     }
/* 128:168 */     if ("org.hibernate.transaction.WebSphereExtendedJTATransactionLookup".equals(tmlImplName)) {
/* 129:169 */       return new WebSphereExtendedJtaPlatform();
/* 130:    */     }
/* 131:    */     try
/* 132:    */     {
/* 133:173 */       TransactionManagerLookup lookup = (TransactionManagerLookup)((ClassLoaderService)registry.getService(ClassLoaderService.class)).classForName(tmlImplName).newInstance();
/* 134:    */       
/* 135:    */ 
/* 136:176 */       return new TransactionManagerLookupBridge(lookup, JndiHelper.extractJndiProperties(configVales));
/* 137:    */     }
/* 138:    */     catch (Exception e)
/* 139:    */     {
/* 140:179 */       throw new JtaPlatformException("Unable to build " + TransactionManagerLookupBridge.class.getName() + " from specified " + TransactionManagerLookup.class.getName() + " implementation: " + tmlImplName);
/* 141:    */     }
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jta.platform.internal.JtaPlatformInitiator
 * JD-Core Version:    0.7.0.1
 */
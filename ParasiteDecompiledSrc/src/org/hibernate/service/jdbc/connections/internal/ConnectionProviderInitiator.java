/*   1:    */ package org.hibernate.service.jdbc.connections.internal;
/*   2:    */ 
/*   3:    */ import java.beans.BeanInfo;
/*   4:    */ import java.beans.PropertyDescriptor;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Properties;
/*  11:    */ import java.util.Set;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.MultiTenancyStrategy;
/*  14:    */ import org.hibernate.internal.CoreMessageLogger;
/*  15:    */ import org.hibernate.internal.util.beans.BeanInfoHelper;
/*  16:    */ import org.hibernate.internal.util.beans.BeanInfoHelper.BeanInfoDelegate;
/*  17:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  18:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  19:    */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  20:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  21:    */ import org.jboss.logging.Logger;
/*  22:    */ 
/*  23:    */ public class ConnectionProviderInitiator
/*  24:    */   implements BasicServiceInitiator<ConnectionProvider>
/*  25:    */ {
/*  26: 55 */   public static final ConnectionProviderInitiator INSTANCE = new ConnectionProviderInitiator();
/*  27: 57 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ConnectionProviderInitiator.class.getName());
/*  28:    */   public static final String C3P0_PROVIDER_CLASS_NAME = "org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider";
/*  29:    */   public static final String PROXOOL_PROVIDER_CLASS_NAME = "org.hibernate.service.jdbc.connections.internal.ProxoolConnectionProvider";
/*  30:    */   public static final String INJECTION_DATA = "hibernate.connection_provider.injection_data";
/*  31: 72 */   private static final Map<String, String> LEGACY_CONNECTION_PROVIDER_MAPPING = new HashMap(5);
/*  32:    */   private static final Set<String> SPECIAL_PROPERTIES;
/*  33:    */   
/*  34:    */   public Class<ConnectionProvider> getServiceInitiated()
/*  35:    */   {
/*  36: 98 */     return ConnectionProvider.class;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ConnectionProvider initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/*  40:    */   {
/*  41:103 */     if (MultiTenancyStrategy.determineMultiTenancyStrategy(configurationValues) != MultiTenancyStrategy.NONE) {}
/*  42:107 */     ClassLoaderService classLoaderService = (ClassLoaderService)registry.getService(ClassLoaderService.class);
/*  43:    */     
/*  44:109 */     ConnectionProvider connectionProvider = null;
/*  45:110 */     String providerClassName = getConfiguredConnectionProviderName(configurationValues);
/*  46:111 */     if (providerClassName != null) {
/*  47:112 */       connectionProvider = instantiateExplicitConnectionProvider(providerClassName, classLoaderService);
/*  48:114 */     } else if (configurationValues.get("hibernate.connection.datasource") != null) {
/*  49:115 */       connectionProvider = new DatasourceConnectionProviderImpl();
/*  50:    */     }
/*  51:118 */     if ((connectionProvider == null) && 
/*  52:119 */       (c3p0ConfigDefined(configurationValues)) && (c3p0ProviderPresent(classLoaderService))) {
/*  53:120 */       connectionProvider = instantiateExplicitConnectionProvider("org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider", classLoaderService);
/*  54:    */     }
/*  55:126 */     if ((connectionProvider == null) && 
/*  56:127 */       (proxoolConfigDefined(configurationValues)) && (proxoolProviderPresent(classLoaderService))) {
/*  57:128 */       connectionProvider = instantiateExplicitConnectionProvider("org.hibernate.service.jdbc.connections.internal.ProxoolConnectionProvider", classLoaderService);
/*  58:    */     }
/*  59:134 */     if ((connectionProvider == null) && 
/*  60:135 */       (configurationValues.get("hibernate.connection.url") != null)) {
/*  61:136 */       connectionProvider = new DriverManagerConnectionProviderImpl();
/*  62:    */     }
/*  63:140 */     if (connectionProvider == null)
/*  64:    */     {
/*  65:141 */       LOG.noAppropriateConnectionProvider();
/*  66:142 */       connectionProvider = new UserSuppliedConnectionProviderImpl();
/*  67:    */     }
/*  68:146 */     final Map injectionData = (Map)configurationValues.get("hibernate.connection_provider.injection_data");
/*  69:147 */     if ((injectionData != null) && (injectionData.size() > 0))
/*  70:    */     {
/*  71:148 */       final ConnectionProvider theConnectionProvider = connectionProvider;
/*  72:149 */       new BeanInfoHelper(connectionProvider.getClass()).applyToBeanInfo(connectionProvider, new BeanInfoHelper.BeanInfoDelegate()
/*  73:    */       {
/*  74:    */         public void processBeanInfo(BeanInfo beanInfo)
/*  75:    */           throws Exception
/*  76:    */         {
/*  77:153 */           PropertyDescriptor[] descritors = beanInfo.getPropertyDescriptors();
/*  78:154 */           int i = 0;
/*  79:154 */           for (int size = descritors.length; i < size; i++)
/*  80:    */           {
/*  81:155 */             String propertyName = descritors[i].getName();
/*  82:156 */             if (injectionData.containsKey(propertyName))
/*  83:    */             {
/*  84:157 */               Method method = descritors[i].getWriteMethod();
/*  85:158 */               method.invoke(theConnectionProvider, new Object[] { injectionData.get(propertyName) });
/*  86:    */             }
/*  87:    */           }
/*  88:    */         }
/*  89:    */       });
/*  90:    */     }
/*  91:169 */     return connectionProvider;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private String getConfiguredConnectionProviderName(Map configurationValues)
/*  95:    */   {
/*  96:173 */     String providerClassName = (String)configurationValues.get("hibernate.connection.provider_class");
/*  97:174 */     if (LEGACY_CONNECTION_PROVIDER_MAPPING.containsKey(providerClassName))
/*  98:    */     {
/*  99:175 */       String actualProviderClassName = (String)LEGACY_CONNECTION_PROVIDER_MAPPING.get(providerClassName);
/* 100:176 */       LOG.providerClassDeprecated(providerClassName, actualProviderClassName);
/* 101:177 */       providerClassName = actualProviderClassName;
/* 102:    */     }
/* 103:179 */     return providerClassName;
/* 104:    */   }
/* 105:    */   
/* 106:    */   private ConnectionProvider instantiateExplicitConnectionProvider(String providerClassName, ClassLoaderService classLoaderService)
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:186 */       LOG.instantiatingExplicitConnectionProvider(providerClassName);
/* 111:187 */       return (ConnectionProvider)classLoaderService.classForName(providerClassName).newInstance();
/* 112:    */     }
/* 113:    */     catch (Exception e)
/* 114:    */     {
/* 115:190 */       throw new HibernateException("Could not instantiate connection provider [" + providerClassName + "]", e);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private boolean c3p0ProviderPresent(ClassLoaderService classLoaderService)
/* 120:    */   {
/* 121:    */     try
/* 122:    */     {
/* 123:196 */       classLoaderService.classForName("org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");
/* 124:    */     }
/* 125:    */     catch (Exception e)
/* 126:    */     {
/* 127:199 */       LOG.c3p0ProviderClassNotFound("org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");
/* 128:200 */       return false;
/* 129:    */     }
/* 130:202 */     return true;
/* 131:    */   }
/* 132:    */   
/* 133:    */   private static boolean c3p0ConfigDefined(Map configValues)
/* 134:    */   {
/* 135:206 */     for (Object key : configValues.keySet()) {
/* 136:207 */       if ((String.class.isInstance(key)) && (((String)key).startsWith("hibernate.c3p0"))) {
/* 137:209 */         return true;
/* 138:    */       }
/* 139:    */     }
/* 140:212 */     return false;
/* 141:    */   }
/* 142:    */   
/* 143:    */   private boolean proxoolProviderPresent(ClassLoaderService classLoaderService)
/* 144:    */   {
/* 145:    */     try
/* 146:    */     {
/* 147:217 */       classLoaderService.classForName("org.hibernate.service.jdbc.connections.internal.ProxoolConnectionProvider");
/* 148:    */     }
/* 149:    */     catch (Exception e)
/* 150:    */     {
/* 151:220 */       LOG.proxoolProviderClassNotFound("org.hibernate.service.jdbc.connections.internal.ProxoolConnectionProvider");
/* 152:221 */       return false;
/* 153:    */     }
/* 154:223 */     return true;
/* 155:    */   }
/* 156:    */   
/* 157:    */   private static boolean proxoolConfigDefined(Map configValues)
/* 158:    */   {
/* 159:227 */     for (Object key : configValues.keySet()) {
/* 160:228 */       if ((String.class.isInstance(key)) && (((String)key).startsWith("hibernate.proxool"))) {
/* 161:230 */         return true;
/* 162:    */       }
/* 163:    */     }
/* 164:233 */     return false;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static Properties getConnectionProperties(Map<?, ?> properties)
/* 168:    */   {
/* 169:247 */     Properties result = new Properties();
/* 170:248 */     for (Map.Entry entry : properties.entrySet()) {
/* 171:249 */       if ((String.class.isInstance(entry.getKey())) && (String.class.isInstance(entry.getValue())))
/* 172:    */       {
/* 173:252 */         String key = (String)entry.getKey();
/* 174:253 */         String value = (String)entry.getValue();
/* 175:254 */         if (key.startsWith("hibernate.connection")) {
/* 176:255 */           if (SPECIAL_PROPERTIES.contains(key))
/* 177:    */           {
/* 178:256 */             if ("hibernate.connection.username".equals(key)) {
/* 179:257 */               result.setProperty("user", value);
/* 180:    */             }
/* 181:    */           }
/* 182:    */           else {
/* 183:261 */             result.setProperty(key.substring("hibernate.connection".length() + 1), value);
/* 184:    */           }
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:268 */     return result;
/* 189:    */   }
/* 190:    */   
/* 191:    */   static
/* 192:    */   {
/* 193: 74 */     LEGACY_CONNECTION_PROVIDER_MAPPING.put("org.hibernate.connection.DatasourceConnectionProvider", DatasourceConnectionProviderImpl.class.getName());
/* 194:    */     
/* 195:    */ 
/* 196:    */ 
/* 197: 78 */     LEGACY_CONNECTION_PROVIDER_MAPPING.put("org.hibernate.connection.DriverManagerConnectionProvider", DriverManagerConnectionProviderImpl.class.getName());
/* 198:    */     
/* 199:    */ 
/* 200:    */ 
/* 201: 82 */     LEGACY_CONNECTION_PROVIDER_MAPPING.put("org.hibernate.connection.UserSuppliedConnectionProvider", UserSuppliedConnectionProviderImpl.class.getName());
/* 202:    */     
/* 203:    */ 
/* 204:    */ 
/* 205: 86 */     LEGACY_CONNECTION_PROVIDER_MAPPING.put("org.hibernate.connection.C3P0ConnectionProvider", "org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");
/* 206:    */     
/* 207:    */ 
/* 208:    */ 
/* 209: 90 */     LEGACY_CONNECTION_PROVIDER_MAPPING.put("org.hibernate.connection.ProxoolConnectionProvider", "org.hibernate.service.jdbc.connections.internal.ProxoolConnectionProvider");
/* 210:    */     
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:    */ 
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:    */ 
/* 393:274 */     SPECIAL_PROPERTIES = new HashSet();
/* 394:275 */     SPECIAL_PROPERTIES.add("hibernate.connection.datasource");
/* 395:276 */     SPECIAL_PROPERTIES.add("hibernate.connection.url");
/* 396:277 */     SPECIAL_PROPERTIES.add("hibernate.connection.provider_class");
/* 397:278 */     SPECIAL_PROPERTIES.add("hibernate.connection.pool_size");
/* 398:279 */     SPECIAL_PROPERTIES.add("hibernate.connection.isolation");
/* 399:280 */     SPECIAL_PROPERTIES.add("hibernate.connection.driver_class");
/* 400:281 */     SPECIAL_PROPERTIES.add("hibernate.connection.username");
/* 401:    */   }
/* 402:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.connections.internal.ConnectionProviderInitiator
 * JD-Core Version:    0.7.0.1
 */
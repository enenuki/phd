/*   1:    */ package org.hibernate.cfg.beanvalidation;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationTargetException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Properties;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.cfg.Configuration;
/*  12:    */ import org.hibernate.cfg.Mappings;
/*  13:    */ import org.hibernate.cfg.Settings;
/*  14:    */ import org.hibernate.dialect.Dialect;
/*  15:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  16:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  17:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  18:    */ import org.hibernate.integrator.spi.Integrator;
/*  19:    */ import org.hibernate.internal.CoreMessageLogger;
/*  20:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  21:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  22:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  23:    */ import org.hibernate.service.spi.SessionFactoryServiceRegistry;
/*  24:    */ import org.jboss.logging.Logger;
/*  25:    */ 
/*  26:    */ public class BeanValidationIntegrator
/*  27:    */   implements Integrator
/*  28:    */ {
/*  29: 53 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BeanValidationIntegrator.class.getName());
/*  30:    */   public static final String APPLY_CONSTRAINTS = "hibernate.validator.apply_to_ddl";
/*  31:    */   public static final String BV_CHECK_CLASS = "javax.validation.Validation";
/*  32:    */   public static final String MODE_PROPERTY = "javax.persistence.validation.mode";
/*  33:    */   private static final String ACTIVATOR_CLASS = "org.hibernate.cfg.beanvalidation.TypeSafeActivator";
/*  34:    */   private static final String DDL_METHOD = "applyDDL";
/*  35:    */   private static final String ACTIVATE_METHOD = "activateBeanValidation";
/*  36:    */   private static final String VALIDATE_METHOD = "validateFactory";
/*  37:    */   
/*  38:    */   public static void validateFactory(Object object)
/*  39:    */   {
/*  40:    */     try
/*  41:    */     {
/*  42: 68 */       Class activatorClass = BeanValidationIntegrator.class.getClassLoader().loadClass("org.hibernate.cfg.beanvalidation.TypeSafeActivator");
/*  43:    */       try
/*  44:    */       {
/*  45: 70 */         Method validateMethod = activatorClass.getMethod("validateFactory", new Class[] { Object.class });
/*  46: 71 */         if (!validateMethod.isAccessible()) {
/*  47: 72 */           validateMethod.setAccessible(true);
/*  48:    */         }
/*  49:    */         try
/*  50:    */         {
/*  51: 75 */           validateMethod.invoke(null, new Object[] { object });
/*  52:    */         }
/*  53:    */         catch (InvocationTargetException e)
/*  54:    */         {
/*  55: 78 */           if ((e.getTargetException() instanceof HibernateException)) {
/*  56: 79 */             throw ((HibernateException)e.getTargetException());
/*  57:    */           }
/*  58: 81 */           throw new HibernateException("Unable to check validity of passed ValidatorFactory", e);
/*  59:    */         }
/*  60:    */         catch (IllegalAccessException e)
/*  61:    */         {
/*  62: 84 */           throw new HibernateException("Unable to check validity of passed ValidatorFactory", e);
/*  63:    */         }
/*  64:    */       }
/*  65:    */       catch (HibernateException e)
/*  66:    */       {
/*  67: 88 */         throw e;
/*  68:    */       }
/*  69:    */       catch (Exception e)
/*  70:    */       {
/*  71: 91 */         throw new HibernateException("Could not locate method needed for ValidatorFactory validation", e);
/*  72:    */       }
/*  73:    */     }
/*  74:    */     catch (HibernateException e)
/*  75:    */     {
/*  76: 95 */       throw e;
/*  77:    */     }
/*  78:    */     catch (Exception e)
/*  79:    */     {
/*  80: 98 */       throw new HibernateException("Could not locate TypeSafeActivator class", e);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry)
/*  85:    */   {
/*  86:108 */     Set<ValidationMode> modes = ValidationMode.getModes(configuration.getProperties().get("javax.persistence.validation.mode"));
/*  87:    */     
/*  88:110 */     ClassLoaderService classLoaderService = (ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class);
/*  89:111 */     Dialect dialect = ((JdbcServices)serviceRegistry.getService(JdbcServices.class)).getDialect();
/*  90:    */     boolean isBeanValidationAvailable;
/*  91:    */     try
/*  92:    */     {
/*  93:115 */       classLoaderService.classForName("javax.validation.Validation");
/*  94:116 */       isBeanValidationAvailable = true;
/*  95:    */     }
/*  96:    */     catch (Exception e)
/*  97:    */     {
/*  98:119 */       isBeanValidationAvailable = false;
/*  99:    */     }
/* 100:123 */     Class typeSafeActivatorClass = loadTypeSafeActivatorClass(serviceRegistry);
/* 101:    */     
/* 102:    */ 
/* 103:126 */     applyRelationalConstraints(modes, isBeanValidationAvailable, typeSafeActivatorClass, configuration, dialect);
/* 104:    */     
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:134 */     applyHibernateListeners(modes, isBeanValidationAvailable, typeSafeActivatorClass, configuration, sessionFactory, serviceRegistry);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {}
/* 115:    */   
/* 116:    */   private Class loadTypeSafeActivatorClass(SessionFactoryServiceRegistry serviceRegistry)
/* 117:    */   {
/* 118:    */     try
/* 119:    */     {
/* 120:173 */       return ((ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class)).classForName("org.hibernate.cfg.beanvalidation.TypeSafeActivator");
/* 121:    */     }
/* 122:    */     catch (Exception e) {}
/* 123:176 */     return null;
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void applyRelationalConstraints(Set<ValidationMode> modes, boolean beanValidationAvailable, Class typeSafeActivatorClass, Configuration configuration, Dialect dialect)
/* 127:    */   {
/* 128:186 */     if (!ConfigurationHelper.getBoolean("hibernate.validator.apply_to_ddl", configuration.getProperties(), true))
/* 129:    */     {
/* 130:187 */       LOG.debug("Skipping application of relational constraints from legacy Hibernate Validator");
/* 131:188 */       return;
/* 132:    */     }
/* 133:191 */     if ((!modes.contains(ValidationMode.DDL)) && (!modes.contains(ValidationMode.AUTO))) {
/* 134:192 */       return;
/* 135:    */     }
/* 136:195 */     if (!beanValidationAvailable)
/* 137:    */     {
/* 138:196 */       if (modes.contains(ValidationMode.DDL)) {
/* 139:197 */         throw new HibernateException("Bean Validation not available in the class path but required in javax.persistence.validation.mode");
/* 140:    */       }
/* 141:199 */       if (modes.contains(ValidationMode.AUTO)) {
/* 142:201 */         return;
/* 143:    */       }
/* 144:    */     }
/* 145:    */     try
/* 146:    */     {
/* 147:206 */       Method applyDDLMethod = typeSafeActivatorClass.getMethod("applyDDL", new Class[] { Collection.class, Properties.class, Dialect.class });
/* 148:    */       try
/* 149:    */       {
/* 150:208 */         applyDDLMethod.invoke(null, new Object[] { configuration.createMappings().getClasses().values(), configuration.getProperties(), dialect });
/* 151:    */       }
/* 152:    */       catch (HibernateException e)
/* 153:    */       {
/* 154:216 */         throw e;
/* 155:    */       }
/* 156:    */       catch (Exception e)
/* 157:    */       {
/* 158:219 */         throw new HibernateException("Error applying BeanValidation relational constraints", e);
/* 159:    */       }
/* 160:    */     }
/* 161:    */     catch (HibernateException e)
/* 162:    */     {
/* 163:223 */       throw e;
/* 164:    */     }
/* 165:    */     catch (Exception e)
/* 166:    */     {
/* 167:226 */       throw new HibernateException("Unable to locate TypeSafeActivator#applyDDL method", e);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   private void applyHibernateListeners(Set<ValidationMode> modes, boolean beanValidationAvailable, Class typeSafeActivatorClass, Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry)
/* 172:    */   {
/* 173:271 */     if (configuration.getProperty("hibernate.check_nullability") == null) {
/* 174:272 */       sessionFactory.getSettings().setCheckNullability(false);
/* 175:    */     }
/* 176:275 */     if ((!modes.contains(ValidationMode.CALLBACK)) && (!modes.contains(ValidationMode.AUTO))) {
/* 177:276 */       return;
/* 178:    */     }
/* 179:279 */     if (!beanValidationAvailable)
/* 180:    */     {
/* 181:280 */       if (modes.contains(ValidationMode.CALLBACK)) {
/* 182:281 */         throw new HibernateException("Bean Validation not available in the class path but required in javax.persistence.validation.mode");
/* 183:    */       }
/* 184:283 */       if (modes.contains(ValidationMode.AUTO)) {
/* 185:285 */         return;
/* 186:    */       }
/* 187:    */     }
/* 188:    */     try
/* 189:    */     {
/* 190:290 */       Method activateMethod = typeSafeActivatorClass.getMethod("activateBeanValidation", new Class[] { EventListenerRegistry.class, Configuration.class });
/* 191:    */       try
/* 192:    */       {
/* 193:292 */         activateMethod.invoke(null, new Object[] { serviceRegistry.getService(EventListenerRegistry.class), configuration });
/* 194:    */       }
/* 195:    */       catch (HibernateException e)
/* 196:    */       {
/* 197:299 */         throw e;
/* 198:    */       }
/* 199:    */       catch (Exception e)
/* 200:    */       {
/* 201:302 */         throw new HibernateException("Error applying BeanValidation relational constraints", e);
/* 202:    */       }
/* 203:    */     }
/* 204:    */     catch (HibernateException e)
/* 205:    */     {
/* 206:306 */       throw e;
/* 207:    */     }
/* 208:    */     catch (Exception e)
/* 209:    */     {
/* 210:309 */       throw new HibernateException("Unable to locate TypeSafeActivator#applyDDL method", e);
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {}
/* 215:    */   
/* 216:    */   private static enum ValidationMode
/* 217:    */   {
/* 218:350 */     AUTO,  CALLBACK,  NONE,  DDL;
/* 219:    */     
/* 220:    */     private ValidationMode() {}
/* 221:    */     
/* 222:    */     public static Set<ValidationMode> getModes(Object modeProperty)
/* 223:    */     {
/* 224:356 */       Set<ValidationMode> modes = new HashSet(3);
/* 225:357 */       if (modeProperty == null)
/* 226:    */       {
/* 227:358 */         modes.add(AUTO);
/* 228:    */       }
/* 229:    */       else
/* 230:    */       {
/* 231:361 */         String[] modesInString = modeProperty.toString().split(",");
/* 232:362 */         for (String modeInString : modesInString) {
/* 233:363 */           modes.add(getMode(modeInString));
/* 234:    */         }
/* 235:    */       }
/* 236:366 */       if ((modes.size() > 1) && ((modes.contains(AUTO)) || (modes.contains(NONE))))
/* 237:    */       {
/* 238:367 */         StringBuilder message = new StringBuilder("Incompatible validation modes mixed: ");
/* 239:368 */         for (ValidationMode mode : modes) {
/* 240:369 */           message.append(mode).append(", ");
/* 241:    */         }
/* 242:371 */         throw new HibernateException(message.substring(0, message.length() - 2));
/* 243:    */       }
/* 244:373 */       return modes;
/* 245:    */     }
/* 246:    */     
/* 247:    */     private static ValidationMode getMode(String modeProperty)
/* 248:    */     {
/* 249:377 */       if ((modeProperty == null) || (modeProperty.length() == 0)) {
/* 250:378 */         return AUTO;
/* 251:    */       }
/* 252:    */       try
/* 253:    */       {
/* 254:382 */         return valueOf(modeProperty.trim().toUpperCase());
/* 255:    */       }
/* 256:    */       catch (IllegalArgumentException e)
/* 257:    */       {
/* 258:385 */         throw new HibernateException("Unknown validation mode in javax.persistence.validation.mode: " + modeProperty);
/* 259:    */       }
/* 260:    */     }
/* 261:    */   }
/* 262:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.beanvalidation.BeanValidationIntegrator
 * JD-Core Version:    0.7.0.1
 */
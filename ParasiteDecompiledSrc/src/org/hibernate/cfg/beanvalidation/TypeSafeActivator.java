/*   1:    */ package org.hibernate.cfg.beanvalidation;
/*   2:    */ 
/*   3:    */ import java.lang.annotation.Annotation;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Properties;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.StringTokenizer;
/*  13:    */ import javax.validation.Validation;
/*  14:    */ import javax.validation.Validator;
/*  15:    */ import javax.validation.ValidatorFactory;
/*  16:    */ import javax.validation.constraints.Digits;
/*  17:    */ import javax.validation.constraints.Max;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ import javax.validation.metadata.BeanDescriptor;
/*  22:    */ import javax.validation.metadata.ConstraintDescriptor;
/*  23:    */ import javax.validation.metadata.PropertyDescriptor;
/*  24:    */ import org.hibernate.AssertionFailure;
/*  25:    */ import org.hibernate.HibernateException;
/*  26:    */ import org.hibernate.MappingException;
/*  27:    */ import org.hibernate.cfg.Configuration;
/*  28:    */ import org.hibernate.dialect.Dialect;
/*  29:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  30:    */ import org.hibernate.event.spi.EventType;
/*  31:    */ import org.hibernate.event.spi.PreDeleteEventListener;
/*  32:    */ import org.hibernate.event.spi.PreInsertEventListener;
/*  33:    */ import org.hibernate.event.spi.PreUpdateEventListener;
/*  34:    */ import org.hibernate.internal.CoreMessageLogger;
/*  35:    */ import org.hibernate.internal.util.ReflectHelper;
/*  36:    */ import org.hibernate.internal.util.StringHelper;
/*  37:    */ import org.hibernate.mapping.Column;
/*  38:    */ import org.hibernate.mapping.Component;
/*  39:    */ import org.hibernate.mapping.PersistentClass;
/*  40:    */ import org.hibernate.mapping.Property;
/*  41:    */ import org.hibernate.mapping.SingleTableSubclass;
/*  42:    */ import org.jboss.logging.Logger;
/*  43:    */ 
/*  44:    */ class TypeSafeActivator
/*  45:    */ {
/*  46: 70 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TypeSafeActivator.class.getName());
/*  47:    */   private static final String FACTORY_PROPERTY = "javax.persistence.validation.factory";
/*  48:    */   
/*  49:    */   public static void validateFactory(Object object)
/*  50:    */   {
/*  51: 76 */     if (!ValidatorFactory.class.isInstance(object)) {
/*  52: 77 */       throw new HibernateException("Given object was not an instance of " + ValidatorFactory.class.getName() + "[" + object.getClass().getName() + "]");
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static void activateBeanValidation(EventListenerRegistry listenerRegistry, Configuration configuration)
/*  57:    */   {
/*  58: 86 */     Properties properties = configuration.getProperties();
/*  59: 87 */     ValidatorFactory factory = getValidatorFactory(properties);
/*  60: 88 */     BeanValidationEventListener listener = new BeanValidationEventListener(factory, properties);
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64: 92 */     listenerRegistry.addDuplicationStrategy(DuplicationStrategyImpl.INSTANCE);
/*  65:    */     
/*  66: 94 */     listenerRegistry.appendListeners(EventType.PRE_INSERT, new PreInsertEventListener[] { listener });
/*  67: 95 */     listenerRegistry.appendListeners(EventType.PRE_UPDATE, new PreUpdateEventListener[] { listener });
/*  68: 96 */     listenerRegistry.appendListeners(EventType.PRE_DELETE, new PreDeleteEventListener[] { listener });
/*  69:    */     
/*  70: 98 */     listener.initialize(configuration);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static void applyDDL(Collection<PersistentClass> persistentClasses, Properties properties, Dialect dialect)
/*  74:    */   {
/*  75:119 */     ValidatorFactory factory = getValidatorFactory(properties);
/*  76:120 */     Class<?>[] groupsArray = new GroupsPerOperation(properties).get(GroupsPerOperation.Operation.DDL);
/*  77:121 */     Set<Class<?>> groups = new HashSet(Arrays.asList(groupsArray));
/*  78:123 */     for (PersistentClass persistentClass : persistentClasses)
/*  79:    */     {
/*  80:124 */       String className = persistentClass.getClassName();
/*  81:126 */       if ((className != null) && (className.length() != 0))
/*  82:    */       {
/*  83:    */         Class<?> clazz;
/*  84:    */         try
/*  85:    */         {
/*  86:131 */           clazz = ReflectHelper.classForName(className, TypeSafeActivator.class);
/*  87:    */         }
/*  88:    */         catch (ClassNotFoundException e)
/*  89:    */         {
/*  90:134 */           throw new AssertionFailure("Entity class not found", e);
/*  91:    */         }
/*  92:    */         try
/*  93:    */         {
/*  94:138 */           applyDDL("", persistentClass, clazz, factory, groups, true, dialect);
/*  95:    */         }
/*  96:    */         catch (Exception e)
/*  97:    */         {
/*  98:141 */           LOG.unableToApplyConstraints(className, e);
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private static void applyDDL(String prefix, PersistentClass persistentClass, Class<?> clazz, ValidatorFactory factory, Set<Class<?>> groups, boolean activateNotNull, Dialect dialect)
/* 105:    */   {
/* 106:172 */     BeanDescriptor descriptor = factory.getValidator().getConstraintsForClass(clazz);
/* 107:175 */     for (PropertyDescriptor propertyDesc : descriptor.getConstrainedProperties())
/* 108:    */     {
/* 109:176 */       Property property = findPropertyByName(persistentClass, prefix + propertyDesc.getPropertyName());
/* 110:178 */       if (property != null)
/* 111:    */       {
/* 112:179 */         boolean hasNotNull = applyConstraints(propertyDesc.getConstraintDescriptors(), property, propertyDesc, groups, activateNotNull, dialect);
/* 113:182 */         if ((property.isComposite()) && (propertyDesc.isCascaded()))
/* 114:    */         {
/* 115:183 */           Class<?> componentClass = ((Component)property.getValue()).getComponentClass();
/* 116:    */           
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:190 */           boolean canSetNotNullOnColumns = (activateNotNull) && (hasNotNull);
/* 123:191 */           applyDDL(prefix + propertyDesc.getPropertyName() + ".", persistentClass, componentClass, factory, groups, canSetNotNullOnColumns, dialect);
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   private static boolean applyConstraints(Set<ConstraintDescriptor<?>> constraintDescriptors, Property property, PropertyDescriptor propertyDesc, Set<Class<?>> groups, boolean canApplyNotNull, Dialect dialect)
/* 130:    */   {
/* 131:227 */     boolean hasNotNull = false;
/* 132:228 */     for (ConstraintDescriptor<?> descriptor : constraintDescriptors) {
/* 133:229 */       if ((groups == null) || (!Collections.disjoint(descriptor.getGroups(), groups)))
/* 134:    */       {
/* 135:233 */         if (canApplyNotNull) {
/* 136:234 */           hasNotNull = (hasNotNull) || (applyNotNull(property, descriptor));
/* 137:    */         }
/* 138:238 */         applyDigits(property, descriptor);
/* 139:239 */         applySize(property, descriptor, propertyDesc);
/* 140:240 */         applyMin(property, descriptor, dialect);
/* 141:241 */         applyMax(property, descriptor, dialect);
/* 142:    */         
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:246 */         applyLength(property, descriptor, propertyDesc);
/* 147:    */         
/* 148:    */ 
/* 149:249 */         hasNotNull = (hasNotNull) || (applyConstraints(descriptor.getComposingConstraints(), property, propertyDesc, null, canApplyNotNull, dialect));
/* 150:    */       }
/* 151:    */     }
/* 152:256 */     return hasNotNull;
/* 153:    */   }
/* 154:    */   
/* 155:    */   private static void applyMin(Property property, ConstraintDescriptor<?> descriptor, Dialect dialect)
/* 156:    */   {
/* 157:291 */     if (Min.class.equals(descriptor.getAnnotation().annotationType()))
/* 158:    */     {
/* 159:293 */       ConstraintDescriptor<Min> minConstraint = descriptor;
/* 160:294 */       long min = ((Min)minConstraint.getAnnotation()).value();
/* 161:    */       
/* 162:296 */       Column col = (Column)property.getColumnIterator().next();
/* 163:297 */       String checkConstraint = col.getQuotedName(dialect) + ">=" + min;
/* 164:298 */       applySQLCheck(col, checkConstraint);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   private static void applyMax(Property property, ConstraintDescriptor<?> descriptor, Dialect dialect)
/* 169:    */   {
/* 170:303 */     if (Max.class.equals(descriptor.getAnnotation().annotationType()))
/* 171:    */     {
/* 172:305 */       ConstraintDescriptor<Max> maxConstraint = descriptor;
/* 173:306 */       long max = ((Max)maxConstraint.getAnnotation()).value();
/* 174:307 */       Column col = (Column)property.getColumnIterator().next();
/* 175:308 */       String checkConstraint = col.getQuotedName(dialect) + "<=" + max;
/* 176:309 */       applySQLCheck(col, checkConstraint);
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   private static void applySQLCheck(Column col, String checkConstraint)
/* 181:    */   {
/* 182:314 */     String existingCheck = col.getCheckConstraint();
/* 183:317 */     if ((StringHelper.isNotEmpty(existingCheck)) && (!existingCheck.contains(checkConstraint))) {
/* 184:318 */       checkConstraint = col.getCheckConstraint() + " AND " + checkConstraint;
/* 185:    */     }
/* 186:320 */     col.setCheckConstraint(checkConstraint);
/* 187:    */   }
/* 188:    */   
/* 189:    */   private static boolean applyNotNull(Property property, ConstraintDescriptor<?> descriptor)
/* 190:    */   {
/* 191:324 */     boolean hasNotNull = false;
/* 192:325 */     if (NotNull.class.equals(descriptor.getAnnotation().annotationType()))
/* 193:    */     {
/* 194:326 */       if (!(property.getPersistentClass() instanceof SingleTableSubclass)) {
/* 195:328 */         if (!property.isComposite())
/* 196:    */         {
/* 197:330 */           Iterator<Column> iter = property.getColumnIterator();
/* 198:331 */           while (iter.hasNext())
/* 199:    */           {
/* 200:332 */             ((Column)iter.next()).setNullable(false);
/* 201:333 */             hasNotNull = true;
/* 202:    */           }
/* 203:    */         }
/* 204:    */       }
/* 205:337 */       hasNotNull = true;
/* 206:    */     }
/* 207:339 */     return hasNotNull;
/* 208:    */   }
/* 209:    */   
/* 210:    */   private static void applyDigits(Property property, ConstraintDescriptor<?> descriptor)
/* 211:    */   {
/* 212:363 */     if (Digits.class.equals(descriptor.getAnnotation().annotationType()))
/* 213:    */     {
/* 214:365 */       ConstraintDescriptor<Digits> digitsConstraint = descriptor;
/* 215:366 */       int integerDigits = ((Digits)digitsConstraint.getAnnotation()).integer();
/* 216:367 */       int fractionalDigits = ((Digits)digitsConstraint.getAnnotation()).fraction();
/* 217:368 */       Column col = (Column)property.getColumnIterator().next();
/* 218:369 */       col.setPrecision(integerDigits + fractionalDigits);
/* 219:370 */       col.setScale(fractionalDigits);
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   private static void applySize(Property property, ConstraintDescriptor<?> descriptor, PropertyDescriptor propertyDescriptor)
/* 224:    */   {
/* 225:375 */     if ((Size.class.equals(descriptor.getAnnotation().annotationType())) && (String.class.equals(propertyDescriptor.getElementClass())))
/* 226:    */     {
/* 227:378 */       ConstraintDescriptor<Size> sizeConstraint = descriptor;
/* 228:379 */       int max = ((Size)sizeConstraint.getAnnotation()).max();
/* 229:380 */       Column col = (Column)property.getColumnIterator().next();
/* 230:381 */       if (max < 2147483647) {
/* 231:382 */         col.setLength(max);
/* 232:    */       }
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   private static void applyLength(Property property, ConstraintDescriptor<?> descriptor, PropertyDescriptor propertyDescriptor)
/* 237:    */   {
/* 238:388 */     if (("org.hibernate.validator.constraints.Length".equals(descriptor.getAnnotation().annotationType().getName())) && (String.class.equals(propertyDescriptor.getElementClass())))
/* 239:    */     {
/* 240:393 */       int max = ((Integer)descriptor.getAttributes().get("max")).intValue();
/* 241:394 */       Column col = (Column)property.getColumnIterator().next();
/* 242:395 */       if (max < 2147483647) {
/* 243:396 */         col.setLength(max);
/* 244:    */       }
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   private static Property findPropertyByName(PersistentClass associatedClass, String propertyName)
/* 249:    */   {
/* 250:408 */     Property property = null;
/* 251:409 */     Property idProperty = associatedClass.getIdentifierProperty();
/* 252:410 */     String idName = idProperty != null ? idProperty.getName() : null;
/* 253:    */     try
/* 254:    */     {
/* 255:412 */       if ((propertyName == null) || (propertyName.length() == 0) || (propertyName.equals(idName)))
/* 256:    */       {
/* 257:416 */         property = idProperty;
/* 258:    */       }
/* 259:    */       else
/* 260:    */       {
/* 261:419 */         if (propertyName.indexOf(idName + ".") == 0)
/* 262:    */         {
/* 263:420 */           property = idProperty;
/* 264:421 */           propertyName = propertyName.substring(idName.length() + 1);
/* 265:    */         }
/* 266:423 */         StringTokenizer st = new StringTokenizer(propertyName, ".", false);
/* 267:424 */         while (st.hasMoreElements())
/* 268:    */         {
/* 269:425 */           String element = (String)st.nextElement();
/* 270:426 */           if (property == null)
/* 271:    */           {
/* 272:427 */             property = associatedClass.getProperty(element);
/* 273:    */           }
/* 274:    */           else
/* 275:    */           {
/* 276:430 */             if (!property.isComposite()) {
/* 277:431 */               return null;
/* 278:    */             }
/* 279:433 */             property = ((Component)property.getValue()).getProperty(element);
/* 280:    */           }
/* 281:    */         }
/* 282:    */       }
/* 283:    */     }
/* 284:    */     catch (MappingException e)
/* 285:    */     {
/* 286:    */       try
/* 287:    */       {
/* 288:441 */         if (associatedClass.getIdentifierMapper() == null) {
/* 289:442 */           return null;
/* 290:    */         }
/* 291:444 */         StringTokenizer st = new StringTokenizer(propertyName, ".", false);
/* 292:445 */         while (st.hasMoreElements())
/* 293:    */         {
/* 294:446 */           String element = (String)st.nextElement();
/* 295:447 */           if (property == null)
/* 296:    */           {
/* 297:448 */             property = associatedClass.getIdentifierMapper().getProperty(element);
/* 298:    */           }
/* 299:    */           else
/* 300:    */           {
/* 301:451 */             if (!property.isComposite()) {
/* 302:452 */               return null;
/* 303:    */             }
/* 304:454 */             property = ((Component)property.getValue()).getProperty(element);
/* 305:    */           }
/* 306:    */         }
/* 307:    */       }
/* 308:    */       catch (MappingException ee)
/* 309:    */       {
/* 310:459 */         return null;
/* 311:    */       }
/* 312:    */     }
/* 313:462 */     return property;
/* 314:    */   }
/* 315:    */   
/* 316:    */   private static ValidatorFactory getValidatorFactory(Map<Object, Object> properties)
/* 317:    */   {
/* 318:504 */     ValidatorFactory factory = null;
/* 319:505 */     if (properties != null)
/* 320:    */     {
/* 321:506 */       Object unsafeProperty = properties.get("javax.persistence.validation.factory");
/* 322:507 */       if (unsafeProperty != null) {
/* 323:    */         try
/* 324:    */         {
/* 325:509 */           factory = (ValidatorFactory)ValidatorFactory.class.cast(unsafeProperty);
/* 326:    */         }
/* 327:    */         catch (ClassCastException e)
/* 328:    */         {
/* 329:512 */           throw new HibernateException("Property javax.persistence.validation.factory should contain an object of type " + ValidatorFactory.class.getName());
/* 330:    */         }
/* 331:    */       }
/* 332:    */     }
/* 333:519 */     if (factory == null) {
/* 334:    */       try
/* 335:    */       {
/* 336:521 */         factory = Validation.buildDefaultValidatorFactory();
/* 337:    */       }
/* 338:    */       catch (Exception e)
/* 339:    */       {
/* 340:524 */         throw new HibernateException("Unable to build the default ValidatorFactory", e);
/* 341:    */       }
/* 342:    */     }
/* 343:527 */     return factory;
/* 344:    */   }
/* 345:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.beanvalidation.TypeSafeActivator
 * JD-Core Version:    0.7.0.1
 */
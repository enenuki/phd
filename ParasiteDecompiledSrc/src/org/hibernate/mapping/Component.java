/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.hibernate.EntityMode;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.cfg.Mappings;
/*  12:    */ import org.hibernate.dialect.Dialect;
/*  13:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  14:    */ import org.hibernate.id.CompositeNestedGeneratedValueGenerator;
/*  15:    */ import org.hibernate.id.CompositeNestedGeneratedValueGenerator.GenerationContextLocator;
/*  16:    */ import org.hibernate.id.CompositeNestedGeneratedValueGenerator.GenerationPlan;
/*  17:    */ import org.hibernate.id.IdentifierGenerator;
/*  18:    */ import org.hibernate.id.PersistentIdentifierGenerator;
/*  19:    */ import org.hibernate.id.factory.IdentifierGeneratorFactory;
/*  20:    */ import org.hibernate.internal.util.ReflectHelper;
/*  21:    */ import org.hibernate.internal.util.collections.JoinedIterator;
/*  22:    */ import org.hibernate.persister.entity.EntityPersister;
/*  23:    */ import org.hibernate.property.PropertyAccessor;
/*  24:    */ import org.hibernate.property.Setter;
/*  25:    */ import org.hibernate.tuple.component.ComponentMetamodel;
/*  26:    */ import org.hibernate.type.Type;
/*  27:    */ import org.hibernate.type.TypeFactory;
/*  28:    */ import org.hibernate.type.TypeResolver;
/*  29:    */ 
/*  30:    */ public class Component
/*  31:    */   extends SimpleValue
/*  32:    */   implements MetaAttributable
/*  33:    */ {
/*  34: 54 */   private ArrayList properties = new ArrayList();
/*  35:    */   private String componentClassName;
/*  36:    */   private boolean embedded;
/*  37:    */   private String parentProperty;
/*  38:    */   private PersistentClass owner;
/*  39:    */   private boolean dynamic;
/*  40:    */   private Map metaAttributes;
/*  41:    */   private String nodeName;
/*  42:    */   private boolean isKey;
/*  43:    */   private String roleName;
/*  44:    */   private Map tuplizerImpls;
/*  45:    */   private Type type;
/*  46:    */   private IdentifierGenerator builtIdentifierGenerator;
/*  47:    */   
/*  48:    */   public Component(Mappings mappings, PersistentClass owner)
/*  49:    */     throws MappingException
/*  50:    */   {
/*  51: 68 */     super(mappings, owner.getTable());
/*  52: 69 */     this.owner = owner;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Component(Mappings mappings, Component component)
/*  56:    */     throws MappingException
/*  57:    */   {
/*  58: 73 */     super(mappings, component.getTable());
/*  59: 74 */     this.owner = component.getOwner();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Component(Mappings mappings, Join join)
/*  63:    */     throws MappingException
/*  64:    */   {
/*  65: 78 */     super(mappings, join.getTable());
/*  66: 79 */     this.owner = join.getPersistentClass();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Component(Mappings mappings, Collection collection)
/*  70:    */     throws MappingException
/*  71:    */   {
/*  72: 83 */     super(mappings, collection.getCollectionTable());
/*  73: 84 */     this.owner = collection.getOwner();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getPropertySpan()
/*  77:    */   {
/*  78: 88 */     return this.properties.size();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Iterator getPropertyIterator()
/*  82:    */   {
/*  83: 91 */     return this.properties.iterator();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void addProperty(Property p)
/*  87:    */   {
/*  88: 94 */     this.properties.add(p);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void addColumn(Column column)
/*  92:    */   {
/*  93: 97 */     throw new UnsupportedOperationException("Cant add a column to a component");
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getColumnSpan()
/*  97:    */   {
/*  98:100 */     int n = 0;
/*  99:101 */     Iterator iter = getPropertyIterator();
/* 100:102 */     while (iter.hasNext())
/* 101:    */     {
/* 102:103 */       Property p = (Property)iter.next();
/* 103:104 */       n += p.getColumnSpan();
/* 104:    */     }
/* 105:106 */     return n;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Iterator getColumnIterator()
/* 109:    */   {
/* 110:109 */     Iterator[] iters = new Iterator[getPropertySpan()];
/* 111:110 */     Iterator iter = getPropertyIterator();
/* 112:111 */     int i = 0;
/* 113:112 */     while (iter.hasNext()) {
/* 114:113 */       iters[(i++)] = ((Property)iter.next()).getColumnIterator();
/* 115:    */     }
/* 116:115 */     return new JoinedIterator(iters);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setTypeByReflection(String propertyClass, String propertyName) {}
/* 120:    */   
/* 121:    */   public boolean isEmbedded()
/* 122:    */   {
/* 123:121 */     return this.embedded;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getComponentClassName()
/* 127:    */   {
/* 128:125 */     return this.componentClassName;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Class getComponentClass()
/* 132:    */     throws MappingException
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:130 */       return ReflectHelper.classForName(this.componentClassName);
/* 137:    */     }
/* 138:    */     catch (ClassNotFoundException cnfe)
/* 139:    */     {
/* 140:133 */       throw new MappingException("component class not found: " + this.componentClassName, cnfe);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public PersistentClass getOwner()
/* 145:    */   {
/* 146:138 */     return this.owner;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getParentProperty()
/* 150:    */   {
/* 151:142 */     return this.parentProperty;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setComponentClassName(String componentClass)
/* 155:    */   {
/* 156:146 */     this.componentClassName = componentClass;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setEmbedded(boolean embedded)
/* 160:    */   {
/* 161:150 */     this.embedded = embedded;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setOwner(PersistentClass owner)
/* 165:    */   {
/* 166:154 */     this.owner = owner;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setParentProperty(String parentProperty)
/* 170:    */   {
/* 171:158 */     this.parentProperty = parentProperty;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean isDynamic()
/* 175:    */   {
/* 176:162 */     return this.dynamic;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setDynamic(boolean dynamic)
/* 180:    */   {
/* 181:166 */     this.dynamic = dynamic;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Type getType()
/* 185:    */     throws MappingException
/* 186:    */   {
/* 187:173 */     if (this.type == null) {
/* 188:174 */       this.type = buildType();
/* 189:    */     }
/* 190:176 */     return this.type;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private Type buildType()
/* 194:    */   {
/* 195:181 */     ComponentMetamodel metamodel = new ComponentMetamodel(this);
/* 196:182 */     if (isEmbedded()) {
/* 197:183 */       return getMappings().getTypeResolver().getTypeFactory().embeddedComponent(metamodel);
/* 198:    */     }
/* 199:186 */     return getMappings().getTypeResolver().getTypeFactory().component(metamodel);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setTypeUsingReflection(String className, String propertyName)
/* 203:    */     throws MappingException
/* 204:    */   {}
/* 205:    */   
/* 206:    */   public Map getMetaAttributes()
/* 207:    */   {
/* 208:195 */     return this.metaAttributes;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public MetaAttribute getMetaAttribute(String attributeName)
/* 212:    */   {
/* 213:198 */     return this.metaAttributes == null ? null : (MetaAttribute)this.metaAttributes.get(attributeName);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setMetaAttributes(Map metas)
/* 217:    */   {
/* 218:202 */     this.metaAttributes = metas;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Object accept(ValueVisitor visitor)
/* 222:    */   {
/* 223:206 */     return visitor.accept(this);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean[] getColumnInsertability()
/* 227:    */   {
/* 228:210 */     boolean[] result = new boolean[getColumnSpan()];
/* 229:211 */     Iterator iter = getPropertyIterator();
/* 230:212 */     int i = 0;
/* 231:213 */     while (iter.hasNext())
/* 232:    */     {
/* 233:214 */       Property prop = (Property)iter.next();
/* 234:215 */       boolean[] chunk = prop.getValue().getColumnInsertability();
/* 235:216 */       if (prop.isInsertable()) {
/* 236:217 */         System.arraycopy(chunk, 0, result, i, chunk.length);
/* 237:    */       }
/* 238:219 */       i += chunk.length;
/* 239:    */     }
/* 240:221 */     return result;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public boolean[] getColumnUpdateability()
/* 244:    */   {
/* 245:225 */     boolean[] result = new boolean[getColumnSpan()];
/* 246:226 */     Iterator iter = getPropertyIterator();
/* 247:227 */     int i = 0;
/* 248:228 */     while (iter.hasNext())
/* 249:    */     {
/* 250:229 */       Property prop = (Property)iter.next();
/* 251:230 */       boolean[] chunk = prop.getValue().getColumnUpdateability();
/* 252:231 */       if (prop.isUpdateable()) {
/* 253:232 */         System.arraycopy(chunk, 0, result, i, chunk.length);
/* 254:    */       }
/* 255:234 */       i += chunk.length;
/* 256:    */     }
/* 257:236 */     return result;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String getNodeName()
/* 261:    */   {
/* 262:240 */     return this.nodeName;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setNodeName(String nodeName)
/* 266:    */   {
/* 267:244 */     this.nodeName = nodeName;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public boolean isKey()
/* 271:    */   {
/* 272:248 */     return this.isKey;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setKey(boolean isKey)
/* 276:    */   {
/* 277:252 */     this.isKey = isKey;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean hasPojoRepresentation()
/* 281:    */   {
/* 282:256 */     return this.componentClassName != null;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void addTuplizer(EntityMode entityMode, String implClassName)
/* 286:    */   {
/* 287:260 */     if (this.tuplizerImpls == null) {
/* 288:261 */       this.tuplizerImpls = new HashMap();
/* 289:    */     }
/* 290:263 */     this.tuplizerImpls.put(entityMode, implClassName);
/* 291:    */   }
/* 292:    */   
/* 293:    */   public String getTuplizerImplClassName(EntityMode mode)
/* 294:    */   {
/* 295:268 */     if (this.tuplizerImpls == null) {
/* 296:269 */       return null;
/* 297:    */     }
/* 298:271 */     return (String)this.tuplizerImpls.get(mode);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public Map getTuplizerMap()
/* 302:    */   {
/* 303:275 */     if (this.tuplizerImpls == null) {
/* 304:276 */       return null;
/* 305:    */     }
/* 306:278 */     return Collections.unmodifiableMap(this.tuplizerImpls);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public Property getProperty(String propertyName)
/* 310:    */     throws MappingException
/* 311:    */   {
/* 312:282 */     Iterator iter = getPropertyIterator();
/* 313:283 */     while (iter.hasNext())
/* 314:    */     {
/* 315:284 */       Property prop = (Property)iter.next();
/* 316:285 */       if (prop.getName().equals(propertyName)) {
/* 317:286 */         return prop;
/* 318:    */       }
/* 319:    */     }
/* 320:289 */     throw new MappingException("component property not found: " + propertyName);
/* 321:    */   }
/* 322:    */   
/* 323:    */   public String getRoleName()
/* 324:    */   {
/* 325:293 */     return this.roleName;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setRoleName(String roleName)
/* 329:    */   {
/* 330:297 */     this.roleName = roleName;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public String toString()
/* 334:    */   {
/* 335:301 */     return getClass().getName() + '(' + this.properties.toString() + ')';
/* 336:    */   }
/* 337:    */   
/* 338:    */   public IdentifierGenerator createIdentifierGenerator(IdentifierGeneratorFactory identifierGeneratorFactory, Dialect dialect, String defaultCatalog, String defaultSchema, RootClass rootClass)
/* 339:    */     throws MappingException
/* 340:    */   {
/* 341:312 */     if (this.builtIdentifierGenerator == null) {
/* 342:313 */       this.builtIdentifierGenerator = buildIdentifierGenerator(identifierGeneratorFactory, dialect, defaultCatalog, defaultSchema, rootClass);
/* 343:    */     }
/* 344:321 */     return this.builtIdentifierGenerator;
/* 345:    */   }
/* 346:    */   
/* 347:    */   private IdentifierGenerator buildIdentifierGenerator(IdentifierGeneratorFactory identifierGeneratorFactory, Dialect dialect, String defaultCatalog, String defaultSchema, RootClass rootClass)
/* 348:    */     throws MappingException
/* 349:    */   {
/* 350:330 */     boolean hasCustomGenerator = !"assigned".equals(getIdentifierGeneratorStrategy());
/* 351:331 */     if (hasCustomGenerator) {
/* 352:332 */       return super.createIdentifierGenerator(identifierGeneratorFactory, dialect, defaultCatalog, defaultSchema, rootClass);
/* 353:    */     }
/* 354:337 */     Class entityClass = rootClass.getMappedClass();
/* 355:    */     Class attributeDeclarer;
/* 356:    */     Class attributeDeclarer;
/* 357:343 */     if (rootClass.getIdentifierMapper() != null)
/* 358:    */     {
/* 359:345 */       attributeDeclarer = resolveComponentClass();
/* 360:    */     }
/* 361:    */     else
/* 362:    */     {
/* 363:    */       Class attributeDeclarer;
/* 364:347 */       if (rootClass.getIdentifierProperty() != null) {
/* 365:349 */         attributeDeclarer = resolveComponentClass();
/* 366:    */       } else {
/* 367:353 */         attributeDeclarer = entityClass;
/* 368:    */       }
/* 369:    */     }
/* 370:356 */     CompositeNestedGeneratedValueGenerator.GenerationContextLocator locator = new StandardGenerationContextLocator(rootClass.getEntityName());
/* 371:357 */     CompositeNestedGeneratedValueGenerator generator = new CompositeNestedGeneratedValueGenerator(locator);
/* 372:    */     
/* 373:359 */     Iterator itr = getPropertyIterator();
/* 374:360 */     while (itr.hasNext())
/* 375:    */     {
/* 376:361 */       Property property = (Property)itr.next();
/* 377:362 */       if (property.getValue().isSimpleValue())
/* 378:    */       {
/* 379:363 */         SimpleValue value = (SimpleValue)property.getValue();
/* 380:365 */         if (!"assigned".equals(value.getIdentifierGeneratorStrategy()))
/* 381:    */         {
/* 382:371 */           IdentifierGenerator valueGenerator = value.createIdentifierGenerator(identifierGeneratorFactory, dialect, defaultCatalog, defaultSchema, rootClass);
/* 383:    */           
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:    */ 
/* 388:    */ 
/* 389:378 */           generator.addGeneratedValuePlan(new ValueGenerationPlan(property.getName(), valueGenerator, injector(property, attributeDeclarer)));
/* 390:    */         }
/* 391:    */       }
/* 392:    */     }
/* 393:387 */     return generator;
/* 394:    */   }
/* 395:    */   
/* 396:    */   private Setter injector(Property property, Class attributeDeclarer)
/* 397:    */   {
/* 398:391 */     return property.getPropertyAccessor(attributeDeclarer).getSetter(attributeDeclarer, property.getName());
/* 399:    */   }
/* 400:    */   
/* 401:    */   private Class resolveComponentClass()
/* 402:    */   {
/* 403:    */     try
/* 404:    */     {
/* 405:397 */       return getComponentClass();
/* 406:    */     }
/* 407:    */     catch (Exception e) {}
/* 408:400 */     return null;
/* 409:    */   }
/* 410:    */   
/* 411:    */   public static class StandardGenerationContextLocator
/* 412:    */     implements CompositeNestedGeneratedValueGenerator.GenerationContextLocator
/* 413:    */   {
/* 414:    */     private final String entityName;
/* 415:    */     
/* 416:    */     public StandardGenerationContextLocator(String entityName)
/* 417:    */     {
/* 418:409 */       this.entityName = entityName;
/* 419:    */     }
/* 420:    */     
/* 421:    */     public Serializable locateGenerationContext(SessionImplementor session, Object incomingObject)
/* 422:    */     {
/* 423:413 */       return session.getEntityPersister(this.entityName, incomingObject).getIdentifier(incomingObject, session);
/* 424:    */     }
/* 425:    */   }
/* 426:    */   
/* 427:    */   public static class ValueGenerationPlan
/* 428:    */     implements CompositeNestedGeneratedValueGenerator.GenerationPlan
/* 429:    */   {
/* 430:    */     private final String propertyName;
/* 431:    */     private final IdentifierGenerator subGenerator;
/* 432:    */     private final Setter injector;
/* 433:    */     
/* 434:    */     public ValueGenerationPlan(String propertyName, IdentifierGenerator subGenerator, Setter injector)
/* 435:    */     {
/* 436:426 */       this.propertyName = propertyName;
/* 437:427 */       this.subGenerator = subGenerator;
/* 438:428 */       this.injector = injector;
/* 439:    */     }
/* 440:    */     
/* 441:    */     public void execute(SessionImplementor session, Object incomingObject, Object injectionContext)
/* 442:    */     {
/* 443:435 */       Object generatedValue = this.subGenerator.generate(session, incomingObject);
/* 444:436 */       this.injector.set(injectionContext, generatedValue, session.getFactory());
/* 445:    */     }
/* 446:    */     
/* 447:    */     public void registerPersistentGenerators(Map generatorMap)
/* 448:    */     {
/* 449:440 */       if (PersistentIdentifierGenerator.class.isInstance(this.subGenerator)) {
/* 450:441 */         generatorMap.put(((PersistentIdentifierGenerator)this.subGenerator).generatorKey(), this.subGenerator);
/* 451:    */       }
/* 452:    */     }
/* 453:    */   }
/* 454:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Component
 * JD-Core Version:    0.7.0.1
 */
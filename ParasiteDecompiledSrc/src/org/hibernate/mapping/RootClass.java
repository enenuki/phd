/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.engine.spi.Mapping;
/*   9:    */ import org.hibernate.internal.CoreMessageLogger;
/*  10:    */ import org.hibernate.internal.util.ReflectHelper;
/*  11:    */ import org.hibernate.internal.util.collections.SingletonIterator;
/*  12:    */ import org.hibernate.type.Type;
/*  13:    */ import org.jboss.logging.Logger;
/*  14:    */ 
/*  15:    */ public class RootClass
/*  16:    */   extends PersistentClass
/*  17:    */   implements TableOwner
/*  18:    */ {
/*  19: 44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, RootClass.class.getName());
/*  20:    */   public static final String DEFAULT_IDENTIFIER_COLUMN_NAME = "id";
/*  21:    */   public static final String DEFAULT_DISCRIMINATOR_COLUMN_NAME = "class";
/*  22:    */   private Property identifierProperty;
/*  23:    */   private KeyValue identifier;
/*  24:    */   private Property version;
/*  25:    */   private boolean polymorphic;
/*  26:    */   private String cacheConcurrencyStrategy;
/*  27:    */   private String cacheRegionName;
/*  28: 55 */   private boolean lazyPropertiesCacheable = true;
/*  29:    */   private Value discriminator;
/*  30: 57 */   private boolean mutable = true;
/*  31: 58 */   private boolean embeddedIdentifier = false;
/*  32:    */   private boolean explicitPolymorphism;
/*  33:    */   private Class entityPersisterClass;
/*  34: 61 */   private boolean forceDiscriminator = false;
/*  35:    */   private String where;
/*  36:    */   private Table table;
/*  37: 64 */   private boolean discriminatorInsertable = true;
/*  38: 65 */   private int nextSubclassId = 0;
/*  39:    */   private Property declaredIdentifierProperty;
/*  40:    */   private Property declaredVersion;
/*  41:    */   
/*  42:    */   int nextSubclassId()
/*  43:    */   {
/*  44: 71 */     return ++this.nextSubclassId;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getSubclassId()
/*  48:    */   {
/*  49: 76 */     return 0;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setTable(Table table)
/*  53:    */   {
/*  54: 80 */     this.table = table;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Table getTable()
/*  58:    */   {
/*  59: 84 */     return this.table;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Property getIdentifierProperty()
/*  63:    */   {
/*  64: 89 */     return this.identifierProperty;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Property getDeclaredIdentifierProperty()
/*  68:    */   {
/*  69: 94 */     return this.declaredIdentifierProperty;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setDeclaredIdentifierProperty(Property declaredIdentifierProperty)
/*  73:    */   {
/*  74: 98 */     this.declaredIdentifierProperty = declaredIdentifierProperty;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public KeyValue getIdentifier()
/*  78:    */   {
/*  79:103 */     return this.identifier;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean hasIdentifierProperty()
/*  83:    */   {
/*  84:107 */     return this.identifierProperty != null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Value getDiscriminator()
/*  88:    */   {
/*  89:112 */     return this.discriminator;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isInherited()
/*  93:    */   {
/*  94:117 */     return false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isPolymorphic()
/*  98:    */   {
/*  99:121 */     return this.polymorphic;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setPolymorphic(boolean polymorphic)
/* 103:    */   {
/* 104:125 */     this.polymorphic = polymorphic;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public RootClass getRootClass()
/* 108:    */   {
/* 109:130 */     return this;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Iterator getPropertyClosureIterator()
/* 113:    */   {
/* 114:135 */     return getPropertyIterator();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Iterator getTableClosureIterator()
/* 118:    */   {
/* 119:139 */     return new SingletonIterator(getTable());
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Iterator getKeyClosureIterator()
/* 123:    */   {
/* 124:143 */     return new SingletonIterator(getKey());
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void addSubclass(Subclass subclass)
/* 128:    */     throws MappingException
/* 129:    */   {
/* 130:148 */     super.addSubclass(subclass);
/* 131:149 */     setPolymorphic(true);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean isExplicitPolymorphism()
/* 135:    */   {
/* 136:154 */     return this.explicitPolymorphism;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Property getVersion()
/* 140:    */   {
/* 141:159 */     return this.version;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Property getDeclaredVersion()
/* 145:    */   {
/* 146:164 */     return this.declaredVersion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setDeclaredVersion(Property declaredVersion)
/* 150:    */   {
/* 151:168 */     this.declaredVersion = declaredVersion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setVersion(Property version)
/* 155:    */   {
/* 156:172 */     this.version = version;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isVersioned()
/* 160:    */   {
/* 161:176 */     return this.version != null;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean isMutable()
/* 165:    */   {
/* 166:181 */     return this.mutable;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean hasEmbeddedIdentifier()
/* 170:    */   {
/* 171:185 */     return this.embeddedIdentifier;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Class getEntityPersisterClass()
/* 175:    */   {
/* 176:190 */     return this.entityPersisterClass;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Table getRootTable()
/* 180:    */   {
/* 181:195 */     return getTable();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setEntityPersisterClass(Class persister)
/* 185:    */   {
/* 186:200 */     this.entityPersisterClass = persister;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public PersistentClass getSuperclass()
/* 190:    */   {
/* 191:205 */     return null;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public KeyValue getKey()
/* 195:    */   {
/* 196:210 */     return getIdentifier();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setDiscriminator(Value discriminator)
/* 200:    */   {
/* 201:214 */     this.discriminator = discriminator;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setEmbeddedIdentifier(boolean embeddedIdentifier)
/* 205:    */   {
/* 206:218 */     this.embeddedIdentifier = embeddedIdentifier;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setExplicitPolymorphism(boolean explicitPolymorphism)
/* 210:    */   {
/* 211:222 */     this.explicitPolymorphism = explicitPolymorphism;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setIdentifier(KeyValue identifier)
/* 215:    */   {
/* 216:226 */     this.identifier = identifier;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setIdentifierProperty(Property identifierProperty)
/* 220:    */   {
/* 221:230 */     this.identifierProperty = identifierProperty;
/* 222:231 */     identifierProperty.setPersistentClass(this);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setMutable(boolean mutable)
/* 226:    */   {
/* 227:236 */     this.mutable = mutable;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public boolean isDiscriminatorInsertable()
/* 231:    */   {
/* 232:241 */     return this.discriminatorInsertable;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setDiscriminatorInsertable(boolean insertable)
/* 236:    */   {
/* 237:245 */     this.discriminatorInsertable = insertable;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean isForceDiscriminator()
/* 241:    */   {
/* 242:250 */     return this.forceDiscriminator;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setForceDiscriminator(boolean forceDiscriminator)
/* 246:    */   {
/* 247:254 */     this.forceDiscriminator = forceDiscriminator;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String getWhere()
/* 251:    */   {
/* 252:259 */     return this.where;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setWhere(String string)
/* 256:    */   {
/* 257:263 */     this.where = string;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void validate(Mapping mapping)
/* 261:    */     throws MappingException
/* 262:    */   {
/* 263:268 */     super.validate(mapping);
/* 264:269 */     if (!getIdentifier().isValid(mapping)) {
/* 265:270 */       throw new MappingException("identifier mapping has wrong number of columns: " + getEntityName() + " type: " + getIdentifier().getType().getName());
/* 266:    */     }
/* 267:277 */     checkCompositeIdentifier();
/* 268:    */   }
/* 269:    */   
/* 270:    */   private void checkCompositeIdentifier()
/* 271:    */   {
/* 272:281 */     if ((getIdentifier() instanceof Component))
/* 273:    */     {
/* 274:282 */       Component id = (Component)getIdentifier();
/* 275:283 */       if (!id.isDynamic())
/* 276:    */       {
/* 277:284 */         Class idClass = id.getComponentClass();
/* 278:285 */         String idComponendClassName = idClass.getName();
/* 279:286 */         if ((idClass != null) && (!ReflectHelper.overridesEquals(idClass))) {
/* 280:286 */           LOG.compositeIdClassDoesNotOverrideEquals(idComponendClassName);
/* 281:    */         }
/* 282:287 */         if (!ReflectHelper.overridesHashCode(idClass)) {
/* 283:287 */           LOG.compositeIdClassDoesNotOverrideHashCode(idComponendClassName);
/* 284:    */         }
/* 285:288 */         if (!Serializable.class.isAssignableFrom(idClass)) {
/* 286:288 */           throw new MappingException("Composite-id class must implement Serializable: " + idComponendClassName);
/* 287:    */         }
/* 288:    */       }
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public String getCacheConcurrencyStrategy()
/* 293:    */   {
/* 294:297 */     return this.cacheConcurrencyStrategy;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setCacheConcurrencyStrategy(String cacheConcurrencyStrategy)
/* 298:    */   {
/* 299:301 */     this.cacheConcurrencyStrategy = cacheConcurrencyStrategy;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public String getCacheRegionName()
/* 303:    */   {
/* 304:305 */     return this.cacheRegionName == null ? getEntityName() : this.cacheRegionName;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setCacheRegionName(String cacheRegionName)
/* 308:    */   {
/* 309:308 */     this.cacheRegionName = cacheRegionName;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public boolean isLazyPropertiesCacheable()
/* 313:    */   {
/* 314:313 */     return this.lazyPropertiesCacheable;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setLazyPropertiesCacheable(boolean lazyPropertiesCacheable)
/* 318:    */   {
/* 319:317 */     this.lazyPropertiesCacheable = lazyPropertiesCacheable;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public boolean isJoinedSubclass()
/* 323:    */   {
/* 324:322 */     return false;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public Set getSynchronizedTables()
/* 328:    */   {
/* 329:327 */     return this.synchronizedTables;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public Set getIdentityTables()
/* 333:    */   {
/* 334:331 */     Set tables = new HashSet();
/* 335:332 */     Iterator iter = getSubclassClosureIterator();
/* 336:    */     PersistentClass clazz;
/* 337:333 */     for (; iter.hasNext(); tables.add(clazz.getIdentityTable()))
/* 338:    */     {
/* 339:334 */       clazz = (PersistentClass)iter.next();
/* 340:335 */       if ((clazz.isAbstract() != null) && (clazz.isAbstract().booleanValue())) {}
/* 341:    */     }
/* 342:337 */     return tables;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public Object accept(PersistentClassVisitor mv)
/* 346:    */   {
/* 347:342 */     return mv.accept(this);
/* 348:    */   }
/* 349:    */   
/* 350:    */   public int getOptimisticLockMode()
/* 351:    */   {
/* 352:347 */     return this.optimisticLockMode;
/* 353:    */   }
/* 354:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.RootClass
 * JD-Core Version:    0.7.0.1
 */
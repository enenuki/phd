/*   1:    */ package org.hibernate.persister.internal;
/*   2:    */ 
/*   3:    */ import org.hibernate.HibernateException;
/*   4:    */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*   5:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*   6:    */ import org.hibernate.cfg.Configuration;
/*   7:    */ import org.hibernate.engine.spi.Mapping;
/*   8:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   9:    */ import org.hibernate.mapping.Collection;
/*  10:    */ import org.hibernate.mapping.PersistentClass;
/*  11:    */ import org.hibernate.metamodel.binding.AbstractPluralAttributeBinding;
/*  12:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  13:    */ import org.hibernate.metamodel.binding.PluralAttributeBinding;
/*  14:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  15:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  16:    */ import org.hibernate.persister.entity.EntityPersister;
/*  17:    */ import org.hibernate.persister.spi.PersisterClassResolver;
/*  18:    */ import org.hibernate.persister.spi.PersisterFactory;
/*  19:    */ import org.hibernate.service.spi.ServiceRegistryAwareService;
/*  20:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  21:    */ 
/*  22:    */ public final class PersisterFactoryImpl
/*  23:    */   implements PersisterFactory, ServiceRegistryAwareService
/*  24:    */ {
/*  25: 62 */   public static final Class[] ENTITY_PERSISTER_CONSTRUCTOR_ARGS = { PersistentClass.class, EntityRegionAccessStrategy.class, SessionFactoryImplementor.class, Mapping.class };
/*  26: 77 */   public static final Class[] ENTITY_PERSISTER_CONSTRUCTOR_ARGS_NEW = { EntityBinding.class, EntityRegionAccessStrategy.class, SessionFactoryImplementor.class, Mapping.class };
/*  27: 90 */   private static final Class[] COLLECTION_PERSISTER_CONSTRUCTOR_ARGS = { Collection.class, CollectionRegionAccessStrategy.class, Configuration.class, SessionFactoryImplementor.class };
/*  28:106 */   private static final Class[] COLLECTION_PERSISTER_CONSTRUCTOR_ARGS_NEW = { AbstractPluralAttributeBinding.class, CollectionRegionAccessStrategy.class, MetadataImplementor.class, SessionFactoryImplementor.class };
/*  29:    */   private ServiceRegistryImplementor serviceRegistry;
/*  30:    */   
/*  31:    */   public void injectServices(ServiceRegistryImplementor serviceRegistry)
/*  32:    */   {
/*  33:117 */     this.serviceRegistry = serviceRegistry;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public EntityPersister createEntityPersister(PersistentClass metadata, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory, Mapping cfg)
/*  37:    */   {
/*  38:127 */     Class<? extends EntityPersister> persisterClass = metadata.getEntityPersisterClass();
/*  39:128 */     if (persisterClass == null) {
/*  40:129 */       persisterClass = ((PersisterClassResolver)this.serviceRegistry.getService(PersisterClassResolver.class)).getEntityPersisterClass(metadata);
/*  41:    */     }
/*  42:131 */     return create(persisterClass, ENTITY_PERSISTER_CONSTRUCTOR_ARGS, metadata, cacheAccessStrategy, factory, cfg);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public EntityPersister createEntityPersister(EntityBinding metadata, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory, Mapping cfg)
/*  46:    */   {
/*  47:140 */     Class<? extends EntityPersister> persisterClass = metadata.getCustomEntityPersisterClass();
/*  48:141 */     if (persisterClass == null) {
/*  49:142 */       persisterClass = ((PersisterClassResolver)this.serviceRegistry.getService(PersisterClassResolver.class)).getEntityPersisterClass(metadata);
/*  50:    */     }
/*  51:144 */     return create(persisterClass, ENTITY_PERSISTER_CONSTRUCTOR_ARGS_NEW, metadata, cacheAccessStrategy, factory, cfg);
/*  52:    */   }
/*  53:    */   
/*  54:    */   /* Error */
/*  55:    */   private static EntityPersister create(Class<? extends EntityPersister> persisterClass, Class[] persisterConstructorArgs, Object metadata, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory, Mapping cfg)
/*  56:    */     throws HibernateException
/*  57:    */   {
/*  58:    */     // Byte code:
/*  59:    */     //   0: aload_0
/*  60:    */     //   1: aload_1
/*  61:    */     //   2: invokevirtual 12	java/lang/Class:getConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/*  62:    */     //   5: astore 6
/*  63:    */     //   7: aload 6
/*  64:    */     //   9: iconst_4
/*  65:    */     //   10: anewarray 13	java/lang/Object
/*  66:    */     //   13: dup
/*  67:    */     //   14: iconst_0
/*  68:    */     //   15: aload_2
/*  69:    */     //   16: aastore
/*  70:    */     //   17: dup
/*  71:    */     //   18: iconst_1
/*  72:    */     //   19: aload_3
/*  73:    */     //   20: aastore
/*  74:    */     //   21: dup
/*  75:    */     //   22: iconst_2
/*  76:    */     //   23: aload 4
/*  77:    */     //   25: aastore
/*  78:    */     //   26: dup
/*  79:    */     //   27: iconst_3
/*  80:    */     //   28: aload 5
/*  81:    */     //   30: aastore
/*  82:    */     //   31: invokevirtual 14	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
/*  83:    */     //   34: checkcast 15	org/hibernate/persister/entity/EntityPersister
/*  84:    */     //   37: areturn
/*  85:    */     //   38: astore 7
/*  86:    */     //   40: aload 7
/*  87:    */     //   42: athrow
/*  88:    */     //   43: astore 7
/*  89:    */     //   45: aload 7
/*  90:    */     //   47: invokevirtual 18	java/lang/reflect/InvocationTargetException:getTargetException	()Ljava/lang/Throwable;
/*  91:    */     //   50: astore 8
/*  92:    */     //   52: aload 8
/*  93:    */     //   54: instanceof 19
/*  94:    */     //   57: ifeq +9 -> 66
/*  95:    */     //   60: aload 8
/*  96:    */     //   62: checkcast 19	org/hibernate/HibernateException
/*  97:    */     //   65: athrow
/*  98:    */     //   66: new 16	org/hibernate/MappingException
/*  99:    */     //   69: dup
/* 100:    */     //   70: new 20	java/lang/StringBuilder
/* 101:    */     //   73: dup
/* 102:    */     //   74: invokespecial 21	java/lang/StringBuilder:<init>	()V
/* 103:    */     //   77: ldc 22
/* 104:    */     //   79: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 105:    */     //   82: aload_0
/* 106:    */     //   83: invokevirtual 24	java/lang/Class:getName	()Ljava/lang/String;
/* 107:    */     //   86: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 108:    */     //   89: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 109:    */     //   92: aload 8
/* 110:    */     //   94: invokespecial 26	org/hibernate/MappingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 111:    */     //   97: athrow
/* 112:    */     //   98: astore 7
/* 113:    */     //   100: new 16	org/hibernate/MappingException
/* 114:    */     //   103: dup
/* 115:    */     //   104: new 20	java/lang/StringBuilder
/* 116:    */     //   107: dup
/* 117:    */     //   108: invokespecial 21	java/lang/StringBuilder:<init>	()V
/* 118:    */     //   111: ldc 22
/* 119:    */     //   113: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 120:    */     //   116: aload_0
/* 121:    */     //   117: invokevirtual 24	java/lang/Class:getName	()Ljava/lang/String;
/* 122:    */     //   120: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 123:    */     //   123: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 124:    */     //   126: aload 7
/* 125:    */     //   128: invokespecial 26	org/hibernate/MappingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 126:    */     //   131: athrow
/* 127:    */     //   132: astore 6
/* 128:    */     //   134: aload 6
/* 129:    */     //   136: athrow
/* 130:    */     //   137: astore 6
/* 131:    */     //   139: new 16	org/hibernate/MappingException
/* 132:    */     //   142: dup
/* 133:    */     //   143: new 20	java/lang/StringBuilder
/* 134:    */     //   146: dup
/* 135:    */     //   147: invokespecial 21	java/lang/StringBuilder:<init>	()V
/* 136:    */     //   150: ldc 28
/* 137:    */     //   152: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 138:    */     //   155: aload_0
/* 139:    */     //   156: invokevirtual 24	java/lang/Class:getName	()Ljava/lang/String;
/* 140:    */     //   159: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 141:    */     //   162: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 142:    */     //   165: aload 6
/* 143:    */     //   167: invokespecial 26	org/hibernate/MappingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 144:    */     //   170: athrow
/* 145:    */     // Line number table:
/* 146:    */     //   Java source line #156	-> byte code offset #0
/* 147:    */     //   Java source line #158	-> byte code offset #7
/* 148:    */     //   Java source line #160	-> byte code offset #38
/* 149:    */     //   Java source line #161	-> byte code offset #40
/* 150:    */     //   Java source line #163	-> byte code offset #43
/* 151:    */     //   Java source line #164	-> byte code offset #45
/* 152:    */     //   Java source line #165	-> byte code offset #52
/* 153:    */     //   Java source line #166	-> byte code offset #60
/* 154:    */     //   Java source line #169	-> byte code offset #66
/* 155:    */     //   Java source line #172	-> byte code offset #98
/* 156:    */     //   Java source line #173	-> byte code offset #100
/* 157:    */     //   Java source line #176	-> byte code offset #132
/* 158:    */     //   Java source line #177	-> byte code offset #134
/* 159:    */     //   Java source line #179	-> byte code offset #137
/* 160:    */     //   Java source line #180	-> byte code offset #139
/* 161:    */     // Local variable table:
/* 162:    */     //   start	length	slot	name	signature
/* 163:    */     //   0	171	0	persisterClass	Class<? extends EntityPersister>
/* 164:    */     //   0	171	1	persisterConstructorArgs	Class[]
/* 165:    */     //   0	171	2	metadata	Object
/* 166:    */     //   0	171	3	cacheAccessStrategy	EntityRegionAccessStrategy
/* 167:    */     //   0	171	4	factory	SessionFactoryImplementor
/* 168:    */     //   0	171	5	cfg	Mapping
/* 169:    */     //   5	3	6	constructor	java.lang.reflect.Constructor<? extends EntityPersister>
/* 170:    */     //   132	3	6	e	org.hibernate.MappingException
/* 171:    */     //   137	29	6	e	java.lang.Exception
/* 172:    */     //   38	3	7	e	org.hibernate.MappingException
/* 173:    */     //   43	3	7	e	java.lang.reflect.InvocationTargetException
/* 174:    */     //   98	29	7	e	java.lang.Exception
/* 175:    */     //   50	43	8	target	java.lang.Throwable
/* 176:    */     // Exception table:
/* 177:    */     //   from	to	target	type
/* 178:    */     //   7	37	38	org/hibernate/MappingException
/* 179:    */     //   7	37	43	java/lang/reflect/InvocationTargetException
/* 180:    */     //   7	37	98	java/lang/Exception
/* 181:    */     //   0	37	132	org/hibernate/MappingException
/* 182:    */     //   38	132	132	org/hibernate/MappingException
/* 183:    */     //   0	37	137	java/lang/Exception
/* 184:    */     //   38	132	137	java/lang/Exception
/* 185:    */   }
/* 186:    */   
/* 187:    */   public CollectionPersister createCollectionPersister(Configuration cfg, Collection collectionMetadata, CollectionRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory)
/* 188:    */     throws HibernateException
/* 189:    */   {
/* 190:191 */     Class<? extends CollectionPersister> persisterClass = collectionMetadata.getCollectionPersisterClass();
/* 191:192 */     if (persisterClass == null) {
/* 192:193 */       persisterClass = ((PersisterClassResolver)this.serviceRegistry.getService(PersisterClassResolver.class)).getCollectionPersisterClass(collectionMetadata);
/* 193:    */     }
/* 194:196 */     return create(persisterClass, COLLECTION_PERSISTER_CONSTRUCTOR_ARGS, cfg, collectionMetadata, cacheAccessStrategy, factory);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public CollectionPersister createCollectionPersister(MetadataImplementor metadata, PluralAttributeBinding collectionMetadata, CollectionRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory)
/* 198:    */     throws HibernateException
/* 199:    */   {
/* 200:206 */     Class<? extends CollectionPersister> persisterClass = collectionMetadata.getCollectionPersisterClass();
/* 201:207 */     if (persisterClass == null) {
/* 202:208 */       persisterClass = ((PersisterClassResolver)this.serviceRegistry.getService(PersisterClassResolver.class)).getCollectionPersisterClass(collectionMetadata);
/* 203:    */     }
/* 204:211 */     return create(persisterClass, COLLECTION_PERSISTER_CONSTRUCTOR_ARGS_NEW, metadata, collectionMetadata, cacheAccessStrategy, factory);
/* 205:    */   }
/* 206:    */   
/* 207:    */   /* Error */
/* 208:    */   private static CollectionPersister create(Class<? extends CollectionPersister> persisterClass, Class[] persisterConstructorArgs, Object cfg, Object collectionMetadata, CollectionRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory)
/* 209:    */     throws HibernateException
/* 210:    */   {
/* 211:    */     // Byte code:
/* 212:    */     //   0: aload_0
/* 213:    */     //   1: aload_1
/* 214:    */     //   2: invokevirtual 12	java/lang/Class:getConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
/* 215:    */     //   5: astore 6
/* 216:    */     //   7: aload 6
/* 217:    */     //   9: iconst_4
/* 218:    */     //   10: anewarray 13	java/lang/Object
/* 219:    */     //   13: dup
/* 220:    */     //   14: iconst_0
/* 221:    */     //   15: aload_3
/* 222:    */     //   16: aastore
/* 223:    */     //   17: dup
/* 224:    */     //   18: iconst_1
/* 225:    */     //   19: aload 4
/* 226:    */     //   21: aastore
/* 227:    */     //   22: dup
/* 228:    */     //   23: iconst_2
/* 229:    */     //   24: aload_2
/* 230:    */     //   25: aastore
/* 231:    */     //   26: dup
/* 232:    */     //   27: iconst_3
/* 233:    */     //   28: aload 5
/* 234:    */     //   30: aastore
/* 235:    */     //   31: invokevirtual 14	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
/* 236:    */     //   34: checkcast 36	org/hibernate/persister/collection/CollectionPersister
/* 237:    */     //   37: areturn
/* 238:    */     //   38: astore 7
/* 239:    */     //   40: aload 7
/* 240:    */     //   42: athrow
/* 241:    */     //   43: astore 7
/* 242:    */     //   45: aload 7
/* 243:    */     //   47: invokevirtual 18	java/lang/reflect/InvocationTargetException:getTargetException	()Ljava/lang/Throwable;
/* 244:    */     //   50: astore 8
/* 245:    */     //   52: aload 8
/* 246:    */     //   54: instanceof 19
/* 247:    */     //   57: ifeq +9 -> 66
/* 248:    */     //   60: aload 8
/* 249:    */     //   62: checkcast 19	org/hibernate/HibernateException
/* 250:    */     //   65: athrow
/* 251:    */     //   66: new 16	org/hibernate/MappingException
/* 252:    */     //   69: dup
/* 253:    */     //   70: new 20	java/lang/StringBuilder
/* 254:    */     //   73: dup
/* 255:    */     //   74: invokespecial 21	java/lang/StringBuilder:<init>	()V
/* 256:    */     //   77: ldc 37
/* 257:    */     //   79: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 258:    */     //   82: aload_0
/* 259:    */     //   83: invokevirtual 24	java/lang/Class:getName	()Ljava/lang/String;
/* 260:    */     //   86: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 261:    */     //   89: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 262:    */     //   92: aload 8
/* 263:    */     //   94: invokespecial 26	org/hibernate/MappingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 264:    */     //   97: athrow
/* 265:    */     //   98: astore 7
/* 266:    */     //   100: new 16	org/hibernate/MappingException
/* 267:    */     //   103: dup
/* 268:    */     //   104: new 20	java/lang/StringBuilder
/* 269:    */     //   107: dup
/* 270:    */     //   108: invokespecial 21	java/lang/StringBuilder:<init>	()V
/* 271:    */     //   111: ldc 37
/* 272:    */     //   113: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 273:    */     //   116: aload_0
/* 274:    */     //   117: invokevirtual 24	java/lang/Class:getName	()Ljava/lang/String;
/* 275:    */     //   120: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 276:    */     //   123: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 277:    */     //   126: aload 7
/* 278:    */     //   128: invokespecial 26	org/hibernate/MappingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 279:    */     //   131: athrow
/* 280:    */     //   132: astore 6
/* 281:    */     //   134: aload 6
/* 282:    */     //   136: athrow
/* 283:    */     //   137: astore 6
/* 284:    */     //   139: new 16	org/hibernate/MappingException
/* 285:    */     //   142: dup
/* 286:    */     //   143: new 20	java/lang/StringBuilder
/* 287:    */     //   146: dup
/* 288:    */     //   147: invokespecial 21	java/lang/StringBuilder:<init>	()V
/* 289:    */     //   150: ldc 28
/* 290:    */     //   152: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 291:    */     //   155: aload_0
/* 292:    */     //   156: invokevirtual 24	java/lang/Class:getName	()Ljava/lang/String;
/* 293:    */     //   159: invokevirtual 23	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 294:    */     //   162: invokevirtual 25	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 295:    */     //   165: aload 6
/* 296:    */     //   167: invokespecial 26	org/hibernate/MappingException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 297:    */     //   170: athrow
/* 298:    */     // Line number table:
/* 299:    */     //   Java source line #224	-> byte code offset #0
/* 300:    */     //   Java source line #226	-> byte code offset #7
/* 301:    */     //   Java source line #228	-> byte code offset #38
/* 302:    */     //   Java source line #229	-> byte code offset #40
/* 303:    */     //   Java source line #231	-> byte code offset #43
/* 304:    */     //   Java source line #232	-> byte code offset #45
/* 305:    */     //   Java source line #233	-> byte code offset #52
/* 306:    */     //   Java source line #234	-> byte code offset #60
/* 307:    */     //   Java source line #237	-> byte code offset #66
/* 308:    */     //   Java source line #240	-> byte code offset #98
/* 309:    */     //   Java source line #241	-> byte code offset #100
/* 310:    */     //   Java source line #244	-> byte code offset #132
/* 311:    */     //   Java source line #245	-> byte code offset #134
/* 312:    */     //   Java source line #247	-> byte code offset #137
/* 313:    */     //   Java source line #248	-> byte code offset #139
/* 314:    */     // Local variable table:
/* 315:    */     //   start	length	slot	name	signature
/* 316:    */     //   0	171	0	persisterClass	Class<? extends CollectionPersister>
/* 317:    */     //   0	171	1	persisterConstructorArgs	Class[]
/* 318:    */     //   0	171	2	cfg	Object
/* 319:    */     //   0	171	3	collectionMetadata	Object
/* 320:    */     //   0	171	4	cacheAccessStrategy	CollectionRegionAccessStrategy
/* 321:    */     //   0	171	5	factory	SessionFactoryImplementor
/* 322:    */     //   5	3	6	constructor	java.lang.reflect.Constructor<? extends CollectionPersister>
/* 323:    */     //   132	3	6	e	org.hibernate.MappingException
/* 324:    */     //   137	29	6	e	java.lang.Exception
/* 325:    */     //   38	3	7	e	org.hibernate.MappingException
/* 326:    */     //   43	3	7	e	java.lang.reflect.InvocationTargetException
/* 327:    */     //   98	29	7	e	java.lang.Exception
/* 328:    */     //   50	43	8	target	java.lang.Throwable
/* 329:    */     // Exception table:
/* 330:    */     //   from	to	target	type
/* 331:    */     //   7	37	38	org/hibernate/MappingException
/* 332:    */     //   7	37	43	java/lang/reflect/InvocationTargetException
/* 333:    */     //   7	37	98	java/lang/Exception
/* 334:    */     //   0	37	132	org/hibernate/MappingException
/* 335:    */     //   38	132	132	org/hibernate/MappingException
/* 336:    */     //   0	37	137	java/lang/Exception
/* 337:    */     //   38	132	137	java/lang/Exception
/* 338:    */   }
/* 339:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.internal.PersisterFactoryImpl
 * JD-Core Version:    0.7.0.1
 */
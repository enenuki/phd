/*    1:     */ package org.hibernate.metamodel.source.binder;
/*    2:     */ 
/*    3:     */ import java.beans.BeanInfo;
/*    4:     */ import java.beans.PropertyDescriptor;
/*    5:     */ import java.lang.reflect.Field;
/*    6:     */ import java.lang.reflect.Method;
/*    7:     */ import java.lang.reflect.ParameterizedType;
/*    8:     */ import java.util.ArrayList;
/*    9:     */ import java.util.HashMap;
/*   10:     */ import java.util.List;
/*   11:     */ import java.util.Map;
/*   12:     */ import org.hibernate.AssertionFailure;
/*   13:     */ import org.hibernate.EntityMode;
/*   14:     */ import org.hibernate.cfg.NamingStrategy;
/*   15:     */ import org.hibernate.cfg.NotYetImplementedException;
/*   16:     */ import org.hibernate.internal.util.StringHelper;
/*   17:     */ import org.hibernate.internal.util.beans.BeanInfoHelper;
/*   18:     */ import org.hibernate.internal.util.beans.BeanInfoHelper.BeanInfoDelegate;
/*   19:     */ import org.hibernate.metamodel.Metadata.Options;
/*   20:     */ import org.hibernate.metamodel.binding.AbstractPluralAttributeBinding;
/*   21:     */ import org.hibernate.metamodel.binding.AttributeBinding;
/*   22:     */ import org.hibernate.metamodel.binding.AttributeBindingContainer;
/*   23:     */ import org.hibernate.metamodel.binding.BasicAttributeBinding;
/*   24:     */ import org.hibernate.metamodel.binding.BasicCollectionElement;
/*   25:     */ import org.hibernate.metamodel.binding.CollectionElementNature;
/*   26:     */ import org.hibernate.metamodel.binding.CollectionKey;
/*   27:     */ import org.hibernate.metamodel.binding.CollectionLaziness;
/*   28:     */ import org.hibernate.metamodel.binding.ComponentAttributeBinding;
/*   29:     */ import org.hibernate.metamodel.binding.EntityBinding;
/*   30:     */ import org.hibernate.metamodel.binding.EntityDiscriminator;
/*   31:     */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*   32:     */ import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
/*   33:     */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*   34:     */ import org.hibernate.metamodel.binding.IdGenerator;
/*   35:     */ import org.hibernate.metamodel.binding.InheritanceType;
/*   36:     */ import org.hibernate.metamodel.binding.ManyToOneAttributeBinding;
/*   37:     */ import org.hibernate.metamodel.binding.MetaAttribute;
/*   38:     */ import org.hibernate.metamodel.binding.SimpleValueBinding;
/*   39:     */ import org.hibernate.metamodel.binding.SingularAttributeBinding;
/*   40:     */ import org.hibernate.metamodel.binding.TypeDef;
/*   41:     */ import org.hibernate.metamodel.domain.Attribute;
/*   42:     */ import org.hibernate.metamodel.domain.AttributeContainer;
/*   43:     */ import org.hibernate.metamodel.domain.Component;
/*   44:     */ import org.hibernate.metamodel.domain.Entity;
/*   45:     */ import org.hibernate.metamodel.domain.PluralAttribute;
/*   46:     */ import org.hibernate.metamodel.domain.SingularAttribute;
/*   47:     */ import org.hibernate.metamodel.relational.Column;
/*   48:     */ import org.hibernate.metamodel.relational.Database;
/*   49:     */ import org.hibernate.metamodel.relational.DerivedValue;
/*   50:     */ import org.hibernate.metamodel.relational.ForeignKey;
/*   51:     */ import org.hibernate.metamodel.relational.Identifier;
/*   52:     */ import org.hibernate.metamodel.relational.PrimaryKey;
/*   53:     */ import org.hibernate.metamodel.relational.Schema;
/*   54:     */ import org.hibernate.metamodel.relational.Schema.Name;
/*   55:     */ import org.hibernate.metamodel.relational.SimpleValue;
/*   56:     */ import org.hibernate.metamodel.relational.Table;
/*   57:     */ import org.hibernate.metamodel.relational.TableSpecification;
/*   58:     */ import org.hibernate.metamodel.relational.Tuple;
/*   59:     */ import org.hibernate.metamodel.relational.UniqueKey;
/*   60:     */ import org.hibernate.metamodel.relational.Value;
/*   61:     */ import org.hibernate.metamodel.source.LocalBindingContext;
/*   62:     */ import org.hibernate.metamodel.source.MappingDefaults;
/*   63:     */ import org.hibernate.metamodel.source.MappingException;
/*   64:     */ import org.hibernate.metamodel.source.MetaAttributeContext;
/*   65:     */ import org.hibernate.metamodel.source.MetadataImplementor;
/*   66:     */ import org.hibernate.metamodel.source.hbm.Helper;
/*   67:     */ 
/*   68:     */ public class Binder
/*   69:     */ {
/*   70:     */   private final MetadataImplementor metadata;
/*   71:     */   private final List<String> processedEntityNames;
/*   72:     */   private InheritanceType currentInheritanceType;
/*   73:     */   private EntityMode currentHierarchyEntityMode;
/*   74:     */   private LocalBindingContext currentBindingContext;
/*   75:     */   
/*   76:     */   public Binder(MetadataImplementor metadata, List<String> processedEntityNames)
/*   77:     */   {
/*   78:  97 */     this.metadata = metadata;
/*   79:  98 */     this.processedEntityNames = processedEntityNames;
/*   80:     */   }
/*   81:     */   
/*   82:     */   public void processEntityHierarchy(EntityHierarchy entityHierarchy)
/*   83:     */   {
/*   84: 107 */     this.currentInheritanceType = entityHierarchy.getHierarchyInheritanceType();
/*   85: 108 */     EntityBinding rootEntityBinding = createEntityBinding(entityHierarchy.getRootEntitySource(), null);
/*   86: 109 */     if (this.currentInheritanceType != InheritanceType.NO_INHERITANCE) {
/*   87: 110 */       processHierarchySubEntities(entityHierarchy.getRootEntitySource(), rootEntityBinding);
/*   88:     */     }
/*   89: 112 */     this.currentHierarchyEntityMode = null;
/*   90:     */   }
/*   91:     */   
/*   92:     */   private void processHierarchySubEntities(SubclassEntityContainer subclassEntitySource, EntityBinding superEntityBinding)
/*   93:     */   {
/*   94: 116 */     for (SubclassEntitySource subEntity : subclassEntitySource.subclassEntitySources())
/*   95:     */     {
/*   96: 117 */       EntityBinding entityBinding = createEntityBinding(subEntity, superEntityBinding);
/*   97: 118 */       processHierarchySubEntities(subEntity, entityBinding);
/*   98:     */     }
/*   99:     */   }
/*  100:     */   
/*  101:     */   private EntityBinding createEntityBinding(EntitySource entitySource, EntityBinding superEntityBinding)
/*  102:     */   {
/*  103: 126 */     if (this.processedEntityNames.contains(entitySource.getEntityName())) {
/*  104: 127 */       return this.metadata.getEntityBinding(entitySource.getEntityName());
/*  105:     */     }
/*  106: 130 */     this.currentBindingContext = entitySource.getLocalBindingContext();
/*  107:     */     try
/*  108:     */     {
/*  109: 132 */       EntityBinding entityBinding = doCreateEntityBinding(entitySource, superEntityBinding);
/*  110:     */       
/*  111: 134 */       this.metadata.addEntity(entityBinding);
/*  112: 135 */       this.processedEntityNames.add(entityBinding.getEntity().getName());
/*  113:     */       
/*  114: 137 */       processFetchProfiles(entitySource, entityBinding);
/*  115:     */       
/*  116: 139 */       return entityBinding;
/*  117:     */     }
/*  118:     */     finally
/*  119:     */     {
/*  120: 142 */       this.currentBindingContext = null;
/*  121:     */     }
/*  122:     */   }
/*  123:     */   
/*  124:     */   private EntityBinding doCreateEntityBinding(EntitySource entitySource, EntityBinding superEntityBinding)
/*  125:     */   {
/*  126: 147 */     EntityBinding entityBinding = createBasicEntityBinding(entitySource, superEntityBinding);
/*  127:     */     
/*  128: 149 */     bindSecondaryTables(entitySource, entityBinding);
/*  129: 150 */     bindAttributes(entitySource, entityBinding);
/*  130:     */     
/*  131: 152 */     bindTableUniqueConstraints(entitySource, entityBinding);
/*  132:     */     
/*  133: 154 */     return entityBinding;
/*  134:     */   }
/*  135:     */   
/*  136:     */   private EntityBinding createBasicEntityBinding(EntitySource entitySource, EntityBinding superEntityBinding)
/*  137:     */   {
/*  138: 158 */     if (superEntityBinding == null) {
/*  139: 159 */       return makeRootEntityBinding((RootEntitySource)entitySource);
/*  140:     */     }
/*  141: 162 */     switch (1.$SwitchMap$org$hibernate$metamodel$binding$InheritanceType[this.currentInheritanceType.ordinal()])
/*  142:     */     {
/*  143:     */     case 1: 
/*  144: 164 */       return makeDiscriminatedSubclassBinding((SubclassEntitySource)entitySource, superEntityBinding);
/*  145:     */     case 2: 
/*  146: 166 */       return makeJoinedSubclassBinding((SubclassEntitySource)entitySource, superEntityBinding);
/*  147:     */     case 3: 
/*  148: 168 */       return makeUnionedSubclassBinding((SubclassEntitySource)entitySource, superEntityBinding);
/*  149:     */     }
/*  150: 171 */     throw new AssertionFailure("Internal condition failure");
/*  151:     */   }
/*  152:     */   
/*  153:     */   private EntityBinding makeRootEntityBinding(RootEntitySource entitySource)
/*  154:     */   {
/*  155: 177 */     this.currentHierarchyEntityMode = entitySource.getEntityMode();
/*  156:     */     
/*  157: 179 */     EntityBinding entityBinding = buildBasicEntityBinding(entitySource, null);
/*  158:     */     
/*  159: 181 */     bindPrimaryTable(entitySource, entityBinding);
/*  160:     */     
/*  161: 183 */     bindIdentifier(entitySource, entityBinding);
/*  162: 184 */     bindVersion(entityBinding, entitySource);
/*  163: 185 */     bindDiscriminator(entitySource, entityBinding);
/*  164:     */     
/*  165: 187 */     entityBinding.getHierarchyDetails().setCaching(entitySource.getCaching());
/*  166: 188 */     entityBinding.getHierarchyDetails().setExplicitPolymorphism(entitySource.isExplicitPolymorphism());
/*  167: 189 */     entityBinding.getHierarchyDetails().setOptimisticLockStyle(entitySource.getOptimisticLockStyle());
/*  168:     */     
/*  169: 191 */     entityBinding.setMutable(entitySource.isMutable());
/*  170: 192 */     entityBinding.setWhereFilter(entitySource.getWhere());
/*  171: 193 */     entityBinding.setRowId(entitySource.getRowId());
/*  172:     */     
/*  173: 195 */     return entityBinding;
/*  174:     */   }
/*  175:     */   
/*  176:     */   private EntityBinding buildBasicEntityBinding(EntitySource entitySource, EntityBinding superEntityBinding)
/*  177:     */   {
/*  178: 199 */     EntityBinding entityBinding = superEntityBinding == null ? new EntityBinding(this.currentInheritanceType, this.currentHierarchyEntityMode) : new EntityBinding(superEntityBinding);
/*  179:     */     
/*  180:     */ 
/*  181:     */ 
/*  182: 203 */     String entityName = entitySource.getEntityName();
/*  183: 204 */     String className = this.currentHierarchyEntityMode == EntityMode.POJO ? entitySource.getClassName() : null;
/*  184:     */     
/*  185: 206 */     Entity entity = new Entity(entityName, className, this.currentBindingContext.makeClassReference(className), superEntityBinding == null ? null : superEntityBinding.getEntity());
/*  186:     */     
/*  187:     */ 
/*  188:     */ 
/*  189:     */ 
/*  190:     */ 
/*  191: 212 */     entityBinding.setEntity(entity);
/*  192:     */     
/*  193: 214 */     entityBinding.setJpaEntityName(entitySource.getJpaEntityName());
/*  194: 216 */     if (this.currentHierarchyEntityMode == EntityMode.POJO)
/*  195:     */     {
/*  196: 217 */       String proxy = entitySource.getProxy();
/*  197: 218 */       if (proxy != null)
/*  198:     */       {
/*  199: 219 */         entityBinding.setProxyInterfaceType(this.currentBindingContext.makeClassReference(this.currentBindingContext.qualifyClassName(proxy)));
/*  200:     */         
/*  201:     */ 
/*  202:     */ 
/*  203:     */ 
/*  204: 224 */         entityBinding.setLazy(true);
/*  205:     */       }
/*  206: 226 */       else if (entitySource.isLazy())
/*  207:     */       {
/*  208: 227 */         entityBinding.setProxyInterfaceType(entityBinding.getEntity().getClassReferenceUnresolved());
/*  209: 228 */         entityBinding.setLazy(true);
/*  210:     */       }
/*  211:     */     }
/*  212:     */     else
/*  213:     */     {
/*  214: 232 */       entityBinding.setProxyInterfaceType(null);
/*  215: 233 */       entityBinding.setLazy(entitySource.isLazy());
/*  216:     */     }
/*  217: 236 */     String customTuplizerClassName = entitySource.getCustomTuplizerClassName();
/*  218: 237 */     if (customTuplizerClassName != null) {
/*  219: 238 */       entityBinding.setCustomEntityTuplizerClass(this.currentBindingContext.locateClassByName(customTuplizerClassName));
/*  220:     */     }
/*  221: 245 */     String customPersisterClassName = entitySource.getCustomPersisterClassName();
/*  222: 246 */     if (customPersisterClassName != null) {
/*  223: 247 */       entityBinding.setCustomEntityPersisterClass(this.currentBindingContext.locateClassByName(customPersisterClassName));
/*  224:     */     }
/*  225: 254 */     entityBinding.setMetaAttributeContext(buildMetaAttributeContext(entitySource));
/*  226:     */     
/*  227: 256 */     entityBinding.setDynamicUpdate(entitySource.isDynamicUpdate());
/*  228: 257 */     entityBinding.setDynamicInsert(entitySource.isDynamicInsert());
/*  229: 258 */     entityBinding.setBatchSize(entitySource.getBatchSize());
/*  230: 259 */     entityBinding.setSelectBeforeUpdate(entitySource.isSelectBeforeUpdate());
/*  231: 260 */     entityBinding.setAbstract(Boolean.valueOf(entitySource.isAbstract()));
/*  232:     */     
/*  233: 262 */     entityBinding.setCustomLoaderName(entitySource.getCustomLoaderName());
/*  234: 263 */     entityBinding.setCustomInsert(entitySource.getCustomSqlInsert());
/*  235: 264 */     entityBinding.setCustomUpdate(entitySource.getCustomSqlUpdate());
/*  236: 265 */     entityBinding.setCustomDelete(entitySource.getCustomSqlDelete());
/*  237: 267 */     if (entitySource.getSynchronizedTableNames() != null) {
/*  238: 268 */       entityBinding.addSynchronizedTableNames(entitySource.getSynchronizedTableNames());
/*  239:     */     }
/*  240: 271 */     entityBinding.setJpaCallbackClasses(entitySource.getJpaCallbackClasses());
/*  241:     */     
/*  242: 273 */     return entityBinding;
/*  243:     */   }
/*  244:     */   
/*  245:     */   private EntityBinding makeDiscriminatedSubclassBinding(SubclassEntitySource entitySource, EntityBinding superEntityBinding)
/*  246:     */   {
/*  247: 277 */     EntityBinding entityBinding = buildBasicEntityBinding(entitySource, superEntityBinding);
/*  248:     */     
/*  249: 279 */     entityBinding.setPrimaryTable(superEntityBinding.getPrimaryTable());
/*  250: 280 */     entityBinding.setPrimaryTableName(superEntityBinding.getPrimaryTableName());
/*  251: 281 */     bindDiscriminatorValue(entitySource, entityBinding);
/*  252:     */     
/*  253: 283 */     return entityBinding;
/*  254:     */   }
/*  255:     */   
/*  256:     */   private EntityBinding makeJoinedSubclassBinding(SubclassEntitySource entitySource, EntityBinding superEntityBinding)
/*  257:     */   {
/*  258: 287 */     EntityBinding entityBinding = buildBasicEntityBinding(entitySource, superEntityBinding);
/*  259:     */     
/*  260: 289 */     bindPrimaryTable(entitySource, entityBinding);
/*  261:     */     
/*  262:     */ 
/*  263:     */ 
/*  264: 293 */     return entityBinding;
/*  265:     */   }
/*  266:     */   
/*  267:     */   private EntityBinding makeUnionedSubclassBinding(SubclassEntitySource entitySource, EntityBinding superEntityBinding)
/*  268:     */   {
/*  269: 297 */     EntityBinding entityBinding = buildBasicEntityBinding(entitySource, superEntityBinding);
/*  270:     */     
/*  271: 299 */     bindPrimaryTable(entitySource, entityBinding);
/*  272:     */     
/*  273:     */ 
/*  274:     */ 
/*  275: 303 */     return entityBinding;
/*  276:     */   }
/*  277:     */   
/*  278:     */   private void bindIdentifier(RootEntitySource entitySource, EntityBinding entityBinding)
/*  279:     */   {
/*  280: 309 */     if (entitySource.getIdentifierSource() == null) {
/*  281: 310 */       throw new AssertionFailure("Expecting identifier information on root entity descriptor");
/*  282:     */     }
/*  283: 312 */     switch (1.$SwitchMap$org$hibernate$metamodel$source$binder$IdentifierSource$Nature[entitySource.getIdentifierSource().getNature().ordinal()])
/*  284:     */     {
/*  285:     */     case 1: 
/*  286: 314 */       bindSimpleIdentifier((SimpleIdentifierSource)entitySource.getIdentifierSource(), entityBinding);
/*  287: 315 */       break;
/*  288:     */     case 2: 
/*  289:     */       break;
/*  290:     */     }
/*  291:     */   }
/*  292:     */   
/*  293:     */   private void bindSimpleIdentifier(SimpleIdentifierSource identifierSource, EntityBinding entityBinding)
/*  294:     */   {
/*  295: 331 */     BasicAttributeBinding idAttributeBinding = doBasicSingularAttributeBindingCreation(identifierSource.getIdentifierAttributeSource(), entityBinding);
/*  296:     */     
/*  297:     */ 
/*  298:     */ 
/*  299: 335 */     entityBinding.getHierarchyDetails().getEntityIdentifier().setValueBinding(idAttributeBinding);
/*  300: 336 */     IdGenerator generator = identifierSource.getIdentifierGeneratorDescriptor();
/*  301: 337 */     if (generator == null)
/*  302:     */     {
/*  303: 338 */       Map<String, String> params = new HashMap();
/*  304: 339 */       params.put("entity_name", entityBinding.getEntity().getName());
/*  305: 340 */       generator = new IdGenerator("default_assign_identity_generator", "assigned", params);
/*  306:     */     }
/*  307: 342 */     entityBinding.getHierarchyDetails().getEntityIdentifier().setIdGenerator(generator);
/*  308:     */     
/*  309:     */ 
/*  310:     */ 
/*  311: 346 */     Value relationalValue = idAttributeBinding.getValue();
/*  312: 348 */     if (SimpleValue.class.isInstance(relationalValue))
/*  313:     */     {
/*  314: 349 */       if (!Column.class.isInstance(relationalValue)) {
/*  315: 351 */         throw new AssertionFailure("Simple-id was not a column.");
/*  316:     */       }
/*  317: 353 */       entityBinding.getPrimaryTable().getPrimaryKey().addColumn((Column)Column.class.cast(relationalValue));
/*  318:     */     }
/*  319:     */     else
/*  320:     */     {
/*  321: 356 */       for (SimpleValue subValue : ((Tuple)relationalValue).values()) {
/*  322: 357 */         if (Column.class.isInstance(subValue)) {
/*  323: 358 */           entityBinding.getPrimaryTable().getPrimaryKey().addColumn((Column)Column.class.cast(subValue));
/*  324:     */         }
/*  325:     */       }
/*  326:     */     }
/*  327:     */   }
/*  328:     */   
/*  329:     */   private void bindVersion(EntityBinding entityBinding, RootEntitySource entitySource)
/*  330:     */   {
/*  331: 365 */     SingularAttributeSource versioningAttributeSource = entitySource.getVersioningAttributeSource();
/*  332: 366 */     if (versioningAttributeSource == null) {
/*  333: 367 */       return;
/*  334:     */     }
/*  335: 370 */     BasicAttributeBinding attributeBinding = doBasicSingularAttributeBindingCreation(versioningAttributeSource, entityBinding);
/*  336:     */     
/*  337:     */ 
/*  338: 373 */     entityBinding.getHierarchyDetails().setVersioningAttributeBinding(attributeBinding);
/*  339:     */   }
/*  340:     */   
/*  341:     */   private void bindDiscriminator(RootEntitySource entitySource, EntityBinding entityBinding)
/*  342:     */   {
/*  343: 377 */     DiscriminatorSource discriminatorSource = entitySource.getDiscriminatorSource();
/*  344: 378 */     if (discriminatorSource == null) {
/*  345: 379 */       return;
/*  346:     */     }
/*  347: 382 */     EntityDiscriminator discriminator = new EntityDiscriminator();
/*  348: 383 */     SimpleValue relationalValue = makeSimpleValue(entityBinding, discriminatorSource.getDiscriminatorRelationalValueSource());
/*  349:     */     
/*  350:     */ 
/*  351:     */ 
/*  352: 387 */     discriminator.setBoundValue(relationalValue);
/*  353:     */     
/*  354: 389 */     discriminator.getExplicitHibernateTypeDescriptor().setExplicitTypeName(discriminatorSource.getExplicitHibernateTypeName() != null ? discriminatorSource.getExplicitHibernateTypeName() : "string");
/*  355:     */     
/*  356:     */ 
/*  357:     */ 
/*  358:     */ 
/*  359:     */ 
/*  360: 395 */     discriminator.setInserted(discriminatorSource.isInserted());
/*  361: 396 */     discriminator.setForced(discriminatorSource.isForced());
/*  362:     */     
/*  363: 398 */     entityBinding.getHierarchyDetails().setEntityDiscriminator(discriminator);
/*  364: 399 */     entityBinding.setDiscriminatorMatchValue(entitySource.getDiscriminatorMatchValue());
/*  365:     */   }
/*  366:     */   
/*  367:     */   private void bindDiscriminatorValue(SubclassEntitySource entitySource, EntityBinding entityBinding)
/*  368:     */   {
/*  369: 403 */     String discriminatorValue = entitySource.getDiscriminatorMatchValue();
/*  370: 404 */     if (discriminatorValue == null) {
/*  371: 405 */       return;
/*  372:     */     }
/*  373: 407 */     entityBinding.setDiscriminatorMatchValue(discriminatorValue);
/*  374:     */   }
/*  375:     */   
/*  376:     */   private void bindAttributes(AttributeSourceContainer attributeSourceContainer, AttributeBindingContainer attributeBindingContainer)
/*  377:     */   {
/*  378: 415 */     for (AttributeSource attributeSource : attributeSourceContainer.attributeSources()) {
/*  379: 416 */       if (attributeSource.isSingular())
/*  380:     */       {
/*  381: 417 */         SingularAttributeSource singularAttributeSource = (SingularAttributeSource)attributeSource;
/*  382: 418 */         if (singularAttributeSource.getNature() == SingularAttributeNature.COMPONENT) {
/*  383: 419 */           bindComponent((ComponentAttributeSource)singularAttributeSource, attributeBindingContainer);
/*  384:     */         } else {
/*  385: 422 */           doBasicSingularAttributeBindingCreation(singularAttributeSource, attributeBindingContainer);
/*  386:     */         }
/*  387:     */       }
/*  388:     */       else
/*  389:     */       {
/*  390: 426 */         bindPersistentCollection((PluralAttributeSource)attributeSource, attributeBindingContainer);
/*  391:     */       }
/*  392:     */     }
/*  393:     */   }
/*  394:     */   
/*  395:     */   private void bindComponent(ComponentAttributeSource attributeSource, AttributeBindingContainer container)
/*  396:     */   {
/*  397: 432 */     String attributeName = attributeSource.getName();
/*  398: 433 */     SingularAttribute attribute = container.getAttributeContainer().locateComponentAttribute(attributeName);
/*  399: 434 */     if (attribute == null)
/*  400:     */     {
/*  401: 435 */       Component component = new Component(attributeSource.getPath(), attributeSource.getClassName(), attributeSource.getClassReference(), null);
/*  402:     */       
/*  403:     */ 
/*  404:     */ 
/*  405:     */ 
/*  406:     */ 
/*  407: 441 */       attribute = container.getAttributeContainer().createComponentAttribute(attributeName, component);
/*  408:     */     }
/*  409: 443 */     ComponentAttributeBinding componentAttributeBinding = container.makeComponentAttributeBinding(attribute);
/*  410: 445 */     if (StringHelper.isNotEmpty(attributeSource.getParentReferenceAttributeName()))
/*  411:     */     {
/*  412: 446 */       SingularAttribute parentReferenceAttribute = componentAttributeBinding.getComponent().createSingularAttribute(attributeSource.getParentReferenceAttributeName());
/*  413:     */       
/*  414:     */ 
/*  415: 449 */       componentAttributeBinding.setParentReference(parentReferenceAttribute);
/*  416:     */     }
/*  417: 452 */     componentAttributeBinding.setMetaAttributeContext(buildMetaAttributeContext(attributeSource.metaAttributes(), container.getMetaAttributeContext()));
/*  418:     */     
/*  419:     */ 
/*  420:     */ 
/*  421: 456 */     bindAttributes(attributeSource, componentAttributeBinding);
/*  422:     */   }
/*  423:     */   
/*  424:     */   private void bindPersistentCollection(PluralAttributeSource attributeSource, AttributeBindingContainer attributeBindingContainer)
/*  425:     */   {
/*  426: 460 */     PluralAttribute existingAttribute = attributeBindingContainer.getAttributeContainer().locatePluralAttribute(attributeSource.getName());
/*  427:     */     AbstractPluralAttributeBinding pluralAttributeBinding;
/*  428: 464 */     if (attributeSource.getPluralAttributeNature() == PluralAttributeNature.BAG)
/*  429:     */     {
/*  430: 465 */       PluralAttribute attribute = existingAttribute != null ? existingAttribute : attributeBindingContainer.getAttributeContainer().createBag(attributeSource.getName());
/*  431:     */       
/*  432:     */ 
/*  433: 468 */       pluralAttributeBinding = attributeBindingContainer.makeBagAttributeBinding(attribute, convert(attributeSource.getElementSource().getNature()));
/*  434:     */     }
/*  435:     */     else
/*  436:     */     {
/*  437:     */       AbstractPluralAttributeBinding pluralAttributeBinding;
/*  438: 473 */       if (attributeSource.getPluralAttributeNature() == PluralAttributeNature.SET)
/*  439:     */       {
/*  440: 474 */         PluralAttribute attribute = existingAttribute != null ? existingAttribute : attributeBindingContainer.getAttributeContainer().createSet(attributeSource.getName());
/*  441:     */         
/*  442:     */ 
/*  443: 477 */         pluralAttributeBinding = attributeBindingContainer.makeSetAttributeBinding(attribute, convert(attributeSource.getElementSource().getNature()));
/*  444:     */       }
/*  445:     */       else
/*  446:     */       {
/*  447: 484 */         throw new NotYetImplementedException("Collections other than bag and set not yet implemented :(");
/*  448:     */       }
/*  449:     */     }
/*  450:     */     AbstractPluralAttributeBinding pluralAttributeBinding;
/*  451: 487 */     doBasicPluralAttributeBinding(attributeSource, pluralAttributeBinding);
/*  452:     */     
/*  453: 489 */     bindCollectionTable(attributeSource, pluralAttributeBinding);
/*  454: 490 */     bindSortingAndOrdering(attributeSource, pluralAttributeBinding);
/*  455:     */     
/*  456: 492 */     bindCollectionKey(attributeSource, pluralAttributeBinding);
/*  457: 493 */     bindCollectionElement(attributeSource, pluralAttributeBinding);
/*  458: 494 */     bindCollectionIndex(attributeSource, pluralAttributeBinding);
/*  459:     */     
/*  460: 496 */     this.metadata.addCollection(pluralAttributeBinding);
/*  461:     */   }
/*  462:     */   
/*  463:     */   private void doBasicPluralAttributeBinding(PluralAttributeSource source, AbstractPluralAttributeBinding binding)
/*  464:     */   {
/*  465: 500 */     binding.setFetchTiming(source.getFetchTiming());
/*  466: 501 */     binding.setFetchStyle(source.getFetchStyle());
/*  467: 502 */     binding.setCascadeStyles(source.getCascadeStyles());
/*  468:     */     
/*  469: 504 */     binding.setCaching(source.getCaching());
/*  470:     */     
/*  471: 506 */     binding.getHibernateTypeDescriptor().setJavaTypeName(source.getPluralAttributeNature().reportedJavaType().getName());
/*  472:     */     
/*  473:     */ 
/*  474: 509 */     binding.getHibernateTypeDescriptor().setExplicitTypeName(source.getTypeInformation().getName());
/*  475: 510 */     binding.getHibernateTypeDescriptor().getTypeParameters().putAll(source.getTypeInformation().getParameters());
/*  476: 512 */     if (StringHelper.isNotEmpty(source.getCustomPersisterClassName())) {
/*  477: 513 */       binding.setCollectionPersisterClass(this.currentBindingContext.locateClassByName(source.getCustomPersisterClassName()));
/*  478:     */     }
/*  479: 518 */     if (source.getCustomPersisterClassName() != null) {
/*  480: 519 */       binding.setCollectionPersisterClass(this.metadata.locateClassByName(source.getCustomPersisterClassName()));
/*  481:     */     }
/*  482: 524 */     binding.setCustomLoaderName(source.getCustomLoaderName());
/*  483: 525 */     binding.setCustomSqlInsert(source.getCustomSqlInsert());
/*  484: 526 */     binding.setCustomSqlUpdate(source.getCustomSqlUpdate());
/*  485: 527 */     binding.setCustomSqlDelete(source.getCustomSqlDelete());
/*  486: 528 */     binding.setCustomSqlDeleteAll(source.getCustomSqlDeleteAll());
/*  487:     */     
/*  488: 530 */     binding.setMetaAttributeContext(buildMetaAttributeContext(source.metaAttributes(), binding.getContainer().getMetaAttributeContext()));
/*  489:     */     
/*  490:     */ 
/*  491:     */ 
/*  492:     */ 
/*  493:     */ 
/*  494:     */ 
/*  495: 537 */     doBasicAttributeBinding(source, binding);
/*  496:     */   }
/*  497:     */   
/*  498:     */   private CollectionLaziness interpretLaziness(String laziness)
/*  499:     */   {
/*  500: 541 */     if (laziness == null) {
/*  501: 542 */       laziness = Boolean.toString(this.metadata.getMappingDefaults().areAssociationsLazy());
/*  502:     */     }
/*  503: 545 */     if ("extra".equals(laziness)) {
/*  504: 546 */       return CollectionLaziness.EXTRA;
/*  505:     */     }
/*  506: 548 */     if ("false".equals(laziness)) {
/*  507: 549 */       return CollectionLaziness.NOT;
/*  508:     */     }
/*  509: 551 */     if ("true".equals(laziness)) {
/*  510: 552 */       return CollectionLaziness.LAZY;
/*  511:     */     }
/*  512: 555 */     throw new MappingException(String.format("Unexpected collection laziness value %s", new Object[] { laziness }), this.currentBindingContext.getOrigin());
/*  513:     */   }
/*  514:     */   
/*  515:     */   private void bindCollectionTable(PluralAttributeSource attributeSource, AbstractPluralAttributeBinding pluralAttributeBinding)
/*  516:     */   {
/*  517: 564 */     if (attributeSource.getElementSource().getNature() == PluralAttributeElementNature.ONE_TO_MANY) {
/*  518: 565 */       return;
/*  519:     */     }
/*  520: 568 */     Schema.Name schemaName = Helper.determineDatabaseSchemaName(attributeSource.getExplicitSchemaName(), attributeSource.getExplicitCatalogName(), this.currentBindingContext);
/*  521:     */     
/*  522:     */ 
/*  523:     */ 
/*  524:     */ 
/*  525: 573 */     Schema schema = this.metadata.getDatabase().locateSchema(schemaName);
/*  526:     */     
/*  527: 575 */     String tableName = attributeSource.getExplicitCollectionTableName();
/*  528: 576 */     if (StringHelper.isNotEmpty(tableName))
/*  529:     */     {
/*  530: 577 */       Identifier tableIdentifier = Identifier.toIdentifier(this.currentBindingContext.getNamingStrategy().tableName(tableName));
/*  531:     */       
/*  532:     */ 
/*  533: 580 */       Table collectionTable = schema.locateTable(tableIdentifier);
/*  534: 581 */       if (collectionTable == null) {
/*  535: 582 */         collectionTable = schema.createTable(tableIdentifier);
/*  536:     */       }
/*  537: 584 */       pluralAttributeBinding.setCollectionTable(collectionTable);
/*  538:     */     }
/*  539:     */     else
/*  540:     */     {
/*  541: 588 */       EntityBinding owner = pluralAttributeBinding.getContainer().seekEntityBinding();
/*  542: 589 */       String ownerTableLogicalName = Table.class.isInstance(owner.getPrimaryTable()) ? ((Table)Table.class.cast(owner.getPrimaryTable())).getTableName().getName() : null;
/*  543:     */       
/*  544:     */ 
/*  545: 592 */       String collectionTableName = this.currentBindingContext.getNamingStrategy().collectionTableName(owner.getEntity().getName(), ownerTableLogicalName, null, null, pluralAttributeBinding.getContainer().getPathBase() + '.' + attributeSource.getName());
/*  546:     */       
/*  547:     */ 
/*  548:     */ 
/*  549:     */ 
/*  550:     */ 
/*  551:     */ 
/*  552: 599 */       collectionTableName = quoteIdentifier(collectionTableName);
/*  553: 600 */       pluralAttributeBinding.setCollectionTable(schema.locateOrCreateTable(Identifier.toIdentifier(collectionTableName)));
/*  554:     */     }
/*  555: 609 */     if (StringHelper.isNotEmpty(attributeSource.getCollectionTableComment())) {
/*  556: 610 */       pluralAttributeBinding.getCollectionTable().addComment(attributeSource.getCollectionTableComment());
/*  557:     */     }
/*  558: 613 */     if (StringHelper.isNotEmpty(attributeSource.getCollectionTableCheck())) {
/*  559: 614 */       pluralAttributeBinding.getCollectionTable().addCheckConstraint(attributeSource.getCollectionTableCheck());
/*  560:     */     }
/*  561: 617 */     pluralAttributeBinding.setWhere(attributeSource.getWhere());
/*  562:     */   }
/*  563:     */   
/*  564:     */   private void bindCollectionKey(PluralAttributeSource attributeSource, AbstractPluralAttributeBinding pluralAttributeBinding)
/*  565:     */   {
/*  566: 623 */     pluralAttributeBinding.getCollectionKey().prepareForeignKey(attributeSource.getKeySource().getExplicitForeignKeyName(), null);
/*  567:     */     
/*  568:     */ 
/*  569:     */ 
/*  570: 627 */     pluralAttributeBinding.getCollectionKey().getForeignKey().setDeleteRule(attributeSource.getKeySource().getOnDeleteAction());
/*  571:     */   }
/*  572:     */   
/*  573:     */   private void bindCollectionElement(PluralAttributeSource attributeSource, AbstractPluralAttributeBinding pluralAttributeBinding)
/*  574:     */   {
/*  575: 636 */     PluralAttributeElementSource elementSource = attributeSource.getElementSource();
/*  576: 637 */     if (elementSource.getNature() == PluralAttributeElementNature.BASIC)
/*  577:     */     {
/*  578: 638 */       BasicPluralAttributeElementSource basicElementSource = (BasicPluralAttributeElementSource)elementSource;
/*  579: 639 */       BasicCollectionElement basicCollectionElement = (BasicCollectionElement)pluralAttributeBinding.getCollectionElement();
/*  580: 640 */       resolveTypeInformation(basicElementSource.getExplicitHibernateTypeSource(), pluralAttributeBinding.getAttribute(), basicCollectionElement);
/*  581:     */       
/*  582:     */ 
/*  583:     */ 
/*  584:     */ 
/*  585:     */ 
/*  586: 646 */       return;
/*  587:     */     }
/*  588: 649 */     throw new NotYetImplementedException(String.format("Support for collection elements of type %s not yet implemented", new Object[] { elementSource.getNature() }));
/*  589:     */   }
/*  590:     */   
/*  591:     */   private void bindCollectionIndex(PluralAttributeSource attributeSource, AbstractPluralAttributeBinding pluralAttributeBinding)
/*  592:     */   {
/*  593: 660 */     if ((attributeSource.getPluralAttributeNature() != PluralAttributeNature.LIST) && (attributeSource.getPluralAttributeNature() != PluralAttributeNature.MAP)) {
/*  594: 662 */       return;
/*  595:     */     }
/*  596: 666 */     throw new NotYetImplementedException();
/*  597:     */   }
/*  598:     */   
/*  599:     */   private void bindSortingAndOrdering(PluralAttributeSource attributeSource, AbstractPluralAttributeBinding pluralAttributeBinding)
/*  600:     */   {
/*  601: 672 */     if (Sortable.class.isInstance(attributeSource))
/*  602:     */     {
/*  603: 673 */       Sortable sortable = (Sortable)Sortable.class.cast(attributeSource);
/*  604: 674 */       if (sortable.isSorted()) {
/*  605: 678 */         return;
/*  606:     */       }
/*  607:     */     }
/*  608: 682 */     if (Orderable.class.isInstance(attributeSource))
/*  609:     */     {
/*  610: 683 */       Orderable orderable = (Orderable)Orderable.class.cast(attributeSource);
/*  611: 684 */       if (!orderable.isOrdered()) {}
/*  612:     */     }
/*  613:     */   }
/*  614:     */   
/*  615:     */   private void doBasicAttributeBinding(AttributeSource attributeSource, AttributeBinding attributeBinding)
/*  616:     */   {
/*  617: 691 */     attributeBinding.setPropertyAccessorName(attributeSource.getPropertyAccessorName());
/*  618: 692 */     attributeBinding.setIncludedInOptimisticLocking(attributeSource.isIncludedInOptimisticLocking());
/*  619:     */   }
/*  620:     */   
/*  621:     */   private CollectionElementNature convert(PluralAttributeElementNature pluralAttributeElementNature)
/*  622:     */   {
/*  623: 696 */     return CollectionElementNature.valueOf(pluralAttributeElementNature.name());
/*  624:     */   }
/*  625:     */   
/*  626:     */   private BasicAttributeBinding doBasicSingularAttributeBindingCreation(SingularAttributeSource attributeSource, AttributeBindingContainer attributeBindingContainer)
/*  627:     */   {
/*  628: 702 */     SingularAttribute existingAttribute = attributeBindingContainer.getAttributeContainer().locateSingularAttribute(attributeSource.getName());
/*  629:     */     SingularAttribute attribute;
/*  630:     */     SingularAttribute attribute;
/*  631: 705 */     if (existingAttribute != null)
/*  632:     */     {
/*  633: 706 */       attribute = existingAttribute;
/*  634:     */     }
/*  635:     */     else
/*  636:     */     {
/*  637:     */       SingularAttribute attribute;
/*  638: 708 */       if (attributeSource.isVirtualAttribute()) {
/*  639: 709 */         attribute = attributeBindingContainer.getAttributeContainer().createVirtualSingularAttribute(attributeSource.getName());
/*  640:     */       } else {
/*  641: 714 */         attribute = attributeBindingContainer.getAttributeContainer().createSingularAttribute(attributeSource.getName());
/*  642:     */       }
/*  643:     */     }
/*  644: 719 */     if (attributeSource.getNature() == SingularAttributeNature.BASIC)
/*  645:     */     {
/*  646: 720 */       BasicAttributeBinding attributeBinding = attributeBindingContainer.makeBasicAttributeBinding(attribute);
/*  647: 721 */       resolveTypeInformation(attributeSource.getTypeInformation(), attributeBinding);
/*  648:     */     }
/*  649: 723 */     else if (attributeSource.getNature() == SingularAttributeNature.MANY_TO_ONE)
/*  650:     */     {
/*  651: 724 */       BasicAttributeBinding attributeBinding = attributeBindingContainer.makeManyToOneAttributeBinding(attribute);
/*  652: 725 */       resolveTypeInformation(attributeSource.getTypeInformation(), attributeBinding);
/*  653: 726 */       resolveToOneInformation((ToOneAttributeSource)attributeSource, (ManyToOneAttributeBinding)attributeBinding);
/*  654:     */     }
/*  655:     */     else
/*  656:     */     {
/*  657: 732 */       throw new NotYetImplementedException();
/*  658:     */     }
/*  659:     */     BasicAttributeBinding attributeBinding;
/*  660: 735 */     attributeBinding.setGeneration(attributeSource.getGeneration());
/*  661: 736 */     attributeBinding.setLazy(attributeSource.isLazy());
/*  662: 737 */     attributeBinding.setIncludedInOptimisticLocking(attributeSource.isIncludedInOptimisticLocking());
/*  663:     */     
/*  664: 739 */     attributeBinding.setPropertyAccessorName(Helper.getPropertyAccessorName(attributeSource.getPropertyAccessorName(), false, this.currentBindingContext.getMappingDefaults().getPropertyAccessorName()));
/*  665:     */     
/*  666:     */ 
/*  667:     */ 
/*  668:     */ 
/*  669:     */ 
/*  670:     */ 
/*  671:     */ 
/*  672: 747 */     bindRelationalValues(attributeSource, attributeBinding);
/*  673:     */     
/*  674: 749 */     attributeBinding.setMetaAttributeContext(buildMetaAttributeContext(attributeSource.metaAttributes(), attributeBindingContainer.getMetaAttributeContext()));
/*  675:     */     
/*  676:     */ 
/*  677:     */ 
/*  678:     */ 
/*  679:     */ 
/*  680:     */ 
/*  681: 756 */     return attributeBinding;
/*  682:     */   }
/*  683:     */   
/*  684:     */   private void resolveTypeInformation(ExplicitHibernateTypeSource typeSource, BasicAttributeBinding attributeBinding)
/*  685:     */   {
/*  686: 760 */     Class<?> attributeJavaType = determineJavaType(attributeBinding.getAttribute());
/*  687: 761 */     if (attributeJavaType != null) {
/*  688: 762 */       attributeBinding.getAttribute().resolveType(this.currentBindingContext.makeJavaType(attributeJavaType.getName()));
/*  689:     */     }
/*  690: 766 */     resolveTypeInformation(typeSource, attributeBinding.getHibernateTypeDescriptor(), attributeJavaType);
/*  691:     */   }
/*  692:     */   
/*  693:     */   private void resolveTypeInformation(ExplicitHibernateTypeSource typeSource, PluralAttribute attribute, BasicCollectionElement collectionElement)
/*  694:     */   {
/*  695: 773 */     Class<?> attributeJavaType = determineJavaType(attribute);
/*  696: 774 */     resolveTypeInformation(typeSource, collectionElement.getHibernateTypeDescriptor(), attributeJavaType);
/*  697:     */   }
/*  698:     */   
/*  699:     */   private void resolveTypeInformation(ExplicitHibernateTypeSource typeSource, HibernateTypeDescriptor hibernateTypeDescriptor, Class<?> discoveredJavaType)
/*  700:     */   {
/*  701: 781 */     if (discoveredJavaType != null) {
/*  702: 782 */       hibernateTypeDescriptor.setJavaTypeName(discoveredJavaType.getName());
/*  703:     */     }
/*  704: 785 */     String explicitTypeName = typeSource.getName();
/*  705: 786 */     if (explicitTypeName != null)
/*  706:     */     {
/*  707: 787 */       TypeDef typeDef = this.currentBindingContext.getMetadataImplementor().getTypeDefinition(explicitTypeName);
/*  708: 789 */       if (typeDef != null)
/*  709:     */       {
/*  710: 790 */         hibernateTypeDescriptor.setExplicitTypeName(typeDef.getTypeClass());
/*  711: 791 */         hibernateTypeDescriptor.getTypeParameters().putAll(typeDef.getParameters());
/*  712:     */       }
/*  713:     */       else
/*  714:     */       {
/*  715: 794 */         hibernateTypeDescriptor.setExplicitTypeName(explicitTypeName);
/*  716:     */       }
/*  717: 796 */       Map<String, String> parameters = typeSource.getParameters();
/*  718: 797 */       if (parameters != null) {
/*  719: 798 */         hibernateTypeDescriptor.getTypeParameters().putAll(parameters);
/*  720:     */       }
/*  721:     */     }
/*  722: 802 */     else if (discoveredJavaType != null) {}
/*  723:     */   }
/*  724:     */   
/*  725:     */   private Class<?> determineJavaType(SingularAttribute attribute)
/*  726:     */   {
/*  727:     */     try
/*  728:     */     {
/*  729: 816 */       Class<?> ownerClass = attribute.getAttributeContainer().getClassReference();
/*  730: 817 */       AttributeJavaTypeDeterminerDelegate delegate = new AttributeJavaTypeDeterminerDelegate(attribute.getName(), null);
/*  731: 818 */       BeanInfoHelper.visitBeanInfo(ownerClass, delegate);
/*  732: 819 */       return delegate.javaType;
/*  733:     */     }
/*  734:     */     catch (Exception ignore) {}
/*  735: 824 */     return null;
/*  736:     */   }
/*  737:     */   
/*  738:     */   private Class<?> determineJavaType(PluralAttribute attribute)
/*  739:     */   {
/*  740:     */     try
/*  741:     */     {
/*  742: 829 */       Class<?> ownerClass = attribute.getAttributeContainer().getClassReference();
/*  743: 830 */       PluralAttributeJavaTypeDeterminerDelegate delegate = new PluralAttributeJavaTypeDeterminerDelegate(ownerClass, attribute.getName(), null);
/*  744:     */       
/*  745:     */ 
/*  746:     */ 
/*  747: 834 */       BeanInfoHelper.visitBeanInfo(ownerClass, delegate);
/*  748: 835 */       return delegate.javaType;
/*  749:     */     }
/*  750:     */     catch (Exception ignore) {}
/*  751: 840 */     return null;
/*  752:     */   }
/*  753:     */   
/*  754:     */   private class PluralAttributeJavaTypeDeterminerDelegate
/*  755:     */     implements BeanInfoHelper.BeanInfoDelegate
/*  756:     */   {
/*  757:     */     private final Class<?> ownerClass;
/*  758:     */     private final String attributeName;
/*  759: 847 */     private Class<?> javaType = null;
/*  760:     */     
/*  761:     */     private PluralAttributeJavaTypeDeterminerDelegate(String ownerClass)
/*  762:     */     {
/*  763: 850 */       this.ownerClass = ownerClass;
/*  764: 851 */       this.attributeName = attributeName;
/*  765:     */     }
/*  766:     */     
/*  767:     */     public void processBeanInfo(BeanInfo beanInfo)
/*  768:     */       throws Exception
/*  769:     */     {
/*  770: 856 */       for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
/*  771: 857 */         if (propertyDescriptor.getName().equals(this.attributeName))
/*  772:     */         {
/*  773: 858 */           this.javaType = extractCollectionComponentType(beanInfo, propertyDescriptor);
/*  774: 859 */           break;
/*  775:     */         }
/*  776:     */       }
/*  777:     */     }
/*  778:     */     
/*  779:     */     private Class<?> extractCollectionComponentType(BeanInfo beanInfo, PropertyDescriptor propertyDescriptor)
/*  780:     */     {
/*  781:     */       java.lang.reflect.Type collectionAttributeType;
/*  782:     */       java.lang.reflect.Type collectionAttributeType;
/*  783: 867 */       if (propertyDescriptor.getReadMethod() != null)
/*  784:     */       {
/*  785: 868 */         collectionAttributeType = propertyDescriptor.getReadMethod().getGenericReturnType();
/*  786:     */       }
/*  787:     */       else
/*  788:     */       {
/*  789:     */         java.lang.reflect.Type collectionAttributeType;
/*  790: 870 */         if (propertyDescriptor.getWriteMethod() != null) {
/*  791: 871 */           collectionAttributeType = propertyDescriptor.getWriteMethod().getGenericParameterTypes()[0];
/*  792:     */         } else {
/*  793:     */           try
/*  794:     */           {
/*  795: 876 */             collectionAttributeType = this.ownerClass.getField(propertyDescriptor.getName()).getGenericType();
/*  796:     */           }
/*  797:     */           catch (Exception e)
/*  798:     */           {
/*  799: 879 */             return null;
/*  800:     */           }
/*  801:     */         }
/*  802:     */       }
/*  803: 883 */       if (ParameterizedType.class.isInstance(collectionAttributeType))
/*  804:     */       {
/*  805: 884 */         java.lang.reflect.Type[] types = ((ParameterizedType)collectionAttributeType).getActualTypeArguments();
/*  806: 885 */         if (types == null) {
/*  807: 886 */           return null;
/*  808:     */         }
/*  809: 888 */         if (types.length == 1) {
/*  810: 889 */           return (Class)types[0];
/*  811:     */         }
/*  812: 891 */         if (types.length == 2) {
/*  813: 893 */           return (Class)types[1];
/*  814:     */         }
/*  815:     */       }
/*  816: 896 */       return null;
/*  817:     */     }
/*  818:     */   }
/*  819:     */   
/*  820:     */   private void resolveToOneInformation(ToOneAttributeSource attributeSource, ManyToOneAttributeBinding attributeBinding)
/*  821:     */   {
/*  822: 901 */     String referencedEntityName = attributeSource.getReferencedEntityName() != null ? attributeSource.getReferencedEntityName() : attributeBinding.getAttribute().getSingularAttributeType().getClassName();
/*  823:     */     
/*  824:     */ 
/*  825: 904 */     attributeBinding.setReferencedEntityName(referencedEntityName);
/*  826:     */     
/*  827: 906 */     attributeBinding.setReferencedAttributeName(attributeSource.getReferencedEntityAttributeName());
/*  828:     */     
/*  829: 908 */     attributeBinding.setCascadeStyles(attributeSource.getCascadeStyles());
/*  830: 909 */     attributeBinding.setFetchTiming(attributeSource.getFetchTiming());
/*  831: 910 */     attributeBinding.setFetchStyle(attributeSource.getFetchStyle());
/*  832:     */   }
/*  833:     */   
/*  834:     */   private MetaAttributeContext buildMetaAttributeContext(EntitySource entitySource)
/*  835:     */   {
/*  836: 914 */     return buildMetaAttributeContext(entitySource.metaAttributes(), true, this.currentBindingContext.getMetadataImplementor().getGlobalMetaAttributeContext());
/*  837:     */   }
/*  838:     */   
/*  839:     */   private static MetaAttributeContext buildMetaAttributeContext(Iterable<MetaAttributeSource> metaAttributeSources, MetaAttributeContext parentContext)
/*  840:     */   {
/*  841: 924 */     return buildMetaAttributeContext(metaAttributeSources, false, parentContext);
/*  842:     */   }
/*  843:     */   
/*  844:     */   private static MetaAttributeContext buildMetaAttributeContext(Iterable<MetaAttributeSource> metaAttributeSources, boolean onlyInheritable, MetaAttributeContext parentContext)
/*  845:     */   {
/*  846: 931 */     MetaAttributeContext subContext = new MetaAttributeContext(parentContext);
/*  847: 933 */     for (MetaAttributeSource metaAttributeSource : metaAttributeSources) {
/*  848: 934 */       if (!(onlyInheritable & !metaAttributeSource.isInheritable()))
/*  849:     */       {
/*  850: 938 */         String name = metaAttributeSource.getName();
/*  851: 939 */         MetaAttribute inheritedMetaAttribute = parentContext.getMetaAttribute(name);
/*  852: 940 */         MetaAttribute metaAttribute = subContext.getLocalMetaAttribute(name);
/*  853: 941 */         if ((metaAttribute == null) || (metaAttribute == inheritedMetaAttribute))
/*  854:     */         {
/*  855: 942 */           metaAttribute = new MetaAttribute(name);
/*  856: 943 */           subContext.add(metaAttribute);
/*  857:     */         }
/*  858: 945 */         metaAttribute.addValue(metaAttributeSource.getValue());
/*  859:     */       }
/*  860:     */     }
/*  861: 948 */     return subContext;
/*  862:     */   }
/*  863:     */   
/*  864:     */   private void bindPrimaryTable(EntitySource entitySource, EntityBinding entityBinding)
/*  865:     */   {
/*  866: 954 */     TableSource tableSource = entitySource.getPrimaryTable();
/*  867: 955 */     Table table = createTable(entityBinding, tableSource);
/*  868: 956 */     entityBinding.setPrimaryTable(table);
/*  869: 957 */     entityBinding.setPrimaryTableName(table.getTableName().getName());
/*  870:     */   }
/*  871:     */   
/*  872:     */   private void bindSecondaryTables(EntitySource entitySource, EntityBinding entityBinding)
/*  873:     */   {
/*  874: 961 */     for (TableSource secondaryTableSource : entitySource.getSecondaryTables())
/*  875:     */     {
/*  876: 962 */       Table table = createTable(entityBinding, secondaryTableSource);
/*  877: 963 */       entityBinding.addSecondaryTable(secondaryTableSource.getLogicalName(), table);
/*  878:     */     }
/*  879:     */   }
/*  880:     */   
/*  881:     */   private Table createTable(EntityBinding entityBinding, TableSource tableSource)
/*  882:     */   {
/*  883: 968 */     String tableName = tableSource.getExplicitTableName();
/*  884: 969 */     if (StringHelper.isEmpty(tableName)) {
/*  885: 970 */       tableName = this.currentBindingContext.getNamingStrategy().classToTableName(entityBinding.getEntity().getClassName());
/*  886:     */     } else {
/*  887: 974 */       tableName = this.currentBindingContext.getNamingStrategy().tableName(tableName);
/*  888:     */     }
/*  889: 976 */     tableName = quoteIdentifier(tableName);
/*  890:     */     
/*  891: 978 */     Schema.Name databaseSchemaName = Helper.determineDatabaseSchemaName(tableSource.getExplicitSchemaName(), tableSource.getExplicitCatalogName(), this.currentBindingContext);
/*  892:     */     
/*  893:     */ 
/*  894:     */ 
/*  895:     */ 
/*  896: 983 */     return this.currentBindingContext.getMetadataImplementor().getDatabase().locateSchema(databaseSchemaName).locateOrCreateTable(Identifier.toIdentifier(tableName));
/*  897:     */   }
/*  898:     */   
/*  899:     */   private void bindTableUniqueConstraints(EntitySource entitySource, EntityBinding entityBinding)
/*  900:     */   {
/*  901: 990 */     for (ConstraintSource constraintSource : entitySource.getConstraints()) {
/*  902: 991 */       if ((constraintSource instanceof UniqueConstraintSource))
/*  903:     */       {
/*  904: 992 */         table = entityBinding.locateTable(constraintSource.getTableName());
/*  905: 993 */         if (table == null) {}
/*  906: 996 */         String constraintName = constraintSource.name();
/*  907: 997 */         if (constraintName == null) {}
/*  908:1001 */         uniqueKey = table.getOrCreateUniqueKey(constraintName);
/*  909:1002 */         for (String columnName : constraintSource.columnNames()) {
/*  910:1003 */           uniqueKey.addColumn(table.locateOrCreateColumn(quoteIdentifier(columnName)));
/*  911:     */         }
/*  912:     */       }
/*  913:     */     }
/*  914:     */     TableSpecification table;
/*  915:     */     UniqueKey uniqueKey;
/*  916:     */   }
/*  917:     */   
/*  918:     */   private void bindRelationalValues(RelationalValueSourceContainer relationalValueSourceContainer, SingularAttributeBinding attributeBinding)
/*  919:     */   {
/*  920:1013 */     List<SimpleValueBinding> valueBindings = new ArrayList();
/*  921:1015 */     if (!relationalValueSourceContainer.relationalValueSources().isEmpty())
/*  922:     */     {
/*  923:1016 */       for (RelationalValueSource valueSource : relationalValueSourceContainer.relationalValueSources())
/*  924:     */       {
/*  925:1017 */         TableSpecification table = attributeBinding.getContainer().seekEntityBinding().locateTable(valueSource.getContainingTableName());
/*  926:1021 */         if (ColumnSource.class.isInstance(valueSource))
/*  927:     */         {
/*  928:1022 */           ColumnSource columnSource = (ColumnSource)ColumnSource.class.cast(valueSource);
/*  929:1023 */           Column column = makeColumn((ColumnSource)valueSource, table);
/*  930:1024 */           valueBindings.add(new SimpleValueBinding(column, columnSource.isIncludedInInsert(), columnSource.isIncludedInUpdate()));
/*  931:     */         }
/*  932:     */         else
/*  933:     */         {
/*  934:1033 */           valueBindings.add(new SimpleValueBinding(makeDerivedValue((DerivedValueSource)valueSource, table)));
/*  935:     */         }
/*  936:     */       }
/*  937:     */     }
/*  938:     */     else
/*  939:     */     {
/*  940:1042 */       String name = this.metadata.getOptions().getNamingStrategy().propertyToColumnName(attributeBinding.getAttribute().getName());
/*  941:     */       
/*  942:     */ 
/*  943:1045 */       name = quoteIdentifier(name);
/*  944:1046 */       Column column = attributeBinding.getContainer().seekEntityBinding().getPrimaryTable().locateOrCreateColumn(name);
/*  945:     */       
/*  946:     */ 
/*  947:     */ 
/*  948:1050 */       column.setNullable(relationalValueSourceContainer.areValuesNullableByDefault());
/*  949:1051 */       valueBindings.add(new SimpleValueBinding(column, relationalValueSourceContainer.areValuesIncludedInInsertByDefault(), relationalValueSourceContainer.areValuesIncludedInUpdateByDefault()));
/*  950:     */     }
/*  951:1059 */     attributeBinding.setSimpleValueBindings(valueBindings);
/*  952:     */   }
/*  953:     */   
/*  954:     */   private String quoteIdentifier(String identifier)
/*  955:     */   {
/*  956:1063 */     return this.currentBindingContext.isGloballyQuotedIdentifiers() ? StringHelper.quote(identifier) : identifier;
/*  957:     */   }
/*  958:     */   
/*  959:     */   private SimpleValue makeSimpleValue(EntityBinding entityBinding, RelationalValueSource valueSource)
/*  960:     */   {
/*  961:1069 */     TableSpecification table = entityBinding.locateTable(valueSource.getContainingTableName());
/*  962:1071 */     if (ColumnSource.class.isInstance(valueSource)) {
/*  963:1072 */       return makeColumn((ColumnSource)valueSource, table);
/*  964:     */     }
/*  965:1075 */     return makeDerivedValue((DerivedValueSource)valueSource, table);
/*  966:     */   }
/*  967:     */   
/*  968:     */   private Column makeColumn(ColumnSource columnSource, TableSpecification table)
/*  969:     */   {
/*  970:1080 */     String name = columnSource.getName();
/*  971:1081 */     name = this.metadata.getOptions().getNamingStrategy().columnName(name);
/*  972:1082 */     name = quoteIdentifier(name);
/*  973:1083 */     Column column = table.locateOrCreateColumn(name);
/*  974:1084 */     column.setNullable(columnSource.isNullable());
/*  975:1085 */     column.setDefaultValue(columnSource.getDefaultValue());
/*  976:1086 */     column.setSqlType(columnSource.getSqlType());
/*  977:1087 */     column.setSize(columnSource.getSize());
/*  978:1088 */     column.setDatatype(columnSource.getDatatype());
/*  979:1089 */     column.setReadFragment(columnSource.getReadFragment());
/*  980:1090 */     column.setWriteFragment(columnSource.getWriteFragment());
/*  981:1091 */     column.setUnique(columnSource.isUnique());
/*  982:1092 */     column.setCheckCondition(columnSource.getCheckCondition());
/*  983:1093 */     column.setComment(columnSource.getComment());
/*  984:1094 */     return column;
/*  985:     */   }
/*  986:     */   
/*  987:     */   private DerivedValue makeDerivedValue(DerivedValueSource derivedValueSource, TableSpecification table)
/*  988:     */   {
/*  989:1098 */     return table.locateOrCreateDerivedValue(derivedValueSource.getExpression());
/*  990:     */   }
/*  991:     */   
/*  992:     */   private void processFetchProfiles(EntitySource entitySource, EntityBinding entityBinding) {}
/*  993:     */   
/*  994:     */   private static class AttributeJavaTypeDeterminerDelegate
/*  995:     */     implements BeanInfoHelper.BeanInfoDelegate
/*  996:     */   {
/*  997:     */     private final String attributeName;
/*  998:1107 */     private Class<?> javaType = null;
/*  999:     */     
/* 1000:     */     private AttributeJavaTypeDeterminerDelegate(String attributeName)
/* 1001:     */     {
/* 1002:1110 */       this.attributeName = attributeName;
/* 1003:     */     }
/* 1004:     */     
/* 1005:     */     public void processBeanInfo(BeanInfo beanInfo)
/* 1006:     */       throws Exception
/* 1007:     */     {
/* 1008:1115 */       for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
/* 1009:1116 */         if (propertyDescriptor.getName().equals(this.attributeName))
/* 1010:     */         {
/* 1011:1117 */           this.javaType = propertyDescriptor.getPropertyType();
/* 1012:1118 */           break;
/* 1013:     */         }
/* 1014:     */       }
/* 1015:     */     }
/* 1016:     */   }
/* 1017:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.Binder
 * JD-Core Version:    0.7.0.1
 */
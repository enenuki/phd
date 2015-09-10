/*    1:     */ package org.hibernate.cfg;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.HashMap;
/*    5:     */ import java.util.HashSet;
/*    6:     */ import java.util.Iterator;
/*    7:     */ import java.util.LinkedHashMap;
/*    8:     */ import java.util.Properties;
/*    9:     */ import java.util.StringTokenizer;
/*   10:     */ import org.dom4j.Attribute;
/*   11:     */ import org.dom4j.Document;
/*   12:     */ import org.dom4j.Element;
/*   13:     */ import org.hibernate.CacheMode;
/*   14:     */ import org.hibernate.EntityMode;
/*   15:     */ import org.hibernate.FetchMode;
/*   16:     */ import org.hibernate.FlushMode;
/*   17:     */ import org.hibernate.MappingException;
/*   18:     */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*   19:     */ import org.hibernate.engine.spi.FilterDefinition;
/*   20:     */ import org.hibernate.engine.spi.NamedQueryDefinition;
/*   21:     */ import org.hibernate.id.factory.spi.MutableIdentifierGeneratorFactory;
/*   22:     */ import org.hibernate.internal.CoreMessageLogger;
/*   23:     */ import org.hibernate.internal.util.ReflectHelper;
/*   24:     */ import org.hibernate.internal.util.StringHelper;
/*   25:     */ import org.hibernate.internal.util.collections.JoinedIterator;
/*   26:     */ import org.hibernate.internal.util.xml.XmlDocument;
/*   27:     */ import org.hibernate.mapping.Any;
/*   28:     */ import org.hibernate.mapping.Array;
/*   29:     */ import org.hibernate.mapping.AuxiliaryDatabaseObject;
/*   30:     */ import org.hibernate.mapping.Backref;
/*   31:     */ import org.hibernate.mapping.Bag;
/*   32:     */ import org.hibernate.mapping.Collection;
/*   33:     */ import org.hibernate.mapping.Column;
/*   34:     */ import org.hibernate.mapping.Component;
/*   35:     */ import org.hibernate.mapping.DependantValue;
/*   36:     */ import org.hibernate.mapping.FetchProfile;
/*   37:     */ import org.hibernate.mapping.Fetchable;
/*   38:     */ import org.hibernate.mapping.Filterable;
/*   39:     */ import org.hibernate.mapping.Formula;
/*   40:     */ import org.hibernate.mapping.IdentifierBag;
/*   41:     */ import org.hibernate.mapping.IdentifierCollection;
/*   42:     */ import org.hibernate.mapping.Index;
/*   43:     */ import org.hibernate.mapping.IndexBackref;
/*   44:     */ import org.hibernate.mapping.Join;
/*   45:     */ import org.hibernate.mapping.JoinedSubclass;
/*   46:     */ import org.hibernate.mapping.KeyValue;
/*   47:     */ import org.hibernate.mapping.ManyToOne;
/*   48:     */ import org.hibernate.mapping.MetaAttribute;
/*   49:     */ import org.hibernate.mapping.MetadataSource;
/*   50:     */ import org.hibernate.mapping.OneToMany;
/*   51:     */ import org.hibernate.mapping.OneToOne;
/*   52:     */ import org.hibernate.mapping.PersistentClass;
/*   53:     */ import org.hibernate.mapping.PrimitiveArray;
/*   54:     */ import org.hibernate.mapping.Property;
/*   55:     */ import org.hibernate.mapping.PropertyGeneration;
/*   56:     */ import org.hibernate.mapping.RootClass;
/*   57:     */ import org.hibernate.mapping.Selectable;
/*   58:     */ import org.hibernate.mapping.SimpleAuxiliaryDatabaseObject;
/*   59:     */ import org.hibernate.mapping.SimpleValue;
/*   60:     */ import org.hibernate.mapping.SingleTableSubclass;
/*   61:     */ import org.hibernate.mapping.Subclass;
/*   62:     */ import org.hibernate.mapping.Table;
/*   63:     */ import org.hibernate.mapping.ToOne;
/*   64:     */ import org.hibernate.mapping.TypeDef;
/*   65:     */ import org.hibernate.mapping.UnionSubclass;
/*   66:     */ import org.hibernate.mapping.UniqueKey;
/*   67:     */ import org.hibernate.mapping.Value;
/*   68:     */ import org.hibernate.type.DiscriminatorType;
/*   69:     */ import org.hibernate.type.ForeignKeyDirection;
/*   70:     */ import org.hibernate.type.Type;
/*   71:     */ import org.hibernate.type.TypeResolver;
/*   72:     */ import org.jboss.logging.Logger;
/*   73:     */ 
/*   74:     */ public final class HbmBinder
/*   75:     */ {
/*   76: 109 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, HbmBinder.class.getName());
/*   77:     */   
/*   78:     */   public static void bindRoot(XmlDocument metadataXml, Mappings mappings, java.util.Map inheritedMetas, java.util.Set<String> entityNames)
/*   79:     */     throws MappingException
/*   80:     */   {
/*   81: 134 */     Document doc = metadataXml.getDocumentTree();
/*   82: 135 */     Element hibernateMappingElement = doc.getRootElement();
/*   83:     */     
/*   84: 137 */     java.util.List<String> names = getExtendsNeeded(metadataXml, mappings);
/*   85: 138 */     if (!names.isEmpty())
/*   86:     */     {
/*   87: 140 */       Attribute packageAttribute = hibernateMappingElement.attribute("package");
/*   88: 141 */       String packageName = packageAttribute == null ? null : packageAttribute.getValue();
/*   89: 142 */       for (String name : names) {
/*   90: 143 */         mappings.addToExtendsQueue(new ExtendsQueueEntry(name, packageName, metadataXml, entityNames));
/*   91:     */       }
/*   92: 145 */       return;
/*   93:     */     }
/*   94: 149 */     inheritedMetas = getMetas(hibernateMappingElement, inheritedMetas, true);
/*   95: 150 */     extractRootAttributes(hibernateMappingElement, mappings);
/*   96:     */     
/*   97: 152 */     Iterator rootChildren = hibernateMappingElement.elementIterator();
/*   98: 153 */     while (rootChildren.hasNext())
/*   99:     */     {
/*  100: 154 */       Element element = (Element)rootChildren.next();
/*  101: 155 */       String elementName = element.getName();
/*  102: 157 */       if ("filter-def".equals(elementName))
/*  103:     */       {
/*  104: 158 */         parseFilterDef(element, mappings);
/*  105:     */       }
/*  106: 160 */       else if ("fetch-profile".equals(elementName))
/*  107:     */       {
/*  108: 161 */         parseFetchProfile(element, mappings, null);
/*  109:     */       }
/*  110: 163 */       else if ("identifier-generator".equals(elementName))
/*  111:     */       {
/*  112: 164 */         parseIdentifierGeneratorRegistration(element, mappings);
/*  113:     */       }
/*  114: 166 */       else if ("typedef".equals(elementName))
/*  115:     */       {
/*  116: 167 */         bindTypeDef(element, mappings);
/*  117:     */       }
/*  118: 169 */       else if ("class".equals(elementName))
/*  119:     */       {
/*  120: 170 */         RootClass rootclass = new RootClass();
/*  121: 171 */         bindRootClass(element, rootclass, mappings, inheritedMetas);
/*  122: 172 */         mappings.addClass(rootclass);
/*  123:     */       }
/*  124: 174 */       else if ("subclass".equals(elementName))
/*  125:     */       {
/*  126: 175 */         PersistentClass superModel = getSuperclass(mappings, element);
/*  127: 176 */         handleSubclass(superModel, mappings, element, inheritedMetas);
/*  128:     */       }
/*  129: 178 */       else if ("joined-subclass".equals(elementName))
/*  130:     */       {
/*  131: 179 */         PersistentClass superModel = getSuperclass(mappings, element);
/*  132: 180 */         handleJoinedSubclass(superModel, mappings, element, inheritedMetas);
/*  133:     */       }
/*  134: 182 */       else if ("union-subclass".equals(elementName))
/*  135:     */       {
/*  136: 183 */         PersistentClass superModel = getSuperclass(mappings, element);
/*  137: 184 */         handleUnionSubclass(superModel, mappings, element, inheritedMetas);
/*  138:     */       }
/*  139: 186 */       else if ("query".equals(elementName))
/*  140:     */       {
/*  141: 187 */         bindNamedQuery(element, null, mappings);
/*  142:     */       }
/*  143: 189 */       else if ("sql-query".equals(elementName))
/*  144:     */       {
/*  145: 190 */         bindNamedSQLQuery(element, null, mappings);
/*  146:     */       }
/*  147: 192 */       else if ("resultset".equals(elementName))
/*  148:     */       {
/*  149: 193 */         bindResultSetMappingDefinition(element, null, mappings);
/*  150:     */       }
/*  151: 195 */       else if ("import".equals(elementName))
/*  152:     */       {
/*  153: 196 */         bindImport(element, mappings);
/*  154:     */       }
/*  155: 198 */       else if ("database-object".equals(elementName))
/*  156:     */       {
/*  157: 199 */         bindAuxiliaryDatabaseObject(element, mappings);
/*  158:     */       }
/*  159:     */     }
/*  160:     */   }
/*  161:     */   
/*  162:     */   private static void parseIdentifierGeneratorRegistration(Element element, Mappings mappings)
/*  163:     */   {
/*  164: 205 */     String strategy = element.attributeValue("name");
/*  165: 206 */     if (StringHelper.isEmpty(strategy)) {
/*  166: 207 */       throw new MappingException("'name' attribute expected for identifier-generator elements");
/*  167:     */     }
/*  168: 209 */     String generatorClassName = element.attributeValue("class");
/*  169: 210 */     if (StringHelper.isEmpty(generatorClassName)) {
/*  170: 211 */       throw new MappingException("'class' attribute expected for identifier-generator [identifier-generator@name=" + strategy + "]");
/*  171:     */     }
/*  172:     */     try
/*  173:     */     {
/*  174: 215 */       Class generatorClass = ReflectHelper.classForName(generatorClassName);
/*  175: 216 */       mappings.getIdentifierGeneratorFactory().register(strategy, generatorClass);
/*  176:     */     }
/*  177:     */     catch (ClassNotFoundException e)
/*  178:     */     {
/*  179: 219 */       throw new MappingException("Unable to locate identifier-generator class [name=" + strategy + ", class=" + generatorClassName + "]");
/*  180:     */     }
/*  181:     */   }
/*  182:     */   
/*  183:     */   private static void bindImport(Element importNode, Mappings mappings)
/*  184:     */   {
/*  185: 225 */     String className = getClassName(importNode.attribute("class"), mappings);
/*  186: 226 */     Attribute renameNode = importNode.attribute("rename");
/*  187: 227 */     String rename = renameNode == null ? StringHelper.unqualify(className) : renameNode.getValue();
/*  188:     */     
/*  189:     */ 
/*  190: 230 */     LOG.debugf("Import: %s -> %s", rename, className);
/*  191: 231 */     mappings.addImport(className, rename);
/*  192:     */   }
/*  193:     */   
/*  194:     */   private static void bindTypeDef(Element typedefNode, Mappings mappings)
/*  195:     */   {
/*  196: 235 */     String typeClass = typedefNode.attributeValue("class");
/*  197: 236 */     String typeName = typedefNode.attributeValue("name");
/*  198: 237 */     Iterator paramIter = typedefNode.elementIterator("param");
/*  199: 238 */     Properties parameters = new Properties();
/*  200: 239 */     while (paramIter.hasNext())
/*  201:     */     {
/*  202: 240 */       Element param = (Element)paramIter.next();
/*  203: 241 */       parameters.setProperty(param.attributeValue("name"), param.getTextTrim());
/*  204:     */     }
/*  205: 243 */     mappings.addTypeDef(typeName, typeClass, parameters);
/*  206:     */   }
/*  207:     */   
/*  208:     */   private static void bindAuxiliaryDatabaseObject(Element auxDbObjectNode, Mappings mappings)
/*  209:     */   {
/*  210: 247 */     AuxiliaryDatabaseObject auxDbObject = null;
/*  211: 248 */     Element definitionNode = auxDbObjectNode.element("definition");
/*  212: 249 */     if (definitionNode != null) {
/*  213:     */       try
/*  214:     */       {
/*  215: 251 */         auxDbObject = (AuxiliaryDatabaseObject)ReflectHelper.classForName(definitionNode.attributeValue("class")).newInstance();
/*  216:     */       }
/*  217:     */       catch (ClassNotFoundException e)
/*  218:     */       {
/*  219: 256 */         throw new MappingException("could not locate custom database object class [" + definitionNode.attributeValue("class") + "]");
/*  220:     */       }
/*  221:     */       catch (Throwable t)
/*  222:     */       {
/*  223: 262 */         throw new MappingException("could not instantiate custom database object class [" + definitionNode.attributeValue("class") + "]");
/*  224:     */       }
/*  225:     */     } else {
/*  226: 269 */       auxDbObject = new SimpleAuxiliaryDatabaseObject(auxDbObjectNode.elementTextTrim("create"), auxDbObjectNode.elementTextTrim("drop"));
/*  227:     */     }
/*  228: 275 */     Iterator dialectScopings = auxDbObjectNode.elementIterator("dialect-scope");
/*  229: 276 */     while (dialectScopings.hasNext())
/*  230:     */     {
/*  231: 277 */       Element dialectScoping = (Element)dialectScopings.next();
/*  232: 278 */       auxDbObject.addDialectScope(dialectScoping.attributeValue("name"));
/*  233:     */     }
/*  234: 281 */     mappings.addAuxiliaryDatabaseObject(auxDbObject);
/*  235:     */   }
/*  236:     */   
/*  237:     */   private static void extractRootAttributes(Element hmNode, Mappings mappings)
/*  238:     */   {
/*  239: 285 */     Attribute schemaNode = hmNode.attribute("schema");
/*  240: 286 */     mappings.setSchemaName(schemaNode == null ? null : schemaNode.getValue());
/*  241:     */     
/*  242: 288 */     Attribute catalogNode = hmNode.attribute("catalog");
/*  243: 289 */     mappings.setCatalogName(catalogNode == null ? null : catalogNode.getValue());
/*  244:     */     
/*  245: 291 */     Attribute dcNode = hmNode.attribute("default-cascade");
/*  246: 292 */     mappings.setDefaultCascade(dcNode == null ? "none" : dcNode.getValue());
/*  247:     */     
/*  248: 294 */     Attribute daNode = hmNode.attribute("default-access");
/*  249: 295 */     mappings.setDefaultAccess(daNode == null ? "property" : daNode.getValue());
/*  250:     */     
/*  251: 297 */     Attribute dlNode = hmNode.attribute("default-lazy");
/*  252: 298 */     mappings.setDefaultLazy((dlNode == null) || (dlNode.getValue().equals("true")));
/*  253:     */     
/*  254: 300 */     Attribute aiNode = hmNode.attribute("auto-import");
/*  255: 301 */     mappings.setAutoImport((aiNode == null) || ("true".equals(aiNode.getValue())));
/*  256:     */     
/*  257: 303 */     Attribute packNode = hmNode.attribute("package");
/*  258: 304 */     if (packNode != null) {
/*  259: 304 */       mappings.setDefaultPackage(packNode.getValue());
/*  260:     */     }
/*  261:     */   }
/*  262:     */   
/*  263:     */   public static void bindRootClass(Element node, RootClass rootClass, Mappings mappings, java.util.Map inheritedMetas)
/*  264:     */     throws MappingException
/*  265:     */   {
/*  266: 318 */     bindClass(node, rootClass, mappings, inheritedMetas);
/*  267: 319 */     inheritedMetas = getMetas(node, inheritedMetas, true);
/*  268: 320 */     bindRootPersistentClassCommonValues(node, inheritedMetas, mappings, rootClass);
/*  269:     */   }
/*  270:     */   
/*  271:     */   private static void bindRootPersistentClassCommonValues(Element node, java.util.Map inheritedMetas, Mappings mappings, RootClass entity)
/*  272:     */     throws MappingException
/*  273:     */   {
/*  274: 329 */     Attribute schemaNode = node.attribute("schema");
/*  275: 330 */     String schema = schemaNode == null ? mappings.getSchemaName() : schemaNode.getValue();
/*  276:     */     
/*  277:     */ 
/*  278: 333 */     Attribute catalogNode = node.attribute("catalog");
/*  279: 334 */     String catalog = catalogNode == null ? mappings.getCatalogName() : catalogNode.getValue();
/*  280:     */     
/*  281:     */ 
/*  282: 337 */     Table table = mappings.addTable(schema, catalog, getClassTableName(entity, node, schema, catalog, null, mappings), getSubselect(node), (entity.isAbstract() != null) && (entity.isAbstract().booleanValue()));
/*  283:     */     
/*  284:     */ 
/*  285:     */ 
/*  286:     */ 
/*  287:     */ 
/*  288:     */ 
/*  289: 344 */     entity.setTable(table);
/*  290: 345 */     bindComment(table, node);
/*  291: 347 */     if (LOG.isDebugEnabled()) {
/*  292: 348 */       LOG.debugf("Mapping class: %s -> %s", entity.getEntityName(), entity.getTable().getName());
/*  293:     */     }
/*  294: 352 */     Attribute mutableNode = node.attribute("mutable");
/*  295: 353 */     entity.setMutable((mutableNode == null) || (mutableNode.getValue().equals("true")));
/*  296:     */     
/*  297:     */ 
/*  298: 356 */     Attribute whereNode = node.attribute("where");
/*  299: 357 */     if (whereNode != null) {
/*  300: 357 */       entity.setWhere(whereNode.getValue());
/*  301:     */     }
/*  302: 360 */     Attribute chNode = node.attribute("check");
/*  303: 361 */     if (chNode != null) {
/*  304: 361 */       table.addCheckConstraint(chNode.getValue());
/*  305:     */     }
/*  306: 364 */     Attribute polyNode = node.attribute("polymorphism");
/*  307: 365 */     entity.setExplicitPolymorphism((polyNode != null) && (polyNode.getValue().equals("explicit")));
/*  308:     */     
/*  309:     */ 
/*  310:     */ 
/*  311: 369 */     Attribute rowidNode = node.attribute("rowid");
/*  312: 370 */     if (rowidNode != null) {
/*  313: 370 */       table.setRowId(rowidNode.getValue());
/*  314:     */     }
/*  315: 372 */     Iterator subnodes = node.elementIterator();
/*  316: 373 */     while (subnodes.hasNext())
/*  317:     */     {
/*  318: 375 */       Element subnode = (Element)subnodes.next();
/*  319: 376 */       String name = subnode.getName();
/*  320: 378 */       if ("id".equals(name))
/*  321:     */       {
/*  322: 380 */         bindSimpleId(subnode, entity, mappings, inheritedMetas);
/*  323:     */       }
/*  324: 382 */       else if ("composite-id".equals(name))
/*  325:     */       {
/*  326: 384 */         bindCompositeId(subnode, entity, mappings, inheritedMetas);
/*  327:     */       }
/*  328: 386 */       else if (("version".equals(name)) || ("timestamp".equals(name)))
/*  329:     */       {
/*  330: 388 */         bindVersioningProperty(table, subnode, mappings, name, entity, inheritedMetas);
/*  331:     */       }
/*  332: 390 */       else if ("discriminator".equals(name))
/*  333:     */       {
/*  334: 392 */         bindDiscriminatorProperty(table, entity, subnode, mappings);
/*  335:     */       }
/*  336: 394 */       else if ("cache".equals(name))
/*  337:     */       {
/*  338: 395 */         entity.setCacheConcurrencyStrategy(subnode.attributeValue("usage"));
/*  339: 396 */         entity.setCacheRegionName(subnode.attributeValue("region"));
/*  340: 397 */         entity.setLazyPropertiesCacheable(!"non-lazy".equals(subnode.attributeValue("include")));
/*  341:     */       }
/*  342:     */     }
/*  343: 403 */     entity.createPrimaryKey();
/*  344:     */     
/*  345: 405 */     createClassProperties(node, entity, mappings, inheritedMetas);
/*  346:     */   }
/*  347:     */   
/*  348:     */   private static void bindSimpleId(Element idNode, RootClass entity, Mappings mappings, java.util.Map inheritedMetas)
/*  349:     */     throws MappingException
/*  350:     */   {
/*  351: 410 */     String propertyName = idNode.attributeValue("name");
/*  352:     */     
/*  353: 412 */     SimpleValue id = new SimpleValue(mappings, entity.getTable());
/*  354: 413 */     entity.setIdentifier(id);
/*  355: 433 */     if (propertyName == null) {
/*  356: 434 */       bindSimpleValue(idNode, id, false, "id", mappings);
/*  357:     */     } else {
/*  358: 437 */       bindSimpleValue(idNode, id, false, propertyName, mappings);
/*  359:     */     }
/*  360: 440 */     if ((propertyName == null) || (!entity.hasPojoRepresentation()))
/*  361:     */     {
/*  362: 441 */       if (!id.isTypeSpecified()) {
/*  363: 442 */         throw new MappingException("must specify an identifier type: " + entity.getEntityName());
/*  364:     */       }
/*  365:     */     }
/*  366:     */     else {
/*  367: 447 */       id.setTypeUsingReflection(entity.getClassName(), propertyName);
/*  368:     */     }
/*  369: 450 */     if (propertyName != null)
/*  370:     */     {
/*  371: 451 */       Property prop = new Property();
/*  372: 452 */       prop.setValue(id);
/*  373: 453 */       bindProperty(idNode, prop, mappings, inheritedMetas);
/*  374: 454 */       entity.setIdentifierProperty(prop);
/*  375:     */     }
/*  376: 462 */     makeIdentifier(idNode, id, mappings);
/*  377:     */   }
/*  378:     */   
/*  379:     */   private static void bindCompositeId(Element idNode, RootClass entity, Mappings mappings, java.util.Map inheritedMetas)
/*  380:     */     throws MappingException
/*  381:     */   {
/*  382: 467 */     String propertyName = idNode.attributeValue("name");
/*  383: 468 */     Component id = new Component(mappings, entity);
/*  384: 469 */     entity.setIdentifier(id);
/*  385: 470 */     bindCompositeId(idNode, id, entity, propertyName, mappings, inheritedMetas);
/*  386: 471 */     if (propertyName == null)
/*  387:     */     {
/*  388: 472 */       entity.setEmbeddedIdentifier(id.isEmbedded());
/*  389: 473 */       if (id.isEmbedded()) {
/*  390: 475 */         id.setDynamic(!entity.hasPojoRepresentation());
/*  391:     */       }
/*  392:     */     }
/*  393:     */     else
/*  394:     */     {
/*  395: 484 */       Property prop = new Property();
/*  396: 485 */       prop.setValue(id);
/*  397: 486 */       bindProperty(idNode, prop, mappings, inheritedMetas);
/*  398: 487 */       entity.setIdentifierProperty(prop);
/*  399:     */     }
/*  400: 490 */     makeIdentifier(idNode, id, mappings);
/*  401:     */   }
/*  402:     */   
/*  403:     */   private static void bindVersioningProperty(Table table, Element subnode, Mappings mappings, String name, RootClass entity, java.util.Map inheritedMetas)
/*  404:     */   {
/*  405: 497 */     String propertyName = subnode.attributeValue("name");
/*  406: 498 */     SimpleValue val = new SimpleValue(mappings, table);
/*  407: 499 */     bindSimpleValue(subnode, val, false, propertyName, mappings);
/*  408: 500 */     if (!val.isTypeSpecified()) {
/*  409: 503 */       if ("version".equals(name)) {
/*  410: 504 */         val.setTypeName("integer");
/*  411: 507 */       } else if ("db".equals(subnode.attributeValue("source"))) {
/*  412: 508 */         val.setTypeName("dbtimestamp");
/*  413:     */       } else {
/*  414: 511 */         val.setTypeName("timestamp");
/*  415:     */       }
/*  416:     */     }
/*  417: 515 */     Property prop = new Property();
/*  418: 516 */     prop.setValue(val);
/*  419: 517 */     bindProperty(subnode, prop, mappings, inheritedMetas);
/*  420: 521 */     if (prop.getGeneration() == PropertyGeneration.INSERT) {
/*  421: 522 */       throw new MappingException("'generated' attribute cannot be 'insert' for versioning property");
/*  422:     */     }
/*  423: 524 */     makeVersion(subnode, val);
/*  424: 525 */     entity.setVersion(prop);
/*  425: 526 */     entity.addProperty(prop);
/*  426:     */   }
/*  427:     */   
/*  428:     */   private static void bindDiscriminatorProperty(Table table, RootClass entity, Element subnode, Mappings mappings)
/*  429:     */   {
/*  430: 531 */     SimpleValue discrim = new SimpleValue(mappings, table);
/*  431: 532 */     entity.setDiscriminator(discrim);
/*  432: 533 */     bindSimpleValue(subnode, discrim, false, "class", mappings);
/*  433: 540 */     if (!discrim.isTypeSpecified()) {
/*  434: 541 */       discrim.setTypeName("string");
/*  435:     */     }
/*  436: 544 */     entity.setPolymorphic(true);
/*  437: 545 */     if ("true".equals(subnode.attributeValue("force"))) {
/*  438: 546 */       entity.setForceDiscriminator(true);
/*  439:     */     }
/*  440: 547 */     if ("false".equals(subnode.attributeValue("insert"))) {
/*  441: 548 */       entity.setDiscriminatorInsertable(false);
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   public static void bindClass(Element node, PersistentClass persistentClass, Mappings mappings, java.util.Map inheritedMetas)
/*  446:     */     throws MappingException
/*  447:     */   {
/*  448: 555 */     Attribute lazyNode = node.attribute("lazy");
/*  449: 556 */     boolean lazy = lazyNode == null ? mappings.isDefaultLazy() : "true".equals(lazyNode.getValue());
/*  450:     */     
/*  451:     */ 
/*  452:     */ 
/*  453: 560 */     persistentClass.setLazy(lazy);
/*  454:     */     
/*  455: 562 */     String entityName = node.attributeValue("entity-name");
/*  456: 563 */     if (entityName == null) {
/*  457: 563 */       entityName = getClassName(node.attribute("name"), mappings);
/*  458:     */     }
/*  459: 564 */     if (entityName == null) {
/*  460: 565 */       throw new MappingException("Unable to determine entity name");
/*  461:     */     }
/*  462: 567 */     persistentClass.setEntityName(entityName);
/*  463:     */     
/*  464: 569 */     bindPojoRepresentation(node, persistentClass, mappings, inheritedMetas);
/*  465: 570 */     bindDom4jRepresentation(node, persistentClass, mappings, inheritedMetas);
/*  466: 571 */     bindMapRepresentation(node, persistentClass, mappings, inheritedMetas);
/*  467:     */     
/*  468: 573 */     Iterator itr = node.elementIterator("fetch-profile");
/*  469: 574 */     while (itr.hasNext())
/*  470:     */     {
/*  471: 575 */       Element profileElement = (Element)itr.next();
/*  472: 576 */       parseFetchProfile(profileElement, mappings, entityName);
/*  473:     */     }
/*  474: 579 */     bindPersistentClassCommonValues(node, persistentClass, mappings, inheritedMetas);
/*  475:     */   }
/*  476:     */   
/*  477:     */   private static void bindPojoRepresentation(Element node, PersistentClass entity, Mappings mappings, java.util.Map metaTags)
/*  478:     */   {
/*  479: 585 */     String className = getClassName(node.attribute("name"), mappings);
/*  480: 586 */     String proxyName = getClassName(node.attribute("proxy"), mappings);
/*  481:     */     
/*  482: 588 */     entity.setClassName(className);
/*  483: 590 */     if (proxyName != null)
/*  484:     */     {
/*  485: 591 */       entity.setProxyInterfaceName(proxyName);
/*  486: 592 */       entity.setLazy(true);
/*  487:     */     }
/*  488: 594 */     else if (entity.isLazy())
/*  489:     */     {
/*  490: 595 */       entity.setProxyInterfaceName(className);
/*  491:     */     }
/*  492: 598 */     Element tuplizer = locateTuplizerDefinition(node, EntityMode.POJO);
/*  493: 599 */     if (tuplizer != null) {
/*  494: 600 */       entity.addTuplizer(EntityMode.POJO, tuplizer.attributeValue("class"));
/*  495:     */     }
/*  496:     */   }
/*  497:     */   
/*  498:     */   private static void bindDom4jRepresentation(Element node, PersistentClass entity, Mappings mappings, java.util.Map inheritedMetas)
/*  499:     */   {
/*  500: 606 */     String nodeName = node.attributeValue("node");
/*  501: 607 */     if (nodeName == null) {
/*  502: 607 */       nodeName = StringHelper.unqualify(entity.getEntityName());
/*  503:     */     }
/*  504: 608 */     entity.setNodeName(nodeName);
/*  505:     */   }
/*  506:     */   
/*  507:     */   private static void bindMapRepresentation(Element node, PersistentClass entity, Mappings mappings, java.util.Map inheritedMetas)
/*  508:     */   {
/*  509: 618 */     Element tuplizer = locateTuplizerDefinition(node, EntityMode.MAP);
/*  510: 619 */     if (tuplizer != null) {
/*  511: 620 */       entity.addTuplizer(EntityMode.MAP, tuplizer.attributeValue("class"));
/*  512:     */     }
/*  513:     */   }
/*  514:     */   
/*  515:     */   private static Element locateTuplizerDefinition(Element container, EntityMode entityMode)
/*  516:     */   {
/*  517: 632 */     Iterator itr = container.elements("tuplizer").iterator();
/*  518: 633 */     while (itr.hasNext())
/*  519:     */     {
/*  520: 634 */       Element tuplizerElem = (Element)itr.next();
/*  521: 635 */       if (entityMode.toString().equals(tuplizerElem.attributeValue("entity-mode"))) {
/*  522: 636 */         return tuplizerElem;
/*  523:     */       }
/*  524:     */     }
/*  525: 639 */     return null;
/*  526:     */   }
/*  527:     */   
/*  528:     */   private static void bindPersistentClassCommonValues(Element node, PersistentClass entity, Mappings mappings, java.util.Map inheritedMetas)
/*  529:     */     throws MappingException
/*  530:     */   {
/*  531: 645 */     Attribute discriminatorNode = node.attribute("discriminator-value");
/*  532: 646 */     entity.setDiscriminatorValue(discriminatorNode == null ? entity.getEntityName() : discriminatorNode.getValue());
/*  533:     */     
/*  534:     */ 
/*  535:     */ 
/*  536:     */ 
/*  537: 651 */     Attribute dynamicNode = node.attribute("dynamic-update");
/*  538: 652 */     entity.setDynamicUpdate((dynamicNode != null) && ("true".equals(dynamicNode.getValue())));
/*  539:     */     
/*  540:     */ 
/*  541:     */ 
/*  542:     */ 
/*  543: 657 */     Attribute insertNode = node.attribute("dynamic-insert");
/*  544: 658 */     entity.setDynamicInsert((insertNode != null) && ("true".equals(insertNode.getValue())));
/*  545:     */     
/*  546:     */ 
/*  547:     */ 
/*  548:     */ 
/*  549: 663 */     mappings.addImport(entity.getEntityName(), entity.getEntityName());
/*  550: 664 */     if ((mappings.isAutoImport()) && (entity.getEntityName().indexOf('.') > 0)) {
/*  551: 665 */       mappings.addImport(entity.getEntityName(), StringHelper.unqualify(entity.getEntityName()));
/*  552:     */     }
/*  553: 672 */     Attribute batchNode = node.attribute("batch-size");
/*  554: 673 */     if (batchNode != null) {
/*  555: 673 */       entity.setBatchSize(Integer.parseInt(batchNode.getValue()));
/*  556:     */     }
/*  557: 676 */     Attribute sbuNode = node.attribute("select-before-update");
/*  558: 677 */     if (sbuNode != null) {
/*  559: 677 */       entity.setSelectBeforeUpdate("true".equals(sbuNode.getValue()));
/*  560:     */     }
/*  561: 680 */     Attribute olNode = node.attribute("optimistic-lock");
/*  562: 681 */     entity.setOptimisticLockMode(getOptimisticLockMode(olNode));
/*  563:     */     
/*  564: 683 */     entity.setMetaAttributes(getMetas(node, inheritedMetas));
/*  565:     */     
/*  566:     */ 
/*  567: 686 */     Attribute persisterNode = node.attribute("persister");
/*  568: 687 */     if (persisterNode != null) {
/*  569:     */       try
/*  570:     */       {
/*  571: 689 */         entity.setEntityPersisterClass(ReflectHelper.classForName(persisterNode.getValue()));
/*  572:     */       }
/*  573:     */       catch (ClassNotFoundException cnfe)
/*  574:     */       {
/*  575: 695 */         throw new MappingException("Could not find persister class: " + persisterNode.getValue());
/*  576:     */       }
/*  577:     */     }
/*  578: 701 */     handleCustomSQL(node, entity);
/*  579:     */     
/*  580: 703 */     Iterator tables = node.elementIterator("synchronize");
/*  581: 704 */     while (tables.hasNext()) {
/*  582: 705 */       entity.addSynchronizedTable(((Element)tables.next()).attributeValue("table"));
/*  583:     */     }
/*  584: 708 */     Attribute abstractNode = node.attribute("abstract");
/*  585: 709 */     Boolean isAbstract = "false".equals(abstractNode.getValue()) ? Boolean.FALSE : "true".equals(abstractNode.getValue()) ? Boolean.TRUE : abstractNode == null ? null : null;
/*  586:     */     
/*  587:     */ 
/*  588:     */ 
/*  589:     */ 
/*  590:     */ 
/*  591:     */ 
/*  592: 716 */     entity.setAbstract(isAbstract);
/*  593:     */   }
/*  594:     */   
/*  595:     */   private static void handleCustomSQL(Element node, PersistentClass model)
/*  596:     */     throws MappingException
/*  597:     */   {
/*  598: 721 */     Element element = node.element("sql-insert");
/*  599: 722 */     if (element != null)
/*  600:     */     {
/*  601: 723 */       boolean callable = isCallable(element);
/*  602: 724 */       model.setCustomSQLInsert(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  603:     */     }
/*  604: 727 */     element = node.element("sql-delete");
/*  605: 728 */     if (element != null)
/*  606:     */     {
/*  607: 729 */       boolean callable = isCallable(element);
/*  608: 730 */       model.setCustomSQLDelete(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  609:     */     }
/*  610: 733 */     element = node.element("sql-update");
/*  611: 734 */     if (element != null)
/*  612:     */     {
/*  613: 735 */       boolean callable = isCallable(element);
/*  614: 736 */       model.setCustomSQLUpdate(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  615:     */     }
/*  616: 739 */     element = node.element("loader");
/*  617: 740 */     if (element != null) {
/*  618: 741 */       model.setLoaderName(element.attributeValue("query-ref"));
/*  619:     */     }
/*  620:     */   }
/*  621:     */   
/*  622:     */   private static void handleCustomSQL(Element node, Join model)
/*  623:     */     throws MappingException
/*  624:     */   {
/*  625: 746 */     Element element = node.element("sql-insert");
/*  626: 747 */     if (element != null)
/*  627:     */     {
/*  628: 748 */       boolean callable = isCallable(element);
/*  629: 749 */       model.setCustomSQLInsert(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  630:     */     }
/*  631: 752 */     element = node.element("sql-delete");
/*  632: 753 */     if (element != null)
/*  633:     */     {
/*  634: 754 */       boolean callable = isCallable(element);
/*  635: 755 */       model.setCustomSQLDelete(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  636:     */     }
/*  637: 758 */     element = node.element("sql-update");
/*  638: 759 */     if (element != null)
/*  639:     */     {
/*  640: 760 */       boolean callable = isCallable(element);
/*  641: 761 */       model.setCustomSQLUpdate(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  642:     */     }
/*  643:     */   }
/*  644:     */   
/*  645:     */   private static void handleCustomSQL(Element node, Collection model)
/*  646:     */     throws MappingException
/*  647:     */   {
/*  648: 766 */     Element element = node.element("sql-insert");
/*  649: 767 */     if (element != null)
/*  650:     */     {
/*  651: 768 */       boolean callable = isCallable(element, true);
/*  652: 769 */       model.setCustomSQLInsert(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  653:     */     }
/*  654: 772 */     element = node.element("sql-delete");
/*  655: 773 */     if (element != null)
/*  656:     */     {
/*  657: 774 */       boolean callable = isCallable(element, true);
/*  658: 775 */       model.setCustomSQLDelete(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  659:     */     }
/*  660: 778 */     element = node.element("sql-update");
/*  661: 779 */     if (element != null)
/*  662:     */     {
/*  663: 780 */       boolean callable = isCallable(element, true);
/*  664: 781 */       model.setCustomSQLUpdate(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  665:     */     }
/*  666: 784 */     element = node.element("sql-delete-all");
/*  667: 785 */     if (element != null)
/*  668:     */     {
/*  669: 786 */       boolean callable = isCallable(element, true);
/*  670: 787 */       model.setCustomSQLDeleteAll(element.getTextTrim(), callable, getResultCheckStyle(element, callable));
/*  671:     */     }
/*  672:     */   }
/*  673:     */   
/*  674:     */   private static boolean isCallable(Element e)
/*  675:     */     throws MappingException
/*  676:     */   {
/*  677: 792 */     return isCallable(e, true);
/*  678:     */   }
/*  679:     */   
/*  680:     */   private static boolean isCallable(Element element, boolean supportsCallable)
/*  681:     */     throws MappingException
/*  682:     */   {
/*  683: 797 */     Attribute attrib = element.attribute("callable");
/*  684: 798 */     if ((attrib != null) && ("true".equals(attrib.getValue())))
/*  685:     */     {
/*  686: 799 */       if (!supportsCallable) {
/*  687: 800 */         throw new MappingException("callable attribute not supported yet!");
/*  688:     */       }
/*  689: 802 */       return true;
/*  690:     */     }
/*  691: 804 */     return false;
/*  692:     */   }
/*  693:     */   
/*  694:     */   private static ExecuteUpdateResultCheckStyle getResultCheckStyle(Element element, boolean callable)
/*  695:     */     throws MappingException
/*  696:     */   {
/*  697: 808 */     Attribute attr = element.attribute("check");
/*  698: 809 */     if (attr == null) {
/*  699: 812 */       return ExecuteUpdateResultCheckStyle.COUNT;
/*  700:     */     }
/*  701: 814 */     return ExecuteUpdateResultCheckStyle.fromExternalName(attr.getValue());
/*  702:     */   }
/*  703:     */   
/*  704:     */   public static void bindUnionSubclass(Element node, UnionSubclass unionSubclass, Mappings mappings, java.util.Map inheritedMetas)
/*  705:     */     throws MappingException
/*  706:     */   {
/*  707: 820 */     bindClass(node, unionSubclass, mappings, inheritedMetas);
/*  708: 821 */     inheritedMetas = getMetas(node, inheritedMetas, true);
/*  709:     */     
/*  710: 823 */     Attribute schemaNode = node.attribute("schema");
/*  711: 824 */     String schema = schemaNode == null ? mappings.getSchemaName() : schemaNode.getValue();
/*  712:     */     
/*  713:     */ 
/*  714: 827 */     Attribute catalogNode = node.attribute("catalog");
/*  715: 828 */     String catalog = catalogNode == null ? mappings.getCatalogName() : catalogNode.getValue();
/*  716:     */     
/*  717:     */ 
/*  718: 831 */     Table denormalizedSuperTable = unionSubclass.getSuperclass().getTable();
/*  719: 832 */     Table mytable = mappings.addDenormalizedTable(schema, catalog, getClassTableName(unionSubclass, node, schema, catalog, denormalizedSuperTable, mappings), (unionSubclass.isAbstract() != null) && (unionSubclass.isAbstract().booleanValue()), getSubselect(node), denormalizedSuperTable);
/*  720:     */     
/*  721:     */ 
/*  722:     */ 
/*  723:     */ 
/*  724:     */ 
/*  725:     */ 
/*  726:     */ 
/*  727: 840 */     unionSubclass.setTable(mytable);
/*  728: 842 */     if (LOG.isDebugEnabled()) {
/*  729: 843 */       LOG.debugf("Mapping union-subclass: %s -> %s", unionSubclass.getEntityName(), unionSubclass.getTable().getName());
/*  730:     */     }
/*  731: 846 */     createClassProperties(node, unionSubclass, mappings, inheritedMetas);
/*  732:     */   }
/*  733:     */   
/*  734:     */   public static void bindSubclass(Element node, Subclass subclass, Mappings mappings, java.util.Map inheritedMetas)
/*  735:     */     throws MappingException
/*  736:     */   {
/*  737: 853 */     bindClass(node, subclass, mappings, inheritedMetas);
/*  738: 854 */     inheritedMetas = getMetas(node, inheritedMetas, true);
/*  739: 856 */     if (LOG.isDebugEnabled()) {
/*  740: 857 */       LOG.debugf("Mapping subclass: %s -> %s", subclass.getEntityName(), subclass.getTable().getName());
/*  741:     */     }
/*  742: 861 */     createClassProperties(node, subclass, mappings, inheritedMetas);
/*  743:     */   }
/*  744:     */   
/*  745:     */   private static String getClassTableName(PersistentClass model, Element node, String schema, String catalog, Table denormalizedSuperTable, Mappings mappings)
/*  746:     */   {
/*  747: 871 */     Attribute tableNameNode = node.attribute("table");
/*  748:     */     String physicalTableName;
/*  749:     */     String logicalTableName;
/*  750:     */     String physicalTableName;
/*  751: 874 */     if (tableNameNode == null)
/*  752:     */     {
/*  753: 875 */       String logicalTableName = StringHelper.unqualify(model.getEntityName());
/*  754: 876 */       physicalTableName = mappings.getNamingStrategy().classToTableName(model.getEntityName());
/*  755:     */     }
/*  756:     */     else
/*  757:     */     {
/*  758: 879 */       logicalTableName = tableNameNode.getValue();
/*  759: 880 */       physicalTableName = mappings.getNamingStrategy().tableName(logicalTableName);
/*  760:     */     }
/*  761: 882 */     mappings.addTableBinding(schema, catalog, logicalTableName, physicalTableName, denormalizedSuperTable);
/*  762: 883 */     return physicalTableName;
/*  763:     */   }
/*  764:     */   
/*  765:     */   public static void bindJoinedSubclass(Element node, JoinedSubclass joinedSubclass, Mappings mappings, java.util.Map inheritedMetas)
/*  766:     */     throws MappingException
/*  767:     */   {
/*  768: 889 */     bindClass(node, joinedSubclass, mappings, inheritedMetas);
/*  769: 890 */     inheritedMetas = getMetas(node, inheritedMetas, true);
/*  770:     */     
/*  771:     */ 
/*  772:     */ 
/*  773: 894 */     Attribute schemaNode = node.attribute("schema");
/*  774: 895 */     String schema = schemaNode == null ? mappings.getSchemaName() : schemaNode.getValue();
/*  775:     */     
/*  776:     */ 
/*  777: 898 */     Attribute catalogNode = node.attribute("catalog");
/*  778: 899 */     String catalog = catalogNode == null ? mappings.getCatalogName() : catalogNode.getValue();
/*  779:     */     
/*  780:     */ 
/*  781: 902 */     Table mytable = mappings.addTable(schema, catalog, getClassTableName(joinedSubclass, node, schema, catalog, null, mappings), getSubselect(node), false);
/*  782:     */     
/*  783:     */ 
/*  784:     */ 
/*  785:     */ 
/*  786:     */ 
/*  787:     */ 
/*  788: 909 */     joinedSubclass.setTable(mytable);
/*  789: 910 */     bindComment(mytable, node);
/*  790: 912 */     if (LOG.isDebugEnabled()) {
/*  791: 913 */       LOG.debugf("Mapping joined-subclass: %s -> %s", joinedSubclass.getEntityName(), joinedSubclass.getTable().getName());
/*  792:     */     }
/*  793: 917 */     Element keyNode = node.element("key");
/*  794: 918 */     SimpleValue key = new DependantValue(mappings, mytable, joinedSubclass.getIdentifier());
/*  795: 919 */     joinedSubclass.setKey(key);
/*  796: 920 */     key.setCascadeDeleteEnabled("cascade".equals(keyNode.attributeValue("on-delete")));
/*  797: 921 */     bindSimpleValue(keyNode, key, false, joinedSubclass.getEntityName(), mappings);
/*  798:     */     
/*  799:     */ 
/*  800: 924 */     joinedSubclass.createPrimaryKey();
/*  801: 925 */     joinedSubclass.createForeignKey();
/*  802:     */     
/*  803:     */ 
/*  804: 928 */     Attribute chNode = node.attribute("check");
/*  805: 929 */     if (chNode != null) {
/*  806: 929 */       mytable.addCheckConstraint(chNode.getValue());
/*  807:     */     }
/*  808: 932 */     createClassProperties(node, joinedSubclass, mappings, inheritedMetas);
/*  809:     */   }
/*  810:     */   
/*  811:     */   private static void bindJoin(Element node, Join join, Mappings mappings, java.util.Map inheritedMetas)
/*  812:     */     throws MappingException
/*  813:     */   {
/*  814: 939 */     PersistentClass persistentClass = join.getPersistentClass();
/*  815: 940 */     String path = persistentClass.getEntityName();
/*  816:     */     
/*  817:     */ 
/*  818:     */ 
/*  819: 944 */     Attribute schemaNode = node.attribute("schema");
/*  820: 945 */     String schema = schemaNode == null ? mappings.getSchemaName() : schemaNode.getValue();
/*  821:     */     
/*  822: 947 */     Attribute catalogNode = node.attribute("catalog");
/*  823: 948 */     String catalog = catalogNode == null ? mappings.getCatalogName() : catalogNode.getValue();
/*  824:     */     
/*  825: 950 */     Table primaryTable = persistentClass.getTable();
/*  826: 951 */     Table table = mappings.addTable(schema, catalog, getClassTableName(persistentClass, node, schema, catalog, primaryTable, mappings), getSubselect(node), false);
/*  827:     */     
/*  828:     */ 
/*  829:     */ 
/*  830:     */ 
/*  831:     */ 
/*  832:     */ 
/*  833: 958 */     join.setTable(table);
/*  834: 959 */     bindComment(table, node);
/*  835:     */     
/*  836: 961 */     Attribute fetchNode = node.attribute("fetch");
/*  837: 962 */     if (fetchNode != null) {
/*  838: 963 */       join.setSequentialSelect("select".equals(fetchNode.getValue()));
/*  839:     */     }
/*  840: 966 */     Attribute invNode = node.attribute("inverse");
/*  841: 967 */     if (invNode != null) {
/*  842: 968 */       join.setInverse("true".equals(invNode.getValue()));
/*  843:     */     }
/*  844: 971 */     Attribute nullNode = node.attribute("optional");
/*  845: 972 */     if (nullNode != null) {
/*  846: 973 */       join.setOptional("true".equals(nullNode.getValue()));
/*  847:     */     }
/*  848: 976 */     if (LOG.isDebugEnabled()) {
/*  849: 977 */       LOG.debugf("Mapping class join: %s -> %s", persistentClass.getEntityName(), join.getTable().getName());
/*  850:     */     }
/*  851: 981 */     Element keyNode = node.element("key");
/*  852: 982 */     SimpleValue key = new DependantValue(mappings, table, persistentClass.getIdentifier());
/*  853: 983 */     join.setKey(key);
/*  854: 984 */     key.setCascadeDeleteEnabled("cascade".equals(keyNode.attributeValue("on-delete")));
/*  855: 985 */     bindSimpleValue(keyNode, key, false, persistentClass.getEntityName(), mappings);
/*  856:     */     
/*  857:     */ 
/*  858: 988 */     join.createPrimaryKey();
/*  859: 989 */     join.createForeignKey();
/*  860:     */     
/*  861:     */ 
/*  862: 992 */     Iterator iter = node.elementIterator();
/*  863: 993 */     while (iter.hasNext())
/*  864:     */     {
/*  865: 994 */       Element subnode = (Element)iter.next();
/*  866: 995 */       String name = subnode.getName();
/*  867: 996 */       String propertyName = subnode.attributeValue("name");
/*  868:     */       
/*  869: 998 */       Value value = null;
/*  870: 999 */       if ("many-to-one".equals(name))
/*  871:     */       {
/*  872:1000 */         value = new ManyToOne(mappings, table);
/*  873:1001 */         bindManyToOne(subnode, (ManyToOne)value, propertyName, true, mappings);
/*  874:     */       }
/*  875:1003 */       else if ("any".equals(name))
/*  876:     */       {
/*  877:1004 */         value = new Any(mappings, table);
/*  878:1005 */         bindAny(subnode, (Any)value, true, mappings);
/*  879:     */       }
/*  880:1007 */       else if ("property".equals(name))
/*  881:     */       {
/*  882:1008 */         value = new SimpleValue(mappings, table);
/*  883:1009 */         bindSimpleValue(subnode, (SimpleValue)value, true, propertyName, mappings);
/*  884:     */       }
/*  885:1011 */       else if (("component".equals(name)) || ("dynamic-component".equals(name)))
/*  886:     */       {
/*  887:1012 */         String subpath = StringHelper.qualify(path, propertyName);
/*  888:1013 */         value = new Component(mappings, join);
/*  889:1014 */         bindComponent(subnode, (Component)value, join.getPersistentClass().getClassName(), propertyName, subpath, true, false, mappings, inheritedMetas, false);
/*  890:     */       }
/*  891:1028 */       if (value != null)
/*  892:     */       {
/*  893:1029 */         Property prop = createProperty(value, propertyName, persistentClass.getEntityName(), subnode, mappings, inheritedMetas);
/*  894:     */         
/*  895:1031 */         prop.setOptional(join.isOptional());
/*  896:1032 */         join.addProperty(prop);
/*  897:     */       }
/*  898:     */     }
/*  899:1038 */     handleCustomSQL(node, join);
/*  900:     */   }
/*  901:     */   
/*  902:     */   public static void bindColumns(Element node, SimpleValue simpleValue, boolean isNullable, boolean autoColumn, String propertyPath, Mappings mappings)
/*  903:     */     throws MappingException
/*  904:     */   {
/*  905:1046 */     Table table = simpleValue.getTable();
/*  906:     */     
/*  907:     */ 
/*  908:1049 */     Attribute columnAttribute = node.attribute("column");
/*  909:1050 */     if (columnAttribute == null)
/*  910:     */     {
/*  911:1051 */       Iterator itr = node.elementIterator();
/*  912:1052 */       int count = 0;
/*  913:1053 */       while (itr.hasNext())
/*  914:     */       {
/*  915:1054 */         Element columnElement = (Element)itr.next();
/*  916:1055 */         if (columnElement.getName().equals("column"))
/*  917:     */         {
/*  918:1056 */           Column column = new Column();
/*  919:1057 */           column.setValue(simpleValue);
/*  920:1058 */           column.setTypeIndex(count++);
/*  921:1059 */           bindColumn(columnElement, column, isNullable);
/*  922:1060 */           String columnName = columnElement.attributeValue("name");
/*  923:1061 */           String logicalColumnName = mappings.getNamingStrategy().logicalColumnName(columnName, propertyPath);
/*  924:     */           
/*  925:     */ 
/*  926:1064 */           column.setName(mappings.getNamingStrategy().columnName(columnName));
/*  927:1066 */           if (table != null)
/*  928:     */           {
/*  929:1067 */             table.addColumn(column);
/*  930:     */             
/*  931:     */ 
/*  932:1070 */             mappings.addColumnBinding(logicalColumnName, column, table);
/*  933:     */           }
/*  934:1074 */           simpleValue.addColumn(column);
/*  935:     */           
/*  936:1076 */           bindIndex(columnElement.attribute("index"), table, column, mappings);
/*  937:1077 */           bindIndex(node.attribute("index"), table, column, mappings);
/*  938:     */           
/*  939:1079 */           bindUniqueKey(columnElement.attribute("unique-key"), table, column, mappings);
/*  940:1080 */           bindUniqueKey(node.attribute("unique-key"), table, column, mappings);
/*  941:     */         }
/*  942:1082 */         else if (columnElement.getName().equals("formula"))
/*  943:     */         {
/*  944:1083 */           Formula formula = new Formula();
/*  945:1084 */           formula.setFormula(columnElement.getText());
/*  946:1085 */           simpleValue.addFormula(formula);
/*  947:     */         }
/*  948:     */       }
/*  949:1092 */       Attribute uniqueAttribute = node.attribute("unique");
/*  950:1093 */       if ((uniqueAttribute != null) && ("true".equals(uniqueAttribute.getValue())) && (ManyToOne.class.isInstance(simpleValue))) {
/*  951:1096 */         ((ManyToOne)simpleValue).markAsLogicalOneToOne();
/*  952:     */       }
/*  953:     */     }
/*  954:     */     else
/*  955:     */     {
/*  956:1100 */       if (node.elementIterator("column").hasNext()) {
/*  957:1101 */         throw new MappingException("column attribute may not be used together with <column> subelement");
/*  958:     */       }
/*  959:1104 */       if (node.elementIterator("formula").hasNext()) {
/*  960:1105 */         throw new MappingException("column attribute may not be used together with <formula> subelement");
/*  961:     */       }
/*  962:1109 */       Column column = new Column();
/*  963:1110 */       column.setValue(simpleValue);
/*  964:1111 */       bindColumn(node, column, isNullable);
/*  965:1112 */       if ((column.isUnique()) && (ManyToOne.class.isInstance(simpleValue))) {
/*  966:1113 */         ((ManyToOne)simpleValue).markAsLogicalOneToOne();
/*  967:     */       }
/*  968:1115 */       String columnName = columnAttribute.getValue();
/*  969:1116 */       String logicalColumnName = mappings.getNamingStrategy().logicalColumnName(columnName, propertyPath);
/*  970:     */       
/*  971:     */ 
/*  972:1119 */       column.setName(mappings.getNamingStrategy().columnName(columnName));
/*  973:1120 */       if (table != null)
/*  974:     */       {
/*  975:1121 */         table.addColumn(column);
/*  976:     */         
/*  977:     */ 
/*  978:1124 */         mappings.addColumnBinding(logicalColumnName, column, table);
/*  979:     */       }
/*  980:1126 */       simpleValue.addColumn(column);
/*  981:1127 */       bindIndex(node.attribute("index"), table, column, mappings);
/*  982:1128 */       bindUniqueKey(node.attribute("unique-key"), table, column, mappings);
/*  983:     */     }
/*  984:1131 */     if ((autoColumn) && (simpleValue.getColumnSpan() == 0))
/*  985:     */     {
/*  986:1132 */       Column column = new Column();
/*  987:1133 */       column.setValue(simpleValue);
/*  988:1134 */       bindColumn(node, column, isNullable);
/*  989:1135 */       column.setName(mappings.getNamingStrategy().propertyToColumnName(propertyPath));
/*  990:1136 */       String logicalName = mappings.getNamingStrategy().logicalColumnName(null, propertyPath);
/*  991:1137 */       mappings.addColumnBinding(logicalName, column, table);
/*  992:     */       
/*  993:     */ 
/*  994:     */ 
/*  995:     */ 
/*  996:1142 */       simpleValue.getTable().addColumn(column);
/*  997:1143 */       simpleValue.addColumn(column);
/*  998:1144 */       bindIndex(node.attribute("index"), table, column, mappings);
/*  999:1145 */       bindUniqueKey(node.attribute("unique-key"), table, column, mappings);
/* 1000:     */     }
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   private static void bindIndex(Attribute indexAttribute, Table table, Column column, Mappings mappings)
/* 1004:     */   {
/* 1005:1151 */     if ((indexAttribute != null) && (table != null))
/* 1006:     */     {
/* 1007:1152 */       StringTokenizer tokens = new StringTokenizer(indexAttribute.getValue(), ", ");
/* 1008:1153 */       while (tokens.hasMoreTokens()) {
/* 1009:1154 */         table.getOrCreateIndex(tokens.nextToken()).addColumn(column);
/* 1010:     */       }
/* 1011:     */     }
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   private static void bindUniqueKey(Attribute uniqueKeyAttribute, Table table, Column column, Mappings mappings)
/* 1015:     */   {
/* 1016:1160 */     if ((uniqueKeyAttribute != null) && (table != null))
/* 1017:     */     {
/* 1018:1161 */       StringTokenizer tokens = new StringTokenizer(uniqueKeyAttribute.getValue(), ", ");
/* 1019:1162 */       while (tokens.hasMoreTokens()) {
/* 1020:1163 */         table.getOrCreateUniqueKey(tokens.nextToken()).addColumn(column);
/* 1021:     */       }
/* 1022:     */     }
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public static void bindSimpleValue(Element node, SimpleValue simpleValue, boolean isNullable, String path, Mappings mappings)
/* 1026:     */     throws MappingException
/* 1027:     */   {
/* 1028:1171 */     bindSimpleValueType(node, simpleValue, mappings);
/* 1029:     */     
/* 1030:1173 */     bindColumnsOrFormula(node, simpleValue, path, isNullable, mappings);
/* 1031:     */     
/* 1032:1175 */     Attribute fkNode = node.attribute("foreign-key");
/* 1033:1176 */     if (fkNode != null) {
/* 1034:1176 */       simpleValue.setForeignKeyName(fkNode.getValue());
/* 1035:     */     }
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   private static void bindSimpleValueType(Element node, SimpleValue simpleValue, Mappings mappings)
/* 1039:     */     throws MappingException
/* 1040:     */   {
/* 1041:1181 */     String typeName = null;
/* 1042:     */     
/* 1043:1183 */     Properties parameters = new Properties();
/* 1044:     */     
/* 1045:1185 */     Attribute typeNode = node.attribute("type");
/* 1046:1186 */     if (typeNode == null) {
/* 1047:1187 */       typeNode = node.attribute("id-type");
/* 1048:     */     } else {
/* 1049:1190 */       typeName = typeNode.getValue();
/* 1050:     */     }
/* 1051:1193 */     Element typeChild = node.element("type");
/* 1052:1194 */     if ((typeName == null) && (typeChild != null))
/* 1053:     */     {
/* 1054:1195 */       typeName = typeChild.attribute("name").getValue();
/* 1055:1196 */       Iterator typeParameters = typeChild.elementIterator("param");
/* 1056:1198 */       while (typeParameters.hasNext())
/* 1057:     */       {
/* 1058:1199 */         Element paramElement = (Element)typeParameters.next();
/* 1059:1200 */         parameters.setProperty(paramElement.attributeValue("name"), paramElement.getTextTrim());
/* 1060:     */       }
/* 1061:     */     }
/* 1062:1207 */     TypeDef typeDef = mappings.getTypeDef(typeName);
/* 1063:1208 */     if (typeDef != null)
/* 1064:     */     {
/* 1065:1209 */       typeName = typeDef.getTypeClass();
/* 1066:     */       
/* 1067:     */ 
/* 1068:1212 */       Properties allParameters = new Properties();
/* 1069:1213 */       allParameters.putAll(typeDef.getParameters());
/* 1070:1214 */       allParameters.putAll(parameters);
/* 1071:1215 */       parameters = allParameters;
/* 1072:     */     }
/* 1073:1218 */     if (!parameters.isEmpty()) {
/* 1074:1218 */       simpleValue.setTypeParameters(parameters);
/* 1075:     */     }
/* 1076:1220 */     if (typeName != null) {
/* 1077:1220 */       simpleValue.setTypeName(typeName);
/* 1078:     */     }
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public static void bindProperty(Element node, Property property, Mappings mappings, java.util.Map inheritedMetas)
/* 1082:     */     throws MappingException
/* 1083:     */   {
/* 1084:1229 */     String propName = node.attributeValue("name");
/* 1085:1230 */     property.setName(propName);
/* 1086:1231 */     String nodeName = node.attributeValue("node");
/* 1087:1232 */     if (nodeName == null) {
/* 1088:1232 */       nodeName = propName;
/* 1089:     */     }
/* 1090:1233 */     property.setNodeName(nodeName);
/* 1091:     */     
/* 1092:     */ 
/* 1093:     */ 
/* 1094:     */ 
/* 1095:     */ 
/* 1096:     */ 
/* 1097:1240 */     Attribute accessNode = node.attribute("access");
/* 1098:1241 */     if (accessNode != null) {
/* 1099:1242 */       property.setPropertyAccessorName(accessNode.getValue());
/* 1100:1244 */     } else if (node.getName().equals("properties")) {
/* 1101:1245 */       property.setPropertyAccessorName("embedded");
/* 1102:     */     } else {
/* 1103:1248 */       property.setPropertyAccessorName(mappings.getDefaultAccess());
/* 1104:     */     }
/* 1105:1251 */     Attribute cascadeNode = node.attribute("cascade");
/* 1106:1252 */     property.setCascade(cascadeNode == null ? mappings.getDefaultCascade() : cascadeNode.getValue());
/* 1107:     */     
/* 1108:     */ 
/* 1109:1255 */     Attribute updateNode = node.attribute("update");
/* 1110:1256 */     property.setUpdateable((updateNode == null) || ("true".equals(updateNode.getValue())));
/* 1111:     */     
/* 1112:1258 */     Attribute insertNode = node.attribute("insert");
/* 1113:1259 */     property.setInsertable((insertNode == null) || ("true".equals(insertNode.getValue())));
/* 1114:     */     
/* 1115:1261 */     Attribute lockNode = node.attribute("optimistic-lock");
/* 1116:1262 */     property.setOptimisticLocked((lockNode == null) || ("true".equals(lockNode.getValue())));
/* 1117:     */     
/* 1118:1264 */     Attribute generatedNode = node.attribute("generated");
/* 1119:1265 */     String generationName = generatedNode == null ? null : generatedNode.getValue();
/* 1120:1266 */     PropertyGeneration generation = PropertyGeneration.parse(generationName);
/* 1121:1267 */     property.setGeneration(generation);
/* 1122:1269 */     if ((generation == PropertyGeneration.ALWAYS) || (generation == PropertyGeneration.INSERT))
/* 1123:     */     {
/* 1124:1271 */       if (property.isInsertable()) {
/* 1125:1272 */         if (insertNode == null) {
/* 1126:1275 */           property.setInsertable(false);
/* 1127:     */         } else {
/* 1128:1280 */           throw new MappingException("cannot specify both insert=\"true\" and generated=\"" + generation.getName() + "\" for property: " + propName);
/* 1129:     */         }
/* 1130:     */       }
/* 1131:1289 */       if ((property.isUpdateable()) && (generation == PropertyGeneration.ALWAYS)) {
/* 1132:1290 */         if (updateNode == null) {
/* 1133:1293 */           property.setUpdateable(false);
/* 1134:     */         } else {
/* 1135:1298 */           throw new MappingException("cannot specify both update=\"true\" and generated=\"" + generation.getName() + "\" for property: " + propName);
/* 1136:     */         }
/* 1137:     */       }
/* 1138:     */     }
/* 1139:1307 */     boolean isLazyable = ("property".equals(node.getName())) || ("component".equals(node.getName())) || ("many-to-one".equals(node.getName())) || ("one-to-one".equals(node.getName())) || ("any".equals(node.getName()));
/* 1140:1312 */     if (isLazyable)
/* 1141:     */     {
/* 1142:1313 */       Attribute lazyNode = node.attribute("lazy");
/* 1143:1314 */       property.setLazy((lazyNode != null) && ("true".equals(lazyNode.getValue())));
/* 1144:     */     }
/* 1145:1317 */     if (LOG.isDebugEnabled())
/* 1146:     */     {
/* 1147:1318 */       String msg = "Mapped property: " + property.getName();
/* 1148:1319 */       String columns = columns(property.getValue());
/* 1149:1320 */       if (columns.length() > 0) {
/* 1150:1320 */         msg = msg + " -> " + columns;
/* 1151:     */       }
/* 1152:1323 */       LOG.debug(msg);
/* 1153:     */     }
/* 1154:1326 */     property.setMetaAttributes(getMetas(node, inheritedMetas));
/* 1155:     */   }
/* 1156:     */   
/* 1157:     */   private static String columns(Value val)
/* 1158:     */   {
/* 1159:1331 */     StringBuffer columns = new StringBuffer();
/* 1160:1332 */     Iterator iter = val.getColumnIterator();
/* 1161:1333 */     while (iter.hasNext())
/* 1162:     */     {
/* 1163:1334 */       columns.append(((Selectable)iter.next()).getText());
/* 1164:1335 */       if (iter.hasNext()) {
/* 1165:1335 */         columns.append(", ");
/* 1166:     */       }
/* 1167:     */     }
/* 1168:1337 */     return columns.toString();
/* 1169:     */   }
/* 1170:     */   
/* 1171:     */   public static void bindCollection(Element node, Collection collection, String className, String path, Mappings mappings, java.util.Map inheritedMetas)
/* 1172:     */     throws MappingException
/* 1173:     */   {
/* 1174:1347 */     collection.setRole(path);
/* 1175:     */     
/* 1176:1349 */     Attribute inverseNode = node.attribute("inverse");
/* 1177:1350 */     if (inverseNode != null) {
/* 1178:1351 */       collection.setInverse("true".equals(inverseNode.getValue()));
/* 1179:     */     }
/* 1180:1354 */     Attribute mutableNode = node.attribute("mutable");
/* 1181:1355 */     if (mutableNode != null) {
/* 1182:1356 */       collection.setMutable(!"false".equals(mutableNode.getValue()));
/* 1183:     */     }
/* 1184:1359 */     Attribute olNode = node.attribute("optimistic-lock");
/* 1185:1360 */     collection.setOptimisticLocked((olNode == null) || ("true".equals(olNode.getValue())));
/* 1186:     */     
/* 1187:1362 */     Attribute orderNode = node.attribute("order-by");
/* 1188:1363 */     if (orderNode != null) {
/* 1189:1364 */       collection.setOrderBy(orderNode.getValue());
/* 1190:     */     }
/* 1191:1366 */     Attribute whereNode = node.attribute("where");
/* 1192:1367 */     if (whereNode != null) {
/* 1193:1368 */       collection.setWhere(whereNode.getValue());
/* 1194:     */     }
/* 1195:1370 */     Attribute batchNode = node.attribute("batch-size");
/* 1196:1371 */     if (batchNode != null) {
/* 1197:1372 */       collection.setBatchSize(Integer.parseInt(batchNode.getValue()));
/* 1198:     */     }
/* 1199:1375 */     String nodeName = node.attributeValue("node");
/* 1200:1376 */     if (nodeName == null) {
/* 1201:1376 */       nodeName = node.attributeValue("name");
/* 1202:     */     }
/* 1203:1377 */     collection.setNodeName(nodeName);
/* 1204:1378 */     String embed = node.attributeValue("embed-xml");
/* 1205:1379 */     collection.setEmbedded((embed == null) || ("true".equals(embed)));
/* 1206:     */     
/* 1207:     */ 
/* 1208:     */ 
/* 1209:1383 */     Attribute persisterNode = node.attribute("persister");
/* 1210:1384 */     if (persisterNode != null) {
/* 1211:     */       try
/* 1212:     */       {
/* 1213:1386 */         collection.setCollectionPersisterClass(ReflectHelper.classForName(persisterNode.getValue()));
/* 1214:     */       }
/* 1215:     */       catch (ClassNotFoundException cnfe)
/* 1216:     */       {
/* 1217:1390 */         throw new MappingException("Could not find collection persister class: " + persisterNode.getValue());
/* 1218:     */       }
/* 1219:     */     }
/* 1220:1395 */     Attribute typeNode = node.attribute("collection-type");
/* 1221:1396 */     if (typeNode != null)
/* 1222:     */     {
/* 1223:1397 */       String typeName = typeNode.getValue();
/* 1224:1398 */       TypeDef typeDef = mappings.getTypeDef(typeName);
/* 1225:1399 */       if (typeDef != null)
/* 1226:     */       {
/* 1227:1400 */         collection.setTypeName(typeDef.getTypeClass());
/* 1228:1401 */         collection.setTypeParameters(typeDef.getParameters());
/* 1229:     */       }
/* 1230:     */       else
/* 1231:     */       {
/* 1232:1404 */         collection.setTypeName(typeName);
/* 1233:     */       }
/* 1234:     */     }
/* 1235:1410 */     initOuterJoinFetchSetting(node, collection);
/* 1236:1412 */     if ("subselect".equals(node.attributeValue("fetch")))
/* 1237:     */     {
/* 1238:1413 */       collection.setSubselectLoadable(true);
/* 1239:1414 */       collection.getOwner().setSubselectLoadableCollections(true);
/* 1240:     */     }
/* 1241:1417 */     initLaziness(node, collection, mappings, "true", mappings.isDefaultLazy());
/* 1242:1419 */     if ("extra".equals(node.attributeValue("lazy")))
/* 1243:     */     {
/* 1244:1420 */       collection.setLazy(true);
/* 1245:1421 */       collection.setExtraLazy(true);
/* 1246:     */     }
/* 1247:1424 */     Element oneToManyNode = node.element("one-to-many");
/* 1248:1425 */     if (oneToManyNode != null)
/* 1249:     */     {
/* 1250:1426 */       OneToMany oneToMany = new OneToMany(mappings, collection.getOwner());
/* 1251:1427 */       collection.setElement(oneToMany);
/* 1252:1428 */       bindOneToMany(oneToManyNode, oneToMany, mappings);
/* 1253:     */     }
/* 1254:     */     else
/* 1255:     */     {
/* 1256:1433 */       Attribute tableNode = node.attribute("table");
/* 1257:     */       String tableName;
/* 1258:     */       String tableName;
/* 1259:1435 */       if (tableNode != null)
/* 1260:     */       {
/* 1261:1436 */         tableName = mappings.getNamingStrategy().tableName(tableNode.getValue());
/* 1262:     */       }
/* 1263:     */       else
/* 1264:     */       {
/* 1265:1440 */         Table ownerTable = collection.getOwner().getTable();
/* 1266:     */         
/* 1267:1442 */         String logicalOwnerTableName = ownerTable.getName();
/* 1268:     */         
/* 1269:1444 */         tableName = mappings.getNamingStrategy().collectionTableName(collection.getOwner().getEntityName(), logicalOwnerTableName, null, null, path);
/* 1270:1451 */         if (ownerTable.isQuoted()) {
/* 1271:1452 */           tableName = StringHelper.quote(tableName);
/* 1272:     */         }
/* 1273:     */       }
/* 1274:1455 */       Attribute schemaNode = node.attribute("schema");
/* 1275:1456 */       String schema = schemaNode == null ? mappings.getSchemaName() : schemaNode.getValue();
/* 1276:     */       
/* 1277:     */ 
/* 1278:1459 */       Attribute catalogNode = node.attribute("catalog");
/* 1279:1460 */       String catalog = catalogNode == null ? mappings.getCatalogName() : catalogNode.getValue();
/* 1280:     */       
/* 1281:     */ 
/* 1282:1463 */       Table table = mappings.addTable(schema, catalog, tableName, getSubselect(node), false);
/* 1283:     */       
/* 1284:     */ 
/* 1285:     */ 
/* 1286:     */ 
/* 1287:     */ 
/* 1288:     */ 
/* 1289:1470 */       collection.setCollectionTable(table);
/* 1290:1471 */       bindComment(table, node);
/* 1291:1473 */       if (LOG.isDebugEnabled()) {
/* 1292:1474 */         LOG.debugf("Mapping collection: %s -> %s", collection.getRole(), collection.getCollectionTable().getName());
/* 1293:     */       }
/* 1294:     */     }
/* 1295:1479 */     Attribute sortedAtt = node.attribute("sort");
/* 1296:1481 */     if ((sortedAtt == null) || (sortedAtt.getValue().equals("unsorted")))
/* 1297:     */     {
/* 1298:1482 */       collection.setSorted(false);
/* 1299:     */     }
/* 1300:     */     else
/* 1301:     */     {
/* 1302:1485 */       collection.setSorted(true);
/* 1303:1486 */       String comparatorClassName = sortedAtt.getValue();
/* 1304:1487 */       if (!comparatorClassName.equals("natural")) {
/* 1305:1488 */         collection.setComparatorClassName(comparatorClassName);
/* 1306:     */       }
/* 1307:     */     }
/* 1308:1493 */     Attribute cascadeAtt = node.attribute("cascade");
/* 1309:1494 */     if ((cascadeAtt != null) && (cascadeAtt.getValue().indexOf("delete-orphan") >= 0)) {
/* 1310:1495 */       collection.setOrphanDelete(true);
/* 1311:     */     }
/* 1312:1499 */     handleCustomSQL(node, collection);
/* 1313:1501 */     if ((collection instanceof org.hibernate.mapping.List)) {
/* 1314:1502 */       mappings.addSecondPass(new ListSecondPass(node, mappings, (org.hibernate.mapping.List)collection, inheritedMetas));
/* 1315:1504 */     } else if ((collection instanceof org.hibernate.mapping.Map)) {
/* 1316:1505 */       mappings.addSecondPass(new MapSecondPass(node, mappings, (org.hibernate.mapping.Map)collection, inheritedMetas));
/* 1317:1507 */     } else if ((collection instanceof IdentifierCollection)) {
/* 1318:1508 */       mappings.addSecondPass(new IdentifierCollectionSecondPass(node, mappings, collection, inheritedMetas));
/* 1319:     */     } else {
/* 1320:1516 */       mappings.addSecondPass(new CollectionSecondPass(node, mappings, collection, inheritedMetas));
/* 1321:     */     }
/* 1322:1519 */     Iterator iter = node.elementIterator("filter");
/* 1323:1520 */     while (iter.hasNext())
/* 1324:     */     {
/* 1325:1521 */       Element filter = (Element)iter.next();
/* 1326:1522 */       parseFilter(filter, collection, mappings);
/* 1327:     */     }
/* 1328:1525 */     Iterator tables = node.elementIterator("synchronize");
/* 1329:1526 */     while (tables.hasNext()) {
/* 1330:1527 */       collection.getSynchronizedTables().add(((Element)tables.next()).attributeValue("table"));
/* 1331:     */     }
/* 1332:1531 */     Element element = node.element("loader");
/* 1333:1532 */     if (element != null) {
/* 1334:1533 */       collection.setLoaderName(element.attributeValue("query-ref"));
/* 1335:     */     }
/* 1336:1536 */     collection.setReferencedPropertyName(node.element("key").attributeValue("property-ref"));
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   private static void initLaziness(Element node, Fetchable fetchable, Mappings mappings, String proxyVal, boolean defaultLazy)
/* 1340:     */   {
/* 1341:1546 */     Attribute lazyNode = node.attribute("lazy");
/* 1342:1547 */     boolean isLazyTrue = lazyNode == null ? false : (defaultLazy) && (fetchable.isLazy()) ? true : lazyNode.getValue().equals(proxyVal);
/* 1343:     */     
/* 1344:     */ 
/* 1345:1550 */     fetchable.setLazy(isLazyTrue);
/* 1346:     */   }
/* 1347:     */   
/* 1348:     */   private static void initLaziness(Element node, ToOne fetchable, Mappings mappings, boolean defaultLazy)
/* 1349:     */   {
/* 1350:1559 */     if ("no-proxy".equals(node.attributeValue("lazy")))
/* 1351:     */     {
/* 1352:1560 */       fetchable.setUnwrapProxy(true);
/* 1353:1561 */       fetchable.setLazy(true);
/* 1354:     */     }
/* 1355:     */     else
/* 1356:     */     {
/* 1357:1565 */       initLaziness(node, fetchable, mappings, "proxy", defaultLazy);
/* 1358:     */     }
/* 1359:     */   }
/* 1360:     */   
/* 1361:     */   private static void bindColumnsOrFormula(Element node, SimpleValue simpleValue, String path, boolean isNullable, Mappings mappings)
/* 1362:     */   {
/* 1363:1571 */     Attribute formulaNode = node.attribute("formula");
/* 1364:1572 */     if (formulaNode != null)
/* 1365:     */     {
/* 1366:1573 */       Formula f = new Formula();
/* 1367:1574 */       f.setFormula(formulaNode.getText());
/* 1368:1575 */       simpleValue.addFormula(f);
/* 1369:     */     }
/* 1370:     */     else
/* 1371:     */     {
/* 1372:1578 */       bindColumns(node, simpleValue, isNullable, true, path, mappings);
/* 1373:     */     }
/* 1374:     */   }
/* 1375:     */   
/* 1376:     */   private static void bindComment(Table table, Element node)
/* 1377:     */   {
/* 1378:1583 */     Element comment = node.element("comment");
/* 1379:1584 */     if (comment != null) {
/* 1380:1584 */       table.setComment(comment.getTextTrim());
/* 1381:     */     }
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   public static void bindManyToOne(Element node, ManyToOne manyToOne, String path, boolean isNullable, Mappings mappings)
/* 1385:     */     throws MappingException
/* 1386:     */   {
/* 1387:1590 */     bindColumnsOrFormula(node, manyToOne, path, isNullable, mappings);
/* 1388:1591 */     initOuterJoinFetchSetting(node, manyToOne);
/* 1389:1592 */     initLaziness(node, manyToOne, mappings, true);
/* 1390:     */     
/* 1391:1594 */     Attribute ukName = node.attribute("property-ref");
/* 1392:1595 */     if (ukName != null) {
/* 1393:1596 */       manyToOne.setReferencedPropertyName(ukName.getValue());
/* 1394:     */     }
/* 1395:1599 */     manyToOne.setReferencedEntityName(getEntityName(node, mappings));
/* 1396:     */     
/* 1397:1601 */     String embed = node.attributeValue("embed-xml");
/* 1398:1602 */     manyToOne.setEmbedded((embed == null) || ("true".equals(embed)));
/* 1399:     */     
/* 1400:1604 */     String notFound = node.attributeValue("not-found");
/* 1401:1605 */     manyToOne.setIgnoreNotFound("ignore".equals(notFound));
/* 1402:1607 */     if ((ukName != null) && (!manyToOne.isIgnoreNotFound()) && 
/* 1403:1608 */       (!node.getName().equals("many-to-many"))) {
/* 1404:1609 */       mappings.addSecondPass(new ManyToOneSecondPass(manyToOne));
/* 1405:     */     }
/* 1406:1613 */     Attribute fkNode = node.attribute("foreign-key");
/* 1407:1614 */     if (fkNode != null) {
/* 1408:1614 */       manyToOne.setForeignKeyName(fkNode.getValue());
/* 1409:     */     }
/* 1410:1616 */     String cascade = node.attributeValue("cascade");
/* 1411:1617 */     if ((cascade != null) && (cascade.indexOf("delete-orphan") >= 0) && 
/* 1412:1618 */       (!manyToOne.isLogicalOneToOne())) {
/* 1413:1619 */       throw new MappingException("many-to-one attribute [" + path + "] does not support orphan delete as it is not unique");
/* 1414:     */     }
/* 1415:     */   }
/* 1416:     */   
/* 1417:     */   public static void bindAny(Element node, Any any, boolean isNullable, Mappings mappings)
/* 1418:     */     throws MappingException
/* 1419:     */   {
/* 1420:1628 */     any.setIdentifierType(getTypeFromXML(node));
/* 1421:1629 */     Attribute metaAttribute = node.attribute("meta-type");
/* 1422:1630 */     if (metaAttribute != null)
/* 1423:     */     {
/* 1424:1631 */       any.setMetaType(metaAttribute.getValue());
/* 1425:     */       
/* 1426:1633 */       Iterator iter = node.elementIterator("meta-value");
/* 1427:1634 */       if (iter.hasNext())
/* 1428:     */       {
/* 1429:1635 */         HashMap values = new HashMap();
/* 1430:1636 */         Type metaType = mappings.getTypeResolver().heuristicType(any.getMetaType());
/* 1431:1637 */         while (iter.hasNext())
/* 1432:     */         {
/* 1433:1638 */           Element metaValue = (Element)iter.next();
/* 1434:     */           try
/* 1435:     */           {
/* 1436:1640 */             Object value = ((DiscriminatorType)metaType).stringToObject(metaValue.attributeValue("value"));
/* 1437:     */             
/* 1438:1642 */             String entityName = getClassName(metaValue.attribute("class"), mappings);
/* 1439:1643 */             values.put(value, entityName);
/* 1440:     */           }
/* 1441:     */           catch (ClassCastException cce)
/* 1442:     */           {
/* 1443:1646 */             throw new MappingException("meta-type was not a DiscriminatorType: " + metaType.getName());
/* 1444:     */           }
/* 1445:     */           catch (Exception e)
/* 1446:     */           {
/* 1447:1650 */             throw new MappingException("could not interpret meta-value", e);
/* 1448:     */           }
/* 1449:     */         }
/* 1450:1653 */         any.setMetaValues(values);
/* 1451:     */       }
/* 1452:     */     }
/* 1453:1658 */     bindColumns(node, any, isNullable, false, null, mappings);
/* 1454:     */   }
/* 1455:     */   
/* 1456:     */   public static void bindOneToOne(Element node, OneToOne oneToOne, String path, boolean isNullable, Mappings mappings)
/* 1457:     */     throws MappingException
/* 1458:     */   {
/* 1459:1664 */     bindColumns(node, oneToOne, isNullable, false, null, mappings);
/* 1460:     */     
/* 1461:1666 */     Attribute constrNode = node.attribute("constrained");
/* 1462:1667 */     boolean constrained = (constrNode != null) && (constrNode.getValue().equals("true"));
/* 1463:1668 */     oneToOne.setConstrained(constrained);
/* 1464:     */     
/* 1465:1670 */     oneToOne.setForeignKeyType(constrained ? ForeignKeyDirection.FOREIGN_KEY_FROM_PARENT : ForeignKeyDirection.FOREIGN_KEY_TO_PARENT);
/* 1466:     */     
/* 1467:     */ 
/* 1468:     */ 
/* 1469:1674 */     initOuterJoinFetchSetting(node, oneToOne);
/* 1470:1675 */     initLaziness(node, oneToOne, mappings, true);
/* 1471:     */     
/* 1472:1677 */     oneToOne.setEmbedded("true".equals(node.attributeValue("embed-xml")));
/* 1473:     */     
/* 1474:1679 */     Attribute fkNode = node.attribute("foreign-key");
/* 1475:1680 */     if (fkNode != null) {
/* 1476:1680 */       oneToOne.setForeignKeyName(fkNode.getValue());
/* 1477:     */     }
/* 1478:1682 */     Attribute ukName = node.attribute("property-ref");
/* 1479:1683 */     if (ukName != null) {
/* 1480:1683 */       oneToOne.setReferencedPropertyName(ukName.getValue());
/* 1481:     */     }
/* 1482:1685 */     oneToOne.setPropertyName(node.attributeValue("name"));
/* 1483:     */     
/* 1484:1687 */     oneToOne.setReferencedEntityName(getEntityName(node, mappings));
/* 1485:     */     
/* 1486:1689 */     String cascade = node.attributeValue("cascade");
/* 1487:1690 */     if ((cascade != null) && (cascade.indexOf("delete-orphan") >= 0) && 
/* 1488:1691 */       (oneToOne.isConstrained())) {
/* 1489:1692 */       throw new MappingException("one-to-one attribute [" + path + "] does not support orphan delete as it is constrained");
/* 1490:     */     }
/* 1491:     */   }
/* 1492:     */   
/* 1493:     */   public static void bindOneToMany(Element node, OneToMany oneToMany, Mappings mappings)
/* 1494:     */     throws MappingException
/* 1495:     */   {
/* 1496:1702 */     oneToMany.setReferencedEntityName(getEntityName(node, mappings));
/* 1497:     */     
/* 1498:1704 */     String embed = node.attributeValue("embed-xml");
/* 1499:1705 */     oneToMany.setEmbedded((embed == null) || ("true".equals(embed)));
/* 1500:     */     
/* 1501:1707 */     String notFound = node.attributeValue("not-found");
/* 1502:1708 */     oneToMany.setIgnoreNotFound("ignore".equals(notFound));
/* 1503:     */   }
/* 1504:     */   
/* 1505:     */   public static void bindColumn(Element node, Column column, boolean isNullable)
/* 1506:     */     throws MappingException
/* 1507:     */   {
/* 1508:1713 */     Attribute lengthNode = node.attribute("length");
/* 1509:1714 */     if (lengthNode != null) {
/* 1510:1714 */       column.setLength(Integer.parseInt(lengthNode.getValue()));
/* 1511:     */     }
/* 1512:1715 */     Attribute scalNode = node.attribute("scale");
/* 1513:1716 */     if (scalNode != null) {
/* 1514:1716 */       column.setScale(Integer.parseInt(scalNode.getValue()));
/* 1515:     */     }
/* 1516:1717 */     Attribute precNode = node.attribute("precision");
/* 1517:1718 */     if (precNode != null) {
/* 1518:1718 */       column.setPrecision(Integer.parseInt(precNode.getValue()));
/* 1519:     */     }
/* 1520:1720 */     Attribute nullNode = node.attribute("not-null");
/* 1521:1721 */     column.setNullable(nullNode == null ? isNullable : nullNode.getValue().equals("false"));
/* 1522:     */     
/* 1523:1723 */     Attribute unqNode = node.attribute("unique");
/* 1524:1724 */     if (unqNode != null) {
/* 1525:1724 */       column.setUnique(unqNode.getValue().equals("true"));
/* 1526:     */     }
/* 1527:1726 */     column.setCheckConstraint(node.attributeValue("check"));
/* 1528:1727 */     column.setDefaultValue(node.attributeValue("default"));
/* 1529:     */     
/* 1530:1729 */     Attribute typeNode = node.attribute("sql-type");
/* 1531:1730 */     if (typeNode != null) {
/* 1532:1730 */       column.setSqlType(typeNode.getValue());
/* 1533:     */     }
/* 1534:1732 */     String customWrite = node.attributeValue("write");
/* 1535:1733 */     if ((customWrite != null) && (!customWrite.matches("[^?]*\\?[^?]*"))) {
/* 1536:1734 */       throw new MappingException("write expression must contain exactly one value placeholder ('?') character");
/* 1537:     */     }
/* 1538:1736 */     column.setCustomWrite(customWrite);
/* 1539:1737 */     column.setCustomRead(node.attributeValue("read"));
/* 1540:     */     
/* 1541:1739 */     Element comment = node.element("comment");
/* 1542:1740 */     if (comment != null) {
/* 1543:1740 */       column.setComment(comment.getTextTrim());
/* 1544:     */     }
/* 1545:     */   }
/* 1546:     */   
/* 1547:     */   public static void bindArray(Element node, Array array, String prefix, String path, Mappings mappings, java.util.Map inheritedMetas)
/* 1548:     */     throws MappingException
/* 1549:     */   {
/* 1550:1750 */     bindCollection(node, array, prefix, path, mappings, inheritedMetas);
/* 1551:     */     
/* 1552:1752 */     Attribute att = node.attribute("element-class");
/* 1553:1753 */     if (att != null) {
/* 1554:1753 */       array.setElementClassName(getClassName(att, mappings));
/* 1555:     */     }
/* 1556:     */   }
/* 1557:     */   
/* 1558:     */   private static Class reflectedPropertyClass(String className, String propertyName)
/* 1559:     */     throws MappingException
/* 1560:     */   {
/* 1561:1759 */     if (className == null) {
/* 1562:1759 */       return null;
/* 1563:     */     }
/* 1564:1760 */     return ReflectHelper.reflectedPropertyClass(className, propertyName);
/* 1565:     */   }
/* 1566:     */   
/* 1567:     */   public static void bindComposite(Element node, Component component, String path, boolean isNullable, Mappings mappings, java.util.Map inheritedMetas)
/* 1568:     */     throws MappingException
/* 1569:     */   {
/* 1570:1766 */     bindComponent(node, component, null, null, path, isNullable, false, mappings, inheritedMetas, false);
/* 1571:     */   }
/* 1572:     */   
/* 1573:     */   public static void bindCompositeId(Element node, Component component, PersistentClass persistentClass, String propertyName, Mappings mappings, java.util.Map inheritedMetas)
/* 1574:     */     throws MappingException
/* 1575:     */   {
/* 1576:1784 */     component.setKey(true);
/* 1577:     */     
/* 1578:1786 */     String path = StringHelper.qualify(persistentClass.getEntityName(), propertyName == null ? "id" : propertyName);
/* 1579:     */     
/* 1580:     */ 
/* 1581:     */ 
/* 1582:1790 */     bindComponent(node, component, persistentClass.getClassName(), propertyName, path, false, (node.attribute("class") == null) && (propertyName == null), mappings, inheritedMetas, false);
/* 1583:1804 */     if ("true".equals(node.attributeValue("mapped")))
/* 1584:     */     {
/* 1585:1805 */       if (propertyName != null) {
/* 1586:1806 */         throw new MappingException("cannot combine mapped=\"true\" with specified name");
/* 1587:     */       }
/* 1588:1808 */       Component mapper = new Component(mappings, persistentClass);
/* 1589:1809 */       bindComponent(node, mapper, persistentClass.getClassName(), null, path, false, true, mappings, inheritedMetas, true);
/* 1590:     */       
/* 1591:     */ 
/* 1592:     */ 
/* 1593:     */ 
/* 1594:     */ 
/* 1595:     */ 
/* 1596:     */ 
/* 1597:     */ 
/* 1598:     */ 
/* 1599:     */ 
/* 1600:     */ 
/* 1601:1821 */       persistentClass.setIdentifierMapper(mapper);
/* 1602:1822 */       Property property = new Property();
/* 1603:1823 */       property.setName("_identifierMapper");
/* 1604:1824 */       property.setNodeName("id");
/* 1605:1825 */       property.setUpdateable(false);
/* 1606:1826 */       property.setInsertable(false);
/* 1607:1827 */       property.setValue(mapper);
/* 1608:1828 */       property.setPropertyAccessorName("embedded");
/* 1609:1829 */       persistentClass.addProperty(property);
/* 1610:     */     }
/* 1611:     */   }
/* 1612:     */   
/* 1613:     */   public static void bindComponent(Element node, Component component, String ownerClassName, String parentProperty, String path, boolean isNullable, boolean isEmbedded, Mappings mappings, java.util.Map inheritedMetas, boolean isIdentifierMapper)
/* 1614:     */     throws MappingException
/* 1615:     */   {
/* 1616:1846 */     component.setEmbedded(isEmbedded);
/* 1617:1847 */     component.setRoleName(path);
/* 1618:     */     
/* 1619:1849 */     inheritedMetas = getMetas(node, inheritedMetas);
/* 1620:1850 */     component.setMetaAttributes(inheritedMetas);
/* 1621:     */     
/* 1622:1852 */     Attribute classNode = isIdentifierMapper ? null : node.attribute("class");
/* 1623:1853 */     if (classNode != null)
/* 1624:     */     {
/* 1625:1854 */       component.setComponentClassName(getClassName(classNode, mappings));
/* 1626:     */     }
/* 1627:1856 */     else if ("dynamic-component".equals(node.getName()))
/* 1628:     */     {
/* 1629:1857 */       component.setDynamic(true);
/* 1630:     */     }
/* 1631:1859 */     else if (isEmbedded)
/* 1632:     */     {
/* 1633:1862 */       if (component.getOwner().hasPojoRepresentation()) {
/* 1634:1863 */         component.setComponentClassName(component.getOwner().getClassName());
/* 1635:     */       } else {
/* 1636:1866 */         component.setDynamic(true);
/* 1637:     */       }
/* 1638:     */     }
/* 1639:1871 */     else if (component.getOwner().hasPojoRepresentation())
/* 1640:     */     {
/* 1641:1872 */       Class reflectedClass = reflectedPropertyClass(ownerClassName, parentProperty);
/* 1642:1873 */       if (reflectedClass != null) {
/* 1643:1874 */         component.setComponentClassName(reflectedClass.getName());
/* 1644:     */       }
/* 1645:     */     }
/* 1646:     */     else
/* 1647:     */     {
/* 1648:1878 */       component.setDynamic(true);
/* 1649:     */     }
/* 1650:1882 */     String nodeName = node.attributeValue("node");
/* 1651:1883 */     if (nodeName == null) {
/* 1652:1883 */       nodeName = node.attributeValue("name");
/* 1653:     */     }
/* 1654:1884 */     if (nodeName == null) {
/* 1655:1884 */       nodeName = component.getOwner().getNodeName();
/* 1656:     */     }
/* 1657:1885 */     component.setNodeName(nodeName);
/* 1658:     */     
/* 1659:1887 */     Iterator iter = node.elementIterator();
/* 1660:1888 */     while (iter.hasNext())
/* 1661:     */     {
/* 1662:1890 */       Element subnode = (Element)iter.next();
/* 1663:1891 */       String name = subnode.getName();
/* 1664:1892 */       String propertyName = getPropertyName(subnode);
/* 1665:1893 */       String subpath = propertyName == null ? null : StringHelper.qualify(path, propertyName);
/* 1666:     */       
/* 1667:     */ 
/* 1668:1896 */       CollectionType collectType = CollectionType.collectionTypeFromString(name);
/* 1669:1897 */       Value value = null;
/* 1670:1898 */       if (collectType != null)
/* 1671:     */       {
/* 1672:1899 */         Collection collection = collectType.create(subnode, subpath, component.getOwner(), mappings, inheritedMetas);
/* 1673:     */         
/* 1674:     */ 
/* 1675:     */ 
/* 1676:     */ 
/* 1677:     */ 
/* 1678:1905 */         mappings.addCollection(collection);
/* 1679:1906 */         value = collection;
/* 1680:     */       }
/* 1681:1908 */       else if (("many-to-one".equals(name)) || ("key-many-to-one".equals(name)))
/* 1682:     */       {
/* 1683:1909 */         value = new ManyToOne(mappings, component.getTable());
/* 1684:     */         String relativePath;
/* 1685:     */         String relativePath;
/* 1686:1911 */         if (isEmbedded) {
/* 1687:1912 */           relativePath = propertyName;
/* 1688:     */         } else {
/* 1689:1915 */           relativePath = subpath.substring(component.getOwner().getEntityName().length() + 1);
/* 1690:     */         }
/* 1691:1917 */         bindManyToOne(subnode, (ManyToOne)value, relativePath, isNullable, mappings);
/* 1692:     */       }
/* 1693:1919 */       else if ("one-to-one".equals(name))
/* 1694:     */       {
/* 1695:1920 */         value = new OneToOne(mappings, component.getTable(), component.getOwner());
/* 1696:     */         String relativePath;
/* 1697:     */         String relativePath;
/* 1698:1922 */         if (isEmbedded) {
/* 1699:1923 */           relativePath = propertyName;
/* 1700:     */         } else {
/* 1701:1926 */           relativePath = subpath.substring(component.getOwner().getEntityName().length() + 1);
/* 1702:     */         }
/* 1703:1928 */         bindOneToOne(subnode, (OneToOne)value, relativePath, isNullable, mappings);
/* 1704:     */       }
/* 1705:1930 */       else if ("any".equals(name))
/* 1706:     */       {
/* 1707:1931 */         value = new Any(mappings, component.getTable());
/* 1708:1932 */         bindAny(subnode, (Any)value, isNullable, mappings);
/* 1709:     */       }
/* 1710:1934 */       else if (("property".equals(name)) || ("key-property".equals(name)))
/* 1711:     */       {
/* 1712:1935 */         value = new SimpleValue(mappings, component.getTable());
/* 1713:     */         String relativePath;
/* 1714:     */         String relativePath;
/* 1715:1937 */         if (isEmbedded) {
/* 1716:1938 */           relativePath = propertyName;
/* 1717:     */         } else {
/* 1718:1941 */           relativePath = subpath.substring(component.getOwner().getEntityName().length() + 1);
/* 1719:     */         }
/* 1720:1943 */         bindSimpleValue(subnode, (SimpleValue)value, isNullable, relativePath, mappings);
/* 1721:     */       }
/* 1722:1945 */       else if (("component".equals(name)) || ("dynamic-component".equals(name)) || ("nested-composite-element".equals(name)))
/* 1723:     */       {
/* 1724:1948 */         value = new Component(mappings, component);
/* 1725:1949 */         bindComponent(subnode, (Component)value, component.getComponentClassName(), propertyName, subpath, isNullable, isEmbedded, mappings, inheritedMetas, isIdentifierMapper);
/* 1726:     */       }
/* 1727:1962 */       else if ("parent".equals(name))
/* 1728:     */       {
/* 1729:1963 */         component.setParentProperty(propertyName);
/* 1730:     */       }
/* 1731:1966 */       if (value != null)
/* 1732:     */       {
/* 1733:1967 */         Property property = createProperty(value, propertyName, component.getComponentClassName(), subnode, mappings, inheritedMetas);
/* 1734:1969 */         if (isIdentifierMapper)
/* 1735:     */         {
/* 1736:1970 */           property.setInsertable(false);
/* 1737:1971 */           property.setUpdateable(false);
/* 1738:     */         }
/* 1739:1973 */         component.addProperty(property);
/* 1740:     */       }
/* 1741:     */     }
/* 1742:1977 */     if ("true".equals(node.attributeValue("unique")))
/* 1743:     */     {
/* 1744:1978 */       iter = component.getColumnIterator();
/* 1745:1979 */       ArrayList cols = new ArrayList();
/* 1746:1980 */       while (iter.hasNext()) {
/* 1747:1981 */         cols.add(iter.next());
/* 1748:     */       }
/* 1749:1983 */       component.getOwner().getTable().createUniqueKey(cols);
/* 1750:     */     }
/* 1751:1986 */     iter = node.elementIterator("tuplizer");
/* 1752:1987 */     while (iter.hasNext())
/* 1753:     */     {
/* 1754:1988 */       Element tuplizerElem = (Element)iter.next();
/* 1755:1989 */       EntityMode mode = EntityMode.parse(tuplizerElem.attributeValue("entity-mode"));
/* 1756:1990 */       component.addTuplizer(mode, tuplizerElem.attributeValue("class"));
/* 1757:     */     }
/* 1758:     */   }
/* 1759:     */   
/* 1760:     */   public static String getTypeFromXML(Element node)
/* 1761:     */     throws MappingException
/* 1762:     */   {
/* 1763:1996 */     Attribute typeNode = node.attribute("type");
/* 1764:1997 */     if (typeNode == null) {
/* 1765:1997 */       typeNode = node.attribute("id-type");
/* 1766:     */     }
/* 1767:1998 */     if (typeNode == null) {
/* 1768:1998 */       return null;
/* 1769:     */     }
/* 1770:1999 */     return typeNode.getValue();
/* 1771:     */   }
/* 1772:     */   
/* 1773:     */   private static void initOuterJoinFetchSetting(Element node, Fetchable model)
/* 1774:     */   {
/* 1775:2003 */     Attribute fetchNode = node.attribute("fetch");
/* 1776:     */     
/* 1777:2005 */     boolean lazy = true;
/* 1778:     */     FetchMode fetchStyle;
/* 1779:     */     FetchMode fetchStyle;
/* 1780:2006 */     if (fetchNode == null)
/* 1781:     */     {
/* 1782:2007 */       Attribute jfNode = node.attribute("outer-join");
/* 1783:     */       FetchMode fetchStyle;
/* 1784:2008 */       if (jfNode == null)
/* 1785:     */       {
/* 1786:     */         FetchMode fetchStyle;
/* 1787:2009 */         if ("many-to-many".equals(node.getName()))
/* 1788:     */         {
/* 1789:2013 */           lazy = false;
/* 1790:2014 */           fetchStyle = FetchMode.JOIN;
/* 1791:     */         }
/* 1792:     */         else
/* 1793:     */         {
/* 1794:     */           FetchMode fetchStyle;
/* 1795:2016 */           if ("one-to-one".equals(node.getName()))
/* 1796:     */           {
/* 1797:2020 */             lazy = ((OneToOne)model).isConstrained();
/* 1798:2021 */             fetchStyle = lazy ? FetchMode.DEFAULT : FetchMode.JOIN;
/* 1799:     */           }
/* 1800:     */           else
/* 1801:     */           {
/* 1802:2024 */             fetchStyle = FetchMode.DEFAULT;
/* 1803:     */           }
/* 1804:     */         }
/* 1805:     */       }
/* 1806:     */       else
/* 1807:     */       {
/* 1808:2029 */         String eoj = jfNode.getValue();
/* 1809:     */         FetchMode fetchStyle;
/* 1810:2030 */         if ("auto".equals(eoj))
/* 1811:     */         {
/* 1812:2031 */           fetchStyle = FetchMode.DEFAULT;
/* 1813:     */         }
/* 1814:     */         else
/* 1815:     */         {
/* 1816:2034 */           boolean join = "true".equals(eoj);
/* 1817:2035 */           fetchStyle = join ? FetchMode.JOIN : FetchMode.SELECT;
/* 1818:     */         }
/* 1819:     */       }
/* 1820:     */     }
/* 1821:     */     else
/* 1822:     */     {
/* 1823:2040 */       boolean join = "join".equals(fetchNode.getValue());
/* 1824:     */       
/* 1825:2042 */       fetchStyle = join ? FetchMode.JOIN : FetchMode.SELECT;
/* 1826:     */     }
/* 1827:2044 */     model.setFetchMode(fetchStyle);
/* 1828:2045 */     model.setLazy(lazy);
/* 1829:     */   }
/* 1830:     */   
/* 1831:     */   private static void makeIdentifier(Element node, SimpleValue model, Mappings mappings)
/* 1832:     */   {
/* 1833:2051 */     Element subnode = node.element("generator");
/* 1834:2052 */     if (subnode != null)
/* 1835:     */     {
/* 1836:2053 */       String generatorClass = subnode.attributeValue("class");
/* 1837:2054 */       model.setIdentifierGeneratorStrategy(generatorClass);
/* 1838:     */       
/* 1839:2056 */       Properties params = new Properties();
/* 1840:     */       
/* 1841:2058 */       params.put("identifier_normalizer", mappings.getObjectNameNormalizer());
/* 1842:2060 */       if (mappings.getSchemaName() != null) {
/* 1843:2061 */         params.setProperty("schema", mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(mappings.getSchemaName()));
/* 1844:     */       }
/* 1845:2066 */       if (mappings.getCatalogName() != null) {
/* 1846:2067 */         params.setProperty("catalog", mappings.getObjectNameNormalizer().normalizeIdentifierQuoting(mappings.getCatalogName()));
/* 1847:     */       }
/* 1848:2073 */       Iterator iter = subnode.elementIterator("param");
/* 1849:2074 */       while (iter.hasNext())
/* 1850:     */       {
/* 1851:2075 */         Element childNode = (Element)iter.next();
/* 1852:2076 */         params.setProperty(childNode.attributeValue("name"), childNode.getTextTrim());
/* 1853:     */       }
/* 1854:2079 */       model.setIdentifierGeneratorProperties(params);
/* 1855:     */     }
/* 1856:2082 */     model.getTable().setIdentifierValue(model);
/* 1857:     */     
/* 1858:     */ 
/* 1859:2085 */     Attribute nullValueNode = node.attribute("unsaved-value");
/* 1860:2086 */     if (nullValueNode != null) {
/* 1861:2087 */       model.setNullValue(nullValueNode.getValue());
/* 1862:2090 */     } else if ("assigned".equals(model.getIdentifierGeneratorStrategy())) {
/* 1863:2091 */       model.setNullValue("undefined");
/* 1864:     */     } else {
/* 1865:2094 */       model.setNullValue(null);
/* 1866:     */     }
/* 1867:     */   }
/* 1868:     */   
/* 1869:     */   private static final void makeVersion(Element node, SimpleValue model)
/* 1870:     */   {
/* 1871:2102 */     Attribute nullValueNode = node.attribute("unsaved-value");
/* 1872:2103 */     if (nullValueNode != null) {
/* 1873:2104 */       model.setNullValue(nullValueNode.getValue());
/* 1874:     */     } else {
/* 1875:2107 */       model.setNullValue("undefined");
/* 1876:     */     }
/* 1877:     */   }
/* 1878:     */   
/* 1879:     */   protected static void createClassProperties(Element node, PersistentClass persistentClass, Mappings mappings, java.util.Map inheritedMetas)
/* 1880:     */     throws MappingException
/* 1881:     */   {
/* 1882:2114 */     createClassProperties(node, persistentClass, mappings, inheritedMetas, null, true, true, false);
/* 1883:     */   }
/* 1884:     */   
/* 1885:     */   protected static void createClassProperties(Element node, PersistentClass persistentClass, Mappings mappings, java.util.Map inheritedMetas, UniqueKey uniqueKey, boolean mutable, boolean nullable, boolean naturalId)
/* 1886:     */     throws MappingException
/* 1887:     */   {
/* 1888:2121 */     String entityName = persistentClass.getEntityName();
/* 1889:2122 */     Table table = persistentClass.getTable();
/* 1890:     */     
/* 1891:2124 */     Iterator iter = node.elementIterator();
/* 1892:2125 */     while (iter.hasNext())
/* 1893:     */     {
/* 1894:2126 */       Element subnode = (Element)iter.next();
/* 1895:2127 */       String name = subnode.getName();
/* 1896:2128 */       String propertyName = subnode.attributeValue("name");
/* 1897:     */       
/* 1898:2130 */       CollectionType collectType = CollectionType.collectionTypeFromString(name);
/* 1899:2131 */       Value value = null;
/* 1900:2132 */       if (collectType != null)
/* 1901:     */       {
/* 1902:2133 */         Collection collection = collectType.create(subnode, StringHelper.qualify(entityName, propertyName), persistentClass, mappings, inheritedMetas);
/* 1903:     */         
/* 1904:     */ 
/* 1905:     */ 
/* 1906:     */ 
/* 1907:     */ 
/* 1908:2139 */         mappings.addCollection(collection);
/* 1909:2140 */         value = collection;
/* 1910:     */       }
/* 1911:2142 */       else if ("many-to-one".equals(name))
/* 1912:     */       {
/* 1913:2143 */         value = new ManyToOne(mappings, table);
/* 1914:2144 */         bindManyToOne(subnode, (ManyToOne)value, propertyName, nullable, mappings);
/* 1915:     */       }
/* 1916:2146 */       else if ("any".equals(name))
/* 1917:     */       {
/* 1918:2147 */         value = new Any(mappings, table);
/* 1919:2148 */         bindAny(subnode, (Any)value, nullable, mappings);
/* 1920:     */       }
/* 1921:2150 */       else if ("one-to-one".equals(name))
/* 1922:     */       {
/* 1923:2151 */         value = new OneToOne(mappings, table, persistentClass);
/* 1924:2152 */         bindOneToOne(subnode, (OneToOne)value, propertyName, true, mappings);
/* 1925:     */       }
/* 1926:2154 */       else if ("property".equals(name))
/* 1927:     */       {
/* 1928:2155 */         value = new SimpleValue(mappings, table);
/* 1929:2156 */         bindSimpleValue(subnode, (SimpleValue)value, nullable, propertyName, mappings);
/* 1930:     */       }
/* 1931:2158 */       else if (("component".equals(name)) || ("dynamic-component".equals(name)) || ("properties".equals(name)))
/* 1932:     */       {
/* 1933:2161 */         String subpath = StringHelper.qualify(entityName, propertyName);
/* 1934:2162 */         value = new Component(mappings, persistentClass);
/* 1935:     */         
/* 1936:2164 */         bindComponent(subnode, (Component)value, persistentClass.getClassName(), propertyName, subpath, true, "properties".equals(name), mappings, inheritedMetas, false);
/* 1937:     */       }
/* 1938:2177 */       else if ("join".equals(name))
/* 1939:     */       {
/* 1940:2178 */         Join join = new Join();
/* 1941:2179 */         join.setPersistentClass(persistentClass);
/* 1942:2180 */         bindJoin(subnode, join, mappings, inheritedMetas);
/* 1943:2181 */         persistentClass.addJoin(join);
/* 1944:     */       }
/* 1945:2183 */       else if ("subclass".equals(name))
/* 1946:     */       {
/* 1947:2184 */         handleSubclass(persistentClass, mappings, subnode, inheritedMetas);
/* 1948:     */       }
/* 1949:2186 */       else if ("joined-subclass".equals(name))
/* 1950:     */       {
/* 1951:2187 */         handleJoinedSubclass(persistentClass, mappings, subnode, inheritedMetas);
/* 1952:     */       }
/* 1953:2189 */       else if ("union-subclass".equals(name))
/* 1954:     */       {
/* 1955:2190 */         handleUnionSubclass(persistentClass, mappings, subnode, inheritedMetas);
/* 1956:     */       }
/* 1957:2192 */       else if ("filter".equals(name))
/* 1958:     */       {
/* 1959:2193 */         parseFilter(subnode, persistentClass, mappings);
/* 1960:     */       }
/* 1961:2195 */       else if ("natural-id".equals(name))
/* 1962:     */       {
/* 1963:2196 */         UniqueKey uk = new UniqueKey();
/* 1964:2197 */         uk.setName("_UniqueKey");
/* 1965:2198 */         uk.setTable(table);
/* 1966:     */         
/* 1967:2200 */         boolean mutableId = "true".equals(subnode.attributeValue("mutable"));
/* 1968:2201 */         createClassProperties(subnode, persistentClass, mappings, inheritedMetas, uk, mutableId, false, true);
/* 1969:     */         
/* 1970:     */ 
/* 1971:     */ 
/* 1972:     */ 
/* 1973:     */ 
/* 1974:     */ 
/* 1975:     */ 
/* 1976:     */ 
/* 1977:     */ 
/* 1978:2211 */         table.addUniqueKey(uk);
/* 1979:     */       }
/* 1980:2213 */       else if ("query".equals(name))
/* 1981:     */       {
/* 1982:2214 */         bindNamedQuery(subnode, persistentClass.getEntityName(), mappings);
/* 1983:     */       }
/* 1984:2216 */       else if ("sql-query".equals(name))
/* 1985:     */       {
/* 1986:2217 */         bindNamedSQLQuery(subnode, persistentClass.getEntityName(), mappings);
/* 1987:     */       }
/* 1988:2219 */       else if ("resultset".equals(name))
/* 1989:     */       {
/* 1990:2220 */         bindResultSetMappingDefinition(subnode, persistentClass.getEntityName(), mappings);
/* 1991:     */       }
/* 1992:2223 */       if (value != null)
/* 1993:     */       {
/* 1994:2224 */         Property property = createProperty(value, propertyName, persistentClass.getClassName(), subnode, mappings, inheritedMetas);
/* 1995:2226 */         if (!mutable) {
/* 1996:2226 */           property.setUpdateable(false);
/* 1997:     */         }
/* 1998:2227 */         if (naturalId) {
/* 1999:2227 */           property.setNaturalIdentifier(true);
/* 2000:     */         }
/* 2001:2228 */         persistentClass.addProperty(property);
/* 2002:2229 */         if (uniqueKey != null) {
/* 2003:2229 */           uniqueKey.addColumns(property.getColumnIterator());
/* 2004:     */         }
/* 2005:     */       }
/* 2006:     */     }
/* 2007:     */   }
/* 2008:     */   
/* 2009:     */   private static Property createProperty(Value value, String propertyName, String className, Element subnode, Mappings mappings, java.util.Map inheritedMetas)
/* 2010:     */     throws MappingException
/* 2011:     */   {
/* 2012:2243 */     if (StringHelper.isEmpty(propertyName)) {
/* 2013:2244 */       throw new MappingException(subnode.getName() + " mapping must defined a name attribute [" + className + "]");
/* 2014:     */     }
/* 2015:2247 */     value.setTypeUsingReflection(className, propertyName);
/* 2016:2251 */     if ((value instanceof ToOne))
/* 2017:     */     {
/* 2018:2252 */       ToOne toOne = (ToOne)value;
/* 2019:2253 */       String propertyRef = toOne.getReferencedPropertyName();
/* 2020:2254 */       if (propertyRef != null) {
/* 2021:2255 */         mappings.addUniquePropertyReference(toOne.getReferencedEntityName(), propertyRef);
/* 2022:     */       }
/* 2023:     */     }
/* 2024:2258 */     else if ((value instanceof Collection))
/* 2025:     */     {
/* 2026:2259 */       Collection coll = (Collection)value;
/* 2027:2260 */       String propertyRef = coll.getReferencedPropertyName();
/* 2028:2262 */       if (propertyRef != null) {
/* 2029:2263 */         mappings.addPropertyReference(coll.getOwnerEntityName(), propertyRef);
/* 2030:     */       }
/* 2031:     */     }
/* 2032:2267 */     value.createForeignKey();
/* 2033:2268 */     Property prop = new Property();
/* 2034:2269 */     prop.setValue(value);
/* 2035:2270 */     bindProperty(subnode, prop, mappings, inheritedMetas);
/* 2036:2271 */     return prop;
/* 2037:     */   }
/* 2038:     */   
/* 2039:     */   private static void handleUnionSubclass(PersistentClass model, Mappings mappings, Element subnode, java.util.Map inheritedMetas)
/* 2040:     */     throws MappingException
/* 2041:     */   {
/* 2042:2276 */     UnionSubclass subclass = new UnionSubclass(model);
/* 2043:2277 */     bindUnionSubclass(subnode, subclass, mappings, inheritedMetas);
/* 2044:2278 */     model.addSubclass(subclass);
/* 2045:2279 */     mappings.addClass(subclass);
/* 2046:     */   }
/* 2047:     */   
/* 2048:     */   private static void handleJoinedSubclass(PersistentClass model, Mappings mappings, Element subnode, java.util.Map inheritedMetas)
/* 2049:     */     throws MappingException
/* 2050:     */   {
/* 2051:2284 */     JoinedSubclass subclass = new JoinedSubclass(model);
/* 2052:2285 */     bindJoinedSubclass(subnode, subclass, mappings, inheritedMetas);
/* 2053:2286 */     model.addSubclass(subclass);
/* 2054:2287 */     mappings.addClass(subclass);
/* 2055:     */   }
/* 2056:     */   
/* 2057:     */   private static void handleSubclass(PersistentClass model, Mappings mappings, Element subnode, java.util.Map inheritedMetas)
/* 2058:     */     throws MappingException
/* 2059:     */   {
/* 2060:2292 */     Subclass subclass = new SingleTableSubclass(model);
/* 2061:2293 */     bindSubclass(subnode, subclass, mappings, inheritedMetas);
/* 2062:2294 */     model.addSubclass(subclass);
/* 2063:2295 */     mappings.addClass(subclass);
/* 2064:     */   }
/* 2065:     */   
/* 2066:     */   public static void bindListSecondPass(Element node, org.hibernate.mapping.List list, java.util.Map classes, Mappings mappings, java.util.Map inheritedMetas)
/* 2067:     */     throws MappingException
/* 2068:     */   {
/* 2069:2304 */     bindCollectionSecondPass(node, list, classes, mappings, inheritedMetas);
/* 2070:     */     
/* 2071:2306 */     Element subnode = node.element("list-index");
/* 2072:2307 */     if (subnode == null) {
/* 2073:2307 */       subnode = node.element("index");
/* 2074:     */     }
/* 2075:2308 */     SimpleValue iv = new SimpleValue(mappings, list.getCollectionTable());
/* 2076:2309 */     bindSimpleValue(subnode, iv, list.isOneToMany(), "idx", mappings);
/* 2077:     */     
/* 2078:     */ 
/* 2079:     */ 
/* 2080:     */ 
/* 2081:     */ 
/* 2082:     */ 
/* 2083:2316 */     iv.setTypeName("integer");
/* 2084:2317 */     list.setIndex(iv);
/* 2085:2318 */     String baseIndex = subnode.attributeValue("base");
/* 2086:2319 */     if (baseIndex != null) {
/* 2087:2319 */       list.setBaseIndex(Integer.parseInt(baseIndex));
/* 2088:     */     }
/* 2089:2320 */     list.setIndexNodeName(subnode.attributeValue("node"));
/* 2090:2322 */     if ((list.isOneToMany()) && (!list.getKey().isNullable()) && (!list.isInverse()))
/* 2091:     */     {
/* 2092:2323 */       String entityName = ((OneToMany)list.getElement()).getReferencedEntityName();
/* 2093:2324 */       PersistentClass referenced = mappings.getClass(entityName);
/* 2094:2325 */       IndexBackref ib = new IndexBackref();
/* 2095:2326 */       ib.setName('_' + list.getOwnerEntityName() + "." + node.attributeValue("name") + "IndexBackref");
/* 2096:2327 */       ib.setUpdateable(false);
/* 2097:2328 */       ib.setSelectable(false);
/* 2098:2329 */       ib.setCollectionRole(list.getRole());
/* 2099:2330 */       ib.setEntityName(list.getOwner().getEntityName());
/* 2100:2331 */       ib.setValue(list.getIndex());
/* 2101:     */       
/* 2102:     */ 
/* 2103:2334 */       referenced.addProperty(ib);
/* 2104:     */     }
/* 2105:     */   }
/* 2106:     */   
/* 2107:     */   public static void bindIdentifierCollectionSecondPass(Element node, IdentifierCollection collection, java.util.Map persistentClasses, Mappings mappings, java.util.Map inheritedMetas)
/* 2108:     */     throws MappingException
/* 2109:     */   {
/* 2110:2342 */     bindCollectionSecondPass(node, collection, persistentClasses, mappings, inheritedMetas);
/* 2111:     */     
/* 2112:2344 */     Element subnode = node.element("collection-id");
/* 2113:2345 */     SimpleValue id = new SimpleValue(mappings, collection.getCollectionTable());
/* 2114:2346 */     bindSimpleValue(subnode, id, false, "id", mappings);
/* 2115:     */     
/* 2116:     */ 
/* 2117:     */ 
/* 2118:     */ 
/* 2119:     */ 
/* 2120:     */ 
/* 2121:2353 */     collection.setIdentifier(id);
/* 2122:2354 */     makeIdentifier(subnode, id, mappings);
/* 2123:     */   }
/* 2124:     */   
/* 2125:     */   public static void bindMapSecondPass(Element node, org.hibernate.mapping.Map map, java.util.Map classes, Mappings mappings, java.util.Map inheritedMetas)
/* 2126:     */     throws MappingException
/* 2127:     */   {
/* 2128:2364 */     bindCollectionSecondPass(node, map, classes, mappings, inheritedMetas);
/* 2129:     */     
/* 2130:2366 */     Iterator iter = node.elementIterator();
/* 2131:2367 */     while (iter.hasNext())
/* 2132:     */     {
/* 2133:2368 */       Element subnode = (Element)iter.next();
/* 2134:2369 */       String name = subnode.getName();
/* 2135:2371 */       if (("index".equals(name)) || ("map-key".equals(name)))
/* 2136:     */       {
/* 2137:2372 */         SimpleValue value = new SimpleValue(mappings, map.getCollectionTable());
/* 2138:2373 */         bindSimpleValue(subnode, value, map.isOneToMany(), "idx", mappings);
/* 2139:2380 */         if (!value.isTypeSpecified()) {
/* 2140:2381 */           throw new MappingException("map index element must specify a type: " + map.getRole());
/* 2141:     */         }
/* 2142:2384 */         map.setIndex(value);
/* 2143:2385 */         map.setIndexNodeName(subnode.attributeValue("node"));
/* 2144:     */       }
/* 2145:2387 */       else if (("index-many-to-many".equals(name)) || ("map-key-many-to-many".equals(name)))
/* 2146:     */       {
/* 2147:2388 */         ManyToOne mto = new ManyToOne(mappings, map.getCollectionTable());
/* 2148:2389 */         bindManyToOne(subnode, mto, "idx", map.isOneToMany(), mappings);
/* 2149:     */         
/* 2150:     */ 
/* 2151:     */ 
/* 2152:     */ 
/* 2153:     */ 
/* 2154:     */ 
/* 2155:2396 */         map.setIndex(mto);
/* 2156:     */       }
/* 2157:2399 */       else if (("composite-index".equals(name)) || ("composite-map-key".equals(name)))
/* 2158:     */       {
/* 2159:2400 */         Component component = new Component(mappings, map);
/* 2160:2401 */         bindComposite(subnode, component, map.getRole() + ".index", map.isOneToMany(), mappings, inheritedMetas);
/* 2161:     */         
/* 2162:     */ 
/* 2163:     */ 
/* 2164:     */ 
/* 2165:     */ 
/* 2166:     */ 
/* 2167:     */ 
/* 2168:2409 */         map.setIndex(component);
/* 2169:     */       }
/* 2170:2411 */       else if ("index-many-to-any".equals(name))
/* 2171:     */       {
/* 2172:2412 */         Any any = new Any(mappings, map.getCollectionTable());
/* 2173:2413 */         bindAny(subnode, any, map.isOneToMany(), mappings);
/* 2174:2414 */         map.setIndex(any);
/* 2175:     */       }
/* 2176:     */     }
/* 2177:2419 */     boolean indexIsFormula = false;
/* 2178:2420 */     Iterator colIter = map.getIndex().getColumnIterator();
/* 2179:2421 */     while (colIter.hasNext()) {
/* 2180:2422 */       if (((Selectable)colIter.next()).isFormula()) {
/* 2181:2422 */         indexIsFormula = true;
/* 2182:     */       }
/* 2183:     */     }
/* 2184:2425 */     if ((map.isOneToMany()) && (!map.getKey().isNullable()) && (!map.isInverse()) && (!indexIsFormula))
/* 2185:     */     {
/* 2186:2426 */       String entityName = ((OneToMany)map.getElement()).getReferencedEntityName();
/* 2187:2427 */       PersistentClass referenced = mappings.getClass(entityName);
/* 2188:2428 */       IndexBackref ib = new IndexBackref();
/* 2189:2429 */       ib.setName('_' + map.getOwnerEntityName() + "." + node.attributeValue("name") + "IndexBackref");
/* 2190:2430 */       ib.setUpdateable(false);
/* 2191:2431 */       ib.setSelectable(false);
/* 2192:2432 */       ib.setCollectionRole(map.getRole());
/* 2193:2433 */       ib.setEntityName(map.getOwner().getEntityName());
/* 2194:2434 */       ib.setValue(map.getIndex());
/* 2195:     */       
/* 2196:     */ 
/* 2197:2437 */       referenced.addProperty(ib);
/* 2198:     */     }
/* 2199:     */   }
/* 2200:     */   
/* 2201:     */   public static void bindCollectionSecondPass(Element node, Collection collection, java.util.Map persistentClasses, Mappings mappings, java.util.Map inheritedMetas)
/* 2202:     */     throws MappingException
/* 2203:     */   {
/* 2204:2448 */     if (collection.isOneToMany())
/* 2205:     */     {
/* 2206:2449 */       OneToMany oneToMany = (OneToMany)collection.getElement();
/* 2207:2450 */       String assocClass = oneToMany.getReferencedEntityName();
/* 2208:2451 */       PersistentClass persistentClass = (PersistentClass)persistentClasses.get(assocClass);
/* 2209:2452 */       if (persistentClass == null) {
/* 2210:2453 */         throw new MappingException("Association references unmapped class: " + assocClass);
/* 2211:     */       }
/* 2212:2455 */       oneToMany.setAssociatedClass(persistentClass);
/* 2213:2456 */       collection.setCollectionTable(persistentClass.getTable());
/* 2214:2458 */       if (LOG.isDebugEnabled()) {
/* 2215:2459 */         LOG.debugf("Mapping collection: %s -> %s", collection.getRole(), collection.getCollectionTable().getName());
/* 2216:     */       }
/* 2217:     */     }
/* 2218:2464 */     Attribute chNode = node.attribute("check");
/* 2219:2465 */     if (chNode != null) {
/* 2220:2466 */       collection.getCollectionTable().addCheckConstraint(chNode.getValue());
/* 2221:     */     }
/* 2222:2470 */     Iterator iter = node.elementIterator();
/* 2223:2471 */     while (iter.hasNext())
/* 2224:     */     {
/* 2225:2472 */       Element subnode = (Element)iter.next();
/* 2226:2473 */       String name = subnode.getName();
/* 2227:2475 */       if ("key".equals(name))
/* 2228:     */       {
/* 2229:2477 */         String propRef = collection.getReferencedPropertyName();
/* 2230:     */         KeyValue keyVal;
/* 2231:     */         KeyValue keyVal;
/* 2232:2478 */         if (propRef == null) {
/* 2233:2479 */           keyVal = collection.getOwner().getIdentifier();
/* 2234:     */         } else {
/* 2235:2482 */           keyVal = (KeyValue)collection.getOwner().getRecursiveProperty(propRef).getValue();
/* 2236:     */         }
/* 2237:2484 */         SimpleValue key = new DependantValue(mappings, collection.getCollectionTable(), keyVal);
/* 2238:2485 */         key.setCascadeDeleteEnabled("cascade".equals(subnode.attributeValue("on-delete")));
/* 2239:     */         
/* 2240:2487 */         bindSimpleValue(subnode, key, collection.isOneToMany(), "id", mappings);
/* 2241:     */         
/* 2242:     */ 
/* 2243:     */ 
/* 2244:     */ 
/* 2245:     */ 
/* 2246:     */ 
/* 2247:2494 */         collection.setKey(key);
/* 2248:     */         
/* 2249:2496 */         Attribute notNull = subnode.attribute("not-null");
/* 2250:2497 */         ((DependantValue)key).setNullable((notNull == null) || (notNull.getValue().equals("false")));
/* 2251:     */         
/* 2252:2499 */         Attribute updateable = subnode.attribute("update");
/* 2253:2500 */         ((DependantValue)key).setUpdateable((updateable == null) || (updateable.getValue().equals("true")));
/* 2254:     */       }
/* 2255:2504 */       else if ("element".equals(name))
/* 2256:     */       {
/* 2257:2505 */         SimpleValue elt = new SimpleValue(mappings, collection.getCollectionTable());
/* 2258:2506 */         collection.setElement(elt);
/* 2259:2507 */         bindSimpleValue(subnode, elt, true, "elt", mappings);
/* 2260:     */       }
/* 2261:2515 */       else if ("many-to-many".equals(name))
/* 2262:     */       {
/* 2263:2516 */         ManyToOne element = new ManyToOne(mappings, collection.getCollectionTable());
/* 2264:2517 */         collection.setElement(element);
/* 2265:2518 */         bindManyToOne(subnode, element, "elt", false, mappings);
/* 2266:     */         
/* 2267:     */ 
/* 2268:     */ 
/* 2269:     */ 
/* 2270:     */ 
/* 2271:     */ 
/* 2272:2525 */         bindManyToManySubelements(collection, subnode, mappings);
/* 2273:     */       }
/* 2274:2527 */       else if ("composite-element".equals(name))
/* 2275:     */       {
/* 2276:2528 */         Component element = new Component(mappings, collection);
/* 2277:2529 */         collection.setElement(element);
/* 2278:2530 */         bindComposite(subnode, element, collection.getRole() + ".element", true, mappings, inheritedMetas);
/* 2279:     */       }
/* 2280:2539 */       else if ("many-to-any".equals(name))
/* 2281:     */       {
/* 2282:2540 */         Any element = new Any(mappings, collection.getCollectionTable());
/* 2283:2541 */         collection.setElement(element);
/* 2284:2542 */         bindAny(subnode, element, true, mappings);
/* 2285:     */       }
/* 2286:2544 */       else if ("cache".equals(name))
/* 2287:     */       {
/* 2288:2545 */         collection.setCacheConcurrencyStrategy(subnode.attributeValue("usage"));
/* 2289:2546 */         collection.setCacheRegionName(subnode.attributeValue("region"));
/* 2290:     */       }
/* 2291:2549 */       String nodeName = subnode.attributeValue("node");
/* 2292:2550 */       if (nodeName != null) {
/* 2293:2550 */         collection.setElementNodeName(nodeName);
/* 2294:     */       }
/* 2295:     */     }
/* 2296:2554 */     if ((collection.isOneToMany()) && (!collection.isInverse()) && (!collection.getKey().isNullable()))
/* 2297:     */     {
/* 2298:2558 */       String entityName = ((OneToMany)collection.getElement()).getReferencedEntityName();
/* 2299:2559 */       PersistentClass referenced = mappings.getClass(entityName);
/* 2300:2560 */       Backref prop = new Backref();
/* 2301:2561 */       prop.setName('_' + collection.getOwnerEntityName() + "." + node.attributeValue("name") + "Backref");
/* 2302:2562 */       prop.setUpdateable(false);
/* 2303:2563 */       prop.setSelectable(false);
/* 2304:2564 */       prop.setCollectionRole(collection.getRole());
/* 2305:2565 */       prop.setEntityName(collection.getOwner().getEntityName());
/* 2306:2566 */       prop.setValue(collection.getKey());
/* 2307:2567 */       referenced.addProperty(prop);
/* 2308:     */     }
/* 2309:     */   }
/* 2310:     */   
/* 2311:     */   private static void bindManyToManySubelements(Collection collection, Element manyToManyNode, Mappings model)
/* 2312:     */     throws MappingException
/* 2313:     */   {
/* 2314:2576 */     Attribute where = manyToManyNode.attribute("where");
/* 2315:2577 */     String whereCondition = where == null ? null : where.getValue();
/* 2316:2578 */     collection.setManyToManyWhere(whereCondition);
/* 2317:     */     
/* 2318:     */ 
/* 2319:2581 */     Attribute order = manyToManyNode.attribute("order-by");
/* 2320:2582 */     String orderFragment = order == null ? null : order.getValue();
/* 2321:2583 */     collection.setManyToManyOrdering(orderFragment);
/* 2322:     */     
/* 2323:     */ 
/* 2324:2586 */     Iterator filters = manyToManyNode.elementIterator("filter");
/* 2325:2587 */     if (((filters.hasNext()) || (whereCondition != null)) && (collection.getFetchMode() == FetchMode.JOIN) && (collection.getElement().getFetchMode() != FetchMode.JOIN)) {
/* 2326:2590 */       throw new MappingException("many-to-many defining filter or where without join fetching not valid within collection using join fetching [" + collection.getRole() + "]");
/* 2327:     */     }
/* 2328:2595 */     while (filters.hasNext())
/* 2329:     */     {
/* 2330:2596 */       Element filterElement = (Element)filters.next();
/* 2331:2597 */       String name = filterElement.attributeValue("name");
/* 2332:2598 */       String condition = filterElement.getTextTrim();
/* 2333:2599 */       if (StringHelper.isEmpty(condition)) {
/* 2334:2599 */         condition = filterElement.attributeValue("condition");
/* 2335:     */       }
/* 2336:2600 */       if (StringHelper.isEmpty(condition)) {
/* 2337:2601 */         condition = model.getFilterDefinition(name).getDefaultFilterCondition();
/* 2338:     */       }
/* 2339:2603 */       if (condition == null) {
/* 2340:2604 */         throw new MappingException("no filter condition found for filter: " + name);
/* 2341:     */       }
/* 2342:2606 */       if (LOG.isDebugEnabled()) {
/* 2343:2607 */         LOG.debugf("Applying many-to-many filter [%s] as [%s] to role [%s]", name, condition, collection.getRole());
/* 2344:     */       }
/* 2345:2609 */       collection.addManyToManyFilter(name, condition);
/* 2346:     */     }
/* 2347:     */   }
/* 2348:     */   
/* 2349:     */   public static final FlushMode getFlushMode(String flushMode)
/* 2350:     */   {
/* 2351:2614 */     if (flushMode == null) {
/* 2352:2615 */       return null;
/* 2353:     */     }
/* 2354:2617 */     if ("auto".equals(flushMode)) {
/* 2355:2618 */       return FlushMode.AUTO;
/* 2356:     */     }
/* 2357:2620 */     if ("commit".equals(flushMode)) {
/* 2358:2621 */       return FlushMode.COMMIT;
/* 2359:     */     }
/* 2360:2623 */     if ("never".equals(flushMode)) {
/* 2361:2624 */       return FlushMode.NEVER;
/* 2362:     */     }
/* 2363:2626 */     if ("manual".equals(flushMode)) {
/* 2364:2627 */       return FlushMode.MANUAL;
/* 2365:     */     }
/* 2366:2629 */     if ("always".equals(flushMode)) {
/* 2367:2630 */       return FlushMode.ALWAYS;
/* 2368:     */     }
/* 2369:2633 */     throw new MappingException("unknown flushmode");
/* 2370:     */   }
/* 2371:     */   
/* 2372:     */   private static void bindNamedQuery(Element queryElem, String path, Mappings mappings)
/* 2373:     */   {
/* 2374:2638 */     String queryName = queryElem.attributeValue("name");
/* 2375:2639 */     if (path != null) {
/* 2376:2639 */       queryName = path + '.' + queryName;
/* 2377:     */     }
/* 2378:2640 */     String query = queryElem.getText();
/* 2379:2641 */     LOG.debugf("Named query: %s -> %s", queryName, query);
/* 2380:     */     
/* 2381:2643 */     boolean cacheable = "true".equals(queryElem.attributeValue("cacheable"));
/* 2382:2644 */     String region = queryElem.attributeValue("cache-region");
/* 2383:2645 */     Attribute tAtt = queryElem.attribute("timeout");
/* 2384:2646 */     Integer timeout = tAtt == null ? null : Integer.valueOf(tAtt.getValue());
/* 2385:2647 */     Attribute fsAtt = queryElem.attribute("fetch-size");
/* 2386:2648 */     Integer fetchSize = fsAtt == null ? null : Integer.valueOf(fsAtt.getValue());
/* 2387:2649 */     Attribute roAttr = queryElem.attribute("read-only");
/* 2388:2650 */     boolean readOnly = (roAttr != null) && ("true".equals(roAttr.getValue()));
/* 2389:2651 */     Attribute cacheModeAtt = queryElem.attribute("cache-mode");
/* 2390:2652 */     String cacheMode = cacheModeAtt == null ? null : cacheModeAtt.getValue();
/* 2391:2653 */     Attribute cmAtt = queryElem.attribute("comment");
/* 2392:2654 */     String comment = cmAtt == null ? null : cmAtt.getValue();
/* 2393:     */     
/* 2394:2656 */     NamedQueryDefinition namedQuery = new NamedQueryDefinition(queryName, query, cacheable, region, timeout, fetchSize, getFlushMode(queryElem.attributeValue("flush-mode")), getCacheMode(cacheMode), readOnly, comment, getParameterTypes(queryElem));
/* 2395:     */     
/* 2396:     */ 
/* 2397:     */ 
/* 2398:     */ 
/* 2399:     */ 
/* 2400:     */ 
/* 2401:     */ 
/* 2402:     */ 
/* 2403:     */ 
/* 2404:     */ 
/* 2405:     */ 
/* 2406:     */ 
/* 2407:     */ 
/* 2408:2670 */     mappings.addQuery(namedQuery.getName(), namedQuery);
/* 2409:     */   }
/* 2410:     */   
/* 2411:     */   public static CacheMode getCacheMode(String cacheMode)
/* 2412:     */   {
/* 2413:2674 */     if (cacheMode == null) {
/* 2414:2674 */       return null;
/* 2415:     */     }
/* 2416:2675 */     if ("get".equals(cacheMode)) {
/* 2417:2675 */       return CacheMode.GET;
/* 2418:     */     }
/* 2419:2676 */     if ("ignore".equals(cacheMode)) {
/* 2420:2676 */       return CacheMode.IGNORE;
/* 2421:     */     }
/* 2422:2677 */     if ("normal".equals(cacheMode)) {
/* 2423:2677 */       return CacheMode.NORMAL;
/* 2424:     */     }
/* 2425:2678 */     if ("put".equals(cacheMode)) {
/* 2426:2678 */       return CacheMode.PUT;
/* 2427:     */     }
/* 2428:2679 */     if ("refresh".equals(cacheMode)) {
/* 2429:2679 */       return CacheMode.REFRESH;
/* 2430:     */     }
/* 2431:2680 */     throw new MappingException("Unknown Cache Mode: " + cacheMode);
/* 2432:     */   }
/* 2433:     */   
/* 2434:     */   public static java.util.Map getParameterTypes(Element queryElem)
/* 2435:     */   {
/* 2436:2684 */     java.util.Map result = new LinkedHashMap();
/* 2437:2685 */     Iterator iter = queryElem.elementIterator("query-param");
/* 2438:2686 */     while (iter.hasNext())
/* 2439:     */     {
/* 2440:2687 */       Element element = (Element)iter.next();
/* 2441:2688 */       result.put(element.attributeValue("name"), element.attributeValue("type"));
/* 2442:     */     }
/* 2443:2690 */     return result;
/* 2444:     */   }
/* 2445:     */   
/* 2446:     */   private static void bindResultSetMappingDefinition(Element resultSetElem, String path, Mappings mappings)
/* 2447:     */   {
/* 2448:2694 */     mappings.addSecondPass(new ResultSetMappingSecondPass(resultSetElem, path, mappings));
/* 2449:     */   }
/* 2450:     */   
/* 2451:     */   private static void bindNamedSQLQuery(Element queryElem, String path, Mappings mappings)
/* 2452:     */   {
/* 2453:2698 */     mappings.addSecondPass(new NamedSQLQuerySecondPass(queryElem, path, mappings));
/* 2454:     */   }
/* 2455:     */   
/* 2456:     */   private static String getPropertyName(Element node)
/* 2457:     */   {
/* 2458:2702 */     return node.attributeValue("name");
/* 2459:     */   }
/* 2460:     */   
/* 2461:     */   private static PersistentClass getSuperclass(Mappings mappings, Element subnode)
/* 2462:     */     throws MappingException
/* 2463:     */   {
/* 2464:2707 */     String extendsName = subnode.attributeValue("extends");
/* 2465:2708 */     PersistentClass superModel = mappings.getClass(extendsName);
/* 2466:2709 */     if (superModel == null)
/* 2467:     */     {
/* 2468:2710 */       String qualifiedExtendsName = getClassName(extendsName, mappings);
/* 2469:2711 */       superModel = mappings.getClass(qualifiedExtendsName);
/* 2470:     */     }
/* 2471:2714 */     if (superModel == null) {
/* 2472:2715 */       throw new MappingException("Cannot extend unmapped class " + extendsName);
/* 2473:     */     }
/* 2474:2717 */     return superModel;
/* 2475:     */   }
/* 2476:     */   
/* 2477:     */   static class CollectionSecondPass
/* 2478:     */     extends CollectionSecondPass
/* 2479:     */   {
/* 2480:     */     Element node;
/* 2481:     */     
/* 2482:     */     CollectionSecondPass(Element node, Mappings mappings, Collection collection, java.util.Map inheritedMetas)
/* 2483:     */     {
/* 2484:2724 */       super(collection, inheritedMetas);
/* 2485:2725 */       this.node = node;
/* 2486:     */     }
/* 2487:     */     
/* 2488:     */     public void secondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas)
/* 2489:     */       throws MappingException
/* 2490:     */     {
/* 2491:2730 */       HbmBinder.bindCollectionSecondPass(this.node, this.collection, persistentClasses, this.mappings, inheritedMetas);
/* 2492:     */     }
/* 2493:     */   }
/* 2494:     */   
/* 2495:     */   static class IdentifierCollectionSecondPass
/* 2496:     */     extends HbmBinder.CollectionSecondPass
/* 2497:     */   {
/* 2498:     */     IdentifierCollectionSecondPass(Element node, Mappings mappings, Collection collection, java.util.Map inheritedMetas)
/* 2499:     */     {
/* 2500:2742 */       super(mappings, collection, inheritedMetas);
/* 2501:     */     }
/* 2502:     */     
/* 2503:     */     public void secondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas)
/* 2504:     */       throws MappingException
/* 2505:     */     {
/* 2506:2747 */       HbmBinder.bindIdentifierCollectionSecondPass(this.node, (IdentifierCollection)this.collection, persistentClasses, this.mappings, inheritedMetas);
/* 2507:     */     }
/* 2508:     */   }
/* 2509:     */   
/* 2510:     */   static class MapSecondPass
/* 2511:     */     extends HbmBinder.CollectionSecondPass
/* 2512:     */   {
/* 2513:     */     MapSecondPass(Element node, Mappings mappings, org.hibernate.mapping.Map collection, java.util.Map inheritedMetas)
/* 2514:     */     {
/* 2515:2760 */       super(mappings, collection, inheritedMetas);
/* 2516:     */     }
/* 2517:     */     
/* 2518:     */     public void secondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas)
/* 2519:     */       throws MappingException
/* 2520:     */     {
/* 2521:2765 */       HbmBinder.bindMapSecondPass(this.node, (org.hibernate.mapping.Map)this.collection, persistentClasses, this.mappings, inheritedMetas);
/* 2522:     */     }
/* 2523:     */   }
/* 2524:     */   
/* 2525:     */   static class ManyToOneSecondPass
/* 2526:     */     implements SecondPass
/* 2527:     */   {
/* 2528:     */     private final ManyToOne manyToOne;
/* 2529:     */     
/* 2530:     */     ManyToOneSecondPass(ManyToOne manyToOne)
/* 2531:     */     {
/* 2532:2781 */       this.manyToOne = manyToOne;
/* 2533:     */     }
/* 2534:     */     
/* 2535:     */     public void doSecondPass(java.util.Map persistentClasses)
/* 2536:     */       throws MappingException
/* 2537:     */     {
/* 2538:2785 */       this.manyToOne.createPropertyRefConstraints(persistentClasses);
/* 2539:     */     }
/* 2540:     */   }
/* 2541:     */   
/* 2542:     */   static class ListSecondPass
/* 2543:     */     extends HbmBinder.CollectionSecondPass
/* 2544:     */   {
/* 2545:     */     ListSecondPass(Element node, Mappings mappings, org.hibernate.mapping.List collection, java.util.Map inheritedMetas)
/* 2546:     */     {
/* 2547:2792 */       super(mappings, collection, inheritedMetas);
/* 2548:     */     }
/* 2549:     */     
/* 2550:     */     public void secondPass(java.util.Map persistentClasses, java.util.Map inheritedMetas)
/* 2551:     */       throws MappingException
/* 2552:     */     {
/* 2553:2797 */       HbmBinder.bindListSecondPass(this.node, (org.hibernate.mapping.List)this.collection, persistentClasses, this.mappings, inheritedMetas);
/* 2554:     */     }
/* 2555:     */   }
/* 2556:     */   
/* 2557:     */   static abstract class CollectionType
/* 2558:     */   {
/* 2559:     */     private String xmlTag;
/* 2560:     */     
/* 2561:     */     CollectionType(String xmlTag)
/* 2562:     */     {
/* 2563:2816 */       this.xmlTag = xmlTag;
/* 2564:     */     }
/* 2565:     */     
/* 2566:     */     public String toString()
/* 2567:     */     {
/* 2568:2820 */       return this.xmlTag;
/* 2569:     */     }
/* 2570:     */     
/* 2571:2823 */     private static final CollectionType MAP = new CollectionType("map")
/* 2572:     */     {
/* 2573:     */       public Collection create(Element node, String path, PersistentClass owner, Mappings mappings, java.util.Map inheritedMetas)
/* 2574:     */         throws MappingException
/* 2575:     */       {
/* 2576:2826 */         org.hibernate.mapping.Map map = new org.hibernate.mapping.Map(mappings, owner);
/* 2577:2827 */         HbmBinder.bindCollection(node, map, owner.getEntityName(), path, mappings, inheritedMetas);
/* 2578:2828 */         return map;
/* 2579:     */       }
/* 2580:     */     };
/* 2581:2831 */     private static final CollectionType SET = new CollectionType("set")
/* 2582:     */     {
/* 2583:     */       public Collection create(Element node, String path, PersistentClass owner, Mappings mappings, java.util.Map inheritedMetas)
/* 2584:     */         throws MappingException
/* 2585:     */       {
/* 2586:2834 */         org.hibernate.mapping.Set set = new org.hibernate.mapping.Set(mappings, owner);
/* 2587:2835 */         HbmBinder.bindCollection(node, set, owner.getEntityName(), path, mappings, inheritedMetas);
/* 2588:2836 */         return set;
/* 2589:     */       }
/* 2590:     */     };
/* 2591:2839 */     private static final CollectionType LIST = new CollectionType("list")
/* 2592:     */     {
/* 2593:     */       public Collection create(Element node, String path, PersistentClass owner, Mappings mappings, java.util.Map inheritedMetas)
/* 2594:     */         throws MappingException
/* 2595:     */       {
/* 2596:2842 */         org.hibernate.mapping.List list = new org.hibernate.mapping.List(mappings, owner);
/* 2597:2843 */         HbmBinder.bindCollection(node, list, owner.getEntityName(), path, mappings, inheritedMetas);
/* 2598:2844 */         return list;
/* 2599:     */       }
/* 2600:     */     };
/* 2601:2847 */     private static final CollectionType BAG = new CollectionType("bag")
/* 2602:     */     {
/* 2603:     */       public Collection create(Element node, String path, PersistentClass owner, Mappings mappings, java.util.Map inheritedMetas)
/* 2604:     */         throws MappingException
/* 2605:     */       {
/* 2606:2850 */         Bag bag = new Bag(mappings, owner);
/* 2607:2851 */         HbmBinder.bindCollection(node, bag, owner.getEntityName(), path, mappings, inheritedMetas);
/* 2608:2852 */         return bag;
/* 2609:     */       }
/* 2610:     */     };
/* 2611:2855 */     private static final CollectionType IDBAG = new CollectionType("idbag")
/* 2612:     */     {
/* 2613:     */       public Collection create(Element node, String path, PersistentClass owner, Mappings mappings, java.util.Map inheritedMetas)
/* 2614:     */         throws MappingException
/* 2615:     */       {
/* 2616:2858 */         IdentifierBag bag = new IdentifierBag(mappings, owner);
/* 2617:2859 */         HbmBinder.bindCollection(node, bag, owner.getEntityName(), path, mappings, inheritedMetas);
/* 2618:2860 */         return bag;
/* 2619:     */       }
/* 2620:     */     };
/* 2621:2863 */     private static final CollectionType ARRAY = new CollectionType("array")
/* 2622:     */     {
/* 2623:     */       public Collection create(Element node, String path, PersistentClass owner, Mappings mappings, java.util.Map inheritedMetas)
/* 2624:     */         throws MappingException
/* 2625:     */       {
/* 2626:2866 */         Array array = new Array(mappings, owner);
/* 2627:2867 */         HbmBinder.bindArray(node, array, owner.getEntityName(), path, mappings, inheritedMetas);
/* 2628:2868 */         return array;
/* 2629:     */       }
/* 2630:     */     };
/* 2631:2871 */     private static final CollectionType PRIMITIVE_ARRAY = new CollectionType("primitive-array")
/* 2632:     */     {
/* 2633:     */       public Collection create(Element node, String path, PersistentClass owner, Mappings mappings, java.util.Map inheritedMetas)
/* 2634:     */         throws MappingException
/* 2635:     */       {
/* 2636:2874 */         PrimitiveArray array = new PrimitiveArray(mappings, owner);
/* 2637:2875 */         HbmBinder.bindArray(node, array, owner.getEntityName(), path, mappings, inheritedMetas);
/* 2638:2876 */         return array;
/* 2639:     */       }
/* 2640:     */     };
/* 2641:2879 */     private static final HashMap INSTANCES = new HashMap();
/* 2642:     */     
/* 2643:     */     static
/* 2644:     */     {
/* 2645:2882 */       INSTANCES.put(MAP.toString(), MAP);
/* 2646:2883 */       INSTANCES.put(BAG.toString(), BAG);
/* 2647:2884 */       INSTANCES.put(IDBAG.toString(), IDBAG);
/* 2648:2885 */       INSTANCES.put(SET.toString(), SET);
/* 2649:2886 */       INSTANCES.put(LIST.toString(), LIST);
/* 2650:2887 */       INSTANCES.put(ARRAY.toString(), ARRAY);
/* 2651:2888 */       INSTANCES.put(PRIMITIVE_ARRAY.toString(), PRIMITIVE_ARRAY);
/* 2652:     */     }
/* 2653:     */     
/* 2654:     */     public static CollectionType collectionTypeFromString(String xmlTagName)
/* 2655:     */     {
/* 2656:2892 */       return (CollectionType)INSTANCES.get(xmlTagName);
/* 2657:     */     }
/* 2658:     */     
/* 2659:     */     public abstract Collection create(Element paramElement, String paramString, PersistentClass paramPersistentClass, Mappings paramMappings, java.util.Map paramMap)
/* 2660:     */       throws MappingException;
/* 2661:     */   }
/* 2662:     */   
/* 2663:     */   private static int getOptimisticLockMode(Attribute olAtt)
/* 2664:     */     throws MappingException
/* 2665:     */   {
/* 2666:2898 */     if (olAtt == null) {
/* 2667:2898 */       return 0;
/* 2668:     */     }
/* 2669:2899 */     String olMode = olAtt.getValue();
/* 2670:2900 */     if ((olMode == null) || ("version".equals(olMode))) {
/* 2671:2901 */       return 0;
/* 2672:     */     }
/* 2673:2903 */     if ("dirty".equals(olMode)) {
/* 2674:2904 */       return 1;
/* 2675:     */     }
/* 2676:2906 */     if ("all".equals(olMode)) {
/* 2677:2907 */       return 2;
/* 2678:     */     }
/* 2679:2909 */     if ("none".equals(olMode)) {
/* 2680:2910 */       return -1;
/* 2681:     */     }
/* 2682:2913 */     throw new MappingException("Unsupported optimistic-lock style: " + olMode);
/* 2683:     */   }
/* 2684:     */   
/* 2685:     */   private static final java.util.Map getMetas(Element node, java.util.Map inheritedMeta)
/* 2686:     */   {
/* 2687:2918 */     return getMetas(node, inheritedMeta, false);
/* 2688:     */   }
/* 2689:     */   
/* 2690:     */   public static final java.util.Map getMetas(Element node, java.util.Map inheritedMeta, boolean onlyInheritable)
/* 2691:     */   {
/* 2692:2923 */     java.util.Map map = new HashMap();
/* 2693:2924 */     map.putAll(inheritedMeta);
/* 2694:     */     
/* 2695:2926 */     Iterator iter = node.elementIterator("meta");
/* 2696:2927 */     while (iter.hasNext())
/* 2697:     */     {
/* 2698:2928 */       Element metaNode = (Element)iter.next();
/* 2699:2929 */       boolean inheritable = Boolean.valueOf(metaNode.attributeValue("inherit")).booleanValue();
/* 2700:2932 */       if (!(onlyInheritable & !inheritable))
/* 2701:     */       {
/* 2702:2935 */         String name = metaNode.attributeValue("attribute");
/* 2703:     */         
/* 2704:2937 */         MetaAttribute meta = (MetaAttribute)map.get(name);
/* 2705:2938 */         MetaAttribute inheritedAttribute = (MetaAttribute)inheritedMeta.get(name);
/* 2706:2939 */         if (meta == null)
/* 2707:     */         {
/* 2708:2940 */           meta = new MetaAttribute(name);
/* 2709:2941 */           map.put(name, meta);
/* 2710:     */         }
/* 2711:2942 */         else if (meta == inheritedAttribute)
/* 2712:     */         {
/* 2713:2943 */           meta = new MetaAttribute(name);
/* 2714:2944 */           map.put(name, meta);
/* 2715:     */         }
/* 2716:2946 */         meta.addValue(metaNode.getText());
/* 2717:     */       }
/* 2718:     */     }
/* 2719:2948 */     return map;
/* 2720:     */   }
/* 2721:     */   
/* 2722:     */   public static String getEntityName(Element elem, Mappings model)
/* 2723:     */   {
/* 2724:2952 */     String entityName = elem.attributeValue("entity-name");
/* 2725:2953 */     return entityName == null ? getClassName(elem.attribute("class"), model) : entityName;
/* 2726:     */   }
/* 2727:     */   
/* 2728:     */   private static String getClassName(Attribute att, Mappings model)
/* 2729:     */   {
/* 2730:2957 */     if (att == null) {
/* 2731:2957 */       return null;
/* 2732:     */     }
/* 2733:2958 */     return getClassName(att.getValue(), model);
/* 2734:     */   }
/* 2735:     */   
/* 2736:     */   public static String getClassName(String unqualifiedName, Mappings model)
/* 2737:     */   {
/* 2738:2962 */     return getClassName(unqualifiedName, model.getDefaultPackage());
/* 2739:     */   }
/* 2740:     */   
/* 2741:     */   public static String getClassName(String unqualifiedName, String defaultPackage)
/* 2742:     */   {
/* 2743:2966 */     if (unqualifiedName == null) {
/* 2744:2966 */       return null;
/* 2745:     */     }
/* 2746:2967 */     if ((unqualifiedName.indexOf('.') < 0) && (defaultPackage != null)) {
/* 2747:2968 */       return defaultPackage + '.' + unqualifiedName;
/* 2748:     */     }
/* 2749:2970 */     return unqualifiedName;
/* 2750:     */   }
/* 2751:     */   
/* 2752:     */   private static void parseFilterDef(Element element, Mappings mappings)
/* 2753:     */   {
/* 2754:2974 */     String name = element.attributeValue("name");
/* 2755:2975 */     LOG.debugf("Parsing filter-def [%s]", name);
/* 2756:2976 */     String defaultCondition = element.getTextTrim();
/* 2757:2977 */     if (StringHelper.isEmpty(defaultCondition)) {
/* 2758:2978 */       defaultCondition = element.attributeValue("condition");
/* 2759:     */     }
/* 2760:2980 */     HashMap paramMappings = new HashMap();
/* 2761:2981 */     Iterator params = element.elementIterator("filter-param");
/* 2762:2982 */     while (params.hasNext())
/* 2763:     */     {
/* 2764:2983 */       Element param = (Element)params.next();
/* 2765:2984 */       String paramName = param.attributeValue("name");
/* 2766:2985 */       String paramType = param.attributeValue("type");
/* 2767:2986 */       LOG.debugf("Adding filter parameter : %s -> %s", paramName, paramType);
/* 2768:2987 */       Type heuristicType = mappings.getTypeResolver().heuristicType(paramType);
/* 2769:2988 */       LOG.debugf("Parameter heuristic type : %s", heuristicType);
/* 2770:2989 */       paramMappings.put(paramName, heuristicType);
/* 2771:     */     }
/* 2772:2991 */     LOG.debugf("Parsed filter-def [%s]", name);
/* 2773:2992 */     FilterDefinition def = new FilterDefinition(name, defaultCondition, paramMappings);
/* 2774:2993 */     mappings.addFilterDefinition(def);
/* 2775:     */   }
/* 2776:     */   
/* 2777:     */   private static void parseFilter(Element filterElement, Filterable filterable, Mappings model)
/* 2778:     */   {
/* 2779:2997 */     String name = filterElement.attributeValue("name");
/* 2780:2998 */     String condition = filterElement.getTextTrim();
/* 2781:2999 */     if (StringHelper.isEmpty(condition)) {
/* 2782:3000 */       condition = filterElement.attributeValue("condition");
/* 2783:     */     }
/* 2784:3008 */     if (StringHelper.isEmpty(condition)) {
/* 2785:3009 */       condition = model.getFilterDefinition(name).getDefaultFilterCondition();
/* 2786:     */     }
/* 2787:3011 */     if (condition == null) {
/* 2788:3012 */       throw new MappingException("no filter condition found for filter: " + name);
/* 2789:     */     }
/* 2790:3014 */     LOG.debugf("Applying filter [%s] as [%s]", name, condition);
/* 2791:3015 */     filterable.addFilter(name, condition);
/* 2792:     */   }
/* 2793:     */   
/* 2794:     */   private static void parseFetchProfile(Element element, Mappings mappings, String containingEntityName)
/* 2795:     */   {
/* 2796:3019 */     String profileName = element.attributeValue("name");
/* 2797:3020 */     FetchProfile profile = mappings.findOrCreateFetchProfile(profileName, MetadataSource.HBM);
/* 2798:3021 */     Iterator itr = element.elementIterator("fetch");
/* 2799:3022 */     while (itr.hasNext())
/* 2800:     */     {
/* 2801:3023 */       Element fetchElement = (Element)itr.next();
/* 2802:3024 */       String association = fetchElement.attributeValue("association");
/* 2803:3025 */       String style = fetchElement.attributeValue("style");
/* 2804:3026 */       String entityName = fetchElement.attributeValue("entity");
/* 2805:3027 */       if (entityName == null) {
/* 2806:3028 */         entityName = containingEntityName;
/* 2807:     */       }
/* 2808:3030 */       if (entityName == null) {
/* 2809:3031 */         throw new MappingException("could not determine entity for fetch-profile fetch [" + profileName + "]:[" + association + "]");
/* 2810:     */       }
/* 2811:3033 */       profile.addFetch(entityName, association, style);
/* 2812:     */     }
/* 2813:     */   }
/* 2814:     */   
/* 2815:     */   private static String getSubselect(Element element)
/* 2816:     */   {
/* 2817:3038 */     String subselect = element.attributeValue("subselect");
/* 2818:3039 */     if (subselect != null) {
/* 2819:3040 */       return subselect;
/* 2820:     */     }
/* 2821:3043 */     Element subselectElement = element.element("subselect");
/* 2822:3044 */     return subselectElement == null ? null : subselectElement.getText();
/* 2823:     */   }
/* 2824:     */   
/* 2825:     */   public static java.util.List<String> getExtendsNeeded(XmlDocument metadataXml, Mappings mappings)
/* 2826:     */   {
/* 2827:3057 */     java.util.List<String> extendz = new ArrayList();
/* 2828:3058 */     Iterator[] subclasses = new Iterator[3];
/* 2829:3059 */     Element hmNode = metadataXml.getDocumentTree().getRootElement();
/* 2830:     */     
/* 2831:3061 */     Attribute packNode = hmNode.attribute("package");
/* 2832:3062 */     final String packageName = packNode == null ? null : packNode.getValue();
/* 2833:3063 */     if (packageName != null) {
/* 2834:3064 */       mappings.setDefaultPackage(packageName);
/* 2835:     */     }
/* 2836:3070 */     subclasses[0] = hmNode.elementIterator("subclass");
/* 2837:3071 */     subclasses[1] = hmNode.elementIterator("joined-subclass");
/* 2838:3072 */     subclasses[2] = hmNode.elementIterator("union-subclass");
/* 2839:     */     
/* 2840:3074 */     Iterator iterator = new JoinedIterator(subclasses);
/* 2841:3075 */     while (iterator.hasNext())
/* 2842:     */     {
/* 2843:3076 */       Element element = (Element)iterator.next();
/* 2844:3077 */       String extendsName = element.attributeValue("extends");
/* 2845:3080 */       if ((mappings.getClass(extendsName) == null) && (mappings.getClass(getClassName(extendsName, mappings)) == null)) {
/* 2846:3081 */         extendz.add(extendsName);
/* 2847:     */       }
/* 2848:     */     }
/* 2849:3085 */     if (!extendz.isEmpty())
/* 2850:     */     {
/* 2851:3093 */       java.util.Set<String> set = new HashSet(extendz);
/* 2852:3094 */       EntityElementHandler handler = new EntityElementHandler()
/* 2853:     */       {
/* 2854:     */         public void handleEntity(String entityName, String className, Mappings mappings)
/* 2855:     */         {
/* 2856:3096 */           if (entityName != null)
/* 2857:     */           {
/* 2858:3097 */             this.val$set.remove(entityName);
/* 2859:     */           }
/* 2860:     */           else
/* 2861:     */           {
/* 2862:3100 */             String fqn = HbmBinder.getClassName(className, packageName);
/* 2863:3101 */             this.val$set.remove(fqn);
/* 2864:3102 */             if (packageName != null) {
/* 2865:3103 */               this.val$set.remove(StringHelper.unqualify(fqn));
/* 2866:     */             }
/* 2867:     */           }
/* 2868:     */         }
/* 2869:3107 */       };
/* 2870:3108 */       recognizeEntities(mappings, hmNode, handler);
/* 2871:3109 */       extendz.clear();
/* 2872:3110 */       extendz.addAll(set);
/* 2873:     */     }
/* 2874:3113 */     return extendz;
/* 2875:     */   }
/* 2876:     */   
/* 2877:     */   private static void recognizeEntities(Mappings mappings, Element startNode, EntityElementHandler handler)
/* 2878:     */   {
/* 2879:3129 */     Iterator[] classes = new Iterator[4];
/* 2880:3130 */     classes[0] = startNode.elementIterator("class");
/* 2881:3131 */     classes[1] = startNode.elementIterator("subclass");
/* 2882:3132 */     classes[2] = startNode.elementIterator("joined-subclass");
/* 2883:3133 */     classes[3] = startNode.elementIterator("union-subclass");
/* 2884:     */     
/* 2885:3135 */     Iterator classIterator = new JoinedIterator(classes);
/* 2886:3136 */     while (classIterator.hasNext())
/* 2887:     */     {
/* 2888:3137 */       Element element = (Element)classIterator.next();
/* 2889:3138 */       handler.handleEntity(element.attributeValue("entity-name"), element.attributeValue("name"), mappings);
/* 2890:     */       
/* 2891:     */ 
/* 2892:     */ 
/* 2893:     */ 
/* 2894:3143 */       recognizeEntities(mappings, element, handler);
/* 2895:     */     }
/* 2896:     */   }
/* 2897:     */   
/* 2898:     */   private static abstract interface EntityElementHandler
/* 2899:     */   {
/* 2900:     */     public abstract void handleEntity(String paramString1, String paramString2, Mappings paramMappings);
/* 2901:     */   }
/* 2902:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.HbmBinder
 * JD-Core Version:    0.7.0.1
 */
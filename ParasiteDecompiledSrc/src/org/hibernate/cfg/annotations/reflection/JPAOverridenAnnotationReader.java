/*    1:     */ package org.hibernate.cfg.annotations.reflection;
/*    2:     */ 
/*    3:     */ import java.beans.Introspector;
/*    4:     */ import java.lang.annotation.Annotation;
/*    5:     */ import java.lang.reflect.AccessibleObject;
/*    6:     */ import java.lang.reflect.AnnotatedElement;
/*    7:     */ import java.lang.reflect.Field;
/*    8:     */ import java.lang.reflect.Method;
/*    9:     */ import java.util.ArrayList;
/*   10:     */ import java.util.HashMap;
/*   11:     */ import java.util.HashSet;
/*   12:     */ import java.util.Iterator;
/*   13:     */ import java.util.List;
/*   14:     */ import java.util.Map;
/*   15:     */ import java.util.Set;
/*   16:     */ import javax.persistence.Access;
/*   17:     */ import javax.persistence.AccessType;
/*   18:     */ import javax.persistence.AssociationOverride;
/*   19:     */ import javax.persistence.AssociationOverrides;
/*   20:     */ import javax.persistence.AttributeOverride;
/*   21:     */ import javax.persistence.AttributeOverrides;
/*   22:     */ import javax.persistence.Basic;
/*   23:     */ import javax.persistence.CascadeType;
/*   24:     */ import javax.persistence.CollectionTable;
/*   25:     */ import javax.persistence.Column;
/*   26:     */ import javax.persistence.ColumnResult;
/*   27:     */ import javax.persistence.DiscriminatorColumn;
/*   28:     */ import javax.persistence.DiscriminatorType;
/*   29:     */ import javax.persistence.DiscriminatorValue;
/*   30:     */ import javax.persistence.ElementCollection;
/*   31:     */ import javax.persistence.Embeddable;
/*   32:     */ import javax.persistence.Embedded;
/*   33:     */ import javax.persistence.EmbeddedId;
/*   34:     */ import javax.persistence.Entity;
/*   35:     */ import javax.persistence.EntityListeners;
/*   36:     */ import javax.persistence.EntityResult;
/*   37:     */ import javax.persistence.EnumType;
/*   38:     */ import javax.persistence.Enumerated;
/*   39:     */ import javax.persistence.ExcludeDefaultListeners;
/*   40:     */ import javax.persistence.ExcludeSuperclassListeners;
/*   41:     */ import javax.persistence.FetchType;
/*   42:     */ import javax.persistence.FieldResult;
/*   43:     */ import javax.persistence.GeneratedValue;
/*   44:     */ import javax.persistence.GenerationType;
/*   45:     */ import javax.persistence.Id;
/*   46:     */ import javax.persistence.IdClass;
/*   47:     */ import javax.persistence.Inheritance;
/*   48:     */ import javax.persistence.InheritanceType;
/*   49:     */ import javax.persistence.JoinColumn;
/*   50:     */ import javax.persistence.JoinColumns;
/*   51:     */ import javax.persistence.JoinTable;
/*   52:     */ import javax.persistence.Lob;
/*   53:     */ import javax.persistence.ManyToMany;
/*   54:     */ import javax.persistence.ManyToOne;
/*   55:     */ import javax.persistence.MapKey;
/*   56:     */ import javax.persistence.MapKeyClass;
/*   57:     */ import javax.persistence.MapKeyColumn;
/*   58:     */ import javax.persistence.MapKeyEnumerated;
/*   59:     */ import javax.persistence.MapKeyJoinColumn;
/*   60:     */ import javax.persistence.MapKeyJoinColumns;
/*   61:     */ import javax.persistence.MapKeyTemporal;
/*   62:     */ import javax.persistence.MappedSuperclass;
/*   63:     */ import javax.persistence.MapsId;
/*   64:     */ import javax.persistence.NamedNativeQueries;
/*   65:     */ import javax.persistence.NamedNativeQuery;
/*   66:     */ import javax.persistence.NamedQueries;
/*   67:     */ import javax.persistence.NamedQuery;
/*   68:     */ import javax.persistence.OneToMany;
/*   69:     */ import javax.persistence.OneToOne;
/*   70:     */ import javax.persistence.OrderBy;
/*   71:     */ import javax.persistence.OrderColumn;
/*   72:     */ import javax.persistence.PostLoad;
/*   73:     */ import javax.persistence.PostPersist;
/*   74:     */ import javax.persistence.PostRemove;
/*   75:     */ import javax.persistence.PostUpdate;
/*   76:     */ import javax.persistence.PrePersist;
/*   77:     */ import javax.persistence.PreRemove;
/*   78:     */ import javax.persistence.PreUpdate;
/*   79:     */ import javax.persistence.PrimaryKeyJoinColumn;
/*   80:     */ import javax.persistence.PrimaryKeyJoinColumns;
/*   81:     */ import javax.persistence.QueryHint;
/*   82:     */ import javax.persistence.SecondaryTable;
/*   83:     */ import javax.persistence.SecondaryTables;
/*   84:     */ import javax.persistence.SequenceGenerator;
/*   85:     */ import javax.persistence.SqlResultSetMapping;
/*   86:     */ import javax.persistence.SqlResultSetMappings;
/*   87:     */ import javax.persistence.Table;
/*   88:     */ import javax.persistence.TableGenerator;
/*   89:     */ import javax.persistence.Temporal;
/*   90:     */ import javax.persistence.TemporalType;
/*   91:     */ import javax.persistence.Transient;
/*   92:     */ import javax.persistence.UniqueConstraint;
/*   93:     */ import javax.persistence.Version;
/*   94:     */ import org.dom4j.Attribute;
/*   95:     */ import org.dom4j.Element;
/*   96:     */ import org.hibernate.AnnotationException;
/*   97:     */ import org.hibernate.annotations.Cascade;
/*   98:     */ import org.hibernate.annotations.Columns;
/*   99:     */ import org.hibernate.annotations.common.annotationfactory.AnnotationDescriptor;
/*  100:     */ import org.hibernate.annotations.common.annotationfactory.AnnotationFactory;
/*  101:     */ import org.hibernate.annotations.common.reflection.AnnotationReader;
/*  102:     */ import org.hibernate.annotations.common.reflection.Filter;
/*  103:     */ import org.hibernate.annotations.common.reflection.ReflectionUtil;
/*  104:     */ import org.hibernate.internal.CoreMessageLogger;
/*  105:     */ import org.hibernate.internal.util.ReflectHelper;
/*  106:     */ import org.hibernate.internal.util.StringHelper;
/*  107:     */ import org.jboss.logging.Logger;
/*  108:     */ 
/*  109:     */ public class JPAOverridenAnnotationReader
/*  110:     */   implements AnnotationReader
/*  111:     */ {
/*  112: 146 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JPAOverridenAnnotationReader.class.getName());
/*  113:     */   private static final Map<Class, String> annotationToXml;
/*  114:     */   private static final String SCHEMA_VALIDATION = "Activate schema validation for more information";
/*  115: 150 */   private static final Filter FILTER = new Filter()
/*  116:     */   {
/*  117:     */     public boolean returnStatic()
/*  118:     */     {
/*  119: 152 */       return false;
/*  120:     */     }
/*  121:     */     
/*  122:     */     public boolean returnTransient()
/*  123:     */     {
/*  124: 156 */       return false;
/*  125:     */     }
/*  126:     */   };
/*  127:     */   private XMLContext xmlContext;
/*  128:     */   private String className;
/*  129:     */   private String propertyName;
/*  130:     */   private PropertyType propertyType;
/*  131:     */   private transient Annotation[] annotations;
/*  132:     */   private transient Map<Class, Annotation> annotationsMap;
/*  133:     */   private static final String WORD_SEPARATOR = "-";
/*  134:     */   private transient List<Element> elementsForProperty;
/*  135:     */   private AccessibleObject mirroredAttribute;
/*  136:     */   private final AnnotatedElement element;
/*  137:     */   
/*  138:     */   static
/*  139:     */   {
/*  140: 161 */     annotationToXml = new HashMap();
/*  141: 162 */     annotationToXml.put(Entity.class, "entity");
/*  142: 163 */     annotationToXml.put(MappedSuperclass.class, "mapped-superclass");
/*  143: 164 */     annotationToXml.put(Embeddable.class, "embeddable");
/*  144: 165 */     annotationToXml.put(Table.class, "table");
/*  145: 166 */     annotationToXml.put(SecondaryTable.class, "secondary-table");
/*  146: 167 */     annotationToXml.put(SecondaryTables.class, "secondary-table");
/*  147: 168 */     annotationToXml.put(PrimaryKeyJoinColumn.class, "primary-key-join-column");
/*  148: 169 */     annotationToXml.put(PrimaryKeyJoinColumns.class, "primary-key-join-column");
/*  149: 170 */     annotationToXml.put(IdClass.class, "id-class");
/*  150: 171 */     annotationToXml.put(Inheritance.class, "inheritance");
/*  151: 172 */     annotationToXml.put(DiscriminatorValue.class, "discriminator-value");
/*  152: 173 */     annotationToXml.put(DiscriminatorColumn.class, "discriminator-column");
/*  153: 174 */     annotationToXml.put(SequenceGenerator.class, "sequence-generator");
/*  154: 175 */     annotationToXml.put(TableGenerator.class, "table-generator");
/*  155: 176 */     annotationToXml.put(NamedQuery.class, "named-query");
/*  156: 177 */     annotationToXml.put(NamedQueries.class, "named-query");
/*  157: 178 */     annotationToXml.put(NamedNativeQuery.class, "named-native-query");
/*  158: 179 */     annotationToXml.put(NamedNativeQueries.class, "named-native-query");
/*  159: 180 */     annotationToXml.put(SqlResultSetMapping.class, "sql-result-set-mapping");
/*  160: 181 */     annotationToXml.put(SqlResultSetMappings.class, "sql-result-set-mapping");
/*  161: 182 */     annotationToXml.put(ExcludeDefaultListeners.class, "exclude-default-listeners");
/*  162: 183 */     annotationToXml.put(ExcludeSuperclassListeners.class, "exclude-superclass-listeners");
/*  163: 184 */     annotationToXml.put(AccessType.class, "access");
/*  164: 185 */     annotationToXml.put(AttributeOverride.class, "attribute-override");
/*  165: 186 */     annotationToXml.put(AttributeOverrides.class, "attribute-override");
/*  166: 187 */     annotationToXml.put(AttributeOverride.class, "association-override");
/*  167: 188 */     annotationToXml.put(AttributeOverrides.class, "association-override");
/*  168: 189 */     annotationToXml.put(AttributeOverride.class, "map-key-attribute-override");
/*  169: 190 */     annotationToXml.put(AttributeOverrides.class, "map-key-attribute-override");
/*  170: 191 */     annotationToXml.put(Id.class, "id");
/*  171: 192 */     annotationToXml.put(EmbeddedId.class, "embedded-id");
/*  172: 193 */     annotationToXml.put(GeneratedValue.class, "generated-value");
/*  173: 194 */     annotationToXml.put(Column.class, "column");
/*  174: 195 */     annotationToXml.put(Columns.class, "column");
/*  175: 196 */     annotationToXml.put(Temporal.class, "temporal");
/*  176: 197 */     annotationToXml.put(Lob.class, "lob");
/*  177: 198 */     annotationToXml.put(Enumerated.class, "enumerated");
/*  178: 199 */     annotationToXml.put(Version.class, "version");
/*  179: 200 */     annotationToXml.put(Transient.class, "transient");
/*  180: 201 */     annotationToXml.put(Basic.class, "basic");
/*  181: 202 */     annotationToXml.put(Embedded.class, "embedded");
/*  182: 203 */     annotationToXml.put(ManyToOne.class, "many-to-one");
/*  183: 204 */     annotationToXml.put(OneToOne.class, "one-to-one");
/*  184: 205 */     annotationToXml.put(OneToMany.class, "one-to-many");
/*  185: 206 */     annotationToXml.put(ManyToMany.class, "many-to-many");
/*  186: 207 */     annotationToXml.put(JoinTable.class, "join-table");
/*  187: 208 */     annotationToXml.put(JoinColumn.class, "join-column");
/*  188: 209 */     annotationToXml.put(JoinColumns.class, "join-column");
/*  189: 210 */     annotationToXml.put(MapKey.class, "map-key");
/*  190: 211 */     annotationToXml.put(OrderBy.class, "order-by");
/*  191: 212 */     annotationToXml.put(EntityListeners.class, "entity-listeners");
/*  192: 213 */     annotationToXml.put(PrePersist.class, "pre-persist");
/*  193: 214 */     annotationToXml.put(PreRemove.class, "pre-remove");
/*  194: 215 */     annotationToXml.put(PreUpdate.class, "pre-update");
/*  195: 216 */     annotationToXml.put(PostPersist.class, "post-persist");
/*  196: 217 */     annotationToXml.put(PostRemove.class, "post-remove");
/*  197: 218 */     annotationToXml.put(PostUpdate.class, "post-update");
/*  198: 219 */     annotationToXml.put(PostLoad.class, "post-load");
/*  199: 220 */     annotationToXml.put(CollectionTable.class, "collection-table");
/*  200: 221 */     annotationToXml.put(MapKeyClass.class, "map-key-class");
/*  201: 222 */     annotationToXml.put(MapKeyTemporal.class, "map-key-temporal");
/*  202: 223 */     annotationToXml.put(MapKeyEnumerated.class, "map-key-enumerated");
/*  203: 224 */     annotationToXml.put(MapKeyColumn.class, "map-key-column");
/*  204: 225 */     annotationToXml.put(MapKeyJoinColumn.class, "map-key-join-column");
/*  205: 226 */     annotationToXml.put(MapKeyJoinColumns.class, "map-key-join-column");
/*  206: 227 */     annotationToXml.put(OrderColumn.class, "order-column");
/*  207:     */   }
/*  208:     */   
/*  209:     */   private static enum PropertyType
/*  210:     */   {
/*  211: 242 */     PROPERTY,  FIELD,  METHOD;
/*  212:     */     
/*  213:     */     private PropertyType() {}
/*  214:     */   }
/*  215:     */   
/*  216:     */   public JPAOverridenAnnotationReader(AnnotatedElement el, XMLContext xmlContext)
/*  217:     */   {
/*  218: 248 */     this.element = el;
/*  219: 249 */     this.xmlContext = xmlContext;
/*  220: 250 */     if ((el instanceof Class))
/*  221:     */     {
/*  222: 251 */       Class clazz = (Class)el;
/*  223: 252 */       this.className = clazz.getName();
/*  224:     */     }
/*  225: 254 */     else if ((el instanceof Field))
/*  226:     */     {
/*  227: 255 */       Field field = (Field)el;
/*  228: 256 */       this.className = field.getDeclaringClass().getName();
/*  229: 257 */       this.propertyName = field.getName();
/*  230: 258 */       this.propertyType = PropertyType.FIELD;
/*  231: 259 */       String expectedGetter = "get" + Character.toUpperCase(this.propertyName.charAt(0)) + this.propertyName.substring(1);
/*  232:     */       try
/*  233:     */       {
/*  234: 263 */         this.mirroredAttribute = field.getDeclaringClass().getDeclaredMethod(expectedGetter, new Class[0]);
/*  235:     */       }
/*  236:     */       catch (NoSuchMethodException e) {}
/*  237:     */     }
/*  238: 269 */     else if ((el instanceof Method))
/*  239:     */     {
/*  240: 270 */       Method method = (Method)el;
/*  241: 271 */       this.className = method.getDeclaringClass().getName();
/*  242: 272 */       this.propertyName = method.getName();
/*  243: 273 */       if (ReflectionUtil.isProperty(method, null, FILTER))
/*  244:     */       {
/*  245: 278 */         if (this.propertyName.startsWith("get")) {
/*  246: 279 */           this.propertyName = Introspector.decapitalize(this.propertyName.substring("get".length()));
/*  247: 281 */         } else if (this.propertyName.startsWith("is")) {
/*  248: 282 */           this.propertyName = Introspector.decapitalize(this.propertyName.substring("is".length()));
/*  249:     */         } else {
/*  250: 285 */           throw new RuntimeException("Method " + this.propertyName + " is not a property getter");
/*  251:     */         }
/*  252: 287 */         this.propertyType = PropertyType.PROPERTY;
/*  253:     */         try
/*  254:     */         {
/*  255: 289 */           this.mirroredAttribute = method.getDeclaringClass().getDeclaredField(this.propertyName);
/*  256:     */         }
/*  257:     */         catch (NoSuchFieldException e) {}
/*  258:     */       }
/*  259:     */       else
/*  260:     */       {
/*  261: 296 */         this.propertyType = PropertyType.METHOD;
/*  262:     */       }
/*  263:     */     }
/*  264:     */     else
/*  265:     */     {
/*  266: 300 */       this.className = null;
/*  267: 301 */       this.propertyName = null;
/*  268:     */     }
/*  269:     */   }
/*  270:     */   
/*  271:     */   public <T extends Annotation> T getAnnotation(Class<T> annotationType)
/*  272:     */   {
/*  273: 306 */     initAnnotations();
/*  274: 307 */     return (Annotation)this.annotationsMap.get(annotationType);
/*  275:     */   }
/*  276:     */   
/*  277:     */   public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationType)
/*  278:     */   {
/*  279: 311 */     initAnnotations();
/*  280: 312 */     return (Annotation)this.annotationsMap.get(annotationType) != null;
/*  281:     */   }
/*  282:     */   
/*  283:     */   public Annotation[] getAnnotations()
/*  284:     */   {
/*  285: 316 */     initAnnotations();
/*  286: 317 */     return this.annotations;
/*  287:     */   }
/*  288:     */   
/*  289:     */   private void initAnnotations()
/*  290:     */   {
/*  291: 325 */     if (this.annotations == null)
/*  292:     */     {
/*  293: 326 */       XMLContext.Default defaults = this.xmlContext.getDefault(this.className);
/*  294: 327 */       if ((this.className != null) && (this.propertyName == null))
/*  295:     */       {
/*  296: 329 */         Element tree = this.xmlContext.getXMLTree(this.className);
/*  297: 330 */         Annotation[] annotations = getJavaAnnotations();
/*  298: 331 */         List<Annotation> annotationList = new ArrayList(annotations.length + 5);
/*  299: 332 */         this.annotationsMap = new HashMap(annotations.length + 5);
/*  300: 333 */         for (Annotation annotation : annotations) {
/*  301: 334 */           if (!annotationToXml.containsKey(annotation.annotationType())) {
/*  302: 336 */             annotationList.add(annotation);
/*  303:     */           }
/*  304:     */         }
/*  305: 339 */         addIfNotNull(annotationList, getEntity(tree, defaults));
/*  306: 340 */         addIfNotNull(annotationList, getMappedSuperclass(tree, defaults));
/*  307: 341 */         addIfNotNull(annotationList, getEmbeddable(tree, defaults));
/*  308: 342 */         addIfNotNull(annotationList, getTable(tree, defaults));
/*  309: 343 */         addIfNotNull(annotationList, getSecondaryTables(tree, defaults));
/*  310: 344 */         addIfNotNull(annotationList, getPrimaryKeyJoinColumns(tree, defaults, true));
/*  311: 345 */         addIfNotNull(annotationList, getIdClass(tree, defaults));
/*  312: 346 */         addIfNotNull(annotationList, getInheritance(tree, defaults));
/*  313: 347 */         addIfNotNull(annotationList, getDiscriminatorValue(tree, defaults));
/*  314: 348 */         addIfNotNull(annotationList, getDiscriminatorColumn(tree, defaults));
/*  315: 349 */         addIfNotNull(annotationList, getSequenceGenerator(tree, defaults));
/*  316: 350 */         addIfNotNull(annotationList, getTableGenerator(tree, defaults));
/*  317: 351 */         addIfNotNull(annotationList, getNamedQueries(tree, defaults));
/*  318: 352 */         addIfNotNull(annotationList, getNamedNativeQueries(tree, defaults));
/*  319: 353 */         addIfNotNull(annotationList, getSqlResultSetMappings(tree, defaults));
/*  320: 354 */         addIfNotNull(annotationList, getExcludeDefaultListeners(tree, defaults));
/*  321: 355 */         addIfNotNull(annotationList, getExcludeSuperclassListeners(tree, defaults));
/*  322: 356 */         addIfNotNull(annotationList, getAccessType(tree, defaults));
/*  323: 357 */         addIfNotNull(annotationList, getAttributeOverrides(tree, defaults, true));
/*  324: 358 */         addIfNotNull(annotationList, getAssociationOverrides(tree, defaults, true));
/*  325: 359 */         addIfNotNull(annotationList, getEntityListeners(tree, defaults));
/*  326: 360 */         this.annotations = ((Annotation[])annotationList.toArray(new Annotation[annotationList.size()]));
/*  327: 361 */         for (Annotation ann : this.annotations) {
/*  328: 362 */           this.annotationsMap.put(ann.annotationType(), ann);
/*  329:     */         }
/*  330: 364 */         checkForOrphanProperties(tree);
/*  331:     */       }
/*  332: 366 */       else if (this.className != null)
/*  333:     */       {
/*  334: 367 */         Element tree = this.xmlContext.getXMLTree(this.className);
/*  335: 368 */         Annotation[] annotations = getJavaAnnotations();
/*  336: 369 */         List<Annotation> annotationList = new ArrayList(annotations.length + 5);
/*  337: 370 */         this.annotationsMap = new HashMap(annotations.length + 5);
/*  338: 371 */         for (Annotation annotation : annotations) {
/*  339: 372 */           if (!annotationToXml.containsKey(annotation.annotationType())) {
/*  340: 374 */             annotationList.add(annotation);
/*  341:     */           }
/*  342:     */         }
/*  343: 377 */         preCalculateElementsForProperty(tree);
/*  344: 378 */         Transient transientAnn = getTransient(defaults);
/*  345: 379 */         if (transientAnn != null)
/*  346:     */         {
/*  347: 380 */           annotationList.add(transientAnn);
/*  348:     */         }
/*  349:     */         else
/*  350:     */         {
/*  351: 383 */           if (defaults.canUseJavaAnnotations())
/*  352:     */           {
/*  353: 384 */             Annotation annotation = getJavaAnnotation(Access.class);
/*  354: 385 */             addIfNotNull(annotationList, annotation);
/*  355:     */           }
/*  356: 387 */           getId(annotationList, defaults);
/*  357: 388 */           getEmbeddedId(annotationList, defaults);
/*  358: 389 */           getEmbedded(annotationList, defaults);
/*  359: 390 */           getBasic(annotationList, defaults);
/*  360: 391 */           getVersion(annotationList, defaults);
/*  361: 392 */           getAssociation(ManyToOne.class, annotationList, defaults);
/*  362: 393 */           getAssociation(OneToOne.class, annotationList, defaults);
/*  363: 394 */           getAssociation(OneToMany.class, annotationList, defaults);
/*  364: 395 */           getAssociation(ManyToMany.class, annotationList, defaults);
/*  365: 396 */           getElementCollection(annotationList, defaults);
/*  366: 397 */           addIfNotNull(annotationList, getSequenceGenerator(this.elementsForProperty, defaults));
/*  367: 398 */           addIfNotNull(annotationList, getTableGenerator(this.elementsForProperty, defaults));
/*  368:     */         }
/*  369: 400 */         processEventAnnotations(annotationList, defaults);
/*  370:     */         
/*  371: 402 */         this.annotations = ((Annotation[])annotationList.toArray(new Annotation[annotationList.size()]));
/*  372: 403 */         for (Annotation ann : this.annotations) {
/*  373: 404 */           this.annotationsMap.put(ann.annotationType(), ann);
/*  374:     */         }
/*  375:     */       }
/*  376:     */       else
/*  377:     */       {
/*  378: 408 */         this.annotations = getJavaAnnotations();
/*  379: 409 */         this.annotationsMap = new HashMap(this.annotations.length + 5);
/*  380: 410 */         for (Annotation ann : this.annotations) {
/*  381: 411 */           this.annotationsMap.put(ann.annotationType(), ann);
/*  382:     */         }
/*  383:     */       }
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   private void checkForOrphanProperties(Element tree)
/*  388:     */   {
/*  389:     */     Class clazz;
/*  390:     */     try
/*  391:     */     {
/*  392: 420 */       clazz = ReflectHelper.classForName(this.className, getClass());
/*  393:     */     }
/*  394:     */     catch (ClassNotFoundException e)
/*  395:     */     {
/*  396: 423 */       return;
/*  397:     */     }
/*  398: 425 */     Element element = tree != null ? tree.element("attributes") : null;
/*  399:     */     Set<String> properties;
/*  400: 427 */     if (element != null)
/*  401:     */     {
/*  402: 430 */       properties = new HashSet();
/*  403: 431 */       for (Field field : clazz.getFields()) {
/*  404: 432 */         properties.add(field.getName());
/*  405:     */       }
/*  406: 434 */       for (Method method : clazz.getMethods())
/*  407:     */       {
/*  408: 435 */         String name = method.getName();
/*  409: 436 */         if (name.startsWith("get")) {
/*  410: 437 */           properties.add(Introspector.decapitalize(name.substring("get".length())));
/*  411: 439 */         } else if (name.startsWith("is")) {
/*  412: 440 */           properties.add(Introspector.decapitalize(name.substring("is".length())));
/*  413:     */         }
/*  414:     */       }
/*  415: 443 */       for (Element subelement : element.elements())
/*  416:     */       {
/*  417: 444 */         String propertyName = subelement.attributeValue("name");
/*  418: 445 */         if (!properties.contains(propertyName)) {
/*  419: 446 */           LOG.propertyNotFound(StringHelper.qualify(this.className, propertyName));
/*  420:     */         }
/*  421:     */       }
/*  422:     */     }
/*  423:     */   }
/*  424:     */   
/*  425:     */   private Annotation addIfNotNull(List<Annotation> annotationList, Annotation annotation)
/*  426:     */   {
/*  427: 461 */     if (annotation != null) {
/*  428: 462 */       annotationList.add(annotation);
/*  429:     */     }
/*  430: 464 */     return annotation;
/*  431:     */   }
/*  432:     */   
/*  433:     */   private Annotation getTableGenerator(List<Element> elementsForProperty, XMLContext.Default defaults)
/*  434:     */   {
/*  435: 469 */     for (Element element : elementsForProperty)
/*  436:     */     {
/*  437: 470 */       Element subelement = element != null ? element.element((String)annotationToXml.get(TableGenerator.class)) : null;
/*  438: 471 */       if (subelement != null) {
/*  439: 472 */         return buildTableGeneratorAnnotation(subelement, defaults);
/*  440:     */       }
/*  441:     */     }
/*  442: 475 */     if ((elementsForProperty.size() == 0) && (defaults.canUseJavaAnnotations())) {
/*  443: 476 */       return getJavaAnnotation(TableGenerator.class);
/*  444:     */     }
/*  445: 479 */     return null;
/*  446:     */   }
/*  447:     */   
/*  448:     */   private Annotation getSequenceGenerator(List<Element> elementsForProperty, XMLContext.Default defaults)
/*  449:     */   {
/*  450: 484 */     for (Element element : elementsForProperty)
/*  451:     */     {
/*  452: 485 */       Element subelement = element != null ? element.element((String)annotationToXml.get(SequenceGenerator.class)) : null;
/*  453: 486 */       if (subelement != null) {
/*  454: 487 */         return buildSequenceGeneratorAnnotation(subelement);
/*  455:     */       }
/*  456:     */     }
/*  457: 490 */     if ((elementsForProperty.size() == 0) && (defaults.canUseJavaAnnotations())) {
/*  458: 491 */       return getJavaAnnotation(SequenceGenerator.class);
/*  459:     */     }
/*  460: 494 */     return null;
/*  461:     */   }
/*  462:     */   
/*  463:     */   private void processEventAnnotations(List<Annotation> annotationList, XMLContext.Default defaults)
/*  464:     */   {
/*  465: 499 */     boolean eventElement = false;
/*  466: 500 */     for (Element element : this.elementsForProperty)
/*  467:     */     {
/*  468: 501 */       String elementName = element.getName();
/*  469: 502 */       if ("pre-persist".equals(elementName))
/*  470:     */       {
/*  471: 503 */         AnnotationDescriptor ad = new AnnotationDescriptor(PrePersist.class);
/*  472: 504 */         annotationList.add(AnnotationFactory.create(ad));
/*  473: 505 */         eventElement = true;
/*  474:     */       }
/*  475: 507 */       else if ("pre-remove".equals(elementName))
/*  476:     */       {
/*  477: 508 */         AnnotationDescriptor ad = new AnnotationDescriptor(PreRemove.class);
/*  478: 509 */         annotationList.add(AnnotationFactory.create(ad));
/*  479: 510 */         eventElement = true;
/*  480:     */       }
/*  481: 512 */       else if ("pre-update".equals(elementName))
/*  482:     */       {
/*  483: 513 */         AnnotationDescriptor ad = new AnnotationDescriptor(PreUpdate.class);
/*  484: 514 */         annotationList.add(AnnotationFactory.create(ad));
/*  485: 515 */         eventElement = true;
/*  486:     */       }
/*  487: 517 */       else if ("post-persist".equals(elementName))
/*  488:     */       {
/*  489: 518 */         AnnotationDescriptor ad = new AnnotationDescriptor(PostPersist.class);
/*  490: 519 */         annotationList.add(AnnotationFactory.create(ad));
/*  491: 520 */         eventElement = true;
/*  492:     */       }
/*  493: 522 */       else if ("post-remove".equals(elementName))
/*  494:     */       {
/*  495: 523 */         AnnotationDescriptor ad = new AnnotationDescriptor(PostRemove.class);
/*  496: 524 */         annotationList.add(AnnotationFactory.create(ad));
/*  497: 525 */         eventElement = true;
/*  498:     */       }
/*  499: 527 */       else if ("post-update".equals(elementName))
/*  500:     */       {
/*  501: 528 */         AnnotationDescriptor ad = new AnnotationDescriptor(PostUpdate.class);
/*  502: 529 */         annotationList.add(AnnotationFactory.create(ad));
/*  503: 530 */         eventElement = true;
/*  504:     */       }
/*  505: 532 */       else if ("post-load".equals(elementName))
/*  506:     */       {
/*  507: 533 */         AnnotationDescriptor ad = new AnnotationDescriptor(PostLoad.class);
/*  508: 534 */         annotationList.add(AnnotationFactory.create(ad));
/*  509: 535 */         eventElement = true;
/*  510:     */       }
/*  511:     */     }
/*  512: 538 */     if ((!eventElement) && (defaults.canUseJavaAnnotations()))
/*  513:     */     {
/*  514: 539 */       Annotation ann = getJavaAnnotation(PrePersist.class);
/*  515: 540 */       addIfNotNull(annotationList, ann);
/*  516: 541 */       ann = getJavaAnnotation(PreRemove.class);
/*  517: 542 */       addIfNotNull(annotationList, ann);
/*  518: 543 */       ann = getJavaAnnotation(PreUpdate.class);
/*  519: 544 */       addIfNotNull(annotationList, ann);
/*  520: 545 */       ann = getJavaAnnotation(PostPersist.class);
/*  521: 546 */       addIfNotNull(annotationList, ann);
/*  522: 547 */       ann = getJavaAnnotation(PostRemove.class);
/*  523: 548 */       addIfNotNull(annotationList, ann);
/*  524: 549 */       ann = getJavaAnnotation(PostUpdate.class);
/*  525: 550 */       addIfNotNull(annotationList, ann);
/*  526: 551 */       ann = getJavaAnnotation(PostLoad.class);
/*  527: 552 */       addIfNotNull(annotationList, ann);
/*  528:     */     }
/*  529:     */   }
/*  530:     */   
/*  531:     */   private EntityListeners getEntityListeners(Element tree, XMLContext.Default defaults)
/*  532:     */   {
/*  533: 557 */     Element element = tree != null ? tree.element("entity-listeners") : null;
/*  534: 558 */     if (element != null)
/*  535:     */     {
/*  536: 559 */       List<Class> entityListenerClasses = new ArrayList();
/*  537: 560 */       for (Element subelement : element.elements("entity-listener"))
/*  538:     */       {
/*  539: 561 */         String className = subelement.attributeValue("class");
/*  540:     */         try
/*  541:     */         {
/*  542: 563 */           entityListenerClasses.add(ReflectHelper.classForName(XMLContext.buildSafeClassName(className, defaults), getClass()));
/*  543:     */         }
/*  544:     */         catch (ClassNotFoundException e)
/*  545:     */         {
/*  546: 571 */           throw new AnnotationException("Unable to find " + element.getPath() + ".class: " + className, e);
/*  547:     */         }
/*  548:     */       }
/*  549: 576 */       AnnotationDescriptor ad = new AnnotationDescriptor(EntityListeners.class);
/*  550: 577 */       ad.setValue("value", entityListenerClasses.toArray(new Class[entityListenerClasses.size()]));
/*  551: 578 */       return (EntityListeners)AnnotationFactory.create(ad);
/*  552:     */     }
/*  553: 580 */     if (defaults.canUseJavaAnnotations()) {
/*  554: 581 */       return (EntityListeners)getJavaAnnotation(EntityListeners.class);
/*  555:     */     }
/*  556: 584 */     return null;
/*  557:     */   }
/*  558:     */   
/*  559:     */   private JoinTable overridesDefaultsInJoinTable(Annotation annotation, XMLContext.Default defaults)
/*  560:     */   {
/*  561: 590 */     boolean defaultToJoinTable = (!isJavaAnnotationPresent(JoinColumn.class)) && (!isJavaAnnotationPresent(JoinColumns.class));
/*  562:     */     
/*  563: 592 */     Class<? extends Annotation> annotationClass = annotation.annotationType();
/*  564: 593 */     defaultToJoinTable = (defaultToJoinTable) && (((annotationClass == ManyToMany.class) && (StringHelper.isEmpty(((ManyToMany)annotation).mappedBy()))) || ((annotationClass == OneToMany.class) && (StringHelper.isEmpty(((OneToMany)annotation).mappedBy()))) || (annotationClass == ElementCollection.class));
/*  565:     */     
/*  566:     */ 
/*  567:     */ 
/*  568:     */ 
/*  569: 598 */     Class<JoinTable> annotationType = JoinTable.class;
/*  570: 599 */     if ((defaultToJoinTable) && ((StringHelper.isNotEmpty(defaults.getCatalog())) || (StringHelper.isNotEmpty(defaults.getSchema()))))
/*  571:     */     {
/*  572: 602 */       AnnotationDescriptor ad = new AnnotationDescriptor(annotationType);
/*  573: 603 */       if (defaults.canUseJavaAnnotations())
/*  574:     */       {
/*  575: 604 */         JoinTable table = (JoinTable)getJavaAnnotation(annotationType);
/*  576: 605 */         if (table != null)
/*  577:     */         {
/*  578: 606 */           ad.setValue("name", table.name());
/*  579: 607 */           ad.setValue("schema", table.schema());
/*  580: 608 */           ad.setValue("catalog", table.catalog());
/*  581: 609 */           ad.setValue("uniqueConstraints", table.uniqueConstraints());
/*  582: 610 */           ad.setValue("joinColumns", table.joinColumns());
/*  583: 611 */           ad.setValue("inverseJoinColumns", table.inverseJoinColumns());
/*  584:     */         }
/*  585:     */       }
/*  586: 614 */       if ((StringHelper.isEmpty((String)ad.valueOf("schema"))) && (StringHelper.isNotEmpty(defaults.getSchema()))) {
/*  587: 616 */         ad.setValue("schema", defaults.getSchema());
/*  588:     */       }
/*  589: 618 */       if ((StringHelper.isEmpty((String)ad.valueOf("catalog"))) && (StringHelper.isNotEmpty(defaults.getCatalog()))) {
/*  590: 620 */         ad.setValue("catalog", defaults.getCatalog());
/*  591:     */       }
/*  592: 622 */       return (JoinTable)AnnotationFactory.create(ad);
/*  593:     */     }
/*  594: 624 */     if (defaults.canUseJavaAnnotations()) {
/*  595: 625 */       return (JoinTable)getJavaAnnotation(annotationType);
/*  596:     */     }
/*  597: 628 */     return null;
/*  598:     */   }
/*  599:     */   
/*  600:     */   private void getJoinTable(List<Annotation> annotationList, Element tree, XMLContext.Default defaults)
/*  601:     */   {
/*  602: 633 */     addIfNotNull(annotationList, buildJoinTable(tree, defaults));
/*  603:     */   }
/*  604:     */   
/*  605:     */   private JoinTable buildJoinTable(Element tree, XMLContext.Default defaults)
/*  606:     */   {
/*  607: 640 */     Element subelement = tree == null ? null : tree.element("join-table");
/*  608: 641 */     Class<JoinTable> annotationType = JoinTable.class;
/*  609: 642 */     if (subelement == null) {
/*  610: 643 */       return null;
/*  611:     */     }
/*  612: 646 */     AnnotationDescriptor annotation = new AnnotationDescriptor(annotationType);
/*  613: 647 */     copyStringAttribute(annotation, subelement, "name", false);
/*  614: 648 */     copyStringAttribute(annotation, subelement, "catalog", false);
/*  615: 649 */     if ((StringHelper.isNotEmpty(defaults.getCatalog())) && (StringHelper.isEmpty((String)annotation.valueOf("catalog")))) {
/*  616: 651 */       annotation.setValue("catalog", defaults.getCatalog());
/*  617:     */     }
/*  618: 653 */     copyStringAttribute(annotation, subelement, "schema", false);
/*  619: 654 */     if ((StringHelper.isNotEmpty(defaults.getSchema())) && (StringHelper.isEmpty((String)annotation.valueOf("schema")))) {
/*  620: 656 */       annotation.setValue("schema", defaults.getSchema());
/*  621:     */     }
/*  622: 658 */     buildUniqueConstraints(annotation, subelement);
/*  623: 659 */     annotation.setValue("joinColumns", getJoinColumns(subelement, false));
/*  624: 660 */     annotation.setValue("inverseJoinColumns", getJoinColumns(subelement, true));
/*  625: 661 */     return (JoinTable)AnnotationFactory.create(annotation);
/*  626:     */   }
/*  627:     */   
/*  628:     */   private void getAssociation(Class<? extends Annotation> annotationType, List<Annotation> annotationList, XMLContext.Default defaults)
/*  629:     */   {
/*  630: 676 */     String xmlName = (String)annotationToXml.get(annotationType);
/*  631: 677 */     for (Element element : this.elementsForProperty) {
/*  632: 678 */       if (xmlName.equals(element.getName()))
/*  633:     */       {
/*  634: 679 */         AnnotationDescriptor ad = new AnnotationDescriptor(annotationType);
/*  635: 680 */         addTargetClass(element, ad, "target-entity", defaults);
/*  636: 681 */         getFetchType(ad, element);
/*  637: 682 */         getCascades(ad, element, defaults);
/*  638: 683 */         getJoinTable(annotationList, element, defaults);
/*  639: 684 */         buildJoinColumns(annotationList, element);
/*  640: 685 */         Annotation annotation = getPrimaryKeyJoinColumns(element, defaults, false);
/*  641: 686 */         addIfNotNull(annotationList, annotation);
/*  642: 687 */         copyBooleanAttribute(ad, element, "optional");
/*  643: 688 */         copyBooleanAttribute(ad, element, "orphan-removal");
/*  644: 689 */         copyStringAttribute(ad, element, "mapped-by", false);
/*  645: 690 */         getOrderBy(annotationList, element);
/*  646: 691 */         getMapKey(annotationList, element);
/*  647: 692 */         getMapKeyClass(annotationList, element, defaults);
/*  648: 693 */         getMapKeyColumn(annotationList, element);
/*  649: 694 */         getOrderColumn(annotationList, element);
/*  650: 695 */         getMapKeyTemporal(annotationList, element);
/*  651: 696 */         getMapKeyEnumerated(annotationList, element);
/*  652: 697 */         annotation = getMapKeyAttributeOverrides(element, defaults);
/*  653: 698 */         addIfNotNull(annotationList, annotation);
/*  654: 699 */         buildMapKeyJoinColumns(annotationList, element);
/*  655: 700 */         getAssociationId(annotationList, element);
/*  656: 701 */         getMapsId(annotationList, element);
/*  657: 702 */         annotationList.add(AnnotationFactory.create(ad));
/*  658: 703 */         getAccessType(annotationList, element);
/*  659:     */       }
/*  660:     */     }
/*  661: 706 */     if ((this.elementsForProperty.size() == 0) && (defaults.canUseJavaAnnotations()))
/*  662:     */     {
/*  663: 707 */       Annotation annotation = getJavaAnnotation(annotationType);
/*  664: 708 */       if (annotation != null)
/*  665:     */       {
/*  666: 709 */         annotationList.add(annotation);
/*  667: 710 */         annotation = overridesDefaultsInJoinTable(annotation, defaults);
/*  668: 711 */         addIfNotNull(annotationList, annotation);
/*  669: 712 */         annotation = getJavaAnnotation(JoinColumn.class);
/*  670: 713 */         addIfNotNull(annotationList, annotation);
/*  671: 714 */         annotation = getJavaAnnotation(JoinColumns.class);
/*  672: 715 */         addIfNotNull(annotationList, annotation);
/*  673: 716 */         annotation = getJavaAnnotation(PrimaryKeyJoinColumn.class);
/*  674: 717 */         addIfNotNull(annotationList, annotation);
/*  675: 718 */         annotation = getJavaAnnotation(PrimaryKeyJoinColumns.class);
/*  676: 719 */         addIfNotNull(annotationList, annotation);
/*  677: 720 */         annotation = getJavaAnnotation(MapKey.class);
/*  678: 721 */         addIfNotNull(annotationList, annotation);
/*  679: 722 */         annotation = getJavaAnnotation(OrderBy.class);
/*  680: 723 */         addIfNotNull(annotationList, annotation);
/*  681: 724 */         annotation = getJavaAnnotation(AttributeOverride.class);
/*  682: 725 */         addIfNotNull(annotationList, annotation);
/*  683: 726 */         annotation = getJavaAnnotation(AttributeOverrides.class);
/*  684: 727 */         addIfNotNull(annotationList, annotation);
/*  685: 728 */         annotation = getJavaAnnotation(AssociationOverride.class);
/*  686: 729 */         addIfNotNull(annotationList, annotation);
/*  687: 730 */         annotation = getJavaAnnotation(AssociationOverrides.class);
/*  688: 731 */         addIfNotNull(annotationList, annotation);
/*  689: 732 */         annotation = getJavaAnnotation(Lob.class);
/*  690: 733 */         addIfNotNull(annotationList, annotation);
/*  691: 734 */         annotation = getJavaAnnotation(Enumerated.class);
/*  692: 735 */         addIfNotNull(annotationList, annotation);
/*  693: 736 */         annotation = getJavaAnnotation(Temporal.class);
/*  694: 737 */         addIfNotNull(annotationList, annotation);
/*  695: 738 */         annotation = getJavaAnnotation(Column.class);
/*  696: 739 */         addIfNotNull(annotationList, annotation);
/*  697: 740 */         annotation = getJavaAnnotation(Columns.class);
/*  698: 741 */         addIfNotNull(annotationList, annotation);
/*  699: 742 */         annotation = getJavaAnnotation(MapKeyClass.class);
/*  700: 743 */         addIfNotNull(annotationList, annotation);
/*  701: 744 */         annotation = getJavaAnnotation(MapKeyTemporal.class);
/*  702: 745 */         addIfNotNull(annotationList, annotation);
/*  703: 746 */         annotation = getJavaAnnotation(MapKeyEnumerated.class);
/*  704: 747 */         addIfNotNull(annotationList, annotation);
/*  705: 748 */         annotation = getJavaAnnotation(MapKeyColumn.class);
/*  706: 749 */         addIfNotNull(annotationList, annotation);
/*  707: 750 */         annotation = getJavaAnnotation(MapKeyJoinColumn.class);
/*  708: 751 */         addIfNotNull(annotationList, annotation);
/*  709: 752 */         annotation = getJavaAnnotation(MapKeyJoinColumns.class);
/*  710: 753 */         addIfNotNull(annotationList, annotation);
/*  711: 754 */         annotation = getJavaAnnotation(OrderColumn.class);
/*  712: 755 */         addIfNotNull(annotationList, annotation);
/*  713: 756 */         annotation = getJavaAnnotation(Cascade.class);
/*  714: 757 */         addIfNotNull(annotationList, annotation);
/*  715:     */       }
/*  716: 759 */       else if (isJavaAnnotationPresent(ElementCollection.class))
/*  717:     */       {
/*  718: 760 */         annotation = overridesDefaultsInJoinTable(getJavaAnnotation(ElementCollection.class), defaults);
/*  719: 761 */         addIfNotNull(annotationList, annotation);
/*  720: 762 */         annotation = getJavaAnnotation(MapKey.class);
/*  721: 763 */         addIfNotNull(annotationList, annotation);
/*  722: 764 */         annotation = getJavaAnnotation(OrderBy.class);
/*  723: 765 */         addIfNotNull(annotationList, annotation);
/*  724: 766 */         annotation = getJavaAnnotation(AttributeOverride.class);
/*  725: 767 */         addIfNotNull(annotationList, annotation);
/*  726: 768 */         annotation = getJavaAnnotation(AttributeOverrides.class);
/*  727: 769 */         addIfNotNull(annotationList, annotation);
/*  728: 770 */         annotation = getJavaAnnotation(AssociationOverride.class);
/*  729: 771 */         addIfNotNull(annotationList, annotation);
/*  730: 772 */         annotation = getJavaAnnotation(AssociationOverrides.class);
/*  731: 773 */         addIfNotNull(annotationList, annotation);
/*  732: 774 */         annotation = getJavaAnnotation(Lob.class);
/*  733: 775 */         addIfNotNull(annotationList, annotation);
/*  734: 776 */         annotation = getJavaAnnotation(Enumerated.class);
/*  735: 777 */         addIfNotNull(annotationList, annotation);
/*  736: 778 */         annotation = getJavaAnnotation(Temporal.class);
/*  737: 779 */         addIfNotNull(annotationList, annotation);
/*  738: 780 */         annotation = getJavaAnnotation(Column.class);
/*  739: 781 */         addIfNotNull(annotationList, annotation);
/*  740: 782 */         annotation = getJavaAnnotation(OrderColumn.class);
/*  741: 783 */         addIfNotNull(annotationList, annotation);
/*  742: 784 */         annotation = getJavaAnnotation(MapKeyClass.class);
/*  743: 785 */         addIfNotNull(annotationList, annotation);
/*  744: 786 */         annotation = getJavaAnnotation(MapKeyTemporal.class);
/*  745: 787 */         addIfNotNull(annotationList, annotation);
/*  746: 788 */         annotation = getJavaAnnotation(MapKeyEnumerated.class);
/*  747: 789 */         addIfNotNull(annotationList, annotation);
/*  748: 790 */         annotation = getJavaAnnotation(MapKeyColumn.class);
/*  749: 791 */         addIfNotNull(annotationList, annotation);
/*  750: 792 */         annotation = getJavaAnnotation(MapKeyJoinColumn.class);
/*  751: 793 */         addIfNotNull(annotationList, annotation);
/*  752: 794 */         annotation = getJavaAnnotation(MapKeyJoinColumns.class);
/*  753: 795 */         addIfNotNull(annotationList, annotation);
/*  754: 796 */         annotation = getJavaAnnotation(CollectionTable.class);
/*  755: 797 */         addIfNotNull(annotationList, annotation);
/*  756:     */       }
/*  757:     */     }
/*  758:     */   }
/*  759:     */   
/*  760:     */   private void buildMapKeyJoinColumns(List<Annotation> annotationList, Element element)
/*  761:     */   {
/*  762: 803 */     MapKeyJoinColumn[] joinColumns = getMapKeyJoinColumns(element);
/*  763: 804 */     if (joinColumns.length > 0)
/*  764:     */     {
/*  765: 805 */       AnnotationDescriptor ad = new AnnotationDescriptor(MapKeyJoinColumns.class);
/*  766: 806 */       ad.setValue("value", joinColumns);
/*  767: 807 */       annotationList.add(AnnotationFactory.create(ad));
/*  768:     */     }
/*  769:     */   }
/*  770:     */   
/*  771:     */   private MapKeyJoinColumn[] getMapKeyJoinColumns(Element element)
/*  772:     */   {
/*  773: 812 */     List<Element> subelements = element != null ? element.elements("map-key-join-column") : null;
/*  774: 813 */     List<MapKeyJoinColumn> joinColumns = new ArrayList();
/*  775: 814 */     if (subelements != null) {
/*  776: 815 */       for (Element subelement : subelements)
/*  777:     */       {
/*  778: 816 */         AnnotationDescriptor column = new AnnotationDescriptor(MapKeyJoinColumn.class);
/*  779: 817 */         copyStringAttribute(column, subelement, "name", false);
/*  780: 818 */         copyStringAttribute(column, subelement, "referenced-column-name", false);
/*  781: 819 */         copyBooleanAttribute(column, subelement, "unique");
/*  782: 820 */         copyBooleanAttribute(column, subelement, "nullable");
/*  783: 821 */         copyBooleanAttribute(column, subelement, "insertable");
/*  784: 822 */         copyBooleanAttribute(column, subelement, "updatable");
/*  785: 823 */         copyStringAttribute(column, subelement, "column-definition", false);
/*  786: 824 */         copyStringAttribute(column, subelement, "table", false);
/*  787: 825 */         joinColumns.add((MapKeyJoinColumn)AnnotationFactory.create(column));
/*  788:     */       }
/*  789:     */     }
/*  790: 828 */     return (MapKeyJoinColumn[])joinColumns.toArray(new MapKeyJoinColumn[joinColumns.size()]);
/*  791:     */   }
/*  792:     */   
/*  793:     */   private AttributeOverrides getMapKeyAttributeOverrides(Element tree, XMLContext.Default defaults)
/*  794:     */   {
/*  795: 832 */     List<AttributeOverride> attributes = buildAttributeOverrides(tree, "map-key-attribute-override");
/*  796: 833 */     return mergeAttributeOverrides(defaults, attributes, false);
/*  797:     */   }
/*  798:     */   
/*  799:     */   private void getMapKeyEnumerated(List<Annotation> annotationList, Element element)
/*  800:     */   {
/*  801: 842 */     Element subelement = element != null ? element.element("map-key-enumerated") : null;
/*  802: 843 */     if (subelement != null)
/*  803:     */     {
/*  804: 844 */       AnnotationDescriptor ad = new AnnotationDescriptor(MapKeyEnumerated.class);
/*  805: 845 */       EnumType value = EnumType.valueOf(subelement.getTextTrim());
/*  806: 846 */       ad.setValue("value", value);
/*  807: 847 */       annotationList.add(AnnotationFactory.create(ad));
/*  808:     */     }
/*  809:     */   }
/*  810:     */   
/*  811:     */   private void getMapKeyTemporal(List<Annotation> annotationList, Element element)
/*  812:     */   {
/*  813: 857 */     Element subelement = element != null ? element.element("map-key-temporal") : null;
/*  814: 858 */     if (subelement != null)
/*  815:     */     {
/*  816: 859 */       AnnotationDescriptor ad = new AnnotationDescriptor(MapKeyTemporal.class);
/*  817: 860 */       TemporalType value = TemporalType.valueOf(subelement.getTextTrim());
/*  818: 861 */       ad.setValue("value", value);
/*  819: 862 */       annotationList.add(AnnotationFactory.create(ad));
/*  820:     */     }
/*  821:     */   }
/*  822:     */   
/*  823:     */   private void getOrderColumn(List<Annotation> annotationList, Element element)
/*  824:     */   {
/*  825: 872 */     Element subelement = element != null ? element.element("order-column") : null;
/*  826: 873 */     if (subelement != null)
/*  827:     */     {
/*  828: 874 */       AnnotationDescriptor ad = new AnnotationDescriptor(OrderColumn.class);
/*  829: 875 */       copyStringAttribute(ad, subelement, "name", false);
/*  830: 876 */       copyBooleanAttribute(ad, subelement, "nullable");
/*  831: 877 */       copyBooleanAttribute(ad, subelement, "insertable");
/*  832: 878 */       copyBooleanAttribute(ad, subelement, "updatable");
/*  833: 879 */       copyStringAttribute(ad, subelement, "column-definition", false);
/*  834: 880 */       annotationList.add(AnnotationFactory.create(ad));
/*  835:     */     }
/*  836:     */   }
/*  837:     */   
/*  838:     */   private void getMapsId(List<Annotation> annotationList, Element element)
/*  839:     */   {
/*  840: 890 */     String attrVal = element.attributeValue("maps-id");
/*  841: 891 */     if (attrVal != null)
/*  842:     */     {
/*  843: 892 */       AnnotationDescriptor ad = new AnnotationDescriptor(MapsId.class);
/*  844: 893 */       ad.setValue("value", attrVal);
/*  845: 894 */       annotationList.add(AnnotationFactory.create(ad));
/*  846:     */     }
/*  847:     */   }
/*  848:     */   
/*  849:     */   private void getAssociationId(List<Annotation> annotationList, Element element)
/*  850:     */   {
/*  851: 904 */     String attrVal = element.attributeValue("id");
/*  852: 905 */     if ("true".equals(attrVal))
/*  853:     */     {
/*  854: 906 */       AnnotationDescriptor ad = new AnnotationDescriptor(Id.class);
/*  855: 907 */       annotationList.add(AnnotationFactory.create(ad));
/*  856:     */     }
/*  857:     */   }
/*  858:     */   
/*  859:     */   private void addTargetClass(Element element, AnnotationDescriptor ad, String nodeName, XMLContext.Default defaults)
/*  860:     */   {
/*  861: 912 */     String className = element.attributeValue(nodeName);
/*  862: 913 */     if (className != null)
/*  863:     */     {
/*  864:     */       Class clazz;
/*  865:     */       try
/*  866:     */       {
/*  867: 916 */         clazz = ReflectHelper.classForName(XMLContext.buildSafeClassName(className, defaults), getClass());
/*  868:     */       }
/*  869:     */       catch (ClassNotFoundException e)
/*  870:     */       {
/*  871: 921 */         throw new AnnotationException("Unable to find " + element.getPath() + " " + nodeName + ": " + className, e);
/*  872:     */       }
/*  873: 925 */       ad.setValue(getJavaAttributeNameFromXMLOne(nodeName), clazz);
/*  874:     */     }
/*  875:     */   }
/*  876:     */   
/*  877:     */   private void getElementCollection(List<Annotation> annotationList, XMLContext.Default defaults)
/*  878:     */   {
/*  879: 937 */     for (Element element : this.elementsForProperty) {
/*  880: 938 */       if ("element-collection".equals(element.getName()))
/*  881:     */       {
/*  882: 939 */         AnnotationDescriptor ad = new AnnotationDescriptor(ElementCollection.class);
/*  883: 940 */         addTargetClass(element, ad, "target-class", defaults);
/*  884: 941 */         getFetchType(ad, element);
/*  885: 942 */         getOrderBy(annotationList, element);
/*  886: 943 */         getOrderColumn(annotationList, element);
/*  887: 944 */         getMapKey(annotationList, element);
/*  888: 945 */         getMapKeyClass(annotationList, element, defaults);
/*  889: 946 */         getMapKeyTemporal(annotationList, element);
/*  890: 947 */         getMapKeyEnumerated(annotationList, element);
/*  891: 948 */         getMapKeyColumn(annotationList, element);
/*  892: 949 */         buildMapKeyJoinColumns(annotationList, element);
/*  893: 950 */         Annotation annotation = getColumn(element.element("column"), false, element);
/*  894: 951 */         addIfNotNull(annotationList, annotation);
/*  895: 952 */         getTemporal(annotationList, element);
/*  896: 953 */         getEnumerated(annotationList, element);
/*  897: 954 */         getLob(annotationList, element);
/*  898:     */         
/*  899:     */ 
/*  900:     */ 
/*  901: 958 */         List<AttributeOverride> attributes = new ArrayList();
/*  902: 959 */         attributes.addAll(buildAttributeOverrides(element, "map-key-attribute-override"));
/*  903: 960 */         attributes.addAll(buildAttributeOverrides(element, "attribute-override"));
/*  904: 961 */         annotation = mergeAttributeOverrides(defaults, attributes, false);
/*  905: 962 */         addIfNotNull(annotationList, annotation);
/*  906: 963 */         annotation = getAssociationOverrides(element, defaults, false);
/*  907: 964 */         addIfNotNull(annotationList, annotation);
/*  908: 965 */         getCollectionTable(annotationList, element, defaults);
/*  909: 966 */         annotationList.add(AnnotationFactory.create(ad));
/*  910: 967 */         getAccessType(annotationList, element);
/*  911:     */       }
/*  912:     */     }
/*  913:     */   }
/*  914:     */   
/*  915:     */   private void getOrderBy(List<Annotation> annotationList, Element element)
/*  916:     */   {
/*  917: 973 */     Element subelement = element != null ? element.element("order-by") : null;
/*  918: 974 */     if (subelement != null)
/*  919:     */     {
/*  920: 975 */       AnnotationDescriptor ad = new AnnotationDescriptor(OrderBy.class);
/*  921: 976 */       copyStringElement(subelement, ad, "value");
/*  922: 977 */       annotationList.add(AnnotationFactory.create(ad));
/*  923:     */     }
/*  924:     */   }
/*  925:     */   
/*  926:     */   private void getMapKey(List<Annotation> annotationList, Element element)
/*  927:     */   {
/*  928: 982 */     Element subelement = element != null ? element.element("map-key") : null;
/*  929: 983 */     if (subelement != null)
/*  930:     */     {
/*  931: 984 */       AnnotationDescriptor ad = new AnnotationDescriptor(MapKey.class);
/*  932: 985 */       copyStringAttribute(ad, subelement, "name", false);
/*  933: 986 */       annotationList.add(AnnotationFactory.create(ad));
/*  934:     */     }
/*  935:     */   }
/*  936:     */   
/*  937:     */   private void getMapKeyColumn(List<Annotation> annotationList, Element element)
/*  938:     */   {
/*  939: 991 */     Element subelement = element != null ? element.element("map-key-column") : null;
/*  940: 992 */     if (subelement != null)
/*  941:     */     {
/*  942: 993 */       AnnotationDescriptor ad = new AnnotationDescriptor(MapKeyColumn.class);
/*  943: 994 */       copyStringAttribute(ad, subelement, "name", false);
/*  944: 995 */       copyBooleanAttribute(ad, subelement, "unique");
/*  945: 996 */       copyBooleanAttribute(ad, subelement, "nullable");
/*  946: 997 */       copyBooleanAttribute(ad, subelement, "insertable");
/*  947: 998 */       copyBooleanAttribute(ad, subelement, "updatable");
/*  948: 999 */       copyStringAttribute(ad, subelement, "column-definition", false);
/*  949:1000 */       copyStringAttribute(ad, subelement, "table", false);
/*  950:1001 */       copyIntegerAttribute(ad, subelement, "length");
/*  951:1002 */       copyIntegerAttribute(ad, subelement, "precision");
/*  952:1003 */       copyIntegerAttribute(ad, subelement, "scale");
/*  953:1004 */       annotationList.add(AnnotationFactory.create(ad));
/*  954:     */     }
/*  955:     */   }
/*  956:     */   
/*  957:     */   private void getMapKeyClass(List<Annotation> annotationList, Element element, XMLContext.Default defaults)
/*  958:     */   {
/*  959:1009 */     String nodeName = "map-key-class";
/*  960:1010 */     Element subelement = element != null ? element.element(nodeName) : null;
/*  961:1011 */     if (subelement != null)
/*  962:     */     {
/*  963:1012 */       String mapKeyClassName = subelement.attributeValue("class");
/*  964:1013 */       AnnotationDescriptor ad = new AnnotationDescriptor(MapKeyClass.class);
/*  965:1014 */       if (StringHelper.isNotEmpty(mapKeyClassName))
/*  966:     */       {
/*  967:     */         Class clazz;
/*  968:     */         try
/*  969:     */         {
/*  970:1017 */           clazz = ReflectHelper.classForName(XMLContext.buildSafeClassName(mapKeyClassName, defaults), getClass());
/*  971:     */         }
/*  972:     */         catch (ClassNotFoundException e)
/*  973:     */         {
/*  974:1023 */           throw new AnnotationException("Unable to find " + element.getPath() + " " + nodeName + ": " + mapKeyClassName, e);
/*  975:     */         }
/*  976:1027 */         ad.setValue("value", clazz);
/*  977:     */       }
/*  978:1029 */       annotationList.add(AnnotationFactory.create(ad));
/*  979:     */     }
/*  980:     */   }
/*  981:     */   
/*  982:     */   private void getCollectionTable(List<Annotation> annotationList, Element element, XMLContext.Default defaults)
/*  983:     */   {
/*  984:1034 */     Element subelement = element != null ? element.element("collection-table") : null;
/*  985:1035 */     if (subelement != null)
/*  986:     */     {
/*  987:1036 */       AnnotationDescriptor annotation = new AnnotationDescriptor(CollectionTable.class);
/*  988:1037 */       copyStringAttribute(annotation, subelement, "name", false);
/*  989:1038 */       copyStringAttribute(annotation, subelement, "catalog", false);
/*  990:1039 */       if ((StringHelper.isNotEmpty(defaults.getCatalog())) && (StringHelper.isEmpty((String)annotation.valueOf("catalog")))) {
/*  991:1041 */         annotation.setValue("catalog", defaults.getCatalog());
/*  992:     */       }
/*  993:1043 */       copyStringAttribute(annotation, subelement, "schema", false);
/*  994:1044 */       if ((StringHelper.isNotEmpty(defaults.getSchema())) && (StringHelper.isEmpty((String)annotation.valueOf("schema")))) {
/*  995:1046 */         annotation.setValue("schema", defaults.getSchema());
/*  996:     */       }
/*  997:1048 */       JoinColumn[] joinColumns = getJoinColumns(subelement, false);
/*  998:1049 */       if (joinColumns.length > 0) {
/*  999:1050 */         annotation.setValue("joinColumns", joinColumns);
/* 1000:     */       }
/* 1001:1052 */       buildUniqueConstraints(annotation, subelement);
/* 1002:1053 */       annotationList.add(AnnotationFactory.create(annotation));
/* 1003:     */     }
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   private void buildJoinColumns(List<Annotation> annotationList, Element element)
/* 1007:     */   {
/* 1008:1058 */     JoinColumn[] joinColumns = getJoinColumns(element, false);
/* 1009:1059 */     if (joinColumns.length > 0)
/* 1010:     */     {
/* 1011:1060 */       AnnotationDescriptor ad = new AnnotationDescriptor(JoinColumns.class);
/* 1012:1061 */       ad.setValue("value", joinColumns);
/* 1013:1062 */       annotationList.add(AnnotationFactory.create(ad));
/* 1014:     */     }
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   private void getCascades(AnnotationDescriptor ad, Element element, XMLContext.Default defaults)
/* 1018:     */   {
/* 1019:1067 */     List<Element> elements = element != null ? element.elements("cascade") : new ArrayList(0);
/* 1020:1068 */     List<CascadeType> cascades = new ArrayList();
/* 1021:1069 */     for (Element subelement : elements)
/* 1022:     */     {
/* 1023:1070 */       if (subelement.element("cascade-all") != null) {
/* 1024:1071 */         cascades.add(CascadeType.ALL);
/* 1025:     */       }
/* 1026:1073 */       if (subelement.element("cascade-persist") != null) {
/* 1027:1074 */         cascades.add(CascadeType.PERSIST);
/* 1028:     */       }
/* 1029:1076 */       if (subelement.element("cascade-merge") != null) {
/* 1030:1077 */         cascades.add(CascadeType.MERGE);
/* 1031:     */       }
/* 1032:1079 */       if (subelement.element("cascade-remove") != null) {
/* 1033:1080 */         cascades.add(CascadeType.REMOVE);
/* 1034:     */       }
/* 1035:1082 */       if (subelement.element("cascade-refresh") != null) {
/* 1036:1083 */         cascades.add(CascadeType.REFRESH);
/* 1037:     */       }
/* 1038:1085 */       if (subelement.element("cascade-detach") != null) {
/* 1039:1086 */         cascades.add(CascadeType.DETACH);
/* 1040:     */       }
/* 1041:     */     }
/* 1042:1089 */     if ((Boolean.TRUE.equals(defaults.getCascadePersist())) && (!cascades.contains(CascadeType.ALL)) && (!cascades.contains(CascadeType.PERSIST))) {
/* 1043:1091 */       cascades.add(CascadeType.PERSIST);
/* 1044:     */     }
/* 1045:1093 */     if (cascades.size() > 0) {
/* 1046:1094 */       ad.setValue("cascade", cascades.toArray(new CascadeType[cascades.size()]));
/* 1047:     */     }
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   private void getEmbedded(List<Annotation> annotationList, XMLContext.Default defaults)
/* 1051:     */   {
/* 1052:1099 */     for (Element element : this.elementsForProperty) {
/* 1053:1100 */       if ("embedded".equals(element.getName()))
/* 1054:     */       {
/* 1055:1101 */         AnnotationDescriptor ad = new AnnotationDescriptor(Embedded.class);
/* 1056:1102 */         annotationList.add(AnnotationFactory.create(ad));
/* 1057:1103 */         Annotation annotation = getAttributeOverrides(element, defaults, false);
/* 1058:1104 */         addIfNotNull(annotationList, annotation);
/* 1059:1105 */         annotation = getAssociationOverrides(element, defaults, false);
/* 1060:1106 */         addIfNotNull(annotationList, annotation);
/* 1061:1107 */         getAccessType(annotationList, element);
/* 1062:     */       }
/* 1063:     */     }
/* 1064:1110 */     if ((this.elementsForProperty.size() == 0) && (defaults.canUseJavaAnnotations()))
/* 1065:     */     {
/* 1066:1111 */       Annotation annotation = getJavaAnnotation(Embedded.class);
/* 1067:1112 */       if (annotation != null)
/* 1068:     */       {
/* 1069:1113 */         annotationList.add(annotation);
/* 1070:1114 */         annotation = getJavaAnnotation(AttributeOverride.class);
/* 1071:1115 */         addIfNotNull(annotationList, annotation);
/* 1072:1116 */         annotation = getJavaAnnotation(AttributeOverrides.class);
/* 1073:1117 */         addIfNotNull(annotationList, annotation);
/* 1074:1118 */         annotation = getJavaAnnotation(AssociationOverride.class);
/* 1075:1119 */         addIfNotNull(annotationList, annotation);
/* 1076:1120 */         annotation = getJavaAnnotation(AssociationOverrides.class);
/* 1077:1121 */         addIfNotNull(annotationList, annotation);
/* 1078:     */       }
/* 1079:     */     }
/* 1080:     */   }
/* 1081:     */   
/* 1082:     */   private Transient getTransient(XMLContext.Default defaults)
/* 1083:     */   {
/* 1084:1127 */     for (Element element : this.elementsForProperty) {
/* 1085:1128 */       if ("transient".equals(element.getName()))
/* 1086:     */       {
/* 1087:1129 */         AnnotationDescriptor ad = new AnnotationDescriptor(Transient.class);
/* 1088:1130 */         return (Transient)AnnotationFactory.create(ad);
/* 1089:     */       }
/* 1090:     */     }
/* 1091:1133 */     if ((this.elementsForProperty.size() == 0) && (defaults.canUseJavaAnnotations())) {
/* 1092:1134 */       return (Transient)getJavaAnnotation(Transient.class);
/* 1093:     */     }
/* 1094:1137 */     return null;
/* 1095:     */   }
/* 1096:     */   
/* 1097:     */   private void getVersion(List<Annotation> annotationList, XMLContext.Default defaults)
/* 1098:     */   {
/* 1099:1142 */     for (Element element : this.elementsForProperty) {
/* 1100:1143 */       if ("version".equals(element.getName()))
/* 1101:     */       {
/* 1102:1144 */         Annotation annotation = buildColumns(element);
/* 1103:1145 */         addIfNotNull(annotationList, annotation);
/* 1104:1146 */         getTemporal(annotationList, element);
/* 1105:1147 */         AnnotationDescriptor basic = new AnnotationDescriptor(Version.class);
/* 1106:1148 */         annotationList.add(AnnotationFactory.create(basic));
/* 1107:1149 */         getAccessType(annotationList, element);
/* 1108:     */       }
/* 1109:     */     }
/* 1110:1152 */     if ((this.elementsForProperty.size() == 0) && (defaults.canUseJavaAnnotations()))
/* 1111:     */     {
/* 1112:1154 */       Annotation annotation = getJavaAnnotation(Version.class);
/* 1113:1155 */       if (annotation != null)
/* 1114:     */       {
/* 1115:1156 */         annotationList.add(annotation);
/* 1116:1157 */         annotation = getJavaAnnotation(Column.class);
/* 1117:1158 */         addIfNotNull(annotationList, annotation);
/* 1118:1159 */         annotation = getJavaAnnotation(Columns.class);
/* 1119:1160 */         addIfNotNull(annotationList, annotation);
/* 1120:1161 */         annotation = getJavaAnnotation(Temporal.class);
/* 1121:1162 */         addIfNotNull(annotationList, annotation);
/* 1122:     */       }
/* 1123:     */     }
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   private void getBasic(List<Annotation> annotationList, XMLContext.Default defaults)
/* 1127:     */   {
/* 1128:1168 */     for (Element element : this.elementsForProperty) {
/* 1129:1169 */       if ("basic".equals(element.getName()))
/* 1130:     */       {
/* 1131:1170 */         Annotation annotation = buildColumns(element);
/* 1132:1171 */         addIfNotNull(annotationList, annotation);
/* 1133:1172 */         getAccessType(annotationList, element);
/* 1134:1173 */         getTemporal(annotationList, element);
/* 1135:1174 */         getLob(annotationList, element);
/* 1136:1175 */         getEnumerated(annotationList, element);
/* 1137:1176 */         AnnotationDescriptor basic = new AnnotationDescriptor(Basic.class);
/* 1138:1177 */         getFetchType(basic, element);
/* 1139:1178 */         copyBooleanAttribute(basic, element, "optional");
/* 1140:1179 */         annotationList.add(AnnotationFactory.create(basic));
/* 1141:     */       }
/* 1142:     */     }
/* 1143:1182 */     if ((this.elementsForProperty.size() == 0) && (defaults.canUseJavaAnnotations()))
/* 1144:     */     {
/* 1145:1184 */       Annotation annotation = getJavaAnnotation(Basic.class);
/* 1146:1185 */       addIfNotNull(annotationList, annotation);
/* 1147:1186 */       annotation = getJavaAnnotation(Lob.class);
/* 1148:1187 */       addIfNotNull(annotationList, annotation);
/* 1149:1188 */       annotation = getJavaAnnotation(Enumerated.class);
/* 1150:1189 */       addIfNotNull(annotationList, annotation);
/* 1151:1190 */       annotation = getJavaAnnotation(Temporal.class);
/* 1152:1191 */       addIfNotNull(annotationList, annotation);
/* 1153:1192 */       annotation = getJavaAnnotation(Column.class);
/* 1154:1193 */       addIfNotNull(annotationList, annotation);
/* 1155:1194 */       annotation = getJavaAnnotation(Columns.class);
/* 1156:1195 */       addIfNotNull(annotationList, annotation);
/* 1157:1196 */       annotation = getJavaAnnotation(AttributeOverride.class);
/* 1158:1197 */       addIfNotNull(annotationList, annotation);
/* 1159:1198 */       annotation = getJavaAnnotation(AttributeOverrides.class);
/* 1160:1199 */       addIfNotNull(annotationList, annotation);
/* 1161:1200 */       annotation = getJavaAnnotation(AssociationOverride.class);
/* 1162:1201 */       addIfNotNull(annotationList, annotation);
/* 1163:1202 */       annotation = getJavaAnnotation(AssociationOverrides.class);
/* 1164:1203 */       addIfNotNull(annotationList, annotation);
/* 1165:     */     }
/* 1166:     */   }
/* 1167:     */   
/* 1168:     */   private void getEnumerated(List<Annotation> annotationList, Element element)
/* 1169:     */   {
/* 1170:1208 */     Element subElement = element != null ? element.element("enumerated") : null;
/* 1171:1209 */     if (subElement != null)
/* 1172:     */     {
/* 1173:1210 */       AnnotationDescriptor ad = new AnnotationDescriptor(Enumerated.class);
/* 1174:1211 */       String enumerated = subElement.getTextTrim();
/* 1175:1212 */       if ("ORDINAL".equalsIgnoreCase(enumerated)) {
/* 1176:1213 */         ad.setValue("value", EnumType.ORDINAL);
/* 1177:1215 */       } else if ("STRING".equalsIgnoreCase(enumerated)) {
/* 1178:1216 */         ad.setValue("value", EnumType.STRING);
/* 1179:1218 */       } else if (StringHelper.isNotEmpty(enumerated)) {
/* 1180:1219 */         throw new AnnotationException("Unknown EnumType: " + enumerated + ". " + "Activate schema validation for more information");
/* 1181:     */       }
/* 1182:1221 */       annotationList.add(AnnotationFactory.create(ad));
/* 1183:     */     }
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   private void getLob(List<Annotation> annotationList, Element element)
/* 1187:     */   {
/* 1188:1226 */     Element subElement = element != null ? element.element("lob") : null;
/* 1189:1227 */     if (subElement != null) {
/* 1190:1228 */       annotationList.add(AnnotationFactory.create(new AnnotationDescriptor(Lob.class)));
/* 1191:     */     }
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   private void getFetchType(AnnotationDescriptor descriptor, Element element)
/* 1195:     */   {
/* 1196:1233 */     String fetchString = element != null ? element.attributeValue("fetch") : null;
/* 1197:1234 */     if (fetchString != null) {
/* 1198:1235 */       if ("eager".equalsIgnoreCase(fetchString)) {
/* 1199:1236 */         descriptor.setValue("fetch", FetchType.EAGER);
/* 1200:1238 */       } else if ("lazy".equalsIgnoreCase(fetchString)) {
/* 1201:1239 */         descriptor.setValue("fetch", FetchType.LAZY);
/* 1202:     */       }
/* 1203:     */     }
/* 1204:     */   }
/* 1205:     */   
/* 1206:     */   private void getEmbeddedId(List<Annotation> annotationList, XMLContext.Default defaults)
/* 1207:     */   {
/* 1208:1245 */     for (Element element : this.elementsForProperty) {
/* 1209:1246 */       if (("embedded-id".equals(element.getName())) && 
/* 1210:1247 */         (isProcessingId(defaults)))
/* 1211:     */       {
/* 1212:1248 */         Annotation annotation = getAttributeOverrides(element, defaults, false);
/* 1213:1249 */         addIfNotNull(annotationList, annotation);
/* 1214:1250 */         annotation = getAssociationOverrides(element, defaults, false);
/* 1215:1251 */         addIfNotNull(annotationList, annotation);
/* 1216:1252 */         AnnotationDescriptor ad = new AnnotationDescriptor(EmbeddedId.class);
/* 1217:1253 */         annotationList.add(AnnotationFactory.create(ad));
/* 1218:1254 */         getAccessType(annotationList, element);
/* 1219:     */       }
/* 1220:     */     }
/* 1221:1258 */     if ((this.elementsForProperty.size() == 0) && (defaults.canUseJavaAnnotations()))
/* 1222:     */     {
/* 1223:1259 */       Annotation annotation = getJavaAnnotation(EmbeddedId.class);
/* 1224:1260 */       if (annotation != null)
/* 1225:     */       {
/* 1226:1261 */         annotationList.add(annotation);
/* 1227:1262 */         annotation = getJavaAnnotation(Column.class);
/* 1228:1263 */         addIfNotNull(annotationList, annotation);
/* 1229:1264 */         annotation = getJavaAnnotation(Columns.class);
/* 1230:1265 */         addIfNotNull(annotationList, annotation);
/* 1231:1266 */         annotation = getJavaAnnotation(GeneratedValue.class);
/* 1232:1267 */         addIfNotNull(annotationList, annotation);
/* 1233:1268 */         annotation = getJavaAnnotation(Temporal.class);
/* 1234:1269 */         addIfNotNull(annotationList, annotation);
/* 1235:1270 */         annotation = getJavaAnnotation(TableGenerator.class);
/* 1236:1271 */         addIfNotNull(annotationList, annotation);
/* 1237:1272 */         annotation = getJavaAnnotation(SequenceGenerator.class);
/* 1238:1273 */         addIfNotNull(annotationList, annotation);
/* 1239:1274 */         annotation = getJavaAnnotation(AttributeOverride.class);
/* 1240:1275 */         addIfNotNull(annotationList, annotation);
/* 1241:1276 */         annotation = getJavaAnnotation(AttributeOverrides.class);
/* 1242:1277 */         addIfNotNull(annotationList, annotation);
/* 1243:1278 */         annotation = getJavaAnnotation(AssociationOverride.class);
/* 1244:1279 */         addIfNotNull(annotationList, annotation);
/* 1245:1280 */         annotation = getJavaAnnotation(AssociationOverrides.class);
/* 1246:1281 */         addIfNotNull(annotationList, annotation);
/* 1247:     */       }
/* 1248:     */     }
/* 1249:     */   }
/* 1250:     */   
/* 1251:     */   private void preCalculateElementsForProperty(Element tree)
/* 1252:     */   {
/* 1253:1287 */     this.elementsForProperty = new ArrayList();
/* 1254:1288 */     Element element = tree != null ? tree.element("attributes") : null;
/* 1255:1290 */     if (element != null) {
/* 1256:1291 */       for (Element subelement : element.elements()) {
/* 1257:1292 */         if (this.propertyName.equals(subelement.attributeValue("name"))) {
/* 1258:1293 */           this.elementsForProperty.add(subelement);
/* 1259:     */         }
/* 1260:     */       }
/* 1261:     */     }
/* 1262:1298 */     if (tree != null) {
/* 1263:1299 */       for (Element subelement : tree.elements()) {
/* 1264:1300 */         if (this.propertyName.equals(subelement.attributeValue("method-name"))) {
/* 1265:1301 */           this.elementsForProperty.add(subelement);
/* 1266:     */         }
/* 1267:     */       }
/* 1268:     */     }
/* 1269:     */   }
/* 1270:     */   
/* 1271:     */   private void getId(List<Annotation> annotationList, XMLContext.Default defaults)
/* 1272:     */   {
/* 1273:1308 */     for (Element element : this.elementsForProperty) {
/* 1274:1309 */       if ("id".equals(element.getName()))
/* 1275:     */       {
/* 1276:1310 */         boolean processId = isProcessingId(defaults);
/* 1277:1311 */         if (processId)
/* 1278:     */         {
/* 1279:1312 */           Annotation annotation = buildColumns(element);
/* 1280:1313 */           addIfNotNull(annotationList, annotation);
/* 1281:1314 */           annotation = buildGeneratedValue(element);
/* 1282:1315 */           addIfNotNull(annotationList, annotation);
/* 1283:1316 */           getTemporal(annotationList, element);
/* 1284:     */           
/* 1285:1318 */           annotation = getTableGenerator(element, defaults);
/* 1286:1319 */           addIfNotNull(annotationList, annotation);
/* 1287:1320 */           annotation = getSequenceGenerator(element, defaults);
/* 1288:1321 */           addIfNotNull(annotationList, annotation);
/* 1289:1322 */           AnnotationDescriptor id = new AnnotationDescriptor(Id.class);
/* 1290:1323 */           annotationList.add(AnnotationFactory.create(id));
/* 1291:1324 */           getAccessType(annotationList, element);
/* 1292:     */         }
/* 1293:     */       }
/* 1294:     */     }
/* 1295:1328 */     if ((this.elementsForProperty.size() == 0) && (defaults.canUseJavaAnnotations()))
/* 1296:     */     {
/* 1297:1329 */       Annotation annotation = getJavaAnnotation(Id.class);
/* 1298:1330 */       if (annotation != null)
/* 1299:     */       {
/* 1300:1331 */         annotationList.add(annotation);
/* 1301:1332 */         annotation = getJavaAnnotation(Column.class);
/* 1302:1333 */         addIfNotNull(annotationList, annotation);
/* 1303:1334 */         annotation = getJavaAnnotation(Columns.class);
/* 1304:1335 */         addIfNotNull(annotationList, annotation);
/* 1305:1336 */         annotation = getJavaAnnotation(GeneratedValue.class);
/* 1306:1337 */         addIfNotNull(annotationList, annotation);
/* 1307:1338 */         annotation = getJavaAnnotation(Temporal.class);
/* 1308:1339 */         addIfNotNull(annotationList, annotation);
/* 1309:1340 */         annotation = getJavaAnnotation(TableGenerator.class);
/* 1310:1341 */         addIfNotNull(annotationList, annotation);
/* 1311:1342 */         annotation = getJavaAnnotation(SequenceGenerator.class);
/* 1312:1343 */         addIfNotNull(annotationList, annotation);
/* 1313:1344 */         annotation = getJavaAnnotation(AttributeOverride.class);
/* 1314:1345 */         addIfNotNull(annotationList, annotation);
/* 1315:1346 */         annotation = getJavaAnnotation(AttributeOverrides.class);
/* 1316:1347 */         addIfNotNull(annotationList, annotation);
/* 1317:1348 */         annotation = getJavaAnnotation(AssociationOverride.class);
/* 1318:1349 */         addIfNotNull(annotationList, annotation);
/* 1319:1350 */         annotation = getJavaAnnotation(AssociationOverrides.class);
/* 1320:1351 */         addIfNotNull(annotationList, annotation);
/* 1321:     */       }
/* 1322:     */     }
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   private boolean isProcessingId(XMLContext.Default defaults)
/* 1326:     */   {
/* 1327:1357 */     boolean isExplicit = defaults.getAccess() != null;
/* 1328:1358 */     boolean correctAccess = ((PropertyType.PROPERTY.equals(this.propertyType)) && (AccessType.PROPERTY.equals(defaults.getAccess()))) || ((PropertyType.FIELD.equals(this.propertyType)) && (AccessType.FIELD.equals(defaults.getAccess())));
/* 1329:     */     
/* 1330:     */ 
/* 1331:     */ 
/* 1332:1362 */     boolean hasId = (defaults.canUseJavaAnnotations()) && ((isJavaAnnotationPresent(Id.class)) || (isJavaAnnotationPresent(EmbeddedId.class)));
/* 1333:     */     
/* 1334:     */ 
/* 1335:1365 */     boolean mirrorAttributeIsId = (defaults.canUseJavaAnnotations()) && (this.mirroredAttribute != null) && ((this.mirroredAttribute.isAnnotationPresent(Id.class)) || (this.mirroredAttribute.isAnnotationPresent(EmbeddedId.class)));
/* 1336:     */     
/* 1337:     */ 
/* 1338:     */ 
/* 1339:1369 */     boolean propertyIsDefault = (PropertyType.PROPERTY.equals(this.propertyType)) && (!mirrorAttributeIsId);
/* 1340:     */     
/* 1341:1371 */     return (correctAccess) || ((!isExplicit) && (hasId)) || ((!isExplicit) && (propertyIsDefault));
/* 1342:     */   }
/* 1343:     */   
/* 1344:     */   private Columns buildColumns(Element element)
/* 1345:     */   {
/* 1346:1375 */     List<Element> subelements = element.elements("column");
/* 1347:1376 */     List<Column> columns = new ArrayList(subelements.size());
/* 1348:1377 */     for (Element subelement : subelements) {
/* 1349:1378 */       columns.add(getColumn(subelement, false, element));
/* 1350:     */     }
/* 1351:1380 */     if (columns.size() > 0)
/* 1352:     */     {
/* 1353:1381 */       AnnotationDescriptor columnsDescr = new AnnotationDescriptor(Columns.class);
/* 1354:1382 */       columnsDescr.setValue("columns", columns.toArray(new Column[columns.size()]));
/* 1355:1383 */       return (Columns)AnnotationFactory.create(columnsDescr);
/* 1356:     */     }
/* 1357:1386 */     return null;
/* 1358:     */   }
/* 1359:     */   
/* 1360:     */   private GeneratedValue buildGeneratedValue(Element element)
/* 1361:     */   {
/* 1362:1391 */     Element subElement = element != null ? element.element("generated-value") : null;
/* 1363:1392 */     if (subElement != null)
/* 1364:     */     {
/* 1365:1393 */       AnnotationDescriptor ad = new AnnotationDescriptor(GeneratedValue.class);
/* 1366:1394 */       String strategy = subElement.attributeValue("strategy");
/* 1367:1395 */       if ("TABLE".equalsIgnoreCase(strategy)) {
/* 1368:1396 */         ad.setValue("strategy", GenerationType.TABLE);
/* 1369:1398 */       } else if ("SEQUENCE".equalsIgnoreCase(strategy)) {
/* 1370:1399 */         ad.setValue("strategy", GenerationType.SEQUENCE);
/* 1371:1401 */       } else if ("IDENTITY".equalsIgnoreCase(strategy)) {
/* 1372:1402 */         ad.setValue("strategy", GenerationType.IDENTITY);
/* 1373:1404 */       } else if ("AUTO".equalsIgnoreCase(strategy)) {
/* 1374:1405 */         ad.setValue("strategy", GenerationType.AUTO);
/* 1375:1407 */       } else if (StringHelper.isNotEmpty(strategy)) {
/* 1376:1408 */         throw new AnnotationException("Unknown GenerationType: " + strategy + ". " + "Activate schema validation for more information");
/* 1377:     */       }
/* 1378:1410 */       copyStringAttribute(ad, subElement, "generator", false);
/* 1379:1411 */       return (GeneratedValue)AnnotationFactory.create(ad);
/* 1380:     */     }
/* 1381:1414 */     return null;
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   private void getTemporal(List<Annotation> annotationList, Element element)
/* 1385:     */   {
/* 1386:1419 */     Element subElement = element != null ? element.element("temporal") : null;
/* 1387:1420 */     if (subElement != null)
/* 1388:     */     {
/* 1389:1421 */       AnnotationDescriptor ad = new AnnotationDescriptor(Temporal.class);
/* 1390:1422 */       String temporal = subElement.getTextTrim();
/* 1391:1423 */       if ("DATE".equalsIgnoreCase(temporal)) {
/* 1392:1424 */         ad.setValue("value", TemporalType.DATE);
/* 1393:1426 */       } else if ("TIME".equalsIgnoreCase(temporal)) {
/* 1394:1427 */         ad.setValue("value", TemporalType.TIME);
/* 1395:1429 */       } else if ("TIMESTAMP".equalsIgnoreCase(temporal)) {
/* 1396:1430 */         ad.setValue("value", TemporalType.TIMESTAMP);
/* 1397:1432 */       } else if (StringHelper.isNotEmpty(temporal)) {
/* 1398:1433 */         throw new AnnotationException("Unknown TemporalType: " + temporal + ". " + "Activate schema validation for more information");
/* 1399:     */       }
/* 1400:1435 */       annotationList.add(AnnotationFactory.create(ad));
/* 1401:     */     }
/* 1402:     */   }
/* 1403:     */   
/* 1404:     */   private void getAccessType(List<Annotation> annotationList, Element element)
/* 1405:     */   {
/* 1406:1440 */     if (element == null) {
/* 1407:1441 */       return;
/* 1408:     */     }
/* 1409:1443 */     String access = element.attributeValue("access");
/* 1410:1444 */     if (access != null)
/* 1411:     */     {
/* 1412:1445 */       AnnotationDescriptor ad = new AnnotationDescriptor(Access.class);
/* 1413:     */       AccessType type;
/* 1414:     */       try
/* 1415:     */       {
/* 1416:1448 */         type = AccessType.valueOf(access);
/* 1417:     */       }
/* 1418:     */       catch (IllegalArgumentException e)
/* 1419:     */       {
/* 1420:1451 */         throw new AnnotationException(access + " is not a valid access type. Check you xml confguration.");
/* 1421:     */       }
/* 1422:1454 */       if (((AccessType.PROPERTY.equals(type)) && ((this.element instanceof Method))) || ((AccessType.FIELD.equals(type)) && ((this.element instanceof Field)))) {
/* 1423:1456 */         return;
/* 1424:     */       }
/* 1425:1459 */       ad.setValue("value", type);
/* 1426:1460 */       annotationList.add(AnnotationFactory.create(ad));
/* 1427:     */     }
/* 1428:     */   }
/* 1429:     */   
/* 1430:     */   private AssociationOverrides getAssociationOverrides(Element tree, XMLContext.Default defaults, boolean mergeWithAnnotations)
/* 1431:     */   {
/* 1432:1471 */     List<AssociationOverride> attributes = buildAssociationOverrides(tree, defaults);
/* 1433:1472 */     if ((mergeWithAnnotations) && (defaults.canUseJavaAnnotations()))
/* 1434:     */     {
/* 1435:1473 */       AssociationOverride annotation = (AssociationOverride)getJavaAnnotation(AssociationOverride.class);
/* 1436:1474 */       addAssociationOverrideIfNeeded(annotation, attributes);
/* 1437:1475 */       AssociationOverrides annotations = (AssociationOverrides)getJavaAnnotation(AssociationOverrides.class);
/* 1438:1476 */       if (annotations != null) {
/* 1439:1477 */         for (AssociationOverride current : annotations.value()) {
/* 1440:1478 */           addAssociationOverrideIfNeeded(current, attributes);
/* 1441:     */         }
/* 1442:     */       }
/* 1443:     */     }
/* 1444:1482 */     if (attributes.size() > 0)
/* 1445:     */     {
/* 1446:1483 */       AnnotationDescriptor ad = new AnnotationDescriptor(AssociationOverrides.class);
/* 1447:1484 */       ad.setValue("value", attributes.toArray(new AssociationOverride[attributes.size()]));
/* 1448:1485 */       return (AssociationOverrides)AnnotationFactory.create(ad);
/* 1449:     */     }
/* 1450:1488 */     return null;
/* 1451:     */   }
/* 1452:     */   
/* 1453:     */   private List<AssociationOverride> buildAssociationOverrides(Element element, XMLContext.Default defaults)
/* 1454:     */   {
/* 1455:1493 */     List<Element> subelements = element == null ? null : element.elements("association-override");
/* 1456:1494 */     List<AssociationOverride> overrides = new ArrayList();
/* 1457:1495 */     if ((subelements != null) && (subelements.size() > 0)) {
/* 1458:1496 */       for (Element current : subelements)
/* 1459:     */       {
/* 1460:1497 */         AnnotationDescriptor override = new AnnotationDescriptor(AssociationOverride.class);
/* 1461:1498 */         copyStringAttribute(override, current, "name", true);
/* 1462:1499 */         override.setValue("joinColumns", getJoinColumns(current, false));
/* 1463:1500 */         JoinTable joinTable = buildJoinTable(current, defaults);
/* 1464:1501 */         if (joinTable != null) {
/* 1465:1502 */           override.setValue("joinTable", joinTable);
/* 1466:     */         }
/* 1467:1504 */         overrides.add((AssociationOverride)AnnotationFactory.create(override));
/* 1468:     */       }
/* 1469:     */     }
/* 1470:1507 */     return overrides;
/* 1471:     */   }
/* 1472:     */   
/* 1473:     */   private JoinColumn[] getJoinColumns(Element element, boolean isInverse)
/* 1474:     */   {
/* 1475:1511 */     List<Element> subelements = element != null ? element.elements(isInverse ? "inverse-join-column" : "join-column") : null;
/* 1476:     */     
/* 1477:     */ 
/* 1478:1514 */     List<JoinColumn> joinColumns = new ArrayList();
/* 1479:1515 */     if (subelements != null) {
/* 1480:1516 */       for (Element subelement : subelements)
/* 1481:     */       {
/* 1482:1517 */         AnnotationDescriptor column = new AnnotationDescriptor(JoinColumn.class);
/* 1483:1518 */         copyStringAttribute(column, subelement, "name", false);
/* 1484:1519 */         copyStringAttribute(column, subelement, "referenced-column-name", false);
/* 1485:1520 */         copyBooleanAttribute(column, subelement, "unique");
/* 1486:1521 */         copyBooleanAttribute(column, subelement, "nullable");
/* 1487:1522 */         copyBooleanAttribute(column, subelement, "insertable");
/* 1488:1523 */         copyBooleanAttribute(column, subelement, "updatable");
/* 1489:1524 */         copyStringAttribute(column, subelement, "column-definition", false);
/* 1490:1525 */         copyStringAttribute(column, subelement, "table", false);
/* 1491:1526 */         joinColumns.add((JoinColumn)AnnotationFactory.create(column));
/* 1492:     */       }
/* 1493:     */     }
/* 1494:1529 */     return (JoinColumn[])joinColumns.toArray(new JoinColumn[joinColumns.size()]);
/* 1495:     */   }
/* 1496:     */   
/* 1497:     */   private void addAssociationOverrideIfNeeded(AssociationOverride annotation, List<AssociationOverride> overrides)
/* 1498:     */   {
/* 1499:1533 */     if (annotation != null)
/* 1500:     */     {
/* 1501:1534 */       String overrideName = annotation.name();
/* 1502:1535 */       boolean present = false;
/* 1503:1536 */       for (AssociationOverride current : overrides) {
/* 1504:1537 */         if (current.name().equals(overrideName))
/* 1505:     */         {
/* 1506:1538 */           present = true;
/* 1507:1539 */           break;
/* 1508:     */         }
/* 1509:     */       }
/* 1510:1542 */       if (!present) {
/* 1511:1543 */         overrides.add(annotation);
/* 1512:     */       }
/* 1513:     */     }
/* 1514:     */   }
/* 1515:     */   
/* 1516:     */   private AttributeOverrides getAttributeOverrides(Element tree, XMLContext.Default defaults, boolean mergeWithAnnotations)
/* 1517:     */   {
/* 1518:1555 */     List<AttributeOverride> attributes = buildAttributeOverrides(tree, "attribute-override");
/* 1519:1556 */     return mergeAttributeOverrides(defaults, attributes, mergeWithAnnotations);
/* 1520:     */   }
/* 1521:     */   
/* 1522:     */   private AttributeOverrides mergeAttributeOverrides(XMLContext.Default defaults, List<AttributeOverride> attributes, boolean mergeWithAnnotations)
/* 1523:     */   {
/* 1524:1566 */     if ((mergeWithAnnotations) && (defaults.canUseJavaAnnotations()))
/* 1525:     */     {
/* 1526:1567 */       AttributeOverride annotation = (AttributeOverride)getJavaAnnotation(AttributeOverride.class);
/* 1527:1568 */       addAttributeOverrideIfNeeded(annotation, attributes);
/* 1528:1569 */       AttributeOverrides annotations = (AttributeOverrides)getJavaAnnotation(AttributeOverrides.class);
/* 1529:1570 */       if (annotations != null) {
/* 1530:1571 */         for (AttributeOverride current : annotations.value()) {
/* 1531:1572 */           addAttributeOverrideIfNeeded(current, attributes);
/* 1532:     */         }
/* 1533:     */       }
/* 1534:     */     }
/* 1535:1576 */     if (attributes.size() > 0)
/* 1536:     */     {
/* 1537:1577 */       AnnotationDescriptor ad = new AnnotationDescriptor(AttributeOverrides.class);
/* 1538:1578 */       ad.setValue("value", attributes.toArray(new AttributeOverride[attributes.size()]));
/* 1539:1579 */       return (AttributeOverrides)AnnotationFactory.create(ad);
/* 1540:     */     }
/* 1541:1582 */     return null;
/* 1542:     */   }
/* 1543:     */   
/* 1544:     */   private List<AttributeOverride> buildAttributeOverrides(Element element, String nodeName)
/* 1545:     */   {
/* 1546:1587 */     List<Element> subelements = element == null ? null : element.elements(nodeName);
/* 1547:1588 */     return buildAttributeOverrides(subelements, nodeName);
/* 1548:     */   }
/* 1549:     */   
/* 1550:     */   private List<AttributeOverride> buildAttributeOverrides(List<Element> subelements, String nodeName)
/* 1551:     */   {
/* 1552:1592 */     List<AttributeOverride> overrides = new ArrayList();
/* 1553:1593 */     if ((subelements != null) && (subelements.size() > 0)) {
/* 1554:1594 */       for (Element current : subelements) {
/* 1555:1595 */         if (current.getName().equals(nodeName))
/* 1556:     */         {
/* 1557:1598 */           AnnotationDescriptor override = new AnnotationDescriptor(AttributeOverride.class);
/* 1558:1599 */           copyStringAttribute(override, current, "name", true);
/* 1559:1600 */           Element column = current.element("column");
/* 1560:1601 */           override.setValue("column", getColumn(column, true, current));
/* 1561:1602 */           overrides.add((AttributeOverride)AnnotationFactory.create(override));
/* 1562:     */         }
/* 1563:     */       }
/* 1564:     */     }
/* 1565:1605 */     return overrides;
/* 1566:     */   }
/* 1567:     */   
/* 1568:     */   private Column getColumn(Element element, boolean isMandatory, Element current)
/* 1569:     */   {
/* 1570:1610 */     if (element != null)
/* 1571:     */     {
/* 1572:1611 */       AnnotationDescriptor column = new AnnotationDescriptor(Column.class);
/* 1573:1612 */       copyStringAttribute(column, element, "name", false);
/* 1574:1613 */       copyBooleanAttribute(column, element, "unique");
/* 1575:1614 */       copyBooleanAttribute(column, element, "nullable");
/* 1576:1615 */       copyBooleanAttribute(column, element, "insertable");
/* 1577:1616 */       copyBooleanAttribute(column, element, "updatable");
/* 1578:1617 */       copyStringAttribute(column, element, "column-definition", false);
/* 1579:1618 */       copyStringAttribute(column, element, "table", false);
/* 1580:1619 */       copyIntegerAttribute(column, element, "length");
/* 1581:1620 */       copyIntegerAttribute(column, element, "precision");
/* 1582:1621 */       copyIntegerAttribute(column, element, "scale");
/* 1583:1622 */       return (Column)AnnotationFactory.create(column);
/* 1584:     */     }
/* 1585:1625 */     if (isMandatory) {
/* 1586:1626 */       throw new AnnotationException(current.getPath() + ".column is mandatory. " + "Activate schema validation for more information");
/* 1587:     */     }
/* 1588:1628 */     return null;
/* 1589:     */   }
/* 1590:     */   
/* 1591:     */   private void addAttributeOverrideIfNeeded(AttributeOverride annotation, List<AttributeOverride> overrides)
/* 1592:     */   {
/* 1593:1633 */     if (annotation != null)
/* 1594:     */     {
/* 1595:1634 */       String overrideName = annotation.name();
/* 1596:1635 */       boolean present = false;
/* 1597:1636 */       for (AttributeOverride current : overrides) {
/* 1598:1637 */         if (current.name().equals(overrideName))
/* 1599:     */         {
/* 1600:1638 */           present = true;
/* 1601:1639 */           break;
/* 1602:     */         }
/* 1603:     */       }
/* 1604:1642 */       if (!present) {
/* 1605:1643 */         overrides.add(annotation);
/* 1606:     */       }
/* 1607:     */     }
/* 1608:     */   }
/* 1609:     */   
/* 1610:     */   private Access getAccessType(Element tree, XMLContext.Default defaults)
/* 1611:     */   {
/* 1612:1649 */     String access = tree == null ? null : tree.attributeValue("access");
/* 1613:1650 */     if (access != null)
/* 1614:     */     {
/* 1615:1651 */       AnnotationDescriptor ad = new AnnotationDescriptor(Access.class);
/* 1616:     */       AccessType type;
/* 1617:     */       try
/* 1618:     */       {
/* 1619:1654 */         type = AccessType.valueOf(access);
/* 1620:     */       }
/* 1621:     */       catch (IllegalArgumentException e)
/* 1622:     */       {
/* 1623:1657 */         throw new AnnotationException(access + " is not a valid access type. Check you xml confguration.");
/* 1624:     */       }
/* 1625:1659 */       ad.setValue("value", type);
/* 1626:1660 */       return (Access)AnnotationFactory.create(ad);
/* 1627:     */     }
/* 1628:1662 */     if ((defaults.canUseJavaAnnotations()) && (isJavaAnnotationPresent(Access.class))) {
/* 1629:1663 */       return (Access)getJavaAnnotation(Access.class);
/* 1630:     */     }
/* 1631:1665 */     if (defaults.getAccess() != null)
/* 1632:     */     {
/* 1633:1666 */       AnnotationDescriptor ad = new AnnotationDescriptor(Access.class);
/* 1634:1667 */       ad.setValue("value", defaults.getAccess());
/* 1635:1668 */       return (Access)AnnotationFactory.create(ad);
/* 1636:     */     }
/* 1637:1671 */     return null;
/* 1638:     */   }
/* 1639:     */   
/* 1640:     */   private ExcludeSuperclassListeners getExcludeSuperclassListeners(Element tree, XMLContext.Default defaults)
/* 1641:     */   {
/* 1642:1676 */     return (ExcludeSuperclassListeners)getMarkerAnnotation(ExcludeSuperclassListeners.class, tree, defaults);
/* 1643:     */   }
/* 1644:     */   
/* 1645:     */   private ExcludeDefaultListeners getExcludeDefaultListeners(Element tree, XMLContext.Default defaults)
/* 1646:     */   {
/* 1647:1680 */     return (ExcludeDefaultListeners)getMarkerAnnotation(ExcludeDefaultListeners.class, tree, defaults);
/* 1648:     */   }
/* 1649:     */   
/* 1650:     */   private Annotation getMarkerAnnotation(Class<? extends Annotation> clazz, Element element, XMLContext.Default defaults)
/* 1651:     */   {
/* 1652:1686 */     Element subelement = element == null ? null : element.element((String)annotationToXml.get(clazz));
/* 1653:1687 */     if (subelement != null) {
/* 1654:1688 */       return AnnotationFactory.create(new AnnotationDescriptor(clazz));
/* 1655:     */     }
/* 1656:1690 */     if (defaults.canUseJavaAnnotations()) {
/* 1657:1692 */       return getJavaAnnotation(clazz);
/* 1658:     */     }
/* 1659:1695 */     return null;
/* 1660:     */   }
/* 1661:     */   
/* 1662:     */   private SqlResultSetMappings getSqlResultSetMappings(Element tree, XMLContext.Default defaults)
/* 1663:     */   {
/* 1664:1700 */     List<SqlResultSetMapping> results = buildSqlResultsetMappings(tree, defaults);
/* 1665:1701 */     if (defaults.canUseJavaAnnotations())
/* 1666:     */     {
/* 1667:1702 */       SqlResultSetMapping annotation = (SqlResultSetMapping)getJavaAnnotation(SqlResultSetMapping.class);
/* 1668:1703 */       addSqlResultsetMappingIfNeeded(annotation, results);
/* 1669:1704 */       SqlResultSetMappings annotations = (SqlResultSetMappings)getJavaAnnotation(SqlResultSetMappings.class);
/* 1670:1705 */       if (annotations != null) {
/* 1671:1706 */         for (SqlResultSetMapping current : annotations.value()) {
/* 1672:1707 */           addSqlResultsetMappingIfNeeded(current, results);
/* 1673:     */         }
/* 1674:     */       }
/* 1675:     */     }
/* 1676:1711 */     if (results.size() > 0)
/* 1677:     */     {
/* 1678:1712 */       AnnotationDescriptor ad = new AnnotationDescriptor(SqlResultSetMappings.class);
/* 1679:1713 */       ad.setValue("value", results.toArray(new SqlResultSetMapping[results.size()]));
/* 1680:1714 */       return (SqlResultSetMappings)AnnotationFactory.create(ad);
/* 1681:     */     }
/* 1682:1717 */     return null;
/* 1683:     */   }
/* 1684:     */   
/* 1685:     */   public static List<SqlResultSetMapping> buildSqlResultsetMappings(Element element, XMLContext.Default defaults)
/* 1686:     */   {
/* 1687:1722 */     if (element == null) {
/* 1688:1723 */       return new ArrayList();
/* 1689:     */     }
/* 1690:1725 */     List resultsetElementList = element.elements("sql-result-set-mapping");
/* 1691:1726 */     List<SqlResultSetMapping> resultsets = new ArrayList();
/* 1692:1727 */     Iterator it = resultsetElementList.listIterator();
/* 1693:1728 */     while (it.hasNext())
/* 1694:     */     {
/* 1695:1729 */       Element subelement = (Element)it.next();
/* 1696:1730 */       AnnotationDescriptor ann = new AnnotationDescriptor(SqlResultSetMapping.class);
/* 1697:1731 */       copyStringAttribute(ann, subelement, "name", true);
/* 1698:1732 */       List<Element> elements = subelement.elements("entity-result");
/* 1699:1733 */       List<EntityResult> entityResults = new ArrayList(elements.size());
/* 1700:1734 */       for (Element entityResult : elements)
/* 1701:     */       {
/* 1702:1735 */         AnnotationDescriptor entityResultDescriptor = new AnnotationDescriptor(EntityResult.class);
/* 1703:1736 */         String clazzName = entityResult.attributeValue("entity-class");
/* 1704:1737 */         if (clazzName == null) {
/* 1705:1738 */           throw new AnnotationException("<entity-result> without entity-class. Activate schema validation for more information");
/* 1706:     */         }
/* 1707:     */         Class clazz;
/* 1708:     */         try
/* 1709:     */         {
/* 1710:1742 */           clazz = ReflectHelper.classForName(XMLContext.buildSafeClassName(clazzName, defaults), JPAOverridenAnnotationReader.class);
/* 1711:     */         }
/* 1712:     */         catch (ClassNotFoundException e)
/* 1713:     */         {
/* 1714:1748 */           throw new AnnotationException("Unable to find entity-class: " + clazzName, e);
/* 1715:     */         }
/* 1716:1750 */         entityResultDescriptor.setValue("entityClass", clazz);
/* 1717:1751 */         copyStringAttribute(entityResultDescriptor, entityResult, "discriminator-column", false);
/* 1718:1752 */         List<FieldResult> fieldResults = new ArrayList();
/* 1719:1753 */         for (Element fieldResult : entityResult.elements("field-result"))
/* 1720:     */         {
/* 1721:1754 */           AnnotationDescriptor fieldResultDescriptor = new AnnotationDescriptor(FieldResult.class);
/* 1722:1755 */           copyStringAttribute(fieldResultDescriptor, fieldResult, "name", true);
/* 1723:1756 */           copyStringAttribute(fieldResultDescriptor, fieldResult, "column", true);
/* 1724:1757 */           fieldResults.add((FieldResult)AnnotationFactory.create(fieldResultDescriptor));
/* 1725:     */         }
/* 1726:1759 */         entityResultDescriptor.setValue("fields", fieldResults.toArray(new FieldResult[fieldResults.size()]));
/* 1727:     */         
/* 1728:     */ 
/* 1729:1762 */         entityResults.add((EntityResult)AnnotationFactory.create(entityResultDescriptor));
/* 1730:     */       }
/* 1731:1764 */       ann.setValue("entities", entityResults.toArray(new EntityResult[entityResults.size()]));
/* 1732:     */       
/* 1733:1766 */       elements = subelement.elements("column-result");
/* 1734:1767 */       List<ColumnResult> columnResults = new ArrayList(elements.size());
/* 1735:1768 */       for (Element columnResult : elements)
/* 1736:     */       {
/* 1737:1769 */         AnnotationDescriptor columnResultDescriptor = new AnnotationDescriptor(ColumnResult.class);
/* 1738:1770 */         copyStringAttribute(columnResultDescriptor, columnResult, "name", true);
/* 1739:1771 */         columnResults.add((ColumnResult)AnnotationFactory.create(columnResultDescriptor));
/* 1740:     */       }
/* 1741:1773 */       ann.setValue("columns", columnResults.toArray(new ColumnResult[columnResults.size()]));
/* 1742:     */       
/* 1743:1775 */       String clazzName = subelement.attributeValue("result-class");
/* 1744:1776 */       if (StringHelper.isNotEmpty(clazzName))
/* 1745:     */       {
/* 1746:     */         Class clazz;
/* 1747:     */         try
/* 1748:     */         {
/* 1749:1779 */           clazz = ReflectHelper.classForName(XMLContext.buildSafeClassName(clazzName, defaults), JPAOverridenAnnotationReader.class);
/* 1750:     */         }
/* 1751:     */         catch (ClassNotFoundException e)
/* 1752:     */         {
/* 1753:1785 */           throw new AnnotationException("Unable to find entity-class: " + clazzName, e);
/* 1754:     */         }
/* 1755:1787 */         ann.setValue("resultClass", clazz);
/* 1756:     */       }
/* 1757:1789 */       copyStringAttribute(ann, subelement, "result-set-mapping", false);
/* 1758:1790 */       resultsets.add((SqlResultSetMapping)AnnotationFactory.create(ann));
/* 1759:     */     }
/* 1760:1792 */     return resultsets;
/* 1761:     */   }
/* 1762:     */   
/* 1763:     */   private void addSqlResultsetMappingIfNeeded(SqlResultSetMapping annotation, List<SqlResultSetMapping> resultsets)
/* 1764:     */   {
/* 1765:1796 */     if (annotation != null)
/* 1766:     */     {
/* 1767:1797 */       String resultsetName = annotation.name();
/* 1768:1798 */       boolean present = false;
/* 1769:1799 */       for (SqlResultSetMapping current : resultsets) {
/* 1770:1800 */         if (current.name().equals(resultsetName))
/* 1771:     */         {
/* 1772:1801 */           present = true;
/* 1773:1802 */           break;
/* 1774:     */         }
/* 1775:     */       }
/* 1776:1805 */       if (!present) {
/* 1777:1806 */         resultsets.add(annotation);
/* 1778:     */       }
/* 1779:     */     }
/* 1780:     */   }
/* 1781:     */   
/* 1782:     */   private NamedQueries getNamedQueries(Element tree, XMLContext.Default defaults)
/* 1783:     */   {
/* 1784:1813 */     List<NamedQuery> queries = buildNamedQueries(tree, false, defaults);
/* 1785:1814 */     if (defaults.canUseJavaAnnotations())
/* 1786:     */     {
/* 1787:1815 */       NamedQuery annotation = (NamedQuery)getJavaAnnotation(NamedQuery.class);
/* 1788:1816 */       addNamedQueryIfNeeded(annotation, queries);
/* 1789:1817 */       NamedQueries annotations = (NamedQueries)getJavaAnnotation(NamedQueries.class);
/* 1790:1818 */       if (annotations != null) {
/* 1791:1819 */         for (NamedQuery current : annotations.value()) {
/* 1792:1820 */           addNamedQueryIfNeeded(current, queries);
/* 1793:     */         }
/* 1794:     */       }
/* 1795:     */     }
/* 1796:1824 */     if (queries.size() > 0)
/* 1797:     */     {
/* 1798:1825 */       AnnotationDescriptor ad = new AnnotationDescriptor(NamedQueries.class);
/* 1799:1826 */       ad.setValue("value", queries.toArray(new NamedQuery[queries.size()]));
/* 1800:1827 */       return (NamedQueries)AnnotationFactory.create(ad);
/* 1801:     */     }
/* 1802:1830 */     return null;
/* 1803:     */   }
/* 1804:     */   
/* 1805:     */   private void addNamedQueryIfNeeded(NamedQuery annotation, List<NamedQuery> queries)
/* 1806:     */   {
/* 1807:1835 */     if (annotation != null)
/* 1808:     */     {
/* 1809:1836 */       String queryName = annotation.name();
/* 1810:1837 */       boolean present = false;
/* 1811:1838 */       for (NamedQuery current : queries) {
/* 1812:1839 */         if (current.name().equals(queryName))
/* 1813:     */         {
/* 1814:1840 */           present = true;
/* 1815:1841 */           break;
/* 1816:     */         }
/* 1817:     */       }
/* 1818:1844 */       if (!present) {
/* 1819:1845 */         queries.add(annotation);
/* 1820:     */       }
/* 1821:     */     }
/* 1822:     */   }
/* 1823:     */   
/* 1824:     */   private NamedNativeQueries getNamedNativeQueries(Element tree, XMLContext.Default defaults)
/* 1825:     */   {
/* 1826:1851 */     List<NamedNativeQuery> queries = buildNamedQueries(tree, true, defaults);
/* 1827:1852 */     if (defaults.canUseJavaAnnotations())
/* 1828:     */     {
/* 1829:1853 */       NamedNativeQuery annotation = (NamedNativeQuery)getJavaAnnotation(NamedNativeQuery.class);
/* 1830:1854 */       addNamedNativeQueryIfNeeded(annotation, queries);
/* 1831:1855 */       NamedNativeQueries annotations = (NamedNativeQueries)getJavaAnnotation(NamedNativeQueries.class);
/* 1832:1856 */       if (annotations != null) {
/* 1833:1857 */         for (NamedNativeQuery current : annotations.value()) {
/* 1834:1858 */           addNamedNativeQueryIfNeeded(current, queries);
/* 1835:     */         }
/* 1836:     */       }
/* 1837:     */     }
/* 1838:1862 */     if (queries.size() > 0)
/* 1839:     */     {
/* 1840:1863 */       AnnotationDescriptor ad = new AnnotationDescriptor(NamedNativeQueries.class);
/* 1841:1864 */       ad.setValue("value", queries.toArray(new NamedNativeQuery[queries.size()]));
/* 1842:1865 */       return (NamedNativeQueries)AnnotationFactory.create(ad);
/* 1843:     */     }
/* 1844:1868 */     return null;
/* 1845:     */   }
/* 1846:     */   
/* 1847:     */   private void addNamedNativeQueryIfNeeded(NamedNativeQuery annotation, List<NamedNativeQuery> queries)
/* 1848:     */   {
/* 1849:1873 */     if (annotation != null)
/* 1850:     */     {
/* 1851:1874 */       String queryName = annotation.name();
/* 1852:1875 */       boolean present = false;
/* 1853:1876 */       for (NamedNativeQuery current : queries) {
/* 1854:1877 */         if (current.name().equals(queryName))
/* 1855:     */         {
/* 1856:1878 */           present = true;
/* 1857:1879 */           break;
/* 1858:     */         }
/* 1859:     */       }
/* 1860:1882 */       if (!present) {
/* 1861:1883 */         queries.add(annotation);
/* 1862:     */       }
/* 1863:     */     }
/* 1864:     */   }
/* 1865:     */   
/* 1866:     */   public static List buildNamedQueries(Element element, boolean isNative, XMLContext.Default defaults)
/* 1867:     */   {
/* 1868:1889 */     if (element == null) {
/* 1869:1890 */       return new ArrayList();
/* 1870:     */     }
/* 1871:1892 */     List namedQueryElementList = isNative ? element.elements("named-native-query") : element.elements("named-query");
/* 1872:     */     
/* 1873:     */ 
/* 1874:1895 */     List namedQueries = new ArrayList();
/* 1875:1896 */     Iterator it = namedQueryElementList.listIterator();
/* 1876:1897 */     while (it.hasNext())
/* 1877:     */     {
/* 1878:1898 */       Element subelement = (Element)it.next();
/* 1879:1899 */       AnnotationDescriptor ann = new AnnotationDescriptor(isNative ? NamedNativeQuery.class : NamedQuery.class);
/* 1880:     */       
/* 1881:     */ 
/* 1882:1902 */       copyStringAttribute(ann, subelement, "name", false);
/* 1883:1903 */       Element queryElt = subelement.element("query");
/* 1884:1904 */       if (queryElt == null) {
/* 1885:1905 */         throw new AnnotationException("No <query> element found.Activate schema validation for more information");
/* 1886:     */       }
/* 1887:1907 */       copyStringElement(queryElt, ann, "query");
/* 1888:1908 */       List<Element> elements = subelement.elements("hint");
/* 1889:1909 */       List<QueryHint> queryHints = new ArrayList(elements.size());
/* 1890:1910 */       for (Element hint : elements)
/* 1891:     */       {
/* 1892:1911 */         AnnotationDescriptor hintDescriptor = new AnnotationDescriptor(QueryHint.class);
/* 1893:1912 */         String value = hint.attributeValue("name");
/* 1894:1913 */         if (value == null) {
/* 1895:1914 */           throw new AnnotationException("<hint> without name. Activate schema validation for more information");
/* 1896:     */         }
/* 1897:1916 */         hintDescriptor.setValue("name", value);
/* 1898:1917 */         value = hint.attributeValue("value");
/* 1899:1918 */         if (value == null) {
/* 1900:1919 */           throw new AnnotationException("<hint> without value. Activate schema validation for more information");
/* 1901:     */         }
/* 1902:1921 */         hintDescriptor.setValue("value", value);
/* 1903:1922 */         queryHints.add((QueryHint)AnnotationFactory.create(hintDescriptor));
/* 1904:     */       }
/* 1905:1924 */       ann.setValue("hints", queryHints.toArray(new QueryHint[queryHints.size()]));
/* 1906:1925 */       String clazzName = subelement.attributeValue("result-class");
/* 1907:1926 */       if (StringHelper.isNotEmpty(clazzName))
/* 1908:     */       {
/* 1909:     */         Class clazz;
/* 1910:     */         try
/* 1911:     */         {
/* 1912:1929 */           clazz = ReflectHelper.classForName(XMLContext.buildSafeClassName(clazzName, defaults), JPAOverridenAnnotationReader.class);
/* 1913:     */         }
/* 1914:     */         catch (ClassNotFoundException e)
/* 1915:     */         {
/* 1916:1935 */           throw new AnnotationException("Unable to find entity-class: " + clazzName, e);
/* 1917:     */         }
/* 1918:1937 */         ann.setValue("resultClass", clazz);
/* 1919:     */       }
/* 1920:1939 */       copyStringAttribute(ann, subelement, "result-set-mapping", false);
/* 1921:1940 */       namedQueries.add(AnnotationFactory.create(ann));
/* 1922:     */     }
/* 1923:1942 */     return namedQueries;
/* 1924:     */   }
/* 1925:     */   
/* 1926:     */   private TableGenerator getTableGenerator(Element tree, XMLContext.Default defaults)
/* 1927:     */   {
/* 1928:1946 */     Element element = tree != null ? tree.element((String)annotationToXml.get(TableGenerator.class)) : null;
/* 1929:1947 */     if (element != null) {
/* 1930:1948 */       return buildTableGeneratorAnnotation(element, defaults);
/* 1931:     */     }
/* 1932:1950 */     if ((defaults.canUseJavaAnnotations()) && (isJavaAnnotationPresent(TableGenerator.class)))
/* 1933:     */     {
/* 1934:1951 */       TableGenerator tableAnn = (TableGenerator)getJavaAnnotation(TableGenerator.class);
/* 1935:1952 */       if ((StringHelper.isNotEmpty(defaults.getSchema())) || (StringHelper.isNotEmpty(defaults.getCatalog())))
/* 1936:     */       {
/* 1937:1954 */         AnnotationDescriptor annotation = new AnnotationDescriptor(TableGenerator.class);
/* 1938:1955 */         annotation.setValue("name", tableAnn.name());
/* 1939:1956 */         annotation.setValue("table", tableAnn.table());
/* 1940:1957 */         annotation.setValue("catalog", tableAnn.table());
/* 1941:1958 */         if ((StringHelper.isEmpty((String)annotation.valueOf("catalog"))) && (StringHelper.isNotEmpty(defaults.getCatalog()))) {
/* 1942:1960 */           annotation.setValue("catalog", defaults.getCatalog());
/* 1943:     */         }
/* 1944:1962 */         annotation.setValue("schema", tableAnn.table());
/* 1945:1963 */         if ((StringHelper.isEmpty((String)annotation.valueOf("schema"))) && (StringHelper.isNotEmpty(defaults.getSchema()))) {
/* 1946:1965 */           annotation.setValue("catalog", defaults.getSchema());
/* 1947:     */         }
/* 1948:1967 */         annotation.setValue("pkColumnName", tableAnn.pkColumnName());
/* 1949:1968 */         annotation.setValue("valueColumnName", tableAnn.valueColumnName());
/* 1950:1969 */         annotation.setValue("pkColumnValue", tableAnn.pkColumnValue());
/* 1951:1970 */         annotation.setValue("initialValue", Integer.valueOf(tableAnn.initialValue()));
/* 1952:1971 */         annotation.setValue("allocationSize", Integer.valueOf(tableAnn.allocationSize()));
/* 1953:1972 */         annotation.setValue("uniqueConstraints", tableAnn.uniqueConstraints());
/* 1954:1973 */         return (TableGenerator)AnnotationFactory.create(annotation);
/* 1955:     */       }
/* 1956:1976 */       return tableAnn;
/* 1957:     */     }
/* 1958:1980 */     return null;
/* 1959:     */   }
/* 1960:     */   
/* 1961:     */   public static TableGenerator buildTableGeneratorAnnotation(Element element, XMLContext.Default defaults)
/* 1962:     */   {
/* 1963:1985 */     AnnotationDescriptor ad = new AnnotationDescriptor(TableGenerator.class);
/* 1964:1986 */     copyStringAttribute(ad, element, "name", false);
/* 1965:1987 */     copyStringAttribute(ad, element, "table", false);
/* 1966:1988 */     copyStringAttribute(ad, element, "catalog", false);
/* 1967:1989 */     copyStringAttribute(ad, element, "schema", false);
/* 1968:1990 */     copyStringAttribute(ad, element, "pk-column-name", false);
/* 1969:1991 */     copyStringAttribute(ad, element, "value-column-name", false);
/* 1970:1992 */     copyStringAttribute(ad, element, "pk-column-value", false);
/* 1971:1993 */     copyIntegerAttribute(ad, element, "initial-value");
/* 1972:1994 */     copyIntegerAttribute(ad, element, "allocation-size");
/* 1973:1995 */     buildUniqueConstraints(ad, element);
/* 1974:1996 */     if ((StringHelper.isEmpty((String)ad.valueOf("schema"))) && (StringHelper.isNotEmpty(defaults.getSchema()))) {
/* 1975:1998 */       ad.setValue("schema", defaults.getSchema());
/* 1976:     */     }
/* 1977:2000 */     if ((StringHelper.isEmpty((String)ad.valueOf("catalog"))) && (StringHelper.isNotEmpty(defaults.getCatalog()))) {
/* 1978:2002 */       ad.setValue("catalog", defaults.getCatalog());
/* 1979:     */     }
/* 1980:2004 */     return (TableGenerator)AnnotationFactory.create(ad);
/* 1981:     */   }
/* 1982:     */   
/* 1983:     */   private SequenceGenerator getSequenceGenerator(Element tree, XMLContext.Default defaults)
/* 1984:     */   {
/* 1985:2008 */     Element element = tree != null ? tree.element((String)annotationToXml.get(SequenceGenerator.class)) : null;
/* 1986:2009 */     if (element != null) {
/* 1987:2010 */       return buildSequenceGeneratorAnnotation(element);
/* 1988:     */     }
/* 1989:2012 */     if (defaults.canUseJavaAnnotations()) {
/* 1990:2013 */       return (SequenceGenerator)getJavaAnnotation(SequenceGenerator.class);
/* 1991:     */     }
/* 1992:2016 */     return null;
/* 1993:     */   }
/* 1994:     */   
/* 1995:     */   public static SequenceGenerator buildSequenceGeneratorAnnotation(Element element)
/* 1996:     */   {
/* 1997:2021 */     if (element != null)
/* 1998:     */     {
/* 1999:2022 */       AnnotationDescriptor ad = new AnnotationDescriptor(SequenceGenerator.class);
/* 2000:2023 */       copyStringAttribute(ad, element, "name", false);
/* 2001:2024 */       copyStringAttribute(ad, element, "sequence-name", false);
/* 2002:2025 */       copyIntegerAttribute(ad, element, "initial-value");
/* 2003:2026 */       copyIntegerAttribute(ad, element, "allocation-size");
/* 2004:2027 */       return (SequenceGenerator)AnnotationFactory.create(ad);
/* 2005:     */     }
/* 2006:2030 */     return null;
/* 2007:     */   }
/* 2008:     */   
/* 2009:     */   private DiscriminatorColumn getDiscriminatorColumn(Element tree, XMLContext.Default defaults)
/* 2010:     */   {
/* 2011:2035 */     Element element = tree != null ? tree.element("discriminator-column") : null;
/* 2012:2036 */     if (element != null)
/* 2013:     */     {
/* 2014:2037 */       AnnotationDescriptor ad = new AnnotationDescriptor(DiscriminatorColumn.class);
/* 2015:2038 */       copyStringAttribute(ad, element, "name", false);
/* 2016:2039 */       copyStringAttribute(ad, element, "column-definition", false);
/* 2017:2040 */       String value = element.attributeValue("discriminator-type");
/* 2018:2041 */       DiscriminatorType type = DiscriminatorType.STRING;
/* 2019:2042 */       if (value != null) {
/* 2020:2043 */         if ("STRING".equals(value)) {
/* 2021:2044 */           type = DiscriminatorType.STRING;
/* 2022:2046 */         } else if ("CHAR".equals(value)) {
/* 2023:2047 */           type = DiscriminatorType.CHAR;
/* 2024:2049 */         } else if ("INTEGER".equals(value)) {
/* 2025:2050 */           type = DiscriminatorType.INTEGER;
/* 2026:     */         } else {
/* 2027:2053 */           throw new AnnotationException("Unknown DiscrimiatorType in XML: " + value + " (" + "Activate schema validation for more information" + ")");
/* 2028:     */         }
/* 2029:     */       }
/* 2030:2058 */       ad.setValue("discriminatorType", type);
/* 2031:2059 */       copyIntegerAttribute(ad, element, "length");
/* 2032:2060 */       return (DiscriminatorColumn)AnnotationFactory.create(ad);
/* 2033:     */     }
/* 2034:2062 */     if (defaults.canUseJavaAnnotations()) {
/* 2035:2063 */       return (DiscriminatorColumn)getJavaAnnotation(DiscriminatorColumn.class);
/* 2036:     */     }
/* 2037:2066 */     return null;
/* 2038:     */   }
/* 2039:     */   
/* 2040:     */   private DiscriminatorValue getDiscriminatorValue(Element tree, XMLContext.Default defaults)
/* 2041:     */   {
/* 2042:2071 */     Element element = tree != null ? tree.element("discriminator-value") : null;
/* 2043:2072 */     if (element != null)
/* 2044:     */     {
/* 2045:2073 */       AnnotationDescriptor ad = new AnnotationDescriptor(DiscriminatorValue.class);
/* 2046:2074 */       copyStringElement(element, ad, "value");
/* 2047:2075 */       return (DiscriminatorValue)AnnotationFactory.create(ad);
/* 2048:     */     }
/* 2049:2077 */     if (defaults.canUseJavaAnnotations()) {
/* 2050:2078 */       return (DiscriminatorValue)getJavaAnnotation(DiscriminatorValue.class);
/* 2051:     */     }
/* 2052:2081 */     return null;
/* 2053:     */   }
/* 2054:     */   
/* 2055:     */   private Inheritance getInheritance(Element tree, XMLContext.Default defaults)
/* 2056:     */   {
/* 2057:2086 */     Element element = tree != null ? tree.element("inheritance") : null;
/* 2058:2087 */     if (element != null)
/* 2059:     */     {
/* 2060:2088 */       AnnotationDescriptor ad = new AnnotationDescriptor(Inheritance.class);
/* 2061:2089 */       Attribute attr = element.attribute("strategy");
/* 2062:2090 */       InheritanceType strategy = InheritanceType.SINGLE_TABLE;
/* 2063:2091 */       if (attr != null)
/* 2064:     */       {
/* 2065:2092 */         String value = attr.getValue();
/* 2066:2093 */         if ("SINGLE_TABLE".equals(value)) {
/* 2067:2094 */           strategy = InheritanceType.SINGLE_TABLE;
/* 2068:2096 */         } else if ("JOINED".equals(value)) {
/* 2069:2097 */           strategy = InheritanceType.JOINED;
/* 2070:2099 */         } else if ("TABLE_PER_CLASS".equals(value)) {
/* 2071:2100 */           strategy = InheritanceType.TABLE_PER_CLASS;
/* 2072:     */         } else {
/* 2073:2103 */           throw new AnnotationException("Unknown InheritanceType in XML: " + value + " (" + "Activate schema validation for more information" + ")");
/* 2074:     */         }
/* 2075:     */       }
/* 2076:2108 */       ad.setValue("strategy", strategy);
/* 2077:2109 */       return (Inheritance)AnnotationFactory.create(ad);
/* 2078:     */     }
/* 2079:2111 */     if (defaults.canUseJavaAnnotations()) {
/* 2080:2112 */       return (Inheritance)getJavaAnnotation(Inheritance.class);
/* 2081:     */     }
/* 2082:2115 */     return null;
/* 2083:     */   }
/* 2084:     */   
/* 2085:     */   private IdClass getIdClass(Element tree, XMLContext.Default defaults)
/* 2086:     */   {
/* 2087:2120 */     Element element = tree == null ? null : tree.element("id-class");
/* 2088:2121 */     if (element != null)
/* 2089:     */     {
/* 2090:2122 */       Attribute attr = element.attribute("class");
/* 2091:2123 */       if (attr != null)
/* 2092:     */       {
/* 2093:2124 */         AnnotationDescriptor ad = new AnnotationDescriptor(IdClass.class);
/* 2094:     */         Class clazz;
/* 2095:     */         try
/* 2096:     */         {
/* 2097:2127 */           clazz = ReflectHelper.classForName(XMLContext.buildSafeClassName(attr.getValue(), defaults), getClass());
/* 2098:     */         }
/* 2099:     */         catch (ClassNotFoundException e)
/* 2100:     */         {
/* 2101:2133 */           throw new AnnotationException("Unable to find id-class: " + attr.getValue(), e);
/* 2102:     */         }
/* 2103:2135 */         ad.setValue("value", clazz);
/* 2104:2136 */         return (IdClass)AnnotationFactory.create(ad);
/* 2105:     */       }
/* 2106:2139 */       throw new AnnotationException("id-class without class. Activate schema validation for more information");
/* 2107:     */     }
/* 2108:2142 */     if (defaults.canUseJavaAnnotations()) {
/* 2109:2143 */       return (IdClass)getJavaAnnotation(IdClass.class);
/* 2110:     */     }
/* 2111:2146 */     return null;
/* 2112:     */   }
/* 2113:     */   
/* 2114:     */   private PrimaryKeyJoinColumns getPrimaryKeyJoinColumns(Element element, XMLContext.Default defaults, boolean mergeWithAnnotations)
/* 2115:     */   {
/* 2116:2157 */     PrimaryKeyJoinColumn[] columns = buildPrimaryKeyJoinColumns(element);
/* 2117:2158 */     if ((mergeWithAnnotations) && 
/* 2118:2159 */       (columns.length == 0) && (defaults.canUseJavaAnnotations()))
/* 2119:     */     {
/* 2120:2160 */       PrimaryKeyJoinColumn annotation = (PrimaryKeyJoinColumn)getJavaAnnotation(PrimaryKeyJoinColumn.class);
/* 2121:2161 */       if (annotation != null)
/* 2122:     */       {
/* 2123:2162 */         columns = new PrimaryKeyJoinColumn[] { annotation };
/* 2124:     */       }
/* 2125:     */       else
/* 2126:     */       {
/* 2127:2165 */         PrimaryKeyJoinColumns annotations = (PrimaryKeyJoinColumns)getJavaAnnotation(PrimaryKeyJoinColumns.class);
/* 2128:2166 */         columns = annotations != null ? annotations.value() : columns;
/* 2129:     */       }
/* 2130:     */     }
/* 2131:2170 */     if (columns.length > 0)
/* 2132:     */     {
/* 2133:2171 */       AnnotationDescriptor ad = new AnnotationDescriptor(PrimaryKeyJoinColumns.class);
/* 2134:2172 */       ad.setValue("value", columns);
/* 2135:2173 */       return (PrimaryKeyJoinColumns)AnnotationFactory.create(ad);
/* 2136:     */     }
/* 2137:2176 */     return null;
/* 2138:     */   }
/* 2139:     */   
/* 2140:     */   private Entity getEntity(Element tree, XMLContext.Default defaults)
/* 2141:     */   {
/* 2142:2181 */     if (tree == null) {
/* 2143:2182 */       return defaults.canUseJavaAnnotations() ? (Entity)getJavaAnnotation(Entity.class) : null;
/* 2144:     */     }
/* 2145:2185 */     if ("entity".equals(tree.getName()))
/* 2146:     */     {
/* 2147:2186 */       AnnotationDescriptor entity = new AnnotationDescriptor(Entity.class);
/* 2148:2187 */       copyStringAttribute(entity, tree, "name", false);
/* 2149:2188 */       if ((defaults.canUseJavaAnnotations()) && (StringHelper.isEmpty((String)entity.valueOf("name"))))
/* 2150:     */       {
/* 2151:2190 */         Entity javaAnn = (Entity)getJavaAnnotation(Entity.class);
/* 2152:2191 */         if (javaAnn != null) {
/* 2153:2192 */           entity.setValue("name", javaAnn.name());
/* 2154:     */         }
/* 2155:     */       }
/* 2156:2195 */       return (Entity)AnnotationFactory.create(entity);
/* 2157:     */     }
/* 2158:2198 */     return null;
/* 2159:     */   }
/* 2160:     */   
/* 2161:     */   private MappedSuperclass getMappedSuperclass(Element tree, XMLContext.Default defaults)
/* 2162:     */   {
/* 2163:2204 */     if (tree == null) {
/* 2164:2205 */       return defaults.canUseJavaAnnotations() ? (MappedSuperclass)getJavaAnnotation(MappedSuperclass.class) : null;
/* 2165:     */     }
/* 2166:2208 */     if ("mapped-superclass".equals(tree.getName()))
/* 2167:     */     {
/* 2168:2209 */       AnnotationDescriptor entity = new AnnotationDescriptor(MappedSuperclass.class);
/* 2169:2210 */       return (MappedSuperclass)AnnotationFactory.create(entity);
/* 2170:     */     }
/* 2171:2213 */     return null;
/* 2172:     */   }
/* 2173:     */   
/* 2174:     */   private Embeddable getEmbeddable(Element tree, XMLContext.Default defaults)
/* 2175:     */   {
/* 2176:2219 */     if (tree == null) {
/* 2177:2220 */       return defaults.canUseJavaAnnotations() ? (Embeddable)getJavaAnnotation(Embeddable.class) : null;
/* 2178:     */     }
/* 2179:2223 */     if ("embeddable".equals(tree.getName()))
/* 2180:     */     {
/* 2181:2224 */       AnnotationDescriptor entity = new AnnotationDescriptor(Embeddable.class);
/* 2182:2225 */       return (Embeddable)AnnotationFactory.create(entity);
/* 2183:     */     }
/* 2184:2228 */     return null;
/* 2185:     */   }
/* 2186:     */   
/* 2187:     */   private Table getTable(Element tree, XMLContext.Default defaults)
/* 2188:     */   {
/* 2189:2234 */     Element subelement = tree == null ? null : tree.element("table");
/* 2190:2235 */     if (subelement == null)
/* 2191:     */     {
/* 2192:2237 */       if ((StringHelper.isNotEmpty(defaults.getCatalog())) || (StringHelper.isNotEmpty(defaults.getSchema())))
/* 2193:     */       {
/* 2194:2239 */         AnnotationDescriptor annotation = new AnnotationDescriptor(Table.class);
/* 2195:2240 */         if (defaults.canUseJavaAnnotations())
/* 2196:     */         {
/* 2197:2241 */           Table table = (Table)getJavaAnnotation(Table.class);
/* 2198:2242 */           if (table != null)
/* 2199:     */           {
/* 2200:2243 */             annotation.setValue("name", table.name());
/* 2201:2244 */             annotation.setValue("schema", table.schema());
/* 2202:2245 */             annotation.setValue("catalog", table.catalog());
/* 2203:2246 */             annotation.setValue("uniqueConstraints", table.uniqueConstraints());
/* 2204:     */           }
/* 2205:     */         }
/* 2206:2249 */         if ((StringHelper.isEmpty((String)annotation.valueOf("schema"))) && (StringHelper.isNotEmpty(defaults.getSchema()))) {
/* 2207:2251 */           annotation.setValue("schema", defaults.getSchema());
/* 2208:     */         }
/* 2209:2253 */         if ((StringHelper.isEmpty((String)annotation.valueOf("catalog"))) && (StringHelper.isNotEmpty(defaults.getCatalog()))) {
/* 2210:2255 */           annotation.setValue("catalog", defaults.getCatalog());
/* 2211:     */         }
/* 2212:2257 */         return (Table)AnnotationFactory.create(annotation);
/* 2213:     */       }
/* 2214:2259 */       if (defaults.canUseJavaAnnotations()) {
/* 2215:2260 */         return (Table)getJavaAnnotation(Table.class);
/* 2216:     */       }
/* 2217:2263 */       return null;
/* 2218:     */     }
/* 2219:2268 */     AnnotationDescriptor annotation = new AnnotationDescriptor(Table.class);
/* 2220:2269 */     copyStringAttribute(annotation, subelement, "name", false);
/* 2221:2270 */     copyStringAttribute(annotation, subelement, "catalog", false);
/* 2222:2271 */     if ((StringHelper.isNotEmpty(defaults.getCatalog())) && (StringHelper.isEmpty((String)annotation.valueOf("catalog")))) {
/* 2223:2273 */       annotation.setValue("catalog", defaults.getCatalog());
/* 2224:     */     }
/* 2225:2275 */     copyStringAttribute(annotation, subelement, "schema", false);
/* 2226:2276 */     if ((StringHelper.isNotEmpty(defaults.getSchema())) && (StringHelper.isEmpty((String)annotation.valueOf("schema")))) {
/* 2227:2278 */       annotation.setValue("schema", defaults.getSchema());
/* 2228:     */     }
/* 2229:2280 */     buildUniqueConstraints(annotation, subelement);
/* 2230:2281 */     return (Table)AnnotationFactory.create(annotation);
/* 2231:     */   }
/* 2232:     */   
/* 2233:     */   private SecondaryTables getSecondaryTables(Element tree, XMLContext.Default defaults)
/* 2234:     */   {
/* 2235:2286 */     List<Element> elements = tree == null ? new ArrayList() : tree.elements("secondary-table");
/* 2236:     */     
/* 2237:     */ 
/* 2238:2289 */     List<SecondaryTable> secondaryTables = new ArrayList(3);
/* 2239:2290 */     for (Element element : elements)
/* 2240:     */     {
/* 2241:2291 */       AnnotationDescriptor annotation = new AnnotationDescriptor(SecondaryTable.class);
/* 2242:2292 */       copyStringAttribute(annotation, element, "name", false);
/* 2243:2293 */       copyStringAttribute(annotation, element, "catalog", false);
/* 2244:2294 */       if ((StringHelper.isNotEmpty(defaults.getCatalog())) && (StringHelper.isEmpty((String)annotation.valueOf("catalog")))) {
/* 2245:2296 */         annotation.setValue("catalog", defaults.getCatalog());
/* 2246:     */       }
/* 2247:2298 */       copyStringAttribute(annotation, element, "schema", false);
/* 2248:2299 */       if ((StringHelper.isNotEmpty(defaults.getSchema())) && (StringHelper.isEmpty((String)annotation.valueOf("schema")))) {
/* 2249:2301 */         annotation.setValue("schema", defaults.getSchema());
/* 2250:     */       }
/* 2251:2303 */       buildUniqueConstraints(annotation, element);
/* 2252:2304 */       annotation.setValue("pkJoinColumns", buildPrimaryKeyJoinColumns(element));
/* 2253:2305 */       secondaryTables.add((SecondaryTable)AnnotationFactory.create(annotation));
/* 2254:     */     }
/* 2255:2311 */     if ((secondaryTables.size() == 0) && (defaults.canUseJavaAnnotations()))
/* 2256:     */     {
/* 2257:2312 */       SecondaryTable secTableAnn = (SecondaryTable)getJavaAnnotation(SecondaryTable.class);
/* 2258:2313 */       overridesDefaultInSecondaryTable(secTableAnn, defaults, secondaryTables);
/* 2259:2314 */       SecondaryTables secTablesAnn = (SecondaryTables)getJavaAnnotation(SecondaryTables.class);
/* 2260:2315 */       if (secTablesAnn != null) {
/* 2261:2316 */         for (SecondaryTable table : secTablesAnn.value()) {
/* 2262:2317 */           overridesDefaultInSecondaryTable(table, defaults, secondaryTables);
/* 2263:     */         }
/* 2264:     */       }
/* 2265:     */     }
/* 2266:2321 */     if (secondaryTables.size() > 0)
/* 2267:     */     {
/* 2268:2322 */       AnnotationDescriptor descriptor = new AnnotationDescriptor(SecondaryTables.class);
/* 2269:2323 */       descriptor.setValue("value", secondaryTables.toArray(new SecondaryTable[secondaryTables.size()]));
/* 2270:2324 */       return (SecondaryTables)AnnotationFactory.create(descriptor);
/* 2271:     */     }
/* 2272:2327 */     return null;
/* 2273:     */   }
/* 2274:     */   
/* 2275:     */   private void overridesDefaultInSecondaryTable(SecondaryTable secTableAnn, XMLContext.Default defaults, List<SecondaryTable> secondaryTables)
/* 2276:     */   {
/* 2277:2334 */     if (secTableAnn != null) {
/* 2278:2336 */       if ((StringHelper.isNotEmpty(defaults.getCatalog())) || (StringHelper.isNotEmpty(defaults.getSchema())))
/* 2279:     */       {
/* 2280:2338 */         AnnotationDescriptor annotation = new AnnotationDescriptor(SecondaryTable.class);
/* 2281:2339 */         annotation.setValue("name", secTableAnn.name());
/* 2282:2340 */         annotation.setValue("schema", secTableAnn.schema());
/* 2283:2341 */         annotation.setValue("catalog", secTableAnn.catalog());
/* 2284:2342 */         annotation.setValue("uniqueConstraints", secTableAnn.uniqueConstraints());
/* 2285:2343 */         annotation.setValue("pkJoinColumns", secTableAnn.pkJoinColumns());
/* 2286:2344 */         if ((StringHelper.isEmpty((String)annotation.valueOf("schema"))) && (StringHelper.isNotEmpty(defaults.getSchema()))) {
/* 2287:2346 */           annotation.setValue("schema", defaults.getSchema());
/* 2288:     */         }
/* 2289:2348 */         if ((StringHelper.isEmpty((String)annotation.valueOf("catalog"))) && (StringHelper.isNotEmpty(defaults.getCatalog()))) {
/* 2290:2350 */           annotation.setValue("catalog", defaults.getCatalog());
/* 2291:     */         }
/* 2292:2352 */         secondaryTables.add((SecondaryTable)AnnotationFactory.create(annotation));
/* 2293:     */       }
/* 2294:     */       else
/* 2295:     */       {
/* 2296:2355 */         secondaryTables.add(secTableAnn);
/* 2297:     */       }
/* 2298:     */     }
/* 2299:     */   }
/* 2300:     */   
/* 2301:     */   private static void buildUniqueConstraints(AnnotationDescriptor annotation, Element element)
/* 2302:     */   {
/* 2303:2361 */     List uniqueConstraintElementList = element.elements("unique-constraint");
/* 2304:2362 */     UniqueConstraint[] uniqueConstraints = new UniqueConstraint[uniqueConstraintElementList.size()];
/* 2305:2363 */     int ucIndex = 0;
/* 2306:2364 */     Iterator ucIt = uniqueConstraintElementList.listIterator();
/* 2307:2365 */     while (ucIt.hasNext())
/* 2308:     */     {
/* 2309:2366 */       Element subelement = (Element)ucIt.next();
/* 2310:2367 */       List<Element> columnNamesElements = subelement.elements("column-name");
/* 2311:2368 */       String[] columnNames = new String[columnNamesElements.size()];
/* 2312:2369 */       int columnNameIndex = 0;
/* 2313:2370 */       Iterator it = columnNamesElements.listIterator();
/* 2314:2371 */       while (it.hasNext())
/* 2315:     */       {
/* 2316:2372 */         Element columnNameElt = (Element)it.next();
/* 2317:2373 */         columnNames[(columnNameIndex++)] = columnNameElt.getTextTrim();
/* 2318:     */       }
/* 2319:2375 */       AnnotationDescriptor ucAnn = new AnnotationDescriptor(UniqueConstraint.class);
/* 2320:2376 */       copyStringAttribute(ucAnn, subelement, "name", false);
/* 2321:2377 */       ucAnn.setValue("columnNames", columnNames);
/* 2322:2378 */       uniqueConstraints[(ucIndex++)] = ((UniqueConstraint)AnnotationFactory.create(ucAnn));
/* 2323:     */     }
/* 2324:2380 */     annotation.setValue("uniqueConstraints", uniqueConstraints);
/* 2325:     */   }
/* 2326:     */   
/* 2327:     */   private PrimaryKeyJoinColumn[] buildPrimaryKeyJoinColumns(Element element)
/* 2328:     */   {
/* 2329:2384 */     if (element == null) {
/* 2330:2385 */       return new PrimaryKeyJoinColumn[0];
/* 2331:     */     }
/* 2332:2387 */     List pkJoinColumnElementList = element.elements("primary-key-join-column");
/* 2333:2388 */     PrimaryKeyJoinColumn[] pkJoinColumns = new PrimaryKeyJoinColumn[pkJoinColumnElementList.size()];
/* 2334:2389 */     int index = 0;
/* 2335:2390 */     Iterator pkIt = pkJoinColumnElementList.listIterator();
/* 2336:2391 */     while (pkIt.hasNext())
/* 2337:     */     {
/* 2338:2392 */       Element subelement = (Element)pkIt.next();
/* 2339:2393 */       AnnotationDescriptor pkAnn = new AnnotationDescriptor(PrimaryKeyJoinColumn.class);
/* 2340:2394 */       copyStringAttribute(pkAnn, subelement, "name", false);
/* 2341:2395 */       copyStringAttribute(pkAnn, subelement, "referenced-column-name", false);
/* 2342:2396 */       copyStringAttribute(pkAnn, subelement, "column-definition", false);
/* 2343:2397 */       pkJoinColumns[(index++)] = ((PrimaryKeyJoinColumn)AnnotationFactory.create(pkAnn));
/* 2344:     */     }
/* 2345:2399 */     return pkJoinColumns;
/* 2346:     */   }
/* 2347:     */   
/* 2348:     */   private static void copyStringAttribute(AnnotationDescriptor annotation, Element element, String attributeName, boolean mandatory)
/* 2349:     */   {
/* 2350:2405 */     String attribute = element.attributeValue(attributeName);
/* 2351:2406 */     if (attribute != null)
/* 2352:     */     {
/* 2353:2407 */       String annotationAttributeName = getJavaAttributeNameFromXMLOne(attributeName);
/* 2354:2408 */       annotation.setValue(annotationAttributeName, attribute);
/* 2355:     */     }
/* 2356:2411 */     else if (mandatory)
/* 2357:     */     {
/* 2358:2412 */       throw new AnnotationException(element.getName() + "." + attributeName + " is mandatory in XML overriding. " + "Activate schema validation for more information");
/* 2359:     */     }
/* 2360:     */   }
/* 2361:     */   
/* 2362:     */   private static void copyIntegerAttribute(AnnotationDescriptor annotation, Element element, String attributeName)
/* 2363:     */   {
/* 2364:2420 */     String attribute = element.attributeValue(attributeName);
/* 2365:2421 */     if (attribute != null)
/* 2366:     */     {
/* 2367:2422 */       String annotationAttributeName = getJavaAttributeNameFromXMLOne(attributeName);
/* 2368:2423 */       annotation.setValue(annotationAttributeName, attribute);
/* 2369:     */       try
/* 2370:     */       {
/* 2371:2425 */         int length = Integer.parseInt(attribute);
/* 2372:2426 */         annotation.setValue(annotationAttributeName, Integer.valueOf(length));
/* 2373:     */       }
/* 2374:     */       catch (NumberFormatException e)
/* 2375:     */       {
/* 2376:2429 */         throw new AnnotationException(element.getPath() + attributeName + " not parseable: " + attribute + " (" + "Activate schema validation for more information" + ")");
/* 2377:     */       }
/* 2378:     */     }
/* 2379:     */   }
/* 2380:     */   
/* 2381:     */   private static String getJavaAttributeNameFromXMLOne(String attributeName)
/* 2382:     */   {
/* 2383:2437 */     StringBuilder annotationAttributeName = new StringBuilder(attributeName);
/* 2384:2438 */     int index = annotationAttributeName.indexOf("-");
/* 2385:2439 */     while (index != -1)
/* 2386:     */     {
/* 2387:2440 */       annotationAttributeName.deleteCharAt(index);
/* 2388:2441 */       annotationAttributeName.setCharAt(index, Character.toUpperCase(annotationAttributeName.charAt(index)));
/* 2389:     */       
/* 2390:     */ 
/* 2391:2444 */       index = annotationAttributeName.indexOf("-");
/* 2392:     */     }
/* 2393:2446 */     return annotationAttributeName.toString();
/* 2394:     */   }
/* 2395:     */   
/* 2396:     */   private static void copyStringElement(Element element, AnnotationDescriptor ad, String annotationAttribute)
/* 2397:     */   {
/* 2398:2450 */     String discr = element.getTextTrim();
/* 2399:2451 */     ad.setValue(annotationAttribute, discr);
/* 2400:     */   }
/* 2401:     */   
/* 2402:     */   private static void copyBooleanAttribute(AnnotationDescriptor descriptor, Element element, String attribute)
/* 2403:     */   {
/* 2404:2455 */     String attributeValue = element.attributeValue(attribute);
/* 2405:2456 */     if (StringHelper.isNotEmpty(attributeValue))
/* 2406:     */     {
/* 2407:2457 */       String javaAttribute = getJavaAttributeNameFromXMLOne(attribute);
/* 2408:2458 */       descriptor.setValue(javaAttribute, Boolean.valueOf(Boolean.parseBoolean(attributeValue)));
/* 2409:     */     }
/* 2410:     */   }
/* 2411:     */   
/* 2412:     */   private <T extends Annotation> T getJavaAnnotation(Class<T> annotationType)
/* 2413:     */   {
/* 2414:2463 */     return this.element.getAnnotation(annotationType);
/* 2415:     */   }
/* 2416:     */   
/* 2417:     */   private <T extends Annotation> boolean isJavaAnnotationPresent(Class<T> annotationType)
/* 2418:     */   {
/* 2419:2467 */     return this.element.isAnnotationPresent(annotationType);
/* 2420:     */   }
/* 2421:     */   
/* 2422:     */   private Annotation[] getJavaAnnotations()
/* 2423:     */   {
/* 2424:2471 */     return this.element.getAnnotations();
/* 2425:     */   }
/* 2426:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.reflection.JPAOverridenAnnotationReader
 * JD-Core Version:    0.7.0.1
 */
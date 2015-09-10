/*    1:     */ package org.hibernate.persister.entity;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.util.ArrayList;
/*    5:     */ import java.util.HashMap;
/*    6:     */ import java.util.HashSet;
/*    7:     */ import java.util.Iterator;
/*    8:     */ import java.util.Map;
/*    9:     */ import org.hibernate.HibernateException;
/*   10:     */ import org.hibernate.MappingException;
/*   11:     */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*   12:     */ import org.hibernate.cfg.Settings;
/*   13:     */ import org.hibernate.dialect.Dialect;
/*   14:     */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*   15:     */ import org.hibernate.engine.spi.Mapping;
/*   16:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   17:     */ import org.hibernate.internal.util.MarkerObject;
/*   18:     */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   19:     */ import org.hibernate.mapping.Formula;
/*   20:     */ import org.hibernate.mapping.Join;
/*   21:     */ import org.hibernate.mapping.KeyValue;
/*   22:     */ import org.hibernate.mapping.PersistentClass;
/*   23:     */ import org.hibernate.mapping.Property;
/*   24:     */ import org.hibernate.mapping.Selectable;
/*   25:     */ import org.hibernate.mapping.Subclass;
/*   26:     */ import org.hibernate.mapping.Table;
/*   27:     */ import org.hibernate.mapping.Value;
/*   28:     */ import org.hibernate.metamodel.binding.AttributeBinding;
/*   29:     */ import org.hibernate.metamodel.binding.AttributeBindingContainer;
/*   30:     */ import org.hibernate.metamodel.binding.CustomSQL;
/*   31:     */ import org.hibernate.metamodel.binding.EntityBinding;
/*   32:     */ import org.hibernate.metamodel.binding.EntityDiscriminator;
/*   33:     */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*   34:     */ import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
/*   35:     */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*   36:     */ import org.hibernate.metamodel.binding.SimpleValueBinding;
/*   37:     */ import org.hibernate.metamodel.binding.SingularAttributeBinding;
/*   38:     */ import org.hibernate.metamodel.domain.Attribute;
/*   39:     */ import org.hibernate.metamodel.domain.Entity;
/*   40:     */ import org.hibernate.metamodel.relational.DerivedValue;
/*   41:     */ import org.hibernate.metamodel.relational.Identifier;
/*   42:     */ import org.hibernate.metamodel.relational.SimpleValue;
/*   43:     */ import org.hibernate.metamodel.relational.TableSpecification;
/*   44:     */ import org.hibernate.sql.InFragment;
/*   45:     */ import org.hibernate.sql.Insert;
/*   46:     */ import org.hibernate.sql.SelectFragment;
/*   47:     */ import org.hibernate.tuple.entity.EntityMetamodel;
/*   48:     */ import org.hibernate.type.AssociationType;
/*   49:     */ import org.hibernate.type.DiscriminatorType;
/*   50:     */ import org.hibernate.type.Type;
/*   51:     */ 
/*   52:     */ public class SingleTableEntityPersister
/*   53:     */   extends AbstractEntityPersister
/*   54:     */ {
/*   55:     */   private final int joinSpan;
/*   56:     */   private final String[] qualifiedTableNames;
/*   57:     */   private final boolean[] isInverseTable;
/*   58:     */   private final boolean[] isNullableTable;
/*   59:     */   private final String[][] keyColumnNames;
/*   60:     */   private final boolean[] cascadeDeleteEnabled;
/*   61:     */   private final boolean hasSequentialSelects;
/*   62:     */   private final String[] spaces;
/*   63:     */   private final String[] subclassClosure;
/*   64:     */   private final String[] subclassTableNameClosure;
/*   65:     */   private final boolean[] subclassTableIsLazyClosure;
/*   66:     */   private final boolean[] isInverseSubclassTable;
/*   67:     */   private final boolean[] isNullableSubclassTable;
/*   68:     */   private final boolean[] subclassTableSequentialSelect;
/*   69:     */   private final String[][] subclassTableKeyColumnClosure;
/*   70:     */   private final boolean[] isClassOrSuperclassTable;
/*   71:     */   private final int[] propertyTableNumbers;
/*   72:     */   private final int[] subclassPropertyTableNumberClosure;
/*   73:     */   private final int[] subclassColumnTableNumberClosure;
/*   74:     */   private final int[] subclassFormulaTableNumberClosure;
/*   75: 108 */   private final Map subclassesByDiscriminatorValue = new HashMap();
/*   76:     */   private final boolean forceDiscriminator;
/*   77:     */   private final String discriminatorColumnName;
/*   78:     */   private final String discriminatorColumnReaders;
/*   79:     */   private final String discriminatorColumnReaderTemplate;
/*   80:     */   private final String discriminatorFormula;
/*   81:     */   private final String discriminatorFormulaTemplate;
/*   82:     */   private final String discriminatorAlias;
/*   83:     */   private final Type discriminatorType;
/*   84:     */   private final Object discriminatorValue;
/*   85:     */   private final String discriminatorSQLValue;
/*   86:     */   private final boolean discriminatorInsertable;
/*   87:     */   private final String[] constraintOrderedTableNames;
/*   88:     */   private final String[][] constraintOrderedKeyColumnNames;
/*   89: 125 */   private final Map propertyTableNumbersByNameAndSubclass = new HashMap();
/*   90: 127 */   private final Map sequentialSelectStringsByEntityName = new HashMap();
/*   91: 129 */   private static final Object NULL_DISCRIMINATOR = new MarkerObject("<null discriminator>");
/*   92: 130 */   private static final Object NOT_NULL_DISCRIMINATOR = new MarkerObject("<not null discriminator>");
/*   93:     */   private static final String NULL_STRING = "null";
/*   94:     */   private static final String NOT_NULL_STRING = "not null";
/*   95:     */   
/*   96:     */   public SingleTableEntityPersister(PersistentClass persistentClass, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory, Mapping mapping)
/*   97:     */     throws HibernateException
/*   98:     */   {
/*   99: 142 */     super(persistentClass, cacheAccessStrategy, factory);
/*  100:     */     
/*  101:     */ 
/*  102:     */ 
/*  103: 146 */     this.joinSpan = (persistentClass.getJoinClosureSpan() + 1);
/*  104: 147 */     this.qualifiedTableNames = new String[this.joinSpan];
/*  105: 148 */     this.isInverseTable = new boolean[this.joinSpan];
/*  106: 149 */     this.isNullableTable = new boolean[this.joinSpan];
/*  107: 150 */     this.keyColumnNames = new String[this.joinSpan][];
/*  108: 151 */     Table table = persistentClass.getRootTable();
/*  109: 152 */     this.qualifiedTableNames[0] = table.getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/*  110:     */     
/*  111:     */ 
/*  112:     */ 
/*  113:     */ 
/*  114: 157 */     this.isInverseTable[0] = false;
/*  115: 158 */     this.isNullableTable[0] = false;
/*  116: 159 */     this.keyColumnNames[0] = getIdentifierColumnNames();
/*  117: 160 */     this.cascadeDeleteEnabled = new boolean[this.joinSpan];
/*  118:     */     
/*  119:     */ 
/*  120: 163 */     this.customSQLInsert = new String[this.joinSpan];
/*  121: 164 */     this.customSQLUpdate = new String[this.joinSpan];
/*  122: 165 */     this.customSQLDelete = new String[this.joinSpan];
/*  123: 166 */     this.insertCallable = new boolean[this.joinSpan];
/*  124: 167 */     this.updateCallable = new boolean[this.joinSpan];
/*  125: 168 */     this.deleteCallable = new boolean[this.joinSpan];
/*  126: 169 */     this.insertResultCheckStyles = new ExecuteUpdateResultCheckStyle[this.joinSpan];
/*  127: 170 */     this.updateResultCheckStyles = new ExecuteUpdateResultCheckStyle[this.joinSpan];
/*  128: 171 */     this.deleteResultCheckStyles = new ExecuteUpdateResultCheckStyle[this.joinSpan];
/*  129:     */     
/*  130: 173 */     this.customSQLInsert[0] = persistentClass.getCustomSQLInsert();
/*  131: 174 */     this.insertCallable[0] = ((this.customSQLInsert[0] != null) && (persistentClass.isCustomInsertCallable()) ? 1 : false);
/*  132: 175 */     this.insertResultCheckStyles[0] = (persistentClass.getCustomSQLInsertCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLInsert[0], this.insertCallable[0]) : persistentClass.getCustomSQLInsertCheckStyle());
/*  133:     */     
/*  134:     */ 
/*  135: 178 */     this.customSQLUpdate[0] = persistentClass.getCustomSQLUpdate();
/*  136: 179 */     this.updateCallable[0] = ((this.customSQLUpdate[0] != null) && (persistentClass.isCustomUpdateCallable()) ? 1 : false);
/*  137: 180 */     this.updateResultCheckStyles[0] = (persistentClass.getCustomSQLUpdateCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLUpdate[0], this.updateCallable[0]) : persistentClass.getCustomSQLUpdateCheckStyle());
/*  138:     */     
/*  139:     */ 
/*  140: 183 */     this.customSQLDelete[0] = persistentClass.getCustomSQLDelete();
/*  141: 184 */     this.deleteCallable[0] = ((this.customSQLDelete[0] != null) && (persistentClass.isCustomDeleteCallable()) ? 1 : false);
/*  142: 185 */     this.deleteResultCheckStyles[0] = (persistentClass.getCustomSQLDeleteCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLDelete[0], this.deleteCallable[0]) : persistentClass.getCustomSQLDeleteCheckStyle());
/*  143:     */     
/*  144:     */ 
/*  145:     */ 
/*  146:     */ 
/*  147:     */ 
/*  148: 191 */     Iterator joinIter = persistentClass.getJoinClosureIterator();
/*  149: 192 */     int j = 1;
/*  150: 193 */     while (joinIter.hasNext())
/*  151:     */     {
/*  152: 194 */       Join join = (Join)joinIter.next();
/*  153: 195 */       this.qualifiedTableNames[j] = join.getTable().getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/*  154:     */       
/*  155:     */ 
/*  156:     */ 
/*  157:     */ 
/*  158: 200 */       this.isInverseTable[j] = join.isInverse();
/*  159: 201 */       this.isNullableTable[j] = join.isOptional();
/*  160: 202 */       this.cascadeDeleteEnabled[j] = ((join.getKey().isCascadeDeleteEnabled()) && (factory.getDialect().supportsCascadeDelete()) ? 1 : false);
/*  161:     */       
/*  162:     */ 
/*  163: 205 */       this.customSQLInsert[j] = join.getCustomSQLInsert();
/*  164: 206 */       this.insertCallable[j] = ((this.customSQLInsert[j] != null) && (join.isCustomInsertCallable()) ? 1 : false);
/*  165: 207 */       this.insertResultCheckStyles[j] = (join.getCustomSQLInsertCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLInsert[j], this.insertCallable[j]) : join.getCustomSQLInsertCheckStyle());
/*  166:     */       
/*  167:     */ 
/*  168: 210 */       this.customSQLUpdate[j] = join.getCustomSQLUpdate();
/*  169: 211 */       this.updateCallable[j] = ((this.customSQLUpdate[j] != null) && (join.isCustomUpdateCallable()) ? 1 : false);
/*  170: 212 */       this.updateResultCheckStyles[j] = (join.getCustomSQLUpdateCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLUpdate[j], this.updateCallable[j]) : join.getCustomSQLUpdateCheckStyle());
/*  171:     */       
/*  172:     */ 
/*  173: 215 */       this.customSQLDelete[j] = join.getCustomSQLDelete();
/*  174: 216 */       this.deleteCallable[j] = ((this.customSQLDelete[j] != null) && (join.isCustomDeleteCallable()) ? 1 : false);
/*  175: 217 */       this.deleteResultCheckStyles[j] = (join.getCustomSQLDeleteCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(this.customSQLDelete[j], this.deleteCallable[j]) : join.getCustomSQLDeleteCheckStyle());
/*  176:     */       
/*  177:     */ 
/*  178:     */ 
/*  179: 221 */       Iterator iter = join.getKey().getColumnIterator();
/*  180: 222 */       this.keyColumnNames[j] = new String[join.getKey().getColumnSpan()];
/*  181: 223 */       int i = 0;
/*  182: 224 */       while (iter.hasNext())
/*  183:     */       {
/*  184: 225 */         org.hibernate.mapping.Column col = (org.hibernate.mapping.Column)iter.next();
/*  185: 226 */         this.keyColumnNames[j][(i++)] = col.getQuotedName(factory.getDialect());
/*  186:     */       }
/*  187: 229 */       j++;
/*  188:     */     }
/*  189: 232 */     this.constraintOrderedTableNames = new String[this.qualifiedTableNames.length];
/*  190: 233 */     this.constraintOrderedKeyColumnNames = new String[this.qualifiedTableNames.length][];
/*  191: 234 */     int i = this.qualifiedTableNames.length - 1;
/*  192: 234 */     for (int position = 0; i >= 0; position++)
/*  193:     */     {
/*  194: 235 */       this.constraintOrderedTableNames[position] = this.qualifiedTableNames[i];
/*  195: 236 */       this.constraintOrderedKeyColumnNames[position] = this.keyColumnNames[i];i--;
/*  196:     */     }
/*  197: 239 */     this.spaces = ArrayHelper.join(this.qualifiedTableNames, ArrayHelper.toStringArray(persistentClass.getSynchronizedTables()));
/*  198:     */     
/*  199:     */ 
/*  200:     */ 
/*  201:     */ 
/*  202: 244 */     boolean lazyAvailable = isInstrumented();
/*  203:     */     
/*  204: 246 */     boolean hasDeferred = false;
/*  205: 247 */     ArrayList subclassTables = new ArrayList();
/*  206: 248 */     ArrayList joinKeyColumns = new ArrayList();
/*  207: 249 */     ArrayList<Boolean> isConcretes = new ArrayList();
/*  208: 250 */     ArrayList<Boolean> isDeferreds = new ArrayList();
/*  209: 251 */     ArrayList<Boolean> isInverses = new ArrayList();
/*  210: 252 */     ArrayList<Boolean> isNullables = new ArrayList();
/*  211: 253 */     ArrayList<Boolean> isLazies = new ArrayList();
/*  212: 254 */     subclassTables.add(this.qualifiedTableNames[0]);
/*  213: 255 */     joinKeyColumns.add(getIdentifierColumnNames());
/*  214: 256 */     isConcretes.add(Boolean.TRUE);
/*  215: 257 */     isDeferreds.add(Boolean.FALSE);
/*  216: 258 */     isInverses.add(Boolean.FALSE);
/*  217: 259 */     isNullables.add(Boolean.FALSE);
/*  218: 260 */     isLazies.add(Boolean.FALSE);
/*  219: 261 */     joinIter = persistentClass.getSubclassJoinClosureIterator();
/*  220: 262 */     while (joinIter.hasNext())
/*  221:     */     {
/*  222: 263 */       Join join = (Join)joinIter.next();
/*  223: 264 */       isConcretes.add(Boolean.valueOf(persistentClass.isClassOrSuperclassJoin(join)));
/*  224: 265 */       isDeferreds.add(Boolean.valueOf(join.isSequentialSelect()));
/*  225: 266 */       isInverses.add(Boolean.valueOf(join.isInverse()));
/*  226: 267 */       isNullables.add(Boolean.valueOf(join.isOptional()));
/*  227: 268 */       isLazies.add(Boolean.valueOf((lazyAvailable) && (join.isLazy())));
/*  228: 269 */       if ((join.isSequentialSelect()) && (!persistentClass.isClassOrSuperclassJoin(join))) {
/*  229: 269 */         hasDeferred = true;
/*  230:     */       }
/*  231: 270 */       subclassTables.add(join.getTable().getQualifiedName(factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName()));
/*  232:     */       
/*  233:     */ 
/*  234:     */ 
/*  235:     */ 
/*  236: 275 */       Iterator iter = join.getKey().getColumnIterator();
/*  237: 276 */       String[] keyCols = new String[join.getKey().getColumnSpan()];
/*  238: 277 */       int i = 0;
/*  239: 278 */       while (iter.hasNext())
/*  240:     */       {
/*  241: 279 */         org.hibernate.mapping.Column col = (org.hibernate.mapping.Column)iter.next();
/*  242: 280 */         keyCols[(i++)] = col.getQuotedName(factory.getDialect());
/*  243:     */       }
/*  244: 282 */       joinKeyColumns.add(keyCols);
/*  245:     */     }
/*  246: 285 */     this.subclassTableSequentialSelect = ArrayHelper.toBooleanArray(isDeferreds);
/*  247: 286 */     this.subclassTableNameClosure = ArrayHelper.toStringArray(subclassTables);
/*  248: 287 */     this.subclassTableIsLazyClosure = ArrayHelper.toBooleanArray(isLazies);
/*  249: 288 */     this.subclassTableKeyColumnClosure = ArrayHelper.to2DStringArray(joinKeyColumns);
/*  250: 289 */     this.isClassOrSuperclassTable = ArrayHelper.toBooleanArray(isConcretes);
/*  251: 290 */     this.isInverseSubclassTable = ArrayHelper.toBooleanArray(isInverses);
/*  252: 291 */     this.isNullableSubclassTable = ArrayHelper.toBooleanArray(isNullables);
/*  253: 292 */     this.hasSequentialSelects = hasDeferred;
/*  254: 296 */     if (persistentClass.isPolymorphic())
/*  255:     */     {
/*  256: 297 */       Value discrimValue = persistentClass.getDiscriminator();
/*  257: 298 */       if (discrimValue == null) {
/*  258: 299 */         throw new MappingException("discriminator mapping required for single table polymorphic persistence");
/*  259:     */       }
/*  260: 301 */       this.forceDiscriminator = persistentClass.isForceDiscriminator();
/*  261: 302 */       Selectable selectable = (Selectable)discrimValue.getColumnIterator().next();
/*  262: 303 */       if (discrimValue.hasFormula())
/*  263:     */       {
/*  264: 304 */         Formula formula = (Formula)selectable;
/*  265: 305 */         this.discriminatorFormula = formula.getFormula();
/*  266: 306 */         this.discriminatorFormulaTemplate = formula.getTemplate(factory.getDialect(), factory.getSqlFunctionRegistry());
/*  267: 307 */         this.discriminatorColumnName = null;
/*  268: 308 */         this.discriminatorColumnReaders = null;
/*  269: 309 */         this.discriminatorColumnReaderTemplate = null;
/*  270: 310 */         this.discriminatorAlias = "clazz_";
/*  271:     */       }
/*  272:     */       else
/*  273:     */       {
/*  274: 313 */         org.hibernate.mapping.Column column = (org.hibernate.mapping.Column)selectable;
/*  275: 314 */         this.discriminatorColumnName = column.getQuotedName(factory.getDialect());
/*  276: 315 */         this.discriminatorColumnReaders = column.getReadExpr(factory.getDialect());
/*  277: 316 */         this.discriminatorColumnReaderTemplate = column.getTemplate(factory.getDialect(), factory.getSqlFunctionRegistry());
/*  278: 317 */         this.discriminatorAlias = column.getAlias(factory.getDialect(), persistentClass.getRootTable());
/*  279: 318 */         this.discriminatorFormula = null;
/*  280: 319 */         this.discriminatorFormulaTemplate = null;
/*  281:     */       }
/*  282: 321 */       this.discriminatorType = persistentClass.getDiscriminator().getType();
/*  283: 322 */       if (persistentClass.isDiscriminatorValueNull())
/*  284:     */       {
/*  285: 323 */         this.discriminatorValue = NULL_DISCRIMINATOR;
/*  286: 324 */         this.discriminatorSQLValue = "null";
/*  287: 325 */         this.discriminatorInsertable = false;
/*  288:     */       }
/*  289: 327 */       else if (persistentClass.isDiscriminatorValueNotNull())
/*  290:     */       {
/*  291: 328 */         this.discriminatorValue = NOT_NULL_DISCRIMINATOR;
/*  292: 329 */         this.discriminatorSQLValue = "not null";
/*  293: 330 */         this.discriminatorInsertable = false;
/*  294:     */       }
/*  295:     */       else
/*  296:     */       {
/*  297: 333 */         this.discriminatorInsertable = ((persistentClass.isDiscriminatorInsertable()) && (!discrimValue.hasFormula()));
/*  298:     */         try
/*  299:     */         {
/*  300: 335 */           DiscriminatorType dtype = (DiscriminatorType)this.discriminatorType;
/*  301: 336 */           this.discriminatorValue = dtype.stringToObject(persistentClass.getDiscriminatorValue());
/*  302: 337 */           this.discriminatorSQLValue = dtype.objectToSQLString(this.discriminatorValue, factory.getDialect());
/*  303:     */         }
/*  304:     */         catch (ClassCastException cce)
/*  305:     */         {
/*  306: 340 */           throw new MappingException("Illegal discriminator type: " + this.discriminatorType.getName());
/*  307:     */         }
/*  308:     */         catch (Exception e)
/*  309:     */         {
/*  310: 343 */           throw new MappingException("Could not format discriminator value to SQL string", e);
/*  311:     */         }
/*  312:     */       }
/*  313:     */     }
/*  314:     */     else
/*  315:     */     {
/*  316: 348 */       this.forceDiscriminator = false;
/*  317: 349 */       this.discriminatorInsertable = false;
/*  318: 350 */       this.discriminatorColumnName = null;
/*  319: 351 */       this.discriminatorColumnReaders = null;
/*  320: 352 */       this.discriminatorColumnReaderTemplate = null;
/*  321: 353 */       this.discriminatorAlias = null;
/*  322: 354 */       this.discriminatorType = null;
/*  323: 355 */       this.discriminatorValue = null;
/*  324: 356 */       this.discriminatorSQLValue = null;
/*  325: 357 */       this.discriminatorFormula = null;
/*  326: 358 */       this.discriminatorFormulaTemplate = null;
/*  327:     */     }
/*  328: 363 */     this.propertyTableNumbers = new int[getPropertySpan()];
/*  329: 364 */     Iterator iter = persistentClass.getPropertyClosureIterator();
/*  330: 365 */     int i = 0;
/*  331: 366 */     while (iter.hasNext())
/*  332:     */     {
/*  333: 367 */       Property prop = (Property)iter.next();
/*  334: 368 */       this.propertyTableNumbers[(i++)] = persistentClass.getJoinNumber(prop);
/*  335:     */     }
/*  336: 374 */     ArrayList columnJoinNumbers = new ArrayList();
/*  337: 375 */     ArrayList formulaJoinedNumbers = new ArrayList();
/*  338: 376 */     ArrayList propertyJoinNumbers = new ArrayList();
/*  339:     */     
/*  340: 378 */     iter = persistentClass.getSubclassPropertyClosureIterator();
/*  341: 379 */     while (iter.hasNext())
/*  342:     */     {
/*  343: 380 */       Property prop = (Property)iter.next();
/*  344: 381 */       Integer join = Integer.valueOf(persistentClass.getJoinNumber(prop));
/*  345: 382 */       propertyJoinNumbers.add(join);
/*  346:     */       
/*  347:     */ 
/*  348: 385 */       this.propertyTableNumbersByNameAndSubclass.put(prop.getPersistentClass().getEntityName() + '.' + prop.getName(), join);
/*  349:     */       
/*  350:     */ 
/*  351:     */ 
/*  352:     */ 
/*  353: 390 */       Iterator citer = prop.getColumnIterator();
/*  354: 391 */       while (citer.hasNext())
/*  355:     */       {
/*  356: 392 */         Selectable thing = (Selectable)citer.next();
/*  357: 393 */         if (thing.isFormula()) {
/*  358: 394 */           formulaJoinedNumbers.add(join);
/*  359:     */         } else {
/*  360: 397 */           columnJoinNumbers.add(join);
/*  361:     */         }
/*  362:     */       }
/*  363:     */     }
/*  364: 401 */     this.subclassColumnTableNumberClosure = ArrayHelper.toIntArray(columnJoinNumbers);
/*  365: 402 */     this.subclassFormulaTableNumberClosure = ArrayHelper.toIntArray(formulaJoinedNumbers);
/*  366: 403 */     this.subclassPropertyTableNumberClosure = ArrayHelper.toIntArray(propertyJoinNumbers);
/*  367:     */     
/*  368: 405 */     int subclassSpan = persistentClass.getSubclassSpan() + 1;
/*  369: 406 */     this.subclassClosure = new String[subclassSpan];
/*  370: 407 */     this.subclassClosure[0] = getEntityName();
/*  371: 408 */     if (persistentClass.isPolymorphic()) {
/*  372: 409 */       this.subclassesByDiscriminatorValue.put(this.discriminatorValue, getEntityName());
/*  373:     */     }
/*  374: 413 */     if (persistentClass.isPolymorphic())
/*  375:     */     {
/*  376: 414 */       iter = persistentClass.getSubclassIterator();
/*  377: 415 */       int k = 1;
/*  378: 416 */       while (iter.hasNext())
/*  379:     */       {
/*  380: 417 */         Subclass sc = (Subclass)iter.next();
/*  381: 418 */         this.subclassClosure[(k++)] = sc.getEntityName();
/*  382: 419 */         if (sc.isDiscriminatorValueNull()) {
/*  383: 420 */           this.subclassesByDiscriminatorValue.put(NULL_DISCRIMINATOR, sc.getEntityName());
/*  384: 422 */         } else if (sc.isDiscriminatorValueNotNull()) {
/*  385: 423 */           this.subclassesByDiscriminatorValue.put(NOT_NULL_DISCRIMINATOR, sc.getEntityName());
/*  386:     */         } else {
/*  387:     */           try
/*  388:     */           {
/*  389: 427 */             DiscriminatorType dtype = (DiscriminatorType)this.discriminatorType;
/*  390: 428 */             this.subclassesByDiscriminatorValue.put(dtype.stringToObject(sc.getDiscriminatorValue()), sc.getEntityName());
/*  391:     */           }
/*  392:     */           catch (ClassCastException cce)
/*  393:     */           {
/*  394: 434 */             throw new MappingException("Illegal discriminator type: " + this.discriminatorType.getName());
/*  395:     */           }
/*  396:     */           catch (Exception e)
/*  397:     */           {
/*  398: 437 */             throw new MappingException("Error parsing discriminator value", e);
/*  399:     */           }
/*  400:     */         }
/*  401:     */       }
/*  402:     */     }
/*  403: 443 */     initLockers();
/*  404:     */     
/*  405: 445 */     initSubclassPropertyAliasesMap(persistentClass);
/*  406:     */     
/*  407: 447 */     postConstruct(mapping);
/*  408:     */   }
/*  409:     */   
/*  410:     */   public SingleTableEntityPersister(EntityBinding entityBinding, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory, Mapping mapping)
/*  411:     */     throws HibernateException
/*  412:     */   {
/*  413: 457 */     super(entityBinding, cacheAccessStrategy, factory);
/*  414:     */     
/*  415:     */ 
/*  416:     */ 
/*  417:     */ 
/*  418:     */ 
/*  419: 463 */     this.joinSpan = 1;
/*  420: 464 */     this.qualifiedTableNames = new String[this.joinSpan];
/*  421: 465 */     this.isInverseTable = new boolean[this.joinSpan];
/*  422: 466 */     this.isNullableTable = new boolean[this.joinSpan];
/*  423: 467 */     this.keyColumnNames = new String[this.joinSpan][];
/*  424:     */     
/*  425: 469 */     TableSpecification table = entityBinding.getPrimaryTable();
/*  426: 470 */     this.qualifiedTableNames[0] = table.getQualifiedName(factory.getDialect());
/*  427: 471 */     this.isInverseTable[0] = false;
/*  428: 472 */     this.isNullableTable[0] = false;
/*  429: 473 */     this.keyColumnNames[0] = getIdentifierColumnNames();
/*  430: 474 */     this.cascadeDeleteEnabled = new boolean[this.joinSpan];
/*  431:     */     
/*  432:     */ 
/*  433: 477 */     this.customSQLInsert = new String[this.joinSpan];
/*  434: 478 */     this.customSQLUpdate = new String[this.joinSpan];
/*  435: 479 */     this.customSQLDelete = new String[this.joinSpan];
/*  436: 480 */     this.insertCallable = new boolean[this.joinSpan];
/*  437: 481 */     this.updateCallable = new boolean[this.joinSpan];
/*  438: 482 */     this.deleteCallable = new boolean[this.joinSpan];
/*  439: 483 */     this.insertResultCheckStyles = new ExecuteUpdateResultCheckStyle[this.joinSpan];
/*  440: 484 */     this.updateResultCheckStyles = new ExecuteUpdateResultCheckStyle[this.joinSpan];
/*  441: 485 */     this.deleteResultCheckStyles = new ExecuteUpdateResultCheckStyle[this.joinSpan];
/*  442:     */     
/*  443: 487 */     initializeCustomSql(entityBinding.getCustomInsert(), 0, this.customSQLInsert, this.insertCallable, this.insertResultCheckStyles);
/*  444: 488 */     initializeCustomSql(entityBinding.getCustomUpdate(), 0, this.customSQLUpdate, this.updateCallable, this.updateResultCheckStyles);
/*  445: 489 */     initializeCustomSql(entityBinding.getCustomDelete(), 0, this.customSQLDelete, this.deleteCallable, this.deleteResultCheckStyles);
/*  446:     */     
/*  447:     */ 
/*  448:     */ 
/*  449:     */ 
/*  450:     */ 
/*  451: 495 */     this.constraintOrderedTableNames = new String[this.qualifiedTableNames.length];
/*  452: 496 */     this.constraintOrderedKeyColumnNames = new String[this.qualifiedTableNames.length][];
/*  453: 497 */     int i = this.qualifiedTableNames.length - 1;
/*  454: 497 */     for (int position = 0; i >= 0; position++)
/*  455:     */     {
/*  456: 498 */       this.constraintOrderedTableNames[position] = this.qualifiedTableNames[i];
/*  457: 499 */       this.constraintOrderedKeyColumnNames[position] = this.keyColumnNames[i];i--;
/*  458:     */     }
/*  459: 502 */     this.spaces = ArrayHelper.join(this.qualifiedTableNames, ArrayHelper.toStringArray(entityBinding.getSynchronizedTableNames()));
/*  460:     */     
/*  461:     */ 
/*  462:     */ 
/*  463:     */ 
/*  464: 507 */     boolean lazyAvailable = isInstrumented();
/*  465:     */     
/*  466: 509 */     boolean hasDeferred = false;
/*  467: 510 */     ArrayList subclassTables = new ArrayList();
/*  468: 511 */     ArrayList joinKeyColumns = new ArrayList();
/*  469: 512 */     ArrayList<Boolean> isConcretes = new ArrayList();
/*  470: 513 */     ArrayList<Boolean> isDeferreds = new ArrayList();
/*  471: 514 */     ArrayList<Boolean> isInverses = new ArrayList();
/*  472: 515 */     ArrayList<Boolean> isNullables = new ArrayList();
/*  473: 516 */     ArrayList<Boolean> isLazies = new ArrayList();
/*  474: 517 */     subclassTables.add(this.qualifiedTableNames[0]);
/*  475: 518 */     joinKeyColumns.add(getIdentifierColumnNames());
/*  476: 519 */     isConcretes.add(Boolean.TRUE);
/*  477: 520 */     isDeferreds.add(Boolean.FALSE);
/*  478: 521 */     isInverses.add(Boolean.FALSE);
/*  479: 522 */     isNullables.add(Boolean.FALSE);
/*  480: 523 */     isLazies.add(Boolean.FALSE);
/*  481:     */     
/*  482:     */ 
/*  483:     */ 
/*  484:     */ 
/*  485: 528 */     this.subclassTableSequentialSelect = ArrayHelper.toBooleanArray(isDeferreds);
/*  486: 529 */     this.subclassTableNameClosure = ArrayHelper.toStringArray(subclassTables);
/*  487: 530 */     this.subclassTableIsLazyClosure = ArrayHelper.toBooleanArray(isLazies);
/*  488: 531 */     this.subclassTableKeyColumnClosure = ArrayHelper.to2DStringArray(joinKeyColumns);
/*  489: 532 */     this.isClassOrSuperclassTable = ArrayHelper.toBooleanArray(isConcretes);
/*  490: 533 */     this.isInverseSubclassTable = ArrayHelper.toBooleanArray(isInverses);
/*  491: 534 */     this.isNullableSubclassTable = ArrayHelper.toBooleanArray(isNullables);
/*  492: 535 */     this.hasSequentialSelects = hasDeferred;
/*  493: 539 */     if (entityBinding.isPolymorphic())
/*  494:     */     {
/*  495: 540 */       SimpleValue discriminatorRelationalValue = entityBinding.getHierarchyDetails().getEntityDiscriminator().getBoundValue();
/*  496: 541 */       if (discriminatorRelationalValue == null) {
/*  497: 542 */         throw new MappingException("discriminator mapping required for single table polymorphic persistence");
/*  498:     */       }
/*  499: 544 */       this.forceDiscriminator = entityBinding.getHierarchyDetails().getEntityDiscriminator().isForced();
/*  500: 545 */       if (DerivedValue.class.isInstance(discriminatorRelationalValue))
/*  501:     */       {
/*  502: 546 */         DerivedValue formula = (DerivedValue)discriminatorRelationalValue;
/*  503: 547 */         this.discriminatorFormula = formula.getExpression();
/*  504: 548 */         this.discriminatorFormulaTemplate = getTemplateFromString(formula.getExpression(), factory);
/*  505: 549 */         this.discriminatorColumnName = null;
/*  506: 550 */         this.discriminatorColumnReaders = null;
/*  507: 551 */         this.discriminatorColumnReaderTemplate = null;
/*  508: 552 */         this.discriminatorAlias = "clazz_";
/*  509:     */       }
/*  510:     */       else
/*  511:     */       {
/*  512: 555 */         org.hibernate.metamodel.relational.Column column = (org.hibernate.metamodel.relational.Column)discriminatorRelationalValue;
/*  513: 556 */         this.discriminatorColumnName = column.getColumnName().encloseInQuotesIfQuoted(factory.getDialect());
/*  514: 557 */         this.discriminatorColumnReaders = (column.getReadFragment() == null ? column.getColumnName().encloseInQuotesIfQuoted(factory.getDialect()) : column.getReadFragment());
/*  515:     */         
/*  516:     */ 
/*  517:     */ 
/*  518: 561 */         this.discriminatorColumnReaderTemplate = getTemplateFromColumn(column, factory);
/*  519: 562 */         this.discriminatorAlias = column.getAlias(factory.getDialect());
/*  520: 563 */         this.discriminatorFormula = null;
/*  521: 564 */         this.discriminatorFormulaTemplate = null;
/*  522:     */       }
/*  523: 567 */       this.discriminatorType = entityBinding.getHierarchyDetails().getEntityDiscriminator().getExplicitHibernateTypeDescriptor().getResolvedTypeMapping();
/*  524: 571 */       if (entityBinding.getDiscriminatorMatchValue() == null)
/*  525:     */       {
/*  526: 572 */         this.discriminatorValue = NULL_DISCRIMINATOR;
/*  527: 573 */         this.discriminatorSQLValue = "null";
/*  528: 574 */         this.discriminatorInsertable = false;
/*  529:     */       }
/*  530: 576 */       else if (entityBinding.getDiscriminatorMatchValue().equals("null"))
/*  531:     */       {
/*  532: 577 */         this.discriminatorValue = NOT_NULL_DISCRIMINATOR;
/*  533: 578 */         this.discriminatorSQLValue = "not null";
/*  534: 579 */         this.discriminatorInsertable = false;
/*  535:     */       }
/*  536: 581 */       else if (entityBinding.getDiscriminatorMatchValue().equals("not null"))
/*  537:     */       {
/*  538: 582 */         this.discriminatorValue = NOT_NULL_DISCRIMINATOR;
/*  539: 583 */         this.discriminatorSQLValue = "not null";
/*  540: 584 */         this.discriminatorInsertable = false;
/*  541:     */       }
/*  542:     */       else
/*  543:     */       {
/*  544: 587 */         this.discriminatorInsertable = ((entityBinding.getHierarchyDetails().getEntityDiscriminator().isInserted()) && (!DerivedValue.class.isInstance(discriminatorRelationalValue)));
/*  545:     */         try
/*  546:     */         {
/*  547: 590 */           DiscriminatorType dtype = (DiscriminatorType)this.discriminatorType;
/*  548: 591 */           this.discriminatorValue = dtype.stringToObject(entityBinding.getDiscriminatorMatchValue());
/*  549: 592 */           this.discriminatorSQLValue = dtype.objectToSQLString(this.discriminatorValue, factory.getDialect());
/*  550:     */         }
/*  551:     */         catch (ClassCastException cce)
/*  552:     */         {
/*  553: 595 */           throw new MappingException("Illegal discriminator type: " + this.discriminatorType.getName());
/*  554:     */         }
/*  555:     */         catch (Exception e)
/*  556:     */         {
/*  557: 598 */           throw new MappingException("Could not format discriminator value to SQL string", e);
/*  558:     */         }
/*  559:     */       }
/*  560:     */     }
/*  561:     */     else
/*  562:     */     {
/*  563: 603 */       this.forceDiscriminator = false;
/*  564: 604 */       this.discriminatorInsertable = false;
/*  565: 605 */       this.discriminatorColumnName = null;
/*  566: 606 */       this.discriminatorColumnReaders = null;
/*  567: 607 */       this.discriminatorColumnReaderTemplate = null;
/*  568: 608 */       this.discriminatorAlias = null;
/*  569: 609 */       this.discriminatorType = null;
/*  570: 610 */       this.discriminatorValue = null;
/*  571: 611 */       this.discriminatorSQLValue = null;
/*  572: 612 */       this.discriminatorFormula = null;
/*  573: 613 */       this.discriminatorFormulaTemplate = null;
/*  574:     */     }
/*  575: 618 */     this.propertyTableNumbers = new int[getPropertySpan()];
/*  576: 619 */     int i = 0;
/*  577: 620 */     for (AttributeBinding attributeBinding : entityBinding.getAttributeBindingClosure()) {
/*  578: 623 */       if ((attributeBinding != entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding()) && 
/*  579:     */       
/*  580:     */ 
/*  581: 626 */         (attributeBinding.getAttribute().isSingular())) {
/*  582: 629 */         this.propertyTableNumbers[(i++)] = 0;
/*  583:     */       }
/*  584:     */     }
/*  585: 634 */     ArrayList columnJoinNumbers = new ArrayList();
/*  586: 635 */     ArrayList formulaJoinedNumbers = new ArrayList();
/*  587: 636 */     ArrayList propertyJoinNumbers = new ArrayList();
/*  588: 638 */     for (AttributeBinding attributeBinding : entityBinding.getSubEntityAttributeBindingClosure()) {
/*  589: 639 */       if (attributeBinding.getAttribute().isSingular())
/*  590:     */       {
/*  591: 642 */         SingularAttributeBinding singularAttributeBinding = (SingularAttributeBinding)attributeBinding;
/*  592:     */         
/*  593:     */ 
/*  594:     */ 
/*  595: 646 */         join = 0;
/*  596: 647 */         propertyJoinNumbers.add(Integer.valueOf(join));
/*  597:     */         
/*  598:     */ 
/*  599: 650 */         this.propertyTableNumbersByNameAndSubclass.put(singularAttributeBinding.getContainer().getPathBase() + '.' + singularAttributeBinding.getAttribute().getName(), Integer.valueOf(join));
/*  600: 655 */         for (SimpleValueBinding simpleValueBinding : singularAttributeBinding.getSimpleValueBindings()) {
/*  601: 656 */           if (DerivedValue.class.isInstance(simpleValueBinding.getSimpleValue())) {
/*  602: 657 */             formulaJoinedNumbers.add(Integer.valueOf(join));
/*  603:     */           } else {
/*  604: 660 */             columnJoinNumbers.add(Integer.valueOf(join));
/*  605:     */           }
/*  606:     */         }
/*  607:     */       }
/*  608:     */     }
/*  609:     */     int join;
/*  610: 664 */     this.subclassColumnTableNumberClosure = ArrayHelper.toIntArray(columnJoinNumbers);
/*  611: 665 */     this.subclassFormulaTableNumberClosure = ArrayHelper.toIntArray(formulaJoinedNumbers);
/*  612: 666 */     this.subclassPropertyTableNumberClosure = ArrayHelper.toIntArray(propertyJoinNumbers);
/*  613:     */     
/*  614: 668 */     int subclassSpan = entityBinding.getSubEntityBindingClosureSpan() + 1;
/*  615: 669 */     this.subclassClosure = new String[subclassSpan];
/*  616: 670 */     this.subclassClosure[0] = getEntityName();
/*  617: 671 */     if (entityBinding.isPolymorphic()) {
/*  618: 672 */       this.subclassesByDiscriminatorValue.put(this.discriminatorValue, getEntityName());
/*  619:     */     }
/*  620:     */     int k;
/*  621: 676 */     if (entityBinding.isPolymorphic())
/*  622:     */     {
/*  623: 677 */       k = 1;
/*  624: 678 */       for (EntityBinding subEntityBinding : entityBinding.getPostOrderSubEntityBindingClosure())
/*  625:     */       {
/*  626: 679 */         this.subclassClosure[(k++)] = subEntityBinding.getEntity().getName();
/*  627: 680 */         if (subEntityBinding.isDiscriminatorMatchValueNull()) {
/*  628: 681 */           this.subclassesByDiscriminatorValue.put(NULL_DISCRIMINATOR, subEntityBinding.getEntity().getName());
/*  629: 683 */         } else if (subEntityBinding.isDiscriminatorMatchValueNotNull()) {
/*  630: 684 */           this.subclassesByDiscriminatorValue.put(NOT_NULL_DISCRIMINATOR, subEntityBinding.getEntity().getName());
/*  631:     */         } else {
/*  632:     */           try
/*  633:     */           {
/*  634: 688 */             DiscriminatorType dtype = (DiscriminatorType)this.discriminatorType;
/*  635: 689 */             this.subclassesByDiscriminatorValue.put(dtype.stringToObject(subEntityBinding.getDiscriminatorMatchValue()), subEntityBinding.getEntity().getName());
/*  636:     */           }
/*  637:     */           catch (ClassCastException cce)
/*  638:     */           {
/*  639: 695 */             throw new MappingException("Illegal discriminator type: " + this.discriminatorType.getName());
/*  640:     */           }
/*  641:     */           catch (Exception e)
/*  642:     */           {
/*  643: 698 */             throw new MappingException("Error parsing discriminator value", e);
/*  644:     */           }
/*  645:     */         }
/*  646:     */       }
/*  647:     */     }
/*  648: 704 */     initLockers();
/*  649:     */     
/*  650: 706 */     initSubclassPropertyAliasesMap(entityBinding);
/*  651:     */     
/*  652: 708 */     postConstruct(mapping);
/*  653:     */   }
/*  654:     */   
/*  655:     */   private static void initializeCustomSql(CustomSQL customSql, int i, String[] sqlStrings, boolean[] callable, ExecuteUpdateResultCheckStyle[] checkStyles)
/*  656:     */   {
/*  657: 717 */     sqlStrings[i] = (customSql != null ? customSql.getSql() : null);
/*  658: 718 */     callable[i] = ((sqlStrings[i] != null) && (customSql.isCallable()) ? 1 : false);
/*  659: 719 */     checkStyles[i] = ((customSql != null) && (customSql.getCheckStyle() != null) ? customSql.getCheckStyle() : ExecuteUpdateResultCheckStyle.determineDefault(sqlStrings[i], callable[i]));
/*  660:     */   }
/*  661:     */   
/*  662:     */   protected boolean isInverseTable(int j)
/*  663:     */   {
/*  664: 725 */     return this.isInverseTable[j];
/*  665:     */   }
/*  666:     */   
/*  667:     */   protected boolean isInverseSubclassTable(int j)
/*  668:     */   {
/*  669: 729 */     return this.isInverseSubclassTable[j];
/*  670:     */   }
/*  671:     */   
/*  672:     */   public String getDiscriminatorColumnName()
/*  673:     */   {
/*  674: 733 */     return this.discriminatorColumnName;
/*  675:     */   }
/*  676:     */   
/*  677:     */   public String getDiscriminatorColumnReaders()
/*  678:     */   {
/*  679: 737 */     return this.discriminatorColumnReaders;
/*  680:     */   }
/*  681:     */   
/*  682:     */   public String getDiscriminatorColumnReaderTemplate()
/*  683:     */   {
/*  684: 741 */     return this.discriminatorColumnReaderTemplate;
/*  685:     */   }
/*  686:     */   
/*  687:     */   protected String getDiscriminatorAlias()
/*  688:     */   {
/*  689: 745 */     return this.discriminatorAlias;
/*  690:     */   }
/*  691:     */   
/*  692:     */   protected String getDiscriminatorFormulaTemplate()
/*  693:     */   {
/*  694: 749 */     return this.discriminatorFormulaTemplate;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public String getTableName()
/*  698:     */   {
/*  699: 753 */     return this.qualifiedTableNames[0];
/*  700:     */   }
/*  701:     */   
/*  702:     */   public Type getDiscriminatorType()
/*  703:     */   {
/*  704: 757 */     return this.discriminatorType;
/*  705:     */   }
/*  706:     */   
/*  707:     */   public Object getDiscriminatorValue()
/*  708:     */   {
/*  709: 761 */     return this.discriminatorValue;
/*  710:     */   }
/*  711:     */   
/*  712:     */   public String getDiscriminatorSQLValue()
/*  713:     */   {
/*  714: 765 */     return this.discriminatorSQLValue;
/*  715:     */   }
/*  716:     */   
/*  717:     */   public String[] getSubclassClosure()
/*  718:     */   {
/*  719: 769 */     return this.subclassClosure;
/*  720:     */   }
/*  721:     */   
/*  722:     */   public String getSubclassForDiscriminatorValue(Object value)
/*  723:     */   {
/*  724: 773 */     if (value == null) {
/*  725: 774 */       return (String)this.subclassesByDiscriminatorValue.get(NULL_DISCRIMINATOR);
/*  726:     */     }
/*  727: 777 */     String result = (String)this.subclassesByDiscriminatorValue.get(value);
/*  728: 778 */     if (result == null) {
/*  729: 778 */       result = (String)this.subclassesByDiscriminatorValue.get(NOT_NULL_DISCRIMINATOR);
/*  730:     */     }
/*  731: 779 */     return result;
/*  732:     */   }
/*  733:     */   
/*  734:     */   public Serializable[] getPropertySpaces()
/*  735:     */   {
/*  736: 784 */     return this.spaces;
/*  737:     */   }
/*  738:     */   
/*  739:     */   protected boolean isDiscriminatorFormula()
/*  740:     */   {
/*  741: 790 */     return this.discriminatorColumnName == null;
/*  742:     */   }
/*  743:     */   
/*  744:     */   protected String getDiscriminatorFormula()
/*  745:     */   {
/*  746: 794 */     return this.discriminatorFormula;
/*  747:     */   }
/*  748:     */   
/*  749:     */   protected String getTableName(int j)
/*  750:     */   {
/*  751: 798 */     return this.qualifiedTableNames[j];
/*  752:     */   }
/*  753:     */   
/*  754:     */   protected String[] getKeyColumns(int j)
/*  755:     */   {
/*  756: 802 */     return this.keyColumnNames[j];
/*  757:     */   }
/*  758:     */   
/*  759:     */   protected boolean isTableCascadeDeleteEnabled(int j)
/*  760:     */   {
/*  761: 806 */     return this.cascadeDeleteEnabled[j];
/*  762:     */   }
/*  763:     */   
/*  764:     */   protected boolean isPropertyOfTable(int property, int j)
/*  765:     */   {
/*  766: 810 */     return this.propertyTableNumbers[property] == j;
/*  767:     */   }
/*  768:     */   
/*  769:     */   protected boolean isSubclassTableSequentialSelect(int j)
/*  770:     */   {
/*  771: 814 */     return (this.subclassTableSequentialSelect[j] != 0) && (this.isClassOrSuperclassTable[j] == 0);
/*  772:     */   }
/*  773:     */   
/*  774:     */   public String fromTableFragment(String name)
/*  775:     */   {
/*  776: 820 */     return getTableName() + ' ' + name;
/*  777:     */   }
/*  778:     */   
/*  779:     */   public String filterFragment(String alias)
/*  780:     */     throws MappingException
/*  781:     */   {
/*  782: 824 */     String result = discriminatorFilterFragment(alias);
/*  783: 825 */     if (hasWhere()) {
/*  784: 825 */       result = result + " and " + getSQLWhereString(alias);
/*  785:     */     }
/*  786: 826 */     return result;
/*  787:     */   }
/*  788:     */   
/*  789:     */   public String oneToManyFilterFragment(String alias)
/*  790:     */     throws MappingException
/*  791:     */   {
/*  792: 830 */     return this.forceDiscriminator ? discriminatorFilterFragment(alias) : "";
/*  793:     */   }
/*  794:     */   
/*  795:     */   private String discriminatorFilterFragment(String alias)
/*  796:     */     throws MappingException
/*  797:     */   {
/*  798: 836 */     if (needsDiscriminator())
/*  799:     */     {
/*  800: 837 */       InFragment frag = new InFragment();
/*  801: 839 */       if (isDiscriminatorFormula()) {
/*  802: 840 */         frag.setFormula(alias, getDiscriminatorFormulaTemplate());
/*  803:     */       } else {
/*  804: 843 */         frag.setColumn(alias, getDiscriminatorColumnName());
/*  805:     */       }
/*  806: 846 */       String[] subclasses = getSubclassClosure();
/*  807: 847 */       for (int i = 0; i < subclasses.length; i++)
/*  808:     */       {
/*  809: 848 */         Queryable queryable = (Queryable)getFactory().getEntityPersister(subclasses[i]);
/*  810: 849 */         if (!queryable.isAbstract()) {
/*  811: 849 */           frag.addValue(queryable.getDiscriminatorSQLValue());
/*  812:     */         }
/*  813:     */       }
/*  814: 852 */       StringBuffer buf = new StringBuffer(50).append(" and ").append(frag.toFragmentString());
/*  815:     */       
/*  816:     */ 
/*  817:     */ 
/*  818: 856 */       return buf.toString();
/*  819:     */     }
/*  820: 859 */     return "";
/*  821:     */   }
/*  822:     */   
/*  823:     */   private boolean needsDiscriminator()
/*  824:     */   {
/*  825: 864 */     return (this.forceDiscriminator) || (isInherited());
/*  826:     */   }
/*  827:     */   
/*  828:     */   public String getSubclassPropertyTableName(int i)
/*  829:     */   {
/*  830: 868 */     return this.subclassTableNameClosure[this.subclassPropertyTableNumberClosure[i]];
/*  831:     */   }
/*  832:     */   
/*  833:     */   protected void addDiscriminatorToSelect(SelectFragment select, String name, String suffix)
/*  834:     */   {
/*  835: 872 */     if (isDiscriminatorFormula()) {
/*  836: 873 */       select.addFormula(name, getDiscriminatorFormulaTemplate(), getDiscriminatorAlias());
/*  837:     */     } else {
/*  838: 876 */       select.addColumn(name, getDiscriminatorColumnName(), getDiscriminatorAlias());
/*  839:     */     }
/*  840:     */   }
/*  841:     */   
/*  842:     */   protected int[] getPropertyTableNumbersInSelect()
/*  843:     */   {
/*  844: 881 */     return this.propertyTableNumbers;
/*  845:     */   }
/*  846:     */   
/*  847:     */   protected int getSubclassPropertyTableNumber(int i)
/*  848:     */   {
/*  849: 885 */     return this.subclassPropertyTableNumberClosure[i];
/*  850:     */   }
/*  851:     */   
/*  852:     */   public int getTableSpan()
/*  853:     */   {
/*  854: 889 */     return this.joinSpan;
/*  855:     */   }
/*  856:     */   
/*  857:     */   protected void addDiscriminatorToInsert(Insert insert)
/*  858:     */   {
/*  859: 894 */     if (this.discriminatorInsertable) {
/*  860: 895 */       insert.addColumn(getDiscriminatorColumnName(), this.discriminatorSQLValue);
/*  861:     */     }
/*  862:     */   }
/*  863:     */   
/*  864:     */   protected int[] getSubclassColumnTableNumberClosure()
/*  865:     */   {
/*  866: 901 */     return this.subclassColumnTableNumberClosure;
/*  867:     */   }
/*  868:     */   
/*  869:     */   protected int[] getSubclassFormulaTableNumberClosure()
/*  870:     */   {
/*  871: 905 */     return this.subclassFormulaTableNumberClosure;
/*  872:     */   }
/*  873:     */   
/*  874:     */   protected int[] getPropertyTableNumbers()
/*  875:     */   {
/*  876: 909 */     return this.propertyTableNumbers;
/*  877:     */   }
/*  878:     */   
/*  879:     */   protected boolean isSubclassPropertyDeferred(String propertyName, String entityName)
/*  880:     */   {
/*  881: 913 */     return (this.hasSequentialSelects) && (isSubclassTableSequentialSelect(getSubclassPropertyTableNumber(propertyName, entityName)));
/*  882:     */   }
/*  883:     */   
/*  884:     */   public boolean hasSequentialSelect()
/*  885:     */   {
/*  886: 918 */     return this.hasSequentialSelects;
/*  887:     */   }
/*  888:     */   
/*  889:     */   private int getSubclassPropertyTableNumber(String propertyName, String entityName)
/*  890:     */   {
/*  891: 922 */     Type type = this.propertyMapping.toType(propertyName);
/*  892: 923 */     if ((type.isAssociationType()) && (((AssociationType)type).useLHSPrimaryKey())) {
/*  893: 923 */       return 0;
/*  894:     */     }
/*  895: 924 */     Integer tabnum = (Integer)this.propertyTableNumbersByNameAndSubclass.get(entityName + '.' + propertyName);
/*  896: 925 */     return tabnum == null ? 0 : tabnum.intValue();
/*  897:     */   }
/*  898:     */   
/*  899:     */   protected String getSequentialSelect(String entityName)
/*  900:     */   {
/*  901: 929 */     return (String)this.sequentialSelectStringsByEntityName.get(entityName);
/*  902:     */   }
/*  903:     */   
/*  904:     */   private String generateSequentialSelect(Loadable persister)
/*  905:     */   {
/*  906: 939 */     AbstractEntityPersister subclassPersister = (AbstractEntityPersister)persister;
/*  907: 940 */     HashSet tableNumbers = new HashSet();
/*  908: 941 */     String[] props = subclassPersister.getPropertyNames();
/*  909: 942 */     String[] classes = subclassPersister.getPropertySubclassNames();
/*  910: 943 */     for (int i = 0; i < props.length; i++)
/*  911:     */     {
/*  912: 944 */       int propTableNumber = getSubclassPropertyTableNumber(props[i], classes[i]);
/*  913: 945 */       if ((isSubclassTableSequentialSelect(propTableNumber)) && (!isSubclassTableLazy(propTableNumber))) {
/*  914: 946 */         tableNumbers.add(Integer.valueOf(propTableNumber));
/*  915:     */       }
/*  916:     */     }
/*  917: 949 */     if (tableNumbers.isEmpty()) {
/*  918: 949 */       return null;
/*  919:     */     }
/*  920: 952 */     ArrayList columnNumbers = new ArrayList();
/*  921: 953 */     int[] columnTableNumbers = getSubclassColumnTableNumberClosure();
/*  922: 954 */     for (int i = 0; i < getSubclassColumnClosure().length; i++) {
/*  923: 955 */       if (tableNumbers.contains(Integer.valueOf(columnTableNumbers[i]))) {
/*  924: 956 */         columnNumbers.add(Integer.valueOf(i));
/*  925:     */       }
/*  926:     */     }
/*  927: 961 */     ArrayList formulaNumbers = new ArrayList();
/*  928: 962 */     int[] formulaTableNumbers = getSubclassColumnTableNumberClosure();
/*  929: 963 */     for (int i = 0; i < getSubclassFormulaTemplateClosure().length; i++) {
/*  930: 964 */       if (tableNumbers.contains(Integer.valueOf(formulaTableNumbers[i]))) {
/*  931: 965 */         formulaNumbers.add(Integer.valueOf(i));
/*  932:     */       }
/*  933:     */     }
/*  934: 970 */     return renderSelect(ArrayHelper.toIntArray(tableNumbers), ArrayHelper.toIntArray(columnNumbers), ArrayHelper.toIntArray(formulaNumbers));
/*  935:     */   }
/*  936:     */   
/*  937:     */   protected String[] getSubclassTableKeyColumns(int j)
/*  938:     */   {
/*  939: 979 */     return this.subclassTableKeyColumnClosure[j];
/*  940:     */   }
/*  941:     */   
/*  942:     */   public String getSubclassTableName(int j)
/*  943:     */   {
/*  944: 983 */     return this.subclassTableNameClosure[j];
/*  945:     */   }
/*  946:     */   
/*  947:     */   public int getSubclassTableSpan()
/*  948:     */   {
/*  949: 987 */     return this.subclassTableNameClosure.length;
/*  950:     */   }
/*  951:     */   
/*  952:     */   protected boolean isClassOrSuperclassTable(int j)
/*  953:     */   {
/*  954: 991 */     return this.isClassOrSuperclassTable[j];
/*  955:     */   }
/*  956:     */   
/*  957:     */   protected boolean isSubclassTableLazy(int j)
/*  958:     */   {
/*  959: 995 */     return this.subclassTableIsLazyClosure[j];
/*  960:     */   }
/*  961:     */   
/*  962:     */   protected boolean isNullableTable(int j)
/*  963:     */   {
/*  964: 999 */     return this.isNullableTable[j];
/*  965:     */   }
/*  966:     */   
/*  967:     */   protected boolean isNullableSubclassTable(int j)
/*  968:     */   {
/*  969:1003 */     return this.isNullableSubclassTable[j];
/*  970:     */   }
/*  971:     */   
/*  972:     */   public String getPropertyTableName(String propertyName)
/*  973:     */   {
/*  974:1007 */     Integer index = getEntityMetamodel().getPropertyIndexOrNull(propertyName);
/*  975:1008 */     if (index == null) {
/*  976:1008 */       return null;
/*  977:     */     }
/*  978:1009 */     return this.qualifiedTableNames[this.propertyTableNumbers[index.intValue()]];
/*  979:     */   }
/*  980:     */   
/*  981:     */   public void postInstantiate()
/*  982:     */   {
/*  983:1013 */     super.postInstantiate();
/*  984:1014 */     if (this.hasSequentialSelects)
/*  985:     */     {
/*  986:1015 */       String[] entityNames = getSubclassClosure();
/*  987:1016 */       for (int i = 1; i < entityNames.length; i++)
/*  988:     */       {
/*  989:1017 */         Loadable loadable = (Loadable)getFactory().getEntityPersister(entityNames[i]);
/*  990:1018 */         if (!loadable.isAbstract())
/*  991:     */         {
/*  992:1019 */           String sequentialSelect = generateSequentialSelect(loadable);
/*  993:1020 */           this.sequentialSelectStringsByEntityName.put(entityNames[i], sequentialSelect);
/*  994:     */         }
/*  995:     */       }
/*  996:     */     }
/*  997:     */   }
/*  998:     */   
/*  999:     */   public boolean isMultiTable()
/* 1000:     */   {
/* 1001:1027 */     return getTableSpan() > 1;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public String[] getConstraintOrderedTableNameClosure()
/* 1005:     */   {
/* 1006:1031 */     return this.constraintOrderedTableNames;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public String[][] getContraintOrderedTableKeyColumnClosure()
/* 1010:     */   {
/* 1011:1035 */     return this.constraintOrderedKeyColumnNames;
/* 1012:     */   }
/* 1013:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.SingleTableEntityPersister
 * JD-Core Version:    0.7.0.1
 */
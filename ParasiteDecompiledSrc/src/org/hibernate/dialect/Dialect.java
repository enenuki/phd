/*    1:     */ package org.hibernate.dialect;
/*    2:     */ 
/*    3:     */ import java.io.InputStream;
/*    4:     */ import java.io.OutputStream;
/*    5:     */ import java.sql.Blob;
/*    6:     */ import java.sql.CallableStatement;
/*    7:     */ import java.sql.Clob;
/*    8:     */ import java.sql.NClob;
/*    9:     */ import java.sql.ResultSet;
/*   10:     */ import java.sql.SQLException;
/*   11:     */ import java.util.HashMap;
/*   12:     */ import java.util.HashSet;
/*   13:     */ import java.util.Map;
/*   14:     */ import java.util.Properties;
/*   15:     */ import java.util.Set;
/*   16:     */ import org.hibernate.HibernateException;
/*   17:     */ import org.hibernate.LockMode;
/*   18:     */ import org.hibernate.LockOptions;
/*   19:     */ import org.hibernate.MappingException;
/*   20:     */ import org.hibernate.cfg.Environment;
/*   21:     */ import org.hibernate.dialect.function.CastFunction;
/*   22:     */ import org.hibernate.dialect.function.SQLFunction;
/*   23:     */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*   24:     */ import org.hibernate.dialect.function.StandardAnsiSqlAggregationFunctions;
/*   25:     */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   26:     */ import org.hibernate.dialect.lock.LockingStrategy;
/*   27:     */ import org.hibernate.dialect.lock.OptimisticForceIncrementLockingStrategy;
/*   28:     */ import org.hibernate.dialect.lock.OptimisticLockingStrategy;
/*   29:     */ import org.hibernate.dialect.lock.PessimisticForceIncrementLockingStrategy;
/*   30:     */ import org.hibernate.dialect.lock.PessimisticReadSelectLockingStrategy;
/*   31:     */ import org.hibernate.dialect.lock.PessimisticWriteSelectLockingStrategy;
/*   32:     */ import org.hibernate.dialect.lock.SelectLockingStrategy;
/*   33:     */ import org.hibernate.engine.jdbc.LobCreator;
/*   34:     */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*   35:     */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   36:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   37:     */ import org.hibernate.engine.spi.SessionImplementor;
/*   38:     */ import org.hibernate.exception.internal.SQLStateConverter;
/*   39:     */ import org.hibernate.exception.spi.SQLExceptionConverter;
/*   40:     */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*   41:     */ import org.hibernate.id.IdentityGenerator;
/*   42:     */ import org.hibernate.id.SequenceGenerator;
/*   43:     */ import org.hibernate.id.TableHiLoGenerator;
/*   44:     */ import org.hibernate.internal.CoreMessageLogger;
/*   45:     */ import org.hibernate.internal.util.ReflectHelper;
/*   46:     */ import org.hibernate.internal.util.StringHelper;
/*   47:     */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   48:     */ import org.hibernate.internal.util.io.StreamCopier;
/*   49:     */ import org.hibernate.persister.entity.Lockable;
/*   50:     */ import org.hibernate.sql.ANSICaseFragment;
/*   51:     */ import org.hibernate.sql.ANSIJoinFragment;
/*   52:     */ import org.hibernate.sql.CaseFragment;
/*   53:     */ import org.hibernate.sql.ForUpdateFragment;
/*   54:     */ import org.hibernate.sql.JoinFragment;
/*   55:     */ import org.hibernate.type.BigDecimalType;
/*   56:     */ import org.hibernate.type.BigIntegerType;
/*   57:     */ import org.hibernate.type.BinaryType;
/*   58:     */ import org.hibernate.type.BlobType;
/*   59:     */ import org.hibernate.type.BooleanType;
/*   60:     */ import org.hibernate.type.ByteType;
/*   61:     */ import org.hibernate.type.CharacterType;
/*   62:     */ import org.hibernate.type.ClobType;
/*   63:     */ import org.hibernate.type.DateType;
/*   64:     */ import org.hibernate.type.DoubleType;
/*   65:     */ import org.hibernate.type.FloatType;
/*   66:     */ import org.hibernate.type.ImageType;
/*   67:     */ import org.hibernate.type.IntegerType;
/*   68:     */ import org.hibernate.type.ShortType;
/*   69:     */ import org.hibernate.type.StandardBasicTypes;
/*   70:     */ import org.hibernate.type.StringType;
/*   71:     */ import org.hibernate.type.TextType;
/*   72:     */ import org.hibernate.type.TimeType;
/*   73:     */ import org.hibernate.type.TimestampType;
/*   74:     */ import org.hibernate.type.descriptor.sql.BlobTypeDescriptor;
/*   75:     */ import org.hibernate.type.descriptor.sql.ClobTypeDescriptor;
/*   76:     */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/*   77:     */ import org.jboss.logging.Logger;
/*   78:     */ 
/*   79:     */ public abstract class Dialect
/*   80:     */ {
/*   81:  98 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Dialect.class.getName());
/*   82:     */   public static final String DEFAULT_BATCH_SIZE = "15";
/*   83:     */   public static final String NO_BATCH = "0";
/*   84:     */   public static final String QUOTE = "`\"[";
/*   85:     */   public static final String CLOSED_QUOTE = "`\"]";
/*   86: 109 */   private final TypeNames typeNames = new TypeNames();
/*   87: 110 */   private final TypeNames hibernateTypeNames = new TypeNames();
/*   88: 112 */   private final Properties properties = new Properties();
/*   89: 113 */   private final Map<String, SQLFunction> sqlFunctions = new HashMap();
/*   90: 114 */   private final Set<String> sqlKeywords = new HashSet();
/*   91:     */   
/*   92:     */   protected Dialect()
/*   93:     */   {
/*   94: 120 */     LOG.usingDialect(this);
/*   95: 121 */     StandardAnsiSqlAggregationFunctions.primeFunctionMap(this.sqlFunctions);
/*   96:     */     
/*   97:     */ 
/*   98: 124 */     registerFunction("substring", new SQLFunctionTemplate(StandardBasicTypes.STRING, "substring(?1, ?2, ?3)"));
/*   99: 125 */     registerFunction("locate", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "locate(?1, ?2, ?3)"));
/*  100: 126 */     registerFunction("trim", new SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?1 ?2 ?3 ?4)"));
/*  101: 127 */     registerFunction("length", new StandardSQLFunction("length", StandardBasicTypes.INTEGER));
/*  102: 128 */     registerFunction("bit_length", new StandardSQLFunction("bit_length", StandardBasicTypes.INTEGER));
/*  103: 129 */     registerFunction("coalesce", new StandardSQLFunction("coalesce"));
/*  104: 130 */     registerFunction("nullif", new StandardSQLFunction("nullif"));
/*  105: 131 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  106: 132 */     registerFunction("mod", new StandardSQLFunction("mod", StandardBasicTypes.INTEGER));
/*  107: 133 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/*  108: 134 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  109: 135 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  110: 136 */     registerFunction("cast", new CastFunction());
/*  111: 137 */     registerFunction("extract", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "extract(?1 ?2 ?3)"));
/*  112:     */     
/*  113:     */ 
/*  114: 140 */     registerFunction("second", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "extract(second from ?1)"));
/*  115: 141 */     registerFunction("minute", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "extract(minute from ?1)"));
/*  116: 142 */     registerFunction("hour", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "extract(hour from ?1)"));
/*  117: 143 */     registerFunction("day", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "extract(day from ?1)"));
/*  118: 144 */     registerFunction("month", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "extract(month from ?1)"));
/*  119: 145 */     registerFunction("year", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "extract(year from ?1)"));
/*  120:     */     
/*  121: 147 */     registerFunction("str", new SQLFunctionTemplate(StandardBasicTypes.STRING, "cast(?1 as char)"));
/*  122:     */     
/*  123: 149 */     registerColumnType(-7, "bit");
/*  124: 150 */     registerColumnType(16, "boolean");
/*  125: 151 */     registerColumnType(-6, "tinyint");
/*  126: 152 */     registerColumnType(5, "smallint");
/*  127: 153 */     registerColumnType(4, "integer");
/*  128: 154 */     registerColumnType(-5, "bigint");
/*  129: 155 */     registerColumnType(6, "float($p)");
/*  130: 156 */     registerColumnType(8, "double precision");
/*  131: 157 */     registerColumnType(2, "numeric($p,$s)");
/*  132: 158 */     registerColumnType(7, "real");
/*  133:     */     
/*  134: 160 */     registerColumnType(91, "date");
/*  135: 161 */     registerColumnType(92, "time");
/*  136: 162 */     registerColumnType(93, "timestamp");
/*  137:     */     
/*  138: 164 */     registerColumnType(-3, "bit varying($l)");
/*  139: 165 */     registerColumnType(-4, "bit varying($l)");
/*  140: 166 */     registerColumnType(2004, "blob");
/*  141:     */     
/*  142: 168 */     registerColumnType(1, "char($l)");
/*  143: 169 */     registerColumnType(12, "varchar($l)");
/*  144: 170 */     registerColumnType(-1, "varchar($l)");
/*  145: 171 */     registerColumnType(2005, "clob");
/*  146:     */     
/*  147: 173 */     registerColumnType(-15, "nchar($l)");
/*  148: 174 */     registerColumnType(-9, "nvarchar($l)");
/*  149: 175 */     registerColumnType(-16, "nvarchar($l)");
/*  150: 176 */     registerColumnType(2011, "nclob");
/*  151:     */     
/*  152:     */ 
/*  153: 179 */     registerHibernateType(-5, StandardBasicTypes.BIG_INTEGER.getName());
/*  154: 180 */     registerHibernateType(-2, StandardBasicTypes.BINARY.getName());
/*  155: 181 */     registerHibernateType(-7, StandardBasicTypes.BOOLEAN.getName());
/*  156: 182 */     registerHibernateType(16, StandardBasicTypes.BOOLEAN.getName());
/*  157: 183 */     registerHibernateType(1, StandardBasicTypes.CHARACTER.getName());
/*  158: 184 */     registerHibernateType(1, 1L, StandardBasicTypes.CHARACTER.getName());
/*  159: 185 */     registerHibernateType(1, 255L, StandardBasicTypes.STRING.getName());
/*  160: 186 */     registerHibernateType(91, StandardBasicTypes.DATE.getName());
/*  161: 187 */     registerHibernateType(8, StandardBasicTypes.DOUBLE.getName());
/*  162: 188 */     registerHibernateType(6, StandardBasicTypes.FLOAT.getName());
/*  163: 189 */     registerHibernateType(4, StandardBasicTypes.INTEGER.getName());
/*  164: 190 */     registerHibernateType(5, StandardBasicTypes.SHORT.getName());
/*  165: 191 */     registerHibernateType(-6, StandardBasicTypes.BYTE.getName());
/*  166: 192 */     registerHibernateType(92, StandardBasicTypes.TIME.getName());
/*  167: 193 */     registerHibernateType(93, StandardBasicTypes.TIMESTAMP.getName());
/*  168: 194 */     registerHibernateType(12, StandardBasicTypes.STRING.getName());
/*  169: 195 */     registerHibernateType(-3, StandardBasicTypes.BINARY.getName());
/*  170: 196 */     registerHibernateType(-1, StandardBasicTypes.TEXT.getName());
/*  171: 197 */     registerHibernateType(-4, StandardBasicTypes.IMAGE.getName());
/*  172: 198 */     registerHibernateType(2, StandardBasicTypes.BIG_DECIMAL.getName());
/*  173: 199 */     registerHibernateType(3, StandardBasicTypes.BIG_DECIMAL.getName());
/*  174: 200 */     registerHibernateType(2004, StandardBasicTypes.BLOB.getName());
/*  175: 201 */     registerHibernateType(2005, StandardBasicTypes.CLOB.getName());
/*  176: 202 */     registerHibernateType(7, StandardBasicTypes.FLOAT.getName());
/*  177:     */   }
/*  178:     */   
/*  179:     */   public static Dialect getDialect()
/*  180:     */     throws HibernateException
/*  181:     */   {
/*  182: 212 */     String dialectName = Environment.getProperties().getProperty("hibernate.dialect");
/*  183: 213 */     return instantiateDialect(dialectName);
/*  184:     */   }
/*  185:     */   
/*  186:     */   public static Dialect getDialect(Properties props)
/*  187:     */     throws HibernateException
/*  188:     */   {
/*  189: 226 */     String dialectName = props.getProperty("hibernate.dialect");
/*  190: 227 */     if (dialectName == null) {
/*  191: 228 */       return getDialect();
/*  192:     */     }
/*  193: 230 */     return instantiateDialect(dialectName);
/*  194:     */   }
/*  195:     */   
/*  196:     */   private static Dialect instantiateDialect(String dialectName)
/*  197:     */     throws HibernateException
/*  198:     */   {
/*  199: 234 */     if (dialectName == null) {
/*  200: 235 */       throw new HibernateException("The dialect was not set. Set the property hibernate.dialect.");
/*  201:     */     }
/*  202:     */     try
/*  203:     */     {
/*  204: 238 */       return (Dialect)ReflectHelper.classForName(dialectName).newInstance();
/*  205:     */     }
/*  206:     */     catch (ClassNotFoundException cnfe)
/*  207:     */     {
/*  208: 241 */       throw new HibernateException("Dialect class not found: " + dialectName);
/*  209:     */     }
/*  210:     */     catch (Exception e)
/*  211:     */     {
/*  212: 244 */       throw new HibernateException("Could not instantiate given dialect class: " + dialectName, e);
/*  213:     */     }
/*  214:     */   }
/*  215:     */   
/*  216:     */   public final Properties getDefaultProperties()
/*  217:     */   {
/*  218: 254 */     return this.properties;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public String toString()
/*  222:     */   {
/*  223: 259 */     return getClass().getName();
/*  224:     */   }
/*  225:     */   
/*  226:     */   public String getTypeName(int code)
/*  227:     */     throws HibernateException
/*  228:     */   {
/*  229: 274 */     String result = this.typeNames.get(code);
/*  230: 275 */     if (result == null) {
/*  231: 276 */       throw new HibernateException("No default type mapping for (java.sql.Types) " + code);
/*  232:     */     }
/*  233: 278 */     return result;
/*  234:     */   }
/*  235:     */   
/*  236:     */   public String getTypeName(int code, long length, int precision, int scale)
/*  237:     */     throws HibernateException
/*  238:     */   {
/*  239: 294 */     String result = this.typeNames.get(code, length, precision, scale);
/*  240: 295 */     if (result == null) {
/*  241: 296 */       throw new HibernateException(String.format("No type mapping for java.sql.Types code: %s, length: %s", new Object[] { Integer.valueOf(code), Long.valueOf(length) }));
/*  242:     */     }
/*  243: 298 */     return result;
/*  244:     */   }
/*  245:     */   
/*  246:     */   public String getCastTypeName(int code)
/*  247:     */   {
/*  248: 309 */     return getTypeName(code, 255L, 19, 2);
/*  249:     */   }
/*  250:     */   
/*  251:     */   protected void registerColumnType(int code, long capacity, String name)
/*  252:     */   {
/*  253: 322 */     this.typeNames.put(code, capacity, name);
/*  254:     */   }
/*  255:     */   
/*  256:     */   protected void registerColumnType(int code, String name)
/*  257:     */   {
/*  258: 333 */     this.typeNames.put(code, name);
/*  259:     */   }
/*  260:     */   
/*  261:     */   public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor)
/*  262:     */   {
/*  263: 355 */     if (sqlTypeDescriptor == null) {
/*  264: 356 */       throw new IllegalArgumentException("sqlTypeDescriptor is null");
/*  265:     */     }
/*  266: 358 */     if (!sqlTypeDescriptor.canBeRemapped()) {
/*  267: 359 */       return sqlTypeDescriptor;
/*  268:     */     }
/*  269: 362 */     SqlTypeDescriptor overridden = getSqlTypeDescriptorOverride(sqlTypeDescriptor.getSqlType());
/*  270: 363 */     return overridden == null ? sqlTypeDescriptor : overridden;
/*  271:     */   }
/*  272:     */   
/*  273:     */   protected SqlTypeDescriptor getSqlTypeDescriptorOverride(int sqlCode)
/*  274:     */   {
/*  275:     */     SqlTypeDescriptor descriptor;
/*  276: 377 */     switch (sqlCode)
/*  277:     */     {
/*  278:     */     case 2004: 
/*  279: 379 */       descriptor = useInputStreamToInsertBlob() ? BlobTypeDescriptor.STREAM_BINDING : null;
/*  280: 380 */       break;
/*  281:     */     case 2005: 
/*  282: 383 */       descriptor = useInputStreamToInsertBlob() ? ClobTypeDescriptor.STREAM_BINDING : null;
/*  283: 384 */       break;
/*  284:     */     default: 
/*  285: 387 */       descriptor = null;
/*  286:     */     }
/*  287: 391 */     return descriptor;
/*  288:     */   }
/*  289:     */   
/*  290: 397 */   protected static final LobMergeStrategy LEGACY_LOB_MERGE_STRATEGY = new LobMergeStrategy()
/*  291:     */   {
/*  292:     */     public Blob mergeBlob(Blob original, Blob target, SessionImplementor session)
/*  293:     */     {
/*  294: 400 */       return target;
/*  295:     */     }
/*  296:     */     
/*  297:     */     public Clob mergeClob(Clob original, Clob target, SessionImplementor session)
/*  298:     */     {
/*  299: 405 */       return target;
/*  300:     */     }
/*  301:     */     
/*  302:     */     public NClob mergeNClob(NClob original, NClob target, SessionImplementor session)
/*  303:     */     {
/*  304: 410 */       return target;
/*  305:     */     }
/*  306:     */   };
/*  307: 417 */   protected static final LobMergeStrategy STREAM_XFER_LOB_MERGE_STRATEGY = new LobMergeStrategy()
/*  308:     */   {
/*  309:     */     public Blob mergeBlob(Blob original, Blob target, SessionImplementor session)
/*  310:     */     {
/*  311: 420 */       if (original != target) {
/*  312:     */         try
/*  313:     */         {
/*  314: 422 */           OutputStream connectedStream = target.setBinaryStream(1L);
/*  315: 423 */           InputStream detachedStream = original.getBinaryStream();
/*  316: 424 */           StreamCopier.copy(detachedStream, connectedStream);
/*  317: 425 */           return target;
/*  318:     */         }
/*  319:     */         catch (SQLException e)
/*  320:     */         {
/*  321: 428 */           throw session.getFactory().getSQLExceptionHelper().convert(e, "unable to merge BLOB data");
/*  322:     */         }
/*  323:     */       }
/*  324: 432 */       return Dialect.NEW_LOCATOR_LOB_MERGE_STRATEGY.mergeBlob(original, target, session);
/*  325:     */     }
/*  326:     */     
/*  327:     */     public Clob mergeClob(Clob original, Clob target, SessionImplementor session)
/*  328:     */     {
/*  329: 438 */       if (original != target) {
/*  330:     */         try
/*  331:     */         {
/*  332: 440 */           OutputStream connectedStream = target.setAsciiStream(1L);
/*  333: 441 */           InputStream detachedStream = original.getAsciiStream();
/*  334: 442 */           StreamCopier.copy(detachedStream, connectedStream);
/*  335: 443 */           return target;
/*  336:     */         }
/*  337:     */         catch (SQLException e)
/*  338:     */         {
/*  339: 446 */           throw session.getFactory().getSQLExceptionHelper().convert(e, "unable to merge CLOB data");
/*  340:     */         }
/*  341:     */       }
/*  342: 450 */       return Dialect.NEW_LOCATOR_LOB_MERGE_STRATEGY.mergeClob(original, target, session);
/*  343:     */     }
/*  344:     */     
/*  345:     */     public NClob mergeNClob(NClob original, NClob target, SessionImplementor session)
/*  346:     */     {
/*  347: 456 */       if (original != target) {
/*  348:     */         try
/*  349:     */         {
/*  350: 458 */           OutputStream connectedStream = target.setAsciiStream(1L);
/*  351: 459 */           InputStream detachedStream = original.getAsciiStream();
/*  352: 460 */           StreamCopier.copy(detachedStream, connectedStream);
/*  353: 461 */           return target;
/*  354:     */         }
/*  355:     */         catch (SQLException e)
/*  356:     */         {
/*  357: 464 */           throw session.getFactory().getSQLExceptionHelper().convert(e, "unable to merge NCLOB data");
/*  358:     */         }
/*  359:     */       }
/*  360: 468 */       return Dialect.NEW_LOCATOR_LOB_MERGE_STRATEGY.mergeNClob(original, target, session);
/*  361:     */     }
/*  362:     */   };
/*  363: 476 */   protected static final LobMergeStrategy NEW_LOCATOR_LOB_MERGE_STRATEGY = new LobMergeStrategy()
/*  364:     */   {
/*  365:     */     public Blob mergeBlob(Blob original, Blob target, SessionImplementor session)
/*  366:     */     {
/*  367: 479 */       if ((original == null) && (target == null)) {
/*  368: 480 */         return null;
/*  369:     */       }
/*  370:     */       try
/*  371:     */       {
/*  372: 483 */         LobCreator lobCreator = session.getFactory().getJdbcServices().getLobCreator(session);
/*  373: 484 */         return original == null ? lobCreator.createBlob(ArrayHelper.EMPTY_BYTE_ARRAY) : lobCreator.createBlob(original.getBinaryStream(), original.length());
/*  374:     */       }
/*  375:     */       catch (SQLException e)
/*  376:     */       {
/*  377: 489 */         throw session.getFactory().getSQLExceptionHelper().convert(e, "unable to merge BLOB data");
/*  378:     */       }
/*  379:     */     }
/*  380:     */     
/*  381:     */     public Clob mergeClob(Clob original, Clob target, SessionImplementor session)
/*  382:     */     {
/*  383: 495 */       if ((original == null) && (target == null)) {
/*  384: 496 */         return null;
/*  385:     */       }
/*  386:     */       try
/*  387:     */       {
/*  388: 499 */         LobCreator lobCreator = session.getFactory().getJdbcServices().getLobCreator(session);
/*  389: 500 */         return original == null ? lobCreator.createClob("") : lobCreator.createClob(original.getCharacterStream(), original.length());
/*  390:     */       }
/*  391:     */       catch (SQLException e)
/*  392:     */       {
/*  393: 505 */         throw session.getFactory().getSQLExceptionHelper().convert(e, "unable to merge CLOB data");
/*  394:     */       }
/*  395:     */     }
/*  396:     */     
/*  397:     */     public NClob mergeNClob(NClob original, NClob target, SessionImplementor session)
/*  398:     */     {
/*  399: 511 */       if ((original == null) && (target == null)) {
/*  400: 512 */         return null;
/*  401:     */       }
/*  402:     */       try
/*  403:     */       {
/*  404: 515 */         LobCreator lobCreator = session.getFactory().getJdbcServices().getLobCreator(session);
/*  405: 516 */         return original == null ? lobCreator.createNClob("") : lobCreator.createNClob(original.getCharacterStream(), original.length());
/*  406:     */       }
/*  407:     */       catch (SQLException e)
/*  408:     */       {
/*  409: 521 */         throw session.getFactory().getSQLExceptionHelper().convert(e, "unable to merge NCLOB data");
/*  410:     */       }
/*  411:     */     }
/*  412:     */   };
/*  413:     */   
/*  414:     */   public LobMergeStrategy getLobMergeStrategy()
/*  415:     */   {
/*  416: 527 */     return NEW_LOCATOR_LOB_MERGE_STRATEGY;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public String getHibernateTypeName(int code)
/*  420:     */     throws HibernateException
/*  421:     */   {
/*  422: 542 */     String result = this.hibernateTypeNames.get(code);
/*  423: 543 */     if (result == null) {
/*  424: 544 */       throw new HibernateException("No Hibernate type mapping for java.sql.Types code: " + code);
/*  425:     */     }
/*  426: 546 */     return result;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public String getHibernateTypeName(int code, int length, int precision, int scale)
/*  430:     */     throws HibernateException
/*  431:     */   {
/*  432: 562 */     String result = this.hibernateTypeNames.get(code, length, precision, scale);
/*  433: 563 */     if (result == null) {
/*  434: 564 */       throw new HibernateException("No Hibernate type mapping for java.sql.Types code: " + code + ", length: " + length);
/*  435:     */     }
/*  436: 571 */     return result;
/*  437:     */   }
/*  438:     */   
/*  439:     */   protected void registerHibernateType(int code, long capacity, String name)
/*  440:     */   {
/*  441: 583 */     this.hibernateTypeNames.put(code, capacity, name);
/*  442:     */   }
/*  443:     */   
/*  444:     */   protected void registerHibernateType(int code, String name)
/*  445:     */   {
/*  446: 594 */     this.hibernateTypeNames.put(code, name);
/*  447:     */   }
/*  448:     */   
/*  449:     */   protected void registerFunction(String name, SQLFunction function)
/*  450:     */   {
/*  451: 601 */     this.sqlFunctions.put(name, function);
/*  452:     */   }
/*  453:     */   
/*  454:     */   public final Map<String, SQLFunction> getFunctions()
/*  455:     */   {
/*  456: 611 */     return this.sqlFunctions;
/*  457:     */   }
/*  458:     */   
/*  459:     */   protected void registerKeyword(String word)
/*  460:     */   {
/*  461: 618 */     this.sqlKeywords.add(word);
/*  462:     */   }
/*  463:     */   
/*  464:     */   public Set<String> getKeywords()
/*  465:     */   {
/*  466: 622 */     return this.sqlKeywords;
/*  467:     */   }
/*  468:     */   
/*  469:     */   public Class getNativeIdentifierGeneratorClass()
/*  470:     */   {
/*  471: 637 */     if (supportsIdentityColumns()) {
/*  472: 638 */       return IdentityGenerator.class;
/*  473:     */     }
/*  474: 640 */     if (supportsSequences()) {
/*  475: 641 */       return SequenceGenerator.class;
/*  476:     */     }
/*  477: 644 */     return TableHiLoGenerator.class;
/*  478:     */   }
/*  479:     */   
/*  480:     */   public boolean supportsIdentityColumns()
/*  481:     */   {
/*  482: 657 */     return false;
/*  483:     */   }
/*  484:     */   
/*  485:     */   public boolean supportsInsertSelectIdentity()
/*  486:     */   {
/*  487: 668 */     return false;
/*  488:     */   }
/*  489:     */   
/*  490:     */   public boolean hasDataTypeInIdentityColumn()
/*  491:     */   {
/*  492: 678 */     return true;
/*  493:     */   }
/*  494:     */   
/*  495:     */   public String appendIdentitySelectToInsert(String insertString)
/*  496:     */   {
/*  497: 693 */     return insertString;
/*  498:     */   }
/*  499:     */   
/*  500:     */   public String getIdentitySelectString(String table, String column, int type)
/*  501:     */     throws MappingException
/*  502:     */   {
/*  503: 707 */     return getIdentitySelectString();
/*  504:     */   }
/*  505:     */   
/*  506:     */   protected String getIdentitySelectString()
/*  507:     */     throws MappingException
/*  508:     */   {
/*  509: 718 */     throw new MappingException(getClass().getName() + " does not support identity key generation");
/*  510:     */   }
/*  511:     */   
/*  512:     */   public String getIdentityColumnString(int type)
/*  513:     */     throws MappingException
/*  514:     */   {
/*  515: 730 */     return getIdentityColumnString();
/*  516:     */   }
/*  517:     */   
/*  518:     */   protected String getIdentityColumnString()
/*  519:     */     throws MappingException
/*  520:     */   {
/*  521: 740 */     throw new MappingException(getClass().getName() + " does not support identity key generation");
/*  522:     */   }
/*  523:     */   
/*  524:     */   public String getIdentityInsertString()
/*  525:     */   {
/*  526: 750 */     return null;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public boolean supportsSequences()
/*  530:     */   {
/*  531: 762 */     return false;
/*  532:     */   }
/*  533:     */   
/*  534:     */   public boolean supportsPooledSequences()
/*  535:     */   {
/*  536: 774 */     return false;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public String getSequenceNextValString(String sequenceName)
/*  540:     */     throws MappingException
/*  541:     */   {
/*  542: 788 */     throw new MappingException(getClass().getName() + " does not support sequences");
/*  543:     */   }
/*  544:     */   
/*  545:     */   public String getSelectSequenceNextValString(String sequenceName)
/*  546:     */     throws MappingException
/*  547:     */   {
/*  548: 803 */     throw new MappingException(getClass().getName() + " does not support sequences");
/*  549:     */   }
/*  550:     */   
/*  551:     */   @Deprecated
/*  552:     */   public String[] getCreateSequenceStrings(String sequenceName)
/*  553:     */     throws MappingException
/*  554:     */   {
/*  555: 816 */     return new String[] { getCreateSequenceString(sequenceName) };
/*  556:     */   }
/*  557:     */   
/*  558:     */   public String[] getCreateSequenceStrings(String sequenceName, int initialValue, int incrementSize)
/*  559:     */     throws MappingException
/*  560:     */   {
/*  561: 829 */     return new String[] { getCreateSequenceString(sequenceName, initialValue, incrementSize) };
/*  562:     */   }
/*  563:     */   
/*  564:     */   protected String getCreateSequenceString(String sequenceName)
/*  565:     */     throws MappingException
/*  566:     */   {
/*  567: 847 */     throw new MappingException(getClass().getName() + " does not support sequences");
/*  568:     */   }
/*  569:     */   
/*  570:     */   protected String getCreateSequenceString(String sequenceName, int initialValue, int incrementSize)
/*  571:     */     throws MappingException
/*  572:     */   {
/*  573: 868 */     if (supportsPooledSequences()) {
/*  574: 869 */       return getCreateSequenceString(sequenceName) + " start with " + initialValue + " increment by " + incrementSize;
/*  575:     */     }
/*  576: 871 */     throw new MappingException(getClass().getName() + " does not support pooled sequences");
/*  577:     */   }
/*  578:     */   
/*  579:     */   public String[] getDropSequenceStrings(String sequenceName)
/*  580:     */     throws MappingException
/*  581:     */   {
/*  582: 882 */     return new String[] { getDropSequenceString(sequenceName) };
/*  583:     */   }
/*  584:     */   
/*  585:     */   protected String getDropSequenceString(String sequenceName)
/*  586:     */     throws MappingException
/*  587:     */   {
/*  588: 900 */     throw new MappingException(getClass().getName() + " does not support sequences");
/*  589:     */   }
/*  590:     */   
/*  591:     */   public String getQuerySequencesString()
/*  592:     */   {
/*  593: 910 */     return null;
/*  594:     */   }
/*  595:     */   
/*  596:     */   public String getSelectGUIDString()
/*  597:     */   {
/*  598: 924 */     throw new UnsupportedOperationException(getClass().getName() + " does not support GUIDs");
/*  599:     */   }
/*  600:     */   
/*  601:     */   public boolean supportsLimit()
/*  602:     */   {
/*  603: 937 */     return false;
/*  604:     */   }
/*  605:     */   
/*  606:     */   public boolean supportsLimitOffset()
/*  607:     */   {
/*  608: 947 */     return supportsLimit();
/*  609:     */   }
/*  610:     */   
/*  611:     */   public boolean supportsVariableLimit()
/*  612:     */   {
/*  613: 957 */     return supportsLimit();
/*  614:     */   }
/*  615:     */   
/*  616:     */   public boolean bindLimitParametersInReverseOrder()
/*  617:     */   {
/*  618: 967 */     return false;
/*  619:     */   }
/*  620:     */   
/*  621:     */   public boolean bindLimitParametersFirst()
/*  622:     */   {
/*  623: 977 */     return false;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public boolean useMaxForLimit()
/*  627:     */   {
/*  628: 997 */     return false;
/*  629:     */   }
/*  630:     */   
/*  631:     */   public boolean forceLimitUsage()
/*  632:     */   {
/*  633:1007 */     return false;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public String getLimitString(String query, int offset, int limit)
/*  637:     */   {
/*  638:1019 */     return getLimitString(query, (offset > 0) || (forceLimitUsage()));
/*  639:     */   }
/*  640:     */   
/*  641:     */   protected String getLimitString(String query, boolean hasOffset)
/*  642:     */   {
/*  643:1040 */     throw new UnsupportedOperationException("Paged queries not supported by " + getClass().getName());
/*  644:     */   }
/*  645:     */   
/*  646:     */   public int convertToFirstRowValue(int zeroBasedFirstResult)
/*  647:     */   {
/*  648:1059 */     return zeroBasedFirstResult;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public boolean supportsLockTimeouts()
/*  652:     */   {
/*  653:1072 */     return true;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public boolean isLockTimeoutParameterized()
/*  657:     */   {
/*  658:1090 */     return false;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode)
/*  662:     */   {
/*  663:1103 */     switch (5.$SwitchMap$org$hibernate$LockMode[lockMode.ordinal()])
/*  664:     */     {
/*  665:     */     case 1: 
/*  666:1105 */       return new PessimisticForceIncrementLockingStrategy(lockable, lockMode);
/*  667:     */     case 2: 
/*  668:1107 */       return new PessimisticWriteSelectLockingStrategy(lockable, lockMode);
/*  669:     */     case 3: 
/*  670:1109 */       return new PessimisticReadSelectLockingStrategy(lockable, lockMode);
/*  671:     */     case 4: 
/*  672:1111 */       return new OptimisticLockingStrategy(lockable, lockMode);
/*  673:     */     case 5: 
/*  674:1113 */       return new OptimisticForceIncrementLockingStrategy(lockable, lockMode);
/*  675:     */     }
/*  676:1115 */     return new SelectLockingStrategy(lockable, lockMode);
/*  677:     */   }
/*  678:     */   
/*  679:     */   public String getForUpdateString(LockOptions lockOptions)
/*  680:     */   {
/*  681:1126 */     LockMode lockMode = lockOptions.getLockMode();
/*  682:1127 */     return getForUpdateString(lockMode, lockOptions.getTimeOut());
/*  683:     */   }
/*  684:     */   
/*  685:     */   private String getForUpdateString(LockMode lockMode, int timeout)
/*  686:     */   {
/*  687:1131 */     switch (5.$SwitchMap$org$hibernate$LockMode[lockMode.ordinal()])
/*  688:     */     {
/*  689:     */     case 6: 
/*  690:1133 */       return getForUpdateString();
/*  691:     */     case 3: 
/*  692:1135 */       return getReadLockString(timeout);
/*  693:     */     case 2: 
/*  694:1137 */       return getWriteLockString(timeout);
/*  695:     */     case 1: 
/*  696:     */     case 7: 
/*  697:     */     case 8: 
/*  698:1141 */       return getForUpdateNowaitString();
/*  699:     */     }
/*  700:1143 */     return "";
/*  701:     */   }
/*  702:     */   
/*  703:     */   public String getForUpdateString(LockMode lockMode)
/*  704:     */   {
/*  705:1154 */     return getForUpdateString(lockMode, -1);
/*  706:     */   }
/*  707:     */   
/*  708:     */   public String getForUpdateString()
/*  709:     */   {
/*  710:1164 */     return " for update";
/*  711:     */   }
/*  712:     */   
/*  713:     */   public String getWriteLockString(int timeout)
/*  714:     */   {
/*  715:1176 */     return getForUpdateString();
/*  716:     */   }
/*  717:     */   
/*  718:     */   public String getReadLockString(int timeout)
/*  719:     */   {
/*  720:1188 */     return getForUpdateString();
/*  721:     */   }
/*  722:     */   
/*  723:     */   public boolean forUpdateOfColumns()
/*  724:     */   {
/*  725:1200 */     return false;
/*  726:     */   }
/*  727:     */   
/*  728:     */   public boolean supportsOuterJoinForUpdate()
/*  729:     */   {
/*  730:1210 */     return true;
/*  731:     */   }
/*  732:     */   
/*  733:     */   public String getForUpdateString(String aliases)
/*  734:     */   {
/*  735:1223 */     return getForUpdateString();
/*  736:     */   }
/*  737:     */   
/*  738:     */   public String getForUpdateString(String aliases, LockOptions lockOptions)
/*  739:     */   {
/*  740:1237 */     return getForUpdateString(lockOptions);
/*  741:     */   }
/*  742:     */   
/*  743:     */   public String getForUpdateNowaitString()
/*  744:     */   {
/*  745:1247 */     return getForUpdateString();
/*  746:     */   }
/*  747:     */   
/*  748:     */   public String getForUpdateNowaitString(String aliases)
/*  749:     */   {
/*  750:1258 */     return getForUpdateString(aliases);
/*  751:     */   }
/*  752:     */   
/*  753:     */   public String appendLockHint(LockMode mode, String tableName)
/*  754:     */   {
/*  755:1272 */     return tableName;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public String applyLocksToSql(String sql, LockOptions aliasedLockOptions, Map keyColumnNames)
/*  759:     */   {
/*  760:1289 */     return sql + new ForUpdateFragment(this, aliasedLockOptions, keyColumnNames).toFragmentString();
/*  761:     */   }
/*  762:     */   
/*  763:     */   public String getCreateTableString()
/*  764:     */   {
/*  765:1301 */     return "create table";
/*  766:     */   }
/*  767:     */   
/*  768:     */   public String getCreateMultisetTableString()
/*  769:     */   {
/*  770:1315 */     return getCreateTableString();
/*  771:     */   }
/*  772:     */   
/*  773:     */   public boolean supportsTemporaryTables()
/*  774:     */   {
/*  775:1327 */     return false;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public String generateTemporaryTableName(String baseTableName)
/*  779:     */   {
/*  780:1337 */     return "HT_" + baseTableName;
/*  781:     */   }
/*  782:     */   
/*  783:     */   public String getCreateTemporaryTableString()
/*  784:     */   {
/*  785:1346 */     return "create table";
/*  786:     */   }
/*  787:     */   
/*  788:     */   public String getCreateTemporaryTablePostfix()
/*  789:     */   {
/*  790:1356 */     return "";
/*  791:     */   }
/*  792:     */   
/*  793:     */   public String getDropTemporaryTableString()
/*  794:     */   {
/*  795:1365 */     return "drop table";
/*  796:     */   }
/*  797:     */   
/*  798:     */   public Boolean performTemporaryTableDDLInIsolation()
/*  799:     */   {
/*  800:1392 */     return null;
/*  801:     */   }
/*  802:     */   
/*  803:     */   public boolean dropTemporaryTableAfterUse()
/*  804:     */   {
/*  805:1401 */     return true;
/*  806:     */   }
/*  807:     */   
/*  808:     */   public int registerResultSetOutParameter(CallableStatement statement, int position)
/*  809:     */     throws SQLException
/*  810:     */   {
/*  811:1418 */     throw new UnsupportedOperationException(getClass().getName() + " does not support resultsets via stored procedures");
/*  812:     */   }
/*  813:     */   
/*  814:     */   public ResultSet getResultSet(CallableStatement statement)
/*  815:     */     throws SQLException
/*  816:     */   {
/*  817:1433 */     throw new UnsupportedOperationException(getClass().getName() + " does not support resultsets via stored procedures");
/*  818:     */   }
/*  819:     */   
/*  820:     */   public boolean supportsCurrentTimestampSelection()
/*  821:     */   {
/*  822:1448 */     return false;
/*  823:     */   }
/*  824:     */   
/*  825:     */   public boolean isCurrentTimestampSelectStringCallable()
/*  826:     */   {
/*  827:1460 */     throw new UnsupportedOperationException("Database not known to define a current timestamp function");
/*  828:     */   }
/*  829:     */   
/*  830:     */   public String getCurrentTimestampSelectString()
/*  831:     */   {
/*  832:1470 */     throw new UnsupportedOperationException("Database not known to define a current timestamp function");
/*  833:     */   }
/*  834:     */   
/*  835:     */   public String getCurrentTimestampSQLFunctionName()
/*  836:     */   {
/*  837:1481 */     return "current_timestamp";
/*  838:     */   }
/*  839:     */   
/*  840:     */   public SQLExceptionConverter buildSQLExceptionConverter()
/*  841:     */   {
/*  842:1503 */     return new SQLStateConverter(getViolatedConstraintNameExtracter());
/*  843:     */   }
/*  844:     */   
/*  845:1506 */   private static final ViolatedConstraintNameExtracter EXTRACTER = new ViolatedConstraintNameExtracter()
/*  846:     */   {
/*  847:     */     public String extractConstraintName(SQLException sqle)
/*  848:     */     {
/*  849:1508 */       return null;
/*  850:     */     }
/*  851:     */   };
/*  852:     */   
/*  853:     */   public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter()
/*  854:     */   {
/*  855:1513 */     return EXTRACTER;
/*  856:     */   }
/*  857:     */   
/*  858:     */   public String getSelectClauseNullString(int sqlType)
/*  859:     */   {
/*  860:1531 */     return "null";
/*  861:     */   }
/*  862:     */   
/*  863:     */   public boolean supportsUnionAll()
/*  864:     */   {
/*  865:1541 */     return false;
/*  866:     */   }
/*  867:     */   
/*  868:     */   public JoinFragment createOuterJoinFragment()
/*  869:     */   {
/*  870:1555 */     return new ANSIJoinFragment();
/*  871:     */   }
/*  872:     */   
/*  873:     */   public CaseFragment createCaseFragment()
/*  874:     */   {
/*  875:1566 */     return new ANSICaseFragment();
/*  876:     */   }
/*  877:     */   
/*  878:     */   public String getNoColumnsInsertString()
/*  879:     */   {
/*  880:1576 */     return "values ( )";
/*  881:     */   }
/*  882:     */   
/*  883:     */   public String getLowercaseFunction()
/*  884:     */   {
/*  885:1586 */     return "lower";
/*  886:     */   }
/*  887:     */   
/*  888:     */   public String getCaseInsensitiveLike()
/*  889:     */   {
/*  890:1594 */     return "like";
/*  891:     */   }
/*  892:     */   
/*  893:     */   public boolean supportsCaseInsensitiveLike()
/*  894:     */   {
/*  895:1602 */     return false;
/*  896:     */   }
/*  897:     */   
/*  898:     */   public String transformSelectString(String select)
/*  899:     */   {
/*  900:1616 */     return select;
/*  901:     */   }
/*  902:     */   
/*  903:     */   public int getMaxAliasLength()
/*  904:     */   {
/*  905:1625 */     return 10;
/*  906:     */   }
/*  907:     */   
/*  908:     */   public String toBooleanValueString(boolean bool)
/*  909:     */   {
/*  910:1635 */     return bool ? "1" : "0";
/*  911:     */   }
/*  912:     */   
/*  913:     */   public char openQuote()
/*  914:     */   {
/*  915:1647 */     return '"';
/*  916:     */   }
/*  917:     */   
/*  918:     */   public char closeQuote()
/*  919:     */   {
/*  920:1656 */     return '"';
/*  921:     */   }
/*  922:     */   
/*  923:     */   public final String quote(String name)
/*  924:     */   {
/*  925:1671 */     if (name == null) {
/*  926:1672 */       return null;
/*  927:     */     }
/*  928:1675 */     if (name.charAt(0) == '`') {
/*  929:1676 */       return openQuote() + name.substring(1, name.length() - 1) + closeQuote();
/*  930:     */     }
/*  931:1679 */     return name;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public boolean hasAlterTable()
/*  935:     */   {
/*  936:1692 */     return true;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public boolean dropConstraints()
/*  940:     */   {
/*  941:1702 */     return true;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public boolean qualifyIndexName()
/*  945:     */   {
/*  946:1711 */     return true;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public boolean supportsUnique()
/*  950:     */   {
/*  951:1720 */     return true;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public boolean supportsUniqueConstraintInCreateAlterTable()
/*  955:     */   {
/*  956:1728 */     return true;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public String getAddColumnString()
/*  960:     */   {
/*  961:1737 */     throw new UnsupportedOperationException("No add column syntax supported by " + getClass().getName());
/*  962:     */   }
/*  963:     */   
/*  964:     */   public String getDropForeignKeyString()
/*  965:     */   {
/*  966:1741 */     return " drop constraint ";
/*  967:     */   }
/*  968:     */   
/*  969:     */   public String getTableTypeString()
/*  970:     */   {
/*  971:1746 */     return "";
/*  972:     */   }
/*  973:     */   
/*  974:     */   public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable, String[] primaryKey, boolean referencesPrimaryKey)
/*  975:     */   {
/*  976:1768 */     StringBuilder res = new StringBuilder(30);
/*  977:     */     
/*  978:1770 */     res.append(" add constraint ").append(constraintName).append(" foreign key (").append(StringHelper.join(", ", foreignKey)).append(") references ").append(referencedTable);
/*  979:1777 */     if (!referencesPrimaryKey) {
/*  980:1778 */       res.append(" (").append(StringHelper.join(", ", primaryKey)).append(')');
/*  981:     */     }
/*  982:1783 */     return res.toString();
/*  983:     */   }
/*  984:     */   
/*  985:     */   public String getAddPrimaryKeyConstraintString(String constraintName)
/*  986:     */   {
/*  987:1793 */     return " add constraint " + constraintName + " primary key ";
/*  988:     */   }
/*  989:     */   
/*  990:     */   public String getAddUniqueConstraintString(String constraintName)
/*  991:     */   {
/*  992:1803 */     return " add constraint " + constraintName + " unique ";
/*  993:     */   }
/*  994:     */   
/*  995:     */   public boolean hasSelfReferentialForeignKeyBug()
/*  996:     */   {
/*  997:1807 */     return false;
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public String getNullColumnString()
/* 1001:     */   {
/* 1002:1816 */     return "";
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public boolean supportsCommentOn()
/* 1006:     */   {
/* 1007:1820 */     return false;
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public String getTableComment(String comment)
/* 1011:     */   {
/* 1012:1824 */     return "";
/* 1013:     */   }
/* 1014:     */   
/* 1015:     */   public String getColumnComment(String comment)
/* 1016:     */   {
/* 1017:1828 */     return "";
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public boolean supportsIfExistsBeforeTableName()
/* 1021:     */   {
/* 1022:1832 */     return false;
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public boolean supportsIfExistsAfterTableName()
/* 1026:     */   {
/* 1027:1836 */     return false;
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public boolean supportsColumnCheck()
/* 1031:     */   {
/* 1032:1846 */     return true;
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   public boolean supportsTableCheck()
/* 1036:     */   {
/* 1037:1856 */     return true;
/* 1038:     */   }
/* 1039:     */   
/* 1040:     */   public boolean supportsCascadeDelete()
/* 1041:     */   {
/* 1042:1860 */     return true;
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public boolean supportsNotNullUnique()
/* 1046:     */   {
/* 1047:1864 */     return true;
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public String getCascadeConstraintsString()
/* 1051:     */   {
/* 1052:1873 */     return "";
/* 1053:     */   }
/* 1054:     */   
/* 1055:     */   public String getCrossJoinSeparator()
/* 1056:     */   {
/* 1057:1885 */     return " cross join ";
/* 1058:     */   }
/* 1059:     */   
/* 1060:     */   public ColumnAliasExtractor getColumnAliasExtractor()
/* 1061:     */   {
/* 1062:1889 */     return ColumnAliasExtractor.COLUMN_LABEL_EXTRACTOR;
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public boolean supportsEmptyInList()
/* 1066:     */   {
/* 1067:1904 */     return true;
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public boolean areStringComparisonsCaseInsensitive()
/* 1071:     */   {
/* 1072:1916 */     return false;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public boolean supportsRowValueConstructorSyntax()
/* 1076:     */   {
/* 1077:1932 */     return false;
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public boolean supportsRowValueConstructorSyntaxInInList()
/* 1081:     */   {
/* 1082:1946 */     return false;
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public boolean useInputStreamToInsertBlob()
/* 1086:     */   {
/* 1087:1957 */     return true;
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public boolean supportsParametersInInsertSelect()
/* 1091:     */   {
/* 1092:1968 */     return true;
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public boolean replaceResultVariableInOrderByClauseWithPosition()
/* 1096:     */   {
/* 1097:1982 */     return false;
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   public boolean requiresCastingOfParametersInSelectClause()
/* 1101:     */   {
/* 1102:1993 */     return false;
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   public boolean supportsResultSetPositionQueryMethodsOnForwardOnlyCursor()
/* 1106:     */   {
/* 1107:2012 */     return true;
/* 1108:     */   }
/* 1109:     */   
/* 1110:     */   public boolean supportsCircularCascadeDeleteConstraints()
/* 1111:     */   {
/* 1112:2024 */     return true;
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public boolean supportsSubselectAsInPredicateLHS()
/* 1116:     */   {
/* 1117:2038 */     return true;
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public boolean supportsExpectedLobUsagePattern()
/* 1121:     */   {
/* 1122:2056 */     return true;
/* 1123:     */   }
/* 1124:     */   
/* 1125:     */   public boolean supportsLobValueChangePropogation()
/* 1126:     */   {
/* 1127:2088 */     return true;
/* 1128:     */   }
/* 1129:     */   
/* 1130:     */   public boolean supportsUnboundedLobLocatorMaterialization()
/* 1131:     */   {
/* 1132:2105 */     return true;
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public boolean supportsSubqueryOnMutatingTable()
/* 1136:     */   {
/* 1137:2123 */     return true;
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   public boolean supportsExistsInSelect()
/* 1141:     */   {
/* 1142:2132 */     return true;
/* 1143:     */   }
/* 1144:     */   
/* 1145:     */   public boolean doesReadCommittedCauseWritersToBlockReaders()
/* 1146:     */   {
/* 1147:2142 */     return false;
/* 1148:     */   }
/* 1149:     */   
/* 1150:     */   public boolean doesRepeatableReadCauseReadersToBlockWriters()
/* 1151:     */   {
/* 1152:2152 */     return false;
/* 1153:     */   }
/* 1154:     */   
/* 1155:     */   public boolean supportsBindAsCallableArgument()
/* 1156:     */   {
/* 1157:2163 */     return true;
/* 1158:     */   }
/* 1159:     */   
/* 1160:     */   public boolean supportsTupleCounts()
/* 1161:     */   {
/* 1162:2172 */     return false;
/* 1163:     */   }
/* 1164:     */   
/* 1165:     */   public boolean supportsTupleDistinctCounts()
/* 1166:     */   {
/* 1167:2182 */     return true;
/* 1168:     */   }
/* 1169:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.Dialect
 * JD-Core Version:    0.7.0.1
 */
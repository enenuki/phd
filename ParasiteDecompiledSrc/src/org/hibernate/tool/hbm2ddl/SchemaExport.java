/*   1:    */ package org.hibernate.tool.hbm2ddl;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.io.InputStreamReader;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.io.Reader;
/*  11:    */ import java.io.Writer;
/*  12:    */ import java.sql.Connection;
/*  13:    */ import java.sql.SQLException;
/*  14:    */ import java.sql.SQLWarning;
/*  15:    */ import java.sql.Statement;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Properties;
/*  20:    */ import org.hibernate.HibernateException;
/*  21:    */ import org.hibernate.cfg.Configuration;
/*  22:    */ import org.hibernate.cfg.Environment;
/*  23:    */ import org.hibernate.cfg.NamingStrategy;
/*  24:    */ import org.hibernate.dialect.Dialect;
/*  25:    */ import org.hibernate.engine.jdbc.internal.FormatStyle;
/*  26:    */ import org.hibernate.engine.jdbc.internal.Formatter;
/*  27:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  28:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  29:    */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  30:    */ import org.hibernate.internal.CoreMessageLogger;
/*  31:    */ import org.hibernate.internal.util.ConfigHelper;
/*  32:    */ import org.hibernate.internal.util.ReflectHelper;
/*  33:    */ import org.hibernate.internal.util.StringHelper;
/*  34:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  35:    */ import org.hibernate.metamodel.relational.Database;
/*  36:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  37:    */ import org.hibernate.service.ServiceRegistry;
/*  38:    */ import org.hibernate.service.ServiceRegistryBuilder;
/*  39:    */ import org.hibernate.service.config.spi.ConfigurationService;
/*  40:    */ import org.hibernate.service.internal.StandardServiceRegistryImpl;
/*  41:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  42:    */ import org.jboss.logging.Logger;
/*  43:    */ 
/*  44:    */ public class SchemaExport
/*  45:    */ {
/*  46: 76 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SchemaExport.class.getName());
/*  47:    */   private static final String DEFAULT_IMPORT_FILE = "/import.sql";
/*  48:    */   private final ConnectionHelper connectionHelper;
/*  49:    */   private final SqlStatementLogger sqlStatementLogger;
/*  50:    */   private final SqlExceptionHelper sqlExceptionHelper;
/*  51:    */   private final String[] dropSQL;
/*  52:    */   private final String[] createSQL;
/*  53:    */   private final String importFiles;
/*  54:    */   
/*  55:    */   public static enum Type
/*  56:    */   {
/*  57: 80 */     CREATE,  DROP,  NONE,  BOTH;
/*  58:    */     
/*  59:    */     private Type() {}
/*  60:    */     
/*  61:    */     public boolean doCreate()
/*  62:    */     {
/*  63: 86 */       return (this == BOTH) || (this == CREATE);
/*  64:    */     }
/*  65:    */     
/*  66:    */     public boolean doDrop()
/*  67:    */     {
/*  68: 90 */       return (this == BOTH) || (this == DROP);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:101 */   private final List<Exception> exceptions = new ArrayList();
/*  73:    */   private Formatter formatter;
/*  74:104 */   private ImportSqlCommandExtractor importSqlCommandExtractor = ImportSqlCommandExtractorInitiator.DEFAULT_EXTRACTOR;
/*  75:106 */   private String outputFile = null;
/*  76:    */   private String delimiter;
/*  77:108 */   private boolean haltOnError = false;
/*  78:    */   
/*  79:    */   public SchemaExport(ServiceRegistry serviceRegistry, Configuration configuration)
/*  80:    */   {
/*  81:111 */     this.connectionHelper = new SuppliedConnectionProviderConnectionHelper((ConnectionProvider)serviceRegistry.getService(ConnectionProvider.class));
/*  82:    */     
/*  83:    */ 
/*  84:114 */     this.sqlStatementLogger = ((JdbcServices)serviceRegistry.getService(JdbcServices.class)).getSqlStatementLogger();
/*  85:115 */     this.formatter = (this.sqlStatementLogger.isFormat() ? FormatStyle.DDL : FormatStyle.NONE).getFormatter();
/*  86:116 */     this.sqlExceptionHelper = ((JdbcServices)serviceRegistry.getService(JdbcServices.class)).getSqlExceptionHelper();
/*  87:    */     
/*  88:118 */     this.importFiles = ConfigurationHelper.getString("hibernate.hbm2ddl.import_files", configuration.getProperties(), "/import.sql");
/*  89:    */     
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:124 */     Dialect dialect = ((JdbcServices)serviceRegistry.getService(JdbcServices.class)).getDialect();
/*  95:125 */     this.dropSQL = configuration.generateDropSchemaScript(dialect);
/*  96:126 */     this.createSQL = configuration.generateSchemaCreationScript(dialect);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public SchemaExport(MetadataImplementor metadata)
/* 100:    */   {
/* 101:130 */     ServiceRegistry serviceRegistry = metadata.getServiceRegistry();
/* 102:131 */     this.connectionHelper = new SuppliedConnectionProviderConnectionHelper((ConnectionProvider)serviceRegistry.getService(ConnectionProvider.class));
/* 103:    */     
/* 104:    */ 
/* 105:134 */     JdbcServices jdbcServices = (JdbcServices)serviceRegistry.getService(JdbcServices.class);
/* 106:135 */     this.sqlStatementLogger = jdbcServices.getSqlStatementLogger();
/* 107:136 */     this.formatter = (this.sqlStatementLogger.isFormat() ? FormatStyle.DDL : FormatStyle.NONE).getFormatter();
/* 108:137 */     this.sqlExceptionHelper = jdbcServices.getSqlExceptionHelper();
/* 109:    */     
/* 110:139 */     this.importFiles = ConfigurationHelper.getString("hibernate.hbm2ddl.import_files", ((ConfigurationService)serviceRegistry.getService(ConfigurationService.class)).getSettings(), "/import.sql");
/* 111:    */     
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:145 */     Dialect dialect = jdbcServices.getDialect();
/* 117:146 */     this.dropSQL = metadata.getDatabase().generateDropSchemaScript(dialect);
/* 118:147 */     this.createSQL = metadata.getDatabase().generateSchemaCreationScript(dialect);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public SchemaExport(Configuration configuration)
/* 122:    */   {
/* 123:157 */     this(configuration, configuration.getProperties());
/* 124:    */   }
/* 125:    */   
/* 126:    */   @Deprecated
/* 127:    */   public SchemaExport(Configuration configuration, Properties properties)
/* 128:    */     throws HibernateException
/* 129:    */   {
/* 130:172 */     Dialect dialect = Dialect.getDialect(properties);
/* 131:    */     
/* 132:174 */     Properties props = new Properties();
/* 133:175 */     props.putAll(dialect.getDefaultProperties());
/* 134:176 */     props.putAll(properties);
/* 135:177 */     this.connectionHelper = new ManagedProviderConnectionHelper(props);
/* 136:    */     
/* 137:179 */     this.sqlStatementLogger = new SqlStatementLogger(false, true);
/* 138:180 */     this.formatter = FormatStyle.DDL.getFormatter();
/* 139:181 */     this.sqlExceptionHelper = new SqlExceptionHelper();
/* 140:    */     
/* 141:183 */     this.importFiles = ConfigurationHelper.getString("hibernate.hbm2ddl.import_files", properties, "/import.sql");
/* 142:    */     
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:189 */     this.dropSQL = configuration.generateDropSchemaScript(dialect);
/* 148:190 */     this.createSQL = configuration.generateSchemaCreationScript(dialect);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public SchemaExport(Configuration configuration, Connection connection)
/* 152:    */     throws HibernateException
/* 153:    */   {
/* 154:201 */     this.connectionHelper = new SuppliedConnectionHelper(connection);
/* 155:    */     
/* 156:203 */     this.sqlStatementLogger = new SqlStatementLogger(false, true);
/* 157:204 */     this.formatter = FormatStyle.DDL.getFormatter();
/* 158:205 */     this.sqlExceptionHelper = new SqlExceptionHelper();
/* 159:    */     
/* 160:207 */     this.importFiles = ConfigurationHelper.getString("hibernate.hbm2ddl.import_files", configuration.getProperties(), "/import.sql");
/* 161:    */     
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:213 */     Dialect dialect = Dialect.getDialect(configuration.getProperties());
/* 167:214 */     this.dropSQL = configuration.generateDropSchemaScript(dialect);
/* 168:215 */     this.createSQL = configuration.generateSchemaCreationScript(dialect);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public SchemaExport(ConnectionHelper connectionHelper, String[] dropSql, String[] createSql)
/* 172:    */   {
/* 173:222 */     this.connectionHelper = connectionHelper;
/* 174:223 */     this.dropSQL = dropSql;
/* 175:224 */     this.createSQL = createSql;
/* 176:225 */     this.importFiles = "";
/* 177:226 */     this.sqlStatementLogger = new SqlStatementLogger(false, true);
/* 178:227 */     this.sqlExceptionHelper = new SqlExceptionHelper();
/* 179:228 */     this.formatter = FormatStyle.DDL.getFormatter();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public SchemaExport setOutputFile(String filename)
/* 183:    */   {
/* 184:238 */     this.outputFile = filename;
/* 185:239 */     return this;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public SchemaExport setDelimiter(String delimiter)
/* 189:    */   {
/* 190:249 */     this.delimiter = delimiter;
/* 191:250 */     return this;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public SchemaExport setFormat(boolean format)
/* 195:    */   {
/* 196:260 */     this.formatter = (format ? FormatStyle.DDL : FormatStyle.NONE).getFormatter();
/* 197:261 */     return this;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public SchemaExport setImportSqlCommandExtractor(ImportSqlCommandExtractor importSqlCommandExtractor)
/* 201:    */   {
/* 202:271 */     this.importSqlCommandExtractor = importSqlCommandExtractor;
/* 203:272 */     return this;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public SchemaExport setHaltOnError(boolean haltOnError)
/* 207:    */   {
/* 208:282 */     this.haltOnError = haltOnError;
/* 209:283 */     return this;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void create(boolean script, boolean export)
/* 213:    */   {
/* 214:294 */     create(Target.interpret(script, export));
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void create(Target output)
/* 218:    */   {
/* 219:305 */     execute(output, Type.BOTH);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void drop(boolean script, boolean export)
/* 223:    */   {
/* 224:315 */     drop(Target.interpret(script, export));
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void drop(Target output)
/* 228:    */   {
/* 229:319 */     execute(output, Type.DROP);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void execute(boolean script, boolean export, boolean justDrop, boolean justCreate)
/* 233:    */   {
/* 234:323 */     execute(Target.interpret(script, export), interpretType(justDrop, justCreate));
/* 235:    */   }
/* 236:    */   
/* 237:    */   private Type interpretType(boolean justDrop, boolean justCreate)
/* 238:    */   {
/* 239:327 */     if (justDrop) {
/* 240:328 */       return Type.DROP;
/* 241:    */     }
/* 242:330 */     if (justCreate) {
/* 243:331 */       return Type.CREATE;
/* 244:    */     }
/* 245:334 */     return Type.BOTH;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void execute(Target output, Type type)
/* 249:    */   {
/* 250:339 */     if ((output == Target.NONE) || (type == Type.NONE)) {
/* 251:340 */       return;
/* 252:    */     }
/* 253:342 */     this.exceptions.clear();
/* 254:    */     
/* 255:344 */     LOG.runningHbm2ddlSchemaExport();
/* 256:    */     
/* 257:346 */     List<NamedReader> importFileReaders = new ArrayList();
/* 258:347 */     for (String currentFile : this.importFiles.split(",")) {
/* 259:    */       try
/* 260:    */       {
/* 261:349 */         String resourceName = currentFile.trim();
/* 262:350 */         InputStream stream = ConfigHelper.getResourceAsStream(resourceName);
/* 263:351 */         importFileReaders.add(new NamedReader(resourceName, stream));
/* 264:    */       }
/* 265:    */       catch (HibernateException e)
/* 266:    */       {
/* 267:354 */         LOG.debugf("Import file not found: %s", currentFile);
/* 268:    */       }
/* 269:    */     }
/* 270:358 */     List<Exporter> exporters = new ArrayList();
/* 271:    */     try
/* 272:    */     {
/* 273:361 */       if (output.doScript()) {
/* 274:362 */         exporters.add(new ScriptExporter());
/* 275:    */       }
/* 276:364 */       if (this.outputFile != null) {
/* 277:365 */         exporters.add(new FileExporter(this.outputFile));
/* 278:    */       }
/* 279:367 */       if (output.doExport()) {
/* 280:368 */         exporters.add(new DatabaseExporter(this.connectionHelper, this.sqlExceptionHelper));
/* 281:    */       }
/* 282:372 */       if (type.doDrop()) {
/* 283:373 */         perform(this.dropSQL, exporters);
/* 284:    */       }
/* 285:375 */       if (type.doCreate())
/* 286:    */       {
/* 287:376 */         perform(this.createSQL, exporters);
/* 288:377 */         if (!importFileReaders.isEmpty()) {
/* 289:378 */           for (NamedReader namedReader : importFileReaders) {
/* 290:379 */             importScript(namedReader, exporters);
/* 291:    */           }
/* 292:    */         }
/* 293:    */       }
/* 294:    */     }
/* 295:    */     catch (Exception e)
/* 296:    */     {
/* 297:    */       Iterator i$;
/* 298:    */       Exporter exporter;
/* 299:    */       Iterator i$;
/* 300:    */       NamedReader namedReader;
/* 301:385 */       this.exceptions.add(e);
/* 302:386 */       LOG.schemaExportUnsuccessful(e);
/* 303:    */     }
/* 304:    */     finally
/* 305:    */     {
/* 306:    */       Iterator i$;
/* 307:    */       Exporter exporter;
/* 308:    */       Iterator i$;
/* 309:    */       NamedReader namedReader;
/* 310:390 */       for (Exporter exporter : exporters) {
/* 311:    */         try
/* 312:    */         {
/* 313:392 */           exporter.release();
/* 314:    */         }
/* 315:    */         catch (Exception ignore) {}
/* 316:    */       }
/* 317:399 */       for (NamedReader namedReader : importFileReaders) {
/* 318:    */         try
/* 319:    */         {
/* 320:401 */           namedReader.getReader().close();
/* 321:    */         }
/* 322:    */         catch (Exception ignore) {}
/* 323:    */       }
/* 324:406 */       LOG.schemaExportComplete();
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   private void perform(String[] sqlCommands, List<Exporter> exporters)
/* 329:    */   {
/* 330:    */     String sqlCommand;
/* 331:    */     String formatted;
/* 332:411 */     for (sqlCommand : sqlCommands)
/* 333:    */     {
/* 334:412 */       formatted = this.formatter.format(sqlCommand);
/* 335:413 */       if (this.delimiter != null) {
/* 336:414 */         formatted = formatted + this.delimiter;
/* 337:    */       }
/* 338:416 */       this.sqlStatementLogger.logStatement(sqlCommand, this.formatter);
/* 339:417 */       for (Exporter exporter : exporters) {
/* 340:    */         try
/* 341:    */         {
/* 342:419 */           exporter.export(formatted);
/* 343:    */         }
/* 344:    */         catch (Exception e)
/* 345:    */         {
/* 346:422 */           if (this.haltOnError) {
/* 347:423 */             throw new HibernateException("Error during DDL export", e);
/* 348:    */           }
/* 349:425 */           this.exceptions.add(e);
/* 350:426 */           LOG.unsuccessfulCreate(sqlCommand);
/* 351:427 */           LOG.error(e.getMessage());
/* 352:    */         }
/* 353:    */       }
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   private void importScript(NamedReader namedReader, List<Exporter> exporters)
/* 358:    */     throws Exception
/* 359:    */   {
/* 360:434 */     BufferedReader reader = new BufferedReader(namedReader.getReader());
/* 361:435 */     String[] statements = this.importSqlCommandExtractor.extractCommands(reader);
/* 362:436 */     if (statements != null) {
/* 363:437 */       for (String statement : statements) {
/* 364:438 */         if (statement != null)
/* 365:    */         {
/* 366:439 */           String trimmedSql = statement.trim();
/* 367:440 */           if (trimmedSql.endsWith(";")) {
/* 368:441 */             trimmedSql = trimmedSql.substring(0, statement.length() - 1);
/* 369:    */           }
/* 370:443 */           if (!StringHelper.isEmpty(trimmedSql)) {
/* 371:    */             try
/* 372:    */             {
/* 373:445 */               for (Exporter exporter : exporters) {
/* 374:446 */                 if (exporter.acceptsImportScripts()) {
/* 375:447 */                   exporter.export(trimmedSql);
/* 376:    */                 }
/* 377:    */               }
/* 378:    */             }
/* 379:    */             catch (Exception e)
/* 380:    */             {
/* 381:452 */               throw new ImportScriptException("Error during statement execution (file: '" + namedReader.getName() + "'): " + trimmedSql, e);
/* 382:    */             }
/* 383:    */           }
/* 384:    */         }
/* 385:    */       }
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   private static class NamedReader
/* 390:    */   {
/* 391:    */     private final Reader reader;
/* 392:    */     private final String name;
/* 393:    */     
/* 394:    */     public NamedReader(String name, InputStream stream)
/* 395:    */     {
/* 396:465 */       this.name = name;
/* 397:466 */       this.reader = new InputStreamReader(stream);
/* 398:    */     }
/* 399:    */     
/* 400:    */     public Reader getReader()
/* 401:    */     {
/* 402:470 */       return this.reader;
/* 403:    */     }
/* 404:    */     
/* 405:    */     public String getName()
/* 406:    */     {
/* 407:474 */       return this.name;
/* 408:    */     }
/* 409:    */   }
/* 410:    */   
/* 411:    */   private void execute(boolean script, boolean export, Writer fileOutput, Statement statement, String sql)
/* 412:    */     throws IOException, SQLException
/* 413:    */   {
/* 414:480 */     SqlExceptionHelper sqlExceptionHelper = new SqlExceptionHelper();
/* 415:    */     
/* 416:482 */     String formatted = this.formatter.format(sql);
/* 417:483 */     if (this.delimiter != null) {
/* 418:483 */       formatted = formatted + this.delimiter;
/* 419:    */     }
/* 420:484 */     if (script) {
/* 421:484 */       System.out.println(formatted);
/* 422:    */     }
/* 423:485 */     LOG.debug(formatted);
/* 424:486 */     if (this.outputFile != null) {
/* 425:487 */       fileOutput.write(formatted + "\n");
/* 426:    */     }
/* 427:489 */     if (export)
/* 428:    */     {
/* 429:491 */       statement.executeUpdate(sql);
/* 430:    */       try
/* 431:    */       {
/* 432:493 */         SQLWarning warnings = statement.getWarnings();
/* 433:494 */         if (warnings != null) {
/* 434:495 */           sqlExceptionHelper.logAndClearWarnings(this.connectionHelper.getConnection());
/* 435:    */         }
/* 436:    */       }
/* 437:    */       catch (SQLException sqle)
/* 438:    */       {
/* 439:499 */         LOG.unableToLogSqlWarnings(sqle);
/* 440:    */       }
/* 441:    */     }
/* 442:    */   }
/* 443:    */   
/* 444:    */   private static StandardServiceRegistryImpl createServiceRegistry(Properties properties)
/* 445:    */   {
/* 446:506 */     Environment.verifyProperties(properties);
/* 447:507 */     ConfigurationHelper.resolvePlaceHolders(properties);
/* 448:508 */     return (StandardServiceRegistryImpl)new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
/* 449:    */   }
/* 450:    */   
/* 451:    */   public static void main(String[] args)
/* 452:    */   {
/* 453:    */     try
/* 454:    */     {
/* 455:513 */       Configuration cfg = new Configuration();
/* 456:    */       
/* 457:515 */       boolean script = true;
/* 458:516 */       boolean drop = false;
/* 459:517 */       boolean create = false;
/* 460:518 */       boolean halt = false;
/* 461:519 */       boolean export = true;
/* 462:520 */       String outFile = null;
/* 463:521 */       String importFile = "/import.sql";
/* 464:522 */       String propFile = null;
/* 465:523 */       boolean format = false;
/* 466:524 */       String delim = null;
/* 467:526 */       for (int i = 0; i < args.length; i++) {
/* 468:527 */         if (args[i].startsWith("--"))
/* 469:    */         {
/* 470:528 */           if (args[i].equals("--quiet")) {
/* 471:529 */             script = false;
/* 472:531 */           } else if (args[i].equals("--drop")) {
/* 473:532 */             drop = true;
/* 474:534 */           } else if (args[i].equals("--create")) {
/* 475:535 */             create = true;
/* 476:537 */           } else if (args[i].equals("--haltonerror")) {
/* 477:538 */             halt = true;
/* 478:540 */           } else if (args[i].equals("--text")) {
/* 479:541 */             export = false;
/* 480:543 */           } else if (args[i].startsWith("--output=")) {
/* 481:544 */             outFile = args[i].substring(9);
/* 482:546 */           } else if (args[i].startsWith("--import=")) {
/* 483:547 */             importFile = args[i].substring(9);
/* 484:549 */           } else if (args[i].startsWith("--properties=")) {
/* 485:550 */             propFile = args[i].substring(13);
/* 486:552 */           } else if (args[i].equals("--format")) {
/* 487:553 */             format = true;
/* 488:555 */           } else if (args[i].startsWith("--delimiter=")) {
/* 489:556 */             delim = args[i].substring(12);
/* 490:558 */           } else if (args[i].startsWith("--config=")) {
/* 491:559 */             cfg.configure(args[i].substring(9));
/* 492:561 */           } else if (args[i].startsWith("--naming=")) {
/* 493:562 */             cfg.setNamingStrategy((NamingStrategy)ReflectHelper.classForName(args[i].substring(9)).newInstance());
/* 494:    */           }
/* 495:    */         }
/* 496:    */         else
/* 497:    */         {
/* 498:569 */           String filename = args[i];
/* 499:570 */           if (filename.endsWith(".jar")) {
/* 500:571 */             cfg.addJar(new File(filename));
/* 501:    */           } else {
/* 502:574 */             cfg.addFile(filename);
/* 503:    */           }
/* 504:    */         }
/* 505:    */       }
/* 506:580 */       if (propFile != null)
/* 507:    */       {
/* 508:581 */         Properties props = new Properties();
/* 509:582 */         props.putAll(cfg.getProperties());
/* 510:583 */         props.load(new FileInputStream(propFile));
/* 511:584 */         cfg.setProperties(props);
/* 512:    */       }
/* 513:587 */       if (importFile != null) {
/* 514:588 */         cfg.setProperty("hibernate.hbm2ddl.import_files", importFile);
/* 515:    */       }
/* 516:591 */       StandardServiceRegistryImpl serviceRegistry = createServiceRegistry(cfg.getProperties());
/* 517:    */       try
/* 518:    */       {
/* 519:593 */         SchemaExport se = new SchemaExport(serviceRegistry, cfg).setHaltOnError(halt).setOutputFile(outFile).setDelimiter(delim).setImportSqlCommandExtractor((ImportSqlCommandExtractor)serviceRegistry.getService(ImportSqlCommandExtractor.class));
/* 520:598 */         if (format) {
/* 521:599 */           se.setFormat(true);
/* 522:    */         }
/* 523:601 */         se.execute(script, export, drop, create);
/* 524:    */       }
/* 525:    */       finally
/* 526:    */       {
/* 527:604 */         serviceRegistry.destroy();
/* 528:    */       }
/* 529:    */     }
/* 530:    */     catch (Exception e)
/* 531:    */     {
/* 532:608 */       LOG.unableToCreateSchema(e);
/* 533:609 */       e.printStackTrace();
/* 534:    */     }
/* 535:    */   }
/* 536:    */   
/* 537:    */   public List getExceptions()
/* 538:    */   {
/* 539:619 */     return this.exceptions;
/* 540:    */   }
/* 541:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.SchemaExport
 * JD-Core Version:    0.7.0.1
 */
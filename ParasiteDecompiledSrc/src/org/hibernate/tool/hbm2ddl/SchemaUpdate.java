/*   1:    */ package org.hibernate.tool.hbm2ddl;
/*   2:    */ 
/*   3:    */ import java.io.FileInputStream;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.io.Writer;
/*   7:    */ import java.sql.Connection;
/*   8:    */ import java.sql.SQLException;
/*   9:    */ import java.sql.Statement;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Properties;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.JDBCException;
/*  15:    */ import org.hibernate.cfg.Configuration;
/*  16:    */ import org.hibernate.cfg.Environment;
/*  17:    */ import org.hibernate.cfg.NamingStrategy;
/*  18:    */ import org.hibernate.dialect.Dialect;
/*  19:    */ import org.hibernate.engine.jdbc.internal.FormatStyle;
/*  20:    */ import org.hibernate.engine.jdbc.internal.Formatter;
/*  21:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  22:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  23:    */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  24:    */ import org.hibernate.internal.CoreMessageLogger;
/*  25:    */ import org.hibernate.internal.util.ReflectHelper;
/*  26:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  27:    */ import org.hibernate.service.ServiceRegistry;
/*  28:    */ import org.hibernate.service.ServiceRegistryBuilder;
/*  29:    */ import org.hibernate.service.internal.StandardServiceRegistryImpl;
/*  30:    */ import org.jboss.logging.Logger;
/*  31:    */ 
/*  32:    */ public class SchemaUpdate
/*  33:    */ {
/*  34: 63 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SchemaUpdate.class.getName());
/*  35:    */   private final Configuration configuration;
/*  36:    */   private final ConnectionHelper connectionHelper;
/*  37:    */   private final SqlStatementLogger sqlStatementLogger;
/*  38:    */   private final SqlExceptionHelper sqlExceptionHelper;
/*  39:    */   private final Dialect dialect;
/*  40: 71 */   private final List<Exception> exceptions = new ArrayList();
/*  41:    */   private Formatter formatter;
/*  42: 75 */   private boolean haltOnError = false;
/*  43: 76 */   private boolean format = true;
/*  44: 77 */   private String outputFile = null;
/*  45:    */   private String delimiter;
/*  46:    */   
/*  47:    */   public SchemaUpdate(Configuration cfg)
/*  48:    */     throws HibernateException
/*  49:    */   {
/*  50: 81 */     this(cfg, cfg.getProperties());
/*  51:    */   }
/*  52:    */   
/*  53:    */   public SchemaUpdate(Configuration configuration, Properties properties)
/*  54:    */     throws HibernateException
/*  55:    */   {
/*  56: 85 */     this.configuration = configuration;
/*  57: 86 */     this.dialect = Dialect.getDialect(properties);
/*  58:    */     
/*  59: 88 */     Properties props = new Properties();
/*  60: 89 */     props.putAll(this.dialect.getDefaultProperties());
/*  61: 90 */     props.putAll(properties);
/*  62: 91 */     this.connectionHelper = new ManagedProviderConnectionHelper(props);
/*  63:    */     
/*  64: 93 */     this.sqlExceptionHelper = new SqlExceptionHelper();
/*  65: 94 */     this.sqlStatementLogger = new SqlStatementLogger(false, true);
/*  66: 95 */     this.formatter = FormatStyle.DDL.getFormatter();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public SchemaUpdate(ServiceRegistry serviceRegistry, Configuration cfg)
/*  70:    */     throws HibernateException
/*  71:    */   {
/*  72: 99 */     this.configuration = cfg;
/*  73:    */     
/*  74:101 */     JdbcServices jdbcServices = (JdbcServices)serviceRegistry.getService(JdbcServices.class);
/*  75:102 */     this.dialect = jdbcServices.getDialect();
/*  76:103 */     this.connectionHelper = new SuppliedConnectionProviderConnectionHelper(jdbcServices.getConnectionProvider());
/*  77:    */     
/*  78:105 */     this.sqlExceptionHelper = new SqlExceptionHelper();
/*  79:106 */     this.sqlStatementLogger = jdbcServices.getSqlStatementLogger();
/*  80:107 */     this.formatter = (this.sqlStatementLogger.isFormat() ? FormatStyle.DDL : FormatStyle.NONE).getFormatter();
/*  81:    */   }
/*  82:    */   
/*  83:    */   private static StandardServiceRegistryImpl createServiceRegistry(Properties properties)
/*  84:    */   {
/*  85:111 */     Environment.verifyProperties(properties);
/*  86:112 */     ConfigurationHelper.resolvePlaceHolders(properties);
/*  87:113 */     return (StandardServiceRegistryImpl)new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void main(String[] args)
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:118 */       Configuration cfg = new Configuration();
/*  95:    */       
/*  96:120 */       boolean script = true;
/*  97:    */       
/*  98:122 */       boolean doUpdate = true;
/*  99:123 */       String propFile = null;
/* 100:125 */       for (int i = 0; i < args.length; i++) {
/* 101:126 */         if (args[i].startsWith("--"))
/* 102:    */         {
/* 103:127 */           if (args[i].equals("--quiet")) {
/* 104:128 */             script = false;
/* 105:130 */           } else if (args[i].startsWith("--properties=")) {
/* 106:131 */             propFile = args[i].substring(13);
/* 107:133 */           } else if (args[i].startsWith("--config=")) {
/* 108:134 */             cfg.configure(args[i].substring(9));
/* 109:136 */           } else if (args[i].startsWith("--text")) {
/* 110:137 */             doUpdate = false;
/* 111:139 */           } else if (args[i].startsWith("--naming=")) {
/* 112:140 */             cfg.setNamingStrategy((NamingStrategy)ReflectHelper.classForName(args[i].substring(9)).newInstance());
/* 113:    */           }
/* 114:    */         }
/* 115:    */         else {
/* 116:146 */           cfg.addFile(args[i]);
/* 117:    */         }
/* 118:    */       }
/* 119:151 */       if (propFile != null)
/* 120:    */       {
/* 121:152 */         Properties props = new Properties();
/* 122:153 */         props.putAll(cfg.getProperties());
/* 123:154 */         props.load(new FileInputStream(propFile));
/* 124:155 */         cfg.setProperties(props);
/* 125:    */       }
/* 126:158 */       StandardServiceRegistryImpl serviceRegistry = createServiceRegistry(cfg.getProperties());
/* 127:    */       try
/* 128:    */       {
/* 129:160 */         new SchemaUpdate(serviceRegistry, cfg).execute(script, doUpdate);
/* 130:    */       }
/* 131:    */       finally
/* 132:    */       {
/* 133:163 */         serviceRegistry.destroy();
/* 134:    */       }
/* 135:    */     }
/* 136:    */     catch (Exception e)
/* 137:    */     {
/* 138:167 */       LOG.unableToRunSchemaUpdate(e);
/* 139:168 */       e.printStackTrace();
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void execute(boolean script, boolean doUpdate)
/* 144:    */   {
/* 145:178 */     execute(Target.interpret(script, doUpdate));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void execute(Target target)
/* 149:    */   {
/* 150:182 */     LOG.runningHbm2ddlSchemaUpdate();
/* 151:    */     
/* 152:184 */     Connection connection = null;
/* 153:185 */     Statement stmt = null;
/* 154:186 */     Writer outputFileWriter = null;
/* 155:    */     
/* 156:188 */     this.exceptions.clear();
/* 157:    */     try
/* 158:    */     {
/* 159:    */       DatabaseMetadata meta;
/* 160:    */       try
/* 161:    */       {
/* 162:193 */         LOG.fetchingDatabaseMetadata();
/* 163:194 */         this.connectionHelper.prepare(true);
/* 164:195 */         connection = this.connectionHelper.getConnection();
/* 165:196 */         meta = new DatabaseMetadata(connection, this.dialect);
/* 166:197 */         stmt = connection.createStatement();
/* 167:    */       }
/* 168:    */       catch (SQLException sqle)
/* 169:    */       {
/* 170:200 */         this.exceptions.add(sqle);
/* 171:201 */         LOG.unableToGetDatabaseMetadata(sqle);
/* 172:202 */         throw sqle;
/* 173:    */       }
/* 174:205 */       LOG.updatingSchema();
/* 175:207 */       if (this.outputFile != null)
/* 176:    */       {
/* 177:208 */         LOG.writingGeneratedSchemaToFile(this.outputFile);
/* 178:209 */         outputFileWriter = new FileWriter(this.outputFile);
/* 179:    */       }
/* 180:212 */       String[] sqlStrings = this.configuration.generateSchemaUpdateScript(this.dialect, meta);
/* 181:213 */       for (String sql : sqlStrings)
/* 182:    */       {
/* 183:214 */         String formatted = this.formatter.format(sql);
/* 184:    */         try
/* 185:    */         {
/* 186:216 */           if (this.delimiter != null) {
/* 187:217 */             formatted = formatted + this.delimiter;
/* 188:    */           }
/* 189:219 */           if (target.doScript()) {
/* 190:220 */             System.out.println(formatted);
/* 191:    */           }
/* 192:222 */           if (this.outputFile != null) {
/* 193:223 */             outputFileWriter.write(formatted + "\n");
/* 194:    */           }
/* 195:225 */           if (target.doExport())
/* 196:    */           {
/* 197:226 */             LOG.debug(sql);
/* 198:227 */             stmt.executeUpdate(formatted);
/* 199:    */           }
/* 200:    */         }
/* 201:    */         catch (SQLException e)
/* 202:    */         {
/* 203:231 */           if (this.haltOnError) {
/* 204:232 */             throw new JDBCException("Error during DDL export", e);
/* 205:    */           }
/* 206:234 */           this.exceptions.add(e);
/* 207:235 */           LOG.unsuccessful(sql);
/* 208:236 */           LOG.error(e.getMessage());
/* 209:    */         }
/* 210:    */       }
/* 211:240 */       LOG.schemaUpdateComplete(); return;
/* 212:    */     }
/* 213:    */     catch (Exception e)
/* 214:    */     {
/* 215:244 */       this.exceptions.add(e);
/* 216:245 */       LOG.unableToCompleteSchemaUpdate(e);
/* 217:    */     }
/* 218:    */     finally
/* 219:    */     {
/* 220:    */       try
/* 221:    */       {
/* 222:250 */         if (stmt != null) {
/* 223:251 */           stmt.close();
/* 224:    */         }
/* 225:253 */         this.connectionHelper.release();
/* 226:    */       }
/* 227:    */       catch (Exception e)
/* 228:    */       {
/* 229:256 */         this.exceptions.add(e);
/* 230:257 */         LOG.unableToCloseConnection(e);
/* 231:    */       }
/* 232:    */       try
/* 233:    */       {
/* 234:260 */         if (outputFileWriter != null) {
/* 235:261 */           outputFileWriter.close();
/* 236:    */         }
/* 237:    */       }
/* 238:    */       catch (Exception e)
/* 239:    */       {
/* 240:265 */         this.exceptions.add(e);
/* 241:266 */         LOG.unableToCloseConnection(e);
/* 242:    */       }
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List getExceptions()
/* 247:    */   {
/* 248:277 */     return this.exceptions;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setHaltOnError(boolean haltOnError)
/* 252:    */   {
/* 253:281 */     this.haltOnError = haltOnError;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setFormat(boolean format)
/* 257:    */   {
/* 258:285 */     this.formatter = (format ? FormatStyle.DDL : FormatStyle.NONE).getFormatter();
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setOutputFile(String outputFile)
/* 262:    */   {
/* 263:289 */     this.outputFile = outputFile;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setDelimiter(String delimiter)
/* 267:    */   {
/* 268:293 */     this.delimiter = delimiter;
/* 269:    */   }
/* 270:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.SchemaUpdate
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.hibernate.tool.hbm2ddl;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.DatabaseMetaData;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.sql.Statement;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.dialect.Dialect;
/*  14:    */ import org.hibernate.exception.spi.SQLExceptionConverter;
/*  15:    */ import org.hibernate.internal.CoreMessageLogger;
/*  16:    */ import org.hibernate.internal.util.StringHelper;
/*  17:    */ import org.hibernate.mapping.Table;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ public class DatabaseMetadata
/*  21:    */ {
/*  22: 53 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DatabaseMetaData.class.getName());
/*  23: 55 */   private final Map tables = new HashMap();
/*  24: 56 */   private final Set sequences = new HashSet();
/*  25:    */   private final boolean extras;
/*  26:    */   private DatabaseMetaData meta;
/*  27:    */   private SQLExceptionConverter sqlExceptionConverter;
/*  28:    */   
/*  29:    */   public DatabaseMetadata(Connection connection, Dialect dialect)
/*  30:    */     throws SQLException
/*  31:    */   {
/*  32: 63 */     this(connection, dialect, true);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public DatabaseMetadata(Connection connection, Dialect dialect, boolean extras)
/*  36:    */     throws SQLException
/*  37:    */   {
/*  38: 67 */     this.sqlExceptionConverter = dialect.buildSQLExceptionConverter();
/*  39: 68 */     this.meta = connection.getMetaData();
/*  40: 69 */     this.extras = extras;
/*  41: 70 */     initSequences(connection, dialect);
/*  42:    */   }
/*  43:    */   
/*  44: 73 */   private static final String[] TYPES = { "TABLE", "VIEW" };
/*  45:    */   
/*  46:    */   /* Error */
/*  47:    */   public TableMetadata getTableMetadata(String name, String schema, String catalog, boolean isQuoted)
/*  48:    */     throws HibernateException
/*  49:    */   {
/*  50:    */     // Byte code:
/*  51:    */     //   0: aload_0
/*  52:    */     //   1: aload_3
/*  53:    */     //   2: aload_2
/*  54:    */     //   3: aload_1
/*  55:    */     //   4: invokespecial 15	org/hibernate/tool/hbm2ddl/DatabaseMetadata:identifier	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
/*  56:    */     //   7: astore 5
/*  57:    */     //   9: aload_0
/*  58:    */     //   10: getfield 5	org/hibernate/tool/hbm2ddl/DatabaseMetadata:tables	Ljava/util/Map;
/*  59:    */     //   13: aload 5
/*  60:    */     //   15: invokeinterface 16 2 0
/*  61:    */     //   20: checkcast 17	org/hibernate/tool/hbm2ddl/TableMetadata
/*  62:    */     //   23: astore 6
/*  63:    */     //   25: aload 6
/*  64:    */     //   27: ifnull +6 -> 33
/*  65:    */     //   30: aload 6
/*  66:    */     //   32: areturn
/*  67:    */     //   33: aconst_null
/*  68:    */     //   34: astore 7
/*  69:    */     //   36: iload 4
/*  70:    */     //   38: ifeq +35 -> 73
/*  71:    */     //   41: aload_0
/*  72:    */     //   42: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/*  73:    */     //   45: invokeinterface 18 1 0
/*  74:    */     //   50: ifeq +23 -> 73
/*  75:    */     //   53: aload_0
/*  76:    */     //   54: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/*  77:    */     //   57: aload_3
/*  78:    */     //   58: aload_2
/*  79:    */     //   59: aload_1
/*  80:    */     //   60: getstatic 19	org/hibernate/tool/hbm2ddl/DatabaseMetadata:TYPES	[Ljava/lang/String;
/*  81:    */     //   63: invokeinterface 20 5 0
/*  82:    */     //   68: astore 7
/*  83:    */     //   70: goto +146 -> 216
/*  84:    */     //   73: iload 4
/*  85:    */     //   75: ifeq +15 -> 90
/*  86:    */     //   78: aload_0
/*  87:    */     //   79: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/*  88:    */     //   82: invokeinterface 21 1 0
/*  89:    */     //   87: ifne +20 -> 107
/*  90:    */     //   90: iload 4
/*  91:    */     //   92: ifne +44 -> 136
/*  92:    */     //   95: aload_0
/*  93:    */     //   96: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/*  94:    */     //   99: invokeinterface 22 1 0
/*  95:    */     //   104: ifeq +32 -> 136
/*  96:    */     //   107: aload_0
/*  97:    */     //   108: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/*  98:    */     //   111: aload_3
/*  99:    */     //   112: invokestatic 23	org/hibernate/internal/util/StringHelper:toUpperCase	(Ljava/lang/String;)Ljava/lang/String;
/* 100:    */     //   115: aload_2
/* 101:    */     //   116: invokestatic 23	org/hibernate/internal/util/StringHelper:toUpperCase	(Ljava/lang/String;)Ljava/lang/String;
/* 102:    */     //   119: aload_1
/* 103:    */     //   120: invokestatic 23	org/hibernate/internal/util/StringHelper:toUpperCase	(Ljava/lang/String;)Ljava/lang/String;
/* 104:    */     //   123: getstatic 19	org/hibernate/tool/hbm2ddl/DatabaseMetadata:TYPES	[Ljava/lang/String;
/* 105:    */     //   126: invokeinterface 20 5 0
/* 106:    */     //   131: astore 7
/* 107:    */     //   133: goto +83 -> 216
/* 108:    */     //   136: iload 4
/* 109:    */     //   138: ifeq +15 -> 153
/* 110:    */     //   141: aload_0
/* 111:    */     //   142: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/* 112:    */     //   145: invokeinterface 24 1 0
/* 113:    */     //   150: ifne +20 -> 170
/* 114:    */     //   153: iload 4
/* 115:    */     //   155: ifne +44 -> 199
/* 116:    */     //   158: aload_0
/* 117:    */     //   159: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/* 118:    */     //   162: invokeinterface 25 1 0
/* 119:    */     //   167: ifeq +32 -> 199
/* 120:    */     //   170: aload_0
/* 121:    */     //   171: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/* 122:    */     //   174: aload_3
/* 123:    */     //   175: invokestatic 26	org/hibernate/internal/util/StringHelper:toLowerCase	(Ljava/lang/String;)Ljava/lang/String;
/* 124:    */     //   178: aload_2
/* 125:    */     //   179: invokestatic 26	org/hibernate/internal/util/StringHelper:toLowerCase	(Ljava/lang/String;)Ljava/lang/String;
/* 126:    */     //   182: aload_1
/* 127:    */     //   183: invokestatic 26	org/hibernate/internal/util/StringHelper:toLowerCase	(Ljava/lang/String;)Ljava/lang/String;
/* 128:    */     //   186: getstatic 19	org/hibernate/tool/hbm2ddl/DatabaseMetadata:TYPES	[Ljava/lang/String;
/* 129:    */     //   189: invokeinterface 20 5 0
/* 130:    */     //   194: astore 7
/* 131:    */     //   196: goto +20 -> 216
/* 132:    */     //   199: aload_0
/* 133:    */     //   200: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/* 134:    */     //   203: aload_3
/* 135:    */     //   204: aload_2
/* 136:    */     //   205: aload_1
/* 137:    */     //   206: getstatic 19	org/hibernate/tool/hbm2ddl/DatabaseMetadata:TYPES	[Ljava/lang/String;
/* 138:    */     //   209: invokeinterface 20 5 0
/* 139:    */     //   214: astore 7
/* 140:    */     //   216: aload 7
/* 141:    */     //   218: invokeinterface 27 1 0
/* 142:    */     //   223: ifeq +78 -> 301
/* 143:    */     //   226: aload 7
/* 144:    */     //   228: ldc 28
/* 145:    */     //   230: invokeinterface 29 2 0
/* 146:    */     //   235: astore 8
/* 147:    */     //   237: aload_1
/* 148:    */     //   238: aload 8
/* 149:    */     //   240: invokevirtual 30	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/* 150:    */     //   243: ifeq +55 -> 298
/* 151:    */     //   246: new 17	org/hibernate/tool/hbm2ddl/TableMetadata
/* 152:    */     //   249: dup
/* 153:    */     //   250: aload 7
/* 154:    */     //   252: aload_0
/* 155:    */     //   253: getfield 12	org/hibernate/tool/hbm2ddl/DatabaseMetadata:meta	Ljava/sql/DatabaseMetaData;
/* 156:    */     //   256: aload_0
/* 157:    */     //   257: getfield 13	org/hibernate/tool/hbm2ddl/DatabaseMetadata:extras	Z
/* 158:    */     //   260: invokespecial 31	org/hibernate/tool/hbm2ddl/TableMetadata:<init>	(Ljava/sql/ResultSet;Ljava/sql/DatabaseMetaData;Z)V
/* 159:    */     //   263: astore 6
/* 160:    */     //   265: aload_0
/* 161:    */     //   266: getfield 5	org/hibernate/tool/hbm2ddl/DatabaseMetadata:tables	Ljava/util/Map;
/* 162:    */     //   269: aload 5
/* 163:    */     //   271: aload 6
/* 164:    */     //   273: invokeinterface 32 3 0
/* 165:    */     //   278: pop
/* 166:    */     //   279: aload 6
/* 167:    */     //   281: astore 9
/* 168:    */     //   283: aload 7
/* 169:    */     //   285: ifnull +10 -> 295
/* 170:    */     //   288: aload 7
/* 171:    */     //   290: invokeinterface 33 1 0
/* 172:    */     //   295: aload 9
/* 173:    */     //   297: areturn
/* 174:    */     //   298: goto -82 -> 216
/* 175:    */     //   301: getstatic 34	org/hibernate/tool/hbm2ddl/DatabaseMetadata:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 176:    */     //   304: aload_1
/* 177:    */     //   305: invokeinterface 35 2 0
/* 178:    */     //   310: aconst_null
/* 179:    */     //   311: astore 8
/* 180:    */     //   313: aload 7
/* 181:    */     //   315: ifnull +10 -> 325
/* 182:    */     //   318: aload 7
/* 183:    */     //   320: invokeinterface 33 1 0
/* 184:    */     //   325: aload 8
/* 185:    */     //   327: areturn
/* 186:    */     //   328: astore 10
/* 187:    */     //   330: aload 7
/* 188:    */     //   332: ifnull +10 -> 342
/* 189:    */     //   335: aload 7
/* 190:    */     //   337: invokeinterface 33 1 0
/* 191:    */     //   342: aload 10
/* 192:    */     //   344: athrow
/* 193:    */     //   345: astore 7
/* 194:    */     //   347: new 37	org/hibernate/engine/jdbc/spi/SqlExceptionHelper
/* 195:    */     //   350: dup
/* 196:    */     //   351: aload_0
/* 197:    */     //   352: getfield 10	org/hibernate/tool/hbm2ddl/DatabaseMetadata:sqlExceptionConverter	Lorg/hibernate/exception/spi/SQLExceptionConverter;
/* 198:    */     //   355: invokespecial 38	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:<init>	(Lorg/hibernate/exception/spi/SQLExceptionConverter;)V
/* 199:    */     //   358: aload 7
/* 200:    */     //   360: new 39	java/lang/StringBuilder
/* 201:    */     //   363: dup
/* 202:    */     //   364: invokespecial 40	java/lang/StringBuilder:<init>	()V
/* 203:    */     //   367: ldc 41
/* 204:    */     //   369: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 205:    */     //   372: aload_1
/* 206:    */     //   373: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 207:    */     //   376: invokevirtual 43	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 208:    */     //   379: invokevirtual 44	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 209:    */     //   382: athrow
/* 210:    */     // Line number table:
/* 211:    */     //   Java source line #77	-> byte code offset #0
/* 212:    */     //   Java source line #78	-> byte code offset #9
/* 213:    */     //   Java source line #79	-> byte code offset #25
/* 214:    */     //   Java source line #80	-> byte code offset #30
/* 215:    */     //   Java source line #85	-> byte code offset #33
/* 216:    */     //   Java source line #87	-> byte code offset #36
/* 217:    */     //   Java source line #88	-> byte code offset #53
/* 218:    */     //   Java source line #89	-> byte code offset #73
/* 219:    */     //   Java source line #91	-> byte code offset #107
/* 220:    */     //   Java source line #98	-> byte code offset #136
/* 221:    */     //   Java source line #100	-> byte code offset #170
/* 222:    */     //   Java source line #108	-> byte code offset #199
/* 223:    */     //   Java source line #111	-> byte code offset #216
/* 224:    */     //   Java source line #112	-> byte code offset #226
/* 225:    */     //   Java source line #113	-> byte code offset #237
/* 226:    */     //   Java source line #114	-> byte code offset #246
/* 227:    */     //   Java source line #115	-> byte code offset #265
/* 228:    */     //   Java source line #116	-> byte code offset #279
/* 229:    */     //   Java source line #125	-> byte code offset #283
/* 230:    */     //   Java source line #118	-> byte code offset #298
/* 231:    */     //   Java source line #120	-> byte code offset #301
/* 232:    */     //   Java source line #121	-> byte code offset #310
/* 233:    */     //   Java source line #125	-> byte code offset #313
/* 234:    */     //   Java source line #128	-> byte code offset #345
/* 235:    */     //   Java source line #129	-> byte code offset #347
/* 236:    */     // Local variable table:
/* 237:    */     //   start	length	slot	name	signature
/* 238:    */     //   0	383	0	this	DatabaseMetadata
/* 239:    */     //   0	383	1	name	String
/* 240:    */     //   0	383	2	schema	String
/* 241:    */     //   0	383	3	catalog	String
/* 242:    */     //   0	383	4	isQuoted	boolean
/* 243:    */     //   7	263	5	identifier	Object
/* 244:    */     //   23	257	6	table	TableMetadata
/* 245:    */     //   34	302	7	rs	ResultSet
/* 246:    */     //   345	14	7	sqlException	SQLException
/* 247:    */     //   235	91	8	tableName	String
/* 248:    */     //   328	15	10	localObject1	Object
/* 249:    */     // Exception table:
/* 250:    */     //   from	to	target	type
/* 251:    */     //   36	283	328	finally
/* 252:    */     //   298	313	328	finally
/* 253:    */     //   328	330	328	finally
/* 254:    */     //   33	295	345	java/sql/SQLException
/* 255:    */     //   298	325	345	java/sql/SQLException
/* 256:    */     //   328	345	345	java/sql/SQLException
/* 257:    */   }
/* 258:    */   
/* 259:    */   private Object identifier(String catalog, String schema, String name)
/* 260:    */   {
/* 261:137 */     return Table.qualify(catalog, schema, name);
/* 262:    */   }
/* 263:    */   
/* 264:    */   private void initSequences(Connection connection, Dialect dialect)
/* 265:    */     throws SQLException
/* 266:    */   {
/* 267:141 */     if (dialect.supportsSequences())
/* 268:    */     {
/* 269:142 */       String sql = dialect.getQuerySequencesString();
/* 270:143 */       if (sql != null)
/* 271:    */       {
/* 272:145 */         Statement statement = null;
/* 273:146 */         ResultSet rs = null;
/* 274:    */         try
/* 275:    */         {
/* 276:148 */           statement = connection.createStatement();
/* 277:149 */           rs = statement.executeQuery(sql);
/* 278:151 */           while (rs.next()) {
/* 279:152 */             this.sequences.add(rs.getString(1).toLowerCase().trim());
/* 280:    */           }
/* 281:    */         }
/* 282:    */         finally
/* 283:    */         {
/* 284:156 */           if (rs != null) {
/* 285:156 */             rs.close();
/* 286:    */           }
/* 287:157 */           if (statement != null) {
/* 288:157 */             statement.close();
/* 289:    */           }
/* 290:    */         }
/* 291:    */       }
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean isSequence(Object key)
/* 296:    */   {
/* 297:165 */     if ((key instanceof String))
/* 298:    */     {
/* 299:166 */       String[] strings = StringHelper.split(".", (String)key);
/* 300:167 */       return this.sequences.contains(strings[(strings.length - 1)].toLowerCase());
/* 301:    */     }
/* 302:169 */     return false;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public boolean isTable(Object key)
/* 306:    */     throws HibernateException
/* 307:    */   {
/* 308:173 */     if ((key instanceof String))
/* 309:    */     {
/* 310:174 */       Table tbl = new Table((String)key);
/* 311:175 */       if (getTableMetadata(tbl.getName(), tbl.getSchema(), tbl.getCatalog(), tbl.isQuoted()) != null) {
/* 312:176 */         return true;
/* 313:    */       }
/* 314:178 */       String[] strings = StringHelper.split(".", (String)key);
/* 315:179 */       if (strings.length == 3)
/* 316:    */       {
/* 317:180 */         tbl = new Table(strings[2]);
/* 318:181 */         tbl.setCatalog(strings[0]);
/* 319:182 */         tbl.setSchema(strings[1]);
/* 320:183 */         return getTableMetadata(tbl.getName(), tbl.getSchema(), tbl.getCatalog(), tbl.isQuoted()) != null;
/* 321:    */       }
/* 322:184 */       if (strings.length == 2)
/* 323:    */       {
/* 324:185 */         tbl = new Table(strings[1]);
/* 325:186 */         tbl.setSchema(strings[0]);
/* 326:187 */         return getTableMetadata(tbl.getName(), tbl.getSchema(), tbl.getCatalog(), tbl.isQuoted()) != null;
/* 327:    */       }
/* 328:    */     }
/* 329:191 */     return false;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public String toString()
/* 333:    */   {
/* 334:196 */     return "DatabaseMetadata" + this.tables.keySet().toString() + this.sequences.toString();
/* 335:    */   }
/* 336:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.DatabaseMetadata
 * JD-Core Version:    0.7.0.1
 */
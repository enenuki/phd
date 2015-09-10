/*   1:    */ package org.hibernate.engine.transaction.internal.jta;
/*   2:    */ 
/*   3:    */ import javax.transaction.NotSupportedException;
/*   4:    */ import javax.transaction.SystemException;
/*   5:    */ import javax.transaction.TransactionManager;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*   8:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   9:    */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*  10:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  11:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  12:    */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.jdbc.WorkExecutorVisitable;
/*  15:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  16:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ public class JtaIsolationDelegate
/*  20:    */   implements IsolationDelegate
/*  21:    */ {
/*  22: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JtaIsolationDelegate.class.getName());
/*  23:    */   private final TransactionCoordinator transactionCoordinator;
/*  24:    */   
/*  25:    */   public JtaIsolationDelegate(TransactionCoordinator transactionCoordinator)
/*  26:    */   {
/*  27: 56 */     this.transactionCoordinator = transactionCoordinator;
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected TransactionManager transactionManager()
/*  31:    */   {
/*  32: 60 */     return this.transactionCoordinator.getTransactionContext().getTransactionEnvironment().getJtaPlatform().retrieveTransactionManager();
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected ConnectionProvider connectionProvider()
/*  36:    */   {
/*  37: 67 */     return this.transactionCoordinator.getTransactionContext().getTransactionEnvironment().getJdbcServices().getConnectionProvider();
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected SqlExceptionHelper sqlExceptionHelper()
/*  41:    */   {
/*  42: 74 */     return this.transactionCoordinator.getTransactionContext().getTransactionEnvironment().getJdbcServices().getSqlExceptionHelper();
/*  43:    */   }
/*  44:    */   
/*  45:    */   /* Error */
/*  46:    */   public <T> T delegateWork(WorkExecutorVisitable<T> work, boolean transacted)
/*  47:    */     throws HibernateException
/*  48:    */   {
/*  49:    */     // Byte code:
/*  50:    */     //   0: aload_0
/*  51:    */     //   1: invokevirtual 10	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:transactionManager	()Ljavax/transaction/TransactionManager;
/*  52:    */     //   4: astore_3
/*  53:    */     //   5: aload_3
/*  54:    */     //   6: invokeinterface 11 1 0
/*  55:    */     //   11: astore 4
/*  56:    */     //   13: getstatic 12	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/*  57:    */     //   16: ldc 13
/*  58:    */     //   18: aload 4
/*  59:    */     //   20: invokeinterface 14 3 0
/*  60:    */     //   25: iconst_0
/*  61:    */     //   26: istore 5
/*  62:    */     //   28: iload_2
/*  63:    */     //   29: ifeq +56 -> 85
/*  64:    */     //   32: aload_0
/*  65:    */     //   33: aload_1
/*  66:    */     //   34: aload_3
/*  67:    */     //   35: invokespecial 15	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:doTheWorkInNewTransaction	(Lorg/hibernate/jdbc/WorkExecutorVisitable;Ljavax/transaction/TransactionManager;)Ljava/lang/Object;
/*  68:    */     //   38: astore 6
/*  69:    */     //   40: aload_3
/*  70:    */     //   41: aload 4
/*  71:    */     //   43: invokeinterface 16 2 0
/*  72:    */     //   48: getstatic 12	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/*  73:    */     //   51: ldc 17
/*  74:    */     //   53: aload 4
/*  75:    */     //   55: invokeinterface 14 3 0
/*  76:    */     //   60: goto +22 -> 82
/*  77:    */     //   63: astore 7
/*  78:    */     //   65: iload 5
/*  79:    */     //   67: ifne +15 -> 82
/*  80:    */     //   70: new 19	org/hibernate/HibernateException
/*  81:    */     //   73: dup
/*  82:    */     //   74: ldc 20
/*  83:    */     //   76: aload 7
/*  84:    */     //   78: invokespecial 21	org/hibernate/HibernateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/*  85:    */     //   81: athrow
/*  86:    */     //   82: aload 6
/*  87:    */     //   84: areturn
/*  88:    */     //   85: aload_0
/*  89:    */     //   86: aload_1
/*  90:    */     //   87: invokespecial 22	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:doTheWorkInNoTransaction	(Lorg/hibernate/jdbc/WorkExecutorVisitable;)Ljava/lang/Object;
/*  91:    */     //   90: astore 6
/*  92:    */     //   92: aload_3
/*  93:    */     //   93: aload 4
/*  94:    */     //   95: invokeinterface 16 2 0
/*  95:    */     //   100: getstatic 12	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/*  96:    */     //   103: ldc 17
/*  97:    */     //   105: aload 4
/*  98:    */     //   107: invokeinterface 14 3 0
/*  99:    */     //   112: goto +22 -> 134
/* 100:    */     //   115: astore 7
/* 101:    */     //   117: iload 5
/* 102:    */     //   119: ifne +15 -> 134
/* 103:    */     //   122: new 19	org/hibernate/HibernateException
/* 104:    */     //   125: dup
/* 105:    */     //   126: ldc 20
/* 106:    */     //   128: aload 7
/* 107:    */     //   130: invokespecial 21	org/hibernate/HibernateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 108:    */     //   133: athrow
/* 109:    */     //   134: aload 6
/* 110:    */     //   136: areturn
/* 111:    */     //   137: astore 6
/* 112:    */     //   139: iconst_1
/* 113:    */     //   140: istore 5
/* 114:    */     //   142: aload 6
/* 115:    */     //   144: athrow
/* 116:    */     //   145: astore 8
/* 117:    */     //   147: aload_3
/* 118:    */     //   148: aload 4
/* 119:    */     //   150: invokeinterface 16 2 0
/* 120:    */     //   155: getstatic 12	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 121:    */     //   158: ldc 17
/* 122:    */     //   160: aload 4
/* 123:    */     //   162: invokeinterface 14 3 0
/* 124:    */     //   167: goto +22 -> 189
/* 125:    */     //   170: astore 9
/* 126:    */     //   172: iload 5
/* 127:    */     //   174: ifne +15 -> 189
/* 128:    */     //   177: new 19	org/hibernate/HibernateException
/* 129:    */     //   180: dup
/* 130:    */     //   181: ldc 20
/* 131:    */     //   183: aload 9
/* 132:    */     //   185: invokespecial 21	org/hibernate/HibernateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 133:    */     //   188: athrow
/* 134:    */     //   189: aload 8
/* 135:    */     //   191: athrow
/* 136:    */     //   192: astore 4
/* 137:    */     //   194: new 19	org/hibernate/HibernateException
/* 138:    */     //   197: dup
/* 139:    */     //   198: ldc 24
/* 140:    */     //   200: aload 4
/* 141:    */     //   202: invokespecial 21	org/hibernate/HibernateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 142:    */     //   205: athrow
/* 143:    */     // Line number table:
/* 144:    */     //   Java source line #82	-> byte code offset #0
/* 145:    */     //   Java source line #86	-> byte code offset #5
/* 146:    */     //   Java source line #87	-> byte code offset #13
/* 147:    */     //   Java source line #89	-> byte code offset #25
/* 148:    */     //   Java source line #92	-> byte code offset #28
/* 149:    */     //   Java source line #93	-> byte code offset #32
/* 150:    */     //   Java source line #105	-> byte code offset #40
/* 151:    */     //   Java source line #106	-> byte code offset #48
/* 152:    */     //   Java source line #114	-> byte code offset #60
/* 153:    */     //   Java source line #108	-> byte code offset #63
/* 154:    */     //   Java source line #110	-> byte code offset #65
/* 155:    */     //   Java source line #112	-> byte code offset #70
/* 156:    */     //   Java source line #114	-> byte code offset #82
/* 157:    */     //   Java source line #96	-> byte code offset #85
/* 158:    */     //   Java source line #105	-> byte code offset #92
/* 159:    */     //   Java source line #106	-> byte code offset #100
/* 160:    */     //   Java source line #114	-> byte code offset #112
/* 161:    */     //   Java source line #108	-> byte code offset #115
/* 162:    */     //   Java source line #110	-> byte code offset #117
/* 163:    */     //   Java source line #112	-> byte code offset #122
/* 164:    */     //   Java source line #114	-> byte code offset #134
/* 165:    */     //   Java source line #99	-> byte code offset #137
/* 166:    */     //   Java source line #100	-> byte code offset #139
/* 167:    */     //   Java source line #101	-> byte code offset #142
/* 168:    */     //   Java source line #104	-> byte code offset #145
/* 169:    */     //   Java source line #105	-> byte code offset #147
/* 170:    */     //   Java source line #106	-> byte code offset #155
/* 171:    */     //   Java source line #114	-> byte code offset #167
/* 172:    */     //   Java source line #108	-> byte code offset #170
/* 173:    */     //   Java source line #110	-> byte code offset #172
/* 174:    */     //   Java source line #112	-> byte code offset #177
/* 175:    */     //   Java source line #114	-> byte code offset #189
/* 176:    */     //   Java source line #117	-> byte code offset #192
/* 177:    */     //   Java source line #118	-> byte code offset #194
/* 178:    */     // Local variable table:
/* 179:    */     //   start	length	slot	name	signature
/* 180:    */     //   0	206	0	this	JtaIsolationDelegate
/* 181:    */     //   0	206	1	work	WorkExecutorVisitable<T>
/* 182:    */     //   0	206	2	transacted	boolean
/* 183:    */     //   4	144	3	transactionManager	TransactionManager
/* 184:    */     //   11	150	4	surroundingTransaction	javax.transaction.Transaction
/* 185:    */     //   192	9	4	e	SystemException
/* 186:    */     //   26	147	5	hadProblems	boolean
/* 187:    */     //   38	97	6	localObject1	Object
/* 188:    */     //   137	6	6	e	HibernateException
/* 189:    */     //   63	14	7	t	java.lang.Throwable
/* 190:    */     //   115	14	7	t	java.lang.Throwable
/* 191:    */     //   145	45	8	localObject2	Object
/* 192:    */     //   170	14	9	t	java.lang.Throwable
/* 193:    */     // Exception table:
/* 194:    */     //   from	to	target	type
/* 195:    */     //   40	60	63	java/lang/Throwable
/* 196:    */     //   92	112	115	java/lang/Throwable
/* 197:    */     //   28	40	137	org/hibernate/HibernateException
/* 198:    */     //   85	92	137	org/hibernate/HibernateException
/* 199:    */     //   28	40	145	finally
/* 200:    */     //   85	92	145	finally
/* 201:    */     //   137	147	145	finally
/* 202:    */     //   147	167	170	java/lang/Throwable
/* 203:    */     //   5	82	192	javax/transaction/SystemException
/* 204:    */     //   85	134	192	javax/transaction/SystemException
/* 205:    */     //   137	192	192	javax/transaction/SystemException
/* 206:    */   }
/* 207:    */   
/* 208:    */   private <T> T doTheWorkInNewTransaction(WorkExecutorVisitable<T> work, TransactionManager transactionManager)
/* 209:    */   {
/* 210:123 */     T result = null;
/* 211:    */     try
/* 212:    */     {
/* 213:126 */       transactionManager.begin();
/* 214:    */       try
/* 215:    */       {
/* 216:129 */         result = doTheWork(work);
/* 217:    */         
/* 218:131 */         transactionManager.commit();
/* 219:    */       }
/* 220:    */       catch (Exception e)
/* 221:    */       {
/* 222:    */         try
/* 223:    */         {
/* 224:135 */           transactionManager.rollback();
/* 225:    */         }
/* 226:    */         catch (Exception ignore)
/* 227:    */         {
/* 228:138 */           LOG.unableToRollbackIsolatedTransaction(e, ignore);
/* 229:    */         }
/* 230:140 */         throw new HibernateException("Could not apply work", e);
/* 231:    */       }
/* 232:    */     }
/* 233:    */     catch (SystemException e)
/* 234:    */     {
/* 235:144 */       throw new HibernateException("Unable to start isolated transaction", e);
/* 236:    */     }
/* 237:    */     catch (NotSupportedException e)
/* 238:    */     {
/* 239:147 */       throw new HibernateException("Unable to start isolated transaction", e);
/* 240:    */     }
/* 241:149 */     return result;
/* 242:    */   }
/* 243:    */   
/* 244:    */   private <T> T doTheWorkInNoTransaction(WorkExecutorVisitable<T> work)
/* 245:    */   {
/* 246:153 */     return doTheWork(work);
/* 247:    */   }
/* 248:    */   
/* 249:    */   /* Error */
/* 250:    */   private <T> T doTheWork(WorkExecutorVisitable<T> work)
/* 251:    */   {
/* 252:    */     // Byte code:
/* 253:    */     //   0: aload_0
/* 254:    */     //   1: invokevirtual 34	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:connectionProvider	()Lorg/hibernate/service/jdbc/connections/spi/ConnectionProvider;
/* 255:    */     //   4: invokeinterface 35 1 0
/* 256:    */     //   9: astore_2
/* 257:    */     //   10: aload_1
/* 258:    */     //   11: new 36	org/hibernate/jdbc/WorkExecutor
/* 259:    */     //   14: dup
/* 260:    */     //   15: invokespecial 37	org/hibernate/jdbc/WorkExecutor:<init>	()V
/* 261:    */     //   18: aload_2
/* 262:    */     //   19: invokeinterface 38 3 0
/* 263:    */     //   24: astore_3
/* 264:    */     //   25: aload_0
/* 265:    */     //   26: invokevirtual 34	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:connectionProvider	()Lorg/hibernate/service/jdbc/connections/spi/ConnectionProvider;
/* 266:    */     //   29: aload_2
/* 267:    */     //   30: invokeinterface 39 2 0
/* 268:    */     //   35: goto +15 -> 50
/* 269:    */     //   38: astore 4
/* 270:    */     //   40: getstatic 12	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 271:    */     //   43: aload 4
/* 272:    */     //   45: invokeinterface 40 2 0
/* 273:    */     //   50: aload_3
/* 274:    */     //   51: areturn
/* 275:    */     //   52: astore_3
/* 276:    */     //   53: aload_3
/* 277:    */     //   54: athrow
/* 278:    */     //   55: astore_3
/* 279:    */     //   56: new 19	org/hibernate/HibernateException
/* 280:    */     //   59: dup
/* 281:    */     //   60: ldc 41
/* 282:    */     //   62: aload_3
/* 283:    */     //   63: invokespecial 21	org/hibernate/HibernateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* 284:    */     //   66: athrow
/* 285:    */     //   67: astore 5
/* 286:    */     //   69: aload_0
/* 287:    */     //   70: invokevirtual 34	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:connectionProvider	()Lorg/hibernate/service/jdbc/connections/spi/ConnectionProvider;
/* 288:    */     //   73: aload_2
/* 289:    */     //   74: invokeinterface 39 2 0
/* 290:    */     //   79: goto +15 -> 94
/* 291:    */     //   82: astore 6
/* 292:    */     //   84: getstatic 12	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 293:    */     //   87: aload 6
/* 294:    */     //   89: invokeinterface 40 2 0
/* 295:    */     //   94: aload 5
/* 296:    */     //   96: athrow
/* 297:    */     //   97: astore_2
/* 298:    */     //   98: aload_0
/* 299:    */     //   99: invokevirtual 43	org/hibernate/engine/transaction/internal/jta/JtaIsolationDelegate:sqlExceptionHelper	()Lorg/hibernate/engine/jdbc/spi/SqlExceptionHelper;
/* 300:    */     //   102: aload_2
/* 301:    */     //   103: ldc 44
/* 302:    */     //   105: invokevirtual 45	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 303:    */     //   108: athrow
/* 304:    */     // Line number table:
/* 305:    */     //   Java source line #159	-> byte code offset #0
/* 306:    */     //   Java source line #162	-> byte code offset #10
/* 307:    */     //   Java source line #173	-> byte code offset #25
/* 308:    */     //   Java source line #177	-> byte code offset #35
/* 309:    */     //   Java source line #175	-> byte code offset #38
/* 310:    */     //   Java source line #176	-> byte code offset #40
/* 311:    */     //   Java source line #177	-> byte code offset #50
/* 312:    */     //   Java source line #164	-> byte code offset #52
/* 313:    */     //   Java source line #165	-> byte code offset #53
/* 314:    */     //   Java source line #167	-> byte code offset #55
/* 315:    */     //   Java source line #168	-> byte code offset #56
/* 316:    */     //   Java source line #171	-> byte code offset #67
/* 317:    */     //   Java source line #173	-> byte code offset #69
/* 318:    */     //   Java source line #177	-> byte code offset #79
/* 319:    */     //   Java source line #175	-> byte code offset #82
/* 320:    */     //   Java source line #176	-> byte code offset #84
/* 321:    */     //   Java source line #177	-> byte code offset #94
/* 322:    */     //   Java source line #180	-> byte code offset #97
/* 323:    */     //   Java source line #181	-> byte code offset #98
/* 324:    */     // Local variable table:
/* 325:    */     //   start	length	slot	name	signature
/* 326:    */     //   0	109	0	this	JtaIsolationDelegate
/* 327:    */     //   0	109	1	work	WorkExecutorVisitable<T>
/* 328:    */     //   9	65	2	connection	java.sql.Connection
/* 329:    */     //   97	6	2	e	java.sql.SQLException
/* 330:    */     //   52	2	3	e	HibernateException
/* 331:    */     //   55	8	3	e	Exception
/* 332:    */     //   38	6	4	ignore	java.lang.Throwable
/* 333:    */     //   67	28	5	localObject2	Object
/* 334:    */     //   82	6	6	ignore	java.lang.Throwable
/* 335:    */     // Exception table:
/* 336:    */     //   from	to	target	type
/* 337:    */     //   25	35	38	java/lang/Throwable
/* 338:    */     //   10	25	52	org/hibernate/HibernateException
/* 339:    */     //   10	25	55	java/lang/Exception
/* 340:    */     //   10	25	67	finally
/* 341:    */     //   52	69	67	finally
/* 342:    */     //   69	79	82	java/lang/Throwable
/* 343:    */     //   0	50	97	java/sql/SQLException
/* 344:    */     //   52	97	97	java/sql/SQLException
/* 345:    */   }
/* 346:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.jta.JtaIsolationDelegate
 * JD-Core Version:    0.7.0.1
 */
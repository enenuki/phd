/*  1:   */ package org.hibernate.engine.transaction.internal.jdbc;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  4:   */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  5:   */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  6:   */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  7:   */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*  8:   */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  9:   */ import org.hibernate.internal.CoreMessageLogger;
/* 10:   */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/* 11:   */ import org.jboss.logging.Logger;
/* 12:   */ 
/* 13:   */ public class JdbcIsolationDelegate
/* 14:   */   implements IsolationDelegate
/* 15:   */ {
/* 16:47 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JdbcIsolationDelegate.class.getName());
/* 17:   */   private final TransactionCoordinator transactionCoordinator;
/* 18:   */   
/* 19:   */   public JdbcIsolationDelegate(TransactionCoordinator transactionCoordinator)
/* 20:   */   {
/* 21:52 */     this.transactionCoordinator = transactionCoordinator;
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected ConnectionProvider connectionProvider()
/* 25:   */   {
/* 26:56 */     return this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().getJdbcServices().getConnectionProvider();
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected SqlExceptionHelper sqlExceptionHelper()
/* 30:   */   {
/* 31:60 */     return this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().getJdbcServices().getSqlExceptionHelper();
/* 32:   */   }
/* 33:   */   
/* 34:   */   /* Error */
/* 35:   */   public <T> T delegateWork(org.hibernate.jdbc.WorkExecutorVisitable<T> work, boolean transacted)
/* 36:   */     throws org.hibernate.HibernateException
/* 37:   */   {
/* 38:   */     // Byte code:
/* 39:   */     //   0: iconst_0
/* 40:   */     //   1: istore_3
/* 41:   */     //   2: aload_0
/* 42:   */     //   3: invokevirtual 8	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:connectionProvider	()Lorg/hibernate/service/jdbc/connections/spi/ConnectionProvider;
/* 43:   */     //   6: invokeinterface 9 1 0
/* 44:   */     //   11: astore 4
/* 45:   */     //   13: iload_2
/* 46:   */     //   14: ifeq +23 -> 37
/* 47:   */     //   17: aload 4
/* 48:   */     //   19: invokeinterface 10 1 0
/* 49:   */     //   24: ifeq +13 -> 37
/* 50:   */     //   27: iconst_1
/* 51:   */     //   28: istore_3
/* 52:   */     //   29: aload 4
/* 53:   */     //   31: iconst_0
/* 54:   */     //   32: invokeinterface 11 2 0
/* 55:   */     //   37: aload_1
/* 56:   */     //   38: new 12	org/hibernate/jdbc/WorkExecutor
/* 57:   */     //   41: dup
/* 58:   */     //   42: invokespecial 13	org/hibernate/jdbc/WorkExecutor:<init>	()V
/* 59:   */     //   45: aload 4
/* 60:   */     //   47: invokeinterface 14 3 0
/* 61:   */     //   52: astore 5
/* 62:   */     //   54: iload_2
/* 63:   */     //   55: ifeq +10 -> 65
/* 64:   */     //   58: aload 4
/* 65:   */     //   60: invokeinterface 15 1 0
/* 66:   */     //   65: aload 5
/* 67:   */     //   67: astore 6
/* 68:   */     //   69: iload_2
/* 69:   */     //   70: ifeq +30 -> 100
/* 70:   */     //   73: iload_3
/* 71:   */     //   74: ifeq +26 -> 100
/* 72:   */     //   77: aload 4
/* 73:   */     //   79: iconst_1
/* 74:   */     //   80: invokeinterface 11 2 0
/* 75:   */     //   85: goto +15 -> 100
/* 76:   */     //   88: astore 7
/* 77:   */     //   90: getstatic 17	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 78:   */     //   93: ldc 18
/* 79:   */     //   95: invokeinterface 19 2 0
/* 80:   */     //   100: aload_0
/* 81:   */     //   101: invokevirtual 8	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:connectionProvider	()Lorg/hibernate/service/jdbc/connections/spi/ConnectionProvider;
/* 82:   */     //   104: aload 4
/* 83:   */     //   106: invokeinterface 20 2 0
/* 84:   */     //   111: goto +15 -> 126
/* 85:   */     //   114: astore 7
/* 86:   */     //   116: getstatic 17	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 87:   */     //   119: aload 7
/* 88:   */     //   121: invokeinterface 21 2 0
/* 89:   */     //   126: aload 6
/* 90:   */     //   128: areturn
/* 91:   */     //   129: astore 5
/* 92:   */     //   131: iload_2
/* 93:   */     //   132: ifeq +20 -> 152
/* 94:   */     //   135: aload 4
/* 95:   */     //   137: invokeinterface 22 1 0
/* 96:   */     //   142: ifne +10 -> 152
/* 97:   */     //   145: aload 4
/* 98:   */     //   147: invokeinterface 23 1 0
/* 99:   */     //   152: goto +15 -> 167
/* :0:   */     //   155: astore 6
/* :1:   */     //   157: getstatic 17	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* :2:   */     //   160: aload 6
/* :3:   */     //   162: invokeinterface 24 2 0
/* :4:   */     //   167: aload 5
/* :5:   */     //   169: instanceof 25
/* :6:   */     //   172: ifeq +9 -> 181
/* :7:   */     //   175: aload 5
/* :8:   */     //   177: checkcast 25	org/hibernate/HibernateException
/* :9:   */     //   180: athrow
/* ;0:   */     //   181: aload 5
/* ;1:   */     //   183: instanceof 26
/* ;2:   */     //   186: ifeq +18 -> 204
/* ;3:   */     //   189: aload_0
/* ;4:   */     //   190: invokevirtual 27	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:sqlExceptionHelper	()Lorg/hibernate/engine/jdbc/spi/SqlExceptionHelper;
/* ;5:   */     //   193: aload 5
/* ;6:   */     //   195: checkcast 26	java/sql/SQLException
/* ;7:   */     //   198: ldc 28
/* ;8:   */     //   200: invokevirtual 29	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* ;9:   */     //   203: athrow
/* <0:   */     //   204: new 25	org/hibernate/HibernateException
/* <1:   */     //   207: dup
/* <2:   */     //   208: ldc 28
/* <3:   */     //   210: aload 5
/* <4:   */     //   212: invokespecial 30	org/hibernate/HibernateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
/* <5:   */     //   215: athrow
/* <6:   */     //   216: astore 8
/* <7:   */     //   218: iload_2
/* <8:   */     //   219: ifeq +30 -> 249
/* <9:   */     //   222: iload_3
/* =0:   */     //   223: ifeq +26 -> 249
/* =1:   */     //   226: aload 4
/* =2:   */     //   228: iconst_1
/* =3:   */     //   229: invokeinterface 11 2 0
/* =4:   */     //   234: goto +15 -> 249
/* =5:   */     //   237: astore 9
/* =6:   */     //   239: getstatic 17	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* =7:   */     //   242: ldc 18
/* =8:   */     //   244: invokeinterface 19 2 0
/* =9:   */     //   249: aload_0
/* >0:   */     //   250: invokevirtual 8	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:connectionProvider	()Lorg/hibernate/service/jdbc/connections/spi/ConnectionProvider;
/* >1:   */     //   253: aload 4
/* >2:   */     //   255: invokeinterface 20 2 0
/* >3:   */     //   260: goto +15 -> 275
/* >4:   */     //   263: astore 9
/* >5:   */     //   265: getstatic 17	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* >6:   */     //   268: aload 9
/* >7:   */     //   270: invokeinterface 21 2 0
/* >8:   */     //   275: aload 8
/* >9:   */     //   277: athrow
/* ?0:   */     //   278: astore 4
/* ?1:   */     //   280: aload_0
/* ?2:   */     //   281: invokevirtual 27	org/hibernate/engine/transaction/internal/jdbc/JdbcIsolationDelegate:sqlExceptionHelper	()Lorg/hibernate/engine/jdbc/spi/SqlExceptionHelper;
/* ?3:   */     //   284: aload 4
/* ?4:   */     //   286: ldc 31
/* ?5:   */     //   288: invokevirtual 29	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* ?6:   */     //   291: athrow
/* ?7:   */     // Line number table:
/* ?8:   */     //   Java source line #65	-> byte code offset #0
/* ?9:   */     //   Java source line #68	-> byte code offset #2
/* @0:   */     //   Java source line #70	-> byte code offset #13
/* @1:   */     //   Java source line #71	-> byte code offset #17
/* @2:   */     //   Java source line #72	-> byte code offset #27
/* @3:   */     //   Java source line #73	-> byte code offset #29
/* @4:   */     //   Java source line #77	-> byte code offset #37
/* @5:   */     //   Java source line #79	-> byte code offset #54
/* @6:   */     //   Java source line #80	-> byte code offset #58
/* @7:   */     //   Java source line #83	-> byte code offset #65
/* @8:   */     //   Java source line #106	-> byte code offset #69
/* @9:   */     //   Java source line #108	-> byte code offset #77
/* A0:   */     //   Java source line #112	-> byte code offset #85
/* A1:   */     //   Java source line #110	-> byte code offset #88
/* A2:   */     //   Java source line #111	-> byte code offset #90
/* A3:   */     //   Java source line #115	-> byte code offset #100
/* A4:   */     //   Java source line #119	-> byte code offset #111
/* A5:   */     //   Java source line #117	-> byte code offset #114
/* A6:   */     //   Java source line #118	-> byte code offset #116
/* A7:   */     //   Java source line #119	-> byte code offset #126
/* A8:   */     //   Java source line #85	-> byte code offset #129
/* A9:   */     //   Java source line #87	-> byte code offset #131
/* B0:   */     //   Java source line #88	-> byte code offset #145
/* B1:   */     //   Java source line #93	-> byte code offset #152
/* B2:   */     //   Java source line #91	-> byte code offset #155
/* B3:   */     //   Java source line #92	-> byte code offset #157
/* B4:   */     //   Java source line #95	-> byte code offset #167
/* B5:   */     //   Java source line #96	-> byte code offset #175
/* B6:   */     //   Java source line #98	-> byte code offset #181
/* B7:   */     //   Java source line #99	-> byte code offset #189
/* B8:   */     //   Java source line #102	-> byte code offset #204
/* B9:   */     //   Java source line #106	-> byte code offset #216
/* C0:   */     //   Java source line #108	-> byte code offset #226
/* C1:   */     //   Java source line #112	-> byte code offset #234
/* C2:   */     //   Java source line #110	-> byte code offset #237
/* C3:   */     //   Java source line #111	-> byte code offset #239
/* C4:   */     //   Java source line #115	-> byte code offset #249
/* C5:   */     //   Java source line #119	-> byte code offset #260
/* C6:   */     //   Java source line #117	-> byte code offset #263
/* C7:   */     //   Java source line #118	-> byte code offset #265
/* C8:   */     //   Java source line #119	-> byte code offset #275
/* C9:   */     //   Java source line #122	-> byte code offset #278
/* D0:   */     //   Java source line #123	-> byte code offset #280
/* D1:   */     // Local variable table:
/* D2:   */     //   start	length	slot	name	signature
/* D3:   */     //   0	292	0	this	JdbcIsolationDelegate
/* D4:   */     //   0	292	1	work	org.hibernate.jdbc.WorkExecutorVisitable<T>
/* D5:   */     //   0	292	2	transacted	boolean
/* D6:   */     //   1	222	3	wasAutoCommit	boolean
/* D7:   */     //   11	243	4	connection	java.sql.Connection
/* D8:   */     //   278	7	4	sqle	java.sql.SQLException
/* D9:   */     //   52	14	5	result	T
/* E0:   */     //   129	82	5	e	java.lang.Exception
/* E1:   */     //   155	6	6	ignore	java.lang.Exception
/* E2:   */     //   88	3	7	ignore	java.lang.Exception
/* E3:   */     //   114	6	7	ignore	java.lang.Exception
/* E4:   */     //   216	60	8	localObject	Object
/* E5:   */     //   237	3	9	ignore	java.lang.Exception
/* E6:   */     //   263	6	9	ignore	java.lang.Exception
/* E7:   */     // Exception table:
/* E8:   */     //   from	to	target	type
/* E9:   */     //   77	85	88	java/lang/Exception
/* F0:   */     //   100	111	114	java/lang/Exception
/* F1:   */     //   13	69	129	java/lang/Exception
/* F2:   */     //   131	152	155	java/lang/Exception
/* F3:   */     //   13	69	216	finally
/* F4:   */     //   129	218	216	finally
/* F5:   */     //   226	234	237	java/lang/Exception
/* F6:   */     //   249	260	263	java/lang/Exception
/* F7:   */     //   2	126	278	java/sql/SQLException
/* F8:   */     //   129	278	278	java/sql/SQLException
/* F9:   */   }
/* G0:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.jdbc.JdbcIsolationDelegate
 * JD-Core Version:    0.7.0.1
 */
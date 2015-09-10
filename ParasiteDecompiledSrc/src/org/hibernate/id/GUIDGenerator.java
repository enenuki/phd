/*  1:   */ package org.hibernate.id;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.CoreMessageLogger;
/*  4:   */ import org.jboss.logging.Logger;
/*  5:   */ 
/*  6:   */ public class GUIDGenerator
/*  7:   */   implements IdentifierGenerator
/*  8:   */ {
/*  9:43 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, GUIDGenerator.class.getName());
/* 10:44 */   private static boolean warned = false;
/* 11:   */   
/* 12:   */   public GUIDGenerator()
/* 13:   */   {
/* 14:47 */     if (!warned)
/* 15:   */     {
/* 16:48 */       warned = true;
/* 17:49 */       LOG.deprecatedUuidGenerator(UUIDGenerator.class.getName(), UUIDGenerationStrategy.class.getName());
/* 18:   */     }
/* 19:   */   }
/* 20:   */   
/* 21:   */   /* Error */
/* 22:   */   public java.io.Serializable generate(org.hibernate.engine.spi.SessionImplementor session, Object obj)
/* 23:   */     throws org.hibernate.HibernateException
/* 24:   */   {
/* 25:   */     // Byte code:
/* 26:   */     //   0: aload_1
/* 27:   */     //   1: invokeinterface 8 1 0
/* 28:   */     //   6: invokeinterface 9 1 0
/* 29:   */     //   11: invokevirtual 10	org/hibernate/dialect/Dialect:getSelectGUIDString	()Ljava/lang/String;
/* 30:   */     //   14: astore_3
/* 31:   */     //   15: aload_1
/* 32:   */     //   16: invokeinterface 11 1 0
/* 33:   */     //   21: invokeinterface 12 1 0
/* 34:   */     //   26: invokeinterface 13 1 0
/* 35:   */     //   31: aload_3
/* 36:   */     //   32: invokeinterface 14 2 0
/* 37:   */     //   37: astore 4
/* 38:   */     //   39: aload 4
/* 39:   */     //   41: invokeinterface 15 1 0
/* 40:   */     //   46: astore 5
/* 41:   */     //   48: aload 5
/* 42:   */     //   50: invokeinterface 16 1 0
/* 43:   */     //   55: pop
/* 44:   */     //   56: aload 5
/* 45:   */     //   58: iconst_1
/* 46:   */     //   59: invokeinterface 17 2 0
/* 47:   */     //   64: astore 6
/* 48:   */     //   66: aload 5
/* 49:   */     //   68: invokeinterface 18 1 0
/* 50:   */     //   73: goto +15 -> 88
/* 51:   */     //   76: astore 7
/* 52:   */     //   78: aload 5
/* 53:   */     //   80: invokeinterface 18 1 0
/* 54:   */     //   85: aload 7
/* 55:   */     //   87: athrow
/* 56:   */     //   88: getstatic 3	org/hibernate/id/GUIDGenerator:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 57:   */     //   91: aload 6
/* 58:   */     //   93: invokeinterface 19 2 0
/* 59:   */     //   98: aload 6
/* 60:   */     //   100: astore 7
/* 61:   */     //   102: aload 4
/* 62:   */     //   104: invokeinterface 20 1 0
/* 63:   */     //   109: aload 7
/* 64:   */     //   111: areturn
/* 65:   */     //   112: astore 8
/* 66:   */     //   114: aload 4
/* 67:   */     //   116: invokeinterface 20 1 0
/* 68:   */     //   121: aload 8
/* 69:   */     //   123: athrow
/* 70:   */     //   124: astore 4
/* 71:   */     //   126: aload_1
/* 72:   */     //   127: invokeinterface 8 1 0
/* 73:   */     //   132: invokeinterface 22 1 0
/* 74:   */     //   137: aload 4
/* 75:   */     //   139: ldc 23
/* 76:   */     //   141: aload_3
/* 77:   */     //   142: invokevirtual 24	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 78:   */     //   145: athrow
/* 79:   */     // Line number table:
/* 80:   */     //   Java source line #56	-> byte code offset #0
/* 81:   */     //   Java source line #58	-> byte code offset #15
/* 82:   */     //   Java source line #60	-> byte code offset #39
/* 83:   */     //   Java source line #63	-> byte code offset #48
/* 84:   */     //   Java source line #64	-> byte code offset #56
/* 85:   */     //   Java source line #67	-> byte code offset #66
/* 86:   */     //   Java source line #68	-> byte code offset #73
/* 87:   */     //   Java source line #67	-> byte code offset #76
/* 88:   */     //   Java source line #69	-> byte code offset #88
/* 89:   */     //   Java source line #70	-> byte code offset #98
/* 90:   */     //   Java source line #73	-> byte code offset #102
/* 91:   */     //   Java source line #76	-> byte code offset #124
/* 92:   */     //   Java source line #77	-> byte code offset #126
/* 93:   */     // Local variable table:
/* 94:   */     //   start	length	slot	name	signature
/* 95:   */     //   0	146	0	this	GUIDGenerator
/* 96:   */     //   0	146	1	session	org.hibernate.engine.spi.SessionImplementor
/* 97:   */     //   0	146	2	obj	Object
/* 98:   */     //   14	128	3	sql	java.lang.String
/* 99:   */     //   37	78	4	st	java.sql.PreparedStatement
/* :0:   */     //   124	14	4	sqle	java.sql.SQLException
/* :1:   */     //   46	33	5	rs	java.sql.ResultSet
/* :2:   */     //   64	35	6	result	java.lang.String
/* :3:   */     //   76	10	7	localObject1	Object
/* :4:   */     //   112	10	8	localObject2	Object
/* :5:   */     // Exception table:
/* :6:   */     //   from	to	target	type
/* :7:   */     //   48	66	76	finally
/* :8:   */     //   76	78	76	finally
/* :9:   */     //   39	102	112	finally
/* ;0:   */     //   112	114	112	finally
/* ;1:   */     //   15	109	124	java/sql/SQLException
/* ;2:   */     //   112	124	124	java/sql/SQLException
/* ;3:   */   }
/* ;4:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.GUIDGenerator
 * JD-Core Version:    0.7.0.1
 */
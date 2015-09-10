/*  1:   */ package org.hibernate.id.insert;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.sql.PreparedStatement;
/*  5:   */ import java.sql.ResultSet;
/*  6:   */ import java.sql.SQLException;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.id.PostInsertIdentityPersister;
/*  9:   */ 
/* 10:   */ public abstract class AbstractSelectingDelegate
/* 11:   */   implements InsertGeneratedIdentifierDelegate
/* 12:   */ {
/* 13:   */   private final PostInsertIdentityPersister persister;
/* 14:   */   
/* 15:   */   protected AbstractSelectingDelegate(PostInsertIdentityPersister persister)
/* 16:   */   {
/* 17:46 */     this.persister = persister;
/* 18:   */   }
/* 19:   */   
/* 20:   */   /* Error */
/* 21:   */   public final Serializable performInsert(String insertSQL, SessionImplementor session, Binder binder)
/* 22:   */   {
/* 23:   */     // Byte code:
/* 24:   */     //   0: aload_2
/* 25:   */     //   1: invokeinterface 3 1 0
/* 26:   */     //   6: invokeinterface 4 1 0
/* 27:   */     //   11: invokeinterface 5 1 0
/* 28:   */     //   16: aload_1
/* 29:   */     //   17: iconst_2
/* 30:   */     //   18: invokeinterface 6 3 0
/* 31:   */     //   23: astore 4
/* 32:   */     //   25: aload_3
/* 33:   */     //   26: aload 4
/* 34:   */     //   28: invokeinterface 7 2 0
/* 35:   */     //   33: aload 4
/* 36:   */     //   35: invokeinterface 8 1 0
/* 37:   */     //   40: pop
/* 38:   */     //   41: aload 4
/* 39:   */     //   43: invokeinterface 9 1 0
/* 40:   */     //   48: goto +15 -> 63
/* 41:   */     //   51: astore 5
/* 42:   */     //   53: aload 4
/* 43:   */     //   55: invokeinterface 9 1 0
/* 44:   */     //   60: aload 5
/* 45:   */     //   62: athrow
/* 46:   */     //   63: goto +48 -> 111
/* 47:   */     //   66: astore 4
/* 48:   */     //   68: aload_2
/* 49:   */     //   69: invokeinterface 11 1 0
/* 50:   */     //   74: invokeinterface 12 1 0
/* 51:   */     //   79: aload 4
/* 52:   */     //   81: new 13	java/lang/StringBuilder
/* 53:   */     //   84: dup
/* 54:   */     //   85: invokespecial 14	java/lang/StringBuilder:<init>	()V
/* 55:   */     //   88: ldc 15
/* 56:   */     //   90: invokevirtual 16	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 57:   */     //   93: aload_0
/* 58:   */     //   94: getfield 2	org/hibernate/id/insert/AbstractSelectingDelegate:persister	Lorg/hibernate/id/PostInsertIdentityPersister;
/* 59:   */     //   97: invokestatic 17	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;)Ljava/lang/String;
/* 60:   */     //   100: invokevirtual 16	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 61:   */     //   103: invokevirtual 18	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 62:   */     //   106: aload_1
/* 63:   */     //   107: invokevirtual 19	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 64:   */     //   110: athrow
/* 65:   */     //   111: aload_0
/* 66:   */     //   112: invokevirtual 20	org/hibernate/id/insert/AbstractSelectingDelegate:getSelectSQL	()Ljava/lang/String;
/* 67:   */     //   115: astore 4
/* 68:   */     //   117: aload_2
/* 69:   */     //   118: invokeinterface 3 1 0
/* 70:   */     //   123: invokeinterface 4 1 0
/* 71:   */     //   128: invokeinterface 5 1 0
/* 72:   */     //   133: aload 4
/* 73:   */     //   135: iconst_0
/* 74:   */     //   136: invokeinterface 21 3 0
/* 75:   */     //   141: astore 5
/* 76:   */     //   143: aload_0
/* 77:   */     //   144: aload_2
/* 78:   */     //   145: aload 5
/* 79:   */     //   147: aload_3
/* 80:   */     //   148: invokeinterface 22 1 0
/* 81:   */     //   153: invokevirtual 23	org/hibernate/id/insert/AbstractSelectingDelegate:bindParameters	(Lorg/hibernate/engine/spi/SessionImplementor;Ljava/sql/PreparedStatement;Ljava/lang/Object;)V
/* 82:   */     //   156: aload 5
/* 83:   */     //   158: invokeinterface 24 1 0
/* 84:   */     //   163: astore 6
/* 85:   */     //   165: aload_0
/* 86:   */     //   166: aload_2
/* 87:   */     //   167: aload 6
/* 88:   */     //   169: aload_3
/* 89:   */     //   170: invokeinterface 22 1 0
/* 90:   */     //   175: invokevirtual 25	org/hibernate/id/insert/AbstractSelectingDelegate:getResult	(Lorg/hibernate/engine/spi/SessionImplementor;Ljava/sql/ResultSet;Ljava/lang/Object;)Ljava/io/Serializable;
/* 91:   */     //   178: astore 7
/* 92:   */     //   180: aload 6
/* 93:   */     //   182: invokeinterface 26 1 0
/* 94:   */     //   187: aload 5
/* 95:   */     //   189: invokeinterface 9 1 0
/* 96:   */     //   194: aload 7
/* 97:   */     //   196: areturn
/* 98:   */     //   197: astore 8
/* 99:   */     //   199: aload 6
/* :0:   */     //   201: invokeinterface 26 1 0
/* :1:   */     //   206: aload 8
/* :2:   */     //   208: athrow
/* :3:   */     //   209: astore 9
/* :4:   */     //   211: aload 5
/* :5:   */     //   213: invokeinterface 9 1 0
/* :6:   */     //   218: aload 9
/* :7:   */     //   220: athrow
/* :8:   */     //   221: astore 5
/* :9:   */     //   223: aload_2
/* ;0:   */     //   224: invokeinterface 11 1 0
/* ;1:   */     //   229: invokeinterface 12 1 0
/* ;2:   */     //   234: aload 5
/* ;3:   */     //   236: new 13	java/lang/StringBuilder
/* ;4:   */     //   239: dup
/* ;5:   */     //   240: invokespecial 14	java/lang/StringBuilder:<init>	()V
/* ;6:   */     //   243: ldc 27
/* ;7:   */     //   245: invokevirtual 16	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* ;8:   */     //   248: aload_0
/* ;9:   */     //   249: getfield 2	org/hibernate/id/insert/AbstractSelectingDelegate:persister	Lorg/hibernate/id/PostInsertIdentityPersister;
/* <0:   */     //   252: invokestatic 17	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;)Ljava/lang/String;
/* <1:   */     //   255: invokevirtual 16	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* <2:   */     //   258: invokevirtual 18	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* <3:   */     //   261: aload_1
/* <4:   */     //   262: invokevirtual 19	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* <5:   */     //   265: athrow
/* <6:   */     // Line number table:
/* <7:   */     //   Java source line #52	-> byte code offset #0
/* <8:   */     //   Java source line #57	-> byte code offset #25
/* <9:   */     //   Java source line #58	-> byte code offset #33
/* =0:   */     //   Java source line #61	-> byte code offset #41
/* =1:   */     //   Java source line #62	-> byte code offset #48
/* =2:   */     //   Java source line #61	-> byte code offset #51
/* =3:   */     //   Java source line #70	-> byte code offset #63
/* =4:   */     //   Java source line #64	-> byte code offset #66
/* =5:   */     //   Java source line #65	-> byte code offset #68
/* =6:   */     //   Java source line #72	-> byte code offset #111
/* =7:   */     //   Java source line #76	-> byte code offset #117
/* =8:   */     //   Java source line #81	-> byte code offset #143
/* =9:   */     //   Java source line #82	-> byte code offset #156
/* >0:   */     //   Java source line #84	-> byte code offset #165
/* >1:   */     //   Java source line #87	-> byte code offset #180
/* >2:   */     //   Java source line #91	-> byte code offset #187
/* >3:   */     //   Java source line #87	-> byte code offset #197
/* >4:   */     //   Java source line #91	-> byte code offset #209
/* >5:   */     //   Java source line #95	-> byte code offset #221
/* >6:   */     //   Java source line #96	-> byte code offset #223
/* >7:   */     // Local variable table:
/* >8:   */     //   start	length	slot	name	signature
/* >9:   */     //   0	266	0	this	AbstractSelectingDelegate
/* ?0:   */     //   0	266	1	insertSQL	String
/* ?1:   */     //   0	266	2	session	SessionImplementor
/* ?2:   */     //   0	266	3	binder	Binder
/* ?3:   */     //   23	31	4	insert	PreparedStatement
/* ?4:   */     //   66	14	4	sqle	SQLException
/* ?5:   */     //   115	19	4	selectSQL	String
/* ?6:   */     //   51	10	5	localObject1	Object
/* ?7:   */     //   141	71	5	idSelect	PreparedStatement
/* ?8:   */     //   221	14	5	sqle	SQLException
/* ?9:   */     //   163	37	6	rs	ResultSet
/* @0:   */     //   197	10	8	localObject2	Object
/* @1:   */     //   209	10	9	localObject3	Object
/* @2:   */     // Exception table:
/* @3:   */     //   from	to	target	type
/* @4:   */     //   25	41	51	finally
/* @5:   */     //   51	53	51	finally
/* @6:   */     //   0	63	66	java/sql/SQLException
/* @7:   */     //   165	180	197	finally
/* @8:   */     //   197	199	197	finally
/* @9:   */     //   143	187	209	finally
/* A0:   */     //   197	211	209	finally
/* A1:   */     //   117	194	221	java/sql/SQLException
/* A2:   */     //   197	221	221	java/sql/SQLException
/* A3:   */   }
/* A4:   */   
/* A5:   */   protected abstract String getSelectSQL();
/* A6:   */   
/* A7:   */   protected void bindParameters(SessionImplementor session, PreparedStatement ps, Object entity)
/* A8:   */     throws SQLException
/* A9:   */   {}
/* B0:   */   
/* B1:   */   protected abstract Serializable getResult(SessionImplementor paramSessionImplementor, ResultSet paramResultSet, Object paramObject)
/* B2:   */     throws SQLException;
/* B3:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.insert.AbstractSelectingDelegate
 * JD-Core Version:    0.7.0.1
 */
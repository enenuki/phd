/*  1:   */ package org.hibernate.id.insert;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.sql.PreparedStatement;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.id.PostInsertIdentityPersister;
/*  8:   */ 
/*  9:   */ public abstract class AbstractReturningDelegate
/* 10:   */   implements InsertGeneratedIdentifierDelegate
/* 11:   */ {
/* 12:   */   private final PostInsertIdentityPersister persister;
/* 13:   */   
/* 14:   */   public AbstractReturningDelegate(PostInsertIdentityPersister persister)
/* 15:   */   {
/* 16:46 */     this.persister = persister;
/* 17:   */   }
/* 18:   */   
/* 19:   */   /* Error */
/* 20:   */   public final Serializable performInsert(String insertSQL, SessionImplementor session, Binder binder)
/* 21:   */   {
/* 22:   */     // Byte code:
/* 23:   */     //   0: aload_0
/* 24:   */     //   1: aload_1
/* 25:   */     //   2: aload_2
/* 26:   */     //   3: invokevirtual 3	org/hibernate/id/insert/AbstractReturningDelegate:prepare	(Ljava/lang/String;Lorg/hibernate/engine/spi/SessionImplementor;)Ljava/sql/PreparedStatement;
/* 27:   */     //   6: astore 4
/* 28:   */     //   8: aload_3
/* 29:   */     //   9: aload 4
/* 30:   */     //   11: invokeinterface 4 2 0
/* 31:   */     //   16: aload_0
/* 32:   */     //   17: aload 4
/* 33:   */     //   19: invokevirtual 5	org/hibernate/id/insert/AbstractReturningDelegate:executeAndExtract	(Ljava/sql/PreparedStatement;)Ljava/io/Serializable;
/* 34:   */     //   22: astore 5
/* 35:   */     //   24: aload_0
/* 36:   */     //   25: aload 4
/* 37:   */     //   27: aload_2
/* 38:   */     //   28: invokevirtual 6	org/hibernate/id/insert/AbstractReturningDelegate:releaseStatement	(Ljava/sql/PreparedStatement;Lorg/hibernate/engine/spi/SessionImplementor;)V
/* 39:   */     //   31: aload 5
/* 40:   */     //   33: areturn
/* 41:   */     //   34: astore 6
/* 42:   */     //   36: aload_0
/* 43:   */     //   37: aload 4
/* 44:   */     //   39: aload_2
/* 45:   */     //   40: invokevirtual 6	org/hibernate/id/insert/AbstractReturningDelegate:releaseStatement	(Ljava/sql/PreparedStatement;Lorg/hibernate/engine/spi/SessionImplementor;)V
/* 46:   */     //   43: aload 6
/* 47:   */     //   45: athrow
/* 48:   */     //   46: astore 4
/* 49:   */     //   48: aload_2
/* 50:   */     //   49: invokeinterface 8 1 0
/* 51:   */     //   54: invokeinterface 9 1 0
/* 52:   */     //   59: aload 4
/* 53:   */     //   61: new 10	java/lang/StringBuilder
/* 54:   */     //   64: dup
/* 55:   */     //   65: invokespecial 11	java/lang/StringBuilder:<init>	()V
/* 56:   */     //   68: ldc 12
/* 57:   */     //   70: invokevirtual 13	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 58:   */     //   73: aload_0
/* 59:   */     //   74: getfield 2	org/hibernate/id/insert/AbstractReturningDelegate:persister	Lorg/hibernate/id/PostInsertIdentityPersister;
/* 60:   */     //   77: invokestatic 14	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;)Ljava/lang/String;
/* 61:   */     //   80: invokevirtual 13	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 62:   */     //   83: invokevirtual 15	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 63:   */     //   86: aload_1
/* 64:   */     //   87: invokevirtual 16	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 65:   */     //   90: athrow
/* 66:   */     // Line number table:
/* 67:   */     //   Java source line #52	-> byte code offset #0
/* 68:   */     //   Java source line #54	-> byte code offset #8
/* 69:   */     //   Java source line #55	-> byte code offset #16
/* 70:   */     //   Java source line #58	-> byte code offset #24
/* 71:   */     //   Java source line #61	-> byte code offset #46
/* 72:   */     //   Java source line #62	-> byte code offset #48
/* 73:   */     // Local variable table:
/* 74:   */     //   start	length	slot	name	signature
/* 75:   */     //   0	91	0	this	AbstractReturningDelegate
/* 76:   */     //   0	91	1	insertSQL	String
/* 77:   */     //   0	91	2	session	SessionImplementor
/* 78:   */     //   0	91	3	binder	Binder
/* 79:   */     //   6	32	4	insert	PreparedStatement
/* 80:   */     //   46	14	4	sqle	SQLException
/* 81:   */     //   34	10	6	localObject	Object
/* 82:   */     // Exception table:
/* 83:   */     //   from	to	target	type
/* 84:   */     //   8	24	34	finally
/* 85:   */     //   34	36	34	finally
/* 86:   */     //   0	31	46	java/sql/SQLException
/* 87:   */     //   34	46	46	java/sql/SQLException
/* 88:   */   }
/* 89:   */   
/* 90:   */   protected PostInsertIdentityPersister getPersister()
/* 91:   */   {
/* 92:71 */     return this.persister;
/* 93:   */   }
/* 94:   */   
/* 95:   */   protected abstract PreparedStatement prepare(String paramString, SessionImplementor paramSessionImplementor)
/* 96:   */     throws SQLException;
/* 97:   */   
/* 98:   */   protected abstract Serializable executeAndExtract(PreparedStatement paramPreparedStatement)
/* 99:   */     throws SQLException;
/* :0:   */   
/* :1:   */   protected void releaseStatement(PreparedStatement insert, SessionImplementor session)
/* :2:   */     throws SQLException
/* :3:   */   {
/* :4:79 */     insert.close();
/* :5:   */   }
/* :6:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.insert.AbstractReturningDelegate
 * JD-Core Version:    0.7.0.1
 */
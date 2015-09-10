/*   1:    */ package org.hibernate.hql.internal.ast.exec;
/*   2:    */ 
/*   3:    */ import antlr.RecognitionException;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*   6:    */ import org.hibernate.hql.internal.ast.ParseErrorHandler;
/*   7:    */ import org.hibernate.hql.internal.ast.QuerySyntaxException;
/*   8:    */ import org.hibernate.hql.internal.ast.SqlGenerator;
/*   9:    */ import org.hibernate.persister.entity.Queryable;
/*  10:    */ 
/*  11:    */ public class BasicExecutor
/*  12:    */   extends AbstractStatementExecutor
/*  13:    */ {
/*  14:    */   private final Queryable persister;
/*  15:    */   private final String sql;
/*  16:    */   private final List parameterSpecifications;
/*  17:    */   
/*  18:    */   public BasicExecutor(HqlSqlWalker walker, Queryable persister)
/*  19:    */   {
/*  20: 55 */     super(walker, null);
/*  21: 56 */     this.persister = persister;
/*  22:    */     try
/*  23:    */     {
/*  24: 58 */       SqlGenerator gen = new SqlGenerator(getFactory());
/*  25: 59 */       gen.statement(walker.getAST());
/*  26: 60 */       this.sql = gen.getSQL();
/*  27: 61 */       gen.getParseErrorHandler().throwQueryException();
/*  28: 62 */       this.parameterSpecifications = gen.getCollectedParameters();
/*  29:    */     }
/*  30:    */     catch (RecognitionException e)
/*  31:    */     {
/*  32: 65 */       throw QuerySyntaxException.convert(e);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String[] getSqlStatements()
/*  37:    */   {
/*  38: 70 */     return new String[] { this.sql };
/*  39:    */   }
/*  40:    */   
/*  41:    */   /* Error */
/*  42:    */   public int execute(org.hibernate.engine.spi.QueryParameters parameters, org.hibernate.engine.spi.SessionImplementor session)
/*  43:    */     throws org.hibernate.HibernateException
/*  44:    */   {
/*  45:    */     // Byte code:
/*  46:    */     //   0: aload_0
/*  47:    */     //   1: aload_2
/*  48:    */     //   2: invokevirtual 17	org/hibernate/hql/internal/ast/exec/BasicExecutor:coordinateSharedCacheCleanup	(Lorg/hibernate/engine/spi/SessionImplementor;)V
/*  49:    */     //   5: aconst_null
/*  50:    */     //   6: astore_3
/*  51:    */     //   7: aload_1
/*  52:    */     //   8: invokevirtual 18	org/hibernate/engine/spi/QueryParameters:getRowSelection	()Lorg/hibernate/engine/spi/RowSelection;
/*  53:    */     //   11: astore 4
/*  54:    */     //   13: aload_2
/*  55:    */     //   14: invokeinterface 19 1 0
/*  56:    */     //   19: invokeinterface 20 1 0
/*  57:    */     //   24: invokeinterface 21 1 0
/*  58:    */     //   29: aload_0
/*  59:    */     //   30: getfield 9	org/hibernate/hql/internal/ast/exec/BasicExecutor:sql	Ljava/lang/String;
/*  60:    */     //   33: iconst_0
/*  61:    */     //   34: invokeinterface 22 3 0
/*  62:    */     //   39: astore_3
/*  63:    */     //   40: aload_0
/*  64:    */     //   41: getfield 13	org/hibernate/hql/internal/ast/exec/BasicExecutor:parameterSpecifications	Ljava/util/List;
/*  65:    */     //   44: invokeinterface 23 1 0
/*  66:    */     //   49: astore 5
/*  67:    */     //   51: iconst_1
/*  68:    */     //   52: istore 6
/*  69:    */     //   54: aload 5
/*  70:    */     //   56: invokeinterface 24 1 0
/*  71:    */     //   61: ifeq +35 -> 96
/*  72:    */     //   64: aload 5
/*  73:    */     //   66: invokeinterface 25 1 0
/*  74:    */     //   71: checkcast 26	org/hibernate/param/ParameterSpecification
/*  75:    */     //   74: astore 7
/*  76:    */     //   76: iload 6
/*  77:    */     //   78: aload 7
/*  78:    */     //   80: aload_3
/*  79:    */     //   81: aload_1
/*  80:    */     //   82: aload_2
/*  81:    */     //   83: iload 6
/*  82:    */     //   85: invokeinterface 27 5 0
/*  83:    */     //   90: iadd
/*  84:    */     //   91: istore 6
/*  85:    */     //   93: goto -39 -> 54
/*  86:    */     //   96: aload 4
/*  87:    */     //   98: ifnull +25 -> 123
/*  88:    */     //   101: aload 4
/*  89:    */     //   103: invokevirtual 28	org/hibernate/engine/spi/RowSelection:getTimeout	()Ljava/lang/Integer;
/*  90:    */     //   106: ifnull +17 -> 123
/*  91:    */     //   109: aload_3
/*  92:    */     //   110: aload 4
/*  93:    */     //   112: invokevirtual 28	org/hibernate/engine/spi/RowSelection:getTimeout	()Ljava/lang/Integer;
/*  94:    */     //   115: invokevirtual 29	java/lang/Integer:intValue	()I
/*  95:    */     //   118: invokeinterface 30 2 0
/*  96:    */     //   123: aload_3
/*  97:    */     //   124: invokeinterface 31 1 0
/*  98:    */     //   129: istore 7
/*  99:    */     //   131: aload_3
/* 100:    */     //   132: ifnull +9 -> 141
/* 101:    */     //   135: aload_3
/* 102:    */     //   136: invokeinterface 32 1 0
/* 103:    */     //   141: iload 7
/* 104:    */     //   143: ireturn
/* 105:    */     //   144: astore 8
/* 106:    */     //   146: aload_3
/* 107:    */     //   147: ifnull +9 -> 156
/* 108:    */     //   150: aload_3
/* 109:    */     //   151: invokeinterface 32 1 0
/* 110:    */     //   156: aload 8
/* 111:    */     //   158: athrow
/* 112:    */     //   159: astore 5
/* 113:    */     //   161: aload_0
/* 114:    */     //   162: invokevirtual 4	org/hibernate/hql/internal/ast/exec/BasicExecutor:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 115:    */     //   165: invokeinterface 34 1 0
/* 116:    */     //   170: aload 5
/* 117:    */     //   172: ldc 35
/* 118:    */     //   174: aload_0
/* 119:    */     //   175: getfield 9	org/hibernate/hql/internal/ast/exec/BasicExecutor:sql	Ljava/lang/String;
/* 120:    */     //   178: invokevirtual 36	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 121:    */     //   181: athrow
/* 122:    */     // Line number table:
/* 123:    */     //   Java source line #75	-> byte code offset #0
/* 124:    */     //   Java source line #77	-> byte code offset #5
/* 125:    */     //   Java source line #78	-> byte code offset #7
/* 126:    */     //   Java source line #82	-> byte code offset #13
/* 127:    */     //   Java source line #83	-> byte code offset #40
/* 128:    */     //   Java source line #84	-> byte code offset #51
/* 129:    */     //   Java source line #85	-> byte code offset #54
/* 130:    */     //   Java source line #86	-> byte code offset #64
/* 131:    */     //   Java source line #87	-> byte code offset #76
/* 132:    */     //   Java source line #88	-> byte code offset #93
/* 133:    */     //   Java source line #89	-> byte code offset #96
/* 134:    */     //   Java source line #90	-> byte code offset #101
/* 135:    */     //   Java source line #91	-> byte code offset #109
/* 136:    */     //   Java source line #95	-> byte code offset #123
/* 137:    */     //   Java source line #98	-> byte code offset #131
/* 138:    */     //   Java source line #99	-> byte code offset #135
/* 139:    */     //   Java source line #98	-> byte code offset #144
/* 140:    */     //   Java source line #99	-> byte code offset #150
/* 141:    */     //   Java source line #103	-> byte code offset #159
/* 142:    */     //   Java source line #104	-> byte code offset #161
/* 143:    */     // Local variable table:
/* 144:    */     //   start	length	slot	name	signature
/* 145:    */     //   0	182	0	this	BasicExecutor
/* 146:    */     //   0	182	1	parameters	org.hibernate.engine.spi.QueryParameters
/* 147:    */     //   0	182	2	session	org.hibernate.engine.spi.SessionImplementor
/* 148:    */     //   6	145	3	st	java.sql.PreparedStatement
/* 149:    */     //   11	100	4	selection	org.hibernate.engine.spi.RowSelection
/* 150:    */     //   49	16	5	parameterSpecifications	java.util.Iterator
/* 151:    */     //   159	12	5	sqle	java.sql.SQLException
/* 152:    */     //   52	40	6	pos	int
/* 153:    */     //   74	68	7	paramSpec	org.hibernate.param.ParameterSpecification
/* 154:    */     //   144	13	8	localObject	java.lang.Object
/* 155:    */     // Exception table:
/* 156:    */     //   from	to	target	type
/* 157:    */     //   13	131	144	finally
/* 158:    */     //   144	146	144	finally
/* 159:    */     //   13	141	159	java/sql/SQLException
/* 160:    */     //   144	159	159	java/sql/SQLException
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected Queryable[] getAffectedQueryables()
/* 164:    */   {
/* 165:114 */     return new Queryable[] { this.persister };
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.exec.BasicExecutor
 * JD-Core Version:    0.7.0.1
 */
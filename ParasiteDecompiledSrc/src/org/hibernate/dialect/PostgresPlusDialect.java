/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import java.sql.CallableStatement;
/*  4:   */ import java.sql.ResultSet;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*  7:   */ import org.hibernate.dialect.function.NvlFunction;
/*  8:   */ import org.hibernate.dialect.function.StandardSQLFunction;
/*  9:   */ import org.hibernate.type.StandardBasicTypes;
/* 10:   */ 
/* 11:   */ public class PostgresPlusDialect
/* 12:   */   extends PostgreSQLDialect
/* 13:   */ {
/* 14:   */   public PostgresPlusDialect()
/* 15:   */   {
/* 16:45 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/* 17:46 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/* 18:47 */     registerFunction("soundex", new StandardSQLFunction("soundex"));
/* 19:48 */     registerFunction("sysdate", new NoArgSQLFunction("sysdate", StandardBasicTypes.DATE, false));
/* 20:49 */     registerFunction("rowid", new NoArgSQLFunction("rowid", StandardBasicTypes.LONG, false));
/* 21:50 */     registerFunction("rownum", new NoArgSQLFunction("rownum", StandardBasicTypes.LONG, false));
/* 22:51 */     registerFunction("instr", new StandardSQLFunction("instr", StandardBasicTypes.INTEGER));
/* 23:52 */     registerFunction("lpad", new StandardSQLFunction("lpad", StandardBasicTypes.STRING));
/* 24:53 */     registerFunction("replace", new StandardSQLFunction("replace", StandardBasicTypes.STRING));
/* 25:54 */     registerFunction("rpad", new StandardSQLFunction("rpad", StandardBasicTypes.STRING));
/* 26:55 */     registerFunction("translate", new StandardSQLFunction("translate", StandardBasicTypes.STRING));
/* 27:56 */     registerFunction("substring", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/* 28:57 */     registerFunction("coalesce", new NvlFunction());
/* 29:58 */     registerFunction("atan2", new StandardSQLFunction("atan2", StandardBasicTypes.FLOAT));
/* 30:59 */     registerFunction("mod", new StandardSQLFunction("mod", StandardBasicTypes.INTEGER));
/* 31:60 */     registerFunction("nvl", new StandardSQLFunction("nvl"));
/* 32:61 */     registerFunction("nvl2", new StandardSQLFunction("nvl2"));
/* 33:62 */     registerFunction("power", new StandardSQLFunction("power", StandardBasicTypes.FLOAT));
/* 34:63 */     registerFunction("add_months", new StandardSQLFunction("add_months", StandardBasicTypes.DATE));
/* 35:64 */     registerFunction("months_between", new StandardSQLFunction("months_between", StandardBasicTypes.FLOAT));
/* 36:65 */     registerFunction("next_day", new StandardSQLFunction("next_day", StandardBasicTypes.DATE));
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getCurrentTimestampSelectString()
/* 40:   */   {
/* 41:69 */     return "select sysdate";
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getCurrentTimestampSQLFunctionName()
/* 45:   */   {
/* 46:73 */     return "sysdate";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int registerResultSetOutParameter(CallableStatement statement, int col)
/* 50:   */     throws SQLException
/* 51:   */   {
/* 52:77 */     statement.registerOutParameter(col, 2006);
/* 53:78 */     col++;
/* 54:79 */     return col;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public ResultSet getResultSet(CallableStatement ps)
/* 58:   */     throws SQLException
/* 59:   */   {
/* 60:83 */     ps.execute();
/* 61:84 */     return (ResultSet)ps.getObject(1);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public String getSelectGUIDString()
/* 65:   */   {
/* 66:88 */     return "select uuid_generate_v1";
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.PostgresPlusDialect
 * JD-Core Version:    0.7.0.1
 */
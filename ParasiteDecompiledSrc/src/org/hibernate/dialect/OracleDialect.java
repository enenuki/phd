/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import org.hibernate.internal.CoreMessageLogger;
/*   4:    */ import org.hibernate.sql.CaseFragment;
/*   5:    */ import org.hibernate.sql.DecodeCaseFragment;
/*   6:    */ import org.hibernate.sql.JoinFragment;
/*   7:    */ import org.hibernate.sql.OracleJoinFragment;
/*   8:    */ import org.jboss.logging.Logger;
/*   9:    */ 
/*  10:    */ @Deprecated
/*  11:    */ public class OracleDialect
/*  12:    */   extends Oracle9Dialect
/*  13:    */ {
/*  14: 44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, OracleDialect.class.getName());
/*  15:    */   
/*  16:    */   public OracleDialect()
/*  17:    */   {
/*  18: 48 */     LOG.deprecatedOracleDialect();
/*  19:    */     
/*  20:    */ 
/*  21: 51 */     registerColumnType(93, "date");
/*  22: 52 */     registerColumnType(1, "char(1)");
/*  23: 53 */     registerColumnType(12, 4000L, "varchar2($l)");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public JoinFragment createOuterJoinFragment()
/*  27:    */   {
/*  28: 58 */     return new OracleJoinFragment();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public CaseFragment createCaseFragment()
/*  32:    */   {
/*  33: 62 */     return new DecodeCaseFragment();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getLimitString(String sql, boolean hasOffset)
/*  37:    */   {
/*  38: 68 */     sql = sql.trim();
/*  39: 69 */     boolean isForUpdate = false;
/*  40: 70 */     if (sql.toLowerCase().endsWith(" for update"))
/*  41:    */     {
/*  42: 71 */       sql = sql.substring(0, sql.length() - 11);
/*  43: 72 */       isForUpdate = true;
/*  44:    */     }
/*  45: 75 */     StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
/*  46: 76 */     if (hasOffset) {
/*  47: 77 */       pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
/*  48:    */     } else {
/*  49: 80 */       pagingSelect.append("select * from ( ");
/*  50:    */     }
/*  51: 82 */     pagingSelect.append(sql);
/*  52: 83 */     if (hasOffset) {
/*  53: 84 */       pagingSelect.append(" ) row_ ) where rownum_ <= ? and rownum_ > ?");
/*  54:    */     } else {
/*  55: 87 */       pagingSelect.append(" ) where rownum <= ?");
/*  56:    */     }
/*  57: 90 */     if (isForUpdate) {
/*  58: 91 */       pagingSelect.append(" for update");
/*  59:    */     }
/*  60: 94 */     return pagingSelect.toString();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getSelectClauseNullString(int sqlType)
/*  64:    */   {
/*  65: 99 */     switch (sqlType)
/*  66:    */     {
/*  67:    */     case 1: 
/*  68:    */     case 12: 
/*  69:102 */       return "to_char(null)";
/*  70:    */     case 91: 
/*  71:    */     case 92: 
/*  72:    */     case 93: 
/*  73:106 */       return "to_date(null)";
/*  74:    */     }
/*  75:108 */     return "to_number(null)";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getCurrentTimestampSelectString()
/*  79:    */   {
/*  80:114 */     return "select sysdate from dual";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getCurrentTimestampSQLFunctionName()
/*  84:    */   {
/*  85:119 */     return "sysdate";
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.OracleDialect
 * JD-Core Version:    0.7.0.1
 */
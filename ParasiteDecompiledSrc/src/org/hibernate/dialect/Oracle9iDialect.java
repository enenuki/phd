/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import org.hibernate.sql.ANSICaseFragment;
/*   4:    */ import org.hibernate.sql.CaseFragment;
/*   5:    */ 
/*   6:    */ public class Oracle9iDialect
/*   7:    */   extends Oracle8iDialect
/*   8:    */ {
/*   9:    */   protected void registerCharacterTypeMappings()
/*  10:    */   {
/*  11: 42 */     registerColumnType(1, "char(1 char)");
/*  12: 43 */     registerColumnType(12, 4000L, "varchar2($l char)");
/*  13: 44 */     registerColumnType(12, "long");
/*  14:    */   }
/*  15:    */   
/*  16:    */   protected void registerDateTimeTypeMappings()
/*  17:    */   {
/*  18: 48 */     registerColumnType(91, "date");
/*  19: 49 */     registerColumnType(92, "date");
/*  20: 50 */     registerColumnType(93, "timestamp");
/*  21:    */   }
/*  22:    */   
/*  23:    */   public CaseFragment createCaseFragment()
/*  24:    */   {
/*  25: 55 */     return new ANSICaseFragment();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getLimitString(String sql, boolean hasOffset)
/*  29:    */   {
/*  30: 59 */     sql = sql.trim();
/*  31: 60 */     String forUpdateClause = null;
/*  32: 61 */     boolean isForUpdate = false;
/*  33: 62 */     int forUpdateIndex = sql.toLowerCase().lastIndexOf("for update");
/*  34: 63 */     if (forUpdateIndex > -1)
/*  35:    */     {
/*  36: 65 */       forUpdateClause = sql.substring(forUpdateIndex);
/*  37: 66 */       sql = sql.substring(0, forUpdateIndex - 1);
/*  38: 67 */       isForUpdate = true;
/*  39:    */     }
/*  40: 70 */     StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
/*  41: 71 */     if (hasOffset) {
/*  42: 72 */       pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
/*  43:    */     } else {
/*  44: 75 */       pagingSelect.append("select * from ( ");
/*  45:    */     }
/*  46: 77 */     pagingSelect.append(sql);
/*  47: 78 */     if (hasOffset) {
/*  48: 79 */       pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
/*  49:    */     } else {
/*  50: 82 */       pagingSelect.append(" ) where rownum <= ?");
/*  51:    */     }
/*  52: 85 */     if (isForUpdate)
/*  53:    */     {
/*  54: 86 */       pagingSelect.append(" ");
/*  55: 87 */       pagingSelect.append(forUpdateClause);
/*  56:    */     }
/*  57: 90 */     return pagingSelect.toString();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getSelectClauseNullString(int sqlType)
/*  61:    */   {
/*  62: 94 */     return getBasicSelectClauseNullString(sqlType);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getCurrentTimestampSelectString()
/*  66:    */   {
/*  67: 98 */     return "select systimestamp from dual";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getCurrentTimestampSQLFunctionName()
/*  71:    */   {
/*  72:103 */     return "current_timestamp";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getForUpdateString()
/*  76:    */   {
/*  77:108 */     return " for update";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getWriteLockString(int timeout)
/*  81:    */   {
/*  82:112 */     if (timeout == 0) {
/*  83:113 */       return " for update nowait";
/*  84:    */     }
/*  85:115 */     if (timeout > 0)
/*  86:    */     {
/*  87:117 */       float seconds = timeout / 1000.0F;
/*  88:118 */       timeout = Math.round(seconds);
/*  89:119 */       return " for update wait " + timeout;
/*  90:    */     }
/*  91:122 */     return " for update";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getReadLockString(int timeout)
/*  95:    */   {
/*  96:126 */     return getWriteLockString(timeout);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean supportsRowValueConstructorSyntaxInInList()
/* 100:    */   {
/* 101:133 */     return true;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean supportsTupleDistinctCounts()
/* 105:    */   {
/* 106:137 */     return false;
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.Oracle9iDialect
 * JD-Core Version:    0.7.0.1
 */
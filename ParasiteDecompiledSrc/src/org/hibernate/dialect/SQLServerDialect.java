/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import org.hibernate.LockMode;
/*   4:    */ import org.hibernate.dialect.function.AnsiTrimEmulationFunction;
/*   5:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*   6:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   7:    */ import org.hibernate.type.StandardBasicTypes;
/*   8:    */ import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;
/*   9:    */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/*  10:    */ 
/*  11:    */ public class SQLServerDialect
/*  12:    */   extends AbstractTransactSQLDialect
/*  13:    */ {
/*  14:    */   public SQLServerDialect()
/*  15:    */   {
/*  16: 43 */     registerColumnType(-3, "image");
/*  17: 44 */     registerColumnType(-3, 8000L, "varbinary($l)");
/*  18: 45 */     registerColumnType(-4, "image");
/*  19: 46 */     registerColumnType(-1, "text");
/*  20:    */     
/*  21: 48 */     registerFunction("second", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datepart(second, ?1)"));
/*  22: 49 */     registerFunction("minute", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datepart(minute, ?1)"));
/*  23: 50 */     registerFunction("hour", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datepart(hour, ?1)"));
/*  24: 51 */     registerFunction("locate", new StandardSQLFunction("charindex", StandardBasicTypes.INTEGER));
/*  25:    */     
/*  26: 53 */     registerFunction("extract", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datepart(?1, ?3)"));
/*  27: 54 */     registerFunction("mod", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "?1 % ?2"));
/*  28: 55 */     registerFunction("bit_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "datalength(?1) * 8"));
/*  29:    */     
/*  30: 57 */     registerFunction("trim", new AnsiTrimEmulationFunction());
/*  31:    */     
/*  32: 59 */     registerKeyword("top");
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getNoColumnsInsertString()
/*  36:    */   {
/*  37: 64 */     return "default values";
/*  38:    */   }
/*  39:    */   
/*  40:    */   static int getAfterSelectInsertPoint(String sql)
/*  41:    */   {
/*  42: 68 */     int selectIndex = sql.toLowerCase().indexOf("select");
/*  43: 69 */     int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
/*  44: 70 */     return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getLimitString(String querySelect, int offset, int limit)
/*  48:    */   {
/*  49: 75 */     if (offset > 0) {
/*  50: 76 */       throw new UnsupportedOperationException("query result offset is not supported");
/*  51:    */     }
/*  52: 78 */     return new StringBuffer(querySelect.length() + 8).append(querySelect).insert(getAfterSelectInsertPoint(querySelect), " top " + limit).toString();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String appendIdentitySelectToInsert(String insertSQL)
/*  56:    */   {
/*  57: 89 */     return insertSQL + " select scope_identity()";
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean supportsLimit()
/*  61:    */   {
/*  62: 94 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean useMaxForLimit()
/*  66:    */   {
/*  67: 99 */     return true;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean supportsLimitOffset()
/*  71:    */   {
/*  72:104 */     return false;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean supportsVariableLimit()
/*  76:    */   {
/*  77:109 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public char closeQuote()
/*  81:    */   {
/*  82:114 */     return ']';
/*  83:    */   }
/*  84:    */   
/*  85:    */   public char openQuote()
/*  86:    */   {
/*  87:119 */     return '[';
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String appendLockHint(LockMode mode, String tableName)
/*  91:    */   {
/*  92:124 */     if ((mode == LockMode.UPGRADE) || (mode == LockMode.UPGRADE_NOWAIT) || (mode == LockMode.PESSIMISTIC_WRITE) || (mode == LockMode.WRITE)) {
/*  93:128 */       return tableName + " with (updlock, rowlock)";
/*  94:    */     }
/*  95:130 */     if (mode == LockMode.PESSIMISTIC_READ) {
/*  96:131 */       return tableName + " with (holdlock, rowlock)";
/*  97:    */     }
/*  98:134 */     return tableName;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getCurrentTimestampSelectString()
/* 102:    */   {
/* 103:142 */     return "select current_timestamp";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean areStringComparisonsCaseInsensitive()
/* 107:    */   {
/* 108:149 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean supportsResultSetPositionQueryMethodsOnForwardOnlyCursor()
/* 112:    */   {
/* 113:154 */     return false;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean supportsCircularCascadeDeleteConstraints()
/* 117:    */   {
/* 118:162 */     return false;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean supportsLobValueChangePropogation()
/* 122:    */   {
/* 123:168 */     return false;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean doesReadCommittedCauseWritersToBlockReaders()
/* 127:    */   {
/* 128:173 */     return false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean doesRepeatableReadCauseReadersToBlockWriters()
/* 132:    */   {
/* 133:178 */     return false;
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected SqlTypeDescriptor getSqlTypeDescriptorOverride(int sqlCode)
/* 137:    */   {
/* 138:188 */     return sqlCode == -6 ? SmallIntTypeDescriptor.INSTANCE : super.getSqlTypeDescriptorOverride(sqlCode);
/* 139:    */   }
/* 140:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.SQLServerDialect
 * JD-Core Version:    0.7.0.1
 */
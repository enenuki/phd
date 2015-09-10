/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.regex.Matcher;
/*   4:    */ import java.util.regex.Pattern;
/*   5:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   6:    */ import org.hibernate.type.StandardBasicTypes;
/*   7:    */ 
/*   8:    */ public class SQLServer2005Dialect
/*   9:    */   extends SQLServerDialect
/*  10:    */ {
/*  11:    */   private static final String SELECT = "select";
/*  12:    */   private static final String FROM = "from";
/*  13:    */   private static final String DISTINCT = "distinct";
/*  14:    */   private static final int MAX_LENGTH = 8000;
/*  15: 47 */   private static final Pattern ALIAS_PATTERN = Pattern.compile("\\sas[^,]+(,?)");
/*  16:    */   
/*  17:    */   public SQLServer2005Dialect()
/*  18:    */   {
/*  19: 53 */     registerColumnType(2004, "varbinary(MAX)");
/*  20: 54 */     registerColumnType(-3, "varbinary(MAX)");
/*  21: 55 */     registerColumnType(-3, 8000L, "varbinary($l)");
/*  22: 56 */     registerColumnType(-4, "varbinary(MAX)");
/*  23:    */     
/*  24: 58 */     registerColumnType(2005, "varchar(MAX)");
/*  25: 59 */     registerColumnType(-1, "varchar(MAX)");
/*  26: 60 */     registerColumnType(12, "varchar(MAX)");
/*  27: 61 */     registerColumnType(12, 8000L, "varchar($l)");
/*  28:    */     
/*  29: 63 */     registerColumnType(-5, "bigint");
/*  30: 64 */     registerColumnType(-7, "bit");
/*  31: 65 */     registerColumnType(16, "bit");
/*  32:    */     
/*  33:    */ 
/*  34: 68 */     registerFunction("row_number", new NoArgSQLFunction("row_number", StandardBasicTypes.INTEGER, true));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean supportsLimitOffset()
/*  38:    */   {
/*  39: 73 */     return true;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean bindLimitParametersFirst()
/*  43:    */   {
/*  44: 78 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean supportsVariableLimit()
/*  48:    */   {
/*  49: 83 */     return true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int convertToFirstRowValue(int zeroBasedFirstResult)
/*  53:    */   {
/*  54: 89 */     return zeroBasedFirstResult + 1;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getLimitString(String query, int offset, int limit)
/*  58:    */   {
/*  59: 95 */     if ((offset > 1) || (limit > 1)) {
/*  60: 96 */       return getLimitString(query, true);
/*  61:    */     }
/*  62: 98 */     return query;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getLimitString(String querySqlString, boolean hasOffset)
/*  66:    */   {
/*  67:121 */     StringBuilder sb = new StringBuilder(querySqlString.trim().toLowerCase());
/*  68:    */     
/*  69:123 */     int orderByIndex = sb.indexOf("order by");
/*  70:124 */     CharSequence orderby = orderByIndex > 0 ? sb.subSequence(orderByIndex, sb.length()) : "ORDER BY CURRENT_TIMESTAMP";
/*  71:128 */     if (orderByIndex > 0) {
/*  72:129 */       sb.delete(orderByIndex, orderByIndex + orderby.length());
/*  73:    */     }
/*  74:133 */     replaceDistinctWithGroupBy(sb);
/*  75:    */     
/*  76:135 */     insertRowNumberFunction(sb, orderby);
/*  77:    */     
/*  78:    */ 
/*  79:138 */     sb.insert(0, "WITH query AS (").append(") SELECT * FROM query ");
/*  80:139 */     sb.append("WHERE __hibernate_row_nr__ >= ? AND __hibernate_row_nr__ < ?");
/*  81:    */     
/*  82:141 */     return sb.toString();
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected static void replaceDistinctWithGroupBy(StringBuilder sql)
/*  86:    */   {
/*  87:152 */     int distinctIndex = sql.indexOf("distinct");
/*  88:153 */     if (distinctIndex > 0)
/*  89:    */     {
/*  90:154 */       sql.delete(distinctIndex, distinctIndex + "distinct".length() + 1);
/*  91:155 */       sql.append(" group by").append(getSelectFieldsWithoutAliases(sql));
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected static CharSequence getSelectFieldsWithoutAliases(StringBuilder sql)
/*  96:    */   {
/*  97:168 */     String select = sql.substring(sql.indexOf("select") + "select".length(), sql.indexOf("from"));
/*  98:    */     
/*  99:    */ 
/* 100:171 */     return stripAliases(select);
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected static String stripAliases(String str)
/* 104:    */   {
/* 105:182 */     Matcher matcher = ALIAS_PATTERN.matcher(str);
/* 106:183 */     return matcher.replaceAll("$1");
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected static void insertRowNumberFunction(StringBuilder sql, CharSequence orderby)
/* 110:    */   {
/* 111:194 */     int selectEndIndex = sql.indexOf("from");
/* 112:    */     
/* 113:    */ 
/* 114:197 */     sql.insert(selectEndIndex - 1, ", ROW_NUMBER() OVER (" + orderby + ") as __hibernate_row_nr__");
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.SQLServer2005Dialect
 * JD-Core Version:    0.7.0.1
 */
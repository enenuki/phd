/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*   5:    */ import org.hibernate.type.StandardBasicTypes;
/*   6:    */ 
/*   7:    */ public class InterbaseDialect
/*   8:    */   extends Dialect
/*   9:    */ {
/*  10:    */   public InterbaseDialect()
/*  11:    */   {
/*  12: 40 */     registerColumnType(-7, "smallint");
/*  13: 41 */     registerColumnType(-5, "numeric(18,0)");
/*  14: 42 */     registerColumnType(5, "smallint");
/*  15: 43 */     registerColumnType(-6, "smallint");
/*  16: 44 */     registerColumnType(4, "integer");
/*  17: 45 */     registerColumnType(1, "char(1)");
/*  18: 46 */     registerColumnType(12, "varchar($l)");
/*  19: 47 */     registerColumnType(6, "float");
/*  20: 48 */     registerColumnType(8, "double precision");
/*  21: 49 */     registerColumnType(91, "date");
/*  22: 50 */     registerColumnType(92, "time");
/*  23: 51 */     registerColumnType(93, "timestamp");
/*  24: 52 */     registerColumnType(-3, "blob");
/*  25: 53 */     registerColumnType(2, "numeric($p,$s)");
/*  26: 54 */     registerColumnType(2004, "blob");
/*  27: 55 */     registerColumnType(2005, "blob sub_type 1");
/*  28:    */     
/*  29: 57 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "||", ")"));
/*  30:    */     
/*  31: 59 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "0");
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getAddColumnString()
/*  35:    */   {
/*  36: 63 */     return "add";
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getSequenceNextValString(String sequenceName)
/*  40:    */   {
/*  41: 67 */     return "select " + getSelectSequenceNextValString(sequenceName) + " from RDB$DATABASE";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getSelectSequenceNextValString(String sequenceName)
/*  45:    */   {
/*  46: 71 */     return "gen_id( " + sequenceName + ", 1 )";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getCreateSequenceString(String sequenceName)
/*  50:    */   {
/*  51: 75 */     return "create generator " + sequenceName;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getDropSequenceString(String sequenceName)
/*  55:    */   {
/*  56: 79 */     return "delete from RDB$GENERATORS where RDB$GENERATOR_NAME = '" + sequenceName.toUpperCase() + "'";
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getQuerySequencesString()
/*  60:    */   {
/*  61: 83 */     return "select RDB$GENERATOR_NAME from RDB$GENERATORS";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getForUpdateString()
/*  65:    */   {
/*  66: 87 */     return " with lock";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getForUpdateString(String aliases)
/*  70:    */   {
/*  71: 90 */     return " for update of " + aliases + " with lock";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean supportsSequences()
/*  75:    */   {
/*  76: 94 */     return true;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean supportsLimit()
/*  80:    */   {
/*  81: 98 */     return true;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getLimitString(String sql, boolean hasOffset)
/*  85:    */   {
/*  86:102 */     return sql.length() + 15 + sql + (hasOffset ? " rows ? to ?" : " rows ?");
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean bindLimitParametersFirst()
/*  90:    */   {
/*  91:109 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean bindLimitParametersInReverseOrder()
/*  95:    */   {
/*  96:113 */     return false;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getCurrentTimestampCallString()
/* 100:    */   {
/* 101:120 */     return "{?= call CURRENT_TIMESTAMP }";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 105:    */   {
/* 106:125 */     return true;
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.InterbaseDialect
 * JD-Core Version:    0.7.0.1
 */
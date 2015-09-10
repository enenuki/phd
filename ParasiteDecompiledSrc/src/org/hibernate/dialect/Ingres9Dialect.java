/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   4:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*   5:    */ import org.hibernate.type.StandardBasicTypes;
/*   6:    */ 
/*   7:    */ public class Ingres9Dialect
/*   8:    */   extends IngresDialect
/*   9:    */ {
/*  10:    */   public Ingres9Dialect()
/*  11:    */   {
/*  12: 51 */     registerDateTimeFunctions();
/*  13: 52 */     registerDateTimeColumnTypes();
/*  14: 53 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "||", ")"));
/*  15:    */   }
/*  16:    */   
/*  17:    */   protected void registerDateTimeFunctions()
/*  18:    */   {
/*  19: 60 */     registerFunction("current_time", new NoArgSQLFunction("current_time", StandardBasicTypes.TIME, false));
/*  20: 61 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
/*  21: 62 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected void registerDateTimeColumnTypes()
/*  25:    */   {
/*  26: 69 */     registerColumnType(91, "ansidate");
/*  27: 70 */     registerColumnType(93, "timestamp(9) with time zone");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean supportsOuterJoinForUpdate()
/*  31:    */   {
/*  32: 82 */     return false;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean forUpdateOfColumns()
/*  36:    */   {
/*  37: 92 */     return true;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getIdentitySelectString()
/*  41:    */   {
/*  42:104 */     return "select last_identity()";
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getQuerySequencesString()
/*  46:    */   {
/*  47:114 */     return "select seq_name from iisequences";
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean supportsPooledSequences()
/*  51:    */   {
/*  52:126 */     return true;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isCurrentTimestampSelectStringCallable()
/*  56:    */   {
/*  57:140 */     return false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean supportsCurrentTimestampSelection()
/*  61:    */   {
/*  62:150 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getCurrentTimestampSelectString()
/*  66:    */   {
/*  67:160 */     return "select current_timestamp";
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getCurrentTimestampSQLFunctionName()
/*  71:    */   {
/*  72:167 */     return "current_timestamp";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean supportsUnionAll()
/*  76:    */   {
/*  77:179 */     return true;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean doesReadCommittedCauseWritersToBlockReaders()
/*  81:    */   {
/*  82:191 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean doesRepeatableReadCauseReadersToBlockWriters()
/*  86:    */   {
/*  87:201 */     return true;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean supportsLimitOffset()
/*  91:    */   {
/*  92:213 */     return true;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean supportsVariableLimit()
/*  96:    */   {
/*  97:223 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean useMaxForLimit()
/* 101:    */   {
/* 102:231 */     return false;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getLimitString(String querySelect, int offset, int limit)
/* 106:    */   {
/* 107:240 */     StringBuffer soff = new StringBuffer(" offset " + offset);
/* 108:241 */     StringBuffer slim = new StringBuffer(" fetch first " + limit + " rows only");
/* 109:242 */     StringBuffer sb = new StringBuffer(querySelect.length() + soff.length() + slim.length()).append(querySelect);
/* 110:244 */     if (offset > 0) {
/* 111:245 */       sb.append(soff);
/* 112:    */     }
/* 113:247 */     if (limit > 0) {
/* 114:248 */       sb.append(slim);
/* 115:    */     }
/* 116:250 */     return sb.toString();
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.Ingres9Dialect
 * JD-Core Version:    0.7.0.1
 */
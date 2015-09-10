/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import org.hibernate.dialect.Dialect;
/*   4:    */ 
/*   5:    */ public class QueryJoinFragment
/*   6:    */   extends JoinFragment
/*   7:    */ {
/*   8: 35 */   private StringBuffer afterFrom = new StringBuffer();
/*   9: 36 */   private StringBuffer afterWhere = new StringBuffer();
/*  10:    */   private Dialect dialect;
/*  11:    */   private boolean useThetaStyleInnerJoins;
/*  12:    */   
/*  13:    */   public QueryJoinFragment(Dialect dialect, boolean useThetaStyleInnerJoins)
/*  14:    */   {
/*  15: 41 */     this.dialect = dialect;
/*  16: 42 */     this.useThetaStyleInnerJoins = useThetaStyleInnerJoins;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType)
/*  20:    */   {
/*  21: 46 */     addJoin(tableName, alias, alias, fkColumns, pkColumns, joinType, null);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType, String on)
/*  25:    */   {
/*  26: 50 */     addJoin(tableName, alias, alias, fkColumns, pkColumns, joinType, on);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private void addJoin(String tableName, String alias, String concreteAlias, String[] fkColumns, String[] pkColumns, JoinType joinType, String on)
/*  30:    */   {
/*  31: 54 */     if ((!this.useThetaStyleInnerJoins) || (joinType != JoinType.INNER_JOIN))
/*  32:    */     {
/*  33: 55 */       JoinFragment jf = this.dialect.createOuterJoinFragment();
/*  34: 56 */       jf.addJoin(tableName, alias, fkColumns, pkColumns, joinType, on);
/*  35: 57 */       addFragment(jf);
/*  36:    */     }
/*  37:    */     else
/*  38:    */     {
/*  39: 60 */       addCrossJoin(tableName, alias);
/*  40: 61 */       addCondition(concreteAlias, fkColumns, pkColumns);
/*  41: 62 */       addCondition(on);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String toFromFragmentString()
/*  46:    */   {
/*  47: 67 */     return this.afterFrom.toString();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String toWhereFragmentString()
/*  51:    */   {
/*  52: 71 */     return this.afterWhere.toString();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void addJoins(String fromFragment, String whereFragment)
/*  56:    */   {
/*  57: 75 */     this.afterFrom.append(fromFragment);
/*  58: 76 */     this.afterWhere.append(whereFragment);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public JoinFragment copy()
/*  62:    */   {
/*  63: 80 */     QueryJoinFragment copy = new QueryJoinFragment(this.dialect, this.useThetaStyleInnerJoins);
/*  64: 81 */     copy.afterFrom = new StringBuffer(this.afterFrom.toString());
/*  65: 82 */     copy.afterWhere = new StringBuffer(this.afterWhere.toString());
/*  66: 83 */     return copy;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void addCondition(String alias, String[] columns, String condition)
/*  70:    */   {
/*  71: 87 */     for (int i = 0; i < columns.length; i++) {
/*  72: 88 */       this.afterWhere.append(" and ").append(alias).append('.').append(columns[i]).append(condition);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void addCrossJoin(String tableName, String alias)
/*  77:    */   {
/*  78: 98 */     this.afterFrom.append(", ").append(tableName).append(' ').append(alias);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void addCondition(String alias, String[] fkColumns, String[] pkColumns)
/*  82:    */   {
/*  83:105 */     for (int j = 0; j < fkColumns.length; j++) {
/*  84:106 */       this.afterWhere.append(" and ").append(fkColumns[j]).append('=').append(alias).append('.').append(pkColumns[j]);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean addCondition(String condition)
/*  89:    */   {
/*  90:123 */     if ((this.afterFrom.toString().indexOf(condition.trim()) < 0) && (this.afterWhere.toString().indexOf(condition.trim()) < 0))
/*  91:    */     {
/*  92:127 */       if (!condition.startsWith(" and ")) {
/*  93:128 */         this.afterWhere.append(" and ");
/*  94:    */       }
/*  95:130 */       this.afterWhere.append(condition);
/*  96:131 */       return true;
/*  97:    */     }
/*  98:134 */     return false;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void addFromFragmentString(String fromFragmentString)
/* 102:    */   {
/* 103:139 */     this.afterFrom.append(fromFragmentString);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void clearWherePart()
/* 107:    */   {
/* 108:143 */     this.afterWhere.setLength(0);
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.QueryJoinFragment
 * JD-Core Version:    0.7.0.1
 */
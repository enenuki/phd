/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ public class Sybase11JoinFragment
/*   4:    */   extends JoinFragment
/*   5:    */ {
/*   6: 37 */   private StringBuffer afterFrom = new StringBuffer();
/*   7: 38 */   private StringBuffer afterWhere = new StringBuffer();
/*   8:    */   
/*   9:    */   public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType)
/*  10:    */   {
/*  11: 42 */     addCrossJoin(tableName, alias);
/*  12: 44 */     for (int j = 0; j < fkColumns.length; j++)
/*  13:    */     {
/*  14: 46 */       if (joinType == JoinType.FULL_JOIN) {
/*  15: 46 */         throw new UnsupportedOperationException();
/*  16:    */       }
/*  17: 48 */       this.afterWhere.append(" and ").append(fkColumns[j]).append(" ");
/*  18: 52 */       if (joinType == JoinType.LEFT_OUTER_JOIN) {
/*  19: 52 */         this.afterWhere.append("*");
/*  20:    */       }
/*  21: 53 */       this.afterWhere.append('=');
/*  22: 54 */       if (joinType == JoinType.RIGHT_OUTER_JOIN) {
/*  23: 54 */         this.afterWhere.append("*");
/*  24:    */       }
/*  25: 56 */       this.afterWhere.append(" ").append(alias).append('.').append(pkColumns[j]);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String toFromFragmentString()
/*  30:    */   {
/*  31: 65 */     return this.afterFrom.toString();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toWhereFragmentString()
/*  35:    */   {
/*  36: 69 */     return this.afterWhere.toString();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addJoins(String fromFragment, String whereFragment)
/*  40:    */   {
/*  41: 73 */     this.afterFrom.append(fromFragment);
/*  42: 74 */     this.afterWhere.append(whereFragment);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public JoinFragment copy()
/*  46:    */   {
/*  47: 78 */     Sybase11JoinFragment copy = new Sybase11JoinFragment();
/*  48: 79 */     copy.afterFrom = new StringBuffer(this.afterFrom.toString());
/*  49: 80 */     copy.afterWhere = new StringBuffer(this.afterWhere.toString());
/*  50: 81 */     return copy;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void addCondition(String alias, String[] columns, String condition)
/*  54:    */   {
/*  55: 85 */     for (int i = 0; i < columns.length; i++) {
/*  56: 86 */       this.afterWhere.append(" and ").append(alias).append('.').append(columns[i]).append(condition);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addCrossJoin(String tableName, String alias)
/*  61:    */   {
/*  62: 95 */     this.afterFrom.append(", ").append(tableName).append(' ').append(alias);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void addCondition(String alias, String[] fkColumns, String[] pkColumns)
/*  66:    */   {
/*  67:102 */     throw new UnsupportedOperationException();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean addCondition(String condition)
/*  71:    */   {
/*  72:107 */     return addCondition(this.afterWhere, condition);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void addFromFragmentString(String fromFragmentString)
/*  76:    */   {
/*  77:112 */     this.afterFrom.append(fromFragmentString);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType, String on)
/*  81:    */   {
/*  82:117 */     addJoin(tableName, alias, fkColumns, pkColumns, joinType);
/*  83:118 */     addCondition(on);
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.Sybase11JoinFragment
 * JD-Core Version:    0.7.0.1
 */
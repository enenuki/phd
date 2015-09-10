/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import org.hibernate.AssertionFailure;
/*   4:    */ 
/*   5:    */ public class ANSIJoinFragment
/*   6:    */   extends JoinFragment
/*   7:    */ {
/*   8: 35 */   private StringBuffer buffer = new StringBuffer();
/*   9: 36 */   private StringBuffer conditions = new StringBuffer();
/*  10:    */   
/*  11:    */   public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType)
/*  12:    */   {
/*  13: 39 */     addJoin(tableName, alias, fkColumns, pkColumns, joinType, null);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType, String on)
/*  17:    */   {
/*  18:    */     String joinString;
/*  19: 44 */     switch (1.$SwitchMap$org$hibernate$sql$JoinType[joinType.ordinal()])
/*  20:    */     {
/*  21:    */     case 1: 
/*  22: 46 */       joinString = " inner join ";
/*  23: 47 */       break;
/*  24:    */     case 2: 
/*  25: 49 */       joinString = " left outer join ";
/*  26: 50 */       break;
/*  27:    */     case 3: 
/*  28: 52 */       joinString = " right outer join ";
/*  29: 53 */       break;
/*  30:    */     case 4: 
/*  31: 55 */       joinString = " full outer join ";
/*  32: 56 */       break;
/*  33:    */     default: 
/*  34: 58 */       throw new AssertionFailure("undefined join type");
/*  35:    */     }
/*  36: 61 */     this.buffer.append(joinString).append(tableName).append(' ').append(alias).append(" on ");
/*  37: 68 */     for (int j = 0; j < fkColumns.length; j++)
/*  38:    */     {
/*  39: 72 */       this.buffer.append(fkColumns[j]).append('=').append(alias).append('.').append(pkColumns[j]);
/*  40: 77 */       if (j < fkColumns.length - 1) {
/*  41: 77 */         this.buffer.append(" and ");
/*  42:    */       }
/*  43:    */     }
/*  44: 80 */     addCondition(this.buffer, on);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String toFromFragmentString()
/*  48:    */   {
/*  49: 85 */     return this.buffer.toString();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String toWhereFragmentString()
/*  53:    */   {
/*  54: 89 */     return this.conditions.toString();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void addJoins(String fromFragment, String whereFragment)
/*  58:    */   {
/*  59: 93 */     this.buffer.append(fromFragment);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public JoinFragment copy()
/*  63:    */   {
/*  64: 98 */     ANSIJoinFragment copy = new ANSIJoinFragment();
/*  65: 99 */     copy.buffer = new StringBuffer(this.buffer.toString());
/*  66:100 */     return copy;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void addCondition(String alias, String[] columns, String condition)
/*  70:    */   {
/*  71:104 */     for (int i = 0; i < columns.length; i++) {
/*  72:105 */       this.conditions.append(" and ").append(alias).append('.').append(columns[i]).append(condition);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void addCrossJoin(String tableName, String alias)
/*  77:    */   {
/*  78:114 */     this.buffer.append(", ").append(tableName).append(' ').append(alias);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void addCondition(String alias, String[] fkColumns, String[] pkColumns)
/*  82:    */   {
/*  83:121 */     throw new UnsupportedOperationException();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean addCondition(String condition)
/*  87:    */   {
/*  88:126 */     return addCondition(this.conditions, condition);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void addFromFragmentString(String fromFragmentString)
/*  92:    */   {
/*  93:130 */     this.buffer.append(fromFragmentString);
/*  94:    */   }
/*  95:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ANSIJoinFragment
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Set;
/*   5:    */ 
/*   6:    */ public class OracleJoinFragment
/*   7:    */   extends JoinFragment
/*   8:    */ {
/*   9: 36 */   private StringBuffer afterFrom = new StringBuffer();
/*  10: 37 */   private StringBuffer afterWhere = new StringBuffer();
/*  11:    */   
/*  12:    */   public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType)
/*  13:    */   {
/*  14: 41 */     addCrossJoin(tableName, alias);
/*  15: 43 */     for (int j = 0; j < fkColumns.length; j++)
/*  16:    */     {
/*  17: 44 */       setHasThetaJoins(true);
/*  18: 45 */       this.afterWhere.append(" and ").append(fkColumns[j]);
/*  19: 47 */       if ((joinType == JoinType.RIGHT_OUTER_JOIN) || (joinType == JoinType.FULL_JOIN)) {
/*  20: 47 */         this.afterWhere.append("(+)");
/*  21:    */       }
/*  22: 48 */       this.afterWhere.append('=').append(alias).append('.').append(pkColumns[j]);
/*  23: 52 */       if ((joinType == JoinType.LEFT_OUTER_JOIN) || (joinType == JoinType.FULL_JOIN)) {
/*  24: 52 */         this.afterWhere.append("(+)");
/*  25:    */       }
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String toFromFragmentString()
/*  30:    */   {
/*  31: 58 */     return this.afterFrom.toString();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toWhereFragmentString()
/*  35:    */   {
/*  36: 62 */     return this.afterWhere.toString();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addJoins(String fromFragment, String whereFragment)
/*  40:    */   {
/*  41: 66 */     this.afterFrom.append(fromFragment);
/*  42: 67 */     this.afterWhere.append(whereFragment);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public JoinFragment copy()
/*  46:    */   {
/*  47: 71 */     OracleJoinFragment copy = new OracleJoinFragment();
/*  48: 72 */     copy.afterFrom = new StringBuffer(this.afterFrom.toString());
/*  49: 73 */     copy.afterWhere = new StringBuffer(this.afterWhere.toString());
/*  50: 74 */     return copy;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void addCondition(String alias, String[] columns, String condition)
/*  54:    */   {
/*  55: 78 */     for (int i = 0; i < columns.length; i++) {
/*  56: 79 */       this.afterWhere.append(" and ").append(alias).append('.').append(columns[i]).append(condition);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addCrossJoin(String tableName, String alias)
/*  61:    */   {
/*  62: 88 */     this.afterFrom.append(", ").append(tableName).append(' ').append(alias);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void addCondition(String alias, String[] fkColumns, String[] pkColumns)
/*  66:    */   {
/*  67: 95 */     throw new UnsupportedOperationException();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean addCondition(String condition)
/*  71:    */   {
/*  72: 99 */     return addCondition(this.afterWhere, condition);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void addFromFragmentString(String fromFragmentString)
/*  76:    */   {
/*  77:103 */     this.afterFrom.append(fromFragmentString);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void addJoin(String tableName, String alias, String[] fkColumns, String[] pkColumns, JoinType joinType, String on)
/*  81:    */   {
/*  82:108 */     addJoin(tableName, alias, fkColumns, pkColumns, joinType);
/*  83:109 */     if (joinType == JoinType.INNER_JOIN) {
/*  84:110 */       addCondition(on);
/*  85:112 */     } else if (joinType == JoinType.LEFT_OUTER_JOIN) {
/*  86:113 */       addLeftOuterJoinCondition(on);
/*  87:    */     } else {
/*  88:116 */       throw new UnsupportedOperationException("join type not supported by OracleJoinFragment (use Oracle9iDialect/Oracle10gDialect)");
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private void addLeftOuterJoinCondition(String on)
/*  93:    */   {
/*  94:129 */     StringBuffer buf = new StringBuffer(on);
/*  95:130 */     for (int i = 0; i < buf.length(); i++)
/*  96:    */     {
/*  97:131 */       char character = buf.charAt(i);
/*  98:132 */       boolean isInsertPoint = (OPERATORS.contains(new Character(character))) || ((character == ' ') && (buf.length() > i + 3) && ("is ".equals(buf.substring(i + 1, i + 4))));
/*  99:134 */       if (isInsertPoint)
/* 100:    */       {
/* 101:135 */         buf.insert(i, "(+)");
/* 102:136 */         i += 3;
/* 103:    */       }
/* 104:    */     }
/* 105:139 */     addCondition(buf.toString());
/* 106:    */   }
/* 107:    */   
/* 108:142 */   private static final Set OPERATORS = new HashSet();
/* 109:    */   
/* 110:    */   static
/* 111:    */   {
/* 112:145 */     OPERATORS.add(new Character('='));
/* 113:146 */     OPERATORS.add(new Character('<'));
/* 114:147 */     OPERATORS.add(new Character('>'));
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.OracleJoinFragment
 * JD-Core Version:    0.7.0.1
 */
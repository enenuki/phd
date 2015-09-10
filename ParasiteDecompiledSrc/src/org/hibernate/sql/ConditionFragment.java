/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  4:   */ 
/*  5:   */ public class ConditionFragment
/*  6:   */ {
/*  7:   */   private String tableAlias;
/*  8:   */   private String[] lhs;
/*  9:   */   private String[] rhs;
/* 10:36 */   private String op = "=";
/* 11:   */   
/* 12:   */   public ConditionFragment setOp(String op)
/* 13:   */   {
/* 14:43 */     this.op = op;
/* 15:44 */     return this;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ConditionFragment setTableAlias(String tableAlias)
/* 19:   */   {
/* 20:52 */     this.tableAlias = tableAlias;
/* 21:53 */     return this;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ConditionFragment setCondition(String[] lhs, String[] rhs)
/* 25:   */   {
/* 26:57 */     this.lhs = lhs;
/* 27:58 */     this.rhs = rhs;
/* 28:59 */     return this;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public ConditionFragment setCondition(String[] lhs, String rhs)
/* 32:   */   {
/* 33:63 */     this.lhs = lhs;
/* 34:64 */     this.rhs = ArrayHelper.fillArray(rhs, lhs.length);
/* 35:65 */     return this;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String toFragmentString()
/* 39:   */   {
/* 40:69 */     StringBuffer buf = new StringBuffer(this.lhs.length * 10);
/* 41:70 */     for (int i = 0; i < this.lhs.length; i++)
/* 42:   */     {
/* 43:71 */       buf.append(this.tableAlias).append('.').append(this.lhs[i]).append(this.op).append(this.rhs[i]);
/* 44:76 */       if (i < this.lhs.length - 1) {
/* 45:76 */         buf.append(" and ");
/* 46:   */       }
/* 47:   */     }
/* 48:78 */     return buf.toString();
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ConditionFragment
 * JD-Core Version:    0.7.0.1
 */
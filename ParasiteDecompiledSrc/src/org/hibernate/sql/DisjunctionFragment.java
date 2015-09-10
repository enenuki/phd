/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ public class DisjunctionFragment
/*  4:   */ {
/*  5:34 */   private StringBuffer buffer = new StringBuffer();
/*  6:   */   
/*  7:   */   public DisjunctionFragment addCondition(ConditionFragment fragment)
/*  8:   */   {
/*  9:37 */     if (this.buffer.length() > 0) {
/* 10:37 */       this.buffer.append(" or ");
/* 11:   */     }
/* 12:38 */     this.buffer.append("(").append(fragment.toFragmentString()).append(")");
/* 13:   */     
/* 14:   */ 
/* 15:41 */     return this;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String toFragmentString()
/* 19:   */   {
/* 20:45 */     return this.buffer.toString();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.DisjunctionFragment
 * JD-Core Version:    0.7.0.1
 */
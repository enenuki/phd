/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public enum JoinType
/*  6:   */ {
/*  7:10 */   NONE(-666),  INNER_JOIN(0),  LEFT_OUTER_JOIN(1),  RIGHT_OUTER_JOIN(2),  FULL_JOIN(4);
/*  8:   */   
/*  9:   */   private int joinTypeValue;
/* 10:   */   
/* 11:   */   private JoinType(int joinTypeValue)
/* 12:   */   {
/* 13:18 */     this.joinTypeValue = joinTypeValue;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int getJoinTypeValue()
/* 17:   */   {
/* 18:22 */     return this.joinTypeValue;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static JoinType parse(int joinType)
/* 22:   */   {
/* 23:26 */     if (joinType < 0) {
/* 24:27 */       return NONE;
/* 25:   */     }
/* 26:29 */     switch (joinType)
/* 27:   */     {
/* 28:   */     case 0: 
/* 29:31 */       return INNER_JOIN;
/* 30:   */     case 1: 
/* 31:33 */       return LEFT_OUTER_JOIN;
/* 32:   */     case 2: 
/* 33:35 */       return RIGHT_OUTER_JOIN;
/* 34:   */     case 4: 
/* 35:37 */       return FULL_JOIN;
/* 36:   */     }
/* 37:39 */     throw new HibernateException("unknown join type: " + joinType);
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.JoinType
 * JD-Core Version:    0.7.0.1
 */
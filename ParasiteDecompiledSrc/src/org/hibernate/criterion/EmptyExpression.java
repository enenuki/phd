/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ public class EmptyExpression
/*  4:   */   extends AbstractEmptinessExpression
/*  5:   */   implements Criterion
/*  6:   */ {
/*  7:   */   protected EmptyExpression(String propertyName)
/*  8:   */   {
/*  9:34 */     super(propertyName);
/* 10:   */   }
/* 11:   */   
/* 12:   */   protected boolean excludeEmpty()
/* 13:   */   {
/* 14:38 */     return false;
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.EmptyExpression
 * JD-Core Version:    0.7.0.1
 */
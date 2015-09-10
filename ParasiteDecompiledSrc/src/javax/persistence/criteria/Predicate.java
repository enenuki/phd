/*  1:   */ package javax.persistence.criteria;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ 
/*  5:   */ public abstract interface Predicate
/*  6:   */   extends Expression<Boolean>
/*  7:   */ {
/*  8:   */   public abstract BooleanOperator getOperator();
/*  9:   */   
/* 10:   */   public abstract boolean isNegated();
/* 11:   */   
/* 12:   */   public abstract List<Expression<Boolean>> getExpressions();
/* 13:   */   
/* 14:   */   public abstract Predicate not();
/* 15:   */   
/* 16:   */   public static enum BooleanOperator
/* 17:   */   {
/* 18:33 */     AND,  OR;
/* 19:   */     
/* 20:   */     private BooleanOperator() {}
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.Predicate
 * JD-Core Version:    0.7.0.1
 */
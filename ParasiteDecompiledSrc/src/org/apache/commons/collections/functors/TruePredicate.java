/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Predicate;
/*  5:   */ 
/*  6:   */ public final class TruePredicate
/*  7:   */   implements Predicate, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 3374767158756189740L;
/* 10:37 */   public static final Predicate INSTANCE = new TruePredicate();
/* 11:   */   
/* 12:   */   public static Predicate getInstance()
/* 13:   */   {
/* 14:46 */     return INSTANCE;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean evaluate(Object object)
/* 18:   */   {
/* 19:63 */     return true;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.TruePredicate
 * JD-Core Version:    0.7.0.1
 */
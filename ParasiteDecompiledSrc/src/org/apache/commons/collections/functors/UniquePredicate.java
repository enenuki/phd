/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.HashSet;
/*  5:   */ import java.util.Set;
/*  6:   */ import org.apache.commons.collections.Predicate;
/*  7:   */ 
/*  8:   */ public final class UniquePredicate
/*  9:   */   implements Predicate, Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -3319417438027438040L;
/* 12:40 */   private final Set iSet = new HashSet();
/* 13:   */   
/* 14:   */   public static Predicate getInstance()
/* 15:   */   {
/* 16:49 */     return new UniquePredicate();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean evaluate(Object object)
/* 20:   */   {
/* 21:68 */     return this.iSet.add(object);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.UniquePredicate
 * JD-Core Version:    0.7.0.1
 */
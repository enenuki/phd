/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Predicate;
/*  5:   */ 
/*  6:   */ public final class EqualPredicate
/*  7:   */   implements Predicate, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 5633766978029907089L;
/* 10:   */   private final Object iValue;
/* 11:   */   
/* 12:   */   public static Predicate getInstance(Object object)
/* 13:   */   {
/* 14:48 */     if (object == null) {
/* 15:49 */       return NullPredicate.INSTANCE;
/* 16:   */     }
/* 17:51 */     return new EqualPredicate(object);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public EqualPredicate(Object object)
/* 21:   */   {
/* 22:62 */     this.iValue = object;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean evaluate(Object object)
/* 26:   */   {
/* 27:72 */     return this.iValue.equals(object);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Object getValue()
/* 31:   */   {
/* 32:82 */     return this.iValue;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.EqualPredicate
 * JD-Core Version:    0.7.0.1
 */
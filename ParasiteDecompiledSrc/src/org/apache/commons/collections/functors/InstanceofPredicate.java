/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Predicate;
/*  5:   */ 
/*  6:   */ public final class InstanceofPredicate
/*  7:   */   implements Predicate, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -6682656911025165584L;
/* 10:   */   private final Class iType;
/* 11:   */   
/* 12:   */   public static Predicate getInstance(Class type)
/* 13:   */   {
/* 14:48 */     if (type == null) {
/* 15:49 */       throw new IllegalArgumentException("The type to check instanceof must not be null");
/* 16:   */     }
/* 17:51 */     return new InstanceofPredicate(type);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public InstanceofPredicate(Class type)
/* 21:   */   {
/* 22:62 */     this.iType = type;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean evaluate(Object object)
/* 26:   */   {
/* 27:72 */     return this.iType.isInstance(object);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Class getType()
/* 31:   */   {
/* 32:82 */     return this.iType;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.InstanceofPredicate
 * JD-Core Version:    0.7.0.1
 */
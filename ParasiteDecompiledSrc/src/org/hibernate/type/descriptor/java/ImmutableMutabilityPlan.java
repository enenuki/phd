/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class ImmutableMutabilityPlan<T>
/*  6:   */   implements MutabilityPlan<T>
/*  7:   */ {
/*  8:33 */   public static final ImmutableMutabilityPlan INSTANCE = new ImmutableMutabilityPlan();
/*  9:   */   
/* 10:   */   public boolean isMutable()
/* 11:   */   {
/* 12:39 */     return false;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public T deepCopy(T value)
/* 16:   */   {
/* 17:46 */     return value;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Serializable disassemble(T value)
/* 21:   */   {
/* 22:53 */     return (Serializable)value;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public T assemble(Serializable cached)
/* 26:   */   {
/* 27:61 */     return cached;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.ImmutableMutabilityPlan
 * JD-Core Version:    0.7.0.1
 */
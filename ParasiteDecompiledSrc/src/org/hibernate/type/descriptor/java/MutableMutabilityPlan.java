/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public abstract class MutableMutabilityPlan<T>
/*  6:   */   implements MutabilityPlan<T>
/*  7:   */ {
/*  8:   */   public boolean isMutable()
/*  9:   */   {
/* 10:38 */     return true;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Serializable disassemble(T value)
/* 14:   */   {
/* 15:45 */     return (Serializable)deepCopy(value);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public T assemble(Serializable cached)
/* 19:   */   {
/* 20:53 */     return deepCopy(cached);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public final T deepCopy(T value)
/* 24:   */   {
/* 25:57 */     return value == null ? null : deepCopyNotNull(value);
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected abstract T deepCopyNotNull(T paramT);
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.MutableMutabilityPlan
 * JD-Core Version:    0.7.0.1
 */
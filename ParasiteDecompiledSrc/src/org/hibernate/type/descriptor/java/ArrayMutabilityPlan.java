/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Array;
/*  4:   */ 
/*  5:   */ public class ArrayMutabilityPlan<T>
/*  6:   */   extends MutableMutabilityPlan<T>
/*  7:   */ {
/*  8:34 */   public static final ArrayMutabilityPlan INSTANCE = new ArrayMutabilityPlan();
/*  9:   */   
/* 10:   */   public T deepCopyNotNull(T value)
/* 11:   */   {
/* 12:38 */     if (!value.getClass().isArray()) {
/* 13:40 */       throw new IllegalArgumentException("Value was not an array [" + value.getClass().getName() + "]");
/* 14:   */     }
/* 15:42 */     int length = Array.getLength(value);
/* 16:43 */     T copy = Array.newInstance(value.getClass().getComponentType(), length);
/* 17:44 */     System.arraycopy(value, 0, copy, 0, length);
/* 18:45 */     return copy;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.ArrayMutabilityPlan
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.apache.commons.collections.buffer;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.Buffer;
/*  4:   */ import org.apache.commons.collections.functors.InstanceofPredicate;
/*  5:   */ 
/*  6:   */ public class TypedBuffer
/*  7:   */ {
/*  8:   */   public static Buffer decorate(Buffer buffer, Class type)
/*  9:   */   {
/* 10:51 */     return new PredicatedBuffer(buffer, InstanceofPredicate.getInstance(type));
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.TypedBuffer
 * JD-Core Version:    0.7.0.1
 */
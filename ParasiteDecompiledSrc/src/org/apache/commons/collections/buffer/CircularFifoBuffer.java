/*  1:   */ package org.apache.commons.collections.buffer;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ 
/*  5:   */ public class CircularFifoBuffer
/*  6:   */   extends BoundedFifoBuffer
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = -8423413834657610406L;
/*  9:   */   
/* 10:   */   public CircularFifoBuffer()
/* 11:   */   {
/* 12:58 */     super(32);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public CircularFifoBuffer(int size)
/* 16:   */   {
/* 17:68 */     super(size);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public CircularFifoBuffer(Collection coll)
/* 21:   */   {
/* 22:79 */     super(coll);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean add(Object element)
/* 26:   */   {
/* 27:90 */     if (isFull()) {
/* 28:91 */       remove();
/* 29:   */     }
/* 30:93 */     return super.add(element);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.CircularFifoBuffer
 * JD-Core Version:    0.7.0.1
 */
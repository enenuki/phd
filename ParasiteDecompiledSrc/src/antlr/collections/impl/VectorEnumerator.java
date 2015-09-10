/*  1:   */ package antlr.collections.impl;
/*  2:   */ 
/*  3:   */ import java.util.Enumeration;
/*  4:   */ import java.util.NoSuchElementException;
/*  5:   */ 
/*  6:   */ class VectorEnumerator
/*  7:   */   implements Enumeration
/*  8:   */ {
/*  9:   */   Vector vector;
/* 10:   */   int i;
/* 11:   */   
/* 12:   */   VectorEnumerator(Vector paramVector)
/* 13:   */   {
/* 14:23 */     this.vector = paramVector;
/* 15:24 */     this.i = 0;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean hasMoreElements()
/* 19:   */   {
/* 20:28 */     synchronized (this.vector)
/* 21:   */     {
/* 22:29 */       return this.i <= this.vector.lastElement;
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object nextElement()
/* 27:   */   {
/* 28:34 */     synchronized (this.vector)
/* 29:   */     {
/* 30:35 */       if (this.i <= this.vector.lastElement) {
/* 31:36 */         return this.vector.data[(this.i++)];
/* 32:   */       }
/* 33:38 */       throw new NoSuchElementException("VectorEnumerator");
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.impl.VectorEnumerator
 * JD-Core Version:    0.7.0.1
 */
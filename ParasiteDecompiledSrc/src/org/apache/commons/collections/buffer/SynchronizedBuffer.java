/*  1:   */ package org.apache.commons.collections.buffer;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.Buffer;
/*  4:   */ import org.apache.commons.collections.collection.SynchronizedCollection;
/*  5:   */ 
/*  6:   */ public class SynchronizedBuffer
/*  7:   */   extends SynchronizedCollection
/*  8:   */   implements Buffer
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -6859936183953626253L;
/* 11:   */   
/* 12:   */   public static Buffer decorate(Buffer buffer)
/* 13:   */   {
/* 14:48 */     return new SynchronizedBuffer(buffer);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected SynchronizedBuffer(Buffer buffer)
/* 18:   */   {
/* 19:59 */     super(buffer);
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected SynchronizedBuffer(Buffer buffer, Object lock)
/* 23:   */   {
/* 24:70 */     super(buffer, lock);
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected Buffer getBuffer()
/* 28:   */   {
/* 29:79 */     return (Buffer)this.collection;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Object get()
/* 33:   */   {
/* 34:84 */     synchronized (this.lock)
/* 35:   */     {
/* 36:85 */       return getBuffer().get();
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Object remove()
/* 41:   */   {
/* 42:90 */     synchronized (this.lock)
/* 43:   */     {
/* 44:91 */       return getBuffer().remove();
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.SynchronizedBuffer
 * JD-Core Version:    0.7.0.1
 */
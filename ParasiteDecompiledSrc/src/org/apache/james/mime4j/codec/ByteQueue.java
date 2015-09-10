/*  1:   */ package org.apache.james.mime4j.codec;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ 
/*  5:   */ public class ByteQueue
/*  6:   */   implements Iterable<Byte>
/*  7:   */ {
/*  8:   */   private UnboundedFifoByteBuffer buf;
/*  9:27 */   private int initialCapacity = -1;
/* 10:   */   
/* 11:   */   public ByteQueue()
/* 12:   */   {
/* 13:30 */     this.buf = new UnboundedFifoByteBuffer();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public ByteQueue(int initialCapacity)
/* 17:   */   {
/* 18:34 */     this.buf = new UnboundedFifoByteBuffer(initialCapacity);
/* 19:35 */     this.initialCapacity = initialCapacity;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void enqueue(byte b)
/* 23:   */   {
/* 24:39 */     this.buf.add(b);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public byte dequeue()
/* 28:   */   {
/* 29:43 */     return this.buf.remove();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int count()
/* 33:   */   {
/* 34:47 */     return this.buf.size();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void clear()
/* 38:   */   {
/* 39:51 */     if (this.initialCapacity != -1) {
/* 40:52 */       this.buf = new UnboundedFifoByteBuffer(this.initialCapacity);
/* 41:   */     } else {
/* 42:54 */       this.buf = new UnboundedFifoByteBuffer();
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Iterator<Byte> iterator()
/* 47:   */   {
/* 48:58 */     return this.buf.iterator();
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.ByteQueue
 * JD-Core Version:    0.7.0.1
 */
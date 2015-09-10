/*  1:   */ package org.apache.commons.collections.buffer;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.Buffer;
/*  4:   */ import org.apache.commons.collections.collection.AbstractCollectionDecorator;
/*  5:   */ 
/*  6:   */ public abstract class AbstractBufferDecorator
/*  7:   */   extends AbstractCollectionDecorator
/*  8:   */   implements Buffer
/*  9:   */ {
/* 10:   */   protected AbstractBufferDecorator() {}
/* 11:   */   
/* 12:   */   protected AbstractBufferDecorator(Buffer buffer)
/* 13:   */   {
/* 14:49 */     super(buffer);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected Buffer getBuffer()
/* 18:   */   {
/* 19:58 */     return (Buffer)getCollection();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object get()
/* 23:   */   {
/* 24:63 */     return getBuffer().get();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Object remove()
/* 28:   */   {
/* 29:67 */     return getBuffer().remove();
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.AbstractBufferDecorator
 * JD-Core Version:    0.7.0.1
 */
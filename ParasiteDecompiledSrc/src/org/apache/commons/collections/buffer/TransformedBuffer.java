/*  1:   */ package org.apache.commons.collections.buffer;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.Buffer;
/*  4:   */ import org.apache.commons.collections.Transformer;
/*  5:   */ import org.apache.commons.collections.collection.TransformedCollection;
/*  6:   */ 
/*  7:   */ public class TransformedBuffer
/*  8:   */   extends TransformedCollection
/*  9:   */   implements Buffer
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -7901091318986132033L;
/* 12:   */   
/* 13:   */   public static Buffer decorate(Buffer buffer, Transformer transformer)
/* 14:   */   {
/* 15:55 */     return new TransformedBuffer(buffer, transformer);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected TransformedBuffer(Buffer buffer, Transformer transformer)
/* 19:   */   {
/* 20:70 */     super(buffer, transformer);
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected Buffer getBuffer()
/* 24:   */   {
/* 25:79 */     return (Buffer)this.collection;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Object get()
/* 29:   */   {
/* 30:84 */     return getBuffer().get();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Object remove()
/* 34:   */   {
/* 35:88 */     return getBuffer().remove();
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.TransformedBuffer
 * JD-Core Version:    0.7.0.1
 */
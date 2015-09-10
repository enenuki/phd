/*  1:   */ package org.apache.commons.collections.buffer;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.Buffer;
/*  4:   */ import org.apache.commons.collections.Predicate;
/*  5:   */ import org.apache.commons.collections.collection.PredicatedCollection;
/*  6:   */ 
/*  7:   */ public class PredicatedBuffer
/*  8:   */   extends PredicatedCollection
/*  9:   */   implements Buffer
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 2307609000539943581L;
/* 12:   */   
/* 13:   */   public static Buffer decorate(Buffer buffer, Predicate predicate)
/* 14:   */   {
/* 15:60 */     return new PredicatedBuffer(buffer, predicate);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected PredicatedBuffer(Buffer buffer, Predicate predicate)
/* 19:   */   {
/* 20:76 */     super(buffer, predicate);
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected Buffer getBuffer()
/* 24:   */   {
/* 25:85 */     return (Buffer)getCollection();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Object get()
/* 29:   */   {
/* 30:90 */     return getBuffer().get();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Object remove()
/* 34:   */   {
/* 35:94 */     return getBuffer().remove();
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.PredicatedBuffer
 * JD-Core Version:    0.7.0.1
 */
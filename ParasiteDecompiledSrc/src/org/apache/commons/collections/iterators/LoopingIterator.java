/*   1:    */ package org.apache.commons.collections.iterators;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.NoSuchElementException;
/*   6:    */ import org.apache.commons.collections.ResettableIterator;
/*   7:    */ 
/*   8:    */ public class LoopingIterator
/*   9:    */   implements ResettableIterator
/*  10:    */ {
/*  11:    */   private Collection collection;
/*  12:    */   private Iterator iterator;
/*  13:    */   
/*  14:    */   public LoopingIterator(Collection coll)
/*  15:    */   {
/*  16: 58 */     if (coll == null) {
/*  17: 59 */       throw new NullPointerException("The collection must not be null");
/*  18:    */     }
/*  19: 61 */     this.collection = coll;
/*  20: 62 */     reset();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean hasNext()
/*  24:    */   {
/*  25: 74 */     return this.collection.size() > 0;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object next()
/*  29:    */   {
/*  30: 86 */     if (this.collection.size() == 0) {
/*  31: 87 */       throw new NoSuchElementException("There are no elements for this iterator to loop on");
/*  32:    */     }
/*  33: 89 */     if (!this.iterator.hasNext()) {
/*  34: 90 */       reset();
/*  35:    */     }
/*  36: 92 */     return this.iterator.next();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void remove()
/*  40:    */   {
/*  41:108 */     this.iterator.remove();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void reset()
/*  45:    */   {
/*  46:115 */     this.iterator = this.collection.iterator();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int size()
/*  50:    */   {
/*  51:124 */     return this.collection.size();
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.LoopingIterator
 * JD-Core Version:    0.7.0.1
 */
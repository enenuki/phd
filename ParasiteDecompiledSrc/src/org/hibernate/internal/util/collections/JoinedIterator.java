/*   1:    */ package org.hibernate.internal.util.collections;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class JoinedIterator
/*   7:    */   implements Iterator
/*   8:    */ {
/*   9: 41 */   private static final Iterator[] ITERATORS = new Iterator[0];
/*  10:    */   private Iterator[] iterators;
/*  11:    */   private int currentIteratorIndex;
/*  12:    */   private Iterator currentIterator;
/*  13:    */   private Iterator lastUsedIterator;
/*  14:    */   
/*  15:    */   public JoinedIterator(List iterators)
/*  16:    */   {
/*  17: 56 */     this((Iterator[])iterators.toArray(ITERATORS));
/*  18:    */   }
/*  19:    */   
/*  20:    */   public JoinedIterator(Iterator[] iterators)
/*  21:    */   {
/*  22: 60 */     if (iterators == null) {
/*  23: 61 */       throw new NullPointerException("Unexpected NULL iterators argument");
/*  24:    */     }
/*  25: 62 */     this.iterators = iterators;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public JoinedIterator(Iterator first, Iterator second)
/*  29:    */   {
/*  30: 66 */     this(new Iterator[] { first, second });
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean hasNext()
/*  34:    */   {
/*  35: 70 */     updateCurrentIterator();
/*  36: 71 */     return this.currentIterator.hasNext();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Object next()
/*  40:    */   {
/*  41: 75 */     updateCurrentIterator();
/*  42: 76 */     return this.currentIterator.next();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void remove()
/*  46:    */   {
/*  47: 80 */     updateCurrentIterator();
/*  48: 81 */     this.lastUsedIterator.remove();
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void updateCurrentIterator()
/*  52:    */   {
/*  53: 89 */     if (this.currentIterator == null)
/*  54:    */     {
/*  55: 90 */       if (this.iterators.length == 0) {
/*  56: 91 */         this.currentIterator = EmptyIterator.INSTANCE;
/*  57:    */       } else {
/*  58: 94 */         this.currentIterator = this.iterators[0];
/*  59:    */       }
/*  60: 98 */       this.lastUsedIterator = this.currentIterator;
/*  61:    */     }
/*  62:101 */     while ((!this.currentIterator.hasNext()) && (this.currentIteratorIndex < this.iterators.length - 1))
/*  63:    */     {
/*  64:102 */       this.currentIteratorIndex += 1;
/*  65:103 */       this.currentIterator = this.iterators[this.currentIteratorIndex];
/*  66:    */     }
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.JoinedIterator
 * JD-Core Version:    0.7.0.1
 */
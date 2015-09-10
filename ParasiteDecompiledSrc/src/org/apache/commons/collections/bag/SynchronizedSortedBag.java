/*   1:    */ package org.apache.commons.collections.bag;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import org.apache.commons.collections.Bag;
/*   5:    */ import org.apache.commons.collections.SortedBag;
/*   6:    */ 
/*   7:    */ public class SynchronizedSortedBag
/*   8:    */   extends SynchronizedBag
/*   9:    */   implements SortedBag
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 722374056718497858L;
/*  12:    */   
/*  13:    */   public static SortedBag decorate(SortedBag bag)
/*  14:    */   {
/*  15: 52 */     return new SynchronizedSortedBag(bag);
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected SynchronizedSortedBag(SortedBag bag)
/*  19:    */   {
/*  20: 63 */     super(bag);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected SynchronizedSortedBag(Bag bag, Object lock)
/*  24:    */   {
/*  25: 74 */     super(bag, lock);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected SortedBag getSortedBag()
/*  29:    */   {
/*  30: 83 */     return (SortedBag)this.collection;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public synchronized Object first()
/*  34:    */   {
/*  35: 88 */     synchronized (this.lock)
/*  36:    */     {
/*  37: 89 */       return getSortedBag().first();
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public synchronized Object last()
/*  42:    */   {
/*  43: 94 */     synchronized (this.lock)
/*  44:    */     {
/*  45: 95 */       return getSortedBag().last();
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public synchronized Comparator comparator()
/*  50:    */   {
/*  51:100 */     synchronized (this.lock)
/*  52:    */     {
/*  53:101 */       return getSortedBag().comparator();
/*  54:    */     }
/*  55:    */   }
/*  56:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.SynchronizedSortedBag
 * JD-Core Version:    0.7.0.1
 */
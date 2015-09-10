/*   1:    */ package org.apache.commons.collections.bag;
/*   2:    */ 
/*   3:    */ import java.util.Set;
/*   4:    */ import org.apache.commons.collections.Bag;
/*   5:    */ import org.apache.commons.collections.collection.SynchronizedCollection;
/*   6:    */ import org.apache.commons.collections.set.SynchronizedSet;
/*   7:    */ 
/*   8:    */ public class SynchronizedBag
/*   9:    */   extends SynchronizedCollection
/*  10:    */   implements Bag
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 8084674570753837109L;
/*  13:    */   
/*  14:    */   public static Bag decorate(Bag bag)
/*  15:    */   {
/*  16: 53 */     return new SynchronizedBag(bag);
/*  17:    */   }
/*  18:    */   
/*  19:    */   protected SynchronizedBag(Bag bag)
/*  20:    */   {
/*  21: 64 */     super(bag);
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected SynchronizedBag(Bag bag, Object lock)
/*  25:    */   {
/*  26: 75 */     super(bag, lock);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected Bag getBag()
/*  30:    */   {
/*  31: 84 */     return (Bag)this.collection;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean add(Object object, int count)
/*  35:    */   {
/*  36: 89 */     synchronized (this.lock)
/*  37:    */     {
/*  38: 90 */       return getBag().add(object, count);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean remove(Object object, int count)
/*  43:    */   {
/*  44: 95 */     synchronized (this.lock)
/*  45:    */     {
/*  46: 96 */       return getBag().remove(object, count);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Set uniqueSet()
/*  51:    */   {
/*  52:101 */     synchronized (this.lock)
/*  53:    */     {
/*  54:102 */       Set set = getBag().uniqueSet();
/*  55:103 */       return new SynchronizedBagSet(set, this.lock);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getCount(Object object)
/*  60:    */   {
/*  61:108 */     synchronized (this.lock)
/*  62:    */     {
/*  63:109 */       return getBag().getCount(object);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   class SynchronizedBagSet
/*  68:    */     extends SynchronizedSet
/*  69:    */   {
/*  70:    */     SynchronizedBagSet(Set set, Object lock)
/*  71:    */     {
/*  72:124 */       super(lock);
/*  73:    */     }
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.SynchronizedBag
 * JD-Core Version:    0.7.0.1
 */
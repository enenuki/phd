/*   1:    */ package org.apache.commons.collections.set;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import java.util.SortedSet;
/*   5:    */ import org.apache.commons.collections.collection.SynchronizedCollection;
/*   6:    */ 
/*   7:    */ public class SynchronizedSortedSet
/*   8:    */   extends SynchronizedCollection
/*   9:    */   implements SortedSet
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 2775582861954500111L;
/*  12:    */   
/*  13:    */   public static SortedSet decorate(SortedSet set)
/*  14:    */   {
/*  15: 49 */     return new SynchronizedSortedSet(set);
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected SynchronizedSortedSet(SortedSet set)
/*  19:    */   {
/*  20: 60 */     super(set);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected SynchronizedSortedSet(SortedSet set, Object lock)
/*  24:    */   {
/*  25: 71 */     super(set, lock);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected SortedSet getSortedSet()
/*  29:    */   {
/*  30: 80 */     return (SortedSet)this.collection;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public SortedSet subSet(Object fromElement, Object toElement)
/*  34:    */   {
/*  35: 85 */     synchronized (this.lock)
/*  36:    */     {
/*  37: 86 */       SortedSet set = getSortedSet().subSet(fromElement, toElement);
/*  38:    */       
/*  39:    */ 
/*  40: 89 */       return new SynchronizedSortedSet(set, this.lock);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public SortedSet headSet(Object toElement)
/*  45:    */   {
/*  46: 94 */     synchronized (this.lock)
/*  47:    */     {
/*  48: 95 */       SortedSet set = getSortedSet().headSet(toElement);
/*  49:    */       
/*  50:    */ 
/*  51: 98 */       return new SynchronizedSortedSet(set, this.lock);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public SortedSet tailSet(Object fromElement)
/*  56:    */   {
/*  57:103 */     synchronized (this.lock)
/*  58:    */     {
/*  59:104 */       SortedSet set = getSortedSet().tailSet(fromElement);
/*  60:    */       
/*  61:    */ 
/*  62:107 */       return new SynchronizedSortedSet(set, this.lock);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Object first()
/*  67:    */   {
/*  68:112 */     synchronized (this.lock)
/*  69:    */     {
/*  70:113 */       return getSortedSet().first();
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object last()
/*  75:    */   {
/*  76:118 */     synchronized (this.lock)
/*  77:    */     {
/*  78:119 */       return getSortedSet().last();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Comparator comparator()
/*  83:    */   {
/*  84:124 */     synchronized (this.lock)
/*  85:    */     {
/*  86:125 */       return getSortedSet().comparator();
/*  87:    */     }
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.SynchronizedSortedSet
 * JD-Core Version:    0.7.0.1
 */
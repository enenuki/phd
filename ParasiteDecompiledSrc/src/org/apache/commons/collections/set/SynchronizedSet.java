/*  1:   */ package org.apache.commons.collections.set;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.apache.commons.collections.collection.SynchronizedCollection;
/*  5:   */ 
/*  6:   */ public class SynchronizedSet
/*  7:   */   extends SynchronizedCollection
/*  8:   */   implements Set
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -8304417378626543635L;
/* 11:   */   
/* 12:   */   public static Set decorate(Set set)
/* 13:   */   {
/* 14:48 */     return new SynchronizedSet(set);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected SynchronizedSet(Set set)
/* 18:   */   {
/* 19:59 */     super(set);
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected SynchronizedSet(Set set, Object lock)
/* 23:   */   {
/* 24:70 */     super(set, lock);
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected Set getSet()
/* 28:   */   {
/* 29:79 */     return (Set)this.collection;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.SynchronizedSet
 * JD-Core Version:    0.7.0.1
 */
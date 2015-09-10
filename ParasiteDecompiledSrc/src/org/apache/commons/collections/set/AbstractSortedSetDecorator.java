/*  1:   */ package org.apache.commons.collections.set;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import java.util.Set;
/*  5:   */ import java.util.SortedSet;
/*  6:   */ 
/*  7:   */ public abstract class AbstractSortedSetDecorator
/*  8:   */   extends AbstractSetDecorator
/*  9:   */   implements SortedSet
/* 10:   */ {
/* 11:   */   protected AbstractSortedSetDecorator() {}
/* 12:   */   
/* 13:   */   protected AbstractSortedSetDecorator(Set set)
/* 14:   */   {
/* 15:50 */     super(set);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected SortedSet getSortedSet()
/* 19:   */   {
/* 20:59 */     return (SortedSet)getCollection();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public SortedSet subSet(Object fromElement, Object toElement)
/* 24:   */   {
/* 25:64 */     return getSortedSet().subSet(fromElement, toElement);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public SortedSet headSet(Object toElement)
/* 29:   */   {
/* 30:68 */     return getSortedSet().headSet(toElement);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public SortedSet tailSet(Object fromElement)
/* 34:   */   {
/* 35:72 */     return getSortedSet().tailSet(fromElement);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Object first()
/* 39:   */   {
/* 40:76 */     return getSortedSet().first();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Object last()
/* 44:   */   {
/* 45:80 */     return getSortedSet().last();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Comparator comparator()
/* 49:   */   {
/* 50:84 */     return getSortedSet().comparator();
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.AbstractSortedSetDecorator
 * JD-Core Version:    0.7.0.1
 */
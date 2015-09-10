/*  1:   */ package org.apache.commons.collections.bidimap;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import java.util.SortedMap;
/*  5:   */ import org.apache.commons.collections.SortedBidiMap;
/*  6:   */ 
/*  7:   */ public abstract class AbstractSortedBidiMapDecorator
/*  8:   */   extends AbstractOrderedBidiMapDecorator
/*  9:   */   implements SortedBidiMap
/* 10:   */ {
/* 11:   */   public AbstractSortedBidiMapDecorator(SortedBidiMap map)
/* 12:   */   {
/* 13:51 */     super(map);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected SortedBidiMap getSortedBidiMap()
/* 17:   */   {
/* 18:60 */     return (SortedBidiMap)this.map;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public SortedBidiMap inverseSortedBidiMap()
/* 22:   */   {
/* 23:65 */     return getSortedBidiMap().inverseSortedBidiMap();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Comparator comparator()
/* 27:   */   {
/* 28:69 */     return getSortedBidiMap().comparator();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public SortedMap subMap(Object fromKey, Object toKey)
/* 32:   */   {
/* 33:73 */     return getSortedBidiMap().subMap(fromKey, toKey);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public SortedMap headMap(Object toKey)
/* 37:   */   {
/* 38:77 */     return getSortedBidiMap().headMap(toKey);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public SortedMap tailMap(Object fromKey)
/* 42:   */   {
/* 43:81 */     return getSortedBidiMap().tailMap(fromKey);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.AbstractSortedBidiMapDecorator
 * JD-Core Version:    0.7.0.1
 */
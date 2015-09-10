/*  1:   */ package org.apache.commons.collections.map;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import java.util.SortedMap;
/*  5:   */ 
/*  6:   */ public abstract class AbstractSortedMapDecorator
/*  7:   */   extends AbstractMapDecorator
/*  8:   */   implements SortedMap
/*  9:   */ {
/* 10:   */   protected AbstractSortedMapDecorator() {}
/* 11:   */   
/* 12:   */   public AbstractSortedMapDecorator(SortedMap map)
/* 13:   */   {
/* 14:57 */     super(map);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected SortedMap getSortedMap()
/* 18:   */   {
/* 19:66 */     return (SortedMap)this.map;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Comparator comparator()
/* 23:   */   {
/* 24:71 */     return getSortedMap().comparator();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Object firstKey()
/* 28:   */   {
/* 29:75 */     return getSortedMap().firstKey();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public SortedMap headMap(Object toKey)
/* 33:   */   {
/* 34:79 */     return getSortedMap().headMap(toKey);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Object lastKey()
/* 38:   */   {
/* 39:83 */     return getSortedMap().lastKey();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public SortedMap subMap(Object fromKey, Object toKey)
/* 43:   */   {
/* 44:87 */     return getSortedMap().subMap(fromKey, toKey);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public SortedMap tailMap(Object fromKey)
/* 48:   */   {
/* 49:91 */     return getSortedMap().tailMap(fromKey);
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.AbstractSortedMapDecorator
 * JD-Core Version:    0.7.0.1
 */
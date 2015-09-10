/*  1:   */ package org.apache.commons.collections.map;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.MapIterator;
/*  4:   */ import org.apache.commons.collections.OrderedMap;
/*  5:   */ import org.apache.commons.collections.OrderedMapIterator;
/*  6:   */ 
/*  7:   */ public abstract class AbstractOrderedMapDecorator
/*  8:   */   extends AbstractMapDecorator
/*  9:   */   implements OrderedMap
/* 10:   */ {
/* 11:   */   protected AbstractOrderedMapDecorator() {}
/* 12:   */   
/* 13:   */   public AbstractOrderedMapDecorator(OrderedMap map)
/* 14:   */   {
/* 15:58 */     super(map);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected OrderedMap getOrderedMap()
/* 19:   */   {
/* 20:67 */     return (OrderedMap)this.map;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object firstKey()
/* 24:   */   {
/* 25:72 */     return getOrderedMap().firstKey();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Object lastKey()
/* 29:   */   {
/* 30:76 */     return getOrderedMap().lastKey();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Object nextKey(Object key)
/* 34:   */   {
/* 35:80 */     return getOrderedMap().nextKey(key);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Object previousKey(Object key)
/* 39:   */   {
/* 40:84 */     return getOrderedMap().previousKey(key);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public MapIterator mapIterator()
/* 44:   */   {
/* 45:88 */     return getOrderedMap().mapIterator();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public OrderedMapIterator orderedMapIterator()
/* 49:   */   {
/* 50:92 */     return getOrderedMap().orderedMapIterator();
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.AbstractOrderedMapDecorator
 * JD-Core Version:    0.7.0.1
 */
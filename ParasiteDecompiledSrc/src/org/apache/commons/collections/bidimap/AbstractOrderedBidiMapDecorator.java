/*  1:   */ package org.apache.commons.collections.bidimap;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.OrderedBidiMap;
/*  4:   */ import org.apache.commons.collections.OrderedMapIterator;
/*  5:   */ 
/*  6:   */ public abstract class AbstractOrderedBidiMapDecorator
/*  7:   */   extends AbstractBidiMapDecorator
/*  8:   */   implements OrderedBidiMap
/*  9:   */ {
/* 10:   */   protected AbstractOrderedBidiMapDecorator(OrderedBidiMap map)
/* 11:   */   {
/* 12:49 */     super(map);
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected OrderedBidiMap getOrderedBidiMap()
/* 16:   */   {
/* 17:58 */     return (OrderedBidiMap)this.map;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public OrderedMapIterator orderedMapIterator()
/* 21:   */   {
/* 22:63 */     return getOrderedBidiMap().orderedMapIterator();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Object firstKey()
/* 26:   */   {
/* 27:67 */     return getOrderedBidiMap().firstKey();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Object lastKey()
/* 31:   */   {
/* 32:71 */     return getOrderedBidiMap().lastKey();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Object nextKey(Object key)
/* 36:   */   {
/* 37:75 */     return getOrderedBidiMap().nextKey(key);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Object previousKey(Object key)
/* 41:   */   {
/* 42:79 */     return getOrderedBidiMap().previousKey(key);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public OrderedBidiMap inverseOrderedBidiMap()
/* 46:   */   {
/* 47:83 */     return getOrderedBidiMap().inverseOrderedBidiMap();
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.AbstractOrderedBidiMapDecorator
 * JD-Core Version:    0.7.0.1
 */
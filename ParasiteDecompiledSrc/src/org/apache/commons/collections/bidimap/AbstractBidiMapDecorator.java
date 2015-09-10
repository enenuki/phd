/*  1:   */ package org.apache.commons.collections.bidimap;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.BidiMap;
/*  4:   */ import org.apache.commons.collections.MapIterator;
/*  5:   */ import org.apache.commons.collections.map.AbstractMapDecorator;
/*  6:   */ 
/*  7:   */ public abstract class AbstractBidiMapDecorator
/*  8:   */   extends AbstractMapDecorator
/*  9:   */   implements BidiMap
/* 10:   */ {
/* 11:   */   protected AbstractBidiMapDecorator(BidiMap map)
/* 12:   */   {
/* 13:50 */     super(map);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected BidiMap getBidiMap()
/* 17:   */   {
/* 18:59 */     return (BidiMap)this.map;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public MapIterator mapIterator()
/* 22:   */   {
/* 23:64 */     return getBidiMap().mapIterator();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object getKey(Object value)
/* 27:   */   {
/* 28:68 */     return getBidiMap().getKey(value);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object removeValue(Object value)
/* 32:   */   {
/* 33:72 */     return getBidiMap().removeValue(value);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public BidiMap inverseBidiMap()
/* 37:   */   {
/* 38:76 */     return getBidiMap().inverseBidiMap();
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.AbstractBidiMapDecorator
 * JD-Core Version:    0.7.0.1
 */
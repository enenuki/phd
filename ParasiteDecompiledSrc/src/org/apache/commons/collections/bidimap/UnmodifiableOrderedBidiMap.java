/*   1:    */ package org.apache.commons.collections.bidimap;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.apache.commons.collections.BidiMap;
/*   7:    */ import org.apache.commons.collections.MapIterator;
/*   8:    */ import org.apache.commons.collections.OrderedBidiMap;
/*   9:    */ import org.apache.commons.collections.OrderedMapIterator;
/*  10:    */ import org.apache.commons.collections.Unmodifiable;
/*  11:    */ import org.apache.commons.collections.collection.UnmodifiableCollection;
/*  12:    */ import org.apache.commons.collections.iterators.UnmodifiableOrderedMapIterator;
/*  13:    */ import org.apache.commons.collections.map.UnmodifiableEntrySet;
/*  14:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  15:    */ 
/*  16:    */ public final class UnmodifiableOrderedBidiMap
/*  17:    */   extends AbstractOrderedBidiMapDecorator
/*  18:    */   implements Unmodifiable
/*  19:    */ {
/*  20:    */   private UnmodifiableOrderedBidiMap inverse;
/*  21:    */   
/*  22:    */   public static OrderedBidiMap decorate(OrderedBidiMap map)
/*  23:    */   {
/*  24: 57 */     if ((map instanceof Unmodifiable)) {
/*  25: 58 */       return map;
/*  26:    */     }
/*  27: 60 */     return new UnmodifiableOrderedBidiMap(map);
/*  28:    */   }
/*  29:    */   
/*  30:    */   private UnmodifiableOrderedBidiMap(OrderedBidiMap map)
/*  31:    */   {
/*  32: 71 */     super(map);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void clear()
/*  36:    */   {
/*  37: 76 */     throw new UnsupportedOperationException();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object put(Object key, Object value)
/*  41:    */   {
/*  42: 80 */     throw new UnsupportedOperationException();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void putAll(Map mapToCopy)
/*  46:    */   {
/*  47: 84 */     throw new UnsupportedOperationException();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Object remove(Object key)
/*  51:    */   {
/*  52: 88 */     throw new UnsupportedOperationException();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Set entrySet()
/*  56:    */   {
/*  57: 92 */     Set set = super.entrySet();
/*  58: 93 */     return UnmodifiableEntrySet.decorate(set);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Set keySet()
/*  62:    */   {
/*  63: 97 */     Set set = super.keySet();
/*  64: 98 */     return UnmodifiableSet.decorate(set);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Collection values()
/*  68:    */   {
/*  69:102 */     Collection coll = super.values();
/*  70:103 */     return UnmodifiableCollection.decorate(coll);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Object removeValue(Object value)
/*  74:    */   {
/*  75:108 */     throw new UnsupportedOperationException();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public MapIterator mapIterator()
/*  79:    */   {
/*  80:112 */     return orderedMapIterator();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public BidiMap inverseBidiMap()
/*  84:    */   {
/*  85:116 */     return inverseOrderedBidiMap();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public OrderedMapIterator orderedMapIterator()
/*  89:    */   {
/*  90:121 */     OrderedMapIterator it = getOrderedBidiMap().orderedMapIterator();
/*  91:122 */     return UnmodifiableOrderedMapIterator.decorate(it);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public OrderedBidiMap inverseOrderedBidiMap()
/*  95:    */   {
/*  96:126 */     if (this.inverse == null)
/*  97:    */     {
/*  98:127 */       this.inverse = new UnmodifiableOrderedBidiMap(getOrderedBidiMap().inverseOrderedBidiMap());
/*  99:128 */       this.inverse.inverse = this;
/* 100:    */     }
/* 101:130 */     return this.inverse;
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.UnmodifiableOrderedBidiMap
 * JD-Core Version:    0.7.0.1
 */
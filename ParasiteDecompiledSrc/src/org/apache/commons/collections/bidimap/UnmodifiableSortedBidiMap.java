/*   1:    */ package org.apache.commons.collections.bidimap;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Set;
/*   6:    */ import java.util.SortedMap;
/*   7:    */ import org.apache.commons.collections.BidiMap;
/*   8:    */ import org.apache.commons.collections.MapIterator;
/*   9:    */ import org.apache.commons.collections.OrderedBidiMap;
/*  10:    */ import org.apache.commons.collections.OrderedMapIterator;
/*  11:    */ import org.apache.commons.collections.SortedBidiMap;
/*  12:    */ import org.apache.commons.collections.Unmodifiable;
/*  13:    */ import org.apache.commons.collections.collection.UnmodifiableCollection;
/*  14:    */ import org.apache.commons.collections.iterators.UnmodifiableOrderedMapIterator;
/*  15:    */ import org.apache.commons.collections.map.UnmodifiableEntrySet;
/*  16:    */ import org.apache.commons.collections.map.UnmodifiableSortedMap;
/*  17:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  18:    */ 
/*  19:    */ public final class UnmodifiableSortedBidiMap
/*  20:    */   extends AbstractSortedBidiMapDecorator
/*  21:    */   implements Unmodifiable
/*  22:    */ {
/*  23:    */   private UnmodifiableSortedBidiMap inverse;
/*  24:    */   
/*  25:    */   public static SortedBidiMap decorate(SortedBidiMap map)
/*  26:    */   {
/*  27: 60 */     if ((map instanceof Unmodifiable)) {
/*  28: 61 */       return map;
/*  29:    */     }
/*  30: 63 */     return new UnmodifiableSortedBidiMap(map);
/*  31:    */   }
/*  32:    */   
/*  33:    */   private UnmodifiableSortedBidiMap(SortedBidiMap map)
/*  34:    */   {
/*  35: 74 */     super(map);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void clear()
/*  39:    */   {
/*  40: 79 */     throw new UnsupportedOperationException();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object put(Object key, Object value)
/*  44:    */   {
/*  45: 83 */     throw new UnsupportedOperationException();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void putAll(Map mapToCopy)
/*  49:    */   {
/*  50: 87 */     throw new UnsupportedOperationException();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Object remove(Object key)
/*  54:    */   {
/*  55: 91 */     throw new UnsupportedOperationException();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Set entrySet()
/*  59:    */   {
/*  60: 95 */     Set set = super.entrySet();
/*  61: 96 */     return UnmodifiableEntrySet.decorate(set);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Set keySet()
/*  65:    */   {
/*  66:100 */     Set set = super.keySet();
/*  67:101 */     return UnmodifiableSet.decorate(set);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Collection values()
/*  71:    */   {
/*  72:105 */     Collection coll = super.values();
/*  73:106 */     return UnmodifiableCollection.decorate(coll);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object removeValue(Object value)
/*  77:    */   {
/*  78:111 */     throw new UnsupportedOperationException();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public MapIterator mapIterator()
/*  82:    */   {
/*  83:115 */     return orderedMapIterator();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public BidiMap inverseBidiMap()
/*  87:    */   {
/*  88:119 */     return inverseSortedBidiMap();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public OrderedMapIterator orderedMapIterator()
/*  92:    */   {
/*  93:124 */     OrderedMapIterator it = getSortedBidiMap().orderedMapIterator();
/*  94:125 */     return UnmodifiableOrderedMapIterator.decorate(it);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public OrderedBidiMap inverseOrderedBidiMap()
/*  98:    */   {
/*  99:129 */     return inverseSortedBidiMap();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public SortedBidiMap inverseSortedBidiMap()
/* 103:    */   {
/* 104:134 */     if (this.inverse == null)
/* 105:    */     {
/* 106:135 */       this.inverse = new UnmodifiableSortedBidiMap(getSortedBidiMap().inverseSortedBidiMap());
/* 107:136 */       this.inverse.inverse = this;
/* 108:    */     }
/* 109:138 */     return this.inverse;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public SortedMap subMap(Object fromKey, Object toKey)
/* 113:    */   {
/* 114:142 */     SortedMap sm = getSortedBidiMap().subMap(fromKey, toKey);
/* 115:143 */     return UnmodifiableSortedMap.decorate(sm);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public SortedMap headMap(Object toKey)
/* 119:    */   {
/* 120:147 */     SortedMap sm = getSortedBidiMap().headMap(toKey);
/* 121:148 */     return UnmodifiableSortedMap.decorate(sm);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public SortedMap tailMap(Object fromKey)
/* 125:    */   {
/* 126:152 */     SortedMap sm = getSortedBidiMap().tailMap(fromKey);
/* 127:153 */     return UnmodifiableSortedMap.decorate(sm);
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.UnmodifiableSortedBidiMap
 * JD-Core Version:    0.7.0.1
 */
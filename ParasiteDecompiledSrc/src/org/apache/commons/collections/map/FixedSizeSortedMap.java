/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.SortedMap;
/*  12:    */ import org.apache.commons.collections.BoundedMap;
/*  13:    */ import org.apache.commons.collections.collection.UnmodifiableCollection;
/*  14:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  15:    */ 
/*  16:    */ public class FixedSizeSortedMap
/*  17:    */   extends AbstractSortedMapDecorator
/*  18:    */   implements SortedMap, BoundedMap, Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 3126019624511683653L;
/*  21:    */   
/*  22:    */   public static SortedMap decorate(SortedMap map)
/*  23:    */   {
/*  24: 74 */     return new FixedSizeSortedMap(map);
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected FixedSizeSortedMap(SortedMap map)
/*  28:    */   {
/*  29: 85 */     super(map);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected SortedMap getSortedMap()
/*  33:    */   {
/*  34: 94 */     return (SortedMap)this.map;
/*  35:    */   }
/*  36:    */   
/*  37:    */   private void writeObject(ObjectOutputStream out)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40:102 */     out.defaultWriteObject();
/*  41:103 */     out.writeObject(this.map);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void readObject(ObjectInputStream in)
/*  45:    */     throws IOException, ClassNotFoundException
/*  46:    */   {
/*  47:110 */     in.defaultReadObject();
/*  48:111 */     this.map = ((Map)in.readObject());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Object put(Object key, Object value)
/*  52:    */   {
/*  53:116 */     if (!this.map.containsKey(key)) {
/*  54:117 */       throw new IllegalArgumentException("Cannot put new key/value pair - Map is fixed size");
/*  55:    */     }
/*  56:119 */     return this.map.put(key, value);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void putAll(Map mapToCopy)
/*  60:    */   {
/*  61:123 */     for (Iterator it = mapToCopy.keySet().iterator(); it.hasNext();) {
/*  62:124 */       if (!mapToCopy.containsKey(it.next())) {
/*  63:125 */         throw new IllegalArgumentException("Cannot put new key/value pair - Map is fixed size");
/*  64:    */       }
/*  65:    */     }
/*  66:128 */     this.map.putAll(mapToCopy);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void clear()
/*  70:    */   {
/*  71:132 */     throw new UnsupportedOperationException("Map is fixed size");
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object remove(Object key)
/*  75:    */   {
/*  76:136 */     throw new UnsupportedOperationException("Map is fixed size");
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Set entrySet()
/*  80:    */   {
/*  81:140 */     Set set = this.map.entrySet();
/*  82:141 */     return UnmodifiableSet.decorate(set);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Set keySet()
/*  86:    */   {
/*  87:145 */     Set set = this.map.keySet();
/*  88:146 */     return UnmodifiableSet.decorate(set);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Collection values()
/*  92:    */   {
/*  93:150 */     Collection coll = this.map.values();
/*  94:151 */     return UnmodifiableCollection.decorate(coll);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public SortedMap subMap(Object fromKey, Object toKey)
/*  98:    */   {
/*  99:156 */     SortedMap map = getSortedMap().subMap(fromKey, toKey);
/* 100:157 */     return new FixedSizeSortedMap(map);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public SortedMap headMap(Object toKey)
/* 104:    */   {
/* 105:161 */     SortedMap map = getSortedMap().headMap(toKey);
/* 106:162 */     return new FixedSizeSortedMap(map);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public SortedMap tailMap(Object fromKey)
/* 110:    */   {
/* 111:166 */     SortedMap map = getSortedMap().tailMap(fromKey);
/* 112:167 */     return new FixedSizeSortedMap(map);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isFull()
/* 116:    */   {
/* 117:171 */     return true;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int maxSize()
/* 121:    */   {
/* 122:175 */     return size();
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.FixedSizeSortedMap
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Comparator;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.SortedMap;
/*  12:    */ import org.apache.commons.collections.Unmodifiable;
/*  13:    */ import org.apache.commons.collections.collection.UnmodifiableCollection;
/*  14:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  15:    */ 
/*  16:    */ public final class UnmodifiableSortedMap
/*  17:    */   extends AbstractSortedMapDecorator
/*  18:    */   implements Unmodifiable, Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 5805344239827376360L;
/*  21:    */   
/*  22:    */   public static SortedMap decorate(SortedMap map)
/*  23:    */   {
/*  24: 57 */     if ((map instanceof Unmodifiable)) {
/*  25: 58 */       return map;
/*  26:    */     }
/*  27: 60 */     return new UnmodifiableSortedMap(map);
/*  28:    */   }
/*  29:    */   
/*  30:    */   private UnmodifiableSortedMap(SortedMap map)
/*  31:    */   {
/*  32: 71 */     super(map);
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void writeObject(ObjectOutputStream out)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 83 */     out.defaultWriteObject();
/*  39: 84 */     out.writeObject(this.map);
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void readObject(ObjectInputStream in)
/*  43:    */     throws IOException, ClassNotFoundException
/*  44:    */   {
/*  45: 96 */     in.defaultReadObject();
/*  46: 97 */     this.map = ((Map)in.readObject());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void clear()
/*  50:    */   {
/*  51:102 */     throw new UnsupportedOperationException();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object put(Object key, Object value)
/*  55:    */   {
/*  56:106 */     throw new UnsupportedOperationException();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void putAll(Map mapToCopy)
/*  60:    */   {
/*  61:110 */     throw new UnsupportedOperationException();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object remove(Object key)
/*  65:    */   {
/*  66:114 */     throw new UnsupportedOperationException();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Set entrySet()
/*  70:    */   {
/*  71:118 */     Set set = super.entrySet();
/*  72:119 */     return UnmodifiableEntrySet.decorate(set);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Set keySet()
/*  76:    */   {
/*  77:123 */     Set set = super.keySet();
/*  78:124 */     return UnmodifiableSet.decorate(set);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Collection values()
/*  82:    */   {
/*  83:128 */     Collection coll = super.values();
/*  84:129 */     return UnmodifiableCollection.decorate(coll);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Object firstKey()
/*  88:    */   {
/*  89:134 */     return getSortedMap().firstKey();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Object lastKey()
/*  93:    */   {
/*  94:138 */     return getSortedMap().lastKey();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Comparator comparator()
/*  98:    */   {
/*  99:142 */     return getSortedMap().comparator();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public SortedMap subMap(Object fromKey, Object toKey)
/* 103:    */   {
/* 104:146 */     SortedMap map = getSortedMap().subMap(fromKey, toKey);
/* 105:147 */     return new UnmodifiableSortedMap(map);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public SortedMap headMap(Object toKey)
/* 109:    */   {
/* 110:151 */     SortedMap map = getSortedMap().headMap(toKey);
/* 111:152 */     return new UnmodifiableSortedMap(map);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public SortedMap tailMap(Object fromKey)
/* 115:    */   {
/* 116:156 */     SortedMap map = getSortedMap().tailMap(fromKey);
/* 117:157 */     return new UnmodifiableSortedMap(map);
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.UnmodifiableSortedMap
 * JD-Core Version:    0.7.0.1
 */
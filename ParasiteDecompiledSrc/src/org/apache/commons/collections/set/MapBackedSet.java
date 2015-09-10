/*   1:    */ package org.apache.commons.collections.set;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public final class MapBackedSet
/*  10:    */   implements Set, Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 6723912213766056587L;
/*  13:    */   protected final Map map;
/*  14:    */   protected final Object dummyValue;
/*  15:    */   
/*  16:    */   public static Set decorate(Map map)
/*  17:    */   {
/*  18: 57 */     return decorate(map, null);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static Set decorate(Map map, Object dummyValue)
/*  22:    */   {
/*  23: 68 */     if (map == null) {
/*  24: 69 */       throw new IllegalArgumentException("The map must not be null");
/*  25:    */     }
/*  26: 71 */     return new MapBackedSet(map, dummyValue);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private MapBackedSet(Map map, Object dummyValue)
/*  30:    */   {
/*  31: 84 */     this.map = map;
/*  32: 85 */     this.dummyValue = dummyValue;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int size()
/*  36:    */   {
/*  37: 90 */     return this.map.size();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isEmpty()
/*  41:    */   {
/*  42: 94 */     return this.map.isEmpty();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Iterator iterator()
/*  46:    */   {
/*  47: 98 */     return this.map.keySet().iterator();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean contains(Object obj)
/*  51:    */   {
/*  52:102 */     return this.map.containsKey(obj);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean containsAll(Collection coll)
/*  56:    */   {
/*  57:106 */     return this.map.keySet().containsAll(coll);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean add(Object obj)
/*  61:    */   {
/*  62:110 */     int size = this.map.size();
/*  63:111 */     this.map.put(obj, this.dummyValue);
/*  64:112 */     return this.map.size() != size;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean addAll(Collection coll)
/*  68:    */   {
/*  69:116 */     int size = this.map.size();
/*  70:117 */     for (Iterator it = coll.iterator(); it.hasNext();)
/*  71:    */     {
/*  72:118 */       Object obj = it.next();
/*  73:119 */       this.map.put(obj, this.dummyValue);
/*  74:    */     }
/*  75:121 */     return this.map.size() != size;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean remove(Object obj)
/*  79:    */   {
/*  80:125 */     int size = this.map.size();
/*  81:126 */     this.map.remove(obj);
/*  82:127 */     return this.map.size() != size;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean removeAll(Collection coll)
/*  86:    */   {
/*  87:131 */     return this.map.keySet().removeAll(coll);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean retainAll(Collection coll)
/*  91:    */   {
/*  92:135 */     return this.map.keySet().retainAll(coll);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void clear()
/*  96:    */   {
/*  97:139 */     this.map.clear();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Object[] toArray()
/* 101:    */   {
/* 102:143 */     return this.map.keySet().toArray();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Object[] toArray(Object[] array)
/* 106:    */   {
/* 107:147 */     return this.map.keySet().toArray(array);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean equals(Object obj)
/* 111:    */   {
/* 112:151 */     return this.map.keySet().equals(obj);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int hashCode()
/* 116:    */   {
/* 117:155 */     return this.map.keySet().hashCode();
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.MapBackedSet
 * JD-Core Version:    0.7.0.1
 */
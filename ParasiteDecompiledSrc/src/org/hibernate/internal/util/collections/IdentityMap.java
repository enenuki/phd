/*   1:    */ package org.hibernate.internal.util.collections;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.LinkedHashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ 
/*  12:    */ public final class IdentityMap<K, V>
/*  13:    */   implements Map<K, V>
/*  14:    */ {
/*  15:    */   private final Map<IdentityKey<K>, V> map;
/*  16: 42 */   private transient Map.Entry<IdentityKey<K>, V>[] entryArray = new Map.Entry[0];
/*  17: 44 */   private transient boolean dirty = false;
/*  18:    */   
/*  19:    */   public static <K, V> IdentityMap<K, V> instantiateSequenced(int size)
/*  20:    */   {
/*  21: 54 */     return new IdentityMap(new LinkedHashMap(size));
/*  22:    */   }
/*  23:    */   
/*  24:    */   private IdentityMap(Map<IdentityKey<K>, V> underlyingMap)
/*  25:    */   {
/*  26: 63 */     this.map = underlyingMap;
/*  27: 64 */     this.dirty = true;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static <K, V> Map.Entry<K, V>[] concurrentEntries(Map<K, V> map)
/*  31:    */   {
/*  32: 76 */     return ((IdentityMap)map).entryArray();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Iterator<K> keyIterator()
/*  36:    */   {
/*  37: 80 */     return new KeyIterator(this.map.keySet().iterator(), null);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int size()
/*  41:    */   {
/*  42: 85 */     return this.map.size();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isEmpty()
/*  46:    */   {
/*  47: 90 */     return this.map.isEmpty();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean containsKey(Object key)
/*  51:    */   {
/*  52: 96 */     return this.map.containsKey(new IdentityKey(key));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean containsValue(Object val)
/*  56:    */   {
/*  57:101 */     return this.map.containsValue(val);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public V get(Object key)
/*  61:    */   {
/*  62:107 */     return this.map.get(new IdentityKey(key));
/*  63:    */   }
/*  64:    */   
/*  65:    */   public V put(K key, V value)
/*  66:    */   {
/*  67:112 */     this.dirty = true;
/*  68:113 */     return this.map.put(new IdentityKey(key), value);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public V remove(Object key)
/*  72:    */   {
/*  73:119 */     this.dirty = true;
/*  74:120 */     return this.map.remove(new IdentityKey(key));
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void putAll(Map<? extends K, ? extends V> otherMap)
/*  78:    */   {
/*  79:125 */     for (Map.Entry<? extends K, ? extends V> entry : otherMap.entrySet()) {
/*  80:126 */       put(entry.getKey(), entry.getValue());
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void clear()
/*  85:    */   {
/*  86:132 */     this.dirty = true;
/*  87:133 */     this.entryArray = null;
/*  88:134 */     this.map.clear();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Set<K> keySet()
/*  92:    */   {
/*  93:140 */     throw new UnsupportedOperationException();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Collection<V> values()
/*  97:    */   {
/*  98:145 */     return this.map.values();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Set<Map.Entry<K, V>> entrySet()
/* 102:    */   {
/* 103:150 */     Set<Map.Entry<K, V>> set = new HashSet(this.map.size());
/* 104:151 */     for (Map.Entry<IdentityKey<K>, V> entry : this.map.entrySet()) {
/* 105:152 */       set.add(new IdentityMapEntry(((IdentityKey)entry.getKey()).getRealKey(), entry.getValue()));
/* 106:    */     }
/* 107:154 */     return set;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Map.Entry[] entryArray()
/* 111:    */   {
/* 112:159 */     if (this.dirty)
/* 113:    */     {
/* 114:160 */       this.entryArray = new Map.Entry[this.map.size()];
/* 115:161 */       Iterator itr = this.map.entrySet().iterator();
/* 116:162 */       int i = 0;
/* 117:163 */       while (itr.hasNext())
/* 118:    */       {
/* 119:164 */         Map.Entry me = (Map.Entry)itr.next();
/* 120:165 */         this.entryArray[(i++)] = new IdentityMapEntry(((IdentityKey)me.getKey()).key, me.getValue());
/* 121:    */       }
/* 122:167 */       this.dirty = false;
/* 123:    */     }
/* 124:169 */     return this.entryArray;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String toString()
/* 128:    */   {
/* 129:174 */     return this.map.toString();
/* 130:    */   }
/* 131:    */   
/* 132:    */   static final class KeyIterator<K>
/* 133:    */     implements Iterator<K>
/* 134:    */   {
/* 135:    */     private final Iterator<IdentityMap.IdentityKey<K>> identityKeyIterator;
/* 136:    */     
/* 137:    */     private KeyIterator(Iterator<IdentityMap.IdentityKey<K>> iterator)
/* 138:    */     {
/* 139:181 */       this.identityKeyIterator = iterator;
/* 140:    */     }
/* 141:    */     
/* 142:    */     public boolean hasNext()
/* 143:    */     {
/* 144:185 */       return this.identityKeyIterator.hasNext();
/* 145:    */     }
/* 146:    */     
/* 147:    */     public K next()
/* 148:    */     {
/* 149:189 */       return ((IdentityMap.IdentityKey)this.identityKeyIterator.next()).getRealKey();
/* 150:    */     }
/* 151:    */     
/* 152:    */     public void remove()
/* 153:    */     {
/* 154:193 */       throw new UnsupportedOperationException();
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static final class IdentityMapEntry<K, V>
/* 159:    */     implements Map.Entry<K, V>
/* 160:    */   {
/* 161:    */     private K key;
/* 162:    */     private V value;
/* 163:    */     
/* 164:    */     IdentityMapEntry(K key, V value)
/* 165:    */     {
/* 166:202 */       this.key = key;
/* 167:203 */       this.value = value;
/* 168:    */     }
/* 169:    */     
/* 170:    */     public K getKey()
/* 171:    */     {
/* 172:207 */       return this.key;
/* 173:    */     }
/* 174:    */     
/* 175:    */     public V getValue()
/* 176:    */     {
/* 177:211 */       return this.value;
/* 178:    */     }
/* 179:    */     
/* 180:    */     public V setValue(V value)
/* 181:    */     {
/* 182:215 */       V result = this.value;
/* 183:216 */       this.value = value;
/* 184:217 */       return result;
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static final class IdentityKey<K>
/* 189:    */     implements Serializable
/* 190:    */   {
/* 191:    */     private K key;
/* 192:    */     
/* 193:    */     IdentityKey(K key)
/* 194:    */     {
/* 195:225 */       this.key = key;
/* 196:    */     }
/* 197:    */     
/* 198:    */     public boolean equals(Object other)
/* 199:    */     {
/* 200:231 */       return this.key == ((IdentityKey)other).key;
/* 201:    */     }
/* 202:    */     
/* 203:    */     public int hashCode()
/* 204:    */     {
/* 205:236 */       return System.identityHashCode(this.key);
/* 206:    */     }
/* 207:    */     
/* 208:    */     public String toString()
/* 209:    */     {
/* 210:241 */       return this.key.toString();
/* 211:    */     }
/* 212:    */     
/* 213:    */     public K getRealKey()
/* 214:    */     {
/* 215:245 */       return this.key;
/* 216:    */     }
/* 217:    */   }
/* 218:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.IdentityMap
 * JD-Core Version:    0.7.0.1
 */
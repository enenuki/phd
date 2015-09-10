/*   1:    */ package org.hibernate.collection.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.SortedMap;
/*  11:    */ import java.util.TreeMap;
/*  12:    */ import org.hibernate.EntityMode;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  15:    */ import org.hibernate.persister.collection.BasicCollectionPersister;
/*  16:    */ import org.hibernate.type.Type;
/*  17:    */ 
/*  18:    */ public class PersistentSortedMap
/*  19:    */   extends PersistentMap
/*  20:    */   implements SortedMap
/*  21:    */ {
/*  22:    */   protected Comparator comparator;
/*  23:    */   
/*  24:    */   protected Serializable snapshot(BasicCollectionPersister persister, EntityMode entityMode)
/*  25:    */     throws HibernateException
/*  26:    */   {
/*  27: 52 */     TreeMap clonedMap = new TreeMap(this.comparator);
/*  28: 53 */     Iterator iter = this.map.entrySet().iterator();
/*  29: 54 */     while (iter.hasNext())
/*  30:    */     {
/*  31: 55 */       Map.Entry e = (Map.Entry)iter.next();
/*  32: 56 */       clonedMap.put(e.getKey(), persister.getElementType().deepCopy(e.getValue(), persister.getFactory()));
/*  33:    */     }
/*  34: 58 */     return clonedMap;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public PersistentSortedMap(SessionImplementor session)
/*  38:    */   {
/*  39: 62 */     super(session);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setComparator(Comparator comparator)
/*  43:    */   {
/*  44: 66 */     this.comparator = comparator;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public PersistentSortedMap(SessionImplementor session, SortedMap map)
/*  48:    */   {
/*  49: 70 */     super(session, map);
/*  50: 71 */     this.comparator = map.comparator();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public PersistentSortedMap() {}
/*  54:    */   
/*  55:    */   public Comparator comparator()
/*  56:    */   {
/*  57: 80 */     return this.comparator;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public SortedMap subMap(Object fromKey, Object toKey)
/*  61:    */   {
/*  62: 87 */     read();
/*  63: 88 */     SortedMap m = ((SortedMap)this.map).subMap(fromKey, toKey);
/*  64: 89 */     return new SortedSubMap(m);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public SortedMap headMap(Object toKey)
/*  68:    */   {
/*  69: 96 */     read();
/*  70:    */     
/*  71: 98 */     SortedMap m = ((SortedMap)this.map).headMap(toKey);
/*  72: 99 */     return new SortedSubMap(m);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public SortedMap tailMap(Object fromKey)
/*  76:    */   {
/*  77:106 */     read();
/*  78:    */     
/*  79:108 */     SortedMap m = ((SortedMap)this.map).tailMap(fromKey);
/*  80:109 */     return new SortedSubMap(m);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Object firstKey()
/*  84:    */   {
/*  85:116 */     read();
/*  86:117 */     return ((SortedMap)this.map).firstKey();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Object lastKey()
/*  90:    */   {
/*  91:124 */     read();
/*  92:125 */     return ((SortedMap)this.map).lastKey();
/*  93:    */   }
/*  94:    */   
/*  95:    */   class SortedSubMap
/*  96:    */     implements SortedMap
/*  97:    */   {
/*  98:    */     SortedMap submap;
/*  99:    */     
/* 100:    */     SortedSubMap(SortedMap m)
/* 101:    */     {
/* 102:133 */       this.submap = m;
/* 103:    */     }
/* 104:    */     
/* 105:    */     public int size()
/* 106:    */     {
/* 107:137 */       return this.submap.size();
/* 108:    */     }
/* 109:    */     
/* 110:    */     public boolean isEmpty()
/* 111:    */     {
/* 112:140 */       return this.submap.isEmpty();
/* 113:    */     }
/* 114:    */     
/* 115:    */     public boolean containsKey(Object key)
/* 116:    */     {
/* 117:143 */       return this.submap.containsKey(key);
/* 118:    */     }
/* 119:    */     
/* 120:    */     public boolean containsValue(Object key)
/* 121:    */     {
/* 122:146 */       return this.submap.containsValue(key);
/* 123:    */     }
/* 124:    */     
/* 125:    */     public Object get(Object key)
/* 126:    */     {
/* 127:149 */       return this.submap.get(key);
/* 128:    */     }
/* 129:    */     
/* 130:    */     public Object put(Object key, Object value)
/* 131:    */     {
/* 132:152 */       PersistentSortedMap.this.write();
/* 133:153 */       return this.submap.put(key, value);
/* 134:    */     }
/* 135:    */     
/* 136:    */     public Object remove(Object key)
/* 137:    */     {
/* 138:156 */       PersistentSortedMap.this.write();
/* 139:157 */       return this.submap.remove(key);
/* 140:    */     }
/* 141:    */     
/* 142:    */     public void putAll(Map other)
/* 143:    */     {
/* 144:160 */       PersistentSortedMap.this.write();
/* 145:161 */       this.submap.putAll(other);
/* 146:    */     }
/* 147:    */     
/* 148:    */     public void clear()
/* 149:    */     {
/* 150:164 */       PersistentSortedMap.this.write();
/* 151:165 */       this.submap.clear();
/* 152:    */     }
/* 153:    */     
/* 154:    */     public Set keySet()
/* 155:    */     {
/* 156:168 */       return new AbstractPersistentCollection.SetProxy(PersistentSortedMap.this, this.submap.keySet());
/* 157:    */     }
/* 158:    */     
/* 159:    */     public Collection values()
/* 160:    */     {
/* 161:171 */       return new AbstractPersistentCollection.SetProxy(PersistentSortedMap.this, this.submap.values());
/* 162:    */     }
/* 163:    */     
/* 164:    */     public Set entrySet()
/* 165:    */     {
/* 166:174 */       return new PersistentMap.EntrySetProxy(PersistentSortedMap.this, this.submap.entrySet());
/* 167:    */     }
/* 168:    */     
/* 169:    */     public Comparator comparator()
/* 170:    */     {
/* 171:178 */       return this.submap.comparator();
/* 172:    */     }
/* 173:    */     
/* 174:    */     public SortedMap subMap(Object fromKey, Object toKey)
/* 175:    */     {
/* 176:182 */       SortedMap m = this.submap.subMap(fromKey, toKey);
/* 177:183 */       return new SortedSubMap(PersistentSortedMap.this, m);
/* 178:    */     }
/* 179:    */     
/* 180:    */     public SortedMap headMap(Object toKey)
/* 181:    */     {
/* 182:187 */       SortedMap m = this.submap.headMap(toKey);
/* 183:188 */       return new SortedSubMap(PersistentSortedMap.this, m);
/* 184:    */     }
/* 185:    */     
/* 186:    */     public SortedMap tailMap(Object fromKey)
/* 187:    */     {
/* 188:192 */       SortedMap m = this.submap.tailMap(fromKey);
/* 189:193 */       return new SortedSubMap(PersistentSortedMap.this, m);
/* 190:    */     }
/* 191:    */     
/* 192:    */     public Object firstKey()
/* 193:    */     {
/* 194:196 */       return this.submap.firstKey();
/* 195:    */     }
/* 196:    */     
/* 197:    */     public Object lastKey()
/* 198:    */     {
/* 199:199 */       return this.submap.lastKey();
/* 200:    */     }
/* 201:    */   }
/* 202:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentSortedMap
 * JD-Core Version:    0.7.0.1
 */
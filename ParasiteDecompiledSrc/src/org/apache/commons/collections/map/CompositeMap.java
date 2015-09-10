/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.apache.commons.collections.CollectionUtils;
/*   8:    */ import org.apache.commons.collections.collection.CompositeCollection;
/*   9:    */ import org.apache.commons.collections.set.CompositeSet;
/*  10:    */ 
/*  11:    */ public class CompositeMap
/*  12:    */   implements Map
/*  13:    */ {
/*  14:    */   private Map[] composite;
/*  15:    */   private MapMutator mutator;
/*  16:    */   
/*  17:    */   public CompositeMap()
/*  18:    */   {
/*  19: 58 */     this(new Map[0], null);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public CompositeMap(Map one, Map two)
/*  23:    */   {
/*  24: 69 */     this(new Map[] { one, two }, null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public CompositeMap(Map one, Map two, MapMutator mutator)
/*  28:    */   {
/*  29: 80 */     this(new Map[] { one, two }, mutator);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public CompositeMap(Map[] composite)
/*  33:    */   {
/*  34: 91 */     this(composite, null);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public CompositeMap(Map[] composite, MapMutator mutator)
/*  38:    */   {
/*  39:102 */     this.mutator = mutator;
/*  40:103 */     this.composite = new Map[0];
/*  41:104 */     for (int i = composite.length - 1; i >= 0; i--) {
/*  42:105 */       addComposited(composite[i]);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setMutator(MapMutator mutator)
/*  47:    */   {
/*  48:116 */     this.mutator = mutator;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public synchronized void addComposited(Map map)
/*  52:    */     throws IllegalArgumentException
/*  53:    */   {
/*  54:127 */     for (int i = this.composite.length - 1; i >= 0; i--)
/*  55:    */     {
/*  56:128 */       Collection intersect = CollectionUtils.intersection(this.composite[i].keySet(), map.keySet());
/*  57:129 */       if (intersect.size() != 0)
/*  58:    */       {
/*  59:130 */         if (this.mutator == null) {
/*  60:131 */           throw new IllegalArgumentException("Key collision adding Map to CompositeMap");
/*  61:    */         }
/*  62:134 */         this.mutator.resolveCollision(this, this.composite[i], map, intersect);
/*  63:    */       }
/*  64:    */     }
/*  65:138 */     Map[] temp = new Map[this.composite.length + 1];
/*  66:139 */     System.arraycopy(this.composite, 0, temp, 0, this.composite.length);
/*  67:140 */     temp[(temp.length - 1)] = map;
/*  68:141 */     this.composite = temp;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public synchronized Map removeComposited(Map map)
/*  72:    */   {
/*  73:151 */     int size = this.composite.length;
/*  74:152 */     for (int i = 0; i < size; i++) {
/*  75:153 */       if (this.composite[i].equals(map))
/*  76:    */       {
/*  77:154 */         Map[] temp = new Map[size - 1];
/*  78:155 */         System.arraycopy(this.composite, 0, temp, 0, i);
/*  79:156 */         System.arraycopy(this.composite, i + 1, temp, i, size - i - 1);
/*  80:157 */         this.composite = temp;
/*  81:158 */         return map;
/*  82:    */       }
/*  83:    */     }
/*  84:161 */     return null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void clear()
/*  88:    */   {
/*  89:171 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/*  90:172 */       this.composite[i].clear();
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean containsKey(Object key)
/*  95:    */   {
/*  96:193 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/*  97:194 */       if (this.composite[i].containsKey(key)) {
/*  98:195 */         return true;
/*  99:    */       }
/* 100:    */     }
/* 101:198 */     return false;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean containsValue(Object value)
/* 105:    */   {
/* 106:218 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/* 107:219 */       if (this.composite[i].containsValue(value)) {
/* 108:220 */         return true;
/* 109:    */       }
/* 110:    */     }
/* 111:223 */     return false;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Set entrySet()
/* 115:    */   {
/* 116:244 */     CompositeSet entries = new CompositeSet();
/* 117:245 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/* 118:246 */       entries.addComposited(this.composite[i].entrySet());
/* 119:    */     }
/* 120:248 */     return entries;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Object get(Object key)
/* 124:    */   {
/* 125:276 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/* 126:277 */       if (this.composite[i].containsKey(key)) {
/* 127:278 */         return this.composite[i].get(key);
/* 128:    */       }
/* 129:    */     }
/* 130:281 */     return null;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isEmpty()
/* 134:    */   {
/* 135:290 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/* 136:291 */       if (!this.composite[i].isEmpty()) {
/* 137:292 */         return false;
/* 138:    */       }
/* 139:    */     }
/* 140:295 */     return true;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Set keySet()
/* 144:    */   {
/* 145:314 */     CompositeSet keys = new CompositeSet();
/* 146:315 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/* 147:316 */       keys.addComposited(this.composite[i].keySet());
/* 148:    */     }
/* 149:318 */     return keys;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Object put(Object key, Object value)
/* 153:    */   {
/* 154:347 */     if (this.mutator == null) {
/* 155:348 */       throw new UnsupportedOperationException("No mutator specified");
/* 156:    */     }
/* 157:350 */     return this.mutator.put(this, this.composite, key, value);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void putAll(Map map)
/* 161:    */   {
/* 162:376 */     if (this.mutator == null) {
/* 163:377 */       throw new UnsupportedOperationException("No mutator specified");
/* 164:    */     }
/* 165:379 */     this.mutator.putAll(this, this.composite, map);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Object remove(Object key)
/* 169:    */   {
/* 170:408 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/* 171:409 */       if (this.composite[i].containsKey(key)) {
/* 172:410 */         return this.composite[i].remove(key);
/* 173:    */       }
/* 174:    */     }
/* 175:413 */     return null;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int size()
/* 179:    */   {
/* 180:424 */     int size = 0;
/* 181:425 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/* 182:426 */       size += this.composite[i].size();
/* 183:    */     }
/* 184:428 */     return size;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Collection values()
/* 188:    */   {
/* 189:445 */     CompositeCollection keys = new CompositeCollection();
/* 190:446 */     for (int i = this.composite.length - 1; i >= 0; i--) {
/* 191:447 */       keys.addComposited(this.composite[i].values());
/* 192:    */     }
/* 193:449 */     return keys;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean equals(Object obj)
/* 197:    */   {
/* 198:459 */     if ((obj instanceof Map))
/* 199:    */     {
/* 200:460 */       Map map = (Map)obj;
/* 201:461 */       return entrySet().equals(map.entrySet());
/* 202:    */     }
/* 203:463 */     return false;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int hashCode()
/* 207:    */   {
/* 208:470 */     int code = 0;
/* 209:471 */     for (Iterator i = entrySet().iterator(); i.hasNext();) {
/* 210:472 */       code += i.next().hashCode();
/* 211:    */     }
/* 212:474 */     return code;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static abstract interface MapMutator
/* 216:    */   {
/* 217:    */     public abstract void resolveCollision(CompositeMap paramCompositeMap, Map paramMap1, Map paramMap2, Collection paramCollection);
/* 218:    */     
/* 219:    */     public abstract Object put(CompositeMap paramCompositeMap, Map[] paramArrayOfMap, Object paramObject1, Object paramObject2);
/* 220:    */     
/* 221:    */     public abstract void putAll(CompositeMap paramCompositeMap, Map[] paramArrayOfMap, Map paramMap);
/* 222:    */   }
/* 223:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.CompositeMap
 * JD-Core Version:    0.7.0.1
 */
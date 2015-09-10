/*   1:    */ package org.apache.commons.collections.bidimap;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Comparator;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.ListIterator;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Map.Entry;
/*  13:    */ import java.util.Set;
/*  14:    */ import java.util.SortedMap;
/*  15:    */ import java.util.TreeMap;
/*  16:    */ import org.apache.commons.collections.BidiMap;
/*  17:    */ import org.apache.commons.collections.OrderedBidiMap;
/*  18:    */ import org.apache.commons.collections.OrderedMap;
/*  19:    */ import org.apache.commons.collections.OrderedMapIterator;
/*  20:    */ import org.apache.commons.collections.ResettableIterator;
/*  21:    */ import org.apache.commons.collections.SortedBidiMap;
/*  22:    */ import org.apache.commons.collections.map.AbstractSortedMapDecorator;
/*  23:    */ 
/*  24:    */ public class DualTreeBidiMap
/*  25:    */   extends AbstractDualBidiMap
/*  26:    */   implements SortedBidiMap, Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 721969328361809L;
/*  29:    */   protected final Comparator comparator;
/*  30:    */   
/*  31:    */   public DualTreeBidiMap()
/*  32:    */   {
/*  33: 70 */     super(new TreeMap(), new TreeMap());
/*  34: 71 */     this.comparator = null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public DualTreeBidiMap(Map map)
/*  38:    */   {
/*  39: 81 */     super(new TreeMap(), new TreeMap());
/*  40: 82 */     putAll(map);
/*  41: 83 */     this.comparator = null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public DualTreeBidiMap(Comparator comparator)
/*  45:    */   {
/*  46: 92 */     super(new TreeMap(comparator), new TreeMap(comparator));
/*  47: 93 */     this.comparator = comparator;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected DualTreeBidiMap(Map normalMap, Map reverseMap, BidiMap inverseBidiMap)
/*  51:    */   {
/*  52:104 */     super(normalMap, reverseMap, inverseBidiMap);
/*  53:105 */     this.comparator = ((SortedMap)normalMap).comparator();
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected BidiMap createBidiMap(Map normalMap, Map reverseMap, BidiMap inverseMap)
/*  57:    */   {
/*  58:117 */     return new DualTreeBidiMap(normalMap, reverseMap, inverseMap);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Comparator comparator()
/*  62:    */   {
/*  63:122 */     return ((SortedMap)this.maps[0]).comparator();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Object firstKey()
/*  67:    */   {
/*  68:126 */     return ((SortedMap)this.maps[0]).firstKey();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Object lastKey()
/*  72:    */   {
/*  73:130 */     return ((SortedMap)this.maps[0]).lastKey();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object nextKey(Object key)
/*  77:    */   {
/*  78:134 */     if (isEmpty()) {
/*  79:135 */       return null;
/*  80:    */     }
/*  81:137 */     if ((this.maps[0] instanceof OrderedMap)) {
/*  82:138 */       return ((OrderedMap)this.maps[0]).nextKey(key);
/*  83:    */     }
/*  84:140 */     SortedMap sm = (SortedMap)this.maps[0];
/*  85:141 */     Iterator it = sm.tailMap(key).keySet().iterator();
/*  86:142 */     it.next();
/*  87:143 */     if (it.hasNext()) {
/*  88:144 */       return it.next();
/*  89:    */     }
/*  90:146 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Object previousKey(Object key)
/*  94:    */   {
/*  95:150 */     if (isEmpty()) {
/*  96:151 */       return null;
/*  97:    */     }
/*  98:153 */     if ((this.maps[0] instanceof OrderedMap)) {
/*  99:154 */       return ((OrderedMap)this.maps[0]).previousKey(key);
/* 100:    */     }
/* 101:156 */     SortedMap sm = (SortedMap)this.maps[0];
/* 102:157 */     SortedMap hm = sm.headMap(key);
/* 103:158 */     if (hm.isEmpty()) {
/* 104:159 */       return null;
/* 105:    */     }
/* 106:161 */     return hm.lastKey();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public OrderedMapIterator orderedMapIterator()
/* 110:    */   {
/* 111:174 */     return new BidiOrderedMapIterator(this);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public SortedBidiMap inverseSortedBidiMap()
/* 115:    */   {
/* 116:178 */     return (SortedBidiMap)inverseBidiMap();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public OrderedBidiMap inverseOrderedBidiMap()
/* 120:    */   {
/* 121:182 */     return (OrderedBidiMap)inverseBidiMap();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public SortedMap headMap(Object toKey)
/* 125:    */   {
/* 126:187 */     SortedMap sub = ((SortedMap)this.maps[0]).headMap(toKey);
/* 127:188 */     return new ViewMap(this, sub);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public SortedMap tailMap(Object fromKey)
/* 131:    */   {
/* 132:192 */     SortedMap sub = ((SortedMap)this.maps[0]).tailMap(fromKey);
/* 133:193 */     return new ViewMap(this, sub);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public SortedMap subMap(Object fromKey, Object toKey)
/* 137:    */   {
/* 138:197 */     SortedMap sub = ((SortedMap)this.maps[0]).subMap(fromKey, toKey);
/* 139:198 */     return new ViewMap(this, sub);
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected static class ViewMap
/* 143:    */     extends AbstractSortedMapDecorator
/* 144:    */   {
/* 145:    */     final DualTreeBidiMap bidi;
/* 146:    */     
/* 147:    */     protected ViewMap(DualTreeBidiMap bidi, SortedMap sm)
/* 148:    */     {
/* 149:218 */       super();
/* 150:219 */       this.bidi = ((DualTreeBidiMap)this.map);
/* 151:    */     }
/* 152:    */     
/* 153:    */     public boolean containsValue(Object value)
/* 154:    */     {
/* 155:224 */       return this.bidi.maps[0].containsValue(value);
/* 156:    */     }
/* 157:    */     
/* 158:    */     public void clear()
/* 159:    */     {
/* 160:229 */       for (Iterator it = keySet().iterator(); it.hasNext();)
/* 161:    */       {
/* 162:230 */         it.next();
/* 163:231 */         it.remove();
/* 164:    */       }
/* 165:    */     }
/* 166:    */     
/* 167:    */     public SortedMap headMap(Object toKey)
/* 168:    */     {
/* 169:236 */       return new ViewMap(this.bidi, super.headMap(toKey));
/* 170:    */     }
/* 171:    */     
/* 172:    */     public SortedMap tailMap(Object fromKey)
/* 173:    */     {
/* 174:240 */       return new ViewMap(this.bidi, super.tailMap(fromKey));
/* 175:    */     }
/* 176:    */     
/* 177:    */     public SortedMap subMap(Object fromKey, Object toKey)
/* 178:    */     {
/* 179:244 */       return new ViewMap(this.bidi, super.subMap(fromKey, toKey));
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected static class BidiOrderedMapIterator
/* 184:    */     implements OrderedMapIterator, ResettableIterator
/* 185:    */   {
/* 186:    */     protected final AbstractDualBidiMap parent;
/* 187:    */     protected ListIterator iterator;
/* 188:259 */     private Map.Entry last = null;
/* 189:    */     
/* 190:    */     protected BidiOrderedMapIterator(AbstractDualBidiMap parent)
/* 191:    */     {
/* 192:267 */       this.parent = parent;
/* 193:268 */       this.iterator = new ArrayList(parent.entrySet()).listIterator();
/* 194:    */     }
/* 195:    */     
/* 196:    */     public boolean hasNext()
/* 197:    */     {
/* 198:272 */       return this.iterator.hasNext();
/* 199:    */     }
/* 200:    */     
/* 201:    */     public Object next()
/* 202:    */     {
/* 203:276 */       this.last = ((Map.Entry)this.iterator.next());
/* 204:277 */       return this.last.getKey();
/* 205:    */     }
/* 206:    */     
/* 207:    */     public boolean hasPrevious()
/* 208:    */     {
/* 209:281 */       return this.iterator.hasPrevious();
/* 210:    */     }
/* 211:    */     
/* 212:    */     public Object previous()
/* 213:    */     {
/* 214:285 */       this.last = ((Map.Entry)this.iterator.previous());
/* 215:286 */       return this.last.getKey();
/* 216:    */     }
/* 217:    */     
/* 218:    */     public void remove()
/* 219:    */     {
/* 220:290 */       this.iterator.remove();
/* 221:291 */       this.parent.remove(this.last.getKey());
/* 222:292 */       this.last = null;
/* 223:    */     }
/* 224:    */     
/* 225:    */     public Object getKey()
/* 226:    */     {
/* 227:296 */       if (this.last == null) {
/* 228:297 */         throw new IllegalStateException("Iterator getKey() can only be called after next() and before remove()");
/* 229:    */       }
/* 230:299 */       return this.last.getKey();
/* 231:    */     }
/* 232:    */     
/* 233:    */     public Object getValue()
/* 234:    */     {
/* 235:303 */       if (this.last == null) {
/* 236:304 */         throw new IllegalStateException("Iterator getValue() can only be called after next() and before remove()");
/* 237:    */       }
/* 238:306 */       return this.last.getValue();
/* 239:    */     }
/* 240:    */     
/* 241:    */     public Object setValue(Object value)
/* 242:    */     {
/* 243:310 */       if (this.last == null) {
/* 244:311 */         throw new IllegalStateException("Iterator setValue() can only be called after next() and before remove()");
/* 245:    */       }
/* 246:313 */       if ((this.parent.maps[1].containsKey(value)) && (this.parent.maps[1].get(value) != this.last.getKey())) {
/* 247:315 */         throw new IllegalArgumentException("Cannot use setValue() when the object being set is already in the map");
/* 248:    */       }
/* 249:317 */       return this.parent.put(this.last.getKey(), value);
/* 250:    */     }
/* 251:    */     
/* 252:    */     public void reset()
/* 253:    */     {
/* 254:321 */       this.iterator = new ArrayList(this.parent.entrySet()).listIterator();
/* 255:322 */       this.last = null;
/* 256:    */     }
/* 257:    */     
/* 258:    */     public String toString()
/* 259:    */     {
/* 260:326 */       if (this.last != null) {
/* 261:327 */         return "MapIterator[" + getKey() + "=" + getValue() + "]";
/* 262:    */       }
/* 263:329 */       return "MapIterator[]";
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   private void writeObject(ObjectOutputStream out)
/* 268:    */     throws IOException
/* 269:    */   {
/* 270:337 */     out.defaultWriteObject();
/* 271:338 */     out.writeObject(this.maps[0]);
/* 272:    */   }
/* 273:    */   
/* 274:    */   private void readObject(ObjectInputStream in)
/* 275:    */     throws IOException, ClassNotFoundException
/* 276:    */   {
/* 277:342 */     in.defaultReadObject();
/* 278:343 */     this.maps[0] = new TreeMap(this.comparator);
/* 279:344 */     this.maps[1] = new TreeMap(this.comparator);
/* 280:345 */     Map map = (Map)in.readObject();
/* 281:346 */     putAll(map);
/* 282:    */   }
/* 283:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.DualTreeBidiMap
 * JD-Core Version:    0.7.0.1
 */
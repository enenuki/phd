/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.AbstractSet;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.NoSuchElementException;
/*  11:    */ import java.util.Set;
/*  12:    */ import org.apache.commons.collections.BoundedMap;
/*  13:    */ import org.apache.commons.collections.KeyValue;
/*  14:    */ import org.apache.commons.collections.MapIterator;
/*  15:    */ import org.apache.commons.collections.OrderedMap;
/*  16:    */ import org.apache.commons.collections.OrderedMapIterator;
/*  17:    */ import org.apache.commons.collections.ResettableIterator;
/*  18:    */ import org.apache.commons.collections.iterators.SingletonIterator;
/*  19:    */ import org.apache.commons.collections.keyvalue.TiedMapEntry;
/*  20:    */ 
/*  21:    */ public class SingletonMap
/*  22:    */   implements OrderedMap, BoundedMap, KeyValue, Serializable, Cloneable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = -8931271118676803261L;
/*  25:    */   private final Object key;
/*  26:    */   private Object value;
/*  27:    */   
/*  28:    */   public SingletonMap()
/*  29:    */   {
/*  30: 78 */     this.key = null;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public SingletonMap(Object key, Object value)
/*  34:    */   {
/*  35: 89 */     this.key = key;
/*  36: 90 */     this.value = value;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public SingletonMap(KeyValue keyValue)
/*  40:    */   {
/*  41:100 */     this.key = keyValue.getKey();
/*  42:101 */     this.value = keyValue.getValue();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public SingletonMap(Map.Entry mapEntry)
/*  46:    */   {
/*  47:111 */     this.key = mapEntry.getKey();
/*  48:112 */     this.value = mapEntry.getValue();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public SingletonMap(Map map)
/*  52:    */   {
/*  53:124 */     if (map.size() != 1) {
/*  54:125 */       throw new IllegalArgumentException("The map size must be 1");
/*  55:    */     }
/*  56:127 */     Map.Entry entry = (Map.Entry)map.entrySet().iterator().next();
/*  57:128 */     this.key = entry.getKey();
/*  58:129 */     this.value = entry.getValue();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Object getKey()
/*  62:    */   {
/*  63:140 */     return this.key;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Object getValue()
/*  67:    */   {
/*  68:149 */     return this.value;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Object setValue(Object value)
/*  72:    */   {
/*  73:159 */     Object old = this.value;
/*  74:160 */     this.value = value;
/*  75:161 */     return old;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isFull()
/*  79:    */   {
/*  80:172 */     return true;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int maxSize()
/*  84:    */   {
/*  85:181 */     return 1;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Object get(Object key)
/*  89:    */   {
/*  90:193 */     if (isEqualKey(key)) {
/*  91:194 */       return this.value;
/*  92:    */     }
/*  93:196 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int size()
/*  97:    */   {
/*  98:205 */     return 1;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isEmpty()
/* 102:    */   {
/* 103:214 */     return false;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean containsKey(Object key)
/* 107:    */   {
/* 108:225 */     return isEqualKey(key);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean containsValue(Object value)
/* 112:    */   {
/* 113:235 */     return isEqualValue(value);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Object put(Object key, Object value)
/* 117:    */   {
/* 118:251 */     if (isEqualKey(key)) {
/* 119:252 */       return setValue(value);
/* 120:    */     }
/* 121:254 */     throw new IllegalArgumentException("Cannot put new key/value pair - Map is fixed size singleton");
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void putAll(Map map)
/* 125:    */   {
/* 126:269 */     switch (map.size())
/* 127:    */     {
/* 128:    */     case 0: 
/* 129:271 */       return;
/* 130:    */     case 1: 
/* 131:274 */       Map.Entry entry = (Map.Entry)map.entrySet().iterator().next();
/* 132:275 */       put(entry.getKey(), entry.getValue());
/* 133:276 */       return;
/* 134:    */     }
/* 135:279 */     throw new IllegalArgumentException("The map size must be 0 or 1");
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Object remove(Object key)
/* 139:    */   {
/* 140:291 */     throw new UnsupportedOperationException();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void clear()
/* 144:    */   {
/* 145:298 */     throw new UnsupportedOperationException();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Set entrySet()
/* 149:    */   {
/* 150:310 */     Map.Entry entry = new TiedMapEntry(this, getKey());
/* 151:311 */     return Collections.singleton(entry);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Set keySet()
/* 155:    */   {
/* 156:322 */     return Collections.singleton(this.key);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Collection values()
/* 160:    */   {
/* 161:333 */     return new SingletonValues(this);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public MapIterator mapIterator()
/* 165:    */   {
/* 166:349 */     return new SingletonMapIterator(this);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public OrderedMapIterator orderedMapIterator()
/* 170:    */   {
/* 171:363 */     return new SingletonMapIterator(this);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Object firstKey()
/* 175:    */   {
/* 176:372 */     return getKey();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Object lastKey()
/* 180:    */   {
/* 181:381 */     return getKey();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Object nextKey(Object key)
/* 185:    */   {
/* 186:391 */     return null;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Object previousKey(Object key)
/* 190:    */   {
/* 191:401 */     return null;
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected boolean isEqualKey(Object key)
/* 195:    */   {
/* 196:412 */     return key == null ? false : getKey() == null ? true : key.equals(getKey());
/* 197:    */   }
/* 198:    */   
/* 199:    */   protected boolean isEqualValue(Object value)
/* 200:    */   {
/* 201:422 */     return value == null ? false : getValue() == null ? true : value.equals(getValue());
/* 202:    */   }
/* 203:    */   
/* 204:    */   static class SingletonMapIterator
/* 205:    */     implements OrderedMapIterator, ResettableIterator
/* 206:    */   {
/* 207:    */     private final SingletonMap parent;
/* 208:431 */     private boolean hasNext = true;
/* 209:432 */     private boolean canGetSet = false;
/* 210:    */     
/* 211:    */     SingletonMapIterator(SingletonMap parent)
/* 212:    */     {
/* 213:436 */       this.parent = parent;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public boolean hasNext()
/* 217:    */     {
/* 218:440 */       return this.hasNext;
/* 219:    */     }
/* 220:    */     
/* 221:    */     public Object next()
/* 222:    */     {
/* 223:444 */       if (!this.hasNext) {
/* 224:445 */         throw new NoSuchElementException("No next() entry in the iteration");
/* 225:    */       }
/* 226:447 */       this.hasNext = false;
/* 227:448 */       this.canGetSet = true;
/* 228:449 */       return this.parent.getKey();
/* 229:    */     }
/* 230:    */     
/* 231:    */     public boolean hasPrevious()
/* 232:    */     {
/* 233:453 */       return !this.hasNext;
/* 234:    */     }
/* 235:    */     
/* 236:    */     public Object previous()
/* 237:    */     {
/* 238:457 */       if (this.hasNext == true) {
/* 239:458 */         throw new NoSuchElementException("No previous() entry in the iteration");
/* 240:    */       }
/* 241:460 */       this.hasNext = true;
/* 242:461 */       return this.parent.getKey();
/* 243:    */     }
/* 244:    */     
/* 245:    */     public void remove()
/* 246:    */     {
/* 247:465 */       throw new UnsupportedOperationException();
/* 248:    */     }
/* 249:    */     
/* 250:    */     public Object getKey()
/* 251:    */     {
/* 252:469 */       if (!this.canGetSet) {
/* 253:470 */         throw new IllegalStateException("getKey() can only be called after next() and before remove()");
/* 254:    */       }
/* 255:472 */       return this.parent.getKey();
/* 256:    */     }
/* 257:    */     
/* 258:    */     public Object getValue()
/* 259:    */     {
/* 260:476 */       if (!this.canGetSet) {
/* 261:477 */         throw new IllegalStateException("getValue() can only be called after next() and before remove()");
/* 262:    */       }
/* 263:479 */       return this.parent.getValue();
/* 264:    */     }
/* 265:    */     
/* 266:    */     public Object setValue(Object value)
/* 267:    */     {
/* 268:483 */       if (!this.canGetSet) {
/* 269:484 */         throw new IllegalStateException("setValue() can only be called after next() and before remove()");
/* 270:    */       }
/* 271:486 */       return this.parent.setValue(value);
/* 272:    */     }
/* 273:    */     
/* 274:    */     public void reset()
/* 275:    */     {
/* 276:490 */       this.hasNext = true;
/* 277:    */     }
/* 278:    */     
/* 279:    */     public String toString()
/* 280:    */     {
/* 281:494 */       if (this.hasNext) {
/* 282:495 */         return "Iterator[]";
/* 283:    */       }
/* 284:497 */       return "Iterator[" + getKey() + "=" + getValue() + "]";
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   static class SingletonValues
/* 289:    */     extends AbstractSet
/* 290:    */     implements Serializable
/* 291:    */   {
/* 292:    */     private static final long serialVersionUID = -3689524741863047872L;
/* 293:    */     private final SingletonMap parent;
/* 294:    */     
/* 295:    */     SingletonValues(SingletonMap parent)
/* 296:    */     {
/* 297:512 */       this.parent = parent;
/* 298:    */     }
/* 299:    */     
/* 300:    */     public int size()
/* 301:    */     {
/* 302:516 */       return 1;
/* 303:    */     }
/* 304:    */     
/* 305:    */     public boolean isEmpty()
/* 306:    */     {
/* 307:519 */       return false;
/* 308:    */     }
/* 309:    */     
/* 310:    */     public boolean contains(Object object)
/* 311:    */     {
/* 312:522 */       return this.parent.containsValue(object);
/* 313:    */     }
/* 314:    */     
/* 315:    */     public void clear()
/* 316:    */     {
/* 317:525 */       throw new UnsupportedOperationException();
/* 318:    */     }
/* 319:    */     
/* 320:    */     public Iterator iterator()
/* 321:    */     {
/* 322:528 */       return new SingletonIterator(this.parent.getValue(), false);
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:    */   public Object clone()
/* 327:    */   {
/* 328:    */     try
/* 329:    */     {
/* 330:540 */       return (SingletonMap)super.clone();
/* 331:    */     }
/* 332:    */     catch (CloneNotSupportedException ex)
/* 333:    */     {
/* 334:543 */       throw new InternalError();
/* 335:    */     }
/* 336:    */   }
/* 337:    */   
/* 338:    */   public boolean equals(Object obj)
/* 339:    */   {
/* 340:554 */     if (obj == this) {
/* 341:555 */       return true;
/* 342:    */     }
/* 343:557 */     if (!(obj instanceof Map)) {
/* 344:558 */       return false;
/* 345:    */     }
/* 346:560 */     Map other = (Map)obj;
/* 347:561 */     if (other.size() != 1) {
/* 348:562 */       return false;
/* 349:    */     }
/* 350:564 */     Map.Entry entry = (Map.Entry)other.entrySet().iterator().next();
/* 351:565 */     return (isEqualKey(entry.getKey())) && (isEqualValue(entry.getValue()));
/* 352:    */   }
/* 353:    */   
/* 354:    */   public int hashCode()
/* 355:    */   {
/* 356:574 */     return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/* 357:    */   }
/* 358:    */   
/* 359:    */   public String toString()
/* 360:    */   {
/* 361:584 */     return 128 + '{' + (getKey() == this ? "(this Map)" : getKey()) + '=' + (getValue() == this ? "(this Map)" : getValue()) + '}';
/* 362:    */   }
/* 363:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.SingletonMap
 * JD-Core Version:    0.7.0.1
 */
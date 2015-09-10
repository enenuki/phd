/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.ConcurrentModificationException;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  11:    */ 
/*  12:    */ /**
/*  13:    */  * @deprecated
/*  14:    */  */
/*  15:    */ public abstract class DefaultMapBag
/*  16:    */   implements Bag
/*  17:    */ {
/*  18: 49 */   private Map _map = null;
/*  19: 50 */   private int _total = 0;
/*  20: 51 */   private int _mods = 0;
/*  21:    */   
/*  22:    */   public DefaultMapBag() {}
/*  23:    */   
/*  24:    */   protected DefaultMapBag(Map map)
/*  25:    */   {
/*  26: 68 */     setMap(map);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean add(Object object)
/*  30:    */   {
/*  31: 79 */     return add(object, 1);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean add(Object object, int nCopies)
/*  35:    */   {
/*  36: 90 */     this._mods += 1;
/*  37: 91 */     if (nCopies > 0)
/*  38:    */     {
/*  39: 92 */       int count = nCopies + getCount(object);
/*  40: 93 */       this._map.put(object, new Integer(count));
/*  41: 94 */       this._total += nCopies;
/*  42: 95 */       return count == nCopies;
/*  43:    */     }
/*  44: 97 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean addAll(Collection coll)
/*  48:    */   {
/*  49:108 */     boolean changed = false;
/*  50:109 */     Iterator i = coll.iterator();
/*  51:110 */     while (i.hasNext())
/*  52:    */     {
/*  53:111 */       boolean added = add(i.next());
/*  54:112 */       changed = (changed) || (added);
/*  55:    */     }
/*  56:114 */     return changed;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void clear()
/*  60:    */   {
/*  61:121 */     this._mods += 1;
/*  62:122 */     this._map.clear();
/*  63:123 */     this._total = 0;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean contains(Object object)
/*  67:    */   {
/*  68:134 */     return this._map.containsKey(object);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean containsAll(Collection coll)
/*  72:    */   {
/*  73:144 */     return containsAll(new HashBag(coll));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean containsAll(Bag other)
/*  77:    */   {
/*  78:155 */     boolean result = true;
/*  79:156 */     Iterator i = other.uniqueSet().iterator();
/*  80:157 */     while (i.hasNext())
/*  81:    */     {
/*  82:158 */       Object current = i.next();
/*  83:159 */       boolean contains = getCount(current) >= other.getCount(current);
/*  84:160 */       result = (result) && (contains);
/*  85:    */     }
/*  86:162 */     return result;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean equals(Object object)
/*  90:    */   {
/*  91:174 */     if (object == this) {
/*  92:175 */       return true;
/*  93:    */     }
/*  94:177 */     if (!(object instanceof Bag)) {
/*  95:178 */       return false;
/*  96:    */     }
/*  97:180 */     Bag other = (Bag)object;
/*  98:181 */     if (other.size() != size()) {
/*  99:182 */       return false;
/* 100:    */     }
/* 101:184 */     for (Iterator it = this._map.keySet().iterator(); it.hasNext();)
/* 102:    */     {
/* 103:185 */       Object element = it.next();
/* 104:186 */       if (other.getCount(element) != getCount(element)) {
/* 105:187 */         return false;
/* 106:    */       }
/* 107:    */     }
/* 108:190 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int hashCode()
/* 112:    */   {
/* 113:199 */     return this._map.hashCode();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean isEmpty()
/* 117:    */   {
/* 118:208 */     return this._map.isEmpty();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Iterator iterator()
/* 122:    */   {
/* 123:212 */     return new BagIterator(this, extractList().iterator());
/* 124:    */   }
/* 125:    */   
/* 126:    */   static class BagIterator
/* 127:    */     implements Iterator
/* 128:    */   {
/* 129:216 */     private DefaultMapBag _parent = null;
/* 130:217 */     private Iterator _support = null;
/* 131:218 */     private Object _current = null;
/* 132:219 */     private int _mods = 0;
/* 133:    */     
/* 134:    */     public BagIterator(DefaultMapBag parent, Iterator support)
/* 135:    */     {
/* 136:222 */       this._parent = parent;
/* 137:223 */       this._support = support;
/* 138:224 */       this._current = null;
/* 139:225 */       this._mods = parent.modCount();
/* 140:    */     }
/* 141:    */     
/* 142:    */     public boolean hasNext()
/* 143:    */     {
/* 144:229 */       return this._support.hasNext();
/* 145:    */     }
/* 146:    */     
/* 147:    */     public Object next()
/* 148:    */     {
/* 149:233 */       if (this._parent.modCount() != this._mods) {
/* 150:234 */         throw new ConcurrentModificationException();
/* 151:    */       }
/* 152:236 */       this._current = this._support.next();
/* 153:237 */       return this._current;
/* 154:    */     }
/* 155:    */     
/* 156:    */     public void remove()
/* 157:    */     {
/* 158:241 */       if (this._parent.modCount() != this._mods) {
/* 159:242 */         throw new ConcurrentModificationException();
/* 160:    */       }
/* 161:244 */       this._support.remove();
/* 162:245 */       this._parent.remove(this._current, 1);
/* 163:246 */       this._mods += 1;
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean remove(Object object)
/* 168:    */   {
/* 169:251 */     return remove(object, getCount(object));
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean remove(Object object, int nCopies)
/* 173:    */   {
/* 174:255 */     this._mods += 1;
/* 175:256 */     boolean result = false;
/* 176:257 */     int count = getCount(object);
/* 177:258 */     if (nCopies <= 0)
/* 178:    */     {
/* 179:259 */       result = false;
/* 180:    */     }
/* 181:260 */     else if (count > nCopies)
/* 182:    */     {
/* 183:261 */       this._map.put(object, new Integer(count - nCopies));
/* 184:262 */       result = true;
/* 185:263 */       this._total -= nCopies;
/* 186:    */     }
/* 187:    */     else
/* 188:    */     {
/* 189:266 */       result = this._map.remove(object) != null;
/* 190:267 */       this._total -= count;
/* 191:    */     }
/* 192:269 */     return result;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean removeAll(Collection coll)
/* 196:    */   {
/* 197:273 */     boolean result = false;
/* 198:274 */     if (coll != null)
/* 199:    */     {
/* 200:275 */       Iterator i = coll.iterator();
/* 201:276 */       while (i.hasNext())
/* 202:    */       {
/* 203:277 */         boolean changed = remove(i.next(), 1);
/* 204:278 */         result = (result) || (changed);
/* 205:    */       }
/* 206:    */     }
/* 207:281 */     return result;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public boolean retainAll(Collection coll)
/* 211:    */   {
/* 212:292 */     return retainAll(new HashBag(coll));
/* 213:    */   }
/* 214:    */   
/* 215:    */   public boolean retainAll(Bag other)
/* 216:    */   {
/* 217:304 */     boolean result = false;
/* 218:305 */     Bag excess = new HashBag();
/* 219:306 */     Iterator i = uniqueSet().iterator();
/* 220:307 */     while (i.hasNext())
/* 221:    */     {
/* 222:308 */       Object current = i.next();
/* 223:309 */       int myCount = getCount(current);
/* 224:310 */       int otherCount = other.getCount(current);
/* 225:311 */       if ((1 <= otherCount) && (otherCount <= myCount)) {
/* 226:312 */         excess.add(current, myCount - otherCount);
/* 227:    */       } else {
/* 228:314 */         excess.add(current, myCount);
/* 229:    */       }
/* 230:    */     }
/* 231:317 */     if (!excess.isEmpty()) {
/* 232:318 */       result = removeAll(excess);
/* 233:    */     }
/* 234:320 */     return result;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Object[] toArray()
/* 238:    */   {
/* 239:329 */     return extractList().toArray();
/* 240:    */   }
/* 241:    */   
/* 242:    */   public Object[] toArray(Object[] array)
/* 243:    */   {
/* 244:339 */     return extractList().toArray(array);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public int getCount(Object object)
/* 248:    */   {
/* 249:350 */     int result = 0;
/* 250:351 */     Integer count = MapUtils.getInteger(this._map, object);
/* 251:352 */     if (count != null) {
/* 252:353 */       result = count.intValue();
/* 253:    */     }
/* 254:355 */     return result;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Set uniqueSet()
/* 258:    */   {
/* 259:364 */     return UnmodifiableSet.decorate(this._map.keySet());
/* 260:    */   }
/* 261:    */   
/* 262:    */   public int size()
/* 263:    */   {
/* 264:373 */     return this._total;
/* 265:    */   }
/* 266:    */   
/* 267:    */   protected int calcTotalSize()
/* 268:    */   {
/* 269:383 */     this._total = extractList().size();
/* 270:384 */     return this._total;
/* 271:    */   }
/* 272:    */   
/* 273:    */   protected void setMap(Map map)
/* 274:    */   {
/* 275:393 */     if ((map == null) || (!map.isEmpty())) {
/* 276:394 */       throw new IllegalArgumentException("The map must be non-null and empty");
/* 277:    */     }
/* 278:396 */     this._map = map;
/* 279:    */   }
/* 280:    */   
/* 281:    */   protected Map getMap()
/* 282:    */   {
/* 283:405 */     return this._map;
/* 284:    */   }
/* 285:    */   
/* 286:    */   private List extractList()
/* 287:    */   {
/* 288:412 */     List result = new ArrayList();
/* 289:413 */     Iterator i = uniqueSet().iterator();
/* 290:414 */     while (i.hasNext())
/* 291:    */     {
/* 292:415 */       Object current = i.next();
/* 293:416 */       for (int index = getCount(current); index > 0; index--) {
/* 294:417 */         result.add(current);
/* 295:    */       }
/* 296:    */     }
/* 297:420 */     return result;
/* 298:    */   }
/* 299:    */   
/* 300:    */   private int modCount()
/* 301:    */   {
/* 302:429 */     return this._mods;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public String toString()
/* 306:    */   {
/* 307:438 */     StringBuffer buf = new StringBuffer();
/* 308:439 */     buf.append("[");
/* 309:440 */     Iterator i = uniqueSet().iterator();
/* 310:441 */     while (i.hasNext())
/* 311:    */     {
/* 312:442 */       Object current = i.next();
/* 313:443 */       int count = getCount(current);
/* 314:444 */       buf.append(count);
/* 315:445 */       buf.append(":");
/* 316:446 */       buf.append(current);
/* 317:447 */       if (i.hasNext()) {
/* 318:448 */         buf.append(",");
/* 319:    */       }
/* 320:    */     }
/* 321:451 */     buf.append("]");
/* 322:452 */     return buf.toString();
/* 323:    */   }
/* 324:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.DefaultMapBag
 * JD-Core Version:    0.7.0.1
 */
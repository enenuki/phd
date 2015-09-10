/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.util.AbstractCollection;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.NoSuchElementException;
/*  13:    */ import java.util.Set;
/*  14:    */ import org.apache.commons.collections.iterators.EmptyIterator;
/*  15:    */ 
/*  16:    */ /**
/*  17:    */  * @deprecated
/*  18:    */  */
/*  19:    */ public class MultiHashMap
/*  20:    */   extends HashMap
/*  21:    */   implements MultiMap
/*  22:    */ {
/*  23: 72 */   private transient Collection values = null;
/*  24:    */   private static final long serialVersionUID = 1943563828307035349L;
/*  25:    */   
/*  26:    */   public MultiHashMap() {}
/*  27:    */   
/*  28:    */   public MultiHashMap(int initialCapacity)
/*  29:    */   {
/*  30: 90 */     super(initialCapacity);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public MultiHashMap(int initialCapacity, float loadFactor)
/*  34:    */   {
/*  35:100 */     super(initialCapacity, loadFactor);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public MultiHashMap(Map mapToCopy)
/*  39:    */   {
/*  40:120 */     super((int)(mapToCopy.size() * 1.4F));
/*  41:121 */     putAll(mapToCopy);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void readObject(ObjectInputStream s)
/*  45:    */     throws IOException, ClassNotFoundException
/*  46:    */   {
/*  47:132 */     s.defaultReadObject();
/*  48:    */     
/*  49:    */ 
/*  50:135 */     String version = "1.2";
/*  51:    */     try
/*  52:    */     {
/*  53:137 */       version = System.getProperty("java.version");
/*  54:    */     }
/*  55:    */     catch (SecurityException ex) {}
/*  56:    */     Iterator iterator;
/*  57:142 */     if ((version.startsWith("1.2")) || (version.startsWith("1.3"))) {
/*  58:143 */       for (iterator = entrySet().iterator(); iterator.hasNext();)
/*  59:    */       {
/*  60:144 */         Map.Entry entry = (Map.Entry)iterator.next();
/*  61:    */         
/*  62:146 */         super.put(entry.getKey(), ((Collection)entry.getValue()).iterator().next());
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int totalSize()
/*  68:    */   {
/*  69:159 */     int total = 0;
/*  70:160 */     Collection values = super.values();
/*  71:161 */     for (Iterator it = values.iterator(); it.hasNext();)
/*  72:    */     {
/*  73:162 */       Collection coll = (Collection)it.next();
/*  74:163 */       total += coll.size();
/*  75:    */     }
/*  76:165 */     return total;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Collection getCollection(Object key)
/*  80:    */   {
/*  81:177 */     return (Collection)get(key);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int size(Object key)
/*  85:    */   {
/*  86:188 */     Collection coll = getCollection(key);
/*  87:189 */     if (coll == null) {
/*  88:190 */       return 0;
/*  89:    */     }
/*  90:192 */     return coll.size();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Iterator iterator(Object key)
/*  94:    */   {
/*  95:203 */     Collection coll = getCollection(key);
/*  96:204 */     if (coll == null) {
/*  97:205 */       return EmptyIterator.INSTANCE;
/*  98:    */     }
/*  99:207 */     return coll.iterator();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object put(Object key, Object value)
/* 103:    */   {
/* 104:223 */     Collection coll = getCollection(key);
/* 105:224 */     if (coll == null)
/* 106:    */     {
/* 107:225 */       coll = createCollection(null);
/* 108:226 */       super.put(key, coll);
/* 109:    */     }
/* 110:228 */     boolean results = coll.add(value);
/* 111:229 */     return results ? value : null;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void putAll(Map map)
/* 115:    */   {
/* 116:    */     Iterator it;
/* 117:    */     Iterator it;
/* 118:242 */     if ((map instanceof MultiMap)) {
/* 119:243 */       for (it = map.entrySet().iterator(); it.hasNext();)
/* 120:    */       {
/* 121:244 */         Map.Entry entry = (Map.Entry)it.next();
/* 122:245 */         Collection coll = (Collection)entry.getValue();
/* 123:246 */         putAll(entry.getKey(), coll);
/* 124:    */       }
/* 125:    */     } else {
/* 126:249 */       for (it = map.entrySet().iterator(); it.hasNext();)
/* 127:    */       {
/* 128:250 */         Map.Entry entry = (Map.Entry)it.next();
/* 129:251 */         put(entry.getKey(), entry.getValue());
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean putAll(Object key, Collection values)
/* 135:    */   {
/* 136:265 */     if ((values == null) || (values.size() == 0)) {
/* 137:266 */       return false;
/* 138:    */     }
/* 139:268 */     Collection coll = getCollection(key);
/* 140:269 */     if (coll == null)
/* 141:    */     {
/* 142:270 */       coll = createCollection(values);
/* 143:271 */       if (coll.size() == 0) {
/* 144:272 */         return false;
/* 145:    */       }
/* 146:274 */       super.put(key, coll);
/* 147:275 */       return true;
/* 148:    */     }
/* 149:277 */     return coll.addAll(values);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean containsValue(Object value)
/* 153:    */   {
/* 154:290 */     Set pairs = super.entrySet();
/* 155:292 */     if (pairs == null) {
/* 156:293 */       return false;
/* 157:    */     }
/* 158:295 */     Iterator pairsIterator = pairs.iterator();
/* 159:296 */     while (pairsIterator.hasNext())
/* 160:    */     {
/* 161:297 */       Map.Entry keyValuePair = (Map.Entry)pairsIterator.next();
/* 162:298 */       Collection coll = (Collection)keyValuePair.getValue();
/* 163:299 */       if (coll.contains(value)) {
/* 164:300 */         return true;
/* 165:    */       }
/* 166:    */     }
/* 167:303 */     return false;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean containsValue(Object key, Object value)
/* 171:    */   {
/* 172:314 */     Collection coll = getCollection(key);
/* 173:315 */     if (coll == null) {
/* 174:316 */       return false;
/* 175:    */     }
/* 176:318 */     return coll.contains(value);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Object remove(Object key, Object item)
/* 180:    */   {
/* 181:335 */     Collection valuesForKey = getCollection(key);
/* 182:336 */     if (valuesForKey == null) {
/* 183:337 */       return null;
/* 184:    */     }
/* 185:339 */     boolean removed = valuesForKey.remove(item);
/* 186:340 */     if (!removed) {
/* 187:341 */       return null;
/* 188:    */     }
/* 189:345 */     if (valuesForKey.isEmpty()) {
/* 190:346 */       remove(key);
/* 191:    */     }
/* 192:348 */     return item;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void clear()
/* 196:    */   {
/* 197:358 */     Set pairs = super.entrySet();
/* 198:359 */     Iterator pairsIterator = pairs.iterator();
/* 199:360 */     while (pairsIterator.hasNext())
/* 200:    */     {
/* 201:361 */       Map.Entry keyValuePair = (Map.Entry)pairsIterator.next();
/* 202:362 */       Collection coll = (Collection)keyValuePair.getValue();
/* 203:363 */       coll.clear();
/* 204:    */     }
/* 205:365 */     super.clear();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Collection values()
/* 209:    */   {
/* 210:376 */     Collection vs = this.values;
/* 211:377 */     return this.values = new Values(null);
/* 212:    */   }
/* 213:    */   
/* 214:    */   Iterator superValuesIterator()
/* 215:    */   {
/* 216:386 */     return super.values().iterator();
/* 217:    */   }
/* 218:    */   
/* 219:    */   private class Values
/* 220:    */     extends AbstractCollection
/* 221:    */   {
/* 222:    */     Values(MultiHashMap.1 x1)
/* 223:    */     {
/* 224:393 */       this();
/* 225:    */     }
/* 226:    */     
/* 227:    */     public Iterator iterator()
/* 228:    */     {
/* 229:396 */       return new MultiHashMap.ValueIterator(MultiHashMap.this, null);
/* 230:    */     }
/* 231:    */     
/* 232:    */     public int size()
/* 233:    */     {
/* 234:400 */       int compt = 0;
/* 235:401 */       Iterator it = iterator();
/* 236:402 */       while (it.hasNext())
/* 237:    */       {
/* 238:403 */         it.next();
/* 239:404 */         compt++;
/* 240:    */       }
/* 241:406 */       return compt;
/* 242:    */     }
/* 243:    */     
/* 244:    */     public void clear()
/* 245:    */     {
/* 246:410 */       MultiHashMap.this.clear();
/* 247:    */     }
/* 248:    */     
/* 249:    */     private Values() {}
/* 250:    */   }
/* 251:    */   
/* 252:    */   private class ValueIterator
/* 253:    */     implements Iterator
/* 254:    */   {
/* 255:    */     private Iterator backedIterator;
/* 256:    */     private Iterator tempIterator;
/* 257:    */     
/* 258:    */     ValueIterator(MultiHashMap.1 x1)
/* 259:    */     {
/* 260:418 */       this();
/* 261:    */     }
/* 262:    */     
/* 263:    */     private ValueIterator()
/* 264:    */     {
/* 265:423 */       this.backedIterator = MultiHashMap.this.superValuesIterator();
/* 266:    */     }
/* 267:    */     
/* 268:    */     private boolean searchNextIterator()
/* 269:    */     {
/* 270:427 */       while ((this.tempIterator == null) || (!this.tempIterator.hasNext()))
/* 271:    */       {
/* 272:428 */         if (!this.backedIterator.hasNext()) {
/* 273:429 */           return false;
/* 274:    */         }
/* 275:431 */         this.tempIterator = ((Collection)this.backedIterator.next()).iterator();
/* 276:    */       }
/* 277:433 */       return true;
/* 278:    */     }
/* 279:    */     
/* 280:    */     public boolean hasNext()
/* 281:    */     {
/* 282:437 */       return searchNextIterator();
/* 283:    */     }
/* 284:    */     
/* 285:    */     public Object next()
/* 286:    */     {
/* 287:441 */       if (!searchNextIterator()) {
/* 288:442 */         throw new NoSuchElementException();
/* 289:    */       }
/* 290:444 */       return this.tempIterator.next();
/* 291:    */     }
/* 292:    */     
/* 293:    */     public void remove()
/* 294:    */     {
/* 295:448 */       if (this.tempIterator == null) {
/* 296:449 */         throw new IllegalStateException();
/* 297:    */       }
/* 298:451 */       this.tempIterator.remove();
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   public Object clone()
/* 303:    */   {
/* 304:465 */     MultiHashMap cloned = (MultiHashMap)super.clone();
/* 305:468 */     for (Iterator it = cloned.entrySet().iterator(); it.hasNext();)
/* 306:    */     {
/* 307:469 */       Map.Entry entry = (Map.Entry)it.next();
/* 308:470 */       Collection coll = (Collection)entry.getValue();
/* 309:471 */       Collection newColl = createCollection(coll);
/* 310:472 */       entry.setValue(newColl);
/* 311:    */     }
/* 312:474 */     return cloned;
/* 313:    */   }
/* 314:    */   
/* 315:    */   protected Collection createCollection(Collection coll)
/* 316:    */   {
/* 317:486 */     if (coll == null) {
/* 318:487 */       return new ArrayList();
/* 319:    */     }
/* 320:489 */     return new ArrayList(coll);
/* 321:    */   }
/* 322:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.MultiHashMap
 * JD-Core Version:    0.7.0.1
 */
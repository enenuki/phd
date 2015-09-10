/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.apache.commons.collections.Factory;
/*  12:    */ import org.apache.commons.collections.FunctorException;
/*  13:    */ import org.apache.commons.collections.MultiMap;
/*  14:    */ import org.apache.commons.collections.iterators.EmptyIterator;
/*  15:    */ import org.apache.commons.collections.iterators.IteratorChain;
/*  16:    */ 
/*  17:    */ public class MultiValueMap
/*  18:    */   extends AbstractMapDecorator
/*  19:    */   implements MultiMap
/*  20:    */ {
/*  21:    */   private final Factory collectionFactory;
/*  22:    */   private transient Collection values;
/*  23:    */   
/*  24:    */   public static MultiValueMap decorate(Map map)
/*  25:    */   {
/*  26: 78 */     return new MultiValueMap(map, new ReflectionFactory(ArrayList.class));
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static MultiValueMap decorate(Map map, Class collectionClass)
/*  30:    */   {
/*  31: 89 */     return new MultiValueMap(map, new ReflectionFactory(collectionClass));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static MultiValueMap decorate(Map map, Factory collectionFactory)
/*  35:    */   {
/*  36:100 */     return new MultiValueMap(map, collectionFactory);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public MultiValueMap()
/*  40:    */   {
/*  41:109 */     this(new HashMap(), new ReflectionFactory(ArrayList.class));
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected MultiValueMap(Map map, Factory collectionFactory)
/*  45:    */   {
/*  46:120 */     super(map);
/*  47:121 */     if (collectionFactory == null) {
/*  48:122 */       throw new IllegalArgumentException("The factory must not be null");
/*  49:    */     }
/*  50:124 */     this.collectionFactory = collectionFactory;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void clear()
/*  54:    */   {
/*  55:140 */     getMap().clear();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Object remove(Object key, Object value)
/*  59:    */   {
/*  60:157 */     Collection valuesForKey = getCollection(key);
/*  61:158 */     if (valuesForKey == null) {
/*  62:159 */       return null;
/*  63:    */     }
/*  64:161 */     boolean removed = valuesForKey.remove(value);
/*  65:162 */     if (!removed) {
/*  66:163 */       return null;
/*  67:    */     }
/*  68:165 */     if (valuesForKey.isEmpty()) {
/*  69:166 */       remove(key);
/*  70:    */     }
/*  71:168 */     return value;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean containsValue(Object value)
/*  75:    */   {
/*  76:180 */     Set pairs = getMap().entrySet();
/*  77:181 */     if (pairs == null) {
/*  78:182 */       return false;
/*  79:    */     }
/*  80:184 */     Iterator pairsIterator = pairs.iterator();
/*  81:185 */     while (pairsIterator.hasNext())
/*  82:    */     {
/*  83:186 */       Map.Entry keyValuePair = (Map.Entry)pairsIterator.next();
/*  84:187 */       Collection coll = (Collection)keyValuePair.getValue();
/*  85:188 */       if (coll.contains(value)) {
/*  86:189 */         return true;
/*  87:    */       }
/*  88:    */     }
/*  89:192 */     return false;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Object put(Object key, Object value)
/*  93:    */   {
/*  94:206 */     boolean result = false;
/*  95:207 */     Collection coll = getCollection(key);
/*  96:208 */     if (coll == null)
/*  97:    */     {
/*  98:209 */       coll = createCollection(1);
/*  99:210 */       result = coll.add(value);
/* 100:211 */       if (coll.size() > 0)
/* 101:    */       {
/* 102:213 */         getMap().put(key, coll);
/* 103:214 */         result = false;
/* 104:    */       }
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:217 */       result = coll.add(value);
/* 109:    */     }
/* 110:219 */     return result ? value : null;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void putAll(Map map)
/* 114:    */   {
/* 115:    */     Iterator it;
/* 116:    */     Iterator it;
/* 117:234 */     if ((map instanceof MultiMap)) {
/* 118:235 */       for (it = map.entrySet().iterator(); it.hasNext();)
/* 119:    */       {
/* 120:236 */         Map.Entry entry = (Map.Entry)it.next();
/* 121:237 */         Collection coll = (Collection)entry.getValue();
/* 122:238 */         putAll(entry.getKey(), coll);
/* 123:    */       }
/* 124:    */     } else {
/* 125:241 */       for (it = map.entrySet().iterator(); it.hasNext();)
/* 126:    */       {
/* 127:242 */         Map.Entry entry = (Map.Entry)it.next();
/* 128:243 */         put(entry.getKey(), entry.getValue());
/* 129:    */       }
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Collection values()
/* 134:    */   {
/* 135:256 */     Collection vs = this.values;
/* 136:257 */     return this.values = new Values(null);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean containsValue(Object key, Object value)
/* 140:    */   {
/* 141:267 */     Collection coll = getCollection(key);
/* 142:268 */     if (coll == null) {
/* 143:269 */       return false;
/* 144:    */     }
/* 145:271 */     return coll.contains(value);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Collection getCollection(Object key)
/* 149:    */   {
/* 150:282 */     return (Collection)getMap().get(key);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public int size(Object key)
/* 154:    */   {
/* 155:292 */     Collection coll = getCollection(key);
/* 156:293 */     if (coll == null) {
/* 157:294 */       return 0;
/* 158:    */     }
/* 159:296 */     return coll.size();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean putAll(Object key, Collection values)
/* 163:    */   {
/* 164:308 */     if ((values == null) || (values.size() == 0)) {
/* 165:309 */       return false;
/* 166:    */     }
/* 167:311 */     Collection coll = getCollection(key);
/* 168:312 */     if (coll == null)
/* 169:    */     {
/* 170:313 */       coll = createCollection(values.size());
/* 171:314 */       boolean result = coll.addAll(values);
/* 172:315 */       if (coll.size() > 0)
/* 173:    */       {
/* 174:317 */         getMap().put(key, coll);
/* 175:318 */         result = false;
/* 176:    */       }
/* 177:320 */       return result;
/* 178:    */     }
/* 179:322 */     return coll.addAll(values);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Iterator iterator(Object key)
/* 183:    */   {
/* 184:333 */     if (!containsKey(key)) {
/* 185:334 */       return EmptyIterator.INSTANCE;
/* 186:    */     }
/* 187:336 */     return new ValuesIterator(key);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int totalSize()
/* 191:    */   {
/* 192:346 */     int total = 0;
/* 193:347 */     Collection values = getMap().values();
/* 194:348 */     for (Iterator it = values.iterator(); it.hasNext();)
/* 195:    */     {
/* 196:349 */       Collection coll = (Collection)it.next();
/* 197:350 */       total += coll.size();
/* 198:    */     }
/* 199:352 */     return total;
/* 200:    */   }
/* 201:    */   
/* 202:    */   protected Collection createCollection(int size)
/* 203:    */   {
/* 204:366 */     return (Collection)this.collectionFactory.create();
/* 205:    */   }
/* 206:    */   
/* 207:    */   private class Values
/* 208:    */     extends AbstractCollection
/* 209:    */   {
/* 210:    */     Values(MultiValueMap.1 x1)
/* 211:    */     {
/* 212:373 */       this();
/* 213:    */     }
/* 214:    */     
/* 215:    */     public Iterator iterator()
/* 216:    */     {
/* 217:375 */       IteratorChain chain = new IteratorChain();
/* 218:376 */       for (Iterator it = MultiValueMap.this.keySet().iterator(); it.hasNext();) {
/* 219:377 */         chain.addIterator(new MultiValueMap.ValuesIterator(MultiValueMap.this, it.next()));
/* 220:    */       }
/* 221:379 */       return chain;
/* 222:    */     }
/* 223:    */     
/* 224:    */     public int size()
/* 225:    */     {
/* 226:383 */       return MultiValueMap.this.totalSize();
/* 227:    */     }
/* 228:    */     
/* 229:    */     public void clear()
/* 230:    */     {
/* 231:387 */       MultiValueMap.this.clear();
/* 232:    */     }
/* 233:    */     
/* 234:    */     private Values() {}
/* 235:    */   }
/* 236:    */   
/* 237:    */   private class ValuesIterator
/* 238:    */     implements Iterator
/* 239:    */   {
/* 240:    */     private final Object key;
/* 241:    */     private final Collection values;
/* 242:    */     private final Iterator iterator;
/* 243:    */     
/* 244:    */     public ValuesIterator(Object key)
/* 245:    */     {
/* 246:400 */       this.key = key;
/* 247:401 */       this.values = MultiValueMap.this.getCollection(key);
/* 248:402 */       this.iterator = this.values.iterator();
/* 249:    */     }
/* 250:    */     
/* 251:    */     public void remove()
/* 252:    */     {
/* 253:406 */       this.iterator.remove();
/* 254:407 */       if (this.values.isEmpty()) {
/* 255:408 */         MultiValueMap.this.remove(this.key);
/* 256:    */       }
/* 257:    */     }
/* 258:    */     
/* 259:    */     public boolean hasNext()
/* 260:    */     {
/* 261:413 */       return this.iterator.hasNext();
/* 262:    */     }
/* 263:    */     
/* 264:    */     public Object next()
/* 265:    */     {
/* 266:417 */       return this.iterator.next();
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   private static class ReflectionFactory
/* 271:    */     implements Factory
/* 272:    */   {
/* 273:    */     private final Class clazz;
/* 274:    */     
/* 275:    */     public ReflectionFactory(Class clazz)
/* 276:    */     {
/* 277:428 */       this.clazz = clazz;
/* 278:    */     }
/* 279:    */     
/* 280:    */     public Object create()
/* 281:    */     {
/* 282:    */       try
/* 283:    */       {
/* 284:433 */         return this.clazz.newInstance();
/* 285:    */       }
/* 286:    */       catch (Exception ex)
/* 287:    */       {
/* 288:435 */         throw new FunctorException("Cannot instantiate class: " + this.clazz, ex);
/* 289:    */       }
/* 290:    */     }
/* 291:    */   }
/* 292:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.MultiValueMap
 * JD-Core Version:    0.7.0.1
 */
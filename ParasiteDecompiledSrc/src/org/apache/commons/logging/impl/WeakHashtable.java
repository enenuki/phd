/*   1:    */ package org.apache.commons.logging.impl;
/*   2:    */ 
/*   3:    */ import java.lang.ref.Reference;
/*   4:    */ import java.lang.ref.ReferenceQueue;
/*   5:    */ import java.lang.ref.WeakReference;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import java.util.Hashtable;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Map.Entry;
/*  13:    */ import java.util.Set;
/*  14:    */ 
/*  15:    */ public final class WeakHashtable
/*  16:    */   extends Hashtable
/*  17:    */ {
/*  18:    */   private static final int MAX_CHANGES_BEFORE_PURGE = 100;
/*  19:    */   private static final int PARTIAL_PURGE_COUNT = 10;
/*  20:126 */   private ReferenceQueue queue = new ReferenceQueue();
/*  21:128 */   private int changeCount = 0;
/*  22:    */   
/*  23:    */   public boolean containsKey(Object key)
/*  24:    */   {
/*  25:142 */     Referenced referenced = new Referenced(key, null);
/*  26:143 */     return super.containsKey(referenced);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Enumeration elements()
/*  30:    */   {
/*  31:150 */     purge();
/*  32:151 */     return super.elements();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Set entrySet()
/*  36:    */   {
/*  37:158 */     purge();
/*  38:159 */     Set referencedEntries = super.entrySet();
/*  39:160 */     Set unreferencedEntries = new HashSet();
/*  40:161 */     for (Iterator it = referencedEntries.iterator(); it.hasNext();)
/*  41:    */     {
/*  42:162 */       Map.Entry entry = (Map.Entry)it.next();
/*  43:163 */       Referenced referencedKey = (Referenced)entry.getKey();
/*  44:164 */       Object key = referencedKey.getValue();
/*  45:165 */       Object value = entry.getValue();
/*  46:166 */       if (key != null)
/*  47:    */       {
/*  48:167 */         Entry dereferencedEntry = new Entry(key, value, null);
/*  49:168 */         unreferencedEntries.add(dereferencedEntry);
/*  50:    */       }
/*  51:    */     }
/*  52:171 */     return unreferencedEntries;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object get(Object key)
/*  56:    */   {
/*  57:179 */     Referenced referenceKey = new Referenced(key, null);
/*  58:180 */     return super.get(referenceKey);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Enumeration keys()
/*  62:    */   {
/*  63:187 */     purge();
/*  64:188 */     Enumeration enumer = super.keys();
/*  65:189 */     new Enumeration()
/*  66:    */     {
/*  67:    */       private final Enumeration val$enumer;
/*  68:    */       
/*  69:    */       public boolean hasMoreElements()
/*  70:    */       {
/*  71:191 */         return this.val$enumer.hasMoreElements();
/*  72:    */       }
/*  73:    */       
/*  74:    */       public Object nextElement()
/*  75:    */       {
/*  76:194 */         WeakHashtable.Referenced nextReference = (WeakHashtable.Referenced)this.val$enumer.nextElement();
/*  77:195 */         return nextReference.getValue();
/*  78:    */       }
/*  79:    */     };
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Set keySet()
/*  83:    */   {
/*  84:205 */     purge();
/*  85:206 */     Set referencedKeys = super.keySet();
/*  86:207 */     Set unreferencedKeys = new HashSet();
/*  87:208 */     for (Iterator it = referencedKeys.iterator(); it.hasNext();)
/*  88:    */     {
/*  89:209 */       Referenced referenceKey = (Referenced)it.next();
/*  90:210 */       Object keyValue = referenceKey.getValue();
/*  91:211 */       if (keyValue != null) {
/*  92:212 */         unreferencedKeys.add(keyValue);
/*  93:    */       }
/*  94:    */     }
/*  95:215 */     return unreferencedKeys;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Object put(Object key, Object value)
/*  99:    */   {
/* 100:223 */     if (key == null) {
/* 101:224 */       throw new NullPointerException("Null keys are not allowed");
/* 102:    */     }
/* 103:226 */     if (value == null) {
/* 104:227 */       throw new NullPointerException("Null values are not allowed");
/* 105:    */     }
/* 106:232 */     if (this.changeCount++ > 100)
/* 107:    */     {
/* 108:233 */       purge();
/* 109:234 */       this.changeCount = 0;
/* 110:    */     }
/* 111:237 */     else if (this.changeCount % 10 == 0)
/* 112:    */     {
/* 113:238 */       purgeOne();
/* 114:    */     }
/* 115:241 */     Referenced keyRef = new Referenced(key, this.queue, null);
/* 116:242 */     return super.put(keyRef, value);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void putAll(Map t)
/* 120:    */   {
/* 121:    */     Iterator it;
/* 122:249 */     if (t != null)
/* 123:    */     {
/* 124:250 */       Set entrySet = t.entrySet();
/* 125:251 */       for (it = entrySet.iterator(); it.hasNext();)
/* 126:    */       {
/* 127:252 */         Map.Entry entry = (Map.Entry)it.next();
/* 128:253 */         put(entry.getKey(), entry.getValue());
/* 129:    */       }
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Collection values()
/* 134:    */   {
/* 135:262 */     purge();
/* 136:263 */     return super.values();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Object remove(Object key)
/* 140:    */   {
/* 141:272 */     if (this.changeCount++ > 100)
/* 142:    */     {
/* 143:273 */       purge();
/* 144:274 */       this.changeCount = 0;
/* 145:    */     }
/* 146:277 */     else if (this.changeCount % 10 == 0)
/* 147:    */     {
/* 148:278 */       purgeOne();
/* 149:    */     }
/* 150:280 */     return super.remove(new Referenced(key, null));
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean isEmpty()
/* 154:    */   {
/* 155:287 */     purge();
/* 156:288 */     return super.isEmpty();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int size()
/* 160:    */   {
/* 161:295 */     purge();
/* 162:296 */     return super.size();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String toString()
/* 166:    */   {
/* 167:303 */     purge();
/* 168:304 */     return super.toString();
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected void rehash()
/* 172:    */   {
/* 173:312 */     purge();
/* 174:313 */     super.rehash();
/* 175:    */   }
/* 176:    */   
/* 177:    */   private void purge()
/* 178:    */   {
/* 179:321 */     synchronized (this.queue)
/* 180:    */     {
/* 181:    */       WeakKey key;
/* 182:323 */       while ((key = (WeakKey)this.queue.poll()) != null) {
/* 183:324 */         super.remove(key.getReferenced());
/* 184:    */       }
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   private void purgeOne()
/* 189:    */   {
/* 190:335 */     synchronized (this.queue)
/* 191:    */     {
/* 192:336 */       WeakKey key = (WeakKey)this.queue.poll();
/* 193:337 */       if (key != null) {
/* 194:338 */         super.remove(key.getReferenced());
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   private static final class Entry
/* 200:    */     implements Map.Entry
/* 201:    */   {
/* 202:    */     private final Object key;
/* 203:    */     private final Object value;
/* 204:    */     
/* 205:    */     Entry(Object x0, Object x1, WeakHashtable.1 x2)
/* 206:    */     {
/* 207:344 */       this(x0, x1);
/* 208:    */     }
/* 209:    */     
/* 210:    */     private Entry(Object key, Object value)
/* 211:    */     {
/* 212:350 */       this.key = key;
/* 213:351 */       this.value = value;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public boolean equals(Object o)
/* 217:    */     {
/* 218:355 */       boolean result = false;
/* 219:356 */       if ((o != null) && ((o instanceof Map.Entry)))
/* 220:    */       {
/* 221:357 */         Map.Entry entry = (Map.Entry)o;
/* 222:358 */         result = (getKey() == null ? entry.getKey() == null : getKey().equals(entry.getKey())) && (getValue() == null ? entry.getValue() == null : getValue().equals(entry.getValue()));
/* 223:    */       }
/* 224:366 */       return result;
/* 225:    */     }
/* 226:    */     
/* 227:    */     public int hashCode()
/* 228:    */     {
/* 229:371 */       return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/* 230:    */     }
/* 231:    */     
/* 232:    */     public Object setValue(Object value)
/* 233:    */     {
/* 234:376 */       throw new UnsupportedOperationException("Entry.setValue is not supported.");
/* 235:    */     }
/* 236:    */     
/* 237:    */     public Object getValue()
/* 238:    */     {
/* 239:380 */       return this.value;
/* 240:    */     }
/* 241:    */     
/* 242:    */     public Object getKey()
/* 243:    */     {
/* 244:384 */       return this.key;
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   private static final class Referenced
/* 249:    */   {
/* 250:    */     private final WeakReference reference;
/* 251:    */     private final int hashCode;
/* 252:    */     
/* 253:    */     Referenced(Object x0, WeakHashtable.1 x1)
/* 254:    */     {
/* 255:390 */       this(x0);
/* 256:    */     }
/* 257:    */     
/* 258:    */     Referenced(Object x0, ReferenceQueue x1, WeakHashtable.1 x2)
/* 259:    */     {
/* 260:390 */       this(x0, x1);
/* 261:    */     }
/* 262:    */     
/* 263:    */     private Referenced(Object referant)
/* 264:    */     {
/* 265:400 */       this.reference = new WeakReference(referant);
/* 266:    */       
/* 267:    */ 
/* 268:403 */       this.hashCode = referant.hashCode();
/* 269:    */     }
/* 270:    */     
/* 271:    */     private Referenced(Object key, ReferenceQueue queue)
/* 272:    */     {
/* 273:411 */       this.reference = new WeakHashtable.WeakKey(key, queue, this, null);
/* 274:    */       
/* 275:    */ 
/* 276:414 */       this.hashCode = key.hashCode();
/* 277:    */     }
/* 278:    */     
/* 279:    */     public int hashCode()
/* 280:    */     {
/* 281:419 */       return this.hashCode;
/* 282:    */     }
/* 283:    */     
/* 284:    */     private Object getValue()
/* 285:    */     {
/* 286:423 */       return this.reference.get();
/* 287:    */     }
/* 288:    */     
/* 289:    */     public boolean equals(Object o)
/* 290:    */     {
/* 291:427 */       boolean result = false;
/* 292:428 */       if ((o instanceof Referenced))
/* 293:    */       {
/* 294:429 */         Referenced otherKey = (Referenced)o;
/* 295:430 */         Object thisKeyValue = getValue();
/* 296:431 */         Object otherKeyValue = otherKey.getValue();
/* 297:432 */         if (thisKeyValue == null)
/* 298:    */         {
/* 299:433 */           result = otherKeyValue == null;
/* 300:441 */           if (result == true) {
/* 301:442 */             result = hashCode() == otherKey.hashCode();
/* 302:    */           }
/* 303:    */         }
/* 304:    */         else
/* 305:    */         {
/* 306:451 */           result = thisKeyValue.equals(otherKeyValue);
/* 307:    */         }
/* 308:    */       }
/* 309:454 */       return result;
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   private static final class WeakKey
/* 314:    */     extends WeakReference
/* 315:    */   {
/* 316:    */     private final WeakHashtable.Referenced referenced;
/* 317:    */     
/* 318:    */     WeakKey(Object x0, ReferenceQueue x1, WeakHashtable.Referenced x2, WeakHashtable.1 x3)
/* 319:    */     {
/* 320:463 */       this(x0, x1, x2);
/* 321:    */     }
/* 322:    */     
/* 323:    */     private WeakKey(Object key, ReferenceQueue queue, WeakHashtable.Referenced referenced)
/* 324:    */     {
/* 325:470 */       super(queue);
/* 326:471 */       this.referenced = referenced;
/* 327:    */     }
/* 328:    */     
/* 329:    */     private WeakHashtable.Referenced getReferenced()
/* 330:    */     {
/* 331:475 */       return this.referenced;
/* 332:    */     }
/* 333:    */   }
/* 334:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.impl.WeakHashtable
 * JD-Core Version:    0.7.0.1
 */
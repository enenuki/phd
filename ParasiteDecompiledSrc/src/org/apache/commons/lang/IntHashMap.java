/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ class IntHashMap
/*   4:    */ {
/*   5:    */   private transient Entry[] table;
/*   6:    */   private transient int count;
/*   7:    */   private int threshold;
/*   8:    */   private final float loadFactor;
/*   9:    */   
/*  10:    */   private static class Entry
/*  11:    */   {
/*  12:    */     final int hash;
/*  13:    */     final int key;
/*  14:    */     Object value;
/*  15:    */     Entry next;
/*  16:    */     
/*  17:    */     protected Entry(int hash, int key, Object value, Entry next)
/*  18:    */     {
/*  19: 84 */       this.hash = hash;
/*  20: 85 */       this.key = key;
/*  21: 86 */       this.value = value;
/*  22: 87 */       this.next = next;
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public IntHashMap()
/*  27:    */   {
/*  28: 96 */     this(20, 0.75F);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public IntHashMap(int initialCapacity)
/*  32:    */   {
/*  33:108 */     this(initialCapacity, 0.75F);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public IntHashMap(int initialCapacity, float loadFactor)
/*  37:    */   {
/*  38:122 */     if (initialCapacity < 0) {
/*  39:123 */       throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
/*  40:    */     }
/*  41:125 */     if (loadFactor <= 0.0F) {
/*  42:126 */       throw new IllegalArgumentException("Illegal Load: " + loadFactor);
/*  43:    */     }
/*  44:128 */     if (initialCapacity == 0) {
/*  45:129 */       initialCapacity = 1;
/*  46:    */     }
/*  47:132 */     this.loadFactor = loadFactor;
/*  48:133 */     this.table = new Entry[initialCapacity];
/*  49:134 */     this.threshold = ((int)(initialCapacity * loadFactor));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int size()
/*  53:    */   {
/*  54:143 */     return this.count;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isEmpty()
/*  58:    */   {
/*  59:153 */     return this.count == 0;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean contains(Object value)
/*  63:    */   {
/*  64:175 */     if (value == null) {
/*  65:176 */       throw new NullPointerException();
/*  66:    */     }
/*  67:179 */     Entry[] tab = this.table;
/*  68:180 */     for (int i = tab.length; i-- > 0;) {
/*  69:181 */       for (Entry e = tab[i]; e != null; e = e.next) {
/*  70:182 */         if (e.value.equals(value)) {
/*  71:183 */           return true;
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75:187 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean containsValue(Object value)
/*  79:    */   {
/*  80:203 */     return contains(value);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean containsKey(int key)
/*  84:    */   {
/*  85:216 */     Entry[] tab = this.table;
/*  86:217 */     int hash = key;
/*  87:218 */     int index = (hash & 0x7FFFFFFF) % tab.length;
/*  88:219 */     for (Entry e = tab[index]; e != null; e = e.next) {
/*  89:220 */       if (e.hash == hash) {
/*  90:221 */         return true;
/*  91:    */       }
/*  92:    */     }
/*  93:224 */     return false;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Object get(int key)
/*  97:    */   {
/*  98:237 */     Entry[] tab = this.table;
/*  99:238 */     int hash = key;
/* 100:239 */     int index = (hash & 0x7FFFFFFF) % tab.length;
/* 101:240 */     for (Entry e = tab[index]; e != null; e = e.next) {
/* 102:241 */       if (e.hash == hash) {
/* 103:242 */         return e.value;
/* 104:    */       }
/* 105:    */     }
/* 106:245 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected void rehash()
/* 110:    */   {
/* 111:258 */     int oldCapacity = this.table.length;
/* 112:259 */     Entry[] oldMap = this.table;
/* 113:    */     
/* 114:261 */     int newCapacity = oldCapacity * 2 + 1;
/* 115:262 */     Entry[] newMap = new Entry[newCapacity];
/* 116:    */     
/* 117:264 */     this.threshold = ((int)(newCapacity * this.loadFactor));
/* 118:265 */     this.table = newMap;
/* 119:267 */     for (int i = oldCapacity; i-- > 0;) {
/* 120:268 */       for (old = oldMap[i]; old != null;)
/* 121:    */       {
/* 122:269 */         Entry e = old;
/* 123:270 */         old = old.next;
/* 124:    */         
/* 125:272 */         int index = (e.hash & 0x7FFFFFFF) % newCapacity;
/* 126:273 */         e.next = newMap[index];
/* 127:274 */         newMap[index] = e;
/* 128:    */       }
/* 129:    */     }
/* 130:    */     Entry old;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Object put(int key, Object value)
/* 134:    */   {
/* 135:296 */     Entry[] tab = this.table;
/* 136:297 */     int hash = key;
/* 137:298 */     int index = (hash & 0x7FFFFFFF) % tab.length;
/* 138:299 */     for (Entry e = tab[index]; e != null; e = e.next) {
/* 139:300 */       if (e.hash == hash)
/* 140:    */       {
/* 141:301 */         Object old = e.value;
/* 142:302 */         e.value = value;
/* 143:303 */         return old;
/* 144:    */       }
/* 145:    */     }
/* 146:307 */     if (this.count >= this.threshold)
/* 147:    */     {
/* 148:309 */       rehash();
/* 149:    */       
/* 150:311 */       tab = this.table;
/* 151:312 */       index = (hash & 0x7FFFFFFF) % tab.length;
/* 152:    */     }
/* 153:316 */     Entry e = new Entry(hash, key, value, tab[index]);
/* 154:317 */     tab[index] = e;
/* 155:318 */     this.count += 1;
/* 156:319 */     return null;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Object remove(int key)
/* 160:    */   {
/* 161:334 */     Entry[] tab = this.table;
/* 162:335 */     int hash = key;
/* 163:336 */     int index = (hash & 0x7FFFFFFF) % tab.length;
/* 164:337 */     Entry e = tab[index];
/* 165:337 */     for (Entry prev = null; e != null; e = e.next)
/* 166:    */     {
/* 167:338 */       if (e.hash == hash)
/* 168:    */       {
/* 169:339 */         if (prev != null) {
/* 170:340 */           prev.next = e.next;
/* 171:    */         } else {
/* 172:342 */           tab[index] = e.next;
/* 173:    */         }
/* 174:344 */         this.count -= 1;
/* 175:345 */         Object oldValue = e.value;
/* 176:346 */         e.value = null;
/* 177:347 */         return oldValue;
/* 178:    */       }
/* 179:337 */       prev = e;
/* 180:    */     }
/* 181:350 */     return null;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public synchronized void clear()
/* 185:    */   {
/* 186:357 */     Entry[] tab = this.table;
/* 187:358 */     int index = tab.length;
/* 188:    */     for (;;)
/* 189:    */     {
/* 190:358 */       index--;
/* 191:358 */       if (index < 0) {
/* 192:    */         break;
/* 193:    */       }
/* 194:359 */       tab[index] = null;
/* 195:    */     }
/* 196:361 */     this.count = 0;
/* 197:    */   }
/* 198:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.IntHashMap
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.apache.xalan.xsltc.runtime;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ 
/*   5:    */ public class Hashtable
/*   6:    */ {
/*   7:    */   private transient HashtableEntry[] table;
/*   8:    */   private transient int count;
/*   9:    */   private int threshold;
/*  10:    */   private float loadFactor;
/*  11:    */   
/*  12:    */   public Hashtable(int initialCapacity, float loadFactor)
/*  13:    */   {
/*  14: 68 */     if (initialCapacity <= 0) {
/*  15: 68 */       initialCapacity = 11;
/*  16:    */     }
/*  17: 69 */     if (loadFactor <= 0.0D) {
/*  18: 69 */       loadFactor = 0.75F;
/*  19:    */     }
/*  20: 70 */     this.loadFactor = loadFactor;
/*  21: 71 */     this.table = new HashtableEntry[initialCapacity];
/*  22: 72 */     this.threshold = ((int)(initialCapacity * loadFactor));
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Hashtable(int initialCapacity)
/*  26:    */   {
/*  27: 80 */     this(initialCapacity, 0.75F);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Hashtable()
/*  31:    */   {
/*  32: 88 */     this(101, 0.75F);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int size()
/*  36:    */   {
/*  37: 95 */     return this.count;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isEmpty()
/*  41:    */   {
/*  42:102 */     return this.count == 0;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Enumeration keys()
/*  46:    */   {
/*  47:109 */     return new HashtableEnumerator(this.table, true);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Enumeration elements()
/*  51:    */   {
/*  52:118 */     return new HashtableEnumerator(this.table, false);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean contains(Object value)
/*  56:    */   {
/*  57:128 */     if (value == null) {
/*  58:128 */       throw new NullPointerException();
/*  59:    */     }
/*  60:132 */     HashtableEntry[] tab = this.table;
/*  61:    */     HashtableEntry e;
/*  62:134 */     for (int i = tab.length; i-- > 0; e != null)
/*  63:    */     {
/*  64:135 */       e = tab[i]; continue;
/*  65:136 */       if (e.value.equals(value)) {
/*  66:137 */         return true;
/*  67:    */       }
/*  68:135 */       e = e.next;
/*  69:    */     }
/*  70:141 */     return false;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean containsKey(Object key)
/*  74:    */   {
/*  75:149 */     HashtableEntry[] tab = this.table;
/*  76:150 */     int hash = key.hashCode();
/*  77:151 */     int index = (hash & 0x7FFFFFFF) % tab.length;
/*  78:153 */     for (HashtableEntry e = tab[index]; e != null; e = e.next) {
/*  79:154 */       if ((e.hash == hash) && (e.key.equals(key))) {
/*  80:155 */         return true;
/*  81:    */       }
/*  82:    */     }
/*  83:157 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Object get(Object key)
/*  87:    */   {
/*  88:165 */     HashtableEntry[] tab = this.table;
/*  89:166 */     int hash = key.hashCode();
/*  90:167 */     int index = (hash & 0x7FFFFFFF) % tab.length;
/*  91:169 */     for (HashtableEntry e = tab[index]; e != null; e = e.next) {
/*  92:170 */       if ((e.hash == hash) && (e.key.equals(key))) {
/*  93:171 */         return e.value;
/*  94:    */       }
/*  95:    */     }
/*  96:173 */     return null;
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected void rehash()
/* 100:    */   {
/* 101:185 */     int oldCapacity = this.table.length;
/* 102:186 */     HashtableEntry[] oldTable = this.table;
/* 103:    */     
/* 104:188 */     int newCapacity = oldCapacity * 2 + 1;
/* 105:189 */     HashtableEntry[] newTable = new HashtableEntry[newCapacity];
/* 106:    */     
/* 107:191 */     this.threshold = ((int)(newCapacity * this.loadFactor));
/* 108:192 */     this.table = newTable;
/* 109:    */     HashtableEntry old;
/* 110:194 */     for (int i = oldCapacity; i-- > 0; old != null)
/* 111:    */     {
/* 112:195 */       old = oldTable[i]; continue;
/* 113:196 */       HashtableEntry e = old;
/* 114:197 */       old = old.next;
/* 115:198 */       int index = (e.hash & 0x7FFFFFFF) % newCapacity;
/* 116:199 */       e.next = newTable[index];
/* 117:200 */       newTable[index] = e;
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Object put(Object key, Object value)
/* 122:    */   {
/* 123:215 */     if (value == null) {
/* 124:215 */       throw new NullPointerException();
/* 125:    */     }
/* 126:219 */     HashtableEntry[] tab = this.table;
/* 127:220 */     int hash = key.hashCode();
/* 128:221 */     int index = (hash & 0x7FFFFFFF) % tab.length;
/* 129:223 */     for (HashtableEntry e = tab[index]; e != null; e = e.next) {
/* 130:224 */       if ((e.hash == hash) && (e.key.equals(key)))
/* 131:    */       {
/* 132:225 */         Object old = e.value;
/* 133:226 */         e.value = value;
/* 134:227 */         return old;
/* 135:    */       }
/* 136:    */     }
/* 137:232 */     if (this.count >= this.threshold)
/* 138:    */     {
/* 139:233 */       rehash();
/* 140:234 */       return put(key, value);
/* 141:    */     }
/* 142:238 */     e = new HashtableEntry();
/* 143:239 */     e.hash = hash;
/* 144:240 */     e.key = key;
/* 145:241 */     e.value = value;
/* 146:242 */     e.next = tab[index];
/* 147:243 */     tab[index] = e;
/* 148:244 */     this.count += 1;
/* 149:245 */     return null;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Object remove(Object key)
/* 153:    */   {
/* 154:254 */     HashtableEntry[] tab = this.table;
/* 155:255 */     int hash = key.hashCode();
/* 156:256 */     int index = (hash & 0x7FFFFFFF) % tab.length;
/* 157:257 */     HashtableEntry e = tab[index];
/* 158:257 */     for (HashtableEntry prev = null; e != null; e = e.next)
/* 159:    */     {
/* 160:258 */       if ((e.hash == hash) && (e.key.equals(key)))
/* 161:    */       {
/* 162:259 */         if (prev != null) {
/* 163:260 */           prev.next = e.next;
/* 164:    */         } else {
/* 165:262 */           tab[index] = e.next;
/* 166:    */         }
/* 167:263 */         this.count -= 1;
/* 168:264 */         return e.value;
/* 169:    */       }
/* 170:257 */       prev = e;
/* 171:    */     }
/* 172:267 */     return null;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void clear()
/* 176:    */   {
/* 177:274 */     HashtableEntry[] tab = this.table;
/* 178:275 */     int index = tab.length;
/* 179:    */     do
/* 180:    */     {
/* 181:276 */       tab[index] = null;index--;
/* 182:275 */     } while (index >= 0);
/* 183:277 */     this.count = 0;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String toString()
/* 187:    */   {
/* 188:286 */     int max = size() - 1;
/* 189:287 */     StringBuffer buf = new StringBuffer();
/* 190:288 */     Enumeration k = keys();
/* 191:289 */     Enumeration e = elements();
/* 192:290 */     buf.append("{");
/* 193:292 */     for (int i = 0; i <= max; i++)
/* 194:    */     {
/* 195:293 */       String s1 = k.nextElement().toString();
/* 196:294 */       String s2 = e.nextElement().toString();
/* 197:295 */       buf.append(s1 + "=" + s2);
/* 198:296 */       if (i < max) {
/* 199:296 */         buf.append(", ");
/* 200:    */       }
/* 201:    */     }
/* 202:298 */     buf.append("}");
/* 203:299 */     return buf.toString();
/* 204:    */   }
/* 205:    */   
/* 206:    */   class HashtableEnumerator
/* 207:    */     implements Enumeration
/* 208:    */   {
/* 209:    */     boolean keys;
/* 210:    */     int index;
/* 211:    */     HashtableEntry[] table;
/* 212:    */     HashtableEntry entry;
/* 213:    */     
/* 214:    */     HashtableEnumerator(HashtableEntry[] table, boolean keys)
/* 215:    */     {
/* 216:313 */       this.table = table;
/* 217:314 */       this.keys = keys;
/* 218:315 */       this.index = table.length;
/* 219:    */     }
/* 220:    */     
/* 221:    */     public boolean hasMoreElements()
/* 222:    */     {
/* 223:319 */       if (this.entry != null) {
/* 224:320 */         return true;
/* 225:    */       }
/* 226:322 */       while (this.index-- > 0) {
/* 227:323 */         if ((this.entry = this.table[this.index]) != null) {
/* 228:324 */           return true;
/* 229:    */         }
/* 230:    */       }
/* 231:327 */       return false;
/* 232:    */     }
/* 233:    */     
/* 234:    */     public Object nextElement()
/* 235:    */     {
/* 236:331 */       while ((this.entry == null) && (
/* 237:332 */         (goto 10) || ((this.index-- > 0) && ((this.entry = this.table[this.index]) == null)))) {}
/* 238:334 */       if (this.entry != null)
/* 239:    */       {
/* 240:335 */         HashtableEntry e = this.entry;
/* 241:336 */         this.entry = e.next;
/* 242:337 */         return this.keys ? e.key : e.value;
/* 243:    */       }
/* 244:339 */       return null;
/* 245:    */     }
/* 246:    */   }
/* 247:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.Hashtable
 * JD-Core Version:    0.7.0.1
 */
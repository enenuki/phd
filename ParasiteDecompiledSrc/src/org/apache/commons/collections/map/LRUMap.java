/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.apache.commons.collections.BoundedMap;
/*   9:    */ 
/*  10:    */ public class LRUMap
/*  11:    */   extends AbstractLinkedMap
/*  12:    */   implements BoundedMap, Serializable, Cloneable
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = -612114643488955218L;
/*  15:    */   protected static final int DEFAULT_MAX_SIZE = 100;
/*  16:    */   private transient int maxSize;
/*  17:    */   private boolean scanUntilRemovable;
/*  18:    */   
/*  19:    */   public LRUMap()
/*  20:    */   {
/*  21: 76 */     this(100, 0.75F, false);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public LRUMap(int maxSize)
/*  25:    */   {
/*  26: 86 */     this(maxSize, 0.75F);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public LRUMap(int maxSize, boolean scanUntilRemovable)
/*  30:    */   {
/*  31: 98 */     this(maxSize, 0.75F, scanUntilRemovable);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public LRUMap(int maxSize, float loadFactor)
/*  35:    */   {
/*  36:111 */     this(maxSize, loadFactor, false);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public LRUMap(int maxSize, float loadFactor, boolean scanUntilRemovable)
/*  40:    */   {
/*  41:126 */     super(maxSize < 1 ? 16 : maxSize, loadFactor);
/*  42:127 */     if (maxSize < 1) {
/*  43:128 */       throw new IllegalArgumentException("LRUMap max size must be greater than 0");
/*  44:    */     }
/*  45:130 */     this.maxSize = maxSize;
/*  46:131 */     this.scanUntilRemovable = scanUntilRemovable;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public LRUMap(Map map)
/*  50:    */   {
/*  51:144 */     this(map, false);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public LRUMap(Map map, boolean scanUntilRemovable)
/*  55:    */   {
/*  56:159 */     this(map.size(), 0.75F, scanUntilRemovable);
/*  57:160 */     putAll(map);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Object get(Object key)
/*  61:    */   {
/*  62:174 */     AbstractLinkedMap.LinkEntry entry = (AbstractLinkedMap.LinkEntry)getEntry(key);
/*  63:175 */     if (entry == null) {
/*  64:176 */       return null;
/*  65:    */     }
/*  66:178 */     moveToMRU(entry);
/*  67:179 */     return entry.getValue();
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected void moveToMRU(AbstractLinkedMap.LinkEntry entry)
/*  71:    */   {
/*  72:191 */     if (entry.after != this.header)
/*  73:    */     {
/*  74:192 */       this.modCount += 1;
/*  75:    */       
/*  76:194 */       entry.before.after = entry.after;
/*  77:195 */       entry.after.before = entry.before;
/*  78:    */       
/*  79:197 */       entry.after = this.header;
/*  80:198 */       entry.before = this.header.before;
/*  81:199 */       this.header.before.after = entry;
/*  82:200 */       this.header.before = entry;
/*  83:    */     }
/*  84:201 */     else if (entry == this.header)
/*  85:    */     {
/*  86:202 */       throw new IllegalStateException("Can't move header to MRU (please report this to commons-dev@jakarta.apache.org)");
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected void updateEntry(AbstractHashedMap.HashEntry entry, Object newValue)
/*  91:    */   {
/*  92:217 */     moveToMRU((AbstractLinkedMap.LinkEntry)entry);
/*  93:218 */     entry.setValue(newValue);
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void addMapping(int hashIndex, int hashCode, Object key, Object value)
/*  97:    */   {
/*  98:237 */     if (isFull())
/*  99:    */     {
/* 100:238 */       AbstractLinkedMap.LinkEntry reuse = this.header.after;
/* 101:239 */       boolean removeLRUEntry = false;
/* 102:240 */       if (this.scanUntilRemovable)
/* 103:    */       {
/* 104:241 */         while ((reuse != this.header) && (reuse != null))
/* 105:    */         {
/* 106:242 */           if (removeLRU(reuse))
/* 107:    */           {
/* 108:243 */             removeLRUEntry = true;
/* 109:244 */             break;
/* 110:    */           }
/* 111:246 */           reuse = reuse.after;
/* 112:    */         }
/* 113:248 */         if (reuse == null) {
/* 114:249 */           throw new IllegalStateException("Entry.after=null, header.after" + this.header.after + " header.before" + this.header.before + " key=" + key + " value=" + value + " size=" + this.size + " maxSize=" + this.maxSize + " Please check that your keys are immutable, and that you have used synchronization properly." + " If so, then please report this to commons-dev@jakarta.apache.org as a bug.");
/* 115:    */         }
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:256 */         removeLRUEntry = removeLRU(reuse);
/* 120:    */       }
/* 121:259 */       if (removeLRUEntry)
/* 122:    */       {
/* 123:260 */         if (reuse == null) {
/* 124:261 */           throw new IllegalStateException("reuse=null, header.after=" + this.header.after + " header.before" + this.header.before + " key=" + key + " value=" + value + " size=" + this.size + " maxSize=" + this.maxSize + " Please check that your keys are immutable, and that you have used synchronization properly." + " If so, then please report this to commons-dev@jakarta.apache.org as a bug.");
/* 125:    */         }
/* 126:267 */         reuseMapping(reuse, hashIndex, hashCode, key, value);
/* 127:    */       }
/* 128:    */       else
/* 129:    */       {
/* 130:269 */         super.addMapping(hashIndex, hashCode, key, value);
/* 131:    */       }
/* 132:    */     }
/* 133:    */     else
/* 134:    */     {
/* 135:272 */       super.addMapping(hashIndex, hashCode, key, value);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected void reuseMapping(AbstractLinkedMap.LinkEntry entry, int hashIndex, int hashCode, Object key, Object value)
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:292 */       int removeIndex = hashIndex(entry.hashCode, this.data.length);
/* 144:293 */       AbstractHashedMap.HashEntry[] tmp = this.data;
/* 145:294 */       AbstractHashedMap.HashEntry loop = tmp[removeIndex];
/* 146:295 */       AbstractHashedMap.HashEntry previous = null;
/* 147:296 */       while ((loop != entry) && (loop != null))
/* 148:    */       {
/* 149:297 */         previous = loop;
/* 150:298 */         loop = loop.next;
/* 151:    */       }
/* 152:300 */       if (loop == null) {
/* 153:301 */         throw new IllegalStateException("Entry.next=null, data[removeIndex]=" + this.data[removeIndex] + " previous=" + previous + " key=" + key + " value=" + value + " size=" + this.size + " maxSize=" + this.maxSize + " Please check that your keys are immutable, and that you have used synchronization properly." + " If so, then please report this to commons-dev@jakarta.apache.org as a bug.");
/* 154:    */       }
/* 155:309 */       this.modCount += 1;
/* 156:310 */       removeEntry(entry, removeIndex, previous);
/* 157:311 */       reuseEntry(entry, hashIndex, hashCode, key, value);
/* 158:312 */       addEntry(entry, hashIndex);
/* 159:    */     }
/* 160:    */     catch (NullPointerException ex)
/* 161:    */     {
/* 162:314 */       throw new IllegalStateException("NPE, entry=" + entry + " entryIsHeader=" + (entry == this.header) + " key=" + key + " value=" + value + " size=" + this.size + " maxSize=" + this.maxSize + " Please check that your keys are immutable, and that you have used synchronization properly." + " If so, then please report this to commons-dev@jakarta.apache.org as a bug.");
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected boolean removeLRU(AbstractLinkedMap.LinkEntry entry)
/* 167:    */   {
/* 168:356 */     return true;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isFull()
/* 172:    */   {
/* 173:366 */     return this.size >= this.maxSize;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int maxSize()
/* 177:    */   {
/* 178:375 */     return this.maxSize;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean isScanUntilRemovable()
/* 182:    */   {
/* 183:386 */     return this.scanUntilRemovable;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Object clone()
/* 187:    */   {
/* 188:396 */     return super.clone();
/* 189:    */   }
/* 190:    */   
/* 191:    */   private void writeObject(ObjectOutputStream out)
/* 192:    */     throws IOException
/* 193:    */   {
/* 194:403 */     out.defaultWriteObject();
/* 195:404 */     doWriteObject(out);
/* 196:    */   }
/* 197:    */   
/* 198:    */   private void readObject(ObjectInputStream in)
/* 199:    */     throws IOException, ClassNotFoundException
/* 200:    */   {
/* 201:411 */     in.defaultReadObject();
/* 202:412 */     doReadObject(in);
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected void doWriteObject(ObjectOutputStream out)
/* 206:    */     throws IOException
/* 207:    */   {
/* 208:419 */     out.writeInt(this.maxSize);
/* 209:420 */     super.doWriteObject(out);
/* 210:    */   }
/* 211:    */   
/* 212:    */   protected void doReadObject(ObjectInputStream in)
/* 213:    */     throws IOException, ClassNotFoundException
/* 214:    */   {
/* 215:427 */     this.maxSize = in.readInt();
/* 216:428 */     super.doReadObject(in);
/* 217:    */   }
/* 218:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.LRUMap
 * JD-Core Version:    0.7.0.1
 */
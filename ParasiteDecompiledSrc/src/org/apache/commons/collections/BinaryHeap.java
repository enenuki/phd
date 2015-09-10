/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.NoSuchElementException;
/*   7:    */ 
/*   8:    */ /**
/*   9:    */  * @deprecated
/*  10:    */  */
/*  11:    */ public final class BinaryHeap
/*  12:    */   extends AbstractCollection
/*  13:    */   implements PriorityQueue, Buffer
/*  14:    */ {
/*  15:    */   private static final int DEFAULT_CAPACITY = 13;
/*  16:    */   int m_size;
/*  17:    */   Object[] m_elements;
/*  18:    */   boolean m_isMinHeap;
/*  19:    */   Comparator m_comparator;
/*  20:    */   
/*  21:    */   public BinaryHeap()
/*  22:    */   {
/*  23: 93 */     this(13, true);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public BinaryHeap(Comparator comparator)
/*  27:    */   {
/*  28:104 */     this();
/*  29:105 */     this.m_comparator = comparator;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public BinaryHeap(int capacity)
/*  33:    */   {
/*  34:117 */     this(capacity, true);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public BinaryHeap(int capacity, Comparator comparator)
/*  38:    */   {
/*  39:130 */     this(capacity);
/*  40:131 */     this.m_comparator = comparator;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public BinaryHeap(boolean isMinHeap)
/*  44:    */   {
/*  45:141 */     this(13, isMinHeap);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public BinaryHeap(boolean isMinHeap, Comparator comparator)
/*  49:    */   {
/*  50:153 */     this(isMinHeap);
/*  51:154 */     this.m_comparator = comparator;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public BinaryHeap(int capacity, boolean isMinHeap)
/*  55:    */   {
/*  56:169 */     if (capacity <= 0) {
/*  57:170 */       throw new IllegalArgumentException("invalid capacity");
/*  58:    */     }
/*  59:172 */     this.m_isMinHeap = isMinHeap;
/*  60:    */     
/*  61:    */ 
/*  62:175 */     this.m_elements = new Object[capacity + 1];
/*  63:    */   }
/*  64:    */   
/*  65:    */   public BinaryHeap(int capacity, boolean isMinHeap, Comparator comparator)
/*  66:    */   {
/*  67:190 */     this(capacity, isMinHeap);
/*  68:191 */     this.m_comparator = comparator;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void clear()
/*  72:    */   {
/*  73:199 */     this.m_elements = new Object[this.m_elements.length];
/*  74:200 */     this.m_size = 0;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isEmpty()
/*  78:    */   {
/*  79:210 */     return this.m_size == 0;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isFull()
/*  83:    */   {
/*  84:221 */     return this.m_elements.length == this.m_size + 1;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void insert(Object element)
/*  88:    */   {
/*  89:230 */     if (isFull()) {
/*  90:231 */       grow();
/*  91:    */     }
/*  92:234 */     if (this.m_isMinHeap) {
/*  93:235 */       percolateUpMinHeap(element);
/*  94:    */     } else {
/*  95:237 */       percolateUpMaxHeap(element);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Object peek()
/* 100:    */     throws NoSuchElementException
/* 101:    */   {
/* 102:248 */     if (isEmpty()) {
/* 103:249 */       throw new NoSuchElementException();
/* 104:    */     }
/* 105:251 */     return this.m_elements[1];
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Object pop()
/* 109:    */     throws NoSuchElementException
/* 110:    */   {
/* 111:262 */     Object result = peek();
/* 112:263 */     this.m_elements[1] = this.m_elements[(this.m_size--)];
/* 113:    */     
/* 114:    */ 
/* 115:    */ 
/* 116:267 */     this.m_elements[(this.m_size + 1)] = null;
/* 117:269 */     if (this.m_size != 0) {
/* 118:271 */       if (this.m_isMinHeap) {
/* 119:272 */         percolateDownMinHeap(1);
/* 120:    */       } else {
/* 121:274 */         percolateDownMaxHeap(1);
/* 122:    */       }
/* 123:    */     }
/* 124:278 */     return result;
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void percolateDownMinHeap(int index)
/* 128:    */   {
/* 129:289 */     Object element = this.m_elements[index];
/* 130:290 */     int hole = index;
/* 131:292 */     while (hole * 2 <= this.m_size)
/* 132:    */     {
/* 133:293 */       int child = hole * 2;
/* 134:297 */       if ((child != this.m_size) && (compare(this.m_elements[(child + 1)], this.m_elements[child]) < 0)) {
/* 135:298 */         child++;
/* 136:    */       }
/* 137:302 */       if (compare(this.m_elements[child], element) >= 0) {
/* 138:    */         break;
/* 139:    */       }
/* 140:306 */       this.m_elements[hole] = this.m_elements[child];
/* 141:307 */       hole = child;
/* 142:    */     }
/* 143:310 */     this.m_elements[hole] = element;
/* 144:    */   }
/* 145:    */   
/* 146:    */   protected void percolateDownMaxHeap(int index)
/* 147:    */   {
/* 148:321 */     Object element = this.m_elements[index];
/* 149:322 */     int hole = index;
/* 150:324 */     while (hole * 2 <= this.m_size)
/* 151:    */     {
/* 152:325 */       int child = hole * 2;
/* 153:329 */       if ((child != this.m_size) && (compare(this.m_elements[(child + 1)], this.m_elements[child]) > 0)) {
/* 154:330 */         child++;
/* 155:    */       }
/* 156:334 */       if (compare(this.m_elements[child], element) <= 0) {
/* 157:    */         break;
/* 158:    */       }
/* 159:338 */       this.m_elements[hole] = this.m_elements[child];
/* 160:339 */       hole = child;
/* 161:    */     }
/* 162:342 */     this.m_elements[hole] = element;
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected void percolateUpMinHeap(int index)
/* 166:    */   {
/* 167:353 */     int hole = index;
/* 168:354 */     Object element = this.m_elements[hole];
/* 169:355 */     while ((hole > 1) && (compare(element, this.m_elements[(hole / 2)]) < 0))
/* 170:    */     {
/* 171:358 */       int next = hole / 2;
/* 172:359 */       this.m_elements[hole] = this.m_elements[next];
/* 173:360 */       hole = next;
/* 174:    */     }
/* 175:362 */     this.m_elements[hole] = element;
/* 176:    */   }
/* 177:    */   
/* 178:    */   protected void percolateUpMinHeap(Object element)
/* 179:    */   {
/* 180:373 */     this.m_elements[(++this.m_size)] = element;
/* 181:374 */     percolateUpMinHeap(this.m_size);
/* 182:    */   }
/* 183:    */   
/* 184:    */   protected void percolateUpMaxHeap(int index)
/* 185:    */   {
/* 186:385 */     int hole = index;
/* 187:386 */     Object element = this.m_elements[hole];
/* 188:388 */     while ((hole > 1) && (compare(element, this.m_elements[(hole / 2)]) > 0))
/* 189:    */     {
/* 190:391 */       int next = hole / 2;
/* 191:392 */       this.m_elements[hole] = this.m_elements[next];
/* 192:393 */       hole = next;
/* 193:    */     }
/* 194:396 */     this.m_elements[hole] = element;
/* 195:    */   }
/* 196:    */   
/* 197:    */   protected void percolateUpMaxHeap(Object element)
/* 198:    */   {
/* 199:407 */     this.m_elements[(++this.m_size)] = element;
/* 200:408 */     percolateUpMaxHeap(this.m_size);
/* 201:    */   }
/* 202:    */   
/* 203:    */   private int compare(Object a, Object b)
/* 204:    */   {
/* 205:420 */     if (this.m_comparator != null) {
/* 206:421 */       return this.m_comparator.compare(a, b);
/* 207:    */     }
/* 208:423 */     return ((Comparable)a).compareTo(b);
/* 209:    */   }
/* 210:    */   
/* 211:    */   protected void grow()
/* 212:    */   {
/* 213:431 */     Object[] elements = new Object[this.m_elements.length * 2];
/* 214:432 */     System.arraycopy(this.m_elements, 0, elements, 0, this.m_elements.length);
/* 215:433 */     this.m_elements = elements;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String toString()
/* 219:    */   {
/* 220:443 */     StringBuffer sb = new StringBuffer();
/* 221:    */     
/* 222:445 */     sb.append("[ ");
/* 223:447 */     for (int i = 1; i < this.m_size + 1; i++)
/* 224:    */     {
/* 225:448 */       if (i != 1) {
/* 226:449 */         sb.append(", ");
/* 227:    */       }
/* 228:451 */       sb.append(this.m_elements[i]);
/* 229:    */     }
/* 230:454 */     sb.append(" ]");
/* 231:    */     
/* 232:456 */     return sb.toString();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Iterator iterator()
/* 236:    */   {
/* 237:466 */     new Iterator()
/* 238:    */     {
/* 239:468 */       private int index = 1;
/* 240:469 */       private int lastReturnedIndex = -1;
/* 241:    */       
/* 242:    */       public boolean hasNext()
/* 243:    */       {
/* 244:472 */         return this.index <= BinaryHeap.this.m_size;
/* 245:    */       }
/* 246:    */       
/* 247:    */       public Object next()
/* 248:    */       {
/* 249:476 */         if (!hasNext()) {
/* 250:476 */           throw new NoSuchElementException();
/* 251:    */         }
/* 252:477 */         this.lastReturnedIndex = this.index;
/* 253:478 */         this.index += 1;
/* 254:479 */         return BinaryHeap.this.m_elements[this.lastReturnedIndex];
/* 255:    */       }
/* 256:    */       
/* 257:    */       public void remove()
/* 258:    */       {
/* 259:483 */         if (this.lastReturnedIndex == -1) {
/* 260:484 */           throw new IllegalStateException();
/* 261:    */         }
/* 262:486 */         BinaryHeap.this.m_elements[this.lastReturnedIndex] = BinaryHeap.this.m_elements[BinaryHeap.this.m_size];
/* 263:487 */         BinaryHeap.this.m_elements[BinaryHeap.this.m_size] = null;
/* 264:488 */         BinaryHeap.this.m_size -= 1;
/* 265:489 */         if ((BinaryHeap.this.m_size != 0) && (this.lastReturnedIndex <= BinaryHeap.this.m_size))
/* 266:    */         {
/* 267:490 */           int compareToParent = 0;
/* 268:491 */           if (this.lastReturnedIndex > 1) {
/* 269:492 */             compareToParent = BinaryHeap.this.compare(BinaryHeap.this.m_elements[this.lastReturnedIndex], BinaryHeap.this.m_elements[(this.lastReturnedIndex / 2)]);
/* 270:    */           }
/* 271:495 */           if (BinaryHeap.this.m_isMinHeap)
/* 272:    */           {
/* 273:496 */             if ((this.lastReturnedIndex > 1) && (compareToParent < 0)) {
/* 274:497 */               BinaryHeap.this.percolateUpMinHeap(this.lastReturnedIndex);
/* 275:    */             } else {
/* 276:499 */               BinaryHeap.this.percolateDownMinHeap(this.lastReturnedIndex);
/* 277:    */             }
/* 278:    */           }
/* 279:502 */           else if ((this.lastReturnedIndex > 1) && (compareToParent > 0)) {
/* 280:503 */             BinaryHeap.this.percolateUpMaxHeap(this.lastReturnedIndex);
/* 281:    */           } else {
/* 282:505 */             BinaryHeap.this.percolateDownMaxHeap(this.lastReturnedIndex);
/* 283:    */           }
/* 284:    */         }
/* 285:509 */         this.index -= 1;
/* 286:510 */         this.lastReturnedIndex = -1;
/* 287:    */       }
/* 288:    */     };
/* 289:    */   }
/* 290:    */   
/* 291:    */   public boolean add(Object object)
/* 292:    */   {
/* 293:524 */     insert(object);
/* 294:525 */     return true;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Object get()
/* 298:    */   {
/* 299:    */     try
/* 300:    */     {
/* 301:536 */       return peek();
/* 302:    */     }
/* 303:    */     catch (NoSuchElementException e)
/* 304:    */     {
/* 305:538 */       throw new BufferUnderflowException();
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   public Object remove()
/* 310:    */   {
/* 311:    */     try
/* 312:    */     {
/* 313:550 */       return pop();
/* 314:    */     }
/* 315:    */     catch (NoSuchElementException e)
/* 316:    */     {
/* 317:552 */       throw new BufferUnderflowException();
/* 318:    */     }
/* 319:    */   }
/* 320:    */   
/* 321:    */   public int size()
/* 322:    */   {
/* 323:562 */     return this.m_size;
/* 324:    */   }
/* 325:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BinaryHeap
 * JD-Core Version:    0.7.0.1
 */
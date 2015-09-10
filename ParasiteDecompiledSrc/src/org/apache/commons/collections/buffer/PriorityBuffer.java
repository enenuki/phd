/*   1:    */ package org.apache.commons.collections.buffer;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.AbstractCollection;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.NoSuchElementException;
/*   8:    */ import org.apache.commons.collections.Buffer;
/*   9:    */ import org.apache.commons.collections.BufferUnderflowException;
/*  10:    */ 
/*  11:    */ public class PriorityBuffer
/*  12:    */   extends AbstractCollection
/*  13:    */   implements Buffer, Serializable
/*  14:    */ {
/*  15:    */   private static final long serialVersionUID = 6891186490470027896L;
/*  16:    */   private static final int DEFAULT_CAPACITY = 13;
/*  17:    */   protected Object[] elements;
/*  18:    */   protected int size;
/*  19:    */   protected boolean ascendingOrder;
/*  20:    */   protected Comparator comparator;
/*  21:    */   
/*  22:    */   public PriorityBuffer()
/*  23:    */   {
/*  24:101 */     this(13, true, null);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public PriorityBuffer(Comparator comparator)
/*  28:    */   {
/*  29:112 */     this(13, true, comparator);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public PriorityBuffer(boolean ascendingOrder)
/*  33:    */   {
/*  34:123 */     this(13, ascendingOrder, null);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public PriorityBuffer(boolean ascendingOrder, Comparator comparator)
/*  38:    */   {
/*  39:135 */     this(13, ascendingOrder, comparator);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public PriorityBuffer(int capacity)
/*  43:    */   {
/*  44:146 */     this(capacity, true, null);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public PriorityBuffer(int capacity, Comparator comparator)
/*  48:    */   {
/*  49:159 */     this(capacity, true, comparator);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public PriorityBuffer(int capacity, boolean ascendingOrder)
/*  53:    */   {
/*  54:172 */     this(capacity, ascendingOrder, null);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public PriorityBuffer(int capacity, boolean ascendingOrder, Comparator comparator)
/*  58:    */   {
/*  59:188 */     if (capacity <= 0) {
/*  60:189 */       throw new IllegalArgumentException("invalid capacity");
/*  61:    */     }
/*  62:191 */     this.ascendingOrder = ascendingOrder;
/*  63:    */     
/*  64:    */ 
/*  65:194 */     this.elements = new Object[capacity + 1];
/*  66:195 */     this.comparator = comparator;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isAscendingOrder()
/*  70:    */   {
/*  71:205 */     return this.ascendingOrder;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Comparator comparator()
/*  75:    */   {
/*  76:214 */     return this.comparator;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int size()
/*  80:    */   {
/*  81:224 */     return this.size;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void clear()
/*  85:    */   {
/*  86:231 */     this.elements = new Object[this.elements.length];
/*  87:232 */     this.size = 0;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean add(Object element)
/*  91:    */   {
/*  92:244 */     if (isAtCapacity()) {
/*  93:245 */       grow();
/*  94:    */     }
/*  95:248 */     if (this.ascendingOrder) {
/*  96:249 */       percolateUpMinHeap(element);
/*  97:    */     } else {
/*  98:251 */       percolateUpMaxHeap(element);
/*  99:    */     }
/* 100:253 */     return true;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Object get()
/* 104:    */   {
/* 105:263 */     if (isEmpty()) {
/* 106:264 */       throw new BufferUnderflowException();
/* 107:    */     }
/* 108:266 */     return this.elements[1];
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Object remove()
/* 112:    */   {
/* 113:277 */     Object result = get();
/* 114:278 */     this.elements[1] = this.elements[(this.size--)];
/* 115:    */     
/* 116:    */ 
/* 117:    */ 
/* 118:282 */     this.elements[(this.size + 1)] = null;
/* 119:284 */     if (this.size != 0) {
/* 120:286 */       if (this.ascendingOrder) {
/* 121:287 */         percolateDownMinHeap(1);
/* 122:    */       } else {
/* 123:289 */         percolateDownMaxHeap(1);
/* 124:    */       }
/* 125:    */     }
/* 126:293 */     return result;
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected boolean isAtCapacity()
/* 130:    */   {
/* 131:304 */     return this.elements.length == this.size + 1;
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected void percolateDownMinHeap(int index)
/* 135:    */   {
/* 136:316 */     Object element = this.elements[index];
/* 137:317 */     int hole = index;
/* 138:319 */     while (hole * 2 <= this.size)
/* 139:    */     {
/* 140:320 */       int child = hole * 2;
/* 141:324 */       if ((child != this.size) && (compare(this.elements[(child + 1)], this.elements[child]) < 0)) {
/* 142:325 */         child++;
/* 143:    */       }
/* 144:329 */       if (compare(this.elements[child], element) >= 0) {
/* 145:    */         break;
/* 146:    */       }
/* 147:333 */       this.elements[hole] = this.elements[child];
/* 148:334 */       hole = child;
/* 149:    */     }
/* 150:337 */     this.elements[hole] = element;
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected void percolateDownMaxHeap(int index)
/* 154:    */   {
/* 155:348 */     Object element = this.elements[index];
/* 156:349 */     int hole = index;
/* 157:351 */     while (hole * 2 <= this.size)
/* 158:    */     {
/* 159:352 */       int child = hole * 2;
/* 160:356 */       if ((child != this.size) && (compare(this.elements[(child + 1)], this.elements[child]) > 0)) {
/* 161:357 */         child++;
/* 162:    */       }
/* 163:361 */       if (compare(this.elements[child], element) <= 0) {
/* 164:    */         break;
/* 165:    */       }
/* 166:365 */       this.elements[hole] = this.elements[child];
/* 167:366 */       hole = child;
/* 168:    */     }
/* 169:369 */     this.elements[hole] = element;
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected void percolateUpMinHeap(int index)
/* 173:    */   {
/* 174:380 */     int hole = index;
/* 175:381 */     Object element = this.elements[hole];
/* 176:382 */     while ((hole > 1) && (compare(element, this.elements[(hole / 2)]) < 0))
/* 177:    */     {
/* 178:385 */       int next = hole / 2;
/* 179:386 */       this.elements[hole] = this.elements[next];
/* 180:387 */       hole = next;
/* 181:    */     }
/* 182:389 */     this.elements[hole] = element;
/* 183:    */   }
/* 184:    */   
/* 185:    */   protected void percolateUpMinHeap(Object element)
/* 186:    */   {
/* 187:400 */     this.elements[(++this.size)] = element;
/* 188:401 */     percolateUpMinHeap(this.size);
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected void percolateUpMaxHeap(int index)
/* 192:    */   {
/* 193:412 */     int hole = index;
/* 194:413 */     Object element = this.elements[hole];
/* 195:415 */     while ((hole > 1) && (compare(element, this.elements[(hole / 2)]) > 0))
/* 196:    */     {
/* 197:418 */       int next = hole / 2;
/* 198:419 */       this.elements[hole] = this.elements[next];
/* 199:420 */       hole = next;
/* 200:    */     }
/* 201:423 */     this.elements[hole] = element;
/* 202:    */   }
/* 203:    */   
/* 204:    */   protected void percolateUpMaxHeap(Object element)
/* 205:    */   {
/* 206:434 */     this.elements[(++this.size)] = element;
/* 207:435 */     percolateUpMaxHeap(this.size);
/* 208:    */   }
/* 209:    */   
/* 210:    */   protected int compare(Object a, Object b)
/* 211:    */   {
/* 212:447 */     if (this.comparator != null) {
/* 213:448 */       return this.comparator.compare(a, b);
/* 214:    */     }
/* 215:450 */     return ((Comparable)a).compareTo(b);
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected void grow()
/* 219:    */   {
/* 220:458 */     Object[] array = new Object[this.elements.length * 2];
/* 221:459 */     System.arraycopy(this.elements, 0, array, 0, this.elements.length);
/* 222:460 */     this.elements = array;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Iterator iterator()
/* 226:    */   {
/* 227:470 */     new Iterator()
/* 228:    */     {
/* 229:472 */       private int index = 1;
/* 230:473 */       private int lastReturnedIndex = -1;
/* 231:    */       
/* 232:    */       public boolean hasNext()
/* 233:    */       {
/* 234:476 */         return this.index <= PriorityBuffer.this.size;
/* 235:    */       }
/* 236:    */       
/* 237:    */       public Object next()
/* 238:    */       {
/* 239:480 */         if (!hasNext()) {
/* 240:481 */           throw new NoSuchElementException();
/* 241:    */         }
/* 242:483 */         this.lastReturnedIndex = this.index;
/* 243:484 */         this.index += 1;
/* 244:485 */         return PriorityBuffer.this.elements[this.lastReturnedIndex];
/* 245:    */       }
/* 246:    */       
/* 247:    */       public void remove()
/* 248:    */       {
/* 249:489 */         if (this.lastReturnedIndex == -1) {
/* 250:490 */           throw new IllegalStateException();
/* 251:    */         }
/* 252:492 */         PriorityBuffer.this.elements[this.lastReturnedIndex] = PriorityBuffer.this.elements[PriorityBuffer.this.size];
/* 253:493 */         PriorityBuffer.this.elements[PriorityBuffer.this.size] = null;
/* 254:494 */         PriorityBuffer.this.size -= 1;
/* 255:495 */         if ((PriorityBuffer.this.size != 0) && (this.lastReturnedIndex <= PriorityBuffer.this.size))
/* 256:    */         {
/* 257:496 */           int compareToParent = 0;
/* 258:497 */           if (this.lastReturnedIndex > 1) {
/* 259:498 */             compareToParent = PriorityBuffer.this.compare(PriorityBuffer.this.elements[this.lastReturnedIndex], PriorityBuffer.this.elements[(this.lastReturnedIndex / 2)]);
/* 260:    */           }
/* 261:501 */           if (PriorityBuffer.this.ascendingOrder)
/* 262:    */           {
/* 263:502 */             if ((this.lastReturnedIndex > 1) && (compareToParent < 0)) {
/* 264:503 */               PriorityBuffer.this.percolateUpMinHeap(this.lastReturnedIndex);
/* 265:    */             } else {
/* 266:505 */               PriorityBuffer.this.percolateDownMinHeap(this.lastReturnedIndex);
/* 267:    */             }
/* 268:    */           }
/* 269:508 */           else if ((this.lastReturnedIndex > 1) && (compareToParent > 0)) {
/* 270:509 */             PriorityBuffer.this.percolateUpMaxHeap(this.lastReturnedIndex);
/* 271:    */           } else {
/* 272:511 */             PriorityBuffer.this.percolateDownMaxHeap(this.lastReturnedIndex);
/* 273:    */           }
/* 274:    */         }
/* 275:515 */         this.index -= 1;
/* 276:516 */         this.lastReturnedIndex = -1;
/* 277:    */       }
/* 278:    */     };
/* 279:    */   }
/* 280:    */   
/* 281:    */   public String toString()
/* 282:    */   {
/* 283:529 */     StringBuffer sb = new StringBuffer();
/* 284:    */     
/* 285:531 */     sb.append("[ ");
/* 286:533 */     for (int i = 1; i < this.size + 1; i++)
/* 287:    */     {
/* 288:534 */       if (i != 1) {
/* 289:535 */         sb.append(", ");
/* 290:    */       }
/* 291:537 */       sb.append(this.elements[i]);
/* 292:    */     }
/* 293:540 */     sb.append(" ]");
/* 294:    */     
/* 295:542 */     return sb.toString();
/* 296:    */   }
/* 297:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.PriorityBuffer
 * JD-Core Version:    0.7.0.1
 */
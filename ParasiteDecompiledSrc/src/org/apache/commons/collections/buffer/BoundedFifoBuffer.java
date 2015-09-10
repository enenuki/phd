/*   1:    */ package org.apache.commons.collections.buffer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.AbstractCollection;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.NoSuchElementException;
/*  12:    */ import org.apache.commons.collections.BoundedCollection;
/*  13:    */ import org.apache.commons.collections.Buffer;
/*  14:    */ import org.apache.commons.collections.BufferOverflowException;
/*  15:    */ import org.apache.commons.collections.BufferUnderflowException;
/*  16:    */ 
/*  17:    */ public class BoundedFifoBuffer
/*  18:    */   extends AbstractCollection
/*  19:    */   implements Buffer, BoundedCollection, Serializable
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 5603722811189451017L;
/*  22:    */   private transient Object[] elements;
/*  23: 75 */   private transient int start = 0;
/*  24: 84 */   private transient int end = 0;
/*  25: 87 */   private transient boolean full = false;
/*  26:    */   private final int maxElements;
/*  27:    */   
/*  28:    */   public BoundedFifoBuffer()
/*  29:    */   {
/*  30: 97 */     this(32);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public BoundedFifoBuffer(int size)
/*  34:    */   {
/*  35:108 */     if (size <= 0) {
/*  36:109 */       throw new IllegalArgumentException("The size must be greater than 0");
/*  37:    */     }
/*  38:111 */     this.elements = new Object[size];
/*  39:112 */     this.maxElements = this.elements.length;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public BoundedFifoBuffer(Collection coll)
/*  43:    */   {
/*  44:124 */     this(coll.size());
/*  45:125 */     addAll(coll);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void writeObject(ObjectOutputStream out)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51:136 */     out.defaultWriteObject();
/*  52:137 */     out.writeInt(size());
/*  53:138 */     for (Iterator it = iterator(); it.hasNext();) {
/*  54:139 */       out.writeObject(it.next());
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   private void readObject(ObjectInputStream in)
/*  59:    */     throws IOException, ClassNotFoundException
/*  60:    */   {
/*  61:151 */     in.defaultReadObject();
/*  62:152 */     this.elements = new Object[this.maxElements];
/*  63:153 */     int size = in.readInt();
/*  64:154 */     for (int i = 0; i < size; i++) {
/*  65:155 */       this.elements[i] = in.readObject();
/*  66:    */     }
/*  67:157 */     this.start = 0;
/*  68:158 */     this.full = (size == this.maxElements);
/*  69:159 */     if (this.full) {
/*  70:160 */       this.end = 0;
/*  71:    */     } else {
/*  72:162 */       this.end = size;
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int size()
/*  77:    */   {
/*  78:173 */     int size = 0;
/*  79:175 */     if (this.end < this.start) {
/*  80:176 */       size = this.maxElements - this.start + this.end;
/*  81:177 */     } else if (this.end == this.start) {
/*  82:178 */       size = this.full ? this.maxElements : 0;
/*  83:    */     } else {
/*  84:180 */       size = this.end - this.start;
/*  85:    */     }
/*  86:183 */     return size;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isEmpty()
/*  90:    */   {
/*  91:192 */     return size() == 0;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isFull()
/*  95:    */   {
/*  96:201 */     return size() == this.maxElements;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int maxSize()
/* 100:    */   {
/* 101:210 */     return this.maxElements;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void clear()
/* 105:    */   {
/* 106:217 */     this.full = false;
/* 107:218 */     this.start = 0;
/* 108:219 */     this.end = 0;
/* 109:220 */     Arrays.fill(this.elements, null);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean add(Object element)
/* 113:    */   {
/* 114:232 */     if (null == element) {
/* 115:233 */       throw new NullPointerException("Attempted to add null object to buffer");
/* 116:    */     }
/* 117:236 */     if (this.full) {
/* 118:237 */       throw new BufferOverflowException("The buffer cannot hold more than " + this.maxElements + " objects.");
/* 119:    */     }
/* 120:240 */     this.elements[(this.end++)] = element;
/* 121:242 */     if (this.end >= this.maxElements) {
/* 122:243 */       this.end = 0;
/* 123:    */     }
/* 124:246 */     if (this.end == this.start) {
/* 125:247 */       this.full = true;
/* 126:    */     }
/* 127:250 */     return true;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Object get()
/* 131:    */   {
/* 132:260 */     if (isEmpty()) {
/* 133:261 */       throw new BufferUnderflowException("The buffer is already empty");
/* 134:    */     }
/* 135:264 */     return this.elements[this.start];
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Object remove()
/* 139:    */   {
/* 140:274 */     if (isEmpty()) {
/* 141:275 */       throw new BufferUnderflowException("The buffer is already empty");
/* 142:    */     }
/* 143:278 */     Object element = this.elements[this.start];
/* 144:280 */     if (null != element)
/* 145:    */     {
/* 146:281 */       this.elements[(this.start++)] = null;
/* 147:283 */       if (this.start >= this.maxElements) {
/* 148:284 */         this.start = 0;
/* 149:    */       }
/* 150:287 */       this.full = false;
/* 151:    */     }
/* 152:290 */     return element;
/* 153:    */   }
/* 154:    */   
/* 155:    */   private int increment(int index)
/* 156:    */   {
/* 157:    */     
/* 158:301 */     if (index >= this.maxElements) {
/* 159:302 */       index = 0;
/* 160:    */     }
/* 161:304 */     return index;
/* 162:    */   }
/* 163:    */   
/* 164:    */   private int decrement(int index)
/* 165:    */   {
/* 166:    */     
/* 167:315 */     if (index < 0) {
/* 168:316 */       index = this.maxElements - 1;
/* 169:    */     }
/* 170:318 */     return index;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Iterator iterator()
/* 174:    */   {
/* 175:327 */     new Iterator()
/* 176:    */     {
/* 177:329 */       private int index = BoundedFifoBuffer.this.start;
/* 178:330 */       private int lastReturnedIndex = -1;
/* 179:331 */       private boolean isFirst = BoundedFifoBuffer.this.full;
/* 180:    */       
/* 181:    */       public boolean hasNext()
/* 182:    */       {
/* 183:334 */         return (this.isFirst) || (this.index != BoundedFifoBuffer.this.end);
/* 184:    */       }
/* 185:    */       
/* 186:    */       public Object next()
/* 187:    */       {
/* 188:339 */         if (!hasNext()) {
/* 189:340 */           throw new NoSuchElementException();
/* 190:    */         }
/* 191:342 */         this.isFirst = false;
/* 192:343 */         this.lastReturnedIndex = this.index;
/* 193:344 */         this.index = BoundedFifoBuffer.this.increment(this.index);
/* 194:345 */         return BoundedFifoBuffer.this.elements[this.lastReturnedIndex];
/* 195:    */       }
/* 196:    */       
/* 197:    */       public void remove()
/* 198:    */       {
/* 199:349 */         if (this.lastReturnedIndex == -1) {
/* 200:350 */           throw new IllegalStateException();
/* 201:    */         }
/* 202:354 */         if (this.lastReturnedIndex == BoundedFifoBuffer.this.start)
/* 203:    */         {
/* 204:355 */           BoundedFifoBuffer.this.remove();
/* 205:356 */           this.lastReturnedIndex = -1;
/* 206:357 */           return;
/* 207:    */         }
/* 208:360 */         int pos = this.lastReturnedIndex + 1;
/* 209:361 */         if ((BoundedFifoBuffer.this.start < this.lastReturnedIndex) && (pos < BoundedFifoBuffer.this.end)) {
/* 210:363 */           System.arraycopy(BoundedFifoBuffer.this.elements, pos, BoundedFifoBuffer.this.elements, this.lastReturnedIndex, BoundedFifoBuffer.this.end - pos);
/* 211:    */         } else {
/* 212:367 */           while (pos != BoundedFifoBuffer.this.end) {
/* 213:368 */             if (pos >= BoundedFifoBuffer.this.maxElements)
/* 214:    */             {
/* 215:369 */               BoundedFifoBuffer.this.elements[(pos - 1)] = BoundedFifoBuffer.this.elements[0];
/* 216:370 */               pos = 0;
/* 217:    */             }
/* 218:    */             else
/* 219:    */             {
/* 220:372 */               BoundedFifoBuffer.this.elements[BoundedFifoBuffer.this.decrement(pos)] = BoundedFifoBuffer.this.elements[pos];
/* 221:373 */               pos = BoundedFifoBuffer.this.increment(pos);
/* 222:    */             }
/* 223:    */           }
/* 224:    */         }
/* 225:378 */         this.lastReturnedIndex = -1;
/* 226:379 */         BoundedFifoBuffer.this.end = BoundedFifoBuffer.this.decrement(BoundedFifoBuffer.this.end);
/* 227:380 */         BoundedFifoBuffer.this.elements[BoundedFifoBuffer.this.end] = null;
/* 228:381 */         BoundedFifoBuffer.this.full = false;
/* 229:382 */         this.index = BoundedFifoBuffer.this.decrement(this.index);
/* 230:    */       }
/* 231:    */     };
/* 232:    */   }
/* 233:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.BoundedFifoBuffer
 * JD-Core Version:    0.7.0.1
 */
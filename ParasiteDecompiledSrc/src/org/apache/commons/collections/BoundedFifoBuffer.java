/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.NoSuchElementException;
/*   8:    */ 
/*   9:    */ /**
/*  10:    */  * @deprecated
/*  11:    */  */
/*  12:    */ public class BoundedFifoBuffer
/*  13:    */   extends AbstractCollection
/*  14:    */   implements Buffer, BoundedCollection
/*  15:    */ {
/*  16:    */   private final Object[] m_elements;
/*  17: 59 */   private int m_start = 0;
/*  18: 60 */   private int m_end = 0;
/*  19: 61 */   private boolean m_full = false;
/*  20:    */   private final int maxElements;
/*  21:    */   
/*  22:    */   public BoundedFifoBuffer()
/*  23:    */   {
/*  24: 69 */     this(32);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public BoundedFifoBuffer(int size)
/*  28:    */   {
/*  29: 80 */     if (size <= 0) {
/*  30: 81 */       throw new IllegalArgumentException("The size must be greater than 0");
/*  31:    */     }
/*  32: 83 */     this.m_elements = new Object[size];
/*  33: 84 */     this.maxElements = this.m_elements.length;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public BoundedFifoBuffer(Collection coll)
/*  37:    */   {
/*  38: 96 */     this(coll.size());
/*  39: 97 */     addAll(coll);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int size()
/*  43:    */   {
/*  44:106 */     int size = 0;
/*  45:108 */     if (this.m_end < this.m_start) {
/*  46:109 */       size = this.maxElements - this.m_start + this.m_end;
/*  47:110 */     } else if (this.m_end == this.m_start) {
/*  48:111 */       size = this.m_full ? this.maxElements : 0;
/*  49:    */     } else {
/*  50:113 */       size = this.m_end - this.m_start;
/*  51:    */     }
/*  52:116 */     return size;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isEmpty()
/*  56:    */   {
/*  57:125 */     return size() == 0;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isFull()
/*  61:    */   {
/*  62:134 */     return size() == this.maxElements;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int maxSize()
/*  66:    */   {
/*  67:143 */     return this.maxElements;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void clear()
/*  71:    */   {
/*  72:150 */     this.m_full = false;
/*  73:151 */     this.m_start = 0;
/*  74:152 */     this.m_end = 0;
/*  75:153 */     Arrays.fill(this.m_elements, null);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean add(Object element)
/*  79:    */   {
/*  80:165 */     if (null == element) {
/*  81:166 */       throw new NullPointerException("Attempted to add null object to buffer");
/*  82:    */     }
/*  83:169 */     if (this.m_full) {
/*  84:170 */       throw new BufferOverflowException("The buffer cannot hold more than " + this.maxElements + " objects.");
/*  85:    */     }
/*  86:173 */     this.m_elements[(this.m_end++)] = element;
/*  87:175 */     if (this.m_end >= this.maxElements) {
/*  88:176 */       this.m_end = 0;
/*  89:    */     }
/*  90:179 */     if (this.m_end == this.m_start) {
/*  91:180 */       this.m_full = true;
/*  92:    */     }
/*  93:183 */     return true;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Object get()
/*  97:    */   {
/*  98:193 */     if (isEmpty()) {
/*  99:194 */       throw new BufferUnderflowException("The buffer is already empty");
/* 100:    */     }
/* 101:197 */     return this.m_elements[this.m_start];
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Object remove()
/* 105:    */   {
/* 106:207 */     if (isEmpty()) {
/* 107:208 */       throw new BufferUnderflowException("The buffer is already empty");
/* 108:    */     }
/* 109:211 */     Object element = this.m_elements[this.m_start];
/* 110:213 */     if (null != element)
/* 111:    */     {
/* 112:214 */       this.m_elements[(this.m_start++)] = null;
/* 113:216 */       if (this.m_start >= this.maxElements) {
/* 114:217 */         this.m_start = 0;
/* 115:    */       }
/* 116:220 */       this.m_full = false;
/* 117:    */     }
/* 118:223 */     return element;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private int increment(int index)
/* 122:    */   {
/* 123:    */     
/* 124:234 */     if (index >= this.maxElements) {
/* 125:235 */       index = 0;
/* 126:    */     }
/* 127:237 */     return index;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private int decrement(int index)
/* 131:    */   {
/* 132:    */     
/* 133:248 */     if (index < 0) {
/* 134:249 */       index = this.maxElements - 1;
/* 135:    */     }
/* 136:251 */     return index;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Iterator iterator()
/* 140:    */   {
/* 141:260 */     new Iterator()
/* 142:    */     {
/* 143:262 */       private int index = BoundedFifoBuffer.this.m_start;
/* 144:263 */       private int lastReturnedIndex = -1;
/* 145:264 */       private boolean isFirst = BoundedFifoBuffer.this.m_full;
/* 146:    */       
/* 147:    */       public boolean hasNext()
/* 148:    */       {
/* 149:267 */         return (this.isFirst) || (this.index != BoundedFifoBuffer.this.m_end);
/* 150:    */       }
/* 151:    */       
/* 152:    */       public Object next()
/* 153:    */       {
/* 154:272 */         if (!hasNext()) {
/* 155:272 */           throw new NoSuchElementException();
/* 156:    */         }
/* 157:273 */         this.isFirst = false;
/* 158:274 */         this.lastReturnedIndex = this.index;
/* 159:275 */         this.index = BoundedFifoBuffer.this.increment(this.index);
/* 160:276 */         return BoundedFifoBuffer.this.m_elements[this.lastReturnedIndex];
/* 161:    */       }
/* 162:    */       
/* 163:    */       public void remove()
/* 164:    */       {
/* 165:280 */         if (this.lastReturnedIndex == -1) {
/* 166:280 */           throw new IllegalStateException();
/* 167:    */         }
/* 168:283 */         if (this.lastReturnedIndex == BoundedFifoBuffer.this.m_start)
/* 169:    */         {
/* 170:284 */           BoundedFifoBuffer.this.remove();
/* 171:285 */           this.lastReturnedIndex = -1;
/* 172:286 */           return;
/* 173:    */         }
/* 174:290 */         int i = this.lastReturnedIndex + 1;
/* 175:291 */         while (i != BoundedFifoBuffer.this.m_end) {
/* 176:292 */           if (i >= BoundedFifoBuffer.this.maxElements)
/* 177:    */           {
/* 178:293 */             BoundedFifoBuffer.this.m_elements[(i - 1)] = BoundedFifoBuffer.this.m_elements[0];
/* 179:294 */             i = 0;
/* 180:    */           }
/* 181:    */           else
/* 182:    */           {
/* 183:296 */             BoundedFifoBuffer.this.m_elements[(i - 1)] = BoundedFifoBuffer.this.m_elements[i];
/* 184:297 */             i++;
/* 185:    */           }
/* 186:    */         }
/* 187:301 */         this.lastReturnedIndex = -1;
/* 188:302 */         BoundedFifoBuffer.this.m_end = BoundedFifoBuffer.this.decrement(BoundedFifoBuffer.this.m_end);
/* 189:303 */         BoundedFifoBuffer.this.m_elements[BoundedFifoBuffer.this.m_end] = null;
/* 190:304 */         BoundedFifoBuffer.this.m_full = false;
/* 191:305 */         this.index = BoundedFifoBuffer.this.decrement(this.index);
/* 192:    */       }
/* 193:    */     };
/* 194:    */   }
/* 195:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BoundedFifoBuffer
 * JD-Core Version:    0.7.0.1
 */
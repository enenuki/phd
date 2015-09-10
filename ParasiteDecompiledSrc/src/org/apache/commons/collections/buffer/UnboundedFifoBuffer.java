/*   1:    */ package org.apache.commons.collections.buffer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.AbstractCollection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.NoSuchElementException;
/*  10:    */ import org.apache.commons.collections.Buffer;
/*  11:    */ import org.apache.commons.collections.BufferUnderflowException;
/*  12:    */ 
/*  13:    */ public class UnboundedFifoBuffer
/*  14:    */   extends AbstractCollection
/*  15:    */   implements Buffer, Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -3482960336579541419L;
/*  18:    */   protected transient Object[] buffer;
/*  19:    */   protected transient int head;
/*  20:    */   protected transient int tail;
/*  21:    */   
/*  22:    */   public UnboundedFifoBuffer()
/*  23:    */   {
/*  24: 89 */     this(32);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public UnboundedFifoBuffer(int initialSize)
/*  28:    */   {
/*  29:100 */     if (initialSize <= 0) {
/*  30:101 */       throw new IllegalArgumentException("The size must be greater than 0");
/*  31:    */     }
/*  32:103 */     this.buffer = new Object[initialSize + 1];
/*  33:104 */     this.head = 0;
/*  34:105 */     this.tail = 0;
/*  35:    */   }
/*  36:    */   
/*  37:    */   private void writeObject(ObjectOutputStream out)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40:116 */     out.defaultWriteObject();
/*  41:117 */     out.writeInt(size());
/*  42:118 */     for (Iterator it = iterator(); it.hasNext();) {
/*  43:119 */       out.writeObject(it.next());
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void readObject(ObjectInputStream in)
/*  48:    */     throws IOException, ClassNotFoundException
/*  49:    */   {
/*  50:131 */     in.defaultReadObject();
/*  51:132 */     int size = in.readInt();
/*  52:133 */     this.buffer = new Object[size + 1];
/*  53:134 */     for (int i = 0; i < size; i++) {
/*  54:135 */       this.buffer[i] = in.readObject();
/*  55:    */     }
/*  56:137 */     this.head = 0;
/*  57:138 */     this.tail = size;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int size()
/*  61:    */   {
/*  62:148 */     int size = 0;
/*  63:150 */     if (this.tail < this.head) {
/*  64:151 */       size = this.buffer.length - this.head + this.tail;
/*  65:    */     } else {
/*  66:153 */       size = this.tail - this.head;
/*  67:    */     }
/*  68:156 */     return size;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isEmpty()
/*  72:    */   {
/*  73:165 */     return size() == 0;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean add(Object obj)
/*  77:    */   {
/*  78:176 */     if (obj == null) {
/*  79:177 */       throw new NullPointerException("Attempted to add null object to buffer");
/*  80:    */     }
/*  81:180 */     if (size() + 1 >= this.buffer.length)
/*  82:    */     {
/*  83:182 */       Object[] tmp = new Object[(this.buffer.length - 1) * 2 + 1];
/*  84:183 */       int j = 0;
/*  85:185 */       for (int i = this.head; i != this.tail;)
/*  86:    */       {
/*  87:186 */         tmp[j] = this.buffer[i];
/*  88:187 */         this.buffer[i] = null;
/*  89:    */         
/*  90:189 */         j++;
/*  91:190 */         i = increment(i);
/*  92:    */       }
/*  93:192 */       this.buffer = tmp;
/*  94:193 */       this.head = 0;
/*  95:194 */       this.tail = j;
/*  96:    */     }
/*  97:197 */     this.buffer[this.tail] = obj;
/*  98:198 */     this.tail = increment(this.tail);
/*  99:199 */     return true;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object get()
/* 103:    */   {
/* 104:209 */     if (isEmpty()) {
/* 105:210 */       throw new BufferUnderflowException("The buffer is already empty");
/* 106:    */     }
/* 107:213 */     return this.buffer[this.head];
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Object remove()
/* 111:    */   {
/* 112:223 */     if (isEmpty()) {
/* 113:224 */       throw new BufferUnderflowException("The buffer is already empty");
/* 114:    */     }
/* 115:227 */     Object element = this.buffer[this.head];
/* 116:228 */     if (element != null)
/* 117:    */     {
/* 118:229 */       this.buffer[this.head] = null;
/* 119:230 */       this.head = increment(this.head);
/* 120:    */     }
/* 121:232 */     return element;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private int increment(int index)
/* 125:    */   {
/* 126:    */     
/* 127:243 */     if (index >= this.buffer.length) {
/* 128:244 */       index = 0;
/* 129:    */     }
/* 130:246 */     return index;
/* 131:    */   }
/* 132:    */   
/* 133:    */   private int decrement(int index)
/* 134:    */   {
/* 135:    */     
/* 136:257 */     if (index < 0) {
/* 137:258 */       index = this.buffer.length - 1;
/* 138:    */     }
/* 139:260 */     return index;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Iterator iterator()
/* 143:    */   {
/* 144:269 */     new Iterator()
/* 145:    */     {
/* 146:271 */       private int index = UnboundedFifoBuffer.this.head;
/* 147:272 */       private int lastReturnedIndex = -1;
/* 148:    */       
/* 149:    */       public boolean hasNext()
/* 150:    */       {
/* 151:275 */         return this.index != UnboundedFifoBuffer.this.tail;
/* 152:    */       }
/* 153:    */       
/* 154:    */       public Object next()
/* 155:    */       {
/* 156:280 */         if (!hasNext()) {
/* 157:281 */           throw new NoSuchElementException();
/* 158:    */         }
/* 159:283 */         this.lastReturnedIndex = this.index;
/* 160:284 */         this.index = UnboundedFifoBuffer.this.increment(this.index);
/* 161:285 */         return UnboundedFifoBuffer.this.buffer[this.lastReturnedIndex];
/* 162:    */       }
/* 163:    */       
/* 164:    */       public void remove()
/* 165:    */       {
/* 166:289 */         if (this.lastReturnedIndex == -1) {
/* 167:290 */           throw new IllegalStateException();
/* 168:    */         }
/* 169:294 */         if (this.lastReturnedIndex == UnboundedFifoBuffer.this.head)
/* 170:    */         {
/* 171:295 */           UnboundedFifoBuffer.this.remove();
/* 172:296 */           this.lastReturnedIndex = -1;
/* 173:297 */           return;
/* 174:    */         }
/* 175:301 */         int i = UnboundedFifoBuffer.this.increment(this.lastReturnedIndex);
/* 176:302 */         while (i != UnboundedFifoBuffer.this.tail)
/* 177:    */         {
/* 178:303 */           UnboundedFifoBuffer.this.buffer[UnboundedFifoBuffer.this.decrement(i)] = UnboundedFifoBuffer.this.buffer[i];
/* 179:304 */           i = UnboundedFifoBuffer.this.increment(i);
/* 180:    */         }
/* 181:307 */         this.lastReturnedIndex = -1;
/* 182:308 */         UnboundedFifoBuffer.this.tail = UnboundedFifoBuffer.this.decrement(UnboundedFifoBuffer.this.tail);
/* 183:309 */         UnboundedFifoBuffer.this.buffer[UnboundedFifoBuffer.this.tail] = null;
/* 184:310 */         this.index = UnboundedFifoBuffer.this.decrement(this.index);
/* 185:    */       }
/* 186:    */     };
/* 187:    */   }
/* 188:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.UnboundedFifoBuffer
 * JD-Core Version:    0.7.0.1
 */
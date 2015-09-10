/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.NoSuchElementException;
/*   6:    */ 
/*   7:    */ /**
/*   8:    */  * @deprecated
/*   9:    */  */
/*  10:    */ public class UnboundedFifoBuffer
/*  11:    */   extends AbstractCollection
/*  12:    */   implements Buffer
/*  13:    */ {
/*  14:    */   protected Object[] m_buffer;
/*  15:    */   protected int m_head;
/*  16:    */   protected int m_tail;
/*  17:    */   
/*  18:    */   public UnboundedFifoBuffer()
/*  19:    */   {
/*  20: 70 */     this(32);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public UnboundedFifoBuffer(int initialSize)
/*  24:    */   {
/*  25: 81 */     if (initialSize <= 0) {
/*  26: 82 */       throw new IllegalArgumentException("The size must be greater than 0");
/*  27:    */     }
/*  28: 84 */     this.m_buffer = new Object[initialSize + 1];
/*  29: 85 */     this.m_head = 0;
/*  30: 86 */     this.m_tail = 0;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int size()
/*  34:    */   {
/*  35: 95 */     int size = 0;
/*  36: 97 */     if (this.m_tail < this.m_head) {
/*  37: 98 */       size = this.m_buffer.length - this.m_head + this.m_tail;
/*  38:    */     } else {
/*  39:100 */       size = this.m_tail - this.m_head;
/*  40:    */     }
/*  41:103 */     return size;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isEmpty()
/*  45:    */   {
/*  46:112 */     return size() == 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean add(Object obj)
/*  50:    */   {
/*  51:124 */     if (obj == null) {
/*  52:125 */       throw new NullPointerException("Attempted to add null object to buffer");
/*  53:    */     }
/*  54:128 */     if (size() + 1 >= this.m_buffer.length)
/*  55:    */     {
/*  56:129 */       Object[] tmp = new Object[(this.m_buffer.length - 1) * 2 + 1];
/*  57:    */       
/*  58:131 */       int j = 0;
/*  59:132 */       for (int i = this.m_head; i != this.m_tail;)
/*  60:    */       {
/*  61:133 */         tmp[j] = this.m_buffer[i];
/*  62:134 */         this.m_buffer[i] = null;
/*  63:    */         
/*  64:136 */         j++;
/*  65:137 */         i++;
/*  66:138 */         if (i == this.m_buffer.length) {
/*  67:139 */           i = 0;
/*  68:    */         }
/*  69:    */       }
/*  70:143 */       this.m_buffer = tmp;
/*  71:144 */       this.m_head = 0;
/*  72:145 */       this.m_tail = j;
/*  73:    */     }
/*  74:148 */     this.m_buffer[this.m_tail] = obj;
/*  75:149 */     this.m_tail += 1;
/*  76:150 */     if (this.m_tail >= this.m_buffer.length) {
/*  77:151 */       this.m_tail = 0;
/*  78:    */     }
/*  79:153 */     return true;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object get()
/*  83:    */   {
/*  84:163 */     if (isEmpty()) {
/*  85:164 */       throw new BufferUnderflowException("The buffer is already empty");
/*  86:    */     }
/*  87:167 */     return this.m_buffer[this.m_head];
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Object remove()
/*  91:    */   {
/*  92:177 */     if (isEmpty()) {
/*  93:178 */       throw new BufferUnderflowException("The buffer is already empty");
/*  94:    */     }
/*  95:181 */     Object element = this.m_buffer[this.m_head];
/*  96:183 */     if (null != element)
/*  97:    */     {
/*  98:184 */       this.m_buffer[this.m_head] = null;
/*  99:    */       
/* 100:186 */       this.m_head += 1;
/* 101:187 */       if (this.m_head >= this.m_buffer.length) {
/* 102:188 */         this.m_head = 0;
/* 103:    */       }
/* 104:    */     }
/* 105:192 */     return element;
/* 106:    */   }
/* 107:    */   
/* 108:    */   private int increment(int index)
/* 109:    */   {
/* 110:    */     
/* 111:203 */     if (index >= this.m_buffer.length) {
/* 112:204 */       index = 0;
/* 113:    */     }
/* 114:206 */     return index;
/* 115:    */   }
/* 116:    */   
/* 117:    */   private int decrement(int index)
/* 118:    */   {
/* 119:    */     
/* 120:217 */     if (index < 0) {
/* 121:218 */       index = this.m_buffer.length - 1;
/* 122:    */     }
/* 123:220 */     return index;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Iterator iterator()
/* 127:    */   {
/* 128:229 */     new Iterator()
/* 129:    */     {
/* 130:231 */       private int index = UnboundedFifoBuffer.this.m_head;
/* 131:232 */       private int lastReturnedIndex = -1;
/* 132:    */       
/* 133:    */       public boolean hasNext()
/* 134:    */       {
/* 135:235 */         return this.index != UnboundedFifoBuffer.this.m_tail;
/* 136:    */       }
/* 137:    */       
/* 138:    */       public Object next()
/* 139:    */       {
/* 140:240 */         if (!hasNext()) {
/* 141:241 */           throw new NoSuchElementException();
/* 142:    */         }
/* 143:242 */         this.lastReturnedIndex = this.index;
/* 144:243 */         this.index = UnboundedFifoBuffer.this.increment(this.index);
/* 145:244 */         return UnboundedFifoBuffer.this.m_buffer[this.lastReturnedIndex];
/* 146:    */       }
/* 147:    */       
/* 148:    */       public void remove()
/* 149:    */       {
/* 150:248 */         if (this.lastReturnedIndex == -1) {
/* 151:249 */           throw new IllegalStateException();
/* 152:    */         }
/* 153:252 */         if (this.lastReturnedIndex == UnboundedFifoBuffer.this.m_head)
/* 154:    */         {
/* 155:253 */           UnboundedFifoBuffer.this.remove();
/* 156:254 */           this.lastReturnedIndex = -1;
/* 157:255 */           return;
/* 158:    */         }
/* 159:259 */         int i = UnboundedFifoBuffer.this.increment(this.lastReturnedIndex);
/* 160:260 */         while (i != UnboundedFifoBuffer.this.m_tail)
/* 161:    */         {
/* 162:261 */           UnboundedFifoBuffer.this.m_buffer[UnboundedFifoBuffer.this.decrement(i)] = UnboundedFifoBuffer.this.m_buffer[i];
/* 163:262 */           i = UnboundedFifoBuffer.this.increment(i);
/* 164:    */         }
/* 165:265 */         this.lastReturnedIndex = -1;
/* 166:266 */         UnboundedFifoBuffer.this.m_tail = UnboundedFifoBuffer.this.decrement(UnboundedFifoBuffer.this.m_tail);
/* 167:267 */         UnboundedFifoBuffer.this.m_buffer[UnboundedFifoBuffer.this.m_tail] = null;
/* 168:268 */         this.index = UnboundedFifoBuffer.this.decrement(this.index);
/* 169:    */       }
/* 170:    */     };
/* 171:    */   }
/* 172:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.UnboundedFifoBuffer
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.apache.james.mime4j.codec;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.NoSuchElementException;
/*   5:    */ 
/*   6:    */ class UnboundedFifoByteBuffer
/*   7:    */ {
/*   8:    */   protected byte[] buffer;
/*   9:    */   protected int head;
/*  10:    */   protected int tail;
/*  11:    */   
/*  12:    */   public UnboundedFifoByteBuffer()
/*  13:    */   {
/*  14: 63 */     this(32);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public UnboundedFifoByteBuffer(int initialSize)
/*  18:    */   {
/*  19: 74 */     if (initialSize <= 0) {
/*  20: 75 */       throw new IllegalArgumentException("The size must be greater than 0");
/*  21:    */     }
/*  22: 77 */     this.buffer = new byte[initialSize + 1];
/*  23: 78 */     this.head = 0;
/*  24: 79 */     this.tail = 0;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int size()
/*  28:    */   {
/*  29: 88 */     int size = 0;
/*  30: 90 */     if (this.tail < this.head) {
/*  31: 91 */       size = this.buffer.length - this.head + this.tail;
/*  32:    */     } else {
/*  33: 93 */       size = this.tail - this.head;
/*  34:    */     }
/*  35: 96 */     return size;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isEmpty()
/*  39:    */   {
/*  40:105 */     return size() == 0;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean add(byte b)
/*  44:    */   {
/*  45:116 */     if (size() + 1 >= this.buffer.length)
/*  46:    */     {
/*  47:117 */       byte[] tmp = new byte[(this.buffer.length - 1) * 2 + 1];
/*  48:    */       
/*  49:119 */       int j = 0;
/*  50:120 */       for (int i = this.head; i != this.tail;)
/*  51:    */       {
/*  52:121 */         tmp[j] = this.buffer[i];
/*  53:122 */         this.buffer[i] = 0;
/*  54:    */         
/*  55:124 */         j++;
/*  56:125 */         i++;
/*  57:126 */         if (i == this.buffer.length) {
/*  58:127 */           i = 0;
/*  59:    */         }
/*  60:    */       }
/*  61:131 */       this.buffer = tmp;
/*  62:132 */       this.head = 0;
/*  63:133 */       this.tail = j;
/*  64:    */     }
/*  65:136 */     this.buffer[this.tail] = b;
/*  66:137 */     this.tail += 1;
/*  67:138 */     if (this.tail >= this.buffer.length) {
/*  68:139 */       this.tail = 0;
/*  69:    */     }
/*  70:141 */     return true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public byte get()
/*  74:    */   {
/*  75:151 */     if (isEmpty()) {
/*  76:152 */       throw new IllegalStateException("The buffer is already empty");
/*  77:    */     }
/*  78:155 */     return this.buffer[this.head];
/*  79:    */   }
/*  80:    */   
/*  81:    */   public byte remove()
/*  82:    */   {
/*  83:165 */     if (isEmpty()) {
/*  84:166 */       throw new IllegalStateException("The buffer is already empty");
/*  85:    */     }
/*  86:169 */     byte element = this.buffer[this.head];
/*  87:    */     
/*  88:171 */     this.head += 1;
/*  89:172 */     if (this.head >= this.buffer.length) {
/*  90:173 */       this.head = 0;
/*  91:    */     }
/*  92:176 */     return element;
/*  93:    */   }
/*  94:    */   
/*  95:    */   private int increment(int index)
/*  96:    */   {
/*  97:    */     
/*  98:187 */     if (index >= this.buffer.length) {
/*  99:188 */       index = 0;
/* 100:    */     }
/* 101:190 */     return index;
/* 102:    */   }
/* 103:    */   
/* 104:    */   private int decrement(int index)
/* 105:    */   {
/* 106:    */     
/* 107:201 */     if (index < 0) {
/* 108:202 */       index = this.buffer.length - 1;
/* 109:    */     }
/* 110:204 */     return index;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Iterator<Byte> iterator()
/* 114:    */   {
/* 115:213 */     new Iterator()
/* 116:    */     {
/* 117:215 */       private int index = UnboundedFifoByteBuffer.this.head;
/* 118:216 */       private int lastReturnedIndex = -1;
/* 119:    */       
/* 120:    */       public boolean hasNext()
/* 121:    */       {
/* 122:219 */         return this.index != UnboundedFifoByteBuffer.this.tail;
/* 123:    */       }
/* 124:    */       
/* 125:    */       public Byte next()
/* 126:    */       {
/* 127:224 */         if (!hasNext()) {
/* 128:225 */           throw new NoSuchElementException();
/* 129:    */         }
/* 130:227 */         this.lastReturnedIndex = this.index;
/* 131:228 */         this.index = UnboundedFifoByteBuffer.this.increment(this.index);
/* 132:229 */         return new Byte(UnboundedFifoByteBuffer.this.buffer[this.lastReturnedIndex]);
/* 133:    */       }
/* 134:    */       
/* 135:    */       public void remove()
/* 136:    */       {
/* 137:233 */         if (this.lastReturnedIndex == -1) {
/* 138:234 */           throw new IllegalStateException();
/* 139:    */         }
/* 140:238 */         if (this.lastReturnedIndex == UnboundedFifoByteBuffer.this.head)
/* 141:    */         {
/* 142:239 */           UnboundedFifoByteBuffer.this.remove();
/* 143:240 */           this.lastReturnedIndex = -1;
/* 144:241 */           return;
/* 145:    */         }
/* 146:245 */         int i = this.lastReturnedIndex + 1;
/* 147:246 */         while (i != UnboundedFifoByteBuffer.this.tail) {
/* 148:247 */           if (i >= UnboundedFifoByteBuffer.this.buffer.length)
/* 149:    */           {
/* 150:248 */             UnboundedFifoByteBuffer.this.buffer[(i - 1)] = UnboundedFifoByteBuffer.this.buffer[0];
/* 151:249 */             i = 0;
/* 152:    */           }
/* 153:    */           else
/* 154:    */           {
/* 155:251 */             UnboundedFifoByteBuffer.this.buffer[(i - 1)] = UnboundedFifoByteBuffer.this.buffer[i];
/* 156:252 */             i++;
/* 157:    */           }
/* 158:    */         }
/* 159:256 */         this.lastReturnedIndex = -1;
/* 160:257 */         UnboundedFifoByteBuffer.this.tail = UnboundedFifoByteBuffer.this.decrement(UnboundedFifoByteBuffer.this.tail);
/* 161:258 */         UnboundedFifoByteBuffer.this.buffer[UnboundedFifoByteBuffer.this.tail] = 0;
/* 162:259 */         this.index = UnboundedFifoByteBuffer.this.decrement(this.index);
/* 163:    */       }
/* 164:    */     };
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.UnboundedFifoByteBuffer
 * JD-Core Version:    0.7.0.1
 */
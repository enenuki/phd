/*   1:    */ package org.apache.james.mime4j.util;
/*   2:    */ 
/*   3:    */ public final class ByteArrayBuffer
/*   4:    */   implements ByteSequence
/*   5:    */ {
/*   6:    */   private byte[] buffer;
/*   7:    */   private int len;
/*   8:    */   
/*   9:    */   public ByteArrayBuffer(int capacity)
/*  10:    */   {
/*  11: 33 */     if (capacity < 0) {
/*  12: 34 */       throw new IllegalArgumentException("Buffer capacity may not be negative");
/*  13:    */     }
/*  14: 36 */     this.buffer = new byte[capacity];
/*  15:    */   }
/*  16:    */   
/*  17:    */   public ByteArrayBuffer(byte[] bytes, boolean dontCopy)
/*  18:    */   {
/*  19: 40 */     this(bytes, bytes.length, dontCopy);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ByteArrayBuffer(byte[] bytes, int len, boolean dontCopy)
/*  23:    */   {
/*  24: 44 */     if (bytes == null) {
/*  25: 45 */       throw new IllegalArgumentException();
/*  26:    */     }
/*  27: 46 */     if ((len < 0) || (len > bytes.length)) {
/*  28: 47 */       throw new IllegalArgumentException();
/*  29:    */     }
/*  30: 49 */     if (dontCopy)
/*  31:    */     {
/*  32: 50 */       this.buffer = bytes;
/*  33:    */     }
/*  34:    */     else
/*  35:    */     {
/*  36: 52 */       this.buffer = new byte[len];
/*  37: 53 */       System.arraycopy(bytes, 0, this.buffer, 0, len);
/*  38:    */     }
/*  39: 56 */     this.len = len;
/*  40:    */   }
/*  41:    */   
/*  42:    */   private void expand(int newlen)
/*  43:    */   {
/*  44: 60 */     byte[] newbuffer = new byte[Math.max(this.buffer.length << 1, newlen)];
/*  45: 61 */     System.arraycopy(this.buffer, 0, newbuffer, 0, this.len);
/*  46: 62 */     this.buffer = newbuffer;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void append(byte[] b, int off, int len)
/*  50:    */   {
/*  51: 66 */     if (b == null) {
/*  52: 67 */       return;
/*  53:    */     }
/*  54: 69 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len < 0) || (off + len > b.length)) {
/*  55: 71 */       throw new IndexOutOfBoundsException();
/*  56:    */     }
/*  57: 73 */     if (len == 0) {
/*  58: 74 */       return;
/*  59:    */     }
/*  60: 76 */     int newlen = this.len + len;
/*  61: 77 */     if (newlen > this.buffer.length) {
/*  62: 78 */       expand(newlen);
/*  63:    */     }
/*  64: 80 */     System.arraycopy(b, off, this.buffer, this.len, len);
/*  65: 81 */     this.len = newlen;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void append(int b)
/*  69:    */   {
/*  70: 85 */     int newlen = this.len + 1;
/*  71: 86 */     if (newlen > this.buffer.length) {
/*  72: 87 */       expand(newlen);
/*  73:    */     }
/*  74: 89 */     this.buffer[this.len] = ((byte)b);
/*  75: 90 */     this.len = newlen;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void clear()
/*  79:    */   {
/*  80: 94 */     this.len = 0;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public byte[] toByteArray()
/*  84:    */   {
/*  85: 98 */     byte[] b = new byte[this.len];
/*  86: 99 */     if (this.len > 0) {
/*  87:100 */       System.arraycopy(this.buffer, 0, b, 0, this.len);
/*  88:    */     }
/*  89:102 */     return b;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public byte byteAt(int i)
/*  93:    */   {
/*  94:106 */     if ((i < 0) || (i >= this.len)) {
/*  95:107 */       throw new IndexOutOfBoundsException();
/*  96:    */     }
/*  97:109 */     return this.buffer[i];
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int capacity()
/* 101:    */   {
/* 102:113 */     return this.buffer.length;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int length()
/* 106:    */   {
/* 107:117 */     return this.len;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public byte[] buffer()
/* 111:    */   {
/* 112:121 */     return this.buffer;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int indexOf(byte b)
/* 116:    */   {
/* 117:125 */     return indexOf(b, 0, this.len);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int indexOf(byte b, int beginIndex, int endIndex)
/* 121:    */   {
/* 122:129 */     if (beginIndex < 0) {
/* 123:130 */       beginIndex = 0;
/* 124:    */     }
/* 125:132 */     if (endIndex > this.len) {
/* 126:133 */       endIndex = this.len;
/* 127:    */     }
/* 128:135 */     if (beginIndex > endIndex) {
/* 129:136 */       return -1;
/* 130:    */     }
/* 131:138 */     for (int i = beginIndex; i < endIndex; i++) {
/* 132:139 */       if (this.buffer[i] == b) {
/* 133:140 */         return i;
/* 134:    */       }
/* 135:    */     }
/* 136:143 */     return -1;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setLength(int len)
/* 140:    */   {
/* 141:147 */     if ((len < 0) || (len > this.buffer.length)) {
/* 142:148 */       throw new IndexOutOfBoundsException();
/* 143:    */     }
/* 144:150 */     this.len = len;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isEmpty()
/* 148:    */   {
/* 149:154 */     return this.len == 0;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean isFull()
/* 153:    */   {
/* 154:158 */     return this.len == this.buffer.length;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String toString()
/* 158:    */   {
/* 159:163 */     return new String(toByteArray());
/* 160:    */   }
/* 161:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.util.ByteArrayBuffer
 * JD-Core Version:    0.7.0.1
 */
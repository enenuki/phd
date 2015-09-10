/*   1:    */ package org.apache.http.util;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public final class ByteArrayBuffer
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = 4359112959524048036L;
/*   9:    */   private byte[] buffer;
/*  10:    */   private int len;
/*  11:    */   
/*  12:    */   public ByteArrayBuffer(int capacity)
/*  13:    */   {
/*  14: 52 */     if (capacity < 0) {
/*  15: 53 */       throw new IllegalArgumentException("Buffer capacity may not be negative");
/*  16:    */     }
/*  17: 55 */     this.buffer = new byte[capacity];
/*  18:    */   }
/*  19:    */   
/*  20:    */   private void expand(int newlen)
/*  21:    */   {
/*  22: 59 */     byte[] newbuffer = new byte[Math.max(this.buffer.length << 1, newlen)];
/*  23: 60 */     System.arraycopy(this.buffer, 0, newbuffer, 0, this.len);
/*  24: 61 */     this.buffer = newbuffer;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void append(byte[] b, int off, int len)
/*  28:    */   {
/*  29: 77 */     if (b == null) {
/*  30: 78 */       return;
/*  31:    */     }
/*  32: 80 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len < 0) || (off + len > b.length)) {
/*  33: 82 */       throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.length);
/*  34:    */     }
/*  35: 84 */     if (len == 0) {
/*  36: 85 */       return;
/*  37:    */     }
/*  38: 87 */     int newlen = this.len + len;
/*  39: 88 */     if (newlen > this.buffer.length) {
/*  40: 89 */       expand(newlen);
/*  41:    */     }
/*  42: 91 */     System.arraycopy(b, off, this.buffer, this.len, len);
/*  43: 92 */     this.len = newlen;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void append(int b)
/*  47:    */   {
/*  48:102 */     int newlen = this.len + 1;
/*  49:103 */     if (newlen > this.buffer.length) {
/*  50:104 */       expand(newlen);
/*  51:    */     }
/*  52:106 */     this.buffer[this.len] = ((byte)b);
/*  53:107 */     this.len = newlen;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void append(char[] b, int off, int len)
/*  57:    */   {
/*  58:125 */     if (b == null) {
/*  59:126 */       return;
/*  60:    */     }
/*  61:128 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len < 0) || (off + len > b.length)) {
/*  62:130 */       throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.length);
/*  63:    */     }
/*  64:132 */     if (len == 0) {
/*  65:133 */       return;
/*  66:    */     }
/*  67:135 */     int oldlen = this.len;
/*  68:136 */     int newlen = oldlen + len;
/*  69:137 */     if (newlen > this.buffer.length) {
/*  70:138 */       expand(newlen);
/*  71:    */     }
/*  72:140 */     int i1 = off;
/*  73:140 */     for (int i2 = oldlen; i2 < newlen; i2++)
/*  74:    */     {
/*  75:141 */       this.buffer[i2] = ((byte)b[i1]);i1++;
/*  76:    */     }
/*  77:143 */     this.len = newlen;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void append(CharArrayBuffer b, int off, int len)
/*  81:    */   {
/*  82:162 */     if (b == null) {
/*  83:163 */       return;
/*  84:    */     }
/*  85:165 */     append(b.buffer(), off, len);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void clear()
/*  89:    */   {
/*  90:172 */     this.len = 0;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public byte[] toByteArray()
/*  94:    */   {
/*  95:181 */     byte[] b = new byte[this.len];
/*  96:182 */     if (this.len > 0) {
/*  97:183 */       System.arraycopy(this.buffer, 0, b, 0, this.len);
/*  98:    */     }
/*  99:185 */     return b;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int byteAt(int i)
/* 103:    */   {
/* 104:199 */     return this.buffer[i];
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int capacity()
/* 108:    */   {
/* 109:210 */     return this.buffer.length;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int length()
/* 113:    */   {
/* 114:219 */     return this.len;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void ensureCapacity(int required)
/* 118:    */   {
/* 119:233 */     if (required <= 0) {
/* 120:234 */       return;
/* 121:    */     }
/* 122:236 */     int available = this.buffer.length - this.len;
/* 123:237 */     if (required > available) {
/* 124:238 */       expand(this.len + required);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public byte[] buffer()
/* 129:    */   {
/* 130:248 */     return this.buffer;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setLength(int len)
/* 134:    */   {
/* 135:262 */     if ((len < 0) || (len > this.buffer.length)) {
/* 136:263 */       throw new IndexOutOfBoundsException("len: " + len + " < 0 or > buffer len: " + this.buffer.length);
/* 137:    */     }
/* 138:265 */     this.len = len;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isEmpty()
/* 142:    */   {
/* 143:275 */     return this.len == 0;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isFull()
/* 147:    */   {
/* 148:285 */     return this.len == this.buffer.length;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int indexOf(byte b, int beginIndex, int endIndex)
/* 152:    */   {
/* 153:312 */     if (beginIndex < 0) {
/* 154:313 */       beginIndex = 0;
/* 155:    */     }
/* 156:315 */     if (endIndex > this.len) {
/* 157:316 */       endIndex = this.len;
/* 158:    */     }
/* 159:318 */     if (beginIndex > endIndex) {
/* 160:319 */       return -1;
/* 161:    */     }
/* 162:321 */     for (int i = beginIndex; i < endIndex; i++) {
/* 163:322 */       if (this.buffer[i] == b) {
/* 164:323 */         return i;
/* 165:    */       }
/* 166:    */     }
/* 167:326 */     return -1;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int indexOf(byte b)
/* 171:    */   {
/* 172:342 */     return indexOf(b, 0, this.len);
/* 173:    */   }
/* 174:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.util.ByteArrayBuffer
 * JD-Core Version:    0.7.0.1
 */
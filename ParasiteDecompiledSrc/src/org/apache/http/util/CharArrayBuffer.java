/*   1:    */ package org.apache.http.util;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.http.protocol.HTTP;
/*   5:    */ 
/*   6:    */ public final class CharArrayBuffer
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = -6208952725094867135L;
/*  10:    */   private char[] buffer;
/*  11:    */   private int len;
/*  12:    */   
/*  13:    */   public CharArrayBuffer(int capacity)
/*  14:    */   {
/*  15: 54 */     if (capacity < 0) {
/*  16: 55 */       throw new IllegalArgumentException("Buffer capacity may not be negative");
/*  17:    */     }
/*  18: 57 */     this.buffer = new char[capacity];
/*  19:    */   }
/*  20:    */   
/*  21:    */   private void expand(int newlen)
/*  22:    */   {
/*  23: 61 */     char[] newbuffer = new char[Math.max(this.buffer.length << 1, newlen)];
/*  24: 62 */     System.arraycopy(this.buffer, 0, newbuffer, 0, this.len);
/*  25: 63 */     this.buffer = newbuffer;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void append(char[] b, int off, int len)
/*  29:    */   {
/*  30: 79 */     if (b == null) {
/*  31: 80 */       return;
/*  32:    */     }
/*  33: 82 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len < 0) || (off + len > b.length)) {
/*  34: 84 */       throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.length);
/*  35:    */     }
/*  36: 86 */     if (len == 0) {
/*  37: 87 */       return;
/*  38:    */     }
/*  39: 89 */     int newlen = this.len + len;
/*  40: 90 */     if (newlen > this.buffer.length) {
/*  41: 91 */       expand(newlen);
/*  42:    */     }
/*  43: 93 */     System.arraycopy(b, off, this.buffer, this.len, len);
/*  44: 94 */     this.len = newlen;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void append(String str)
/*  48:    */   {
/*  49:104 */     if (str == null) {
/*  50:105 */       str = "null";
/*  51:    */     }
/*  52:107 */     int strlen = str.length();
/*  53:108 */     int newlen = this.len + strlen;
/*  54:109 */     if (newlen > this.buffer.length) {
/*  55:110 */       expand(newlen);
/*  56:    */     }
/*  57:112 */     str.getChars(0, strlen, this.buffer, this.len);
/*  58:113 */     this.len = newlen;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void append(CharArrayBuffer b, int off, int len)
/*  62:    */   {
/*  63:130 */     if (b == null) {
/*  64:131 */       return;
/*  65:    */     }
/*  66:133 */     append(b.buffer, off, len);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void append(CharArrayBuffer b)
/*  70:    */   {
/*  71:144 */     if (b == null) {
/*  72:145 */       return;
/*  73:    */     }
/*  74:147 */     append(b.buffer, 0, b.len);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void append(char ch)
/*  78:    */   {
/*  79:157 */     int newlen = this.len + 1;
/*  80:158 */     if (newlen > this.buffer.length) {
/*  81:159 */       expand(newlen);
/*  82:    */     }
/*  83:161 */     this.buffer[this.len] = ch;
/*  84:162 */     this.len = newlen;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void append(byte[] b, int off, int len)
/*  88:    */   {
/*  89:180 */     if (b == null) {
/*  90:181 */       return;
/*  91:    */     }
/*  92:183 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len < 0) || (off + len > b.length)) {
/*  93:185 */       throw new IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.length);
/*  94:    */     }
/*  95:187 */     if (len == 0) {
/*  96:188 */       return;
/*  97:    */     }
/*  98:190 */     int oldlen = this.len;
/*  99:191 */     int newlen = oldlen + len;
/* 100:192 */     if (newlen > this.buffer.length) {
/* 101:193 */       expand(newlen);
/* 102:    */     }
/* 103:195 */     int i1 = off;
/* 104:195 */     for (int i2 = oldlen; i2 < newlen; i2++)
/* 105:    */     {
/* 106:196 */       this.buffer[i2] = ((char)(b[i1] & 0xFF));i1++;
/* 107:    */     }
/* 108:198 */     this.len = newlen;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void append(ByteArrayBuffer b, int off, int len)
/* 112:    */   {
/* 113:216 */     if (b == null) {
/* 114:217 */       return;
/* 115:    */     }
/* 116:219 */     append(b.buffer(), off, len);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void append(Object obj)
/* 120:    */   {
/* 121:230 */     append(String.valueOf(obj));
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void clear()
/* 125:    */   {
/* 126:237 */     this.len = 0;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public char[] toCharArray()
/* 130:    */   {
/* 131:246 */     char[] b = new char[this.len];
/* 132:247 */     if (this.len > 0) {
/* 133:248 */       System.arraycopy(this.buffer, 0, b, 0, this.len);
/* 134:    */     }
/* 135:250 */     return b;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public char charAt(int i)
/* 139:    */   {
/* 140:264 */     return this.buffer[i];
/* 141:    */   }
/* 142:    */   
/* 143:    */   public char[] buffer()
/* 144:    */   {
/* 145:273 */     return this.buffer;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int capacity()
/* 149:    */   {
/* 150:284 */     return this.buffer.length;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public int length()
/* 154:    */   {
/* 155:293 */     return this.len;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void ensureCapacity(int required)
/* 159:    */   {
/* 160:305 */     if (required <= 0) {
/* 161:306 */       return;
/* 162:    */     }
/* 163:308 */     int available = this.buffer.length - this.len;
/* 164:309 */     if (required > available) {
/* 165:310 */       expand(this.len + required);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setLength(int len)
/* 170:    */   {
/* 171:325 */     if ((len < 0) || (len > this.buffer.length)) {
/* 172:326 */       throw new IndexOutOfBoundsException("len: " + len + " < 0 or > buffer len: " + this.buffer.length);
/* 173:    */     }
/* 174:328 */     this.len = len;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean isEmpty()
/* 178:    */   {
/* 179:338 */     return this.len == 0;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean isFull()
/* 183:    */   {
/* 184:348 */     return this.len == this.buffer.length;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int indexOf(int ch, int beginIndex, int endIndex)
/* 188:    */   {
/* 189:373 */     if (beginIndex < 0) {
/* 190:374 */       beginIndex = 0;
/* 191:    */     }
/* 192:376 */     if (endIndex > this.len) {
/* 193:377 */       endIndex = this.len;
/* 194:    */     }
/* 195:379 */     if (beginIndex > endIndex) {
/* 196:380 */       return -1;
/* 197:    */     }
/* 198:382 */     for (int i = beginIndex; i < endIndex; i++) {
/* 199:383 */       if (this.buffer[i] == ch) {
/* 200:384 */         return i;
/* 201:    */       }
/* 202:    */     }
/* 203:387 */     return -1;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int indexOf(int ch)
/* 207:    */   {
/* 208:401 */     return indexOf(ch, 0, this.len);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String substring(int beginIndex, int endIndex)
/* 212:    */   {
/* 213:419 */     return new String(this.buffer, beginIndex, endIndex - beginIndex);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String substringTrimmed(int beginIndex, int endIndex)
/* 217:    */   {
/* 218:439 */     if (beginIndex < 0) {
/* 219:440 */       throw new IndexOutOfBoundsException("Negative beginIndex: " + beginIndex);
/* 220:    */     }
/* 221:442 */     if (endIndex > this.len) {
/* 222:443 */       throw new IndexOutOfBoundsException("endIndex: " + endIndex + " > length: " + this.len);
/* 223:    */     }
/* 224:445 */     if (beginIndex > endIndex) {
/* 225:446 */       throw new IndexOutOfBoundsException("beginIndex: " + beginIndex + " > endIndex: " + endIndex);
/* 226:    */     }
/* 227:448 */     while ((beginIndex < endIndex) && (HTTP.isWhitespace(this.buffer[beginIndex]))) {
/* 228:449 */       beginIndex++;
/* 229:    */     }
/* 230:451 */     while ((endIndex > beginIndex) && (HTTP.isWhitespace(this.buffer[(endIndex - 1)]))) {
/* 231:452 */       endIndex--;
/* 232:    */     }
/* 233:454 */     return new String(this.buffer, beginIndex, endIndex - beginIndex);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public String toString()
/* 237:    */   {
/* 238:458 */     return new String(this.buffer, 0, this.len);
/* 239:    */   }
/* 240:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.util.CharArrayBuffer
 * JD-Core Version:    0.7.0.1
 */
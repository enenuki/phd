/*   1:    */ package org.apache.james.mime4j.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.james.mime4j.util.ByteArrayBuffer;
/*   6:    */ 
/*   7:    */ public class BufferedLineReaderInputStream
/*   8:    */   extends LineReaderInputStream
/*   9:    */ {
/*  10:    */   private boolean truncated;
/*  11:    */   private byte[] buffer;
/*  12:    */   private int bufpos;
/*  13:    */   private int buflen;
/*  14:    */   private final int maxLineLen;
/*  15:    */   
/*  16:    */   public BufferedLineReaderInputStream(InputStream instream, int buffersize, int maxLineLen)
/*  17:    */   {
/*  18: 46 */     super(instream);
/*  19: 47 */     if (instream == null) {
/*  20: 48 */       throw new IllegalArgumentException("Input stream may not be null");
/*  21:    */     }
/*  22: 50 */     if (buffersize <= 0) {
/*  23: 51 */       throw new IllegalArgumentException("Buffer size may not be negative or zero");
/*  24:    */     }
/*  25: 53 */     this.buffer = new byte[buffersize];
/*  26: 54 */     this.bufpos = 0;
/*  27: 55 */     this.buflen = 0;
/*  28: 56 */     this.maxLineLen = maxLineLen;
/*  29: 57 */     this.truncated = false;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public BufferedLineReaderInputStream(InputStream instream, int buffersize)
/*  33:    */   {
/*  34: 63 */     this(instream, buffersize, -1);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private void expand(int newlen)
/*  38:    */   {
/*  39: 67 */     byte[] newbuffer = new byte[newlen];
/*  40: 68 */     int len = this.buflen - this.bufpos;
/*  41: 69 */     if (len > 0) {
/*  42: 70 */       System.arraycopy(this.buffer, this.bufpos, newbuffer, this.bufpos, len);
/*  43:    */     }
/*  44: 72 */     this.buffer = newbuffer;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void ensureCapacity(int len)
/*  48:    */   {
/*  49: 76 */     if (len > this.buffer.length) {
/*  50: 77 */       expand(len);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int fillBuffer()
/*  55:    */     throws IOException
/*  56:    */   {
/*  57: 83 */     if (this.bufpos > 0)
/*  58:    */     {
/*  59: 84 */       int len = this.buflen - this.bufpos;
/*  60: 85 */       if (len > 0) {
/*  61: 86 */         System.arraycopy(this.buffer, this.bufpos, this.buffer, 0, len);
/*  62:    */       }
/*  63: 88 */       this.bufpos = 0;
/*  64: 89 */       this.buflen = len;
/*  65:    */     }
/*  66: 92 */     int off = this.buflen;
/*  67: 93 */     int len = this.buffer.length - off;
/*  68: 94 */     int l = this.in.read(this.buffer, off, len);
/*  69: 95 */     if (l == -1) {
/*  70: 96 */       return -1;
/*  71:    */     }
/*  72: 98 */     this.buflen = (off + l);
/*  73: 99 */     return l;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean hasBufferedData()
/*  77:    */   {
/*  78:104 */     return this.bufpos < this.buflen;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void truncate()
/*  82:    */   {
/*  83:108 */     clear();
/*  84:109 */     this.truncated = true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int read()
/*  88:    */     throws IOException
/*  89:    */   {
/*  90:114 */     if (this.truncated) {
/*  91:115 */       return -1;
/*  92:    */     }
/*  93:117 */     int noRead = 0;
/*  94:118 */     while (!hasBufferedData())
/*  95:    */     {
/*  96:119 */       noRead = fillBuffer();
/*  97:120 */       if (noRead == -1) {
/*  98:121 */         return -1;
/*  99:    */       }
/* 100:    */     }
/* 101:124 */     return this.buffer[(this.bufpos++)] & 0xFF;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int read(byte[] b, int off, int len)
/* 105:    */     throws IOException
/* 106:    */   {
/* 107:129 */     if (this.truncated) {
/* 108:130 */       return -1;
/* 109:    */     }
/* 110:132 */     if (b == null) {
/* 111:133 */       return 0;
/* 112:    */     }
/* 113:135 */     int noRead = 0;
/* 114:136 */     while (!hasBufferedData())
/* 115:    */     {
/* 116:137 */       noRead = fillBuffer();
/* 117:138 */       if (noRead == -1) {
/* 118:139 */         return -1;
/* 119:    */       }
/* 120:    */     }
/* 121:142 */     int chunk = this.buflen - this.bufpos;
/* 122:143 */     if (chunk > len) {
/* 123:144 */       chunk = len;
/* 124:    */     }
/* 125:146 */     System.arraycopy(this.buffer, this.bufpos, b, off, chunk);
/* 126:147 */     this.bufpos += chunk;
/* 127:148 */     return chunk;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int read(byte[] b)
/* 131:    */     throws IOException
/* 132:    */   {
/* 133:153 */     if (this.truncated) {
/* 134:154 */       return -1;
/* 135:    */     }
/* 136:156 */     if (b == null) {
/* 137:157 */       return 0;
/* 138:    */     }
/* 139:159 */     return read(b, 0, b.length);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean markSupported()
/* 143:    */   {
/* 144:164 */     return false;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int readLine(ByteArrayBuffer dst)
/* 148:    */     throws IOException
/* 149:    */   {
/* 150:170 */     if (dst == null) {
/* 151:171 */       throw new IllegalArgumentException("Buffer may not be null");
/* 152:    */     }
/* 153:173 */     if (this.truncated) {
/* 154:174 */       return -1;
/* 155:    */     }
/* 156:176 */     int total = 0;
/* 157:177 */     boolean found = false;
/* 158:178 */     int bytesRead = 0;
/* 159:179 */     while (!found)
/* 160:    */     {
/* 161:180 */       if (!hasBufferedData())
/* 162:    */       {
/* 163:181 */         bytesRead = fillBuffer();
/* 164:182 */         if (bytesRead == -1) {
/* 165:    */           break;
/* 166:    */         }
/* 167:    */       }
/* 168:186 */       int i = indexOf((byte)10);
/* 169:    */       int chunk;
/* 170:    */       int chunk;
/* 171:188 */       if (i != -1)
/* 172:    */       {
/* 173:189 */         found = true;
/* 174:190 */         chunk = i + 1 - pos();
/* 175:    */       }
/* 176:    */       else
/* 177:    */       {
/* 178:192 */         chunk = length();
/* 179:    */       }
/* 180:194 */       if (chunk > 0)
/* 181:    */       {
/* 182:195 */         dst.append(buf(), pos(), chunk);
/* 183:196 */         skip(chunk);
/* 184:197 */         total += chunk;
/* 185:    */       }
/* 186:199 */       if ((this.maxLineLen > 0) && (dst.length() >= this.maxLineLen)) {
/* 187:200 */         throw new MaxLineLimitException("Maximum line length limit exceeded");
/* 188:    */       }
/* 189:    */     }
/* 190:203 */     if ((total == 0) && (bytesRead == -1)) {
/* 191:204 */       return -1;
/* 192:    */     }
/* 193:206 */     return total;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int indexOf(byte[] pattern, int off, int len)
/* 197:    */   {
/* 198:219 */     if (pattern == null) {
/* 199:220 */       throw new IllegalArgumentException("Pattern may not be null");
/* 200:    */     }
/* 201:222 */     if ((off < this.bufpos) || (len < 0) || (off + len > this.buflen)) {
/* 202:223 */       throw new IndexOutOfBoundsException();
/* 203:    */     }
/* 204:225 */     if (len < pattern.length) {
/* 205:226 */       return -1;
/* 206:    */     }
/* 207:229 */     int[] shiftTable = new int[256];
/* 208:230 */     for (int i = 0; i < shiftTable.length; i++) {
/* 209:231 */       shiftTable[i] = (pattern.length + 1);
/* 210:    */     }
/* 211:233 */     for (int i = 0; i < pattern.length; i++)
/* 212:    */     {
/* 213:234 */       int x = pattern[i] & 0xFF;
/* 214:235 */       shiftTable[x] = (pattern.length - i);
/* 215:    */     }
/* 216:238 */     int j = 0;
/* 217:239 */     while (j <= len - pattern.length)
/* 218:    */     {
/* 219:240 */       int cur = off + j;
/* 220:241 */       boolean match = true;
/* 221:242 */       for (int i = 0; i < pattern.length; i++) {
/* 222:243 */         if (this.buffer[(cur + i)] != pattern[i])
/* 223:    */         {
/* 224:244 */           match = false;
/* 225:245 */           break;
/* 226:    */         }
/* 227:    */       }
/* 228:248 */       if (match) {
/* 229:249 */         return cur;
/* 230:    */       }
/* 231:252 */       int pos = cur + pattern.length;
/* 232:253 */       if (pos >= this.buffer.length) {
/* 233:    */         break;
/* 234:    */       }
/* 235:256 */       int x = this.buffer[pos] & 0xFF;
/* 236:257 */       j += shiftTable[x];
/* 237:    */     }
/* 238:259 */     return -1;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public int indexOf(byte[] pattern)
/* 242:    */   {
/* 243:271 */     return indexOf(pattern, this.bufpos, this.buflen - this.bufpos);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public int indexOf(byte b, int off, int len)
/* 247:    */   {
/* 248:275 */     if ((off < this.bufpos) || (len < 0) || (off + len > this.buflen)) {
/* 249:276 */       throw new IndexOutOfBoundsException();
/* 250:    */     }
/* 251:278 */     for (int i = off; i < off + len; i++) {
/* 252:279 */       if (this.buffer[i] == b) {
/* 253:280 */         return i;
/* 254:    */       }
/* 255:    */     }
/* 256:283 */     return -1;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public int indexOf(byte b)
/* 260:    */   {
/* 261:287 */     return indexOf(b, this.bufpos, this.buflen - this.bufpos);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public byte charAt(int pos)
/* 265:    */   {
/* 266:291 */     if ((pos < this.bufpos) || (pos > this.buflen)) {
/* 267:292 */       throw new IndexOutOfBoundsException();
/* 268:    */     }
/* 269:294 */     return this.buffer[pos];
/* 270:    */   }
/* 271:    */   
/* 272:    */   public byte[] buf()
/* 273:    */   {
/* 274:298 */     return this.buffer;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public int pos()
/* 278:    */   {
/* 279:302 */     return this.bufpos;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public int limit()
/* 283:    */   {
/* 284:306 */     return this.buflen;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public int length()
/* 288:    */   {
/* 289:310 */     return this.buflen - this.bufpos;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public int capacity()
/* 293:    */   {
/* 294:314 */     return this.buffer.length;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public int skip(int n)
/* 298:    */   {
/* 299:318 */     int chunk = Math.min(n, this.buflen - this.bufpos);
/* 300:319 */     this.bufpos += chunk;
/* 301:320 */     return chunk;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void clear()
/* 305:    */   {
/* 306:324 */     this.bufpos = 0;
/* 307:325 */     this.buflen = 0;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public String toString()
/* 311:    */   {
/* 312:330 */     StringBuilder buffer = new StringBuilder();
/* 313:331 */     buffer.append("[pos: ");
/* 314:332 */     buffer.append(this.bufpos);
/* 315:333 */     buffer.append("]");
/* 316:334 */     buffer.append("[limit: ");
/* 317:335 */     buffer.append(this.buflen);
/* 318:336 */     buffer.append("]");
/* 319:337 */     buffer.append("[");
/* 320:338 */     for (int i = this.bufpos; i < this.buflen; i++) {
/* 321:339 */       buffer.append((char)this.buffer[i]);
/* 322:    */     }
/* 323:341 */     buffer.append("]");
/* 324:342 */     return buffer.toString();
/* 325:    */   }
/* 326:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.io.BufferedLineReaderInputStream
 * JD-Core Version:    0.7.0.1
 */
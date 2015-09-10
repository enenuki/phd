/*   1:    */ package org.apache.http.impl.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.http.io.BufferInfo;
/*   6:    */ import org.apache.http.io.HttpTransportMetrics;
/*   7:    */ import org.apache.http.io.SessionInputBuffer;
/*   8:    */ import org.apache.http.params.HttpParams;
/*   9:    */ import org.apache.http.params.HttpProtocolParams;
/*  10:    */ import org.apache.http.util.ByteArrayBuffer;
/*  11:    */ import org.apache.http.util.CharArrayBuffer;
/*  12:    */ 
/*  13:    */ public abstract class AbstractSessionInputBuffer
/*  14:    */   implements SessionInputBuffer, BufferInfo
/*  15:    */ {
/*  16:    */   private InputStream instream;
/*  17:    */   private byte[] buffer;
/*  18:    */   private int bufferpos;
/*  19:    */   private int bufferlen;
/*  20: 69 */   private ByteArrayBuffer linebuffer = null;
/*  21: 71 */   private String charset = "US-ASCII";
/*  22: 72 */   private boolean ascii = true;
/*  23: 73 */   private int maxLineLen = -1;
/*  24: 74 */   private int minChunkLimit = 512;
/*  25:    */   private HttpTransportMetricsImpl metrics;
/*  26:    */   
/*  27:    */   protected void init(InputStream instream, int buffersize, HttpParams params)
/*  28:    */   {
/*  29: 86 */     if (instream == null) {
/*  30: 87 */       throw new IllegalArgumentException("Input stream may not be null");
/*  31:    */     }
/*  32: 89 */     if (buffersize <= 0) {
/*  33: 90 */       throw new IllegalArgumentException("Buffer size may not be negative or zero");
/*  34:    */     }
/*  35: 92 */     if (params == null) {
/*  36: 93 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  37:    */     }
/*  38: 95 */     this.instream = instream;
/*  39: 96 */     this.buffer = new byte[buffersize];
/*  40: 97 */     this.bufferpos = 0;
/*  41: 98 */     this.bufferlen = 0;
/*  42: 99 */     this.linebuffer = new ByteArrayBuffer(buffersize);
/*  43:100 */     this.charset = HttpProtocolParams.getHttpElementCharset(params);
/*  44:101 */     this.ascii = ((this.charset.equalsIgnoreCase("US-ASCII")) || (this.charset.equalsIgnoreCase("ASCII")));
/*  45:    */     
/*  46:103 */     this.maxLineLen = params.getIntParameter("http.connection.max-line-length", -1);
/*  47:104 */     this.minChunkLimit = params.getIntParameter("http.connection.min-chunk-limit", 512);
/*  48:105 */     this.metrics = createTransportMetrics();
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected HttpTransportMetricsImpl createTransportMetrics()
/*  52:    */   {
/*  53:112 */     return new HttpTransportMetricsImpl();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int capacity()
/*  57:    */   {
/*  58:119 */     return this.buffer.length;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int length()
/*  62:    */   {
/*  63:126 */     return this.bufferlen - this.bufferpos;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int available()
/*  67:    */   {
/*  68:133 */     return capacity() - length();
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected int fillBuffer()
/*  72:    */     throws IOException
/*  73:    */   {
/*  74:138 */     if (this.bufferpos > 0)
/*  75:    */     {
/*  76:139 */       int len = this.bufferlen - this.bufferpos;
/*  77:140 */       if (len > 0) {
/*  78:141 */         System.arraycopy(this.buffer, this.bufferpos, this.buffer, 0, len);
/*  79:    */       }
/*  80:143 */       this.bufferpos = 0;
/*  81:144 */       this.bufferlen = len;
/*  82:    */     }
/*  83:147 */     int off = this.bufferlen;
/*  84:148 */     int len = this.buffer.length - off;
/*  85:149 */     int l = this.instream.read(this.buffer, off, len);
/*  86:150 */     if (l == -1) {
/*  87:151 */       return -1;
/*  88:    */     }
/*  89:153 */     this.bufferlen = (off + l);
/*  90:154 */     this.metrics.incrementBytesTransferred(l);
/*  91:155 */     return l;
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected boolean hasBufferedData()
/*  95:    */   {
/*  96:160 */     return this.bufferpos < this.bufferlen;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int read()
/* 100:    */     throws IOException
/* 101:    */   {
/* 102:164 */     int noRead = 0;
/* 103:165 */     while (!hasBufferedData())
/* 104:    */     {
/* 105:166 */       noRead = fillBuffer();
/* 106:167 */       if (noRead == -1) {
/* 107:168 */         return -1;
/* 108:    */       }
/* 109:    */     }
/* 110:171 */     return this.buffer[(this.bufferpos++)] & 0xFF;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int read(byte[] b, int off, int len)
/* 114:    */     throws IOException
/* 115:    */   {
/* 116:175 */     if (b == null) {
/* 117:176 */       return 0;
/* 118:    */     }
/* 119:178 */     if (hasBufferedData())
/* 120:    */     {
/* 121:179 */       int chunk = Math.min(len, this.bufferlen - this.bufferpos);
/* 122:180 */       System.arraycopy(this.buffer, this.bufferpos, b, off, chunk);
/* 123:181 */       this.bufferpos += chunk;
/* 124:182 */       return chunk;
/* 125:    */     }
/* 126:186 */     if (len > this.minChunkLimit)
/* 127:    */     {
/* 128:187 */       int read = this.instream.read(b, off, len);
/* 129:188 */       if (read > 0) {
/* 130:189 */         this.metrics.incrementBytesTransferred(read);
/* 131:    */       }
/* 132:191 */       return read;
/* 133:    */     }
/* 134:194 */     while (!hasBufferedData())
/* 135:    */     {
/* 136:195 */       int noRead = fillBuffer();
/* 137:196 */       if (noRead == -1) {
/* 138:197 */         return -1;
/* 139:    */       }
/* 140:    */     }
/* 141:200 */     int chunk = Math.min(len, this.bufferlen - this.bufferpos);
/* 142:201 */     System.arraycopy(this.buffer, this.bufferpos, b, off, chunk);
/* 143:202 */     this.bufferpos += chunk;
/* 144:203 */     return chunk;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int read(byte[] b)
/* 148:    */     throws IOException
/* 149:    */   {
/* 150:208 */     if (b == null) {
/* 151:209 */       return 0;
/* 152:    */     }
/* 153:211 */     return read(b, 0, b.length);
/* 154:    */   }
/* 155:    */   
/* 156:    */   private int locateLF()
/* 157:    */   {
/* 158:215 */     for (int i = this.bufferpos; i < this.bufferlen; i++) {
/* 159:216 */       if (this.buffer[i] == 10) {
/* 160:217 */         return i;
/* 161:    */       }
/* 162:    */     }
/* 163:220 */     return -1;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public int readLine(CharArrayBuffer charbuffer)
/* 167:    */     throws IOException
/* 168:    */   {
/* 169:239 */     if (charbuffer == null) {
/* 170:240 */       throw new IllegalArgumentException("Char array buffer may not be null");
/* 171:    */     }
/* 172:242 */     int noRead = 0;
/* 173:243 */     boolean retry = true;
/* 174:244 */     while (retry)
/* 175:    */     {
/* 176:246 */       int i = locateLF();
/* 177:247 */       if (i != -1)
/* 178:    */       {
/* 179:249 */         if (this.linebuffer.isEmpty()) {
/* 180:251 */           return lineFromReadBuffer(charbuffer, i);
/* 181:    */         }
/* 182:253 */         retry = false;
/* 183:254 */         int len = i + 1 - this.bufferpos;
/* 184:255 */         this.linebuffer.append(this.buffer, this.bufferpos, len);
/* 185:256 */         this.bufferpos = (i + 1);
/* 186:    */       }
/* 187:    */       else
/* 188:    */       {
/* 189:259 */         if (hasBufferedData())
/* 190:    */         {
/* 191:260 */           int len = this.bufferlen - this.bufferpos;
/* 192:261 */           this.linebuffer.append(this.buffer, this.bufferpos, len);
/* 193:262 */           this.bufferpos = this.bufferlen;
/* 194:    */         }
/* 195:264 */         noRead = fillBuffer();
/* 196:265 */         if (noRead == -1) {
/* 197:266 */           retry = false;
/* 198:    */         }
/* 199:    */       }
/* 200:269 */       if ((this.maxLineLen > 0) && (this.linebuffer.length() >= this.maxLineLen)) {
/* 201:270 */         throw new IOException("Maximum line length limit exceeded");
/* 202:    */       }
/* 203:    */     }
/* 204:273 */     if ((noRead == -1) && (this.linebuffer.isEmpty())) {
/* 205:275 */       return -1;
/* 206:    */     }
/* 207:277 */     return lineFromLineBuffer(charbuffer);
/* 208:    */   }
/* 209:    */   
/* 210:    */   private int lineFromLineBuffer(CharArrayBuffer charbuffer)
/* 211:    */     throws IOException
/* 212:    */   {
/* 213:296 */     int l = this.linebuffer.length();
/* 214:297 */     if (l > 0)
/* 215:    */     {
/* 216:298 */       if (this.linebuffer.byteAt(l - 1) == 10)
/* 217:    */       {
/* 218:299 */         l--;
/* 219:300 */         this.linebuffer.setLength(l);
/* 220:    */       }
/* 221:303 */       if ((l > 0) && 
/* 222:304 */         (this.linebuffer.byteAt(l - 1) == 13))
/* 223:    */       {
/* 224:305 */         l--;
/* 225:306 */         this.linebuffer.setLength(l);
/* 226:    */       }
/* 227:    */     }
/* 228:310 */     l = this.linebuffer.length();
/* 229:311 */     if (this.ascii)
/* 230:    */     {
/* 231:312 */       charbuffer.append(this.linebuffer, 0, l);
/* 232:    */     }
/* 233:    */     else
/* 234:    */     {
/* 235:316 */       String s = new String(this.linebuffer.buffer(), 0, l, this.charset);
/* 236:317 */       l = s.length();
/* 237:318 */       charbuffer.append(s);
/* 238:    */     }
/* 239:320 */     this.linebuffer.clear();
/* 240:321 */     return l;
/* 241:    */   }
/* 242:    */   
/* 243:    */   private int lineFromReadBuffer(CharArrayBuffer charbuffer, int pos)
/* 244:    */     throws IOException
/* 245:    */   {
/* 246:326 */     int off = this.bufferpos;
/* 247:    */     
/* 248:328 */     this.bufferpos = (pos + 1);
/* 249:329 */     if ((pos > 0) && (this.buffer[(pos - 1)] == 13)) {
/* 250:331 */       pos--;
/* 251:    */     }
/* 252:333 */     int len = pos - off;
/* 253:334 */     if (this.ascii)
/* 254:    */     {
/* 255:335 */       charbuffer.append(this.buffer, off, len);
/* 256:    */     }
/* 257:    */     else
/* 258:    */     {
/* 259:339 */       String s = new String(this.buffer, off, len, this.charset);
/* 260:340 */       charbuffer.append(s);
/* 261:341 */       len = s.length();
/* 262:    */     }
/* 263:343 */     return len;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public String readLine()
/* 267:    */     throws IOException
/* 268:    */   {
/* 269:347 */     CharArrayBuffer charbuffer = new CharArrayBuffer(64);
/* 270:348 */     int l = readLine(charbuffer);
/* 271:349 */     if (l != -1) {
/* 272:350 */       return charbuffer.toString();
/* 273:    */     }
/* 274:352 */     return null;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public HttpTransportMetrics getMetrics()
/* 278:    */   {
/* 279:357 */     return this.metrics;
/* 280:    */   }
/* 281:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.AbstractSessionInputBuffer
 * JD-Core Version:    0.7.0.1
 */
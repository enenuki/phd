/*   1:    */ package org.apache.james.mime4j.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.james.mime4j.util.ByteArrayBuffer;
/*   5:    */ 
/*   6:    */ public class MimeBoundaryInputStream
/*   7:    */   extends LineReaderInputStream
/*   8:    */ {
/*   9:    */   private final byte[] boundary;
/*  10:    */   private boolean eof;
/*  11:    */   private int limit;
/*  12:    */   private boolean atBoundary;
/*  13:    */   private int boundaryLen;
/*  14:    */   private boolean lastPart;
/*  15:    */   private boolean completed;
/*  16:    */   private BufferedLineReaderInputStream buffer;
/*  17:    */   
/*  18:    */   public MimeBoundaryInputStream(BufferedLineReaderInputStream inbuffer, String boundary)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 53 */     super(inbuffer);
/*  22: 54 */     if (inbuffer.capacity() <= boundary.length()) {
/*  23: 55 */       throw new IllegalArgumentException("Boundary is too long");
/*  24:    */     }
/*  25: 57 */     this.buffer = inbuffer;
/*  26: 58 */     this.eof = false;
/*  27: 59 */     this.limit = -1;
/*  28: 60 */     this.atBoundary = false;
/*  29: 61 */     this.boundaryLen = 0;
/*  30: 62 */     this.lastPart = false;
/*  31: 63 */     this.completed = false;
/*  32:    */     
/*  33: 65 */     this.boundary = new byte[boundary.length() + 2];
/*  34: 66 */     this.boundary[0] = 45;
/*  35: 67 */     this.boundary[1] = 45;
/*  36: 68 */     for (int i = 0; i < boundary.length(); i++)
/*  37:    */     {
/*  38: 69 */       byte ch = (byte)boundary.charAt(i);
/*  39: 70 */       if ((ch == 13) || (ch == 10)) {
/*  40: 71 */         throw new IllegalArgumentException("Boundary may not contain CR or LF");
/*  41:    */       }
/*  42: 73 */       this.boundary[(i + 2)] = ch;
/*  43:    */     }
/*  44: 75 */     fillBuffer();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void close()
/*  48:    */     throws IOException
/*  49:    */   {}
/*  50:    */   
/*  51:    */   public boolean markSupported()
/*  52:    */   {
/*  53: 92 */     return false;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int read()
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:100 */     if (this.completed) {
/*  60:101 */       return -1;
/*  61:    */     }
/*  62:103 */     if ((endOfStream()) && (!hasData()))
/*  63:    */     {
/*  64:104 */       skipBoundary();
/*  65:105 */       return -1;
/*  66:    */     }
/*  67:    */     for (;;)
/*  68:    */     {
/*  69:108 */       if (hasData()) {
/*  70:109 */         return this.buffer.read();
/*  71:    */       }
/*  72:110 */       if (endOfStream())
/*  73:    */       {
/*  74:111 */         skipBoundary();
/*  75:112 */         return -1;
/*  76:    */       }
/*  77:114 */       fillBuffer();
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int read(byte[] b, int off, int len)
/*  82:    */     throws IOException
/*  83:    */   {
/*  84:120 */     if (this.completed) {
/*  85:121 */       return -1;
/*  86:    */     }
/*  87:123 */     if ((endOfStream()) && (!hasData()))
/*  88:    */     {
/*  89:124 */       skipBoundary();
/*  90:125 */       return -1;
/*  91:    */     }
/*  92:127 */     fillBuffer();
/*  93:128 */     if (!hasData()) {
/*  94:129 */       return read(b, off, len);
/*  95:    */     }
/*  96:131 */     int chunk = Math.min(len, this.limit - this.buffer.pos());
/*  97:132 */     return this.buffer.read(b, off, chunk);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int readLine(ByteArrayBuffer dst)
/* 101:    */     throws IOException
/* 102:    */   {
/* 103:137 */     if (dst == null) {
/* 104:138 */       throw new IllegalArgumentException("Destination buffer may not be null");
/* 105:    */     }
/* 106:140 */     if (this.completed) {
/* 107:141 */       return -1;
/* 108:    */     }
/* 109:143 */     if ((endOfStream()) && (!hasData()))
/* 110:    */     {
/* 111:144 */       skipBoundary();
/* 112:145 */       return -1;
/* 113:    */     }
/* 114:148 */     int total = 0;
/* 115:149 */     boolean found = false;
/* 116:150 */     int bytesRead = 0;
/* 117:151 */     while (!found)
/* 118:    */     {
/* 119:152 */       if (!hasData())
/* 120:    */       {
/* 121:153 */         bytesRead = fillBuffer();
/* 122:154 */         if ((!hasData()) && (endOfStream()))
/* 123:    */         {
/* 124:155 */           skipBoundary();
/* 125:156 */           bytesRead = -1;
/* 126:157 */           break;
/* 127:    */         }
/* 128:    */       }
/* 129:160 */       int len = this.limit - this.buffer.pos();
/* 130:161 */       int i = this.buffer.indexOf((byte)10, this.buffer.pos(), len);
/* 131:    */       int chunk;
/* 132:    */       int chunk;
/* 133:163 */       if (i != -1)
/* 134:    */       {
/* 135:164 */         found = true;
/* 136:165 */         chunk = i + 1 - this.buffer.pos();
/* 137:    */       }
/* 138:    */       else
/* 139:    */       {
/* 140:167 */         chunk = len;
/* 141:    */       }
/* 142:169 */       if (chunk > 0)
/* 143:    */       {
/* 144:170 */         dst.append(this.buffer.buf(), this.buffer.pos(), chunk);
/* 145:171 */         this.buffer.skip(chunk);
/* 146:172 */         total += chunk;
/* 147:    */       }
/* 148:    */     }
/* 149:175 */     if ((total == 0) && (bytesRead == -1)) {
/* 150:176 */       return -1;
/* 151:    */     }
/* 152:178 */     return total;
/* 153:    */   }
/* 154:    */   
/* 155:    */   private boolean endOfStream()
/* 156:    */   {
/* 157:183 */     return (this.eof) || (this.atBoundary);
/* 158:    */   }
/* 159:    */   
/* 160:    */   private boolean hasData()
/* 161:    */   {
/* 162:187 */     return (this.limit > this.buffer.pos()) && (this.limit <= this.buffer.limit());
/* 163:    */   }
/* 164:    */   
/* 165:    */   private int fillBuffer()
/* 166:    */     throws IOException
/* 167:    */   {
/* 168:191 */     if (this.eof) {
/* 169:192 */       return -1;
/* 170:    */     }
/* 171:    */     int bytesRead;
/* 172:    */     int bytesRead;
/* 173:195 */     if (!hasData()) {
/* 174:196 */       bytesRead = this.buffer.fillBuffer();
/* 175:    */     } else {
/* 176:198 */       bytesRead = 0;
/* 177:    */     }
/* 178:200 */     this.eof = (bytesRead == -1);
/* 179:    */     
/* 180:    */ 
/* 181:203 */     int i = this.buffer.indexOf(this.boundary);
/* 182:206 */     while ((i > 0) && (this.buffer.charAt(i - 1) != 10))
/* 183:    */     {
/* 184:209 */       i += this.boundary.length;
/* 185:210 */       i = this.buffer.indexOf(this.boundary, i, this.buffer.limit() - i);
/* 186:    */     }
/* 187:212 */     if (i != -1)
/* 188:    */     {
/* 189:213 */       this.limit = i;
/* 190:214 */       this.atBoundary = true;
/* 191:215 */       calculateBoundaryLen();
/* 192:    */     }
/* 193:217 */     else if (this.eof)
/* 194:    */     {
/* 195:218 */       this.limit = this.buffer.limit();
/* 196:    */     }
/* 197:    */     else
/* 198:    */     {
/* 199:220 */       this.limit = (this.buffer.limit() - (this.boundary.length + 1));
/* 200:    */     }
/* 201:224 */     return bytesRead;
/* 202:    */   }
/* 203:    */   
/* 204:    */   private void calculateBoundaryLen()
/* 205:    */     throws IOException
/* 206:    */   {
/* 207:228 */     this.boundaryLen = this.boundary.length;
/* 208:229 */     int len = this.limit - this.buffer.pos();
/* 209:230 */     if ((len > 0) && 
/* 210:231 */       (this.buffer.charAt(this.limit - 1) == 10))
/* 211:    */     {
/* 212:232 */       this.boundaryLen += 1;
/* 213:233 */       this.limit -= 1;
/* 214:    */     }
/* 215:236 */     if ((len > 1) && 
/* 216:237 */       (this.buffer.charAt(this.limit - 1) == 13))
/* 217:    */     {
/* 218:238 */       this.boundaryLen += 1;
/* 219:239 */       this.limit -= 1;
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   private void skipBoundary()
/* 224:    */     throws IOException
/* 225:    */   {
/* 226:245 */     if (!this.completed)
/* 227:    */     {
/* 228:246 */       this.completed = true;
/* 229:247 */       this.buffer.skip(this.boundaryLen);
/* 230:248 */       boolean checkForLastPart = true;
/* 231:    */       for (;;)
/* 232:    */       {
/* 233:250 */         if (this.buffer.length() > 1)
/* 234:    */         {
/* 235:251 */           int ch1 = this.buffer.charAt(this.buffer.pos());
/* 236:252 */           int ch2 = this.buffer.charAt(this.buffer.pos() + 1);
/* 237:254 */           if ((checkForLastPart) && (ch1 == 45) && (ch2 == 45))
/* 238:    */           {
/* 239:255 */             this.lastPart = true;
/* 240:256 */             this.buffer.skip(2);
/* 241:257 */             checkForLastPart = false;
/* 242:    */           }
/* 243:    */           else
/* 244:    */           {
/* 245:261 */             if ((ch1 == 13) && (ch2 == 10))
/* 246:    */             {
/* 247:262 */               this.buffer.skip(2);
/* 248:263 */               break;
/* 249:    */             }
/* 250:264 */             if (ch1 == 10)
/* 251:    */             {
/* 252:265 */               this.buffer.skip(1);
/* 253:266 */               break;
/* 254:    */             }
/* 255:269 */             this.buffer.skip(1);
/* 256:    */           }
/* 257:    */         }
/* 258:    */         else
/* 259:    */         {
/* 260:273 */           if (this.eof) {
/* 261:    */             break;
/* 262:    */           }
/* 263:276 */           fillBuffer();
/* 264:    */         }
/* 265:    */       }
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public boolean isLastPart()
/* 270:    */   {
/* 271:283 */     return this.lastPart;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean eof()
/* 275:    */   {
/* 276:287 */     return (this.eof) && (!this.buffer.hasBufferedData());
/* 277:    */   }
/* 278:    */   
/* 279:    */   public String toString()
/* 280:    */   {
/* 281:292 */     StringBuilder buffer = new StringBuilder("MimeBoundaryInputStream, boundary ");
/* 282:293 */     for (byte b : this.boundary) {
/* 283:294 */       buffer.append((char)b);
/* 284:    */     }
/* 285:296 */     return buffer.toString();
/* 286:    */   }
/* 287:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.io.MimeBoundaryInputStream
 * JD-Core Version:    0.7.0.1
 */
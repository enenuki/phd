/*   1:    */ package org.apache.james.mime4j.field.contenttype.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.Reader;
/*   7:    */ import java.io.UnsupportedEncodingException;
/*   8:    */ 
/*   9:    */ public class SimpleCharStream
/*  10:    */ {
/*  11:    */   public static final boolean staticFlag = false;
/*  12:    */   int bufsize;
/*  13:    */   int available;
/*  14:    */   int tokenBegin;
/*  15: 33 */   public int bufpos = -1;
/*  16:    */   protected int[] bufline;
/*  17:    */   protected int[] bufcolumn;
/*  18: 37 */   protected int column = 0;
/*  19: 38 */   protected int line = 1;
/*  20: 40 */   protected boolean prevCharIsCR = false;
/*  21: 41 */   protected boolean prevCharIsLF = false;
/*  22:    */   protected Reader inputStream;
/*  23:    */   protected char[] buffer;
/*  24: 46 */   protected int maxNextCharInd = 0;
/*  25: 47 */   protected int inBuf = 0;
/*  26: 48 */   protected int tabSize = 8;
/*  27:    */   
/*  28:    */   protected void setTabSize(int i)
/*  29:    */   {
/*  30: 50 */     this.tabSize = i;
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected int getTabSize(int i)
/*  34:    */   {
/*  35: 51 */     return this.tabSize;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void ExpandBuff(boolean wrapAround)
/*  39:    */   {
/*  40: 56 */     char[] newbuffer = new char[this.bufsize + 2048];
/*  41: 57 */     int[] newbufline = new int[this.bufsize + 2048];
/*  42: 58 */     int[] newbufcolumn = new int[this.bufsize + 2048];
/*  43:    */     try
/*  44:    */     {
/*  45: 62 */       if (wrapAround)
/*  46:    */       {
/*  47: 64 */         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
/*  48: 65 */         System.arraycopy(this.buffer, 0, newbuffer, this.bufsize - this.tokenBegin, this.bufpos);
/*  49:    */         
/*  50: 67 */         this.buffer = newbuffer;
/*  51:    */         
/*  52: 69 */         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
/*  53: 70 */         System.arraycopy(this.bufline, 0, newbufline, this.bufsize - this.tokenBegin, this.bufpos);
/*  54: 71 */         this.bufline = newbufline;
/*  55:    */         
/*  56: 73 */         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
/*  57: 74 */         System.arraycopy(this.bufcolumn, 0, newbufcolumn, this.bufsize - this.tokenBegin, this.bufpos);
/*  58: 75 */         this.bufcolumn = newbufcolumn;
/*  59:    */         
/*  60: 77 */         this.maxNextCharInd = (this.bufpos += this.bufsize - this.tokenBegin);
/*  61:    */       }
/*  62:    */       else
/*  63:    */       {
/*  64: 81 */         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
/*  65: 82 */         this.buffer = newbuffer;
/*  66:    */         
/*  67: 84 */         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
/*  68: 85 */         this.bufline = newbufline;
/*  69:    */         
/*  70: 87 */         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
/*  71: 88 */         this.bufcolumn = newbufcolumn;
/*  72:    */         
/*  73: 90 */         this.maxNextCharInd = (this.bufpos -= this.tokenBegin);
/*  74:    */       }
/*  75:    */     }
/*  76:    */     catch (Throwable t)
/*  77:    */     {
/*  78: 95 */       throw new Error(t.getMessage());
/*  79:    */     }
/*  80: 99 */     this.bufsize += 2048;
/*  81:100 */     this.available = this.bufsize;
/*  82:101 */     this.tokenBegin = 0;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void FillBuff()
/*  86:    */     throws IOException
/*  87:    */   {
/*  88:106 */     if (this.maxNextCharInd == this.available) {
/*  89:108 */       if (this.available == this.bufsize)
/*  90:    */       {
/*  91:110 */         if (this.tokenBegin > 2048)
/*  92:    */         {
/*  93:112 */           this.bufpos = (this.maxNextCharInd = 0);
/*  94:113 */           this.available = this.tokenBegin;
/*  95:    */         }
/*  96:115 */         else if (this.tokenBegin < 0)
/*  97:    */         {
/*  98:116 */           this.bufpos = (this.maxNextCharInd = 0);
/*  99:    */         }
/* 100:    */         else
/* 101:    */         {
/* 102:118 */           ExpandBuff(false);
/* 103:    */         }
/* 104:    */       }
/* 105:120 */       else if (this.available > this.tokenBegin) {
/* 106:121 */         this.available = this.bufsize;
/* 107:122 */       } else if (this.tokenBegin - this.available < 2048) {
/* 108:123 */         ExpandBuff(true);
/* 109:    */       } else {
/* 110:125 */         this.available = this.tokenBegin;
/* 111:    */       }
/* 112:    */     }
/* 113:    */     try
/* 114:    */     {
/* 115:    */       int i;
/* 116:130 */       if ((i = this.inputStream.read(this.buffer, this.maxNextCharInd, this.available - this.maxNextCharInd)) == -1)
/* 117:    */       {
/* 118:133 */         this.inputStream.close();
/* 119:134 */         throw new IOException();
/* 120:    */       }
/* 121:137 */       this.maxNextCharInd += i;
/* 122:138 */       return;
/* 123:    */     }
/* 124:    */     catch (IOException e)
/* 125:    */     {
/* 126:141 */       this.bufpos -= 1;
/* 127:142 */       backup(0);
/* 128:143 */       if (this.tokenBegin == -1) {
/* 129:144 */         this.tokenBegin = this.bufpos;
/* 130:    */       }
/* 131:145 */       throw e;
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public char BeginToken()
/* 136:    */     throws IOException
/* 137:    */   {
/* 138:151 */     this.tokenBegin = -1;
/* 139:152 */     char c = readChar();
/* 140:153 */     this.tokenBegin = this.bufpos;
/* 141:    */     
/* 142:155 */     return c;
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected void UpdateLineColumn(char c)
/* 146:    */   {
/* 147:160 */     this.column += 1;
/* 148:162 */     if (this.prevCharIsLF)
/* 149:    */     {
/* 150:164 */       this.prevCharIsLF = false;
/* 151:165 */       this.line += (this.column = 1);
/* 152:    */     }
/* 153:167 */     else if (this.prevCharIsCR)
/* 154:    */     {
/* 155:169 */       this.prevCharIsCR = false;
/* 156:170 */       if (c == '\n') {
/* 157:172 */         this.prevCharIsLF = true;
/* 158:    */       } else {
/* 159:175 */         this.line += (this.column = 1);
/* 160:    */       }
/* 161:    */     }
/* 162:178 */     switch (c)
/* 163:    */     {
/* 164:    */     case '\r': 
/* 165:181 */       this.prevCharIsCR = true;
/* 166:182 */       break;
/* 167:    */     case '\n': 
/* 168:184 */       this.prevCharIsLF = true;
/* 169:185 */       break;
/* 170:    */     case '\t': 
/* 171:187 */       this.column -= 1;
/* 172:188 */       this.column += this.tabSize - this.column % this.tabSize;
/* 173:189 */       break;
/* 174:    */     }
/* 175:194 */     this.bufline[this.bufpos] = this.line;
/* 176:195 */     this.bufcolumn[this.bufpos] = this.column;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public char readChar()
/* 180:    */     throws IOException
/* 181:    */   {
/* 182:200 */     if (this.inBuf > 0)
/* 183:    */     {
/* 184:202 */       this.inBuf -= 1;
/* 185:204 */       if (++this.bufpos == this.bufsize) {
/* 186:205 */         this.bufpos = 0;
/* 187:    */       }
/* 188:207 */       return this.buffer[this.bufpos];
/* 189:    */     }
/* 190:210 */     if (++this.bufpos >= this.maxNextCharInd) {
/* 191:211 */       FillBuff();
/* 192:    */     }
/* 193:213 */     char c = this.buffer[this.bufpos];
/* 194:    */     
/* 195:215 */     UpdateLineColumn(c);
/* 196:216 */     return c;
/* 197:    */   }
/* 198:    */   
/* 199:    */   /**
/* 200:    */    * @deprecated
/* 201:    */    */
/* 202:    */   public int getColumn()
/* 203:    */   {
/* 204:225 */     return this.bufcolumn[this.bufpos];
/* 205:    */   }
/* 206:    */   
/* 207:    */   /**
/* 208:    */    * @deprecated
/* 209:    */    */
/* 210:    */   public int getLine()
/* 211:    */   {
/* 212:234 */     return this.bufline[this.bufpos];
/* 213:    */   }
/* 214:    */   
/* 215:    */   public int getEndColumn()
/* 216:    */   {
/* 217:238 */     return this.bufcolumn[this.bufpos];
/* 218:    */   }
/* 219:    */   
/* 220:    */   public int getEndLine()
/* 221:    */   {
/* 222:242 */     return this.bufline[this.bufpos];
/* 223:    */   }
/* 224:    */   
/* 225:    */   public int getBeginColumn()
/* 226:    */   {
/* 227:246 */     return this.bufcolumn[this.tokenBegin];
/* 228:    */   }
/* 229:    */   
/* 230:    */   public int getBeginLine()
/* 231:    */   {
/* 232:250 */     return this.bufline[this.tokenBegin];
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void backup(int amount)
/* 236:    */   {
/* 237:255 */     this.inBuf += amount;
/* 238:256 */     if (this.bufpos -= amount < 0) {
/* 239:257 */       this.bufpos += this.bufsize;
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public SimpleCharStream(Reader dstream, int startline, int startcolumn, int buffersize)
/* 244:    */   {
/* 245:263 */     this.inputStream = dstream;
/* 246:264 */     this.line = startline;
/* 247:265 */     this.column = (startcolumn - 1);
/* 248:    */     
/* 249:267 */     this.available = (this.bufsize = buffersize);
/* 250:268 */     this.buffer = new char[buffersize];
/* 251:269 */     this.bufline = new int[buffersize];
/* 252:270 */     this.bufcolumn = new int[buffersize];
/* 253:    */   }
/* 254:    */   
/* 255:    */   public SimpleCharStream(Reader dstream, int startline, int startcolumn)
/* 256:    */   {
/* 257:276 */     this(dstream, startline, startcolumn, 4096);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public SimpleCharStream(Reader dstream)
/* 261:    */   {
/* 262:281 */     this(dstream, 1, 1, 4096);
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void ReInit(Reader dstream, int startline, int startcolumn, int buffersize)
/* 266:    */   {
/* 267:286 */     this.inputStream = dstream;
/* 268:287 */     this.line = startline;
/* 269:288 */     this.column = (startcolumn - 1);
/* 270:290 */     if ((this.buffer == null) || (buffersize != this.buffer.length))
/* 271:    */     {
/* 272:292 */       this.available = (this.bufsize = buffersize);
/* 273:293 */       this.buffer = new char[buffersize];
/* 274:294 */       this.bufline = new int[buffersize];
/* 275:295 */       this.bufcolumn = new int[buffersize];
/* 276:    */     }
/* 277:297 */     this.prevCharIsLF = (this.prevCharIsCR = 0);
/* 278:298 */     this.tokenBegin = (this.inBuf = this.maxNextCharInd = 0);
/* 279:299 */     this.bufpos = -1;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void ReInit(Reader dstream, int startline, int startcolumn)
/* 283:    */   {
/* 284:305 */     ReInit(dstream, startline, startcolumn, 4096);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void ReInit(Reader dstream)
/* 288:    */   {
/* 289:310 */     ReInit(dstream, 1, 1, 4096);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public SimpleCharStream(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
/* 293:    */     throws UnsupportedEncodingException
/* 294:    */   {
/* 295:315 */     this(encoding == null ? new InputStreamReader(dstream) : new InputStreamReader(dstream, encoding), startline, startcolumn, buffersize);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public SimpleCharStream(InputStream dstream, int startline, int startcolumn, int buffersize)
/* 299:    */   {
/* 300:321 */     this(new InputStreamReader(dstream), startline, startcolumn, buffersize);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public SimpleCharStream(InputStream dstream, String encoding, int startline, int startcolumn)
/* 304:    */     throws UnsupportedEncodingException
/* 305:    */   {
/* 306:327 */     this(dstream, encoding, startline, startcolumn, 4096);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public SimpleCharStream(InputStream dstream, int startline, int startcolumn)
/* 310:    */   {
/* 311:333 */     this(dstream, startline, startcolumn, 4096);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public SimpleCharStream(InputStream dstream, String encoding)
/* 315:    */     throws UnsupportedEncodingException
/* 316:    */   {
/* 317:338 */     this(dstream, encoding, 1, 1, 4096);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public SimpleCharStream(InputStream dstream)
/* 321:    */   {
/* 322:343 */     this(dstream, 1, 1, 4096);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
/* 326:    */     throws UnsupportedEncodingException
/* 327:    */   {
/* 328:349 */     ReInit(encoding == null ? new InputStreamReader(dstream) : new InputStreamReader(dstream, encoding), startline, startcolumn, buffersize);
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void ReInit(InputStream dstream, int startline, int startcolumn, int buffersize)
/* 332:    */   {
/* 333:355 */     ReInit(new InputStreamReader(dstream), startline, startcolumn, buffersize);
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void ReInit(InputStream dstream, String encoding)
/* 337:    */     throws UnsupportedEncodingException
/* 338:    */   {
/* 339:360 */     ReInit(dstream, encoding, 1, 1, 4096);
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void ReInit(InputStream dstream)
/* 343:    */   {
/* 344:365 */     ReInit(dstream, 1, 1, 4096);
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn)
/* 348:    */     throws UnsupportedEncodingException
/* 349:    */   {
/* 350:370 */     ReInit(dstream, encoding, startline, startcolumn, 4096);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void ReInit(InputStream dstream, int startline, int startcolumn)
/* 354:    */   {
/* 355:375 */     ReInit(dstream, startline, startcolumn, 4096);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public String GetImage()
/* 359:    */   {
/* 360:379 */     if (this.bufpos >= this.tokenBegin) {
/* 361:380 */       return new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1);
/* 362:    */     }
/* 363:382 */     return new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin) + new String(this.buffer, 0, this.bufpos + 1);
/* 364:    */   }
/* 365:    */   
/* 366:    */   public char[] GetSuffix(int len)
/* 367:    */   {
/* 368:388 */     char[] ret = new char[len];
/* 369:390 */     if (this.bufpos + 1 >= len)
/* 370:    */     {
/* 371:391 */       System.arraycopy(this.buffer, this.bufpos - len + 1, ret, 0, len);
/* 372:    */     }
/* 373:    */     else
/* 374:    */     {
/* 375:394 */       System.arraycopy(this.buffer, this.bufsize - (len - this.bufpos - 1), ret, 0, len - this.bufpos - 1);
/* 376:    */       
/* 377:396 */       System.arraycopy(this.buffer, 0, ret, len - this.bufpos - 1, this.bufpos + 1);
/* 378:    */     }
/* 379:399 */     return ret;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void Done()
/* 383:    */   {
/* 384:404 */     this.buffer = null;
/* 385:405 */     this.bufline = null;
/* 386:406 */     this.bufcolumn = null;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public void adjustBeginLineColumn(int newLine, int newCol)
/* 390:    */   {
/* 391:414 */     int start = this.tokenBegin;
/* 392:    */     int len;
/* 393:    */     int len;
/* 394:417 */     if (this.bufpos >= this.tokenBegin) {
/* 395:419 */       len = this.bufpos - this.tokenBegin + this.inBuf + 1;
/* 396:    */     } else {
/* 397:423 */       len = this.bufsize - this.tokenBegin + this.bufpos + 1 + this.inBuf;
/* 398:    */     }
/* 399:426 */     int i = 0;int j = 0;int k = 0;
/* 400:427 */     int nextColDiff = 0;int columnDiff = 0;
/* 401:430 */     while ((i < len) && (this.bufline[(j = start % this.bufsize)] == this.bufline[(k = ++start % this.bufsize)]))
/* 402:    */     {
/* 403:432 */       this.bufline[j] = newLine;
/* 404:433 */       nextColDiff = columnDiff + this.bufcolumn[k] - this.bufcolumn[j];
/* 405:434 */       this.bufcolumn[j] = (newCol + columnDiff);
/* 406:435 */       columnDiff = nextColDiff;
/* 407:436 */       i++;
/* 408:    */     }
/* 409:439 */     if (i < len)
/* 410:    */     {
/* 411:441 */       this.bufline[j] = (newLine++);
/* 412:442 */       this.bufcolumn[j] = (newCol + columnDiff);
/* 413:444 */       while (i++ < len) {
/* 414:446 */         if (this.bufline[(j = start % this.bufsize)] != this.bufline[(++start % this.bufsize)]) {
/* 415:447 */           this.bufline[j] = (newLine++);
/* 416:    */         } else {
/* 417:449 */           this.bufline[j] = newLine;
/* 418:    */         }
/* 419:    */       }
/* 420:    */     }
/* 421:453 */     this.line = this.bufline[j];
/* 422:454 */     this.column = this.bufcolumn[j];
/* 423:    */   }
/* 424:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.contenttype.parser.SimpleCharStream
 * JD-Core Version:    0.7.0.1
 */
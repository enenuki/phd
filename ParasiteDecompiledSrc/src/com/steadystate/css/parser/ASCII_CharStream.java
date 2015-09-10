/*   1:    */ package com.steadystate.css.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.Reader;
/*   7:    */ 
/*   8:    */ public final class ASCII_CharStream
/*   9:    */   implements CharStream
/*  10:    */ {
/*  11:    */   public static final boolean staticFlag = false;
/*  12:    */   int bufsize;
/*  13:    */   int available;
/*  14:    */   int tokenBegin;
/*  15: 15 */   public int bufpos = -1;
/*  16:    */   private int[] bufline;
/*  17:    */   private int[] bufcolumn;
/*  18: 19 */   private int column = 0;
/*  19: 20 */   private int line = 1;
/*  20: 22 */   private boolean prevCharIsCR = false;
/*  21: 23 */   private boolean prevCharIsLF = false;
/*  22:    */   private Reader inputStream;
/*  23:    */   private char[] buffer;
/*  24: 28 */   private int maxNextCharInd = 0;
/*  25: 29 */   private int inBuf = 0;
/*  26:    */   
/*  27:    */   private final void ExpandBuff(boolean wrapAround)
/*  28:    */   {
/*  29: 33 */     char[] newbuffer = new char[this.bufsize + 2048];
/*  30: 34 */     int[] newbufline = new int[this.bufsize + 2048];
/*  31: 35 */     int[] newbufcolumn = new int[this.bufsize + 2048];
/*  32:    */     try
/*  33:    */     {
/*  34: 39 */       if (wrapAround)
/*  35:    */       {
/*  36: 41 */         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
/*  37: 42 */         System.arraycopy(this.buffer, 0, newbuffer, this.bufsize - this.tokenBegin, this.bufpos);
/*  38:    */         
/*  39: 44 */         this.buffer = newbuffer;
/*  40:    */         
/*  41: 46 */         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
/*  42: 47 */         System.arraycopy(this.bufline, 0, newbufline, this.bufsize - this.tokenBegin, this.bufpos);
/*  43: 48 */         this.bufline = newbufline;
/*  44:    */         
/*  45: 50 */         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
/*  46: 51 */         System.arraycopy(this.bufcolumn, 0, newbufcolumn, this.bufsize - this.tokenBegin, this.bufpos);
/*  47: 52 */         this.bufcolumn = newbufcolumn;
/*  48:    */         
/*  49: 54 */         this.maxNextCharInd = (this.bufpos += this.bufsize - this.tokenBegin);
/*  50:    */       }
/*  51:    */       else
/*  52:    */       {
/*  53: 58 */         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
/*  54: 59 */         this.buffer = newbuffer;
/*  55:    */         
/*  56: 61 */         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
/*  57: 62 */         this.bufline = newbufline;
/*  58:    */         
/*  59: 64 */         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
/*  60: 65 */         this.bufcolumn = newbufcolumn;
/*  61:    */         
/*  62: 67 */         this.maxNextCharInd = (this.bufpos -= this.tokenBegin);
/*  63:    */       }
/*  64:    */     }
/*  65:    */     catch (Throwable t)
/*  66:    */     {
/*  67: 72 */       throw new Error(t.getMessage());
/*  68:    */     }
/*  69: 76 */     this.bufsize += 2048;
/*  70: 77 */     this.available = this.bufsize;
/*  71: 78 */     this.tokenBegin = 0;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private final void FillBuff()
/*  75:    */     throws IOException
/*  76:    */   {
/*  77: 83 */     if (this.maxNextCharInd == this.available) {
/*  78: 85 */       if (this.available == this.bufsize)
/*  79:    */       {
/*  80: 87 */         if (this.tokenBegin > 2048)
/*  81:    */         {
/*  82: 89 */           this.bufpos = (this.maxNextCharInd = 0);
/*  83: 90 */           this.available = this.tokenBegin;
/*  84:    */         }
/*  85: 92 */         else if (this.tokenBegin < 0)
/*  86:    */         {
/*  87: 93 */           this.bufpos = (this.maxNextCharInd = 0);
/*  88:    */         }
/*  89:    */         else
/*  90:    */         {
/*  91: 95 */           ExpandBuff(false);
/*  92:    */         }
/*  93:    */       }
/*  94: 97 */       else if (this.available > this.tokenBegin) {
/*  95: 98 */         this.available = this.bufsize;
/*  96: 99 */       } else if (this.tokenBegin - this.available < 2048) {
/*  97:100 */         ExpandBuff(true);
/*  98:    */       } else {
/*  99:102 */         this.available = this.tokenBegin;
/* 100:    */       }
/* 101:    */     }
/* 102:    */     try
/* 103:    */     {
/* 104:    */       int i;
/* 105:107 */       if ((i = this.inputStream.read(this.buffer, this.maxNextCharInd, this.available - this.maxNextCharInd)) == -1)
/* 106:    */       {
/* 107:110 */         this.inputStream.close();
/* 108:111 */         throw new IOException();
/* 109:    */       }
/* 110:113 */       this.maxNextCharInd += i;
/* 111:114 */       return;
/* 112:    */     }
/* 113:    */     catch (IOException e)
/* 114:    */     {
/* 115:117 */       this.bufpos -= 1;
/* 116:118 */       backup(0);
/* 117:119 */       if (this.tokenBegin == -1) {
/* 118:120 */         this.tokenBegin = this.bufpos;
/* 119:    */       }
/* 120:121 */       throw e;
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public final char BeginToken()
/* 125:    */     throws IOException
/* 126:    */   {
/* 127:127 */     this.tokenBegin = -1;
/* 128:128 */     char c = readChar();
/* 129:129 */     this.tokenBegin = this.bufpos;
/* 130:    */     
/* 131:131 */     return c;
/* 132:    */   }
/* 133:    */   
/* 134:    */   private final void UpdateLineColumn(char c)
/* 135:    */   {
/* 136:136 */     this.column += 1;
/* 137:138 */     if (this.prevCharIsLF)
/* 138:    */     {
/* 139:140 */       this.prevCharIsLF = false;
/* 140:141 */       this.line += (this.column = 1);
/* 141:    */     }
/* 142:143 */     else if (this.prevCharIsCR)
/* 143:    */     {
/* 144:145 */       this.prevCharIsCR = false;
/* 145:146 */       if (c == '\n') {
/* 146:148 */         this.prevCharIsLF = true;
/* 147:    */       } else {
/* 148:151 */         this.line += (this.column = 1);
/* 149:    */       }
/* 150:    */     }
/* 151:154 */     switch (c)
/* 152:    */     {
/* 153:    */     case '\r': 
/* 154:157 */       this.prevCharIsCR = true;
/* 155:158 */       break;
/* 156:    */     case '\n': 
/* 157:160 */       this.prevCharIsLF = true;
/* 158:161 */       break;
/* 159:    */     case '\t': 
/* 160:163 */       this.column -= 1;
/* 161:164 */       this.column += 8 - (this.column & 0x7);
/* 162:165 */       break;
/* 163:    */     }
/* 164:170 */     this.bufline[this.bufpos] = this.line;
/* 165:171 */     this.bufcolumn[this.bufpos] = this.column;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public final char readChar()
/* 169:    */     throws IOException
/* 170:    */   {
/* 171:176 */     if (this.inBuf > 0)
/* 172:    */     {
/* 173:178 */       this.inBuf -= 1;
/* 174:179 */       return (char)(0xFF & this.buffer[(++this.bufpos)]);
/* 175:    */     }
/* 176:182 */     if (++this.bufpos >= this.maxNextCharInd) {
/* 177:183 */       FillBuff();
/* 178:    */     }
/* 179:185 */     char c = (char)(0xFF & this.buffer[this.bufpos]);
/* 180:    */     
/* 181:187 */     UpdateLineColumn(c);
/* 182:188 */     return c;
/* 183:    */   }
/* 184:    */   
/* 185:    */   /**
/* 186:    */    * @deprecated
/* 187:    */    */
/* 188:    */   public final int getColumn()
/* 189:    */   {
/* 190:197 */     return this.bufcolumn[this.bufpos];
/* 191:    */   }
/* 192:    */   
/* 193:    */   /**
/* 194:    */    * @deprecated
/* 195:    */    */
/* 196:    */   public final int getLine()
/* 197:    */   {
/* 198:206 */     return this.bufline[this.bufpos];
/* 199:    */   }
/* 200:    */   
/* 201:    */   public final int getEndColumn()
/* 202:    */   {
/* 203:210 */     return this.bufcolumn[this.bufpos];
/* 204:    */   }
/* 205:    */   
/* 206:    */   public final int getEndLine()
/* 207:    */   {
/* 208:214 */     return this.bufline[this.bufpos];
/* 209:    */   }
/* 210:    */   
/* 211:    */   public final int getBeginColumn()
/* 212:    */   {
/* 213:218 */     return this.bufcolumn[this.tokenBegin];
/* 214:    */   }
/* 215:    */   
/* 216:    */   public final int getBeginLine()
/* 217:    */   {
/* 218:222 */     return this.bufline[this.tokenBegin];
/* 219:    */   }
/* 220:    */   
/* 221:    */   public final void backup(int amount)
/* 222:    */   {
/* 223:227 */     this.inBuf += amount;
/* 224:228 */     if (this.bufpos -= amount < 0) {
/* 225:229 */       this.bufpos += this.bufsize;
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   public ASCII_CharStream(Reader dstream, int startline, int startcolumn, int buffersize)
/* 230:    */   {
/* 231:235 */     this.inputStream = dstream;
/* 232:236 */     this.line = startline;
/* 233:237 */     this.column = (startcolumn - 1);
/* 234:    */     
/* 235:239 */     this.available = (this.bufsize = buffersize);
/* 236:240 */     this.buffer = new char[buffersize];
/* 237:241 */     this.bufline = new int[buffersize];
/* 238:242 */     this.bufcolumn = new int[buffersize];
/* 239:    */   }
/* 240:    */   
/* 241:    */   public ASCII_CharStream(Reader dstream, int startline, int startcolumn)
/* 242:    */   {
/* 243:248 */     this(dstream, startline, startcolumn, 4096);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void ReInit(Reader dstream, int startline, int startcolumn, int buffersize)
/* 247:    */   {
/* 248:253 */     this.inputStream = dstream;
/* 249:254 */     this.line = startline;
/* 250:255 */     this.column = (startcolumn - 1);
/* 251:257 */     if ((this.buffer == null) || (buffersize != this.buffer.length))
/* 252:    */     {
/* 253:259 */       this.available = (this.bufsize = buffersize);
/* 254:260 */       this.buffer = new char[buffersize];
/* 255:261 */       this.bufline = new int[buffersize];
/* 256:262 */       this.bufcolumn = new int[buffersize];
/* 257:    */     }
/* 258:264 */     this.prevCharIsLF = (this.prevCharIsCR = 0);
/* 259:265 */     this.tokenBegin = (this.inBuf = this.maxNextCharInd = 0);
/* 260:266 */     this.bufpos = -1;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void ReInit(Reader dstream, int startline, int startcolumn)
/* 264:    */   {
/* 265:272 */     ReInit(dstream, startline, startcolumn, 4096);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public ASCII_CharStream(InputStream dstream, int startline, int startcolumn, int buffersize)
/* 269:    */   {
/* 270:277 */     this(new InputStreamReader(dstream), startline, startcolumn, 4096);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public ASCII_CharStream(InputStream dstream, int startline, int startcolumn)
/* 274:    */   {
/* 275:283 */     this(dstream, startline, startcolumn, 4096);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void ReInit(InputStream dstream, int startline, int startcolumn, int buffersize)
/* 279:    */   {
/* 280:289 */     ReInit(new InputStreamReader(dstream), startline, startcolumn, 4096);
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void ReInit(InputStream dstream, int startline, int startcolumn)
/* 284:    */   {
/* 285:294 */     ReInit(dstream, startline, startcolumn, 4096);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public final String GetImage()
/* 289:    */   {
/* 290:298 */     if (this.bufpos >= this.tokenBegin) {
/* 291:299 */       return new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1);
/* 292:    */     }
/* 293:300 */     return new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin) + new String(this.buffer, 0, this.bufpos + 1);
/* 294:    */   }
/* 295:    */   
/* 296:    */   public final char[] GetSuffix(int len)
/* 297:    */   {
/* 298:306 */     char[] ret = new char[len];
/* 299:308 */     if (this.bufpos + 1 >= len)
/* 300:    */     {
/* 301:309 */       System.arraycopy(this.buffer, this.bufpos - len + 1, ret, 0, len);
/* 302:    */     }
/* 303:    */     else
/* 304:    */     {
/* 305:312 */       System.arraycopy(this.buffer, this.bufsize - (len - this.bufpos - 1), ret, 0, len - this.bufpos - 1);
/* 306:    */       
/* 307:314 */       System.arraycopy(this.buffer, 0, ret, len - this.bufpos - 1, this.bufpos + 1);
/* 308:    */     }
/* 309:317 */     return ret;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void Done()
/* 313:    */   {
/* 314:322 */     this.buffer = null;
/* 315:323 */     this.bufline = null;
/* 316:324 */     this.bufcolumn = null;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void adjustBeginLineColumn(int newLine, int newCol)
/* 320:    */   {
/* 321:332 */     int start = this.tokenBegin;
/* 322:    */     int len;
/* 323:    */     int len;
/* 324:335 */     if (this.bufpos >= this.tokenBegin) {
/* 325:337 */       len = this.bufpos - this.tokenBegin + this.inBuf + 1;
/* 326:    */     } else {
/* 327:341 */       len = this.bufsize - this.tokenBegin + this.bufpos + 1 + this.inBuf;
/* 328:    */     }
/* 329:344 */     int i = 0;int j = 0;int k = 0;
/* 330:345 */     int nextColDiff = 0;int columnDiff = 0;
/* 331:348 */     while ((i < len) && (this.bufline[(j = start % this.bufsize)] == this.bufline[(k = ++start % this.bufsize)]))
/* 332:    */     {
/* 333:350 */       this.bufline[j] = newLine;
/* 334:351 */       nextColDiff = columnDiff + this.bufcolumn[k] - this.bufcolumn[j];
/* 335:352 */       this.bufcolumn[j] = (newCol + columnDiff);
/* 336:353 */       columnDiff = nextColDiff;
/* 337:354 */       i++;
/* 338:    */     }
/* 339:357 */     if (i < len)
/* 340:    */     {
/* 341:359 */       this.bufline[j] = (newLine++);
/* 342:360 */       this.bufcolumn[j] = (newCol + columnDiff);
/* 343:362 */       while (i++ < len) {
/* 344:364 */         if (this.bufline[(j = start % this.bufsize)] != this.bufline[(++start % this.bufsize)]) {
/* 345:365 */           this.bufline[j] = (newLine++);
/* 346:    */         } else {
/* 347:367 */           this.bufline[j] = newLine;
/* 348:    */         }
/* 349:    */       }
/* 350:    */     }
/* 351:371 */     this.line = this.bufline[j];
/* 352:372 */     this.column = this.bufcolumn[j];
/* 353:    */   }
/* 354:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.ASCII_CharStream
 * JD-Core Version:    0.7.0.1
 */
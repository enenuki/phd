/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.net.MalformedURLException;
/*   7:    */ import java.net.UnknownHostException;
/*   8:    */ import jcifs.util.Encdec;
/*   9:    */ 
/*  10:    */ public class SmbRandomAccessFile
/*  11:    */   implements DataOutput, DataInput
/*  12:    */ {
/*  13: 36 */   private int options = 0;
/*  14: 36 */   private int access = 0;
/*  15: 37 */   private byte[] tmp = new byte[8];
/*  16: 38 */   private SmbComWriteAndXResponse write_andx_resp = null;
/*  17:    */   private static final int WRITE_OPTIONS = 2114;
/*  18:    */   private SmbFile file;
/*  19:    */   private long fp;
/*  20:    */   private int openFlags;
/*  21:    */   private int readSize;
/*  22:    */   private int writeSize;
/*  23:    */   private int ch;
/*  24:    */   
/*  25:    */   public SmbRandomAccessFile(String url, String mode, int shareAccess)
/*  26:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  27:    */   {
/*  28: 42 */     this(new SmbFile(url, "", null, shareAccess), mode);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public SmbRandomAccessFile(SmbFile file, String mode)
/*  32:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  33:    */   {
/*  34: 46 */     this.file = file;
/*  35: 47 */     if (mode.equals("r"))
/*  36:    */     {
/*  37: 48 */       this.openFlags = 17;
/*  38:    */     }
/*  39: 49 */     else if (mode.equals("rw"))
/*  40:    */     {
/*  41: 50 */       this.openFlags = 23;
/*  42: 51 */       this.write_andx_resp = new SmbComWriteAndXResponse();
/*  43: 52 */       this.options = 2114;
/*  44: 53 */       this.access = 3;
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48: 55 */       throw new IllegalArgumentException("Invalid mode");
/*  49:    */     }
/*  50: 57 */     file.open(this.openFlags, this.access, 128, this.options);
/*  51: 58 */     this.readSize = (file.tree.session.transport.rcv_buf_size - 70);
/*  52: 59 */     this.writeSize = (file.tree.session.transport.snd_buf_size - 70);
/*  53: 60 */     this.fp = 0L;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int read()
/*  57:    */     throws SmbException
/*  58:    */   {
/*  59: 64 */     if (read(this.tmp, 0, 1) == -1) {
/*  60: 65 */       return -1;
/*  61:    */     }
/*  62: 67 */     return this.tmp[0] & 0xFF;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int read(byte[] b)
/*  66:    */     throws SmbException
/*  67:    */   {
/*  68: 70 */     return read(b, 0, b.length);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int read(byte[] b, int off, int len)
/*  72:    */     throws SmbException
/*  73:    */   {
/*  74: 73 */     if (len <= 0) {
/*  75: 74 */       return 0;
/*  76:    */     }
/*  77: 76 */     long start = this.fp;
/*  78: 79 */     if (!this.file.isOpen()) {
/*  79: 80 */       this.file.open(this.openFlags, 0, 128, this.options);
/*  80:    */     }
/*  81: 84 */     SmbComReadAndXResponse response = new SmbComReadAndXResponse(b, off);
/*  82:    */     int r;
/*  83:    */     int n;
/*  84:    */     do
/*  85:    */     {
/*  86: 86 */       r = len > this.readSize ? this.readSize : len;
/*  87: 87 */       this.file.send(new SmbComReadAndX(this.file.fid, this.fp, r, null), response);
/*  88: 88 */       if ((n = response.dataLength) <= 0) {
/*  89: 89 */         return (int)(this.fp - start > 0L ? this.fp - start : -1L);
/*  90:    */       }
/*  91: 91 */       this.fp += n;
/*  92: 92 */       len -= n;
/*  93: 93 */       response.off += n;
/*  94: 94 */     } while ((len > 0) && (n == r));
/*  95: 96 */     return (int)(this.fp - start);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public final void readFully(byte[] b)
/*  99:    */     throws SmbException
/* 100:    */   {
/* 101: 99 */     readFully(b, 0, b.length);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public final void readFully(byte[] b, int off, int len)
/* 105:    */     throws SmbException
/* 106:    */   {
/* 107:102 */     int n = 0;
/* 108:    */     do
/* 109:    */     {
/* 110:105 */       int count = read(b, off + n, len - n);
/* 111:106 */       if (count < 0) {
/* 112:106 */         throw new SmbException("EOF");
/* 113:    */       }
/* 114:107 */       n += count;
/* 115:108 */       this.fp += count;
/* 116:109 */     } while (n < len);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int skipBytes(int n)
/* 120:    */     throws SmbException
/* 121:    */   {
/* 122:112 */     if (n > 0)
/* 123:    */     {
/* 124:113 */       this.fp += n;
/* 125:114 */       return n;
/* 126:    */     }
/* 127:116 */     return 0;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void write(int b)
/* 131:    */     throws SmbException
/* 132:    */   {
/* 133:120 */     this.tmp[0] = ((byte)b);
/* 134:121 */     write(this.tmp, 0, 1);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void write(byte[] b)
/* 138:    */     throws SmbException
/* 139:    */   {
/* 140:124 */     write(b, 0, b.length);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void write(byte[] b, int off, int len)
/* 144:    */     throws SmbException
/* 145:    */   {
/* 146:127 */     if (len <= 0) {
/* 147:128 */       return;
/* 148:    */     }
/* 149:132 */     if (!this.file.isOpen()) {
/* 150:133 */       this.file.open(this.openFlags, 0, 128, this.options);
/* 151:    */     }
/* 152:    */     do
/* 153:    */     {
/* 154:138 */       int w = len > this.writeSize ? this.writeSize : len;
/* 155:139 */       this.file.send(new SmbComWriteAndX(this.file.fid, this.fp, len - w, b, off, w, null), this.write_andx_resp);
/* 156:140 */       this.fp += this.write_andx_resp.count;
/* 157:141 */       len = (int)(len - this.write_andx_resp.count);
/* 158:142 */       off = (int)(off + this.write_andx_resp.count);
/* 159:143 */     } while (len > 0);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public long getFilePointer()
/* 163:    */     throws SmbException
/* 164:    */   {
/* 165:146 */     return this.fp;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void seek(long pos)
/* 169:    */     throws SmbException
/* 170:    */   {
/* 171:149 */     this.fp = pos;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public long length()
/* 175:    */     throws SmbException
/* 176:    */   {
/* 177:152 */     return this.file.length();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setLength(long newLength)
/* 181:    */     throws SmbException
/* 182:    */   {
/* 183:156 */     if (!this.file.isOpen()) {
/* 184:157 */       this.file.open(this.openFlags, 0, 128, this.options);
/* 185:    */     }
/* 186:159 */     SmbComWriteResponse rsp = new SmbComWriteResponse();
/* 187:160 */     this.file.send(new SmbComWrite(this.file.fid, (int)(newLength & 0xFFFFFFFF), 0, this.tmp, 0, 0), rsp);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void close()
/* 191:    */     throws SmbException
/* 192:    */   {
/* 193:163 */     this.file.close();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public final boolean readBoolean()
/* 197:    */     throws SmbException
/* 198:    */   {
/* 199:167 */     if (read(this.tmp, 0, 1) < 0) {
/* 200:168 */       throw new SmbException("EOF");
/* 201:    */     }
/* 202:170 */     return this.tmp[0] != 0;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public final byte readByte()
/* 206:    */     throws SmbException
/* 207:    */   {
/* 208:173 */     if (read(this.tmp, 0, 1) < 0) {
/* 209:174 */       throw new SmbException("EOF");
/* 210:    */     }
/* 211:176 */     return this.tmp[0];
/* 212:    */   }
/* 213:    */   
/* 214:    */   public final int readUnsignedByte()
/* 215:    */     throws SmbException
/* 216:    */   {
/* 217:179 */     if (read(this.tmp, 0, 1) < 0) {
/* 218:180 */       throw new SmbException("EOF");
/* 219:    */     }
/* 220:182 */     return this.tmp[0] & 0xFF;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public final short readShort()
/* 224:    */     throws SmbException
/* 225:    */   {
/* 226:185 */     if (read(this.tmp, 0, 2) < 0) {
/* 227:186 */       throw new SmbException("EOF");
/* 228:    */     }
/* 229:188 */     return Encdec.dec_uint16be(this.tmp, 0);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public final int readUnsignedShort()
/* 233:    */     throws SmbException
/* 234:    */   {
/* 235:191 */     if (read(this.tmp, 0, 2) < 0) {
/* 236:192 */       throw new SmbException("EOF");
/* 237:    */     }
/* 238:194 */     return Encdec.dec_uint16be(this.tmp, 0) & 0xFFFF;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public final char readChar()
/* 242:    */     throws SmbException
/* 243:    */   {
/* 244:197 */     if (read(this.tmp, 0, 2) < 0) {
/* 245:198 */       throw new SmbException("EOF");
/* 246:    */     }
/* 247:200 */     return (char)Encdec.dec_uint16be(this.tmp, 0);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public final int readInt()
/* 251:    */     throws SmbException
/* 252:    */   {
/* 253:203 */     if (read(this.tmp, 0, 4) < 0) {
/* 254:204 */       throw new SmbException("EOF");
/* 255:    */     }
/* 256:206 */     return Encdec.dec_uint32be(this.tmp, 0);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public final long readLong()
/* 260:    */     throws SmbException
/* 261:    */   {
/* 262:209 */     if (read(this.tmp, 0, 8) < 0) {
/* 263:210 */       throw new SmbException("EOF");
/* 264:    */     }
/* 265:212 */     return Encdec.dec_uint64be(this.tmp, 0);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public final float readFloat()
/* 269:    */     throws SmbException
/* 270:    */   {
/* 271:215 */     if (read(this.tmp, 0, 4) < 0) {
/* 272:216 */       throw new SmbException("EOF");
/* 273:    */     }
/* 274:218 */     return Encdec.dec_floatbe(this.tmp, 0);
/* 275:    */   }
/* 276:    */   
/* 277:    */   public final double readDouble()
/* 278:    */     throws SmbException
/* 279:    */   {
/* 280:221 */     if (read(this.tmp, 0, 8) < 0) {
/* 281:222 */       throw new SmbException("EOF");
/* 282:    */     }
/* 283:224 */     return Encdec.dec_doublebe(this.tmp, 0);
/* 284:    */   }
/* 285:    */   
/* 286:    */   public final String readLine()
/* 287:    */     throws SmbException
/* 288:    */   {
/* 289:227 */     StringBuffer input = new StringBuffer();
/* 290:228 */     int c = -1;
/* 291:229 */     boolean eol = false;
/* 292:231 */     while (!eol) {
/* 293:232 */       switch (c = read())
/* 294:    */       {
/* 295:    */       case -1: 
/* 296:    */       case 10: 
/* 297:235 */         eol = true;
/* 298:236 */         break;
/* 299:    */       case 13: 
/* 300:238 */         eol = true;
/* 301:239 */         long cur = this.fp;
/* 302:240 */         if (read() != 10) {
/* 303:241 */           this.fp = cur;
/* 304:    */         }
/* 305:    */         break;
/* 306:    */       default: 
/* 307:245 */         input.append((char)c);
/* 308:    */       }
/* 309:    */     }
/* 310:250 */     if ((c == -1) && (input.length() == 0)) {
/* 311:251 */       return null;
/* 312:    */     }
/* 313:254 */     return input.toString();
/* 314:    */   }
/* 315:    */   
/* 316:    */   public final String readUTF()
/* 317:    */     throws SmbException
/* 318:    */   {
/* 319:258 */     int size = readUnsignedShort();
/* 320:259 */     byte[] b = new byte[size];
/* 321:260 */     read(b, 0, size);
/* 322:    */     try
/* 323:    */     {
/* 324:262 */       return Encdec.dec_utf8(b, 0, size);
/* 325:    */     }
/* 326:    */     catch (IOException ioe)
/* 327:    */     {
/* 328:264 */       throw new SmbException("", ioe);
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   public final void writeBoolean(boolean v)
/* 333:    */     throws SmbException
/* 334:    */   {
/* 335:268 */     this.tmp[0] = ((byte)(v ? 1 : 0));
/* 336:269 */     write(this.tmp, 0, 1);
/* 337:    */   }
/* 338:    */   
/* 339:    */   public final void writeByte(int v)
/* 340:    */     throws SmbException
/* 341:    */   {
/* 342:272 */     this.tmp[0] = ((byte)v);
/* 343:273 */     write(this.tmp, 0, 1);
/* 344:    */   }
/* 345:    */   
/* 346:    */   public final void writeShort(int v)
/* 347:    */     throws SmbException
/* 348:    */   {
/* 349:276 */     Encdec.enc_uint16be((short)v, this.tmp, 0);
/* 350:277 */     write(this.tmp, 0, 2);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public final void writeChar(int v)
/* 354:    */     throws SmbException
/* 355:    */   {
/* 356:280 */     Encdec.enc_uint16be((short)v, this.tmp, 0);
/* 357:281 */     write(this.tmp, 0, 2);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public final void writeInt(int v)
/* 361:    */     throws SmbException
/* 362:    */   {
/* 363:284 */     Encdec.enc_uint32be(v, this.tmp, 0);
/* 364:285 */     write(this.tmp, 0, 4);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public final void writeLong(long v)
/* 368:    */     throws SmbException
/* 369:    */   {
/* 370:288 */     Encdec.enc_uint64be(v, this.tmp, 0);
/* 371:289 */     write(this.tmp, 0, 8);
/* 372:    */   }
/* 373:    */   
/* 374:    */   public final void writeFloat(float v)
/* 375:    */     throws SmbException
/* 376:    */   {
/* 377:292 */     Encdec.enc_floatbe(v, this.tmp, 0);
/* 378:293 */     write(this.tmp, 0, 4);
/* 379:    */   }
/* 380:    */   
/* 381:    */   public final void writeDouble(double v)
/* 382:    */     throws SmbException
/* 383:    */   {
/* 384:296 */     Encdec.enc_doublebe(v, this.tmp, 0);
/* 385:297 */     write(this.tmp, 0, 8);
/* 386:    */   }
/* 387:    */   
/* 388:    */   public final void writeBytes(String s)
/* 389:    */     throws SmbException
/* 390:    */   {
/* 391:300 */     byte[] b = s.getBytes();
/* 392:301 */     write(b, 0, b.length);
/* 393:    */   }
/* 394:    */   
/* 395:    */   public final void writeChars(String s)
/* 396:    */     throws SmbException
/* 397:    */   {
/* 398:304 */     int clen = s.length();
/* 399:305 */     int blen = 2 * clen;
/* 400:306 */     byte[] b = new byte[blen];
/* 401:307 */     char[] c = new char[clen];
/* 402:308 */     s.getChars(0, clen, c, 0);
/* 403:309 */     int i = 0;
/* 404:309 */     for (int j = 0; i < clen; i++)
/* 405:    */     {
/* 406:310 */       b[(j++)] = ((byte)(c[i] >>> '\b'));
/* 407:311 */       b[(j++)] = ((byte)(c[i] >>> '\000'));
/* 408:    */     }
/* 409:313 */     write(b, 0, blen);
/* 410:    */   }
/* 411:    */   
/* 412:    */   public final void writeUTF(String str)
/* 413:    */     throws SmbException
/* 414:    */   {
/* 415:316 */     int len = str.length();
/* 416:317 */     int size = 0;
/* 417:320 */     for (int i = 0; i < len; i++)
/* 418:    */     {
/* 419:321 */       int ch = str.charAt(i);
/* 420:322 */       size += (ch > 127 ? 2 : ch > 2047 ? 3 : 1);
/* 421:    */     }
/* 422:324 */     byte[] dst = new byte[size];
/* 423:325 */     writeShort(size);
/* 424:    */     try
/* 425:    */     {
/* 426:327 */       Encdec.enc_utf8(str, dst, 0, size);
/* 427:    */     }
/* 428:    */     catch (IOException ioe)
/* 429:    */     {
/* 430:329 */       throw new SmbException("", ioe);
/* 431:    */     }
/* 432:331 */     write(dst, 0, size);
/* 433:    */   }
/* 434:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbRandomAccessFile
 * JD-Core Version:    0.7.0.1
 */
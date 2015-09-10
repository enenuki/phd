/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.EOFException;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ 
/*   7:    */ public class NullInputStream
/*   8:    */   extends InputStream
/*   9:    */ {
/*  10:    */   private final long size;
/*  11:    */   private long position;
/*  12: 67 */   private long mark = -1L;
/*  13:    */   private long readlimit;
/*  14:    */   private boolean eof;
/*  15:    */   private final boolean throwEofException;
/*  16:    */   private final boolean markSupported;
/*  17:    */   
/*  18:    */   public NullInputStream(long size)
/*  19:    */   {
/*  20: 80 */     this(size, true, false);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public NullInputStream(long size, boolean markSupported, boolean throwEofException)
/*  24:    */   {
/*  25: 95 */     this.size = size;
/*  26: 96 */     this.markSupported = markSupported;
/*  27: 97 */     this.throwEofException = throwEofException;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public long getPosition()
/*  31:    */   {
/*  32:106 */     return this.position;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public long getSize()
/*  36:    */   {
/*  37:115 */     return this.size;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int available()
/*  41:    */   {
/*  42:125 */     long avail = this.size - this.position;
/*  43:126 */     if (avail <= 0L) {
/*  44:127 */       return 0;
/*  45:    */     }
/*  46:128 */     if (avail > 2147483647L) {
/*  47:129 */       return 2147483647;
/*  48:    */     }
/*  49:131 */     return (int)avail;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void close()
/*  53:    */     throws IOException
/*  54:    */   {
/*  55:143 */     this.eof = false;
/*  56:144 */     this.position = 0L;
/*  57:145 */     this.mark = -1L;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public synchronized void mark(int readlimit)
/*  61:    */   {
/*  62:157 */     if (!this.markSupported) {
/*  63:158 */       throw new UnsupportedOperationException("Mark not supported");
/*  64:    */     }
/*  65:160 */     this.mark = this.position;
/*  66:161 */     this.readlimit = readlimit;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean markSupported()
/*  70:    */   {
/*  71:171 */     return this.markSupported;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int read()
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:186 */     if (this.eof) {
/*  78:187 */       throw new IOException("Read after end of file");
/*  79:    */     }
/*  80:189 */     if (this.position == this.size) {
/*  81:190 */       return doEndOfFile();
/*  82:    */     }
/*  83:192 */     this.position += 1L;
/*  84:193 */     return processByte();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int read(byte[] bytes)
/*  88:    */     throws IOException
/*  89:    */   {
/*  90:209 */     return read(bytes, 0, bytes.length);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int read(byte[] bytes, int offset, int length)
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:227 */     if (this.eof) {
/*  97:228 */       throw new IOException("Read after end of file");
/*  98:    */     }
/*  99:230 */     if (this.position == this.size) {
/* 100:231 */       return doEndOfFile();
/* 101:    */     }
/* 102:233 */     this.position += length;
/* 103:234 */     int returnLength = length;
/* 104:235 */     if (this.position > this.size)
/* 105:    */     {
/* 106:236 */       returnLength = length - (int)(this.position - this.size);
/* 107:237 */       this.position = this.size;
/* 108:    */     }
/* 109:239 */     processBytes(bytes, offset, returnLength);
/* 110:240 */     return returnLength;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public synchronized void reset()
/* 114:    */     throws IOException
/* 115:    */   {
/* 116:253 */     if (!this.markSupported) {
/* 117:254 */       throw new UnsupportedOperationException("Mark not supported");
/* 118:    */     }
/* 119:256 */     if (this.mark < 0L) {
/* 120:257 */       throw new IOException("No position has been marked");
/* 121:    */     }
/* 122:259 */     if (this.position > this.mark + this.readlimit) {
/* 123:260 */       throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
/* 124:    */     }
/* 125:264 */     this.position = this.mark;
/* 126:265 */     this.eof = false;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public long skip(long numberOfBytes)
/* 130:    */     throws IOException
/* 131:    */   {
/* 132:281 */     if (this.eof) {
/* 133:282 */       throw new IOException("Skip after end of file");
/* 134:    */     }
/* 135:284 */     if (this.position == this.size) {
/* 136:285 */       return doEndOfFile();
/* 137:    */     }
/* 138:287 */     this.position += numberOfBytes;
/* 139:288 */     long returnLength = numberOfBytes;
/* 140:289 */     if (this.position > this.size)
/* 141:    */     {
/* 142:290 */       returnLength = numberOfBytes - (this.position - this.size);
/* 143:291 */       this.position = this.size;
/* 144:    */     }
/* 145:293 */     return returnLength;
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected int processByte()
/* 149:    */   {
/* 150:305 */     return 0;
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected void processBytes(byte[] bytes, int offset, int length) {}
/* 154:    */   
/* 155:    */   private int doEndOfFile()
/* 156:    */     throws EOFException
/* 157:    */   {
/* 158:331 */     this.eof = true;
/* 159:332 */     if (this.throwEofException) {
/* 160:333 */       throw new EOFException();
/* 161:    */     }
/* 162:335 */     return -1;
/* 163:    */   }
/* 164:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.NullInputStream
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.EOFException;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Reader;
/*   6:    */ 
/*   7:    */ public class NullReader
/*   8:    */   extends Reader
/*   9:    */ {
/*  10:    */   private final long size;
/*  11:    */   private long position;
/*  12: 67 */   private long mark = -1L;
/*  13:    */   private long readlimit;
/*  14:    */   private boolean eof;
/*  15:    */   private final boolean throwEofException;
/*  16:    */   private final boolean markSupported;
/*  17:    */   
/*  18:    */   public NullReader(long size)
/*  19:    */   {
/*  20: 80 */     this(size, true, false);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public NullReader(long size, boolean markSupported, boolean throwEofException)
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
/*  40:    */   public void close()
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:126 */     this.eof = false;
/*  44:127 */     this.position = 0L;
/*  45:128 */     this.mark = -1L;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public synchronized void mark(int readlimit)
/*  49:    */   {
/*  50:140 */     if (!this.markSupported) {
/*  51:141 */       throw new UnsupportedOperationException("Mark not supported");
/*  52:    */     }
/*  53:143 */     this.mark = this.position;
/*  54:144 */     this.readlimit = readlimit;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean markSupported()
/*  58:    */   {
/*  59:154 */     return this.markSupported;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int read()
/*  63:    */     throws IOException
/*  64:    */   {
/*  65:169 */     if (this.eof) {
/*  66:170 */       throw new IOException("Read after end of file");
/*  67:    */     }
/*  68:172 */     if (this.position == this.size) {
/*  69:173 */       return doEndOfFile();
/*  70:    */     }
/*  71:175 */     this.position += 1L;
/*  72:176 */     return processChar();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int read(char[] chars)
/*  76:    */     throws IOException
/*  77:    */   {
/*  78:192 */     return read(chars, 0, chars.length);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int read(char[] chars, int offset, int length)
/*  82:    */     throws IOException
/*  83:    */   {
/*  84:210 */     if (this.eof) {
/*  85:211 */       throw new IOException("Read after end of file");
/*  86:    */     }
/*  87:213 */     if (this.position == this.size) {
/*  88:214 */       return doEndOfFile();
/*  89:    */     }
/*  90:216 */     this.position += length;
/*  91:217 */     int returnLength = length;
/*  92:218 */     if (this.position > this.size)
/*  93:    */     {
/*  94:219 */       returnLength = length - (int)(this.position - this.size);
/*  95:220 */       this.position = this.size;
/*  96:    */     }
/*  97:222 */     processChars(chars, offset, returnLength);
/*  98:223 */     return returnLength;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public synchronized void reset()
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:236 */     if (!this.markSupported) {
/* 105:237 */       throw new UnsupportedOperationException("Mark not supported");
/* 106:    */     }
/* 107:239 */     if (this.mark < 0L) {
/* 108:240 */       throw new IOException("No position has been marked");
/* 109:    */     }
/* 110:242 */     if (this.position > this.mark + this.readlimit) {
/* 111:243 */       throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
/* 112:    */     }
/* 113:247 */     this.position = this.mark;
/* 114:248 */     this.eof = false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public long skip(long numberOfChars)
/* 118:    */     throws IOException
/* 119:    */   {
/* 120:264 */     if (this.eof) {
/* 121:265 */       throw new IOException("Skip after end of file");
/* 122:    */     }
/* 123:267 */     if (this.position == this.size) {
/* 124:268 */       return doEndOfFile();
/* 125:    */     }
/* 126:270 */     this.position += numberOfChars;
/* 127:271 */     long returnLength = numberOfChars;
/* 128:272 */     if (this.position > this.size)
/* 129:    */     {
/* 130:273 */       returnLength = numberOfChars - (this.position - this.size);
/* 131:274 */       this.position = this.size;
/* 132:    */     }
/* 133:276 */     return returnLength;
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected int processChar()
/* 137:    */   {
/* 138:288 */     return 0;
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected void processChars(char[] chars, int offset, int length) {}
/* 142:    */   
/* 143:    */   private int doEndOfFile()
/* 144:    */     throws EOFException
/* 145:    */   {
/* 146:314 */     this.eof = true;
/* 147:315 */     if (this.throwEofException) {
/* 148:316 */       throw new EOFException();
/* 149:    */     }
/* 150:318 */     return -1;
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.NullReader
 * JD-Core Version:    0.7.0.1
 */
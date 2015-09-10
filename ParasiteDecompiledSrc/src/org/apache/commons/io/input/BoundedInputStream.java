/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ 
/*   6:    */ public class BoundedInputStream
/*   7:    */   extends InputStream
/*   8:    */ {
/*   9:    */   private final InputStream in;
/*  10:    */   private final long max;
/*  11: 45 */   private long pos = 0L;
/*  12: 48 */   private long mark = -1L;
/*  13: 51 */   private boolean propagateClose = true;
/*  14:    */   
/*  15:    */   public BoundedInputStream(InputStream in, long size)
/*  16:    */   {
/*  17: 63 */     this.max = size;
/*  18: 64 */     this.in = in;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public BoundedInputStream(InputStream in)
/*  22:    */   {
/*  23: 74 */     this(in, -1L);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int read()
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 86 */     if ((this.max >= 0L) && (this.pos == this.max)) {
/*  30: 87 */       return -1;
/*  31:    */     }
/*  32: 89 */     int result = this.in.read();
/*  33: 90 */     this.pos += 1L;
/*  34: 91 */     return result;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int read(byte[] b)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40:103 */     return read(b, 0, b.length);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int read(byte[] b, int off, int len)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46:117 */     if ((this.max >= 0L) && (this.pos >= this.max)) {
/*  47:118 */       return -1;
/*  48:    */     }
/*  49:120 */     long maxRead = this.max >= 0L ? Math.min(len, this.max - this.pos) : len;
/*  50:121 */     int bytesRead = this.in.read(b, off, (int)maxRead);
/*  51:123 */     if (bytesRead == -1) {
/*  52:124 */       return -1;
/*  53:    */     }
/*  54:127 */     this.pos += bytesRead;
/*  55:128 */     return bytesRead;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public long skip(long n)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61:139 */     long toSkip = this.max >= 0L ? Math.min(n, this.max - this.pos) : n;
/*  62:140 */     long skippedBytes = this.in.skip(toSkip);
/*  63:141 */     this.pos += skippedBytes;
/*  64:142 */     return skippedBytes;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int available()
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:150 */     if ((this.max >= 0L) && (this.pos >= this.max)) {
/*  71:151 */       return 0;
/*  72:    */     }
/*  73:153 */     return this.in.available();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String toString()
/*  77:    */   {
/*  78:162 */     return this.in.toString();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void close()
/*  82:    */     throws IOException
/*  83:    */   {
/*  84:172 */     if (this.propagateClose) {
/*  85:173 */       this.in.close();
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public synchronized void reset()
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:183 */     this.in.reset();
/*  93:184 */     this.pos = this.mark;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public synchronized void mark(int readlimit)
/*  97:    */   {
/*  98:193 */     this.in.mark(readlimit);
/*  99:194 */     this.mark = this.pos;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean markSupported()
/* 103:    */   {
/* 104:203 */     return this.in.markSupported();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean isPropagateClose()
/* 108:    */   {
/* 109:215 */     return this.propagateClose;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setPropagateClose(boolean propagateClose)
/* 113:    */   {
/* 114:228 */     this.propagateClose = propagateClose;
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.BoundedInputStream
 * JD-Core Version:    0.7.0.1
 */
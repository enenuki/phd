/*   1:    */ package org.apache.http.impl.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import org.apache.http.io.SessionOutputBuffer;
/*   6:    */ 
/*   7:    */ public class ChunkedOutputStream
/*   8:    */   extends OutputStream
/*   9:    */ {
/*  10:    */   private final SessionOutputBuffer out;
/*  11:    */   private byte[] cache;
/*  12: 54 */   private int cachePosition = 0;
/*  13: 56 */   private boolean wroteLastChunk = false;
/*  14: 59 */   private boolean closed = false;
/*  15:    */   
/*  16:    */   public ChunkedOutputStream(SessionOutputBuffer out, int bufferSize)
/*  17:    */     throws IOException
/*  18:    */   {
/*  19: 72 */     this.cache = new byte[bufferSize];
/*  20: 73 */     this.out = out;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ChunkedOutputStream(SessionOutputBuffer out)
/*  24:    */     throws IOException
/*  25:    */   {
/*  26: 85 */     this(out, 2048);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void flushCache()
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 93 */     if (this.cachePosition > 0)
/*  33:    */     {
/*  34: 94 */       this.out.writeLine(Integer.toHexString(this.cachePosition));
/*  35: 95 */       this.out.write(this.cache, 0, this.cachePosition);
/*  36: 96 */       this.out.writeLine("");
/*  37: 97 */       this.cachePosition = 0;
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void flushCacheWithAppend(byte[] bufferToAppend, int off, int len)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:106 */     this.out.writeLine(Integer.toHexString(this.cachePosition + len));
/*  45:107 */     this.out.write(this.cache, 0, this.cachePosition);
/*  46:108 */     this.out.write(bufferToAppend, off, len);
/*  47:109 */     this.out.writeLine("");
/*  48:110 */     this.cachePosition = 0;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void writeClosingChunk()
/*  52:    */     throws IOException
/*  53:    */   {
/*  54:115 */     this.out.writeLine("0");
/*  55:116 */     this.out.writeLine("");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void finish()
/*  59:    */     throws IOException
/*  60:    */   {
/*  61:126 */     if (!this.wroteLastChunk)
/*  62:    */     {
/*  63:127 */       flushCache();
/*  64:128 */       writeClosingChunk();
/*  65:129 */       this.wroteLastChunk = true;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void write(int b)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:135 */     if (this.closed) {
/*  73:136 */       throw new IOException("Attempted write to closed stream.");
/*  74:    */     }
/*  75:138 */     this.cache[this.cachePosition] = ((byte)b);
/*  76:139 */     this.cachePosition += 1;
/*  77:140 */     if (this.cachePosition == this.cache.length) {
/*  78:140 */       flushCache();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void write(byte[] b)
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:148 */     write(b, 0, b.length);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void write(byte[] src, int off, int len)
/*  89:    */     throws IOException
/*  90:    */   {
/*  91:156 */     if (this.closed) {
/*  92:157 */       throw new IOException("Attempted write to closed stream.");
/*  93:    */     }
/*  94:159 */     if (len >= this.cache.length - this.cachePosition)
/*  95:    */     {
/*  96:160 */       flushCacheWithAppend(src, off, len);
/*  97:    */     }
/*  98:    */     else
/*  99:    */     {
/* 100:162 */       System.arraycopy(src, off, this.cache, this.cachePosition, len);
/* 101:163 */       this.cachePosition += len;
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void flush()
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:171 */     flushCache();
/* 109:172 */     this.out.flush();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void close()
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:179 */     if (!this.closed)
/* 116:    */     {
/* 117:180 */       this.closed = true;
/* 118:181 */       finish();
/* 119:182 */       this.out.flush();
/* 120:    */     }
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.ChunkedOutputStream
 * JD-Core Version:    0.7.0.1
 */
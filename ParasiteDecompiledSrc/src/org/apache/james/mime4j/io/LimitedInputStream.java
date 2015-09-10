/*  1:   */ package org.apache.james.mime4j.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ 
/*  6:   */ public class LimitedInputStream
/*  7:   */   extends PositionInputStream
/*  8:   */ {
/*  9:   */   private final long limit;
/* 10:   */   
/* 11:   */   public LimitedInputStream(InputStream instream, long limit)
/* 12:   */   {
/* 13:30 */     super(instream);
/* 14:31 */     if (limit < 0L) {
/* 15:32 */       throw new IllegalArgumentException("Limit may not be negative");
/* 16:   */     }
/* 17:34 */     this.limit = limit;
/* 18:   */   }
/* 19:   */   
/* 20:   */   private void enforceLimit()
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:38 */     if (this.position >= this.limit) {
/* 24:39 */       throw new IOException("Input stream limit exceeded");
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int read()
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:45 */     enforceLimit();
/* 32:46 */     return super.read();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int read(byte[] b, int off, int len)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:51 */     enforceLimit();
/* 39:52 */     len = Math.min(len, getBytesLeft());
/* 40:53 */     return super.read(b, off, len);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public long skip(long n)
/* 44:   */     throws IOException
/* 45:   */   {
/* 46:58 */     enforceLimit();
/* 47:59 */     n = Math.min(n, getBytesLeft());
/* 48:60 */     return super.skip(n);
/* 49:   */   }
/* 50:   */   
/* 51:   */   private int getBytesLeft()
/* 52:   */   {
/* 53:64 */     return (int)Math.min(2147483647L, this.limit - this.position);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.io.LimitedInputStream
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.apache.james.mime4j.io;
/*  2:   */ 
/*  3:   */ import java.io.FilterInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ 
/*  7:   */ public class PositionInputStream
/*  8:   */   extends FilterInputStream
/*  9:   */ {
/* 10:29 */   protected long position = 0L;
/* 11:30 */   private long markedPosition = 0L;
/* 12:   */   
/* 13:   */   public PositionInputStream(InputStream inputStream)
/* 14:   */   {
/* 15:33 */     super(inputStream);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public long getPosition()
/* 19:   */   {
/* 20:37 */     return this.position;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int available()
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:42 */     return this.in.available();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int read()
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:47 */     int b = this.in.read();
/* 33:48 */     if (b != -1) {
/* 34:49 */       this.position += 1L;
/* 35:   */     }
/* 36:50 */     return b;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void close()
/* 40:   */     throws IOException
/* 41:   */   {
/* 42:55 */     this.in.close();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void reset()
/* 46:   */     throws IOException
/* 47:   */   {
/* 48:60 */     this.in.reset();
/* 49:61 */     this.position = this.markedPosition;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean markSupported()
/* 53:   */   {
/* 54:66 */     return this.in.markSupported();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void mark(int readlimit)
/* 58:   */   {
/* 59:71 */     this.in.mark(readlimit);
/* 60:72 */     this.markedPosition = this.position;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public long skip(long n)
/* 64:   */     throws IOException
/* 65:   */   {
/* 66:77 */     long c = this.in.skip(n);
/* 67:78 */     if (c > 0L) {
/* 68:79 */       this.position += c;
/* 69:   */     }
/* 70:80 */     return c;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public int read(byte[] b, int off, int len)
/* 74:   */     throws IOException
/* 75:   */   {
/* 76:85 */     int c = this.in.read(b, off, len);
/* 77:86 */     if (c > 0) {
/* 78:87 */       this.position += c;
/* 79:   */     }
/* 80:88 */     return c;
/* 81:   */   }
/* 82:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.io.PositionInputStream
 * JD-Core Version:    0.7.0.1
 */
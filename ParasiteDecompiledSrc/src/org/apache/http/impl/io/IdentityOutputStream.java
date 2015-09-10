/*  1:   */ package org.apache.http.impl.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ import org.apache.http.io.SessionOutputBuffer;
/*  6:   */ 
/*  7:   */ public class IdentityOutputStream
/*  8:   */   extends OutputStream
/*  9:   */ {
/* 10:   */   private final SessionOutputBuffer out;
/* 11:55 */   private boolean closed = false;
/* 12:   */   
/* 13:   */   public IdentityOutputStream(SessionOutputBuffer out)
/* 14:   */   {
/* 15:59 */     if (out == null) {
/* 16:60 */       throw new IllegalArgumentException("Session output buffer may not be null");
/* 17:   */     }
/* 18:62 */     this.out = out;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void close()
/* 22:   */     throws IOException
/* 23:   */   {
/* 24:71 */     if (!this.closed)
/* 25:   */     {
/* 26:72 */       this.closed = true;
/* 27:73 */       this.out.flush();
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void flush()
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:78 */     this.out.flush();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void write(byte[] b, int off, int len)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:82 */     if (this.closed) {
/* 41:83 */       throw new IOException("Attempted write to closed stream.");
/* 42:   */     }
/* 43:85 */     this.out.write(b, off, len);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void write(byte[] b)
/* 47:   */     throws IOException
/* 48:   */   {
/* 49:89 */     write(b, 0, b.length);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void write(int b)
/* 53:   */     throws IOException
/* 54:   */   {
/* 55:93 */     if (this.closed) {
/* 56:94 */       throw new IOException("Attempted write to closed stream.");
/* 57:   */     }
/* 58:96 */     this.out.write(b);
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.IdentityOutputStream
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.apache.http.impl.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import org.apache.http.io.BufferInfo;
/*  6:   */ import org.apache.http.io.SessionInputBuffer;
/*  7:   */ 
/*  8:   */ public class IdentityInputStream
/*  9:   */   extends InputStream
/* 10:   */ {
/* 11:   */   private final SessionInputBuffer in;
/* 12:52 */   private boolean closed = false;
/* 13:   */   
/* 14:   */   public IdentityInputStream(SessionInputBuffer in)
/* 15:   */   {
/* 16:61 */     if (in == null) {
/* 17:62 */       throw new IllegalArgumentException("Session input buffer may not be null");
/* 18:   */     }
/* 19:64 */     this.in = in;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int available()
/* 23:   */     throws IOException
/* 24:   */   {
/* 25:68 */     if ((this.in instanceof BufferInfo)) {
/* 26:69 */       return ((BufferInfo)this.in).length();
/* 27:   */     }
/* 28:71 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void close()
/* 32:   */     throws IOException
/* 33:   */   {
/* 34:76 */     this.closed = true;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int read()
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:80 */     if (this.closed) {
/* 41:81 */       return -1;
/* 42:   */     }
/* 43:83 */     return this.in.read();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int read(byte[] b, int off, int len)
/* 47:   */     throws IOException
/* 48:   */   {
/* 49:88 */     if (this.closed) {
/* 50:89 */       return -1;
/* 51:   */     }
/* 52:91 */     return this.in.read(b, off, len);
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.IdentityInputStream
 * JD-Core Version:    0.7.0.1
 */
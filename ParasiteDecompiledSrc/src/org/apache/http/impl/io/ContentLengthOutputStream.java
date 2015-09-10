/*   1:    */ package org.apache.http.impl.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import org.apache.http.io.SessionOutputBuffer;
/*   6:    */ 
/*   7:    */ public class ContentLengthOutputStream
/*   8:    */   extends OutputStream
/*   9:    */ {
/*  10:    */   private final SessionOutputBuffer out;
/*  11:    */   private final long contentLength;
/*  12: 62 */   private long total = 0L;
/*  13: 65 */   private boolean closed = false;
/*  14:    */   
/*  15:    */   public ContentLengthOutputStream(SessionOutputBuffer out, long contentLength)
/*  16:    */   {
/*  17: 79 */     if (out == null) {
/*  18: 80 */       throw new IllegalArgumentException("Session output buffer may not be null");
/*  19:    */     }
/*  20: 82 */     if (contentLength < 0L) {
/*  21: 83 */       throw new IllegalArgumentException("Content length may not be negative");
/*  22:    */     }
/*  23: 85 */     this.out = out;
/*  24: 86 */     this.contentLength = contentLength;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void close()
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 95 */     if (!this.closed)
/*  31:    */     {
/*  32: 96 */       this.closed = true;
/*  33: 97 */       this.out.flush();
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void flush()
/*  38:    */     throws IOException
/*  39:    */   {
/*  40:102 */     this.out.flush();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void write(byte[] b, int off, int len)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46:106 */     if (this.closed) {
/*  47:107 */       throw new IOException("Attempted write to closed stream.");
/*  48:    */     }
/*  49:109 */     if (this.total < this.contentLength)
/*  50:    */     {
/*  51:110 */       long max = this.contentLength - this.total;
/*  52:111 */       if (len > max) {
/*  53:112 */         len = (int)max;
/*  54:    */       }
/*  55:114 */       this.out.write(b, off, len);
/*  56:115 */       this.total += len;
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void write(byte[] b)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:120 */     write(b, 0, b.length);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void write(int b)
/*  67:    */     throws IOException
/*  68:    */   {
/*  69:124 */     if (this.closed) {
/*  70:125 */       throw new IOException("Attempted write to closed stream.");
/*  71:    */     }
/*  72:127 */     if (this.total < this.contentLength)
/*  73:    */     {
/*  74:128 */       this.out.write(b);
/*  75:129 */       this.total += 1L;
/*  76:    */     }
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.ContentLengthOutputStream
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.apache.james.mime4j.codec;
/*  2:   */ 
/*  3:   */ import java.io.FilterOutputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ 
/*  7:   */ public class QuotedPrintableOutputStream
/*  8:   */   extends FilterOutputStream
/*  9:   */ {
/* 10:   */   private QuotedPrintableEncoder encoder;
/* 11:32 */   private boolean closed = false;
/* 12:   */   
/* 13:   */   public QuotedPrintableOutputStream(OutputStream out, boolean binary)
/* 14:   */   {
/* 15:35 */     super(out);
/* 16:36 */     this.encoder = new QuotedPrintableEncoder(1024, binary);
/* 17:37 */     this.encoder.initEncoding(out);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void close()
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:42 */     if (this.closed) {
/* 24:43 */       return;
/* 25:   */     }
/* 26:   */     try
/* 27:   */     {
/* 28:46 */       this.encoder.completeEncoding();
/* 29:   */     }
/* 30:   */     finally
/* 31:   */     {
/* 32:49 */       this.closed = true;
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void flush()
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:55 */     this.encoder.flushOutput();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void write(int b)
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:60 */     write(new byte[] { (byte)b }, 0, 1);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void write(byte[] b, int off, int len)
/* 49:   */     throws IOException
/* 50:   */   {
/* 51:65 */     if (this.closed) {
/* 52:66 */       throw new IOException("QuotedPrintableOutputStream has been closed");
/* 53:   */     }
/* 54:69 */     this.encoder.encodeChunk(b, off, len);
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.QuotedPrintableOutputStream
 * JD-Core Version:    0.7.0.1
 */
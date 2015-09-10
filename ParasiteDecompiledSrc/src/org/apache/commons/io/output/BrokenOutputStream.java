/*  1:   */ package org.apache.commons.io.output;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ 
/*  6:   */ public class BrokenOutputStream
/*  7:   */   extends OutputStream
/*  8:   */ {
/*  9:   */   private final IOException exception;
/* 10:   */   
/* 11:   */   public BrokenOutputStream(IOException exception)
/* 12:   */   {
/* 13:44 */     this.exception = exception;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public BrokenOutputStream()
/* 17:   */   {
/* 18:51 */     this(new IOException("Broken output stream"));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void write(int b)
/* 22:   */     throws IOException
/* 23:   */   {
/* 24:62 */     throw this.exception;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void flush()
/* 28:   */     throws IOException
/* 29:   */   {
/* 30:72 */     throw this.exception;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void close()
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:82 */     throw this.exception;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.BrokenOutputStream
 * JD-Core Version:    0.7.0.1
 */
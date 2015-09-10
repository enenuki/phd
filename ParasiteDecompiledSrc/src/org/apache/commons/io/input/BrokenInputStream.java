/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ 
/*   6:    */ public class BrokenInputStream
/*   7:    */   extends InputStream
/*   8:    */ {
/*   9:    */   private final IOException exception;
/*  10:    */   
/*  11:    */   public BrokenInputStream(IOException exception)
/*  12:    */   {
/*  13: 44 */     this.exception = exception;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public BrokenInputStream()
/*  17:    */   {
/*  18: 51 */     this(new IOException("Broken input stream"));
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int read()
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 62 */     throw this.exception;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int available()
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 73 */     throw this.exception;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public long skip(long n)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 85 */     throw this.exception;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void reset()
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 95 */     throw this.exception;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void close()
/*  46:    */     throws IOException
/*  47:    */   {
/*  48:105 */     throw this.exception;
/*  49:    */   }
/*  50:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.BrokenInputStream
 * JD-Core Version:    0.7.0.1
 */
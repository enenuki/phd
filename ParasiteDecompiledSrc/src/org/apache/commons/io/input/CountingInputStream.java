/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ 
/*   6:    */ public class CountingInputStream
/*   7:    */   extends ProxyInputStream
/*   8:    */ {
/*   9:    */   private long count;
/*  10:    */   
/*  11:    */   public CountingInputStream(InputStream in)
/*  12:    */   {
/*  13: 43 */     super(in);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public synchronized long skip(long length)
/*  17:    */     throws IOException
/*  18:    */   {
/*  19: 59 */     long skip = super.skip(length);
/*  20: 60 */     this.count += skip;
/*  21: 61 */     return skip;
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected synchronized void afterRead(int n)
/*  25:    */   {
/*  26: 72 */     if (n != -1) {
/*  27: 73 */       this.count += n;
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getCount()
/*  32:    */   {
/*  33: 89 */     long result = getByteCount();
/*  34: 90 */     if (result > 2147483647L) {
/*  35: 91 */       throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
/*  36:    */     }
/*  37: 93 */     return (int)result;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int resetCount()
/*  41:    */   {
/*  42:107 */     long result = resetByteCount();
/*  43:108 */     if (result > 2147483647L) {
/*  44:109 */       throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
/*  45:    */     }
/*  46:111 */     return (int)result;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public synchronized long getByteCount()
/*  50:    */   {
/*  51:125 */     return this.count;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public synchronized long resetByteCount()
/*  55:    */   {
/*  56:139 */     long tmp = this.count;
/*  57:140 */     this.count = 0L;
/*  58:141 */     return tmp;
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.CountingInputStream
 * JD-Core Version:    0.7.0.1
 */
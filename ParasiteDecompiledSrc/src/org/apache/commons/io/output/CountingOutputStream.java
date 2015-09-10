/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.OutputStream;
/*   4:    */ 
/*   5:    */ public class CountingOutputStream
/*   6:    */   extends ProxyOutputStream
/*   7:    */ {
/*   8: 33 */   private long count = 0L;
/*   9:    */   
/*  10:    */   public CountingOutputStream(OutputStream out)
/*  11:    */   {
/*  12: 41 */     super(out);
/*  13:    */   }
/*  14:    */   
/*  15:    */   protected synchronized void beforeWrite(int n)
/*  16:    */   {
/*  17: 54 */     this.count += n;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public int getCount()
/*  21:    */   {
/*  22: 69 */     long result = getByteCount();
/*  23: 70 */     if (result > 2147483647L) {
/*  24: 71 */       throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
/*  25:    */     }
/*  26: 73 */     return (int)result;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int resetCount()
/*  30:    */   {
/*  31: 87 */     long result = resetByteCount();
/*  32: 88 */     if (result > 2147483647L) {
/*  33: 89 */       throw new ArithmeticException("The byte count " + result + " is too large to be converted to an int");
/*  34:    */     }
/*  35: 91 */     return (int)result;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public synchronized long getByteCount()
/*  39:    */   {
/*  40:105 */     return this.count;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public synchronized long resetByteCount()
/*  44:    */   {
/*  45:119 */     long tmp = this.count;
/*  46:120 */     this.count = 0L;
/*  47:121 */     return tmp;
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.CountingOutputStream
 * JD-Core Version:    0.7.0.1
 */
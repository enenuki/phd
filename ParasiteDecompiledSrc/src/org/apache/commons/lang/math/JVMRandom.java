/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public final class JVMRandom
/*   6:    */   extends Random
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = 1L;
/*   9: 49 */   private static final Random SHARED_RANDOM = new Random();
/*  10: 54 */   private boolean constructed = false;
/*  11:    */   
/*  12:    */   public JVMRandom()
/*  13:    */   {
/*  14: 60 */     this.constructed = true;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public synchronized void setSeed(long seed)
/*  18:    */   {
/*  19: 70 */     if (this.constructed) {
/*  20: 71 */       throw new UnsupportedOperationException();
/*  21:    */     }
/*  22:    */   }
/*  23:    */   
/*  24:    */   public synchronized double nextGaussian()
/*  25:    */   {
/*  26: 82 */     throw new UnsupportedOperationException();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void nextBytes(byte[] byteArray)
/*  30:    */   {
/*  31: 92 */     throw new UnsupportedOperationException();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int nextInt()
/*  35:    */   {
/*  36:105 */     return nextInt(2147483647);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int nextInt(int n)
/*  40:    */   {
/*  41:118 */     return SHARED_RANDOM.nextInt(n);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public long nextLong()
/*  45:    */   {
/*  46:131 */     return nextLong(9223372036854775807L);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static long nextLong(long n)
/*  50:    */   {
/*  51:145 */     if (n <= 0L) {
/*  52:146 */       throw new IllegalArgumentException("Upper bound for nextInt must be positive");
/*  53:    */     }
/*  54:151 */     if ((n & -n) == n) {
/*  55:153 */       return next63bits() >> 63 - bitsRequired(n - 1L);
/*  56:    */     }
/*  57:    */     long bits;
/*  58:    */     long val;
/*  59:    */     do
/*  60:    */     {
/*  61:160 */       bits = next63bits();
/*  62:161 */       val = bits % n;
/*  63:162 */     } while (bits - val + (n - 1L) < 0L);
/*  64:163 */     return val;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean nextBoolean()
/*  68:    */   {
/*  69:173 */     return SHARED_RANDOM.nextBoolean();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public float nextFloat()
/*  73:    */   {
/*  74:184 */     return SHARED_RANDOM.nextFloat();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public double nextDouble()
/*  78:    */   {
/*  79:193 */     return SHARED_RANDOM.nextDouble();
/*  80:    */   }
/*  81:    */   
/*  82:    */   private static long next63bits()
/*  83:    */   {
/*  84:202 */     return SHARED_RANDOM.nextLong() & 0xFFFFFFFF;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private static int bitsRequired(long num)
/*  88:    */   {
/*  89:213 */     long y = num;
/*  90:214 */     int n = 0;
/*  91:    */     for (;;)
/*  92:    */     {
/*  93:217 */       if (num < 0L) {
/*  94:218 */         return 64 - n;
/*  95:    */       }
/*  96:220 */       if (y == 0L) {
/*  97:221 */         return n;
/*  98:    */       }
/*  99:223 */       n++;
/* 100:224 */       num <<= 1;
/* 101:225 */       y >>= 1;
/* 102:    */     }
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.JVMRandom
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public class RandomUtils
/*   6:    */ {
/*   7: 37 */   public static final Random JVM_RANDOM = new JVMRandom();
/*   8:    */   
/*   9:    */   public static int nextInt()
/*  10:    */   {
/*  11: 51 */     return nextInt(JVM_RANDOM);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public static int nextInt(Random random)
/*  15:    */   {
/*  16: 62 */     return random.nextInt();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static int nextInt(int n)
/*  20:    */   {
/*  21: 74 */     return nextInt(JVM_RANDOM, n);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static int nextInt(Random random, int n)
/*  25:    */   {
/*  26: 88 */     return random.nextInt(n);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static long nextLong()
/*  30:    */   {
/*  31: 98 */     return nextLong(JVM_RANDOM);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static long nextLong(Random random)
/*  35:    */   {
/*  36:109 */     return random.nextLong();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static boolean nextBoolean()
/*  40:    */   {
/*  41:119 */     return nextBoolean(JVM_RANDOM);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static boolean nextBoolean(Random random)
/*  45:    */   {
/*  46:130 */     return random.nextBoolean();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static float nextFloat()
/*  50:    */   {
/*  51:141 */     return nextFloat(JVM_RANDOM);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static float nextFloat(Random random)
/*  55:    */   {
/*  56:153 */     return random.nextFloat();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static double nextDouble()
/*  60:    */   {
/*  61:164 */     return nextDouble(JVM_RANDOM);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static double nextDouble(Random random)
/*  65:    */   {
/*  66:176 */     return random.nextDouble();
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.RandomUtils
 * JD-Core Version:    0.7.0.1
 */
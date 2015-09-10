/*   1:    */ package org.apache.http.util;
/*   2:    */ 
/*   3:    */ public final class LangUtils
/*   4:    */ {
/*   5:    */   public static final int HASH_SEED = 17;
/*   6:    */   public static final int HASH_OFFSET = 37;
/*   7:    */   
/*   8:    */   public static int hashCode(int seed, int hashcode)
/*   9:    */   {
/*  10: 47 */     return seed * 37 + hashcode;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public static int hashCode(int seed, boolean b)
/*  14:    */   {
/*  15: 51 */     return hashCode(seed, b ? 1 : 0);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static int hashCode(int seed, Object obj)
/*  19:    */   {
/*  20: 55 */     return hashCode(seed, obj != null ? obj.hashCode() : 0);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static boolean equals(Object obj1, Object obj2)
/*  24:    */   {
/*  25: 66 */     return obj1 == null ? false : obj2 == null ? true : obj1.equals(obj2);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static boolean equals(Object[] a1, Object[] a2)
/*  29:    */   {
/*  30: 85 */     if (a1 == null)
/*  31:    */     {
/*  32: 86 */       if (a2 == null) {
/*  33: 87 */         return true;
/*  34:    */       }
/*  35: 89 */       return false;
/*  36:    */     }
/*  37: 92 */     if ((a2 != null) && (a1.length == a2.length))
/*  38:    */     {
/*  39: 93 */       for (int i = 0; i < a1.length; i++) {
/*  40: 94 */         if (!equals(a1[i], a2[i])) {
/*  41: 95 */           return false;
/*  42:    */         }
/*  43:    */       }
/*  44: 98 */       return true;
/*  45:    */     }
/*  46:100 */     return false;
/*  47:    */   }
/*  48:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.util.LangUtils
 * JD-Core Version:    0.7.0.1
 */
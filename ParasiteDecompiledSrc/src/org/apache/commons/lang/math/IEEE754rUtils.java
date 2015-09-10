/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ public class IEEE754rUtils
/*   4:    */ {
/*   5:    */   public static double min(double[] array)
/*   6:    */   {
/*   7: 40 */     if (array == null) {
/*   8: 41 */       throw new IllegalArgumentException("The Array must not be null");
/*   9:    */     }
/*  10: 42 */     if (array.length == 0) {
/*  11: 43 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  12:    */     }
/*  13: 47 */     double min = array[0];
/*  14: 48 */     for (int i = 1; i < array.length; i++) {
/*  15: 49 */       min = min(array[i], min);
/*  16:    */     }
/*  17: 52 */     return min;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static float min(float[] array)
/*  21:    */   {
/*  22: 65 */     if (array == null) {
/*  23: 66 */       throw new IllegalArgumentException("The Array must not be null");
/*  24:    */     }
/*  25: 67 */     if (array.length == 0) {
/*  26: 68 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  27:    */     }
/*  28: 72 */     float min = array[0];
/*  29: 73 */     for (int i = 1; i < array.length; i++) {
/*  30: 74 */       min = min(array[i], min);
/*  31:    */     }
/*  32: 77 */     return min;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static double min(double a, double b, double c)
/*  36:    */   {
/*  37: 91 */     return min(min(a, b), c);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static double min(double a, double b)
/*  41:    */   {
/*  42:104 */     if (Double.isNaN(a)) {
/*  43:105 */       return b;
/*  44:    */     }
/*  45:107 */     if (Double.isNaN(b)) {
/*  46:108 */       return a;
/*  47:    */     }
/*  48:110 */     return Math.min(a, b);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static float min(float a, float b, float c)
/*  52:    */   {
/*  53:125 */     return min(min(a, b), c);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static float min(float a, float b)
/*  57:    */   {
/*  58:138 */     if (Float.isNaN(a)) {
/*  59:139 */       return b;
/*  60:    */     }
/*  61:141 */     if (Float.isNaN(b)) {
/*  62:142 */       return a;
/*  63:    */     }
/*  64:144 */     return Math.min(a, b);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static double max(double[] array)
/*  68:    */   {
/*  69:158 */     if (array == null) {
/*  70:159 */       throw new IllegalArgumentException("The Array must not be null");
/*  71:    */     }
/*  72:160 */     if (array.length == 0) {
/*  73:161 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  74:    */     }
/*  75:165 */     double max = array[0];
/*  76:166 */     for (int j = 1; j < array.length; j++) {
/*  77:167 */       max = max(array[j], max);
/*  78:    */     }
/*  79:170 */     return max;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static float max(float[] array)
/*  83:    */   {
/*  84:183 */     if (array == null) {
/*  85:184 */       throw new IllegalArgumentException("The Array must not be null");
/*  86:    */     }
/*  87:185 */     if (array.length == 0) {
/*  88:186 */       throw new IllegalArgumentException("Array cannot be empty.");
/*  89:    */     }
/*  90:190 */     float max = array[0];
/*  91:191 */     for (int j = 1; j < array.length; j++) {
/*  92:192 */       max = max(array[j], max);
/*  93:    */     }
/*  94:195 */     return max;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static double max(double a, double b, double c)
/*  98:    */   {
/*  99:209 */     return max(max(a, b), c);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static double max(double a, double b)
/* 103:    */   {
/* 104:222 */     if (Double.isNaN(a)) {
/* 105:223 */       return b;
/* 106:    */     }
/* 107:225 */     if (Double.isNaN(b)) {
/* 108:226 */       return a;
/* 109:    */     }
/* 110:228 */     return Math.max(a, b);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static float max(float a, float b, float c)
/* 114:    */   {
/* 115:243 */     return max(max(a, b), c);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static float max(float a, float b)
/* 119:    */   {
/* 120:256 */     if (Float.isNaN(a)) {
/* 121:257 */       return b;
/* 122:    */     }
/* 123:259 */     if (Float.isNaN(b)) {
/* 124:260 */       return a;
/* 125:    */     }
/* 126:262 */     return Math.max(a, b);
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.IEEE754rUtils
 * JD-Core Version:    0.7.0.1
 */
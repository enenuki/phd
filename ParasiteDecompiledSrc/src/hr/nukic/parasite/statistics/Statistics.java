/*  1:   */ package hr.nukic.parasite.statistics;
/*  2:   */ 
/*  3:   */ import cern.colt.list.DoubleArrayList;
/*  4:   */ import cern.jet.stat.Descriptive;
/*  5:   */ import nukic.parasite.utils.MainLogger;
/*  6:   */ 
/*  7:   */ public class Statistics
/*  8:   */ {
/*  9:   */   public static float correlation(float[] sample1, float[] sample2)
/* 10:   */   {
/* 11:10 */     if (sample1.length == sample2.length)
/* 12:   */     {
/* 13:11 */       DoubleArrayList s1 = new DoubleArrayList(floatToDoubleArray(sample1));
/* 14:12 */       DoubleArrayList s2 = new DoubleArrayList(floatToDoubleArray(sample2));
/* 15:13 */       return new Double(Descriptive.correlation(s1, standardDeviation(sample1), s2, standardDeviation(sample2)))
/* 16:14 */         .floatValue();
/* 17:   */     }
/* 18:16 */     MainLogger.error("Cannot calculate correlation between samples of different length!");
/* 19:17 */     return 0.0F;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static double standardDeviation(float[] array)
/* 23:   */   {
/* 24:22 */     DoubleArrayList sample = new DoubleArrayList(floatToDoubleArray(array));
/* 25:23 */     double sumOfSquares = Descriptive.sumOfSquares(sample);
/* 26:24 */     double sum = Descriptive.sum(sample);
/* 27:25 */     double variance = Descriptive.variance(sample.size(), sum, sumOfSquares);
/* 28:26 */     return Descriptive.standardDeviation(variance);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static double standardDeviation(double[] array)
/* 32:   */   {
/* 33:30 */     DoubleArrayList sample = new DoubleArrayList(array);
/* 34:31 */     double sumOfSquares = Descriptive.sumOfSquares(sample);
/* 35:32 */     double sum = Descriptive.sum(sample);
/* 36:33 */     double variance = Descriptive.variance(sample.size(), sum, sumOfSquares);
/* 37:34 */     return Descriptive.standardDeviation(variance);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static float standardDeviationF(float[] array)
/* 41:   */   {
/* 42:38 */     return new Double(standardDeviation(array)).floatValue();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static double[] floatToDoubleArray(float[] array)
/* 46:   */   {
/* 47:42 */     double[] ret = new double[array.length];
/* 48:43 */     for (int i = 0; i < array.length; i++) {
/* 49:44 */       ret[i] = array[i];
/* 50:   */     }
/* 51:46 */     return ret;
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.statistics.Statistics
 * JD-Core Version:    0.7.0.1
 */
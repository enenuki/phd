/*  1:   */ package hr.nukic.parasite.core.algorithm.configurations;
/*  2:   */ 
/*  3:   */ import nukic.parasite.utils.MainLogger;
/*  4:   */ 
/*  5:   */ public class AlgorithmScore
/*  6:   */ {
/*  7:   */   public static final float REFERENCE_ALGORITHM_TRANSACTION_CASH = 5000.0F;
/*  8: 9 */   public float refernceScore = 0.0F;
/*  9:10 */   public float absoluteScore = 1.4E-45F;
/* 10:11 */   public float relativeScore = 1.4E-45F;
/* 11:12 */   public boolean soldAllStocks = false;
/* 12:   */   
/* 13:   */   public AlgorithmScore(float refernceScore, float absoluteScore, boolean soldAllStocks)
/* 14:   */   {
/* 15:15 */     this.refernceScore = refernceScore;
/* 16:16 */     this.absoluteScore = absoluteScore;
/* 17:17 */     this.relativeScore = (this.absoluteScore / this.refernceScore * 100.0F);
/* 18:18 */     MainLogger.info("Relative score is: " + this.relativeScore + " %");
/* 19:19 */     this.soldAllStocks = soldAllStocks;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String toString()
/* 23:   */   {
/* 24:23 */     String str = "";
/* 25:24 */     str = "SCORE: " + this.absoluteScore + "/" + this.refernceScore + "(" + this.relativeScore + ")";
/* 26:25 */     if (!this.soldAllStocks) {
/* 27:26 */       str = str + "NOT_ALL_STOCKS_SOLD";
/* 28:   */     } else {
/* 29:28 */       str = str + "ALL_STOCKS_SOLD";
/* 30:   */     }
/* 31:30 */     return str;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.algorithm.configurations.AlgorithmScore
 * JD-Core Version:    0.7.0.1
 */
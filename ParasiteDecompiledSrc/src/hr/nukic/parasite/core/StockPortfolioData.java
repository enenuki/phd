/*  1:   */ package hr.nukic.parasite.core;
/*  2:   */ 
/*  3:   */ public class StockPortfolioData
/*  4:   */ {
/*  5:   */   public String ticker;
/*  6:   */   public float marketPrice;
/*  7:   */   public float buyPrice;
/*  8:   */   public int amount;
/*  9:   */   public float marketValue;
/* 10:   */   public float buyValue;
/* 11:   */   public float profit;
/* 12:   */   public float profitPercentage;
/* 13:   */   public float portfolioValuePercentage;
/* 14:   */   
/* 15:   */   public String toString()
/* 16:   */   {
/* 17:22 */     String s = "";
/* 18:23 */     s = "TICKER=" + this.ticker + " \t PROFIT=" + this.profit + "kn (" + this.profitPercentage + " %)\t PRICE=" + this.marketPrice + "\t BUY_PRICE=" + this.buyPrice + "\t VALUE=" + 
/* 19:24 */       this.marketValue + "\t BUY_VALUE=" + this.buyValue + "\t AMOUNT=" + this.amount + "\t" + "\n";
/* 20:25 */     return s;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public StockPortfolioData() {}
/* 24:   */   
/* 25:   */   public StockPortfolioData(String ticker, float marketPrice, float buyPrice, int amount)
/* 26:   */   {
/* 27:33 */     this.ticker = ticker;
/* 28:34 */     this.marketPrice = marketPrice;
/* 29:35 */     this.buyPrice = buyPrice;
/* 30:36 */     this.amount = amount;
/* 31:   */     
/* 32:38 */     calculateImplicitStockData();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void updatePriceAndImplicitData(float newPrice)
/* 36:   */   {
/* 37:44 */     this.marketPrice = newPrice;
/* 38:45 */     calculateImplicitStockData();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void update(float newPrice, int amount, float feeValue)
/* 42:   */   {
/* 43:50 */     this.marketPrice = newPrice;
/* 44:51 */     this.amount += amount;
/* 45:52 */     this.buyPrice = ((this.buyValue + newPrice * amount + feeValue) / this.amount);
/* 46:53 */     calculateImplicitStockData();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void calculateImplicitStockData()
/* 50:   */   {
/* 51:58 */     this.marketValue = (this.marketPrice * this.amount);
/* 52:59 */     this.buyValue = (this.buyPrice * this.amount);
/* 53:60 */     this.profit = (this.marketValue - this.buyValue);
/* 54:61 */     this.profitPercentage = (this.profit / this.buyValue * 100.0F);
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.StockPortfolioData
 * JD-Core Version:    0.7.0.1
 */
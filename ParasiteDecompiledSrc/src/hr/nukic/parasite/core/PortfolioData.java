/*   1:    */ package hr.nukic.parasite.core;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import nukic.parasite.utils.MainLogger;
/*   8:    */ 
/*   9:    */ public class PortfolioData
/*  10:    */ {
/*  11: 11 */   public List<StockPortfolioData> stockInfo = new ArrayList(3);
/*  12:    */   public float cash;
/*  13:    */   public float cashPercentage;
/*  14:    */   public float totalMarketValue;
/*  15:    */   public float totalBuyValue;
/*  16:    */   public float totalProfitPercent;
/*  17:    */   public float totalProfit;
/*  18: 20 */   public Date lastRefresh = new Date(0L);
/*  19:    */   
/*  20:    */   public int getOwnedAmount(String ticker)
/*  21:    */   {
/*  22: 23 */     int ret = 0;
/*  23: 24 */     Iterator<StockPortfolioData> i = this.stockInfo.iterator();
/*  24: 25 */     while (i.hasNext())
/*  25:    */     {
/*  26: 26 */       StockPortfolioData spd = (StockPortfolioData)i.next();
/*  27: 27 */       if (spd.ticker.equals(ticker)) {
/*  28: 28 */         ret = spd.amount;
/*  29:    */       }
/*  30:    */     }
/*  31: 32 */     return ret;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean isTickerOwned(String ticker)
/*  35:    */   {
/*  36: 36 */     boolean owned = false;
/*  37: 37 */     Iterator<StockPortfolioData> i = this.stockInfo.iterator();
/*  38: 38 */     while (i.hasNext())
/*  39:    */     {
/*  40: 39 */       StockPortfolioData spd = (StockPortfolioData)i.next();
/*  41: 40 */       if (spd.ticker.equals(ticker))
/*  42:    */       {
/*  43: 41 */         owned = true;
/*  44: 42 */         break;
/*  45:    */       }
/*  46:    */     }
/*  47: 46 */     return owned;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void print()
/*  51:    */   {
/*  52: 50 */     MainLogger.info(toString());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String toString()
/*  56:    */   {
/*  57: 54 */     String str = "";
/*  58: 55 */     str = str + "\n\n-------------------------------------------------------------------------------------------------------------------------------------------------\n";
/*  59: 56 */     Iterator<StockPortfolioData> i = this.stockInfo.iterator();
/*  60: 57 */     while (i.hasNext())
/*  61:    */     {
/*  62: 58 */       StockPortfolioData spd = (StockPortfolioData)i.next();
/*  63: 59 */       str = str + spd.toString();
/*  64:    */     }
/*  65: 62 */     str = str + "-------------------------------------------------------------------------------------------------------------------------------------------------\n";
/*  66: 63 */     str = str + "CASH = " + this.cash + "  PERCENTAGE=" + this.cashPercentage + "%\n";
/*  67: 64 */     str = str + "-------------------------------------------------------------------------------------------------------------------------------------------------\n";
/*  68: 65 */     str = str + "TOTAL_VALUE=" + this.totalMarketValue + "kn  TOTAL_BUY_VALUE=" + this.totalBuyValue + "kn  TOTAL_PROFIT=" + 
/*  69: 66 */       this.totalProfit + "kn (" + this.totalProfitPercent + "%) \n";
/*  70: 67 */     str = str + "-------------------------------------------------------------------------------------------------------------------------------------------------\n";
/*  71:    */     
/*  72: 69 */     return str;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public PortfolioData() {}
/*  76:    */   
/*  77:    */   public PortfolioData(List<StockPortfolioData> stockInfo, float cash)
/*  78:    */   {
/*  79: 76 */     this.stockInfo = stockInfo;
/*  80: 77 */     this.cash = cash;
/*  81:    */     
/*  82:    */ 
/*  83: 80 */     calculateImplicitPortfolioData();
/*  84:    */     
/*  85:    */ 
/*  86: 83 */     Iterator<StockPortfolioData> j = this.stockInfo.iterator();
/*  87: 84 */     while (j.hasNext())
/*  88:    */     {
/*  89: 85 */       StockPortfolioData stockData = (StockPortfolioData)j.next();
/*  90: 86 */       stockData.portfolioValuePercentage = (stockData.marketValue / this.totalMarketValue * 100.0F);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public PortfolioData(float cash)
/*  95:    */   {
/*  96: 92 */     this.cash = cash;
/*  97:    */     
/*  98:    */ 
/*  99: 95 */     calculateImplicitPortfolioData();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void updatePortfolioAfterOrderExecution(Order executedOrder, Date time, float feeValue)
/* 103:    */   {
/* 104:102 */     if (executedOrder.type == OrderType.BUY)
/* 105:    */     {
/* 106:104 */       this.cash = (this.cash - executedOrder.amount * executedOrder.price - feeValue);
/* 107:    */       
/* 108:    */ 
/* 109:107 */       StockPortfolioData existingStockData = null;
/* 110:108 */       Iterator<StockPortfolioData> i = this.stockInfo.iterator();
/* 111:109 */       while (i.hasNext())
/* 112:    */       {
/* 113:110 */         StockPortfolioData stockData = (StockPortfolioData)i.next();
/* 114:111 */         if (stockData.ticker.equals(executedOrder.ticker)) {
/* 115:112 */           existingStockData = stockData;
/* 116:    */         }
/* 117:    */       }
/* 118:115 */       if (existingStockData != null)
/* 119:    */       {
/* 120:117 */         existingStockData.update(executedOrder.price, executedOrder.amount, feeValue);
/* 121:    */       }
/* 122:    */       else
/* 123:    */       {
/* 124:122 */         float buyPrice = (executedOrder.price * executedOrder.amount + feeValue) / executedOrder.amount;
/* 125:123 */         StockPortfolioData newStockData = new StockPortfolioData(executedOrder.ticker, executedOrder.price, 
/* 126:124 */           buyPrice, executedOrder.amount);
/* 127:    */         
/* 128:126 */         this.stockInfo.add(newStockData);
/* 129:    */       }
/* 130:    */     }
/* 131:132 */     else if (executedOrder.type == OrderType.SELL)
/* 132:    */     {
/* 133:134 */       this.cash = (this.cash + executedOrder.amount * executedOrder.price - feeValue);
/* 134:    */       
/* 135:    */ 
/* 136:137 */       StockPortfolioData existingStockData = null;
/* 137:138 */       Iterator<StockPortfolioData> i = this.stockInfo.iterator();
/* 138:139 */       while (i.hasNext())
/* 139:    */       {
/* 140:140 */         StockPortfolioData stockData = (StockPortfolioData)i.next();
/* 141:141 */         if (stockData.ticker.equals(executedOrder.ticker)) {
/* 142:142 */           existingStockData = stockData;
/* 143:    */         }
/* 144:    */       }
/* 145:146 */       if (existingStockData == null)
/* 146:    */       {
/* 147:147 */         MainLogger.error("We sold a stock that we do not own! Something is seriously wrong.");
/* 148:148 */         System.exit(-1);
/* 149:    */       }
/* 150:151 */       if (executedOrder.amount > existingStockData.amount)
/* 151:    */       {
/* 152:152 */         MainLogger.error("We sold more stocks than we own! Something is seriously wrong.");
/* 153:153 */         System.exit(-1);
/* 154:    */       }
/* 155:154 */       else if (executedOrder.amount == existingStockData.amount)
/* 156:    */       {
/* 157:155 */         MainLogger.debug("We sold all stocks that we have...(of this type)");
/* 158:156 */         this.stockInfo.remove(existingStockData);
/* 159:    */       }
/* 160:158 */       else if (executedOrder.amount < existingStockData.amount)
/* 161:    */       {
/* 162:159 */         MainLogger.debug("We sold some of the stocks that we have...(of this type)");
/* 163:160 */         existingStockData.update(executedOrder.price, existingStockData.amount - executedOrder.amount, 
/* 164:161 */           feeValue);
/* 165:    */       }
/* 166:    */     }
/* 167:169 */     calculateImplicitPortfolioData();
/* 168:    */     
/* 169:    */ 
/* 170:172 */     Iterator<StockPortfolioData> j = this.stockInfo.iterator();
/* 171:173 */     while (j.hasNext())
/* 172:    */     {
/* 173:174 */       StockPortfolioData stockData = (StockPortfolioData)j.next();
/* 174:175 */       stockData.portfolioValuePercentage = (stockData.marketValue / this.totalMarketValue * 100.0F);
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void updatePortfolioAfterPriceChange(String ticker, float lastPrice)
/* 179:    */   {
/* 180:185 */     Iterator<StockPortfolioData> i = this.stockInfo.iterator();
/* 181:186 */     while (i.hasNext())
/* 182:    */     {
/* 183:187 */       StockPortfolioData stockData = (StockPortfolioData)i.next();
/* 184:188 */       if (stockData.ticker.equals(ticker)) {
/* 185:189 */         stockData.updatePriceAndImplicitData(lastPrice);
/* 186:    */       }
/* 187:    */     }
/* 188:195 */     calculateImplicitPortfolioData();
/* 189:    */     
/* 190:    */ 
/* 191:198 */     Iterator<StockPortfolioData> j = this.stockInfo.iterator();
/* 192:199 */     while (j.hasNext())
/* 193:    */     {
/* 194:200 */       StockPortfolioData stockData = (StockPortfolioData)j.next();
/* 195:201 */       stockData.portfolioValuePercentage = (stockData.marketValue / this.totalMarketValue * 100.0F);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   private void calculateImplicitPortfolioData()
/* 200:    */   {
/* 201:209 */     this.totalMarketValue = this.cash;
/* 202:210 */     Iterator<StockPortfolioData> i = this.stockInfo.iterator();
/* 203:211 */     while (i.hasNext())
/* 204:    */     {
/* 205:212 */       StockPortfolioData stockData = (StockPortfolioData)i.next();
/* 206:213 */       this.totalMarketValue += stockData.marketValue;
/* 207:    */     }
/* 208:217 */     this.totalBuyValue = this.cash;
/* 209:218 */     Iterator<StockPortfolioData> j = this.stockInfo.iterator();
/* 210:219 */     while (j.hasNext())
/* 211:    */     {
/* 212:220 */       StockPortfolioData stockData = (StockPortfolioData)j.next();
/* 213:221 */       this.totalBuyValue += stockData.buyValue;
/* 214:    */     }
/* 215:225 */     this.totalProfit = (this.totalMarketValue - this.totalBuyValue);
/* 216:    */     
/* 217:    */ 
/* 218:228 */     this.totalProfitPercent = (this.totalProfit / this.totalBuyValue * 100.0F);
/* 219:    */     
/* 220:    */ 
/* 221:231 */     this.cashPercentage = (this.cash / this.totalMarketValue * 100.0F);
/* 222:    */   }
/* 223:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.PortfolioData
 * JD-Core Version:    0.7.0.1
 */
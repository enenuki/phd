/*   1:    */ package hr.nukic.parasite.core;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Properties;
/*   6:    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*   7:    */ import nukic.parasite.utils.MainLogger;
/*   8:    */ 
/*   9:    */ public class ImplicitStockMarketData
/*  10:    */ {
/*  11: 28 */   StockMarketData marketData = null;
/*  12: 29 */   float bidAskValuePercentage = 0.0F;
/*  13: 30 */   public float averageIntradayVolatility = 0.0F;
/*  14: 35 */   public boolean useDefaultBidAskValuePercentage = Boolean.parseBoolean(
/*  15: 36 */     ParasiteManager.getProperty("USE_DEFAULT_BID_ASK_VALUE_PERCENTAGE"));
/*  16: 38 */   public float defaultBidAskValuePercentage = Float.parseFloat(
/*  17: 39 */     ParasiteManager.getProperty("DEFAULT_BID_ASK_VALUE_PERCENTAGE"));
/*  18: 42 */   private float currentRecordPrice = 0.0F;
/*  19: 43 */   private float previousRecordPrice = 0.0F;
/*  20: 44 */   public float absolutePriceChange = 0.0F;
/*  21: 45 */   public float relativePriceChange = 0.0F;
/*  22:    */   public float bidValue;
/*  23:    */   public float askValue;
/*  24:    */   public float baRatio;
/*  25: 53 */   public float baRatioSma = 0.0F;
/*  26: 54 */   public float baRatioSmaPrev = 0.0F;
/*  27: 55 */   public float baRatioSmaDelta = 0.0F;
/*  28: 57 */   public float baRatioWma = 0.0F;
/*  29: 59 */   public float baRatioWmaPrev = 0.0F;
/*  30: 60 */   public float baRatioWmaDelta = 0.0F;
/*  31: 62 */   public ConcurrentLinkedQueue<Float> bidValueQueue = new ConcurrentLinkedQueue();
/*  32: 63 */   public ConcurrentLinkedQueue<Float> askValueQueue = new ConcurrentLinkedQueue();
/*  33: 64 */   public ConcurrentLinkedQueue<Float> baRatioQueue = new ConcurrentLinkedQueue();
/*  34:    */   
/*  35:    */   public ImplicitStockMarketData(StockMarketData marketData)
/*  36:    */   {
/*  37: 67 */     this.marketData = marketData;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ImplicitStockMarketData() {}
/*  41:    */   
/*  42:    */   public void calculateBidAskRelatedData()
/*  43:    */   {
/*  44: 74 */     String time = this.marketData.getTimeString();
/*  45: 75 */     String date = this.marketData.getDateString();
/*  46:    */     
/*  47: 77 */     MainLogger.debug("*********************************************************************");
/*  48: 78 */     MainLogger.debug("DATE: " + date + "  TIME:" + time);
/*  49:    */     
/*  50: 80 */     calculateBidAskValuePercentage();
/*  51:    */     
/*  52: 82 */     this.bidValue = calculateBidOrdersValue(this.bidAskValuePercentage);
/*  53: 83 */     MainLogger.debug("BID value = " + this.bidValue);
/*  54: 84 */     this.askValue = calculateAskOrdersValue(this.bidAskValuePercentage);
/*  55: 85 */     MainLogger.debug("ASK value = " + this.askValue);
/*  56: 86 */     this.baRatio = (this.bidValue / this.askValue);
/*  57: 87 */     MainLogger.debug("BA ratio = " + this.baRatio);
/*  58:    */     
/*  59: 89 */     calculateMovingAverages();
/*  60: 91 */     if (this.baRatioSmaPrev != 0.0F) {
/*  61: 92 */       this.baRatioSmaDelta = (this.baRatioSma - this.baRatioSmaPrev);
/*  62:    */     }
/*  63: 95 */     if (this.baRatioWmaPrev != 0.0F) {
/*  64: 96 */       this.baRatioWmaDelta = (this.baRatioWma - this.baRatioWmaPrev);
/*  65:    */     }
/*  66: 99 */     enqueueBidAskValues(this.bidValue, this.askValue, this.baRatio);
/*  67:    */     
/*  68:101 */     this.baRatioSmaPrev = this.baRatioSma;
/*  69:102 */     this.baRatioWmaPrev = this.baRatioWma;
/*  70:103 */     System.gc();
/*  71:    */   }
/*  72:    */   
/*  73:    */   private void enqueueBidAskValues(float bidValue, float askValue, float baRatio)
/*  74:    */   {
/*  75:109 */     this.bidValueQueue.add(new Float(bidValue));
/*  76:110 */     this.askValueQueue.add(new Float(askValue));
/*  77:111 */     this.baRatioQueue.add(new Float(baRatio));
/*  78:    */     
/*  79:113 */     int queueSize = 
/*  80:114 */       Integer.parseInt(ParasiteManager.getInstance().properties
/*  81:115 */       .getProperty("QUEUE_SIZE"));
/*  82:116 */     if (this.bidValueQueue.size() > queueSize) {
/*  83:117 */       this.bidValueQueue.remove();
/*  84:    */     }
/*  85:119 */     if (this.askValueQueue.size() > queueSize) {
/*  86:120 */       this.askValueQueue.remove();
/*  87:    */     }
/*  88:122 */     if (this.baRatioQueue.size() > queueSize) {
/*  89:123 */       this.baRatioQueue.remove();
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   private void calculateBidAskValuePercentage()
/*  94:    */   {
/*  95:128 */     if (this.useDefaultBidAskValuePercentage)
/*  96:    */     {
/*  97:130 */       MainLogger.debug("Using default value for DEFAULT_BID_ASK_VALUE_PERCENTAGE");
/*  98:131 */       this.bidAskValuePercentage = this.defaultBidAskValuePercentage;
/*  99:    */     }
/* 100:    */     else
/* 101:    */     {
/* 102:133 */       this.averageIntradayVolatility = 
/* 103:134 */         StockMetrics.getStockMetrics(this.marketData.ticker).averageIntradayVolatility;
/* 104:135 */       this.bidAskValuePercentage = (this.averageIntradayVolatility / 2.0F);
/* 105:136 */       if (this.bidAskValuePercentage == 0.0F)
/* 106:    */       {
/* 107:138 */         MainLogger.debug("Using default value for DEFAULT_BID_ASK_VALUE_PERCENTAGE 2");
/* 108:139 */         this.bidAskValuePercentage = this.defaultBidAskValuePercentage;
/* 109:    */       }
/* 110:142 */       MainLogger.debug(
/* 111:143 */         "Using bid Ask Value Percentage:" + this.bidAskValuePercentage);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public float calculateAskOrdersValue(float percent)
/* 116:    */   {
/* 117:150 */     float value = 0.0F;
/* 118:151 */     if (!this.marketData.askList.isEmpty())
/* 119:    */     {
/* 120:152 */       Order lowestAsk = (Order)this.marketData.askList.get(0);
/* 121:153 */       Iterator<Order> i = this.marketData.askList.iterator();
/* 122:154 */       while (i.hasNext())
/* 123:    */       {
/* 124:155 */         Order o = (Order)i.next();
/* 125:156 */         if (o.price <= lowestAsk.price * (1.0D + percent / 100.0D)) {
/* 126:157 */           value += o.amount * o.price;
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:161 */     return value;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public float calculateBidOrdersValue(float percent)
/* 134:    */   {
/* 135:166 */     float value = 0.0F;
/* 136:167 */     if (!this.marketData.bidList.isEmpty())
/* 137:    */     {
/* 138:168 */       Order highestBid = (Order)this.marketData.bidList.get(0);
/* 139:169 */       Iterator<Order> i = this.marketData.bidList.iterator();
/* 140:170 */       while (i.hasNext())
/* 141:    */       {
/* 142:171 */         Order o = (Order)i.next();
/* 143:172 */         if (o.price >= highestBid.price * (1.0D - percent / 100.0D)) {
/* 144:173 */           value += o.amount * o.price;
/* 145:    */         }
/* 146:    */       }
/* 147:    */     }
/* 148:177 */     return value;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public float calculateBidAskOrdersDifference(float percent)
/* 152:    */   {
/* 153:182 */     float diff = 0.0F;
/* 154:183 */     float bidValue = calculateBidOrdersValue(percent);
/* 155:184 */     float askValue = calculateAskOrdersValue(percent);
/* 156:185 */     diff = bidValue - askValue;
/* 157:186 */     return diff;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String toCsvFileString()
/* 161:    */   {
/* 162:190 */     String s = "";
/* 163:    */     
/* 164:192 */     s = s + "BA_RATIO," + this.bidValue / this.askValue;
/* 165:    */     
/* 166:    */ 
/* 167:    */ 
/* 168:196 */     s = s + ",SMA_REL_DELTA," + this.baRatioSmaDelta + ",WMA_REL_DELTA," + 
/* 169:197 */       this.baRatioSmaDelta + "\n";
/* 170:    */     
/* 171:199 */     return s;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void calculateSimpleMovingAverage()
/* 175:    */   {
/* 176:203 */     Iterator<Float> i = this.baRatioQueue.iterator();
/* 177:204 */     while (i.hasNext())
/* 178:    */     {
/* 179:205 */       float baRatio = ((Float)i.next()).floatValue();
/* 180:206 */       this.baRatioSma += baRatio;
/* 181:    */     }
/* 182:210 */     if (this.baRatioQueue.size() != 0) {
/* 183:211 */       this.baRatioSma /= this.baRatioQueue.size();
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void calculateWeightedMovingAverage()
/* 188:    */   {
/* 189:221 */     Iterator<Float> i = this.baRatioQueue.iterator();
/* 190:222 */     int c = 1;
/* 191:223 */     int sum = 0;
/* 192:224 */     while (i.hasNext())
/* 193:    */     {
/* 194:226 */       float weightedBaRatio = ((Float)i.next()).floatValue() * c;
/* 195:    */       
/* 196:    */ 
/* 197:    */ 
/* 198:230 */       this.baRatioWma += weightedBaRatio;
/* 199:231 */       sum += c;
/* 200:232 */       c++;
/* 201:    */     }
/* 202:236 */     if (sum != 0) {
/* 203:238 */       this.baRatioWma /= sum;
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void calculateMovingAverages()
/* 208:    */   {
/* 209:245 */     calculateSimpleMovingAverage();
/* 210:246 */     calculateWeightedMovingAverage();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public synchronized StockMarketData getMarketData()
/* 214:    */   {
/* 215:252 */     return this.marketData;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public synchronized float getBidValue()
/* 219:    */   {
/* 220:256 */     return this.bidValue;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public synchronized float getAskValue()
/* 224:    */   {
/* 225:260 */     return this.askValue;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public synchronized float getBaRatio()
/* 229:    */   {
/* 230:264 */     return this.baRatio;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public synchronized float getBaRatioSma()
/* 234:    */   {
/* 235:268 */     return this.baRatioSma;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public synchronized float getBaRatioSmaPrev()
/* 239:    */   {
/* 240:272 */     return this.baRatioSmaPrev;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public synchronized float getBaRatioSmaDelta()
/* 244:    */   {
/* 245:276 */     return this.baRatioSmaDelta;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public synchronized float getBaRatioWma()
/* 249:    */   {
/* 250:280 */     return this.baRatioWma;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public synchronized float getBaRatioWmaPrev()
/* 254:    */   {
/* 255:284 */     return this.baRatioWmaPrev;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public synchronized float getBaRatioWmaDelta()
/* 259:    */   {
/* 260:288 */     return this.baRatioWmaDelta;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public synchronized float getRealtiveVolatility()
/* 264:    */   {
/* 265:292 */     return this.averageIntradayVolatility;
/* 266:    */   }
/* 267:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.ImplicitStockMarketData
 * JD-Core Version:    0.7.0.1
 */
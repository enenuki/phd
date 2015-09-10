/*   1:    */ package hr.nukic.parasite.accounts;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.accounts.templates.TradingAccount;
/*   4:    */ import hr.nukic.parasite.core.Order;
/*   5:    */ import hr.nukic.parasite.core.OrderType;
/*   6:    */ import hr.nukic.parasite.core.PortfolioData;
/*   7:    */ import hr.nukic.parasite.core.QueryStatus;
/*   8:    */ import hr.nukic.parasite.core.StockPortfolioData;
/*   9:    */ import hr.nukic.parasite.core.Transaction;
/*  10:    */ import hr.nukic.parasite.simulator.MarketSimulator;
/*  11:    */ import hr.nukic.parasite.simulator.OrderExecutionSubscriber;
/*  12:    */ import hr.nukic.parasite.simulator.PriceChangeSubscriber;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import nukic.parasite.utils.MainLogger;
/*  19:    */ 
/*  20:    */ public class SimulatedTradingAccount
/*  21:    */   extends TradingAccount
/*  22:    */   implements PriceChangeSubscriber, OrderExecutionSubscriber
/*  23:    */ {
/*  24:    */   public MarketSimulator simulator;
/*  25:    */   
/*  26:    */   public SimulatedTradingAccount(PortfolioData portfolio, List<Order> ownOrders, MarketSimulator simulator, float feePercentage, float minFee)
/*  27:    */   {
/*  28: 30 */     super(portfolio, ownOrders, feePercentage, minFee);
/*  29:    */     
/*  30: 32 */     this.simulator = simulator;
/*  31:    */     
/*  32:    */ 
/*  33: 35 */     Iterator<StockPortfolioData> i = portfolio.stockInfo.iterator();
/*  34: 36 */     while (i.hasNext())
/*  35:    */     {
/*  36: 37 */       StockPortfolioData spd = (StockPortfolioData)i.next();
/*  37: 38 */       if (spd.ticker.equals(simulator.ticker))
/*  38:    */       {
/*  39: 39 */         MainLogger.debug("Adding self to simulator's price subscriber list...");
/*  40: 40 */         simulator.priceChangeSubscriptions.add(this);
/*  41:    */       }
/*  42:    */     }
/*  43: 45 */     Iterator<Order> j = ownOrders.iterator();
/*  44: 46 */     while (j.hasNext())
/*  45:    */     {
/*  46: 47 */       Order order = (Order)j.next();
/*  47: 48 */       if (!simulator.hasOrderExecutionSubscription(order, this))
/*  48:    */       {
/*  49: 49 */         MainLogger.debug("Adding self to simulator's order subscriber list...");
/*  50: 50 */         simulator.orderExecutionSubscriptions.put(order, this);
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean login()
/*  56:    */   {
/*  57: 57 */     return true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean logout()
/*  61:    */   {
/*  62: 62 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public QueryStatus setBuyOrder(String ticker, float price, int amount, Date expiryDate)
/*  66:    */   {
/*  67: 67 */     Order order = new Order(ticker, OrderType.BUY, amount, price);
/*  68: 68 */     this.ownOrders.add(order);
/*  69:    */     
/*  70: 70 */     float availableCash = readAvailableCash();
/*  71: 72 */     if (availableCash < amount * price + calculateFee(amount * price))
/*  72:    */     {
/*  73: 73 */       MainLogger.info("Not enough available money to set this buy order: " + order.toString());
/*  74: 74 */       return QueryStatus.NOT_ENOUGH_MONEY;
/*  75:    */     }
/*  76: 76 */     this.simulator.setBuyOrder(this, order);
/*  77: 77 */     return QueryStatus.SUCCESS;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public QueryStatus setSellOrder(String ticker, float price, int amount, Date expiryDate)
/*  81:    */   {
/*  82: 82 */     Order order = new Order(ticker, OrderType.SELL, amount, price);
/*  83: 84 */     if (this.portfolio.getOwnedAmount(ticker) < amount)
/*  84:    */     {
/*  85: 85 */       MainLogger.info(
/*  86: 86 */         "Cannot execute this order: " + order.toString() + "\n We do not own that amount of stocks of that type!");
/*  87: 87 */       return QueryStatus.AMOUNT_TOO_GREAT;
/*  88:    */     }
/*  89: 90 */     this.simulator.setSellOrder(this, order);
/*  90: 91 */     return QueryStatus.SUCCESS;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public QueryStatus setOrder(Order order)
/*  94:    */   {
/*  95:100 */     if (order.type == OrderType.SELL)
/*  96:    */     {
/*  97:101 */       if ((this.portfolio.getOwnedAmount(order.ticker) < order.amount) && (!order.isDelayed))
/*  98:    */       {
/*  99:102 */         MainLogger.info(
/* 100:103 */           "Cannot execute this order: " + order.toString() + "\n We do not own that amount of stocks of that type!");
/* 101:104 */         return QueryStatus.AMOUNT_TOO_GREAT;
/* 102:    */       }
/* 103:106 */       this.simulator.setSellOrder(this, order);
/* 104:    */     }
/* 105:108 */     else if (order.type == OrderType.BUY)
/* 106:    */     {
/* 107:109 */       float availableCash = readAvailableCash();
/* 108:110 */       if ((availableCash < order.amount * order.price + calculateFee(order.amount * order.price)) && 
/* 109:111 */         (!order.isDelayed))
/* 110:    */       {
/* 111:112 */         MainLogger.info("Not enough available money to set this buy order: " + order.toString());
/* 112:113 */         return QueryStatus.NOT_ENOUGH_MONEY;
/* 113:    */       }
/* 114:116 */       this.simulator.setBuyOrder(this, order);
/* 115:    */     }
/* 116:119 */     return QueryStatus.SUCCESS;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public QueryStatus cancelOrder(Order order)
/* 120:    */   {
/* 121:124 */     if (order.type == OrderType.SELL) {
/* 122:125 */       this.simulator.cancelSellOrder(this, order);
/* 123:126 */     } else if (order.type == OrderType.BUY) {
/* 124:127 */       this.simulator.cancelBuyOrder(this, order);
/* 125:    */     }
/* 126:130 */     return QueryStatus.SUCCESS;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public QueryStatus cancelBuyOrder(String ticker, float price, int amount, Date expiryDate)
/* 130:    */   {
/* 131:135 */     Order orderToBeCanceled = new Order(ticker, OrderType.BUY, amount, price);
/* 132:    */     
/* 133:137 */     Iterator<Order> i = this.ownOrders.iterator();
/* 134:138 */     while (i.hasNext())
/* 135:    */     {
/* 136:139 */       Order order = (Order)i.next();
/* 137:140 */       if (order.equals(orderToBeCanceled)) {
/* 138:141 */         i.remove();
/* 139:    */       }
/* 140:    */     }
/* 141:145 */     this.simulator.cancelBuyOrder(this, orderToBeCanceled);
/* 142:146 */     return QueryStatus.SUCCESS;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public QueryStatus cancelBuyOrder(String ticker)
/* 146:    */   {
/* 147:152 */     Iterator<Order> i = this.ownOrders.iterator();
/* 148:153 */     while (i.hasNext())
/* 149:    */     {
/* 150:154 */       Order order = (Order)i.next();
/* 151:155 */       if ((order.ticker.equals(ticker)) && (order.type == OrderType.BUY)) {
/* 152:156 */         i.remove();
/* 153:    */       }
/* 154:    */     }
/* 155:161 */     this.simulator.cancelBuyOrder(this, ticker);
/* 156:162 */     return QueryStatus.SUCCESS;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public QueryStatus cancelSellOrder(String ticker, float price, int amount, Date expiryDate)
/* 160:    */   {
/* 161:167 */     Order orderToBeCanceled = new Order(ticker, OrderType.SELL, amount, price);
/* 162:    */     
/* 163:169 */     Iterator<Order> i = this.ownOrders.iterator();
/* 164:170 */     while (i.hasNext())
/* 165:    */     {
/* 166:171 */       Order order = (Order)i.next();
/* 167:172 */       if (order.equals(orderToBeCanceled)) {
/* 168:173 */         i.remove();
/* 169:    */       }
/* 170:    */     }
/* 171:177 */     this.simulator.cancelSellOrder(this, orderToBeCanceled);
/* 172:178 */     return QueryStatus.SUCCESS;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public QueryStatus cancelSellOrder(String ticker)
/* 176:    */   {
/* 177:185 */     Iterator<Order> i = this.ownOrders.iterator();
/* 178:186 */     while (i.hasNext())
/* 179:    */     {
/* 180:187 */       Order order = (Order)i.next();
/* 181:188 */       if ((order.ticker.equals(ticker)) && (order.type == OrderType.SELL)) {
/* 182:189 */         i.remove();
/* 183:    */       }
/* 184:    */     }
/* 185:193 */     this.simulator.cancelSellOrder(this, ticker);
/* 186:194 */     return QueryStatus.SUCCESS;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<Order> readPendingOrdersInfo()
/* 190:    */   {
/* 191:199 */     return this.ownOrders;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<Order> readPendingOrdersInfoForTicker(String ticker)
/* 195:    */   {
/* 196:204 */     List<Order> orders = new ArrayList();
/* 197:    */     
/* 198:206 */     Iterator<Order> i = this.ownOrders.iterator();
/* 199:207 */     while (i.hasNext())
/* 200:    */     {
/* 201:208 */       Order o = (Order)i.next();
/* 202:209 */       if (o.ticker.equals(ticker)) {
/* 203:210 */         orders.add(o);
/* 204:    */       }
/* 205:    */     }
/* 206:214 */     return orders;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public List<Transaction> readCompletedOrdersInfo()
/* 210:    */   {
/* 211:219 */     return this.ownTransactions;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public List<Transaction> readCompletedOrdersInfoForTicker(String ticker)
/* 215:    */   {
/* 216:224 */     List<Transaction> transactions = new ArrayList();
/* 217:    */     
/* 218:226 */     Iterator<Transaction> i = this.ownTransactions.iterator();
/* 219:227 */     while (i.hasNext())
/* 220:    */     {
/* 221:228 */       Transaction t = (Transaction)i.next();
/* 222:229 */       if (t.ticker.equals(ticker)) {
/* 223:230 */         transactions.add(t);
/* 224:    */       }
/* 225:    */     }
/* 226:234 */     return transactions;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public float readCashInPendingBuyOrders()
/* 230:    */   {
/* 231:239 */     float reservedCash = 0.0F;
/* 232:240 */     Iterator<Order> i = this.ownOrders.iterator();
/* 233:241 */     while (i.hasNext())
/* 234:    */     {
/* 235:242 */       Order o = (Order)i.next();
/* 236:243 */       if (o.type == OrderType.BUY) {
/* 237:244 */         reservedCash = reservedCash + o.amount * o.price + calculateFee(o.amount * o.price);
/* 238:    */       }
/* 239:    */     }
/* 240:248 */     return reservedCash;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public float readCashInPendingSellOrders()
/* 244:    */   {
/* 245:253 */     float cashAfterSell = 0.0F;
/* 246:254 */     Iterator<Order> i = this.ownOrders.iterator();
/* 247:255 */     while (i.hasNext())
/* 248:    */     {
/* 249:256 */       Order o = (Order)i.next();
/* 250:257 */       if (o.type == OrderType.BUY) {
/* 251:258 */         cashAfterSell = cashAfterSell + o.amount * o.price - calculateFee(o.amount * o.price);
/* 252:    */       }
/* 253:    */     }
/* 254:261 */     return cashAfterSell;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public PortfolioData readPortfolioData()
/* 258:    */   {
/* 259:267 */     return this.portfolio;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public float readAvailableCash()
/* 263:    */   {
/* 264:272 */     float availableCash = this.portfolio.cash;
/* 265:273 */     Iterator<Order> i = this.ownOrders.iterator();
/* 266:274 */     while (i.hasNext())
/* 267:    */     {
/* 268:275 */       Order order = (Order)i.next();
/* 269:276 */       if (order.type == OrderType.BUY)
/* 270:    */       {
/* 271:277 */         float orderValue = order.amount * order.price;
/* 272:278 */         availableCash = availableCash - orderValue - calculateFee(orderValue);
/* 273:    */       }
/* 274:    */     }
/* 275:281 */     return availableCash;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void onOrderExecution(Order subscribedOrder, Date time)
/* 279:    */   {
/* 280:286 */     MainLogger.debug("Trading account " + this.id + ": Order " + subscribedOrder.toString() + " IS EXECUTED!");
/* 281:287 */     MainLogger.info("\n\n\nPortfolio before transaction:");
/* 282:288 */     this.portfolio.print();
/* 283:    */     
/* 284:    */ 
/* 285:291 */     Iterator<Order> i = this.ownOrders.iterator();
/* 286:292 */     while (i.hasNext())
/* 287:    */     {
/* 288:293 */       Order order = (Order)i.next();
/* 289:294 */       if (order.equals(subscribedOrder)) {
/* 290:295 */         i.remove();
/* 291:    */       }
/* 292:    */     }
/* 293:299 */     Transaction newTransaction = new Transaction(subscribedOrder.ticker, subscribedOrder.amount, 
/* 294:300 */       subscribedOrder.price, time);
/* 295:301 */     newTransaction.isOwn = true;
/* 296:302 */     newTransaction.type = subscribedOrder.type;
/* 297:303 */     this.ownTransactions.add(newTransaction);
/* 298:    */     
/* 299:    */ 
/* 300:306 */     float fee = calculateFee(subscribedOrder.price * subscribedOrder.amount);
/* 301:307 */     MainLogger.debug("Fee value = " + fee);
/* 302:    */     
/* 303:    */ 
/* 304:310 */     this.portfolio.updatePortfolioAfterOrderExecution(subscribedOrder, time, fee);
/* 305:311 */     MainLogger.debug("\n\n\nPortfolio AFTER transaction:");
/* 306:312 */     this.portfolio.print();
/* 307:315 */     if ((subscribedOrder.type == OrderType.BUY) && (!this.simulator.hasPriceSubscription(this)))
/* 308:    */     {
/* 309:316 */       MainLogger.debug("Adding self to simulator's order subscriber list...");
/* 310:317 */       this.simulator.priceChangeSubscriptions.add(this);
/* 311:    */     }
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void onPartialOrderExecution(Order executedOrder, int amount, Date time)
/* 315:    */   {
/* 316:325 */     Order partiallyExecuted = new Order(executedOrder.ticker, executedOrder.type, amount, executedOrder.price);
/* 317:326 */     onOrderExecution(partiallyExecuted, time);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public void onPriceChange(float newPrice, Date time)
/* 321:    */   {
/* 322:331 */     MainLogger.debug("Trading account " + this.id + ": Price change notification! New price is: " + newPrice);
/* 323:332 */     this.portfolio.updatePortfolioAfterPriceChange(this.simulator.ticker, newPrice);
/* 324:    */   }
/* 325:    */   
/* 326:    */   public long getSubscriberId()
/* 327:    */   {
/* 328:338 */     return this.id;
/* 329:    */   }
/* 330:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.SimulatedTradingAccount
 * JD-Core Version:    0.7.0.1
 */
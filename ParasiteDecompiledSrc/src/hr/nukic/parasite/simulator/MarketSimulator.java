/*   1:    */ package hr.nukic.parasite.simulator;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.accounts.SimulatedTradingAccount;
/*   4:    */ import hr.nukic.parasite.accounts.templates.TradingAccount;
/*   5:    */ import hr.nukic.parasite.core.Order;
/*   6:    */ import hr.nukic.parasite.core.StockMarketData;
/*   7:    */ import hr.nukic.parasite.core.Transaction;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Map.Entry;
/*  13:    */ import java.util.Set;
/*  14:    */ import nukic.parasite.utils.MainLogger;
/*  15:    */ 
/*  16:    */ public abstract class MarketSimulator
/*  17:    */ {
/*  18: 21 */   public SimulatorStatus status = SimulatorStatus.NOT_STRATED;
/*  19: 22 */   public int timeScale = 25;
/*  20:    */   public Date marketDay;
/*  21:    */   public Date startTime;
/*  22:    */   public Date endTime;
/*  23:    */   public String ticker;
/*  24:    */   public StockMarketData[] recordList;
/*  25: 29 */   public HashMap<Order, OrderExecutionSubscriber> orderExecutionSubscriptions = new HashMap();
/*  26: 30 */   public ArrayList<PriceChangeSubscriber> priceChangeSubscriptions = new ArrayList();
/*  27: 31 */   public ArrayList<MarketDataSubscriber> marketDataSubscriptions = new ArrayList();
/*  28: 32 */   public StockMarketData currentRecord = null;
/*  29: 33 */   public StockMarketData nextRecord = null;
/*  30: 34 */   public StockMarketData previousRecord = null;
/*  31: 35 */   public StockMarketData firstRecord = null;
/*  32: 36 */   public StockMarketData lastRecord = null;
/*  33: 37 */   protected StockMarketData maxPriceRecord = null;
/*  34: 38 */   protected StockMarketData minPriceRecord = null;
/*  35: 39 */   protected StockMarketData minPriceRecordBeforeMax = null;
/*  36: 40 */   public int currentRecordIndex = 0;
/*  37: 41 */   public long nextLinePeriod = 9223372036854775807L;
/*  38:    */   
/*  39:    */   public SimulatorStatus getStatus()
/*  40:    */   {
/*  41: 44 */     return this.status;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void notifyOrderSubscribers()
/*  45:    */   {
/*  46: 51 */     int transactionsHappened = 0;
/*  47: 52 */     if (this.previousRecord == null) {
/*  48: 54 */       transactionsHappened = this.currentRecord.transactionList.size();
/*  49:    */     } else {
/*  50: 57 */       transactionsHappened = this.currentRecord.transactionList.size() - this.previousRecord.transactionList.size();
/*  51:    */     }
/*  52: 59 */     if (transactionsHappened > 0)
/*  53:    */     {
/*  54: 61 */       MainLogger.debug("PRICE CHANGE: " + ((Transaction)this.currentRecord.transactionList.get(0)).price);
/*  55:    */       
/*  56: 63 */       Date currentTimeAndDate = getFullDateAndTime();
/*  57: 67 */       for (int k = transactionsHappened; k > 0; k--)
/*  58:    */       {
/*  59: 68 */         Transaction t = (Transaction)this.currentRecord.transactionList.get(k - 1);
/*  60:    */         
/*  61:    */ 
/*  62: 71 */         Iterator<Order> i = this.orderExecutionSubscriptions.keySet().iterator();
/*  63: 72 */         while (i.hasNext())
/*  64:    */         {
/*  65: 73 */           Order subscribedOrder = (Order)i.next();
/*  66: 76 */           if (((t.price == subscribedOrder.price) && (!subscribedOrder.isDelayed)) || (
/*  67: 77 */             (t.price == subscribedOrder.price) && (subscribedOrder.isDelayed) && 
/*  68: 78 */             (subscribedOrder.getValidFrom().getTime() <= currentTimeAndDate.getTime()))) {
/*  69: 81 */             if (t.amount >= subscribedOrder.amount)
/*  70:    */             {
/*  71: 82 */               ((OrderExecutionSubscriber)this.orderExecutionSubscriptions.get(subscribedOrder)).onOrderExecution(subscribedOrder, 
/*  72: 83 */                 t.time);
/*  73:    */               
/*  74:    */ 
/*  75: 86 */               i.remove();
/*  76:    */             }
/*  77:    */             else
/*  78:    */             {
/*  79: 90 */               ((OrderExecutionSubscriber)this.orderExecutionSubscriptions.get(subscribedOrder)).onPartialOrderExecution(
/*  80: 91 */                 subscribedOrder, t.amount, t.time);
/*  81:    */             }
/*  82:    */           }
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void notifyPriceChangeSubscribers()
/*  89:    */   {
/*  90:101 */     int transactionsHappened = 0;
/*  91:102 */     if (this.previousRecord == null) {
/*  92:103 */       transactionsHappened = this.currentRecord.transactionList.size();
/*  93:    */     } else {
/*  94:105 */       transactionsHappened = this.currentRecord.transactionList.size() - this.previousRecord.transactionList.size();
/*  95:    */     }
/*  96:107 */     if (transactionsHappened > 0)
/*  97:    */     {
/*  98:108 */       Transaction lastTransaction = (Transaction)this.currentRecord.transactionList.get(0);
/*  99:    */       
/* 100:    */ 
/* 101:111 */       Iterator<PriceChangeSubscriber> i = this.priceChangeSubscriptions.iterator();
/* 102:112 */       while (i.hasNext())
/* 103:    */       {
/* 104:113 */         PriceChangeSubscriber subscriber = (PriceChangeSubscriber)i.next();
/* 105:114 */         subscriber.onPriceChange(lastTransaction.price, lastTransaction.time);
/* 106:    */       }
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected void notifyMarketDataSubscribers()
/* 111:    */   {
/* 112:122 */     Iterator<MarketDataSubscriber> i = this.marketDataSubscriptions.iterator();
/* 113:123 */     while (i.hasNext())
/* 114:    */     {
/* 115:124 */       MarketDataSubscriber subscriber = (MarketDataSubscriber)i.next();
/* 116:125 */       subscriber.onMarketDataChange(this.currentRecord);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setBuyOrder(TradingAccount account, Order order)
/* 121:    */   {
/* 122:130 */     this.orderExecutionSubscriptions.put(order, (SimulatedTradingAccount)account);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setSellOrder(TradingAccount account, Order order)
/* 126:    */   {
/* 127:134 */     this.orderExecutionSubscriptions.put(order, (SimulatedTradingAccount)account);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void cancelSellOrder(TradingAccount account, String ticker)
/* 131:    */   {
/* 132:138 */     Iterator<Map.Entry<Order, OrderExecutionSubscriber>> i = this.orderExecutionSubscriptions.entrySet().iterator();
/* 133:139 */     while (i.hasNext())
/* 134:    */     {
/* 135:140 */       Map.Entry<Order, OrderExecutionSubscriber> entry = (Map.Entry)i.next();
/* 136:141 */       if ((((Order)entry.getKey()).ticker.equals(ticker)) && 
/* 137:142 */         (((SimulatedTradingAccount)entry.getValue()).equals(account))) {
/* 138:143 */         i.remove();
/* 139:    */       }
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void cancelSellOrder(TradingAccount account, Order order)
/* 144:    */   {
/* 145:149 */     Iterator<Map.Entry<Order, OrderExecutionSubscriber>> i = this.orderExecutionSubscriptions.entrySet().iterator();
/* 146:150 */     while (i.hasNext())
/* 147:    */     {
/* 148:151 */       Map.Entry<Order, OrderExecutionSubscriber> entry = (Map.Entry)i.next();
/* 149:152 */       if ((((Order)entry.getKey()).equals(order)) && 
/* 150:153 */         (((SimulatedTradingAccount)entry.getValue()).equals(account))) {
/* 151:154 */         i.remove();
/* 152:    */       }
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void cancelBuyOrder(TradingAccount account, String ticker)
/* 157:    */   {
/* 158:161 */     MainLogger.debug("Cancelling buy order... (deleting from order execution subscriber)");
/* 159:162 */     Iterator<Map.Entry<Order, OrderExecutionSubscriber>> i = this.orderExecutionSubscriptions.entrySet().iterator();
/* 160:163 */     while (i.hasNext())
/* 161:    */     {
/* 162:164 */       Map.Entry<Order, OrderExecutionSubscriber> entry = (Map.Entry)i.next();
/* 163:165 */       if ((((Order)entry.getKey()).ticker.equals(ticker)) && 
/* 164:166 */         (((SimulatedTradingAccount)entry.getValue()).equals(account)))
/* 165:    */       {
/* 166:167 */         MainLogger.debug("Found order execution subscriber! Removing!");
/* 167:168 */         i.remove();
/* 168:    */       }
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void cancelBuyOrder(TradingAccount account, Order order) {}
/* 173:    */   
/* 174:    */   public boolean hasOrderExecutionSubscription(Order order, OrderExecutionSubscriber subscriber)
/* 175:    */   {
/* 176:180 */     boolean has = false;
/* 177:181 */     Iterator<Map.Entry<Order, OrderExecutionSubscriber>> i = this.orderExecutionSubscriptions.entrySet().iterator();
/* 178:182 */     while (i.hasNext())
/* 179:    */     {
/* 180:183 */       Map.Entry<Order, OrderExecutionSubscriber> mapEntry = (Map.Entry)i.next();
/* 181:184 */       if ((((Order)mapEntry.getKey()).equals(order)) && 
/* 182:185 */         (((OrderExecutionSubscriber)mapEntry.getValue()).getSubscriberId() == subscriber.getSubscriberId())) {
/* 183:186 */         has = true;
/* 184:    */       }
/* 185:    */     }
/* 186:189 */     return has;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean hasPriceSubscription(PriceChangeSubscriber subscriber)
/* 190:    */   {
/* 191:193 */     boolean has = false;
/* 192:194 */     Iterator<PriceChangeSubscriber> i = this.priceChangeSubscriptions.iterator();
/* 193:195 */     while (i.hasNext())
/* 194:    */     {
/* 195:196 */       PriceChangeSubscriber temp = (PriceChangeSubscriber)i.next();
/* 196:197 */       if (temp.getSubscriberId() == subscriber.getSubscriberId()) {
/* 197:198 */         has = true;
/* 198:    */       }
/* 199:    */     }
/* 200:201 */     return has;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean hasMarketDataSubscription(MarketDataSubscriber subscriber)
/* 204:    */   {
/* 205:205 */     boolean has = false;
/* 206:206 */     Iterator<MarketDataSubscriber> i = this.marketDataSubscriptions.iterator();
/* 207:207 */     while (i.hasNext())
/* 208:    */     {
/* 209:208 */       MarketDataSubscriber temp = (MarketDataSubscriber)i.next();
/* 210:209 */       if (temp.getSubscriberId() == subscriber.getSubscriberId()) {
/* 211:210 */         has = true;
/* 212:    */       }
/* 213:    */     }
/* 214:213 */     return has;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public abstract Date getFullDateAndTime();
/* 218:    */   
/* 219:    */   public abstract StockMarketData getMinPriceRecord();
/* 220:    */   
/* 221:    */   public abstract StockMarketData getMaxPriceRecord();
/* 222:    */   
/* 223:    */   public abstract StockMarketData getMinPriceRecordBeforeMaxPriceRecord();
/* 224:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.simulator.MarketSimulator
 * JD-Core Version:    0.7.0.1
 */
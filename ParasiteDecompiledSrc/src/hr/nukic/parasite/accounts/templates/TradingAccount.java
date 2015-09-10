/*   1:    */ package hr.nukic.parasite.accounts.templates;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.core.DataCollector;
/*   4:    */ import hr.nukic.parasite.core.Order;
/*   5:    */ import hr.nukic.parasite.core.OrderType;
/*   6:    */ import hr.nukic.parasite.core.PortfolioData;
/*   7:    */ import hr.nukic.parasite.core.QueryStatus;
/*   8:    */ import hr.nukic.parasite.core.Transaction;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Date;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ 
/*  14:    */ public abstract class TradingAccount
/*  15:    */   implements GenericWebAccount
/*  16:    */ {
/*  17: 18 */   public PortfolioData portfolio = null;
/*  18: 19 */   public List<Order> ownOrders = new ArrayList();
/*  19: 20 */   public List<Transaction> ownTransactions = new ArrayList();
/*  20:    */   public long id;
/*  21:    */   public float feePercentage;
/*  22:    */   public float minFee;
/*  23:    */   public DataCollector dataCollector;
/*  24:    */   
/*  25:    */   public TradingAccount(PortfolioData portfolioData, List<Order> ownOrders, float feePercentage, float minFee)
/*  26:    */   {
/*  27: 28 */     this.id = ((9.223372036854776E+018D * Math.random()));
/*  28: 29 */     this.feePercentage = feePercentage;
/*  29: 30 */     this.minFee = minFee;
/*  30: 31 */     this.portfolio = portfolioData;
/*  31: 32 */     this.ownOrders = ownOrders;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public TradingAccount(DataCollector dc, float feePercentage, float minFee)
/*  35:    */   {
/*  36: 37 */     this.id = ((100000.0D * Math.random()));
/*  37: 38 */     this.feePercentage = feePercentage;
/*  38: 39 */     this.minFee = minFee;
/*  39: 40 */     this.dataCollector = dc;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public TradingAccount(float feePercentage, float minFee)
/*  43:    */   {
/*  44: 44 */     this.id = ((100000.0D * Math.random()));
/*  45: 45 */     this.feePercentage = feePercentage;
/*  46: 46 */     this.minFee = minFee;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public abstract QueryStatus setBuyOrder(String paramString, float paramFloat, int paramInt, Date paramDate);
/*  50:    */   
/*  51:    */   public abstract QueryStatus setSellOrder(String paramString, float paramFloat, int paramInt, Date paramDate);
/*  52:    */   
/*  53:    */   public QueryStatus setOrder(Order order)
/*  54:    */   {
/*  55: 55 */     QueryStatus status = QueryStatus.UNKNOWN;
/*  56: 56 */     if (order.type == OrderType.BUY) {
/*  57: 57 */       status = setBuyOrder(order.ticker, order.price, order.amount, order.expiryDate);
/*  58: 59 */     } else if (order.type == OrderType.SELL) {
/*  59: 60 */       status = setSellOrder(order.ticker, order.price, order.amount, order.expiryDate);
/*  60:    */     }
/*  61: 62 */     return status;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public QueryStatus cancelOrder(Order order)
/*  65:    */   {
/*  66: 66 */     QueryStatus status = QueryStatus.UNKNOWN;
/*  67: 67 */     if (order.type == OrderType.BUY) {
/*  68: 68 */       status = cancelBuyOrder(order.ticker, order.price, order.amount, order.expiryDate);
/*  69: 70 */     } else if (order.type == OrderType.SELL) {
/*  70: 71 */       status = cancelSellOrder(order.ticker, order.price, order.amount, order.expiryDate);
/*  71:    */     }
/*  72: 73 */     return status;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public abstract QueryStatus cancelBuyOrder(String paramString, float paramFloat, int paramInt, Date paramDate);
/*  76:    */   
/*  77:    */   public abstract QueryStatus cancelBuyOrder(String paramString);
/*  78:    */   
/*  79:    */   public abstract QueryStatus cancelSellOrder(String paramString, float paramFloat, int paramInt, Date paramDate);
/*  80:    */   
/*  81:    */   public abstract QueryStatus cancelSellOrder(String paramString);
/*  82:    */   
/*  83:    */   public abstract List<Order> readPendingOrdersInfo();
/*  84:    */   
/*  85:    */   public abstract List<Order> readPendingOrdersInfoForTicker(String paramString);
/*  86:    */   
/*  87:    */   public abstract List<Transaction> readCompletedOrdersInfo();
/*  88:    */   
/*  89:    */   public abstract List<Transaction> readCompletedOrdersInfoForTicker(String paramString);
/*  90:    */   
/*  91:    */   public abstract float readCashInPendingBuyOrders();
/*  92:    */   
/*  93:    */   public abstract float readCashInPendingSellOrders();
/*  94:    */   
/*  95:    */   public abstract PortfolioData readPortfolioData();
/*  96:    */   
/*  97:    */   public abstract float readAvailableCash();
/*  98:    */   
/*  99:    */   public void refreshTradingAccountData()
/* 100:    */   {
/* 101:104 */     this.portfolio = readPortfolioData();
/* 102:105 */     this.ownOrders = readPendingOrdersInfo();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean hasPendingBuyOrdersForTicker(String ticker)
/* 106:    */   {
/* 107:109 */     boolean has = false;
/* 108:110 */     Iterator<Order> i = this.ownOrders.iterator();
/* 109:111 */     while (i.hasNext())
/* 110:    */     {
/* 111:112 */       Order order = (Order)i.next();
/* 112:113 */       if ((order.type == OrderType.BUY) && (order.ticker.equals(ticker)))
/* 113:    */       {
/* 114:114 */         has = true;
/* 115:115 */         break;
/* 116:    */       }
/* 117:    */     }
/* 118:118 */     return has;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean hasPendingSellOrdersForTicker(String ticker)
/* 122:    */   {
/* 123:123 */     boolean has = false;
/* 124:124 */     Iterator<Order> i = this.ownOrders.iterator();
/* 125:125 */     while (i.hasNext())
/* 126:    */     {
/* 127:126 */       Order order = (Order)i.next();
/* 128:127 */       if ((order.type == OrderType.SELL) && (order.ticker.equals(ticker)))
/* 129:    */       {
/* 130:128 */         has = true;
/* 131:129 */         break;
/* 132:    */       }
/* 133:    */     }
/* 134:132 */     return has;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public float calculateFee(float orderValue)
/* 138:    */   {
/* 139:136 */     float fee = orderValue * (this.feePercentage / 100.0F);
/* 140:137 */     if (fee < this.minFee) {
/* 141:138 */       fee = this.minFee;
/* 142:    */     }
/* 143:139 */     return fee;
/* 144:    */   }
/* 145:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.templates.TradingAccount
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package hr.nukic.parasite.accounts;
/*   2:    */ 
/*   3:    */ import hr.nukic.parasite.accounts.templates.TradingAccount;
/*   4:    */ import hr.nukic.parasite.core.DataCollector;
/*   5:    */ import hr.nukic.parasite.core.Order;
/*   6:    */ import hr.nukic.parasite.core.PortfolioData;
/*   7:    */ import hr.nukic.parasite.core.QueryStatus;
/*   8:    */ import hr.nukic.parasite.core.Transaction;
/*   9:    */ import java.util.Date;
/*  10:    */ import java.util.List;
/*  11:    */ 
/*  12:    */ public class AgramTrader
/*  13:    */   extends TradingAccount
/*  14:    */ {
/*  15:    */   public static final float FEE_PERCENTAGE = 0.4F;
/*  16:    */   public static final float MIN_FEE = 0.0F;
/*  17:    */   
/*  18:    */   public AgramTrader(DataCollector dc, float feePercentage, float minFee)
/*  19:    */   {
/*  20: 18 */     super(dc, feePercentage, minFee);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean login()
/*  24:    */   {
/*  25: 24 */     return false;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean logout()
/*  29:    */   {
/*  30: 30 */     return false;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public QueryStatus setBuyOrder(String ticker, float price, int amount, Date expiryDate)
/*  34:    */   {
/*  35: 36 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public QueryStatus setSellOrder(String ticker, float price, int amount, Date expiryDate)
/*  39:    */   {
/*  40: 42 */     return null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public QueryStatus cancelBuyOrder(String ticker, float price, int amount, Date expiryDate)
/*  44:    */   {
/*  45: 48 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public QueryStatus cancelSellOrder(String ticker, float price, int amount, Date expiryDate)
/*  49:    */   {
/*  50: 54 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<Order> readPendingOrdersInfo()
/*  54:    */   {
/*  55: 60 */     return null;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<Order> readPendingOrdersInfoForTicker(String ticker)
/*  59:    */   {
/*  60: 66 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<Transaction> readCompletedOrdersInfo()
/*  64:    */   {
/*  65: 72 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<Transaction> readCompletedOrdersInfoForTicker(String ticker)
/*  69:    */   {
/*  70: 78 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public float readCashInPendingBuyOrders()
/*  74:    */   {
/*  75: 84 */     return 0.0F;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public float readCashInPendingSellOrders()
/*  79:    */   {
/*  80: 90 */     return 0.0F;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public PortfolioData readPortfolioData()
/*  84:    */   {
/*  85: 96 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public float readAvailableCash()
/*  89:    */   {
/*  90:102 */     return 0.0F;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public QueryStatus cancelBuyOrder(String ticker)
/*  94:    */   {
/*  95:108 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public QueryStatus cancelSellOrder(String ticker)
/*  99:    */   {
/* 100:114 */     return null;
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.AgramTrader
 * JD-Core Version:    0.7.0.1
 */
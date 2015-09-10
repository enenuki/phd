/*  1:   */ package hr.nukic.parasite.core;
/*  2:   */ 
/*  3:   */ import java.util.Date;
/*  4:   */ 
/*  5:   */ public class Order
/*  6:   */ {
/*  7: 8 */   public long id = (9.223372036854776E+018D * Math.random());
/*  8:   */   public OrderType type;
/*  9:   */   public String ticker;
/* 10:   */   public int amount;
/* 11:   */   public float price;
/* 12:13 */   public OrderOwnership ownership = OrderOwnership.NOT_OWN;
/* 13:16 */   public int position = -1;
/* 14:19 */   public Date expiryDate = new Date(9223372036854775807L);
/* 15:22 */   private Date validFrom = new Date(0L);
/* 16:23 */   public boolean isDelayed = false;
/* 17:   */   
/* 18:   */   public Order(String ticker, OrderType type, int amount, float price)
/* 19:   */   {
/* 20:27 */     this.ticker = ticker;
/* 21:28 */     this.type = type;
/* 22:29 */     this.amount = amount;
/* 23:30 */     this.price = price;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Order() {}
/* 27:   */   
/* 28:   */   public String toString()
/* 29:   */   {
/* 30:38 */     String s = "";
/* 31:39 */     if (this.type == OrderType.BUY) {
/* 32:40 */       s = "BUY,";
/* 33:41 */     } else if (this.type == OrderType.SELL) {
/* 34:42 */       s = "SELL,";
/* 35:   */     }
/* 36:44 */     s = s + this.ticker + "," + this.amount + "," + this.price;
/* 37:45 */     return s;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean equals(Order order)
/* 41:   */   {
/* 42:50 */     if ((this.ticker.equals(order.ticker)) && (this.amount == order.amount) && (this.type == order.type) && 
/* 43:51 */       (this.price == order.price)) {
/* 44:52 */       return true;
/* 45:   */     }
/* 46:54 */     return false;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toCsvString()
/* 50:   */   {
/* 51:59 */     return this.position + "," + this.amount + "," + this.price + ",";
/* 52:   */   }
/* 53:   */   
/* 54:   */   public Date getValidFrom()
/* 55:   */   {
/* 56:63 */     return this.validFrom;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void setValidFrom(Date validFrom)
/* 60:   */   {
/* 61:67 */     this.validFrom = validFrom;
/* 62:68 */     this.isDelayed = true;
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.Order
 * JD-Core Version:    0.7.0.1
 */
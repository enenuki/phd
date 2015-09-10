/*  1:   */ package hr.nukic.parasite.core;
/*  2:   */ 
/*  3:   */ import java.text.Format;
/*  4:   */ import java.text.SimpleDateFormat;
/*  5:   */ import java.util.Date;
/*  6:   */ 
/*  7:   */ public class Transaction
/*  8:   */ {
/*  9:   */   public String ticker;
/* 10:   */   public int amount;
/* 11:   */   public float price;
/* 12:   */   public Date time;
/* 13:   */   public float value;
/* 14:14 */   public boolean isOwn = false;
/* 15:   */   public OrderType type;
/* 16:   */   
/* 17:   */   public Transaction(String ticker, int amount, float price, Date date)
/* 18:   */   {
/* 19:19 */     this.ticker = ticker;
/* 20:20 */     this.amount = amount;
/* 21:21 */     this.price = price;
/* 22:22 */     this.time = date;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Transaction() {}
/* 26:   */   
/* 27:   */   public String toString()
/* 28:   */   {
/* 29:32 */     Format formatter = new SimpleDateFormat("dd.M.yyyy/HH:mm:ss");
/* 30:33 */     String s = this.ticker + ";TRANSACTION;" + formatter.format(this.time) + ";" + this.price + ";" + this.amount + ";" + this.value;
/* 31:35 */     if (this.isOwn) {
/* 32:36 */       s = s + ";OWN=TRUE";
/* 33:   */     } else {
/* 34:38 */       s = s + ";OWN=FALSE";
/* 35:   */     }
/* 36:40 */     return s;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String toCsvString()
/* 40:   */   {
/* 41:46 */     Format formatter = new SimpleDateFormat("HH:mm:ss");
/* 42:47 */     return this.amount + "," + this.price + "," + formatter.format(this.time) + ",";
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean equals(Transaction t)
/* 46:   */   {
/* 47:51 */     if ((this.ticker.equals(t.ticker)) && (this.amount == t.amount) && (this.price == t.price) && (this.time.equals(t.time))) {
/* 48:52 */       return true;
/* 49:   */     }
/* 50:54 */     return false;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.Transaction
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package hr.nukic.parasite.core;
/*   2:    */ 
/*   3:    */ import java.text.ParseException;
/*   4:    */ import java.text.SimpleDateFormat;
/*   5:    */ import java.util.Date;
/*   6:    */ 
/*   7:    */ public class StockDaySummary
/*   8:    */ {
/*   9:    */   public static final String DATE_FORMAT = "yyyy/MM/dd";
/*  10:    */   public Date date;
/*  11: 12 */   public float first = 0.0F;
/*  12: 13 */   public float last = 0.0F;
/*  13: 14 */   public float high = 0.0F;
/*  14: 15 */   public float low = 0.0F;
/*  15: 16 */   public float average = 0.0F;
/*  16: 17 */   public float change = 0.0F;
/*  17: 18 */   public int amount = 0;
/*  18: 19 */   public float turnover = 0.0F;
/*  19: 20 */   public int transactionNum = 0;
/*  20:    */   
/*  21:    */   public StockDaySummary() {}
/*  22:    */   
/*  23:    */   public StockDaySummary(String line)
/*  24:    */   {
/*  25:    */     try
/*  26:    */     {
/*  27: 27 */       SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
/*  28: 28 */       String[] tokens = line.split(",");
/*  29: 29 */       this.date = f.parse(tokens[0]);
/*  30: 30 */       this.first = Float.parseFloat(tokens[1]);
/*  31: 31 */       this.last = Float.parseFloat(tokens[2]);
/*  32: 32 */       this.high = Float.parseFloat(tokens[3]);
/*  33: 33 */       this.low = Float.parseFloat(tokens[4]);
/*  34: 34 */       this.average = Float.parseFloat(tokens[5]);
/*  35: 35 */       this.change = Float.parseFloat(tokens[6]);
/*  36: 36 */       this.amount = Integer.parseInt(tokens[7]);
/*  37: 37 */       this.turnover = Float.parseFloat(tokens[8]);
/*  38: 38 */       this.transactionNum = Integer.parseInt(tokens[9]);
/*  39:    */     }
/*  40:    */     catch (ParseException e)
/*  41:    */     {
/*  42: 41 */       e.printStackTrace();
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String toString()
/*  47:    */   {
/*  48: 47 */     SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
/*  49: 48 */     String str = "";
/*  50: 49 */     str = str + f.format(this.date) + ",";
/*  51: 50 */     str = str + this.first + ",";
/*  52: 51 */     str = str + this.last + ",";
/*  53: 52 */     str = str + this.high + ",";
/*  54: 53 */     str = str + this.low + ",";
/*  55: 54 */     str = str + this.average + ",";
/*  56: 55 */     str = str + this.change + ",";
/*  57: 56 */     str = str + this.amount + ",";
/*  58: 57 */     str = str + this.turnover + ",";
/*  59: 58 */     str = str + this.transactionNum;
/*  60: 59 */     return str;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int hashCode()
/*  64:    */   {
/*  65: 64 */     int prime = 31;
/*  66: 65 */     int result = 1;
/*  67: 66 */     result = 31 * result + this.amount;
/*  68: 67 */     result = 31 * result + Float.floatToIntBits(this.average);
/*  69: 68 */     result = 31 * result + Float.floatToIntBits(this.change);
/*  70: 69 */     result = 31 * result + (this.date == null ? 0 : this.date.hashCode());
/*  71: 70 */     result = 31 * result + Float.floatToIntBits(this.first);
/*  72: 71 */     result = 31 * result + Float.floatToIntBits(this.high);
/*  73: 72 */     result = 31 * result + Float.floatToIntBits(this.last);
/*  74: 73 */     result = 31 * result + Float.floatToIntBits(this.low);
/*  75: 74 */     result = 31 * result + this.transactionNum;
/*  76: 75 */     result = 31 * result + Float.floatToIntBits(this.turnover);
/*  77: 76 */     return result;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean equals(Object obj)
/*  81:    */   {
/*  82: 81 */     if (this == obj) {
/*  83: 82 */       return true;
/*  84:    */     }
/*  85: 83 */     if (obj == null) {
/*  86: 84 */       return false;
/*  87:    */     }
/*  88: 85 */     if (getClass() != obj.getClass()) {
/*  89: 86 */       return false;
/*  90:    */     }
/*  91: 87 */     StockDaySummary other = (StockDaySummary)obj;
/*  92: 88 */     if (this.amount != other.amount) {
/*  93: 89 */       return false;
/*  94:    */     }
/*  95: 90 */     if (Float.floatToIntBits(this.average) != Float.floatToIntBits(other.average)) {
/*  96: 91 */       return false;
/*  97:    */     }
/*  98: 92 */     if (Float.floatToIntBits(this.change) != Float.floatToIntBits(other.change)) {
/*  99: 93 */       return false;
/* 100:    */     }
/* 101: 94 */     if (this.date == null)
/* 102:    */     {
/* 103: 95 */       if (other.date != null) {
/* 104: 96 */         return false;
/* 105:    */       }
/* 106:    */     }
/* 107: 97 */     else if (!this.date.equals(other.date)) {
/* 108: 98 */       return false;
/* 109:    */     }
/* 110: 99 */     if (Float.floatToIntBits(this.first) != Float.floatToIntBits(other.first)) {
/* 111:100 */       return false;
/* 112:    */     }
/* 113:101 */     if (Float.floatToIntBits(this.high) != Float.floatToIntBits(other.high)) {
/* 114:102 */       return false;
/* 115:    */     }
/* 116:103 */     if (Float.floatToIntBits(this.last) != Float.floatToIntBits(other.last)) {
/* 117:104 */       return false;
/* 118:    */     }
/* 119:105 */     if (Float.floatToIntBits(this.low) != Float.floatToIntBits(other.low)) {
/* 120:106 */       return false;
/* 121:    */     }
/* 122:107 */     if (this.transactionNum != other.transactionNum) {
/* 123:108 */       return false;
/* 124:    */     }
/* 125:109 */     if (Float.floatToIntBits(this.turnover) != Float.floatToIntBits(other.turnover)) {
/* 126:110 */       return false;
/* 127:    */     }
/* 128:111 */     return true;
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.StockDaySummary
 * JD-Core Version:    0.7.0.1
 */
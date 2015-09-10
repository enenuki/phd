/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ public class ParserTokenEvent
/*  4:   */   extends Event
/*  5:   */ {
/*  6:   */   private int value;
/*  7:   */   private int amount;
/*  8: 6 */   public static int LA = 0;
/*  9: 7 */   public static int CONSUME = 1;
/* 10:   */   
/* 11:   */   public ParserTokenEvent(Object paramObject)
/* 12:   */   {
/* 13:11 */     super(paramObject);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public ParserTokenEvent(Object paramObject, int paramInt1, int paramInt2, int paramInt3)
/* 17:   */   {
/* 18:15 */     super(paramObject);
/* 19:16 */     setValues(paramInt1, paramInt2, paramInt3);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getAmount()
/* 23:   */   {
/* 24:19 */     return this.amount;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getValue()
/* 28:   */   {
/* 29:22 */     return this.value;
/* 30:   */   }
/* 31:   */   
/* 32:   */   void setAmount(int paramInt)
/* 33:   */   {
/* 34:25 */     this.amount = paramInt;
/* 35:   */   }
/* 36:   */   
/* 37:   */   void setValue(int paramInt)
/* 38:   */   {
/* 39:28 */     this.value = paramInt;
/* 40:   */   }
/* 41:   */   
/* 42:   */   void setValues(int paramInt1, int paramInt2, int paramInt3)
/* 43:   */   {
/* 44:32 */     super.setValues(paramInt1);
/* 45:33 */     setAmount(paramInt2);
/* 46:34 */     setValue(paramInt3);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:37 */     if (getType() == LA) {
/* 52:38 */       return "ParserTokenEvent [LA," + getAmount() + "," + getValue() + "]";
/* 53:   */     }
/* 54:41 */     return "ParserTokenEvent [consume,1," + getValue() + "]";
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ParserTokenEvent
 * JD-Core Version:    0.7.0.1
 */
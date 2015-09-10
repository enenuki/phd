/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ public class TraceEvent
/*  4:   */   extends GuessingEvent
/*  5:   */ {
/*  6:   */   private int ruleNum;
/*  7:   */   private int data;
/*  8: 6 */   public static int ENTER = 0;
/*  9: 7 */   public static int EXIT = 1;
/* 10: 8 */   public static int DONE_PARSING = 2;
/* 11:   */   
/* 12:   */   public TraceEvent(Object paramObject)
/* 13:   */   {
/* 14:12 */     super(paramObject);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public TraceEvent(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/* 18:   */   {
/* 19:15 */     super(paramObject);
/* 20:16 */     setValues(paramInt1, paramInt2, paramInt3, paramInt4);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getData()
/* 24:   */   {
/* 25:19 */     return this.data;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getRuleNum()
/* 29:   */   {
/* 30:22 */     return this.ruleNum;
/* 31:   */   }
/* 32:   */   
/* 33:   */   void setData(int paramInt)
/* 34:   */   {
/* 35:25 */     this.data = paramInt;
/* 36:   */   }
/* 37:   */   
/* 38:   */   void setRuleNum(int paramInt)
/* 39:   */   {
/* 40:28 */     this.ruleNum = paramInt;
/* 41:   */   }
/* 42:   */   
/* 43:   */   void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/* 44:   */   {
/* 45:32 */     super.setValues(paramInt1, paramInt3);
/* 46:33 */     setRuleNum(paramInt2);
/* 47:34 */     setData(paramInt4);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String toString()
/* 51:   */   {
/* 52:37 */     return "ParserTraceEvent [" + (getType() == ENTER ? "enter," : "exit,") + getRuleNum() + "," + getGuessing() + "]";
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.TraceEvent
 * JD-Core Version:    0.7.0.1
 */
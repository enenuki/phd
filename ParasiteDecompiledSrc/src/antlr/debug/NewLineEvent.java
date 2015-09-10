/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ public class NewLineEvent
/*  4:   */   extends Event
/*  5:   */ {
/*  6:   */   private int line;
/*  7:   */   
/*  8:   */   public NewLineEvent(Object paramObject)
/*  9:   */   {
/* 10: 8 */     super(paramObject);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public NewLineEvent(Object paramObject, int paramInt)
/* 14:   */   {
/* 15:11 */     super(paramObject);
/* 16:12 */     setValues(paramInt);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int getLine()
/* 20:   */   {
/* 21:15 */     return this.line;
/* 22:   */   }
/* 23:   */   
/* 24:   */   void setLine(int paramInt)
/* 25:   */   {
/* 26:18 */     this.line = paramInt;
/* 27:   */   }
/* 28:   */   
/* 29:   */   void setValues(int paramInt)
/* 30:   */   {
/* 31:22 */     setLine(paramInt);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String toString()
/* 35:   */   {
/* 36:25 */     return "NewLineEvent [" + this.line + "]";
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.NewLineEvent
 * JD-Core Version:    0.7.0.1
 */
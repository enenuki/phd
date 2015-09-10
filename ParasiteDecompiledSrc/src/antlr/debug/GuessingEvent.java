/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ public abstract class GuessingEvent
/*  4:   */   extends Event
/*  5:   */ {
/*  6:   */   private int guessing;
/*  7:   */   
/*  8:   */   public GuessingEvent(Object paramObject)
/*  9:   */   {
/* 10: 8 */     super(paramObject);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public GuessingEvent(Object paramObject, int paramInt)
/* 14:   */   {
/* 15:11 */     super(paramObject, paramInt);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getGuessing()
/* 19:   */   {
/* 20:14 */     return this.guessing;
/* 21:   */   }
/* 22:   */   
/* 23:   */   void setGuessing(int paramInt)
/* 24:   */   {
/* 25:17 */     this.guessing = paramInt;
/* 26:   */   }
/* 27:   */   
/* 28:   */   void setValues(int paramInt1, int paramInt2)
/* 29:   */   {
/* 30:21 */     super.setValues(paramInt1);
/* 31:22 */     setGuessing(paramInt2);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.GuessingEvent
 * JD-Core Version:    0.7.0.1
 */
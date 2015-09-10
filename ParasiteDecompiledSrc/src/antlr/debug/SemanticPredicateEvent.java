/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ public class SemanticPredicateEvent
/*  4:   */   extends GuessingEvent
/*  5:   */ {
/*  6:   */   public static final int VALIDATING = 0;
/*  7:   */   public static final int PREDICTING = 1;
/*  8:   */   private int condition;
/*  9:   */   private boolean result;
/* 10:   */   
/* 11:   */   public SemanticPredicateEvent(Object paramObject)
/* 12:   */   {
/* 13:11 */     super(paramObject);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public SemanticPredicateEvent(Object paramObject, int paramInt)
/* 17:   */   {
/* 18:14 */     super(paramObject, paramInt);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getCondition()
/* 22:   */   {
/* 23:17 */     return this.condition;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean getResult()
/* 27:   */   {
/* 28:20 */     return this.result;
/* 29:   */   }
/* 30:   */   
/* 31:   */   void setCondition(int paramInt)
/* 32:   */   {
/* 33:23 */     this.condition = paramInt;
/* 34:   */   }
/* 35:   */   
/* 36:   */   void setResult(boolean paramBoolean)
/* 37:   */   {
/* 38:26 */     this.result = paramBoolean;
/* 39:   */   }
/* 40:   */   
/* 41:   */   void setValues(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
/* 42:   */   {
/* 43:30 */     super.setValues(paramInt1, paramInt3);
/* 44:31 */     setCondition(paramInt2);
/* 45:32 */     setResult(paramBoolean);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String toString()
/* 49:   */   {
/* 50:35 */     return "SemanticPredicateEvent [" + getCondition() + "," + getResult() + "," + getGuessing() + "]";
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.SemanticPredicateEvent
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ public class SyntacticPredicateEvent
/*  4:   */   extends GuessingEvent
/*  5:   */ {
/*  6:   */   public SyntacticPredicateEvent(Object paramObject)
/*  7:   */   {
/*  8: 7 */     super(paramObject);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public SyntacticPredicateEvent(Object paramObject, int paramInt)
/* 12:   */   {
/* 13:10 */     super(paramObject, paramInt);
/* 14:   */   }
/* 15:   */   
/* 16:   */   void setValues(int paramInt1, int paramInt2)
/* 17:   */   {
/* 18:14 */     super.setValues(paramInt1, paramInt2);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String toString()
/* 22:   */   {
/* 23:17 */     return "SyntacticPredicateEvent [" + getGuessing() + "]";
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.SyntacticPredicateEvent
 * JD-Core Version:    0.7.0.1
 */
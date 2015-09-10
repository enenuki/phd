/*  1:   */ package jxl.biff.formula;
/*  2:   */ 
/*  3:   */ class LessEqual
/*  4:   */   extends BinaryOperator
/*  5:   */   implements ParsedThing
/*  6:   */ {
/*  7:   */   public String getSymbol()
/*  8:   */   {
/*  9:36 */     return "<=";
/* 10:   */   }
/* 11:   */   
/* 12:   */   Token getToken()
/* 13:   */   {
/* 14:46 */     return Token.LESS_EQUAL;
/* 15:   */   }
/* 16:   */   
/* 17:   */   int getPrecedence()
/* 18:   */   {
/* 19:57 */     return 5;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.LessEqual
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package jxl.biff.formula;
/*  2:   */ 
/*  3:   */ class Divide
/*  4:   */   extends BinaryOperator
/*  5:   */   implements ParsedThing
/*  6:   */ {
/*  7:   */   public String getSymbol()
/*  8:   */   {
/*  9:41 */     return "/";
/* 10:   */   }
/* 11:   */   
/* 12:   */   Token getToken()
/* 13:   */   {
/* 14:51 */     return Token.DIVIDE;
/* 15:   */   }
/* 16:   */   
/* 17:   */   int getPrecedence()
/* 18:   */   {
/* 19:62 */     return 3;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.Divide
 * JD-Core Version:    0.7.0.1
 */
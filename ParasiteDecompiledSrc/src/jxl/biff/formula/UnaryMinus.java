/*  1:   */ package jxl.biff.formula;
/*  2:   */ 
/*  3:   */ class UnaryMinus
/*  4:   */   extends UnaryOperator
/*  5:   */   implements ParsedThing
/*  6:   */ {
/*  7:   */   public String getSymbol()
/*  8:   */   {
/*  9:36 */     return "-";
/* 10:   */   }
/* 11:   */   
/* 12:   */   Token getToken()
/* 13:   */   {
/* 14:46 */     return Token.UNARY_MINUS;
/* 15:   */   }
/* 16:   */   
/* 17:   */   int getPrecedence()
/* 18:   */   {
/* 19:57 */     return 2;
/* 20:   */   }
/* 21:   */   
/* 22:   */   void handleImportedCellReferences() {}
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.UnaryMinus
 * JD-Core Version:    0.7.0.1
 */
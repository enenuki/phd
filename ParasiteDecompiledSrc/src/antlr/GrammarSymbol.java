/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ abstract class GrammarSymbol
/*  4:   */ {
/*  5:   */   protected String id;
/*  6:   */   
/*  7:   */   public GrammarSymbol() {}
/*  8:   */   
/*  9:   */   public GrammarSymbol(String paramString)
/* 10:   */   {
/* 11:20 */     this.id = paramString;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getId()
/* 15:   */   {
/* 16:24 */     return this.id;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setId(String paramString)
/* 20:   */   {
/* 21:28 */     this.id = paramString;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.GrammarSymbol
 * JD-Core Version:    0.7.0.1
 */
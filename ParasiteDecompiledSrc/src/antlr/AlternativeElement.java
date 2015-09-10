/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ abstract class AlternativeElement
/*  4:   */   extends GrammarElement
/*  5:   */ {
/*  6:   */   AlternativeElement next;
/*  7:12 */   protected int autoGenType = 1;
/*  8:   */   protected String enclosingRuleName;
/*  9:   */   
/* 10:   */   public AlternativeElement(Grammar paramGrammar)
/* 11:   */   {
/* 12:17 */     super(paramGrammar);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public AlternativeElement(Grammar paramGrammar, Token paramToken)
/* 16:   */   {
/* 17:21 */     super(paramGrammar, paramToken);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public AlternativeElement(Grammar paramGrammar, Token paramToken, int paramInt)
/* 21:   */   {
/* 22:25 */     super(paramGrammar, paramToken);
/* 23:26 */     this.autoGenType = paramInt;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getAutoGenType()
/* 27:   */   {
/* 28:30 */     return this.autoGenType;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setAutoGenType(int paramInt)
/* 32:   */   {
/* 33:34 */     this.autoGenType = paramInt;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getLabel()
/* 37:   */   {
/* 38:38 */     return null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setLabel(String paramString) {}
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.AlternativeElement
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class RuleRefElement
/*  4:   */   extends AlternativeElement
/*  5:   */ {
/*  6:   */   protected String targetRule;
/*  7:12 */   protected String args = null;
/*  8:13 */   protected String idAssign = null;
/*  9:   */   protected String label;
/* 10:   */   
/* 11:   */   public RuleRefElement(Grammar paramGrammar, Token paramToken, int paramInt)
/* 12:   */   {
/* 13:18 */     super(paramGrammar, paramToken, paramInt);
/* 14:19 */     this.targetRule = paramToken.getText();
/* 15:21 */     if (paramToken.type == 24) {
/* 16:22 */       this.targetRule = CodeGenerator.encodeLexerRuleName(this.targetRule);
/* 17:   */     }
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void generate()
/* 21:   */   {
/* 22:36 */     this.grammar.generator.gen(this);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getArgs()
/* 26:   */   {
/* 27:40 */     return this.args;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getIdAssign()
/* 31:   */   {
/* 32:44 */     return this.idAssign;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getLabel()
/* 36:   */   {
/* 37:48 */     return this.label;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Lookahead look(int paramInt)
/* 41:   */   {
/* 42:52 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setArgs(String paramString)
/* 46:   */   {
/* 47:56 */     this.args = paramString;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setIdAssign(String paramString)
/* 51:   */   {
/* 52:60 */     this.idAssign = paramString;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void setLabel(String paramString)
/* 56:   */   {
/* 57:64 */     this.label = paramString;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String toString()
/* 61:   */   {
/* 62:68 */     if (this.args != null) {
/* 63:69 */       return " " + this.targetRule + this.args;
/* 64:   */     }
/* 65:71 */     return " " + this.targetRule;
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.RuleRefElement
 * JD-Core Version:    0.7.0.1
 */
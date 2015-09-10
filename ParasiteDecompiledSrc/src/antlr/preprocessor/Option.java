/*  1:   */ package antlr.preprocessor;
/*  2:   */ 
/*  3:   */ class Option
/*  4:   */ {
/*  5:   */   protected String name;
/*  6:   */   protected String rhs;
/*  7:   */   protected Grammar enclosingGrammar;
/*  8:   */   
/*  9:   */   public Option(String paramString1, String paramString2, Grammar paramGrammar)
/* 10:   */   {
/* 11:18 */     this.name = paramString1;
/* 12:19 */     this.rhs = paramString2;
/* 13:20 */     setEnclosingGrammar(paramGrammar);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Grammar getEnclosingGrammar()
/* 17:   */   {
/* 18:24 */     return this.enclosingGrammar;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getName()
/* 22:   */   {
/* 23:28 */     return this.name;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getRHS()
/* 27:   */   {
/* 28:32 */     return this.rhs;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setEnclosingGrammar(Grammar paramGrammar)
/* 32:   */   {
/* 33:36 */     this.enclosingGrammar = paramGrammar;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setName(String paramString)
/* 37:   */   {
/* 38:40 */     this.name = paramString;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setRHS(String paramString)
/* 42:   */   {
/* 43:44 */     this.rhs = paramString;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String toString()
/* 47:   */   {
/* 48:48 */     return "\t" + this.name + "=" + this.rhs;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.preprocessor.Option
 * JD-Core Version:    0.7.0.1
 */
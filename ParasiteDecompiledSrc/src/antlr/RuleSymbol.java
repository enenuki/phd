/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.impl.Vector;
/*  4:   */ 
/*  5:   */ class RuleSymbol
/*  6:   */   extends GrammarSymbol
/*  7:   */ {
/*  8:   */   RuleBlock block;
/*  9:   */   boolean defined;
/* 10:   */   Vector references;
/* 11:   */   String access;
/* 12:   */   String comment;
/* 13:   */   
/* 14:   */   public RuleSymbol(String paramString)
/* 15:   */   {
/* 16:22 */     super(paramString);
/* 17:23 */     this.references = new Vector();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void addReference(RuleRefElement paramRuleRefElement)
/* 21:   */   {
/* 22:27 */     this.references.appendElement(paramRuleRefElement);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public RuleBlock getBlock()
/* 26:   */   {
/* 27:31 */     return this.block;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public RuleRefElement getReference(int paramInt)
/* 31:   */   {
/* 32:35 */     return (RuleRefElement)this.references.elementAt(paramInt);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean isDefined()
/* 36:   */   {
/* 37:39 */     return this.defined;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int numReferences()
/* 41:   */   {
/* 42:43 */     return this.references.size();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setBlock(RuleBlock paramRuleBlock)
/* 46:   */   {
/* 47:47 */     this.block = paramRuleBlock;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setDefined()
/* 51:   */   {
/* 52:51 */     this.defined = true;
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.RuleSymbol
 * JD-Core Version:    0.7.0.1
 */
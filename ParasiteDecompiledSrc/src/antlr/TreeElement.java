/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.impl.Vector;
/*  4:   */ 
/*  5:   */ class TreeElement
/*  6:   */   extends AlternativeBlock
/*  7:   */ {
/*  8:   */   GrammarAtom root;
/*  9:   */   
/* 10:   */   public TreeElement(Grammar paramGrammar, Token paramToken)
/* 11:   */   {
/* 12:15 */     super(paramGrammar, paramToken, false);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void generate()
/* 16:   */   {
/* 17:19 */     this.grammar.generator.gen(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Lookahead look(int paramInt)
/* 21:   */   {
/* 22:23 */     return this.grammar.theLLkAnalyzer.look(paramInt, this);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String toString()
/* 26:   */   {
/* 27:27 */     String str = " #(" + this.root;
/* 28:28 */     Alternative localAlternative = (Alternative)this.alternatives.elementAt(0);
/* 29:29 */     AlternativeElement localAlternativeElement = localAlternative.head;
/* 30:30 */     while (localAlternativeElement != null)
/* 31:   */     {
/* 32:31 */       str = str + localAlternativeElement;
/* 33:32 */       localAlternativeElement = localAlternativeElement.next;
/* 34:   */     }
/* 35:34 */     return str + " )";
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TreeElement
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class Alternative
/*  4:   */ {
/*  5:   */   AlternativeElement head;
/*  6:   */   AlternativeElement tail;
/*  7:   */   protected SynPredBlock synPred;
/*  8:   */   protected String semPred;
/*  9:   */   protected ExceptionSpec exceptionSpec;
/* 10:   */   protected Lookahead[] cache;
/* 11:   */   protected int lookaheadDepth;
/* 12:34 */   protected Token treeSpecifier = null;
/* 13:   */   private boolean doAutoGen;
/* 14:   */   
/* 15:   */   public Alternative() {}
/* 16:   */   
/* 17:   */   public Alternative(AlternativeElement paramAlternativeElement)
/* 18:   */   {
/* 19:43 */     addElement(paramAlternativeElement);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void addElement(AlternativeElement paramAlternativeElement)
/* 23:   */   {
/* 24:48 */     if (this.head == null)
/* 25:   */     {
/* 26:49 */       this.head = (this.tail = paramAlternativeElement);
/* 27:   */     }
/* 28:   */     else
/* 29:   */     {
/* 30:52 */       this.tail.next = paramAlternativeElement;
/* 31:53 */       this.tail = paramAlternativeElement;
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean atStart()
/* 36:   */   {
/* 37:58 */     return this.head == null;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean getAutoGen()
/* 41:   */   {
/* 42:63 */     return (this.doAutoGen) && (this.treeSpecifier == null);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Token getTreeSpecifier()
/* 46:   */   {
/* 47:67 */     return this.treeSpecifier;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setAutoGen(boolean paramBoolean)
/* 51:   */   {
/* 52:71 */     this.doAutoGen = paramBoolean;
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.Alternative
 * JD-Core Version:    0.7.0.1
 */
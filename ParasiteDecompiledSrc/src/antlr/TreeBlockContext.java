/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ class TreeBlockContext
/*  4:   */   extends BlockContext
/*  5:   */ {
/*  6:22 */   protected boolean nextElementIsRoot = true;
/*  7:   */   
/*  8:   */   public void addAlternativeElement(AlternativeElement paramAlternativeElement)
/*  9:   */   {
/* 10:26 */     TreeElement localTreeElement = (TreeElement)this.block;
/* 11:27 */     if (this.nextElementIsRoot)
/* 12:   */     {
/* 13:28 */       localTreeElement.root = ((GrammarAtom)paramAlternativeElement);
/* 14:29 */       this.nextElementIsRoot = false;
/* 15:   */     }
/* 16:   */     else
/* 17:   */     {
/* 18:32 */       super.addAlternativeElement(paramAlternativeElement);
/* 19:   */     }
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TreeBlockContext
 * JD-Core Version:    0.7.0.1
 */
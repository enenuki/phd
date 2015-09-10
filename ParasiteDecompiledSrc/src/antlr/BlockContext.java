/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.impl.Vector;
/*  4:   */ 
/*  5:   */ class BlockContext
/*  6:   */ {
/*  7:   */   AlternativeBlock block;
/*  8:   */   int altNum;
/*  9:   */   BlockEndElement blockEnd;
/* 10:   */   
/* 11:   */   public void addAlternativeElement(AlternativeElement paramAlternativeElement)
/* 12:   */   {
/* 13:22 */     currentAlt().addElement(paramAlternativeElement);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Alternative currentAlt()
/* 17:   */   {
/* 18:26 */     return (Alternative)this.block.alternatives.elementAt(this.altNum);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public AlternativeElement currentElement()
/* 22:   */   {
/* 23:30 */     return currentAlt().tail;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.BlockContext
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class ParserSharedInputState
/*  4:   */ {
/*  5:   */   protected TokenBuffer input;
/*  6:20 */   public int guessing = 0;
/*  7:   */   protected String filename;
/*  8:   */   
/*  9:   */   public void reset()
/* 10:   */   {
/* 11:26 */     this.guessing = 0;
/* 12:27 */     this.filename = null;
/* 13:28 */     this.input.reset();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getFilename()
/* 17:   */   {
/* 18:32 */     return this.filename;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public TokenBuffer getInput()
/* 22:   */   {
/* 23:36 */     return this.input;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ParserSharedInputState
 * JD-Core Version:    0.7.0.1
 */
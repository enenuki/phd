/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class TokenStreamRecognitionException
/*  4:   */   extends TokenStreamException
/*  5:   */ {
/*  6:   */   public RecognitionException recog;
/*  7:   */   
/*  8:   */   public TokenStreamRecognitionException(RecognitionException paramRecognitionException)
/*  9:   */   {
/* 10:18 */     super(paramRecognitionException.getMessage());
/* 11:19 */     this.recog = paramRecognitionException;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String toString()
/* 15:   */   {
/* 16:23 */     return this.recog.toString();
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenStreamRecognitionException
 * JD-Core Version:    0.7.0.1
 */
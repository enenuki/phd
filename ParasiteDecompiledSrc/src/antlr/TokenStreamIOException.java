/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ public class TokenStreamIOException
/*  6:   */   extends TokenStreamException
/*  7:   */ {
/*  8:   */   public IOException io;
/*  9:   */   
/* 10:   */   public TokenStreamIOException(IOException paramIOException)
/* 11:   */   {
/* 12:23 */     super(paramIOException.getMessage());
/* 13:24 */     this.io = paramIOException;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenStreamIOException
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ public class CharStreamIOException
/*  6:   */   extends CharStreamException
/*  7:   */ {
/*  8:   */   public IOException io;
/*  9:   */   
/* 10:   */   public CharStreamIOException(IOException paramIOException)
/* 11:   */   {
/* 12:19 */     super(paramIOException.getMessage());
/* 13:20 */     this.io = paramIOException;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CharStreamIOException
 * JD-Core Version:    0.7.0.1
 */
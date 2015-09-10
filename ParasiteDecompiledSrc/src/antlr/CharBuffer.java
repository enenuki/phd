/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.Reader;
/*  5:   */ 
/*  6:   */ public class CharBuffer
/*  7:   */   extends InputBuffer
/*  8:   */ {
/*  9:   */   public transient Reader input;
/* 10:   */   
/* 11:   */   public CharBuffer(Reader paramReader)
/* 12:   */   {
/* 13:36 */     this.input = paramReader;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void fill(int paramInt)
/* 17:   */     throws CharStreamException
/* 18:   */   {
/* 19:   */     try
/* 20:   */     {
/* 21:42 */       syncConsume();
/* 22:44 */       while (this.queue.nbrEntries < paramInt + this.markerOffset) {
/* 23:46 */         this.queue.append((char)this.input.read());
/* 24:   */       }
/* 25:   */     }
/* 26:   */     catch (IOException localIOException)
/* 27:   */     {
/* 28:50 */       throw new CharStreamIOException(localIOException);
/* 29:   */     }
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CharBuffer
 * JD-Core Version:    0.7.0.1
 */
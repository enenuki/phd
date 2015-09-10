/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ import java.io.Reader;
/*  5:   */ 
/*  6:   */ public class LexerSharedInputState
/*  7:   */ {
/*  8:19 */   protected int column = 1;
/*  9:20 */   protected int line = 1;
/* 10:21 */   protected int tokenStartColumn = 1;
/* 11:22 */   protected int tokenStartLine = 1;
/* 12:   */   protected InputBuffer input;
/* 13:   */   protected String filename;
/* 14:28 */   public int guessing = 0;
/* 15:   */   
/* 16:   */   public LexerSharedInputState(InputBuffer paramInputBuffer)
/* 17:   */   {
/* 18:31 */     this.input = paramInputBuffer;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public LexerSharedInputState(InputStream paramInputStream)
/* 22:   */   {
/* 23:35 */     this(new ByteBuffer(paramInputStream));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public LexerSharedInputState(Reader paramReader)
/* 27:   */   {
/* 28:39 */     this(new CharBuffer(paramReader));
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getFilename()
/* 32:   */   {
/* 33:43 */     return this.filename;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public InputBuffer getInput()
/* 37:   */   {
/* 38:47 */     return this.input;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getLine()
/* 42:   */   {
/* 43:52 */     return this.line;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int getTokenStartColumn()
/* 47:   */   {
/* 48:57 */     return this.tokenStartColumn;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int getTokenStartLine()
/* 52:   */   {
/* 53:62 */     return this.tokenStartLine;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int getColumn()
/* 57:   */   {
/* 58:67 */     return this.column;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void reset()
/* 62:   */   {
/* 63:71 */     this.column = 1;
/* 64:72 */     this.line = 1;
/* 65:73 */     this.tokenStartColumn = 1;
/* 66:74 */     this.tokenStartLine = 1;
/* 67:75 */     this.guessing = 0;
/* 68:76 */     this.filename = null;
/* 69:77 */     this.input.reset();
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.LexerSharedInputState
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class LLkParser
/*  6:   */   extends Parser
/*  7:   */ {
/*  8:   */   int k;
/*  9:   */   
/* 10:   */   public LLkParser(int paramInt)
/* 11:   */   {
/* 12:21 */     this.k = paramInt;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public LLkParser(ParserSharedInputState paramParserSharedInputState, int paramInt)
/* 16:   */   {
/* 17:25 */     super(paramParserSharedInputState);
/* 18:26 */     this.k = paramInt;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public LLkParser(TokenBuffer paramTokenBuffer, int paramInt)
/* 22:   */   {
/* 23:30 */     this.k = paramInt;
/* 24:31 */     setTokenBuffer(paramTokenBuffer);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public LLkParser(TokenStream paramTokenStream, int paramInt)
/* 28:   */   {
/* 29:35 */     this.k = paramInt;
/* 30:36 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramTokenStream);
/* 31:37 */     setTokenBuffer(localTokenBuffer);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void consume()
/* 35:   */     throws TokenStreamException
/* 36:   */   {
/* 37:48 */     this.inputState.input.consume();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int LA(int paramInt)
/* 41:   */     throws TokenStreamException
/* 42:   */   {
/* 43:52 */     return this.inputState.input.LA(paramInt);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Token LT(int paramInt)
/* 47:   */     throws TokenStreamException
/* 48:   */   {
/* 49:56 */     return this.inputState.input.LT(paramInt);
/* 50:   */   }
/* 51:   */   
/* 52:   */   private void trace(String paramString1, String paramString2)
/* 53:   */     throws TokenStreamException
/* 54:   */   {
/* 55:60 */     traceIndent();
/* 56:61 */     System.out.print(paramString1 + paramString2 + (this.inputState.guessing > 0 ? "; [guessing]" : "; "));
/* 57:62 */     for (int i = 1; i <= this.k; i++)
/* 58:   */     {
/* 59:63 */       if (i != 1) {
/* 60:64 */         System.out.print(", ");
/* 61:   */       }
/* 62:66 */       if (LT(i) != null) {
/* 63:67 */         System.out.print("LA(" + i + ")==" + LT(i).getText());
/* 64:   */       } else {
/* 65:70 */         System.out.print("LA(" + i + ")==null");
/* 66:   */       }
/* 67:   */     }
/* 68:73 */     System.out.println("");
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void traceIn(String paramString)
/* 72:   */     throws TokenStreamException
/* 73:   */   {
/* 74:77 */     this.traceDepth += 1;
/* 75:78 */     trace("> ", paramString);
/* 76:   */   }
/* 77:   */   
/* 78:   */   public void traceOut(String paramString)
/* 79:   */     throws TokenStreamException
/* 80:   */   {
/* 81:82 */     trace("< ", paramString);
/* 82:83 */     this.traceDepth -= 1;
/* 83:   */   }
/* 84:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.LLkParser
 * JD-Core Version:    0.7.0.1
 */
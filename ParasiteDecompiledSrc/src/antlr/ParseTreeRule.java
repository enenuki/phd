/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ 
/*  5:   */ public class ParseTreeRule
/*  6:   */   extends ParseTree
/*  7:   */ {
/*  8:   */   public static final int INVALID_ALT = -1;
/*  9:   */   protected String ruleName;
/* 10:   */   protected int altNumber;
/* 11:   */   
/* 12:   */   public ParseTreeRule(String paramString)
/* 13:   */   {
/* 14:18 */     this(paramString, -1);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ParseTreeRule(String paramString, int paramInt)
/* 18:   */   {
/* 19:22 */     this.ruleName = paramString;
/* 20:23 */     this.altNumber = paramInt;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getRuleName()
/* 24:   */   {
/* 25:27 */     return this.ruleName;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected int getLeftmostDerivation(StringBuffer paramStringBuffer, int paramInt)
/* 29:   */   {
/* 30:35 */     int i = 0;
/* 31:36 */     if (paramInt <= 0)
/* 32:   */     {
/* 33:37 */       paramStringBuffer.append(' ');
/* 34:38 */       paramStringBuffer.append(toString());
/* 35:39 */       return i;
/* 36:   */     }
/* 37:41 */     AST localAST = getFirstChild();
/* 38:42 */     i = 1;
/* 39:44 */     while (localAST != null)
/* 40:   */     {
/* 41:45 */       if ((i >= paramInt) || ((localAST instanceof ParseTreeToken)))
/* 42:   */       {
/* 43:46 */         paramStringBuffer.append(' ');
/* 44:47 */         paramStringBuffer.append(localAST.toString());
/* 45:   */       }
/* 46:   */       else
/* 47:   */       {
/* 48:51 */         int j = paramInt - i;
/* 49:52 */         int k = ((ParseTree)localAST).getLeftmostDerivation(paramStringBuffer, j);
/* 50:   */         
/* 51:54 */         i += k;
/* 52:   */       }
/* 53:56 */       localAST = localAST.getNextSibling();
/* 54:   */     }
/* 55:58 */     return i;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String toString()
/* 59:   */   {
/* 60:62 */     if (this.altNumber == -1) {
/* 61:63 */       return '<' + this.ruleName + '>';
/* 62:   */     }
/* 63:66 */     return '<' + this.ruleName + "[" + this.altNumber + "]>";
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ParseTreeRule
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import antlr.collections.impl.BitSet;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ 
/*   7:    */ public class TreeParser
/*   8:    */ {
/*   9: 19 */   public static ASTNULLType ASTNULL = new ASTNULLType();
/*  10:    */   protected AST _retTree;
/*  11:    */   protected TreeParserSharedInputState inputState;
/*  12:    */   protected String[] tokenNames;
/*  13:    */   protected AST returnAST;
/*  14: 39 */   protected ASTFactory astFactory = new ASTFactory();
/*  15: 42 */   protected int traceDepth = 0;
/*  16:    */   
/*  17:    */   public TreeParser()
/*  18:    */   {
/*  19: 45 */     this.inputState = new TreeParserSharedInputState();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public AST getAST()
/*  23:    */   {
/*  24: 50 */     return this.returnAST;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ASTFactory getASTFactory()
/*  28:    */   {
/*  29: 54 */     return this.astFactory;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getTokenName(int paramInt)
/*  33:    */   {
/*  34: 58 */     return this.tokenNames[paramInt];
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String[] getTokenNames()
/*  38:    */   {
/*  39: 62 */     return this.tokenNames;
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected void match(AST paramAST, int paramInt)
/*  43:    */     throws MismatchedTokenException
/*  44:    */   {
/*  45: 67 */     if ((paramAST == null) || (paramAST == ASTNULL) || (paramAST.getType() != paramInt)) {
/*  46: 68 */       throw new MismatchedTokenException(getTokenNames(), paramAST, paramInt, false);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void match(AST paramAST, BitSet paramBitSet)
/*  51:    */     throws MismatchedTokenException
/*  52:    */   {
/*  53: 77 */     if ((paramAST == null) || (paramAST == ASTNULL) || (!paramBitSet.member(paramAST.getType()))) {
/*  54: 78 */       throw new MismatchedTokenException(getTokenNames(), paramAST, paramBitSet, false);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected void matchNot(AST paramAST, int paramInt)
/*  59:    */     throws MismatchedTokenException
/*  60:    */   {
/*  61: 84 */     if ((paramAST == null) || (paramAST == ASTNULL) || (paramAST.getType() == paramInt)) {
/*  62: 85 */       throw new MismatchedTokenException(getTokenNames(), paramAST, paramInt, true);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   /**
/*  67:    */    * @deprecated
/*  68:    */    */
/*  69:    */   public static void panic()
/*  70:    */   {
/*  71: 96 */     System.err.println("TreeWalker: panic");
/*  72: 97 */     Utils.error("");
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void reportError(RecognitionException paramRecognitionException)
/*  76:    */   {
/*  77:102 */     System.err.println(paramRecognitionException.toString());
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void reportError(String paramString)
/*  81:    */   {
/*  82:107 */     System.err.println("error: " + paramString);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void reportWarning(String paramString)
/*  86:    */   {
/*  87:112 */     System.err.println("warning: " + paramString);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setASTFactory(ASTFactory paramASTFactory)
/*  91:    */   {
/*  92:120 */     this.astFactory = paramASTFactory;
/*  93:    */   }
/*  94:    */   
/*  95:    */   /**
/*  96:    */    * @deprecated
/*  97:    */    */
/*  98:    */   public void setASTNodeType(String paramString)
/*  99:    */   {
/* 100:127 */     setASTNodeClass(paramString);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setASTNodeClass(String paramString)
/* 104:    */   {
/* 105:132 */     this.astFactory.setASTNodeType(paramString);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void traceIndent()
/* 109:    */   {
/* 110:136 */     for (int i = 0; i < this.traceDepth; i++) {
/* 111:137 */       System.out.print(" ");
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void traceIn(String paramString, AST paramAST)
/* 116:    */   {
/* 117:141 */     this.traceDepth += 1;
/* 118:142 */     traceIndent();
/* 119:143 */     System.out.println("> " + paramString + "(" + (paramAST != null ? paramAST.toString() : "null") + ")" + (this.inputState.guessing > 0 ? " [guessing]" : ""));
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void traceOut(String paramString, AST paramAST)
/* 123:    */   {
/* 124:149 */     traceIndent();
/* 125:150 */     System.out.println("< " + paramString + "(" + (paramAST != null ? paramAST.toString() : "null") + ")" + (this.inputState.guessing > 0 ? " [guessing]" : ""));
/* 126:    */     
/* 127:    */ 
/* 128:153 */     this.traceDepth -= 1;
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TreeParser
 * JD-Core Version:    0.7.0.1
 */
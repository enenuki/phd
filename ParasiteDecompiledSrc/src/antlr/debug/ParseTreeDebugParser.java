/*   1:    */ package antlr.debug;
/*   2:    */ 
/*   3:    */ import antlr.CommonToken;
/*   4:    */ import antlr.LLkParser;
/*   5:    */ import antlr.MismatchedTokenException;
/*   6:    */ import antlr.ParseTree;
/*   7:    */ import antlr.ParseTreeRule;
/*   8:    */ import antlr.ParseTreeToken;
/*   9:    */ import antlr.ParserSharedInputState;
/*  10:    */ import antlr.TokenBuffer;
/*  11:    */ import antlr.TokenStream;
/*  12:    */ import antlr.TokenStreamException;
/*  13:    */ import antlr.collections.impl.BitSet;
/*  14:    */ import java.util.Stack;
/*  15:    */ 
/*  16:    */ public class ParseTreeDebugParser
/*  17:    */   extends LLkParser
/*  18:    */ {
/*  19: 23 */   protected Stack currentParseTreeRoot = new Stack();
/*  20: 28 */   protected ParseTreeRule mostRecentParseTreeRoot = null;
/*  21: 31 */   protected int numberOfDerivationSteps = 1;
/*  22:    */   
/*  23:    */   public ParseTreeDebugParser(int paramInt)
/*  24:    */   {
/*  25: 34 */     super(paramInt);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ParseTreeDebugParser(ParserSharedInputState paramParserSharedInputState, int paramInt)
/*  29:    */   {
/*  30: 38 */     super(paramParserSharedInputState, paramInt);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ParseTreeDebugParser(TokenBuffer paramTokenBuffer, int paramInt)
/*  34:    */   {
/*  35: 42 */     super(paramTokenBuffer, paramInt);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ParseTreeDebugParser(TokenStream paramTokenStream, int paramInt)
/*  39:    */   {
/*  40: 46 */     super(paramTokenStream, paramInt);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ParseTree getParseTree()
/*  44:    */   {
/*  45: 50 */     return this.mostRecentParseTreeRoot;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getNumberOfDerivationSteps()
/*  49:    */   {
/*  50: 54 */     return this.numberOfDerivationSteps;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void match(int paramInt)
/*  54:    */     throws MismatchedTokenException, TokenStreamException
/*  55:    */   {
/*  56: 58 */     addCurrentTokenToParseTree();
/*  57: 59 */     super.match(paramInt);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void match(BitSet paramBitSet)
/*  61:    */     throws MismatchedTokenException, TokenStreamException
/*  62:    */   {
/*  63: 63 */     addCurrentTokenToParseTree();
/*  64: 64 */     super.match(paramBitSet);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void matchNot(int paramInt)
/*  68:    */     throws MismatchedTokenException, TokenStreamException
/*  69:    */   {
/*  70: 68 */     addCurrentTokenToParseTree();
/*  71: 69 */     super.matchNot(paramInt);
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void addCurrentTokenToParseTree()
/*  75:    */     throws TokenStreamException
/*  76:    */   {
/*  77: 80 */     if (this.inputState.guessing > 0) {
/*  78: 81 */       return;
/*  79:    */     }
/*  80: 83 */     ParseTreeRule localParseTreeRule = (ParseTreeRule)this.currentParseTreeRoot.peek();
/*  81: 84 */     ParseTreeToken localParseTreeToken = null;
/*  82: 85 */     if (LA(1) == 1) {
/*  83: 86 */       localParseTreeToken = new ParseTreeToken(new CommonToken("EOF"));
/*  84:    */     } else {
/*  85: 89 */       localParseTreeToken = new ParseTreeToken(LT(1));
/*  86:    */     }
/*  87: 91 */     localParseTreeRule.addChild(localParseTreeToken);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void traceIn(String paramString)
/*  91:    */     throws TokenStreamException
/*  92:    */   {
/*  93: 96 */     if (this.inputState.guessing > 0) {
/*  94: 97 */       return;
/*  95:    */     }
/*  96: 99 */     ParseTreeRule localParseTreeRule1 = new ParseTreeRule(paramString);
/*  97:100 */     if (this.currentParseTreeRoot.size() > 0)
/*  98:    */     {
/*  99:101 */       ParseTreeRule localParseTreeRule2 = (ParseTreeRule)this.currentParseTreeRoot.peek();
/* 100:102 */       localParseTreeRule2.addChild(localParseTreeRule1);
/* 101:    */     }
/* 102:104 */     this.currentParseTreeRoot.push(localParseTreeRule1);
/* 103:105 */     this.numberOfDerivationSteps += 1;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void traceOut(String paramString)
/* 107:    */     throws TokenStreamException
/* 108:    */   {
/* 109:110 */     if (this.inputState.guessing > 0) {
/* 110:111 */       return;
/* 111:    */     }
/* 112:113 */     this.mostRecentParseTreeRoot = ((ParseTreeRule)this.currentParseTreeRoot.pop());
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ParseTreeDebugParser
 * JD-Core Version:    0.7.0.1
 */
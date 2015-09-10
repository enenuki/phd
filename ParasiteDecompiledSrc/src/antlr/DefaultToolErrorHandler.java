/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ 
/*   5:    */ class DefaultToolErrorHandler
/*   6:    */   implements ToolErrorHandler
/*   7:    */ {
/*   8:    */   private final Tool antlrTool;
/*   9:    */   
/*  10:    */   DefaultToolErrorHandler(Tool paramTool)
/*  11:    */   {
/*  12: 14 */     this.antlrTool = paramTool;
/*  13:    */   }
/*  14:    */   
/*  15: 18 */   CharFormatter javaCharFormatter = new JavaCharFormatter();
/*  16:    */   
/*  17:    */   private void dumpSets(String[] paramArrayOfString, int paramInt1, Grammar paramGrammar, boolean paramBoolean, int paramInt2, Lookahead[] paramArrayOfLookahead)
/*  18:    */   {
/*  19: 36 */     StringBuffer localStringBuffer = new StringBuffer(100);
/*  20: 37 */     for (int i = 1; i <= paramInt2; i++)
/*  21:    */     {
/*  22: 38 */       localStringBuffer.append("k==").append(i).append(':');
/*  23: 39 */       if (paramBoolean)
/*  24:    */       {
/*  25: 40 */         String str = paramArrayOfLookahead[i].fset.toStringWithRanges(",", this.javaCharFormatter);
/*  26: 41 */         if (paramArrayOfLookahead[i].containsEpsilon())
/*  27:    */         {
/*  28: 42 */           localStringBuffer.append("<end-of-token>");
/*  29: 43 */           if (str.length() > 0) {
/*  30: 44 */             localStringBuffer.append(',');
/*  31:    */           }
/*  32:    */         }
/*  33: 47 */         localStringBuffer.append(str);
/*  34:    */       }
/*  35:    */       else
/*  36:    */       {
/*  37: 49 */         localStringBuffer.append(paramArrayOfLookahead[i].fset.toString(",", paramGrammar.tokenManager.getVocabulary()));
/*  38:    */       }
/*  39: 51 */       paramArrayOfString[(paramInt1++)] = localStringBuffer.toString();
/*  40: 52 */       localStringBuffer.setLength(0);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void warnAltAmbiguity(Grammar paramGrammar, AlternativeBlock paramAlternativeBlock, boolean paramBoolean, int paramInt1, Lookahead[] paramArrayOfLookahead, int paramInt2, int paramInt3)
/*  45:    */   {
/*  46: 72 */     StringBuffer localStringBuffer = new StringBuffer(100);
/*  47: 73 */     if (((paramAlternativeBlock instanceof RuleBlock)) && (((RuleBlock)paramAlternativeBlock).isLexerAutoGenRule()))
/*  48:    */     {
/*  49: 74 */       localObject = paramAlternativeBlock.getAlternativeAt(paramInt2);
/*  50: 75 */       Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(paramInt3);
/*  51: 76 */       RuleRefElement localRuleRefElement1 = (RuleRefElement)((Alternative)localObject).head;
/*  52: 77 */       RuleRefElement localRuleRefElement2 = (RuleRefElement)localAlternative.head;
/*  53: 78 */       String str1 = CodeGenerator.reverseLexerRuleName(localRuleRefElement1.targetRule);
/*  54: 79 */       String str2 = CodeGenerator.reverseLexerRuleName(localRuleRefElement2.targetRule);
/*  55: 80 */       localStringBuffer.append("lexical nondeterminism between rules ");
/*  56: 81 */       localStringBuffer.append(str1).append(" and ").append(str2).append(" upon");
/*  57:    */     }
/*  58:    */     else
/*  59:    */     {
/*  60: 84 */       if (paramBoolean) {
/*  61: 85 */         localStringBuffer.append("lexical ");
/*  62:    */       }
/*  63: 87 */       localStringBuffer.append("nondeterminism between alts ");
/*  64: 88 */       localStringBuffer.append(paramInt2 + 1).append(" and ");
/*  65: 89 */       localStringBuffer.append(paramInt3 + 1).append(" of block upon");
/*  66:    */     }
/*  67: 91 */     Object localObject = new String[paramInt1 + 1];
/*  68: 92 */     localObject[0] = localStringBuffer.toString();
/*  69: 93 */     dumpSets((String[])localObject, 1, paramGrammar, paramBoolean, paramInt1, paramArrayOfLookahead);
/*  70: 94 */     this.antlrTool.warning((String[])localObject, paramGrammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void warnAltExitAmbiguity(Grammar paramGrammar, BlockWithImpliedExitPath paramBlockWithImpliedExitPath, boolean paramBoolean, int paramInt1, Lookahead[] paramArrayOfLookahead, int paramInt2)
/*  74:    */   {
/*  75:113 */     String[] arrayOfString = new String[paramInt1 + 2];
/*  76:114 */     arrayOfString[0] = ((paramBoolean ? "lexical " : "") + "nondeterminism upon");
/*  77:115 */     dumpSets(arrayOfString, 1, paramGrammar, paramBoolean, paramInt1, paramArrayOfLookahead);
/*  78:116 */     arrayOfString[(paramInt1 + 1)] = ("between alt " + (paramInt2 + 1) + " and exit branch of block");
/*  79:117 */     this.antlrTool.warning(arrayOfString, paramGrammar.getFilename(), paramBlockWithImpliedExitPath.getLine(), paramBlockWithImpliedExitPath.getColumn());
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.DefaultToolErrorHandler
 * JD-Core Version:    0.7.0.1
 */
package antlr;

import antlr.collections.impl.BitSet;

public abstract interface ANTLRGrammarParseBehavior
{
  public abstract void abortGrammar();
  
  public abstract void beginAlt(boolean paramBoolean);
  
  public abstract void beginChildList();
  
  public abstract void beginExceptionGroup();
  
  public abstract void beginExceptionSpec(Token paramToken);
  
  public abstract void beginSubRule(Token paramToken1, Token paramToken2, boolean paramBoolean);
  
  public abstract void beginTree(Token paramToken)
    throws SemanticException;
  
  public abstract void defineRuleName(Token paramToken, String paramString1, boolean paramBoolean, String paramString2)
    throws SemanticException;
  
  public abstract void defineToken(Token paramToken1, Token paramToken2);
  
  public abstract void endAlt();
  
  public abstract void endChildList();
  
  public abstract void endExceptionGroup();
  
  public abstract void endExceptionSpec();
  
  public abstract void endGrammar();
  
  public abstract void endOptions();
  
  public abstract void endRule(String paramString);
  
  public abstract void endSubRule();
  
  public abstract void endTree();
  
  public abstract void hasError();
  
  public abstract void noASTSubRule();
  
  public abstract void oneOrMoreSubRule();
  
  public abstract void optionalSubRule();
  
  public abstract void refAction(Token paramToken);
  
  public abstract void refArgAction(Token paramToken);
  
  public abstract void setUserExceptions(String paramString);
  
  public abstract void refCharLiteral(Token paramToken1, Token paramToken2, boolean paramBoolean1, int paramInt, boolean paramBoolean2);
  
  public abstract void refCharRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean);
  
  public abstract void refElementOption(Token paramToken1, Token paramToken2);
  
  public abstract void refTokensSpecElementOption(Token paramToken1, Token paramToken2, Token paramToken3);
  
  public abstract void refExceptionHandler(Token paramToken1, Token paramToken2);
  
  public abstract void refHeaderAction(Token paramToken1, Token paramToken2);
  
  public abstract void refInitAction(Token paramToken);
  
  public abstract void refMemberAction(Token paramToken);
  
  public abstract void refPreambleAction(Token paramToken);
  
  public abstract void refReturnAction(Token paramToken);
  
  public abstract void refRule(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, int paramInt);
  
  public abstract void refSemPred(Token paramToken);
  
  public abstract void refStringLiteral(Token paramToken1, Token paramToken2, int paramInt, boolean paramBoolean);
  
  public abstract void refToken(Token paramToken1, Token paramToken2, Token paramToken3, Token paramToken4, boolean paramBoolean1, int paramInt, boolean paramBoolean2);
  
  public abstract void refTokenRange(Token paramToken1, Token paramToken2, Token paramToken3, int paramInt, boolean paramBoolean);
  
  public abstract void refTreeSpecifier(Token paramToken);
  
  public abstract void refWildcard(Token paramToken1, Token paramToken2, int paramInt);
  
  public abstract void setArgOfRuleRef(Token paramToken);
  
  public abstract void setCharVocabulary(BitSet paramBitSet);
  
  public abstract void setFileOption(Token paramToken1, Token paramToken2, String paramString);
  
  public abstract void setGrammarOption(Token paramToken1, Token paramToken2);
  
  public abstract void setRuleOption(Token paramToken1, Token paramToken2);
  
  public abstract void setSubruleOption(Token paramToken1, Token paramToken2);
  
  public abstract void startLexer(String paramString1, Token paramToken, String paramString2, String paramString3);
  
  public abstract void startParser(String paramString1, Token paramToken, String paramString2, String paramString3);
  
  public abstract void startTreeWalker(String paramString1, Token paramToken, String paramString2, String paramString3);
  
  public abstract void synPred();
  
  public abstract void zeroOrMoreSubRule();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ANTLRGrammarParseBehavior
 * JD-Core Version:    0.7.0.1
 */
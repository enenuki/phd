package org.hibernate.hql.internal.ast;

import antlr.RecognitionException;

public abstract interface ErrorReporter
{
  public abstract void reportError(RecognitionException paramRecognitionException);
  
  public abstract void reportError(String paramString);
  
  public abstract void reportWarning(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.ErrorReporter
 * JD-Core Version:    0.7.0.1
 */
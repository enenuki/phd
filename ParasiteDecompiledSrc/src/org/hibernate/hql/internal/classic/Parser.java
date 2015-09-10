package org.hibernate.hql.internal.classic;

import org.hibernate.QueryException;

public abstract interface Parser
{
  public abstract void token(String paramString, QueryTranslatorImpl paramQueryTranslatorImpl)
    throws QueryException;
  
  public abstract void start(QueryTranslatorImpl paramQueryTranslatorImpl)
    throws QueryException;
  
  public abstract void end(QueryTranslatorImpl paramQueryTranslatorImpl)
    throws QueryException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.Parser
 * JD-Core Version:    0.7.0.1
 */
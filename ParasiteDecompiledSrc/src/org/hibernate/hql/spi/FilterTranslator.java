package org.hibernate.hql.spi;

import java.util.Map;
import org.hibernate.MappingException;
import org.hibernate.QueryException;

public abstract interface FilterTranslator
  extends QueryTranslator
{
  public abstract void compile(String paramString, Map paramMap, boolean paramBoolean)
    throws QueryException, MappingException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.spi.FilterTranslator
 * JD-Core Version:    0.7.0.1
 */
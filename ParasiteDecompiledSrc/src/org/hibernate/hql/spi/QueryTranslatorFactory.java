package org.hibernate.hql.spi;

import java.util.Map;
import org.hibernate.engine.spi.SessionFactoryImplementor;

public abstract interface QueryTranslatorFactory
{
  public abstract QueryTranslator createQueryTranslator(String paramString1, String paramString2, Map paramMap, SessionFactoryImplementor paramSessionFactoryImplementor);
  
  public abstract FilterTranslator createFilterTranslator(String paramString1, String paramString2, Map paramMap, SessionFactoryImplementor paramSessionFactoryImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.spi.QueryTranslatorFactory
 * JD-Core Version:    0.7.0.1
 */
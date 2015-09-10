package org.hibernate.hql.spi;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.QueryException;
import org.hibernate.ScrollableResults;
import org.hibernate.engine.spi.QueryParameters;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.spi.EventSource;
import org.hibernate.type.Type;

public abstract interface QueryTranslator
{
  public static final String ERROR_CANNOT_FETCH_WITH_ITERATE = "fetch may not be used with scroll() or iterate()";
  public static final String ERROR_NAMED_PARAMETER_DOES_NOT_APPEAR = "Named parameter does not appear in Query: ";
  public static final String ERROR_CANNOT_DETERMINE_TYPE = "Could not determine type of: ";
  public static final String ERROR_CANNOT_FORMAT_LITERAL = "Could not format constant value to SQL literal: ";
  
  public abstract void compile(Map paramMap, boolean paramBoolean)
    throws QueryException, MappingException;
  
  public abstract List list(SessionImplementor paramSessionImplementor, QueryParameters paramQueryParameters)
    throws HibernateException;
  
  public abstract Iterator iterate(QueryParameters paramQueryParameters, EventSource paramEventSource)
    throws HibernateException;
  
  public abstract ScrollableResults scroll(QueryParameters paramQueryParameters, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract int executeUpdate(QueryParameters paramQueryParameters, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Set getQuerySpaces();
  
  public abstract String getQueryIdentifier();
  
  public abstract String getSQLString();
  
  public abstract List collectSqlStrings();
  
  public abstract String getQueryString();
  
  public abstract Map getEnabledFilters();
  
  public abstract Type[] getReturnTypes();
  
  public abstract String[] getReturnAliases();
  
  public abstract String[][] getColumnNames();
  
  public abstract ParameterTranslations getParameterTranslations();
  
  public abstract void validateScrollability()
    throws HibernateException;
  
  public abstract boolean containsCollectionFetches();
  
  public abstract boolean isManipulationStatement();
  
  public abstract Class getDynamicInstantiationResultType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.spi.QueryTranslator
 * JD-Core Version:    0.7.0.1
 */
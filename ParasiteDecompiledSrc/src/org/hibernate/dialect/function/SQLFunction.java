package org.hibernate.dialect.function;

import java.util.List;
import org.hibernate.QueryException;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

public abstract interface SQLFunction
{
  public abstract boolean hasArguments();
  
  public abstract boolean hasParenthesesIfNoArguments();
  
  public abstract Type getReturnType(Type paramType, Mapping paramMapping)
    throws QueryException;
  
  public abstract String render(Type paramType, List paramList, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws QueryException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.SQLFunction
 * JD-Core Version:    0.7.0.1
 */
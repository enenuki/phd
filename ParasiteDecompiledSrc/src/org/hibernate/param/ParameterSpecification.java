package org.hibernate.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.hibernate.engine.spi.QueryParameters;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;

public abstract interface ParameterSpecification
{
  public abstract int bind(PreparedStatement paramPreparedStatement, QueryParameters paramQueryParameters, SessionImplementor paramSessionImplementor, int paramInt)
    throws SQLException;
  
  public abstract Type getExpectedType();
  
  public abstract void setExpectedType(Type paramType);
  
  public abstract String renderDisplayInfo();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.param.ParameterSpecification
 * JD-Core Version:    0.7.0.1
 */
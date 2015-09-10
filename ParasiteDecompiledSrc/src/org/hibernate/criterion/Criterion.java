package org.hibernate.criterion;

import java.io.Serializable;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.TypedValue;

public abstract interface Criterion
  extends Serializable
{
  public abstract String toSqlString(Criteria paramCriteria, CriteriaQuery paramCriteriaQuery)
    throws HibernateException;
  
  public abstract TypedValue[] getTypedValues(Criteria paramCriteria, CriteriaQuery paramCriteriaQuery)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Criterion
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.criterion;

import org.hibernate.Criteria;

public abstract interface EnhancedProjection
  extends Projection
{
  public abstract String[] getColumnAliases(int paramInt, Criteria paramCriteria, CriteriaQuery paramCriteriaQuery);
  
  public abstract String[] getColumnAliases(String paramString, int paramInt, Criteria paramCriteria, CriteriaQuery paramCriteriaQuery);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.EnhancedProjection
 * JD-Core Version:    0.7.0.1
 */
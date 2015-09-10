package org.hibernate.hql.internal.ast.tree;

import java.util.List;
import org.hibernate.transform.ResultTransformer;

public abstract interface AggregatedSelectExpression
  extends SelectExpression
{
  public abstract List getAggregatedSelectionTypeList();
  
  public abstract String[] getAggregatedAliases();
  
  public abstract ResultTransformer getResultTransformer();
  
  public abstract Class getAggregationResultType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.AggregatedSelectExpression
 * JD-Core Version:    0.7.0.1
 */
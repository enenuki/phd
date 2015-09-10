package javax.persistence.criteria;

import java.util.List;
import javax.persistence.TupleElement;

public abstract interface Selection<X>
  extends TupleElement<X>
{
  public abstract Selection<X> alias(String paramString);
  
  public abstract boolean isCompoundSelection();
  
  public abstract List<Selection<?>> getCompoundSelectionItems();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.Selection
 * JD-Core Version:    0.7.0.1
 */
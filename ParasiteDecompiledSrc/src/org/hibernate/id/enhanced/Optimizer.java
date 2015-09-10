package org.hibernate.id.enhanced;

import java.io.Serializable;
import org.hibernate.id.IntegralDataTypeHolder;

public abstract interface Optimizer
{
  public abstract Serializable generate(AccessCallback paramAccessCallback);
  
  public abstract IntegralDataTypeHolder getLastSourceValue();
  
  public abstract int getIncrementSize();
  
  public abstract boolean applyIncrementSizeToSourceValues();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.enhanced.Optimizer
 * JD-Core Version:    0.7.0.1
 */
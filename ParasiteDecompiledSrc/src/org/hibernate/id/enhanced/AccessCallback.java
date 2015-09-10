package org.hibernate.id.enhanced;

import org.hibernate.id.IntegralDataTypeHolder;

public abstract interface AccessCallback
{
  public abstract IntegralDataTypeHolder getNextValue();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.enhanced.AccessCallback
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.proxy;

import java.io.Serializable;

public abstract interface EntityNotFoundDelegate
{
  public abstract void handleEntityNotFound(String paramString, Serializable paramSerializable);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.EntityNotFoundDelegate
 * JD-Core Version:    0.7.0.1
 */
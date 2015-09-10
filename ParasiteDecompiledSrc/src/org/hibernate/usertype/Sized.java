package org.hibernate.usertype;

import org.hibernate.metamodel.relational.Size;

public abstract interface Sized
{
  public abstract Size[] dictatedSizes();
  
  public abstract Size[] defaultSizes();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.usertype.Sized
 * JD-Core Version:    0.7.0.1
 */
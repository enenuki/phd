package org.hibernate.type;

public abstract interface IdentifierType<T>
  extends Type
{
  public abstract T stringToObject(String paramString)
    throws Exception;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.IdentifierType
 * JD-Core Version:    0.7.0.1
 */
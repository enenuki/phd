package org.hibernate.usertype;

public abstract interface EnhancedUserType
  extends UserType
{
  public abstract String objectToSQLString(Object paramObject);
  
  public abstract String toXMLString(Object paramObject);
  
  public abstract Object fromXMLString(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.usertype.EnhancedUserType
 * JD-Core Version:    0.7.0.1
 */
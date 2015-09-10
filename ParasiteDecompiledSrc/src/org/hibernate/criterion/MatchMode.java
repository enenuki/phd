package org.hibernate.criterion;

public enum MatchMode
{
  EXACT,  START,  END,  ANYWHERE;
  
  private MatchMode() {}
  
  public abstract String toMatchString(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.MatchMode
 * JD-Core Version:    0.7.0.1
 */
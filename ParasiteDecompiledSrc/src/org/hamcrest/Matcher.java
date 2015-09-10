package org.hamcrest;

public abstract interface Matcher<T>
  extends SelfDescribing
{
  public abstract boolean matches(Object paramObject);
  
  public abstract void _dont_implement_Matcher___instead_extend_BaseMatcher_();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.Matcher
 * JD-Core Version:    0.7.0.1
 */
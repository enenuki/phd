package org.hamcrest;

public abstract interface Description
{
  public abstract Description appendText(String paramString);
  
  public abstract Description appendDescriptionOf(SelfDescribing paramSelfDescribing);
  
  public abstract Description appendValue(Object paramObject);
  
  public abstract <T> Description appendValueList(String paramString1, String paramString2, String paramString3, T... paramVarArgs);
  
  public abstract <T> Description appendValueList(String paramString1, String paramString2, String paramString3, Iterable<T> paramIterable);
  
  public abstract Description appendList(String paramString1, String paramString2, String paramString3, Iterable<? extends SelfDescribing> paramIterable);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.Description
 * JD-Core Version:    0.7.0.1
 */
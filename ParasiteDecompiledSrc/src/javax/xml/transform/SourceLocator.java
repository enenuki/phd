package javax.xml.transform;

public abstract interface SourceLocator
{
  public abstract String getPublicId();
  
  public abstract String getSystemId();
  
  public abstract int getLineNumber();
  
  public abstract int getColumnNumber();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.transform.SourceLocator
 * JD-Core Version:    0.7.0.1
 */
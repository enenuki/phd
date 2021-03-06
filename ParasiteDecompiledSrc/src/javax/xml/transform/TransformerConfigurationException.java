package javax.xml.transform;

public class TransformerConfigurationException
  extends TransformerException
{
  public TransformerConfigurationException()
  {
    super("Configuration Error");
  }
  
  public TransformerConfigurationException(String paramString)
  {
    super(paramString);
  }
  
  public TransformerConfigurationException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
  
  public TransformerConfigurationException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public TransformerConfigurationException(String paramString, SourceLocator paramSourceLocator)
  {
    super(paramString, paramSourceLocator);
  }
  
  public TransformerConfigurationException(String paramString, SourceLocator paramSourceLocator, Throwable paramThrowable)
  {
    super(paramString, paramSourceLocator, paramThrowable);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.transform.TransformerConfigurationException
 * JD-Core Version:    0.7.0.1
 */
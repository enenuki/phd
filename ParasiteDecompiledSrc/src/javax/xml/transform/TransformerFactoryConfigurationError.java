package javax.xml.transform;

public class TransformerFactoryConfigurationError
  extends Error
{
  private Exception exception;
  
  public TransformerFactoryConfigurationError()
  {
    this.exception = null;
  }
  
  public TransformerFactoryConfigurationError(String paramString)
  {
    super(paramString);
    this.exception = null;
  }
  
  public TransformerFactoryConfigurationError(Exception paramException)
  {
    super(paramException.toString());
    this.exception = paramException;
  }
  
  public TransformerFactoryConfigurationError(Exception paramException, String paramString)
  {
    super(paramString);
    this.exception = paramException;
  }
  
  public String getMessage()
  {
    String str = super.getMessage();
    if ((str == null) && (this.exception != null)) {
      return this.exception.getMessage();
    }
    return str;
  }
  
  public Exception getException()
  {
    return this.exception;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.transform.TransformerFactoryConfigurationError
 * JD-Core Version:    0.7.0.1
 */
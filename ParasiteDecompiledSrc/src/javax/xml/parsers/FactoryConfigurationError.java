package javax.xml.parsers;

public class FactoryConfigurationError
  extends Error
{
  private Exception exception;
  
  public FactoryConfigurationError()
  {
    this.exception = null;
  }
  
  public FactoryConfigurationError(String paramString)
  {
    super(paramString);
    this.exception = null;
  }
  
  public FactoryConfigurationError(Exception paramException)
  {
    super(paramException.toString());
    this.exception = paramException;
  }
  
  public FactoryConfigurationError(Exception paramException, String paramString)
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
 * Qualified Name:     javax.xml.parsers.FactoryConfigurationError
 * JD-Core Version:    0.7.0.1
 */
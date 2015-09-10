package org.apache.xerces.xni;

public class XNIException
  extends RuntimeException
{
  static final long serialVersionUID = 9019819772686063775L;
  private Exception fException;
  
  public XNIException(String paramString)
  {
    super(paramString);
  }
  
  public XNIException(Exception paramException)
  {
    super(paramException.getMessage());
    this.fException = paramException;
  }
  
  public XNIException(String paramString, Exception paramException)
  {
    super(paramString);
    this.fException = paramException;
  }
  
  public Exception getException()
  {
    return this.fException;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.XNIException
 * JD-Core Version:    0.7.0.1
 */
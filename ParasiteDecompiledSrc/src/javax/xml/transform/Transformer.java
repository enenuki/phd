package javax.xml.transform;

import java.util.Properties;

public abstract class Transformer
{
  public void reset()
  {
    throw new UnsupportedOperationException("This Transformer, \"" + getClass().getName() + "\", does not support the reset functionality." + "  Specification \"" + getClass().getPackage().getSpecificationTitle() + "\"" + " version \"" + getClass().getPackage().getSpecificationVersion() + "\"");
  }
  
  public abstract void transform(Source paramSource, Result paramResult)
    throws TransformerException;
  
  public abstract void setParameter(String paramString, Object paramObject);
  
  public abstract Object getParameter(String paramString);
  
  public abstract void clearParameters();
  
  public abstract void setURIResolver(URIResolver paramURIResolver);
  
  public abstract URIResolver getURIResolver();
  
  public abstract void setOutputProperties(Properties paramProperties);
  
  public abstract Properties getOutputProperties();
  
  public abstract void setOutputProperty(String paramString1, String paramString2)
    throws IllegalArgumentException;
  
  public abstract String getOutputProperty(String paramString)
    throws IllegalArgumentException;
  
  public abstract void setErrorListener(ErrorListener paramErrorListener)
    throws IllegalArgumentException;
  
  public abstract ErrorListener getErrorListener();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.transform.Transformer
 * JD-Core Version:    0.7.0.1
 */
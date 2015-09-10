package org.apache.xerces.xs;

public abstract interface XSImplementation
{
  public abstract StringList getRecognizedVersions();
  
  public abstract XSLoader createXSLoader(StringList paramStringList)
    throws XSException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSImplementation
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.impl.xs;

public class XMLSchemaException
  extends Exception
{
  static final long serialVersionUID = -9096984648537046218L;
  String key;
  Object[] args;
  
  public XMLSchemaException(String paramString, Object[] paramArrayOfObject)
  {
    this.key = paramString;
    this.args = paramArrayOfObject;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public Object[] getArgs()
  {
    return this.args;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.XMLSchemaException
 * JD-Core Version:    0.7.0.1
 */
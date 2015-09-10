package org.apache.xerces.impl.dv;

import java.util.Hashtable;

public abstract class DTDDVFactory
{
  private static final String DEFAULT_FACTORY_CLASS = "org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl";
  
  public static final DTDDVFactory getInstance()
    throws DVFactoryException
  {
    return getInstance("org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl");
  }
  
  public static final DTDDVFactory getInstance(String paramString)
    throws DVFactoryException
  {
    try
    {
      return (DTDDVFactory)ObjectFactory.newInstance(paramString, ObjectFactory.findClassLoader(), true);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new DVFactoryException("DTD factory class " + paramString + " does not extend from DTDDVFactory.");
    }
  }
  
  public abstract DatatypeValidator getBuiltInDV(String paramString);
  
  public abstract Hashtable getBuiltInTypes();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.DTDDVFactory
 * JD-Core Version:    0.7.0.1
 */
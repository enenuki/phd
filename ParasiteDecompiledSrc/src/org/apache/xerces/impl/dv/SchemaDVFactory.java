package org.apache.xerces.impl.dv;

import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.xs.XSObjectList;

public abstract class SchemaDVFactory
{
  private static final String DEFAULT_FACTORY_CLASS = "org.apache.xerces.impl.dv.xs.SchemaDVFactoryImpl";
  
  public static final SchemaDVFactory getInstance()
    throws DVFactoryException
  {
    return getInstance("org.apache.xerces.impl.dv.xs.SchemaDVFactoryImpl");
  }
  
  public static final SchemaDVFactory getInstance(String paramString)
    throws DVFactoryException
  {
    try
    {
      return (SchemaDVFactory)ObjectFactory.newInstance(paramString, ObjectFactory.findClassLoader(), true);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new DVFactoryException("Schema factory class " + paramString + " does not extend from SchemaDVFactory.");
    }
  }
  
  public abstract XSSimpleType getBuiltInType(String paramString);
  
  public abstract SymbolHash getBuiltInTypes();
  
  public abstract XSSimpleType createTypeRestriction(String paramString1, String paramString2, short paramShort, XSSimpleType paramXSSimpleType, XSObjectList paramXSObjectList);
  
  public abstract XSSimpleType createTypeList(String paramString1, String paramString2, short paramShort, XSSimpleType paramXSSimpleType, XSObjectList paramXSObjectList);
  
  public abstract XSSimpleType createTypeUnion(String paramString1, String paramString2, short paramShort, XSSimpleType[] paramArrayOfXSSimpleType, XSObjectList paramXSObjectList);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.SchemaDVFactory
 * JD-Core Version:    0.7.0.1
 */
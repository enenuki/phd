package org.apache.xerces.impl.dv.dtd;

import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.xerces.impl.dv.DatatypeValidator;

public class XML11DTDDVFactoryImpl
  extends DTDDVFactoryImpl
{
  static final Hashtable fXML11BuiltInTypes = new Hashtable();
  
  public DatatypeValidator getBuiltInDV(String paramString)
  {
    if (fXML11BuiltInTypes.get(paramString) != null) {
      return (DatatypeValidator)fXML11BuiltInTypes.get(paramString);
    }
    return (DatatypeValidator)DTDDVFactoryImpl.fBuiltInTypes.get(paramString);
  }
  
  public Hashtable getBuiltInTypes()
  {
    Hashtable localHashtable = (Hashtable)DTDDVFactoryImpl.fBuiltInTypes.clone();
    Enumeration localEnumeration = fXML11BuiltInTypes.keys();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      localHashtable.put(localObject, fXML11BuiltInTypes.get(localObject));
    }
    return localHashtable;
  }
  
  static
  {
    fXML11BuiltInTypes.put("XML11ID", new XML11IDDatatypeValidator());
    Object localObject = new XML11IDREFDatatypeValidator();
    fXML11BuiltInTypes.put("XML11IDREF", localObject);
    fXML11BuiltInTypes.put("XML11IDREFS", new ListDatatypeValidator((DatatypeValidator)localObject));
    localObject = new XML11NMTOKENDatatypeValidator();
    fXML11BuiltInTypes.put("XML11NMTOKEN", localObject);
    fXML11BuiltInTypes.put("XML11NMTOKENS", new ListDatatypeValidator((DatatypeValidator)localObject));
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.dtd.XML11DTDDVFactoryImpl
 * JD-Core Version:    0.7.0.1
 */
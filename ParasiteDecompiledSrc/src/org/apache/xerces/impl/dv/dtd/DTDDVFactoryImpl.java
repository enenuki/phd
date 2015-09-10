package org.apache.xerces.impl.dv.dtd;

import java.util.Hashtable;
import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.impl.dv.DatatypeValidator;

public class DTDDVFactoryImpl
  extends DTDDVFactory
{
  static final Hashtable fBuiltInTypes = new Hashtable();
  
  public DatatypeValidator getBuiltInDV(String paramString)
  {
    return (DatatypeValidator)fBuiltInTypes.get(paramString);
  }
  
  public Hashtable getBuiltInTypes()
  {
    return (Hashtable)fBuiltInTypes.clone();
  }
  
  static void createBuiltInTypes()
  {
    fBuiltInTypes.put("string", new StringDatatypeValidator());
    fBuiltInTypes.put("ID", new IDDatatypeValidator());
    Object localObject = new IDREFDatatypeValidator();
    fBuiltInTypes.put("IDREF", localObject);
    fBuiltInTypes.put("IDREFS", new ListDatatypeValidator((DatatypeValidator)localObject));
    localObject = new ENTITYDatatypeValidator();
    fBuiltInTypes.put("ENTITY", new ENTITYDatatypeValidator());
    fBuiltInTypes.put("ENTITIES", new ListDatatypeValidator((DatatypeValidator)localObject));
    fBuiltInTypes.put("NOTATION", new NOTATIONDatatypeValidator());
    localObject = new NMTOKENDatatypeValidator();
    fBuiltInTypes.put("NMTOKEN", localObject);
    fBuiltInTypes.put("NMTOKENS", new ListDatatypeValidator((DatatypeValidator)localObject));
  }
  
  static
  {
    createBuiltInTypes();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.dtd.DTDDVFactoryImpl
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.impl.dtd;

import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.xni.parser.XMLComponentManager;

public class XML11DTDValidator
  extends XMLDTDValidator
{
  protected static final String DTD_VALIDATOR_PROPERTY = "http://apache.org/xml/properties/internal/validator/dtd";
  
  public void reset(XMLComponentManager paramXMLComponentManager)
  {
    XMLDTDValidator localXMLDTDValidator = null;
    if (((localXMLDTDValidator = (XMLDTDValidator)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/dtd")) != null) && (localXMLDTDValidator != this)) {
      this.fGrammarBucket = localXMLDTDValidator.getGrammarBucket();
    }
    super.reset(paramXMLComponentManager);
  }
  
  protected void init()
  {
    if ((this.fValidation) || (this.fDynamicValidation))
    {
      super.init();
      try
      {
        this.fValID = this.fDatatypeValidatorFactory.getBuiltInDV("XML11ID");
        this.fValIDRef = this.fDatatypeValidatorFactory.getBuiltInDV("XML11IDREF");
        this.fValIDRefs = this.fDatatypeValidatorFactory.getBuiltInDV("XML11IDREFS");
        this.fValNMTOKEN = this.fDatatypeValidatorFactory.getBuiltInDV("XML11NMTOKEN");
        this.fValNMTOKENS = this.fDatatypeValidatorFactory.getBuiltInDV("XML11NMTOKENS");
      }
      catch (Exception localException)
      {
        localException.printStackTrace(System.err);
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XML11DTDValidator
 * JD-Core Version:    0.7.0.1
 */
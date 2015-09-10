package org.apache.xerces.jaxp.validation;

import java.util.HashMap;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;
import org.apache.xerces.xni.grammars.XMLGrammarPool;

abstract class AbstractXMLSchema
  extends Schema
  implements XSGrammarPoolContainer
{
  private final HashMap fFeatures = new HashMap();
  
  public final Validator newValidator()
  {
    return new ValidatorImpl(this);
  }
  
  public final ValidatorHandler newValidatorHandler()
  {
    return new ValidatorHandlerImpl(this);
  }
  
  public final Boolean getFeature(String paramString)
  {
    return (Boolean)this.fFeatures.get(paramString);
  }
  
  final void setFeature(String paramString, boolean paramBoolean)
  {
    this.fFeatures.put(paramString, paramBoolean ? Boolean.TRUE : Boolean.FALSE);
  }
  
  public abstract boolean isFullyComposed();
  
  public abstract XMLGrammarPool getGrammarPool();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.AbstractXMLSchema
 * JD-Core Version:    0.7.0.1
 */
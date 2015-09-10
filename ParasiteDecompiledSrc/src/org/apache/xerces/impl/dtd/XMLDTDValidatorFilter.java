package org.apache.xerces.impl.dtd;

import org.apache.xerces.xni.parser.XMLDocumentFilter;

public abstract interface XMLDTDValidatorFilter
  extends XMLDocumentFilter
{
  public abstract boolean hasGrammar();
  
  public abstract boolean validate();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XMLDTDValidatorFilter
 * JD-Core Version:    0.7.0.1
 */
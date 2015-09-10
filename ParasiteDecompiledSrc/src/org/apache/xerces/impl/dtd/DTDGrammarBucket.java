package org.apache.xerces.impl.dtd;

import java.util.Hashtable;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;

public class DTDGrammarBucket
{
  protected final Hashtable fGrammars = new Hashtable();
  protected DTDGrammar fActiveGrammar;
  protected boolean fIsStandalone;
  
  public void putGrammar(DTDGrammar paramDTDGrammar)
  {
    XMLDTDDescription localXMLDTDDescription = (XMLDTDDescription)paramDTDGrammar.getGrammarDescription();
    this.fGrammars.put(localXMLDTDDescription, paramDTDGrammar);
  }
  
  public DTDGrammar getGrammar(XMLGrammarDescription paramXMLGrammarDescription)
  {
    return (DTDGrammar)this.fGrammars.get((XMLDTDDescription)paramXMLGrammarDescription);
  }
  
  public void clear()
  {
    this.fGrammars.clear();
    this.fActiveGrammar = null;
    this.fIsStandalone = false;
  }
  
  void setStandalone(boolean paramBoolean)
  {
    this.fIsStandalone = paramBoolean;
  }
  
  boolean getStandalone()
  {
    return this.fIsStandalone;
  }
  
  void setActiveGrammar(DTDGrammar paramDTDGrammar)
  {
    this.fActiveGrammar = paramDTDGrammar;
  }
  
  DTDGrammar getActiveGrammar()
  {
    return this.fActiveGrammar;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.DTDGrammarBucket
 * JD-Core Version:    0.7.0.1
 */
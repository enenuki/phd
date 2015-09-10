package org.apache.xerces.jaxp.validation;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import org.apache.xerces.xni.grammars.XMLGrammarPool;

final class WeakReferenceXMLSchema
  extends AbstractXMLSchema
{
  private WeakReference fGrammarPool = new WeakReference(null);
  
  public synchronized XMLGrammarPool getGrammarPool()
  {
    Object localObject = (XMLGrammarPool)this.fGrammarPool.get();
    if (localObject == null)
    {
      localObject = new SoftReferenceGrammarPool();
      this.fGrammarPool = new WeakReference(localObject);
    }
    return localObject;
  }
  
  public boolean isFullyComposed()
  {
    return false;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.WeakReferenceXMLSchema
 * JD-Core Version:    0.7.0.1
 */
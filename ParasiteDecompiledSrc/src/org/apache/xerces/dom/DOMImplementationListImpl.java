package org.apache.xerces.dom;

import java.util.Vector;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;

public class DOMImplementationListImpl
  implements DOMImplementationList
{
  private Vector fImplementations;
  
  public DOMImplementationListImpl()
  {
    this.fImplementations = new Vector();
  }
  
  public DOMImplementationListImpl(Vector paramVector)
  {
    this.fImplementations = paramVector;
  }
  
  public DOMImplementation item(int paramInt)
  {
    try
    {
      return (DOMImplementation)this.fImplementations.elementAt(paramInt);
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
    return null;
  }
  
  public int getLength()
  {
    return this.fImplementations.size();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DOMImplementationListImpl
 * JD-Core Version:    0.7.0.1
 */
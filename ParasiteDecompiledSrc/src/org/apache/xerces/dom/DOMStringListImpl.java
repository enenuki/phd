package org.apache.xerces.dom;

import java.util.Vector;
import org.w3c.dom.DOMStringList;

public class DOMStringListImpl
  implements DOMStringList
{
  private Vector fStrings;
  
  public DOMStringListImpl()
  {
    this.fStrings = new Vector();
  }
  
  public DOMStringListImpl(Vector paramVector)
  {
    this.fStrings = paramVector;
  }
  
  public String item(int paramInt)
  {
    try
    {
      return (String)this.fStrings.elementAt(paramInt);
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
    return null;
  }
  
  public int getLength()
  {
    return this.fStrings.size();
  }
  
  public boolean contains(String paramString)
  {
    return this.fStrings.contains(paramString);
  }
  
  public void add(String paramString)
  {
    this.fStrings.add(paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DOMStringListImpl
 * JD-Core Version:    0.7.0.1
 */
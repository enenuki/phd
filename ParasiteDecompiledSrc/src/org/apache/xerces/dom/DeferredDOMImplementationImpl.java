package org.apache.xerces.dom;

import org.w3c.dom.DOMImplementation;

public class DeferredDOMImplementationImpl
  extends DOMImplementationImpl
{
  static DeferredDOMImplementationImpl singleton = new DeferredDOMImplementationImpl();
  
  public static DOMImplementation getDOMImplementation()
  {
    return singleton;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredDOMImplementationImpl
 * JD-Core Version:    0.7.0.1
 */
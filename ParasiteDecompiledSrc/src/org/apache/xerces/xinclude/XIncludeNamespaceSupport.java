package org.apache.xerces.xinclude;

import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.xni.NamespaceContext;

public class XIncludeNamespaceSupport
  extends MultipleScopeNamespaceSupport
{
  private boolean[] fValidContext = new boolean[8];
  
  public XIncludeNamespaceSupport() {}
  
  public XIncludeNamespaceSupport(NamespaceContext paramNamespaceContext)
  {
    super(paramNamespaceContext);
  }
  
  public void pushContext()
  {
    super.pushContext();
    if (this.fCurrentContext + 1 == this.fValidContext.length)
    {
      boolean[] arrayOfBoolean = new boolean[this.fValidContext.length * 2];
      System.arraycopy(this.fValidContext, 0, arrayOfBoolean, 0, this.fValidContext.length);
      this.fValidContext = arrayOfBoolean;
    }
    this.fValidContext[this.fCurrentContext] = true;
  }
  
  public void setContextInvalid()
  {
    this.fValidContext[this.fCurrentContext] = false;
  }
  
  public String getURIFromIncludeParent(String paramString)
  {
    for (int i = this.fCurrentContext - 1; (i > 0) && (this.fValidContext[i] == 0); i--) {}
    return getURI(paramString, i);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xinclude.XIncludeNamespaceSupport
 * JD-Core Version:    0.7.0.1
 */
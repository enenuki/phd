package org.apache.xerces.xinclude;

import java.util.Enumeration;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.NamespaceSupport.Prefixes;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;

public class MultipleScopeNamespaceSupport
  extends NamespaceSupport
{
  protected int[] fScope = new int[8];
  protected int fCurrentScope = 0;
  
  public MultipleScopeNamespaceSupport()
  {
    this.fScope[0] = 0;
  }
  
  public MultipleScopeNamespaceSupport(NamespaceContext paramNamespaceContext)
  {
    super(paramNamespaceContext);
    this.fScope[0] = 0;
  }
  
  public Enumeration getAllPrefixes()
  {
    int i = 0;
    if (this.fPrefixes.length < this.fNamespace.length / 2)
    {
      localObject = new String[this.fNamespaceSize];
      this.fPrefixes = ((String[])localObject);
    }
    Object localObject = null;
    int j = 1;
    for (int k = this.fContext[this.fScope[this.fCurrentScope]]; k <= this.fNamespaceSize - 2; k += 2)
    {
      localObject = this.fNamespace[k];
      for (int m = 0; m < i; m++) {
        if (this.fPrefixes[m] == localObject)
        {
          j = 0;
          break;
        }
      }
      if (j != 0) {
        this.fPrefixes[(i++)] = localObject;
      }
      j = 1;
    }
    return new NamespaceSupport.Prefixes(this, this.fPrefixes, i);
  }
  
  public int getScopeForContext(int paramInt)
  {
    for (int i = this.fCurrentScope; paramInt < this.fScope[i]; i--) {}
    return i;
  }
  
  public String getPrefix(String paramString)
  {
    return getPrefix(paramString, this.fNamespaceSize, this.fContext[this.fScope[this.fCurrentScope]]);
  }
  
  public String getURI(String paramString)
  {
    return getURI(paramString, this.fNamespaceSize, this.fContext[this.fScope[this.fCurrentScope]]);
  }
  
  public String getPrefix(String paramString, int paramInt)
  {
    return getPrefix(paramString, this.fContext[(paramInt + 1)], this.fContext[this.fScope[getScopeForContext(paramInt)]]);
  }
  
  public String getURI(String paramString, int paramInt)
  {
    return getURI(paramString, this.fContext[(paramInt + 1)], this.fContext[this.fScope[getScopeForContext(paramInt)]]);
  }
  
  public String getPrefix(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString == NamespaceContext.XML_URI) {
      return XMLSymbols.PREFIX_XML;
    }
    if (paramString == NamespaceContext.XMLNS_URI) {
      return XMLSymbols.PREFIX_XMLNS;
    }
    for (int i = paramInt1; i > paramInt2; i -= 2) {
      if ((this.fNamespace[(i - 1)] == paramString) && (getURI(this.fNamespace[(i - 2)]) == paramString)) {
        return this.fNamespace[(i - 2)];
      }
    }
    return null;
  }
  
  public String getURI(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString == XMLSymbols.PREFIX_XML) {
      return NamespaceContext.XML_URI;
    }
    if (paramString == XMLSymbols.PREFIX_XMLNS) {
      return NamespaceContext.XMLNS_URI;
    }
    for (int i = paramInt1; i > paramInt2; i -= 2) {
      if (this.fNamespace[(i - 2)] == paramString) {
        return this.fNamespace[(i - 1)];
      }
    }
    return null;
  }
  
  public void reset()
  {
    this.fCurrentContext = this.fScope[this.fCurrentScope];
    this.fNamespaceSize = this.fContext[this.fCurrentContext];
  }
  
  public void pushScope()
  {
    if (this.fCurrentScope + 1 == this.fScope.length)
    {
      int[] arrayOfInt = new int[this.fScope.length * 2];
      System.arraycopy(this.fScope, 0, arrayOfInt, 0, this.fScope.length);
      this.fScope = arrayOfInt;
    }
    pushContext();
    this.fScope[(++this.fCurrentScope)] = this.fCurrentContext;
  }
  
  public void popScope()
  {
    this.fCurrentContext = this.fScope[(this.fCurrentScope--)];
    popContext();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xinclude.MultipleScopeNamespaceSupport
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.util;

import java.util.Enumeration;
import java.util.List;

public final class JAXPNamespaceContextWrapper
  implements org.apache.xerces.xni.NamespaceContext
{
  private javax.xml.namespace.NamespaceContext fNamespaceContext;
  private SymbolTable fSymbolTable;
  private List fPrefixes;
  
  public JAXPNamespaceContextWrapper(SymbolTable paramSymbolTable)
  {
    setSymbolTable(paramSymbolTable);
  }
  
  public void setNamespaceContext(javax.xml.namespace.NamespaceContext paramNamespaceContext)
  {
    this.fNamespaceContext = paramNamespaceContext;
  }
  
  public javax.xml.namespace.NamespaceContext getNamespaceContext()
  {
    return this.fNamespaceContext;
  }
  
  public void setSymbolTable(SymbolTable paramSymbolTable)
  {
    this.fSymbolTable = paramSymbolTable;
  }
  
  public SymbolTable getSymbolTable()
  {
    return this.fSymbolTable;
  }
  
  public void setDeclaredPrefixes(List paramList)
  {
    this.fPrefixes = paramList;
  }
  
  public List getDeclaredPrefixes()
  {
    return this.fPrefixes;
  }
  
  public String getURI(String paramString)
  {
    if (this.fNamespaceContext != null)
    {
      String str = this.fNamespaceContext.getNamespaceURI(paramString);
      if ((str != null) && (!"".equals(str))) {
        return this.fSymbolTable != null ? this.fSymbolTable.addSymbol(str) : str.intern();
      }
    }
    return null;
  }
  
  public String getPrefix(String paramString)
  {
    if (this.fNamespaceContext != null)
    {
      if (paramString == null) {
        paramString = "";
      }
      String str = this.fNamespaceContext.getPrefix(paramString);
      if (str == null) {
        str = "";
      }
      return this.fSymbolTable != null ? this.fSymbolTable.addSymbol(str) : str.intern();
    }
    return null;
  }
  
  public Enumeration getAllPrefixes()
  {
    new Enumeration()
    {
      public boolean hasMoreElements()
      {
        return false;
      }
      
      public Object nextElement()
      {
        return null;
      }
    };
  }
  
  public void pushContext() {}
  
  public void popContext() {}
  
  public boolean declarePrefix(String paramString1, String paramString2)
  {
    return true;
  }
  
  public int getDeclaredPrefixCount()
  {
    return this.fPrefixes != null ? this.fPrefixes.size() : 0;
  }
  
  public String getDeclaredPrefixAt(int paramInt)
  {
    return (String)this.fPrefixes.get(paramInt);
  }
  
  public void reset() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.JAXPNamespaceContextWrapper
 * JD-Core Version:    0.7.0.1
 */
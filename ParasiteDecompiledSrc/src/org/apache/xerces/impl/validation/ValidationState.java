package org.apache.xerces.impl.validation;

import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.NamespaceContext;

public class ValidationState
  implements ValidationContext
{
  private boolean fExtraChecking = true;
  private boolean fFacetChecking = true;
  private boolean fNormalize = true;
  private boolean fNamespaces = true;
  private EntityState fEntityState = null;
  private NamespaceContext fNamespaceContext = null;
  private SymbolTable fSymbolTable = null;
  private final Hashtable fIdTable = new Hashtable();
  private final Hashtable fIdRefTable = new Hashtable();
  private static final Object fNullValue = new Object();
  
  public void setExtraChecking(boolean paramBoolean)
  {
    this.fExtraChecking = paramBoolean;
  }
  
  public void setFacetChecking(boolean paramBoolean)
  {
    this.fFacetChecking = paramBoolean;
  }
  
  public void setNormalizationRequired(boolean paramBoolean)
  {
    this.fNormalize = paramBoolean;
  }
  
  public void setUsingNamespaces(boolean paramBoolean)
  {
    this.fNamespaces = paramBoolean;
  }
  
  public void setEntityState(EntityState paramEntityState)
  {
    this.fEntityState = paramEntityState;
  }
  
  public void setNamespaceSupport(NamespaceContext paramNamespaceContext)
  {
    this.fNamespaceContext = paramNamespaceContext;
  }
  
  public void setSymbolTable(SymbolTable paramSymbolTable)
  {
    this.fSymbolTable = paramSymbolTable;
  }
  
  public String checkIDRefID()
  {
    Enumeration localEnumeration = this.fIdRefTable.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (!this.fIdTable.containsKey(str)) {
        return str;
      }
    }
    return null;
  }
  
  public void reset()
  {
    this.fExtraChecking = true;
    this.fFacetChecking = true;
    this.fNamespaces = true;
    this.fIdTable.clear();
    this.fIdRefTable.clear();
    this.fEntityState = null;
    this.fNamespaceContext = null;
    this.fSymbolTable = null;
  }
  
  public void resetIDTables()
  {
    this.fIdTable.clear();
    this.fIdRefTable.clear();
  }
  
  public boolean needExtraChecking()
  {
    return this.fExtraChecking;
  }
  
  public boolean needFacetChecking()
  {
    return this.fFacetChecking;
  }
  
  public boolean needToNormalize()
  {
    return this.fNormalize;
  }
  
  public boolean useNamespaces()
  {
    return this.fNamespaces;
  }
  
  public boolean isEntityDeclared(String paramString)
  {
    if (this.fEntityState != null) {
      return this.fEntityState.isEntityDeclared(getSymbol(paramString));
    }
    return false;
  }
  
  public boolean isEntityUnparsed(String paramString)
  {
    if (this.fEntityState != null) {
      return this.fEntityState.isEntityUnparsed(getSymbol(paramString));
    }
    return false;
  }
  
  public boolean isIdDeclared(String paramString)
  {
    return this.fIdTable.containsKey(paramString);
  }
  
  public void addId(String paramString)
  {
    this.fIdTable.put(paramString, fNullValue);
  }
  
  public void addIdRef(String paramString)
  {
    this.fIdRefTable.put(paramString, fNullValue);
  }
  
  public String getSymbol(String paramString)
  {
    if (this.fSymbolTable != null) {
      return this.fSymbolTable.addSymbol(paramString);
    }
    return paramString.intern();
  }
  
  public String getURI(String paramString)
  {
    if (this.fNamespaceContext != null) {
      return this.fNamespaceContext.getURI(paramString);
    }
    return null;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.validation.ValidationState
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.impl.xs;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class XSGrammarBucket
{
  Hashtable fGrammarRegistry = new Hashtable();
  SchemaGrammar fNoNSGrammar = null;
  
  public SchemaGrammar getGrammar(String paramString)
  {
    if (paramString == null) {
      return this.fNoNSGrammar;
    }
    return (SchemaGrammar)this.fGrammarRegistry.get(paramString);
  }
  
  public void putGrammar(SchemaGrammar paramSchemaGrammar)
  {
    if (paramSchemaGrammar.getTargetNamespace() == null) {
      this.fNoNSGrammar = paramSchemaGrammar;
    } else {
      this.fGrammarRegistry.put(paramSchemaGrammar.getTargetNamespace(), paramSchemaGrammar);
    }
  }
  
  public boolean putGrammar(SchemaGrammar paramSchemaGrammar, boolean paramBoolean)
  {
    SchemaGrammar localSchemaGrammar1 = getGrammar(paramSchemaGrammar.fTargetNamespace);
    if (localSchemaGrammar1 != null) {
      return localSchemaGrammar1 == paramSchemaGrammar;
    }
    if (!paramBoolean)
    {
      putGrammar(paramSchemaGrammar);
      return true;
    }
    Vector localVector1 = paramSchemaGrammar.getImportedGrammars();
    if (localVector1 == null)
    {
      putGrammar(paramSchemaGrammar);
      return true;
    }
    Vector localVector2 = (Vector)localVector1.clone();
    for (int i = 0; i < localVector2.size(); i++)
    {
      SchemaGrammar localSchemaGrammar2 = (SchemaGrammar)localVector2.elementAt(i);
      SchemaGrammar localSchemaGrammar3 = getGrammar(localSchemaGrammar2.fTargetNamespace);
      if (localSchemaGrammar3 == null)
      {
        Vector localVector3 = localSchemaGrammar2.getImportedGrammars();
        if (localVector3 != null) {
          for (j = localVector3.size() - 1; j >= 0; j--)
          {
            localSchemaGrammar3 = (SchemaGrammar)localVector3.elementAt(j);
            if (!localVector2.contains(localSchemaGrammar3)) {
              localVector2.addElement(localSchemaGrammar3);
            }
          }
        }
      }
      else if (localSchemaGrammar3 != localSchemaGrammar2)
      {
        return false;
      }
    }
    putGrammar(paramSchemaGrammar);
    for (int j = localVector2.size() - 1; j >= 0; j--) {
      putGrammar((SchemaGrammar)localVector2.elementAt(j));
    }
    return true;
  }
  
  public SchemaGrammar[] getGrammars()
  {
    int i = this.fGrammarRegistry.size() + (this.fNoNSGrammar == null ? 0 : 1);
    SchemaGrammar[] arrayOfSchemaGrammar = new SchemaGrammar[i];
    Enumeration localEnumeration = this.fGrammarRegistry.elements();
    int j = 0;
    while (localEnumeration.hasMoreElements()) {
      arrayOfSchemaGrammar[(j++)] = ((SchemaGrammar)localEnumeration.nextElement());
    }
    if (this.fNoNSGrammar != null) {
      arrayOfSchemaGrammar[(i - 1)] = this.fNoNSGrammar;
    }
    return arrayOfSchemaGrammar;
  }
  
  public void reset()
  {
    this.fNoNSGrammar = null;
    this.fGrammarRegistry.clear();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.XSGrammarBucket
 * JD-Core Version:    0.7.0.1
 */
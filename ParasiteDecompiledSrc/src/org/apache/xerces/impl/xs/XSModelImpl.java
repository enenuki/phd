package org.apache.xerces.impl.xs;

import java.util.Vector;
import org.apache.xerces.impl.xs.util.NSItemListImpl;
import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.impl.xs.util.XSNamedMap4Types;
import org.apache.xerces.impl.xs.util.XSNamedMapImpl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSAttributeGroupDefinition;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroupDefinition;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSNamespaceItemList;
import org.apache.xerces.xs.XSNotationDeclaration;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;

public class XSModelImpl
  implements XSModel
{
  private static final short MAX_COMP_IDX = 16;
  private static final boolean[] GLOBAL_COMP = { false, true, true, true, false, true, true, false, false, false, false, true, false, false, false, true, true };
  private int fGrammarCount;
  private String[] fNamespaces;
  private SchemaGrammar[] fGrammarList;
  private SymbolHash fGrammarMap;
  private SymbolHash fSubGroupMap;
  private XSNamedMap[] fGlobalComponents;
  private XSNamedMap[][] fNSComponents;
  private XSObjectListImpl fAnnotations = null;
  private boolean fHasIDC = false;
  
  public XSModelImpl(SchemaGrammar[] paramArrayOfSchemaGrammar)
  {
    int i = paramArrayOfSchemaGrammar.length;
    this.fNamespaces = new String[Math.max(i + 1, 5)];
    this.fGrammarList = new SchemaGrammar[Math.max(i + 1, 5)];
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      this.fNamespaces[k] = paramArrayOfSchemaGrammar[k].getTargetNamespace();
      this.fGrammarList[k] = paramArrayOfSchemaGrammar[k];
      if (this.fNamespaces[k] == SchemaSymbols.URI_SCHEMAFORSCHEMA) {
        j = 1;
      }
    }
    if (j == 0)
    {
      this.fNamespaces[i] = SchemaSymbols.URI_SCHEMAFORSCHEMA;
      this.fGrammarList[(i++)] = SchemaGrammar.SG_SchemaNS;
    }
    for (int m = 0; m < i; m++)
    {
      SchemaGrammar localSchemaGrammar1 = this.fGrammarList[m];
      Vector localVector = localSchemaGrammar1.getImportedGrammars();
      for (int n = localVector == null ? -1 : localVector.size() - 1; n >= 0; n--)
      {
        SchemaGrammar localSchemaGrammar2 = (SchemaGrammar)localVector.elementAt(n);
        for (int i1 = 0; i1 < i; i1++) {
          if (localSchemaGrammar2 == this.fGrammarList[i1]) {
            break;
          }
        }
        if (i1 == i)
        {
          if (i == this.fGrammarList.length)
          {
            String[] arrayOfString = new String[i * 2];
            System.arraycopy(this.fNamespaces, 0, arrayOfString, 0, i);
            this.fNamespaces = arrayOfString;
            SchemaGrammar[] arrayOfSchemaGrammar = new SchemaGrammar[i * 2];
            System.arraycopy(this.fGrammarList, 0, arrayOfSchemaGrammar, 0, i);
            this.fGrammarList = arrayOfSchemaGrammar;
          }
          this.fNamespaces[i] = localSchemaGrammar2.getTargetNamespace();
          this.fGrammarList[i] = localSchemaGrammar2;
          i++;
        }
      }
    }
    this.fGrammarMap = new SymbolHash(i * 2);
    for (m = 0; m < i; m++)
    {
      this.fGrammarMap.put(null2EmptyString(this.fNamespaces[m]), this.fGrammarList[m]);
      if (this.fGrammarList[m].hasIDConstraints()) {
        this.fHasIDC = true;
      }
    }
    this.fGrammarCount = i;
    this.fGlobalComponents = new XSNamedMap[17];
    this.fNSComponents = new XSNamedMap[i][17];
    buildSubGroups();
  }
  
  private void buildSubGroups()
  {
    SubstitutionGroupHandler localSubstitutionGroupHandler = new SubstitutionGroupHandler(null);
    for (int i = 0; i < this.fGrammarCount; i++) {
      localSubstitutionGroupHandler.addSubstitutionGroup(this.fGrammarList[i].getSubstitutionGroups());
    }
    XSNamedMap localXSNamedMap = getComponents((short)2);
    int j = localXSNamedMap.getLength();
    this.fSubGroupMap = new SymbolHash(j * 2);
    for (int k = 0; k < j; k++)
    {
      XSElementDecl localXSElementDecl = (XSElementDecl)localXSNamedMap.item(k);
      XSElementDecl[] arrayOfXSElementDecl = localSubstitutionGroupHandler.getSubstitutionGroup(localXSElementDecl);
      this.fSubGroupMap.put(localXSElementDecl, arrayOfXSElementDecl.length > 0 ? new XSObjectListImpl(arrayOfXSElementDecl, arrayOfXSElementDecl.length) : XSObjectListImpl.EMPTY_LIST);
    }
  }
  
  public StringList getNamespaces()
  {
    return new StringListImpl(this.fNamespaces, this.fGrammarCount);
  }
  
  public XSNamespaceItemList getNamespaceItems()
  {
    return new NSItemListImpl(this.fGrammarList, this.fGrammarCount);
  }
  
  public synchronized XSNamedMap getComponents(short paramShort)
  {
    if ((paramShort <= 0) || (paramShort > 16) || (GLOBAL_COMP[paramShort] == 0)) {
      return XSNamedMapImpl.EMPTY_MAP;
    }
    SymbolHash[] arrayOfSymbolHash = new SymbolHash[this.fGrammarCount];
    if (this.fGlobalComponents[paramShort] == null)
    {
      for (int i = 0; i < this.fGrammarCount; i++) {
        switch (paramShort)
        {
        case 3: 
        case 15: 
        case 16: 
          arrayOfSymbolHash[i] = this.fGrammarList[i].fGlobalTypeDecls;
          break;
        case 1: 
          arrayOfSymbolHash[i] = this.fGrammarList[i].fGlobalAttrDecls;
          break;
        case 2: 
          arrayOfSymbolHash[i] = this.fGrammarList[i].fGlobalElemDecls;
          break;
        case 5: 
          arrayOfSymbolHash[i] = this.fGrammarList[i].fGlobalAttrGrpDecls;
          break;
        case 6: 
          arrayOfSymbolHash[i] = this.fGrammarList[i].fGlobalGroupDecls;
          break;
        case 11: 
          arrayOfSymbolHash[i] = this.fGrammarList[i].fGlobalNotationDecls;
        }
      }
      if ((paramShort == 15) || (paramShort == 16)) {
        this.fGlobalComponents[paramShort] = new XSNamedMap4Types(this.fNamespaces, arrayOfSymbolHash, this.fGrammarCount, paramShort);
      } else {
        this.fGlobalComponents[paramShort] = new XSNamedMapImpl(this.fNamespaces, arrayOfSymbolHash, this.fGrammarCount);
      }
    }
    return this.fGlobalComponents[paramShort];
  }
  
  public synchronized XSNamedMap getComponentsByNamespace(short paramShort, String paramString)
  {
    if ((paramShort <= 0) || (paramShort > 16) || (GLOBAL_COMP[paramShort] == 0)) {
      return XSNamedMapImpl.EMPTY_MAP;
    }
    int i = 0;
    if (paramString != null) {
      while (i < this.fGrammarCount)
      {
        if (paramString.equals(this.fNamespaces[i])) {
          break;
        }
        i++;
      }
    } else {
      while (i < this.fGrammarCount)
      {
        if (this.fNamespaces[i] == null) {
          break;
        }
        i++;
      }
    }
    if (i == this.fGrammarCount) {
      return XSNamedMapImpl.EMPTY_MAP;
    }
    if (this.fNSComponents[i][paramShort] == null)
    {
      SymbolHash localSymbolHash = null;
      switch (paramShort)
      {
      case 3: 
      case 15: 
      case 16: 
        localSymbolHash = this.fGrammarList[i].fGlobalTypeDecls;
        break;
      case 1: 
        localSymbolHash = this.fGrammarList[i].fGlobalAttrDecls;
        break;
      case 2: 
        localSymbolHash = this.fGrammarList[i].fGlobalElemDecls;
        break;
      case 5: 
        localSymbolHash = this.fGrammarList[i].fGlobalAttrGrpDecls;
        break;
      case 6: 
        localSymbolHash = this.fGrammarList[i].fGlobalGroupDecls;
        break;
      case 11: 
        localSymbolHash = this.fGrammarList[i].fGlobalNotationDecls;
      }
      if ((paramShort == 15) || (paramShort == 16)) {
        this.fNSComponents[i][paramShort] = new XSNamedMap4Types(paramString, localSymbolHash, paramShort);
      } else {
        this.fNSComponents[i][paramShort] = new XSNamedMapImpl(paramString, localSymbolHash);
      }
    }
    return this.fNSComponents[i][paramShort];
  }
  
  public XSTypeDefinition getTypeDefinition(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSTypeDefinition)localSchemaGrammar.fGlobalTypeDecls.get(paramString1);
  }
  
  public XSAttributeDeclaration getAttributeDeclaration(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSAttributeDeclaration)localSchemaGrammar.fGlobalAttrDecls.get(paramString1);
  }
  
  public XSElementDeclaration getElementDeclaration(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSElementDeclaration)localSchemaGrammar.fGlobalElemDecls.get(paramString1);
  }
  
  public XSAttributeGroupDefinition getAttributeGroup(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSAttributeGroupDefinition)localSchemaGrammar.fGlobalAttrGrpDecls.get(paramString1);
  }
  
  public XSModelGroupDefinition getModelGroupDefinition(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSModelGroupDefinition)localSchemaGrammar.fGlobalGroupDecls.get(paramString1);
  }
  
  public XSNotationDeclaration getNotationDeclaration(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSNotationDeclaration)localSchemaGrammar.fGlobalNotationDecls.get(paramString1);
  }
  
  public synchronized XSObjectList getAnnotations()
  {
    if (this.fAnnotations != null) {
      return this.fAnnotations;
    }
    int i = 0;
    for (int j = 0; j < this.fGrammarCount; j++) {
      i += this.fGrammarList[j].fNumAnnotations;
    }
    XSAnnotationImpl[] arrayOfXSAnnotationImpl = new XSAnnotationImpl[i];
    int k = 0;
    for (int m = 0; m < this.fGrammarCount; m++)
    {
      SchemaGrammar localSchemaGrammar = this.fGrammarList[m];
      if (localSchemaGrammar.fNumAnnotations > 0)
      {
        System.arraycopy(localSchemaGrammar.fAnnotations, 0, arrayOfXSAnnotationImpl, k, localSchemaGrammar.fNumAnnotations);
        k += localSchemaGrammar.fNumAnnotations;
      }
    }
    this.fAnnotations = new XSObjectListImpl(arrayOfXSAnnotationImpl, arrayOfXSAnnotationImpl.length);
    return this.fAnnotations;
  }
  
  private static final String null2EmptyString(String paramString)
  {
    return paramString == null ? XMLSymbols.EMPTY_STRING : paramString;
  }
  
  public boolean hasIDConstraints()
  {
    return this.fHasIDC;
  }
  
  public XSObjectList getSubstitutionGroup(XSElementDeclaration paramXSElementDeclaration)
  {
    return (XSObjectList)this.fSubGroupMap.get(paramXSElementDeclaration);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.XSModelImpl
 * JD-Core Version:    0.7.0.1
 */
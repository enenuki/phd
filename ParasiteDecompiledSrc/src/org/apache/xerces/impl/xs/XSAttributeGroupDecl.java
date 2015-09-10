package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSAttributeGroupDefinition;
import org.apache.xerces.xs.XSAttributeUse;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;
import org.apache.xerces.xs.XSWildcard;

public class XSAttributeGroupDecl
  implements XSAttributeGroupDefinition
{
  public String fName = null;
  public String fTargetNamespace = null;
  int fAttrUseNum = 0;
  private static final int INITIAL_SIZE = 5;
  XSAttributeUseImpl[] fAttributeUses = new XSAttributeUseImpl[5];
  public XSWildcardDecl fAttributeWC = null;
  public String fIDAttrName = null;
  public XSObjectList fAnnotations;
  protected XSObjectListImpl fAttrUses = null;
  
  public String addAttributeUse(XSAttributeUseImpl paramXSAttributeUseImpl)
  {
    if (this.fAttrUseNum == this.fAttributeUses.length) {
      this.fAttributeUses = resize(this.fAttributeUses, this.fAttrUseNum * 2);
    }
    this.fAttributeUses[(this.fAttrUseNum++)] = paramXSAttributeUseImpl;
    if (paramXSAttributeUseImpl.fUse == 2) {
      return null;
    }
    if (paramXSAttributeUseImpl.fAttrDecl.fType.isIDType()) {
      if (this.fIDAttrName == null) {
        this.fIDAttrName = paramXSAttributeUseImpl.fAttrDecl.fName;
      } else {
        return this.fIDAttrName;
      }
    }
    return null;
  }
  
  public XSAttributeUse getAttributeUse(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.fAttrUseNum; i++) {
      if ((this.fAttributeUses[i].fAttrDecl.fTargetNamespace == paramString1) && (this.fAttributeUses[i].fAttrDecl.fName == paramString2)) {
        return this.fAttributeUses[i];
      }
    }
    return null;
  }
  
  public void removeProhibitedAttrs()
  {
    if (this.fAttrUseNum == 0) {
      return;
    }
    int i = 0;
    XSAttributeUseImpl[] arrayOfXSAttributeUseImpl = new XSAttributeUseImpl[this.fAttrUseNum];
    for (int j = 0; j < this.fAttrUseNum; j++) {
      if (this.fAttributeUses[j].fUse == 2)
      {
        i++;
        arrayOfXSAttributeUseImpl[(this.fAttrUseNum - i)] = this.fAttributeUses[j];
      }
    }
    int k = 0;
    if (i > 0)
    {
      for (int m = 0; m < this.fAttrUseNum; m++) {
        if (this.fAttributeUses[m].fUse != 2)
        {
          int n = 1;
          while ((this.fAttributeUses[m].fAttrDecl.fName != arrayOfXSAttributeUseImpl[(this.fAttrUseNum - i)].fAttrDecl.fName) || (this.fAttributeUses[m].fAttrDecl.fTargetNamespace != arrayOfXSAttributeUseImpl[(this.fAttrUseNum - i)].fAttrDecl.fTargetNamespace))
          {
            n++;
            if (n > i) {
              arrayOfXSAttributeUseImpl[(k++)] = this.fAttributeUses[m];
            }
          }
        }
      }
      this.fAttributeUses = arrayOfXSAttributeUseImpl;
      this.fAttrUseNum = k;
    }
  }
  
  public Object[] validRestrictionOf(String paramString, XSAttributeGroupDecl paramXSAttributeGroupDecl)
  {
    Object[] arrayOfObject = null;
    XSAttributeUseImpl localXSAttributeUseImpl1 = null;
    XSAttributeDecl localXSAttributeDecl1 = null;
    XSAttributeUseImpl localXSAttributeUseImpl2 = null;
    XSAttributeDecl localXSAttributeDecl2 = null;
    for (int i = 0; i < this.fAttrUseNum; i++)
    {
      localXSAttributeUseImpl1 = this.fAttributeUses[i];
      localXSAttributeDecl1 = localXSAttributeUseImpl1.fAttrDecl;
      localXSAttributeUseImpl2 = (XSAttributeUseImpl)paramXSAttributeGroupDecl.getAttributeUse(localXSAttributeDecl1.fTargetNamespace, localXSAttributeDecl1.fName);
      if (localXSAttributeUseImpl2 != null)
      {
        if ((localXSAttributeUseImpl2.getRequired()) && (!localXSAttributeUseImpl1.getRequired()))
        {
          arrayOfObject = new Object[] { paramString, localXSAttributeDecl1.fName, localXSAttributeUseImpl1.fUse == 0 ? "optional" : "prohibited", "derivation-ok-restriction.2.1.1" };
          return arrayOfObject;
        }
        if (localXSAttributeUseImpl1.fUse != 2)
        {
          localXSAttributeDecl2 = localXSAttributeUseImpl2.fAttrDecl;
          if (!XSConstraints.checkSimpleDerivationOk(localXSAttributeDecl1.fType, localXSAttributeDecl2.fType, localXSAttributeDecl2.fType.getFinal()))
          {
            arrayOfObject = new Object[] { paramString, localXSAttributeDecl1.fName, localXSAttributeDecl1.fType.getName(), localXSAttributeDecl2.fType.getName(), "derivation-ok-restriction.2.1.2" };
            return arrayOfObject;
          }
          j = localXSAttributeUseImpl2.fConstraintType != 0 ? localXSAttributeUseImpl2.fConstraintType : localXSAttributeDecl2.getConstraintType();
          int k = localXSAttributeUseImpl1.fConstraintType != 0 ? localXSAttributeUseImpl1.fConstraintType : localXSAttributeDecl1.getConstraintType();
          if (j == 2)
          {
            if (k != 2)
            {
              arrayOfObject = new Object[] { paramString, localXSAttributeDecl1.fName, "derivation-ok-restriction.2.1.3.a" };
              return arrayOfObject;
            }
            ValidatedInfo localValidatedInfo1 = localXSAttributeUseImpl2.fDefault != null ? localXSAttributeUseImpl2.fDefault : localXSAttributeDecl2.fDefault;
            ValidatedInfo localValidatedInfo2 = localXSAttributeUseImpl1.fDefault != null ? localXSAttributeUseImpl1.fDefault : localXSAttributeDecl1.fDefault;
            if (!localValidatedInfo1.actualValue.equals(localValidatedInfo2.actualValue))
            {
              arrayOfObject = new Object[] { paramString, localXSAttributeDecl1.fName, localValidatedInfo2.stringValue(), localValidatedInfo1.stringValue(), "derivation-ok-restriction.2.1.3.b" };
              return arrayOfObject;
            }
          }
        }
      }
      else
      {
        if (paramXSAttributeGroupDecl.fAttributeWC == null)
        {
          arrayOfObject = new Object[] { paramString, localXSAttributeDecl1.fName, "derivation-ok-restriction.2.2.a" };
          return arrayOfObject;
        }
        if (!paramXSAttributeGroupDecl.fAttributeWC.allowNamespace(localXSAttributeDecl1.fTargetNamespace))
        {
          arrayOfObject = new Object[] { paramString, localXSAttributeDecl1.fName, localXSAttributeDecl1.fTargetNamespace == null ? "" : localXSAttributeDecl1.fTargetNamespace, "derivation-ok-restriction.2.2.b" };
          return arrayOfObject;
        }
      }
    }
    for (int j = 0; j < paramXSAttributeGroupDecl.fAttrUseNum; j++)
    {
      localXSAttributeUseImpl2 = paramXSAttributeGroupDecl.fAttributeUses[j];
      if (localXSAttributeUseImpl2.fUse == 1)
      {
        localXSAttributeDecl2 = localXSAttributeUseImpl2.fAttrDecl;
        if (getAttributeUse(localXSAttributeDecl2.fTargetNamespace, localXSAttributeDecl2.fName) == null)
        {
          arrayOfObject = new Object[] { paramString, localXSAttributeUseImpl2.fAttrDecl.fName, "derivation-ok-restriction.3" };
          return arrayOfObject;
        }
      }
    }
    if (this.fAttributeWC != null)
    {
      if (paramXSAttributeGroupDecl.fAttributeWC == null)
      {
        arrayOfObject = new Object[] { paramString, "derivation-ok-restriction.4.1" };
        return arrayOfObject;
      }
      if (!this.fAttributeWC.isSubsetOf(paramXSAttributeGroupDecl.fAttributeWC))
      {
        arrayOfObject = new Object[] { paramString, "derivation-ok-restriction.4.2" };
        return arrayOfObject;
      }
      if (this.fAttributeWC.weakerProcessContents(paramXSAttributeGroupDecl.fAttributeWC))
      {
        arrayOfObject = new Object[] { paramString, this.fAttributeWC.getProcessContentsAsString(), paramXSAttributeGroupDecl.fAttributeWC.getProcessContentsAsString(), "derivation-ok-restriction.4.3" };
        return arrayOfObject;
      }
    }
    return null;
  }
  
  static final XSAttributeUseImpl[] resize(XSAttributeUseImpl[] paramArrayOfXSAttributeUseImpl, int paramInt)
  {
    XSAttributeUseImpl[] arrayOfXSAttributeUseImpl = new XSAttributeUseImpl[paramInt];
    System.arraycopy(paramArrayOfXSAttributeUseImpl, 0, arrayOfXSAttributeUseImpl, 0, Math.min(paramArrayOfXSAttributeUseImpl.length, paramInt));
    return arrayOfXSAttributeUseImpl;
  }
  
  public void reset()
  {
    this.fName = null;
    this.fTargetNamespace = null;
    for (int i = 0; i < this.fAttrUseNum; i++) {
      this.fAttributeUses[i] = null;
    }
    this.fAttrUseNum = 0;
    this.fAttributeWC = null;
    this.fAnnotations = null;
    this.fIDAttrName = null;
  }
  
  public short getType()
  {
    return 5;
  }
  
  public String getName()
  {
    return this.fName;
  }
  
  public String getNamespace()
  {
    return this.fTargetNamespace;
  }
  
  public XSObjectList getAttributeUses()
  {
    if (this.fAttrUses == null) {
      this.fAttrUses = new XSObjectListImpl(this.fAttributeUses, this.fAttrUseNum);
    }
    return this.fAttrUses;
  }
  
  public XSWildcard getAttributeWildcard()
  {
    return this.fAttributeWC;
  }
  
  public XSAnnotation getAnnotation()
  {
    return this.fAnnotations != null ? (XSAnnotation)this.fAnnotations.item(0) : null;
  }
  
  public XSObjectList getAnnotations()
  {
    return this.fAnnotations != null ? this.fAnnotations : XSObjectListImpl.EMPTY_LIST;
  }
  
  public XSNamespaceItem getNamespaceItem()
  {
    return null;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.XSAttributeGroupDecl
 * JD-Core Version:    0.7.0.1
 */
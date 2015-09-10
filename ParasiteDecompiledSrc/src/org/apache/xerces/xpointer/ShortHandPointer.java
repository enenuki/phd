package org.apache.xerces.xpointer;

import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ItemPSVI;

class ShortHandPointer
  implements XPointerPart
{
  private String fShortHandPointer;
  private boolean fIsFragmentResolved = false;
  private SymbolTable fSymbolTable;
  int fMatchingChildCount = 0;
  
  public ShortHandPointer() {}
  
  public ShortHandPointer(SymbolTable paramSymbolTable)
  {
    this.fSymbolTable = paramSymbolTable;
  }
  
  public void parseXPointer(String paramString)
    throws XNIException
  {
    this.fShortHandPointer = paramString;
    this.fIsFragmentResolved = false;
  }
  
  public boolean resolveXPointer(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations, int paramInt)
    throws XNIException
  {
    if (this.fMatchingChildCount == 0) {
      this.fIsFragmentResolved = false;
    }
    if (paramInt == 0)
    {
      if (this.fMatchingChildCount == 0) {
        this.fIsFragmentResolved = hasMatchingIdentifier(paramQName, paramXMLAttributes, paramAugmentations, paramInt);
      }
      if (this.fIsFragmentResolved) {
        this.fMatchingChildCount += 1;
      }
    }
    else if (paramInt == 2)
    {
      if (this.fMatchingChildCount == 0) {
        this.fIsFragmentResolved = hasMatchingIdentifier(paramQName, paramXMLAttributes, paramAugmentations, paramInt);
      }
    }
    else if (this.fIsFragmentResolved)
    {
      this.fMatchingChildCount -= 1;
    }
    return this.fIsFragmentResolved;
  }
  
  private boolean hasMatchingIdentifier(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations, int paramInt)
    throws XNIException
  {
    String str = null;
    if (paramXMLAttributes != null) {
      for (int i = 0; i < paramXMLAttributes.getLength(); i++)
      {
        str = getSchemaDeterminedID(paramXMLAttributes, i);
        if (str != null) {
          break;
        }
        str = getChildrenSchemaDeterminedID(paramXMLAttributes, i);
        if (str != null) {
          break;
        }
        str = getDTDDeterminedID(paramXMLAttributes, i);
        if (str != null) {
          break;
        }
      }
    }
    return (str != null) && (str.equals(this.fShortHandPointer));
  }
  
  public String getDTDDeterminedID(XMLAttributes paramXMLAttributes, int paramInt)
    throws XNIException
  {
    if (paramXMLAttributes.getType(paramInt).equals("ID")) {
      return paramXMLAttributes.getValue(paramInt);
    }
    return null;
  }
  
  public String getSchemaDeterminedID(XMLAttributes paramXMLAttributes, int paramInt)
    throws XNIException
  {
    Augmentations localAugmentations = paramXMLAttributes.getAugmentations(paramInt);
    AttributePSVI localAttributePSVI = (AttributePSVI)localAugmentations.getItem("ATTRIBUTE_PSVI");
    if (localAttributePSVI != null)
    {
      Object localObject = localAttributePSVI.getMemberTypeDefinition();
      if (localObject != null) {
        localObject = localAttributePSVI.getTypeDefinition();
      }
      if ((localObject != null) && (((XSSimpleType)localObject).isIDType())) {
        return localAttributePSVI.getSchemaNormalizedValue();
      }
    }
    return null;
  }
  
  public String getChildrenSchemaDeterminedID(XMLAttributes paramXMLAttributes, int paramInt)
    throws XNIException
  {
    return null;
  }
  
  public boolean isFragmentResolved()
  {
    return this.fIsFragmentResolved;
  }
  
  public boolean isChildFragmentResolved()
  {
    return (this.fIsFragmentResolved) && (this.fMatchingChildCount > 0);
  }
  
  public String getSchemeName()
  {
    return this.fShortHandPointer;
  }
  
  public String getSchemeData()
  {
    return null;
  }
  
  public void setSchemeName(String paramString)
  {
    this.fShortHandPointer = paramString;
  }
  
  public void setSchemeData(String paramString) {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xpointer.ShortHandPointer
 * JD-Core Version:    0.7.0.1
 */
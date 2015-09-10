package org.apache.xerces.impl.xs.models;

import java.util.Vector;
import org.apache.xerces.impl.xs.SubstitutionGroupHandler;
import org.apache.xerces.impl.xs.XMLSchemaException;
import org.apache.xerces.impl.xs.XSConstraints;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.xni.QName;

public class XSAllCM
  implements XSCMValidator
{
  private static final short STATE_START = 0;
  private static final short STATE_VALID = 1;
  private static final short STATE_CHILD = 1;
  private final XSElementDecl[] fAllElements;
  private final boolean[] fIsOptionalElement;
  private final boolean fHasOptionalContent;
  private int fNumElements = 0;
  
  public XSAllCM(boolean paramBoolean, int paramInt)
  {
    this.fHasOptionalContent = paramBoolean;
    this.fAllElements = new XSElementDecl[paramInt];
    this.fIsOptionalElement = new boolean[paramInt];
  }
  
  public void addElement(XSElementDecl paramXSElementDecl, boolean paramBoolean)
  {
    this.fAllElements[this.fNumElements] = paramXSElementDecl;
    this.fIsOptionalElement[this.fNumElements] = paramBoolean;
    this.fNumElements += 1;
  }
  
  public int[] startContentModel()
  {
    int[] arrayOfInt = new int[this.fNumElements + 1];
    for (int i = 0; i <= this.fNumElements; i++) {
      arrayOfInt[i] = 0;
    }
    return arrayOfInt;
  }
  
  Object findMatchingDecl(QName paramQName, SubstitutionGroupHandler paramSubstitutionGroupHandler)
  {
    XSElementDecl localXSElementDecl = null;
    for (int i = 0; i < this.fNumElements; i++)
    {
      localXSElementDecl = paramSubstitutionGroupHandler.getMatchingElemDecl(paramQName, this.fAllElements[i]);
      if (localXSElementDecl != null) {
        break;
      }
    }
    return localXSElementDecl;
  }
  
  public Object oneTransition(QName paramQName, int[] paramArrayOfInt, SubstitutionGroupHandler paramSubstitutionGroupHandler)
  {
    if (paramArrayOfInt[0] < 0)
    {
      paramArrayOfInt[0] = -2;
      return findMatchingDecl(paramQName, paramSubstitutionGroupHandler);
    }
    paramArrayOfInt[0] = 1;
    XSElementDecl localXSElementDecl = null;
    for (int i = 0; i < this.fNumElements; i++) {
      if (paramArrayOfInt[(i + 1)] == 0)
      {
        localXSElementDecl = paramSubstitutionGroupHandler.getMatchingElemDecl(paramQName, this.fAllElements[i]);
        if (localXSElementDecl != null)
        {
          paramArrayOfInt[(i + 1)] = 1;
          return localXSElementDecl;
        }
      }
    }
    paramArrayOfInt[0] = -1;
    return findMatchingDecl(paramQName, paramSubstitutionGroupHandler);
  }
  
  public boolean endContentModel(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    if ((i == -1) || (i == -2)) {
      return false;
    }
    if ((this.fHasOptionalContent) && (i == 0)) {
      return true;
    }
    for (int j = 0; j < this.fNumElements; j++) {
      if ((this.fIsOptionalElement[j] == 0) && (paramArrayOfInt[(j + 1)] == 0)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean checkUniqueParticleAttribution(SubstitutionGroupHandler paramSubstitutionGroupHandler)
    throws XMLSchemaException
  {
    for (int i = 0; i < this.fNumElements; i++) {
      for (int j = i + 1; j < this.fNumElements; j++) {
        if (XSConstraints.overlapUPA(this.fAllElements[i], this.fAllElements[j], paramSubstitutionGroupHandler)) {
          throw new XMLSchemaException("cos-nonambig", new Object[] { this.fAllElements[i].toString(), this.fAllElements[j].toString() });
        }
      }
    }
    return false;
  }
  
  public Vector whatCanGoHere(int[] paramArrayOfInt)
  {
    Vector localVector = new Vector();
    for (int i = 0; i < this.fNumElements; i++) {
      if (paramArrayOfInt[(i + 1)] == 0) {
        localVector.addElement(this.fAllElements[i]);
      }
    }
    return localVector;
  }
  
  public boolean isCompactedForUPA()
  {
    return false;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.models.XSAllCM
 * JD-Core Version:    0.7.0.1
 */
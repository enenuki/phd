package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSTerm;

public class XSParticleDecl
  implements XSParticle
{
  public static final short PARTICLE_EMPTY = 0;
  public static final short PARTICLE_ELEMENT = 1;
  public static final short PARTICLE_WILDCARD = 2;
  public static final short PARTICLE_MODELGROUP = 3;
  public static final short PARTICLE_ZERO_OR_MORE = 4;
  public static final short PARTICLE_ZERO_OR_ONE = 5;
  public static final short PARTICLE_ONE_OR_MORE = 6;
  public short fType = 0;
  public XSTerm fValue = null;
  public int fMinOccurs = 1;
  public int fMaxOccurs = 1;
  public XSObjectList fAnnotations = null;
  private String fDescription = null;
  
  public XSParticleDecl makeClone()
  {
    XSParticleDecl localXSParticleDecl = new XSParticleDecl();
    localXSParticleDecl.fType = this.fType;
    localXSParticleDecl.fMinOccurs = this.fMinOccurs;
    localXSParticleDecl.fMaxOccurs = this.fMaxOccurs;
    localXSParticleDecl.fDescription = this.fDescription;
    localXSParticleDecl.fValue = this.fValue;
    localXSParticleDecl.fAnnotations = this.fAnnotations;
    return localXSParticleDecl;
  }
  
  public boolean emptiable()
  {
    return minEffectiveTotalRange() == 0;
  }
  
  public boolean isEmpty()
  {
    if (this.fType == 0) {
      return true;
    }
    if ((this.fType == 1) || (this.fType == 2)) {
      return false;
    }
    return ((XSModelGroupImpl)this.fValue).isEmpty();
  }
  
  public int minEffectiveTotalRange()
  {
    if (this.fType == 0) {
      return 0;
    }
    if (this.fType == 3) {
      return ((XSModelGroupImpl)this.fValue).minEffectiveTotalRange() * this.fMinOccurs;
    }
    return this.fMinOccurs;
  }
  
  public int maxEffectiveTotalRange()
  {
    if (this.fType == 0) {
      return 0;
    }
    if (this.fType == 3)
    {
      int i = ((XSModelGroupImpl)this.fValue).maxEffectiveTotalRange();
      if (i == -1) {
        return -1;
      }
      if ((i != 0) && (this.fMaxOccurs == -1)) {
        return -1;
      }
      return i * this.fMaxOccurs;
    }
    return this.fMaxOccurs;
  }
  
  public String toString()
  {
    if (this.fDescription == null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      appendParticle(localStringBuffer);
      if (((this.fMinOccurs != 0) || (this.fMaxOccurs != 0)) && ((this.fMinOccurs != 1) || (this.fMaxOccurs != 1)))
      {
        localStringBuffer.append("{").append(this.fMinOccurs);
        if (this.fMaxOccurs == -1) {
          localStringBuffer.append("-UNBOUNDED");
        } else if (this.fMinOccurs != this.fMaxOccurs) {
          localStringBuffer.append("-").append(this.fMaxOccurs);
        }
        localStringBuffer.append("}");
      }
      this.fDescription = localStringBuffer.toString();
    }
    return this.fDescription;
  }
  
  void appendParticle(StringBuffer paramStringBuffer)
  {
    switch (this.fType)
    {
    case 0: 
      paramStringBuffer.append("EMPTY");
      break;
    case 1: 
      paramStringBuffer.append(this.fValue.toString());
      break;
    case 2: 
      paramStringBuffer.append('(');
      paramStringBuffer.append(this.fValue.toString());
      paramStringBuffer.append(')');
      break;
    case 3: 
      paramStringBuffer.append(this.fValue.toString());
    }
  }
  
  public void reset()
  {
    this.fType = 0;
    this.fValue = null;
    this.fMinOccurs = 1;
    this.fMaxOccurs = 1;
    this.fDescription = null;
    this.fAnnotations = null;
  }
  
  public short getType()
  {
    return 8;
  }
  
  public String getName()
  {
    return null;
  }
  
  public String getNamespace()
  {
    return null;
  }
  
  public int getMinOccurs()
  {
    return this.fMinOccurs;
  }
  
  public boolean getMaxOccursUnbounded()
  {
    return this.fMaxOccurs == -1;
  }
  
  public int getMaxOccurs()
  {
    return this.fMaxOccurs;
  }
  
  public XSTerm getTerm()
  {
    return this.fValue;
  }
  
  public XSNamespaceItem getNamespaceItem()
  {
    return null;
  }
  
  public XSObjectList getAnnotations()
  {
    return this.fAnnotations != null ? this.fAnnotations : XSObjectListImpl.EMPTY_LIST;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.XSParticleDecl
 * JD-Core Version:    0.7.0.1
 */
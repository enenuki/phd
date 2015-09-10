package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;

public class XSModelGroupImpl
  implements XSModelGroup
{
  public static final short MODELGROUP_CHOICE = 101;
  public static final short MODELGROUP_SEQUENCE = 102;
  public static final short MODELGROUP_ALL = 103;
  public short fCompositor;
  public XSParticleDecl[] fParticles = null;
  public int fParticleCount = 0;
  public XSObjectList fAnnotations = null;
  private String fDescription = null;
  
  public boolean isEmpty()
  {
    for (int i = 0; i < this.fParticleCount; i++) {
      if (!this.fParticles[i].isEmpty()) {
        return false;
      }
    }
    return true;
  }
  
  public int minEffectiveTotalRange()
  {
    if (this.fCompositor == 101) {
      return minEffectiveTotalRangeChoice();
    }
    return minEffectiveTotalRangeAllSeq();
  }
  
  private int minEffectiveTotalRangeAllSeq()
  {
    int i = 0;
    for (int j = 0; j < this.fParticleCount; j++) {
      i += this.fParticles[j].minEffectiveTotalRange();
    }
    return i;
  }
  
  private int minEffectiveTotalRangeChoice()
  {
    int i = 0;
    if (this.fParticleCount > 0) {
      i = this.fParticles[0].minEffectiveTotalRange();
    }
    for (int k = 1; k < this.fParticleCount; k++)
    {
      int j = this.fParticles[k].minEffectiveTotalRange();
      if (j < i) {
        i = j;
      }
    }
    return i;
  }
  
  public int maxEffectiveTotalRange()
  {
    if (this.fCompositor == 101) {
      return maxEffectiveTotalRangeChoice();
    }
    return maxEffectiveTotalRangeAllSeq();
  }
  
  private int maxEffectiveTotalRangeAllSeq()
  {
    int i = 0;
    for (int k = 0; k < this.fParticleCount; k++)
    {
      int j = this.fParticles[k].maxEffectiveTotalRange();
      if (j == -1) {
        return -1;
      }
      i += j;
    }
    return i;
  }
  
  private int maxEffectiveTotalRangeChoice()
  {
    int i = 0;
    if (this.fParticleCount > 0)
    {
      i = this.fParticles[0].maxEffectiveTotalRange();
      if (i == -1) {
        return -1;
      }
    }
    for (int k = 1; k < this.fParticleCount; k++)
    {
      int j = this.fParticles[k].maxEffectiveTotalRange();
      if (j == -1) {
        return -1;
      }
      if (j > i) {
        i = j;
      }
    }
    return i;
  }
  
  public String toString()
  {
    if (this.fDescription == null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      if (this.fCompositor == 103) {
        localStringBuffer.append("all(");
      } else {
        localStringBuffer.append('(');
      }
      if (this.fParticleCount > 0) {
        localStringBuffer.append(this.fParticles[0].toString());
      }
      for (int i = 1; i < this.fParticleCount; i++)
      {
        if (this.fCompositor == 101) {
          localStringBuffer.append('|');
        } else {
          localStringBuffer.append(',');
        }
        localStringBuffer.append(this.fParticles[i].toString());
      }
      localStringBuffer.append(')');
      this.fDescription = localStringBuffer.toString();
    }
    return this.fDescription;
  }
  
  public void reset()
  {
    this.fCompositor = 102;
    this.fParticles = null;
    this.fParticleCount = 0;
    this.fDescription = null;
    this.fAnnotations = null;
  }
  
  public short getType()
  {
    return 7;
  }
  
  public String getName()
  {
    return null;
  }
  
  public String getNamespace()
  {
    return null;
  }
  
  public short getCompositor()
  {
    if (this.fCompositor == 101) {
      return 2;
    }
    if (this.fCompositor == 102) {
      return 1;
    }
    return 3;
  }
  
  public XSObjectList getParticles()
  {
    return new XSObjectListImpl(this.fParticles, this.fParticleCount);
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
 * Qualified Name:     org.apache.xerces.impl.xs.XSModelGroupImpl
 * JD-Core Version:    0.7.0.1
 */
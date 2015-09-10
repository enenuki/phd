package org.apache.xerces.impl.xs.models;

import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.dtd.models.CMStateSet;

public class XSCMLeaf
  extends CMNode
{
  private final Object fLeaf;
  private int fParticleId = -1;
  private int fPosition = -1;
  
  public XSCMLeaf(int paramInt1, Object paramObject, int paramInt2, int paramInt3)
  {
    super(paramInt1);
    this.fLeaf = paramObject;
    this.fParticleId = paramInt2;
    this.fPosition = paramInt3;
  }
  
  final Object getLeaf()
  {
    return this.fLeaf;
  }
  
  final int getParticleId()
  {
    return this.fParticleId;
  }
  
  final int getPosition()
  {
    return this.fPosition;
  }
  
  final void setPosition(int paramInt)
  {
    this.fPosition = paramInt;
  }
  
  public boolean isNullable()
  {
    return this.fPosition == -1;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.fLeaf.toString());
    if (this.fPosition >= 0) {
      localStringBuffer.append(" (Pos:").append(Integer.toString(this.fPosition)).append(')');
    }
    return localStringBuffer.toString();
  }
  
  protected void calcFirstPos(CMStateSet paramCMStateSet)
  {
    if (this.fPosition == -1) {
      paramCMStateSet.zeroBits();
    } else {
      paramCMStateSet.setBit(this.fPosition);
    }
  }
  
  protected void calcLastPos(CMStateSet paramCMStateSet)
  {
    if (this.fPosition == -1) {
      paramCMStateSet.zeroBits();
    } else {
      paramCMStateSet.setBit(this.fPosition);
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.models.XSCMLeaf
 * JD-Core Version:    0.7.0.1
 */
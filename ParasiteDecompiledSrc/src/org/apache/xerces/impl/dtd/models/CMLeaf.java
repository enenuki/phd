package org.apache.xerces.impl.dtd.models;

import org.apache.xerces.xni.QName;

public class CMLeaf
  extends CMNode
{
  private final QName fElement = new QName();
  private int fPosition = -1;
  
  public CMLeaf(QName paramQName, int paramInt)
  {
    super(0);
    this.fElement.setValues(paramQName);
    this.fPosition = paramInt;
  }
  
  public CMLeaf(QName paramQName)
  {
    super(0);
    this.fElement.setValues(paramQName);
  }
  
  final QName getElement()
  {
    return this.fElement;
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
    StringBuffer localStringBuffer = new StringBuffer(this.fElement.toString());
    localStringBuffer.append(" (");
    localStringBuffer.append(this.fElement.uri);
    localStringBuffer.append(',');
    localStringBuffer.append(this.fElement.localpart);
    localStringBuffer.append(')');
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
 * Qualified Name:     org.apache.xerces.impl.dtd.models.CMLeaf
 * JD-Core Version:    0.7.0.1
 */
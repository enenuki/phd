package org.apache.xerces.impl.xs.models;

import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.dtd.models.CMStateSet;

public class XSCMUniOp
  extends CMNode
{
  private CMNode fChild;
  
  public XSCMUniOp(int paramInt, CMNode paramCMNode)
  {
    super(paramInt);
    if ((type() != 5) && (type() != 4) && (type() != 6)) {
      throw new RuntimeException("ImplementationMessages.VAL_UST");
    }
    this.fChild = paramCMNode;
  }
  
  final CMNode getChild()
  {
    return this.fChild;
  }
  
  public boolean isNullable()
  {
    if (type() == 6) {
      return this.fChild.isNullable();
    }
    return true;
  }
  
  protected void calcFirstPos(CMStateSet paramCMStateSet)
  {
    paramCMStateSet.setTo(this.fChild.firstPos());
  }
  
  protected void calcLastPos(CMStateSet paramCMStateSet)
  {
    paramCMStateSet.setTo(this.fChild.lastPos());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.models.XSCMUniOp
 * JD-Core Version:    0.7.0.1
 */
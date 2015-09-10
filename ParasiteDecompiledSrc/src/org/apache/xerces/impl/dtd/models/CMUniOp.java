package org.apache.xerces.impl.dtd.models;

public class CMUniOp
  extends CMNode
{
  private final CMNode fChild;
  
  public CMUniOp(int paramInt, CMNode paramCMNode)
  {
    super(paramInt);
    if ((type() != 1) && (type() != 2) && (type() != 3)) {
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
    if (type() == 3) {
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
 * Qualified Name:     org.apache.xerces.impl.dtd.models.CMUniOp
 * JD-Core Version:    0.7.0.1
 */
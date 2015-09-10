package org.apache.xerces.impl.xs.models;

public final class XSCMRepeatingLeaf
  extends XSCMLeaf
{
  private final int fMinOccurs;
  private final int fMaxOccurs;
  
  public XSCMRepeatingLeaf(int paramInt1, Object paramObject, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    super(paramInt1, paramObject, paramInt4, paramInt5);
    this.fMinOccurs = paramInt2;
    this.fMaxOccurs = paramInt3;
  }
  
  final int getMinOccurs()
  {
    return this.fMinOccurs;
  }
  
  final int getMaxOccurs()
  {
    return this.fMaxOccurs;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.models.XSCMRepeatingLeaf
 * JD-Core Version:    0.7.0.1
 */
package org.w3c.dom.ranges;

public class RangeException
  extends RuntimeException
{
  public short code;
  public static final short BAD_BOUNDARYPOINTS_ERR = 1;
  public static final short INVALID_NODE_TYPE_ERR = 2;
  
  public RangeException(short paramShort, String paramString)
  {
    super(paramString);
    this.code = paramShort;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.ranges.RangeException
 * JD-Core Version:    0.7.0.1
 */
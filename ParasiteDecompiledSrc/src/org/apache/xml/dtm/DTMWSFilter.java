package org.apache.xml.dtm;

public abstract interface DTMWSFilter
{
  public static final short NOTSTRIP = 1;
  public static final short STRIP = 2;
  public static final short INHERIT = 3;
  
  public abstract short getShouldStripSpace(int paramInt, DTM paramDTM);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.DTMWSFilter
 * JD-Core Version:    0.7.0.1
 */
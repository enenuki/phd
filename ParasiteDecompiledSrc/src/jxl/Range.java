package jxl;

public abstract interface Range
{
  public abstract Cell getTopLeft();
  
  public abstract Cell getBottomRight();
  
  public abstract int getFirstSheetIndex();
  
  public abstract int getLastSheetIndex();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.Range
 * JD-Core Version:    0.7.0.1
 */
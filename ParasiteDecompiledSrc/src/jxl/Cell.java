package jxl;

import jxl.format.CellFormat;

public abstract interface Cell
{
  public abstract int getRow();
  
  public abstract int getColumn();
  
  public abstract CellType getType();
  
  public abstract boolean isHidden();
  
  public abstract String getContents();
  
  public abstract CellFormat getCellFormat();
  
  public abstract CellFeatures getCellFeatures();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.Cell
 * JD-Core Version:    0.7.0.1
 */
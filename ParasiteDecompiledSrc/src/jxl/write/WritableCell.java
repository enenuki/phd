package jxl.write;

import jxl.Cell;
import jxl.format.CellFormat;

public abstract interface WritableCell
  extends Cell
{
  public abstract void setCellFormat(CellFormat paramCellFormat);
  
  public abstract WritableCell copyTo(int paramInt1, int paramInt2);
  
  public abstract WritableCellFeatures getWritableCellFeatures();
  
  public abstract void setCellFeatures(WritableCellFeatures paramWritableCellFeatures);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.WritableCell
 * JD-Core Version:    0.7.0.1
 */
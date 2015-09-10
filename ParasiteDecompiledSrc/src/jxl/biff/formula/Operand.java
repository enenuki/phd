package jxl.biff.formula;

abstract class Operand
  extends ParseItem
{
  public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {}
  
  void columnInserted(int sheetIndex, int col, boolean currentSheet) {}
  
  void columnRemoved(int sheetIndex, int col, boolean currentSheet) {}
  
  void rowInserted(int sheetIndex, int row, boolean currentSheet) {}
  
  void rowRemoved(int sheetIndex, int row, boolean currentSheet) {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.Operand
 * JD-Core Version:    0.7.0.1
 */
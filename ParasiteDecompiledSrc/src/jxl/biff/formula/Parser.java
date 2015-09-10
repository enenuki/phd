package jxl.biff.formula;

abstract interface Parser
{
  public abstract void parse()
    throws FormulaException;
  
  public abstract String getFormula();
  
  public abstract byte[] getBytes();
  
  public abstract void adjustRelativeCellReferences(int paramInt1, int paramInt2);
  
  public abstract void columnInserted(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract void columnRemoved(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract void rowInserted(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract void rowRemoved(int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract boolean handleImportedCellReferences();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.Parser
 * JD-Core Version:    0.7.0.1
 */
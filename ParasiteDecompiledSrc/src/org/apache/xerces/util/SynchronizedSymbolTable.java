package org.apache.xerces.util;

public final class SynchronizedSymbolTable
  extends SymbolTable
{
  protected SymbolTable fSymbolTable;
  
  public SynchronizedSymbolTable(SymbolTable paramSymbolTable)
  {
    this.fSymbolTable = paramSymbolTable;
  }
  
  public SynchronizedSymbolTable()
  {
    this.fSymbolTable = new SymbolTable();
  }
  
  public SynchronizedSymbolTable(int paramInt)
  {
    this.fSymbolTable = new SymbolTable(paramInt);
  }
  
  public String addSymbol(String paramString)
  {
    synchronized (this.fSymbolTable)
    {
      String str = this.fSymbolTable.addSymbol(paramString);
      return str;
    }
  }
  
  public String addSymbol(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    synchronized (this.fSymbolTable)
    {
      String str = this.fSymbolTable.addSymbol(paramArrayOfChar, paramInt1, paramInt2);
      return str;
    }
  }
  
  public boolean containsSymbol(String paramString)
  {
    synchronized (this.fSymbolTable)
    {
      boolean bool = this.fSymbolTable.containsSymbol(paramString);
      return bool;
    }
  }
  
  public boolean containsSymbol(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    synchronized (this.fSymbolTable)
    {
      boolean bool = this.fSymbolTable.containsSymbol(paramArrayOfChar, paramInt1, paramInt2);
      return bool;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.SynchronizedSymbolTable
 * JD-Core Version:    0.7.0.1
 */
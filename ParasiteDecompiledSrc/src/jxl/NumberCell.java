package jxl;

import java.text.NumberFormat;

public abstract interface NumberCell
  extends Cell
{
  public abstract double getValue();
  
  public abstract NumberFormat getNumberFormat();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.NumberCell
 * JD-Core Version:    0.7.0.1
 */
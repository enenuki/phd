package hep.aida.ref;

import hep.aida.IHistogram;

abstract class Histogram
  implements IHistogram
{
  private String title;
  
  Histogram(String paramString)
  {
    this.title = paramString;
  }
  
  public String title()
  {
    return this.title;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.Histogram
 * JD-Core Version:    0.7.0.1
 */
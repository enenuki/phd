package cern.colt.matrix.impl;

import corejava.Format;

public class FormerFactory
{
  public Former create(String paramString)
  {
    new Former()
    {
      private Format f;
      private final String val$format;
      
      public String form(double paramAnonymousDouble)
      {
        if ((this.f == null) || (paramAnonymousDouble == (1.0D / 0.0D)) || (paramAnonymousDouble == (-1.0D / 0.0D)) || (paramAnonymousDouble != paramAnonymousDouble)) {
          return String.valueOf(paramAnonymousDouble);
        }
        return this.f.format(paramAnonymousDouble);
      }
    };
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.FormerFactory
 * JD-Core Version:    0.7.0.1
 */
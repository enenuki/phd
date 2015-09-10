package hep.aida.bin;

import cern.colt.matrix.impl.Former;
import cern.colt.matrix.impl.FormerFactory;

public class BinFunctions1D
{
  public static final BinFunctions1D functions = new BinFunctions1D();
  public static final BinFunction1D max = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.max();
    }
    
    public final String name()
    {
      return "Max";
    }
  };
  public static final BinFunction1D mean = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.mean();
    }
    
    public final String name()
    {
      return "Mean";
    }
  };
  public static final BinFunction1D median = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.median();
    }
    
    public final String name()
    {
      return "Median";
    }
  };
  public static final BinFunction1D min = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.min();
    }
    
    public final String name()
    {
      return "Min";
    }
  };
  public static final BinFunction1D rms = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.rms();
    }
    
    public final String name()
    {
      return "RMS";
    }
  };
  public static final BinFunction1D size = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.size();
    }
    
    public final String name()
    {
      return "Size";
    }
  };
  public static final BinFunction1D stdDev = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.standardDeviation();
    }
    
    public final String name()
    {
      return "StdDev";
    }
  };
  public static final BinFunction1D sum = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.sum();
    }
    
    public final String name()
    {
      return "Sum";
    }
  };
  public static final BinFunction1D sumLog = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.sumOfLogarithms();
    }
    
    public final String name()
    {
      return "SumLog";
    }
  };
  public static final BinFunction1D geometricMean = new BinFunction1D()
  {
    public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
    {
      return paramAnonymousDynamicBin1D.geometricMean();
    }
    
    public final String name()
    {
      return "GeomMean";
    }
  };
  
  public static BinFunction1D quantile(double paramDouble)
  {
    new BinFunction1D()
    {
      private final double val$percentage;
      
      public final double apply(DynamicBin1D paramAnonymousDynamicBin1D)
      {
        return paramAnonymousDynamicBin1D.quantile(this.val$percentage);
      }
      
      public final String name()
      {
        return new FormerFactory().create("%1.2G").form(this.val$percentage * 100.0D) + "% Q.";
      }
    };
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.bin.BinFunctions1D
 * JD-Core Version:    0.7.0.1
 */
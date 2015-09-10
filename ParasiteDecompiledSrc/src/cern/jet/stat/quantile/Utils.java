package cern.jet.stat.quantile;

class Utils
{
  protected Utils()
  {
    throw new RuntimeException("Non instantiable");
  }
  
  public static long epsilonCeiling(double paramDouble)
  {
    double d = 1.0E-007D;
    return Math.ceil(paramDouble - d);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.Utils
 * JD-Core Version:    0.7.0.1
 */
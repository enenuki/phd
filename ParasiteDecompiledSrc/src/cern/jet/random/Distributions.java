package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class Distributions
{
  protected Distributions()
  {
    throw new RuntimeException("Non instantiable");
  }
  
  public static double geometricPdf(int paramInt, double paramDouble)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException();
    }
    return paramDouble * Math.pow(1.0D - paramDouble, paramInt);
  }
  
  public static double nextBurr1(double paramDouble, int paramInt, RandomEngine paramRandomEngine)
  {
    double d = Math.exp(Math.log(paramRandomEngine.raw()) / paramDouble);
    switch (paramInt)
    {
    case 2: 
      return -Math.log(1.0D / d - 1.0D);
    case 7: 
      return Math.log(2.0D * d / (2.0D - 2.0D * d)) / 2.0D;
    case 8: 
      return Math.log(Math.tan(d * 3.141592653589793D / 2.0D));
    case 10: 
      return Math.sqrt(-Math.log(1.0D - d));
    }
    return 0.0D;
  }
  
  public static double nextBurr2(double paramDouble1, double paramDouble2, int paramInt, RandomEngine paramRandomEngine)
  {
    double d2 = paramRandomEngine.raw();
    double d1 = Math.exp(-Math.log(d2) / paramDouble1) - 1.0D;
    switch (paramInt)
    {
    case 3: 
      return Math.exp(-Math.log(d1) / paramDouble2);
    case 4: 
      d1 = Math.exp(paramDouble2 * Math.log(d1)) + 1.0D;
      d1 = paramDouble2 / d1;
      return d1;
    case 5: 
      d1 = Math.atan(-Math.log(d1 / paramDouble2));
      return d1;
    case 6: 
      d1 = -Math.log(d1 / paramDouble2) / paramDouble1;
      d1 = Math.log(d1 + Math.sqrt(d1 * d1 + 1.0D));
      return d1;
    case 9: 
      d1 = 1.0D + 2.0D * d2 / (paramDouble2 * (1.0D - d2));
      d1 = Math.exp(Math.log(d1) / paramDouble1) - 1.0D;
      return Math.log(d1);
    case 12: 
      return Math.exp(Math.log(d1) / paramDouble2);
    }
    return 0.0D;
  }
  
  public static double nextCauchy(RandomEngine paramRandomEngine)
  {
    return Math.tan(3.141592653589793D * paramRandomEngine.raw());
  }
  
  public static double nextErlang(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    int i = (int)(paramDouble2 * paramDouble2 / paramDouble1 + 0.5D);
    i = i > 0 ? i : 1;
    double d1 = i / paramDouble2;
    double d2 = 1.0D;
    for (int j = 0; j < i; j++) {
      d2 *= paramRandomEngine.raw();
    }
    return -Math.log(d2) / d1;
  }
  
  public static int nextGeometric(double paramDouble, RandomEngine paramRandomEngine)
  {
    double d = paramRandomEngine.raw();
    return (int)(Math.log(d) / Math.log(1.0D - paramDouble));
  }
  
  public static double nextLambda(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    double d1;
    if ((paramDouble1 < 0.0D) || (paramDouble2 < 0.0D)) {
      d1 = -1.0D;
    } else {
      d1 = 1.0D;
    }
    double d2 = paramRandomEngine.raw();
    double d3 = d1 * (Math.exp(Math.log(d2) * paramDouble1) - Math.exp(Math.log(1.0D - d2) * paramDouble2));
    return d3;
  }
  
  public static double nextLaplace(RandomEngine paramRandomEngine)
  {
    double d = paramRandomEngine.raw();
    d = d + d - 1.0D;
    if (d > 0.0D) {
      return -Math.log(1.0D - d);
    }
    return Math.log(1.0D + d);
  }
  
  public static double nextLogistic(RandomEngine paramRandomEngine)
  {
    double d = paramRandomEngine.raw();
    return -Math.log(1.0D / d - 1.0D);
  }
  
  public static double nextPowLaw(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    return paramDouble2 * Math.pow(paramRandomEngine.raw(), 1.0D / (paramDouble1 + 1.0D));
  }
  
  public static double nextTriangular(RandomEngine paramRandomEngine)
  {
    double d = paramRandomEngine.raw();
    if (d <= 0.5D) {
      return Math.sqrt(2.0D * d) - 1.0D;
    }
    return 1.0D - Math.sqrt(2.0D * (1.0D - d));
  }
  
  public static double nextWeibull(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    return Math.pow(paramDouble2 * -Math.log(1.0D - paramRandomEngine.raw()), 1.0D / paramDouble1);
  }
  
  public static int nextZipfInt(double paramDouble, RandomEngine paramRandomEngine)
  {
    double d1 = Math.pow(2.0D, paramDouble - 1.0D);
    double d2 = -1.0D / (paramDouble - 1.0D);
    int i = 0;
    for (;;)
    {
      double d3 = paramRandomEngine.raw();
      double d4 = paramRandomEngine.raw();
      i = (int)Math.floor(Math.pow(d3, d2));
      double d5 = Math.pow(1.0D + 1.0D / i, paramDouble - 1.0D);
      if (d4 * i * (d5 - 1.0D) / (d1 - 1.0D) <= d5 / d1) {
        break;
      }
    }
    return i;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.Distributions
 * JD-Core Version:    0.7.0.1
 */
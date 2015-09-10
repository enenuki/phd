package cern.jet.random;

import cern.jet.math.Arithmetic;

class Fun
{
  protected Fun()
  {
    throw new RuntimeException("Non instantiable");
  }
  
  private static double _fkt_value(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    double d = Math.cos(paramDouble2 * paramDouble4) / Math.pow(paramDouble4 * paramDouble4 + paramDouble3 * paramDouble3, paramDouble1 + 0.5D);
    return d;
  }
  
  public static double bessel2_fkt(double paramDouble1, double paramDouble2)
  {
    double d7 = 0.01D;
    double d19 = 0.0D;
    double[] arrayOfDouble1 = { -1.5787132D, -0.6130827D, 0.1735823D, 1.4793411D, 2.6667307D, 4.9086836D, 8.1355339D };
    double[] arrayOfDouble2 = { -1.9694802D, -0.7642538D, 0.0826017D, 1.4276355D, 2.6303682D, 4.8857787D, 8.120796800000001D };
    double[] arrayOfDouble3 = { -2.9807345D, -1.1969943D, -0.1843161D, 1.2739241D, 2.5218256D, 4.8172216D, 8.0765633D };
    double[] arrayOfDouble4 = { -5.9889676D, -2.7145389D, -1.1781269D, 0.6782201D, 2.0954009D, 4.5452152D, 7.9003173D };
    double[] arrayOfDouble5 = { -9.680344D, -4.8211925D, -2.6533185D, -0.2583337D, 1.4091915D, 4.0993448D, 7.608831D };
    double[] arrayOfDouble6 = { -18.156715200000001D, -10.0939408D, -6.5819139D, -2.9371545D, -0.6289005D, 2.7270412D, 6.6936799D };
    double[] arrayOfDouble7 = { -32.4910195D, -19.606594300000001D, -14.034729799999999D, -8.3839439D, -4.967973D, -0.3567823D, 4.5589697D };
    if (paramDouble1 == 0.0D)
    {
      if (paramDouble2 == 0.1D) {
        return arrayOfDouble1[0];
      }
      if (paramDouble2 == 0.5D) {
        return arrayOfDouble1[1];
      }
      if (paramDouble2 == 1.0D) {
        return arrayOfDouble1[2];
      }
      if (paramDouble2 == 2.0D) {
        return arrayOfDouble1[3];
      }
      if (paramDouble2 == 3.0D) {
        return arrayOfDouble1[4];
      }
      if (paramDouble2 == 5.0D) {
        return arrayOfDouble1[5];
      }
      if (paramDouble2 == 8.0D) {
        return arrayOfDouble1[6];
      }
    }
    if (paramDouble1 == 0.5D)
    {
      if (paramDouble2 == 0.1D) {
        return arrayOfDouble2[0];
      }
      if (paramDouble2 == 0.5D) {
        return arrayOfDouble2[1];
      }
      if (paramDouble2 == 1.0D) {
        return arrayOfDouble2[2];
      }
      if (paramDouble2 == 2.0D) {
        return arrayOfDouble2[3];
      }
      if (paramDouble2 == 3.0D) {
        return arrayOfDouble2[4];
      }
      if (paramDouble2 == 5.0D) {
        return arrayOfDouble2[5];
      }
      if (paramDouble2 == 8.0D) {
        return arrayOfDouble2[6];
      }
    }
    if (paramDouble1 == 1.0D)
    {
      if (paramDouble2 == 0.1D) {
        return arrayOfDouble3[0];
      }
      if (paramDouble2 == 0.5D) {
        return arrayOfDouble3[1];
      }
      if (paramDouble2 == 1.0D) {
        return arrayOfDouble3[2];
      }
      if (paramDouble2 == 2.0D) {
        return arrayOfDouble3[3];
      }
      if (paramDouble2 == 3.0D) {
        return arrayOfDouble3[4];
      }
      if (paramDouble2 == 5.0D) {
        return arrayOfDouble3[5];
      }
      if (paramDouble2 == 8.0D) {
        return arrayOfDouble3[6];
      }
    }
    if (paramDouble1 == 2.0D)
    {
      if (paramDouble2 == 0.1D) {
        return arrayOfDouble4[0];
      }
      if (paramDouble2 == 0.5D) {
        return arrayOfDouble4[1];
      }
      if (paramDouble2 == 1.0D) {
        return arrayOfDouble4[2];
      }
      if (paramDouble2 == 2.0D) {
        return arrayOfDouble4[3];
      }
      if (paramDouble2 == 3.0D) {
        return arrayOfDouble4[4];
      }
      if (paramDouble2 == 5.0D) {
        return arrayOfDouble4[5];
      }
      if (paramDouble2 == 8.0D) {
        return arrayOfDouble4[6];
      }
    }
    if (paramDouble1 == 3.0D)
    {
      if (paramDouble2 == 0.1D) {
        return arrayOfDouble5[0];
      }
      if (paramDouble2 == 0.5D) {
        return arrayOfDouble5[1];
      }
      if (paramDouble2 == 1.0D) {
        return arrayOfDouble5[2];
      }
      if (paramDouble2 == 2.0D) {
        return arrayOfDouble5[3];
      }
      if (paramDouble2 == 3.0D) {
        return arrayOfDouble5[4];
      }
      if (paramDouble2 == 5.0D) {
        return arrayOfDouble5[5];
      }
      if (paramDouble2 == 8.0D) {
        return arrayOfDouble5[6];
      }
    }
    if (paramDouble1 == 5.0D)
    {
      if (paramDouble2 == 0.1D) {
        return arrayOfDouble6[0];
      }
      if (paramDouble2 == 0.5D) {
        return arrayOfDouble6[1];
      }
      if (paramDouble2 == 1.0D) {
        return arrayOfDouble6[2];
      }
      if (paramDouble2 == 2.0D) {
        return arrayOfDouble6[3];
      }
      if (paramDouble2 == 3.0D) {
        return arrayOfDouble6[4];
      }
      if (paramDouble2 == 5.0D) {
        return arrayOfDouble6[5];
      }
      if (paramDouble2 == 8.0D) {
        return arrayOfDouble6[6];
      }
    }
    if (paramDouble1 == 8.0D)
    {
      if (paramDouble2 == 0.1D) {
        return arrayOfDouble7[0];
      }
      if (paramDouble2 == 0.5D) {
        return arrayOfDouble7[1];
      }
      if (paramDouble2 == 1.0D) {
        return arrayOfDouble7[2];
      }
      if (paramDouble2 == 2.0D) {
        return arrayOfDouble7[3];
      }
      if (paramDouble2 == 3.0D) {
        return arrayOfDouble7[4];
      }
      if (paramDouble2 == 5.0D) {
        return arrayOfDouble7[5];
      }
      if (paramDouble2 == 8.0D) {
        return arrayOfDouble7[6];
      }
    }
    if (paramDouble2 - 5.0D * paramDouble1 - 8.0D >= 0.0D)
    {
      double d17 = 4.0D * paramDouble1 * paramDouble1;
      double d18 = -0.9189385D + 0.5D * Math.log(paramDouble2) + paramDouble2;
      d1 = 1.0D;
      double d21 = 1.0D;
      double d20 = 8.0D;
      for (i = 1; (factorial(i) * Math.pow(8.0D * paramDouble2, i) <= 9.999999999999999E+249D) && (i <= 10); i++)
      {
        if (i == 1)
        {
          d19 = d17 - 1.0D;
        }
        else
        {
          d21 += d20;
          d19 *= (d17 - d21);
          d20 *= 2.0D;
        }
        d1 += d19 / (factorial(i) * Math.pow(8.0D * paramDouble2, i));
      }
      d12 = d18 - Math.log(d1);
      return d12;
    }
    if ((paramDouble1 > 0.0D) && (paramDouble2 - 0.04D * paramDouble1 <= 0.0D))
    {
      if (paramDouble1 < 11.5D)
      {
        d12 = -Math.log(gamma(paramDouble1)) - paramDouble1 * Math.log(2.0D) + paramDouble1 * Math.log(paramDouble2);
        return d12;
      }
      d12 = -(paramDouble1 + 1.0D) * Math.log(2.0D) - (paramDouble1 - 0.5D) * Math.log(paramDouble1) + paramDouble1 + paramDouble1 * Math.log(paramDouble2) - 0.5D * Math.log(1.570796326794897D);
      return d12;
    }
    double d2 = 0.0D;
    double d6;
    if (paramDouble2 < 1.57D)
    {
      double d9 = fkt2_value(paramDouble1, paramDouble2, d2) * 0.01D;
      double d8 = 0.0D;
      for (;;)
      {
        d8 += 0.1D;
        if (fkt2_value(paramDouble1, paramDouble2, d8) < d9) {
          break;
        }
      }
      d3 = d8 * 0.001D;
      d4 = d3;
      d1 = 0.5D * (10.0D * d3 + fkt2_value(paramDouble1, paramDouble2, d4)) * d3;
      double d5 = d1;
      for (;;)
      {
        d2 = d4;
        d4 += d3;
        d6 = 0.5D * (fkt2_value(paramDouble1, paramDouble2, d2) + fkt2_value(paramDouble1, paramDouble2, d4)) * d3;
        d1 += d6;
        if (d6 / d5 < d7) {
          break;
        }
      }
      d12 = -Math.log(2.0D * d1);
      return d12;
    }
    double d11 = 1.57D;
    double d10 = paramDouble2 / 1.57D;
    double d1 = 0.0D;
    double d13 = 3.141592653589793D / d10;
    double d3 = 0.1D * d13;
    double d14 = 100.0D / ((paramDouble1 + 0.1D) * (paramDouble1 + 0.1D));
    int k = (int)Math.ceil(d14 / d13) + 20;
    double d4 = d3;
    for (int i = 1; i <= k; i++) {
      for (j = 1; j <= 10; j++)
      {
        d6 = 0.5D * (_fkt_value(paramDouble1, d10, d11, d2) + _fkt_value(paramDouble1, d10, d11, d4)) * d3;
        d1 += d6;
        d2 = d4;
        d4 += d3;
      }
    }
    for (int j = 1; j <= 5; j++)
    {
      d6 = 0.5D * (_fkt_value(paramDouble1, d10, d11, d2) + _fkt_value(paramDouble1, d10, d11, d4)) * d3;
      d1 += d6;
      d2 = d4;
      d4 += d3;
    }
    double d15 = d1;
    for (j = 1; j <= 10; j++)
    {
      d6 = 0.5D * (_fkt_value(paramDouble1, d10, d11, d2) + _fkt_value(paramDouble1, d10, d11, d4)) * d3;
      d1 += d6;
      d2 = d4;
      d4 += d3;
    }
    double d16 = d1;
    d1 = 0.5D * (d15 + d16);
    double d12 = gamma(paramDouble1 + 0.5D) * Math.pow(2.0D * d11, paramDouble1) / (Math.sqrt(3.141592653589793D) * Math.pow(d10, paramDouble1)) * d1;
    d12 = -Math.log(2.0D * d12);
    return d12;
  }
  
  public static double bessi0(double paramDouble)
  {
    double d1;
    double d3;
    double d2;
    if ((d1 = Math.abs(paramDouble)) < 3.75D)
    {
      d3 = paramDouble / 3.75D;
      d3 *= d3;
      d2 = 1.0D + d3 * (3.5156229D + d3 * (3.0899424D + d3 * (1.2067492D + d3 * (0.2659732D + d3 * (0.0360768D + d3 * 0.0045813D)))));
    }
    else
    {
      d3 = 3.75D / d1;
      d2 = Math.exp(d1) / Math.sqrt(d1) * (0.39894228D + d3 * (0.01328592D + d3 * (0.00225319D + d3 * (-0.00157565D + d3 * (0.00916281D + d3 * (-0.02057706D + d3 * (0.02635537D + d3 * (-0.01647633D + d3 * 0.00392377D))))))));
    }
    return d2;
  }
  
  public static double bessi1(double paramDouble)
  {
    double d1;
    double d3;
    double d2;
    if ((d1 = Math.abs(paramDouble)) < 3.75D)
    {
      d3 = paramDouble / 3.75D;
      d3 *= d3;
      d2 = d1 * (0.5D + d3 * (0.87890594D + d3 * (0.51498869D + d3 * (0.15084934D + d3 * (0.02658733D + d3 * (0.00301532D + d3 * 0.00032411D))))));
    }
    else
    {
      d3 = 3.75D / d1;
      d2 = 0.02282967D + d3 * (-0.02895312D + d3 * (0.01787654D - d3 * 0.00420059D));
      d2 = 0.39894228D + d3 * (-0.03988024D + d3 * (-0.00362018D + d3 * (0.00163801D + d3 * (-0.01031555D + d3 * d2))));
      d2 *= Math.exp(d1) / Math.sqrt(d1);
    }
    return paramDouble < 0.0D ? -d2 : d2;
  }
  
  public static long factorial(int paramInt)
  {
    return Arithmetic.longFactorial(paramInt);
  }
  
  private static double fkt2_value(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d = cosh(paramDouble1 * paramDouble3) * Math.exp(-paramDouble2 * cosh(paramDouble3));
    return d;
  }
  
  private static double cosh(double paramDouble)
  {
    return (Math.exp(paramDouble) + Math.exp(-paramDouble)) / 2.0D;
  }
  
  public static double gamma(double paramDouble)
  {
    paramDouble = logGamma(paramDouble);
    return Math.exp(paramDouble);
  }
  
  public static double logGamma(double paramDouble)
  {
    if (paramDouble <= 0.0D) {
      return -999.0D;
    }
    double d3 = 1.0D;
    while (paramDouble < 11.0D)
    {
      d3 *= paramDouble;
      paramDouble += 1.0D;
    }
    double d2 = 1.0D / (paramDouble * paramDouble);
    double d1 = 0.08333333333333333D + d2 * (-0.002777777777777778D + d2 * (0.0007936507936507937D + d2 * (-0.0005952380952380953D + d2 * (0.0008417508417508417D + d2 + -0.001917526917526917D))));
    d1 = (paramDouble - 0.5D) * Math.log(paramDouble) - paramDouble + 0.9189385332046728D + d1 / paramDouble;
    if (d3 == 1.0D) {
      return d1;
    }
    return d1 - Math.log(d3);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.Fun
 * JD-Core Version:    0.7.0.1
 */
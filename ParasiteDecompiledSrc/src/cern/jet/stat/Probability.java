package cern.jet.stat;

import cern.jet.math.Constants;
import cern.jet.math.Polynomial;

public class Probability
  extends Constants
{
  protected static final double[] P0 = { -59.963350101410789D, 98.001075418599967D, -56.676285746907027D, 13.931260938727968D, -1.239165838673813D };
  protected static final double[] Q0 = { 1.954488583381418D, 4.676279128988815D, 86.360242139089053D, -225.46268785411937D, 200.26021238006066D, -82.037225616833339D, 15.90562251262117D, -1.1833162112133D };
  protected static final double[] P1 = { 4.055448923059625D, 31.525109459989388D, 57.162819224642128D, 44.080507389320083D, 14.684956192885803D, 2.186633068507903D, -0.1402560791713545D, -0.03504246268278482D, -0.0008574567851546855D };
  protected static final double[] Q1 = { 15.779988325646675D, 45.390763512887922D, 41.317203825467203D, 15.04253856929075D, 2.504649462083094D, -0.1421829228547878D, -0.03808064076915783D, -0.0009332594808954574D };
  protected static final double[] P2 = { 3.23774891776946D, 6.915228890689842D, 3.938810252924744D, 1.333034608158076D, 0.2014853895491791D, 0.012371663481782D, 0.0003015815535082354D, 2.658069746867376E-006D, 6.239745391849833E-009D };
  protected static final double[] Q2 = { 6.02427039364742D, 3.679835638561609D, 1.377020994890813D, 0.2162369935944966D, 0.01342040060885432D, 0.0003280144646821277D, 2.892478647453807E-006D, 6.790194080099813E-009D };
  
  public static double beta(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return Gamma.incompleteBeta(paramDouble1, paramDouble2, paramDouble3);
  }
  
  public static double betaComplemented(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return Gamma.incompleteBeta(paramDouble2, paramDouble1, paramDouble3);
  }
  
  public static double binomial(int paramInt1, int paramInt2, double paramDouble)
  {
    if ((paramDouble < 0.0D) || (paramDouble > 1.0D)) {
      throw new IllegalArgumentException();
    }
    if ((paramInt1 < 0) || (paramInt2 < paramInt1)) {
      throw new IllegalArgumentException();
    }
    if (paramInt1 == paramInt2) {
      return 1.0D;
    }
    if (paramInt1 == 0) {
      return Math.pow(1.0D - paramDouble, paramInt2 - paramInt1);
    }
    return Gamma.incompleteBeta(paramInt2 - paramInt1, paramInt1 + 1, 1.0D - paramDouble);
  }
  
  public static double binomialComplemented(int paramInt1, int paramInt2, double paramDouble)
  {
    if ((paramDouble < 0.0D) || (paramDouble > 1.0D)) {
      throw new IllegalArgumentException();
    }
    if ((paramInt1 < 0) || (paramInt2 < paramInt1)) {
      throw new IllegalArgumentException();
    }
    if (paramInt1 == paramInt2) {
      return 0.0D;
    }
    if (paramInt1 == 0) {
      return 1.0D - Math.pow(1.0D - paramDouble, paramInt2 - paramInt1);
    }
    return Gamma.incompleteBeta(paramInt1 + 1, paramInt2 - paramInt1, paramDouble);
  }
  
  public static double chiSquare(double paramDouble1, double paramDouble2)
    throws ArithmeticException
  {
    if ((paramDouble2 < 0.0D) || (paramDouble1 < 1.0D)) {
      return 0.0D;
    }
    return Gamma.incompleteGamma(paramDouble1 / 2.0D, paramDouble2 / 2.0D);
  }
  
  public static double chiSquareComplemented(double paramDouble1, double paramDouble2)
    throws ArithmeticException
  {
    if ((paramDouble2 < 0.0D) || (paramDouble1 < 1.0D)) {
      return 0.0D;
    }
    return Gamma.incompleteGammaComplement(paramDouble1 / 2.0D, paramDouble2 / 2.0D);
  }
  
  public static double errorFunction(double paramDouble)
    throws ArithmeticException
  {
    double[] arrayOfDouble1 = { 9.604973739870516D, 90.026019720384269D, 2232.0053459468431D, 7003.3251411280507D, 55592.301301039493D };
    double[] arrayOfDouble2 = { 33.561714164750313D, 521.35794978015269D, 4594.3238297098014D, 22629.000061389095D, 49267.394260863592D };
    if (Math.abs(paramDouble) > 1.0D) {
      return 1.0D - errorFunctionComplemented(paramDouble);
    }
    double d2 = paramDouble * paramDouble;
    double d1 = paramDouble * Polynomial.polevl(d2, arrayOfDouble1, 4) / Polynomial.p1evl(d2, arrayOfDouble2, 5);
    return d1;
  }
  
  public static double errorFunctionComplemented(double paramDouble)
    throws ArithmeticException
  {
    double[] arrayOfDouble1 = { 2.461969814735305E-010D, 0.5641895648310689D, 7.463210564422699D, 48.637197098568137D, 196.5208329560771D, 526.44519499547732D, 934.52852717195765D, 1027.5518868951572D, 557.53533536939938D };
    double[] arrayOfDouble2 = { 13.228195115474499D, 86.707214088598974D, 354.93777888781989D, 975.70850174320549D, 1823.9091668790973D, 2246.3376081871097D, 1656.6630919416134D, 557.53534081772773D };
    double[] arrayOfDouble3 = { 0.5641895835477551D, 1.275366707599781D, 5.019050422511805D, 6.160210979930536D, 7.40974269950449D, 2.978866653721002D };
    double[] arrayOfDouble4 = { 2.260528632201173D, 9.396035249380015D, 12.048953980809666D, 17.081445074756591D, 9.608968090632859D, 3.369076451000815D };
    double d1;
    if (paramDouble < 0.0D) {
      d1 = -paramDouble;
    } else {
      d1 = paramDouble;
    }
    if (d1 < 1.0D) {
      return 1.0D - errorFunction(paramDouble);
    }
    double d3 = -paramDouble * paramDouble;
    if (d3 < -709.78271289338397D)
    {
      if (paramDouble < 0.0D) {
        return 2.0D;
      }
      return 0.0D;
    }
    d3 = Math.exp(d3);
    double d4;
    double d5;
    if (d1 < 8.0D)
    {
      d4 = Polynomial.polevl(d1, arrayOfDouble1, 8);
      d5 = Polynomial.p1evl(d1, arrayOfDouble2, 8);
    }
    else
    {
      d4 = Polynomial.polevl(d1, arrayOfDouble3, 5);
      d5 = Polynomial.p1evl(d1, arrayOfDouble4, 6);
    }
    double d2 = d3 * d4 / d5;
    if (paramDouble < 0.0D) {
      d2 = 2.0D - d2;
    }
    if (d2 == 0.0D)
    {
      if (paramDouble < 0.0D) {
        return 2.0D;
      }
      return 0.0D;
    }
    return d2;
  }
  
  public static double gamma(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramDouble3 < 0.0D) {
      return 0.0D;
    }
    return Gamma.incompleteGamma(paramDouble2, paramDouble1 * paramDouble3);
  }
  
  public static double gammaComplemented(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramDouble3 < 0.0D) {
      return 0.0D;
    }
    return Gamma.incompleteGammaComplement(paramDouble2, paramDouble1 * paramDouble3);
  }
  
  public static double negativeBinomial(int paramInt1, int paramInt2, double paramDouble)
  {
    if ((paramDouble < 0.0D) || (paramDouble > 1.0D)) {
      throw new IllegalArgumentException();
    }
    if (paramInt1 < 0) {
      return 0.0D;
    }
    return Gamma.incompleteBeta(paramInt2, paramInt1 + 1, paramDouble);
  }
  
  public static double negativeBinomialComplemented(int paramInt1, int paramInt2, double paramDouble)
  {
    if ((paramDouble < 0.0D) || (paramDouble > 1.0D)) {
      throw new IllegalArgumentException();
    }
    if (paramInt1 < 0) {
      return 0.0D;
    }
    return Gamma.incompleteBeta(paramInt1 + 1, paramInt2, 1.0D - paramDouble);
  }
  
  public static double normal(double paramDouble)
    throws ArithmeticException
  {
    double d1 = paramDouble * 0.7071067811865476D;
    double d3 = Math.abs(d1);
    double d2;
    if (d3 < 0.7071067811865476D)
    {
      d2 = 0.5D + 0.5D * errorFunction(d1);
    }
    else
    {
      d2 = 0.5D * errorFunctionComplemented(d3);
      if (d1 > 0.0D) {
        d2 = 1.0D - d2;
      }
    }
    return d2;
  }
  
  public static double normal(double paramDouble1, double paramDouble2, double paramDouble3)
    throws ArithmeticException
  {
    if (paramDouble3 > 0.0D) {
      return 0.5D + 0.5D * errorFunction((paramDouble3 - paramDouble1) / Math.sqrt(2.0D * paramDouble2));
    }
    return 0.5D - 0.5D * errorFunction(-(paramDouble3 - paramDouble1) / Math.sqrt(2.0D * paramDouble2));
  }
  
  public static double normalInverse(double paramDouble)
    throws ArithmeticException
  {
    double d7 = Math.sqrt(6.283185307179586D);
    if (paramDouble <= 0.0D) {
      throw new IllegalArgumentException();
    }
    if (paramDouble >= 1.0D) {
      throw new IllegalArgumentException();
    }
    int i = 1;
    double d2 = paramDouble;
    if (d2 > 0.864664716763387D)
    {
      d2 = 1.0D - d2;
      i = 0;
    }
    if (d2 > 0.135335283236613D)
    {
      d2 -= 0.5D;
      double d4 = d2 * d2;
      d1 = d2 + d2 * (d4 * Polynomial.polevl(d4, P0, 4) / Polynomial.p1evl(d4, Q0, 8));
      d1 *= d7;
      return d1;
    }
    double d1 = Math.sqrt(-2.0D * Math.log(d2));
    double d5 = d1 - Math.log(d1) / d1;
    double d3 = 1.0D / d1;
    double d6;
    if (d1 < 8.0D) {
      d6 = d3 * Polynomial.polevl(d3, P1, 8) / Polynomial.p1evl(d3, Q1, 8);
    } else {
      d6 = d3 * Polynomial.polevl(d3, P2, 8) / Polynomial.p1evl(d3, Q2, 8);
    }
    d1 = d5 - d6;
    if (i != 0) {
      d1 = -d1;
    }
    return d1;
  }
  
  public static double poisson(int paramInt, double paramDouble)
    throws ArithmeticException
  {
    if (paramDouble < 0.0D) {
      throw new IllegalArgumentException();
    }
    if (paramInt < 0) {
      return 0.0D;
    }
    return Gamma.incompleteGammaComplement(paramInt + 1, paramDouble);
  }
  
  public static double poissonComplemented(int paramInt, double paramDouble)
    throws ArithmeticException
  {
    if (paramDouble < 0.0D) {
      throw new IllegalArgumentException();
    }
    if (paramInt < -1) {
      return 0.0D;
    }
    return Gamma.incompleteGamma(paramInt + 1, paramDouble);
  }
  
  public static double studentT(double paramDouble1, double paramDouble2)
    throws ArithmeticException
  {
    if (paramDouble1 <= 0.0D) {
      throw new IllegalArgumentException();
    }
    if (paramDouble2 == 0.0D) {
      return 0.5D;
    }
    double d = 0.5D * Gamma.incompleteBeta(0.5D * paramDouble1, 0.5D, paramDouble1 / (paramDouble1 + paramDouble2 * paramDouble2));
    if (paramDouble2 >= 0.0D) {
      d = 1.0D - d;
    }
    return d;
  }
  
  public static double studentTInverse(double paramDouble, int paramInt)
  {
    double d1 = 1.0D - paramDouble / 2.0D;
    d1 = 1.0D - paramDouble / 2.0D;
    double d5 = normalInverse(d1);
    if (paramInt > 200) {
      return d5;
    }
    double d2 = studentT(paramInt, d5) - d1;
    double d6 = d5;
    double d3 = d2;
    do
    {
      if (d2 > 0.0D) {
        d6 /= 2.0D;
      } else {
        d6 += d5;
      }
      d3 = studentT(paramInt, d6) - d1;
    } while (d2 * d3 > 0.0D);
    do
    {
      double d9 = (d3 - d2) / (d6 - d5);
      double d7 = d6 - d3 / d9;
      double d4 = studentT(paramInt, d7) - d1;
      if (Math.abs(d4) < 1.0E-008D) {
        return d7;
      }
      if (d4 * d3 < 0.0D)
      {
        d5 = d6;
        d2 = d3;
        d6 = d7;
        d3 = d4;
      }
      else
      {
        double d8 = d3 / (d3 + d4);
        d2 = d8 * d2;
        d6 = d7;
        d3 = d4;
      }
    } while (Math.abs(d6 - d5) > 0.001D);
    if (Math.abs(d3) <= Math.abs(d2)) {
      return d6;
    }
    return d5;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.Probability
 * JD-Core Version:    0.7.0.1
 */
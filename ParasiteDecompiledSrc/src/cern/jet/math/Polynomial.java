package cern.jet.math;

public class Polynomial
  extends Constants
{
  public static double p1evl(double paramDouble, double[] paramArrayOfDouble, int paramInt)
    throws ArithmeticException
  {
    double d = paramDouble + paramArrayOfDouble[0];
    for (int i = 1; i < paramInt; i++) {
      d = d * paramDouble + paramArrayOfDouble[i];
    }
    return d;
  }
  
  public static double polevl(double paramDouble, double[] paramArrayOfDouble, int paramInt)
    throws ArithmeticException
  {
    double d = paramArrayOfDouble[0];
    for (int i = 1; i <= paramInt; i++) {
      d = d * paramDouble + paramArrayOfDouble[i];
    }
    return d;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.math.Polynomial
 * JD-Core Version:    0.7.0.1
 */
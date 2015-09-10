package cern.jet.stat.quantile;

import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import hep.aida.bin.DynamicBin1D;
import hep.aida.bin.QuantileBin1D;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Date;

public class Quantile1Test
{
  public static void main(String[] paramArrayOfString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramArrayOfString[0]);
    }
    catch (Exception localException1)
    {
      System.err.println("Unable to parse input line count argument");
      System.err.println(localException1.getMessage());
      System.exit(1);
    }
    System.out.println("Got numExamples=" + i);
    long l = 0L;
    try
    {
      if (paramArrayOfString[1].equals("L")) {
        l = 9223372036854775807L;
      } else if (paramArrayOfString[1].equals("I")) {
        l = 2147483647L;
      } else {
        l = Long.parseLong(paramArrayOfString[1]);
      }
    }
    catch (Exception localException2)
    {
      System.err.println("Error parsing flag for N");
      System.err.println(localException2.getMessage());
      System.exit(1);
    }
    System.out.println("Got N=" + l);
    DRand localDRand = new DRand(new Date());
    QuantileBin1D localQuantileBin1D = new QuantileBin1D(false, l, 0.0001D, 0.001D, 200, localDRand, false, false, 2);
    DynamicBin1D localDynamicBin1D = new DynamicBin1D();
    Uniform localUniform = new Uniform(new DRand(7757));
    for (int j = 1; j <= i; j++)
    {
      double d1 = localUniform.nextDouble();
      localQuantileBin1D.add(d1);
      localDynamicBin1D.add(d1);
    }
    DecimalFormat localDecimalFormat = new DecimalFormat("0.00");
    System.out.println();
    int k = 10;
    int m = 1;
    while (m < 100)
    {
      double d2 = m * 0.01D;
      double d3 = localQuantileBin1D.quantile(d2);
      System.out.println(localDecimalFormat.format(d2) + "  " + d3 + ",  " + localDynamicBin1D.quantile(d2) + ",  " + (localDynamicBin1D.quantile(d2) - d3));
      m += k;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.Quantile1Test
 * JD-Core Version:    0.7.0.1
 */
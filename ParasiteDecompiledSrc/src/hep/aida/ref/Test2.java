package hep.aida.ref;

import hep.aida.IHistogram1D;
import hep.aida.IHistogram2D;
import hep.aida.IHistogram3D;
import java.io.PrintStream;
import java.util.Random;

public class Test2
{
  public static void main(String[] paramArrayOfString)
  {
    Random localRandom = new Random();
    Histogram1D localHistogram1D = new Histogram1D("AIDA 1D Histogram", 40, -3.0D, 3.0D);
    for (int i = 0; i < 10000; i++) {
      localHistogram1D.fill(localRandom.nextGaussian());
    }
    Histogram2D localHistogram2D = new Histogram2D("AIDA 2D Histogram", 40, -3.0D, 3.0D, 40, -3.0D, 3.0D);
    for (int j = 0; j < 10000; j++) {
      localHistogram2D.fill(localRandom.nextGaussian(), localRandom.nextGaussian());
    }
    writeAsXML(localHistogram1D, "aida1.xml");
    writeAsXML(localHistogram2D, "aida2.xml");
    writeAsXML(localHistogram2D.projectionX(), "projectionX.xml");
    writeAsXML(localHistogram2D.projectionY(), "projectionY.xml");
  }
  
  public static void main2(String[] paramArrayOfString)
  {
    double[] arrayOfDouble = { -30.0D, 0.0D, 30.0D, 1000.0D };
    Random localRandom = new Random();
    Histogram1D localHistogram1D = new Histogram1D("AIDA 1D Histogram", new VariableAxis(arrayOfDouble));
    for (int i = 0; i < 10000; i++) {
      localHistogram1D.fill(localRandom.nextGaussian());
    }
    Histogram2D localHistogram2D = new Histogram2D("AIDA 2D Histogram", new VariableAxis(arrayOfDouble), new VariableAxis(arrayOfDouble));
    for (int j = 0; j < 10000; j++) {
      localHistogram2D.fill(localRandom.nextGaussian(), localRandom.nextGaussian());
    }
    Histogram3D localHistogram3D = new Histogram3D("AIDA 3D Histogram", 10, -2.0D, 2.0D, 5, -2.0D, 2.0D, 3, -2.0D, 2.0D);
    for (int k = 0; k < 10000; k++) {
      localHistogram3D.fill(localRandom.nextGaussian(), localRandom.nextGaussian(), localRandom.nextGaussian());
    }
    writeAsXML(localHistogram1D, "aida1.xml");
    writeAsXML(localHistogram2D, "aida2.xml");
    writeAsXML(localHistogram3D, "aida2.xml");
    writeAsXML(localHistogram2D.projectionX(), "projectionX.xml");
    writeAsXML(localHistogram2D.projectionY(), "projectionY.xml");
  }
  
  private static void writeAsXML(IHistogram1D paramIHistogram1D, String paramString)
  {
    System.out.println(new Converter().toString(paramIHistogram1D));
  }
  
  private static void writeAsXML(IHistogram2D paramIHistogram2D, String paramString)
  {
    System.out.println(new Converter().toString(paramIHistogram2D));
  }
  
  private static void writeAsXML(IHistogram3D paramIHistogram3D, String paramString)
  {
    System.out.println(new Converter().toString(paramIHistogram3D));
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.Test2
 * JD-Core Version:    0.7.0.1
 */
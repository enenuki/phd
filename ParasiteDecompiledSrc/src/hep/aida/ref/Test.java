package hep.aida.ref;

import hep.aida.IAxis;
import hep.aida.IHistogram1D;
import hep.aida.IHistogram2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Test
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
  
  private static void writeAsXML(IHistogram1D paramIHistogram1D, String paramString)
  {
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
      localPrintWriter.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
      localPrintWriter.println("<!DOCTYPE plotML SYSTEM \"plotML.dtd\">");
      localPrintWriter.println("<plotML>");
      localPrintWriter.println("<plot>");
      localPrintWriter.println("<dataArea>");
      localPrintWriter.println("<data1d>");
      localPrintWriter.println("<bins1d title=\"" + paramIHistogram1D.title() + "\">");
      for (int i = 0; i < paramIHistogram1D.xAxis().bins(); i++) {
        localPrintWriter.println(paramIHistogram1D.binEntries(i) + "," + paramIHistogram1D.binError(i));
      }
      localPrintWriter.println("</bins1d>");
      localPrintWriter.print("<binnedDataAxisAttributes type=\"double\" axis=\"x0\"");
      localPrintWriter.print(" min=\"" + paramIHistogram1D.xAxis().lowerEdge() + "\"");
      localPrintWriter.print(" max=\"" + paramIHistogram1D.xAxis().upperEdge() + "\"");
      localPrintWriter.print(" numberOfBins=\"" + paramIHistogram1D.xAxis().bins() + "\"");
      localPrintWriter.println("/>");
      localPrintWriter.println("<statistics>");
      localPrintWriter.println("<statistic name=\"Entries\" value=\"" + paramIHistogram1D.entries() + "\"/>");
      localPrintWriter.println("<statistic name=\"Underflow\" value=\"" + paramIHistogram1D.binEntries(-2) + "\"/>");
      localPrintWriter.println("<statistic name=\"Overflow\" value=\"" + paramIHistogram1D.binEntries(-1) + "\"/>");
      if (!Double.isNaN(paramIHistogram1D.mean())) {
        localPrintWriter.println("<statistic name=\"Mean\" value=\"" + paramIHistogram1D.mean() + "\"/>");
      }
      if (!Double.isNaN(paramIHistogram1D.rms())) {
        localPrintWriter.println("<statistic name=\"RMS\" value=\"" + paramIHistogram1D.rms() + "\"/>");
      }
      localPrintWriter.println("</statistics>");
      localPrintWriter.println("</data1d>");
      localPrintWriter.println("</dataArea>");
      localPrintWriter.println("</plot>");
      localPrintWriter.println("</plotML>");
      localPrintWriter.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  private static void writeAsXML(IHistogram2D paramIHistogram2D, String paramString)
  {
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
      localPrintWriter.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
      localPrintWriter.println("<!DOCTYPE plotML SYSTEM \"plotML.dtd\">");
      localPrintWriter.println("<plotML>");
      localPrintWriter.println("<plot>");
      localPrintWriter.println("<dataArea>");
      localPrintWriter.println("<data2d type=\"xxx\">");
      localPrintWriter.println("<bins2d title=\"" + paramIHistogram2D.title() + "\" xSize=\"" + paramIHistogram2D.xAxis().bins() + "\" ySize=\"" + paramIHistogram2D.yAxis().bins() + "\">");
      for (int i = 0; i < paramIHistogram2D.xAxis().bins(); i++) {
        for (int j = 0; j < paramIHistogram2D.yAxis().bins(); j++) {
          localPrintWriter.println(paramIHistogram2D.binEntries(i, j) + "," + paramIHistogram2D.binError(i, j));
        }
      }
      localPrintWriter.println("</bins2d>");
      localPrintWriter.print("<binnedDataAxisAttributes type=\"double\" axis=\"x0\"");
      localPrintWriter.print(" min=\"" + paramIHistogram2D.xAxis().lowerEdge() + "\"");
      localPrintWriter.print(" max=\"" + paramIHistogram2D.xAxis().upperEdge() + "\"");
      localPrintWriter.print(" numberOfBins=\"" + paramIHistogram2D.xAxis().bins() + "\"");
      localPrintWriter.println("/>");
      localPrintWriter.print("<binnedDataAxisAttributes type=\"double\" axis=\"y0\"");
      localPrintWriter.print(" min=\"" + paramIHistogram2D.yAxis().lowerEdge() + "\"");
      localPrintWriter.print(" max=\"" + paramIHistogram2D.yAxis().upperEdge() + "\"");
      localPrintWriter.print(" numberOfBins=\"" + paramIHistogram2D.yAxis().bins() + "\"");
      localPrintWriter.println("/>");
      localPrintWriter.println("</data2d>");
      localPrintWriter.println("</dataArea>");
      localPrintWriter.println("</plot>");
      localPrintWriter.println("</plotML>");
      localPrintWriter.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.Test
 * JD-Core Version:    0.7.0.1
 */
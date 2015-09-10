package cern.colt;

import java.io.PrintStream;

public final class Version
{
  public static String asString()
  {
    if (getPackage() == null) {
      return "whoschek@lbl.gov";
    }
    String str = getPackage().getImplementationVendor();
    if (str == null) {
      str = "whoschek@lbl.gov";
    }
    return "Version " + getMajorVersion() + "." + getMinorVersion() + "." + getMicroVersion() + "." + getBuildVersion() + " (" + getBuildTime() + ")" + "\nPlease report problems to " + str;
  }
  
  public static String getBuildTime()
  {
    if (getPackage() == null) {
      return "unknown";
    }
    String str = getPackage().getImplementationVersion();
    if (str == null) {
      return "unknown";
    }
    int i = str.indexOf('(');
    return str.substring(i + 1, str.length() - 1);
  }
  
  public static int getBuildVersion()
  {
    return numbers()[3];
  }
  
  public static int getMajorVersion()
  {
    return numbers()[0];
  }
  
  public static int getMicroVersion()
  {
    return numbers()[2];
  }
  
  public static int getMinorVersion()
  {
    return numbers()[1];
  }
  
  private static Package getPackage()
  {
    return Package.getPackage("cern.colt");
  }
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println(asString());
  }
  
  private static int[] numbers()
  {
    int i = 4;
    int[] arrayOfInt = { 1, 1, 0, 0 };
    return arrayOfInt;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.Version
 * JD-Core Version:    0.7.0.1
 */
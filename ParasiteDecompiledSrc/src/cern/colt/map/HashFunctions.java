package cern.colt.map;

public class HashFunctions
{
  public static int hash(char paramChar)
  {
    return paramChar;
  }
  
  public static int hash(double paramDouble)
  {
    long l = Double.doubleToLongBits(paramDouble);
    return (int)(l ^ l >>> 32);
  }
  
  public static int hash(float paramFloat)
  {
    return Float.floatToIntBits(paramFloat * 6.63609E+008F);
  }
  
  public static int hash(int paramInt)
  {
    return paramInt;
  }
  
  public static int hash(long paramLong)
  {
    return (int)(paramLong ^ paramLong >> 32);
  }
  
  public static int hash(Object paramObject)
  {
    return paramObject == null ? 0 : paramObject.hashCode();
  }
  
  public static int hash(short paramShort)
  {
    return paramShort;
  }
  
  public static int hash(boolean paramBoolean)
  {
    return paramBoolean ? 1231 : 1237;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.HashFunctions
 * JD-Core Version:    0.7.0.1
 */
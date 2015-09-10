package cern.colt;

public class Arrays
{
  public static byte[] ensureCapacity(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte.length;
    byte[] arrayOfByte;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      arrayOfByte = new byte[j];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, i);
    }
    else
    {
      arrayOfByte = paramArrayOfByte;
    }
    return arrayOfByte;
  }
  
  public static char[] ensureCapacity(char[] paramArrayOfChar, int paramInt)
  {
    int i = paramArrayOfChar.length;
    char[] arrayOfChar;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      arrayOfChar = new char[j];
      System.arraycopy(paramArrayOfChar, 0, arrayOfChar, 0, i);
    }
    else
    {
      arrayOfChar = paramArrayOfChar;
    }
    return arrayOfChar;
  }
  
  public static double[] ensureCapacity(double[] paramArrayOfDouble, int paramInt)
  {
    int i = paramArrayOfDouble.length;
    double[] arrayOfDouble;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      arrayOfDouble = new double[j];
      System.arraycopy(paramArrayOfDouble, 0, arrayOfDouble, 0, i);
    }
    else
    {
      arrayOfDouble = paramArrayOfDouble;
    }
    return arrayOfDouble;
  }
  
  public static float[] ensureCapacity(float[] paramArrayOfFloat, int paramInt)
  {
    int i = paramArrayOfFloat.length;
    float[] arrayOfFloat;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      arrayOfFloat = new float[j];
      System.arraycopy(paramArrayOfFloat, 0, arrayOfFloat, 0, i);
    }
    else
    {
      arrayOfFloat = paramArrayOfFloat;
    }
    return arrayOfFloat;
  }
  
  public static int[] ensureCapacity(int[] paramArrayOfInt, int paramInt)
  {
    int i = paramArrayOfInt.length;
    int[] arrayOfInt;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      arrayOfInt = new int[j];
      System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, i);
    }
    else
    {
      arrayOfInt = paramArrayOfInt;
    }
    return arrayOfInt;
  }
  
  public static long[] ensureCapacity(long[] paramArrayOfLong, int paramInt)
  {
    int i = paramArrayOfLong.length;
    long[] arrayOfLong;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      arrayOfLong = new long[j];
      System.arraycopy(paramArrayOfLong, 0, arrayOfLong, 0, i);
    }
    else
    {
      arrayOfLong = paramArrayOfLong;
    }
    return arrayOfLong;
  }
  
  public static Object[] ensureCapacity(Object[] paramArrayOfObject, int paramInt)
  {
    int i = paramArrayOfObject.length;
    Object[] arrayOfObject;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      arrayOfObject = new Object[j];
      System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, i);
    }
    else
    {
      arrayOfObject = paramArrayOfObject;
    }
    return arrayOfObject;
  }
  
  public static short[] ensureCapacity(short[] paramArrayOfShort, int paramInt)
  {
    int i = paramArrayOfShort.length;
    short[] arrayOfShort;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      arrayOfShort = new short[j];
      System.arraycopy(paramArrayOfShort, 0, arrayOfShort, 0, i);
    }
    else
    {
      arrayOfShort = paramArrayOfShort;
    }
    return arrayOfShort;
  }
  
  public static boolean[] ensureCapacity(boolean[] paramArrayOfBoolean, int paramInt)
  {
    int i = paramArrayOfBoolean.length;
    boolean[] arrayOfBoolean;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      arrayOfBoolean = new boolean[j];
      System.arraycopy(paramArrayOfBoolean, 0, arrayOfBoolean, 0, i);
    }
    else
    {
      arrayOfBoolean = paramArrayOfBoolean;
    }
    return arrayOfBoolean;
  }
  
  public static String toString(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfByte.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfByte[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public static String toString(char[] paramArrayOfChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfChar.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfChar[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public static String toString(double[] paramArrayOfDouble)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfDouble.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfDouble[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public static String toString(float[] paramArrayOfFloat)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfFloat.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfFloat[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public static String toString(int[] paramArrayOfInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfInt.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfInt[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public static String toString(long[] paramArrayOfLong)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfLong.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfLong[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public static String toString(Object[] paramArrayOfObject)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfObject.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfObject[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public static String toString(short[] paramArrayOfShort)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfShort.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfShort[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public static String toString(boolean[] paramArrayOfBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfBoolean.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfBoolean[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public static byte[] trimToCapacity(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length > paramInt)
    {
      byte[] arrayOfByte = paramArrayOfByte;
      paramArrayOfByte = new byte[paramInt];
      System.arraycopy(arrayOfByte, 0, paramArrayOfByte, 0, paramInt);
    }
    return paramArrayOfByte;
  }
  
  public static char[] trimToCapacity(char[] paramArrayOfChar, int paramInt)
  {
    if (paramArrayOfChar.length > paramInt)
    {
      char[] arrayOfChar = paramArrayOfChar;
      paramArrayOfChar = new char[paramInt];
      System.arraycopy(arrayOfChar, 0, paramArrayOfChar, 0, paramInt);
    }
    return paramArrayOfChar;
  }
  
  public static double[] trimToCapacity(double[] paramArrayOfDouble, int paramInt)
  {
    if (paramArrayOfDouble.length > paramInt)
    {
      double[] arrayOfDouble = paramArrayOfDouble;
      paramArrayOfDouble = new double[paramInt];
      System.arraycopy(arrayOfDouble, 0, paramArrayOfDouble, 0, paramInt);
    }
    return paramArrayOfDouble;
  }
  
  public static float[] trimToCapacity(float[] paramArrayOfFloat, int paramInt)
  {
    if (paramArrayOfFloat.length > paramInt)
    {
      float[] arrayOfFloat = paramArrayOfFloat;
      paramArrayOfFloat = new float[paramInt];
      System.arraycopy(arrayOfFloat, 0, paramArrayOfFloat, 0, paramInt);
    }
    return paramArrayOfFloat;
  }
  
  public static int[] trimToCapacity(int[] paramArrayOfInt, int paramInt)
  {
    if (paramArrayOfInt.length > paramInt)
    {
      int[] arrayOfInt = paramArrayOfInt;
      paramArrayOfInt = new int[paramInt];
      System.arraycopy(arrayOfInt, 0, paramArrayOfInt, 0, paramInt);
    }
    return paramArrayOfInt;
  }
  
  public static long[] trimToCapacity(long[] paramArrayOfLong, int paramInt)
  {
    if (paramArrayOfLong.length > paramInt)
    {
      long[] arrayOfLong = paramArrayOfLong;
      paramArrayOfLong = new long[paramInt];
      System.arraycopy(arrayOfLong, 0, paramArrayOfLong, 0, paramInt);
    }
    return paramArrayOfLong;
  }
  
  public static Object[] trimToCapacity(Object[] paramArrayOfObject, int paramInt)
  {
    if (paramArrayOfObject.length > paramInt)
    {
      Object[] arrayOfObject = paramArrayOfObject;
      paramArrayOfObject = new Object[paramInt];
      System.arraycopy(arrayOfObject, 0, paramArrayOfObject, 0, paramInt);
    }
    return paramArrayOfObject;
  }
  
  public static short[] trimToCapacity(short[] paramArrayOfShort, int paramInt)
  {
    if (paramArrayOfShort.length > paramInt)
    {
      short[] arrayOfShort = paramArrayOfShort;
      paramArrayOfShort = new short[paramInt];
      System.arraycopy(arrayOfShort, 0, paramArrayOfShort, 0, paramInt);
    }
    return paramArrayOfShort;
  }
  
  public static boolean[] trimToCapacity(boolean[] paramArrayOfBoolean, int paramInt)
  {
    if (paramArrayOfBoolean.length > paramInt)
    {
      boolean[] arrayOfBoolean = paramArrayOfBoolean;
      paramArrayOfBoolean = new boolean[paramInt];
      System.arraycopy(arrayOfBoolean, 0, paramArrayOfBoolean, 0, paramInt);
    }
    return paramArrayOfBoolean;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.Arrays
 * JD-Core Version:    0.7.0.1
 */
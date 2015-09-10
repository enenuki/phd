package cern.colt;

import cern.jet.math.Arithmetic;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;

public class GenericPermuting
{
  public static int[] permutation(long paramLong, int paramInt)
  {
    if (paramLong < 1L) {
      throw new IllegalArgumentException("Permutations are enumerated 1 .. N!");
    }
    if (paramInt < 0) {
      throw new IllegalArgumentException("Must satisfy N >= 0");
    }
    int[] arrayOfInt = new int[paramInt];
    if (paramInt > 20)
    {
      int i = paramInt;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        arrayOfInt[i] = i;
      }
      localObject = new Uniform(new MersenneTwister((int)paramLong));
      for (j = 0; j < paramInt - 1; j++)
      {
        int k = ((Uniform)localObject).nextIntFromTo(j, paramInt - 1);
        m = arrayOfInt[k];
        arrayOfInt[k] = arrayOfInt[j];
        arrayOfInt[j] = m;
      }
      return arrayOfInt;
    }
    if (paramLong > Arithmetic.longFactorial(paramInt)) {
      throw new IllegalArgumentException("N too large (a sequence of N elements only has N! permutations).");
    }
    Object localObject = new int[paramInt];
    for (int j = 1; j <= paramInt; j++) {
      localObject[(j - 1)] = j;
    }
    long l1 = paramLong - 1L;
    for (int m = paramInt - 1; m >= 1; m--)
    {
      long l2 = Arithmetic.longFactorial(m);
      int n = (int)(l1 / l2) + 1;
      l1 %= l2;
      arrayOfInt[(paramInt - m - 1)] = localObject[(n - 1)];
      for (int i1 = n; i1 <= m; i1++) {
        localObject[(i1 - 1)] = localObject[i1];
      }
    }
    if (paramInt > 0) {
      arrayOfInt[(paramInt - 1)] = localObject[0];
    }
    m = paramInt;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      arrayOfInt[m] -= 1;
    }
    return arrayOfInt;
  }
  
  public static void permute(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int[] arrayOfInt = (int[])paramArrayOfInt1.clone();
    int i = paramArrayOfInt1.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramArrayOfInt1[i] = arrayOfInt[paramArrayOfInt2[i]];
    }
  }
  
  /**
   * @deprecated
   */
  public static void permute(int[] paramArrayOfInt1, Swapper paramSwapper, int[] paramArrayOfInt2)
  {
    permute(paramArrayOfInt1, paramSwapper, paramArrayOfInt2, null);
  }
  
  public static void permute(int[] paramArrayOfInt1, Swapper paramSwapper, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    int i = paramArrayOfInt1.length;
    int[] arrayOfInt1 = paramArrayOfInt2;
    int[] arrayOfInt2 = paramArrayOfInt3;
    if ((arrayOfInt1 == null) || (arrayOfInt1.length < i)) {
      arrayOfInt1 = new int[i];
    }
    if ((arrayOfInt2 == null) || (arrayOfInt2.length < i)) {
      arrayOfInt2 = new int[i];
    }
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt1[j] = j;
      arrayOfInt2[j] = j;
    }
    for (j = 0; j < i; j++)
    {
      int k = paramArrayOfInt1[j];
      int m = arrayOfInt1[k];
      if (j != m)
      {
        paramSwapper.swap(j, m);
        arrayOfInt1[k] = j;
        arrayOfInt1[arrayOfInt2[j]] = m;
        int n = arrayOfInt2[j];
        arrayOfInt2[j] = arrayOfInt2[m];
        arrayOfInt2[m] = n;
      }
    }
  }
  
  public static void permute(Object[] paramArrayOfObject, int[] paramArrayOfInt)
  {
    Object[] arrayOfObject = (Object[])paramArrayOfObject.clone();
    int i = paramArrayOfObject.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramArrayOfObject[i] = arrayOfObject[paramArrayOfInt[i]];
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.GenericPermuting
 * JD-Core Version:    0.7.0.1
 */
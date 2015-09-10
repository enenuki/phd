package cern.jet.stat.quantile;

import cern.colt.function.DoubleProcedure;
import cern.colt.list.DoubleArrayList;

class DoubleBufferSet
  extends BufferSet
{
  protected DoubleBuffer[] buffers;
  private boolean nextTriggerCalculationState;
  
  public DoubleBufferSet(int paramInt1, int paramInt2)
  {
    this.buffers = new DoubleBuffer[paramInt1];
    clear(paramInt2);
  }
  
  public DoubleBuffer _getFirstEmptyBuffer()
  {
    DoubleBuffer localDoubleBuffer = null;
    int i = this.buffers.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      if (this.buffers[i].isEmpty())
      {
        if (this.buffers[i].isAllocated()) {
          return this.buffers[i];
        }
        localDoubleBuffer = this.buffers[i];
      }
    }
    return localDoubleBuffer;
  }
  
  public DoubleBuffer[] _getFullOrPartialBuffers()
  {
    int i = 0;
    int j = this.buffers.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      if (!this.buffers[j].isEmpty()) {
        i++;
      }
    }
    DoubleBuffer[] arrayOfDoubleBuffer = new DoubleBuffer[i];
    int k = 0;
    int m = this.buffers.length;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      if (!this.buffers[m].isEmpty()) {
        arrayOfDoubleBuffer[(k++)] = this.buffers[m];
      }
    }
    return arrayOfDoubleBuffer;
  }
  
  public DoubleBuffer[] _getFullOrPartialBuffersWithLevel(int paramInt)
  {
    int i = 0;
    int j = this.buffers.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      if ((!this.buffers[j].isEmpty()) && (this.buffers[j].level() == paramInt)) {
        i++;
      }
    }
    DoubleBuffer[] arrayOfDoubleBuffer = new DoubleBuffer[i];
    int k = 0;
    int m = this.buffers.length;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      if ((!this.buffers[m].isEmpty()) && (this.buffers[m].level() == paramInt)) {
        arrayOfDoubleBuffer[(k++)] = this.buffers[m];
      }
    }
    return arrayOfDoubleBuffer;
  }
  
  public int _getMinLevelOfFullOrPartialBuffers()
  {
    int i = b();
    int j = 2147483647;
    for (int k = 0; k < i; k++)
    {
      DoubleBuffer localDoubleBuffer = this.buffers[k];
      if ((!localDoubleBuffer.isEmpty()) && (localDoubleBuffer.level() < j)) {
        j = localDoubleBuffer.level();
      }
    }
    return j;
  }
  
  public int _getNumberOfEmptyBuffers()
  {
    int i = 0;
    int j = this.buffers.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      if (this.buffers[j].isEmpty()) {
        i++;
      }
    }
    return i;
  }
  
  public DoubleBuffer _getPartialBuffer()
  {
    int i = this.buffers.length;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (!this.buffers[i].isPartial());
    return this.buffers[i];
    return null;
  }
  
  public int b()
  {
    return this.buffers.length;
  }
  
  public void clear()
  {
    clear(k());
  }
  
  protected void clear(int paramInt)
  {
    int i = b();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      this.buffers[i] = new DoubleBuffer(paramInt);
    }
    this.nextTriggerCalculationState = true;
  }
  
  public Object clone()
  {
    DoubleBufferSet localDoubleBufferSet = (DoubleBufferSet)super.clone();
    localDoubleBufferSet.buffers = ((DoubleBuffer[])localDoubleBufferSet.buffers.clone());
    int i = this.buffers.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localDoubleBufferSet.buffers[i] = ((DoubleBuffer)localDoubleBufferSet.buffers[i].clone());
    }
    return localDoubleBufferSet;
  }
  
  public DoubleBuffer collapse(DoubleBuffer[] paramArrayOfDoubleBuffer)
  {
    int i = 0;
    for (int j = 0; j < paramArrayOfDoubleBuffer.length; j++) {
      i += paramArrayOfDoubleBuffer[j].weight();
    }
    j = k();
    long[] arrayOfLong = new long[j];
    for (int k = 0; k < j; k++) {
      arrayOfLong[k] = nextTriggerPosition(k, i);
    }
    double[] arrayOfDouble = getValuesAtPositions(paramArrayOfDoubleBuffer, arrayOfLong);
    for (int m = 1; m < paramArrayOfDoubleBuffer.length; m++) {
      paramArrayOfDoubleBuffer[m].clear();
    }
    DoubleBuffer localDoubleBuffer = paramArrayOfDoubleBuffer[0];
    localDoubleBuffer.values.elements(arrayOfDouble);
    localDoubleBuffer.weight(i);
    return localDoubleBuffer;
  }
  
  public boolean contains(double paramDouble)
  {
    int i = this.buffers.length;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while ((this.buffers[i].isEmpty()) || (!this.buffers[i].contains(paramDouble)));
    return true;
    return false;
  }
  
  public boolean forEach(DoubleProcedure paramDoubleProcedure)
  {
    int i;
    do
    {
      i = this.buffers.length;
      i--;
      int j;
      do
      {
        if (i < 0) {
          break;
        }
        j = this.buffers[i].weight();
        j--;
      } while (j < 0);
    } while (this.buffers[i].values.forEach(paramDoubleProcedure));
    return false;
    return true;
  }
  
  protected double[] getValuesAtPositions(DoubleBuffer[] paramArrayOfDoubleBuffer, long[] paramArrayOfLong)
  {
    int i = paramArrayOfDoubleBuffer.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramArrayOfDoubleBuffer[i].sort();
    }
    int[] arrayOfInt1 = new int[paramArrayOfDoubleBuffer.length];
    double[][] arrayOfDouble = new double[paramArrayOfDoubleBuffer.length][];
    int j = 0;
    int k = paramArrayOfDoubleBuffer.length;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      arrayOfInt1[k] = paramArrayOfDoubleBuffer[k].size();
      arrayOfDouble[k] = paramArrayOfDoubleBuffer[k].values.elements();
      j += arrayOfInt1[k];
    }
    k = paramArrayOfDoubleBuffer.length;
    int m = paramArrayOfLong.length;
    int n = 0;
    int[] arrayOfInt2 = new int[paramArrayOfDoubleBuffer.length];
    long l1 = 0L;
    long l2 = paramArrayOfLong[n];
    double[] arrayOfDouble1 = new double[m];
    if (j == 0)
    {
      for (int i1 = 0; i1 < paramArrayOfLong.length; i1++) {
        arrayOfDouble1[i1] = (0.0D / 0.0D);
      }
      return arrayOfDouble1;
    }
    while (n < m)
    {
      double d1 = (1.0D / 0.0D);
      int i2 = -1;
      int i3 = k;
      for (;;)
      {
        i3--;
        if (i3 < 0) {
          break;
        }
        if (arrayOfInt2[i3] < arrayOfInt1[i3])
        {
          double d2 = arrayOfDouble[i3][arrayOfInt2[i3]];
          if (d2 <= d1)
          {
            d1 = d2;
            i2 = i3;
          }
        }
      }
      DoubleBuffer localDoubleBuffer = paramArrayOfDoubleBuffer[i2];
      l1 += localDoubleBuffer.weight();
      while ((l1 > l2) && (n < m))
      {
        arrayOfDouble1[(n++)] = d1;
        if (n < m) {
          l2 = paramArrayOfLong[n];
        }
      }
      arrayOfInt2[i2] += 1;
    }
    return arrayOfDouble1;
  }
  
  public int k()
  {
    return this.buffers[0].k;
  }
  
  public long memory()
  {
    long l = 0L;
    int i = this.buffers.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      l += this.buffers[i].memory();
    }
    return l;
  }
  
  protected long nextTriggerPosition(int paramInt, long paramLong)
  {
    long l;
    if (paramLong % 2L != 0L) {
      l = paramInt * paramLong + (paramLong + 1L) / 2L;
    } else if (this.nextTriggerCalculationState) {
      l = paramInt * paramLong + paramLong / 2L;
    } else {
      l = paramInt * paramLong + (paramLong + 2L) / 2L;
    }
    return l;
  }
  
  public double phi(double paramDouble)
  {
    double d = 0.0D;
    int i = this.buffers.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      if (!this.buffers[i].isEmpty()) {
        d += this.buffers[i].weight * this.buffers[i].rank(paramDouble);
      }
    }
    return d / totalSize();
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < b(); i++) {
      if (!this.buffers[i].isEmpty())
      {
        localStringBuffer.append("buffer#" + i + " = ");
        localStringBuffer.append(this.buffers[i].toString() + "\n");
      }
    }
    return localStringBuffer.toString();
  }
  
  public long totalSize()
  {
    DoubleBuffer[] arrayOfDoubleBuffer = _getFullOrPartialBuffers();
    long l = 0L;
    int i = arrayOfDoubleBuffer.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      l += arrayOfDoubleBuffer[i].size() * arrayOfDoubleBuffer[i].weight();
    }
    return l;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.DoubleBufferSet
 * JD-Core Version:    0.7.0.1
 */
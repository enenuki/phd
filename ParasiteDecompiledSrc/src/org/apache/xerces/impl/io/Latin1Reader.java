package org.apache.xerces.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class Latin1Reader
  extends Reader
{
  public static final int DEFAULT_BUFFER_SIZE = 2048;
  protected final InputStream fInputStream;
  protected final byte[] fBuffer;
  
  public Latin1Reader(InputStream paramInputStream)
  {
    this(paramInputStream, 2048);
  }
  
  public Latin1Reader(InputStream paramInputStream, int paramInt)
  {
    this(paramInputStream, new byte[paramInt]);
  }
  
  public Latin1Reader(InputStream paramInputStream, byte[] paramArrayOfByte)
  {
    this.fInputStream = paramInputStream;
    this.fBuffer = paramArrayOfByte;
  }
  
  public int read()
    throws IOException
  {
    return this.fInputStream.read();
  }
  
  public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt2 > this.fBuffer.length) {
      paramInt2 = this.fBuffer.length;
    }
    int i = this.fInputStream.read(this.fBuffer, 0, paramInt2);
    for (int j = 0; j < i; j++) {
      paramArrayOfChar[(paramInt1 + j)] = ((char)(this.fBuffer[j] & 0xFF));
    }
    return i;
  }
  
  public long skip(long paramLong)
    throws IOException
  {
    return this.fInputStream.skip(paramLong);
  }
  
  public boolean ready()
    throws IOException
  {
    return false;
  }
  
  public boolean markSupported()
  {
    return this.fInputStream.markSupported();
  }
  
  public void mark(int paramInt)
    throws IOException
  {
    this.fInputStream.mark(paramInt);
  }
  
  public void reset()
    throws IOException
  {
    this.fInputStream.reset();
  }
  
  public void close()
    throws IOException
  {
    this.fInputStream.close();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.io.Latin1Reader
 * JD-Core Version:    0.7.0.1
 */
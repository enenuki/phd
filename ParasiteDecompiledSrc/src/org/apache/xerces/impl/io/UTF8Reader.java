package org.apache.xerces.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Locale;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.util.MessageFormatter;

public class UTF8Reader
  extends Reader
{
  public static final int DEFAULT_BUFFER_SIZE = 2048;
  private static final boolean DEBUG_READ = false;
  protected final InputStream fInputStream;
  protected final byte[] fBuffer;
  protected int fOffset;
  private int fSurrogate = -1;
  private final MessageFormatter fFormatter;
  private final Locale fLocale;
  
  public UTF8Reader(InputStream paramInputStream)
  {
    this(paramInputStream, 2048, new XMLMessageFormatter(), Locale.getDefault());
  }
  
  public UTF8Reader(InputStream paramInputStream, MessageFormatter paramMessageFormatter, Locale paramLocale)
  {
    this(paramInputStream, 2048, paramMessageFormatter, paramLocale);
  }
  
  public UTF8Reader(InputStream paramInputStream, int paramInt, MessageFormatter paramMessageFormatter, Locale paramLocale)
  {
    this(paramInputStream, new byte[paramInt], paramMessageFormatter, paramLocale);
  }
  
  public UTF8Reader(InputStream paramInputStream, byte[] paramArrayOfByte, MessageFormatter paramMessageFormatter, Locale paramLocale)
  {
    this.fInputStream = paramInputStream;
    this.fBuffer = paramArrayOfByte;
    this.fFormatter = paramMessageFormatter;
    this.fLocale = paramLocale;
  }
  
  public int read()
    throws IOException
  {
    int i = this.fSurrogate;
    if (this.fSurrogate == -1)
    {
      int j = 0;
      int k = j == this.fOffset ? this.fInputStream.read() : this.fBuffer[(j++)] & 0xFF;
      if (k == -1) {
        return -1;
      }
      if (k < 128)
      {
        i = (char)k;
      }
      else
      {
        int m;
        if (((k & 0xE0) == 192) && ((k & 0x1E) != 0))
        {
          m = j == this.fOffset ? this.fInputStream.read() : this.fBuffer[(j++)] & 0xFF;
          if (m == -1) {
            expectedByte(2, 2);
          }
          if ((m & 0xC0) != 128) {
            invalidByte(2, 2, m);
          }
          i = k << 6 & 0x7C0 | m & 0x3F;
        }
        else
        {
          int n;
          if ((k & 0xF0) == 224)
          {
            m = j == this.fOffset ? this.fInputStream.read() : this.fBuffer[(j++)] & 0xFF;
            if (m == -1) {
              expectedByte(2, 3);
            }
            if (((m & 0xC0) != 128) || ((k == 237) && (m >= 160)) || (((k & 0xF) == 0) && ((m & 0x20) == 0))) {
              invalidByte(2, 3, m);
            }
            n = j == this.fOffset ? this.fInputStream.read() : this.fBuffer[(j++)] & 0xFF;
            if (n == -1) {
              expectedByte(3, 3);
            }
            if ((n & 0xC0) != 128) {
              invalidByte(3, 3, n);
            }
            i = k << 12 & 0xF000 | m << 6 & 0xFC0 | n & 0x3F;
          }
          else if ((k & 0xF8) == 240)
          {
            m = j == this.fOffset ? this.fInputStream.read() : this.fBuffer[(j++)] & 0xFF;
            if (m == -1) {
              expectedByte(2, 4);
            }
            if (((m & 0xC0) != 128) || (((m & 0x30) == 0) && ((k & 0x7) == 0))) {
              invalidByte(2, 3, m);
            }
            n = j == this.fOffset ? this.fInputStream.read() : this.fBuffer[(j++)] & 0xFF;
            if (n == -1) {
              expectedByte(3, 4);
            }
            if ((n & 0xC0) != 128) {
              invalidByte(3, 3, n);
            }
            int i1 = j == this.fOffset ? this.fInputStream.read() : this.fBuffer[(j++)] & 0xFF;
            if (i1 == -1) {
              expectedByte(4, 4);
            }
            if ((i1 & 0xC0) != 128) {
              invalidByte(4, 4, i1);
            }
            int i2 = k << 2 & 0x1C | m >> 4 & 0x3;
            if (i2 > 16) {
              invalidSurrogate(i2);
            }
            int i3 = i2 - 1;
            int i4 = 0xD800 | i3 << 6 & 0x3C0 | m << 2 & 0x3C | n >> 4 & 0x3;
            int i5 = 0xDC00 | n << 6 & 0x3C0 | i1 & 0x3F;
            i = i4;
            this.fSurrogate = i5;
          }
          else
          {
            invalidByte(1, 1, k);
          }
        }
      }
    }
    else
    {
      this.fSurrogate = -1;
    }
    return i;
  }
  
  public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = paramInt1;
    int j = 0;
    if (this.fOffset == 0)
    {
      if (paramInt2 > this.fBuffer.length) {
        paramInt2 = this.fBuffer.length;
      }
      if (this.fSurrogate != -1)
      {
        paramArrayOfChar[(i++)] = ((char)this.fSurrogate);
        this.fSurrogate = -1;
        paramInt2--;
      }
      j = this.fInputStream.read(this.fBuffer, 0, paramInt2);
      if (j == -1) {
        return -1;
      }
      j += i - paramInt1;
    }
    else
    {
      j = this.fOffset;
      this.fOffset = 0;
    }
    int k = j;
    int n;
    for (int m = 0; m < k; m++)
    {
      n = this.fBuffer[m];
      if (n < 0) {
        break;
      }
      paramArrayOfChar[(i++)] = ((char)n);
    }
    while (m < k)
    {
      n = this.fBuffer[m];
      if (n >= 0)
      {
        paramArrayOfChar[(i++)] = ((char)n);
      }
      else
      {
        int i1 = n & 0xFF;
        int i2;
        int i3;
        if (((i1 & 0xE0) == 192) && ((i1 & 0x1E) != 0))
        {
          i2 = -1;
          m++;
          if (m < k)
          {
            i2 = this.fBuffer[m] & 0xFF;
          }
          else
          {
            i2 = this.fInputStream.read();
            if (i2 == -1)
            {
              if (i > paramInt1)
              {
                this.fBuffer[0] = ((byte)i1);
                this.fOffset = 1;
                return i - paramInt1;
              }
              expectedByte(2, 2);
            }
            j++;
          }
          if ((i2 & 0xC0) != 128)
          {
            if (i > paramInt1)
            {
              this.fBuffer[0] = ((byte)i1);
              this.fBuffer[1] = ((byte)i2);
              this.fOffset = 2;
              return i - paramInt1;
            }
            invalidByte(2, 2, i2);
          }
          i3 = i1 << 6 & 0x7C0 | i2 & 0x3F;
          paramArrayOfChar[(i++)] = ((char)i3);
          j--;
        }
        else
        {
          int i4;
          if ((i1 & 0xF0) == 224)
          {
            i2 = -1;
            m++;
            if (m < k)
            {
              i2 = this.fBuffer[m] & 0xFF;
            }
            else
            {
              i2 = this.fInputStream.read();
              if (i2 == -1)
              {
                if (i > paramInt1)
                {
                  this.fBuffer[0] = ((byte)i1);
                  this.fOffset = 1;
                  return i - paramInt1;
                }
                expectedByte(2, 3);
              }
              j++;
            }
            if (((i2 & 0xC0) != 128) || ((i1 == 237) && (i2 >= 160)) || (((i1 & 0xF) == 0) && ((i2 & 0x20) == 0)))
            {
              if (i > paramInt1)
              {
                this.fBuffer[0] = ((byte)i1);
                this.fBuffer[1] = ((byte)i2);
                this.fOffset = 2;
                return i - paramInt1;
              }
              invalidByte(2, 3, i2);
            }
            i3 = -1;
            m++;
            if (m < k)
            {
              i3 = this.fBuffer[m] & 0xFF;
            }
            else
            {
              i3 = this.fInputStream.read();
              if (i3 == -1)
              {
                if (i > paramInt1)
                {
                  this.fBuffer[0] = ((byte)i1);
                  this.fBuffer[1] = ((byte)i2);
                  this.fOffset = 2;
                  return i - paramInt1;
                }
                expectedByte(3, 3);
              }
              j++;
            }
            if ((i3 & 0xC0) != 128)
            {
              if (i > paramInt1)
              {
                this.fBuffer[0] = ((byte)i1);
                this.fBuffer[1] = ((byte)i2);
                this.fBuffer[2] = ((byte)i3);
                this.fOffset = 3;
                return i - paramInt1;
              }
              invalidByte(3, 3, i3);
            }
            i4 = i1 << 12 & 0xF000 | i2 << 6 & 0xFC0 | i3 & 0x3F;
            paramArrayOfChar[(i++)] = ((char)i4);
            j -= 2;
          }
          else if ((i1 & 0xF8) == 240)
          {
            i2 = -1;
            m++;
            if (m < k)
            {
              i2 = this.fBuffer[m] & 0xFF;
            }
            else
            {
              i2 = this.fInputStream.read();
              if (i2 == -1)
              {
                if (i > paramInt1)
                {
                  this.fBuffer[0] = ((byte)i1);
                  this.fOffset = 1;
                  return i - paramInt1;
                }
                expectedByte(2, 4);
              }
              j++;
            }
            if (((i2 & 0xC0) != 128) || (((i2 & 0x30) == 0) && ((i1 & 0x7) == 0)))
            {
              if (i > paramInt1)
              {
                this.fBuffer[0] = ((byte)i1);
                this.fBuffer[1] = ((byte)i2);
                this.fOffset = 2;
                return i - paramInt1;
              }
              invalidByte(2, 4, i2);
            }
            i3 = -1;
            m++;
            if (m < k)
            {
              i3 = this.fBuffer[m] & 0xFF;
            }
            else
            {
              i3 = this.fInputStream.read();
              if (i3 == -1)
              {
                if (i > paramInt1)
                {
                  this.fBuffer[0] = ((byte)i1);
                  this.fBuffer[1] = ((byte)i2);
                  this.fOffset = 2;
                  return i - paramInt1;
                }
                expectedByte(3, 4);
              }
              j++;
            }
            if ((i3 & 0xC0) != 128)
            {
              if (i > paramInt1)
              {
                this.fBuffer[0] = ((byte)i1);
                this.fBuffer[1] = ((byte)i2);
                this.fBuffer[2] = ((byte)i3);
                this.fOffset = 3;
                return i - paramInt1;
              }
              invalidByte(3, 4, i3);
            }
            i4 = -1;
            m++;
            if (m < k)
            {
              i4 = this.fBuffer[m] & 0xFF;
            }
            else
            {
              i4 = this.fInputStream.read();
              if (i4 == -1)
              {
                if (i > paramInt1)
                {
                  this.fBuffer[0] = ((byte)i1);
                  this.fBuffer[1] = ((byte)i2);
                  this.fBuffer[2] = ((byte)i3);
                  this.fOffset = 3;
                  return i - paramInt1;
                }
                expectedByte(4, 4);
              }
              j++;
            }
            if ((i4 & 0xC0) != 128)
            {
              if (i > paramInt1)
              {
                this.fBuffer[0] = ((byte)i1);
                this.fBuffer[1] = ((byte)i2);
                this.fBuffer[2] = ((byte)i3);
                this.fBuffer[3] = ((byte)i4);
                this.fOffset = 4;
                return i - paramInt1;
              }
              invalidByte(4, 4, i3);
            }
            int i5 = i1 << 2 & 0x1C | i2 >> 4 & 0x3;
            if (i5 > 16) {
              invalidSurrogate(i5);
            }
            int i6 = i5 - 1;
            int i7 = i2 & 0xF;
            int i8 = i3 & 0x3F;
            int i9 = i4 & 0x3F;
            int i10 = 0xD800 | i6 << 6 & 0x3C0 | i7 << 2 | i8 >> 4;
            int i11 = 0xDC00 | i8 << 6 & 0x3C0 | i9;
            paramArrayOfChar[(i++)] = ((char)i10);
            j -= 2;
            if (j <= paramInt2)
            {
              paramArrayOfChar[(i++)] = ((char)i11);
            }
            else
            {
              this.fSurrogate = i11;
              j--;
            }
          }
          else
          {
            if (i > paramInt1)
            {
              this.fBuffer[0] = ((byte)i1);
              this.fOffset = 1;
              return i - paramInt1;
            }
            invalidByte(1, 1, i1);
          }
        }
      }
      m++;
    }
    return j;
  }
  
  public long skip(long paramLong)
    throws IOException
  {
    long l1 = paramLong;
    char[] arrayOfChar = new char[this.fBuffer.length];
    do
    {
      int i = arrayOfChar.length < l1 ? arrayOfChar.length : (int)l1;
      int j = read(arrayOfChar, 0, i);
      if (j <= 0) {
        break;
      }
      l1 -= j;
    } while (l1 > 0L);
    long l2 = paramLong - l1;
    return l2;
  }
  
  public boolean ready()
    throws IOException
  {
    return false;
  }
  
  public boolean markSupported()
  {
    return false;
  }
  
  public void mark(int paramInt)
    throws IOException
  {
    throw new IOException(this.fFormatter.formatMessage(this.fLocale, "OperationNotSupported", new Object[] { "mark()", "UTF-8" }));
  }
  
  public void reset()
    throws IOException
  {
    this.fOffset = 0;
    this.fSurrogate = -1;
  }
  
  public void close()
    throws IOException
  {
    this.fInputStream.close();
  }
  
  private void expectedByte(int paramInt1, int paramInt2)
    throws MalformedByteSequenceException
  {
    throw new MalformedByteSequenceException(this.fFormatter, this.fLocale, "http://www.w3.org/TR/1998/REC-xml-19980210", "ExpectedByte", new Object[] { Integer.toString(paramInt1), Integer.toString(paramInt2) });
  }
  
  private void invalidByte(int paramInt1, int paramInt2, int paramInt3)
    throws MalformedByteSequenceException
  {
    throw new MalformedByteSequenceException(this.fFormatter, this.fLocale, "http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidByte", new Object[] { Integer.toString(paramInt1), Integer.toString(paramInt2) });
  }
  
  private void invalidSurrogate(int paramInt)
    throws MalformedByteSequenceException
  {
    throw new MalformedByteSequenceException(this.fFormatter, this.fLocale, "http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidHighSurrogate", new Object[] { Integer.toHexString(paramInt) });
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.io.UTF8Reader
 * JD-Core Version:    0.7.0.1
 */
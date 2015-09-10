package org.apache.log4j.spi;

import java.io.Writer;

/**
 * @deprecated
 */
class NullWriter
  extends Writer
{
  public void close() {}
  
  public void flush() {}
  
  public void write(char[] cbuf, int off, int len) {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.NullWriter
 * JD-Core Version:    0.7.0.1
 */
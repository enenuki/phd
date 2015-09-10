package org.apache.xalan.xsltc.runtime.output;

abstract interface OutputBuffer
{
  public abstract String close();
  
  public abstract OutputBuffer append(char paramChar);
  
  public abstract OutputBuffer append(String paramString);
  
  public abstract OutputBuffer append(char[] paramArrayOfChar, int paramInt1, int paramInt2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.output.OutputBuffer
 * JD-Core Version:    0.7.0.1
 */
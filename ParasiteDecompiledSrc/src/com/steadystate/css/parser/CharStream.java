package com.steadystate.css.parser;

import java.io.IOException;

public abstract interface CharStream
{
  public abstract char readChar()
    throws IOException;
  
  /**
   * @deprecated
   */
  public abstract int getColumn();
  
  /**
   * @deprecated
   */
  public abstract int getLine();
  
  public abstract int getEndColumn();
  
  public abstract int getEndLine();
  
  public abstract int getBeginColumn();
  
  public abstract int getBeginLine();
  
  public abstract void backup(int paramInt);
  
  public abstract char BeginToken()
    throws IOException;
  
  public abstract String GetImage();
  
  public abstract char[] GetSuffix(int paramInt);
  
  public abstract void Done();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.CharStream
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xml.serialize;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @deprecated
 */
public class IndentPrinter
  extends Printer
{
  private StringBuffer _line = new StringBuffer(80);
  private StringBuffer _text = new StringBuffer(20);
  private int _spaces = 0;
  private int _thisIndent = this._nextIndent = 0;
  private int _nextIndent;
  
  public IndentPrinter(Writer paramWriter, OutputFormat paramOutputFormat)
  {
    super(paramWriter, paramOutputFormat);
  }
  
  public void enterDTD()
  {
    if (this._dtdWriter == null)
    {
      this._line.append(this._text);
      this._text = new StringBuffer(20);
      flushLine(false);
      this._dtdWriter = new StringWriter();
      this._docWriter = this._writer;
      this._writer = this._dtdWriter;
    }
  }
  
  public String leaveDTD()
  {
    if (this._writer == this._dtdWriter)
    {
      this._line.append(this._text);
      this._text = new StringBuffer(20);
      flushLine(false);
      this._writer = this._docWriter;
      return this._dtdWriter.toString();
    }
    return null;
  }
  
  public void printText(String paramString)
  {
    this._text.append(paramString);
  }
  
  public void printText(StringBuffer paramStringBuffer)
  {
    this._text.append(paramStringBuffer.toString());
  }
  
  public void printText(char paramChar)
  {
    this._text.append(paramChar);
  }
  
  public void printText(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this._text.append(paramArrayOfChar, paramInt1, paramInt2);
  }
  
  public void printSpace()
  {
    if (this._text.length() > 0)
    {
      if ((this._format.getLineWidth() > 0) && (this._thisIndent + this._line.length() + this._spaces + this._text.length() > this._format.getLineWidth()))
      {
        flushLine(false);
        try
        {
          this._writer.write(this._format.getLineSeparator());
        }
        catch (IOException localIOException)
        {
          if (this._exception != null) {
            break label113;
          }
        }
        this._exception = localIOException;
      }
      label113:
      while (this._spaces > 0)
      {
        this._line.append(' ');
        this._spaces -= 1;
      }
      this._line.append(this._text);
      this._text = new StringBuffer(20);
    }
    this._spaces += 1;
  }
  
  public void breakLine()
  {
    breakLine(false);
  }
  
  public void breakLine(boolean paramBoolean)
  {
    if (this._text.length() > 0)
    {
      while (this._spaces > 0)
      {
        this._line.append(' ');
        this._spaces -= 1;
      }
      this._line.append(this._text);
      this._text = new StringBuffer(20);
    }
    flushLine(paramBoolean);
    try
    {
      this._writer.write(this._format.getLineSeparator());
    }
    catch (IOException localIOException)
    {
      if (this._exception == null) {
        this._exception = localIOException;
      }
    }
  }
  
  public void flushLine(boolean paramBoolean)
  {
    if (this._line.length() > 0) {
      try
      {
        if ((this._format.getIndenting()) && (!paramBoolean))
        {
          int i = this._thisIndent;
          if ((2 * i > this._format.getLineWidth()) && (this._format.getLineWidth() > 0)) {}
          for (i = this._format.getLineWidth() / 2; i > 0; i--) {
            this._writer.write(32);
          }
        }
        this._thisIndent = this._nextIndent;
        this._spaces = 0;
        this._writer.write(this._line.toString());
        this._line = new StringBuffer(40);
      }
      catch (IOException localIOException)
      {
        if (this._exception == null) {
          this._exception = localIOException;
        }
      }
    }
  }
  
  public void flush()
  {
    if ((this._line.length() > 0) || (this._text.length() > 0)) {
      breakLine();
    }
    try
    {
      this._writer.flush();
    }
    catch (IOException localIOException)
    {
      if (this._exception == null) {
        this._exception = localIOException;
      }
    }
  }
  
  public void indent()
  {
    this._nextIndent += this._format.getIndent();
  }
  
  public void unindent()
  {
    this._nextIndent -= this._format.getIndent();
    if (this._nextIndent < 0) {
      this._nextIndent = 0;
    }
    if (this._line.length() + this._spaces + this._text.length() == 0) {
      this._thisIndent = this._nextIndent;
    }
  }
  
  public int getNextIndent()
  {
    return this._nextIndent;
  }
  
  public void setNextIndent(int paramInt)
  {
    this._nextIndent = paramInt;
  }
  
  public void setThisIndent(int paramInt)
  {
    this._thisIndent = paramInt;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serialize.IndentPrinter
 * JD-Core Version:    0.7.0.1
 */
package javax.xml.transform.stream;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.transform.Result;

public class StreamResult
  implements Result
{
  public static final String FEATURE = "http://javax.xml.transform.stream.StreamResult/feature";
  private String systemId;
  private OutputStream outputStream;
  private Writer writer;
  
  public StreamResult() {}
  
  public StreamResult(OutputStream paramOutputStream)
  {
    setOutputStream(paramOutputStream);
  }
  
  public StreamResult(Writer paramWriter)
  {
    setWriter(paramWriter);
  }
  
  public StreamResult(String paramString)
  {
    this.systemId = paramString;
  }
  
  public StreamResult(File paramFile)
  {
    setSystemId(paramFile);
  }
  
  public void setOutputStream(OutputStream paramOutputStream)
  {
    this.outputStream = paramOutputStream;
  }
  
  public OutputStream getOutputStream()
  {
    return this.outputStream;
  }
  
  public void setWriter(Writer paramWriter)
  {
    this.writer = paramWriter;
  }
  
  public Writer getWriter()
  {
    return this.writer;
  }
  
  public void setSystemId(String paramString)
  {
    this.systemId = paramString;
  }
  
  public void setSystemId(File paramFile)
  {
    try
    {
      Method localMethod1 = paramFile.getClass().getMethod("toURI", (Class[])null);
      Object localObject = localMethod1.invoke(paramFile, (Object[])null);
      Method localMethod2 = localObject.getClass().getMethod("toString", (Class[])null);
      this.systemId = ((String)localMethod2.invoke(localObject, (Object[])null));
    }
    catch (Exception localException)
    {
      try
      {
        this.systemId = paramFile.toURL().toString();
      }
      catch (MalformedURLException localMalformedURLException)
      {
        this.systemId = null;
        throw new RuntimeException("javax.xml.transform.stream.StreamResult#setSystemId(File f) with MalformedURLException: " + localMalformedURLException.toString());
      }
    }
  }
  
  public String getSystemId()
  {
    return this.systemId;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.transform.stream.StreamResult
 * JD-Core Version:    0.7.0.1
 */
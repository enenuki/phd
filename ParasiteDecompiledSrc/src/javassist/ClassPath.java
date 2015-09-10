package javassist;

import java.io.InputStream;
import java.net.URL;

public abstract interface ClassPath
{
  public abstract InputStream openClassfile(String paramString)
    throws NotFoundException;
  
  public abstract URL find(String paramString);
  
  public abstract void close();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.ClassPath
 * JD-Core Version:    0.7.0.1
 */
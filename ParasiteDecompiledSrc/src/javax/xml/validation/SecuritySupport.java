package javax.xml.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;

final class SecuritySupport
{
  static ClassLoader getContextClassLoader()
  {
    (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
    {
      public Object run()
      {
        ClassLoader localClassLoader = null;
        try
        {
          localClassLoader = Thread.currentThread().getContextClassLoader();
        }
        catch (SecurityException localSecurityException) {}
        return localClassLoader;
      }
    });
  }
  
  static String getSystemProperty(String paramString)
  {
    (String)AccessController.doPrivileged(new PrivilegedAction()
    {
      private final String val$propName;
      
      public Object run()
      {
        return System.getProperty(this.val$propName);
      }
    });
  }
  
  static FileInputStream getFileInputStream(File paramFile)
    throws FileNotFoundException
  {
    try
    {
      (FileInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
      {
        private final File val$file;
        
        public Object run()
          throws FileNotFoundException
        {
          return new FileInputStream(this.val$file);
        }
      });
    }
    catch (PrivilegedActionException localPrivilegedActionException)
    {
      throw ((FileNotFoundException)localPrivilegedActionException.getException());
    }
  }
  
  static InputStream getURLInputStream(URL paramURL)
    throws IOException
  {
    try
    {
      (InputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
      {
        private final URL val$url;
        
        public Object run()
          throws IOException
        {
          return this.val$url.openStream();
        }
      });
    }
    catch (PrivilegedActionException localPrivilegedActionException)
    {
      throw ((IOException)localPrivilegedActionException.getException());
    }
  }
  
  static URL getResourceAsURL(ClassLoader paramClassLoader, String paramString)
  {
    (URL)AccessController.doPrivileged(new PrivilegedAction()
    {
      private final ClassLoader val$cl;
      private final String val$name;
      
      public Object run()
      {
        URL localURL;
        if (this.val$cl == null) {
          localURL = ClassLoader.getSystemResource(this.val$name);
        } else {
          localURL = ClassLoader.getSystemResource(this.val$name);
        }
        return localURL;
      }
    });
  }
  
  static Enumeration getResources(ClassLoader paramClassLoader, String paramString)
    throws IOException
  {
    try
    {
      (Enumeration)AccessController.doPrivileged(new PrivilegedExceptionAction()
      {
        private final ClassLoader val$cl;
        private final String val$name;
        
        public Object run()
          throws IOException
        {
          Enumeration localEnumeration;
          if (this.val$cl == null) {
            localEnumeration = ClassLoader.getSystemResources(this.val$name);
          } else {
            localEnumeration = ClassLoader.getSystemResources(this.val$name);
          }
          return localEnumeration;
        }
      });
    }
    catch (PrivilegedActionException localPrivilegedActionException)
    {
      throw ((IOException)localPrivilegedActionException.getException());
    }
  }
  
  static InputStream getResourceAsStream(ClassLoader paramClassLoader, String paramString)
  {
    (InputStream)AccessController.doPrivileged(new PrivilegedAction()
    {
      private final ClassLoader val$cl;
      private final String val$name;
      
      public Object run()
      {
        InputStream localInputStream;
        if (this.val$cl == null) {
          localInputStream = ClassLoader.getSystemResourceAsStream(this.val$name);
        } else {
          localInputStream = this.val$cl.getResourceAsStream(this.val$name);
        }
        return localInputStream;
      }
    });
  }
  
  static boolean doesFileExist(File paramFile)
  {
    ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
    {
      private final File val$f;
      
      public Object run()
      {
        return this.val$f.exists() ? Boolean.TRUE : Boolean.FALSE;
      }
    })).booleanValue();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.xml.validation.SecuritySupport
 * JD-Core Version:    0.7.0.1
 */
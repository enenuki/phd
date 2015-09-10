package org.apache.xerces.parsers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarLoader;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;

public class XMLGrammarPreparser
{
  private static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
  protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
  protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
  protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
  protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
  private static final Hashtable KNOWN_LOADERS = new Hashtable();
  private static final String[] RECOGNIZED_PROPERTIES = { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/grammar-pool" };
  protected SymbolTable fSymbolTable;
  protected XMLErrorReporter fErrorReporter;
  protected XMLEntityResolver fEntityResolver;
  protected XMLGrammarPool fGrammarPool;
  protected Locale fLocale;
  private Hashtable fLoaders;
  private int fModCount = 1;
  
  public XMLGrammarPreparser()
  {
    this(new SymbolTable());
  }
  
  public XMLGrammarPreparser(SymbolTable paramSymbolTable)
  {
    this.fSymbolTable = paramSymbolTable;
    this.fLoaders = new Hashtable();
    setLocale(Locale.getDefault());
    this.fErrorReporter = new XMLErrorReporter();
    this.fErrorReporter.setLocale(this.fLocale);
    this.fEntityResolver = new XMLEntityManager();
  }
  
  public boolean registerPreparser(String paramString, XMLGrammarLoader paramXMLGrammarLoader)
  {
    if (paramXMLGrammarLoader == null)
    {
      if (KNOWN_LOADERS.containsKey(paramString))
      {
        String str = (String)KNOWN_LOADERS.get(paramString);
        try
        {
          ClassLoader localClassLoader = ObjectFactory.findClassLoader();
          XMLGrammarLoader localXMLGrammarLoader = (XMLGrammarLoader)ObjectFactory.newInstance(str, localClassLoader, true);
          this.fLoaders.put(paramString, new XMLGrammarLoaderContainer(localXMLGrammarLoader));
        }
        catch (Exception localException)
        {
          return false;
        }
        return true;
      }
      return false;
    }
    this.fLoaders.put(paramString, new XMLGrammarLoaderContainer(paramXMLGrammarLoader));
    return true;
  }
  
  public Grammar preparseGrammar(String paramString, XMLInputSource paramXMLInputSource)
    throws XNIException, IOException
  {
    if (this.fLoaders.containsKey(paramString))
    {
      XMLGrammarLoaderContainer localXMLGrammarLoaderContainer = (XMLGrammarLoaderContainer)this.fLoaders.get(paramString);
      XMLGrammarLoader localXMLGrammarLoader = localXMLGrammarLoaderContainer.loader;
      if (localXMLGrammarLoaderContainer.modCount != this.fModCount)
      {
        localXMLGrammarLoader.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
        localXMLGrammarLoader.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fEntityResolver);
        localXMLGrammarLoader.setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
        if (this.fGrammarPool != null) {
          try
          {
            localXMLGrammarLoader.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
          }
          catch (Exception localException) {}
        }
        localXMLGrammarLoaderContainer.modCount = this.fModCount;
      }
      return localXMLGrammarLoader.loadGrammar(paramXMLInputSource);
    }
    return null;
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.fLocale = paramLocale;
  }
  
  public Locale getLocale()
  {
    return this.fLocale;
  }
  
  public void setErrorHandler(XMLErrorHandler paramXMLErrorHandler)
  {
    this.fErrorReporter.setProperty("http://apache.org/xml/properties/internal/error-handler", paramXMLErrorHandler);
  }
  
  public XMLErrorHandler getErrorHandler()
  {
    return this.fErrorReporter.getErrorHandler();
  }
  
  public void setEntityResolver(XMLEntityResolver paramXMLEntityResolver)
  {
    if (this.fEntityResolver != paramXMLEntityResolver)
    {
      if (++this.fModCount < 0) {
        clearModCounts();
      }
      this.fEntityResolver = paramXMLEntityResolver;
    }
  }
  
  public XMLEntityResolver getEntityResolver()
  {
    return this.fEntityResolver;
  }
  
  public void setGrammarPool(XMLGrammarPool paramXMLGrammarPool)
  {
    if (this.fGrammarPool != paramXMLGrammarPool)
    {
      if (++this.fModCount < 0) {
        clearModCounts();
      }
      this.fGrammarPool = paramXMLGrammarPool;
    }
  }
  
  public XMLGrammarPool getGrammarPool()
  {
    return this.fGrammarPool;
  }
  
  public XMLGrammarLoader getLoader(String paramString)
  {
    XMLGrammarLoaderContainer localXMLGrammarLoaderContainer = (XMLGrammarLoaderContainer)this.fLoaders.get(paramString);
    return localXMLGrammarLoaderContainer != null ? localXMLGrammarLoaderContainer.loader : null;
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
  {
    Enumeration localEnumeration = this.fLoaders.elements();
    while (localEnumeration.hasMoreElements())
    {
      XMLGrammarLoader localXMLGrammarLoader = ((XMLGrammarLoaderContainer)localEnumeration.nextElement()).loader;
      try
      {
        localXMLGrammarLoader.setFeature(paramString, paramBoolean);
      }
      catch (Exception localException) {}
    }
    if (paramString.equals("http://apache.org/xml/features/continue-after-fatal-error")) {
      this.fErrorReporter.setFeature("http://apache.org/xml/features/continue-after-fatal-error", paramBoolean);
    }
  }
  
  public void setProperty(String paramString, Object paramObject)
  {
    Enumeration localEnumeration = this.fLoaders.elements();
    while (localEnumeration.hasMoreElements())
    {
      XMLGrammarLoader localXMLGrammarLoader = ((XMLGrammarLoaderContainer)localEnumeration.nextElement()).loader;
      try
      {
        localXMLGrammarLoader.setProperty(paramString, paramObject);
      }
      catch (Exception localException) {}
    }
  }
  
  public boolean getFeature(String paramString1, String paramString2)
  {
    XMLGrammarLoader localXMLGrammarLoader = ((XMLGrammarLoaderContainer)this.fLoaders.get(paramString1)).loader;
    return localXMLGrammarLoader.getFeature(paramString2);
  }
  
  public Object getProperty(String paramString1, String paramString2)
  {
    XMLGrammarLoader localXMLGrammarLoader = ((XMLGrammarLoaderContainer)this.fLoaders.get(paramString1)).loader;
    return localXMLGrammarLoader.getProperty(paramString2);
  }
  
  private void clearModCounts()
  {
    Enumeration localEnumeration = this.fLoaders.elements();
    while (localEnumeration.hasMoreElements())
    {
      XMLGrammarLoaderContainer localXMLGrammarLoaderContainer = (XMLGrammarLoaderContainer)localEnumeration.nextElement();
      localXMLGrammarLoaderContainer.modCount = 0;
    }
    this.fModCount = 1;
  }
  
  static
  {
    KNOWN_LOADERS.put("http://www.w3.org/2001/XMLSchema", "org.apache.xerces.impl.xs.XMLSchemaLoader");
    KNOWN_LOADERS.put("http://www.w3.org/TR/REC-xml", "org.apache.xerces.impl.dtd.XMLDTDLoader");
  }
  
  static class XMLGrammarLoaderContainer
  {
    public final XMLGrammarLoader loader;
    public int modCount = 0;
    
    public XMLGrammarLoaderContainer(XMLGrammarLoader paramXMLGrammarLoader)
    {
      this.loader = paramXMLGrammarLoader;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.parsers.XMLGrammarPreparser
 * JD-Core Version:    0.7.0.1
 */
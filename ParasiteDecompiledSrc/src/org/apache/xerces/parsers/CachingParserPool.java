package org.apache.xerces.parsers;

import org.apache.xerces.util.ShadowedSymbolTable;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.SynchronizedSymbolTable;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;

public class CachingParserPool
{
  public static final boolean DEFAULT_SHADOW_SYMBOL_TABLE = false;
  public static final boolean DEFAULT_SHADOW_GRAMMAR_POOL = false;
  protected SymbolTable fSynchronizedSymbolTable;
  protected XMLGrammarPool fSynchronizedGrammarPool;
  protected boolean fShadowSymbolTable = false;
  protected boolean fShadowGrammarPool = false;
  
  public CachingParserPool()
  {
    this(new SymbolTable(), new XMLGrammarPoolImpl());
  }
  
  public CachingParserPool(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool)
  {
    this.fSynchronizedSymbolTable = new SynchronizedSymbolTable(paramSymbolTable);
    this.fSynchronizedGrammarPool = new SynchronizedGrammarPool(paramXMLGrammarPool);
  }
  
  public SymbolTable getSymbolTable()
  {
    return this.fSynchronizedSymbolTable;
  }
  
  public XMLGrammarPool getXMLGrammarPool()
  {
    return this.fSynchronizedGrammarPool;
  }
  
  public void setShadowSymbolTable(boolean paramBoolean)
  {
    this.fShadowSymbolTable = paramBoolean;
  }
  
  public DOMParser createDOMParser()
  {
    SymbolTable localSymbolTable = this.fShadowSymbolTable ? new ShadowedSymbolTable(this.fSynchronizedSymbolTable) : this.fSynchronizedSymbolTable;
    XMLGrammarPool localXMLGrammarPool = this.fShadowGrammarPool ? new ShadowedGrammarPool(this.fSynchronizedGrammarPool) : this.fSynchronizedGrammarPool;
    return new DOMParser(localSymbolTable, localXMLGrammarPool);
  }
  
  public SAXParser createSAXParser()
  {
    SymbolTable localSymbolTable = this.fShadowSymbolTable ? new ShadowedSymbolTable(this.fSynchronizedSymbolTable) : this.fSynchronizedSymbolTable;
    XMLGrammarPool localXMLGrammarPool = this.fShadowGrammarPool ? new ShadowedGrammarPool(this.fSynchronizedGrammarPool) : this.fSynchronizedGrammarPool;
    return new SAXParser(localSymbolTable, localXMLGrammarPool);
  }
  
  public static final class ShadowedGrammarPool
    extends XMLGrammarPoolImpl
  {
    private XMLGrammarPool fGrammarPool;
    
    public ShadowedGrammarPool(XMLGrammarPool paramXMLGrammarPool)
    {
      this.fGrammarPool = paramXMLGrammarPool;
    }
    
    public Grammar[] retrieveInitialGrammarSet(String paramString)
    {
      Grammar[] arrayOfGrammar = super.retrieveInitialGrammarSet(paramString);
      if (arrayOfGrammar != null) {
        return arrayOfGrammar;
      }
      return this.fGrammarPool.retrieveInitialGrammarSet(paramString);
    }
    
    public Grammar retrieveGrammar(XMLGrammarDescription paramXMLGrammarDescription)
    {
      Grammar localGrammar = super.retrieveGrammar(paramXMLGrammarDescription);
      if (localGrammar != null) {
        return localGrammar;
      }
      return this.fGrammarPool.retrieveGrammar(paramXMLGrammarDescription);
    }
    
    public void cacheGrammars(String paramString, Grammar[] paramArrayOfGrammar)
    {
      super.cacheGrammars(paramString, paramArrayOfGrammar);
      this.fGrammarPool.cacheGrammars(paramString, paramArrayOfGrammar);
    }
    
    public Grammar getGrammar(XMLGrammarDescription paramXMLGrammarDescription)
    {
      if (super.containsGrammar(paramXMLGrammarDescription)) {
        return super.getGrammar(paramXMLGrammarDescription);
      }
      return null;
    }
    
    public boolean containsGrammar(XMLGrammarDescription paramXMLGrammarDescription)
    {
      return super.containsGrammar(paramXMLGrammarDescription);
    }
  }
  
  public static final class SynchronizedGrammarPool
    implements XMLGrammarPool
  {
    private XMLGrammarPool fGrammarPool;
    
    public SynchronizedGrammarPool(XMLGrammarPool paramXMLGrammarPool)
    {
      this.fGrammarPool = paramXMLGrammarPool;
    }
    
    public Grammar[] retrieveInitialGrammarSet(String paramString)
    {
      synchronized (this.fGrammarPool)
      {
        Grammar[] arrayOfGrammar = this.fGrammarPool.retrieveInitialGrammarSet(paramString);
        return arrayOfGrammar;
      }
    }
    
    public Grammar retrieveGrammar(XMLGrammarDescription paramXMLGrammarDescription)
    {
      synchronized (this.fGrammarPool)
      {
        Grammar localGrammar = this.fGrammarPool.retrieveGrammar(paramXMLGrammarDescription);
        return localGrammar;
      }
    }
    
    public void cacheGrammars(String paramString, Grammar[] paramArrayOfGrammar)
    {
      synchronized (this.fGrammarPool)
      {
        this.fGrammarPool.cacheGrammars(paramString, paramArrayOfGrammar);
      }
    }
    
    public void lockPool()
    {
      synchronized (this.fGrammarPool)
      {
        this.fGrammarPool.lockPool();
      }
    }
    
    public void clear()
    {
      synchronized (this.fGrammarPool)
      {
        this.fGrammarPool.clear();
      }
    }
    
    public void unlockPool()
    {
      synchronized (this.fGrammarPool)
      {
        this.fGrammarPool.unlockPool();
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.parsers.CachingParserPool
 * JD-Core Version:    0.7.0.1
 */
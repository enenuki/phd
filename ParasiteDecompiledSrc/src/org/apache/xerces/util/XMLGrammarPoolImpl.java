package org.apache.xerces.util;

import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;

public class XMLGrammarPoolImpl
  implements XMLGrammarPool
{
  protected static final int TABLE_SIZE = 11;
  protected Entry[] fGrammars = null;
  protected boolean fPoolIsLocked;
  protected int fGrammarCount = 0;
  private static final boolean DEBUG = false;
  
  public XMLGrammarPoolImpl()
  {
    this.fGrammars = new Entry[11];
    this.fPoolIsLocked = false;
  }
  
  public XMLGrammarPoolImpl(int paramInt)
  {
    this.fGrammars = new Entry[paramInt];
    this.fPoolIsLocked = false;
  }
  
  public Grammar[] retrieveInitialGrammarSet(String paramString)
  {
    synchronized (this.fGrammars)
    {
      int i = this.fGrammars.length;
      Grammar[] arrayOfGrammar = new Grammar[this.fGrammarCount];
      int j = 0;
      for (int k = 0; k < i; k++) {
        for (localObject1 = this.fGrammars[k]; localObject1 != null; localObject1 = ((Entry)localObject1).next) {
          if (((Entry)localObject1).desc.getGrammarType().equals(paramString)) {
            arrayOfGrammar[(j++)] = ((Entry)localObject1).grammar;
          }
        }
      }
      Object localObject1 = new Grammar[j];
      System.arraycopy(arrayOfGrammar, 0, localObject1, 0, j);
      Object localObject2 = localObject1;
      return localObject2;
    }
  }
  
  public void cacheGrammars(String paramString, Grammar[] paramArrayOfGrammar)
  {
    if (!this.fPoolIsLocked) {
      for (int i = 0; i < paramArrayOfGrammar.length; i++) {
        putGrammar(paramArrayOfGrammar[i]);
      }
    }
  }
  
  public Grammar retrieveGrammar(XMLGrammarDescription paramXMLGrammarDescription)
  {
    return getGrammar(paramXMLGrammarDescription);
  }
  
  public void putGrammar(Grammar paramGrammar)
  {
    if (!this.fPoolIsLocked) {
      synchronized (this.fGrammars)
      {
        XMLGrammarDescription localXMLGrammarDescription = paramGrammar.getGrammarDescription();
        int i = hashCode(localXMLGrammarDescription);
        int j = (i & 0x7FFFFFFF) % this.fGrammars.length;
        for (Entry localEntry1 = this.fGrammars[j]; localEntry1 != null; localEntry1 = localEntry1.next) {
          if ((localEntry1.hash == i) && (equals(localEntry1.desc, localXMLGrammarDescription)))
          {
            localEntry1.grammar = paramGrammar;
            return;
          }
        }
        Entry localEntry2 = new Entry(i, localXMLGrammarDescription, paramGrammar, this.fGrammars[j]);
        this.fGrammars[j] = localEntry2;
        this.fGrammarCount += 1;
      }
    }
  }
  
  public Grammar getGrammar(XMLGrammarDescription paramXMLGrammarDescription)
  {
    synchronized (this.fGrammars)
    {
      int i = hashCode(paramXMLGrammarDescription);
      int j = (i & 0x7FFFFFFF) % this.fGrammars.length;
      for (Entry localEntry = this.fGrammars[j]; localEntry != null; localEntry = localEntry.next) {
        if ((localEntry.hash == i) && (equals(localEntry.desc, paramXMLGrammarDescription)))
        {
          localGrammar = localEntry.grammar;
          return localGrammar;
        }
      }
      Grammar localGrammar = null;
      return localGrammar;
    }
  }
  
  public Grammar removeGrammar(XMLGrammarDescription paramXMLGrammarDescription)
  {
    synchronized (this.fGrammars)
    {
      int i = hashCode(paramXMLGrammarDescription);
      int j = (i & 0x7FFFFFFF) % this.fGrammars.length;
      Entry localEntry1 = this.fGrammars[j];
      Entry localEntry2 = null;
      while (localEntry1 != null)
      {
        if ((localEntry1.hash == i) && (equals(localEntry1.desc, paramXMLGrammarDescription)))
        {
          if (localEntry2 != null) {
            localEntry2.next = localEntry1.next;
          } else {
            this.fGrammars[j] = localEntry1.next;
          }
          localGrammar1 = localEntry1.grammar;
          localEntry1.grammar = null;
          this.fGrammarCount -= 1;
          Grammar localGrammar2 = localGrammar1;
          return localGrammar2;
        }
        localEntry2 = localEntry1;
        localEntry1 = localEntry1.next;
      }
      Grammar localGrammar1 = null;
      return localGrammar1;
    }
  }
  
  public boolean containsGrammar(XMLGrammarDescription paramXMLGrammarDescription)
  {
    synchronized (this.fGrammars)
    {
      int i = hashCode(paramXMLGrammarDescription);
      int j = (i & 0x7FFFFFFF) % this.fGrammars.length;
      for (Entry localEntry = this.fGrammars[j]; localEntry != null; localEntry = localEntry.next) {
        if ((localEntry.hash == i) && (equals(localEntry.desc, paramXMLGrammarDescription)))
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
      return bool;
    }
  }
  
  public void lockPool()
  {
    this.fPoolIsLocked = true;
  }
  
  public void unlockPool()
  {
    this.fPoolIsLocked = false;
  }
  
  public void clear()
  {
    for (int i = 0; i < this.fGrammars.length; i++) {
      if (this.fGrammars[i] != null)
      {
        this.fGrammars[i].clear();
        this.fGrammars[i] = null;
      }
    }
    this.fGrammarCount = 0;
  }
  
  public boolean equals(XMLGrammarDescription paramXMLGrammarDescription1, XMLGrammarDescription paramXMLGrammarDescription2)
  {
    return paramXMLGrammarDescription1.equals(paramXMLGrammarDescription2);
  }
  
  public int hashCode(XMLGrammarDescription paramXMLGrammarDescription)
  {
    return paramXMLGrammarDescription.hashCode();
  }
  
  protected static final class Entry
  {
    public int hash;
    public XMLGrammarDescription desc;
    public Grammar grammar;
    public Entry next;
    
    protected Entry(int paramInt, XMLGrammarDescription paramXMLGrammarDescription, Grammar paramGrammar, Entry paramEntry)
    {
      this.hash = paramInt;
      this.desc = paramXMLGrammarDescription;
      this.grammar = paramGrammar;
      this.next = paramEntry;
    }
    
    protected void clear()
    {
      this.desc = null;
      this.grammar = null;
      if (this.next != null)
      {
        this.next.clear();
        this.next = null;
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.XMLGrammarPoolImpl
 * JD-Core Version:    0.7.0.1
 */
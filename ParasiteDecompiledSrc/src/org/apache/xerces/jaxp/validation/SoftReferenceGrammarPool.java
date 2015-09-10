package org.apache.xerces.jaxp.validation;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.grammars.XMLSchemaDescription;

final class SoftReferenceGrammarPool
  implements XMLGrammarPool
{
  protected static final int TABLE_SIZE = 11;
  protected static final Grammar[] ZERO_LENGTH_GRAMMAR_ARRAY = new Grammar[0];
  protected Entry[] fGrammars = null;
  protected boolean fPoolIsLocked;
  protected int fGrammarCount = 0;
  protected final ReferenceQueue fReferenceQueue = new ReferenceQueue();
  
  public SoftReferenceGrammarPool()
  {
    this.fGrammars = new Entry[11];
    this.fPoolIsLocked = false;
  }
  
  public SoftReferenceGrammarPool(int paramInt)
  {
    this.fGrammars = new Entry[paramInt];
    this.fPoolIsLocked = false;
  }
  
  public Grammar[] retrieveInitialGrammarSet(String paramString)
  {
    synchronized (this.fGrammars)
    {
      clean();
      Grammar[] arrayOfGrammar = ZERO_LENGTH_GRAMMAR_ARRAY;
      return arrayOfGrammar;
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
        clean();
        XMLGrammarDescription localXMLGrammarDescription = paramGrammar.getGrammarDescription();
        int i = hashCode(localXMLGrammarDescription);
        int j = (i & 0x7FFFFFFF) % this.fGrammars.length;
        for (Entry localEntry1 = this.fGrammars[j]; localEntry1 != null; localEntry1 = localEntry1.next) {
          if ((localEntry1.hash == i) && (equals(localEntry1.desc, localXMLGrammarDescription)))
          {
            if (localEntry1.grammar.get() != paramGrammar) {
              localEntry1.grammar = new SoftGrammarReference(localEntry1, paramGrammar, this.fReferenceQueue);
            }
            return;
          }
        }
        Entry localEntry2 = new Entry(i, j, localXMLGrammarDescription, paramGrammar, this.fGrammars[j], this.fReferenceQueue);
        this.fGrammars[j] = localEntry2;
        this.fGrammarCount += 1;
      }
    }
  }
  
  public Grammar getGrammar(XMLGrammarDescription paramXMLGrammarDescription)
  {
    synchronized (this.fGrammars)
    {
      clean();
      int i = hashCode(paramXMLGrammarDescription);
      int j = (i & 0x7FFFFFFF) % this.fGrammars.length;
      for (Entry localEntry = this.fGrammars[j]; localEntry != null; localEntry = localEntry.next)
      {
        localGrammar1 = (Grammar)localEntry.grammar.get();
        if (localGrammar1 == null)
        {
          removeEntry(localEntry);
        }
        else if ((localEntry.hash == i) && (equals(localEntry.desc, paramXMLGrammarDescription)))
        {
          Grammar localGrammar2 = localGrammar1;
          return localGrammar2;
        }
      }
      Grammar localGrammar1 = null;
      return localGrammar1;
    }
  }
  
  public Grammar removeGrammar(XMLGrammarDescription paramXMLGrammarDescription)
  {
    synchronized (this.fGrammars)
    {
      clean();
      int i = hashCode(paramXMLGrammarDescription);
      int j = (i & 0x7FFFFFFF) % this.fGrammars.length;
      for (Entry localEntry = this.fGrammars[j]; localEntry != null; localEntry = localEntry.next) {
        if ((localEntry.hash == i) && (equals(localEntry.desc, paramXMLGrammarDescription)))
        {
          localGrammar = removeEntry(localEntry);
          return localGrammar;
        }
      }
      Grammar localGrammar = null;
      return localGrammar;
    }
  }
  
  public boolean containsGrammar(XMLGrammarDescription paramXMLGrammarDescription)
  {
    synchronized (this.fGrammars)
    {
      clean();
      int i = hashCode(paramXMLGrammarDescription);
      int j = (i & 0x7FFFFFFF) % this.fGrammars.length;
      for (Entry localEntry = this.fGrammars[j]; localEntry != null; localEntry = localEntry.next)
      {
        Grammar localGrammar = (Grammar)localEntry.grammar.get();
        if (localGrammar == null)
        {
          removeEntry(localEntry);
        }
        else if ((localEntry.hash == i) && (equals(localEntry.desc, paramXMLGrammarDescription)))
        {
          boolean bool2 = true;
          return bool2;
        }
      }
      boolean bool1 = false;
      return bool1;
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
    if ((paramXMLGrammarDescription1 instanceof XMLSchemaDescription))
    {
      if (!(paramXMLGrammarDescription2 instanceof XMLSchemaDescription)) {
        return false;
      }
      XMLSchemaDescription localXMLSchemaDescription1 = (XMLSchemaDescription)paramXMLGrammarDescription1;
      XMLSchemaDescription localXMLSchemaDescription2 = (XMLSchemaDescription)paramXMLGrammarDescription2;
      String str1 = localXMLSchemaDescription1.getTargetNamespace();
      if (str1 != null)
      {
        if (!str1.equals(localXMLSchemaDescription2.getTargetNamespace())) {
          return false;
        }
      }
      else if (localXMLSchemaDescription2.getTargetNamespace() != null) {
        return false;
      }
      String str2 = localXMLSchemaDescription1.getExpandedSystemId();
      if (str2 != null)
      {
        if (!str2.equals(localXMLSchemaDescription2.getExpandedSystemId())) {
          return false;
        }
      }
      else if (localXMLSchemaDescription2.getExpandedSystemId() != null) {
        return false;
      }
      return true;
    }
    return paramXMLGrammarDescription1.equals(paramXMLGrammarDescription2);
  }
  
  public int hashCode(XMLGrammarDescription paramXMLGrammarDescription)
  {
    if ((paramXMLGrammarDescription instanceof XMLSchemaDescription))
    {
      XMLSchemaDescription localXMLSchemaDescription = (XMLSchemaDescription)paramXMLGrammarDescription;
      String str1 = localXMLSchemaDescription.getTargetNamespace();
      String str2 = localXMLSchemaDescription.getExpandedSystemId();
      int i = str1 != null ? str1.hashCode() : 0;
      i ^= (str2 != null ? str2.hashCode() : 0);
      return i;
    }
    return paramXMLGrammarDescription.hashCode();
  }
  
  private Grammar removeEntry(Entry paramEntry)
  {
    if (paramEntry.prev != null) {
      paramEntry.prev.next = paramEntry.next;
    } else {
      this.fGrammars[paramEntry.bucket] = paramEntry.next;
    }
    if (paramEntry.next != null) {
      paramEntry.next.prev = paramEntry.prev;
    }
    this.fGrammarCount -= 1;
    paramEntry.grammar.entry = null;
    return (Grammar)paramEntry.grammar.get();
  }
  
  private void clean()
  {
    for (Reference localReference = this.fReferenceQueue.poll(); localReference != null; localReference = this.fReferenceQueue.poll())
    {
      Entry localEntry = ((SoftGrammarReference)localReference).entry;
      if (localEntry != null) {
        removeEntry(localEntry);
      }
    }
  }
  
  static final class SoftGrammarReference
    extends SoftReference
  {
    public SoftReferenceGrammarPool.Entry entry;
    
    protected SoftGrammarReference(SoftReferenceGrammarPool.Entry paramEntry, Grammar paramGrammar, ReferenceQueue paramReferenceQueue)
    {
      super(paramReferenceQueue);
      this.entry = paramEntry;
    }
  }
  
  static final class Entry
  {
    public int hash;
    public int bucket;
    public Entry prev;
    public Entry next;
    public XMLGrammarDescription desc;
    public SoftReferenceGrammarPool.SoftGrammarReference grammar;
    
    protected Entry(int paramInt1, int paramInt2, XMLGrammarDescription paramXMLGrammarDescription, Grammar paramGrammar, Entry paramEntry, ReferenceQueue paramReferenceQueue)
    {
      this.hash = paramInt1;
      this.bucket = paramInt2;
      this.prev = null;
      this.next = paramEntry;
      if (paramEntry != null) {
        paramEntry.prev = this;
      }
      this.desc = paramXMLGrammarDescription;
      this.grammar = new SoftReferenceGrammarPool.SoftGrammarReference(this, paramGrammar, paramReferenceQueue);
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
 * Qualified Name:     org.apache.xerces.jaxp.validation.SoftReferenceGrammarPool
 * JD-Core Version:    0.7.0.1
 */
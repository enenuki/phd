package org.apache.xerces.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import org.apache.xerces.xni.Augmentations;

public class AugmentationsImpl
  implements Augmentations
{
  private AugmentationsItemsContainer fAugmentationsContainer = new SmallContainer();
  
  public Object putItem(String paramString, Object paramObject)
  {
    Object localObject = this.fAugmentationsContainer.putItem(paramString, paramObject);
    if ((localObject == null) && (this.fAugmentationsContainer.isFull())) {
      this.fAugmentationsContainer = this.fAugmentationsContainer.expand();
    }
    return localObject;
  }
  
  public Object getItem(String paramString)
  {
    return this.fAugmentationsContainer.getItem(paramString);
  }
  
  public Object removeItem(String paramString)
  {
    return this.fAugmentationsContainer.removeItem(paramString);
  }
  
  public Enumeration keys()
  {
    return this.fAugmentationsContainer.keys();
  }
  
  public void removeAllItems()
  {
    this.fAugmentationsContainer.clear();
  }
  
  public String toString()
  {
    return this.fAugmentationsContainer.toString();
  }
  
  class LargeContainer
    extends AugmentationsImpl.AugmentationsItemsContainer
  {
    final Hashtable fAugmentations = new Hashtable();
    
    LargeContainer()
    {
      super();
    }
    
    public Object getItem(Object paramObject)
    {
      return this.fAugmentations.get(paramObject);
    }
    
    public Object putItem(Object paramObject1, Object paramObject2)
    {
      return this.fAugmentations.put(paramObject1, paramObject2);
    }
    
    public Object removeItem(Object paramObject)
    {
      return this.fAugmentations.remove(paramObject);
    }
    
    public Enumeration keys()
    {
      return this.fAugmentations.keys();
    }
    
    public void clear()
    {
      this.fAugmentations.clear();
    }
    
    public boolean isFull()
    {
      return false;
    }
    
    public AugmentationsImpl.AugmentationsItemsContainer expand()
    {
      return this;
    }
    
    public String toString()
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("LargeContainer");
      Enumeration localEnumeration = this.fAugmentations.keys();
      while (localEnumeration.hasMoreElements())
      {
        Object localObject = localEnumeration.nextElement();
        localStringBuffer.append("\nkey == ");
        localStringBuffer.append(localObject);
        localStringBuffer.append("; value == ");
        localStringBuffer.append(this.fAugmentations.get(localObject));
      }
      return localStringBuffer.toString();
    }
  }
  
  class SmallContainer
    extends AugmentationsImpl.AugmentationsItemsContainer
  {
    static final int SIZE_LIMIT = 10;
    final Object[] fAugmentations = new Object[20];
    int fNumEntries = 0;
    
    SmallContainer()
    {
      super();
    }
    
    public Enumeration keys()
    {
      return new SmallContainerKeyEnumeration();
    }
    
    public Object getItem(Object paramObject)
    {
      int i = 0;
      while (i < this.fNumEntries * 2)
      {
        if (this.fAugmentations[i].equals(paramObject)) {
          return this.fAugmentations[(i + 1)];
        }
        i += 2;
      }
      return null;
    }
    
    public Object putItem(Object paramObject1, Object paramObject2)
    {
      int i = 0;
      while (i < this.fNumEntries * 2)
      {
        if (this.fAugmentations[i].equals(paramObject1))
        {
          Object localObject = this.fAugmentations[(i + 1)];
          this.fAugmentations[(i + 1)] = paramObject2;
          return localObject;
        }
        i += 2;
      }
      this.fAugmentations[(this.fNumEntries * 2)] = paramObject1;
      this.fAugmentations[(this.fNumEntries * 2 + 1)] = paramObject2;
      this.fNumEntries += 1;
      return null;
    }
    
    public Object removeItem(Object paramObject)
    {
      int i = 0;
      while (i < this.fNumEntries * 2)
      {
        if (this.fAugmentations[i].equals(paramObject))
        {
          Object localObject = this.fAugmentations[(i + 1)];
          int j = i;
          while (j < this.fNumEntries * 2 - 2)
          {
            this.fAugmentations[j] = this.fAugmentations[(j + 2)];
            this.fAugmentations[(j + 1)] = this.fAugmentations[(j + 3)];
            j += 2;
          }
          this.fAugmentations[(this.fNumEntries * 2 - 2)] = null;
          this.fAugmentations[(this.fNumEntries * 2 - 1)] = null;
          this.fNumEntries -= 1;
          return localObject;
        }
        i += 2;
      }
      return null;
    }
    
    public void clear()
    {
      int i = 0;
      while (i < this.fNumEntries * 2)
      {
        this.fAugmentations[i] = null;
        this.fAugmentations[(i + 1)] = null;
        i += 2;
      }
      this.fNumEntries = 0;
    }
    
    public boolean isFull()
    {
      return this.fNumEntries == 10;
    }
    
    public AugmentationsImpl.AugmentationsItemsContainer expand()
    {
      AugmentationsImpl.LargeContainer localLargeContainer = new AugmentationsImpl.LargeContainer(AugmentationsImpl.this);
      int i = 0;
      while (i < this.fNumEntries * 2)
      {
        localLargeContainer.putItem(this.fAugmentations[i], this.fAugmentations[(i + 1)]);
        i += 2;
      }
      return localLargeContainer;
    }
    
    public String toString()
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("SmallContainer - fNumEntries == ").append(this.fNumEntries);
      int i = 0;
      while (i < 20)
      {
        localStringBuffer.append("\nfAugmentations[");
        localStringBuffer.append(i);
        localStringBuffer.append("] == ");
        localStringBuffer.append(this.fAugmentations[i]);
        localStringBuffer.append("; fAugmentations[");
        localStringBuffer.append(i + 1);
        localStringBuffer.append("] == ");
        localStringBuffer.append(this.fAugmentations[(i + 1)]);
        i += 2;
      }
      return localStringBuffer.toString();
    }
    
    class SmallContainerKeyEnumeration
      implements Enumeration
    {
      Object[] enumArray = new Object[AugmentationsImpl.SmallContainer.this.fNumEntries];
      int next = 0;
      
      SmallContainerKeyEnumeration()
      {
        for (int i = 0; i < AugmentationsImpl.SmallContainer.this.fNumEntries; i++) {
          this.enumArray[i] = AugmentationsImpl.SmallContainer.this.fAugmentations[(i * 2)];
        }
      }
      
      public boolean hasMoreElements()
      {
        return this.next < this.enumArray.length;
      }
      
      public Object nextElement()
      {
        if (this.next >= this.enumArray.length) {
          throw new NoSuchElementException();
        }
        Object localObject = this.enumArray[this.next];
        this.enumArray[this.next] = null;
        this.next += 1;
        return localObject;
      }
    }
  }
  
  abstract class AugmentationsItemsContainer
  {
    AugmentationsItemsContainer() {}
    
    public abstract Object putItem(Object paramObject1, Object paramObject2);
    
    public abstract Object getItem(Object paramObject);
    
    public abstract Object removeItem(Object paramObject);
    
    public abstract Enumeration keys();
    
    public abstract void clear();
    
    public abstract boolean isFull();
    
    public abstract AugmentationsItemsContainer expand();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.AugmentationsImpl
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.util;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;

public class XMLAttributesImpl
  implements XMLAttributes
{
  protected static final int TABLE_SIZE = 101;
  protected static final int SIZE_LIMIT = 20;
  protected boolean fNamespaces = true;
  protected int fLargeCount = 1;
  protected int fLength;
  protected Attribute[] fAttributes = new Attribute[4];
  protected Attribute[] fAttributeTableView;
  protected int[] fAttributeTableViewChainState;
  protected int fTableViewBuckets;
  protected boolean fIsTableViewConsistent;
  
  public XMLAttributesImpl()
  {
    this(101);
  }
  
  public XMLAttributesImpl(int paramInt)
  {
    this.fTableViewBuckets = paramInt;
    for (int i = 0; i < this.fAttributes.length; i++) {
      this.fAttributes[i] = new Attribute();
    }
  }
  
  public void setNamespaces(boolean paramBoolean)
  {
    this.fNamespaces = paramBoolean;
  }
  
  public int addAttribute(QName paramQName, String paramString1, String paramString2)
  {
    int i;
    if (this.fLength < 20)
    {
      i = (paramQName.uri != null) && (!paramQName.uri.equals("")) ? getIndexFast(paramQName.uri, paramQName.localpart) : getIndexFast(paramQName.rawname);
      if (i == -1)
      {
        i = this.fLength;
        if (this.fLength++ == this.fAttributes.length)
        {
          Attribute[] arrayOfAttribute1 = new Attribute[this.fAttributes.length + 4];
          System.arraycopy(this.fAttributes, 0, arrayOfAttribute1, 0, this.fAttributes.length);
          for (int k = this.fAttributes.length; k < arrayOfAttribute1.length; k++) {
            arrayOfAttribute1[k] = new Attribute();
          }
          this.fAttributes = arrayOfAttribute1;
        }
      }
    }
    else if ((paramQName.uri == null) || (paramQName.uri.length() == 0) || ((i = getIndexFast(paramQName.uri, paramQName.localpart)) == -1))
    {
      if ((!this.fIsTableViewConsistent) || (this.fLength == 20))
      {
        prepareAndPopulateTableView();
        this.fIsTableViewConsistent = true;
      }
      int j = getTableViewBucket(paramQName.rawname);
      Object localObject;
      if (this.fAttributeTableViewChainState[j] != this.fLargeCount)
      {
        i = this.fLength;
        if (this.fLength++ == this.fAttributes.length)
        {
          localObject = new Attribute[this.fAttributes.length << 1];
          System.arraycopy(this.fAttributes, 0, localObject, 0, this.fAttributes.length);
          for (int m = this.fAttributes.length; m < localObject.length; m++) {
            localObject[m] = new Attribute();
          }
          this.fAttributes = ((Attribute[])localObject);
        }
        this.fAttributeTableViewChainState[j] = this.fLargeCount;
        this.fAttributes[i].next = null;
        this.fAttributeTableView[j] = this.fAttributes[i];
      }
      else
      {
        for (localObject = this.fAttributeTableView[j]; localObject != null; localObject = ((Attribute)localObject).next) {
          if (((Attribute)localObject).name.rawname == paramQName.rawname) {
            break;
          }
        }
        if (localObject == null)
        {
          i = this.fLength;
          if (this.fLength++ == this.fAttributes.length)
          {
            Attribute[] arrayOfAttribute2 = new Attribute[this.fAttributes.length << 1];
            System.arraycopy(this.fAttributes, 0, arrayOfAttribute2, 0, this.fAttributes.length);
            for (int n = this.fAttributes.length; n < arrayOfAttribute2.length; n++) {
              arrayOfAttribute2[n] = new Attribute();
            }
            this.fAttributes = arrayOfAttribute2;
          }
          this.fAttributes[i].next = this.fAttributeTableView[j];
          this.fAttributeTableView[j] = this.fAttributes[i];
        }
        else
        {
          i = getIndexFast(paramQName.rawname);
        }
      }
    }
    Attribute localAttribute = this.fAttributes[i];
    localAttribute.name.setValues(paramQName);
    localAttribute.type = paramString1;
    localAttribute.value = paramString2;
    localAttribute.nonNormalizedValue = paramString2;
    localAttribute.specified = false;
    localAttribute.augs.removeAllItems();
    return i;
  }
  
  public void removeAllAttributes()
  {
    this.fLength = 0;
  }
  
  public void removeAttributeAt(int paramInt)
  {
    this.fIsTableViewConsistent = false;
    if (paramInt < this.fLength - 1)
    {
      Attribute localAttribute = this.fAttributes[paramInt];
      System.arraycopy(this.fAttributes, paramInt + 1, this.fAttributes, paramInt, this.fLength - paramInt - 1);
      this.fAttributes[(this.fLength - 1)] = localAttribute;
    }
    this.fLength -= 1;
  }
  
  public void setName(int paramInt, QName paramQName)
  {
    this.fAttributes[paramInt].name.setValues(paramQName);
  }
  
  public void getName(int paramInt, QName paramQName)
  {
    paramQName.setValues(this.fAttributes[paramInt].name);
  }
  
  public void setType(int paramInt, String paramString)
  {
    this.fAttributes[paramInt].type = paramString;
  }
  
  public void setValue(int paramInt, String paramString)
  {
    Attribute localAttribute = this.fAttributes[paramInt];
    localAttribute.value = paramString;
    localAttribute.nonNormalizedValue = paramString;
  }
  
  public void setNonNormalizedValue(int paramInt, String paramString)
  {
    if (paramString == null) {
      paramString = this.fAttributes[paramInt].value;
    }
    this.fAttributes[paramInt].nonNormalizedValue = paramString;
  }
  
  public String getNonNormalizedValue(int paramInt)
  {
    String str = this.fAttributes[paramInt].nonNormalizedValue;
    return str;
  }
  
  public void setSpecified(int paramInt, boolean paramBoolean)
  {
    this.fAttributes[paramInt].specified = paramBoolean;
  }
  
  public boolean isSpecified(int paramInt)
  {
    return this.fAttributes[paramInt].specified;
  }
  
  public int getLength()
  {
    return this.fLength;
  }
  
  public String getType(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    return getReportableType(this.fAttributes[paramInt].type);
  }
  
  public String getType(String paramString)
  {
    int i = getIndex(paramString);
    return i != -1 ? getReportableType(this.fAttributes[i].type) : null;
  }
  
  public String getValue(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    return this.fAttributes[paramInt].value;
  }
  
  public String getValue(String paramString)
  {
    int i = getIndex(paramString);
    return i != -1 ? this.fAttributes[i].value : null;
  }
  
  public String getName(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    return this.fAttributes[paramInt].name.rawname;
  }
  
  public int getIndex(String paramString)
  {
    for (int i = 0; i < this.fLength; i++)
    {
      Attribute localAttribute = this.fAttributes[i];
      if ((localAttribute.name.rawname != null) && (localAttribute.name.rawname.equals(paramString))) {
        return i;
      }
    }
    return -1;
  }
  
  public int getIndex(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.fLength; i++)
    {
      Attribute localAttribute = this.fAttributes[i];
      if ((localAttribute.name.localpart != null) && (localAttribute.name.localpart.equals(paramString2)) && ((paramString1 == localAttribute.name.uri) || ((paramString1 != null) && (localAttribute.name.uri != null) && (localAttribute.name.uri.equals(paramString1))))) {
        return i;
      }
    }
    return -1;
  }
  
  public String getLocalName(int paramInt)
  {
    if (!this.fNamespaces) {
      return "";
    }
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    return this.fAttributes[paramInt].name.localpart;
  }
  
  public String getQName(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    String str = this.fAttributes[paramInt].name.rawname;
    return str != null ? str : "";
  }
  
  public String getType(String paramString1, String paramString2)
  {
    if (!this.fNamespaces) {
      return null;
    }
    int i = getIndex(paramString1, paramString2);
    return i != -1 ? getReportableType(this.fAttributes[i].type) : null;
  }
  
  public String getPrefix(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    String str = this.fAttributes[paramInt].name.prefix;
    return str != null ? str : "";
  }
  
  public String getURI(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    String str = this.fAttributes[paramInt].name.uri;
    return str;
  }
  
  public String getValue(String paramString1, String paramString2)
  {
    int i = getIndex(paramString1, paramString2);
    return i != -1 ? getValue(i) : null;
  }
  
  public Augmentations getAugmentations(String paramString1, String paramString2)
  {
    int i = getIndex(paramString1, paramString2);
    return i != -1 ? this.fAttributes[i].augs : null;
  }
  
  public Augmentations getAugmentations(String paramString)
  {
    int i = getIndex(paramString);
    return i != -1 ? this.fAttributes[i].augs : null;
  }
  
  public Augmentations getAugmentations(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    return this.fAttributes[paramInt].augs;
  }
  
  public void setAugmentations(int paramInt, Augmentations paramAugmentations)
  {
    this.fAttributes[paramInt].augs = paramAugmentations;
  }
  
  public void setURI(int paramInt, String paramString)
  {
    this.fAttributes[paramInt].name.uri = paramString;
  }
  
  public void setSchemaId(int paramInt, boolean paramBoolean)
  {
    this.fAttributes[paramInt].schemaId = paramBoolean;
  }
  
  public boolean getSchemaId(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return false;
    }
    return this.fAttributes[paramInt].schemaId;
  }
  
  public boolean getSchemaId(String paramString)
  {
    int i = getIndex(paramString);
    return i != -1 ? this.fAttributes[i].schemaId : false;
  }
  
  public boolean getSchemaId(String paramString1, String paramString2)
  {
    if (!this.fNamespaces) {
      return false;
    }
    int i = getIndex(paramString1, paramString2);
    return i != -1 ? this.fAttributes[i].schemaId : false;
  }
  
  public int getIndexFast(String paramString)
  {
    for (int i = 0; i < this.fLength; i++)
    {
      Attribute localAttribute = this.fAttributes[i];
      if (localAttribute.name.rawname == paramString) {
        return i;
      }
    }
    return -1;
  }
  
  public void addAttributeNS(QName paramQName, String paramString1, String paramString2)
  {
    int i = this.fLength;
    if (this.fLength++ == this.fAttributes.length)
    {
      if (this.fLength < 20) {
        localObject = new Attribute[this.fAttributes.length + 4];
      } else {
        localObject = new Attribute[this.fAttributes.length << 1];
      }
      System.arraycopy(this.fAttributes, 0, localObject, 0, this.fAttributes.length);
      for (int j = this.fAttributes.length; j < localObject.length; j++) {
        localObject[j] = new Attribute();
      }
      this.fAttributes = ((Attribute[])localObject);
    }
    Object localObject = this.fAttributes[i];
    ((Attribute)localObject).name.setValues(paramQName);
    ((Attribute)localObject).type = paramString1;
    ((Attribute)localObject).value = paramString2;
    ((Attribute)localObject).nonNormalizedValue = paramString2;
    ((Attribute)localObject).specified = false;
    ((Attribute)localObject).augs.removeAllItems();
  }
  
  public QName checkDuplicatesNS()
  {
    int k;
    Attribute localAttribute3;
    if (this.fLength <= 20)
    {
      for (int i = 0; i < this.fLength - 1; i++)
      {
        Attribute localAttribute2 = this.fAttributes[i];
        for (k = i + 1; k < this.fLength; k++)
        {
          localAttribute3 = this.fAttributes[k];
          if ((localAttribute2.name.localpart == localAttribute3.name.localpart) && (localAttribute2.name.uri == localAttribute3.name.uri)) {
            return localAttribute3.name;
          }
        }
      }
    }
    else
    {
      this.fIsTableViewConsistent = false;
      prepareTableView();
      for (k = this.fLength - 1; k >= 0; k--)
      {
        Attribute localAttribute1 = this.fAttributes[k];
        int j = getTableViewBucket(localAttribute1.name.localpart, localAttribute1.name.uri);
        if (this.fAttributeTableViewChainState[j] != this.fLargeCount)
        {
          this.fAttributeTableViewChainState[j] = this.fLargeCount;
          localAttribute1.next = null;
          this.fAttributeTableView[j] = localAttribute1;
        }
        else
        {
          for (localAttribute3 = this.fAttributeTableView[j]; localAttribute3 != null; localAttribute3 = localAttribute3.next) {
            if ((localAttribute3.name.localpart == localAttribute1.name.localpart) && (localAttribute3.name.uri == localAttribute1.name.uri)) {
              return localAttribute1.name;
            }
          }
          localAttribute1.next = this.fAttributeTableView[j];
          this.fAttributeTableView[j] = localAttribute1;
        }
      }
    }
    return null;
  }
  
  public int getIndexFast(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.fLength; i++)
    {
      Attribute localAttribute = this.fAttributes[i];
      if ((localAttribute.name.localpart == paramString2) && (localAttribute.name.uri == paramString1)) {
        return i;
      }
    }
    return -1;
  }
  
  private String getReportableType(String paramString)
  {
    if (paramString.charAt(0) == '(') {
      return "NMTOKEN";
    }
    return paramString;
  }
  
  protected int getTableViewBucket(String paramString)
  {
    return (paramString.hashCode() & 0x7FFFFFFF) % this.fTableViewBuckets;
  }
  
  protected int getTableViewBucket(String paramString1, String paramString2)
  {
    if (paramString2 == null) {
      return (paramString1.hashCode() & 0x7FFFFFFF) % this.fTableViewBuckets;
    }
    return (paramString1.hashCode() + paramString2.hashCode() & 0x7FFFFFFF) % this.fTableViewBuckets;
  }
  
  protected void cleanTableView()
  {
    if (++this.fLargeCount < 0)
    {
      if (this.fAttributeTableViewChainState != null) {
        for (int i = this.fTableViewBuckets - 1; i >= 0; i--) {
          this.fAttributeTableViewChainState[i] = 0;
        }
      }
      this.fLargeCount = 1;
    }
  }
  
  protected void prepareTableView()
  {
    if (this.fAttributeTableView == null)
    {
      this.fAttributeTableView = new Attribute[this.fTableViewBuckets];
      this.fAttributeTableViewChainState = new int[this.fTableViewBuckets];
    }
    else
    {
      cleanTableView();
    }
  }
  
  protected void prepareAndPopulateTableView()
  {
    prepareTableView();
    for (int j = 0; j < this.fLength; j++)
    {
      Attribute localAttribute = this.fAttributes[j];
      int i = getTableViewBucket(localAttribute.name.rawname);
      if (this.fAttributeTableViewChainState[i] != this.fLargeCount)
      {
        this.fAttributeTableViewChainState[i] = this.fLargeCount;
        localAttribute.next = null;
        this.fAttributeTableView[i] = localAttribute;
      }
      else
      {
        localAttribute.next = this.fAttributeTableView[i];
        this.fAttributeTableView[i] = localAttribute;
      }
    }
  }
  
  static class Attribute
  {
    public QName name = new QName();
    public String type;
    public String value;
    public String nonNormalizedValue;
    public boolean specified;
    public boolean schemaId;
    public Augmentations augs = new AugmentationsImpl();
    public Attribute next;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.XMLAttributesImpl
 * JD-Core Version:    0.7.0.1
 */
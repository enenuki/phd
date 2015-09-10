package org.apache.xerces.xni;

public class QName
  implements Cloneable
{
  public String prefix;
  public String localpart;
  public String rawname;
  public String uri;
  
  public QName()
  {
    clear();
  }
  
  public QName(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    setValues(paramString1, paramString2, paramString3, paramString4);
  }
  
  public QName(QName paramQName)
  {
    setValues(paramQName);
  }
  
  public void setValues(QName paramQName)
  {
    this.prefix = paramQName.prefix;
    this.localpart = paramQName.localpart;
    this.rawname = paramQName.rawname;
    this.uri = paramQName.uri;
  }
  
  public void setValues(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.prefix = paramString1;
    this.localpart = paramString2;
    this.rawname = paramString3;
    this.uri = paramString4;
  }
  
  public void clear()
  {
    this.prefix = null;
    this.localpart = null;
    this.rawname = null;
    this.uri = null;
  }
  
  public Object clone()
  {
    return new QName(this);
  }
  
  public int hashCode()
  {
    if (this.uri != null) {
      return this.uri.hashCode() + (this.localpart != null ? this.localpart.hashCode() : 0);
    }
    return this.rawname != null ? this.rawname.hashCode() : 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof QName))
    {
      QName localQName = (QName)paramObject;
      if (localQName.uri != null) {
        return (this.uri == localQName.uri) && (this.localpart == localQName.localpart);
      }
      if (this.uri == null) {
        return this.rawname == localQName.rawname;
      }
    }
    return false;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (this.prefix != null)
    {
      localStringBuffer.append("prefix=\"").append(this.prefix).append('"');
      i = 1;
    }
    if (this.localpart != null)
    {
      if (i != 0) {
        localStringBuffer.append(',');
      }
      localStringBuffer.append("localpart=\"").append(this.localpart).append('"');
      i = 1;
    }
    if (this.rawname != null)
    {
      if (i != 0) {
        localStringBuffer.append(',');
      }
      localStringBuffer.append("rawname=\"").append(this.rawname).append('"');
      i = 1;
    }
    if (this.uri != null)
    {
      if (i != 0) {
        localStringBuffer.append(',');
      }
      localStringBuffer.append("uri=\"").append(this.uri).append('"');
    }
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.QName
 * JD-Core Version:    0.7.0.1
 */
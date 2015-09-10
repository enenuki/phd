package org.xml.sax.helpers;

import java.util.Vector;
import org.xml.sax.AttributeList;

/**
 * @deprecated
 */
public class AttributeListImpl
  implements AttributeList
{
  Vector names = new Vector();
  Vector types = new Vector();
  Vector values = new Vector();
  
  public AttributeListImpl() {}
  
  public AttributeListImpl(AttributeList paramAttributeList)
  {
    setAttributeList(paramAttributeList);
  }
  
  public void setAttributeList(AttributeList paramAttributeList)
  {
    int i = paramAttributeList.getLength();
    clear();
    for (int j = 0; j < i; j++) {
      addAttribute(paramAttributeList.getName(j), paramAttributeList.getType(j), paramAttributeList.getValue(j));
    }
  }
  
  public void addAttribute(String paramString1, String paramString2, String paramString3)
  {
    this.names.addElement(paramString1);
    this.types.addElement(paramString2);
    this.values.addElement(paramString3);
  }
  
  public void removeAttribute(String paramString)
  {
    int i = this.names.indexOf(paramString);
    if (i >= 0)
    {
      this.names.removeElementAt(i);
      this.types.removeElementAt(i);
      this.values.removeElementAt(i);
    }
  }
  
  public void clear()
  {
    this.names.removeAllElements();
    this.types.removeAllElements();
    this.values.removeAllElements();
  }
  
  public int getLength()
  {
    return this.names.size();
  }
  
  public String getName(int paramInt)
  {
    if (paramInt < 0) {
      return null;
    }
    try
    {
      return (String)this.names.elementAt(paramInt);
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
    return null;
  }
  
  public String getType(int paramInt)
  {
    if (paramInt < 0) {
      return null;
    }
    try
    {
      return (String)this.types.elementAt(paramInt);
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
    return null;
  }
  
  public String getValue(int paramInt)
  {
    if (paramInt < 0) {
      return null;
    }
    try
    {
      return (String)this.values.elementAt(paramInt);
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
    return null;
  }
  
  public String getType(String paramString)
  {
    return getType(this.names.indexOf(paramString));
  }
  
  public String getValue(String paramString)
  {
    return getValue(this.names.indexOf(paramString));
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.helpers.AttributeListImpl
 * JD-Core Version:    0.7.0.1
 */
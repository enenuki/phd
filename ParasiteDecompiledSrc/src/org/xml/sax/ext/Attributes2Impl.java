package org.xml.sax.ext;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class Attributes2Impl
  extends AttributesImpl
  implements Attributes2
{
  private boolean[] declared;
  private boolean[] specified;
  
  public Attributes2Impl() {}
  
  public Attributes2Impl(Attributes paramAttributes)
  {
    super(paramAttributes);
  }
  
  public boolean isDeclared(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= getLength())) {
      throw new ArrayIndexOutOfBoundsException("No attribute at index: " + paramInt);
    }
    return this.declared[paramInt];
  }
  
  public boolean isDeclared(String paramString1, String paramString2)
  {
    int i = getIndex(paramString1, paramString2);
    if (i < 0) {
      throw new IllegalArgumentException("No such attribute: local=" + paramString2 + ", namespace=" + paramString1);
    }
    return this.declared[i];
  }
  
  public boolean isDeclared(String paramString)
  {
    int i = getIndex(paramString);
    if (i < 0) {
      throw new IllegalArgumentException("No such attribute: " + paramString);
    }
    return this.declared[i];
  }
  
  public boolean isSpecified(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= getLength())) {
      throw new ArrayIndexOutOfBoundsException("No attribute at index: " + paramInt);
    }
    return this.specified[paramInt];
  }
  
  public boolean isSpecified(String paramString1, String paramString2)
  {
    int i = getIndex(paramString1, paramString2);
    if (i < 0) {
      throw new IllegalArgumentException("No such attribute: local=" + paramString2 + ", namespace=" + paramString1);
    }
    return this.specified[i];
  }
  
  public boolean isSpecified(String paramString)
  {
    int i = getIndex(paramString);
    if (i < 0) {
      throw new IllegalArgumentException("No such attribute: " + paramString);
    }
    return this.specified[i];
  }
  
  public void setAttributes(Attributes paramAttributes)
  {
    int i = paramAttributes.getLength();
    super.setAttributes(paramAttributes);
    this.declared = new boolean[i];
    this.specified = new boolean[i];
    if ((paramAttributes instanceof Attributes2))
    {
      Attributes2 localAttributes2 = (Attributes2)paramAttributes;
      for (int k = 0; k < i; k++)
      {
        this.declared[k] = localAttributes2.isDeclared(k);
        this.specified[k] = localAttributes2.isSpecified(k);
      }
    }
    else
    {
      for (int j = 0; j < i; j++)
      {
        this.declared[j] = (!"CDATA".equals(paramAttributes.getType(j)) ? 1 : false);
        this.specified[j] = true;
      }
    }
  }
  
  public void addAttribute(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    super.addAttribute(paramString1, paramString2, paramString3, paramString4, paramString5);
    int i = getLength();
    if (i < this.specified.length)
    {
      boolean[] arrayOfBoolean = new boolean[i];
      System.arraycopy(this.declared, 0, arrayOfBoolean, 0, this.declared.length);
      this.declared = arrayOfBoolean;
      arrayOfBoolean = new boolean[i];
      System.arraycopy(this.specified, 0, arrayOfBoolean, 0, this.specified.length);
      this.specified = arrayOfBoolean;
    }
    this.specified[(i - 1)] = true;
    this.declared[(i - 1)] = (!"CDATA".equals(paramString4) ? 1 : false);
  }
  
  public void removeAttribute(int paramInt)
  {
    int i = getLength() - 1;
    super.removeAttribute(paramInt);
    if (paramInt != i)
    {
      System.arraycopy(this.declared, paramInt + 1, this.declared, paramInt, i - paramInt);
      System.arraycopy(this.specified, paramInt + 1, this.specified, paramInt, i - paramInt);
    }
  }
  
  public void setDeclared(int paramInt, boolean paramBoolean)
  {
    if ((paramInt < 0) || (paramInt >= getLength())) {
      throw new ArrayIndexOutOfBoundsException("No attribute at index: " + paramInt);
    }
    this.declared[paramInt] = paramBoolean;
  }
  
  public void setSpecified(int paramInt, boolean paramBoolean)
  {
    if ((paramInt < 0) || (paramInt >= getLength())) {
      throw new ArrayIndexOutOfBoundsException("No attribute at index: " + paramInt);
    }
    this.specified[paramInt] = paramBoolean;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.ext.Attributes2Impl
 * JD-Core Version:    0.7.0.1
 */
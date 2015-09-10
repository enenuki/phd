package org.apache.xerces.impl.dtd.models;

import org.apache.xerces.xni.QName;

public class SimpleContentModel
  implements ContentModelValidator
{
  public static final short CHOICE = -1;
  public static final short SEQUENCE = -1;
  private final QName fFirstChild = new QName();
  private final QName fSecondChild = new QName();
  private final int fOperator;
  
  public SimpleContentModel(short paramShort, QName paramQName1, QName paramQName2)
  {
    this.fFirstChild.setValues(paramQName1);
    if (paramQName2 != null) {
      this.fSecondChild.setValues(paramQName2);
    } else {
      this.fSecondChild.clear();
    }
    this.fOperator = paramShort;
  }
  
  public int validate(QName[] paramArrayOfQName, int paramInt1, int paramInt2)
  {
    int i;
    switch (this.fOperator)
    {
    case 0: 
      if (paramInt2 == 0) {
        return 0;
      }
      if (paramArrayOfQName[paramInt1].rawname != this.fFirstChild.rawname) {
        return 0;
      }
      if (paramInt2 > 1) {
        return 1;
      }
      break;
    case 1: 
      if ((paramInt2 == 1) && (paramArrayOfQName[paramInt1].rawname != this.fFirstChild.rawname)) {
        return 0;
      }
      if (paramInt2 > 1) {
        return 1;
      }
      break;
    case 2: 
      if (paramInt2 > 0) {
        for (i = 0; i < paramInt2; i++) {
          if (paramArrayOfQName[(paramInt1 + i)].rawname != this.fFirstChild.rawname) {
            return i;
          }
        }
      }
      break;
    case 3: 
      if (paramInt2 == 0) {
        return 0;
      }
      for (i = 0; i < paramInt2; i++) {
        if (paramArrayOfQName[(paramInt1 + i)].rawname != this.fFirstChild.rawname) {
          return i;
        }
      }
      break;
    case 4: 
      if (paramInt2 == 0) {
        return 0;
      }
      if ((paramArrayOfQName[paramInt1].rawname != this.fFirstChild.rawname) && (paramArrayOfQName[paramInt1].rawname != this.fSecondChild.rawname)) {
        return 0;
      }
      if (paramInt2 > 1) {
        return 1;
      }
      break;
    case 5: 
      if (paramInt2 == 2)
      {
        if (paramArrayOfQName[paramInt1].rawname != this.fFirstChild.rawname) {
          return 0;
        }
        if (paramArrayOfQName[(paramInt1 + 1)].rawname != this.fSecondChild.rawname) {
          return 1;
        }
      }
      else
      {
        if (paramInt2 > 2) {
          return 2;
        }
        return paramInt2;
      }
      break;
    default: 
      throw new RuntimeException("ImplementationMessages.VAL_CST");
    }
    return -1;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.models.SimpleContentModel
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSWildcard;

public class XSWildcardDecl
  implements XSWildcard
{
  public static final String ABSENT = null;
  public short fType = 1;
  public short fProcessContents = 1;
  public String[] fNamespaceList;
  public XSObjectList fAnnotations = null;
  private String fDescription = null;
  
  public boolean allowNamespace(String paramString)
  {
    if (this.fType == 1) {
      return true;
    }
    int i;
    int j;
    if (this.fType == 2)
    {
      i = 0;
      j = this.fNamespaceList.length;
      for (int k = 0; (k < j) && (i == 0); k++) {
        if (paramString == this.fNamespaceList[k]) {
          i = 1;
        }
      }
      if (i == 0) {
        return true;
      }
    }
    if (this.fType == 3)
    {
      i = this.fNamespaceList.length;
      for (j = 0; j < i; j++) {
        if (paramString == this.fNamespaceList[j]) {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean isSubsetOf(XSWildcardDecl paramXSWildcardDecl)
  {
    if (paramXSWildcardDecl == null) {
      return false;
    }
    if (paramXSWildcardDecl.fType == 1) {
      return true;
    }
    if ((this.fType == 2) && (paramXSWildcardDecl.fType == 2) && (this.fNamespaceList[0] == paramXSWildcardDecl.fNamespaceList[0])) {
      return true;
    }
    if (this.fType == 3)
    {
      if ((paramXSWildcardDecl.fType == 3) && (subset2sets(this.fNamespaceList, paramXSWildcardDecl.fNamespaceList))) {
        return true;
      }
      if ((paramXSWildcardDecl.fType == 2) && (!elementInSet(paramXSWildcardDecl.fNamespaceList[0], this.fNamespaceList)) && (!elementInSet(ABSENT, this.fNamespaceList))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean weakerProcessContents(XSWildcardDecl paramXSWildcardDecl)
  {
    return ((this.fProcessContents == 3) && (paramXSWildcardDecl.fProcessContents == 1)) || ((this.fProcessContents == 2) && (paramXSWildcardDecl.fProcessContents != 2));
  }
  
  public XSWildcardDecl performUnionWith(XSWildcardDecl paramXSWildcardDecl, short paramShort)
  {
    if (paramXSWildcardDecl == null) {
      return null;
    }
    XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
    localXSWildcardDecl.fProcessContents = paramShort;
    if (areSame(paramXSWildcardDecl))
    {
      localXSWildcardDecl.fType = this.fType;
      localXSWildcardDecl.fNamespaceList = this.fNamespaceList;
    }
    else if ((this.fType == 1) || (paramXSWildcardDecl.fType == 1))
    {
      localXSWildcardDecl.fType = 1;
    }
    else if ((this.fType == 3) && (paramXSWildcardDecl.fType == 3))
    {
      localXSWildcardDecl.fType = 3;
      localXSWildcardDecl.fNamespaceList = union2sets(this.fNamespaceList, paramXSWildcardDecl.fNamespaceList);
    }
    else if ((this.fType == 2) && (paramXSWildcardDecl.fType == 2))
    {
      localXSWildcardDecl.fType = 2;
      localXSWildcardDecl.fNamespaceList = new String[2];
      localXSWildcardDecl.fNamespaceList[0] = ABSENT;
      localXSWildcardDecl.fNamespaceList[1] = ABSENT;
    }
    else if (((this.fType == 2) && (paramXSWildcardDecl.fType == 3)) || ((this.fType == 3) && (paramXSWildcardDecl.fType == 2)))
    {
      String[] arrayOfString1 = null;
      String[] arrayOfString2 = null;
      if (this.fType == 2)
      {
        arrayOfString1 = this.fNamespaceList;
        arrayOfString2 = paramXSWildcardDecl.fNamespaceList;
      }
      else
      {
        arrayOfString1 = paramXSWildcardDecl.fNamespaceList;
        arrayOfString2 = this.fNamespaceList;
      }
      boolean bool1 = elementInSet(ABSENT, arrayOfString2);
      if (arrayOfString1[0] != ABSENT)
      {
        boolean bool2 = elementInSet(arrayOfString1[0], arrayOfString2);
        if ((bool2) && (bool1))
        {
          localXSWildcardDecl.fType = 1;
        }
        else if ((bool2) && (!bool1))
        {
          localXSWildcardDecl.fType = 2;
          localXSWildcardDecl.fNamespaceList = new String[2];
          localXSWildcardDecl.fNamespaceList[0] = ABSENT;
          localXSWildcardDecl.fNamespaceList[1] = ABSENT;
        }
        else
        {
          if ((!bool2) && (bool1)) {
            return null;
          }
          localXSWildcardDecl.fType = 2;
          localXSWildcardDecl.fNamespaceList = arrayOfString1;
        }
      }
      else if (bool1)
      {
        localXSWildcardDecl.fType = 1;
      }
      else
      {
        localXSWildcardDecl.fType = 2;
        localXSWildcardDecl.fNamespaceList = arrayOfString1;
      }
    }
    return localXSWildcardDecl;
  }
  
  public XSWildcardDecl performIntersectionWith(XSWildcardDecl paramXSWildcardDecl, short paramShort)
  {
    if (paramXSWildcardDecl == null) {
      return null;
    }
    XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
    localXSWildcardDecl.fProcessContents = paramShort;
    if (areSame(paramXSWildcardDecl))
    {
      localXSWildcardDecl.fType = this.fType;
      localXSWildcardDecl.fNamespaceList = this.fNamespaceList;
    }
    else
    {
      Object localObject;
      if ((this.fType == 1) || (paramXSWildcardDecl.fType == 1))
      {
        localObject = this;
        if (this.fType == 1) {
          localObject = paramXSWildcardDecl;
        }
        localXSWildcardDecl.fType = ((XSWildcardDecl)localObject).fType;
        localXSWildcardDecl.fNamespaceList = ((XSWildcardDecl)localObject).fNamespaceList;
      }
      else if (((this.fType == 2) && (paramXSWildcardDecl.fType == 3)) || ((this.fType == 3) && (paramXSWildcardDecl.fType == 2)))
      {
        localObject = null;
        String[] arrayOfString1 = null;
        if (this.fType == 2)
        {
          arrayOfString1 = this.fNamespaceList;
          localObject = paramXSWildcardDecl.fNamespaceList;
        }
        else
        {
          arrayOfString1 = paramXSWildcardDecl.fNamespaceList;
          localObject = this.fNamespaceList;
        }
        int i = localObject.length;
        String[] arrayOfString2 = new String[i];
        int j = 0;
        for (int k = 0; k < i; k++) {
          if ((localObject[k] != arrayOfString1[0]) && (localObject[k] != ABSENT)) {
            arrayOfString2[(j++)] = localObject[k];
          }
        }
        localXSWildcardDecl.fType = 3;
        localXSWildcardDecl.fNamespaceList = new String[j];
        System.arraycopy(arrayOfString2, 0, localXSWildcardDecl.fNamespaceList, 0, j);
      }
      else if ((this.fType == 3) && (paramXSWildcardDecl.fType == 3))
      {
        localXSWildcardDecl.fType = 3;
        localXSWildcardDecl.fNamespaceList = intersect2sets(this.fNamespaceList, paramXSWildcardDecl.fNamespaceList);
      }
      else if ((this.fType == 2) && (paramXSWildcardDecl.fType == 2))
      {
        if ((this.fNamespaceList[0] != ABSENT) && (paramXSWildcardDecl.fNamespaceList[0] != ABSENT)) {
          return null;
        }
        localObject = this;
        if (this.fNamespaceList[0] == ABSENT) {
          localObject = paramXSWildcardDecl;
        }
        localXSWildcardDecl.fType = ((XSWildcardDecl)localObject).fType;
        localXSWildcardDecl.fNamespaceList = ((XSWildcardDecl)localObject).fNamespaceList;
      }
    }
    return localXSWildcardDecl;
  }
  
  private boolean areSame(XSWildcardDecl paramXSWildcardDecl)
  {
    if (this.fType == paramXSWildcardDecl.fType)
    {
      if (this.fType == 1) {
        return true;
      }
      if (this.fType == 2) {
        return this.fNamespaceList[0] == paramXSWildcardDecl.fNamespaceList[0];
      }
      if (this.fNamespaceList.length == paramXSWildcardDecl.fNamespaceList.length)
      {
        for (int i = 0; i < this.fNamespaceList.length; i++) {
          if (!elementInSet(this.fNamespaceList[i], paramXSWildcardDecl.fNamespaceList)) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }
  
  String[] intersect2sets(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    String[] arrayOfString1 = new String[Math.min(paramArrayOfString1.length, paramArrayOfString2.length)];
    int i = 0;
    for (int j = 0; j < paramArrayOfString1.length; j++) {
      if (elementInSet(paramArrayOfString1[j], paramArrayOfString2)) {
        arrayOfString1[(i++)] = paramArrayOfString1[j];
      }
    }
    String[] arrayOfString2 = new String[i];
    System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, i);
    return arrayOfString2;
  }
  
  String[] union2sets(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    String[] arrayOfString1 = new String[paramArrayOfString1.length];
    int i = 0;
    for (int j = 0; j < paramArrayOfString1.length; j++) {
      if (!elementInSet(paramArrayOfString1[j], paramArrayOfString2)) {
        arrayOfString1[(i++)] = paramArrayOfString1[j];
      }
    }
    String[] arrayOfString2 = new String[i + paramArrayOfString2.length];
    System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, i);
    System.arraycopy(paramArrayOfString2, 0, arrayOfString2, i, paramArrayOfString2.length);
    return arrayOfString2;
  }
  
  boolean subset2sets(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    for (int i = 0; i < paramArrayOfString1.length; i++) {
      if (!elementInSet(paramArrayOfString1[i], paramArrayOfString2)) {
        return false;
      }
    }
    return true;
  }
  
  boolean elementInSet(String paramString, String[] paramArrayOfString)
  {
    boolean bool = false;
    for (int i = 0; (i < paramArrayOfString.length) && (!bool); i++) {
      if (paramString == paramArrayOfString[i]) {
        bool = true;
      }
    }
    return bool;
  }
  
  public String toString()
  {
    if (this.fDescription == null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("WC[");
      switch (this.fType)
      {
      case 1: 
        localStringBuffer.append("##any");
        break;
      case 2: 
        localStringBuffer.append("##other");
        localStringBuffer.append(":\"");
        if (this.fNamespaceList[0] != null) {
          localStringBuffer.append(this.fNamespaceList[0]);
        }
        localStringBuffer.append("\"");
        break;
      case 3: 
        if (this.fNamespaceList.length != 0)
        {
          localStringBuffer.append("\"");
          if (this.fNamespaceList[0] != null) {
            localStringBuffer.append(this.fNamespaceList[0]);
          }
          localStringBuffer.append("\"");
          for (int i = 1; i < this.fNamespaceList.length; i++)
          {
            localStringBuffer.append(",\"");
            if (this.fNamespaceList[i] != null) {
              localStringBuffer.append(this.fNamespaceList[i]);
            }
            localStringBuffer.append("\"");
          }
        }
        break;
      }
      localStringBuffer.append("]");
      this.fDescription = localStringBuffer.toString();
    }
    return this.fDescription;
  }
  
  public short getType()
  {
    return 9;
  }
  
  public String getName()
  {
    return null;
  }
  
  public String getNamespace()
  {
    return null;
  }
  
  public short getConstraintType()
  {
    return this.fType;
  }
  
  public StringList getNsConstraintList()
  {
    return new StringListImpl(this.fNamespaceList, this.fNamespaceList == null ? 0 : this.fNamespaceList.length);
  }
  
  public short getProcessContents()
  {
    return this.fProcessContents;
  }
  
  public String getProcessContentsAsString()
  {
    switch (this.fProcessContents)
    {
    case 2: 
      return "skip";
    case 3: 
      return "lax";
    case 1: 
      return "strict";
    }
    return "invalid value";
  }
  
  public XSAnnotation getAnnotation()
  {
    return this.fAnnotations != null ? (XSAnnotation)this.fAnnotations.item(0) : null;
  }
  
  public XSObjectList getAnnotations()
  {
    return this.fAnnotations != null ? this.fAnnotations : XSObjectListImpl.EMPTY_LIST;
  }
  
  public XSNamespaceItem getNamespaceItem()
  {
    return null;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.XSWildcardDecl
 * JD-Core Version:    0.7.0.1
 */
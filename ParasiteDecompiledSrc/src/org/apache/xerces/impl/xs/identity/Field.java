package org.apache.xerces.impl.xs.identity;

import org.apache.xerces.impl.xpath.XPath;
import org.apache.xerces.impl.xpath.XPath.Axis;
import org.apache.xerces.impl.xpath.XPath.LocationPath;
import org.apache.xerces.impl.xpath.XPath.Step;
import org.apache.xerces.impl.xpath.XPathException;
import org.apache.xerces.impl.xs.util.ShortListImpl;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

public class Field
{
  protected final XPath fXPath;
  protected final IdentityConstraint fIdentityConstraint;
  
  public Field(XPath paramXPath, IdentityConstraint paramIdentityConstraint)
  {
    this.fXPath = paramXPath;
    this.fIdentityConstraint = paramIdentityConstraint;
  }
  
  public XPath getXPath()
  {
    return this.fXPath;
  }
  
  public IdentityConstraint getIdentityConstraint()
  {
    return this.fIdentityConstraint;
  }
  
  public XPathMatcher createMatcher(ValueStore paramValueStore)
  {
    return new Matcher(this.fXPath, paramValueStore);
  }
  
  public String toString()
  {
    return this.fXPath.toString();
  }
  
  protected class Matcher
    extends XPathMatcher
  {
    protected final ValueStore fStore;
    protected boolean fMayMatch = true;
    
    public Matcher(Field.XPath paramXPath, ValueStore paramValueStore)
    {
      super();
      this.fStore = paramValueStore;
    }
    
    protected void matched(Object paramObject, short paramShort, ShortList paramShortList, boolean paramBoolean)
    {
      super.matched(paramObject, paramShort, paramShortList, paramBoolean);
      if ((paramBoolean) && (Field.this.fIdentityConstraint.getCategory() == 1))
      {
        String str = "KeyMatchesNillable";
        this.fStore.reportError(str, new Object[] { Field.this.fIdentityConstraint.getElementName(), Field.this.fIdentityConstraint.getIdentityConstraintName() });
      }
      this.fStore.addValue(Field.this, this.fMayMatch, paramObject, convertToPrimitiveKind(paramShort), convertToPrimitiveKind(paramShortList));
      this.fMayMatch = false;
    }
    
    private short convertToPrimitiveKind(short paramShort)
    {
      if (paramShort <= 20) {
        return paramShort;
      }
      if (paramShort <= 29) {
        return 2;
      }
      if (paramShort <= 42) {
        return 4;
      }
      return paramShort;
    }
    
    private ShortList convertToPrimitiveKind(ShortList paramShortList)
    {
      if (paramShortList != null)
      {
        int j = paramShortList.getLength();
        for (int i = 0; i < j; i++)
        {
          short s = paramShortList.item(i);
          if (s != convertToPrimitiveKind(s)) {
            break;
          }
        }
        if (i != j)
        {
          short[] arrayOfShort = new short[j];
          for (int k = 0; k < i; k++) {
            arrayOfShort[k] = paramShortList.item(k);
          }
          while (i < j)
          {
            arrayOfShort[i] = convertToPrimitiveKind(paramShortList.item(i));
            i++;
          }
          return new ShortListImpl(arrayOfShort, arrayOfShort.length);
        }
      }
      return paramShortList;
    }
    
    protected void handleContent(XSTypeDefinition paramXSTypeDefinition, boolean paramBoolean, Object paramObject, short paramShort, ShortList paramShortList)
    {
      if ((paramXSTypeDefinition == null) || ((paramXSTypeDefinition.getTypeCategory() == 15) && (((XSComplexTypeDefinition)paramXSTypeDefinition).getContentType() != 1))) {
        this.fStore.reportError("cvc-id.3", new Object[] { Field.this.fIdentityConstraint.getName(), Field.this.fIdentityConstraint.getElementName() });
      }
      this.fMatchedString = paramObject;
      matched(this.fMatchedString, paramShort, paramShortList, paramBoolean);
    }
  }
  
  public static class XPath
    extends XPath
  {
    public XPath(String paramString, SymbolTable paramSymbolTable, NamespaceContext paramNamespaceContext)
      throws XPathException
    {
      super(paramSymbolTable, paramNamespaceContext);
      for (int i = 0; i < this.fLocationPaths.length; i++) {
        for (int j = 0; j < this.fLocationPaths[i].steps.length; j++)
        {
          XPath.Axis localAxis = this.fLocationPaths[i].steps[j].axis;
          if ((localAxis.type == 2) && (j < this.fLocationPaths[i].steps.length - 1)) {
            throw new XPathException("c-fields-xpaths");
          }
        }
      }
    }
    
    private static String fixupXPath(String paramString)
    {
      int i = paramString.length();
      int j = 0;
      int k = 1;
      while (j < i)
      {
        int m = paramString.charAt(j);
        if (k != 0)
        {
          if (!XMLChar.isSpace(m)) {
            if ((m == 46) || (m == 47)) {
              k = 0;
            } else if (m != 124) {
              return fixupXPath2(paramString, j, i);
            }
          }
        }
        else if (m == 124) {
          k = 1;
        }
        j++;
      }
      return paramString;
    }
    
    private static String fixupXPath2(String paramString, int paramInt1, int paramInt2)
    {
      StringBuffer localStringBuffer = new StringBuffer(paramInt2 + 2);
      for (int i = 0; i < paramInt1; i++) {
        localStringBuffer.append(paramString.charAt(i));
      }
      localStringBuffer.append("./");
      int j = 0;
      while (paramInt1 < paramInt2)
      {
        char c = paramString.charAt(paramInt1);
        if (j != 0)
        {
          if (!XMLChar.isSpace(c)) {
            if ((c == '.') || (c == '/'))
            {
              j = 0;
            }
            else if (c != '|')
            {
              localStringBuffer.append("./");
              j = 0;
            }
          }
        }
        else if (c == '|') {
          j = 1;
        }
        localStringBuffer.append(c);
        paramInt1++;
      }
      return localStringBuffer.toString();
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.identity.Field
 * JD-Core Version:    0.7.0.1
 */
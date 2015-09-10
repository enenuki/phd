package org.apache.xerces.impl.xs.identity;

import org.apache.xerces.impl.xpath.XPath;
import org.apache.xerces.impl.xpath.XPath.Axis;
import org.apache.xerces.impl.xpath.XPath.Step;
import org.apache.xerces.impl.xpath.XPathException;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSTypeDefinition;

public class Selector
{
  protected final XPath fXPath;
  protected final IdentityConstraint fIdentityConstraint;
  protected IdentityConstraint fIDConstraint;
  
  public Selector(XPath paramXPath, IdentityConstraint paramIdentityConstraint)
  {
    this.fXPath = paramXPath;
    this.fIdentityConstraint = paramIdentityConstraint;
  }
  
  public XPath getXPath()
  {
    return this.fXPath;
  }
  
  public IdentityConstraint getIDConstraint()
  {
    return this.fIdentityConstraint;
  }
  
  public XPathMatcher createMatcher(FieldActivator paramFieldActivator, int paramInt)
  {
    return new Matcher(this.fXPath, paramFieldActivator, paramInt);
  }
  
  public String toString()
  {
    return this.fXPath.toString();
  }
  
  public class Matcher
    extends XPathMatcher
  {
    protected final FieldActivator fFieldActivator;
    protected final int fInitialDepth;
    protected int fElementDepth;
    protected int fMatchedDepth;
    
    public Matcher(Selector.XPath paramXPath, FieldActivator paramFieldActivator, int paramInt)
    {
      super();
      this.fFieldActivator = paramFieldActivator;
      this.fInitialDepth = paramInt;
    }
    
    public void startDocumentFragment()
    {
      super.startDocumentFragment();
      this.fElementDepth = 0;
      this.fMatchedDepth = -1;
    }
    
    public void startElement(QName paramQName, XMLAttributes paramXMLAttributes)
    {
      super.startElement(paramQName, paramXMLAttributes);
      this.fElementDepth += 1;
      if (isMatched())
      {
        this.fMatchedDepth = this.fElementDepth;
        this.fFieldActivator.startValueScopeFor(Selector.this.fIdentityConstraint, this.fInitialDepth);
        int i = Selector.this.fIdentityConstraint.getFieldCount();
        for (int j = 0; j < i; j++)
        {
          Field localField = Selector.this.fIdentityConstraint.getFieldAt(j);
          XPathMatcher localXPathMatcher = this.fFieldActivator.activateField(localField, this.fInitialDepth);
          localXPathMatcher.startElement(paramQName, paramXMLAttributes);
        }
      }
    }
    
    public void endElement(QName paramQName, XSTypeDefinition paramXSTypeDefinition, boolean paramBoolean, Object paramObject, short paramShort, ShortList paramShortList)
    {
      super.endElement(paramQName, paramXSTypeDefinition, paramBoolean, paramObject, paramShort, paramShortList);
      if (this.fElementDepth-- == this.fMatchedDepth)
      {
        this.fMatchedDepth = -1;
        this.fFieldActivator.endValueScopeFor(Selector.this.fIdentityConstraint, this.fInitialDepth);
      }
    }
    
    public IdentityConstraint getIdentityConstraint()
    {
      return Selector.this.fIdentityConstraint;
    }
    
    public int getInitialDepth()
    {
      return this.fInitialDepth;
    }
  }
  
  public static class XPath
    extends XPath
  {
    public XPath(String paramString, SymbolTable paramSymbolTable, NamespaceContext paramNamespaceContext)
      throws XPathException
    {
      super(paramSymbolTable, paramNamespaceContext);
      for (int i = 0; i < this.fLocationPaths.length; i++)
      {
        XPath.Axis localAxis = this.fLocationPaths[i].steps[(this.fLocationPaths[i].steps.length - 1)].axis;
        if (localAxis.type == 2) {
          throw new XPathException("c-selector-xpath");
        }
      }
    }
    
    private static String normalize(String paramString)
    {
      StringBuffer localStringBuffer = new StringBuffer(paramString.length() + 5);
      int i = -1;
      for (;;)
      {
        if ((!XMLChar.trim(paramString).startsWith("/")) && (!XMLChar.trim(paramString).startsWith("."))) {
          localStringBuffer.append("./");
        }
        i = paramString.indexOf('|');
        if (i == -1)
        {
          localStringBuffer.append(paramString);
          break;
        }
        localStringBuffer.append(paramString.substring(0, i + 1));
        paramString = paramString.substring(i + 1, paramString.length());
      }
      return localStringBuffer.toString();
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.identity.Selector
 * JD-Core Version:    0.7.0.1
 */
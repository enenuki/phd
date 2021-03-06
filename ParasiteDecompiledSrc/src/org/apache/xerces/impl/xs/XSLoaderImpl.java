package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.util.XSGrammarPool;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XSGrammar;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xs.LSInputList;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.ls.LSInput;

public final class XSLoaderImpl
  implements XSLoader, DOMConfiguration
{
  private final XSGrammarPool fGrammarPool = new XSGrammarMerger();
  private final XMLSchemaLoader fSchemaLoader = new XMLSchemaLoader();
  
  public XSLoaderImpl()
  {
    this.fSchemaLoader.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
  }
  
  public DOMConfiguration getConfig()
  {
    return this;
  }
  
  public XSModel loadURIList(StringList paramStringList)
  {
    int i = paramStringList.getLength();
    if (i == 0) {
      return null;
    }
    try
    {
      this.fGrammarPool.clear();
      for (int j = 0; j < i; j++) {
        this.fSchemaLoader.loadGrammar(new XMLInputSource(null, paramStringList.item(j), null));
      }
      return this.fGrammarPool.toXSModel();
    }
    catch (Exception localException)
    {
      this.fSchemaLoader.reportDOMFatalError(localException);
    }
    return null;
  }
  
  public XSModel loadInputList(LSInputList paramLSInputList)
  {
    int i = paramLSInputList.getLength();
    if (i == 0) {
      return null;
    }
    try
    {
      this.fGrammarPool.clear();
      for (int j = 0; j < i; j++) {
        this.fSchemaLoader.loadGrammar(this.fSchemaLoader.dom2xmlInputSource(paramLSInputList.item(j)));
      }
      return this.fGrammarPool.toXSModel();
    }
    catch (Exception localException)
    {
      this.fSchemaLoader.reportDOMFatalError(localException);
    }
    return null;
  }
  
  public XSModel loadURI(String paramString)
  {
    try
    {
      this.fGrammarPool.clear();
      return ((XSGrammar)this.fSchemaLoader.loadGrammar(new XMLInputSource(null, paramString, null))).toXSModel();
    }
    catch (Exception localException)
    {
      this.fSchemaLoader.reportDOMFatalError(localException);
    }
    return null;
  }
  
  public XSModel load(LSInput paramLSInput)
  {
    try
    {
      this.fGrammarPool.clear();
      return ((XSGrammar)this.fSchemaLoader.loadGrammar(this.fSchemaLoader.dom2xmlInputSource(paramLSInput))).toXSModel();
    }
    catch (Exception localException)
    {
      this.fSchemaLoader.reportDOMFatalError(localException);
    }
    return null;
  }
  
  public void setParameter(String paramString, Object paramObject)
    throws DOMException
  {
    this.fSchemaLoader.setParameter(paramString, paramObject);
  }
  
  public Object getParameter(String paramString)
    throws DOMException
  {
    return this.fSchemaLoader.getParameter(paramString);
  }
  
  public boolean canSetParameter(String paramString, Object paramObject)
  {
    return this.fSchemaLoader.canSetParameter(paramString, paramObject);
  }
  
  public DOMStringList getParameterNames()
  {
    return this.fSchemaLoader.getParameterNames();
  }
  
  private static final class XSGrammarMerger
    extends XSGrammarPool
  {
    public void putGrammar(Grammar paramGrammar)
    {
      SchemaGrammar localSchemaGrammar1 = toSchemaGrammar(super.getGrammar(paramGrammar.getGrammarDescription()));
      if (localSchemaGrammar1 != null)
      {
        SchemaGrammar localSchemaGrammar2 = toSchemaGrammar(paramGrammar);
        if (localSchemaGrammar2 != null) {
          mergeSchemaGrammars(localSchemaGrammar1, localSchemaGrammar2);
        }
      }
      else
      {
        super.putGrammar(paramGrammar);
      }
    }
    
    private SchemaGrammar toSchemaGrammar(Grammar paramGrammar)
    {
      return (paramGrammar instanceof SchemaGrammar) ? (SchemaGrammar)paramGrammar : null;
    }
    
    private void mergeSchemaGrammars(SchemaGrammar paramSchemaGrammar1, SchemaGrammar paramSchemaGrammar2)
    {
      XSNamedMap localXSNamedMap = paramSchemaGrammar2.getComponents((short)2);
      int i = localXSNamedMap.getLength();
      for (int j = 0; j < i; j++)
      {
        XSElementDecl localXSElementDecl = (XSElementDecl)localXSNamedMap.item(j);
        if (paramSchemaGrammar1.getGlobalElementDecl(localXSElementDecl.getName()) == null) {
          paramSchemaGrammar1.addGlobalElementDecl(localXSElementDecl);
        }
      }
      localXSNamedMap = paramSchemaGrammar2.getComponents((short)1);
      i = localXSNamedMap.getLength();
      for (int k = 0; k < i; k++)
      {
        XSAttributeDecl localXSAttributeDecl = (XSAttributeDecl)localXSNamedMap.item(k);
        if (paramSchemaGrammar1.getGlobalAttributeDecl(localXSAttributeDecl.getName()) == null) {
          paramSchemaGrammar1.addGlobalAttributeDecl(localXSAttributeDecl);
        }
      }
      localXSNamedMap = paramSchemaGrammar2.getComponents((short)3);
      i = localXSNamedMap.getLength();
      for (int m = 0; m < i; m++)
      {
        XSTypeDefinition localXSTypeDefinition = (XSTypeDefinition)localXSNamedMap.item(m);
        if (paramSchemaGrammar1.getGlobalTypeDecl(localXSTypeDefinition.getName()) == null) {
          paramSchemaGrammar1.addGlobalTypeDecl(localXSTypeDefinition);
        }
      }
      localXSNamedMap = paramSchemaGrammar2.getComponents((short)5);
      i = localXSNamedMap.getLength();
      for (int n = 0; n < i; n++)
      {
        XSAttributeGroupDecl localXSAttributeGroupDecl = (XSAttributeGroupDecl)localXSNamedMap.item(n);
        if (paramSchemaGrammar1.getGlobalAttributeGroupDecl(localXSAttributeGroupDecl.getName()) == null) {
          paramSchemaGrammar1.addGlobalAttributeGroupDecl(localXSAttributeGroupDecl);
        }
      }
      localXSNamedMap = paramSchemaGrammar2.getComponents((short)7);
      i = localXSNamedMap.getLength();
      for (int i1 = 0; i1 < i; i1++)
      {
        XSGroupDecl localXSGroupDecl = (XSGroupDecl)localXSNamedMap.item(i1);
        if (paramSchemaGrammar1.getGlobalGroupDecl(localXSGroupDecl.getName()) == null) {
          paramSchemaGrammar1.addGlobalGroupDecl(localXSGroupDecl);
        }
      }
      localXSNamedMap = paramSchemaGrammar2.getComponents((short)11);
      i = localXSNamedMap.getLength();
      for (int i2 = 0; i2 < i; i2++)
      {
        localObject = (XSNotationDecl)localXSNamedMap.item(i2);
        if (paramSchemaGrammar1.getGlobalNotationDecl(((XSNotationDecl)localObject).getName()) == null) {
          paramSchemaGrammar1.addGlobalNotationDecl((XSNotationDecl)localObject);
        }
      }
      Object localObject = paramSchemaGrammar2.getAnnotations();
      i = ((XSObjectList)localObject).getLength();
      for (int i3 = 0; i3 < i; i3++) {
        paramSchemaGrammar1.addAnnotation((XSAnnotationImpl)((XSObjectList)localObject).item(i3));
      }
    }
    
    public boolean containsGrammar(XMLGrammarDescription paramXMLGrammarDescription)
    {
      return false;
    }
    
    public Grammar getGrammar(XMLGrammarDescription paramXMLGrammarDescription)
    {
      return null;
    }
    
    public Grammar retrieveGrammar(XMLGrammarDescription paramXMLGrammarDescription)
    {
      return null;
    }
    
    public Grammar[] retrieveInitialGrammarSet(String paramString)
    {
      return new Grammar[0];
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.XSLoaderImpl
 * JD-Core Version:    0.7.0.1
 */
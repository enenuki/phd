package org.apache.xerces.util;

import java.io.IOException;
import javax.xml.parsers.SAXParserFactory;
import org.apache.xerces.dom.DOMInputImpl;
import org.apache.xerces.jaxp.SAXParserFactoryImpl;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.readers.SAXCatalogReader;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

public class XMLCatalogResolver
  implements XMLEntityResolver, EntityResolver2, LSResourceResolver
{
  private CatalogManager fResolverCatalogManager = null;
  private Catalog fCatalog = null;
  private String[] fCatalogsList = null;
  private boolean fCatalogsChanged = true;
  private boolean fPreferPublic = true;
  private boolean fUseLiteralSystemId = true;
  
  public XMLCatalogResolver()
  {
    this(null, true);
  }
  
  public XMLCatalogResolver(String[] paramArrayOfString)
  {
    this(paramArrayOfString, true);
  }
  
  public XMLCatalogResolver(String[] paramArrayOfString, boolean paramBoolean)
  {
    init(paramArrayOfString, paramBoolean);
  }
  
  public final synchronized String[] getCatalogList()
  {
    return this.fCatalogsList != null ? (String[])this.fCatalogsList.clone() : null;
  }
  
  public final synchronized void setCatalogList(String[] paramArrayOfString)
  {
    this.fCatalogsChanged = true;
    this.fCatalogsList = (paramArrayOfString != null ? (String[])paramArrayOfString.clone() : null);
  }
  
  public final synchronized void clear()
  {
    this.fCatalog = null;
  }
  
  public final boolean getPreferPublic()
  {
    return this.fPreferPublic;
  }
  
  public final void setPreferPublic(boolean paramBoolean)
  {
    this.fPreferPublic = paramBoolean;
    this.fResolverCatalogManager.setPreferPublic(paramBoolean);
  }
  
  public final boolean getUseLiteralSystemId()
  {
    return this.fUseLiteralSystemId;
  }
  
  public final void setUseLiteralSystemId(boolean paramBoolean)
  {
    this.fUseLiteralSystemId = paramBoolean;
  }
  
  public InputSource resolveEntity(String paramString1, String paramString2)
    throws SAXException, IOException
  {
    String str = null;
    if ((paramString1 != null) && (paramString2 != null)) {
      str = resolvePublic(paramString1, paramString2);
    } else if (paramString2 != null) {
      str = resolveSystem(paramString2);
    }
    if (str != null)
    {
      InputSource localInputSource = new InputSource(str);
      localInputSource.setPublicId(paramString1);
      return localInputSource;
    }
    return null;
  }
  
  public InputSource resolveEntity(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SAXException, IOException
  {
    String str = null;
    if ((!getUseLiteralSystemId()) && (paramString3 != null)) {
      try
      {
        URI localURI = new URI(new URI(paramString3), paramString4);
        paramString4 = localURI.toString();
      }
      catch (URI.MalformedURIException localMalformedURIException) {}
    }
    if ((paramString2 != null) && (paramString4 != null)) {
      str = resolvePublic(paramString2, paramString4);
    } else if (paramString4 != null) {
      str = resolveSystem(paramString4);
    }
    if (str != null)
    {
      InputSource localInputSource = new InputSource(str);
      localInputSource.setPublicId(paramString2);
      return localInputSource;
    }
    return null;
  }
  
  public InputSource getExternalSubset(String paramString1, String paramString2)
    throws SAXException, IOException
  {
    return null;
  }
  
  public LSInput resolveResource(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    String str = null;
    try
    {
      if (paramString2 != null) {
        str = resolveURI(paramString2);
      }
      if ((!getUseLiteralSystemId()) && (paramString5 != null)) {
        try
        {
          URI localURI = new URI(new URI(paramString5), paramString4);
          paramString4 = localURI.toString();
        }
        catch (URI.MalformedURIException localMalformedURIException) {}
      }
      if (str == null) {
        if ((paramString3 != null) && (paramString4 != null)) {
          str = resolvePublic(paramString3, paramString4);
        } else if (paramString4 != null) {
          str = resolveSystem(paramString4);
        }
      }
    }
    catch (IOException localIOException) {}
    if (str != null) {
      return new DOMInputImpl(paramString3, str, paramString5);
    }
    return null;
  }
  
  public XMLInputSource resolveEntity(XMLResourceIdentifier paramXMLResourceIdentifier)
    throws XNIException, IOException
  {
    String str = resolveIdentifier(paramXMLResourceIdentifier);
    if (str != null) {
      return new XMLInputSource(paramXMLResourceIdentifier.getPublicId(), str, paramXMLResourceIdentifier.getBaseSystemId());
    }
    return null;
  }
  
  public String resolveIdentifier(XMLResourceIdentifier paramXMLResourceIdentifier)
    throws IOException, XNIException
  {
    String str1 = null;
    String str2 = paramXMLResourceIdentifier.getNamespace();
    if (str2 != null) {
      str1 = resolveURI(str2);
    }
    if (str1 == null)
    {
      String str3 = paramXMLResourceIdentifier.getPublicId();
      String str4 = getUseLiteralSystemId() ? paramXMLResourceIdentifier.getLiteralSystemId() : paramXMLResourceIdentifier.getExpandedSystemId();
      if ((str3 != null) && (str4 != null)) {
        str1 = resolvePublic(str3, str4);
      } else if (str4 != null) {
        str1 = resolveSystem(str4);
      }
    }
    return str1;
  }
  
  public final synchronized String resolveSystem(String paramString)
    throws IOException
  {
    if (this.fCatalogsChanged)
    {
      parseCatalogs();
      this.fCatalogsChanged = false;
    }
    return this.fCatalog != null ? this.fCatalog.resolveSystem(paramString) : null;
  }
  
  public final synchronized String resolvePublic(String paramString1, String paramString2)
    throws IOException
  {
    if (this.fCatalogsChanged)
    {
      parseCatalogs();
      this.fCatalogsChanged = false;
    }
    return this.fCatalog != null ? this.fCatalog.resolvePublic(paramString1, paramString2) : null;
  }
  
  public final synchronized String resolveURI(String paramString)
    throws IOException
  {
    if (this.fCatalogsChanged)
    {
      parseCatalogs();
      this.fCatalogsChanged = false;
    }
    return this.fCatalog != null ? this.fCatalog.resolveURI(paramString) : null;
  }
  
  private void init(String[] paramArrayOfString, boolean paramBoolean)
  {
    this.fCatalogsList = (paramArrayOfString != null ? (String[])paramArrayOfString.clone() : null);
    this.fPreferPublic = paramBoolean;
    this.fResolverCatalogManager = new CatalogManager();
    this.fResolverCatalogManager.setAllowOasisXMLCatalogPI(false);
    this.fResolverCatalogManager.setCatalogClassName("org.apache.xml.resolver.Catalog");
    this.fResolverCatalogManager.setCatalogFiles("");
    this.fResolverCatalogManager.setIgnoreMissingProperties(true);
    this.fResolverCatalogManager.setPreferPublic(this.fPreferPublic);
    this.fResolverCatalogManager.setRelativeCatalogs(false);
    this.fResolverCatalogManager.setUseStaticCatalog(false);
    this.fResolverCatalogManager.setVerbosity(0);
  }
  
  private void parseCatalogs()
    throws IOException
  {
    if (this.fCatalogsList != null)
    {
      this.fCatalog = new Catalog(this.fResolverCatalogManager);
      attachReaderToCatalog(this.fCatalog);
      for (int i = 0; i < this.fCatalogsList.length; i++)
      {
        String str = this.fCatalogsList[i];
        if ((str != null) && (str.length() > 0)) {
          this.fCatalog.parseCatalog(str);
        }
      }
    }
    else
    {
      this.fCatalog = null;
    }
  }
  
  private void attachReaderToCatalog(Catalog paramCatalog)
  {
    SAXParserFactoryImpl localSAXParserFactoryImpl = new SAXParserFactoryImpl();
    localSAXParserFactoryImpl.setNamespaceAware(true);
    localSAXParserFactoryImpl.setValidating(false);
    SAXCatalogReader localSAXCatalogReader = new SAXCatalogReader(localSAXParserFactoryImpl);
    localSAXCatalogReader.setCatalogParser("urn:oasis:names:tc:entity:xmlns:xml:catalog", "catalog", "org.apache.xml.resolver.readers.OASISXMLCatalogReader");
    paramCatalog.addReader("application/xml", localSAXCatalogReader);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.XMLCatalogResolver
 * JD-Core Version:    0.7.0.1
 */
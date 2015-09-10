package org.apache.xerces.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.xerces.impl.io.ASCIIReader;
import org.apache.xerces.impl.io.Latin1Reader;
import org.apache.xerces.impl.io.UCSReader;
import org.apache.xerces.impl.io.UTF8Reader;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.util.AugmentationsImpl;
import org.apache.xerces.util.EncodingMap;
import org.apache.xerces.util.HTTPInputSource;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.URI;
import org.apache.xerces.util.URI.MalformedURIException;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLEntityDescriptionImpl;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;

public class XMLEntityManager
  implements XMLComponent, XMLEntityResolver
{
  public static final int DEFAULT_BUFFER_SIZE = 2048;
  public static final int DEFAULT_XMLDECL_BUFFER_SIZE = 64;
  public static final int DEFAULT_INTERNAL_BUFFER_SIZE = 512;
  protected static final String VALIDATION = "http://xml.org/sax/features/validation";
  protected static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
  protected static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
  protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
  protected static final String WARN_ON_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
  protected static final String STANDARD_URI_CONFORMANT = "http://apache.org/xml/features/standard-uri-conformant";
  protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
  protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
  protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
  protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
  protected static final String BUFFER_SIZE = "http://apache.org/xml/properties/input-buffer-size";
  protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
  private static final String[] RECOGNIZED_FEATURES = { "http://xml.org/sax/features/validation", "http://xml.org/sax/features/external-general-entities", "http://xml.org/sax/features/external-parameter-entities", "http://apache.org/xml/features/allow-java-encodings", "http://apache.org/xml/features/warn-on-duplicate-entitydef", "http://apache.org/xml/features/standard-uri-conformant" };
  private static final Boolean[] FEATURE_DEFAULTS = { null, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE };
  private static final String[] RECOGNIZED_PROPERTIES = { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/properties/input-buffer-size", "http://apache.org/xml/properties/security-manager" };
  private static final Object[] PROPERTY_DEFAULTS = { null, null, null, null, new Integer(2048), null };
  private static final String XMLEntity = "[xml]".intern();
  private static final String DTDEntity = "[dtd]".intern();
  private static final boolean DEBUG_BUFFER = false;
  private static final boolean DEBUG_ENTITIES = false;
  private static final boolean DEBUG_ENCODINGS = false;
  private static final boolean DEBUG_RESOLVER = false;
  protected boolean fValidation;
  protected boolean fExternalGeneralEntities = true;
  protected boolean fExternalParameterEntities = true;
  protected boolean fAllowJavaEncodings;
  protected boolean fWarnDuplicateEntityDef;
  protected boolean fStrictURI;
  protected SymbolTable fSymbolTable;
  protected XMLErrorReporter fErrorReporter;
  protected XMLEntityResolver fEntityResolver;
  protected ValidationManager fValidationManager;
  protected int fBufferSize = 2048;
  protected SecurityManager fSecurityManager = null;
  protected boolean fStandalone;
  protected boolean fHasPEReferences;
  protected boolean fInExternalSubset = false;
  protected XMLEntityHandler fEntityHandler;
  protected XMLEntityScanner fEntityScanner;
  protected XMLEntityScanner fXML10EntityScanner;
  protected XMLEntityScanner fXML11EntityScanner;
  protected int fEntityExpansionLimit = 0;
  protected int fEntityExpansionCount = 0;
  protected final Hashtable fEntities = new Hashtable();
  protected final Stack fEntityStack = new Stack();
  protected ScannedEntity fCurrentEntity;
  protected Hashtable fDeclaredEntities;
  private final XMLResourceIdentifierImpl fResourceIdentifier = new XMLResourceIdentifierImpl();
  private final Augmentations fEntityAugs = new AugmentationsImpl();
  private final ByteBufferPool fByteBufferPool = new ByteBufferPool(this.fBufferSize);
  private byte[] fTempByteBuffer = null;
  private final CharacterBufferPool fCharacterBufferPool = new CharacterBufferPool(this.fBufferSize, 512);
  protected Stack fReaderStack = new Stack();
  private static String gUserDir;
  private static URI gUserDirURI;
  private static final boolean[] gNeedEscaping = new boolean[''];
  private static final char[] gAfterEscaping1 = new char[''];
  private static final char[] gAfterEscaping2 = new char[''];
  private static final char[] gHexChs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
  private static PrivilegedAction GET_USER_DIR_SYSTEM_PROPERTY = new PrivilegedAction()
  {
    public Object run()
    {
      return System.getProperty("user.dir");
    }
  };
  
  public XMLEntityManager()
  {
    this(null);
  }
  
  public XMLEntityManager(XMLEntityManager paramXMLEntityManager)
  {
    this.fDeclaredEntities = (paramXMLEntityManager != null ? paramXMLEntityManager.getDeclaredEntities() : null);
    setScannerVersion((short)1);
  }
  
  public void setStandalone(boolean paramBoolean)
  {
    this.fStandalone = paramBoolean;
  }
  
  public boolean isStandalone()
  {
    return this.fStandalone;
  }
  
  final void notifyHasPEReferences()
  {
    this.fHasPEReferences = true;
  }
  
  final boolean hasPEReferences()
  {
    return this.fHasPEReferences;
  }
  
  public void setEntityHandler(XMLEntityHandler paramXMLEntityHandler)
  {
    this.fEntityHandler = paramXMLEntityHandler;
  }
  
  public XMLResourceIdentifier getCurrentResourceIdentifier()
  {
    return this.fResourceIdentifier;
  }
  
  public ScannedEntity getCurrentEntity()
  {
    return this.fCurrentEntity;
  }
  
  public void addInternalEntity(String paramString1, String paramString2)
  {
    if (!this.fEntities.containsKey(paramString1))
    {
      InternalEntity localInternalEntity = new InternalEntity(paramString1, paramString2, this.fInExternalSubset);
      this.fEntities.put(paramString1, localInternalEntity);
    }
    else if (this.fWarnDuplicateEntityDef)
    {
      this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[] { paramString1 }, (short)0);
    }
  }
  
  public void addExternalEntity(String paramString1, String paramString2, String paramString3, String paramString4)
    throws IOException
  {
    if (!this.fEntities.containsKey(paramString1))
    {
      if (paramString4 == null)
      {
        int i = this.fEntityStack.size();
        if ((i == 0) && (this.fCurrentEntity != null) && (this.fCurrentEntity.entityLocation != null)) {
          paramString4 = this.fCurrentEntity.entityLocation.getExpandedSystemId();
        }
        for (int j = i - 1; j >= 0; j--)
        {
          ScannedEntity localScannedEntity = (ScannedEntity)this.fEntityStack.elementAt(j);
          if ((localScannedEntity.entityLocation != null) && (localScannedEntity.entityLocation.getExpandedSystemId() != null))
          {
            paramString4 = localScannedEntity.entityLocation.getExpandedSystemId();
            break;
          }
        }
      }
      ExternalEntity localExternalEntity = new ExternalEntity(paramString1, new XMLEntityDescriptionImpl(paramString1, paramString2, paramString3, paramString4, expandSystemId(paramString3, paramString4, false)), null, this.fInExternalSubset);
      this.fEntities.put(paramString1, localExternalEntity);
    }
    else if (this.fWarnDuplicateEntityDef)
    {
      this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[] { paramString1 }, (short)0);
    }
  }
  
  public boolean isExternalEntity(String paramString)
  {
    Entity localEntity = (Entity)this.fEntities.get(paramString);
    if (localEntity == null) {
      return false;
    }
    return localEntity.isExternal();
  }
  
  public boolean isEntityDeclInExternalSubset(String paramString)
  {
    Entity localEntity = (Entity)this.fEntities.get(paramString);
    if (localEntity == null) {
      return false;
    }
    return localEntity.isEntityDeclInExternalSubset();
  }
  
  public void addUnparsedEntity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (!this.fEntities.containsKey(paramString1))
    {
      ExternalEntity localExternalEntity = new ExternalEntity(paramString1, new XMLEntityDescriptionImpl(paramString1, paramString2, paramString3, paramString4, null), paramString5, this.fInExternalSubset);
      this.fEntities.put(paramString1, localExternalEntity);
    }
    else if (this.fWarnDuplicateEntityDef)
    {
      this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[] { paramString1 }, (short)0);
    }
  }
  
  public boolean isUnparsedEntity(String paramString)
  {
    Entity localEntity = (Entity)this.fEntities.get(paramString);
    if (localEntity == null) {
      return false;
    }
    return localEntity.isUnparsed();
  }
  
  public boolean isDeclaredEntity(String paramString)
  {
    Entity localEntity = (Entity)this.fEntities.get(paramString);
    return localEntity != null;
  }
  
  public XMLInputSource resolveEntity(XMLResourceIdentifier paramXMLResourceIdentifier)
    throws IOException, XNIException
  {
    if (paramXMLResourceIdentifier == null) {
      return null;
    }
    String str1 = paramXMLResourceIdentifier.getPublicId();
    String str2 = paramXMLResourceIdentifier.getLiteralSystemId();
    String str3 = paramXMLResourceIdentifier.getBaseSystemId();
    String str4 = paramXMLResourceIdentifier.getExpandedSystemId();
    int i = str4 == null ? 1 : 0;
    if ((str3 == null) && (this.fCurrentEntity != null) && (this.fCurrentEntity.entityLocation != null))
    {
      str3 = this.fCurrentEntity.entityLocation.getExpandedSystemId();
      if (str3 != null) {
        i = 1;
      }
    }
    if (i != 0) {
      str4 = expandSystemId(str2, str3, false);
    }
    XMLInputSource localXMLInputSource = null;
    if (this.fEntityResolver != null)
    {
      paramXMLResourceIdentifier.setBaseSystemId(str3);
      paramXMLResourceIdentifier.setExpandedSystemId(str4);
      localXMLInputSource = this.fEntityResolver.resolveEntity(paramXMLResourceIdentifier);
    }
    if (localXMLInputSource == null) {
      localXMLInputSource = new XMLInputSource(str1, str2, str3);
    }
    return localXMLInputSource;
  }
  
  public void startEntity(String paramString, boolean paramBoolean)
    throws IOException, XNIException
  {
    Entity localEntity = (Entity)this.fEntities.get(paramString);
    if (localEntity == null)
    {
      if (this.fEntityHandler != null)
      {
        String str1 = null;
        this.fResourceIdentifier.clear();
        this.fEntityAugs.removeAllItems();
        this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
        this.fEntityHandler.startEntity(paramString, this.fResourceIdentifier, str1, this.fEntityAugs);
        this.fEntityAugs.removeAllItems();
        this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
        this.fEntityHandler.endEntity(paramString, this.fEntityAugs);
      }
      return;
    }
    boolean bool1 = localEntity.isExternal();
    Object localObject2;
    String str2;
    Object localObject3;
    String str3;
    if ((bool1) && ((this.fValidationManager == null) || (!this.fValidationManager.isCachedDTD())))
    {
      boolean bool2 = localEntity.isUnparsed();
      j = paramString.startsWith("%");
      int k = j == 0 ? 1 : 0;
      if ((bool2) || ((k != 0) && (!this.fExternalGeneralEntities)) || ((j != 0) && (!this.fExternalParameterEntities)))
      {
        if (this.fEntityHandler != null)
        {
          this.fResourceIdentifier.clear();
          localObject2 = null;
          ExternalEntity localExternalEntity = (ExternalEntity)localEntity;
          str2 = localExternalEntity.entityLocation != null ? localExternalEntity.entityLocation.getLiteralSystemId() : null;
          localObject3 = localExternalEntity.entityLocation != null ? localExternalEntity.entityLocation.getBaseSystemId() : null;
          str3 = expandSystemId(str2, (String)localObject3, false);
          this.fResourceIdentifier.setValues(localExternalEntity.entityLocation != null ? localExternalEntity.entityLocation.getPublicId() : null, str2, (String)localObject3, str3);
          this.fEntityAugs.removeAllItems();
          this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
          this.fEntityHandler.startEntity(paramString, this.fResourceIdentifier, (String)localObject2, this.fEntityAugs);
          this.fEntityAugs.removeAllItems();
          this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
          this.fEntityHandler.endEntity(paramString, this.fEntityAugs);
        }
        return;
      }
    }
    int i = this.fEntityStack.size();
    for (int j = i; j >= 0; j--)
    {
      localObject1 = j == i ? this.fCurrentEntity : (Entity)this.fEntityStack.elementAt(j);
      if (((Entity)localObject1).name == paramString)
      {
        localObject2 = new StringBuffer(paramString);
        for (int m = j + 1; m < i; m++)
        {
          localObject1 = (Entity)this.fEntityStack.elementAt(m);
          ((StringBuffer)localObject2).append(" -> ");
          ((StringBuffer)localObject2).append(((Entity)localObject1).name);
        }
        ((StringBuffer)localObject2).append(" -> ");
        ((StringBuffer)localObject2).append(this.fCurrentEntity.name);
        ((StringBuffer)localObject2).append(" -> ");
        ((StringBuffer)localObject2).append(paramString);
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "RecursiveReference", new Object[] { paramString, ((StringBuffer)localObject2).toString() }, (short)2);
        if (this.fEntityHandler != null)
        {
          this.fResourceIdentifier.clear();
          str2 = null;
          if (bool1)
          {
            localObject3 = (ExternalEntity)localEntity;
            str3 = ((ExternalEntity)localObject3).entityLocation != null ? ((ExternalEntity)localObject3).entityLocation.getLiteralSystemId() : null;
            String str4 = ((ExternalEntity)localObject3).entityLocation != null ? ((ExternalEntity)localObject3).entityLocation.getBaseSystemId() : null;
            String str5 = expandSystemId(str3, str4, false);
            this.fResourceIdentifier.setValues(((ExternalEntity)localObject3).entityLocation != null ? ((ExternalEntity)localObject3).entityLocation.getPublicId() : null, str3, str4, str5);
          }
          this.fEntityAugs.removeAllItems();
          this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
          this.fEntityHandler.startEntity(paramString, this.fResourceIdentifier, str2, this.fEntityAugs);
          this.fEntityAugs.removeAllItems();
          this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
          this.fEntityHandler.endEntity(paramString, this.fEntityAugs);
        }
        return;
      }
    }
    Object localObject1 = null;
    if (bool1)
    {
      localObject2 = (ExternalEntity)localEntity;
      localObject1 = resolveEntity(((ExternalEntity)localObject2).entityLocation);
    }
    else
    {
      localObject2 = (InternalEntity)localEntity;
      StringReader localStringReader = new StringReader(((InternalEntity)localObject2).text);
      localObject1 = new XMLInputSource(null, null, null, localStringReader, null);
    }
    startEntity(paramString, (XMLInputSource)localObject1, paramBoolean, bool1);
  }
  
  public void startDocumentEntity(XMLInputSource paramXMLInputSource)
    throws IOException, XNIException
  {
    startEntity(XMLEntity, paramXMLInputSource, false, true);
  }
  
  public void startDTDEntity(XMLInputSource paramXMLInputSource)
    throws IOException, XNIException
  {
    startEntity(DTDEntity, paramXMLInputSource, false, true);
  }
  
  public void startExternalSubset()
  {
    this.fInExternalSubset = true;
  }
  
  public void endExternalSubset()
  {
    this.fInExternalSubset = false;
  }
  
  public void startEntity(String paramString, XMLInputSource paramXMLInputSource, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException, XNIException
  {
    String str = setupCurrentEntity(paramString, paramXMLInputSource, paramBoolean1, paramBoolean2);
    if ((this.fSecurityManager != null) && (this.fEntityExpansionCount++ > this.fEntityExpansionLimit))
    {
      this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityExpansionLimitExceeded", new Object[] { new Integer(this.fEntityExpansionLimit) }, (short)2);
      this.fEntityExpansionCount = 0;
    }
    if (this.fEntityHandler != null) {
      this.fEntityHandler.startEntity(paramString, this.fResourceIdentifier, str, null);
    }
  }
  
  public String setupCurrentEntity(String paramString, XMLInputSource paramXMLInputSource, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException, XNIException
  {
    String str1 = paramXMLInputSource.getPublicId();
    Object localObject1 = paramXMLInputSource.getSystemId();
    Object localObject2 = paramXMLInputSource.getBaseSystemId();
    String str2 = paramXMLInputSource.getEncoding();
    boolean bool1 = str2 != null;
    Boolean localBoolean = null;
    this.fTempByteBuffer = null;
    Object localObject3 = null;
    Reader localReader = paramXMLInputSource.getCharacterStream();
    Object localObject4 = expandSystemId((String)localObject1, (String)localObject2, this.fStrictURI);
    if (localObject2 == null) {
      localObject2 = localObject4;
    }
    if (localReader == null)
    {
      localObject3 = paramXMLInputSource.getByteStream();
      Object localObject5;
      if (localObject3 == null)
      {
        localObject5 = new URL((String)localObject4);
        URLConnection localURLConnection = ((URL)localObject5).openConnection();
        if (!(localURLConnection instanceof HttpURLConnection))
        {
          localObject3 = localURLConnection.getInputStream();
        }
        else
        {
          boolean bool2 = true;
          Object localObject7;
          if ((paramXMLInputSource instanceof HTTPInputSource))
          {
            localObject7 = (HttpURLConnection)localURLConnection;
            HTTPInputSource localHTTPInputSource = (HTTPInputSource)paramXMLInputSource;
            Iterator localIterator = localHTTPInputSource.getHTTPRequestProperties();
            while (localIterator.hasNext())
            {
              Map.Entry localEntry = (Map.Entry)localIterator.next();
              ((URLConnection)localObject7).setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue());
            }
            bool2 = localHTTPInputSource.getFollowHTTPRedirects();
            if (!bool2) {
              setInstanceFollowRedirects((HttpURLConnection)localObject7, bool2);
            }
          }
          localObject3 = localURLConnection.getInputStream();
          if (bool2)
          {
            localObject7 = localURLConnection.getURL().toString();
            if (!((String)localObject7).equals(localObject4))
            {
              localObject1 = localObject7;
              localObject4 = localObject7;
            }
          }
        }
      }
      localObject3 = new RewindableInputStream((InputStream)localObject3);
      int i;
      Object localObject6;
      int j;
      int k;
      int m;
      if (str2 == null)
      {
        localObject5 = new byte[4];
        for (i = 0; i < 4; i++) {
          localObject5[i] = ((byte)((InputStream)localObject3).read());
        }
        if (i == 4)
        {
          localObject6 = getEncodingName((byte[])localObject5, i);
          str2 = (String)localObject6[0];
          localBoolean = (Boolean)localObject6[1];
          ((InputStream)localObject3).reset();
          if ((i > 2) && (str2.equals("UTF-8")))
          {
            j = localObject5[0] & 0xFF;
            k = localObject5[1] & 0xFF;
            m = localObject5[2] & 0xFF;
            if ((j == 239) && (k == 187) && (m == 191)) {
              ((InputStream)localObject3).skip(3L);
            }
          }
          localReader = createReader((InputStream)localObject3, str2, localBoolean);
        }
        else
        {
          localReader = createReader((InputStream)localObject3, str2, localBoolean);
        }
      }
      else
      {
        str2 = str2.toUpperCase(Locale.ENGLISH);
        if (str2.equals("UTF-8"))
        {
          localObject5 = new int[3];
          for (i = 0; i < 3; i++)
          {
            localObject5[i] = ((InputStream)localObject3).read();
            if (localObject5[i] == -1) {
              break;
            }
          }
          if (i == 3)
          {
            if ((localObject5[0] != 239) || (localObject5[1] != 187) || (localObject5[2] != 191)) {
              ((InputStream)localObject3).reset();
            }
          }
          else {
            ((InputStream)localObject3).reset();
          }
          localReader = createReader((InputStream)localObject3, str2, localBoolean);
        }
        else if (str2.equals("UTF-16"))
        {
          localObject5 = new int[4];
          for (i = 0; i < 4; i++)
          {
            localObject5[i] = ((InputStream)localObject3).read();
            if (localObject5[i] == -1) {
              break;
            }
          }
          ((InputStream)localObject3).reset();
          localObject6 = "UTF-16";
          if (i >= 2)
          {
            j = localObject5[0];
            k = localObject5[1];
            if ((j == 254) && (k == 255))
            {
              localObject6 = "UTF-16BE";
              localBoolean = Boolean.TRUE;
            }
            else if ((j == 255) && (k == 254))
            {
              localObject6 = "UTF-16LE";
              localBoolean = Boolean.FALSE;
            }
            else if (i == 4)
            {
              m = localObject5[2];
              int n = localObject5[3];
              if ((j == 0) && (k == 60) && (m == 0) && (n == 63))
              {
                localObject6 = "UTF-16BE";
                localBoolean = Boolean.TRUE;
              }
              if ((j == 60) && (k == 0) && (m == 63) && (n == 0))
              {
                localObject6 = "UTF-16LE";
                localBoolean = Boolean.FALSE;
              }
            }
          }
          localReader = createReader((InputStream)localObject3, (String)localObject6, localBoolean);
        }
        else if (str2.equals("ISO-10646-UCS-4"))
        {
          localObject5 = new int[4];
          for (i = 0; i < 4; i++)
          {
            localObject5[i] = ((InputStream)localObject3).read();
            if (localObject5[i] == -1) {
              break;
            }
          }
          ((InputStream)localObject3).reset();
          if (i == 4) {
            if ((localObject5[0] == 0) && (localObject5[1] == 0) && (localObject5[2] == 0) && (localObject5[3] == 60)) {
              localBoolean = Boolean.TRUE;
            } else if ((localObject5[0] == 60) && (localObject5[1] == 0) && (localObject5[2] == 0) && (localObject5[3] == 0)) {
              localBoolean = Boolean.FALSE;
            }
          }
          localReader = createReader((InputStream)localObject3, str2, localBoolean);
        }
        else if (str2.equals("ISO-10646-UCS-2"))
        {
          localObject5 = new int[4];
          for (i = 0; i < 4; i++)
          {
            localObject5[i] = ((InputStream)localObject3).read();
            if (localObject5[i] == -1) {
              break;
            }
          }
          ((InputStream)localObject3).reset();
          if (i == 4) {
            if ((localObject5[0] == 0) && (localObject5[1] == 60) && (localObject5[2] == 0) && (localObject5[3] == 63)) {
              localBoolean = Boolean.TRUE;
            } else if ((localObject5[0] == 60) && (localObject5[1] == 0) && (localObject5[2] == 63) && (localObject5[3] == 0)) {
              localBoolean = Boolean.FALSE;
            }
          }
          localReader = createReader((InputStream)localObject3, str2, localBoolean);
        }
        else
        {
          localReader = createReader((InputStream)localObject3, str2, localBoolean);
        }
      }
    }
    this.fReaderStack.push(localReader);
    if (this.fCurrentEntity != null) {
      this.fEntityStack.push(this.fCurrentEntity);
    }
    this.fCurrentEntity = new ScannedEntity(paramString, new XMLResourceIdentifierImpl(str1, (String)localObject1, (String)localObject2, (String)localObject4), (InputStream)localObject3, localReader, this.fTempByteBuffer, str2, paramBoolean1, false, paramBoolean2);
    this.fCurrentEntity.setEncodingExternallySpecified(bool1);
    this.fEntityScanner.setCurrentEntity(this.fCurrentEntity);
    this.fResourceIdentifier.setValues(str1, (String)localObject1, (String)localObject2, (String)localObject4);
    return str2;
  }
  
  public void setScannerVersion(short paramShort)
  {
    if (paramShort == 1)
    {
      if (this.fXML10EntityScanner == null) {
        this.fXML10EntityScanner = new XMLEntityScanner();
      }
      this.fXML10EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
      this.fEntityScanner = this.fXML10EntityScanner;
      this.fEntityScanner.setCurrentEntity(this.fCurrentEntity);
    }
    else
    {
      if (this.fXML11EntityScanner == null) {
        this.fXML11EntityScanner = new XML11EntityScanner();
      }
      this.fXML11EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
      this.fEntityScanner = this.fXML11EntityScanner;
      this.fEntityScanner.setCurrentEntity(this.fCurrentEntity);
    }
  }
  
  public XMLEntityScanner getEntityScanner()
  {
    if (this.fEntityScanner == null)
    {
      if (this.fXML10EntityScanner == null) {
        this.fXML10EntityScanner = new XMLEntityScanner();
      }
      this.fXML10EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
      this.fEntityScanner = this.fXML10EntityScanner;
    }
    return this.fEntityScanner;
  }
  
  public void closeReaders()
  {
    for (int i = this.fReaderStack.size() - 1; i >= 0; i--) {
      try
      {
        ((Reader)this.fReaderStack.pop()).close();
      }
      catch (IOException localIOException) {}
    }
  }
  
  public void reset(XMLComponentManager paramXMLComponentManager)
    throws XMLConfigurationException
  {
    boolean bool;
    try
    {
      bool = paramXMLComponentManager.getFeature("http://apache.org/xml/features/internal/parser-settings");
    }
    catch (XMLConfigurationException localXMLConfigurationException1)
    {
      bool = true;
    }
    if (!bool)
    {
      reset();
      return;
    }
    try
    {
      this.fValidation = paramXMLComponentManager.getFeature("http://xml.org/sax/features/validation");
    }
    catch (XMLConfigurationException localXMLConfigurationException2)
    {
      this.fValidation = false;
    }
    try
    {
      this.fExternalGeneralEntities = paramXMLComponentManager.getFeature("http://xml.org/sax/features/external-general-entities");
    }
    catch (XMLConfigurationException localXMLConfigurationException3)
    {
      this.fExternalGeneralEntities = true;
    }
    try
    {
      this.fExternalParameterEntities = paramXMLComponentManager.getFeature("http://xml.org/sax/features/external-parameter-entities");
    }
    catch (XMLConfigurationException localXMLConfigurationException4)
    {
      this.fExternalParameterEntities = true;
    }
    try
    {
      this.fAllowJavaEncodings = paramXMLComponentManager.getFeature("http://apache.org/xml/features/allow-java-encodings");
    }
    catch (XMLConfigurationException localXMLConfigurationException5)
    {
      this.fAllowJavaEncodings = false;
    }
    try
    {
      this.fWarnDuplicateEntityDef = paramXMLComponentManager.getFeature("http://apache.org/xml/features/warn-on-duplicate-entitydef");
    }
    catch (XMLConfigurationException localXMLConfigurationException6)
    {
      this.fWarnDuplicateEntityDef = false;
    }
    try
    {
      this.fStrictURI = paramXMLComponentManager.getFeature("http://apache.org/xml/features/standard-uri-conformant");
    }
    catch (XMLConfigurationException localXMLConfigurationException7)
    {
      this.fStrictURI = false;
    }
    this.fSymbolTable = ((SymbolTable)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"));
    this.fErrorReporter = ((XMLErrorReporter)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter"));
    try
    {
      this.fEntityResolver = ((XMLEntityResolver)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"));
    }
    catch (XMLConfigurationException localXMLConfigurationException8)
    {
      this.fEntityResolver = null;
    }
    try
    {
      this.fValidationManager = ((ValidationManager)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager"));
    }
    catch (XMLConfigurationException localXMLConfigurationException9)
    {
      this.fValidationManager = null;
    }
    try
    {
      this.fSecurityManager = ((SecurityManager)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/security-manager"));
    }
    catch (XMLConfigurationException localXMLConfigurationException10)
    {
      this.fSecurityManager = null;
    }
    reset();
  }
  
  public void reset()
  {
    this.fEntityExpansionLimit = (this.fSecurityManager != null ? this.fSecurityManager.getEntityExpansionLimit() : 0);
    this.fStandalone = false;
    this.fHasPEReferences = false;
    this.fEntities.clear();
    this.fEntityStack.removeAllElements();
    this.fEntityExpansionCount = 0;
    this.fCurrentEntity = null;
    if (this.fXML10EntityScanner != null) {
      this.fXML10EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
    }
    if (this.fXML11EntityScanner != null) {
      this.fXML11EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
    }
    if (this.fDeclaredEntities != null)
    {
      Enumeration localEnumeration = this.fDeclaredEntities.keys();
      while (localEnumeration.hasMoreElements())
      {
        Object localObject1 = localEnumeration.nextElement();
        Object localObject2 = this.fDeclaredEntities.get(localObject1);
        this.fEntities.put(localObject1, localObject2);
      }
    }
    this.fEntityHandler = null;
  }
  
  public String[] getRecognizedFeatures()
  {
    return (String[])RECOGNIZED_FEATURES.clone();
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws XMLConfigurationException
  {
    if (paramString.startsWith("http://apache.org/xml/features/"))
    {
      int i = paramString.length() - "http://apache.org/xml/features/".length();
      if ((i == "allow-java-encodings".length()) && (paramString.endsWith("allow-java-encodings"))) {
        this.fAllowJavaEncodings = paramBoolean;
      }
    }
  }
  
  public String[] getRecognizedProperties()
  {
    return (String[])RECOGNIZED_PROPERTIES.clone();
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws XMLConfigurationException
  {
    if (paramString.startsWith("http://apache.org/xml/properties/"))
    {
      int i = paramString.length() - "http://apache.org/xml/properties/".length();
      if ((i == "internal/symbol-table".length()) && (paramString.endsWith("internal/symbol-table")))
      {
        this.fSymbolTable = ((SymbolTable)paramObject);
        return;
      }
      if ((i == "internal/error-reporter".length()) && (paramString.endsWith("internal/error-reporter")))
      {
        this.fErrorReporter = ((XMLErrorReporter)paramObject);
        return;
      }
      if ((i == "internal/entity-resolver".length()) && (paramString.endsWith("internal/entity-resolver")))
      {
        this.fEntityResolver = ((XMLEntityResolver)paramObject);
        return;
      }
      if ((i == "input-buffer-size".length()) && (paramString.endsWith("input-buffer-size")))
      {
        Integer localInteger = (Integer)paramObject;
        if ((localInteger != null) && (localInteger.intValue() > 64))
        {
          this.fBufferSize = localInteger.intValue();
          this.fEntityScanner.setBufferSize(this.fBufferSize);
          this.fByteBufferPool.setBufferSize(this.fBufferSize);
          this.fCharacterBufferPool.setExternalBufferSize(this.fBufferSize);
        }
      }
      if ((i == "security-manager".length()) && (paramString.endsWith("security-manager")))
      {
        this.fSecurityManager = ((SecurityManager)paramObject);
        this.fEntityExpansionLimit = (this.fSecurityManager != null ? this.fSecurityManager.getEntityExpansionLimit() : 0);
      }
    }
  }
  
  public Boolean getFeatureDefault(String paramString)
  {
    for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
      if (RECOGNIZED_FEATURES[i].equals(paramString)) {
        return FEATURE_DEFAULTS[i];
      }
    }
    return null;
  }
  
  public Object getPropertyDefault(String paramString)
  {
    for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
      if (RECOGNIZED_PROPERTIES[i].equals(paramString)) {
        return PROPERTY_DEFAULTS[i];
      }
    }
    return null;
  }
  
  private static synchronized URI getUserDir()
    throws URI.MalformedURIException
  {
    String str = "";
    try
    {
      str = (String)AccessController.doPrivileged(GET_USER_DIR_SYSTEM_PROPERTY);
    }
    catch (SecurityException localSecurityException) {}
    if (str.length() == 0) {
      return new URI("file", "", "", null, null);
    }
    if ((gUserDirURI != null) && (str.equals(gUserDir))) {
      return gUserDirURI;
    }
    gUserDir = str;
    char c = File.separatorChar;
    str = str.replace(c, '/');
    int i = str.length();
    StringBuffer localStringBuffer = new StringBuffer(i * 3);
    int j;
    if ((i >= 2) && (str.charAt(1) == ':'))
    {
      j = Character.toUpperCase(str.charAt(0));
      if ((j >= 65) && (j <= 90)) {
        localStringBuffer.append('/');
      }
    }
    for (int k = 0; k < i; k++)
    {
      j = str.charAt(k);
      if (j >= 128) {
        break;
      }
      if (gNeedEscaping[j] != 0)
      {
        localStringBuffer.append('%');
        localStringBuffer.append(gAfterEscaping1[j]);
        localStringBuffer.append(gAfterEscaping2[j]);
      }
      else
      {
        localStringBuffer.append((char)j);
      }
    }
    if (k < i)
    {
      byte[] arrayOfByte = null;
      try
      {
        arrayOfByte = str.substring(k).getBytes("UTF-8");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        return new URI("file", "", str, null, null);
      }
      i = arrayOfByte.length;
      for (k = 0; k < i; k++)
      {
        int m = arrayOfByte[k];
        if (m < 0)
        {
          j = m + 256;
          localStringBuffer.append('%');
          localStringBuffer.append(gHexChs[(j >> 4)]);
          localStringBuffer.append(gHexChs[(j & 0xF)]);
        }
        else if (gNeedEscaping[m] != 0)
        {
          localStringBuffer.append('%');
          localStringBuffer.append(gAfterEscaping1[m]);
          localStringBuffer.append(gAfterEscaping2[m]);
        }
        else
        {
          localStringBuffer.append((char)m);
        }
      }
    }
    if (!str.endsWith("/")) {
      localStringBuffer.append('/');
    }
    gUserDirURI = new URI("file", "", localStringBuffer.toString(), null, null);
    return gUserDirURI;
  }
  
  public static void absolutizeAgainstUserDir(URI paramURI)
    throws URI.MalformedURIException
  {
    paramURI.absolutize(getUserDir());
  }
  
  public static String expandSystemId(String paramString1, String paramString2, boolean paramBoolean)
    throws URI.MalformedURIException
  {
    if (paramString1 == null) {
      return null;
    }
    if (paramBoolean) {
      return expandSystemIdStrictOn(paramString1, paramString2);
    }
    try
    {
      return expandSystemIdStrictOff(paramString1, paramString2);
    }
    catch (URI.MalformedURIException localMalformedURIException1)
    {
      if (paramString1.length() == 0) {
        return paramString1;
      }
      String str = fixURI(paramString1);
      URI localURI1 = null;
      URI localURI2 = null;
      try
      {
        if ((paramString2 == null) || (paramString2.length() == 0) || (paramString2.equals(paramString1))) {
          localURI1 = getUserDir();
        } else {
          try
          {
            localURI1 = new URI(fixURI(paramString2).trim());
          }
          catch (URI.MalformedURIException localMalformedURIException2)
          {
            if (paramString2.indexOf(':') != -1) {
              localURI1 = new URI("file", "", fixURI(paramString2).trim(), null, null);
            } else {
              localURI1 = new URI(getUserDir(), fixURI(paramString2));
            }
          }
        }
        localURI2 = new URI(localURI1, str.trim());
      }
      catch (Exception localException) {}
      if (localURI2 == null) {
        return paramString1;
      }
      return localURI2.toString();
    }
  }
  
  private static String expandSystemIdStrictOn(String paramString1, String paramString2)
    throws URI.MalformedURIException
  {
    URI localURI1 = new URI(paramString1, true);
    if (localURI1.isAbsoluteURI()) {
      return paramString1;
    }
    URI localURI2 = null;
    if ((paramString2 == null) || (paramString2.length() == 0))
    {
      localURI2 = getUserDir();
    }
    else
    {
      localURI2 = new URI(paramString2, true);
      if (!localURI2.isAbsoluteURI()) {
        localURI2.absolutize(getUserDir());
      }
    }
    localURI1.absolutize(localURI2);
    return localURI1.toString();
  }
  
  private static String expandSystemIdStrictOff(String paramString1, String paramString2)
    throws URI.MalformedURIException
  {
    URI localURI1 = new URI(paramString1, true);
    if (localURI1.isAbsoluteURI())
    {
      if (localURI1.getScheme().length() > 1) {
        return paramString1;
      }
      throw new URI.MalformedURIException();
    }
    URI localURI2 = null;
    if ((paramString2 == null) || (paramString2.length() == 0))
    {
      localURI2 = getUserDir();
    }
    else
    {
      localURI2 = new URI(paramString2, true);
      if (!localURI2.isAbsoluteURI()) {
        localURI2.absolutize(getUserDir());
      }
    }
    localURI1.absolutize(localURI2);
    return localURI1.toString();
  }
  
  public static void setInstanceFollowRedirects(HttpURLConnection paramHttpURLConnection, boolean paramBoolean)
  {
    try
    {
      Method localMethod = class$java$net$HttpURLConnection.getMethod("setInstanceFollowRedirects", new Class[] { Boolean.TYPE });
      localMethod.invoke(paramHttpURLConnection, new Object[] { paramBoolean ? Boolean.TRUE : Boolean.FALSE });
    }
    catch (Exception localException) {}
  }
  
  public static OutputStream createOutputStream(String paramString)
    throws IOException
  {
    String str1 = expandSystemId(paramString, null, true);
    URL localURL = new URL(str1 != null ? str1 : paramString);
    Object localObject = null;
    String str2 = localURL.getProtocol();
    String str3 = localURL.getHost();
    if ((str2.equals("file")) && ((str3 == null) || (str3.length() == 0) || (str3.equals("localhost"))))
    {
      localObject = new FileOutputStream(getPathWithoutEscapes(localURL.getPath()));
    }
    else
    {
      URLConnection localURLConnection = localURL.openConnection();
      localURLConnection.setDoInput(false);
      localURLConnection.setDoOutput(true);
      localURLConnection.setUseCaches(false);
      if ((localURLConnection instanceof HttpURLConnection))
      {
        HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURLConnection;
        localHttpURLConnection.setRequestMethod("PUT");
      }
      localObject = localURLConnection.getOutputStream();
    }
    return localObject;
  }
  
  private static String getPathWithoutEscapes(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0) && (paramString.indexOf('%') != -1))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "%");
      StringBuffer localStringBuffer = new StringBuffer(paramString.length());
      int i = localStringTokenizer.countTokens();
      localStringBuffer.append(localStringTokenizer.nextToken());
      for (int j = 1; j < i; j++)
      {
        String str = localStringTokenizer.nextToken();
        localStringBuffer.append((char)Integer.valueOf(str.substring(0, 2), 16).intValue());
        localStringBuffer.append(str.substring(2));
      }
      return localStringBuffer.toString();
    }
    return paramString;
  }
  
  void endEntity()
    throws XNIException
  {
    if (this.fEntityHandler != null) {
      this.fEntityHandler.endEntity(this.fCurrentEntity.name, null);
    }
    try
    {
      this.fCurrentEntity.reader.close();
    }
    catch (IOException localIOException) {}
    if (!this.fReaderStack.isEmpty()) {
      this.fReaderStack.pop();
    }
    this.fCharacterBufferPool.returnBuffer(this.fCurrentEntity.fCharacterBuffer);
    if (this.fCurrentEntity.fByteBuffer != null) {
      this.fByteBufferPool.returnBuffer(this.fCurrentEntity.fByteBuffer);
    }
    this.fCurrentEntity = (this.fEntityStack.size() > 0 ? (ScannedEntity)this.fEntityStack.pop() : null);
    this.fEntityScanner.setCurrentEntity(this.fCurrentEntity);
  }
  
  protected Object[] getEncodingName(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramInt < 2) {
      return new Object[] { "UTF-8", null };
    }
    int i = paramArrayOfByte[0] & 0xFF;
    int j = paramArrayOfByte[1] & 0xFF;
    if ((i == 254) && (j == 255)) {
      return new Object[] { "UTF-16BE", Boolean.TRUE };
    }
    if ((i == 255) && (j == 254)) {
      return new Object[] { "UTF-16LE", Boolean.FALSE };
    }
    if (paramInt < 3) {
      return new Object[] { "UTF-8", null };
    }
    int k = paramArrayOfByte[2] & 0xFF;
    if ((i == 239) && (j == 187) && (k == 191)) {
      return new Object[] { "UTF-8", null };
    }
    if (paramInt < 4) {
      return new Object[] { "UTF-8", null };
    }
    int m = paramArrayOfByte[3] & 0xFF;
    if ((i == 0) && (j == 0) && (k == 0) && (m == 60)) {
      return new Object[] { "ISO-10646-UCS-4", Boolean.TRUE };
    }
    if ((i == 60) && (j == 0) && (k == 0) && (m == 0)) {
      return new Object[] { "ISO-10646-UCS-4", Boolean.FALSE };
    }
    if ((i == 0) && (j == 0) && (k == 60) && (m == 0)) {
      return new Object[] { "ISO-10646-UCS-4", null };
    }
    if ((i == 0) && (j == 60) && (k == 0) && (m == 0)) {
      return new Object[] { "ISO-10646-UCS-4", null };
    }
    if ((i == 0) && (j == 60) && (k == 0) && (m == 63)) {
      return new Object[] { "UTF-16BE", Boolean.TRUE };
    }
    if ((i == 60) && (j == 0) && (k == 63) && (m == 0)) {
      return new Object[] { "UTF-16LE", Boolean.FALSE };
    }
    if ((i == 76) && (j == 111) && (k == 167) && (m == 148)) {
      return new Object[] { "CP037", null };
    }
    return new Object[] { "UTF-8", null };
  }
  
  protected Reader createReader(InputStream paramInputStream, String paramString, Boolean paramBoolean)
    throws IOException
  {
    if ((paramString == "UTF-8") || (paramString == null))
    {
      if (this.fTempByteBuffer == null) {
        this.fTempByteBuffer = this.fByteBufferPool.getBuffer();
      }
      return new UTF8Reader(paramInputStream, this.fTempByteBuffer, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }
    String str1 = paramString.toUpperCase(Locale.ENGLISH);
    if (str1.equals("UTF-8"))
    {
      if (this.fTempByteBuffer == null) {
        this.fTempByteBuffer = this.fByteBufferPool.getBuffer();
      }
      return new UTF8Reader(paramInputStream, this.fTempByteBuffer, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }
    if (str1.equals("ISO-10646-UCS-4"))
    {
      if (paramBoolean != null)
      {
        bool1 = paramBoolean.booleanValue();
        if (bool1) {
          return new UCSReader(paramInputStream, (short)8);
        }
        return new UCSReader(paramInputStream, (short)4);
      }
      this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingByteOrderUnsupported", new Object[] { paramString }, (short)2);
    }
    if (str1.equals("ISO-10646-UCS-2"))
    {
      if (paramBoolean != null)
      {
        bool1 = paramBoolean.booleanValue();
        if (bool1) {
          return new UCSReader(paramInputStream, (short)2);
        }
        return new UCSReader(paramInputStream, (short)1);
      }
      this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingByteOrderUnsupported", new Object[] { paramString }, (short)2);
    }
    boolean bool1 = XMLChar.isValidIANAEncoding(paramString);
    boolean bool2 = XMLChar.isValidJavaEncoding(paramString);
    if ((!bool1) || ((this.fAllowJavaEncodings) && (!bool2)))
    {
      this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingDeclInvalid", new Object[] { paramString }, (short)2);
      return new Latin1Reader(paramInputStream, this.fBufferSize);
    }
    String str2 = EncodingMap.getIANA2JavaMapping(str1);
    if (str2 == null)
    {
      if (this.fAllowJavaEncodings)
      {
        str2 = paramString;
      }
      else
      {
        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingDeclInvalid", new Object[] { paramString }, (short)2);
        if (this.fTempByteBuffer == null) {
          this.fTempByteBuffer = this.fByteBufferPool.getBuffer();
        }
        return new Latin1Reader(paramInputStream, this.fTempByteBuffer);
      }
    }
    else
    {
      if (str2.equals("ASCII"))
      {
        if (this.fTempByteBuffer == null) {
          this.fTempByteBuffer = this.fByteBufferPool.getBuffer();
        }
        return new ASCIIReader(paramInputStream, this.fTempByteBuffer, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
      }
      if (str2.equals("ISO8859_1"))
      {
        if (this.fTempByteBuffer == null) {
          this.fTempByteBuffer = this.fByteBufferPool.getBuffer();
        }
        return new Latin1Reader(paramInputStream, this.fTempByteBuffer);
      }
    }
    return new InputStreamReader(paramInputStream, str2);
  }
  
  protected static String fixURI(String paramString)
  {
    paramString = paramString.replace(File.separatorChar, '/');
    StringBuffer localStringBuffer = null;
    int j;
    if (paramString.length() >= 2)
    {
      i = paramString.charAt(1);
      if (i == 58)
      {
        j = Character.toUpperCase(paramString.charAt(0));
        if ((j >= 65) && (j <= 90))
        {
          localStringBuffer = new StringBuffer(paramString.length() + 8);
          localStringBuffer.append("file:///");
        }
      }
      else if ((i == 47) && (paramString.charAt(0) == '/'))
      {
        localStringBuffer = new StringBuffer(paramString.length() + 5);
        localStringBuffer.append("file:");
      }
    }
    int i = paramString.indexOf(' ');
    if (i < 0)
    {
      if (localStringBuffer != null)
      {
        localStringBuffer.append(paramString);
        paramString = localStringBuffer.toString();
      }
    }
    else
    {
      if (localStringBuffer == null) {
        localStringBuffer = new StringBuffer(paramString.length());
      }
      for (j = 0; j < i; j++) {
        localStringBuffer.append(paramString.charAt(j));
      }
      localStringBuffer.append("%20");
      for (int k = i + 1; k < paramString.length(); k++) {
        if (paramString.charAt(k) == ' ') {
          localStringBuffer.append("%20");
        } else {
          localStringBuffer.append(paramString.charAt(k));
        }
      }
      paramString = localStringBuffer.toString();
    }
    return paramString;
  }
  
  Hashtable getDeclaredEntities()
  {
    return this.fEntities;
  }
  
  static final void print(ScannedEntity paramScannedEntity) {}
  
  static
  {
    for (int i = 0; i <= 31; i++)
    {
      gNeedEscaping[i] = true;
      gAfterEscaping1[i] = gHexChs[(i >> 4)];
      gAfterEscaping2[i] = gHexChs[(i & 0xF)];
    }
    gNeedEscaping[127] = true;
    gAfterEscaping1[127] = '7';
    gAfterEscaping2[127] = 'F';
    char[] arrayOfChar = { ' ', '<', '>', '#', '%', '"', '{', '}', '|', '\\', '^', '~', '[', ']', '`' };
    int j = arrayOfChar.length;
    for (int m = 0; m < j; m++)
    {
      int k = arrayOfChar[m];
      gNeedEscaping[k] = true;
      gAfterEscaping1[k] = gHexChs[(k >> 4)];
      gAfterEscaping2[k] = gHexChs[(k & 0xF)];
    }
  }
  
  protected final class RewindableInputStream
    extends InputStream
  {
    private InputStream fInputStream;
    private byte[] fData = new byte[64];
    private int fStartOffset;
    private int fEndOffset;
    private int fOffset;
    private int fLength;
    private int fMark;
    
    public RewindableInputStream(InputStream paramInputStream)
    {
      this.fInputStream = paramInputStream;
      this.fStartOffset = 0;
      this.fEndOffset = -1;
      this.fOffset = 0;
      this.fLength = 0;
      this.fMark = 0;
    }
    
    public void setStartOffset(int paramInt)
    {
      this.fStartOffset = paramInt;
    }
    
    public void rewind()
    {
      this.fOffset = this.fStartOffset;
    }
    
    public int read()
      throws IOException
    {
      int i = 0;
      if (this.fOffset < this.fLength) {
        return this.fData[(this.fOffset++)] & 0xFF;
      }
      if (this.fOffset == this.fEndOffset) {
        return -1;
      }
      if (this.fOffset == this.fData.length)
      {
        byte[] arrayOfByte = new byte[this.fOffset << 1];
        System.arraycopy(this.fData, 0, arrayOfByte, 0, this.fOffset);
        this.fData = arrayOfByte;
      }
      i = this.fInputStream.read();
      if (i == -1)
      {
        this.fEndOffset = this.fOffset;
        return -1;
      }
      this.fData[(this.fLength++)] = ((byte)i);
      this.fOffset += 1;
      return i & 0xFF;
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = this.fLength - this.fOffset;
      if (i == 0)
      {
        if (this.fOffset == this.fEndOffset) {
          return -1;
        }
        if (XMLEntityManager.this.fCurrentEntity.mayReadChunks) {
          return this.fInputStream.read(paramArrayOfByte, paramInt1, paramInt2);
        }
        int j = read();
        if (j == -1)
        {
          this.fEndOffset = this.fOffset;
          return -1;
        }
        paramArrayOfByte[paramInt1] = ((byte)j);
        return 1;
      }
      if (paramInt2 < i)
      {
        if (paramInt2 <= 0) {
          return 0;
        }
      }
      else {
        paramInt2 = i;
      }
      if (paramArrayOfByte != null) {
        System.arraycopy(this.fData, this.fOffset, paramArrayOfByte, paramInt1, paramInt2);
      }
      this.fOffset += paramInt2;
      return paramInt2;
    }
    
    public long skip(long paramLong)
      throws IOException
    {
      if (paramLong <= 0L) {
        return 0L;
      }
      int i = this.fLength - this.fOffset;
      if (i == 0)
      {
        if (this.fOffset == this.fEndOffset) {
          return 0L;
        }
        return this.fInputStream.skip(paramLong);
      }
      if (paramLong <= i)
      {
        this.fOffset = ((int)(this.fOffset + paramLong));
        return paramLong;
      }
      this.fOffset += i;
      if (this.fOffset == this.fEndOffset) {
        return i;
      }
      paramLong -= i;
      return this.fInputStream.skip(paramLong) + i;
    }
    
    public int available()
      throws IOException
    {
      int i = this.fLength - this.fOffset;
      if (i == 0)
      {
        if (this.fOffset == this.fEndOffset) {
          return -1;
        }
        return XMLEntityManager.this.fCurrentEntity.mayReadChunks ? this.fInputStream.available() : 0;
      }
      return i;
    }
    
    public void mark(int paramInt)
    {
      this.fMark = this.fOffset;
    }
    
    public void reset()
    {
      this.fOffset = this.fMark;
    }
    
    public boolean markSupported()
    {
      return true;
    }
    
    public void close()
      throws IOException
    {
      if (this.fInputStream != null)
      {
        this.fInputStream.close();
        this.fInputStream = null;
      }
    }
  }
  
  private static final class CharacterBufferPool
  {
    private static final int DEFAULT_POOL_SIZE = 3;
    private XMLEntityManager.CharacterBuffer[] fInternalBufferPool;
    private XMLEntityManager.CharacterBuffer[] fExternalBufferPool;
    private int fExternalBufferSize;
    private int fInternalBufferSize;
    private int fPoolSize;
    private int fInternalTop;
    private int fExternalTop;
    
    public CharacterBufferPool(int paramInt1, int paramInt2)
    {
      this(3, paramInt1, paramInt2);
    }
    
    public CharacterBufferPool(int paramInt1, int paramInt2, int paramInt3)
    {
      this.fExternalBufferSize = paramInt2;
      this.fInternalBufferSize = paramInt3;
      this.fPoolSize = paramInt1;
      init();
    }
    
    private void init()
    {
      this.fInternalBufferPool = new XMLEntityManager.CharacterBuffer[this.fPoolSize];
      this.fExternalBufferPool = new XMLEntityManager.CharacterBuffer[this.fPoolSize];
      this.fInternalTop = -1;
      this.fExternalTop = -1;
    }
    
    public XMLEntityManager.CharacterBuffer getBuffer(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        if (this.fExternalTop > -1) {
          return this.fExternalBufferPool[(this.fExternalTop--)];
        }
        return new XMLEntityManager.CharacterBuffer(true, this.fExternalBufferSize);
      }
      if (this.fInternalTop > -1) {
        return this.fInternalBufferPool[(this.fInternalTop--)];
      }
      return new XMLEntityManager.CharacterBuffer(false, this.fInternalBufferSize);
    }
    
    public void returnBuffer(XMLEntityManager.CharacterBuffer paramCharacterBuffer)
    {
      if (XMLEntityManager.CharacterBuffer.access$500(paramCharacterBuffer))
      {
        if (this.fExternalTop < this.fExternalBufferPool.length - 1) {
          this.fExternalBufferPool[(++this.fExternalTop)] = paramCharacterBuffer;
        }
      }
      else if (this.fInternalTop < this.fInternalBufferPool.length - 1) {
        this.fInternalBufferPool[(++this.fInternalTop)] = paramCharacterBuffer;
      }
    }
    
    public void setExternalBufferSize(int paramInt)
    {
      this.fExternalBufferSize = paramInt;
      this.fExternalBufferPool = new XMLEntityManager.CharacterBuffer[this.fPoolSize];
      this.fExternalTop = -1;
    }
  }
  
  private static final class CharacterBuffer
  {
    private final char[] ch;
    private final boolean isExternal;
    
    public CharacterBuffer(boolean paramBoolean, int paramInt)
    {
      this.isExternal = paramBoolean;
      this.ch = new char[paramInt];
    }
  }
  
  private static final class ByteBufferPool
  {
    private static final int DEFAULT_POOL_SIZE = 3;
    private int fPoolSize;
    private int fBufferSize;
    private byte[][] fByteBufferPool;
    private int fDepth;
    
    public ByteBufferPool(int paramInt)
    {
      this(3, paramInt);
    }
    
    public ByteBufferPool(int paramInt1, int paramInt2)
    {
      this.fPoolSize = paramInt1;
      this.fBufferSize = paramInt2;
      this.fByteBufferPool = new byte[this.fPoolSize][];
      this.fDepth = 0;
    }
    
    public byte[] getBuffer()
    {
      return this.fDepth > 0 ? this.fByteBufferPool[(--this.fDepth)] : new byte[this.fBufferSize];
    }
    
    public void returnBuffer(byte[] paramArrayOfByte)
    {
      if (this.fDepth < this.fByteBufferPool.length) {
        this.fByteBufferPool[(this.fDepth++)] = paramArrayOfByte;
      }
    }
    
    public void setBufferSize(int paramInt)
    {
      this.fBufferSize = paramInt;
      this.fByteBufferPool = new byte[this.fPoolSize][];
      this.fDepth = 0;
    }
  }
  
  public class ScannedEntity
    extends XMLEntityManager.Entity
  {
    public InputStream stream;
    public Reader reader;
    public XMLResourceIdentifier entityLocation;
    public int lineNumber = 1;
    public int columnNumber = 1;
    public String encoding;
    boolean externallySpecifiedEncoding = false;
    public String xmlVersion = "1.0";
    public boolean literal;
    public boolean isExternal;
    public char[] ch = null;
    public int position;
    public int baseCharOffset;
    public int startPosition;
    public int count;
    public boolean mayReadChunks;
    private XMLEntityManager.CharacterBuffer fCharacterBuffer;
    private byte[] fByteBuffer;
    
    public ScannedEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, InputStream paramInputStream, Reader paramReader, byte[] paramArrayOfByte, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      super(XMLEntityManager.this.fInExternalSubset);
      this.entityLocation = paramXMLResourceIdentifier;
      this.stream = paramInputStream;
      this.reader = paramReader;
      this.encoding = paramString2;
      this.literal = paramBoolean1;
      this.mayReadChunks = paramBoolean2;
      this.isExternal = paramBoolean3;
      this.fCharacterBuffer = XMLEntityManager.this.fCharacterBufferPool.getBuffer(paramBoolean3);
      this.ch = this.fCharacterBuffer.ch;
      this.fByteBuffer = paramArrayOfByte;
    }
    
    public final boolean isExternal()
    {
      return this.isExternal;
    }
    
    public final boolean isUnparsed()
    {
      return false;
    }
    
    public void setReader(InputStream paramInputStream, String paramString, Boolean paramBoolean)
      throws IOException
    {
      XMLEntityManager.this.fTempByteBuffer = this.fByteBuffer;
      this.reader = XMLEntityManager.this.createReader(paramInputStream, paramString, paramBoolean);
      this.fByteBuffer = XMLEntityManager.this.fTempByteBuffer;
    }
    
    public String getExpandedSystemId()
    {
      int i = XMLEntityManager.this.fEntityStack.size();
      for (int j = i - 1; j >= 0; j--)
      {
        ScannedEntity localScannedEntity = (ScannedEntity)XMLEntityManager.this.fEntityStack.elementAt(j);
        if ((localScannedEntity.entityLocation != null) && (localScannedEntity.entityLocation.getExpandedSystemId() != null)) {
          return localScannedEntity.entityLocation.getExpandedSystemId();
        }
      }
      return null;
    }
    
    public String getLiteralSystemId()
    {
      int i = XMLEntityManager.this.fEntityStack.size();
      for (int j = i - 1; j >= 0; j--)
      {
        ScannedEntity localScannedEntity = (ScannedEntity)XMLEntityManager.this.fEntityStack.elementAt(j);
        if ((localScannedEntity.entityLocation != null) && (localScannedEntity.entityLocation.getLiteralSystemId() != null)) {
          return localScannedEntity.entityLocation.getLiteralSystemId();
        }
      }
      return null;
    }
    
    public int getLineNumber()
    {
      int i = XMLEntityManager.this.fEntityStack.size();
      for (int j = i - 1; j >= 0; j--)
      {
        ScannedEntity localScannedEntity = (ScannedEntity)XMLEntityManager.this.fEntityStack.elementAt(j);
        if (localScannedEntity.isExternal()) {
          return localScannedEntity.lineNumber;
        }
      }
      return -1;
    }
    
    public int getColumnNumber()
    {
      int i = XMLEntityManager.this.fEntityStack.size();
      for (int j = i - 1; j >= 0; j--)
      {
        ScannedEntity localScannedEntity = (ScannedEntity)XMLEntityManager.this.fEntityStack.elementAt(j);
        if (localScannedEntity.isExternal()) {
          return localScannedEntity.columnNumber;
        }
      }
      return -1;
    }
    
    public int getCharacterOffset()
    {
      int i = XMLEntityManager.this.fEntityStack.size();
      for (int j = i - 1; j >= 0; j--)
      {
        ScannedEntity localScannedEntity = (ScannedEntity)XMLEntityManager.this.fEntityStack.elementAt(j);
        if (localScannedEntity.isExternal()) {
          return localScannedEntity.baseCharOffset + (localScannedEntity.position - localScannedEntity.startPosition);
        }
      }
      return -1;
    }
    
    public String getEncoding()
    {
      int i = XMLEntityManager.this.fEntityStack.size();
      for (int j = i - 1; j >= 0; j--)
      {
        ScannedEntity localScannedEntity = (ScannedEntity)XMLEntityManager.this.fEntityStack.elementAt(j);
        if (localScannedEntity.isExternal()) {
          return localScannedEntity.encoding;
        }
      }
      return null;
    }
    
    public String getXMLVersion()
    {
      int i = XMLEntityManager.this.fEntityStack.size();
      for (int j = i - 1; j >= 0; j--)
      {
        ScannedEntity localScannedEntity = (ScannedEntity)XMLEntityManager.this.fEntityStack.elementAt(j);
        if (localScannedEntity.isExternal()) {
          return localScannedEntity.xmlVersion;
        }
      }
      return null;
    }
    
    public boolean isEncodingExternallySpecified()
    {
      return this.externallySpecifiedEncoding;
    }
    
    public void setEncodingExternallySpecified(boolean paramBoolean)
    {
      this.externallySpecifiedEncoding = paramBoolean;
    }
    
    public String toString()
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("name=\"").append(this.name).append('"');
      localStringBuffer.append(",ch=");
      localStringBuffer.append(this.ch);
      localStringBuffer.append(",position=").append(this.position);
      localStringBuffer.append(",count=").append(this.count);
      localStringBuffer.append(",baseCharOffset=").append(this.baseCharOffset);
      localStringBuffer.append(",startPosition=").append(this.startPosition);
      return localStringBuffer.toString();
    }
  }
  
  protected static class ExternalEntity
    extends XMLEntityManager.Entity
  {
    public XMLResourceIdentifier entityLocation;
    public String notation;
    
    public ExternalEntity()
    {
      clear();
    }
    
    public ExternalEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, boolean paramBoolean)
    {
      super(paramBoolean);
      this.entityLocation = paramXMLResourceIdentifier;
      this.notation = paramString2;
    }
    
    public final boolean isExternal()
    {
      return true;
    }
    
    public final boolean isUnparsed()
    {
      return this.notation != null;
    }
    
    public void clear()
    {
      super.clear();
      this.entityLocation = null;
      this.notation = null;
    }
    
    public void setValues(XMLEntityManager.Entity paramEntity)
    {
      super.setValues(paramEntity);
      this.entityLocation = null;
      this.notation = null;
    }
    
    public void setValues(ExternalEntity paramExternalEntity)
    {
      super.setValues(paramExternalEntity);
      this.entityLocation = paramExternalEntity.entityLocation;
      this.notation = paramExternalEntity.notation;
    }
  }
  
  protected static class InternalEntity
    extends XMLEntityManager.Entity
  {
    public String text;
    
    public InternalEntity()
    {
      clear();
    }
    
    public InternalEntity(String paramString1, String paramString2, boolean paramBoolean)
    {
      super(paramBoolean);
      this.text = paramString2;
    }
    
    public final boolean isExternal()
    {
      return false;
    }
    
    public final boolean isUnparsed()
    {
      return false;
    }
    
    public void clear()
    {
      super.clear();
      this.text = null;
    }
    
    public void setValues(XMLEntityManager.Entity paramEntity)
    {
      super.setValues(paramEntity);
      this.text = null;
    }
    
    public void setValues(InternalEntity paramInternalEntity)
    {
      super.setValues(paramInternalEntity);
      this.text = paramInternalEntity.text;
    }
  }
  
  public static abstract class Entity
  {
    public String name;
    public boolean inExternalSubset;
    
    public Entity()
    {
      clear();
    }
    
    public Entity(String paramString, boolean paramBoolean)
    {
      this.name = paramString;
      this.inExternalSubset = paramBoolean;
    }
    
    public boolean isEntityDeclInExternalSubset()
    {
      return this.inExternalSubset;
    }
    
    public abstract boolean isExternal();
    
    public abstract boolean isUnparsed();
    
    public void clear()
    {
      this.name = null;
      this.inExternalSubset = false;
    }
    
    public void setValues(Entity paramEntity)
    {
      this.name = paramEntity.name;
      this.inExternalSubset = paramEntity.inExternalSubset;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.XMLEntityManager
 * JD-Core Version:    0.7.0.1
 */
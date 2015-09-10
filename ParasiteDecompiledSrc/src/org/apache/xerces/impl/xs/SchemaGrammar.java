package org.apache.xerces.impl.xs;

import java.lang.ref.SoftReference;
import java.util.Vector;
import org.apache.xerces.impl.dv.SchemaDVFactory;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.util.SimpleLocator;
import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.impl.xs.util.XSNamedMap4Types;
import org.apache.xerces.impl.xs.util.XSNamedMapImpl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XSGrammar;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSAttributeGroupDefinition;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroupDefinition;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSNotationDeclaration;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSTerm;
import org.apache.xerces.xs.XSTypeDefinition;
import org.apache.xerces.xs.XSWildcard;
import org.xml.sax.SAXException;

public class SchemaGrammar
  implements XSGrammar, XSNamespaceItem
{
  String fTargetNamespace;
  SymbolHash fGlobalAttrDecls;
  SymbolHash fGlobalAttrGrpDecls;
  SymbolHash fGlobalElemDecls;
  SymbolHash fGlobalGroupDecls;
  SymbolHash fGlobalNotationDecls;
  SymbolHash fGlobalIDConstraintDecls;
  SymbolHash fGlobalTypeDecls;
  XSDDescription fGrammarDescription = null;
  XSAnnotationImpl[] fAnnotations = null;
  int fNumAnnotations;
  private SymbolTable fSymbolTable = null;
  private SoftReference fSAXParser = null;
  private SoftReference fDOMParser = null;
  private static final int BASICSET_COUNT = 29;
  private static final int FULLSET_COUNT = 46;
  private static final int GRAMMAR_XS = 1;
  private static final int GRAMMAR_XSI = 2;
  Vector fImported = null;
  private static final int INITIAL_SIZE = 16;
  private static final int INC_SIZE = 16;
  private int fCTCount = 0;
  private XSComplexTypeDecl[] fComplexTypeDecls = new XSComplexTypeDecl[16];
  private SimpleLocator[] fCTLocators = new SimpleLocator[16];
  private static final int REDEFINED_GROUP_INIT_SIZE = 2;
  private int fRGCount = 0;
  private XSGroupDecl[] fRedefinedGroupDecls = new XSGroupDecl[2];
  private SimpleLocator[] fRGLocators = new SimpleLocator[1];
  boolean fFullChecked = false;
  private int fSubGroupCount = 0;
  private XSElementDecl[] fSubGroups = new XSElementDecl[16];
  public static final XSComplexTypeDecl fAnyType = new XSAnyType();
  public static final BuiltinSchemaGrammar SG_SchemaNS = new BuiltinSchemaGrammar(1);
  public static final Schema4Annotations SG_Schema4Annotations = new Schema4Annotations();
  public static final XSSimpleType fAnySimpleType = (XSSimpleType)SG_SchemaNS.getGlobalTypeDecl("anySimpleType");
  public static final BuiltinSchemaGrammar SG_XSI = new BuiltinSchemaGrammar(2);
  private static final short MAX_COMP_IDX = 16;
  private static final boolean[] GLOBAL_COMP = { false, true, true, true, false, true, true, false, false, false, false, true, false, false, false, true, true };
  private XSNamedMap[] fComponents = null;
  private Vector fDocuments = null;
  private Vector fLocations = null;
  
  protected SchemaGrammar() {}
  
  public SchemaGrammar(String paramString, XSDDescription paramXSDDescription, SymbolTable paramSymbolTable)
  {
    this.fTargetNamespace = paramString;
    this.fGrammarDescription = paramXSDDescription;
    this.fSymbolTable = paramSymbolTable;
    this.fGlobalAttrDecls = new SymbolHash();
    this.fGlobalAttrGrpDecls = new SymbolHash();
    this.fGlobalElemDecls = new SymbolHash();
    this.fGlobalGroupDecls = new SymbolHash();
    this.fGlobalNotationDecls = new SymbolHash();
    this.fGlobalIDConstraintDecls = new SymbolHash();
    if (this.fTargetNamespace == SchemaSymbols.URI_SCHEMAFORSCHEMA) {
      this.fGlobalTypeDecls = SG_SchemaNS.fGlobalTypeDecls.makeClone();
    } else {
      this.fGlobalTypeDecls = new SymbolHash();
    }
  }
  
  public XMLGrammarDescription getGrammarDescription()
  {
    return this.fGrammarDescription;
  }
  
  public boolean isNamespaceAware()
  {
    return true;
  }
  
  public void setImportedGrammars(Vector paramVector)
  {
    this.fImported = paramVector;
  }
  
  public Vector getImportedGrammars()
  {
    return this.fImported;
  }
  
  public final String getTargetNamespace()
  {
    return this.fTargetNamespace;
  }
  
  public void addGlobalAttributeDecl(XSAttributeDecl paramXSAttributeDecl)
  {
    this.fGlobalAttrDecls.put(paramXSAttributeDecl.fName, paramXSAttributeDecl);
  }
  
  public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl paramXSAttributeGroupDecl)
  {
    this.fGlobalAttrGrpDecls.put(paramXSAttributeGroupDecl.fName, paramXSAttributeGroupDecl);
  }
  
  public void addGlobalElementDecl(XSElementDecl paramXSElementDecl)
  {
    this.fGlobalElemDecls.put(paramXSElementDecl.fName, paramXSElementDecl);
    if (paramXSElementDecl.fSubGroup != null)
    {
      if (this.fSubGroupCount == this.fSubGroups.length) {
        this.fSubGroups = resize(this.fSubGroups, this.fSubGroupCount + 16);
      }
      this.fSubGroups[(this.fSubGroupCount++)] = paramXSElementDecl;
    }
  }
  
  public void addGlobalGroupDecl(XSGroupDecl paramXSGroupDecl)
  {
    this.fGlobalGroupDecls.put(paramXSGroupDecl.fName, paramXSGroupDecl);
  }
  
  public void addGlobalNotationDecl(XSNotationDecl paramXSNotationDecl)
  {
    this.fGlobalNotationDecls.put(paramXSNotationDecl.fName, paramXSNotationDecl);
  }
  
  public void addGlobalTypeDecl(XSTypeDefinition paramXSTypeDefinition)
  {
    this.fGlobalTypeDecls.put(paramXSTypeDefinition.getName(), paramXSTypeDefinition);
  }
  
  public final void addIDConstraintDecl(XSElementDecl paramXSElementDecl, IdentityConstraint paramIdentityConstraint)
  {
    paramXSElementDecl.addIDConstraint(paramIdentityConstraint);
    this.fGlobalIDConstraintDecls.put(paramIdentityConstraint.getIdentityConstraintName(), paramIdentityConstraint);
  }
  
  public final XSAttributeDecl getGlobalAttributeDecl(String paramString)
  {
    return (XSAttributeDecl)this.fGlobalAttrDecls.get(paramString);
  }
  
  public final XSAttributeGroupDecl getGlobalAttributeGroupDecl(String paramString)
  {
    return (XSAttributeGroupDecl)this.fGlobalAttrGrpDecls.get(paramString);
  }
  
  public final XSElementDecl getGlobalElementDecl(String paramString)
  {
    return (XSElementDecl)this.fGlobalElemDecls.get(paramString);
  }
  
  public final XSGroupDecl getGlobalGroupDecl(String paramString)
  {
    return (XSGroupDecl)this.fGlobalGroupDecls.get(paramString);
  }
  
  public final XSNotationDecl getGlobalNotationDecl(String paramString)
  {
    return (XSNotationDecl)this.fGlobalNotationDecls.get(paramString);
  }
  
  public final XSTypeDefinition getGlobalTypeDecl(String paramString)
  {
    return (XSTypeDefinition)this.fGlobalTypeDecls.get(paramString);
  }
  
  public final IdentityConstraint getIDConstraintDecl(String paramString)
  {
    return (IdentityConstraint)this.fGlobalIDConstraintDecls.get(paramString);
  }
  
  public final boolean hasIDConstraints()
  {
    return this.fGlobalIDConstraintDecls.getLength() > 0;
  }
  
  public void addComplexTypeDecl(XSComplexTypeDecl paramXSComplexTypeDecl, SimpleLocator paramSimpleLocator)
  {
    if (this.fCTCount == this.fComplexTypeDecls.length)
    {
      this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount + 16);
      this.fCTLocators = resize(this.fCTLocators, this.fCTCount + 16);
    }
    this.fCTLocators[this.fCTCount] = paramSimpleLocator;
    this.fComplexTypeDecls[(this.fCTCount++)] = paramXSComplexTypeDecl;
  }
  
  public void addRedefinedGroupDecl(XSGroupDecl paramXSGroupDecl1, XSGroupDecl paramXSGroupDecl2, SimpleLocator paramSimpleLocator)
  {
    if (this.fRGCount == this.fRedefinedGroupDecls.length)
    {
      this.fRedefinedGroupDecls = resize(this.fRedefinedGroupDecls, this.fRGCount << 1);
      this.fRGLocators = resize(this.fRGLocators, this.fRGCount);
    }
    this.fRGLocators[(this.fRGCount / 2)] = paramSimpleLocator;
    this.fRedefinedGroupDecls[(this.fRGCount++)] = paramXSGroupDecl1;
    this.fRedefinedGroupDecls[(this.fRGCount++)] = paramXSGroupDecl2;
  }
  
  final XSComplexTypeDecl[] getUncheckedComplexTypeDecls()
  {
    if (this.fCTCount < this.fComplexTypeDecls.length)
    {
      this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount);
      this.fCTLocators = resize(this.fCTLocators, this.fCTCount);
    }
    return this.fComplexTypeDecls;
  }
  
  final SimpleLocator[] getUncheckedCTLocators()
  {
    if (this.fCTCount < this.fCTLocators.length)
    {
      this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount);
      this.fCTLocators = resize(this.fCTLocators, this.fCTCount);
    }
    return this.fCTLocators;
  }
  
  final XSGroupDecl[] getRedefinedGroupDecls()
  {
    if (this.fRGCount < this.fRedefinedGroupDecls.length)
    {
      this.fRedefinedGroupDecls = resize(this.fRedefinedGroupDecls, this.fRGCount);
      this.fRGLocators = resize(this.fRGLocators, this.fRGCount / 2);
    }
    return this.fRedefinedGroupDecls;
  }
  
  final SimpleLocator[] getRGLocators()
  {
    if (this.fRGCount < this.fRedefinedGroupDecls.length)
    {
      this.fRedefinedGroupDecls = resize(this.fRedefinedGroupDecls, this.fRGCount);
      this.fRGLocators = resize(this.fRGLocators, this.fRGCount / 2);
    }
    return this.fRGLocators;
  }
  
  final void setUncheckedTypeNum(int paramInt)
  {
    this.fCTCount = paramInt;
    this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount);
    this.fCTLocators = resize(this.fCTLocators, this.fCTCount);
  }
  
  final XSElementDecl[] getSubstitutionGroups()
  {
    if (this.fSubGroupCount < this.fSubGroups.length) {
      this.fSubGroups = resize(this.fSubGroups, this.fSubGroupCount);
    }
    return this.fSubGroups;
  }
  
  static final XSComplexTypeDecl[] resize(XSComplexTypeDecl[] paramArrayOfXSComplexTypeDecl, int paramInt)
  {
    XSComplexTypeDecl[] arrayOfXSComplexTypeDecl = new XSComplexTypeDecl[paramInt];
    System.arraycopy(paramArrayOfXSComplexTypeDecl, 0, arrayOfXSComplexTypeDecl, 0, Math.min(paramArrayOfXSComplexTypeDecl.length, paramInt));
    return arrayOfXSComplexTypeDecl;
  }
  
  static final XSGroupDecl[] resize(XSGroupDecl[] paramArrayOfXSGroupDecl, int paramInt)
  {
    XSGroupDecl[] arrayOfXSGroupDecl = new XSGroupDecl[paramInt];
    System.arraycopy(paramArrayOfXSGroupDecl, 0, arrayOfXSGroupDecl, 0, Math.min(paramArrayOfXSGroupDecl.length, paramInt));
    return arrayOfXSGroupDecl;
  }
  
  static final XSElementDecl[] resize(XSElementDecl[] paramArrayOfXSElementDecl, int paramInt)
  {
    XSElementDecl[] arrayOfXSElementDecl = new XSElementDecl[paramInt];
    System.arraycopy(paramArrayOfXSElementDecl, 0, arrayOfXSElementDecl, 0, Math.min(paramArrayOfXSElementDecl.length, paramInt));
    return arrayOfXSElementDecl;
  }
  
  static final SimpleLocator[] resize(SimpleLocator[] paramArrayOfSimpleLocator, int paramInt)
  {
    SimpleLocator[] arrayOfSimpleLocator = new SimpleLocator[paramInt];
    System.arraycopy(paramArrayOfSimpleLocator, 0, arrayOfSimpleLocator, 0, Math.min(paramArrayOfSimpleLocator.length, paramInt));
    return arrayOfSimpleLocator;
  }
  
  public synchronized void addDocument(Object paramObject, String paramString)
  {
    if (this.fDocuments == null)
    {
      this.fDocuments = new Vector();
      this.fLocations = new Vector();
    }
    this.fDocuments.addElement(paramObject);
    this.fLocations.addElement(paramString);
  }
  
  public String getSchemaNamespace()
  {
    return this.fTargetNamespace;
  }
  
  synchronized DOMParser getDOMParser()
  {
    if (this.fDOMParser != null)
    {
      localObject = (DOMParser)this.fDOMParser.get();
      if (localObject != null) {
        return localObject;
      }
    }
    Object localObject = new XML11Configuration(this.fSymbolTable);
    ((XML11Configuration)localObject).setFeature("http://xml.org/sax/features/namespaces", true);
    ((XML11Configuration)localObject).setFeature("http://xml.org/sax/features/validation", false);
    DOMParser localDOMParser = new DOMParser((XMLParserConfiguration)localObject);
    try
    {
      localDOMParser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
    }
    catch (SAXException localSAXException) {}
    this.fDOMParser = new SoftReference(localDOMParser);
    return localDOMParser;
  }
  
  synchronized SAXParser getSAXParser()
  {
    if (this.fSAXParser != null)
    {
      localObject = (SAXParser)this.fSAXParser.get();
      if (localObject != null) {
        return localObject;
      }
    }
    Object localObject = new XML11Configuration(this.fSymbolTable);
    ((XML11Configuration)localObject).setFeature("http://xml.org/sax/features/namespaces", true);
    ((XML11Configuration)localObject).setFeature("http://xml.org/sax/features/validation", false);
    SAXParser localSAXParser = new SAXParser((XMLParserConfiguration)localObject);
    this.fSAXParser = new SoftReference(localSAXParser);
    return localSAXParser;
  }
  
  public synchronized XSNamedMap getComponents(short paramShort)
  {
    if ((paramShort <= 0) || (paramShort > 16) || (GLOBAL_COMP[paramShort] == 0)) {
      return XSNamedMapImpl.EMPTY_MAP;
    }
    if (this.fComponents == null) {
      this.fComponents = new XSNamedMap[17];
    }
    if (this.fComponents[paramShort] == null)
    {
      SymbolHash localSymbolHash = null;
      switch (paramShort)
      {
      case 3: 
      case 15: 
      case 16: 
        localSymbolHash = this.fGlobalTypeDecls;
        break;
      case 1: 
        localSymbolHash = this.fGlobalAttrDecls;
        break;
      case 2: 
        localSymbolHash = this.fGlobalElemDecls;
        break;
      case 5: 
        localSymbolHash = this.fGlobalAttrGrpDecls;
        break;
      case 6: 
        localSymbolHash = this.fGlobalGroupDecls;
        break;
      case 11: 
        localSymbolHash = this.fGlobalNotationDecls;
      }
      if ((paramShort == 15) || (paramShort == 16)) {
        this.fComponents[paramShort] = new XSNamedMap4Types(this.fTargetNamespace, localSymbolHash, paramShort);
      } else {
        this.fComponents[paramShort] = new XSNamedMapImpl(this.fTargetNamespace, localSymbolHash);
      }
    }
    return this.fComponents[paramShort];
  }
  
  public XSTypeDefinition getTypeDefinition(String paramString)
  {
    return getGlobalTypeDecl(paramString);
  }
  
  public XSAttributeDeclaration getAttributeDeclaration(String paramString)
  {
    return getGlobalAttributeDecl(paramString);
  }
  
  public XSElementDeclaration getElementDeclaration(String paramString)
  {
    return getGlobalElementDecl(paramString);
  }
  
  public XSAttributeGroupDefinition getAttributeGroup(String paramString)
  {
    return getGlobalAttributeGroupDecl(paramString);
  }
  
  public XSModelGroupDefinition getModelGroupDefinition(String paramString)
  {
    return getGlobalGroupDecl(paramString);
  }
  
  public XSNotationDeclaration getNotationDeclaration(String paramString)
  {
    return getGlobalNotationDecl(paramString);
  }
  
  public StringList getDocumentLocations()
  {
    return new StringListImpl(this.fLocations);
  }
  
  public XSModel toXSModel()
  {
    return new XSModelImpl(new SchemaGrammar[] { this });
  }
  
  public XSModel toXSModel(XSGrammar[] paramArrayOfXSGrammar)
  {
    if ((paramArrayOfXSGrammar == null) || (paramArrayOfXSGrammar.length == 0)) {
      return toXSModel();
    }
    int i = paramArrayOfXSGrammar.length;
    int j = 0;
    for (int k = 0; k < i; k++) {
      if (paramArrayOfXSGrammar[k] == this)
      {
        j = 1;
        break;
      }
    }
    SchemaGrammar[] arrayOfSchemaGrammar = new SchemaGrammar[j != 0 ? i : i + 1];
    for (int m = 0; m < i; m++) {
      arrayOfSchemaGrammar[m] = ((SchemaGrammar)paramArrayOfXSGrammar[m]);
    }
    if (j == 0) {
      arrayOfSchemaGrammar[i] = this;
    }
    return new XSModelImpl(arrayOfSchemaGrammar);
  }
  
  public XSObjectList getAnnotations()
  {
    return new XSObjectListImpl(this.fAnnotations, this.fNumAnnotations);
  }
  
  public void addAnnotation(XSAnnotationImpl paramXSAnnotationImpl)
  {
    if (paramXSAnnotationImpl == null) {
      return;
    }
    if (this.fAnnotations == null)
    {
      this.fAnnotations = new XSAnnotationImpl[2];
    }
    else if (this.fNumAnnotations == this.fAnnotations.length)
    {
      XSAnnotationImpl[] arrayOfXSAnnotationImpl = new XSAnnotationImpl[this.fNumAnnotations << 1];
      System.arraycopy(this.fAnnotations, 0, arrayOfXSAnnotationImpl, 0, this.fNumAnnotations);
      this.fAnnotations = arrayOfXSAnnotationImpl;
    }
    this.fAnnotations[(this.fNumAnnotations++)] = paramXSAnnotationImpl;
  }
  
  private static class BuiltinAttrDecl
    extends XSAttributeDecl
  {
    public BuiltinAttrDecl(String paramString1, String paramString2, XSSimpleType paramXSSimpleType, short paramShort)
    {
      this.fName = paramString1;
      this.fTargetNamespace = paramString2;
      this.fType = paramXSSimpleType;
      this.fScope = paramShort;
    }
    
    public void setValues(String paramString1, String paramString2, XSSimpleType paramXSSimpleType, short paramShort1, short paramShort2, ValidatedInfo paramValidatedInfo, XSComplexTypeDecl paramXSComplexTypeDecl) {}
    
    public void reset() {}
    
    public XSAnnotation getAnnotation()
    {
      return null;
    }
  }
  
  private static class XSAnyType
    extends XSComplexTypeDecl
  {
    public XSAnyType()
    {
      this.fName = "anyType";
      this.fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
      this.fBaseType = this;
      this.fDerivedBy = 2;
      this.fContentType = 3;
      this.fParticle = null;
      this.fAttrGrp = null;
    }
    
    public void setValues(String paramString1, String paramString2, XSTypeDefinition paramXSTypeDefinition, short paramShort1, short paramShort2, short paramShort3, short paramShort4, boolean paramBoolean, XSAttributeGroupDecl paramXSAttributeGroupDecl, XSSimpleType paramXSSimpleType, XSParticleDecl paramXSParticleDecl) {}
    
    public void setName(String paramString) {}
    
    public void setIsAbstractType() {}
    
    public void setContainsTypeID() {}
    
    public void setIsAnonymous() {}
    
    public void reset() {}
    
    public XSObjectList getAttributeUses()
    {
      return new XSObjectListImpl(null, 0);
    }
    
    public XSAttributeGroupDecl getAttrGrp()
    {
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      localXSWildcardDecl.fProcessContents = 3;
      XSAttributeGroupDecl localXSAttributeGroupDecl = new XSAttributeGroupDecl();
      localXSAttributeGroupDecl.fAttributeWC = localXSWildcardDecl;
      return localXSAttributeGroupDecl;
    }
    
    public XSWildcard getAttributeWildcard()
    {
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      localXSWildcardDecl.fProcessContents = 3;
      return localXSWildcardDecl;
    }
    
    public XSParticle getParticle()
    {
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      localXSWildcardDecl.fProcessContents = 3;
      XSParticleDecl localXSParticleDecl1 = new XSParticleDecl();
      localXSParticleDecl1.fMinOccurs = 0;
      localXSParticleDecl1.fMaxOccurs = -1;
      localXSParticleDecl1.fType = 2;
      localXSParticleDecl1.fValue = localXSWildcardDecl;
      XSModelGroupImpl localXSModelGroupImpl = new XSModelGroupImpl();
      localXSModelGroupImpl.fCompositor = 102;
      localXSModelGroupImpl.fParticleCount = 1;
      localXSModelGroupImpl.fParticles = new XSParticleDecl[1];
      localXSModelGroupImpl.fParticles[0] = localXSParticleDecl1;
      XSParticleDecl localXSParticleDecl2 = new XSParticleDecl();
      localXSParticleDecl2.fType = 3;
      localXSParticleDecl2.fValue = localXSModelGroupImpl;
      return localXSParticleDecl2;
    }
    
    public XSObjectList getAnnotations()
    {
      return null;
    }
  }
  
  public static final class Schema4Annotations
    extends SchemaGrammar
  {
    public Schema4Annotations()
    {
      this.fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
      this.fGrammarDescription = new XSDDescription();
      this.fGrammarDescription.fContextType = 3;
      this.fGrammarDescription.setNamespace(SchemaSymbols.URI_SCHEMAFORSCHEMA);
      this.fGlobalAttrDecls = new SymbolHash(1);
      this.fGlobalAttrGrpDecls = new SymbolHash(1);
      this.fGlobalElemDecls = new SymbolHash(6);
      this.fGlobalGroupDecls = new SymbolHash(1);
      this.fGlobalNotationDecls = new SymbolHash(1);
      this.fGlobalIDConstraintDecls = new SymbolHash(1);
      this.fGlobalTypeDecls = SchemaGrammar.SG_SchemaNS.fGlobalTypeDecls;
      XSElementDecl localXSElementDecl1 = createAnnotationElementDecl(SchemaSymbols.ELT_ANNOTATION);
      XSElementDecl localXSElementDecl2 = createAnnotationElementDecl(SchemaSymbols.ELT_DOCUMENTATION);
      XSElementDecl localXSElementDecl3 = createAnnotationElementDecl(SchemaSymbols.ELT_APPINFO);
      this.fGlobalElemDecls.put(localXSElementDecl1.fName, localXSElementDecl1);
      this.fGlobalElemDecls.put(localXSElementDecl2.fName, localXSElementDecl2);
      this.fGlobalElemDecls.put(localXSElementDecl3.fName, localXSElementDecl3);
      XSComplexTypeDecl localXSComplexTypeDecl1 = new XSComplexTypeDecl();
      XSComplexTypeDecl localXSComplexTypeDecl2 = new XSComplexTypeDecl();
      XSComplexTypeDecl localXSComplexTypeDecl3 = new XSComplexTypeDecl();
      localXSElementDecl1.fType = localXSComplexTypeDecl1;
      localXSElementDecl2.fType = localXSComplexTypeDecl2;
      localXSElementDecl3.fType = localXSComplexTypeDecl3;
      XSAttributeGroupDecl localXSAttributeGroupDecl1 = new XSAttributeGroupDecl();
      XSAttributeGroupDecl localXSAttributeGroupDecl2 = new XSAttributeGroupDecl();
      XSAttributeGroupDecl localXSAttributeGroupDecl3 = new XSAttributeGroupDecl();
      Object localObject1 = new XSAttributeUseImpl();
      ((XSAttributeUseImpl)localObject1).fAttrDecl = new XSAttributeDecl();
      ((XSAttributeUseImpl)localObject1).fAttrDecl.setValues(SchemaSymbols.ATT_ID, null, (XSSimpleType)this.fGlobalTypeDecls.get("ID"), (short)0, (short)2, null, localXSComplexTypeDecl1, null);
      ((XSAttributeUseImpl)localObject1).fUse = 0;
      ((XSAttributeUseImpl)localObject1).fConstraintType = 0;
      Object localObject2 = new XSAttributeUseImpl();
      ((XSAttributeUseImpl)localObject2).fAttrDecl = new XSAttributeDecl();
      ((XSAttributeUseImpl)localObject2).fAttrDecl.setValues(SchemaSymbols.ATT_SOURCE, null, (XSSimpleType)this.fGlobalTypeDecls.get("anyURI"), (short)0, (short)2, null, localXSComplexTypeDecl2, null);
      ((XSAttributeUseImpl)localObject2).fUse = 0;
      ((XSAttributeUseImpl)localObject2).fConstraintType = 0;
      XSAttributeUseImpl localXSAttributeUseImpl1 = new XSAttributeUseImpl();
      localXSAttributeUseImpl1.fAttrDecl = new XSAttributeDecl();
      localXSAttributeUseImpl1.fAttrDecl.setValues("lang".intern(), NamespaceContext.XML_URI, (XSSimpleType)this.fGlobalTypeDecls.get("language"), (short)0, (short)2, null, localXSComplexTypeDecl2, null);
      localXSAttributeUseImpl1.fUse = 0;
      localXSAttributeUseImpl1.fConstraintType = 0;
      XSAttributeUseImpl localXSAttributeUseImpl2 = new XSAttributeUseImpl();
      localXSAttributeUseImpl2.fAttrDecl = new XSAttributeDecl();
      localXSAttributeUseImpl2.fAttrDecl.setValues(SchemaSymbols.ATT_SOURCE, null, (XSSimpleType)this.fGlobalTypeDecls.get("anyURI"), (short)0, (short)2, null, localXSComplexTypeDecl3, null);
      localXSAttributeUseImpl2.fUse = 0;
      localXSAttributeUseImpl2.fConstraintType = 0;
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      localXSWildcardDecl.fNamespaceList = new String[] { this.fTargetNamespace, null };
      localXSWildcardDecl.fType = 2;
      localXSWildcardDecl.fProcessContents = 3;
      localXSAttributeGroupDecl1.addAttributeUse((XSAttributeUseImpl)localObject1);
      localXSAttributeGroupDecl1.fAttributeWC = localXSWildcardDecl;
      localXSAttributeGroupDecl2.addAttributeUse((XSAttributeUseImpl)localObject2);
      localXSAttributeGroupDecl2.addAttributeUse(localXSAttributeUseImpl1);
      localXSAttributeGroupDecl2.fAttributeWC = localXSWildcardDecl;
      localXSAttributeGroupDecl3.addAttributeUse(localXSAttributeUseImpl2);
      localXSAttributeGroupDecl3.fAttributeWC = localXSWildcardDecl;
      localObject1 = createUnboundedModelGroupParticle();
      localObject2 = new XSModelGroupImpl();
      ((XSModelGroupImpl)localObject2).fCompositor = 101;
      ((XSModelGroupImpl)localObject2).fParticleCount = 2;
      ((XSModelGroupImpl)localObject2).fParticles = new XSParticleDecl[2];
      ((XSModelGroupImpl)localObject2).fParticles[0] = createChoiceElementParticle(localXSElementDecl3);
      ((XSModelGroupImpl)localObject2).fParticles[1] = createChoiceElementParticle(localXSElementDecl2);
      ((XSParticleDecl)localObject1).fValue = ((XSTerm)localObject2);
      localObject2 = createUnboundedAnyWildcardSequenceParticle();
      localXSComplexTypeDecl1.setValues("#AnonType_" + SchemaSymbols.ELT_ANNOTATION, this.fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)2, false, localXSAttributeGroupDecl1, null, (XSParticleDecl)localObject1, new XSObjectListImpl(null, 0));
      localXSComplexTypeDecl1.setName("#AnonType_" + SchemaSymbols.ELT_ANNOTATION);
      localXSComplexTypeDecl1.setIsAnonymous();
      localXSComplexTypeDecl2.setValues("#AnonType_" + SchemaSymbols.ELT_DOCUMENTATION, this.fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)3, false, localXSAttributeGroupDecl2, null, (XSParticleDecl)localObject2, new XSObjectListImpl(null, 0));
      localXSComplexTypeDecl2.setName("#AnonType_" + SchemaSymbols.ELT_DOCUMENTATION);
      localXSComplexTypeDecl2.setIsAnonymous();
      localXSComplexTypeDecl3.setValues("#AnonType_" + SchemaSymbols.ELT_APPINFO, this.fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)3, false, localXSAttributeGroupDecl3, null, (XSParticleDecl)localObject2, new XSObjectListImpl(null, 0));
      localXSComplexTypeDecl3.setName("#AnonType_" + SchemaSymbols.ELT_APPINFO);
      localXSComplexTypeDecl3.setIsAnonymous();
    }
    
    public XMLGrammarDescription getGrammarDescription()
    {
      return this.fGrammarDescription.makeClone();
    }
    
    public void setImportedGrammars(Vector paramVector) {}
    
    public void addGlobalAttributeDecl(XSAttributeDecl paramXSAttributeDecl) {}
    
    public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl paramXSAttributeGroupDecl) {}
    
    public void addGlobalElementDecl(XSElementDecl paramXSElementDecl) {}
    
    public void addGlobalGroupDecl(XSGroupDecl paramXSGroupDecl) {}
    
    public void addGlobalNotationDecl(XSNotationDecl paramXSNotationDecl) {}
    
    public void addGlobalTypeDecl(XSTypeDefinition paramXSTypeDefinition) {}
    
    public void addComplexTypeDecl(XSComplexTypeDecl paramXSComplexTypeDecl, SimpleLocator paramSimpleLocator) {}
    
    public void addRedefinedGroupDecl(XSGroupDecl paramXSGroupDecl1, XSGroupDecl paramXSGroupDecl2, SimpleLocator paramSimpleLocator) {}
    
    public synchronized void addDocument(Object paramObject, String paramString) {}
    
    synchronized DOMParser getDOMParser()
    {
      return null;
    }
    
    synchronized SAXParser getSAXParser()
    {
      return null;
    }
    
    private XSElementDecl createAnnotationElementDecl(String paramString)
    {
      XSElementDecl localXSElementDecl = new XSElementDecl();
      localXSElementDecl.fName = paramString;
      localXSElementDecl.fTargetNamespace = this.fTargetNamespace;
      localXSElementDecl.setIsGlobal();
      localXSElementDecl.fBlock = 7;
      localXSElementDecl.setConstraintType((short)0);
      return localXSElementDecl;
    }
    
    private XSParticleDecl createUnboundedModelGroupParticle()
    {
      XSParticleDecl localXSParticleDecl = new XSParticleDecl();
      localXSParticleDecl.fMinOccurs = 0;
      localXSParticleDecl.fMaxOccurs = -1;
      localXSParticleDecl.fType = 3;
      return localXSParticleDecl;
    }
    
    private XSParticleDecl createChoiceElementParticle(XSElementDecl paramXSElementDecl)
    {
      XSParticleDecl localXSParticleDecl = new XSParticleDecl();
      localXSParticleDecl.fMinOccurs = 1;
      localXSParticleDecl.fMaxOccurs = 1;
      localXSParticleDecl.fType = 1;
      localXSParticleDecl.fValue = paramXSElementDecl;
      return localXSParticleDecl;
    }
    
    private XSParticleDecl createUnboundedAnyWildcardSequenceParticle()
    {
      XSParticleDecl localXSParticleDecl = createUnboundedModelGroupParticle();
      XSModelGroupImpl localXSModelGroupImpl = new XSModelGroupImpl();
      localXSModelGroupImpl.fCompositor = 102;
      localXSModelGroupImpl.fParticleCount = 1;
      localXSModelGroupImpl.fParticles = new XSParticleDecl[1];
      localXSModelGroupImpl.fParticles[0] = createAnyLaxWildcardParticle();
      localXSParticleDecl.fValue = localXSModelGroupImpl;
      return localXSParticleDecl;
    }
    
    private XSParticleDecl createAnyLaxWildcardParticle()
    {
      XSParticleDecl localXSParticleDecl = new XSParticleDecl();
      localXSParticleDecl.fMinOccurs = 1;
      localXSParticleDecl.fMaxOccurs = 1;
      localXSParticleDecl.fType = 2;
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      localXSWildcardDecl.fNamespaceList = null;
      localXSWildcardDecl.fType = 1;
      localXSWildcardDecl.fProcessContents = 3;
      localXSParticleDecl.fValue = localXSWildcardDecl;
      return localXSParticleDecl;
    }
  }
  
  public static class BuiltinSchemaGrammar
    extends SchemaGrammar
  {
    public BuiltinSchemaGrammar(int paramInt)
    {
      SchemaDVFactory localSchemaDVFactory = SchemaDVFactory.getInstance();
      if (paramInt == 1)
      {
        this.fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
        this.fGrammarDescription = new XSDDescription();
        this.fGrammarDescription.fContextType = 3;
        this.fGrammarDescription.setNamespace(SchemaSymbols.URI_SCHEMAFORSCHEMA);
        this.fGlobalAttrDecls = new SymbolHash(1);
        this.fGlobalAttrGrpDecls = new SymbolHash(1);
        this.fGlobalElemDecls = new SymbolHash(1);
        this.fGlobalGroupDecls = new SymbolHash(1);
        this.fGlobalNotationDecls = new SymbolHash(1);
        this.fGlobalIDConstraintDecls = new SymbolHash(1);
        this.fGlobalTypeDecls = localSchemaDVFactory.getBuiltInTypes();
        this.fGlobalTypeDecls.put(SchemaGrammar.fAnyType.getName(), SchemaGrammar.fAnyType);
      }
      else if (paramInt == 2)
      {
        this.fTargetNamespace = SchemaSymbols.URI_XSI;
        this.fGrammarDescription = new XSDDescription();
        this.fGrammarDescription.fContextType = 3;
        this.fGrammarDescription.setNamespace(SchemaSymbols.URI_XSI);
        this.fGlobalAttrGrpDecls = new SymbolHash(1);
        this.fGlobalElemDecls = new SymbolHash(1);
        this.fGlobalGroupDecls = new SymbolHash(1);
        this.fGlobalNotationDecls = new SymbolHash(1);
        this.fGlobalIDConstraintDecls = new SymbolHash(1);
        this.fGlobalTypeDecls = new SymbolHash(1);
        this.fGlobalAttrDecls = new SymbolHash(8);
        String str1 = null;
        String str2 = null;
        Object localObject = null;
        short s = 1;
        str1 = SchemaSymbols.XSI_TYPE;
        str2 = SchemaSymbols.URI_XSI;
        localObject = localSchemaDVFactory.getBuiltInType("QName");
        this.fGlobalAttrDecls.put(str1, new SchemaGrammar.BuiltinAttrDecl(str1, str2, (XSSimpleType)localObject, s));
        str1 = SchemaSymbols.XSI_NIL;
        str2 = SchemaSymbols.URI_XSI;
        localObject = localSchemaDVFactory.getBuiltInType("boolean");
        this.fGlobalAttrDecls.put(str1, new SchemaGrammar.BuiltinAttrDecl(str1, str2, (XSSimpleType)localObject, s));
        XSSimpleType localXSSimpleType = localSchemaDVFactory.getBuiltInType("anyURI");
        str1 = SchemaSymbols.XSI_SCHEMALOCATION;
        str2 = SchemaSymbols.URI_XSI;
        localObject = localSchemaDVFactory.createTypeList(null, SchemaSymbols.URI_XSI, (short)0, localXSSimpleType, null);
        this.fGlobalAttrDecls.put(str1, new SchemaGrammar.BuiltinAttrDecl(str1, str2, (XSSimpleType)localObject, s));
        str1 = SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION;
        str2 = SchemaSymbols.URI_XSI;
        localObject = localXSSimpleType;
        this.fGlobalAttrDecls.put(str1, new SchemaGrammar.BuiltinAttrDecl(str1, str2, (XSSimpleType)localObject, s));
      }
    }
    
    public XMLGrammarDescription getGrammarDescription()
    {
      return this.fGrammarDescription.makeClone();
    }
    
    public void setImportedGrammars(Vector paramVector) {}
    
    public void addGlobalAttributeDecl(XSAttributeDecl paramXSAttributeDecl) {}
    
    public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl paramXSAttributeGroupDecl) {}
    
    public void addGlobalElementDecl(XSElementDecl paramXSElementDecl) {}
    
    public void addGlobalGroupDecl(XSGroupDecl paramXSGroupDecl) {}
    
    public void addGlobalNotationDecl(XSNotationDecl paramXSNotationDecl) {}
    
    public void addGlobalTypeDecl(XSTypeDefinition paramXSTypeDefinition) {}
    
    public void addComplexTypeDecl(XSComplexTypeDecl paramXSComplexTypeDecl, SimpleLocator paramSimpleLocator) {}
    
    public void addRedefinedGroupDecl(XSGroupDecl paramXSGroupDecl1, XSGroupDecl paramXSGroupDecl2, SimpleLocator paramSimpleLocator) {}
    
    public synchronized void addDocument(Object paramObject, String paramString) {}
    
    synchronized DOMParser getDOMParser()
    {
      return null;
    }
    
    synchronized SAXParser getSAXParser()
    {
      return null;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.SchemaGrammar
 * JD-Core Version:    0.7.0.1
 */
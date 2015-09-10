package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.dv.DatatypeException;
import org.apache.xerces.impl.dv.InvalidDatatypeFacetException;
import org.apache.xerces.impl.dv.SchemaDVFactory;
import org.apache.xerces.impl.dv.XSFacets;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.validation.ValidationState;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSAttributeDecl;
import org.apache.xerces.impl.xs.XSAttributeGroupDecl;
import org.apache.xerces.impl.xs.XSAttributeUseImpl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSConstraints;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.XSWildcardDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSAttributeUse;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTerm;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class XSDComplexTypeTraverser
  extends XSDAbstractParticleTraverser
{
  private static final int GLOBAL_NUM = 11;
  private String fName = null;
  private String fTargetNamespace = null;
  private short fDerivedBy = 2;
  private short fFinal = 0;
  private short fBlock = 0;
  private short fContentType = 0;
  private XSTypeDefinition fBaseType = null;
  private XSAttributeGroupDecl fAttrGrp = null;
  private XSSimpleType fXSSimpleType = null;
  private XSParticleDecl fParticle = null;
  private boolean fIsAbstract = false;
  private XSComplexTypeDecl fComplexTypeDecl = null;
  private XSAnnotationImpl[] fAnnotations = null;
  private XSParticleDecl fEmptyParticle = null;
  private Object[] fGlobalStore = null;
  private int fGlobalStorePos = 0;
  private static final boolean DEBUG = false;
  private SchemaDVFactory schemaFactory = SchemaDVFactory.getInstance();
  
  XSDComplexTypeTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  XSComplexTypeDecl traverseLocal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    String str = genAnonTypeName(paramElement);
    contentBackup();
    XSComplexTypeDecl localXSComplexTypeDecl = traverseComplexTypeDecl(paramElement, str, arrayOfObject, paramXSDocumentInfo, paramSchemaGrammar);
    contentRestore();
    paramSchemaGrammar.addComplexTypeDecl(localXSComplexTypeDecl, this.fSchemaHandler.element2Locator(paramElement));
    localXSComplexTypeDecl.setIsAnonymous();
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSComplexTypeDecl;
  }
  
  XSComplexTypeDecl traverseGlobal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, true, paramXSDocumentInfo);
    String str = (String)arrayOfObject[XSAttributeChecker.ATTIDX_NAME];
    contentBackup();
    XSComplexTypeDecl localXSComplexTypeDecl = traverseComplexTypeDecl(paramElement, str, arrayOfObject, paramXSDocumentInfo, paramSchemaGrammar);
    contentRestore();
    if (str == null) {
      reportSchemaError("s4s-att-must-appear", new Object[] { SchemaSymbols.ELT_COMPLEXTYPE, SchemaSymbols.ATT_NAME }, paramElement);
    } else {
      paramSchemaGrammar.addGlobalTypeDecl(localXSComplexTypeDecl);
    }
    paramSchemaGrammar.addComplexTypeDecl(localXSComplexTypeDecl, this.fSchemaHandler.element2Locator(paramElement));
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSComplexTypeDecl;
  }
  
  private XSComplexTypeDecl traverseComplexTypeDecl(Element paramElement, String paramString, Object[] paramArrayOfObject, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    this.fComplexTypeDecl = new XSComplexTypeDecl();
    this.fAttrGrp = new XSAttributeGroupDecl();
    Boolean localBoolean1 = (Boolean)paramArrayOfObject[XSAttributeChecker.ATTIDX_ABSTRACT];
    XInt localXInt1 = (XInt)paramArrayOfObject[XSAttributeChecker.ATTIDX_BLOCK];
    Boolean localBoolean2 = (Boolean)paramArrayOfObject[XSAttributeChecker.ATTIDX_MIXED];
    XInt localXInt2 = (XInt)paramArrayOfObject[XSAttributeChecker.ATTIDX_FINAL];
    this.fName = paramString;
    this.fComplexTypeDecl.setName(this.fName);
    this.fTargetNamespace = paramXSDocumentInfo.fTargetNamespace;
    this.fBlock = (localXInt1 == null ? paramXSDocumentInfo.fBlockDefault : localXInt1.shortValue());
    this.fFinal = (localXInt2 == null ? paramXSDocumentInfo.fFinalDefault : localXInt2.shortValue());
    this.fBlock = ((short)(this.fBlock & 0x3));
    this.fFinal = ((short)(this.fFinal & 0x3));
    this.fIsAbstract = ((localBoolean1 != null) && (localBoolean1.booleanValue()));
    this.fAnnotations = null;
    Element localElement = null;
    try
    {
      localElement = DOMUtil.getFirstChildElement(paramElement);
      Object localObject;
      if (localElement != null)
      {
        if (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION))
        {
          addAnnotation(traverseAnnotationDecl(localElement, paramArrayOfObject, false, paramXSDocumentInfo));
          localElement = DOMUtil.getNextSiblingElement(localElement);
        }
        else
        {
          localObject = DOMUtil.getSyntheticAnnotation(paramElement);
          if (localObject != null) {
            addAnnotation(traverseSyntheticAnnotation(paramElement, (String)localObject, paramArrayOfObject, false, paramXSDocumentInfo));
          }
        }
        if ((localElement != null) && (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION))) {
          throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, SchemaSymbols.ELT_ANNOTATION }, localElement);
        }
      }
      else
      {
        localObject = DOMUtil.getSyntheticAnnotation(paramElement);
        if (localObject != null) {
          addAnnotation(traverseSyntheticAnnotation(paramElement, (String)localObject, paramArrayOfObject, false, paramXSDocumentInfo));
        }
      }
      if (localElement == null)
      {
        this.fBaseType = SchemaGrammar.fAnyType;
        processComplexContent(localElement, localBoolean2.booleanValue(), false, paramXSDocumentInfo, paramSchemaGrammar);
      }
      else
      {
        String str;
        if (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_SIMPLECONTENT))
        {
          traverseSimpleContent(localElement, paramXSDocumentInfo, paramSchemaGrammar);
          localObject = DOMUtil.getNextSiblingElement(localElement);
          if (localObject != null)
          {
            str = DOMUtil.getLocalName((Node)localObject);
            throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, str }, (Element)localObject);
          }
        }
        else if (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_COMPLEXCONTENT))
        {
          traverseComplexContent(localElement, localBoolean2.booleanValue(), paramXSDocumentInfo, paramSchemaGrammar);
          localObject = DOMUtil.getNextSiblingElement(localElement);
          if (localObject != null)
          {
            str = DOMUtil.getLocalName((Node)localObject);
            throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, str }, (Element)localObject);
          }
        }
        else
        {
          this.fBaseType = SchemaGrammar.fAnyType;
          processComplexContent(localElement, localBoolean2.booleanValue(), false, paramXSDocumentInfo, paramSchemaGrammar);
        }
      }
    }
    catch (ComplexTypeRecoverableError localComplexTypeRecoverableError)
    {
      handleComplexTypeError(localComplexTypeRecoverableError.getMessage(), localComplexTypeRecoverableError.errorSubstText, localComplexTypeRecoverableError.errorElem);
    }
    this.fComplexTypeDecl.setValues(this.fName, this.fTargetNamespace, this.fBaseType, this.fDerivedBy, this.fFinal, this.fBlock, this.fContentType, this.fIsAbstract, this.fAttrGrp, this.fXSSimpleType, this.fParticle, new XSObjectListImpl(this.fAnnotations, this.fAnnotations == null ? 0 : this.fAnnotations.length));
    return this.fComplexTypeDecl;
  }
  
  private void traverseSimpleContent(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
    throws XSDComplexTypeTraverser.ComplexTypeRecoverableError
  {
    Object[] arrayOfObject1 = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    this.fContentType = 1;
    this.fParticle = null;
    Element localElement1 = DOMUtil.getFirstChildElement(paramElement);
    if ((localElement1 != null) && (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      addAnnotation(traverseAnnotationDecl(localElement1, arrayOfObject1, false, paramXSDocumentInfo));
      localElement1 = DOMUtil.getNextSiblingElement(localElement1);
    }
    else
    {
      str = DOMUtil.getSyntheticAnnotation(paramElement);
      if (str != null) {
        addAnnotation(traverseSyntheticAnnotation(paramElement, str, arrayOfObject1, false, paramXSDocumentInfo));
      }
    }
    if (localElement1 == null)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
      throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.2", new Object[] { this.fName, SchemaSymbols.ELT_SIMPLECONTENT }, paramElement);
    }
    String str = DOMUtil.getLocalName(localElement1);
    if (str.equals(SchemaSymbols.ELT_RESTRICTION))
    {
      this.fDerivedBy = 2;
    }
    else if (str.equals(SchemaSymbols.ELT_EXTENSION))
    {
      this.fDerivedBy = 1;
    }
    else
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
      throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, str }, localElement1);
    }
    Element localElement2 = DOMUtil.getNextSiblingElement(localElement1);
    if (localElement2 != null)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
      localObject1 = DOMUtil.getLocalName(localElement2);
      throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, localObject1 }, localElement2);
    }
    Object localObject1 = this.fAttrChecker.checkAttributes(localElement1, false, paramXSDocumentInfo);
    QName localQName = (QName)localObject1[XSAttributeChecker.ATTIDX_BASE];
    if (localQName == null)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
      this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
      throw new ComplexTypeRecoverableError("s4s-att-must-appear", new Object[] { str, "base" }, localElement1);
    }
    XSTypeDefinition localXSTypeDefinition = (XSTypeDefinition)this.fSchemaHandler.getGlobalDecl(paramXSDocumentInfo, 7, localQName, localElement1);
    if (localXSTypeDefinition == null)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
      this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
      throw new ComplexTypeRecoverableError();
    }
    this.fBaseType = localXSTypeDefinition;
    Object localObject2 = null;
    XSComplexTypeDecl localXSComplexTypeDecl = null;
    int i = 0;
    if (localXSTypeDefinition.getTypeCategory() == 15)
    {
      localXSComplexTypeDecl = (XSComplexTypeDecl)localXSTypeDefinition;
      i = localXSComplexTypeDecl.getFinal();
      if (localXSComplexTypeDecl.getContentType() == 1)
      {
        localObject2 = (XSSimpleType)localXSComplexTypeDecl.getSimpleType();
      }
      else if ((this.fDerivedBy != 2) || (localXSComplexTypeDecl.getContentType() != 3) || (!((XSParticleDecl)localXSComplexTypeDecl.getParticle()).emptiable()))
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw new ComplexTypeRecoverableError("src-ct.2.1", new Object[] { this.fName, localXSComplexTypeDecl.getName() }, localElement1);
      }
    }
    else
    {
      localObject2 = (XSSimpleType)localXSTypeDefinition;
      if (this.fDerivedBy == 2)
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw new ComplexTypeRecoverableError("src-ct.2.1", new Object[] { this.fName, ((XSObject)localObject2).getName() }, localElement1);
      }
      i = ((XSTypeDefinition)localObject2).getFinal();
    }
    if ((i & this.fDerivedBy) != 0)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
      this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
      localObject3 = this.fDerivedBy == 1 ? "cos-ct-extends.1.1" : "derivation-ok-restriction.1";
      throw new ComplexTypeRecoverableError((String)localObject3, new Object[] { this.fName, this.fBaseType.getName() }, localElement1);
    }
    Object localObject3 = localElement1;
    localElement1 = DOMUtil.getFirstChildElement(localElement1);
    Object localObject4;
    if (localElement1 != null)
    {
      if (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_ANNOTATION))
      {
        addAnnotation(traverseAnnotationDecl(localElement1, (Object[])localObject1, false, paramXSDocumentInfo));
        localElement1 = DOMUtil.getNextSiblingElement(localElement1);
      }
      else
      {
        localObject4 = DOMUtil.getSyntheticAnnotation((Node)localObject3);
        if (localObject4 != null) {
          addAnnotation(traverseSyntheticAnnotation((Element)localObject3, (String)localObject4, (Object[])localObject1, false, paramXSDocumentInfo));
        }
      }
      if ((localElement1 != null) && (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_ANNOTATION)))
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, SchemaSymbols.ELT_ANNOTATION }, localElement1);
      }
    }
    else
    {
      localObject4 = DOMUtil.getSyntheticAnnotation((Node)localObject3);
      if (localObject4 != null) {
        addAnnotation(traverseSyntheticAnnotation((Element)localObject3, (String)localObject4, (Object[])localObject1, false, paramXSDocumentInfo));
      }
    }
    Object localObject5;
    if (this.fDerivedBy == 2)
    {
      if ((localElement1 != null) && (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_SIMPLETYPE)))
      {
        localObject4 = this.fSchemaHandler.fSimpleTypeTraverser.traverseLocal(localElement1, paramXSDocumentInfo, paramSchemaGrammar);
        if (localObject4 == null)
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError();
        }
        if ((localObject2 != null) && (!XSConstraints.checkSimpleDerivationOk((XSSimpleType)localObject4, (XSTypeDefinition)localObject2, ((XSTypeDefinition)localObject2).getFinal())))
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError("derivation-ok-restriction.5.2.2.1", new Object[] { this.fName, ((XSObject)localObject4).getName(), ((XSObject)localObject2).getName() }, localElement1);
        }
        localObject2 = localObject4;
        localElement1 = DOMUtil.getNextSiblingElement(localElement1);
      }
      if (localObject2 == null)
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw new ComplexTypeRecoverableError("src-ct.2.2", new Object[] { this.fName }, localElement1);
      }
      localObject4 = null;
      localObject5 = null;
      short s1 = 0;
      short s2 = 0;
      if (localElement1 != null)
      {
        XSDAbstractTraverser.FacetInfo localFacetInfo = traverseFacets(localElement1, (XSSimpleType)localObject2, paramXSDocumentInfo);
        localObject4 = localFacetInfo.nodeAfterFacets;
        localObject5 = localFacetInfo.facetdata;
        s1 = localFacetInfo.fPresentFacets;
        s2 = localFacetInfo.fFixedFacets;
      }
      this.fXSSimpleType = this.schemaFactory.createTypeRestriction(null, paramXSDocumentInfo.fTargetNamespace, (short)0, (XSSimpleType)localObject2, null);
      try
      {
        this.fValidationState.setNamespaceSupport(paramXSDocumentInfo.fNamespaceSupport);
        this.fXSSimpleType.applyFacets((XSFacets)localObject5, s1, s2, this.fValidationState);
      }
      catch (InvalidDatatypeFacetException localInvalidDatatypeFacetException)
      {
        reportSchemaError(localInvalidDatatypeFacetException.getKey(), localInvalidDatatypeFacetException.getArgs(), localElement1);
      }
      if (localObject4 != null)
      {
        if (!isAttrOrAttrGroup((Element)localObject4))
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, DOMUtil.getLocalName((Node)localObject4) }, (Element)localObject4);
        }
        Element localElement3 = traverseAttrsAndAttrGrps((Element)localObject4, this.fAttrGrp, paramXSDocumentInfo, paramSchemaGrammar, this.fComplexTypeDecl);
        if (localElement3 != null)
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, DOMUtil.getLocalName(localElement3) }, localElement3);
        }
      }
      try
      {
        mergeAttributes(localXSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, false, paramElement);
      }
      catch (ComplexTypeRecoverableError localComplexTypeRecoverableError2)
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw localComplexTypeRecoverableError2;
      }
      this.fAttrGrp.removeProhibitedAttrs();
      Object[] arrayOfObject2 = this.fAttrGrp.validRestrictionOf(this.fName, localXSComplexTypeDecl.getAttrGrp());
      if (arrayOfObject2 != null)
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw new ComplexTypeRecoverableError((String)arrayOfObject2[(arrayOfObject2.length - 1)], arrayOfObject2, (Element)localObject4);
      }
    }
    else
    {
      this.fXSSimpleType = ((XSSimpleType)localObject2);
      if (localElement1 != null)
      {
        localObject4 = localElement1;
        if (!isAttrOrAttrGroup((Element)localObject4))
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, DOMUtil.getLocalName((Node)localObject4) }, (Element)localObject4);
        }
        localObject5 = traverseAttrsAndAttrGrps((Element)localObject4, this.fAttrGrp, paramXSDocumentInfo, paramSchemaGrammar, this.fComplexTypeDecl);
        if (localObject5 != null)
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, DOMUtil.getLocalName((Node)localObject5) }, (Element)localObject5);
        }
        this.fAttrGrp.removeProhibitedAttrs();
      }
      if (localXSComplexTypeDecl != null) {
        try
        {
          mergeAttributes(localXSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, true, paramElement);
        }
        catch (ComplexTypeRecoverableError localComplexTypeRecoverableError1)
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw localComplexTypeRecoverableError1;
        }
      }
    }
    this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
    this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
  }
  
  private void traverseComplexContent(Element paramElement, boolean paramBoolean, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
    throws XSDComplexTypeTraverser.ComplexTypeRecoverableError
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    boolean bool = paramBoolean;
    Boolean localBoolean = (Boolean)arrayOfObject[XSAttributeChecker.ATTIDX_MIXED];
    if (localBoolean != null) {
      bool = localBoolean.booleanValue();
    }
    this.fXSSimpleType = null;
    Element localElement1 = DOMUtil.getFirstChildElement(paramElement);
    if ((localElement1 != null) && (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      addAnnotation(traverseAnnotationDecl(localElement1, arrayOfObject, false, paramXSDocumentInfo));
      localElement1 = DOMUtil.getNextSiblingElement(localElement1);
    }
    else
    {
      str1 = DOMUtil.getSyntheticAnnotation(paramElement);
      if (str1 != null) {
        addAnnotation(traverseSyntheticAnnotation(paramElement, str1, arrayOfObject, false, paramXSDocumentInfo));
      }
    }
    if (localElement1 == null)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.2", new Object[] { this.fName, SchemaSymbols.ELT_COMPLEXCONTENT }, paramElement);
    }
    String str1 = DOMUtil.getLocalName(localElement1);
    if (str1.equals(SchemaSymbols.ELT_RESTRICTION))
    {
      this.fDerivedBy = 2;
    }
    else if (str1.equals(SchemaSymbols.ELT_EXTENSION))
    {
      this.fDerivedBy = 1;
    }
    else
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, str1 }, localElement1);
    }
    Element localElement2 = DOMUtil.getNextSiblingElement(localElement1);
    if (localElement2 != null)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      localObject1 = DOMUtil.getLocalName(localElement2);
      throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, localObject1 }, localElement2);
    }
    Object localObject1 = this.fAttrChecker.checkAttributes(localElement1, false, paramXSDocumentInfo);
    QName localQName = (QName)localObject1[XSAttributeChecker.ATTIDX_BASE];
    if (localQName == null)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
      throw new ComplexTypeRecoverableError("s4s-att-must-appear", new Object[] { str1, "base" }, localElement1);
    }
    XSTypeDefinition localXSTypeDefinition = (XSTypeDefinition)this.fSchemaHandler.getGlobalDecl(paramXSDocumentInfo, 7, localQName, localElement1);
    if (localXSTypeDefinition == null)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
      throw new ComplexTypeRecoverableError();
    }
    if (!(localXSTypeDefinition instanceof XSComplexTypeDecl))
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
      throw new ComplexTypeRecoverableError("src-ct.1", new Object[] { this.fName, localXSTypeDefinition.getName() }, localElement1);
    }
    XSComplexTypeDecl localXSComplexTypeDecl = (XSComplexTypeDecl)localXSTypeDefinition;
    this.fBaseType = localXSComplexTypeDecl;
    String str2;
    if ((localXSComplexTypeDecl.getFinal() & this.fDerivedBy) != 0)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
      str2 = this.fDerivedBy == 1 ? "cos-ct-extends.1.1" : "derivation-ok-restriction.1";
      throw new ComplexTypeRecoverableError(str2, new Object[] { this.fName, this.fBaseType.getName() }, localElement1);
    }
    localElement1 = DOMUtil.getFirstChildElement(localElement1);
    if (localElement1 != null)
    {
      if (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_ANNOTATION))
      {
        addAnnotation(traverseAnnotationDecl(localElement1, (Object[])localObject1, false, paramXSDocumentInfo));
        localElement1 = DOMUtil.getNextSiblingElement(localElement1);
      }
      else
      {
        str2 = DOMUtil.getSyntheticAnnotation(localElement1);
        if (str2 != null) {
          addAnnotation(traverseSyntheticAnnotation(localElement1, str2, (Object[])localObject1, false, paramXSDocumentInfo));
        }
      }
      if ((localElement1 != null) && (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_ANNOTATION)))
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, SchemaSymbols.ELT_ANNOTATION }, localElement1);
      }
    }
    else
    {
      str2 = DOMUtil.getSyntheticAnnotation(localElement1);
      if (str2 != null) {
        addAnnotation(traverseSyntheticAnnotation(localElement1, str2, (Object[])localObject1, false, paramXSDocumentInfo));
      }
    }
    try
    {
      processComplexContent(localElement1, bool, true, paramXSDocumentInfo, paramSchemaGrammar);
    }
    catch (ComplexTypeRecoverableError localComplexTypeRecoverableError1)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
      throw localComplexTypeRecoverableError1;
    }
    XSParticleDecl localXSParticleDecl1 = (XSParticleDecl)localXSComplexTypeDecl.getParticle();
    Object localObject2;
    if (this.fDerivedBy == 2)
    {
      if ((this.fContentType == 3) && (localXSComplexTypeDecl.getContentType() != 3))
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw new ComplexTypeRecoverableError("derivation-ok-restriction.5.4.1.2", new Object[] { this.fName, localXSComplexTypeDecl.getName() }, localElement1);
      }
      try
      {
        mergeAttributes(localXSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, false, localElement1);
      }
      catch (ComplexTypeRecoverableError localComplexTypeRecoverableError2)
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw localComplexTypeRecoverableError2;
      }
      this.fAttrGrp.removeProhibitedAttrs();
      if (localXSComplexTypeDecl != SchemaGrammar.fAnyType)
      {
        localObject2 = this.fAttrGrp.validRestrictionOf(this.fName, localXSComplexTypeDecl.getAttrGrp());
        if (localObject2 != null)
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError((String)localObject2[(localObject2.length - 1)], (Object[])localObject2, localElement1);
        }
      }
    }
    else
    {
      if (this.fParticle == null)
      {
        this.fContentType = localXSComplexTypeDecl.getContentType();
        this.fXSSimpleType = ((XSSimpleType)localXSComplexTypeDecl.getSimpleType());
        this.fParticle = localXSParticleDecl1;
      }
      else if (localXSComplexTypeDecl.getContentType() != 0)
      {
        if ((this.fContentType == 2) && (localXSComplexTypeDecl.getContentType() != 2))
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError("cos-ct-extends.1.4.3.2.2.1.a", new Object[] { this.fName }, localElement1);
        }
        if ((this.fContentType == 3) && (localXSComplexTypeDecl.getContentType() != 3))
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError("cos-ct-extends.1.4.3.2.2.1.b", new Object[] { this.fName }, localElement1);
        }
        if (((this.fParticle.fType == 3) && (((XSModelGroupImpl)this.fParticle.fValue).fCompositor == 103)) || ((((XSParticleDecl)localXSComplexTypeDecl.getParticle()).fType == 3) && (((XSModelGroupImpl)((XSParticleDecl)localXSComplexTypeDecl.getParticle()).fValue).fCompositor == 103)))
        {
          this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
          this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
          throw new ComplexTypeRecoverableError("cos-all-limited.1.2", new Object[0], localElement1);
        }
        localObject2 = new XSModelGroupImpl();
        ((XSModelGroupImpl)localObject2).fCompositor = 102;
        ((XSModelGroupImpl)localObject2).fParticleCount = 2;
        ((XSModelGroupImpl)localObject2).fParticles = new XSParticleDecl[2];
        ((XSModelGroupImpl)localObject2).fParticles[0] = ((XSParticleDecl)localXSComplexTypeDecl.getParticle());
        ((XSModelGroupImpl)localObject2).fParticles[1] = this.fParticle;
        ((XSModelGroupImpl)localObject2).fAnnotations = XSObjectListImpl.EMPTY_LIST;
        XSParticleDecl localXSParticleDecl2 = new XSParticleDecl();
        localXSParticleDecl2.fType = 3;
        localXSParticleDecl2.fValue = ((XSTerm)localObject2);
        localXSParticleDecl2.fAnnotations = XSObjectListImpl.EMPTY_LIST;
        this.fParticle = localXSParticleDecl2;
      }
      this.fAttrGrp.removeProhibitedAttrs();
      try
      {
        mergeAttributes(localXSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, true, localElement1);
      }
      catch (ComplexTypeRecoverableError localComplexTypeRecoverableError3)
      {
        this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
        this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
        throw localComplexTypeRecoverableError3;
      }
    }
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    this.fAttrChecker.returnAttrArray((Object[])localObject1, paramXSDocumentInfo);
  }
  
  private void mergeAttributes(XSAttributeGroupDecl paramXSAttributeGroupDecl1, XSAttributeGroupDecl paramXSAttributeGroupDecl2, String paramString, boolean paramBoolean, Element paramElement)
    throws XSDComplexTypeTraverser.ComplexTypeRecoverableError
  {
    XSObjectList localXSObjectList = paramXSAttributeGroupDecl1.getAttributeUses();
    Object localObject = null;
    XSAttributeUseImpl localXSAttributeUseImpl = null;
    int i = localXSObjectList.getLength();
    for (int j = 0; j < i; j++)
    {
      localXSAttributeUseImpl = (XSAttributeUseImpl)localXSObjectList.item(j);
      XSAttributeUse localXSAttributeUse = paramXSAttributeGroupDecl2.getAttributeUse(localXSAttributeUseImpl.fAttrDecl.getNamespace(), localXSAttributeUseImpl.fAttrDecl.getName());
      if (localXSAttributeUse == null)
      {
        String str = paramXSAttributeGroupDecl2.addAttributeUse(localXSAttributeUseImpl);
        if (str != null) {
          throw new ComplexTypeRecoverableError("ct-props-correct.5", new Object[] { paramString, str, localXSAttributeUseImpl.fAttrDecl.getName() }, paramElement);
        }
      }
      else if (paramBoolean)
      {
        throw new ComplexTypeRecoverableError("ct-props-correct.4", new Object[] { paramString, localXSAttributeUseImpl.fAttrDecl.getName() }, paramElement);
      }
    }
    if (paramBoolean) {
      if (paramXSAttributeGroupDecl2.fAttributeWC == null)
      {
        paramXSAttributeGroupDecl2.fAttributeWC = paramXSAttributeGroupDecl1.fAttributeWC;
      }
      else if (paramXSAttributeGroupDecl1.fAttributeWC != null)
      {
        paramXSAttributeGroupDecl2.fAttributeWC = paramXSAttributeGroupDecl2.fAttributeWC.performUnionWith(paramXSAttributeGroupDecl1.fAttributeWC, paramXSAttributeGroupDecl2.fAttributeWC.fProcessContents);
        if (paramXSAttributeGroupDecl2.fAttributeWC == null) {
          throw new ComplexTypeRecoverableError("src-ct.5", new Object[] { paramString }, paramElement);
        }
      }
    }
  }
  
  private void processComplexContent(Element paramElement, boolean paramBoolean1, boolean paramBoolean2, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
    throws XSDComplexTypeTraverser.ComplexTypeRecoverableError
  {
    Element localElement = null;
    XSParticleDecl localXSParticleDecl = null;
    int i = 0;
    Object localObject;
    if (paramElement != null)
    {
      localObject = DOMUtil.getLocalName(paramElement);
      if (((String)localObject).equals(SchemaSymbols.ELT_GROUP))
      {
        localXSParticleDecl = this.fSchemaHandler.fGroupTraverser.traverseLocal(paramElement, paramXSDocumentInfo, paramSchemaGrammar);
        localElement = DOMUtil.getNextSiblingElement(paramElement);
      }
      else
      {
        XSModelGroupImpl localXSModelGroupImpl;
        if (((String)localObject).equals(SchemaSymbols.ELT_SEQUENCE))
        {
          localXSParticleDecl = traverseSequence(paramElement, paramXSDocumentInfo, paramSchemaGrammar, 0, this.fComplexTypeDecl);
          if (localXSParticleDecl != null)
          {
            localXSModelGroupImpl = (XSModelGroupImpl)localXSParticleDecl.fValue;
            if (localXSModelGroupImpl.fParticleCount == 0) {
              i = 1;
            }
          }
          localElement = DOMUtil.getNextSiblingElement(paramElement);
        }
        else if (((String)localObject).equals(SchemaSymbols.ELT_CHOICE))
        {
          localXSParticleDecl = traverseChoice(paramElement, paramXSDocumentInfo, paramSchemaGrammar, 0, this.fComplexTypeDecl);
          if ((localXSParticleDecl != null) && (localXSParticleDecl.fMinOccurs == 0))
          {
            localXSModelGroupImpl = (XSModelGroupImpl)localXSParticleDecl.fValue;
            if (localXSModelGroupImpl.fParticleCount == 0) {
              i = 1;
            }
          }
          localElement = DOMUtil.getNextSiblingElement(paramElement);
        }
        else if (((String)localObject).equals(SchemaSymbols.ELT_ALL))
        {
          localXSParticleDecl = traverseAll(paramElement, paramXSDocumentInfo, paramSchemaGrammar, 8, this.fComplexTypeDecl);
          if (localXSParticleDecl != null)
          {
            localXSModelGroupImpl = (XSModelGroupImpl)localXSParticleDecl.fValue;
            if (localXSModelGroupImpl.fParticleCount == 0) {
              i = 1;
            }
          }
          localElement = DOMUtil.getNextSiblingElement(paramElement);
        }
        else
        {
          localElement = paramElement;
        }
      }
    }
    if (i != 0)
    {
      localObject = DOMUtil.getFirstChildElement(paramElement);
      if ((localObject != null) && (DOMUtil.getLocalName((Node)localObject).equals(SchemaSymbols.ELT_ANNOTATION))) {
        localObject = DOMUtil.getNextSiblingElement((Node)localObject);
      }
      if (localObject == null) {
        localXSParticleDecl = null;
      }
    }
    if ((localXSParticleDecl == null) && (paramBoolean1))
    {
      if (this.fEmptyParticle == null)
      {
        localObject = new XSModelGroupImpl();
        ((XSModelGroupImpl)localObject).fCompositor = 102;
        ((XSModelGroupImpl)localObject).fParticleCount = 0;
        ((XSModelGroupImpl)localObject).fParticles = null;
        ((XSModelGroupImpl)localObject).fAnnotations = XSObjectListImpl.EMPTY_LIST;
        this.fEmptyParticle = new XSParticleDecl();
        this.fEmptyParticle.fType = 3;
        this.fEmptyParticle.fValue = ((XSTerm)localObject);
        this.fEmptyParticle.fAnnotations = XSObjectListImpl.EMPTY_LIST;
      }
      localXSParticleDecl = this.fEmptyParticle;
    }
    this.fParticle = localXSParticleDecl;
    if (this.fParticle == null) {
      this.fContentType = 0;
    } else if (paramBoolean1) {
      this.fContentType = 3;
    } else {
      this.fContentType = 2;
    }
    if (localElement != null)
    {
      if (!isAttrOrAttrGroup(localElement)) {
        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, DOMUtil.getLocalName(localElement) }, localElement);
      }
      localObject = traverseAttrsAndAttrGrps(localElement, this.fAttrGrp, paramXSDocumentInfo, paramSchemaGrammar, this.fComplexTypeDecl);
      if (localObject != null) {
        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[] { this.fName, DOMUtil.getLocalName((Node)localObject) }, (Element)localObject);
      }
      if (!paramBoolean2) {
        this.fAttrGrp.removeProhibitedAttrs();
      }
    }
  }
  
  private boolean isAttrOrAttrGroup(Element paramElement)
  {
    String str = DOMUtil.getLocalName(paramElement);
    return (str.equals(SchemaSymbols.ELT_ATTRIBUTE)) || (str.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) || (str.equals(SchemaSymbols.ELT_ANYATTRIBUTE));
  }
  
  private void traverseSimpleContentDecl(Element paramElement) {}
  
  private void traverseComplexContentDecl(Element paramElement, boolean paramBoolean) {}
  
  private String genAnonTypeName(Element paramElement)
  {
    StringBuffer localStringBuffer = new StringBuffer("#AnonType_");
    for (Element localElement = DOMUtil.getParent(paramElement); (localElement != null) && (localElement != DOMUtil.getRoot(DOMUtil.getDocument(localElement))); localElement = DOMUtil.getParent(localElement)) {
      localStringBuffer.append(localElement.getAttribute(SchemaSymbols.ATT_NAME));
    }
    return localStringBuffer.toString();
  }
  
  private void handleComplexTypeError(String paramString, Object[] paramArrayOfObject, Element paramElement)
  {
    if (paramString != null) {
      reportSchemaError(paramString, paramArrayOfObject, paramElement);
    }
    this.fBaseType = SchemaGrammar.fAnyType;
    this.fContentType = 3;
    this.fParticle = getErrorContent();
    this.fAttrGrp.fAttributeWC = getErrorWildcard();
  }
  
  private XSParticleDecl getErrorContent()
  {
    XSParticleDecl localXSParticleDecl1 = new XSParticleDecl();
    localXSParticleDecl1.fType = 2;
    localXSParticleDecl1.fValue = getErrorWildcard();
    localXSParticleDecl1.fMinOccurs = 0;
    localXSParticleDecl1.fMaxOccurs = -1;
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
  
  private XSWildcardDecl getErrorWildcard()
  {
    XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
    localXSWildcardDecl.fProcessContents = 2;
    return localXSWildcardDecl;
  }
  
  private void contentBackup()
  {
    if (this.fGlobalStore == null)
    {
      this.fGlobalStore = new Object[11];
      this.fGlobalStorePos = 0;
    }
    if (this.fGlobalStorePos == this.fGlobalStore.length)
    {
      Object[] arrayOfObject = new Object[this.fGlobalStorePos + 11];
      System.arraycopy(this.fGlobalStore, 0, arrayOfObject, 0, this.fGlobalStorePos);
      this.fGlobalStore = arrayOfObject;
    }
    this.fGlobalStore[(this.fGlobalStorePos++)] = this.fComplexTypeDecl;
    this.fGlobalStore[(this.fGlobalStorePos++)] = (this.fIsAbstract ? Boolean.TRUE : Boolean.FALSE);
    this.fGlobalStore[(this.fGlobalStorePos++)] = this.fName;
    this.fGlobalStore[(this.fGlobalStorePos++)] = this.fTargetNamespace;
    this.fGlobalStore[(this.fGlobalStorePos++)] = new Integer((this.fDerivedBy << 16) + this.fFinal);
    this.fGlobalStore[(this.fGlobalStorePos++)] = new Integer((this.fBlock << 16) + this.fContentType);
    this.fGlobalStore[(this.fGlobalStorePos++)] = this.fBaseType;
    this.fGlobalStore[(this.fGlobalStorePos++)] = this.fAttrGrp;
    this.fGlobalStore[(this.fGlobalStorePos++)] = this.fParticle;
    this.fGlobalStore[(this.fGlobalStorePos++)] = this.fXSSimpleType;
    this.fGlobalStore[(this.fGlobalStorePos++)] = this.fAnnotations;
  }
  
  private void contentRestore()
  {
    this.fAnnotations = ((XSAnnotationImpl[])this.fGlobalStore[(--this.fGlobalStorePos)]);
    this.fXSSimpleType = ((XSSimpleType)this.fGlobalStore[(--this.fGlobalStorePos)]);
    this.fParticle = ((XSParticleDecl)this.fGlobalStore[(--this.fGlobalStorePos)]);
    this.fAttrGrp = ((XSAttributeGroupDecl)this.fGlobalStore[(--this.fGlobalStorePos)]);
    this.fBaseType = ((XSTypeDefinition)this.fGlobalStore[(--this.fGlobalStorePos)]);
    int i = ((Integer)this.fGlobalStore[(--this.fGlobalStorePos)]).intValue();
    this.fBlock = ((short)(i >> 16));
    this.fContentType = ((short)i);
    i = ((Integer)this.fGlobalStore[(--this.fGlobalStorePos)]).intValue();
    this.fDerivedBy = ((short)(i >> 16));
    this.fFinal = ((short)i);
    this.fTargetNamespace = ((String)this.fGlobalStore[(--this.fGlobalStorePos)]);
    this.fName = ((String)this.fGlobalStore[(--this.fGlobalStorePos)]);
    this.fIsAbstract = ((Boolean)this.fGlobalStore[(--this.fGlobalStorePos)]).booleanValue();
    this.fComplexTypeDecl = ((XSComplexTypeDecl)this.fGlobalStore[(--this.fGlobalStorePos)]);
  }
  
  private void addAnnotation(XSAnnotationImpl paramXSAnnotationImpl)
  {
    if (paramXSAnnotationImpl == null) {
      return;
    }
    if (this.fAnnotations == null)
    {
      this.fAnnotations = new XSAnnotationImpl[1];
    }
    else
    {
      XSAnnotationImpl[] arrayOfXSAnnotationImpl = new XSAnnotationImpl[this.fAnnotations.length + 1];
      System.arraycopy(this.fAnnotations, 0, arrayOfXSAnnotationImpl, 0, this.fAnnotations.length);
      this.fAnnotations = arrayOfXSAnnotationImpl;
    }
    this.fAnnotations[(this.fAnnotations.length - 1)] = paramXSAnnotationImpl;
  }
  
  private static final class ComplexTypeRecoverableError
    extends Exception
  {
    private static final long serialVersionUID = 6802729912091130335L;
    Object[] errorSubstText = null;
    Element errorElem = null;
    
    ComplexTypeRecoverableError() {}
    
    ComplexTypeRecoverableError(String paramString, Object[] paramArrayOfObject, Element paramElement)
    {
      super();
      this.errorSubstText = paramArrayOfObject;
      this.errorElem = paramElement;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.XSDComplexTypeTraverser
 * JD-Core Version:    0.7.0.1
 */
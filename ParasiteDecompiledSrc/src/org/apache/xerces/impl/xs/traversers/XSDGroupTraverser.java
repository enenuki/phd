package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSDeclarationPool;
import org.apache.xerces.impl.xs.XSGroupDecl;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSObjectList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class XSDGroupTraverser
  extends XSDAbstractParticleTraverser
{
  XSDGroupTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  XSParticleDecl traverseLocal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    QName localQName = (QName)arrayOfObject[XSAttributeChecker.ATTIDX_REF];
    XInt localXInt1 = (XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MINOCCURS];
    XInt localXInt2 = (XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MAXOCCURS];
    XSGroupDecl localXSGroupDecl = null;
    if (localQName == null) {
      reportSchemaError("s4s-att-must-appear", new Object[] { "group (local)", "ref" }, paramElement);
    } else {
      localXSGroupDecl = (XSGroupDecl)this.fSchemaHandler.getGlobalDecl(paramXSDocumentInfo, 4, localQName, paramElement);
    }
    XSAnnotationImpl localXSAnnotationImpl = null;
    Element localElement = DOMUtil.getFirstChildElement(paramElement);
    if ((localElement != null) && (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      localXSAnnotationImpl = traverseAnnotationDecl(localElement, arrayOfObject, false, paramXSDocumentInfo);
      localElement = DOMUtil.getNextSiblingElement(localElement);
    }
    else
    {
      String str = DOMUtil.getSyntheticAnnotation(paramElement);
      if (str != null) {
        localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, str, arrayOfObject, false, paramXSDocumentInfo);
      }
    }
    if (localElement != null) {
      reportSchemaError("s4s-elt-must-match.1", new Object[] { "group (local)", "(annotation?)", DOMUtil.getLocalName(paramElement) }, paramElement);
    }
    int i = localXInt1.intValue();
    int j = localXInt2.intValue();
    XSParticleDecl localXSParticleDecl = null;
    if ((localXSGroupDecl != null) && (localXSGroupDecl.fModelGroup != null) && ((i != 0) || (j != 0)))
    {
      if (this.fSchemaHandler.fDeclPool != null) {
        localXSParticleDecl = this.fSchemaHandler.fDeclPool.getParticleDecl();
      } else {
        localXSParticleDecl = new XSParticleDecl();
      }
      localXSParticleDecl.fType = 3;
      localXSParticleDecl.fValue = localXSGroupDecl.fModelGroup;
      localXSParticleDecl.fMinOccurs = i;
      localXSParticleDecl.fMaxOccurs = j;
      Object localObject;
      if (localXSGroupDecl.fModelGroup.fCompositor == 103)
      {
        localObject = (Long)arrayOfObject[XSAttributeChecker.ATTIDX_FROMDEFAULT];
        localXSParticleDecl = checkOccurrences(localXSParticleDecl, SchemaSymbols.ELT_GROUP, (Element)paramElement.getParentNode(), 2, ((Long)localObject).longValue());
      }
      if (localQName != null)
      {
        if (localXSAnnotationImpl != null)
        {
          localObject = new XSObjectListImpl();
          ((XSObjectListImpl)localObject).add(localXSAnnotationImpl);
        }
        else
        {
          localObject = XSObjectListImpl.EMPTY_LIST;
        }
        localXSParticleDecl.fAnnotations = ((XSObjectList)localObject);
      }
      else
      {
        localXSParticleDecl.fAnnotations = localXSGroupDecl.fAnnotations;
      }
    }
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSParticleDecl;
  }
  
  XSGroupDecl traverseGlobal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, true, paramXSDocumentInfo);
    String str = (String)arrayOfObject[XSAttributeChecker.ATTIDX_NAME];
    if (str == null) {
      reportSchemaError("s4s-att-must-appear", new Object[] { "group (global)", "name" }, paramElement);
    }
    XSGroupDecl localXSGroupDecl = null;
    XSParticleDecl localXSParticleDecl = null;
    Element localElement = DOMUtil.getFirstChildElement(paramElement);
    XSAnnotationImpl localXSAnnotationImpl = null;
    Object localObject1;
    if (localElement == null)
    {
      reportSchemaError("s4s-elt-must-match.2", new Object[] { "group (global)", "(annotation?, (all | choice | sequence))" }, paramElement);
    }
    else
    {
      localXSGroupDecl = new XSGroupDecl();
      localObject1 = localElement.getLocalName();
      Object localObject2;
      if (((String)localObject1).equals(SchemaSymbols.ELT_ANNOTATION))
      {
        localXSAnnotationImpl = traverseAnnotationDecl(localElement, arrayOfObject, true, paramXSDocumentInfo);
        localElement = DOMUtil.getNextSiblingElement(localElement);
        if (localElement != null) {
          localObject1 = localElement.getLocalName();
        }
      }
      else
      {
        localObject2 = DOMUtil.getSyntheticAnnotation(paramElement);
        if (localObject2 != null) {
          localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, (String)localObject2, arrayOfObject, false, paramXSDocumentInfo);
        }
      }
      if (localElement == null) {
        reportSchemaError("s4s-elt-must-match.2", new Object[] { "group (global)", "(annotation?, (all | choice | sequence))" }, paramElement);
      } else if (((String)localObject1).equals(SchemaSymbols.ELT_ALL)) {
        localXSParticleDecl = traverseAll(localElement, paramXSDocumentInfo, paramSchemaGrammar, 4, localXSGroupDecl);
      } else if (((String)localObject1).equals(SchemaSymbols.ELT_CHOICE)) {
        localXSParticleDecl = traverseChoice(localElement, paramXSDocumentInfo, paramSchemaGrammar, 4, localXSGroupDecl);
      } else if (((String)localObject1).equals(SchemaSymbols.ELT_SEQUENCE)) {
        localXSParticleDecl = traverseSequence(localElement, paramXSDocumentInfo, paramSchemaGrammar, 4, localXSGroupDecl);
      } else {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { "group (global)", "(annotation?, (all | choice | sequence))", DOMUtil.getLocalName(localElement) }, localElement);
      }
      if ((localElement != null) && (DOMUtil.getNextSiblingElement(localElement) != null)) {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { "group (global)", "(annotation?, (all | choice | sequence))", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(localElement)) }, DOMUtil.getNextSiblingElement(localElement));
      }
      if (str != null)
      {
        localXSGroupDecl.fName = str;
        localXSGroupDecl.fTargetNamespace = paramXSDocumentInfo.fTargetNamespace;
        if (localXSParticleDecl != null) {
          localXSGroupDecl.fModelGroup = ((XSModelGroupImpl)localXSParticleDecl.fValue);
        }
        if (localXSAnnotationImpl != null)
        {
          localObject2 = new XSObjectListImpl();
          ((XSObjectListImpl)localObject2).add(localXSAnnotationImpl);
        }
        else
        {
          localObject2 = XSObjectListImpl.EMPTY_LIST;
        }
        localXSGroupDecl.fAnnotations = ((XSObjectList)localObject2);
        paramSchemaGrammar.addGlobalGroupDecl(localXSGroupDecl);
      }
      else
      {
        localXSGroupDecl = null;
      }
    }
    if (localXSGroupDecl != null)
    {
      localObject1 = this.fSchemaHandler.getGrpOrAttrGrpRedefinedByRestriction(4, new QName(XMLSymbols.EMPTY_STRING, str, str, paramXSDocumentInfo.fTargetNamespace), paramXSDocumentInfo, paramElement);
      if (localObject1 != null) {
        paramSchemaGrammar.addRedefinedGroupDecl(localXSGroupDecl, (XSGroupDecl)localObject1, this.fSchemaHandler.element2Locator(paramElement));
      }
    }
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSGroupDecl;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.XSDGroupTraverser
 * JD-Core Version:    0.7.0.1
 */
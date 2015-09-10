package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSDeclarationPool;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.XSWildcardDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.xs.XSObjectList;
import org.w3c.dom.Element;

class XSDWildcardTraverser
  extends XSDAbstractTraverser
{
  XSDWildcardTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  XSParticleDecl traverseAny(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    XSWildcardDecl localXSWildcardDecl = traverseWildcardDecl(paramElement, arrayOfObject, paramXSDocumentInfo, paramSchemaGrammar);
    XSParticleDecl localXSParticleDecl = null;
    if (localXSWildcardDecl != null)
    {
      int i = ((XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MINOCCURS]).intValue();
      int j = ((XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MAXOCCURS]).intValue();
      if (j != 0)
      {
        if (this.fSchemaHandler.fDeclPool != null) {
          localXSParticleDecl = this.fSchemaHandler.fDeclPool.getParticleDecl();
        } else {
          localXSParticleDecl = new XSParticleDecl();
        }
        localXSParticleDecl.fType = 2;
        localXSParticleDecl.fValue = localXSWildcardDecl;
        localXSParticleDecl.fMinOccurs = i;
        localXSParticleDecl.fMaxOccurs = j;
        localXSParticleDecl.fAnnotations = localXSWildcardDecl.fAnnotations;
      }
    }
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSParticleDecl;
  }
  
  XSWildcardDecl traverseAnyAttribute(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    XSWildcardDecl localXSWildcardDecl = traverseWildcardDecl(paramElement, arrayOfObject, paramXSDocumentInfo, paramSchemaGrammar);
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSWildcardDecl;
  }
  
  XSWildcardDecl traverseWildcardDecl(Element paramElement, Object[] paramArrayOfObject, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
    XInt localXInt1 = (XInt)paramArrayOfObject[XSAttributeChecker.ATTIDX_NAMESPACE];
    localXSWildcardDecl.fType = localXInt1.shortValue();
    localXSWildcardDecl.fNamespaceList = ((String[])paramArrayOfObject[XSAttributeChecker.ATTIDX_NAMESPACE_LIST]);
    XInt localXInt2 = (XInt)paramArrayOfObject[XSAttributeChecker.ATTIDX_PROCESSCONTENTS];
    localXSWildcardDecl.fProcessContents = localXInt2.shortValue();
    Element localElement = DOMUtil.getFirstChildElement(paramElement);
    XSAnnotationImpl localXSAnnotationImpl = null;
    Object localObject;
    if (localElement != null)
    {
      if (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION))
      {
        localXSAnnotationImpl = traverseAnnotationDecl(localElement, paramArrayOfObject, false, paramXSDocumentInfo);
        localElement = DOMUtil.getNextSiblingElement(localElement);
      }
      else
      {
        localObject = DOMUtil.getSyntheticAnnotation(paramElement);
        if (localObject != null) {
          localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, (String)localObject, paramArrayOfObject, false, paramXSDocumentInfo);
        }
      }
      if (localElement != null) {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { "wildcard", "(annotation?)", DOMUtil.getLocalName(localElement) }, paramElement);
      }
    }
    else
    {
      localObject = DOMUtil.getSyntheticAnnotation(paramElement);
      if (localObject != null) {
        localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, (String)localObject, paramArrayOfObject, false, paramXSDocumentInfo);
      }
    }
    if (localXSAnnotationImpl != null)
    {
      localObject = new XSObjectListImpl();
      ((XSObjectListImpl)localObject).add(localXSAnnotationImpl);
    }
    else
    {
      localObject = XSObjectListImpl.EMPTY_LIST;
    }
    localXSWildcardDecl.fAnnotations = ((XSObjectList)localObject);
    return localXSWildcardDecl;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.XSDWildcardTraverser
 * JD-Core Version:    0.7.0.1
 */
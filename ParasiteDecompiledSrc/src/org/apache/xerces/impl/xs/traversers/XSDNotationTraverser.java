package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSNotationDecl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.xs.XSObjectList;
import org.w3c.dom.Element;

class XSDNotationTraverser
  extends XSDAbstractTraverser
{
  XSDNotationTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  XSNotationDecl traverse(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject1 = this.fAttrChecker.checkAttributes(paramElement, true, paramXSDocumentInfo);
    String str1 = (String)arrayOfObject1[XSAttributeChecker.ATTIDX_NAME];
    String str2 = (String)arrayOfObject1[XSAttributeChecker.ATTIDX_PUBLIC];
    String str3 = (String)arrayOfObject1[XSAttributeChecker.ATTIDX_SYSTEM];
    if (str1 == null)
    {
      reportSchemaError("s4s-att-must-appear", new Object[] { SchemaSymbols.ELT_NOTATION, SchemaSymbols.ATT_NAME }, paramElement);
      this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
      return null;
    }
    if ((str3 == null) && (str2 == null)) {
      reportSchemaError("PublicSystemOnNotation", null, paramElement);
    }
    XSNotationDecl localXSNotationDecl = new XSNotationDecl();
    localXSNotationDecl.fName = str1;
    localXSNotationDecl.fTargetNamespace = paramXSDocumentInfo.fTargetNamespace;
    localXSNotationDecl.fPublicId = str2;
    localXSNotationDecl.fSystemId = str3;
    Element localElement = DOMUtil.getFirstChildElement(paramElement);
    XSAnnotationImpl localXSAnnotationImpl = null;
    Object localObject;
    if ((localElement != null) && (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      localXSAnnotationImpl = traverseAnnotationDecl(localElement, arrayOfObject1, false, paramXSDocumentInfo);
      localElement = DOMUtil.getNextSiblingElement(localElement);
    }
    else
    {
      localObject = DOMUtil.getSyntheticAnnotation(paramElement);
      if (localObject != null) {
        localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, (String)localObject, arrayOfObject1, false, paramXSDocumentInfo);
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
    localXSNotationDecl.fAnnotations = ((XSObjectList)localObject);
    if (localElement != null)
    {
      Object[] arrayOfObject2 = { SchemaSymbols.ELT_NOTATION, "(annotation?)", DOMUtil.getLocalName(localElement) };
      reportSchemaError("s4s-elt-must-match.1", arrayOfObject2, localElement);
    }
    paramSchemaGrammar.addGlobalNotationDecl(localXSNotationDecl);
    this.fAttrChecker.returnAttrArray(arrayOfObject1, paramXSDocumentInfo);
    return localXSNotationDecl;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.XSDNotationTraverser
 * JD-Core Version:    0.7.0.1
 */
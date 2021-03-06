package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSAttributeGroupDecl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSObjectList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class XSDAttributeGroupTraverser
  extends XSDAbstractTraverser
{
  XSDAttributeGroupTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  XSAttributeGroupDecl traverseLocal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    QName localQName = (QName)arrayOfObject[XSAttributeChecker.ATTIDX_REF];
    XSAttributeGroupDecl localXSAttributeGroupDecl = null;
    if (localQName == null)
    {
      reportSchemaError("s4s-att-must-appear", new Object[] { "attributeGroup (local)", "ref" }, paramElement);
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      return null;
    }
    localXSAttributeGroupDecl = (XSAttributeGroupDecl)this.fSchemaHandler.getGlobalDecl(paramXSDocumentInfo, 2, localQName, paramElement);
    Element localElement = DOMUtil.getFirstChildElement(paramElement);
    if (localElement != null)
    {
      String str = DOMUtil.getLocalName(localElement);
      Object localObject;
      if (str.equals(SchemaSymbols.ELT_ANNOTATION))
      {
        traverseAnnotationDecl(localElement, arrayOfObject, false, paramXSDocumentInfo);
        localElement = DOMUtil.getNextSiblingElement(localElement);
      }
      else
      {
        localObject = DOMUtil.getSyntheticAnnotation(localElement);
        if (localObject != null) {
          traverseSyntheticAnnotation(localElement, (String)localObject, arrayOfObject, false, paramXSDocumentInfo);
        }
      }
      if (localElement != null)
      {
        localObject = new Object[] { localQName.rawname, "(annotation?)", DOMUtil.getLocalName(localElement) };
        reportSchemaError("s4s-elt-must-match.1", (Object[])localObject, localElement);
      }
    }
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSAttributeGroupDecl;
  }
  
  XSAttributeGroupDecl traverseGlobal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    XSAttributeGroupDecl localXSAttributeGroupDecl = new XSAttributeGroupDecl();
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, true, paramXSDocumentInfo);
    String str = (String)arrayOfObject[XSAttributeChecker.ATTIDX_NAME];
    if (str == null)
    {
      reportSchemaError("s4s-att-must-appear", new Object[] { "attributeGroup (global)", "name" }, paramElement);
      str = "no name";
    }
    localXSAttributeGroupDecl.fName = str;
    localXSAttributeGroupDecl.fTargetNamespace = paramXSDocumentInfo.fTargetNamespace;
    Element localElement = DOMUtil.getFirstChildElement(paramElement);
    XSAnnotationImpl localXSAnnotationImpl = null;
    if ((localElement != null) && (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      localXSAnnotationImpl = traverseAnnotationDecl(localElement, arrayOfObject, false, paramXSDocumentInfo);
      localElement = DOMUtil.getNextSiblingElement(localElement);
    }
    else
    {
      localObject1 = DOMUtil.getSyntheticAnnotation(paramElement);
      if (localObject1 != null) {
        localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, (String)localObject1, arrayOfObject, false, paramXSDocumentInfo);
      }
    }
    Object localObject1 = traverseAttrsAndAttrGrps(localElement, localXSAttributeGroupDecl, paramXSDocumentInfo, paramSchemaGrammar, null);
    if (localObject1 != null)
    {
      localObject2 = new Object[] { str, "(annotation?, ((attribute | attributeGroup)*, anyAttribute?))", DOMUtil.getLocalName((Node)localObject1) };
      reportSchemaError("s4s-elt-must-match.1", (Object[])localObject2, (Element)localObject1);
    }
    localXSAttributeGroupDecl.removeProhibitedAttrs();
    Object localObject2 = (XSAttributeGroupDecl)this.fSchemaHandler.getGrpOrAttrGrpRedefinedByRestriction(2, new QName(XMLSymbols.EMPTY_STRING, str, str, paramXSDocumentInfo.fTargetNamespace), paramXSDocumentInfo, paramElement);
    Object localObject3;
    if (localObject2 != null)
    {
      localObject3 = localXSAttributeGroupDecl.validRestrictionOf(str, (XSAttributeGroupDecl)localObject2);
      if (localObject3 != null)
      {
        reportSchemaError((String)localObject3[(localObject3.length - 1)], (Object[])localObject3, localElement);
        reportSchemaError("src-redefine.7.2.2", new Object[] { str, localObject3[(localObject3.length - 1)] }, localElement);
      }
    }
    if (localXSAnnotationImpl != null)
    {
      localObject3 = new XSObjectListImpl();
      ((XSObjectListImpl)localObject3).add(localXSAnnotationImpl);
    }
    else
    {
      localObject3 = XSObjectListImpl.EMPTY_LIST;
    }
    localXSAttributeGroupDecl.fAnnotations = ((XSObjectList)localObject3);
    paramSchemaGrammar.addGlobalAttributeGroupDecl(localXSAttributeGroupDecl);
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSAttributeGroupDecl;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.XSDAttributeGroupTraverser
 * JD-Core Version:    0.7.0.1
 */
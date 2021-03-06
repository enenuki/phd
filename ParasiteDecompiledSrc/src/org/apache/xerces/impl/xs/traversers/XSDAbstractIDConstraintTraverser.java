package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xpath.XPathException;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.identity.Field;
import org.apache.xerces.impl.xs.identity.Field.XPath;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.identity.Selector;
import org.apache.xerces.impl.xs.identity.Selector.XPath;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.XMLChar;
import org.w3c.dom.Element;

class XSDAbstractIDConstraintTraverser
  extends XSDAbstractTraverser
{
  public XSDAbstractIDConstraintTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  void traverseIdentityConstraint(IdentityConstraint paramIdentityConstraint, Element paramElement, XSDocumentInfo paramXSDocumentInfo, Object[] paramArrayOfObject)
  {
    Element localElement1 = DOMUtil.getFirstChildElement(paramElement);
    if (localElement1 == null)
    {
      reportSchemaError("s4s-elt-must-match.2", new Object[] { "identity constraint", "(annotation?, selector, field+)" }, paramElement);
      return;
    }
    if (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_ANNOTATION))
    {
      paramIdentityConstraint.addAnnotation(traverseAnnotationDecl(localElement1, paramArrayOfObject, false, paramXSDocumentInfo));
      localElement1 = DOMUtil.getNextSiblingElement(localElement1);
      if (localElement1 == null) {
        reportSchemaError("s4s-elt-must-match.2", new Object[] { "identity constraint", "(annotation?, selector, field+)" }, paramElement);
      }
    }
    else
    {
      localObject = DOMUtil.getSyntheticAnnotation(paramElement);
      if (localObject != null) {
        paramIdentityConstraint.addAnnotation(traverseSyntheticAnnotation(paramElement, (String)localObject, paramArrayOfObject, false, paramXSDocumentInfo));
      }
    }
    Object localObject = this.fAttrChecker.checkAttributes(localElement1, false, paramXSDocumentInfo);
    if (!DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_SELECTOR)) {
      reportSchemaError("s4s-elt-must-match.1", new Object[] { "identity constraint", "(annotation?, selector, field+)", SchemaSymbols.ELT_SELECTOR }, localElement1);
    }
    Element localElement2 = DOMUtil.getFirstChildElement(localElement1);
    if (localElement2 != null)
    {
      if (DOMUtil.getLocalName(localElement2).equals(SchemaSymbols.ELT_ANNOTATION))
      {
        paramIdentityConstraint.addAnnotation(traverseAnnotationDecl(localElement2, (Object[])localObject, false, paramXSDocumentInfo));
        localElement2 = DOMUtil.getNextSiblingElement(localElement2);
      }
      else
      {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { SchemaSymbols.ELT_SELECTOR, "(annotation?)", DOMUtil.getLocalName(localElement2) }, localElement2);
      }
      if (localElement2 != null) {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { SchemaSymbols.ELT_SELECTOR, "(annotation?)", DOMUtil.getLocalName(localElement2) }, localElement2);
      }
    }
    else
    {
      str1 = DOMUtil.getSyntheticAnnotation(localElement1);
      if (str1 != null) {
        paramIdentityConstraint.addAnnotation(traverseSyntheticAnnotation(paramElement, str1, (Object[])localObject, false, paramXSDocumentInfo));
      }
    }
    String str1 = (String)localObject[XSAttributeChecker.ATTIDX_XPATH];
    if (str1 == null)
    {
      reportSchemaError("s4s-att-must-appear", new Object[] { SchemaSymbols.ELT_SELECTOR, SchemaSymbols.ATT_XPATH }, localElement1);
      return;
    }
    str1 = XMLChar.trim(str1);
    Selector.XPath localXPath = null;
    try
    {
      localXPath = new Selector.XPath(str1, this.fSymbolTable, paramXSDocumentInfo.fNamespaceSupport);
      Selector localSelector = new Selector(localXPath, paramIdentityConstraint);
      paramIdentityConstraint.setSelector(localSelector);
    }
    catch (XPathException localXPathException1)
    {
      reportSchemaError(localXPathException1.getKey(), new Object[] { str1 }, localElement1);
      this.fAttrChecker.returnAttrArray((Object[])localObject, paramXSDocumentInfo);
      return;
    }
    this.fAttrChecker.returnAttrArray((Object[])localObject, paramXSDocumentInfo);
    Element localElement3 = DOMUtil.getNextSiblingElement(localElement1);
    if (localElement3 == null) {
      reportSchemaError("s4s-elt-must-match.2", new Object[] { "identity constraint", "(annotation?, selector, field+)" }, localElement1);
    }
    while (localElement3 != null)
    {
      localObject = this.fAttrChecker.checkAttributes(localElement3, false, paramXSDocumentInfo);
      if (!DOMUtil.getLocalName(localElement3).equals(SchemaSymbols.ELT_FIELD)) {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { "identity constraint", "(annotation?, selector, field+)", SchemaSymbols.ELT_FIELD }, localElement3);
      }
      Element localElement4 = DOMUtil.getFirstChildElement(localElement3);
      if ((localElement4 != null) && (DOMUtil.getLocalName(localElement4).equals(SchemaSymbols.ELT_ANNOTATION)))
      {
        paramIdentityConstraint.addAnnotation(traverseAnnotationDecl(localElement4, (Object[])localObject, false, paramXSDocumentInfo));
        localElement4 = DOMUtil.getNextSiblingElement(localElement4);
      }
      if (localElement4 != null)
      {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { SchemaSymbols.ELT_FIELD, "(annotation?)", DOMUtil.getLocalName(localElement4) }, localElement4);
      }
      else
      {
        str2 = DOMUtil.getSyntheticAnnotation(localElement3);
        if (str2 != null) {
          paramIdentityConstraint.addAnnotation(traverseSyntheticAnnotation(paramElement, str2, (Object[])localObject, false, paramXSDocumentInfo));
        }
      }
      String str2 = (String)localObject[XSAttributeChecker.ATTIDX_XPATH];
      if (str2 == null)
      {
        reportSchemaError("s4s-att-must-appear", new Object[] { SchemaSymbols.ELT_FIELD, SchemaSymbols.ATT_XPATH }, localElement3);
        return;
      }
      str2 = str2.trim();
      try
      {
        Field.XPath localXPath1 = new Field.XPath(str2, this.fSymbolTable, paramXSDocumentInfo.fNamespaceSupport);
        Field localField = new Field(localXPath1, paramIdentityConstraint);
        paramIdentityConstraint.addField(localField);
      }
      catch (XPathException localXPathException2)
      {
        reportSchemaError(localXPathException2.getKey(), new Object[] { str2 }, localElement3);
        this.fAttrChecker.returnAttrArray((Object[])localObject, paramXSDocumentInfo);
        return;
      }
      localElement3 = DOMUtil.getNextSiblingElement(localElement3);
      this.fAttrChecker.returnAttrArray((Object[])localObject, paramXSDocumentInfo);
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.XSDAbstractIDConstraintTraverser
 * JD-Core Version:    0.7.0.1
 */
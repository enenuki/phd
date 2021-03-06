package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.identity.KeyRef;
import org.apache.xerces.impl.xs.identity.UniqueOrKey;
import org.apache.xerces.xni.QName;
import org.w3c.dom.Element;

class XSDKeyrefTraverser
  extends XSDAbstractIDConstraintTraverser
{
  public XSDKeyrefTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  void traverse(Element paramElement, XSElementDecl paramXSElementDecl, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    String str = (String)arrayOfObject[XSAttributeChecker.ATTIDX_NAME];
    if (str == null)
    {
      reportSchemaError("s4s-att-must-appear", new Object[] { SchemaSymbols.ELT_KEYREF, SchemaSymbols.ATT_NAME }, paramElement);
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      return;
    }
    QName localQName = (QName)arrayOfObject[XSAttributeChecker.ATTIDX_REFER];
    if (localQName == null)
    {
      reportSchemaError("s4s-att-must-appear", new Object[] { SchemaSymbols.ELT_KEYREF, SchemaSymbols.ATT_REFER }, paramElement);
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      return;
    }
    UniqueOrKey localUniqueOrKey = null;
    IdentityConstraint localIdentityConstraint = (IdentityConstraint)this.fSchemaHandler.getGlobalDecl(paramXSDocumentInfo, 5, localQName, paramElement);
    if (localIdentityConstraint != null) {
      if ((localIdentityConstraint.getCategory() == 1) || (localIdentityConstraint.getCategory() == 3)) {
        localUniqueOrKey = (UniqueOrKey)localIdentityConstraint;
      } else {
        reportSchemaError("src-resolve", new Object[] { localQName.rawname, "identity constraint key/unique" }, paramElement);
      }
    }
    if (localUniqueOrKey == null)
    {
      this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      return;
    }
    KeyRef localKeyRef = new KeyRef(paramXSDocumentInfo.fTargetNamespace, str, paramXSElementDecl.fName, localUniqueOrKey);
    traverseIdentityConstraint(localKeyRef, paramElement, paramXSDocumentInfo, arrayOfObject);
    if (localUniqueOrKey.getFieldCount() != localKeyRef.getFieldCount()) {
      reportSchemaError("c-props-correct.2", new Object[] { str, localUniqueOrKey.getIdentityConstraintName() }, paramElement);
    } else {
      paramSchemaGrammar.addIDConstraintDecl(paramXSElementDecl, localKeyRef);
    }
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.XSDKeyrefTraverser
 * JD-Core Version:    0.7.0.1
 */
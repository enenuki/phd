package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

abstract class XSDAbstractParticleTraverser
  extends XSDAbstractTraverser
{
  ParticleArray fPArray = new ParticleArray();
  
  XSDAbstractParticleTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  XSParticleDecl traverseAll(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar, int paramInt, XSObject paramXSObject)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    Element localElement = DOMUtil.getFirstChildElement(paramElement);
    XSAnnotationImpl localXSAnnotationImpl = null;
    if ((localElement != null) && (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      localXSAnnotationImpl = traverseAnnotationDecl(localElement, arrayOfObject, false, paramXSDocumentInfo);
      localElement = DOMUtil.getNextSiblingElement(localElement);
    }
    else
    {
      str = DOMUtil.getSyntheticAnnotation(paramElement);
      if (str != null) {
        localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, str, arrayOfObject, false, paramXSDocumentInfo);
      }
    }
    String str = null;
    this.fPArray.pushContext();
    while (localElement != null)
    {
      localXSParticleDecl = null;
      str = DOMUtil.getLocalName(localElement);
      if (str.equals(SchemaSymbols.ELT_ELEMENT))
      {
        localXSParticleDecl = this.fSchemaHandler.fElementTraverser.traverseLocal(localElement, paramXSDocumentInfo, paramSchemaGrammar, 1, paramXSObject);
      }
      else
      {
        localObject1 = new Object[] { "all", "(annotation?, element*)", DOMUtil.getLocalName(localElement) };
        reportSchemaError("s4s-elt-must-match.1", (Object[])localObject1, localElement);
      }
      if (localXSParticleDecl != null) {
        this.fPArray.addParticle(localXSParticleDecl);
      }
      localElement = DOMUtil.getNextSiblingElement(localElement);
    }
    XSParticleDecl localXSParticleDecl = null;
    Object localObject1 = (XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MINOCCURS];
    XInt localXInt = (XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MAXOCCURS];
    Long localLong = (Long)arrayOfObject[XSAttributeChecker.ATTIDX_FROMDEFAULT];
    XSModelGroupImpl localXSModelGroupImpl = new XSModelGroupImpl();
    localXSModelGroupImpl.fCompositor = 103;
    localXSModelGroupImpl.fParticleCount = this.fPArray.getParticleCount();
    localXSModelGroupImpl.fParticles = this.fPArray.popContext();
    Object localObject2;
    if (localXSAnnotationImpl != null)
    {
      localObject2 = new XSObjectListImpl();
      ((XSObjectListImpl)localObject2).add(localXSAnnotationImpl);
    }
    else
    {
      localObject2 = XSObjectListImpl.EMPTY_LIST;
    }
    localXSModelGroupImpl.fAnnotations = ((XSObjectList)localObject2);
    localXSParticleDecl = new XSParticleDecl();
    localXSParticleDecl.fType = 3;
    localXSParticleDecl.fMinOccurs = ((XInt)localObject1).intValue();
    localXSParticleDecl.fMaxOccurs = localXInt.intValue();
    localXSParticleDecl.fValue = localXSModelGroupImpl;
    localXSParticleDecl.fAnnotations = ((XSObjectList)localObject2);
    localXSParticleDecl = checkOccurrences(localXSParticleDecl, SchemaSymbols.ELT_ALL, (Element)paramElement.getParentNode(), paramInt, localLong.longValue());
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSParticleDecl;
  }
  
  XSParticleDecl traverseSequence(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar, int paramInt, XSObject paramXSObject)
  {
    return traverseSeqChoice(paramElement, paramXSDocumentInfo, paramSchemaGrammar, paramInt, false, paramXSObject);
  }
  
  XSParticleDecl traverseChoice(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar, int paramInt, XSObject paramXSObject)
  {
    return traverseSeqChoice(paramElement, paramXSDocumentInfo, paramSchemaGrammar, paramInt, true, paramXSObject);
  }
  
  private XSParticleDecl traverseSeqChoice(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar, int paramInt, boolean paramBoolean, XSObject paramXSObject)
  {
    Object[] arrayOfObject = this.fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    Element localElement = DOMUtil.getFirstChildElement(paramElement);
    XSAnnotationImpl localXSAnnotationImpl = null;
    if ((localElement != null) && (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      localXSAnnotationImpl = traverseAnnotationDecl(localElement, arrayOfObject, false, paramXSDocumentInfo);
      localElement = DOMUtil.getNextSiblingElement(localElement);
    }
    else
    {
      str = DOMUtil.getSyntheticAnnotation(paramElement);
      if (str != null) {
        localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, str, arrayOfObject, false, paramXSDocumentInfo);
      }
    }
    String str = null;
    this.fPArray.pushContext();
    while (localElement != null)
    {
      localXSParticleDecl = null;
      str = DOMUtil.getLocalName(localElement);
      if (str.equals(SchemaSymbols.ELT_ELEMENT))
      {
        localXSParticleDecl = this.fSchemaHandler.fElementTraverser.traverseLocal(localElement, paramXSDocumentInfo, paramSchemaGrammar, 0, paramXSObject);
      }
      else if (str.equals(SchemaSymbols.ELT_GROUP))
      {
        localXSParticleDecl = this.fSchemaHandler.fGroupTraverser.traverseLocal(localElement, paramXSDocumentInfo, paramSchemaGrammar);
        if (hasAllContent(localXSParticleDecl))
        {
          localXSParticleDecl = null;
          reportSchemaError("cos-all-limited.1.2", null, localElement);
        }
      }
      else if (str.equals(SchemaSymbols.ELT_CHOICE))
      {
        localXSParticleDecl = traverseChoice(localElement, paramXSDocumentInfo, paramSchemaGrammar, 0, paramXSObject);
      }
      else if (str.equals(SchemaSymbols.ELT_SEQUENCE))
      {
        localXSParticleDecl = traverseSequence(localElement, paramXSDocumentInfo, paramSchemaGrammar, 0, paramXSObject);
      }
      else if (str.equals(SchemaSymbols.ELT_ANY))
      {
        localXSParticleDecl = this.fSchemaHandler.fWildCardTraverser.traverseAny(localElement, paramXSDocumentInfo, paramSchemaGrammar);
      }
      else
      {
        if (paramBoolean) {
          localObject1 = new Object[] { "choice", "(annotation?, (element | group | choice | sequence | any)*)", DOMUtil.getLocalName(localElement) };
        } else {
          localObject1 = new Object[] { "sequence", "(annotation?, (element | group | choice | sequence | any)*)", DOMUtil.getLocalName(localElement) };
        }
        reportSchemaError("s4s-elt-must-match.1", (Object[])localObject1, localElement);
      }
      if (localXSParticleDecl != null) {
        this.fPArray.addParticle(localXSParticleDecl);
      }
      localElement = DOMUtil.getNextSiblingElement(localElement);
    }
    XSParticleDecl localXSParticleDecl = null;
    Object localObject1 = (XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MINOCCURS];
    XInt localXInt = (XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MAXOCCURS];
    Long localLong = (Long)arrayOfObject[XSAttributeChecker.ATTIDX_FROMDEFAULT];
    XSModelGroupImpl localXSModelGroupImpl = new XSModelGroupImpl();
    localXSModelGroupImpl.fCompositor = (paramBoolean ? 101 : 102);
    localXSModelGroupImpl.fParticleCount = this.fPArray.getParticleCount();
    localXSModelGroupImpl.fParticles = this.fPArray.popContext();
    Object localObject2;
    if (localXSAnnotationImpl != null)
    {
      localObject2 = new XSObjectListImpl();
      ((XSObjectListImpl)localObject2).add(localXSAnnotationImpl);
    }
    else
    {
      localObject2 = XSObjectListImpl.EMPTY_LIST;
    }
    localXSModelGroupImpl.fAnnotations = ((XSObjectList)localObject2);
    localXSParticleDecl = new XSParticleDecl();
    localXSParticleDecl.fType = 3;
    localXSParticleDecl.fMinOccurs = ((XInt)localObject1).intValue();
    localXSParticleDecl.fMaxOccurs = localXInt.intValue();
    localXSParticleDecl.fValue = localXSModelGroupImpl;
    localXSParticleDecl.fAnnotations = ((XSObjectList)localObject2);
    localXSParticleDecl = checkOccurrences(localXSParticleDecl, paramBoolean ? SchemaSymbols.ELT_CHOICE : SchemaSymbols.ELT_SEQUENCE, (Element)paramElement.getParentNode(), paramInt, localLong.longValue());
    this.fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSParticleDecl;
  }
  
  protected boolean hasAllContent(XSParticleDecl paramXSParticleDecl)
  {
    if ((paramXSParticleDecl != null) && (paramXSParticleDecl.fType == 3)) {
      return ((XSModelGroupImpl)paramXSParticleDecl.fValue).fCompositor == 103;
    }
    return false;
  }
  
  protected static class ParticleArray
  {
    XSParticleDecl[] fParticles = new XSParticleDecl[10];
    int[] fPos = new int[5];
    int fContextCount = 0;
    
    void pushContext()
    {
      this.fContextCount += 1;
      if (this.fContextCount == this.fPos.length)
      {
        int i = this.fContextCount * 2;
        int[] arrayOfInt = new int[i];
        System.arraycopy(this.fPos, 0, arrayOfInt, 0, this.fContextCount);
        this.fPos = arrayOfInt;
      }
      this.fPos[this.fContextCount] = this.fPos[(this.fContextCount - 1)];
    }
    
    int getParticleCount()
    {
      return this.fPos[this.fContextCount] - this.fPos[(this.fContextCount - 1)];
    }
    
    void addParticle(XSParticleDecl paramXSParticleDecl)
    {
      if (this.fPos[this.fContextCount] == this.fParticles.length)
      {
        int i = this.fPos[this.fContextCount] * 2;
        XSParticleDecl[] arrayOfXSParticleDecl = new XSParticleDecl[i];
        System.arraycopy(this.fParticles, 0, arrayOfXSParticleDecl, 0, this.fPos[this.fContextCount]);
        this.fParticles = arrayOfXSParticleDecl;
      }
      int tmp70_67 = this.fContextCount;
      int[] tmp70_63 = this.fPos;
      int tmp72_71 = tmp70_63[tmp70_67];
      tmp70_63[tmp70_67] = (tmp72_71 + 1);
      this.fParticles[tmp72_71] = paramXSParticleDecl;
    }
    
    XSParticleDecl[] popContext()
    {
      int i = this.fPos[this.fContextCount] - this.fPos[(this.fContextCount - 1)];
      XSParticleDecl[] arrayOfXSParticleDecl = null;
      if (i != 0)
      {
        arrayOfXSParticleDecl = new XSParticleDecl[i];
        System.arraycopy(this.fParticles, this.fPos[(this.fContextCount - 1)], arrayOfXSParticleDecl, 0, i);
        for (int j = this.fPos[(this.fContextCount - 1)]; j < this.fPos[this.fContextCount]; j++) {
          this.fParticles[j] = null;
        }
      }
      this.fContextCount -= 1;
      return arrayOfXSParticleDecl;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.XSDAbstractParticleTraverser
 * JD-Core Version:    0.7.0.1
 */
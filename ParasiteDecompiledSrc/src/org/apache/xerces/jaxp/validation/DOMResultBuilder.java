package org.apache.xerces.jaxp.validation;

import java.util.ArrayList;
import javax.xml.transform.dom.DOMResult;
import org.apache.xerces.dom.AttrImpl;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.ElementNSImpl;
import org.apache.xerces.dom.EntityImpl;
import org.apache.xerces.dom.NotationImpl;
import org.apache.xerces.dom.PSVIAttrNSImpl;
import org.apache.xerces.dom.PSVIDocumentImpl;
import org.apache.xerces.dom.PSVIElementNSImpl;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.ItemPSVI;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

final class DOMResultBuilder
  implements DOMDocumentHandler
{
  private static final int[] kidOK = new int[13];
  private Document fDocument;
  private CoreDocumentImpl fDocumentImpl;
  private boolean fStorePSVI;
  private Node fTarget;
  private Node fNextSibling;
  private Node fCurrentNode;
  private Node fFragmentRoot;
  private final ArrayList fTargetChildren = new ArrayList();
  private boolean fIgnoreChars;
  private final QName fAttributeQName = new QName();
  
  public void setDOMResult(DOMResult paramDOMResult)
  {
    this.fCurrentNode = null;
    this.fFragmentRoot = null;
    this.fIgnoreChars = false;
    this.fTargetChildren.clear();
    if (paramDOMResult != null)
    {
      this.fTarget = paramDOMResult.getNode();
      this.fNextSibling = paramDOMResult.getNextSibling();
      this.fDocument = (this.fTarget.getNodeType() == 9 ? (Document)this.fTarget : this.fTarget.getOwnerDocument());
      this.fDocumentImpl = ((this.fDocument instanceof CoreDocumentImpl) ? (CoreDocumentImpl)this.fDocument : null);
      this.fStorePSVI = (this.fDocument instanceof PSVIDocumentImpl);
      return;
    }
    this.fTarget = null;
    this.fNextSibling = null;
    this.fDocument = null;
    this.fDocumentImpl = null;
    this.fStorePSVI = false;
  }
  
  public void doctypeDecl(DocumentType paramDocumentType)
    throws XNIException
  {
    if (this.fDocumentImpl != null)
    {
      DocumentType localDocumentType = this.fDocumentImpl.createDocumentType(paramDocumentType.getName(), paramDocumentType.getPublicId(), paramDocumentType.getSystemId());
      String str = paramDocumentType.getInternalSubset();
      if (str != null) {
        ((DocumentTypeImpl)localDocumentType).setInternalSubset(str);
      }
      NamedNodeMap localNamedNodeMap1 = paramDocumentType.getEntities();
      NamedNodeMap localNamedNodeMap2 = localDocumentType.getEntities();
      int i = localNamedNodeMap1.getLength();
      Object localObject;
      for (int j = 0; j < i; j++)
      {
        Entity localEntity = (Entity)localNamedNodeMap1.item(j);
        localObject = (EntityImpl)this.fDocumentImpl.createEntity(localEntity.getNodeName());
        ((EntityImpl)localObject).setPublicId(localEntity.getPublicId());
        ((EntityImpl)localObject).setSystemId(localEntity.getSystemId());
        ((EntityImpl)localObject).setNotationName(localEntity.getNotationName());
        localNamedNodeMap2.setNamedItem((Node)localObject);
      }
      localNamedNodeMap1 = paramDocumentType.getNotations();
      localNamedNodeMap2 = localDocumentType.getNotations();
      i = localNamedNodeMap1.getLength();
      for (int k = 0; k < i; k++)
      {
        localObject = (Notation)localNamedNodeMap1.item(k);
        NotationImpl localNotationImpl = (NotationImpl)this.fDocumentImpl.createNotation(((Node)localObject).getNodeName());
        localNotationImpl.setPublicId(((Notation)localObject).getPublicId());
        localNotationImpl.setSystemId(((Notation)localObject).getSystemId());
        localNamedNodeMap2.setNamedItem(localNotationImpl);
      }
      append(localDocumentType);
    }
  }
  
  public void characters(Text paramText)
    throws XNIException
  {
    append(this.fDocument.createTextNode(paramText.getNodeValue()));
  }
  
  public void cdata(CDATASection paramCDATASection)
    throws XNIException
  {
    append(this.fDocument.createCDATASection(paramCDATASection.getNodeValue()));
  }
  
  public void comment(Comment paramComment)
    throws XNIException
  {
    append(this.fDocument.createComment(paramComment.getNodeValue()));
  }
  
  public void processingInstruction(ProcessingInstruction paramProcessingInstruction)
    throws XNIException
  {
    append(this.fDocument.createProcessingInstruction(paramProcessingInstruction.getTarget(), paramProcessingInstruction.getData()));
  }
  
  public void setIgnoringCharacters(boolean paramBoolean)
  {
    this.fIgnoreChars = paramBoolean;
  }
  
  public void startDocument(XMLLocator paramXMLLocator, String paramString, NamespaceContext paramNamespaceContext, Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void xmlDecl(String paramString1, String paramString2, String paramString3, Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void doctypeDecl(String paramString1, String paramString2, String paramString3, Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void comment(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void processingInstruction(String paramString, XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void startElement(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
    throws XNIException
  {
    int i = paramXMLAttributes.getLength();
    Element localElement;
    int j;
    if (this.fDocumentImpl == null)
    {
      localElement = this.fDocument.createElementNS(paramQName.uri, paramQName.rawname);
      for (j = 0; j < i; j++)
      {
        paramXMLAttributes.getName(j, this.fAttributeQName);
        localElement.setAttributeNS(this.fAttributeQName.uri, this.fAttributeQName.rawname, paramXMLAttributes.getValue(j));
      }
    }
    else
    {
      localElement = this.fDocumentImpl.createElementNS(paramQName.uri, paramQName.rawname, paramQName.localpart);
      for (j = 0; j < i; j++)
      {
        paramXMLAttributes.getName(j, this.fAttributeQName);
        AttrImpl localAttrImpl = (AttrImpl)this.fDocumentImpl.createAttributeNS(this.fAttributeQName.uri, this.fAttributeQName.rawname, this.fAttributeQName.localpart);
        localAttrImpl.setValue(paramXMLAttributes.getValue(j));
        localElement.setAttributeNodeNS(localAttrImpl);
        AttributePSVI localAttributePSVI = (AttributePSVI)paramXMLAttributes.getAugmentations(j).getItem("ATTRIBUTE_PSVI");
        if (localAttributePSVI != null)
        {
          if (this.fStorePSVI) {
            ((PSVIAttrNSImpl)localAttrImpl).setPSVI(localAttributePSVI);
          }
          Object localObject = localAttributePSVI.getMemberTypeDefinition();
          if (localObject == null)
          {
            localObject = localAttributePSVI.getTypeDefinition();
            if (localObject != null)
            {
              localAttrImpl.setType(localObject);
              if (((XSSimpleType)localObject).isIDType()) {
                ((ElementImpl)localElement).setIdAttributeNode(localAttrImpl, true);
              }
            }
          }
          else
          {
            localAttrImpl.setType(localObject);
            if (((XSSimpleType)localObject).isIDType()) {
              ((ElementImpl)localElement).setIdAttributeNode(localAttrImpl, true);
            }
          }
        }
        localAttrImpl.setSpecified(paramXMLAttributes.isSpecified(j));
      }
    }
    append(localElement);
    this.fCurrentNode = localElement;
    if (this.fFragmentRoot == null) {
      this.fFragmentRoot = localElement;
    }
  }
  
  public void emptyElement(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
    throws XNIException
  {
    startElement(paramQName, paramXMLAttributes, paramAugmentations);
    endElement(paramQName, paramAugmentations);
  }
  
  public void startGeneralEntity(String paramString1, XMLResourceIdentifier paramXMLResourceIdentifier, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void textDecl(String paramString1, String paramString2, Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void endGeneralEntity(String paramString, Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void characters(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    if (!this.fIgnoreChars) {
      append(this.fDocument.createTextNode(paramXMLString.toString()));
    }
  }
  
  public void ignorableWhitespace(XMLString paramXMLString, Augmentations paramAugmentations)
    throws XNIException
  {
    characters(paramXMLString, paramAugmentations);
  }
  
  public void endElement(QName paramQName, Augmentations paramAugmentations)
    throws XNIException
  {
    if ((paramAugmentations != null) && (this.fDocumentImpl != null))
    {
      ElementPSVI localElementPSVI = (ElementPSVI)paramAugmentations.getItem("ELEMENT_PSVI");
      if (localElementPSVI != null)
      {
        if (this.fStorePSVI) {
          ((PSVIElementNSImpl)this.fCurrentNode).setPSVI(localElementPSVI);
        }
        Object localObject = localElementPSVI.getMemberTypeDefinition();
        if (localObject == null) {
          localObject = localElementPSVI.getTypeDefinition();
        }
        ((ElementNSImpl)this.fCurrentNode).setType((XSTypeDefinition)localObject);
      }
    }
    if (this.fCurrentNode == this.fFragmentRoot)
    {
      this.fCurrentNode = null;
      this.fFragmentRoot = null;
      return;
    }
    this.fCurrentNode = this.fCurrentNode.getParentNode();
  }
  
  public void startCDATA(Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void endCDATA(Augmentations paramAugmentations)
    throws XNIException
  {}
  
  public void endDocument(Augmentations paramAugmentations)
    throws XNIException
  {
    int i = this.fTargetChildren.size();
    int j;
    if (this.fNextSibling == null) {
      for (j = 0; j < i; j++) {
        this.fTarget.appendChild((Node)this.fTargetChildren.get(j));
      }
    } else {
      for (j = 0; j < i; j++) {
        this.fTarget.insertBefore((Node)this.fTargetChildren.get(j), this.fNextSibling);
      }
    }
  }
  
  public void setDocumentSource(XMLDocumentSource paramXMLDocumentSource) {}
  
  public XMLDocumentSource getDocumentSource()
  {
    return null;
  }
  
  private void append(Node paramNode)
    throws XNIException
  {
    if (this.fCurrentNode != null)
    {
      this.fCurrentNode.appendChild(paramNode);
    }
    else
    {
      if ((kidOK[this.fTarget.getNodeType()] & 1 << paramNode.getNodeType()) == 0)
      {
        String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null);
        throw new XNIException(str);
      }
      this.fTargetChildren.add(paramNode);
    }
  }
  
  static
  {
    kidOK[9] = 1410;
    short tmp41_40 = (kidOK[5] = kidOK[1] = 442);
    kidOK[6] = tmp41_40;
    kidOK[11] = tmp41_40;
    kidOK[2] = 40;
    kidOK[10] = 0;
    kidOK[7] = 0;
    kidOK[8] = 0;
    kidOK[3] = 0;
    kidOK[4] = 0;
    kidOK[12] = 0;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.DOMResultBuilder
 * JD-Core Version:    0.7.0.1
 */
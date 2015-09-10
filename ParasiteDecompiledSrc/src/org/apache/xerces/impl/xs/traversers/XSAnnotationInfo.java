package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.opti.ElementImpl;
import org.w3c.dom.Element;

final class XSAnnotationInfo
{
  String fAnnotation;
  int fLine;
  int fColumn;
  int fCharOffset;
  XSAnnotationInfo next;
  
  XSAnnotationInfo(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    this.fAnnotation = paramString;
    this.fLine = paramInt1;
    this.fColumn = paramInt2;
    this.fCharOffset = paramInt3;
  }
  
  XSAnnotationInfo(String paramString, Element paramElement)
  {
    this.fAnnotation = paramString;
    if ((paramElement instanceof ElementImpl))
    {
      ElementImpl localElementImpl = (ElementImpl)paramElement;
      this.fLine = localElementImpl.getLineNumber();
      this.fColumn = localElementImpl.getColumnNumber();
      this.fCharOffset = localElementImpl.getCharacterOffset();
    }
    else
    {
      this.fLine = -1;
      this.fColumn = -1;
      this.fCharOffset = -1;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.traversers.XSAnnotationInfo
 * JD-Core Version:    0.7.0.1
 */
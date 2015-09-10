package org.apache.xerces.xs;

public abstract interface XSAnnotation
  extends XSObject
{
  public static final short W3C_DOM_ELEMENT = 1;
  public static final short SAX_CONTENTHANDLER = 2;
  public static final short W3C_DOM_DOCUMENT = 3;
  
  public abstract boolean writeAnnotation(Object paramObject, short paramShort);
  
  public abstract String getAnnotationString();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSAnnotation
 * JD-Core Version:    0.7.0.1
 */
package org.apache.xerces.xpointer;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;

public abstract interface XPointerProcessor
{
  public static final int EVENT_ELEMENT_START = 0;
  public static final int EVENT_ELEMENT_END = 1;
  public static final int EVENT_ELEMENT_EMPTY = 2;
  
  public abstract void parseXPointer(String paramString)
    throws XNIException;
  
  public abstract boolean resolveXPointer(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations, int paramInt)
    throws XNIException;
  
  public abstract boolean isFragmentResolved()
    throws XNIException;
  
  public abstract boolean isXPointerResolved()
    throws XNIException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xpointer.XPointerProcessor
 * JD-Core Version:    0.7.0.1
 */
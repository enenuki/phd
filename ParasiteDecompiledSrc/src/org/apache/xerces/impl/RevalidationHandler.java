package org.apache.xerces.impl;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.parser.XMLDocumentFilter;

public abstract interface RevalidationHandler
  extends XMLDocumentFilter
{
  public abstract boolean characterData(String paramString, Augmentations paramAugmentations);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.RevalidationHandler
 * JD-Core Version:    0.7.0.1
 */
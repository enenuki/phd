package org.cyberneko.html;

import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;

public abstract interface HTMLTagBalancingListener
{
  public abstract void ignoredStartElement(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations);
  
  public abstract void ignoredEndElement(QName paramQName, Augmentations paramAugmentations);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.HTMLTagBalancingListener
 * JD-Core Version:    0.7.0.1
 */
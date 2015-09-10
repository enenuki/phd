package org.apache.xerces.dom;

import org.w3c.dom.Node;

public abstract interface DeferredNode
  extends Node
{
  public static final short TYPE_NODE = 20;
  
  public abstract int getNodeIndex();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.DeferredNode
 * JD-Core Version:    0.7.0.1
 */
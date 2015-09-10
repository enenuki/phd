package org.apache.xerces.dom;

import java.io.Serializable;

class NodeListCache
  implements Serializable
{
  private static final long serialVersionUID = -7927529254918631002L;
  int fLength = -1;
  int fChildIndex = -1;
  ChildNode fChild;
  ParentNode fOwner;
  NodeListCache next;
  
  NodeListCache(ParentNode paramParentNode)
  {
    this.fOwner = paramParentNode;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.NodeListCache
 * JD-Core Version:    0.7.0.1
 */
package org.w3c.dom.traversal;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public abstract interface TreeWalker
{
  public abstract Node getRoot();
  
  public abstract int getWhatToShow();
  
  public abstract NodeFilter getFilter();
  
  public abstract boolean getExpandEntityReferences();
  
  public abstract Node getCurrentNode();
  
  public abstract void setCurrentNode(Node paramNode)
    throws DOMException;
  
  public abstract Node parentNode();
  
  public abstract Node firstChild();
  
  public abstract Node lastChild();
  
  public abstract Node previousSibling();
  
  public abstract Node nextSibling();
  
  public abstract Node previousNode();
  
  public abstract Node nextNode();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.traversal.TreeWalker
 * JD-Core Version:    0.7.0.1
 */
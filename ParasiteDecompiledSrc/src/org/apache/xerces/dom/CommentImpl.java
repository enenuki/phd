package org.apache.xerces.dom;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;

public class CommentImpl
  extends CharacterDataImpl
  implements CharacterData, Comment
{
  static final long serialVersionUID = -2685736833408134044L;
  
  public CommentImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString)
  {
    super(paramCoreDocumentImpl, paramString);
  }
  
  public short getNodeType()
  {
    return 8;
  }
  
  public String getNodeName()
  {
    return "#comment";
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom.CommentImpl
 * JD-Core Version:    0.7.0.1
 */
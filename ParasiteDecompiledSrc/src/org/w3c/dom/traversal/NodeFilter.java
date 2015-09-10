package org.w3c.dom.traversal;

import org.w3c.dom.Node;

public abstract interface NodeFilter
{
  public static final short FILTER_ACCEPT = 1;
  public static final short FILTER_REJECT = 2;
  public static final short FILTER_SKIP = 3;
  public static final int SHOW_ALL = -1;
  public static final int SHOW_ELEMENT = 1;
  public static final int SHOW_ATTRIBUTE = 2;
  public static final int SHOW_TEXT = 4;
  public static final int SHOW_CDATA_SECTION = 8;
  public static final int SHOW_ENTITY_REFERENCE = 16;
  public static final int SHOW_ENTITY = 32;
  public static final int SHOW_PROCESSING_INSTRUCTION = 64;
  public static final int SHOW_COMMENT = 128;
  public static final int SHOW_DOCUMENT = 256;
  public static final int SHOW_DOCUMENT_TYPE = 512;
  public static final int SHOW_DOCUMENT_FRAGMENT = 1024;
  public static final int SHOW_NOTATION = 2048;
  
  public abstract short acceptNode(Node paramNode);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.traversal.NodeFilter
 * JD-Core Version:    0.7.0.1
 */
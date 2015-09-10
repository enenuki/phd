package org.apache.xerces.xs;

public abstract interface XSIDCDefinition
  extends XSObject
{
  public static final short IC_KEY = 1;
  public static final short IC_KEYREF = 2;
  public static final short IC_UNIQUE = 3;
  
  public abstract short getCategory();
  
  public abstract String getSelectorStr();
  
  public abstract StringList getFieldStrs();
  
  public abstract XSIDCDefinition getRefKey();
  
  public abstract XSObjectList getAnnotations();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSIDCDefinition
 * JD-Core Version:    0.7.0.1
 */
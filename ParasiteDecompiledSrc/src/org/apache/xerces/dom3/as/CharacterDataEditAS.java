package org.apache.xerces.dom3.as;

/**
 * @deprecated
 */
public abstract interface CharacterDataEditAS
  extends NodeEditAS
{
  public abstract boolean getIsWhitespaceOnly();
  
  public abstract boolean canSetData(int paramInt1, int paramInt2);
  
  public abstract boolean canAppendData(String paramString);
  
  public abstract boolean canReplaceData(int paramInt1, int paramInt2, String paramString);
  
  public abstract boolean canInsertData(int paramInt, String paramString);
  
  public abstract boolean canDeleteData(int paramInt1, int paramInt2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.dom3.as.CharacterDataEditAS
 * JD-Core Version:    0.7.0.1
 */
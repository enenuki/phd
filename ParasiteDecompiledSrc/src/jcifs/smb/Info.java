package jcifs.smb;

abstract interface Info
{
  public abstract int getAttributes();
  
  public abstract long getCreateTime();
  
  public abstract long getLastWriteTime();
  
  public abstract long getSize();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.Info
 * JD-Core Version:    0.7.0.1
 */
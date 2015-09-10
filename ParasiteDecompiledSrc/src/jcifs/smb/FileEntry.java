package jcifs.smb;

public abstract interface FileEntry
{
  public abstract String getName();
  
  public abstract int getType();
  
  public abstract int getAttributes();
  
  public abstract long createTime();
  
  public abstract long lastModified();
  
  public abstract long length();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.FileEntry
 * JD-Core Version:    0.7.0.1
 */
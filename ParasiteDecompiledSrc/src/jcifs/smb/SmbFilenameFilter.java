package jcifs.smb;

public abstract interface SmbFilenameFilter
{
  public abstract boolean accept(SmbFile paramSmbFile, String paramString)
    throws SmbException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbFilenameFilter
 * JD-Core Version:    0.7.0.1
 */
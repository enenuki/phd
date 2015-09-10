package jcifs.smb;

public abstract interface SmbFileFilter
{
  public abstract boolean accept(SmbFile paramSmbFile)
    throws SmbException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbFileFilter
 * JD-Core Version:    0.7.0.1
 */
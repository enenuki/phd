package jcifs.dcerpc;

import jcifs.dcerpc.ndr.NdrBuffer;

public abstract interface DcerpcSecurityProvider
{
  public abstract void wrap(NdrBuffer paramNdrBuffer)
    throws DcerpcException;
  
  public abstract void unwrap(NdrBuffer paramNdrBuffer)
    throws DcerpcException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.DcerpcSecurityProvider
 * JD-Core Version:    0.7.0.1
 */
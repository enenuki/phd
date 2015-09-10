package jcifs.dcerpc.ndr;

public abstract class NdrObject
{
  public abstract void encode(NdrBuffer paramNdrBuffer)
    throws NdrException;
  
  public abstract void decode(NdrBuffer paramNdrBuffer)
    throws NdrException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.ndr.NdrObject
 * JD-Core Version:    0.7.0.1
 */
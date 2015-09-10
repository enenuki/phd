package org.apache.http.impl.auth;

import java.io.IOException;

public abstract interface SpnegoTokenGenerator
{
  public abstract byte[] generateSpnegoDERObject(byte[] paramArrayOfByte)
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.auth.SpnegoTokenGenerator
 * JD-Core Version:    0.7.0.1
 */
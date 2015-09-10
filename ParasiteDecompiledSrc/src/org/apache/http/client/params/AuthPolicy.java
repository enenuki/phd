package org.apache.http.client.params;

import org.apache.http.annotation.Immutable;

@Immutable
public final class AuthPolicy
{
  public static final String NTLM = "NTLM";
  public static final String DIGEST = "Digest";
  public static final String BASIC = "Basic";
  public static final String SPNEGO = "negotiate";
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.params.AuthPolicy
 * JD-Core Version:    0.7.0.1
 */
package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpParams;

@Deprecated
public abstract interface SocketFactory
{
  public abstract Socket createSocket()
    throws IOException;
  
  public abstract Socket connectSocket(Socket paramSocket, String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2, HttpParams paramHttpParams)
    throws IOException, UnknownHostException, ConnectTimeoutException;
  
  public abstract boolean isSecure(Socket paramSocket)
    throws IllegalArgumentException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.conn.scheme.SocketFactory
 * JD-Core Version:    0.7.0.1
 */
package org.apache.http;

import java.io.IOException;

public abstract interface HttpClientConnection
  extends HttpConnection
{
  public abstract boolean isResponseAvailable(int paramInt)
    throws IOException;
  
  public abstract void sendRequestHeader(HttpRequest paramHttpRequest)
    throws HttpException, IOException;
  
  public abstract void sendRequestEntity(HttpEntityEnclosingRequest paramHttpEntityEnclosingRequest)
    throws HttpException, IOException;
  
  public abstract HttpResponse receiveResponseHeader()
    throws HttpException, IOException;
  
  public abstract void receiveResponseEntity(HttpResponse paramHttpResponse)
    throws HttpException, IOException;
  
  public abstract void flush()
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.HttpClientConnection
 * JD-Core Version:    0.7.0.1
 */
package org.apache.http.entity.mime.content;

public abstract interface ContentDescriptor
{
  public abstract String getMimeType();
  
  public abstract String getMediaType();
  
  public abstract String getSubType();
  
  public abstract String getCharset();
  
  public abstract String getTransferEncoding();
  
  public abstract long getContentLength();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.content.ContentDescriptor
 * JD-Core Version:    0.7.0.1
 */
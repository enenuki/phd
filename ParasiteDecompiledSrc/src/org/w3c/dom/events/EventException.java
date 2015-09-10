package org.w3c.dom.events;

public class EventException
  extends RuntimeException
{
  public short code;
  public static final short UNSPECIFIED_EVENT_TYPE_ERR = 0;
  
  public EventException(short paramShort, String paramString)
  {
    super(paramString);
    this.code = paramShort;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.events.EventException
 * JD-Core Version:    0.7.0.1
 */
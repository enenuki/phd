package org.w3c.dom.ls;

import org.w3c.dom.events.Event;

public abstract interface LSProgressEvent
  extends Event
{
  public abstract LSInput getInput();
  
  public abstract int getPosition();
  
  public abstract int getTotalSize();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.ls.LSProgressEvent
 * JD-Core Version:    0.7.0.1
 */
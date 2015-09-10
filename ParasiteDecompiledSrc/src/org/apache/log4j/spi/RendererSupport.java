package org.apache.log4j.spi;

import org.apache.log4j.or.ObjectRenderer;
import org.apache.log4j.or.RendererMap;

public abstract interface RendererSupport
{
  public abstract RendererMap getRendererMap();
  
  public abstract void setRenderer(Class paramClass, ObjectRenderer paramObjectRenderer);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.RendererSupport
 * JD-Core Version:    0.7.0.1
 */
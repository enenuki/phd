package org.hibernate.id;

import java.io.Serializable;
import java.util.UUID;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface UUIDGenerationStrategy
  extends Serializable
{
  public abstract int getGeneratedVersion();
  
  public abstract UUID generateUUID(SessionImplementor paramSessionImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.UUIDGenerationStrategy
 * JD-Core Version:    0.7.0.1
 */
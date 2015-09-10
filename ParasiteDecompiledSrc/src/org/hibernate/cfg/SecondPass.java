package org.hibernate.cfg;

import java.io.Serializable;
import java.util.Map;
import org.hibernate.MappingException;

public abstract interface SecondPass
  extends Serializable
{
  public abstract void doSecondPass(Map paramMap)
    throws MappingException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.SecondPass
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.mapping;

import java.util.Map;

public abstract interface MetaAttributable
{
  public abstract Map getMetaAttributes();
  
  public abstract void setMetaAttributes(Map paramMap);
  
  public abstract MetaAttribute getMetaAttribute(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.MetaAttributable
 * JD-Core Version:    0.7.0.1
 */
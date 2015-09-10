package org.hibernate.id.insert;

import java.io.Serializable;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface InsertGeneratedIdentifierDelegate
{
  public abstract IdentifierGeneratingInsert prepareIdentifierGeneratingInsert();
  
  public abstract Serializable performInsert(String paramString, SessionImplementor paramSessionImplementor, Binder paramBinder);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.insert.InsertGeneratedIdentifierDelegate
 * JD-Core Version:    0.7.0.1
 */
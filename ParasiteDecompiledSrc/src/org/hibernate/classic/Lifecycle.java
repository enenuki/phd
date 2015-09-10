package org.hibernate.classic;

import java.io.Serializable;
import org.hibernate.CallbackException;
import org.hibernate.Session;

public abstract interface Lifecycle
{
  public static final boolean VETO = true;
  public static final boolean NO_VETO = false;
  
  public abstract boolean onSave(Session paramSession)
    throws CallbackException;
  
  public abstract boolean onUpdate(Session paramSession)
    throws CallbackException;
  
  public abstract boolean onDelete(Session paramSession)
    throws CallbackException;
  
  public abstract void onLoad(Session paramSession, Serializable paramSerializable);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.classic.Lifecycle
 * JD-Core Version:    0.7.0.1
 */
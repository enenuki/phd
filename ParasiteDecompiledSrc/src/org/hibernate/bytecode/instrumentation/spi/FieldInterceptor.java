package org.hibernate.bytecode.instrumentation.spi;

import org.hibernate.engine.spi.SessionImplementor;

public abstract interface FieldInterceptor
{
  public abstract void setSession(SessionImplementor paramSessionImplementor);
  
  public abstract boolean isInitialized();
  
  public abstract boolean isInitialized(String paramString);
  
  public abstract void dirty();
  
  public abstract boolean isDirty();
  
  public abstract void clearDirty();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.instrumentation.spi.FieldInterceptor
 * JD-Core Version:    0.7.0.1
 */
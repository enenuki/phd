package org.hibernate.tuple.component;

import java.io.Serializable;
import java.lang.reflect.Method;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.tuple.Tuplizer;

public abstract interface ComponentTuplizer
  extends Tuplizer, Serializable
{
  public abstract Object getParent(Object paramObject);
  
  public abstract void setParent(Object paramObject1, Object paramObject2, SessionFactoryImplementor paramSessionFactoryImplementor);
  
  public abstract boolean hasParentProperty();
  
  public abstract boolean isMethodOf(Method paramMethod);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.component.ComponentTuplizer
 * JD-Core Version:    0.7.0.1
 */
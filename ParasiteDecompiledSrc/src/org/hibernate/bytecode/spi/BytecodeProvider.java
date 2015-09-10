package org.hibernate.bytecode.spi;

import org.hibernate.bytecode.buildtime.spi.ClassFilter;
import org.hibernate.bytecode.buildtime.spi.FieldFilter;

public abstract interface BytecodeProvider
{
  public abstract ProxyFactoryFactory getProxyFactoryFactory();
  
  public abstract ReflectionOptimizer getReflectionOptimizer(Class paramClass, String[] paramArrayOfString1, String[] paramArrayOfString2, Class[] paramArrayOfClass);
  
  public abstract ClassTransformer getTransformer(ClassFilter paramClassFilter, FieldFilter paramFieldFilter);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.spi.BytecodeProvider
 * JD-Core Version:    0.7.0.1
 */
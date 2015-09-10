package org.hibernate.bytecode.spi;

import org.hibernate.proxy.ProxyFactory;

public abstract interface ProxyFactoryFactory
{
  public abstract ProxyFactory buildProxyFactory();
  
  public abstract BasicProxyFactory buildBasicProxyFactory(Class paramClass, Class[] paramArrayOfClass);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.spi.ProxyFactoryFactory
 * JD-Core Version:    0.7.0.1
 */
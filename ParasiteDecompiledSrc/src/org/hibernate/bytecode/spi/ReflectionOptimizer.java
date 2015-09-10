package org.hibernate.bytecode.spi;

public abstract interface ReflectionOptimizer
{
  public abstract InstantiationOptimizer getInstantiationOptimizer();
  
  public abstract AccessOptimizer getAccessOptimizer();
  
  public static abstract interface AccessOptimizer
  {
    public abstract String[] getPropertyNames();
    
    public abstract Object[] getPropertyValues(Object paramObject);
    
    public abstract void setPropertyValues(Object paramObject, Object[] paramArrayOfObject);
  }
  
  public static abstract interface InstantiationOptimizer
  {
    public abstract Object newInstance();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.spi.ReflectionOptimizer
 * JD-Core Version:    0.7.0.1
 */
package javassist.scopedpool;

import javassist.ClassPool;

public abstract interface ScopedClassPoolFactory
{
  public abstract ScopedClassPool create(ClassLoader paramClassLoader, ClassPool paramClassPool, ScopedClassPoolRepository paramScopedClassPoolRepository);
  
  public abstract ScopedClassPool create(ClassPool paramClassPool, ScopedClassPoolRepository paramScopedClassPoolRepository);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.scopedpool.ScopedClassPoolFactory
 * JD-Core Version:    0.7.0.1
 */
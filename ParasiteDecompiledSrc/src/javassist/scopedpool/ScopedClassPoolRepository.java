package javassist.scopedpool;

import java.util.Map;
import javassist.ClassPool;

public abstract interface ScopedClassPoolRepository
{
  public abstract void setClassPoolFactory(ScopedClassPoolFactory paramScopedClassPoolFactory);
  
  public abstract ScopedClassPoolFactory getClassPoolFactory();
  
  public abstract boolean isPrune();
  
  public abstract void setPrune(boolean paramBoolean);
  
  public abstract ScopedClassPool createScopedClassPool(ClassLoader paramClassLoader, ClassPool paramClassPool);
  
  public abstract ClassPool findClassPool(ClassLoader paramClassLoader);
  
  public abstract ClassPool registerClassLoader(ClassLoader paramClassLoader);
  
  public abstract Map getRegisteredCLs();
  
  public abstract void clearUnregisteredClassLoaders();
  
  public abstract void unregisterClassLoader(ClassLoader paramClassLoader);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.scopedpool.ScopedClassPoolRepository
 * JD-Core Version:    0.7.0.1
 */
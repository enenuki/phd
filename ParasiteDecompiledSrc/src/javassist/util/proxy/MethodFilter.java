package javassist.util.proxy;

import java.lang.reflect.Method;

public abstract interface MethodFilter
{
  public abstract boolean isHandled(Method paramMethod);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.proxy.MethodFilter
 * JD-Core Version:    0.7.0.1
 */
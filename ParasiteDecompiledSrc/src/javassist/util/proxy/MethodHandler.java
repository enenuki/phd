package javassist.util.proxy;

import java.lang.reflect.Method;

public abstract interface MethodHandler
{
  public abstract Object invoke(Object paramObject, Method paramMethod1, Method paramMethod2, Object[] paramArrayOfObject)
    throws Throwable;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.proxy.MethodHandler
 * JD-Core Version:    0.7.0.1
 */
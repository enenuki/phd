package javassist.tools.reflect;

public abstract interface Metalevel
{
  public abstract ClassMetaobject _getClass();
  
  public abstract Metaobject _getMetaobject();
  
  public abstract void _setMetaobject(Metaobject paramMetaobject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.reflect.Metalevel
 * JD-Core Version:    0.7.0.1
 */
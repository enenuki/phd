package javassist;

public abstract interface Translator
{
  public abstract void start(ClassPool paramClassPool)
    throws NotFoundException, CannotCompileException;
  
  public abstract void onLoad(ClassPool paramClassPool, String paramString)
    throws NotFoundException, CannotCompileException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.Translator
 * JD-Core Version:    0.7.0.1
 */
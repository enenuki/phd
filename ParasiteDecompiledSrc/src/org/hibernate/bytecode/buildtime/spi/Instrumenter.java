package org.hibernate.bytecode.buildtime.spi;

import java.io.File;
import java.util.Set;

public abstract interface Instrumenter
{
  public abstract void execute(Set<File> paramSet);
  
  public static abstract interface Options
  {
    public abstract boolean performExtendedInstrumentation();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.buildtime.spi.Instrumenter
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.bytecode.buildtime.spi;

public abstract interface ClassDescriptor
{
  public abstract String getName();
  
  public abstract boolean isInstrumented();
  
  public abstract byte[] getBytes();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.buildtime.spi.ClassDescriptor
 * JD-Core Version:    0.7.0.1
 */
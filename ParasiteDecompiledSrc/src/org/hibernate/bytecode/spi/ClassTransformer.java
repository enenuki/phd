package org.hibernate.bytecode.spi;

import java.security.ProtectionDomain;

public abstract interface ClassTransformer
{
  public abstract byte[] transform(ClassLoader paramClassLoader, String paramString, Class paramClass, ProtectionDomain paramProtectionDomain, byte[] paramArrayOfByte);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.spi.ClassTransformer
 * JD-Core Version:    0.7.0.1
 */
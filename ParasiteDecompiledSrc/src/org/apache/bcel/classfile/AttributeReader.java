package org.apache.bcel.classfile;

import java.io.DataInputStream;

public abstract interface AttributeReader
{
  public abstract Attribute createAttribute(int paramInt1, int paramInt2, DataInputStream paramDataInputStream, ConstantPool paramConstantPool);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.AttributeReader
 * JD-Core Version:    0.7.0.1
 */
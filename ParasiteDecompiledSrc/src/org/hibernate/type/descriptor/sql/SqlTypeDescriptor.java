package org.hibernate.type.descriptor.sql;

import java.io.Serializable;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;

public abstract interface SqlTypeDescriptor
  extends Serializable
{
  public abstract int getSqlType();
  
  public abstract boolean canBeRemapped();
  
  public abstract <X> ValueBinder<X> getBinder(JavaTypeDescriptor<X> paramJavaTypeDescriptor);
  
  public abstract <X> ValueExtractor<X> getExtractor(JavaTypeDescriptor<X> paramJavaTypeDescriptor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.SqlTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */
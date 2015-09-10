package org.hibernate.mapping;

import java.io.Serializable;
import java.util.Iterator;
import org.hibernate.FetchMode;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.type.Type;

public abstract interface Value
  extends Serializable
{
  public abstract int getColumnSpan();
  
  public abstract Iterator getColumnIterator();
  
  public abstract Type getType()
    throws MappingException;
  
  public abstract FetchMode getFetchMode();
  
  public abstract Table getTable();
  
  public abstract boolean hasFormula();
  
  public abstract boolean isAlternateUniqueKey();
  
  public abstract boolean isNullable();
  
  public abstract boolean[] getColumnUpdateability();
  
  public abstract boolean[] getColumnInsertability();
  
  public abstract void createForeignKey()
    throws MappingException;
  
  public abstract boolean isSimpleValue();
  
  public abstract boolean isValid(Mapping paramMapping)
    throws MappingException;
  
  public abstract void setTypeUsingReflection(String paramString1, String paramString2)
    throws MappingException;
  
  public abstract Object accept(ValueVisitor paramValueVisitor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Value
 * JD-Core Version:    0.7.0.1
 */
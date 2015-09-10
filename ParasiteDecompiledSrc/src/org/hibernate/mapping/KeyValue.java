package org.hibernate.mapping;

import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.factory.IdentifierGeneratorFactory;

public abstract interface KeyValue
  extends Value
{
  public abstract IdentifierGenerator createIdentifierGenerator(IdentifierGeneratorFactory paramIdentifierGeneratorFactory, Dialect paramDialect, String paramString1, String paramString2, RootClass paramRootClass)
    throws MappingException;
  
  public abstract boolean isIdentityColumn(IdentifierGeneratorFactory paramIdentifierGeneratorFactory, Dialect paramDialect);
  
  public abstract void createForeignKeyOfEntity(String paramString);
  
  public abstract boolean isCascadeDeleteEnabled();
  
  public abstract String getNullValue();
  
  public abstract boolean isUpdateable();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.KeyValue
 * JD-Core Version:    0.7.0.1
 */
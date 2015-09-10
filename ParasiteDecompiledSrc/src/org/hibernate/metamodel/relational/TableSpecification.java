package org.hibernate.metamodel.relational;

import org.hibernate.dialect.Dialect;

public abstract interface TableSpecification
  extends ValueContainer, Loggable
{
  public abstract Schema getSchema();
  
  public abstract int getTableNumber();
  
  public abstract PrimaryKey getPrimaryKey();
  
  public abstract Column locateOrCreateColumn(String paramString);
  
  public abstract Tuple createTuple(String paramString);
  
  public abstract DerivedValue locateOrCreateDerivedValue(String paramString);
  
  public abstract Iterable<ForeignKey> getForeignKeys();
  
  public abstract ForeignKey createForeignKey(TableSpecification paramTableSpecification, String paramString);
  
  public abstract Iterable<Index> getIndexes();
  
  public abstract Index getOrCreateIndex(String paramString);
  
  public abstract Iterable<UniqueKey> getUniqueKeys();
  
  public abstract UniqueKey getOrCreateUniqueKey(String paramString);
  
  public abstract Iterable<CheckConstraint> getCheckConstraints();
  
  public abstract void addCheckConstraint(String paramString);
  
  public abstract Iterable<String> getComments();
  
  public abstract void addComment(String paramString);
  
  public abstract String getQualifiedName(Dialect paramDialect);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.TableSpecification
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.metamodel.source.binder;

public abstract interface SubclassEntityContainer
{
  public abstract void add(SubclassEntitySource paramSubclassEntitySource);
  
  public abstract Iterable<SubclassEntitySource> subclassEntitySources();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.SubclassEntityContainer
 * JD-Core Version:    0.7.0.1
 */
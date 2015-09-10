package org.hibernate.mapping;

public abstract interface PersistentClassVisitor
{
  public abstract Object accept(RootClass paramRootClass);
  
  public abstract Object accept(UnionSubclass paramUnionSubclass);
  
  public abstract Object accept(SingleTableSubclass paramSingleTableSubclass);
  
  public abstract Object accept(JoinedSubclass paramJoinedSubclass);
  
  public abstract Object accept(Subclass paramSubclass);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.PersistentClassVisitor
 * JD-Core Version:    0.7.0.1
 */
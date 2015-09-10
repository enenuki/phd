package org.hibernate.id;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;

public abstract interface PostInsertIdentifierGenerator
  extends IdentifierGenerator
{
  public abstract InsertGeneratedIdentifierDelegate getInsertGeneratedIdentifierDelegate(PostInsertIdentityPersister paramPostInsertIdentityPersister, Dialect paramDialect, boolean paramBoolean)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.PostInsertIdentifierGenerator
 * JD-Core Version:    0.7.0.1
 */
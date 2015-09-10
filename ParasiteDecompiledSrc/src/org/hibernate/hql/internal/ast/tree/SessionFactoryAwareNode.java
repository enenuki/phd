package org.hibernate.hql.internal.ast.tree;

import org.hibernate.engine.spi.SessionFactoryImplementor;

public abstract interface SessionFactoryAwareNode
{
  public abstract void setSessionFactory(SessionFactoryImplementor paramSessionFactoryImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.SessionFactoryAwareNode
 * JD-Core Version:    0.7.0.1
 */
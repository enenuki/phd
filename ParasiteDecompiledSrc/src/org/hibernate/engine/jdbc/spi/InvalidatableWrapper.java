package org.hibernate.engine.jdbc.spi;

public abstract interface InvalidatableWrapper<T>
  extends JdbcWrapper<T>
{
  public abstract void invalidate();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.InvalidatableWrapper
 * JD-Core Version:    0.7.0.1
 */
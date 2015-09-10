package org.hibernate.exception.spi;

import java.sql.SQLException;

public abstract interface ViolatedConstraintNameExtracter
{
  public abstract String extractConstraintName(SQLException paramSQLException);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.spi.ViolatedConstraintNameExtracter
 * JD-Core Version:    0.7.0.1
 */
package org.hibernate.type.descriptor;

import org.hibernate.engine.jdbc.LobCreator;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

public abstract interface WrapperOptions
{
  public abstract boolean useStreamForLobBinding();
  
  public abstract LobCreator getLobCreator();
  
  public abstract SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor paramSqlTypeDescriptor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.WrapperOptions
 * JD-Core Version:    0.7.0.1
 */
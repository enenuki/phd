package org.hibernate.dialect;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface LobMergeStrategy
{
  public abstract Blob mergeBlob(Blob paramBlob1, Blob paramBlob2, SessionImplementor paramSessionImplementor);
  
  public abstract Clob mergeClob(Clob paramClob1, Clob paramClob2, SessionImplementor paramSessionImplementor);
  
  public abstract NClob mergeNClob(NClob paramNClob1, NClob paramNClob2, SessionImplementor paramSessionImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.LobMergeStrategy
 * JD-Core Version:    0.7.0.1
 */
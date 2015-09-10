package org.hibernate.persister.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;

public abstract interface Loadable
  extends EntityPersister
{
  public static final String ROWID_ALIAS = "rowid_";
  
  public abstract boolean hasSubclasses();
  
  public abstract Type getDiscriminatorType();
  
  public abstract Object getDiscriminatorValue();
  
  public abstract String getSubclassForDiscriminatorValue(Object paramObject);
  
  public abstract String[] getIdentifierColumnNames();
  
  public abstract String[] getIdentifierAliases(String paramString);
  
  public abstract String[] getPropertyAliases(String paramString, int paramInt);
  
  public abstract String[] getPropertyColumnNames(int paramInt);
  
  public abstract String getDiscriminatorAlias(String paramString);
  
  public abstract String getDiscriminatorColumnName();
  
  public abstract boolean hasRowId();
  
  public abstract Object[] hydrate(ResultSet paramResultSet, Serializable paramSerializable, Object paramObject, Loadable paramLoadable, String[][] paramArrayOfString, boolean paramBoolean, SessionImplementor paramSessionImplementor)
    throws SQLException, HibernateException;
  
  public abstract boolean isAbstract();
  
  public abstract void registerAffectingFetchProfile(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.Loadable
 * JD-Core Version:    0.7.0.1
 */
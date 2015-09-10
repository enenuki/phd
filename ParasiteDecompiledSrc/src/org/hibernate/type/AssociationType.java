package org.hibernate.type;

import java.util.Map;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.persister.entity.Joinable;

public abstract interface AssociationType
  extends Type
{
  public abstract ForeignKeyDirection getForeignKeyDirection();
  
  public abstract boolean useLHSPrimaryKey();
  
  public abstract String getLHSPropertyName();
  
  public abstract String getRHSUniqueKeyPropertyName();
  
  public abstract Joinable getAssociatedJoinable(SessionFactoryImplementor paramSessionFactoryImplementor)
    throws MappingException;
  
  public abstract String getAssociatedEntityName(SessionFactoryImplementor paramSessionFactoryImplementor)
    throws MappingException;
  
  public abstract String getOnCondition(String paramString, SessionFactoryImplementor paramSessionFactoryImplementor, Map paramMap)
    throws MappingException;
  
  public abstract boolean isAlwaysDirtyChecked();
  
  public abstract boolean isEmbeddedInXML();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AssociationType
 * JD-Core Version:    0.7.0.1
 */
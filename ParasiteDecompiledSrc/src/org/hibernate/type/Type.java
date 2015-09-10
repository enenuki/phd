package org.hibernate.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.dom4j.Node;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metamodel.relational.Size;

public abstract interface Type
  extends Serializable
{
  public abstract boolean isAssociationType();
  
  public abstract boolean isCollectionType();
  
  public abstract boolean isEntityType();
  
  public abstract boolean isAnyType();
  
  public abstract boolean isComponentType();
  
  public abstract int getColumnSpan(Mapping paramMapping)
    throws MappingException;
  
  public abstract int[] sqlTypes(Mapping paramMapping)
    throws MappingException;
  
  public abstract Size[] dictatedSizes(Mapping paramMapping)
    throws MappingException;
  
  public abstract Size[] defaultSizes(Mapping paramMapping)
    throws MappingException;
  
  public abstract Class getReturnedClass();
  
  public abstract boolean isXMLElement();
  
  public abstract boolean isSame(Object paramObject1, Object paramObject2)
    throws HibernateException;
  
  public abstract boolean isEqual(Object paramObject1, Object paramObject2)
    throws HibernateException;
  
  public abstract boolean isEqual(Object paramObject1, Object paramObject2, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws HibernateException;
  
  public abstract int getHashCode(Object paramObject)
    throws HibernateException;
  
  public abstract int getHashCode(Object paramObject, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws HibernateException;
  
  public abstract int compare(Object paramObject1, Object paramObject2);
  
  public abstract boolean isDirty(Object paramObject1, Object paramObject2, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract boolean isDirty(Object paramObject1, Object paramObject2, boolean[] paramArrayOfBoolean, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract boolean isModified(Object paramObject1, Object paramObject2, boolean[] paramArrayOfBoolean, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Object nullSafeGet(ResultSet paramResultSet, String[] paramArrayOfString, SessionImplementor paramSessionImplementor, Object paramObject)
    throws HibernateException, SQLException;
  
  public abstract Object nullSafeGet(ResultSet paramResultSet, String paramString, SessionImplementor paramSessionImplementor, Object paramObject)
    throws HibernateException, SQLException;
  
  public abstract void nullSafeSet(PreparedStatement paramPreparedStatement, Object paramObject, int paramInt, boolean[] paramArrayOfBoolean, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
  
  public abstract void nullSafeSet(PreparedStatement paramPreparedStatement, Object paramObject, int paramInt, SessionImplementor paramSessionImplementor)
    throws HibernateException, SQLException;
  
  public abstract void setToXMLNode(Node paramNode, Object paramObject, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws HibernateException;
  
  public abstract String toLoggableString(Object paramObject, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws HibernateException;
  
  public abstract Object fromXMLNode(Node paramNode, Mapping paramMapping)
    throws HibernateException;
  
  public abstract String getName();
  
  public abstract Object deepCopy(Object paramObject, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws HibernateException;
  
  public abstract boolean isMutable();
  
  public abstract Serializable disassemble(Object paramObject1, SessionImplementor paramSessionImplementor, Object paramObject2)
    throws HibernateException;
  
  public abstract Object assemble(Serializable paramSerializable, SessionImplementor paramSessionImplementor, Object paramObject)
    throws HibernateException;
  
  public abstract void beforeAssemble(Serializable paramSerializable, SessionImplementor paramSessionImplementor);
  
  public abstract Object hydrate(ResultSet paramResultSet, String[] paramArrayOfString, SessionImplementor paramSessionImplementor, Object paramObject)
    throws HibernateException, SQLException;
  
  public abstract Object resolve(Object paramObject1, SessionImplementor paramSessionImplementor, Object paramObject2)
    throws HibernateException;
  
  public abstract Object semiResolve(Object paramObject1, SessionImplementor paramSessionImplementor, Object paramObject2)
    throws HibernateException;
  
  public abstract Type getSemiResolvedType(SessionFactoryImplementor paramSessionFactoryImplementor);
  
  public abstract Object replace(Object paramObject1, Object paramObject2, SessionImplementor paramSessionImplementor, Object paramObject3, Map paramMap)
    throws HibernateException;
  
  public abstract Object replace(Object paramObject1, Object paramObject2, SessionImplementor paramSessionImplementor, Object paramObject3, Map paramMap, ForeignKeyDirection paramForeignKeyDirection)
    throws HibernateException;
  
  public abstract boolean[] toColumnNullness(Object paramObject, Mapping paramMapping);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.Type
 * JD-Core Version:    0.7.0.1
 */
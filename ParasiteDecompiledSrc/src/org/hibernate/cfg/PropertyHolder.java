package org.hibernate.cfg;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.mapping.Join;
import org.hibernate.mapping.KeyValue;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;

public abstract interface PropertyHolder
{
  public abstract String getClassName();
  
  public abstract String getEntityOwnerClassName();
  
  public abstract Table getTable();
  
  public abstract void addProperty(Property paramProperty, XClass paramXClass);
  
  public abstract void addProperty(Property paramProperty, Ejb3Column[] paramArrayOfEjb3Column, XClass paramXClass);
  
  public abstract KeyValue getIdentifier();
  
  public abstract boolean isOrWithinEmbeddedId();
  
  public abstract PersistentClass getPersistentClass();
  
  public abstract boolean isComponent();
  
  public abstract boolean isEntity();
  
  public abstract void setParentProperty(String paramString);
  
  public abstract String getPath();
  
  public abstract Column[] getOverriddenColumn(String paramString);
  
  public abstract JoinColumn[] getOverriddenJoinColumn(String paramString);
  
  public abstract JoinTable getJoinTable(XProperty paramXProperty);
  
  public abstract String getEntityName();
  
  public abstract Join addJoin(JoinTable paramJoinTable, boolean paramBoolean);
  
  public abstract boolean isInIdClass();
  
  public abstract void setInIdClass(Boolean paramBoolean);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.PropertyHolder
 * JD-Core Version:    0.7.0.1
 */
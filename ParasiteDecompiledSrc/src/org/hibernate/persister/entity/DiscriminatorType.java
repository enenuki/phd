/*   1:    */ package org.hibernate.persister.entity;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.dom4j.Node;
/*   8:    */ import org.hibernate.EntityMode;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.engine.spi.Mapping;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  14:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  15:    */ import org.hibernate.internal.util.compare.EqualsHelper;
/*  16:    */ import org.hibernate.metadata.ClassMetadata;
/*  17:    */ import org.hibernate.metamodel.relational.Size;
/*  18:    */ import org.hibernate.type.AbstractType;
/*  19:    */ import org.hibernate.type.Type;
/*  20:    */ 
/*  21:    */ public class DiscriminatorType
/*  22:    */   extends AbstractType
/*  23:    */ {
/*  24:    */   private final Type underlyingType;
/*  25:    */   private final Loadable persister;
/*  26:    */   
/*  27:    */   public DiscriminatorType(Type underlyingType, Loadable persister)
/*  28:    */   {
/*  29: 55 */     this.underlyingType = underlyingType;
/*  30: 56 */     this.persister = persister;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Class getReturnedClass()
/*  34:    */   {
/*  35: 60 */     return Class.class;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getName()
/*  39:    */   {
/*  40: 64 */     return getClass().getName();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean isMutable()
/*  44:    */   {
/*  45: 68 */     return false;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/*  49:    */     throws HibernateException, SQLException
/*  50:    */   {
/*  51: 76 */     return nullSafeGet(rs, names[0], session, owner);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
/*  55:    */     throws HibernateException, SQLException
/*  56:    */   {
/*  57: 84 */     Object discriminatorValue = this.underlyingType.nullSafeGet(rs, name, session, owner);
/*  58: 85 */     String entityName = this.persister.getSubclassForDiscriminatorValue(discriminatorValue);
/*  59: 86 */     if (entityName == null) {
/*  60: 87 */       throw new HibernateException("Unable to resolve discriminator value [" + discriminatorValue + "] to entity name");
/*  61:    */     }
/*  62: 89 */     EntityPersister entityPersister = session.getEntityPersister(entityName, null);
/*  63: 90 */     return EntityMode.POJO == entityPersister.getEntityMode() ? entityPersister.getMappedClass() : entityName;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/*  67:    */     throws HibernateException, SQLException
/*  68:    */   {
/*  69: 99 */     nullSafeSet(st, value, index, session);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/*  73:    */     throws HibernateException, SQLException
/*  74:    */   {
/*  75:107 */     String entityName = session.getFactory().getClassMetadata((Class)value).getEntityName();
/*  76:108 */     Loadable entityPersister = (Loadable)session.getFactory().getEntityPersister(entityName);
/*  77:109 */     this.underlyingType.nullSafeSet(st, entityPersister.getDiscriminatorValue(), index, session);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/*  81:    */     throws HibernateException
/*  82:    */   {
/*  83:113 */     return value == null ? "[null]" : value.toString();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Object deepCopy(Object value, SessionFactoryImplementor factory)
/*  87:    */     throws HibernateException
/*  88:    */   {
/*  89:118 */     return value;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/*  93:    */     throws HibernateException
/*  94:    */   {
/*  95:123 */     return original;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/*  99:    */   {
/* 100:127 */     return value == null ? ArrayHelper.FALSE : ArrayHelper.TRUE;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 104:    */     throws HibernateException
/* 105:    */   {
/* 106:134 */     return EqualsHelper.equals(old, current);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int[] sqlTypes(Mapping mapping)
/* 110:    */     throws MappingException
/* 111:    */   {
/* 112:141 */     return this.underlyingType.sqlTypes(mapping);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Size[] dictatedSizes(Mapping mapping)
/* 116:    */     throws MappingException
/* 117:    */   {
/* 118:146 */     return this.underlyingType.dictatedSizes(mapping);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Size[] defaultSizes(Mapping mapping)
/* 122:    */     throws MappingException
/* 123:    */   {
/* 124:151 */     return this.underlyingType.defaultSizes(mapping);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getColumnSpan(Mapping mapping)
/* 128:    */     throws MappingException
/* 129:    */   {
/* 130:155 */     return this.underlyingType.getColumnSpan(mapping);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/* 134:    */     throws HibernateException
/* 135:    */   {}
/* 136:    */   
/* 137:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 138:    */     throws HibernateException
/* 139:    */   {
/* 140:163 */     return null;
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.DiscriminatorType
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.engine.spi.EntityKey;
/*  10:    */ import org.hibernate.engine.spi.Mapping;
/*  11:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  14:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  15:    */ import org.hibernate.metamodel.relational.Size;
/*  16:    */ import org.hibernate.persister.entity.EntityPersister;
/*  17:    */ 
/*  18:    */ public class OneToOneType
/*  19:    */   extends EntityType
/*  20:    */ {
/*  21:    */   private final ForeignKeyDirection foreignKeyType;
/*  22:    */   private final String propertyName;
/*  23:    */   private final String entityName;
/*  24:    */   
/*  25:    */   public OneToOneType(TypeFactory.TypeScope scope, String referencedEntityName, ForeignKeyDirection foreignKeyType, String uniqueKeyPropertyName, boolean lazy, boolean unwrapProxy, boolean isEmbeddedInXML, String entityName, String propertyName)
/*  26:    */   {
/*  27: 60 */     super(scope, referencedEntityName, uniqueKeyPropertyName, !lazy, isEmbeddedInXML, unwrapProxy);
/*  28: 61 */     this.foreignKeyType = foreignKeyType;
/*  29: 62 */     this.propertyName = propertyName;
/*  30: 63 */     this.entityName = entityName;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getPropertyName()
/*  34:    */   {
/*  35: 67 */     return this.propertyName;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isNull(Object owner, SessionImplementor session)
/*  39:    */   {
/*  40: 71 */     if (this.propertyName != null)
/*  41:    */     {
/*  42: 72 */       EntityPersister ownerPersister = session.getFactory().getEntityPersister(this.entityName);
/*  43: 73 */       Serializable id = session.getContextEntityIdentifier(owner);
/*  44: 74 */       EntityKey entityKey = session.generateEntityKey(id, ownerPersister);
/*  45: 75 */       return session.getPersistenceContext().isPropertyNull(entityKey, getPropertyName());
/*  46:    */     }
/*  47: 78 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getColumnSpan(Mapping session)
/*  51:    */     throws MappingException
/*  52:    */   {
/*  53: 83 */     return 0;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int[] sqlTypes(Mapping session)
/*  57:    */     throws MappingException
/*  58:    */   {
/*  59: 87 */     return ArrayHelper.EMPTY_INT_ARRAY;
/*  60:    */   }
/*  61:    */   
/*  62: 90 */   private static final Size[] SIZES = new Size[0];
/*  63:    */   
/*  64:    */   public Size[] dictatedSizes(Mapping mapping)
/*  65:    */     throws MappingException
/*  66:    */   {
/*  67: 94 */     return SIZES;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Size[] defaultSizes(Mapping mapping)
/*  71:    */     throws MappingException
/*  72:    */   {
/*  73: 99 */     return SIZES;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/*  77:    */   {
/*  78:103 */     return ArrayHelper.EMPTY_BOOLEAN_ARRAY;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session) {}
/*  82:    */   
/*  83:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) {}
/*  84:    */   
/*  85:    */   public boolean isOneToOne()
/*  86:    */   {
/*  87:115 */     return true;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isDirty(Object old, Object current, SessionImplementor session)
/*  91:    */   {
/*  92:119 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/*  96:    */   {
/*  97:123 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isModified(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 101:    */   {
/* 102:127 */     return false;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public ForeignKeyDirection getForeignKeyDirection()
/* 106:    */   {
/* 107:131 */     return this.foreignKeyType;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Object hydrate(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 111:    */     throws HibernateException, SQLException
/* 112:    */   {
/* 113:141 */     return session.getContextEntityIdentifier(owner);
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected boolean isNullable()
/* 117:    */   {
/* 118:145 */     return this.foreignKeyType == ForeignKeyDirection.FOREIGN_KEY_TO_PARENT;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean useLHSPrimaryKey()
/* 122:    */   {
/* 123:149 */     return true;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Serializable disassemble(Object value, SessionImplementor session, Object owner)
/* 127:    */     throws HibernateException
/* 128:    */   {
/* 129:154 */     return null;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Object assemble(Serializable oid, SessionImplementor session, Object owner)
/* 133:    */     throws HibernateException
/* 134:    */   {
/* 135:162 */     return resolve(session.getContextEntityIdentifier(owner), session, owner);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isAlwaysDirtyChecked()
/* 139:    */   {
/* 140:172 */     return false;
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.OneToOneType
 * JD-Core Version:    0.7.0.1
 */
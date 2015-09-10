/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.dom4j.Element;
/*   8:    */ import org.dom4j.Node;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.internal.util.compare.EqualsHelper;
/*  13:    */ import org.hibernate.metamodel.relational.Size;
/*  14:    */ import org.hibernate.metamodel.relational.Size.LobMultiplier;
/*  15:    */ 
/*  16:    */ public abstract class AbstractType
/*  17:    */   implements Type
/*  18:    */ {
/*  19: 46 */   protected static final Size LEGACY_DICTATED_SIZE = new Size();
/*  20: 47 */   protected static final Size LEGACY_DEFAULT_SIZE = new Size(19, 2, 255L, Size.LobMultiplier.NONE);
/*  21:    */   
/*  22:    */   public boolean isAssociationType()
/*  23:    */   {
/*  24: 50 */     return false;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean isCollectionType()
/*  28:    */   {
/*  29: 54 */     return false;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean isComponentType()
/*  33:    */   {
/*  34: 58 */     return false;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean isEntityType()
/*  38:    */   {
/*  39: 62 */     return false;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean isXMLElement()
/*  43:    */   {
/*  44: 66 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int compare(Object x, Object y)
/*  48:    */   {
/*  49: 70 */     return ((Comparable)x).compareTo(y);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Serializable disassemble(Object value, SessionImplementor session, Object owner)
/*  53:    */     throws HibernateException
/*  54:    */   {
/*  55: 76 */     if (value == null) {
/*  56: 77 */       return null;
/*  57:    */     }
/*  58: 80 */     return (Serializable)deepCopy(value, session.getFactory());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Object assemble(Serializable cached, SessionImplementor session, Object owner)
/*  62:    */     throws HibernateException
/*  63:    */   {
/*  64: 86 */     if (cached == null) {
/*  65: 87 */       return null;
/*  66:    */     }
/*  67: 90 */     return deepCopy(cached, session.getFactory());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean isDirty(Object old, Object current, SessionImplementor session)
/*  71:    */     throws HibernateException
/*  72:    */   {
/*  73: 95 */     return !isSame(old, current);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object hydrate(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/*  77:    */     throws HibernateException, SQLException
/*  78:    */   {
/*  79:106 */     return nullSafeGet(rs, names, session, owner);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object resolve(Object value, SessionImplementor session, Object owner)
/*  83:    */     throws HibernateException
/*  84:    */   {
/*  85:111 */     return value;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Object semiResolve(Object value, SessionImplementor session, Object owner)
/*  89:    */     throws HibernateException
/*  90:    */   {
/*  91:116 */     return value;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isAnyType()
/*  95:    */   {
/*  96:120 */     return false;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isModified(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 100:    */     throws HibernateException
/* 101:    */   {
/* 102:125 */     return isDirty(old, current, session);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean isSame(Object x, Object y)
/* 106:    */     throws HibernateException
/* 107:    */   {
/* 108:129 */     return isEqual(x, y);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isEqual(Object x, Object y)
/* 112:    */   {
/* 113:133 */     return EqualsHelper.equals(x, y);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getHashCode(Object x)
/* 117:    */   {
/* 118:137 */     return x.hashCode();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isEqual(Object x, Object y, SessionFactoryImplementor factory)
/* 122:    */   {
/* 123:141 */     return isEqual(x, y);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getHashCode(Object x, SessionFactoryImplementor factory)
/* 127:    */   {
/* 128:145 */     return getHashCode(x);
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected static void replaceNode(Node container, Element value)
/* 132:    */   {
/* 133:149 */     if (container != value)
/* 134:    */     {
/* 135:150 */       Element parent = container.getParent();
/* 136:151 */       container.detach();
/* 137:152 */       value.setName(container.getName());
/* 138:153 */       value.detach();
/* 139:154 */       parent.add(value);
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Type getSemiResolvedType(SessionFactoryImplementor factory)
/* 144:    */   {
/* 145:159 */     return this;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache, ForeignKeyDirection foreignKeyDirection)
/* 149:    */     throws HibernateException
/* 150:    */   {
/* 151:    */     boolean include;
/* 152:    */     boolean include;
/* 153:171 */     if (isAssociationType())
/* 154:    */     {
/* 155:172 */       AssociationType atype = (AssociationType)this;
/* 156:173 */       include = atype.getForeignKeyDirection() == foreignKeyDirection;
/* 157:    */     }
/* 158:    */     else
/* 159:    */     {
/* 160:176 */       include = ForeignKeyDirection.FOREIGN_KEY_FROM_PARENT == foreignKeyDirection;
/* 161:    */     }
/* 162:178 */     return include ? replace(original, target, session, owner, copyCache) : target;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void beforeAssemble(Serializable cached, SessionImplementor session) {}
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AbstractType
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.dom4j.Node;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.MappingException;
/*  14:    */ import org.hibernate.engine.spi.Mapping;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  17:    */ import org.hibernate.metamodel.relational.Size;
/*  18:    */ 
/*  19:    */ public class MetaType
/*  20:    */   extends AbstractType
/*  21:    */ {
/*  22: 46 */   public static final String[] REGISTRATION_KEYS = new String[0];
/*  23:    */   private final Map values;
/*  24:    */   private final Map keys;
/*  25:    */   private final Type baseType;
/*  26:    */   
/*  27:    */   public MetaType(Map values, Type baseType)
/*  28:    */   {
/*  29: 53 */     this.baseType = baseType;
/*  30: 54 */     this.values = values;
/*  31: 55 */     this.keys = new HashMap();
/*  32: 56 */     Iterator iter = values.entrySet().iterator();
/*  33: 57 */     while (iter.hasNext())
/*  34:    */     {
/*  35: 58 */       Map.Entry me = (Map.Entry)iter.next();
/*  36: 59 */       this.keys.put(me.getValue(), me.getKey());
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String[] getRegistrationKeys()
/*  41:    */   {
/*  42: 64 */     return REGISTRATION_KEYS;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int[] sqlTypes(Mapping mapping)
/*  46:    */     throws MappingException
/*  47:    */   {
/*  48: 68 */     return this.baseType.sqlTypes(mapping);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Size[] dictatedSizes(Mapping mapping)
/*  52:    */     throws MappingException
/*  53:    */   {
/*  54: 73 */     return this.baseType.dictatedSizes(mapping);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Size[] defaultSizes(Mapping mapping)
/*  58:    */     throws MappingException
/*  59:    */   {
/*  60: 78 */     return this.baseType.defaultSizes(mapping);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getColumnSpan(Mapping mapping)
/*  64:    */     throws MappingException
/*  65:    */   {
/*  66: 82 */     return this.baseType.getColumnSpan(mapping);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Class getReturnedClass()
/*  70:    */   {
/*  71: 86 */     return String.class;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/*  75:    */     throws HibernateException, SQLException
/*  76:    */   {
/*  77: 95 */     Object key = this.baseType.nullSafeGet(rs, names, session, owner);
/*  78: 96 */     return key == null ? null : this.values.get(key);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
/*  82:    */     throws HibernateException, SQLException
/*  83:    */   {
/*  84:105 */     Object key = this.baseType.nullSafeGet(rs, name, session, owner);
/*  85:106 */     return key == null ? null : this.values.get(key);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/*  89:    */     throws HibernateException, SQLException
/*  90:    */   {
/*  91:111 */     this.baseType.nullSafeSet(st, value == null ? null : this.keys.get(value), index, session);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/*  95:    */     throws HibernateException, SQLException
/*  96:    */   {
/*  97:121 */     if (settable[0] != 0) {
/*  98:121 */       nullSafeSet(st, value, index, session);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 103:    */     throws HibernateException
/* 104:    */   {
/* 105:125 */     return toXMLString(value, factory);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String toXMLString(Object value, SessionFactoryImplementor factory)
/* 109:    */     throws HibernateException
/* 110:    */   {
/* 111:130 */     return (String)value;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Object fromXMLString(String xml, Mapping factory)
/* 115:    */     throws HibernateException
/* 116:    */   {
/* 117:135 */     return xml;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getName()
/* 121:    */   {
/* 122:139 */     return this.baseType.getName();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Object deepCopy(Object value, SessionFactoryImplementor factory)
/* 126:    */     throws HibernateException
/* 127:    */   {
/* 128:144 */     return value;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 132:    */   {
/* 133:154 */     return original;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isMutable()
/* 137:    */   {
/* 138:158 */     return false;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 142:    */     throws HibernateException
/* 143:    */   {
/* 144:162 */     return fromXMLString(xml.getText(), factory);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/* 148:    */     throws HibernateException
/* 149:    */   {
/* 150:166 */     node.setText(toXMLString(value, factory));
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 154:    */   {
/* 155:170 */     throw new UnsupportedOperationException();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 159:    */     throws HibernateException
/* 160:    */   {
/* 161:174 */     return (checkable[0] != 0) && (isDirty(old, current, session));
/* 162:    */   }
/* 163:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.MetaType
 * JD-Core Version:    0.7.0.1
 */
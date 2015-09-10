/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.sql.PreparedStatement;
/*  5:   */ import java.sql.ResultSet;
/*  6:   */ import java.sql.SQLException;
/*  7:   */ import org.hibernate.HibernateException;
/*  8:   */ import org.hibernate.MappingException;
/*  9:   */ import org.hibernate.engine.spi.Mapping;
/* 10:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 11:   */ import org.hibernate.metamodel.relational.Size;
/* 12:   */ 
/* 13:   */ @Deprecated
/* 14:   */ public abstract class AbstractLobType
/* 15:   */   extends AbstractType
/* 16:   */   implements Serializable
/* 17:   */ {
/* 18:   */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 19:   */     throws HibernateException
/* 20:   */   {
/* 21:44 */     return !isEqual(old, current);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Size[] dictatedSizes(Mapping mapping)
/* 25:   */     throws MappingException
/* 26:   */   {
/* 27:49 */     return new Size[] { LEGACY_DICTATED_SIZE };
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Size[] defaultSizes(Mapping mapping)
/* 31:   */     throws MappingException
/* 32:   */   {
/* 33:54 */     return new Size[] { LEGACY_DEFAULT_SIZE };
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean isEqual(Object x, Object y)
/* 37:   */   {
/* 38:59 */     return isEqual(x, y, null);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getHashCode(Object x)
/* 42:   */   {
/* 43:64 */     return getHashCode(x, null);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String getName()
/* 47:   */   {
/* 48:68 */     return getClass().getName();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int getColumnSpan(Mapping mapping)
/* 52:   */     throws MappingException
/* 53:   */   {
/* 54:72 */     return 1;
/* 55:   */   }
/* 56:   */   
/* 57:   */   protected abstract Object get(ResultSet paramResultSet, String paramString)
/* 58:   */     throws SQLException;
/* 59:   */   
/* 60:   */   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 61:   */     throws HibernateException, SQLException
/* 62:   */   {
/* 63:79 */     return get(rs, names[0]);
/* 64:   */   }
/* 65:   */   
/* 66:   */   public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
/* 67:   */     throws HibernateException, SQLException
/* 68:   */   {
/* 69:84 */     return get(rs, name);
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/* 73:   */     throws HibernateException, SQLException
/* 74:   */   {
/* 75:90 */     if (settable[0] != 0) {
/* 76:90 */       set(st, value, index, session);
/* 77:   */     }
/* 78:   */   }
/* 79:   */   
/* 80:   */   protected abstract void set(PreparedStatement paramPreparedStatement, Object paramObject, int paramInt, SessionImplementor paramSessionImplementor)
/* 81:   */     throws SQLException;
/* 82:   */   
/* 83:   */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/* 84:   */     throws HibernateException, SQLException
/* 85:   */   {
/* 86:98 */     set(st, value, index, session);
/* 87:   */   }
/* 88:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AbstractLobType
 * JD-Core Version:    0.7.0.1
 */
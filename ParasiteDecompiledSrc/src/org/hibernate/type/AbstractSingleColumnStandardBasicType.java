/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/*  8:   */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/*  9:   */ 
/* 10:   */ public abstract class AbstractSingleColumnStandardBasicType<T>
/* 11:   */   extends AbstractStandardBasicType<T>
/* 12:   */   implements SingleColumnType<T>
/* 13:   */ {
/* 14:   */   public AbstractSingleColumnStandardBasicType(SqlTypeDescriptor sqlTypeDescriptor, JavaTypeDescriptor<T> javaTypeDescriptor)
/* 15:   */   {
/* 16:44 */     super(sqlTypeDescriptor, javaTypeDescriptor);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public final int sqlType()
/* 20:   */   {
/* 21:48 */     return getSqlTypeDescriptor().getSqlType();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public final void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/* 25:   */     throws HibernateException, SQLException
/* 26:   */   {
/* 27:56 */     if (settable[0] != 0) {
/* 28:57 */       nullSafeSet(st, value, index, session);
/* 29:   */     }
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AbstractSingleColumnStandardBasicType
 * JD-Core Version:    0.7.0.1
 */
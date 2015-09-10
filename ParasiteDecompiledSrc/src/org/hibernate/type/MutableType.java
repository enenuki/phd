/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ 
/*  8:   */ /**
/*  9:   */  * @deprecated
/* 10:   */  */
/* 11:   */ public abstract class MutableType
/* 12:   */   extends NullableType
/* 13:   */ {
/* 14:   */   public final boolean isMutable()
/* 15:   */   {
/* 16:40 */     return true;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected abstract Object deepCopyNotNull(Object paramObject)
/* 20:   */     throws HibernateException;
/* 21:   */   
/* 22:   */   public final Object deepCopy(Object value, SessionFactoryImplementor factory)
/* 23:   */     throws HibernateException
/* 24:   */   {
/* 25:46 */     return value == null ? null : deepCopyNotNull(value);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 29:   */     throws HibernateException
/* 30:   */   {
/* 31:55 */     if (isEqual(original, target)) {
/* 32:56 */       return original;
/* 33:   */     }
/* 34:58 */     return deepCopy(original, session.getFactory());
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.MutableType
 * JD-Core Version:    0.7.0.1
 */
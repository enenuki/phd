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
/* 11:   */ public abstract class ImmutableType
/* 12:   */   extends NullableType
/* 13:   */ {
/* 14:   */   public final Object deepCopy(Object value, SessionFactoryImplementor factory)
/* 15:   */   {
/* 16:40 */     return value;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public final boolean isMutable()
/* 20:   */   {
/* 21:44 */     return false;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 25:   */     throws HibernateException
/* 26:   */   {
/* 27:54 */     return original;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ImmutableType
 * JD-Core Version:    0.7.0.1
 */
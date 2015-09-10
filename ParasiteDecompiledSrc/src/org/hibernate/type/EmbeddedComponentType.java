/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  6:   */ import org.hibernate.tuple.component.ComponentMetamodel;
/*  7:   */ import org.hibernate.tuple.component.ComponentTuplizer;
/*  8:   */ 
/*  9:   */ public class EmbeddedComponentType
/* 10:   */   extends ComponentType
/* 11:   */ {
/* 12:   */   public EmbeddedComponentType(TypeFactory.TypeScope typeScope, ComponentMetamodel metamodel)
/* 13:   */   {
/* 14:37 */     super(typeScope, metamodel);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isEmbedded()
/* 18:   */   {
/* 19:41 */     return true;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean isMethodOf(Method method)
/* 23:   */   {
/* 24:45 */     return this.componentTuplizer.isMethodOf(method);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Object instantiate(Object parent, SessionImplementor session)
/* 28:   */     throws HibernateException
/* 29:   */   {
/* 30:49 */     boolean useParent = (parent != null) && (super.getReturnedClass().isInstance(parent));
/* 31:   */     
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:55 */     return useParent ? parent : super.instantiate(parent, session);
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.EmbeddedComponentType
 * JD-Core Version:    0.7.0.1
 */
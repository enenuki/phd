/*  1:   */ package org.hibernate.metamodel.source.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.EmptyInterceptor;
/*  5:   */ import org.hibernate.Interceptor;
/*  6:   */ import org.hibernate.ObjectNotFoundException;
/*  7:   */ import org.hibernate.SessionFactory;
/*  8:   */ import org.hibernate.SessionFactory.SessionFactoryOptions;
/*  9:   */ import org.hibernate.internal.SessionFactoryImpl;
/* 10:   */ import org.hibernate.metamodel.SessionFactoryBuilder;
/* 11:   */ import org.hibernate.metamodel.source.MetadataImplementor;
/* 12:   */ import org.hibernate.proxy.EntityNotFoundDelegate;
/* 13:   */ 
/* 14:   */ public class SessionFactoryBuilderImpl
/* 15:   */   implements SessionFactoryBuilder
/* 16:   */ {
/* 17:   */   SessionFactoryOptionsImpl options;
/* 18:   */   private final MetadataImplementor metadata;
/* 19:   */   
/* 20:   */   SessionFactoryBuilderImpl(MetadataImplementor metadata)
/* 21:   */   {
/* 22:47 */     this.metadata = metadata;
/* 23:48 */     this.options = new SessionFactoryOptionsImpl(null);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public SessionFactoryBuilder with(Interceptor interceptor)
/* 27:   */   {
/* 28:53 */     this.options.interceptor = interceptor;
/* 29:54 */     return this;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public SessionFactoryBuilder with(EntityNotFoundDelegate entityNotFoundDelegate)
/* 33:   */   {
/* 34:59 */     this.options.entityNotFoundDelegate = entityNotFoundDelegate;
/* 35:60 */     return this;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public SessionFactory buildSessionFactory()
/* 39:   */   {
/* 40:65 */     return new SessionFactoryImpl(this.metadata, this.options, null);
/* 41:   */   }
/* 42:   */   
/* 43:   */   private static class SessionFactoryOptionsImpl
/* 44:   */     implements SessionFactory.SessionFactoryOptions
/* 45:   */   {
/* 46:69 */     private Interceptor interceptor = EmptyInterceptor.INSTANCE;
/* 47:72 */     private EntityNotFoundDelegate entityNotFoundDelegate = new EntityNotFoundDelegate()
/* 48:   */     {
/* 49:   */       public void handleEntityNotFound(String entityName, Serializable id)
/* 50:   */       {
/* 51:74 */         throw new ObjectNotFoundException(id, entityName);
/* 52:   */       }
/* 53:   */     };
/* 54:   */     
/* 55:   */     public Interceptor getInterceptor()
/* 56:   */     {
/* 57:80 */       return this.interceptor;
/* 58:   */     }
/* 59:   */     
/* 60:   */     public EntityNotFoundDelegate getEntityNotFoundDelegate()
/* 61:   */     {
/* 62:85 */       return this.entityNotFoundDelegate;
/* 63:   */     }
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.internal.SessionFactoryBuilderImpl
 * JD-Core Version:    0.7.0.1
 */
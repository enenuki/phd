/*  1:   */ package org.hibernate.id;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Properties;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.MappingException;
/*  7:   */ import org.hibernate.dialect.Dialect;
/*  8:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  9:   */ import org.hibernate.persister.entity.EntityPersister;
/* 10:   */ import org.hibernate.type.Type;
/* 11:   */ 
/* 12:   */ public class Assigned
/* 13:   */   implements IdentifierGenerator, Configurable
/* 14:   */ {
/* 15:   */   private String entityName;
/* 16:   */   
/* 17:   */   public Serializable generate(SessionImplementor session, Object obj)
/* 18:   */     throws HibernateException
/* 19:   */   {
/* 20:50 */     Serializable id = session.getEntityPersister(this.entityName, obj).getIdentifier(obj, session);
/* 21:51 */     if (id == null) {
/* 22:52 */       throw new IdentifierGenerationException("ids for this class must be manually assigned before calling save(): " + this.entityName);
/* 23:   */     }
/* 24:57 */     return id;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void configure(Type type, Properties params, Dialect d)
/* 28:   */     throws MappingException
/* 29:   */   {
/* 30:61 */     this.entityName = params.getProperty("entity_name");
/* 31:62 */     if (this.entityName == null) {
/* 32:63 */       throw new MappingException("no entity name");
/* 33:   */     }
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.Assigned
 * JD-Core Version:    0.7.0.1
 */
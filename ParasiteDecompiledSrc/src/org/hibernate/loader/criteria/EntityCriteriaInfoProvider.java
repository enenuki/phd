/*  1:   */ package org.hibernate.loader.criteria;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.persister.entity.PropertyMapping;
/*  5:   */ import org.hibernate.persister.entity.Queryable;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ class EntityCriteriaInfoProvider
/*  9:   */   implements CriteriaInfoProvider
/* 10:   */ {
/* 11:   */   Queryable persister;
/* 12:   */   
/* 13:   */   EntityCriteriaInfoProvider(Queryable persister)
/* 14:   */   {
/* 15:41 */     this.persister = persister;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:45 */     return this.persister.getEntityName();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Serializable[] getSpaces()
/* 24:   */   {
/* 25:49 */     return this.persister.getQuerySpaces();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public PropertyMapping getPropertyMapping()
/* 29:   */   {
/* 30:53 */     return this.persister;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Type getType(String relativePath)
/* 34:   */   {
/* 35:57 */     return this.persister.toType(relativePath);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.criteria.EntityCriteriaInfoProvider
 * JD-Core Version:    0.7.0.1
 */
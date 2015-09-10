/*  1:   */ package org.hibernate.loader.criteria;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  5:   */ import org.hibernate.persister.collection.QueryableCollection;
/*  6:   */ import org.hibernate.persister.entity.PropertyMapping;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ class ScalarCollectionCriteriaInfoProvider
/* 10:   */   implements CriteriaInfoProvider
/* 11:   */ {
/* 12:   */   String role;
/* 13:   */   QueryableCollection persister;
/* 14:   */   SessionFactoryHelper helper;
/* 15:   */   
/* 16:   */   ScalarCollectionCriteriaInfoProvider(SessionFactoryHelper helper, String role)
/* 17:   */   {
/* 18:44 */     this.role = role;
/* 19:45 */     this.helper = helper;
/* 20:46 */     this.persister = helper.requireQueryableCollection(role);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:50 */     return this.role;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Serializable[] getSpaces()
/* 29:   */   {
/* 30:54 */     return this.persister.getCollectionSpaces();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public PropertyMapping getPropertyMapping()
/* 34:   */   {
/* 35:58 */     return this.helper.getCollectionPropertyMapping(this.role);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Type getType(String relativePath)
/* 39:   */   {
/* 40:64 */     return getPropertyMapping().toType(relativePath);
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.criteria.ScalarCollectionCriteriaInfoProvider
 * JD-Core Version:    0.7.0.1
 */
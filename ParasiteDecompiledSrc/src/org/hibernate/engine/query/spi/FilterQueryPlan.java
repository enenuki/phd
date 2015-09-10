/*  1:   */ package org.hibernate.engine.query.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  6:   */ 
/*  7:   */ public class FilterQueryPlan
/*  8:   */   extends HQLQueryPlan
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   private final String collectionRole;
/* 12:   */   
/* 13:   */   public FilterQueryPlan(String hql, String collectionRole, boolean shallow, Map enabledFilters, SessionFactoryImplementor factory)
/* 14:   */   {
/* 15:47 */     super(hql, collectionRole, shallow, enabledFilters, factory);
/* 16:48 */     this.collectionRole = collectionRole;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getCollectionRole()
/* 20:   */   {
/* 21:52 */     return this.collectionRole;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.FilterQueryPlan
 * JD-Core Version:    0.7.0.1
 */
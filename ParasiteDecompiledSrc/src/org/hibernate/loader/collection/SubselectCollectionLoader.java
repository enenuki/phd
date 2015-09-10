/*  1:   */ package org.hibernate.loader.collection;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import java.util.Map;
/*  7:   */ import org.hibernate.HibernateException;
/*  8:   */ import org.hibernate.MappingException;
/*  9:   */ import org.hibernate.engine.spi.EntityKey;
/* 10:   */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/* 11:   */ import org.hibernate.engine.spi.QueryParameters;
/* 12:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/* 13:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 14:   */ import org.hibernate.persister.collection.QueryableCollection;
/* 15:   */ import org.hibernate.type.Type;
/* 16:   */ 
/* 17:   */ public class SubselectCollectionLoader
/* 18:   */   extends BasicCollectionLoader
/* 19:   */ {
/* 20:   */   private final Serializable[] keys;
/* 21:   */   private final Type[] types;
/* 22:   */   private final Object[] values;
/* 23:   */   private final Map namedParameters;
/* 24:   */   private final Map namedParameterLocMap;
/* 25:   */   
/* 26:   */   public SubselectCollectionLoader(QueryableCollection persister, String subquery, Collection entityKeys, QueryParameters queryParameters, Map namedParameterLocMap, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/* 27:   */     throws MappingException
/* 28:   */   {
/* 29:61 */     super(persister, 1, subquery, factory, loadQueryInfluencers);
/* 30:   */     
/* 31:63 */     this.keys = new Serializable[entityKeys.size()];
/* 32:64 */     Iterator iter = entityKeys.iterator();
/* 33:65 */     int i = 0;
/* 34:66 */     while (iter.hasNext()) {
/* 35:67 */       this.keys[(i++)] = ((EntityKey)iter.next()).getIdentifier();
/* 36:   */     }
/* 37:70 */     this.namedParameters = queryParameters.getNamedParameters();
/* 38:71 */     this.types = queryParameters.getFilteredPositionalParameterTypes();
/* 39:72 */     this.values = queryParameters.getFilteredPositionalParameterValues();
/* 40:73 */     this.namedParameterLocMap = namedParameterLocMap;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void initialize(Serializable id, SessionImplementor session)
/* 44:   */     throws HibernateException
/* 45:   */   {
/* 46:79 */     loadCollectionSubselect(session, this.keys, this.values, this.types, this.namedParameters, getKeyType());
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int[] getNamedParameterLocs(String name)
/* 50:   */   {
/* 51:90 */     return (int[])this.namedParameterLocMap.get(name);
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.collection.SubselectCollectionLoader
 * JD-Core Version:    0.7.0.1
 */
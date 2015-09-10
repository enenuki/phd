/*  1:   */ package org.hibernate.engine.spi;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import java.util.Set;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ import org.hibernate.persister.entity.Loadable;
/*  7:   */ import org.hibernate.persister.entity.PropertyMapping;
/*  8:   */ 
/*  9:   */ public class SubselectFetch
/* 10:   */ {
/* 11:   */   private final Set resultingEntityKeys;
/* 12:   */   private final String queryString;
/* 13:   */   private final String alias;
/* 14:   */   private final Loadable loadable;
/* 15:   */   private final QueryParameters queryParameters;
/* 16:   */   private final Map namedParameterLocMap;
/* 17:   */   
/* 18:   */   public SubselectFetch(String alias, Loadable loadable, QueryParameters queryParameters, Set resultingEntityKeys, Map namedParameterLocMap)
/* 19:   */   {
/* 20:52 */     this.resultingEntityKeys = resultingEntityKeys;
/* 21:53 */     this.queryParameters = queryParameters;
/* 22:54 */     this.namedParameterLocMap = namedParameterLocMap;
/* 23:55 */     this.loadable = loadable;
/* 24:56 */     this.alias = alias;
/* 25:   */     
/* 26:   */ 
/* 27:59 */     String queryString = queryParameters.getFilteredSQL();
/* 28:60 */     int fromIndex = queryString.indexOf(" from ");
/* 29:61 */     int orderByIndex = queryString.lastIndexOf("order by");
/* 30:62 */     this.queryString = (orderByIndex > 0 ? queryString.substring(fromIndex, orderByIndex) : queryString.substring(fromIndex));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public QueryParameters getQueryParameters()
/* 34:   */   {
/* 35:69 */     return this.queryParameters;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Set getResult()
/* 39:   */   {
/* 40:76 */     return this.resultingEntityKeys;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String toSubselectString(String ukname)
/* 44:   */   {
/* 45:81 */     String[] joinColumns = ukname == null ? StringHelper.qualify(this.alias, this.loadable.getIdentifierColumnNames()) : ((PropertyMapping)this.loadable).toColumns(this.alias, ukname);
/* 46:   */     
/* 47:   */ 
/* 48:   */ 
/* 49:85 */     return "select " + StringHelper.join(", ", joinColumns) + this.queryString;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:94 */     return "SubselectFetch(" + this.queryString + ')';
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Map getNamedParameterLocMap()
/* 58:   */   {
/* 59:98 */     return this.namedParameterLocMap;
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.SubselectFetch
 * JD-Core Version:    0.7.0.1
 */
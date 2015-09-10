/*  1:   */ package org.hibernate.engine.query.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Set;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class QueryMetadata
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   private final String sourceQuery;
/* 11:   */   private final ParameterMetadata parameterMetadata;
/* 12:   */   private final String[] returnAliases;
/* 13:   */   private final Type[] returnTypes;
/* 14:   */   private final Set querySpaces;
/* 15:   */   
/* 16:   */   public QueryMetadata(String sourceQuery, ParameterMetadata parameterMetadata, String[] returnAliases, Type[] returnTypes, Set querySpaces)
/* 17:   */   {
/* 18:49 */     this.sourceQuery = sourceQuery;
/* 19:50 */     this.parameterMetadata = parameterMetadata;
/* 20:51 */     this.returnAliases = returnAliases;
/* 21:52 */     this.returnTypes = returnTypes;
/* 22:53 */     this.querySpaces = querySpaces;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getSourceQuery()
/* 26:   */   {
/* 27:62 */     return this.sourceQuery;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public ParameterMetadata getParameterMetadata()
/* 31:   */   {
/* 32:66 */     return this.parameterMetadata;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String[] getReturnAliases()
/* 36:   */   {
/* 37:75 */     return this.returnAliases;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Type[] getReturnTypes()
/* 41:   */   {
/* 42:84 */     return this.returnTypes;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Set getQuerySpaces()
/* 46:   */   {
/* 47:93 */     return this.querySpaces;
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.QueryMetadata
 * JD-Core Version:    0.7.0.1
 */
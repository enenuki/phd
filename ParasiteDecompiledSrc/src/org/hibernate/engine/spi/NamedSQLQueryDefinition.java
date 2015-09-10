/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.CacheMode;
/*   6:    */ import org.hibernate.FlushMode;
/*   7:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
/*   8:    */ 
/*   9:    */ public class NamedSQLQueryDefinition
/*  10:    */   extends NamedQueryDefinition
/*  11:    */ {
/*  12:    */   private NativeSQLQueryReturn[] queryReturns;
/*  13:    */   private final List<String> querySpaces;
/*  14:    */   private final boolean callable;
/*  15:    */   private String resultSetRef;
/*  16:    */   
/*  17:    */   public NamedSQLQueryDefinition(String name, String query, NativeSQLQueryReturn[] queryReturns, List<String> querySpaces, boolean cacheable, String cacheRegion, Integer timeout, Integer fetchSize, FlushMode flushMode, CacheMode cacheMode, boolean readOnly, String comment, Map parameterTypes, boolean callable)
/*  18:    */   {
/*  19: 80 */     super(name, query.trim(), cacheable, cacheRegion, timeout, fetchSize, flushMode, cacheMode, readOnly, comment, parameterTypes);
/*  20:    */     
/*  21:    */ 
/*  22:    */ 
/*  23:    */ 
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32: 93 */     this.queryReturns = queryReturns;
/*  33: 94 */     this.querySpaces = querySpaces;
/*  34: 95 */     this.callable = callable;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public NamedSQLQueryDefinition(String name, String query, String resultSetRef, List<String> querySpaces, boolean cacheable, String cacheRegion, Integer timeout, Integer fetchSize, FlushMode flushMode, CacheMode cacheMode, boolean readOnly, String comment, Map parameterTypes, boolean callable)
/*  38:    */   {
/*  39:132 */     super(name, query.trim(), cacheable, cacheRegion, timeout, fetchSize, flushMode, cacheMode, readOnly, comment, parameterTypes);
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:145 */     this.resultSetRef = resultSetRef;
/*  53:146 */     this.querySpaces = querySpaces;
/*  54:147 */     this.callable = callable;
/*  55:    */   }
/*  56:    */   
/*  57:    */   /**
/*  58:    */    * @deprecated
/*  59:    */    */
/*  60:    */   public NamedSQLQueryDefinition(String query, String resultSetRef, List<String> querySpaces, boolean cacheable, String cacheRegion, Integer timeout, Integer fetchSize, FlushMode flushMode, Map parameterTypes, boolean callable)
/*  61:    */   {
/*  62:180 */     this(null, query, resultSetRef, querySpaces, cacheable, cacheRegion, timeout, fetchSize, flushMode, null, false, null, parameterTypes, callable);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public NativeSQLQueryReturn[] getQueryReturns()
/*  66:    */   {
/*  67:199 */     return this.queryReturns;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List<String> getQuerySpaces()
/*  71:    */   {
/*  72:203 */     return this.querySpaces;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isCallable()
/*  76:    */   {
/*  77:207 */     return this.callable;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getResultSetRef()
/*  81:    */   {
/*  82:211 */     return this.resultSetRef;
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.NamedSQLQueryDefinition
 * JD-Core Version:    0.7.0.1
 */
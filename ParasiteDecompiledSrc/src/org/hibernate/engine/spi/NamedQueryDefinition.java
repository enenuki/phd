/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.CacheMode;
/*   6:    */ import org.hibernate.FlushMode;
/*   7:    */ 
/*   8:    */ public class NamedQueryDefinition
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private final String name;
/*  12:    */   private final String query;
/*  13:    */   private final boolean cacheable;
/*  14:    */   private final String cacheRegion;
/*  15:    */   private final Integer timeout;
/*  16:    */   private final Integer fetchSize;
/*  17:    */   private final FlushMode flushMode;
/*  18:    */   private final Map parameterTypes;
/*  19:    */   private CacheMode cacheMode;
/*  20:    */   private boolean readOnly;
/*  21:    */   private String comment;
/*  22:    */   
/*  23:    */   public NamedQueryDefinition(String query, boolean cacheable, String cacheRegion, Integer timeout, Integer fetchSize, FlushMode flushMode, Map parameterTypes)
/*  24:    */   {
/*  25: 60 */     this(null, query, cacheable, cacheRegion, timeout, fetchSize, flushMode, null, false, null, parameterTypes);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public NamedQueryDefinition(String name, String query, boolean cacheable, String cacheRegion, Integer timeout, Integer fetchSize, FlushMode flushMode, CacheMode cacheMode, boolean readOnly, String comment, Map parameterTypes)
/*  29:    */   {
/*  30: 87 */     this.name = name;
/*  31: 88 */     this.query = query;
/*  32: 89 */     this.cacheable = cacheable;
/*  33: 90 */     this.cacheRegion = cacheRegion;
/*  34: 91 */     this.timeout = timeout;
/*  35: 92 */     this.fetchSize = fetchSize;
/*  36: 93 */     this.flushMode = flushMode;
/*  37: 94 */     this.parameterTypes = parameterTypes;
/*  38: 95 */     this.cacheMode = cacheMode;
/*  39: 96 */     this.readOnly = readOnly;
/*  40: 97 */     this.comment = comment;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getName()
/*  44:    */   {
/*  45:101 */     return this.name;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getQueryString()
/*  49:    */   {
/*  50:105 */     return this.query;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isCacheable()
/*  54:    */   {
/*  55:109 */     return this.cacheable;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getCacheRegion()
/*  59:    */   {
/*  60:113 */     return this.cacheRegion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Integer getFetchSize()
/*  64:    */   {
/*  65:117 */     return this.fetchSize;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Integer getTimeout()
/*  69:    */   {
/*  70:121 */     return this.timeout;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public FlushMode getFlushMode()
/*  74:    */   {
/*  75:125 */     return this.flushMode;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String toString()
/*  79:    */   {
/*  80:129 */     return getClass().getName() + '(' + this.query + ')';
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Map getParameterTypes()
/*  84:    */   {
/*  85:133 */     return this.parameterTypes;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getQuery()
/*  89:    */   {
/*  90:137 */     return this.query;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public CacheMode getCacheMode()
/*  94:    */   {
/*  95:141 */     return this.cacheMode;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isReadOnly()
/*  99:    */   {
/* 100:145 */     return this.readOnly;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getComment()
/* 104:    */   {
/* 105:149 */     return this.comment;
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.NamedQueryDefinition
 * JD-Core Version:    0.7.0.1
 */
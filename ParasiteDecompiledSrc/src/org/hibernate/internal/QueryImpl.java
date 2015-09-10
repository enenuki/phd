/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.FlushMode;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.LockMode;
/*   9:    */ import org.hibernate.LockOptions;
/*  10:    */ import org.hibernate.Query;
/*  11:    */ import org.hibernate.ScrollMode;
/*  12:    */ import org.hibernate.ScrollableResults;
/*  13:    */ import org.hibernate.engine.query.spi.ParameterMetadata;
/*  14:    */ import org.hibernate.engine.spi.QueryParameters;
/*  15:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  16:    */ 
/*  17:    */ public class QueryImpl
/*  18:    */   extends AbstractQueryImpl
/*  19:    */ {
/*  20: 49 */   private LockOptions lockOptions = new LockOptions();
/*  21:    */   
/*  22:    */   public QueryImpl(String queryString, FlushMode flushMode, SessionImplementor session, ParameterMetadata parameterMetadata)
/*  23:    */   {
/*  24: 56 */     super(queryString, flushMode, session, parameterMetadata);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public QueryImpl(String queryString, SessionImplementor session, ParameterMetadata parameterMetadata)
/*  28:    */   {
/*  29: 60 */     this(queryString, null, session, parameterMetadata);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Iterator iterate()
/*  33:    */     throws HibernateException
/*  34:    */   {
/*  35: 64 */     verifyParameters();
/*  36: 65 */     Map namedParams = getNamedParams();
/*  37: 66 */     before();
/*  38:    */     try
/*  39:    */     {
/*  40: 68 */       return getSession().iterate(expandParameterLists(namedParams), getQueryParameters(namedParams));
/*  41:    */     }
/*  42:    */     finally
/*  43:    */     {
/*  44: 74 */       after();
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ScrollableResults scroll()
/*  49:    */     throws HibernateException
/*  50:    */   {
/*  51: 79 */     return scroll(ScrollMode.SCROLL_INSENSITIVE);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ScrollableResults scroll(ScrollMode scrollMode)
/*  55:    */     throws HibernateException
/*  56:    */   {
/*  57: 83 */     verifyParameters();
/*  58: 84 */     Map namedParams = getNamedParams();
/*  59: 85 */     before();
/*  60: 86 */     QueryParameters qp = getQueryParameters(namedParams);
/*  61: 87 */     qp.setScrollMode(scrollMode);
/*  62:    */     try
/*  63:    */     {
/*  64: 89 */       return getSession().scroll(expandParameterLists(namedParams), qp);
/*  65:    */     }
/*  66:    */     finally
/*  67:    */     {
/*  68: 92 */       after();
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public List list()
/*  73:    */     throws HibernateException
/*  74:    */   {
/*  75: 97 */     verifyParameters();
/*  76: 98 */     Map namedParams = getNamedParams();
/*  77: 99 */     before();
/*  78:    */     try
/*  79:    */     {
/*  80:101 */       return getSession().list(expandParameterLists(namedParams), getQueryParameters(namedParams));
/*  81:    */     }
/*  82:    */     finally
/*  83:    */     {
/*  84:107 */       after();
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int executeUpdate()
/*  89:    */     throws HibernateException
/*  90:    */   {
/*  91:112 */     verifyParameters();
/*  92:113 */     Map namedParams = getNamedParams();
/*  93:114 */     before();
/*  94:    */     try
/*  95:    */     {
/*  96:116 */       return getSession().executeUpdate(expandParameterLists(namedParams), getQueryParameters(namedParams));
/*  97:    */     }
/*  98:    */     finally
/*  99:    */     {
/* 100:122 */       after();
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Query setLockMode(String alias, LockMode lockMode)
/* 105:    */   {
/* 106:127 */     this.lockOptions.setAliasSpecificLockMode(alias, lockMode);
/* 107:128 */     return this;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Query setLockOptions(LockOptions lockOption)
/* 111:    */   {
/* 112:132 */     this.lockOptions.setLockMode(lockOption.getLockMode());
/* 113:133 */     this.lockOptions.setScope(lockOption.getScope());
/* 114:134 */     this.lockOptions.setTimeOut(lockOption.getTimeOut());
/* 115:135 */     return this;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public LockOptions getLockOptions()
/* 119:    */   {
/* 120:139 */     return this.lockOptions;
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.QueryImpl
 * JD-Core Version:    0.7.0.1
 */
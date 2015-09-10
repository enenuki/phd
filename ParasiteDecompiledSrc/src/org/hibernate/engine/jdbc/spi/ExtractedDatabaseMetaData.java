/*  1:   */ package org.hibernate.engine.jdbc.spi;
/*  2:   */ 
/*  3:   */ import java.util.LinkedHashSet;
/*  4:   */ import java.util.Set;
/*  5:   */ import org.hibernate.engine.jdbc.internal.TypeInfo;
/*  6:   */ 
/*  7:   */ public abstract interface ExtractedDatabaseMetaData
/*  8:   */ {
/*  9:   */   public abstract boolean supportsScrollableResults();
/* 10:   */   
/* 11:   */   public abstract boolean supportsGetGeneratedKeys();
/* 12:   */   
/* 13:   */   public abstract boolean supportsBatchUpdates();
/* 14:   */   
/* 15:   */   public abstract boolean supportsDataDefinitionInTransaction();
/* 16:   */   
/* 17:   */   public abstract boolean doesDataDefinitionCauseTransactionCommit();
/* 18:   */   
/* 19:   */   public abstract Set<String> getExtraKeywords();
/* 20:   */   
/* 21:   */   public abstract SQLStateType getSqlStateType();
/* 22:   */   
/* 23:   */   public abstract boolean doesLobLocatorUpdateCopy();
/* 24:   */   
/* 25:   */   public abstract String getConnectionSchemaName();
/* 26:   */   
/* 27:   */   public abstract String getConnectionCatalogName();
/* 28:   */   
/* 29:   */   public abstract LinkedHashSet<TypeInfo> getTypeInfoSet();
/* 30:   */   
/* 31:   */   public static enum SQLStateType
/* 32:   */   {
/* 33:41 */     XOpen,  SQL99,  UNKOWN;
/* 34:   */     
/* 35:   */     private SQLStateType() {}
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.ExtractedDatabaseMetaData
 * JD-Core Version:    0.7.0.1
 */
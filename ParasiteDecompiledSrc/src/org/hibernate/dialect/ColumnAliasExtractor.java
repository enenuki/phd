/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSetMetaData;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ 
/*  6:   */ public abstract interface ColumnAliasExtractor
/*  7:   */ {
/*  8:51 */   public static final ColumnAliasExtractor COLUMN_LABEL_EXTRACTOR = new ColumnAliasExtractor()
/*  9:   */   {
/* 10:   */     public String extractColumnAlias(ResultSetMetaData metaData, int position)
/* 11:   */       throws SQLException
/* 12:   */     {
/* 13:54 */       return metaData.getColumnLabel(position);
/* 14:   */     }
/* 15:   */   };
/* 16:61 */   public static final ColumnAliasExtractor COLUMN_NAME_EXTRACTOR = new ColumnAliasExtractor()
/* 17:   */   {
/* 18:   */     public String extractColumnAlias(ResultSetMetaData metaData, int position)
/* 19:   */       throws SQLException
/* 20:   */     {
/* 21:64 */       return metaData.getColumnName(position);
/* 22:   */     }
/* 23:   */   };
/* 24:   */   
/* 25:   */   public abstract String extractColumnAlias(ResultSetMetaData paramResultSetMetaData, int paramInt)
/* 26:   */     throws SQLException;
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.ColumnAliasExtractor
 * JD-Core Version:    0.7.0.1
 */
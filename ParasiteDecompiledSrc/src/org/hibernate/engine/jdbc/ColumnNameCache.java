/*  1:   */ package org.hibernate.engine.jdbc;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSet;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import java.util.Map;
/*  6:   */ import java.util.concurrent.ConcurrentHashMap;
/*  7:   */ 
/*  8:   */ public class ColumnNameCache
/*  9:   */ {
/* 10:   */   public static final float LOAD_FACTOR = 0.75F;
/* 11:   */   private final Map<String, Integer> columnNameToIndexCache;
/* 12:   */   
/* 13:   */   public ColumnNameCache(int columnCount)
/* 14:   */   {
/* 15:42 */     this.columnNameToIndexCache = new ConcurrentHashMap(columnCount + (int)(columnCount * 0.75F) + 1, 0.75F);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getIndexForColumnName(String columnName, ResultSet rs)
/* 19:   */     throws SQLException
/* 20:   */   {
/* 21:46 */     Integer cached = (Integer)this.columnNameToIndexCache.get(columnName);
/* 22:47 */     if (cached != null) {
/* 23:48 */       return cached.intValue();
/* 24:   */     }
/* 25:51 */     int index = rs.findColumn(columnName);
/* 26:52 */     this.columnNameToIndexCache.put(columnName, Integer.valueOf(index));
/* 27:53 */     return index;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.ColumnNameCache
 * JD-Core Version:    0.7.0.1
 */
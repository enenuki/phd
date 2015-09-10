/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSet;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public class IndexMetadata
/*  9:   */ {
/* 10:   */   private final String name;
/* 11:37 */   private final List columns = new ArrayList();
/* 12:   */   
/* 13:   */   IndexMetadata(ResultSet rs)
/* 14:   */     throws SQLException
/* 15:   */   {
/* 16:40 */     this.name = rs.getString("INDEX_NAME");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:44 */     return this.name;
/* 22:   */   }
/* 23:   */   
/* 24:   */   void addColumn(ColumnMetadata column)
/* 25:   */   {
/* 26:48 */     if (column != null) {
/* 27:48 */       this.columns.add(column);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public ColumnMetadata[] getColumns()
/* 32:   */   {
/* 33:52 */     return (ColumnMetadata[])this.columns.toArray(new ColumnMetadata[0]);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String toString()
/* 37:   */   {
/* 38:56 */     return "IndexMatadata(" + this.name + ')';
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.IndexMetadata
 * JD-Core Version:    0.7.0.1
 */
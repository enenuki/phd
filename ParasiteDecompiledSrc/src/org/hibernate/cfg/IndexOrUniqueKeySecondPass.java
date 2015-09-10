/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.AnnotationException;
/*  5:   */ import org.hibernate.MappingException;
/*  6:   */ import org.hibernate.mapping.Column;
/*  7:   */ import org.hibernate.mapping.Index;
/*  8:   */ import org.hibernate.mapping.Table;
/*  9:   */ import org.hibernate.mapping.UniqueKey;
/* 10:   */ 
/* 11:   */ public class IndexOrUniqueKeySecondPass
/* 12:   */   implements SecondPass
/* 13:   */ {
/* 14:   */   private Table table;
/* 15:   */   private final String indexName;
/* 16:   */   private final String[] columns;
/* 17:   */   private final Mappings mappings;
/* 18:   */   private final Ejb3Column column;
/* 19:   */   private final boolean unique;
/* 20:   */   
/* 21:   */   public IndexOrUniqueKeySecondPass(Table table, String indexName, String[] columns, Mappings mappings)
/* 22:   */   {
/* 23:47 */     this.table = table;
/* 24:48 */     this.indexName = indexName;
/* 25:49 */     this.columns = columns;
/* 26:50 */     this.mappings = mappings;
/* 27:51 */     this.column = null;
/* 28:52 */     this.unique = false;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public IndexOrUniqueKeySecondPass(String indexName, Ejb3Column column, Mappings mappings)
/* 32:   */   {
/* 33:59 */     this(indexName, column, mappings, false);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public IndexOrUniqueKeySecondPass(String indexName, Ejb3Column column, Mappings mappings, boolean unique)
/* 37:   */   {
/* 38:66 */     this.indexName = indexName;
/* 39:67 */     this.column = column;
/* 40:68 */     this.columns = null;
/* 41:69 */     this.mappings = mappings;
/* 42:70 */     this.unique = unique;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void doSecondPass(Map persistentClasses)
/* 46:   */     throws MappingException
/* 47:   */   {
/* 48:74 */     if (this.columns != null) {
/* 49:75 */       for (String columnName : this.columns) {
/* 50:76 */         addConstraintToColumn(columnName);
/* 51:   */       }
/* 52:   */     }
/* 53:79 */     if (this.column != null)
/* 54:   */     {
/* 55:80 */       this.table = this.column.getTable();
/* 56:81 */       addConstraintToColumn(this.mappings.getLogicalColumnName(this.column.getMappingColumn().getQuotedName(), this.table));
/* 57:   */     }
/* 58:   */   }
/* 59:   */   
/* 60:   */   private void addConstraintToColumn(String columnName)
/* 61:   */   {
/* 62:86 */     Column column = this.table.getColumn(new Column(this.mappings.getPhysicalColumnName(columnName, this.table)));
/* 63:91 */     if (column == null) {
/* 64:92 */       throw new AnnotationException("@Index references a unknown column: " + columnName);
/* 65:   */     }
/* 66:96 */     if (this.unique) {
/* 67:97 */       this.table.getOrCreateUniqueKey(this.indexName).addColumn(column);
/* 68:   */     } else {
/* 69:99 */       this.table.getOrCreateIndex(this.indexName).addColumn(column);
/* 70:   */     }
/* 71:   */   }
/* 72:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.IndexOrUniqueKeySecondPass
 * JD-Core Version:    0.7.0.1
 */
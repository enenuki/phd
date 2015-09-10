/*   1:    */ package org.hibernate.metamodel.source.annotations.entity;
/*   2:    */ 
/*   3:    */ import org.hibernate.metamodel.source.binder.TableSource;
/*   4:    */ 
/*   5:    */ class TableSourceImpl
/*   6:    */   implements TableSource
/*   7:    */ {
/*   8:    */   private final String schema;
/*   9:    */   private final String catalog;
/*  10:    */   private final String tableName;
/*  11:    */   private final String logicalName;
/*  12:    */   
/*  13:    */   TableSourceImpl(String schema, String catalog, String tableName, String logicalName)
/*  14:    */   {
/*  15: 35 */     this.schema = schema;
/*  16: 36 */     this.catalog = catalog;
/*  17: 37 */     this.tableName = tableName;
/*  18: 38 */     this.logicalName = logicalName;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String getExplicitSchemaName()
/*  22:    */   {
/*  23: 43 */     return this.schema;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getExplicitCatalogName()
/*  27:    */   {
/*  28: 48 */     return this.catalog;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getExplicitTableName()
/*  32:    */   {
/*  33: 53 */     return this.tableName;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getLogicalName()
/*  37:    */   {
/*  38: 58 */     return this.logicalName;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean equals(Object o)
/*  42:    */   {
/*  43: 63 */     if (this == o) {
/*  44: 64 */       return true;
/*  45:    */     }
/*  46: 66 */     if ((o == null) || (getClass() != o.getClass())) {
/*  47: 67 */       return false;
/*  48:    */     }
/*  49: 70 */     TableSourceImpl that = (TableSourceImpl)o;
/*  50: 72 */     if (this.catalog != null ? !this.catalog.equals(that.catalog) : that.catalog != null) {
/*  51: 73 */       return false;
/*  52:    */     }
/*  53: 75 */     if (this.logicalName != null ? !this.logicalName.equals(that.logicalName) : that.logicalName != null) {
/*  54: 76 */       return false;
/*  55:    */     }
/*  56: 78 */     if (this.schema != null ? !this.schema.equals(that.schema) : that.schema != null) {
/*  57: 79 */       return false;
/*  58:    */     }
/*  59: 81 */     if (this.tableName != null ? !this.tableName.equals(that.tableName) : that.tableName != null) {
/*  60: 82 */       return false;
/*  61:    */     }
/*  62: 85 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int hashCode()
/*  66:    */   {
/*  67: 90 */     int result = this.schema != null ? this.schema.hashCode() : 0;
/*  68: 91 */     result = 31 * result + (this.catalog != null ? this.catalog.hashCode() : 0);
/*  69: 92 */     result = 31 * result + (this.tableName != null ? this.tableName.hashCode() : 0);
/*  70: 93 */     result = 31 * result + (this.logicalName != null ? this.logicalName.hashCode() : 0);
/*  71: 94 */     return result;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String toString()
/*  75:    */   {
/*  76: 99 */     StringBuilder sb = new StringBuilder();
/*  77:100 */     sb.append("TableSourceImpl");
/*  78:101 */     sb.append("{schema='").append(this.schema).append('\'');
/*  79:102 */     sb.append(", catalog='").append(this.catalog).append('\'');
/*  80:103 */     sb.append(", tableName='").append(this.tableName).append('\'');
/*  81:104 */     sb.append(", logicalName='").append(this.logicalName).append('\'');
/*  82:105 */     sb.append('}');
/*  83:106 */     return sb.toString();
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.TableSourceImpl
 * JD-Core Version:    0.7.0.1
 */
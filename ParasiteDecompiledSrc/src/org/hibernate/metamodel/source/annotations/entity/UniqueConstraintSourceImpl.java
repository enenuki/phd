/*  1:   */ package org.hibernate.metamodel.source.annotations.entity;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.metamodel.source.binder.UniqueConstraintSource;
/*  5:   */ 
/*  6:   */ class UniqueConstraintSourceImpl
/*  7:   */   implements UniqueConstraintSource
/*  8:   */ {
/*  9:   */   private final String name;
/* 10:   */   private final String tableName;
/* 11:   */   private final List<String> columnNames;
/* 12:   */   
/* 13:   */   UniqueConstraintSourceImpl(String name, String tableName, List<String> columnNames)
/* 14:   */   {
/* 15:39 */     this.name = name;
/* 16:40 */     this.tableName = tableName;
/* 17:41 */     this.columnNames = columnNames;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String name()
/* 21:   */   {
/* 22:46 */     return this.name;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getTableName()
/* 26:   */   {
/* 27:51 */     return this.tableName;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Iterable<String> columnNames()
/* 31:   */   {
/* 32:56 */     return this.columnNames;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean equals(Object o)
/* 36:   */   {
/* 37:61 */     if (this == o) {
/* 38:62 */       return true;
/* 39:   */     }
/* 40:64 */     if ((o == null) || (getClass() != o.getClass())) {
/* 41:65 */       return false;
/* 42:   */     }
/* 43:68 */     UniqueConstraintSourceImpl that = (UniqueConstraintSourceImpl)o;
/* 44:70 */     if (this.columnNames != null ? !this.columnNames.equals(that.columnNames) : that.columnNames != null) {
/* 45:71 */       return false;
/* 46:   */     }
/* 47:73 */     if (this.name != null ? !this.name.equals(that.name) : that.name != null) {
/* 48:74 */       return false;
/* 49:   */     }
/* 50:76 */     if (this.tableName != null ? !this.tableName.equals(that.tableName) : that.tableName != null) {
/* 51:77 */       return false;
/* 52:   */     }
/* 53:80 */     return true;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int hashCode()
/* 57:   */   {
/* 58:85 */     int result = this.name != null ? this.name.hashCode() : 0;
/* 59:86 */     result = 31 * result + (this.tableName != null ? this.tableName.hashCode() : 0);
/* 60:87 */     result = 31 * result + (this.columnNames != null ? this.columnNames.hashCode() : 0);
/* 61:88 */     return result;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public String toString()
/* 65:   */   {
/* 66:93 */     StringBuilder sb = new StringBuilder();
/* 67:94 */     sb.append("UniqueConstraintSourceImpl");
/* 68:95 */     sb.append("{name='").append(this.name).append('\'');
/* 69:96 */     sb.append(", tableName='").append(this.tableName).append('\'');
/* 70:97 */     sb.append(", columnNames=").append(this.columnNames);
/* 71:98 */     sb.append('}');
/* 72:99 */     return sb.toString();
/* 73:   */   }
/* 74:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.UniqueConstraintSourceImpl
 * JD-Core Version:    0.7.0.1
 */
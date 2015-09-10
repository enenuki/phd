/*  1:   */ package org.hibernate.engine.query.spi.sql;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.Type;
/*  4:   */ 
/*  5:   */ public class NativeSQLQueryScalarReturn
/*  6:   */   implements NativeSQLQueryReturn
/*  7:   */ {
/*  8:   */   private final Type type;
/*  9:   */   private final String columnAlias;
/* 10:   */   private final int hashCode;
/* 11:   */   
/* 12:   */   public NativeSQLQueryScalarReturn(String alias, Type type)
/* 13:   */   {
/* 14:39 */     this.type = type;
/* 15:40 */     this.columnAlias = alias;
/* 16:41 */     this.hashCode = determineHashCode();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getColumnAlias()
/* 20:   */   {
/* 21:45 */     return this.columnAlias;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Type getType()
/* 25:   */   {
/* 26:49 */     return this.type;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean equals(Object o)
/* 30:   */   {
/* 31:53 */     if (this == o) {
/* 32:54 */       return true;
/* 33:   */     }
/* 34:56 */     if ((o == null) || (getClass() != o.getClass())) {
/* 35:57 */       return false;
/* 36:   */     }
/* 37:60 */     NativeSQLQueryScalarReturn that = (NativeSQLQueryScalarReturn)o;
/* 38:62 */     if (this.columnAlias != null ? !this.columnAlias.equals(that.columnAlias) : that.columnAlias != null) {
/* 39:63 */       return false;
/* 40:   */     }
/* 41:65 */     if (this.type != null ? !this.type.equals(that.type) : that.type != null) {
/* 42:66 */       return false;
/* 43:   */     }
/* 44:69 */     return true;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int hashCode()
/* 48:   */   {
/* 49:73 */     return this.hashCode;
/* 50:   */   }
/* 51:   */   
/* 52:   */   private int determineHashCode()
/* 53:   */   {
/* 54:77 */     int result = this.type != null ? this.type.hashCode() : 0;
/* 55:78 */     result = 31 * result + getClass().getName().hashCode();
/* 56:79 */     result = 31 * result + (this.columnAlias != null ? this.columnAlias.hashCode() : 0);
/* 57:80 */     return result;
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.sql.NativeSQLQueryScalarReturn
 * JD-Core Version:    0.7.0.1
 */
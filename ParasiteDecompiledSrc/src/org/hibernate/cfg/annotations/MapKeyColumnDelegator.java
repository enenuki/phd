/*  1:   */ package org.hibernate.cfg.annotations;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.MapKeyColumn;
/*  6:   */ 
/*  7:   */ public class MapKeyColumnDelegator
/*  8:   */   implements Column
/*  9:   */ {
/* 10:   */   private final MapKeyColumn column;
/* 11:   */   
/* 12:   */   public MapKeyColumnDelegator(MapKeyColumn column)
/* 13:   */   {
/* 14:37 */     this.column = column;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String name()
/* 18:   */   {
/* 19:41 */     return this.column.name();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean unique()
/* 23:   */   {
/* 24:45 */     return this.column.unique();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean nullable()
/* 28:   */   {
/* 29:49 */     return this.column.nullable();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean insertable()
/* 33:   */   {
/* 34:53 */     return this.column.insertable();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean updatable()
/* 38:   */   {
/* 39:57 */     return this.column.updatable();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String columnDefinition()
/* 43:   */   {
/* 44:61 */     return this.column.columnDefinition();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String table()
/* 48:   */   {
/* 49:65 */     return this.column.table();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int length()
/* 53:   */   {
/* 54:69 */     return this.column.length();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public int precision()
/* 58:   */   {
/* 59:73 */     return this.column.precision();
/* 60:   */   }
/* 61:   */   
/* 62:   */   public int scale()
/* 63:   */   {
/* 64:77 */     return this.column.scale();
/* 65:   */   }
/* 66:   */   
/* 67:   */   public Class<? extends Annotation> annotationType()
/* 68:   */   {
/* 69:81 */     return Column.class;
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.MapKeyColumnDelegator
 * JD-Core Version:    0.7.0.1
 */
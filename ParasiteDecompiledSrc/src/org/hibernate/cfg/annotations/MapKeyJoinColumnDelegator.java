/*  1:   */ package org.hibernate.cfg.annotations;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import javax.persistence.JoinColumn;
/*  6:   */ import javax.persistence.MapKeyJoinColumn;
/*  7:   */ 
/*  8:   */ public class MapKeyJoinColumnDelegator
/*  9:   */   implements JoinColumn
/* 10:   */ {
/* 11:   */   private final MapKeyJoinColumn column;
/* 12:   */   
/* 13:   */   public MapKeyJoinColumnDelegator(MapKeyJoinColumn column)
/* 14:   */   {
/* 15:38 */     this.column = column;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String name()
/* 19:   */   {
/* 20:42 */     return this.column.name();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String referencedColumnName()
/* 24:   */   {
/* 25:46 */     return this.column.referencedColumnName();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean unique()
/* 29:   */   {
/* 30:50 */     return this.column.unique();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean nullable()
/* 34:   */   {
/* 35:54 */     return this.column.nullable();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean insertable()
/* 39:   */   {
/* 40:58 */     return this.column.insertable();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean updatable()
/* 44:   */   {
/* 45:62 */     return this.column.updatable();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String columnDefinition()
/* 49:   */   {
/* 50:66 */     return this.column.columnDefinition();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String table()
/* 54:   */   {
/* 55:70 */     return this.column.table();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public Class<? extends Annotation> annotationType()
/* 59:   */   {
/* 60:74 */     return Column.class;
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.MapKeyJoinColumnDelegator
 * JD-Core Version:    0.7.0.1
 */
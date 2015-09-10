/*  1:   */ package org.hibernate.cfg.annotations;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import javax.persistence.Column;
/*  5:   */ import org.hibernate.annotations.Columns;
/*  6:   */ 
/*  7:   */ public class CustomizableColumns
/*  8:   */   implements Columns
/*  9:   */ {
/* 10:   */   private final Column[] columns;
/* 11:   */   
/* 12:   */   public CustomizableColumns(Column[] columns)
/* 13:   */   {
/* 14:38 */     this.columns = columns;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Column[] columns()
/* 18:   */   {
/* 19:42 */     return this.columns;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Class<? extends Annotation> annotationType()
/* 23:   */   {
/* 24:46 */     return Columns.class;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.CustomizableColumns
 * JD-Core Version:    0.7.0.1
 */
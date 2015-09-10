/*  1:   */ package org.hibernate.id.insert;
/*  2:   */ 
/*  3:   */ import org.hibernate.dialect.Dialect;
/*  4:   */ import org.hibernate.sql.Insert;
/*  5:   */ 
/*  6:   */ public class IdentifierGeneratingInsert
/*  7:   */   extends Insert
/*  8:   */ {
/*  9:   */   public IdentifierGeneratingInsert(Dialect dialect)
/* 10:   */   {
/* 11:38 */     super(dialect);
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.insert.IdentifierGeneratingInsert
 * JD-Core Version:    0.7.0.1
 */
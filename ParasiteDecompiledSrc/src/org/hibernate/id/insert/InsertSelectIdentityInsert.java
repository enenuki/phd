/*  1:   */ package org.hibernate.id.insert;
/*  2:   */ 
/*  3:   */ import org.hibernate.dialect.Dialect;
/*  4:   */ 
/*  5:   */ public class InsertSelectIdentityInsert
/*  6:   */   extends IdentifierGeneratingInsert
/*  7:   */ {
/*  8:   */   public InsertSelectIdentityInsert(Dialect dialect)
/*  9:   */   {
/* 10:37 */     super(dialect);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public String toStatementString()
/* 14:   */   {
/* 15:41 */     return getDialect().appendIdentitySelectToInsert(super.toStatementString());
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.insert.InsertSelectIdentityInsert
 * JD-Core Version:    0.7.0.1
 */
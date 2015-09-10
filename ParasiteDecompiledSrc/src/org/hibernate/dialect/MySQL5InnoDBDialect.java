/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ public class MySQL5InnoDBDialect
/*  4:   */   extends MySQL5Dialect
/*  5:   */ {
/*  6:   */   public boolean supportsCascadeDelete()
/*  7:   */   {
/*  8:33 */     return true;
/*  9:   */   }
/* 10:   */   
/* 11:   */   public String getTableTypeString()
/* 12:   */   {
/* 13:37 */     return " ENGINE=InnoDB";
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean hasSelfReferentialForeignKeyBug()
/* 17:   */   {
/* 18:41 */     return true;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.MySQL5InnoDBDialect
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ public class MySQLMyISAMDialect
/*  4:   */   extends MySQLDialect
/*  5:   */ {
/*  6:   */   public String getTableTypeString()
/*  7:   */   {
/*  8:33 */     return " type=MyISAM";
/*  9:   */   }
/* 10:   */   
/* 11:   */   public boolean dropConstraints()
/* 12:   */   {
/* 13:37 */     return false;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.MySQLMyISAMDialect
 * JD-Core Version:    0.7.0.1
 */
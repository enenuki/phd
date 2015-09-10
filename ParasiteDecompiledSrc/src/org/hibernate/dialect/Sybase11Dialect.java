/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import org.hibernate.sql.JoinFragment;
/*  4:   */ import org.hibernate.sql.Sybase11JoinFragment;
/*  5:   */ 
/*  6:   */ public class Sybase11Dialect
/*  7:   */   extends AbstractTransactSQLDialect
/*  8:   */ {
/*  9:   */   public JoinFragment createOuterJoinFragment()
/* 10:   */   {
/* 11:38 */     return new Sybase11JoinFragment();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getCrossJoinSeparator()
/* 15:   */   {
/* 16:42 */     return ", ";
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.Sybase11Dialect
 * JD-Core Version:    0.7.0.1
 */